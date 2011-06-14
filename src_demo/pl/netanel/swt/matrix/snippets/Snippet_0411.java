package pl.netanel.swt.matrix.snippets;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.util.Arrays;

/**
 * Text, date, boolean, combo editors.
 */
public class Snippet_0411 {
	static final String[] weekDays = new String[] 
            {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	
	int columnCount = 3;
	ArrayList<Object[]> data;
	Matrix<Integer, Integer> matrix;
	int[] editorCell = new int[2];
	Control editor;
	Object currentValue;
	DateFormat dateFormat;
	Calendar calendar = Calendar.getInstance();
	Image checked, unchecked;

	public static void main(String[] args) throws IOException {
		new Snippet_0411();
	}
	
	public Snippet_0411() throws IOException {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		
		// Configuration
		dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
		FileInputStream stream = new FileInputStream("checked.png");
		checked = new Image(display, new ImageData(stream));
		stream.close();
		stream = new FileInputStream("unchecked.png");
		unchecked = new Image(display, new ImageData(stream));
		stream.close();
		
		// Data model
		data = new ArrayList();
		data.add(new Object[] {"a", true, new Date()});
		data.add(new Object[] {true, false, "Monday"});
		data.add(new Object[] {new Date(), "Sunday", "b"});
		
		// Matrix
		matrix = new Matrix(shell, SWT.NONE);
		
		matrix.getAxis0().getBody().setCount(data.size());
		matrix.getAxis1().getBody().setCount(columnCount);
		matrix.getAxis1().getBody().setDefaultCellWidth(80);
		
		matrix.getBody().replacePainter(new Painter("cells", Painter.SCOPE_CELLS_HORIZONTALLY) {
			@Override
			public void paint(Number index0, Number index1, int x, int y, int width, int height) {
				Object value = data.get(index0.intValue())[index1.intValue()];
				text = getLabel(value);
				image = getImage(value);
				textAlignX = value instanceof Date ? SWT.END : SWT.BEGINNING;
				imageAlignX = imageAlignY = image == null ? SWT.BEGINNING : SWT.CENTER;
				super.paint(index0, index1, x, y, width, height);
			}
			
			@Override
			public int computeWidth(Number index0, Number index1) {
				Object value = data.get(index0.intValue())[index1.intValue()];
				text = getLabel(value);
				image = getImage(value);
				return super.computeWidth(index0, index1);
			}
			
			private String getLabel(Object value) {
				if (value instanceof String) {
					return (String) value;
				}
				else if (value instanceof Date) {
					return dateFormat.format(value); 
				}
				else {
					return null;
				}
			}
			
			private Image getImage(Object value) {
				if (value instanceof Boolean) {
					return (Boolean) value ? checked : unchecked;
				}
				else {
					return null;
				}
			}
		});
		
		Listener matrixListener = new Listener() {

			@Override
			public void handleEvent(Event e) {
				int index0 = matrix.getAxis0().getFocusItem().getIndex().intValue();
				int index1 = matrix.getAxis1().getFocusItem().getIndex().intValue();
				currentValue = data.get(index0)[index1];
				
				// Apply when clicked outside of the text editor
				if (e.type == SWT.MouseDown) {
					if (editor != null) {
						Rectangle bounds = editor.getBounds();
						if (!bounds.contains(e.x, e.y)) {
							apply();
						}
					}
					// Change boolean value when clicked on the check box image
					if (currentValue instanceof Boolean) {
						// Calculate the image bounds
						Rectangle cellBounds = matrix.getBody().getCellBounds(index0, index1);
						Rectangle imageBounds = checked.getBounds();
						imageBounds.x = cellBounds.x + (cellBounds.width - imageBounds.width) / 2;
						imageBounds.y = cellBounds.y + (cellBounds.height - imageBounds.height) / 2;
						if (imageBounds.contains(e.x, e.y)) {
							activateEditor(index0, index1, currentValue);							
						}
					}
				}		
				else {
					
					// Open editor on double click within the current cell bounds
					if (e.type == SWT.MouseDoubleClick &&
							matrix.getBody().getCellBounds(index0, index1).contains(e.x, e.y)) 
					{
						activateEditor(index0, index1, currentValue);
					}
					
					else if (e.type == SWT.KeyDown && e.stateMask == 0) {
						
						// Open editor on F2 or space for boolean values
						if (e.keyCode == SWT.F2 
								|| currentValue instanceof Boolean && e.character == ' ') 
						{
							activateEditor(index0, index1, currentValue);
						}
						
						// Nullify value on delete key
						else if (e.keyCode == SWT.DEL) {
							data.get(index0)[index1] = null;
							matrix.redraw();
						}
					}
				}
			}
		};
		matrix.addListener(SWT.MouseDown, matrixListener); // to handle focus out of the editor control
		matrix.addListener(SWT.MouseDoubleClick, matrixListener); // to handle double click editor activation 
		matrix.addListener(SWT.KeyDown, matrixListener); // to handle F2 editor activation
		
		
		shell.setBounds(400, 200, 400, 300);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

	}
	
	
	void activateEditor(Integer index0, Integer index1, Object value) {
		editorCell[0] = index0; editorCell[1] = index1;
		if (value instanceof Date) {
			activateDateEditor(index0, index1, (Date) value);
		} else if (value instanceof Boolean) {
			activateBooleanEditor(index0, index1, (Boolean) value);
		} else if (Arrays.contains(weekDays, (String) value)) {
			activateComboEditor(index0, index1, (String) value);
		}
		else {
			activateTextEditor(index0, index1, (String) value);
		}
	}
	
	void activateTextEditor(int index0, int index1, String value) {
		final Text text = new Text(matrix, SWT.BORDER);
		editor = text;
		text.setText(value == null ? "" : value);
		Rectangle bounds = matrix.getBody().getCellBounds(index0, index1);
		bounds.x--; bounds.y--; bounds.width += 2; bounds.height += 2;
		text.setBounds(bounds);

		// Listener for both the editor control and the matrix control
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event e) {
				// Apply on Enter
				if (e.type == SWT.KeyDown && e.keyCode == SWT.CR) {
					apply();
				}
				// Cancel on Escape
				else if (e.type == SWT.KeyDown && (e.keyCode == SWT.ESC)) {
					text.dispose();
					editor = null;
					matrix.setFocus();
				}
			}
		};
		text.addListener(SWT.KeyDown, listener); // to handle enter, escape and tab
		
