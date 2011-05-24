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
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;

/**
 * Text, date, boolean editors.
 */
public class Snippet_0401 {
	int columnCount = 3;
	ArrayList<Object[]> data;
	Matrix<Integer, Integer> matrix;
	int[] editorCell = new int[2];
	Control editor;
	DateFormat dateFormat;
	Calendar calendar = Calendar.getInstance();
	Image checked, unchecked;

	public static void main(String[] args) throws IOException {
		new Snippet_0401();
	}
	
	public Snippet_0401() throws IOException {
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
		data.add(new Object[] {true, false, "b"});
		data.add(new Object[] {new Date(), "c", "d"});
		
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
		});

		Listener matrixListener = new Listener() {
			@Override
			public void handleEvent(Event e) {
				// Apply when clicked outside of the text editor
				if (e.type == SWT.MouseDown) {
					if (editor != null) {
						Rectangle bounds = editor.getBounds();
						if (!bounds.contains(e.x, e.y)) {
							apply();
						}
					}
				}		
				else {
					int index0 = matrix.getAxis0().getFocusItem().getIndex().intValue();
					int index1 = matrix.getAxis1().getFocusItem().getIndex().intValue();
					Object value = data.get(index0)[index1];
					
					// Open editor on double click within the current cell bounds
					if (e.type == SWT.MouseDoubleClick &&
							matrix.getBody().getCellBounds(index0, index1).contains(e.x, e.y)) 
					{
						openEditor(index0, index1, value);
					}
					
					else if (e.type == SWT.KeyDown && e.stateMask == 0) {
						
						// Open editor on F2 or space for boolean values
						if (e.keyCode == SWT.F2 
								|| e.character == ' ' && value instanceof Boolean) 
						{
							openEditor(index0, index1, value);
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
	
	String getLabel(Object value) {
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
	
	Image getImage(Object value) {
		if (value instanceof Boolean) {
			return (Boolean) value ? checked : unchecked;
		}
		else {
			return null;
		}
	}
	
	void openEditor(Integer index0, Integer index1, Object value) {
		editorCell[0] = index0; editorCell[1] = index1;
		if (value instanceof Date) {
			openDateEditor(index0, index1, (Date) value);
		} else if (value instanceof Boolean) {
			openBooleanEditor(index0, index1, (Boolean) value);
		} else {
			openTextEditor(index0, index1, (String) value);
		}
	}
	
	void openTextEditor(int index0, int index1, String value) {
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
				// Text event
				// Apply on Enter
				if (e.type == SWT.KeyDown && e.keyCode == SWT.CR) {
					apply();
				}
				// Cancel on Escape
				else if (e.type == SWT.KeyDown && (e.keyCode == SWT.ESC || e.keyCode == SWT.TAB)) {
					text.dispose();
					editor = null;
				}
			}
		};
		text.addListener(SWT.KeyDown, listener); // to handle enter, escape and tab
		
		text.setFocus();
	}

	void openDateEditor(int index0, int index1, Date value) {
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
				else if (e.type == SWT.KeyDown && (e.keyCode == SWT.ESC || e.keyCode == SWT.TAB)) {
					date.dispose();
					editor = null;
				}
			}
		};
		date.addListener(SWT.KeyDown, listener); // to handle enter, escape and tab
		
		date.setFocus();
	}
	
	void openBooleanEditor(int index0, int index1, Boolean value) {
		data.get(editorCell[0])[editorCell[1]] = !value;
		matrix.redraw();
	}
	
	
	void apply() 
	{
		Object value = null;
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
		if (editor instanceof DateTime) {
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
	}

}
