package pl.netanel.swt.matrix;

import static pl.netanel.swt.matrix.Matrix.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import pl.netanel.util.OsUtil;
import pl.netanel.util.Preconditions;

/**
 * Provides editing support for zone cells.
 * <p>
 * @author Jacek Kolodziejczyk created 07-06-2011
 * @param <N0> defines indexing type for rows
 * @param <N1> defines indexing type for columns
 */
public abstract class ZoneEditor<N0 extends Number, N1 extends Number> {
	private static final String DEFAULT_TRUE_TEXT = "\u2713";

	private static final String NEW_LINE = System.getProperty("line.separator");
	private static final String ZONE_EDITOR_DATA = "edited cell";
	
	Control control;
	Number[] editedCell;
	Image trueImage, falseImage;
	Calendar calendar = Calendar.getInstance();
	private String falseLabel;
	private String trueLabel;

	final Zone<N0, N1> zone;

	private String systemThemePath;
	private Painter<N0, N1> cellsPainter;
	CommandListener controlListener;
	boolean checkboxPainterRequired;

	private EmbeddedControlsPainter embedded;

	/**
	 * Default constructor, facilitates the specified zone editing. 
	 * @param zone
	 */
	public ZoneEditor(Zone<N0, N1> zone) {
		super();
		this.zone = zone;
		zone.setEditor(this);
		
		trueLabel = DEFAULT_TRUE_TEXT;
		setSystemThemePath(null);
		
		cellsPainter = zone.getPainter("cells");
		zone.replacePainter(new Painter<N0, N1>("editor emulation", 
				Painter.SCOPE_CELLS_HORIZONTALLY) {
			public void paint(N0 index0, N1 index1, int x, int y, int width, int height) {
				Object[] emul = getCheckboxEmulation(index0, index1);
				if (emul == null) return;
				Object value = getModelValue(index0, index1);
				
				if (emul[0] instanceof Image || emul[1] instanceof Image) {
					text = null;
					imageAlignX = imageAlignY = SWT.CENTER;
					image = (Image) (Boolean.TRUE.equals(value) ? emul[0] : emul[1]); 
				} else {
					image = null;
					textAlignX = textAlignY = SWT.CENTER;
					text = (String) (Boolean.TRUE.equals(value) ? emul[0] : emul[1]);
				}
				super.paint(index0, index1, x, y, width, height);
			};
		});
		zone.addPainter(embedded = new EmbeddedControlsPainter(this));
	
		zone.bind(CMD_CUT, SWT.KeyDown, SWT.MOD1 | 'x');
		zone.bind(CMD_COPY, SWT.KeyDown, SWT.MOD1 | 'c');
		zone.bind(CMD_PASTE, SWT.KeyDown, SWT.MOD1 | 'v');
		zone.bind(CMD_EDIT, SWT.KeyUp, SWT.F2);
		zone.bind(CMD_EDIT, SWT.MouseDoubleClick, 1);
		zone.bind(CMD_DELETE, SWT.KeyDown, SWT.DEL);
		// Space bar for check boxes
		zone.bind(new GestureBinding(CMD_EDIT, SWT.KeyDown, ' ') {
			@Override
			public boolean isMatching(Event e) {
				
				// Change boolean value when clicked on the check box image
				N0 index0 = getMatrix().getAxis0().getFocusItem().getIndex();
				N1 index1 = getMatrix().getAxis1().getFocusItem().getIndex();
				Control control2 = embedded.getControl(index0, index1);
				if (control2 != null && 
						control2 instanceof Button && (control2.getStyle() & SWT.CHECK) != 0 ||
						getCheckboxEmulation(index0, index1) != null ) {
					return true;
				}
				return false;
			}
		});
		// Clicking on the image emulation
		zone.bindings.add(new GestureBinding(CMD_EDIT, SWT.MouseDown, SWT.DEL) {
			@Override
			public boolean isMatching(Event e) {
				
				// Change boolean value when clicked on the check box image
				N0 index0 = getMatrix().getAxis0().getFocusItem().getIndex();
				N1 index1 = getMatrix().getAxis1().getFocusItem().getIndex();
				Object[] emulation = getCheckboxEmulation(index0, index1);
				
				if (emulation != null && 
						(emulation[0] instanceof Image || emulation[1] instanceof Image)) {
					// Calculate the image bounds
					Rectangle cellBounds = getMatrix().getBody().getCellBounds(index0, index1);
					Rectangle imageBounds = trueImage.getBounds();
					imageBounds.x = cellBounds.x + (cellBounds.width - imageBounds.width) / 2;
					imageBounds.y = cellBounds.y + (cellBounds.height - imageBounds.height) / 2;
					return imageBounds.contains(e.x, e.y);
				}
				return false;
			}
		});
		
		controlListener = new CommandListener() {
			public void handleEvent(Event e) {
				if (e.type == SWT.Selection && (e.widget.getStyle() & SWT.CHECK) != 0) {
					control = (Control) e.widget;
					apply();
				}
				else {
					super.handleEvent(e);
				}
			};
			protected void executeCommand(int commandId) {
				
				switch (commandId) {
				case CMD_APPLY_EDIT: apply(); break;
				case CMD_CANCEL_EDIT: cancel(); break;
				}
			};
		};
		controlListener.bind(CMD_APPLY_EDIT, SWT.KeyDown, SWT.CR);
		controlListener.bind(CMD_APPLY_EDIT, SWT.FocusOut, 0);
		controlListener.bind(CMD_CANCEL_EDIT, SWT.KeyDown, SWT.ESC);
	}

