package pl.netanel.swt.matrix.snippets;

import java.math.BigInteger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.AxisModel;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.MatrixModel;

public class Snippet {
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setLayout(new FillLayout());
		
		Matrix matrix = new Matrix(shell, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL, 
				new MatrixModel(new AxisModel(BigInteger.class), new AxisModel(BigInteger.class)));
		matrix.getAxis1().getBody().setCount(new BigInteger("1000000000000000"));
		matrix.getAxis0().getBody().setCount(new BigInteger("1000000000000000"));
		matrix.getAxis0().setHeaderVisible(true);
		matrix.getAxis1().setHeaderVisible(true);
		
		
		shell.setBounds(200, 20, 1024, 768);
		shell.open();
		Display display = shell.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
