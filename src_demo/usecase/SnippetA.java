package usecase;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.Matrix;

public class SnippetA {
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setLayout(new FillLayout());
		
		Matrix matrix = new Matrix(shell, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL); 
				//, new MatrixModel(new AxisModel(BigInteger.class), new AxisModel(BigInteger.class)));
		Axis axis1 = matrix.getAxis1();
		Axis axis0 = matrix.getAxis0();
		
		axis0.getBody().setCount(100); //new BigInteger("1000000000000000"));
		axis1.getBody().setCount(100); //new BigInteger("1000000000000000"));
//		matrix.getAxis1().getBody().setDefaultCellWidth(100);
		axis0.getHeader().setVisible(true);
		axis1.getHeader().setVisible(true);
		axis0.freezeHead(1);
		axis1.freezeHead(2);
		axis1.getHeader().setDefaultResizable(true);
		axis1.getBody().setDefaultResizable(true);
		axis1.getBody().setResizable(4, 4, false);
		axis1.getBody().setDefaultMoveable(true);
		
//		matrix.getBody().setSelectionEnabled(false);
//		matrix.getBody().setBackground(1, 1, Resources.getColor(SWT.COLOR_YELLOW));
//		matrix.getBody().setBackground(3, 3, Resources.getColor(SWT.COLOR_YELLOW));
//		matrix.getBody().setBackground(1, 1, null);
		
		
		shell.setBounds(200, 20, 1024, 568);
		shell.open();
		
//		matrix.setBounds(5, 5, 600, 300);
		
		Display display = shell.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