		text.setFocus();
	}
	
	void activateComboEditor(int index0, int index1, String value) {
		final Combo combo = new Combo(matrix, SWT.BORDER);
		editor = combo;
		combo.setItems(weekDays);
		combo.setText(value == null ? "" : value);
		Rectangle bounds = matrix.getBody().getCellBounds(index0, index1);
		bounds.x--; bounds.y--; bounds.width += 2; bounds.height += 2;
		combo.setBounds(bounds);
		
		// Listener for both the editor control and the matrix control
		Listener listener = new Listener() {
			
			@Override
			public void handleEvent(Event e) {
				if (combo.getListVisible() == true) return;
				
				// Apply on Enter
				if (e.type == SWT.KeyDown && e.keyCode == SWT.CR) {
					apply();
				}
				// Cancel on Escape
				else if (e.type == SWT.KeyDown && (e.keyCode == SWT.ESC)) {
					combo.dispose();
					editor = null;
					matrix.setFocus();
				}
			}
		};
		combo.addListener(SWT.KeyDown, listener); // to handle enter, escape and tab
		
		combo.setFocus();
	}

	void activateDateEditor(int index0, int index1, Date value) {
		final DateTime date = new DateTime(matrix, SWT.BORDER);
		editor = date;
		calendar.setTime(value);
		date.setDate(calendar.get(Calendar.YEAR), 
				calendar.get(Calendar.MONTH), 
				calendar.get(Calendar.DAY_OF_MONTH));
		Rectangle bounds = matrix.getBody().getCellBounds(index0, index1);
		bounds.x--; bounds.y--; bounds.width += 2; bounds.height += 2;
		date.setBounds(bounds);
		
		// Listener for both the editor control and the matrix control
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event e) {
				// Text event
				// Apply on Enter
				if (e.type == SWT.KeyDown && e.keyCode == SWT.CR) {
					apply();
				}
				// Cancel on Escape
				else if (e.type == SWT.KeyDown && (e.keyCode == SWT.ESC)) {
					date.dispose();
					editor = null;
					matrix.setFocus();
				}
			}
		};
		date.addListener(SWT.KeyDown, listener); // to handle enter, escape and tab
		
		date.setFocus();
	}
	
	void activateBooleanEditor(int index0, int index1, Boolean value) {
		data.get(editorCell[0])[editorCell[1]] = !value;
		matrix.redraw();
	}
	
	
	void apply() 
	{
		Object value = currentValue;
		if (editor instanceof Text) {
			String s = ((Text) editor).getText();
			try {
				value = dateFormat.parse(s);
			} 
			catch (ParseException e) { 
				if ("true".equals(s) || "false".equals(s)) {
					value = Boolean.parseBoolean(s);
				}
			}
		}
		else if (editor instanceof Combo) {
			Combo combo = (Combo) editor;
			int selectionIndex = combo.getSelectionIndex();
			if (selectionIndex != -1) {
			value = combo.getItem(selectionIndex);
			}
		}
		else if (editor instanceof DateTime) {
			calendar.clear();
			DateTime date = ((DateTime) editor);
			calendar.set(Calendar.YEAR, date.getYear());
			calendar.set(Calendar.MONTH, date.getMonth());
			calendar.set(Calendar.DAY_OF_MONTH, date.getDay());
			value = calendar.getTime();
		}
		
		data.get(editorCell[0])[editorCell[1]] = value;
		editor.dispose();
		editor = null;
		matrix.redraw();
		matrix.setFocus();
	}

}
