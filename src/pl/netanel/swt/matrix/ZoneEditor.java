package pl.netanel.swt.matrix;

import static pl.netanel.swt.matrix.Matrix.CMD_COPY;
import static pl.netanel.swt.matrix.Matrix.CMD_CUT;
import static pl.netanel.swt.matrix.Matrix.CMD_DELETE;
import static pl.netanel.swt.matrix.Matrix.CMD_EDIT_ACTIVATE;
import static pl.netanel.swt.matrix.Matrix.CMD_EDIT_DEACTIVATE_APPLY;
import static pl.netanel.swt.matrix.Matrix.CMD_EDIT_DEACTIVATE_CANCEL;
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
 * @param <X> indexing type for the horizontal axis
 * @param <Y> indexing type for vertical axis
 */
public class ZoneEditor<X extends Number, Y extends Number> {
	
	private static final String ZONE_EDITOR_DATA = "edited cell";
	private static final String DEFAULT_TRUE_TEXT = "\u2713";
	private static final String NEW_LINE = System.getProperty("line.separator");
 
	final ZoneCore<X, Y> zone;
	final CommandListener controlListener;
	
	EmbeddedControlsPainter<X, Y> embedded;
	private Painter<X, Y> cellsPainter;

	private Image trueImage, falseImage;
	private String systemThemePath;


	/**
	 * Default constructor, facilitates editing of the specified zone. 
	 * @param zone
	 */
	public ZoneEditor(Zone<X, Y> zone) {
		super();
		this.zone = ZoneCore.from(zone);
		this.zone.setEditor(this);
		
		setImagePath(null);
		
		// Painters
		cellsPainter = zone.getPainter(Painter.NAME_CELLS);
		zone.replacePainter(new Painter<X, Y>(
		  Painter.NAME_EMULATED_CONTROLS, Painter.SCOPE_CELLS) 
	  {
		  @Override protected boolean init() {
		    return true;
		  }
		  @Override public void setup(X indexX, Y indexY) {
				Object[] emul = getCheckboxEmulation(indexX, indexY);
				if (emul == null) return;
				Object value = getModelValue(indexX, indexY);
				
				if (emul[0] instanceof Image || emul[1] instanceof Image) {
					text = null;
					style.imageAlignX = style.imageAlignY = SWT.CENTER;
					image = (Image) (Boolean.TRUE.equals(value) ? emul[0] : emul[1]); 
				} else {
					image = null;
					style.textAlignX = style.textAlignY = SWT.CENTER;
					text = (String) (Boolean.TRUE.equals(value) ? emul[0] : emul[1]);
				}
				super.setup(indexX, indexY);
			};
		});
		zone.addPainter(embedded = new EmbeddedControlsPainter<X, Y>(this));
	
		// Command binding
		zone.bind(CMD_CUT, SWT.KeyDown, SWT.MOD1 | 'x');
		zone.bind(CMD_COPY, SWT.KeyDown, SWT.MOD1 | 'c');
		zone.bind(CMD_PASTE, SWT.KeyDown, SWT.MOD1 | 'v');
		zone.bind(CMD_DELETE, SWT.KeyDown, SWT.DEL);
		zone.bind(CMD_EDIT_ACTIVATE, SWT.KeyUp, SWT.F2);
		zone.bind(CMD_EDIT_ACTIVATE, SWT.MouseDoubleClick, 1);
		zone.bind(CMD_EDIT_ACTIVATE, SWT.KeyDown, Matrix.PRINTABLE_CHARS);

		// Clicking on the image emulation
		this.zone.bind(new GestureBinding(CMD_EDIT_ACTIVATE, SWT.MouseDown, 1) {
			
			@Override public boolean isMatching(Event e) {
				if (!super.isMatching(e)) return false;
				
				// Change boolean value when clicked on the check box image
				Matrix<X, Y> matrix = getMatrix();
				Y indexY = matrix.getAxisY().getFocusItem().getIndex();
				X indexX = matrix.getAxisX().getFocusItem().getIndex();
				Object[] emulation = getCheckboxEmulation(indexX, indexY);
				
				if (emulation != null && 
						(emulation[0] instanceof Image || emulation[1] instanceof Image)) {
					// Calculate the image bounds
					Rectangle cellBounds = matrix.getBody().getCellBounds(indexX, indexY);
					Rectangle imageBounds = trueImage.getBounds();
					imageBounds.x = cellBounds.x + (cellBounds.width - imageBounds.width) / 2;
					imageBounds.y = cellBounds.y + (cellBounds.height - imageBounds.height) / 2;
					return imageBounds.contains(e.x, e.y);
				}
				return false;
			}
		});
		
		// Avoid double click on the image
		this.zone.bind(new GestureBinding(CMD_EDIT_ACTIVATE, SWT.MouseDoubleClick, 1) {
		  
		  @Override public boolean isMatching(Event e) {
		    if (!super.isMatching(e)) return false;
		    
		    // Change boolean value when clicked on the check box image
		    Matrix<X, Y> matrix = getMatrix();
		    Y indexY = matrix.getAxisY().getFocusItem().getIndex();
		    X indexX = matrix.getAxisX().getFocusItem().getIndex();
		    Object[] emulation = getCheckboxEmulation(indexX, indexY);
		    
		    if (emulation != null && 
		      (emulation[0] instanceof Image || emulation[1] instanceof Image)) {
		      // Calculate the image bounds
		      Rectangle cellBounds = matrix.getBody().getCellBounds(indexX, indexY);
		      Rectangle imageBounds = trueImage.getBounds();
		      imageBounds.x = cellBounds.x + (cellBounds.width - imageBounds.width) / 2;
		      imageBounds.y = cellBounds.y + (cellBounds.height - imageBounds.height) / 2;
		      return !imageBounds.contains(e.x, e.y);
		    }
		    return false;
		  }
		});
		
		controlListener = new CommandListener() {
			@Override protected void executeCommand(int commandId) {
				Control control = (Control) event.widget;
				
				switch (commandId) {
				case CMD_EDIT_DEACTIVATE_APPLY: apply(control); break;
				case CMD_EDIT_DEACTIVATE_CANCEL: cancel(control); break;
				}
			};
		};
		controlListener.bind(CMD_EDIT_DEACTIVATE_CANCEL, SWT.KeyDown, SWT.ESC);
		controlListener.bind(CMD_EDIT_DEACTIVATE_APPLY, SWT.KeyDown, SWT.CR);
		controlListener.bind(CMD_EDIT_DEACTIVATE_APPLY, SWT.FocusOut, 0);
		controlListener.bindings.add(new GestureBinding(CMD_EDIT_DEACTIVATE_APPLY, SWT.Selection, 0) {
		  @Override public boolean isMatching(Event e) {
		    if (!(e.widget instanceof Button)) return false;
		    return super.isMatching(e);
		  }
		});
	}


