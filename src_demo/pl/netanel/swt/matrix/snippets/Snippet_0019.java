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
		Shell shell = new Shell();
		shell.setBounds(400, 200, 400, 300);
		shell.setLayout(new FillLayout());
		
		Matrix<Integer, Integer> matrix = new Matrix(shell, SWT.NONE);
		matrix.getAxis1().getBody().setCount(4);
		matrix.getAxis0().getBody().setCount(10);
		matrix.getAxis0().getHeader().setVisible(true);
		
		matrix.getColumnHeader().replacePainter(
				new Painter("cells", Painter.SCOPE_CELLS_VERTICALLY) {
					@Override
					public void paint(Number index0, Number index1, int x, int y, int width, int height)
					{
						text = index1.toString();
						textAlignX = index1.intValue() == 2 ? SWT.RIGHT : SWT.LEFT;
						super.paint(index0, index1, x, y, width, height);
					}
				});

		matrix.getBody().replacePainter(
			new Painter("cells", Painter.SCOPE_CELLS_VERTICALLY) {
				@Override
				public void paint(Number index0, Number index1, int x, int y, int width, int height)
				{
					text = index0.toString() + ", " + index1;
					textAlignX = index1.intValue() == 2 ? SWT.RIGHT : SWT.LEFT;
					super.paint(index0, index1, x, y, width, height);
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