	/**
	 * Returns a value from the specified control.
	 * <p>
	 * It returns: <ul>
	 * <li>{@link String} from {@link Text} and {@link Combo} controls
	 * <li>{@link java.util.Date} from {@link DateTime} control 
	 * <li>{@link Boolean} from check {@link Button}
	 * </ul>  
	 * @return a value from the specified control
	 */
	public Object getEditorValue(Control control) {
		if (control == null || control.isDisposed()) return null;
		if (control instanceof Text) {
			return ((Text) control).getText();
		} 
		else if (control instanceof Combo) {
			return ((Combo) control).getText();
//			Combo combo = (Combo) control;
//			int selectionIndex = combo.getSelectionIndex();
//			return selectionIndex == -1 ? null : combo.getItem(selectionIndex);
		} 
		else if (control instanceof DateTime) {
			DateTime date = (DateTime) control;
			calendar.clear();
			calendar.set(Calendar.YEAR, date.getYear());
			calendar.set(Calendar.MONTH, date.getMonth());
			calendar.set(Calendar.DAY_OF_MONTH, date.getDay());
			return calendar.getTime();
		} 
		
		return null;
	}

	/**
	 * Sets the value in the cell editor control.
	 * @param control to set the value for
	 * @param value to set in the control
	 */
	public void setEditorValue(Control control, Object value) {
		if (control == null || control.isDisposed()) return;
		if (control instanceof Text) {
			((Text) control).setText(value == null ? "" : value.toString());
		} 
		else if (control instanceof Combo) {
			((Combo) control).setText(value == null ? "" : value.toString());
		} 
		else if (control instanceof Button && (control.getStyle() & SWT.CHECK) != 0) {
			Button button = (Button) control;
			button.setSelection((Boolean) value);
		}
		else if (control instanceof DateTime) {
//			if (value != null && !(value instanceof Date)) {
//				try {
//					value = dateFormat.parse(value.toString());
//				} 
//				catch (ParseException e) {
//					throw new RuntimeException(e);
//				}
//			}
			calendar.setTime((Date) value);
			DateTime dateTime = (DateTime) control;
			dateTime.setDate(
					calendar.get(Calendar.YEAR), 
					calendar.get(Calendar.MONTH), 
					calendar.get(Calendar.DAY_OF_MONTH));
		} 
//		else if (booleanEditor) {
//			setModelValue(activeIndex0, activeIndex1, Boolean.FALSE.equals(value));
//		}
	}

	/** 
	 * @param index0 cell index on <code>axis0</code> 
	 * @param index1 cell index on <code>axis1</code> 
	 */
	protected Object getModelValue(N0 index0, N1 index1) {
		return cellsPainter == null ? null : cellsPainter.getText(index0, index1); 
	}
	
	protected abstract void setModelValue(N0 index0, N1 index1, Object value);
	
	
	private void apply() {
		ZoneEditorData data = (ZoneEditorData) getData(control);
		setModelValue(data.index0, data.index1, getEditorValue(control));
		cancel();
		getMatrix().redraw();
	}
	
