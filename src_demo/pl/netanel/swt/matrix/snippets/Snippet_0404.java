package pl.netanel.swt.matrix.snippets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.ZoneEditor;
import pl.netanel.util.Arrays;

/**
 * Edit controls Text, DateTime, Boolean, Combo depending on the cell value.
 */
public class Snippet_0404 {
	static final String[] weekDays = new String[] 
            {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	
	public static void main(String[] args) throws IOException {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		
		final DateFormat dateFormat = SimpleDateFormat.getDateInstance();

		// Data model
		final ArrayList<Object[]> data = new ArrayList();
		data.add(new Object[] {"a", true, new Date()});
		data.add(new Object[] {true, false, "Monday"});
		data.add(new Object[] {new Date(), "Sunday", "b"});
		
		// Matrix
		final Matrix matrix = new Matrix(shell, SWT.NONE);
		
		matrix.getAxis0().getBody().setCount(data.size());
		matrix.getAxis1().getBody().setCount(3);
		matrix.getAxis1().getBody().setDefaultCellWidth(80);
		
		// Body editor
		new ZoneEditor(matrix.getBody()) {
			@Override
			public Object getModelValue(Number index0, Number index1) {
				return data.get(index0.intValue())[index1.intValue()];
			}
			
			@Override
			public void setModelValue(Number index0, Number index1, Object value) {
				if (value instanceof String) {
					try {
						value = dateFormat.parse((String) value);
					} 
					catch (ParseException e) { 
						if ("true".equals(value) || "false".equals(value)) {
							value = Boolean.parseBoolean((String) value);
						}
					}
				}
				data.get(index0.intValue())[index1.intValue()] = value;
			}
			
			@Override
			protected Object parse(Number index0, Number index1, String s) {
				Object o = s;
				try {
					o = dateFormat.parse(s);
				} 
				catch (ParseException e) { /* Ignore */}
				return o;
			}
			
			@Override
			public Control createControl(Number index0, Number index1) {
				Object value = data.get(index0.intValue())[index1.intValue()];
				
				// Date
				if (value instanceof Date) {
					return new DateTime(matrix, SWT.BORDER);
				} 
				
				else if (value instanceof String) {
					// Combo
					if (Arrays.contains(weekDays, (String) value)) {
						Combo combo = new Combo(matrix, SWT.BORDER);
						combo.setItems(weekDays);
						return combo;
					}
					
					// Text
					else {
						return new Text(matrix, SWT.BORDER);
					}
				}
				
				// Text
				else if (value == null) {
					return new Text(matrix, SWT.BORDER);
				}
				return null;
			}
			
			@Override
			public Object[] getCheckboxEmulation(Number index0, Number index1) {
				Object value = data.get(index0.intValue())[index1.intValue()];
				return value instanceof Boolean ? getDefaultCheckBoxImages() : null;
			}
		};

		
		// Paint text from the model in the body
		matrix.getBody().replacePainter(new Painter("cells", Painter.SCOPE_CELLS_HORIZONTALLY) {
			@Override
			public String getText(Number index0, Number index1) {
				Object value = data.get(index0.intValue())[index1.intValue()];
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
		});
		
		shell.setBounds(400, 200, 400, 300);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
