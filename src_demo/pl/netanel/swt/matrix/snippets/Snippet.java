package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;

public class Snippet {
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setLayout(new FillLayout());
		
		Matrix matrix = new Matrix(shell, SWT.NONE);
		matrix.getAxis1().getBody().setCount(1000);
		matrix.getAxis0().getBody().setCount(1000);
		matrix.getAxis0().setHeaderVisible(true);
		matrix.getAxis0().setHeaderVisible(false);
		
		shell.setBounds(200, 100, 1024, 768);
		shell.open();
		Display display = shell.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
