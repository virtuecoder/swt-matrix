package pl.netanel.swt.matrix.snippets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Zone;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.ZoneEditor;

/**
 * Activate editor with single click instead of double click.
 */
public class Snippet_0450 {

	public static void main(String[] args) throws IOException {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		shell.setText("Activate editor with single click instead of double click");
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

		Zone<Integer, Integer> body = matrix.getBody();
		
		// Data painter
    body.replacePainter(
      new Painter<Integer, Integer>("cells", Painter.SCOPE_CELLS_HORIZONTALLY) {
        @Override
        public String getText(Integer index0, Integer index1) {
          Object value = data.get(index0.intValue())[index1.intValue()];
          return value == null ? "" : value.toString();
        }
      }
    );
		
		// Body editor
		new ZoneEditor<Integer, Integer>(body) {
			@Override
			public void setModelValue(Integer index0, Integer index1, Object value) {
				data.get(index0.intValue())[index1.intValue()] = value;
			}
		};
		
		body.unbind(Matrix.CMD_EDIT_ACTIVATE, SWT.MouseDoubleClick, 1);
		body.bind(Matrix.CMD_EDIT_ACTIVATE, SWT.MouseDown, 1);
		
		shell.setBounds(400, 200, 600, 400);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
