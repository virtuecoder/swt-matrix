package pl.netanel.swt.matrix.snippets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.ZoneEditor;

/**
 * Simplest zone editor.
 */
public class Snippet_0401 {

	public static void main(String[] args) throws IOException {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		
		// Data model
		final ArrayList<Object[]> data = new ArrayList<Object[]>();
		data.add(new Object[] {"a", true, new Date()});
		data.add(new Object[] {true, false, "Monday"});
		data.add(new Object[] {"c", "Sunday", "b"});
		
		// Matrix
		final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);
		
		matrix.getAxis0().getBody().setCount(data.size());
		matrix.getAxis1().getBody().setCount(3);

		// Data painter
		matrix.getBody().replacePainter(
		  new Painter<Integer, Integer>("cells", Painter.SCOPE_CELLS_HORIZONTALLY) {
			@Override
			public String getText(Integer index0, Integer index1) {
				Object value = data.get(index0.intValue())[index1.intValue()];
				return value == null ? "" : value.toString();
			}
		});
		
		// Body editor
		new ZoneEditor<Integer, Integer>(matrix.getBody()) {
			@Override
			public void setModelValue(Integer index0, Integer index1, Object value) {
				data.get(index0.intValue())[index1.intValue()] = value;
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
