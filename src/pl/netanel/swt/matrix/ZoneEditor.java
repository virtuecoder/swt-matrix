package pl.netanel.swt.matrix;

import static pl.netanel.swt.matrix.Matrix.CMD_APPLY_EDIT;
import static pl.netanel.swt.matrix.Matrix.CMD_CANCEL_EDIT;
import static pl.netanel.swt.matrix.Matrix.CMD_COPY;
import static pl.netanel.swt.matrix.Matrix.CMD_CUT;
import static pl.netanel.swt.matrix.Matrix.CMD_DELETE;
import static pl.netanel.swt.matrix.Matrix.CMD_EDIT;
import static pl.netanel.swt.matrix.Matrix.CMD_PASTE;

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
import org.eclipse.swt.widgets.Composite;
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
public class ZoneEditor<N0 extends Number, N1 extends Number> {
	
	private static final String ZONE_EDITOR_DATA = "edited cell";
	private static final String DEFAULT_TRUE_TEXT = "\u2713";
	private static final Calendar CALENDAR = Calendar.getInstance();
	private static final String NEW_LINE = System.getProperty("line.separator");
 

	final Zone<N0, N1> zone;
	final CommandListener controlListener;
	
	private EmbeddedControlsPainter embedded;
	private Painter<N0, N1> cellsPainter;

	private Image trueImage, falseImage;
	private String trueLabel, falseLabel;
	private String systemThemePath;


