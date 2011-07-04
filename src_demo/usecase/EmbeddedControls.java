package usecase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.ZoneEditor;

/**
 * Simplest zone editor.
 */
public class EmbeddedControls {

	public static void main(String[] args) throws IOException {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		
		// Data model
		final ArrayList<Object[]> data = new ArrayList();
		data.add(new Object[] {"a", true, new Date()});
		data.add(new Object[] {true, false, "Monday"});
		data.add(new Object[] {new Date(), "Sunday", "b"});
//		for (int i = 0; i < 100; i++) {
//			data.add(new Object[] {i % 3 == 0 ? true : null, null, null});
//		}
		
		// Matrix
		final Matrix matrix = new Matrix(shell, SWT.NONE);
		
		matrix.getAxis0().getBody().setCount(data.size());
		matrix.getAxis0().getHeader().setVisible(true);
		matrix.getAxis1().getBody().setCount(3);
		matrix.getAxis1().getBody().setDefaultCellWidth(80);
		// To test positioning of the embedded controls while resizing columns
		matrix.getAxis1().getBody().setDefaultResizable(true);

		// Data painter
		matrix.getBody().replacePainter(new Painter("cells", Painter.SCOPE_CELLS_HORIZONTALLY) {
			@Override
			public String getText(Number index0, Number index1) {
				Object value = data.get(index0.intValue())[index1.intValue()];
				return value == null || value instanceof Boolean ? "" : value.toString();
			}
		});
		
		// Body editor
		new ZoneEditor(matrix.getBody()) {
			@Override
			protected Object getModelValue(Number index0, Number index1) {
				return data.get(index0.intValue())[index1.intValue()];
			}
			@Override
			public void setModelValue(Number index0, Number index1, Object value) {
				data.get(index0.intValue())[index1.intValue()] = value;
			}
			
			@Override
			public boolean hasEmbeddedControl(Number index0, Number index1) {
//				Object value = data.get(index0.intValue())[index1.intValue()];
//				return value instanceof Boolean;
				return true;
			}
			
			@Override
			protected Control createControl(Number index0, Number index1) {
				Object value = data.get(index0.intValue())[index1.intValue()];
				if (value instanceof Boolean) {
					return new Button(matrix, SWT.CHECK);
				}
				return new Text(matrix.getParent(), SWT.NONE);
			}
			
			@Override
			protected void setBounds(Number index0, Number index1, Control control) {
				if (control instanceof Text) {
					Rectangle bounds = matrix.getBody().getCellBounds(index0, index1);
					control.setBounds(bounds);
				} else {
					super.setBounds(index0, index1, control);
				}
			}
		};
		
		shell.setBounds(400, 200, 400, 300);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
