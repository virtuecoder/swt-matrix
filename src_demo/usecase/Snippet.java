package usecase;

import java.math.BigInteger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.Matrix;

public class Snippet {
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setLayout(new FillLayout());
		
		Axis model0 = new Axis(BigInteger.class);
		Axis model1 = new Axis(BigInteger.class);
		
		new Matrix(shell, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL , model0, model1);

		model1.getBody().setCount(new BigInteger("1000000000000000"));
		model0.getBody().setCount(new BigInteger("1000000000000000"));
		model1.getHeader().setDefaultCellWidth(40);
		model1.getBody().setDefaultCellWidth(50);
		model1.getHeader().setVisible(true);
		model0.getHeader().setVisible(true);
		
		
		shell.setBounds(200, 20, 1024, 568);
		shell.open();
		Display display = shell.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
