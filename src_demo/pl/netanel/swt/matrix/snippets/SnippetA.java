package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;

public class SnippetA {
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setLayout(new FillLayout());
		
		Matrix matrix = new Matrix(shell, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL); 
				//, new MatrixModel(new AxisModel(BigInteger.class), new AxisModel(BigInteger.class)));
		matrix.getModel1().getBody().setCount(1000000000); //new BigInteger("1000000000000000"));
		matrix.getModel0().getBody().setCount(1000000000); //new BigInteger("1000000000000000"));
		matrix.getModel1().getBody().setDefaultCellWidth(100);
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
