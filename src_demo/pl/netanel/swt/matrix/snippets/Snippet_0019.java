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
		matrix.getAxisX().getBody().setCount(4);
		matrix.getAxisX().getBody().setDefaultResizable(true);
		matrix.getAxisY().getBody().setCount(10);
		matrix.getAxisY().getHeader().setVisible(true);
		
		matrix.getHeaderX().replacePainter(
			new Painter<Integer, Integer>(Painter.NAME_CELLS, Painter.SCOPE_CELLS_Y) {
				@Override
				public String getText(Integer indexX, Integer indexY) {
					textAlignX = indexX.intValue() == 2 ? SWT.RIGHT : SWT.LEFT;
					return indexX.toString();
				}
			});

		matrix.getBody().replacePainter(
			new Painter<Integer, Integer>(Painter.NAME_CELLS, Painter.SCOPE_CELLS_Y) {
				@Override
				public String getText(Integer indexX, Integer indexY) {
					textAlignX = indexX.intValue() == 2 ? SWT.RIGHT : SWT.LEFT;
					return indexY.toString() + ", " + indexX;
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