	private void cancel() {
		if (control != null && !getData(control).isEmbedded) {
			control.dispose();
			control = null;
		}
		getMatrix().setFocus();
	}

	
	/**
	 * Shows a control to edit the value of the specified cell. 
	 */
	Control activate(N0 index0, N1 index1) {
		cellsPainter = zone.getPainter("cells");
		
		Control control = embedded.getControl(index0, index1);
		Object[] emulation = getCheckboxEmulation(index0, index1);
		if (control != null || emulation != null) {			
			setModelValue(index0, index1, !Boolean.TRUE.equals(getModelValue(index0, index1)));
			getMatrix().redraw();
		} 
		else {
			control = addControl(index0, index1);
			this.control = control; 
		}
		
		return control;
	}
	
	Control addControl(N0 index0, N1 index1) {
		Control control = createControl(index0, index1);
		if (control != null) {
			ZoneEditorData data = new ZoneEditorData(index0, index1, 
					hasEmbeddedControl(index0, index1));
			control.setData(ZONE_EDITOR_DATA, data);
			setEditorValue(control, getModelValue(index0, index1));
			setBounds(index0, index1, control);
			control.moveAbove(getMatrix());
			
			if (!data.isEmbedded) {
				controlListener.attachTo(control);
				control.moveAbove(getMatrix());
				control.setFocus();
			}
		}
		return control;
	}
	
	/**
	 * Creates a control to edit the value of the specified cell.
	 * <p> 
	 * It creates a {@link Text} control by default. 
	 * The method should return null to make the cell read only. 
	 *  
	 * @param index0 cell index on <code>axis0</code> 
	 * @param index1 cell index on <code>axis1</code> 
	 * @return
	 */
	protected Control createControl(N0 index0, N1 index1) {
		return new Text(getMatrix().getParent(), SWT.BORDER);
	}
	
	
	/**
	 * @param index0 cell index on <code>axis0</code>  
	 * @param index1 cell index on <code>axis1</code>  
	 * @param control
	 */
	protected void setBounds(N0 index0, N1 index1, Control control) {
		Rectangle bounds = getMatrix().getBody().getCellBounds(index0, index1);
		Painter.offsetRectangle(bounds, 1);			

		int style = control.getStyle();
		Point size  = null;
		if (control instanceof Button && (style & SWT.CHECK) != 0 ) {
			size = control.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			if (size.y > size.x) size.y = size.x;
			control.setSize(size);
		} else {
			control.setBounds(bounds);
			size = control.getSize();
		}
		control.setLocation(
				bounds.x + Painter.align(SWT.CENTER, 0, size.x, bounds.width),			
				bounds.y + Painter.align(SWT.CENTER, 0, size.y, bounds.height));	
	}

	public Object[] getCheckboxEmulation(N0 index0, N1 index1) {
		return null;
	}
	
	protected final Object[] getDefaultCheckBoxImages() {
		if (trueImage == null && falseImage == null) {
			return null;
		}
		else {
			return new Object[] {trueImage, falseImage};
		} 
	}
	protected final Object[] getDefaultCheckBoxLabels() {
		if (trueLabel == null && falseLabel == null) {
			return null;
		}
		else {
			return new Object[] {trueLabel, falseLabel};
		} 
	}
	
	/**
	 * Returns true if the cell editor control for the specified cell
	 * is embedded in the cell, or false if it pops up.
	 * 
	 * @param index0
	 * @param index1
	 * @return
	 */
	public boolean hasEmbeddedControl(N0 index0, N1 index1) {
		return false;
	}

	
	
	/*------------------------------------------------------------------------
	 * Commands
	 */
	
	void edit() {
		Matrix<N0, N1> matrix = getMatrix();
		final AxisItem<N0> focusItem0 = matrix.axis0.getFocusItem();
		final AxisItem<N1> focusItem1 = matrix.axis1.getFocusItem();
		if (focusItem0 == null || focusItem1 == null) return;

//		final Display display = getMatrix().getDisplay();
//		display.asyncExec(new Runnable() {
//			@Override
//			public void run() {
//				if (getMatrix().isDisposed()) return;
				activate(focusItem0.getIndex(), focusItem1.getIndex());
//			}
//		});
	}

	/**
	 * Sets the <code>null</code> value to the selected cells.
	 */
	void delete() {
		Iterator<Number[]> it = zone.getSelectedIterator();
		
		while (it.hasNext()) {
			Number[] next = it.next();
			N0 index0 = (N0) next[0];
			N1 index1 = (N1) next[1];
			setModelValue(index0, index1, null);
		}
		embedded.layoutModified = true;
		getMatrix().redraw();
	}
	
