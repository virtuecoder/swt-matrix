package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;

/**
 * Simplest matrix.
 */
public class Snippet_0001 {
	public static void main(String[] args) {
		Shell shell = (new Shell());
		shell.setText("Simplest matrix");
		shell.setLayout(new FillLayout());
		
		Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);
		matrix.getAxis1().getBody().setCount(4);
		matrix.getAxis0().getBody().setCount(10);
		matrix.getAxis0().getBody().setDefaultResizable(true);
		matrix.getAxis1().getBody().setDefaultResizable(true);
		matrix.getAxis0().getHeader().setVisible(true);
		matrix.getAxis1().getHeader().setVisible(true);
		
		shell.setBounds(400, 200, 800, 400);
		shell.open();
		Display display = shell.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
