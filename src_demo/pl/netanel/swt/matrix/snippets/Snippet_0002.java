package pl.netanel.swt.matrix.snippets;

import java.math.BigInteger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.Matrix;

/**
 * Unlimited number of items.
 */
public class Snippet_0002 {
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setLayout(new FillLayout());
		
		Axis axis0 = new Axis(BigInteger.class, 2);
		
		Matrix<BigInteger, Integer> matrix = new Matrix(shell, SWT.V_SCROLL | SWT.H_SCROLL, axis0, null);
		matrix.getAxis1().getBody().setCount(2);
		matrix.getAxis1().getBody().setDefaultCellWidth(200);
		matrix.getAxis1().getHeader().setVisible(true);
		matrix.getAxis1().freezeHead(1);
		matrix.getAxis1().getHeader().setDefaultCellWidth(200);
		matrix.getAxis0().getBody().setCount(new BigInteger("123456789012345678901234567890"));
		
		shell.setBounds(400, 200, 500, 300);
		shell.open();
		Display display = shell.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