	/**
	 * Copy selected cells to the clipboard. 
	 * <p>
	 * Only rectangular area can be copied. 
	 */
	public void copy() 
	{
		StringBuilder sb = new StringBuilder();
		cellsPainter = zone.getPainter("cells");

		Number[] n = zone.getSelectedExtent();
		N0 max0 = (N0) n[1];
		N1 max1 = (N1) n[3];
		Iterator<Number[]> it = zone.getSelectedBoundsIterator();
		
		while (it.hasNext()) {
			Number[] next = it.next();
			N0 index0 = (N0) next[0];
			N1 index1 = (N1) next[1];
			sb.append(zone.isSelected(index0, index1) ? format(index0, index1) : "");
			if (getMatrix().axis1.math.compare(index1, max1) < 0) sb.append("\t");
			else if (getMatrix().axis0.math.compare(index0, max0) < 0) {
				sb.append(NEW_LINE);
			}
		}
		if (sb.length() > 0) {
			Clipboard clipboard = new Clipboard(getMatrix().getDisplay());
			clipboard.setContents(new Object[] {sb.toString()},
					new Transfer[] {TextTransfer.getInstance()});		
			clipboard.dispose();
		}
	}
	
	/**
	 * Paste form the clipboard to the zone starting from the focus cell.
	 * <p>
	 * The items in the clipboard exceeding the section item count will be ignored. 
	 */
	public void paste() 
	{
		Clipboard clipboard = new Clipboard(getMatrix().getDisplay());
		Object contents = clipboard.getContents(TextTransfer.getInstance());
		clipboard.dispose();
		
		if (contents == null) return;
		
		Axis<N0> axis0 = getMatrix().getAxis0();
		Axis<N1> axis1 = getMatrix().getAxis1();

		// Get the focus cell to start the pasting from
		AxisItem<N0> focusItem0 = axis0.getFocusItem();
		AxisItem<N1> focusItem1 = axis1.getFocusItem();
		if (focusItem0 == null || focusItem1 == null) return;
		Math<N0> math0 = axis0.math;
		Math<N1> math1 = axis1.math;
		N0 start0 = focusItem0.getIndex(), index0 = start0;
		N1 start1 = focusItem1.getIndex(), index1 = start1;
		N0 count0 = axis0.getBody().getCount();
		N1 count1 = axis1.getBody().getCount();
		
		String[] rows = contents.toString().split(NEW_LINE);
		for (int i = 0; i < rows.length && math0.compare(index0, count0) < 0; i++) {
			String[] cells = split(rows[i], "\t");
			index1 = start1;
			for (int j = 0; j < cells.length && math1.compare(index1, count1) < 0; j++) {
				Object value = parse(index0, index1, cells[j]);
				if (value != null) {
					setModelValue(index0, index1, value);
				}
				index1 = math1.increment(index1);
			}
			index0 = math0.increment(index0);
		}
		
		embedded.layoutModified = true;
		getMatrix().redraw();
	}

	/**
	 * Cuts the selected cells by copying them to the clipboard 
	 * and setting <code>null</code> value to them.
	 * <p>
	 * Only rectangular area can be copied.
	 *  
	 * @see #copy()
	 */
	public void cut() {
		copy();

		// Set null value in the selected cells
		Iterator<Number[]> it = zone.getSelectedBoundsIterator();
		while (it.hasNext()) {
			Number[] next = it.next();
			N0 index0 = (N0) next[0];
			N1 index1 = (N1) next[1];
			if (zone.isSelected(index0, index1) ) {
				setModelValue(index0, index1, null);
			}
		}
		embedded.layoutModified = true;
		getMatrix().redraw();
	}
	
	protected String format(N0 index0, N1 index1) {
		Object value = getModelValue(index0, index1);
		return value == null ? "" : value.toString();
	}
	
	protected Object parse(N0 index0, N1 index1, String s) {
		return s;
	}

	/**
	 * Overcomes the limitation of the String split method needed to parse 
	 * the pasted text.
	 * 
	 * @param s String to parse
	 * @param separator 
	 * @return
	 */
	private String[] split(String s, String separator) {
		ArrayList<String> list = new ArrayList();
		int pos1 = 0, pos2 = 0;
		while (true) {
			pos1 = pos2;
			pos2 = s.indexOf("\t", pos2);
			if (pos2 == -1) {
				list.add(pos1 == s.length() ? null : s.substring(pos1, s.length()));
				break;
			}
			if (pos1 == pos2) {
				list.add(null);
			} else {
				list.add(s.substring(pos1, pos2));
			}
			pos2++;
		}
		return list.toArray(new String[] {});
	}