	/** 
	 * Returns value from the model.  
	 * <p>
	 * This method is usually overridden, since the default implementation returns 
	 * the result of the {@link Painter#getText(Number, Number)} method of the 
	 * zone's Painter.NAME_CELLS painter, which is always {@link String}, while some edit 
	 * controls require other types, like {@link Boolean} or {@link Date}. 
	 * If the Painter.NAME_CELLS painter does not exist in the zone it always returns null. 
	 * @param indexX cell index on the horizontal axis 
	 * @param indexY cell index on the vertical axis 
	 */
	public Object getModelValue(X indexX, Y indexY) {
	  if (cellsPainter == null) return null;
	  cellsPainter.setupSpatial(indexX, indexY);
	  return cellsPainter.text;
	}
	
	/**
	 * Sets the specified value to the model.
	 * <p>
	 * It is used to apply the result of cell editing. 
	 * @param indexX cell index on the horizontal axis 
	 * @param indexY cell index on the vertical axis 
	 * @param value to set in the model
	 */
	public void setModelValue(X indexX, Y indexY, Object value) {
		
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
			Calendar cal = Calendar.getInstance();
			cal.clear();
			cal.set(Calendar.YEAR, date.getYear());
			cal.set(Calendar.MONTH, date.getMonth());
			cal.set(Calendar.DAY_OF_MONTH, date.getDay());
			return cal.getTime();
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
		String s = value == null ? "" : value.toString();
		
    if (control instanceof Text) {
			Text text = (Text) control;
      text.setText(s);
			text.setSelection(s.length());
		} 
		else if (control instanceof Combo) {
			Combo combo = (Combo) control;
			int indexOf = combo.indexOf(s);
			if (indexOf == -1) {
			  combo.setText(s);
			} else {
			  combo.select(indexOf);
			}
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
		  Calendar cal = Calendar.getInstance();
			cal.setTime((Date) value);
			DateTime dateTime = (DateTime) control;
			dateTime.setDate(
					cal.get(Calendar.YEAR), 
					cal.get(Calendar.MONTH), 
					cal.get(Calendar.DAY_OF_MONTH));
		} 
	}

	
	private void apply(Control control) {
		if (control == null) return;
		ZoneEditorData<X, Y> data = getData(control);
		if (data == null) return;
		setModelValue(data.indexX, data.indexY, getEditorValue(control));
		cancel(control);
		getMatrix().redraw();
	}
	
