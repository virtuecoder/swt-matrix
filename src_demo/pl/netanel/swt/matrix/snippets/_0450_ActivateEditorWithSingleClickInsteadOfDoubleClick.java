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
public class _0450_ActivateEditorWithSingleClickInsteadOfDoubleClick {

	public static void main(String[] args) throws IOException {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		shell.setText(title);
		shell.setLayout(new FillLayout());
		
		// Data model
		final ArrayList<Object[]> data = new ArrayList<Object[]>();
		data.add(new Object[] {"a", true, new Date()});
		data.add(new Object[] {true, false, "Monday"});
		data.add(new Object[] {"c", "Sunday", "b"});
		
		// Matrix
		final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);
		
		matrix.getAxisY().getBody().setCount(data.size());
		matrix.getAxisX().getBody().setCount(3);

		Zone<Integer, Integer> body = matrix.getBody();
		
		// Data painter
    body.replacePainter(
      new Painter<Integer, Integer>(Painter.NAME_CELLS, Painter.SCOPE_CELLS) {
        @Override
        public String getText(Integer indexX, Integer indexY) {
          Object value = data.get(indexY.intValue())[indexX.intValue()];
          return value == null ? "" : value.toString();
        }
      }
    );
		
		// Body editor
		new ZoneEditor<Integer, Integer>(body) {
			@Override
			public void setModelValue(Integer indexX, Integer indexY, Object value) {
				data.get(indexY.intValue())[indexX.intValue()] = value;
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

	// Meta data
	static final String title = "Activate editor with single click instead of double click";
	static final String instructions = "";
	static final String code = "0450";
}