	Matrix<N0, N1> getMatrix() {
		return zone.getMatrix();
	}
	
	
	
	/*------------------------------------------------------------------------
	 * Emulation 
	 */

	/**
	 * Snaps the image of a selected and unselected check box control. 
	 * The images are used for a check box emulation. The images are placed 
	 * in the specified directory or if the argument is null then in the 
	 * system default location for application files.  
	 * <p>
	 * Note: it opens a temporary shell in order to snap the images and then closes it immediately.
	 * @param imagePath
	 */
	public void snapSystemTheme(String imagePath) {
		if (imagePath == null) {
			systemThemePath = OsUtil.getUserDirectory("SWT Matrix").getAbsolutePath();
			new File(systemThemePath).mkdirs();
		} else {
			setSystemThemePath(imagePath);
		}
				
		File file = new File(systemThemePath);
		Preconditions.checkArgument(file.exists(), 
				"Directory {0} does not exist.", file.getAbsoluteFile()); 
		Preconditions.checkArgument(file.isDirectory(), 
				"Path {0} is not e directory.", file.getAbsoluteFile());
		
		Shell shell = new Shell();
		RowLayout layout = new RowLayout();
		layout.spacing = layout.marginBottom = layout.marginTop = 0;
		layout.marginLeft = layout.marginRight = 0; 
		shell.setLayout(layout);
		shell.setSize(100, 100);
		shell.open();

		Display display = shell.getDisplay();
		ImageLoader loader = new ImageLoader();
		
		// Check boxes
		Button button = new Button(shell, SWT.CHECK);
		shell.layout();
		shell.update();
		Point size = button.getSize();
        GC gc = new GC(button);
		Image image = new Image(display, size.x, size.y);
        gc.copyArea(image, 0, 0);
        loader.data = new ImageData[] {image.getImageData()};
        loader.save(new File(imagePath, "unchecked.png").getAbsolutePath(), SWT.IMAGE_PNG);
        gc.dispose();
        image.dispose();
        button.dispose();
        
        button = new Button(shell, SWT.CHECK);
        button.setSelection(true);
		shell.layout();
		shell.update();
		gc = new GC(button);
		image = new Image(display, size.x, size.y);
        gc.copyArea(image, 0, 0);
        loader.data = new ImageData[] {image.getImageData()};
        loader.save(new File(imagePath, "checked.png").getAbsolutePath(), SWT.IMAGE_PNG);
        gc.dispose();
        image.dispose();
        button.dispose();

        shell.dispose();
	}

	/**
	 * Sets the path to the folder containing system theme emulation images.
	 * @param systemThemePath path to the folder containing system theme emulation images
	 * @see #snapSystemTheme(String)
	 */
	public void setSystemThemePath(String path) {
		systemThemePath = path == null 
			? "" : path + System.getProperty("file.separator");
		File file = new File(systemThemePath);
		if (path != null) {
			Preconditions.checkArgument(file.exists(), " does not the exist", systemThemePath);
			Preconditions.checkArgument(file.isDirectory(), " is not a directory", systemThemePath);
		}

		// Read check box images
		Display display = getMatrix().getDisplay();
		try {
			FileInputStream  stream = new FileInputStream(systemThemePath + "checked.png");
			trueImage = new Image(display, new ImageData(stream));
			stream.close();
			
			stream = new FileInputStream(systemThemePath + "unchecked.png");
			falseImage = new Image(display, new ImageData(stream));
			stream.close();
		} 
		catch (FileNotFoundException e) { /* Ignore */ }
		catch (IOException e) { /* Ignore */ }
	}

	/**
	 * Returns the path to the folder containing system theme emulation images.
	 * @return the path to the folder containing system theme emulation images
	 * @see #snapSystemTheme(String)
	 */
	public String getSystemThemePath() {
		return systemThemePath;
	}


	class ZoneEditorData {
		public N0 index0;
		public N1 index1;
		public boolean isEmbedded;
		public ZoneEditorData(N0 index02, N1 index12, boolean embedded) {
			index0 = index02;
			index1 = index12;
			isEmbedded = embedded;
		}
	}


	public ZoneEditorData getData(Widget widget) {
		return (ZoneEditorData) widget.getData(ZONE_EDITOR_DATA);
	}
}
