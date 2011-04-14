package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;

/**
 * Change gesture binding.
 * 
 * @author Jacek Kolodziejczyk created 04-03-2011
 */
public class Snippet_0901 {
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setLayout(new FillLayout());
		
		Matrix<Integer, Integer> matrix = new Matrix(shell, SWT.NONE);
		matrix.getAxis1().getBody().setCount(4);
		matrix.getAxis0().getBody().setCount(10);
		
		// Replace HOME with CTRL+ARROW_LEFT
		matrix.unbind(Matrix.CMD_FOCUS_MOST_LEFT, SWT.KeyDown, SWT.HOME);
		matrix.bind(Matrix.CMD_FOCUS_MOST_LEFT, SWT.KeyDown, SWT.MOD1 | SWT.ARROW_LEFT);
		
		shell.setBounds(400, 200, 400, 300);
		shell.open();
		Display display = shell.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
