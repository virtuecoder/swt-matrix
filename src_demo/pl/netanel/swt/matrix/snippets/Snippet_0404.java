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
		shell.setText("Edit controls Text, DateTime, Boolean, Combo depending on the cell value");
		shell.setLayout(new FillLayout());
		
		final DateFormat dateFormat = SimpleDateFormat.getDateInstance();

		// Data model
		final ArrayList<Object[]> data = new ArrayList<Object[]>();
		data.add(new Object[] {"a", true, new Date()});
		data.add(new Object[] {true, false, "Monday"});
		data.add(new Object[] {new Date(), "Sunday", "b"});
		
		// Matrix
		final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);
		
		matrix.getAxisY().getBody().setCount(data.size());
		matrix.getAxisX().getBody().setCount(3);
		matrix.getAxisX().getBody().setDefaultCellWidth(80);
		
		// Body editor
		new ZoneEditor<Integer, Integer>(matrix.getBody()) {
			@Override
			public Object getModelValue(Integer indexX, Integer indexY) {
				return data.get(indexY.intValue())[indexX.intValue()];
			}
			
			@Override
			public void setModelValue(Integer indexX, Integer indexY, Object value) {
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
				data.get(indexY.intValue())[indexX.intValue()] = value;
			}
			
			@Override
			protected Object parse(Integer indexX, Integer indexY, String s) {
				Object o = s;
				try {
					o = dateFormat.parse(s);
				} 
				catch (ParseException e) { /* Ignore */}
				return o;
			}
			
			@Override
			public Control createControl(Integer indexX, Integer indexY) {
				Object value = data.get(indexY.intValue())[indexX.intValue()];
				
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
			public Object[] getCheckboxEmulation(Integer indexX, Integer indexY) {
				Object value = data.get(indexY.intValue())[indexX.intValue()];
				return value instanceof Boolean ? getDefaultCheckBoxImages() : null;
			}
		};

		
		// Paint text from the model in the body
		matrix.getBody().replacePainter(new Painter<Integer, Integer>("cells", Painter.SCOPE_CELLS) {
			@Override
			public String getText(Integer indexX, Integer indexY) {
				Object value = data.get(indexY.intValue())[indexX.intValue()];
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
		
		shell.setBounds(400, 200, 600, 400);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