	private void cancel(Control control) {
		if (control != null && !getData(control).isEmbedded) {
			disposeControl(control);
		}
		getMatrix().forceFocus();
	}

	
	/**
	 * Shows a control to edit the value of the specified cell. 
	 */
	private Control activate(X indexX, Y indexY, GestureBinding b) {
		cellsPainter = zone.getPainter(Painter.NAME_CELLS);
		
//		Control control = embedded.getControl(indexY, indexX);
		Object[] emulation = getCheckboxEmulation(indexX, indexY);
		Control control = embedded.getControl(indexY, indexX);
		if (emulation != null || isCheckbox(control)) {			
			boolean value = !Boolean.TRUE.equals(getModelValue(indexX, indexY));
			setModelValue(indexX, indexY, value);
			getMatrix().redraw();
			if (control != null) {
				((Button) control).setSelection(value);
			}
		} else {
			if (control == null) {
				control = addControl(indexX, indexY);
				if (b.isCharActivated) {
				  if (isCheckbox(control)) {
				    boolean value = !Boolean.TRUE.equals(getModelValue(indexX, indexY));
				    ((Button) control).setSelection(value);
			      setModelValue(indexX, indexY, value);
				  } else {
				    setEditorValue(control, b.character);
				  }
				}
			}
			if (control != null) {
			  control.setFocus();
			}
		}
		return control;
	}
	
	private boolean isCheckbox(Control control) {
		return control instanceof Button && (control.getStyle() & SWT.CHECK) != 0;
	}
	