	/**
	 * Default constructor, facilitates editing of the specified zone. 
	 * @param zone
	 */
	public ZoneEditor(Zone<N0, N1> zone) {
		super();
		this.zone = zone;
		zone.setEditor(this);
		
		trueLabel = DEFAULT_TRUE_TEXT;
		setEmulationPath(null);
		
		// Painters
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
	
		// Command binding
		zone.bind(CMD_CUT, SWT.KeyDown, SWT.MOD1 | 'x');
		zone.bind(CMD_COPY, SWT.KeyDown, SWT.MOD1 | 'c');
		zone.bind(CMD_PASTE, SWT.KeyDown, SWT.MOD1 | 'v');
		zone.bind(CMD_EDIT, SWT.KeyUp, SWT.F2);
		zone.bind(CMD_EDIT, SWT.MouseDoubleClick, 1);
		zone.bind(CMD_DELETE, SWT.KeyDown, SWT.DEL);
	
		// Space bar for check boxes
		zone.bind(new GestureBinding(CMD_EDIT, SWT.KeyDown, ' ') {
			
			public boolean isMatching(Event e) {
				if (!super.isMatching(e)) return false;
				
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
		zone.bind(new GestureBinding(CMD_EDIT, SWT.MouseDown, 1) {
			
			public boolean isMatching(Event e) {
				if (!super.isMatching(e)) return false;
				
				// Change boolean value when clicked on the check box image
				Matrix<N0, N1> matrix = getMatrix();
				N0 index0 = matrix.getAxis0().getFocusItem().getIndex();
				N1 index1 = matrix.getAxis1().getFocusItem().getIndex();
				Object[] emulation = getCheckboxEmulation(index0, index1);
				
				if (emulation != null && 
						(emulation[0] instanceof Image || emulation[1] instanceof Image)) {
					// Calculate the image bounds
					Rectangle cellBounds = matrix.getBody().getCellBounds(index0, index1);
					Rectangle imageBounds = trueImage.getBounds();
					imageBounds.x = cellBounds.x + (cellBounds.width - imageBounds.width) / 2;
					imageBounds.y = cellBounds.y + (cellBounds.height - imageBounds.height) / 2;
					return imageBounds.contains(e.x, e.y);
				}
				return false;
			}
		});
		
		controlListener = new CommandListener() {
			protected void executeCommand(int commandId) {
				Control control = (Control) event.widget;
				
				switch (commandId) {
				case CMD_APPLY_EDIT: apply(control); break;
				case CMD_CANCEL_EDIT: cancel(control); break;
				}
			};
		};
		controlListener.bind(CMD_APPLY_EDIT, SWT.KeyDown, SWT.CR);
		controlListener.bind(CMD_APPLY_EDIT, SWT.FocusOut, 0);
		controlListener.bind(CMD_APPLY_EDIT, SWT.Selection, 0);
		controlListener.bind(CMD_CANCEL_EDIT, SWT.KeyDown, SWT.ESC);
	}


	/** 
	 * Returns value from the model.  
	 * <p>
	 * This method is usually overridden, since the default implementation returns 
	 * the result of the {@link Painter#getText(Number, Number)} method of the 
	 * zone's "cells" painter, which is always {@link String}, while some edit 
	 * controls require other types, like {@link Boolean} or {@link Date}. 
	 * If the "cells" painter does not exist in the zone it always returns null. 
	 * 
	 * @param index0 cell index on <code>axis0</code> 
	 * @param index1 cell index on <code>axis1</code> 
	 */
	protected Object getModelValue(N0 index0, N1 index1) {
		return cellsPainter == null ? null : cellsPainter.getText(index0, index1); 
	}
	
	/**
	 * Sets the specified value to the model.
	 * <p>
	 * It is used to apply the result of cell editing. 
	 * 
	 * @param index0 cell index on <code>axis0</code> 
	 * @param index1 cell index on <code>axis1</code> 
	 * @param value to set in the model
	 */
	protected void setModelValue(N0 index0, N1 index1, Object value) {
		
	}
	
	
	/**
	 * Returns a value from the specified edit control.
	 * <p>
	 * It returns: <ul>
	 * <li>{@link String} from {@link Text} and {@link Combo} controls
	 * <li>{@link java.util.Date} from a {@link DateTime} control 
	 * <li>{@link Boolean} from a {@link Button}
	 * </ul>  
	 * @return a value from the specified control
	 * @see #setEditorValue(Control, Object)
	 */
	protected Object getEditorValue(Control control) {
		if (control == null || control.isDisposed()) return null;
		if (control instanceof Text) {
			return ((Text) control).getText();
		} 
		else if (control instanceof Button) {
			return ((Button) control).getSelection();
		}
		else if (control instanceof Combo) {
			return ((Combo) control).getText();
//			Combo combo = (Combo) control;
//			int selectionIndex = combo.getSelectionIndex();
//			return selectionIndex == -1 ? null : combo.getItem(selectionIndex);
		} 
		else if (control instanceof DateTime) {
			DateTime date = (DateTime) control;
			CALENDAR.clear();
			CALENDAR.set(Calendar.YEAR, date.getYear());
			CALENDAR.set(Calendar.MONTH, date.getMonth());
			CALENDAR.set(Calendar.DAY_OF_MONTH, date.getDay());
			return CALENDAR.getTime();
		} 
		
		return null;
	}

	/**
	 * Sets the value in the cell editor control.
	 * <p>
	 * The value must of the following type: <ul>
	 * <li>{@link java.util.Date} for a {@link DateTime} control 
	 * <li>{@link Boolean} for a {@link Button}
	 * <li>any type for {@link Text} and {@link Combo} controls
	 * </ul>  
	 * @param control to set the value for
	 * @param value to set in the control
	 * @see {@link #getEditorValue(Control)}
	 */
	protected void setEditorValue(Control control, Object value) {
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
			CALENDAR.setTime((Date) value);
			DateTime dateTime = (DateTime) control;
			dateTime.setDate(
					CALENDAR.get(Calendar.YEAR), 
					CALENDAR.get(Calendar.MONTH), 
					CALENDAR.get(Calendar.DAY_OF_MONTH));
		} 
	}

	
	private void apply(Control control) {
		if (control == null) return;
		ZoneEditorData data = (ZoneEditorData) getData(control);
		if (data == null) return;
		setModelValue(data.index0, data.index1, getEditorValue(control));
		cancel(control);
		getMatrix().redraw();
	}
	
	private void cancel(Control control) {
		if (control != null && !getData(control).isEmbedded) {
			disposeControl(control);
		}
		getMatrix().setFocus();
	}

	
	/**
	 * Shows a control to edit the value of the specified cell. 
	 */
	private Control activate(N0 index0, N1 index1) {
		cellsPainter = zone.getPainter("cells");
		
//		Control control = embedded.getControl(index0, index1);
		Object[] emulation = getCheckboxEmulation(index0, index1);
		Control control = embedded.getControl(index0, index1);
		if (emulation != null || isCheckbox(control)) {			
			boolean value = !Boolean.TRUE.equals(getModelValue(index0, index1));
			setModelValue(index0, index1, value);
			getMatrix().redraw();
			if (control != null) {
				((Button) control).setSelection(value);
			}
		} else {
			if (control == null) {
				control = addControl(index0, index1);
			}
			control.setFocus();
		}
		return control;
	}
	
	private boolean isCheckbox(Control control) {
		return control instanceof Button && (control.getStyle() & SWT.CHECK) != 0;
	}
	
	Control addControl(N0 index0, N1 index1) {
		Control control = createControl(index0, index1, getMatrix().getParent());
		if (control != null) {
			ZoneEditorData data = new ZoneEditorData(index0, index1, 
					hasEmbeddedControl(index0, index1));
			control.setData(ZONE_EDITOR_DATA, data);
			setEditorValue(control, getModelValue(index0, index1));
			setBounds(index0, index1, control);
			control.moveAbove(getMatrix());
			controlListener.attachTo(control);
			control.moveAbove(getMatrix());
		}
		return control;
	}
	
	/**
	 * Creates a control to edit the value of the specified cell.
	 * <p> 
	 * It creates a {@link Text} control by default. 
	 * The method should return null to make the cell read only. 
	 * <p>
	 * The <code>parent</code> argument is always the parent of {@link Matrix} component. 
	 * It is passed explicitly to discourage creating edit controls 
	 * as children of {@link Matrix} which would block the matrix 
	 * from receiving focus and thus any key or mouse events.
	 *  
	 * @param index0 cell index on <code>axis0</code> 
	 * @param index1 cell index on <code>axis1</code> 
	 * @param parent composite to create the control in
	 * @return control to edit the value of the specified cell
	 * @see #createControl(Number, Number)
	 */
	protected Control createControl(N0 index0, N1 index1, Composite parent) {
		return new Text(parent, SWT.BORDER);
	}
	
	/**
	 * Disposes the edit control. This method can be overridden to implement 
	 * the control hiding instead of disposing, making also the {@link #createControl(Number, Number)}
	 * method to show the control instead of creating a new one in that case.  
	 * 
	 * @param control
	 * @see #createControl(Number, Number)
	 */
	protected void disposeControl(Control control) {
		control.dispose();
	}
	
	
	/**
	 * Sets the bounds of the specified edit control in the specified cell.
	 * <p>
	 * The cell bounds are offset by 1 pixel and applied with 
	 * the {@link Control#setBounds(Rectangle)} method, unless the control 
	 * is a check button whose size is not modified. Then the control 
	 * location is centralized relating to the cell.
	 * 
	 * @param index0 cell index on <code>axis0</code>  
	 * @param index1 cell index on <code>axis1</code>  
	 * @param control to set the bounds for
	 */
	protected void setBounds(N0 index0, N1 index1, Control control) {
		Rectangle bounds = getMatrix().getBody().getCellBounds(index0, index1);
		Painter.offsetRectangle(bounds, 1);			

		Point size  = null;
		if (isCheckbox(control)) {
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

	
	/**
	 * Returns <code>true</code> if the cell editor control for the specified cell
	 * is embedded in the cell, or <code>false</code> if it is a pop-up.
	 * <p>
	 * By default this function returns <code>false</code>.
	 * 
	 * @param index0 cell index on <code>axis0</code>  
	 * @param index1 cell index on <code>axis1</code>
	 * @return
	 */
	protected boolean hasEmbeddedControl(N0 index0, N1 index1) {
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
//			
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
		embedded.needsPainting = true;
		getMatrix().redraw();
	}
	
	/**
	 * Copies selected cells to the clipboard. 
	 * <p>
	 * Only rectangular area can be copied. 
	 */
	protected void copy() 
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
	 * Pastes form the clipboard to the zone starting from the focus cell.
	 * <p>
	 * The items in the clipboard exceeding the section item count will be ignored. 
	 */
	protected void paste() 
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
		
		embedded.needsPainting = true;
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
	protected void cut() {
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
		embedded.needsPainting = true;
		getMatrix().redraw();
	}
	
	/**
	 * Returns the label for the specified cell to be included 
	 * in the clipboard copying. By default it returns a value from 
	 * the {@link Painter#getText(Number, Number)} method call.
	 * 
	 * @param index0 cell index on <code>axis0</code> 
	 * @param index1 cell index on <code>axis1</code> 
	 * @return 
	 */
	protected String format(N0 index0, N1 index1) {
		return cellsPainter == null ? null : cellsPainter.getText(index0, index1);
//		Object value = getModelValue(index0, index1);
//		return value == null ? "" : value.toString();
	}
	
	/**
	 * Parses a text for the specified cell.
	 * By default it returns the <code>s</code> argument.
	 *  
	 * @param index0 cell index on <code>axis0</code> 
	 * @param index1 cell index on <code>axis1</code> 
	 * @param s text to parse from
	 * @return
	 */
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
	 * Returns the check box emulation data. 
	 * It is an array with either two {@link String} elements 
	 * for a true value label and false value label,
	 * or two {@link Image} elements for a true value image and a false value image.
	 *  
	 * @param index0 cell index on <code>axis0</code>  
	 * @param index1 cell index on <code>axis1</code>
	 * @return the check box emulation data.
	 * @see #getDefaultCheckBoxImages() 
	 * @see #getDefaultCheckBoxLabels() 
	 */
	protected Object[] getCheckboxEmulation(N0 index0, N1 index1) {
		return null;
	}
	
	/**
	 * Returns the default images to emulate check boxes. 
	 * The first image in the returned array is for the <code>true</code> value,
	 * the second one is for the <code>false</code> value.
	 * <p>
	 * The default check box images can be configured with the {@link #snapControlImages(String)}
	 * and the {@link #setEmulationPath(String)}
	 *    
	 * @return default images to emulate check boxes.
	 * @see #getCheckboxEmulation(Number, Number)
	 * @see #setEmulationPath(String)
	 * @see #snapControlImages(String)
	 */
	protected final Object[] getDefaultCheckBoxImages() {
		if (trueImage == null && falseImage == null) {
			return null;
		}
		else {
			return new Object[] {trueImage, falseImage};
		} 
	}
	
	/**
	 * Returns the default labels to emulate check boxes. 
	 * The first label in the returned array is &#x2713; for the 
	 * <code>true</code> value, the second one is <code>null</code> 
	 * for the <code>false</code> value.
	 *    
	 * @return default labels to emulate check boxes.
	 * @see #getCheckboxEmulation(Number, Number)
	 * @see #setEmulationPath(String)
	 */
	protected final Object[] getDefaultCheckBoxLabels() {
		if (trueLabel == null && falseLabel == null) {
			return null;
		}
		else {
			return new Object[] {trueLabel, falseLabel};
		} 
	}
	
	/**
	 * Snaps the image of a selected and unselected check box control 
	 * to be used for a check box emulation. The images are placed 
	 * in the specified directory or if the argument is null then in the 
	 * system default location for application files.  
	 * <p>
	 * Note: it opens a temporary shell in order to snap the images and then closes it immediately.
	 * @param imagePath
	 */
	public void snapControlImages(String imagePath) {
		if (imagePath == null) {
			systemThemePath = OsUtil.getUserDirectory("SWT Matrix").getAbsolutePath();
			new File(systemThemePath).mkdirs();
		} else {
			setEmulationPath(imagePath);
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
	 * @see #snapControlImages(String)
	 */
	public void setEmulationPath(String path) {
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
	 * @see #snapControlImages(String)
	 */
	public String getEmulationPath() {
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


	ZoneEditorData getData(Widget widget) {
		return (ZoneEditorData) widget.getData(ZONE_EDITOR_DATA);
	}
}
