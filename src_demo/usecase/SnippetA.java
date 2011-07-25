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
		Axis axisX = matrix.getAxisX();
		Axis axisY = matrix.getAxisY();
		
		axisY.getBody().setCount(100); //new BigInteger("1000000000000000"));
		axisX.getBody().setCount(100); //new BigInteger("1000000000000000"));
//		matrix.getAxisX().getBody().setDefaultCellWidth(100);
		axisY.getHeader().setVisible(true);
		axisX.getHeader().setVisible(true);
//		axisY.freezeHead(1);
//		axisX.freezeHead(2);
		axisX.getHeader().setDefaultResizable(true);
		axisX.getBody().setDefaultResizable(true);
		axisX.getBody().setResizable(4, 4, false);
		axisX.getBody().setDefaultMoveable(true);
		axisY.getBody().setDefaultMoveable(true);
		
//		matrix.getBody().setSelectionEnabled(false);
//		matrix.getBody().setBackground(1, 1, Resources.getColor(SWT.COLOR_YELLOW));
//		matrix.getBody().setBackground(3, 3, Resources.getColor(SWT.COLOR_YELLOW));
//		matrix.getBody().setBackground(1, 1, null);
		
		shell.setBounds(400, 200, 600, 400);
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