	Control addControl(X indexX, Y indexY) {
		Control control = createControl(indexX, indexY);
		if (control != null) {
			ZoneEditorData<X, Y> data = new ZoneEditorData<X, Y>(indexX, indexY, 
					hasEmbeddedControl(indexX, indexY));
			control.setData(ZONE_EDITOR_DATA, data);
			setEditorValue(control, getModelValue(indexX, indexY));
			setBounds(indexX, indexY, control);
			control.moveAbove(getMatrix());
			controlListener.attachTo(control);
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
	 * @param indexX cell index on the horizontal axis 
	 * @param indexY cell index on the vertical axis 
	 * @param parent composite to create the control in
	 *  
	 * @return control to edit the value of the specified cell
	 * @see #createControl(Number, Number)
	 */
	protected Control createControl(X indexX, Y indexY) {
		return new Text(getMatrix(), SWT.BORDER);
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
	 * @param indexX cell index on the horizontal axis  
	 * @param indexY cell index on the vertical axis  
	 * @param control to set the bounds for
	 */
	protected void setBounds(X indexX, Y indexY, Control control) {
		Rectangle bounds = zone.getCellBounds(indexX, indexY);
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
	 * @param indexX cell index on the horizontal axis
	 * @param indexY cell index on the vertical axis  
	 * 
	 * @return
	 */
	protected boolean hasEmbeddedControl(X indexX, Y indexY) {
		return false;
	}

	
	
	/*------------------------------------------------------------------------
	 * Commands
	 */
	
	void edit(GestureBinding b) {
		Matrix<X, Y> matrix = getMatrix();
		final AxisItem<X> focusItem1X = matrix.axisX.getFocusItem();
		final AxisItem<Y> focusItemY = matrix.axisY.getFocusItem();
		if (focusItem1X == null || focusItemY == null) return;

		activate(focusItem1X.getIndex(), focusItemY.getIndex(), b);
	}

	/**
	 * Sets the <code>null</code> value to the selected cells.
	 */
	void delete() {
		Iterator<Cell<X, Y>> it = zone.getSelectedIterator();
		
		while (it.hasNext()) {
		  Cell<X, Y> cell = it.next();
			setModelValue(cell.getIndexX(), cell.getIndexY(), null);
		}
		embedded.needsPainting = true;
		getMatrix().redraw();
	}
	
	/**
	 * Copies selected cells to the clipboard. 
	 * <p>
	 * Only rectangular area can be copied. 
	 */
	public void copy() 
	{
		StringBuilder sb = new StringBuilder();
		cellsPainter = zone.getPainter(Painter.NAME_CELLS);

		CellExtent<X, Y> n = zone.getSelectedExtent();
		Y maxY = n.getEndY();
		X maxX = n.getEndX();
		Iterator<Cell<X, Y>> it = zone.getSelectedBoundsIterator();
		
		while (it.hasNext()) {
			Cell<X, Y> next = it.next();
			X indexX = next.getIndexX();
			Y indexY = next.getIndexY();
			sb.append(zone.isSelected(indexX, indexY) ? format(indexX, indexY) : "");
			if (getMatrix().axisX.math.compare(indexX, maxX) < 0) sb.append("\t");
			else if (getMatrix().axisY.math.compare(indexY, maxY) < 0) {
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
	public void paste() 
	{
		Clipboard clipboard = new Clipboard(getMatrix().getDisplay());
		Object contents = clipboard.getContents(TextTransfer.getInstance());
		clipboard.dispose();
		
		if (contents == null) return;
		
		Axis<X> axisX = getMatrix().getAxisX();
		Axis<Y> axisY = getMatrix().getAxisY();

		// Get the focus cell to start the pasting from
		AxisItem<X> focusItemX = axisX.getFocusItem();
		AxisItem<Y> focusItemY = axisY.getFocusItem();
		if (focusItemX == null || focusItemY == null) return;
		Math<Y> mathY = axisY.math;
		Math<X> mathX = axisX.math;
		X startX = focusItemX.getIndex(), indexX = startX;
		Y startY = focusItemY.getIndex(), indexY = startY;
		X countX = axisX.getBody().getCount();
		Y countY = axisY.getBody().getCount();
		
		String[] rows = contents.toString().split(NEW_LINE);
		for (int i = 0; i < rows.length && mathY.compare(indexY, countY) < 0; i++) {
			String[] cells = split(rows[i], "\t");
			indexX = startX;
			for (int j = 0; j < cells.length && mathX.compare(indexX, countX) < 0; j++) {
				Object value = parse(indexX, indexY, cells[j]);
				if (value != null) {
					setModelValue(indexX, indexY, value);
				}
				indexX = mathX.increment(indexX);
			}
			indexY = mathY.increment(indexY);
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
	public void cut() {
		copy();

		// Set null value in the selected cells
		Iterator<Cell<X, Y>> it = zone.getSelectedBoundsIterator();
		while (it.hasNext()) {
			Cell<X, Y> next = it.next();
			X indexX = next.getIndexX();
			Y indexY = next.getIndexY();
			if (zone.isSelected(indexX, indexY) ) {
				setModelValue(indexX, indexY, null);
			}
		}
		embedded.needsPainting = true;
		getMatrix().redraw();
	}
	
	/**
	 * Returns the label for the specified cell to be included 
	 * in the clipboard copying. By default it returns a value from 
	 * the {@link Painter#getText(Number, Number)} method call.
	 * @param indexX cell index on the horizontal axis 
	 * @param indexY cell index on the vertical axis 
	 * 
	 * @return 
	 */
	protected String format(X indexX, Y indexY) {
	   if (cellsPainter == null) return null;
	    cellsPainter.setupSpatial(indexX, indexY);
	    return cellsPainter.text;
//		Object value = getModelValue(indexY, indexX);
//		return value == null ? "" : value.toString();
	}
	
	/**
	 * Parses a text for the specified cell.
	 * By default it returns the <code>s</code> argument.
	 * @param indexX cell index on the horizontal axis 
	 * @param indexY cell index on the vertical axis 
	 * @param s text to parse from
	 *  
	 * @return
	 */
	protected Object parse(X indexX, Y indexY, String s) {
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
		ArrayList<String> list = new ArrayList<String>();
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



	Matrix<X, Y> getMatrix() {
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
	 * @param indexX cell index on the horizontal axis
	 * @param indexY cell index on the vertical axis  
	 *  
	 * @return the check box emulation data.
	 * @see #getDefaultCheckBoxImages() 
	 * @see #getDefaultCheckBoxLabels() 
	 */
	protected Object[] getCheckboxEmulation(X indexX, Y indexY) {
		return null;
	}
	
	/**
	 * Returns the default images to emulate check boxes. 
	 * The first image in the returned array is for the <code>true</code> value,
	 * the second one is for the <code>false</code> value.
	 * <p>
	 * The default check box images can be configured with the {@link #snapControlImages(String)}
	 * and the {@link #setImagePath(String)}
	 *    
	 * @return default images to emulate check boxes.
	 * @see #getCheckboxEmulation(Number, Number)
	 * @see #setImagePath(String)
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
	 * @see #setImagePath(String)
	 */
	protected final Object[] getDefaultCheckBoxLabels() {
	  return new Object[] {DEFAULT_TRUE_TEXT, null};
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
  void snapControlImages(String imagePath) {
    if (imagePath == null) {
      systemThemePath = OsUtil.getUserDirectory("SWT Matrix").getAbsolutePath();
      if (!new File(systemThemePath).mkdirs()) {
        return;
      }
    }
    else {
      setImagePath(imagePath);
    }

    File file = new File(systemThemePath);
    Preconditions.checkArgument(file.exists(), "Directory {0} does not exist.",
      file.getAbsoluteFile());
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
    loader.data = new ImageData[] { image.getImageData() };
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
    loader.data = new ImageData[] { image.getImageData() };
    loader.save(new File(imagePath, "checked.png").getAbsolutePath(),
      SWT.IMAGE_PNG);
    gc.dispose();
    image.dispose();
    button.dispose();

    shell.dispose();
  }

	/**
	 * Sets the path to the folder containing images emulating the system theme.
	 * @param systemThemePath path to the folder containing system theme emulation images
	 * @see #snapControlImages(String)
	 */
	public void setImagePath(String path) {
		systemThemePath = path == null 
			? "" : path + System.getProperty("file.separator");
		File file = new File(systemThemePath);
		if (path != null) {
			Preconditions.checkArgument(file.exists(), " does not the exist", systemThemePath);
			Preconditions.checkArgument(file.isDirectory(), " is not a directory", systemThemePath);
		}

		// Read check box images
		Display display = getMatrix().getDisplay();
		FileInputStream stream;
		try {
		  stream = new FileInputStream(systemThemePath + "checked.png");
		  trueImage = new Image(display, new ImageData(stream));
		  stream.close();

		  stream = new FileInputStream(systemThemePath + "unchecked.png");
		  falseImage = new Image(display, new ImageData(stream));
		  stream.close();
		}
		catch (FileNotFoundException e) {
		  throw new RuntimeException(e);
		}
    catch (IOException e) {
      throw new RuntimeException(e);
    }
	}

	/**
	 * Returns the path to the folder containing system theme emulation images.
	 * @return the path to the folder containing system theme emulation images
	 * @see #snapControlImages(String)
	 */
	public String getEmulationPath() {
		return systemThemePath;
	}
	
	/**
	 * Makes the embedded controls to be recreated.
	 */
	public void redraw() {
	  embedded.needsPainting = true;
	  getMatrix().redraw();
	}

	static class ZoneEditorData<X2 extends Number, Y2 extends Number> {
	  public X2 indexX;
		public Y2 indexY;
		public boolean isEmbedded;
		public ZoneEditorData(X2 indexX2, Y2 indexY2, boolean embedded) {
			indexY = indexY2;
			indexX = indexX2;
			isEmbedded = embedded;
		}
	}


	@SuppressWarnings("unchecked") 
	ZoneEditorData<X, Y> getData(Widget widget) {
		return (ZoneEditorData<X, Y>) widget.getData(ZONE_EDITOR_DATA);
	}
}
