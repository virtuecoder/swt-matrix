package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;

/**
 * Align column to the right.
 */
public class Snippet_0019 {
	
	public static void main(String[] args) {
		Shell shell = (new Shell());
    shell.setText("Align column to the right");
		shell.setBounds(400, 200, 600, 400);
		shell.setLayout(new FillLayout());
		
		Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(
		  shell, SWT.H_SCROLL | SWT.V_SCROLL);
		matrix.getAxis1().getBody().setCount(4);
		matrix.getAxis1().getBody().setDefaultResizable(true);
		matrix.getAxis0().getBody().setCount(10);
		matrix.getAxis0().getHeader().setVisible(true);
		
		matrix.getColumnHeader().replacePainter(
			new Painter<Integer, Integer>("cells", Painter.SCOPE_CELLS_VERTICALLY) {
				@Override
				public String getText(Integer index0, Integer index1) {
					textAlignX = index1.intValue() == 2 ? SWT.RIGHT : SWT.LEFT;
					return index1.toString();
				}
			});

		matrix.getBody().replacePainter(
			new Painter<Integer, Integer>("cells", Painter.SCOPE_CELLS_VERTICALLY) {
				@Override
				public String getText(Integer index0, Integer index1) {
					textAlignX = index1.intValue() == 2 ? SWT.RIGHT : SWT.LEFT;
					return index0.toString() + ", " + index1;
				}
		});
		
		shell.open();
		Display display = shell.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
