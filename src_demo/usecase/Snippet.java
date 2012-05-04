package usecase;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.AxisItem;
import pl.netanel.swt.matrix.Matrix;

public class Snippet {
  public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setLayout(new FillLayout());

		Axis<Integer> axisX = new Axis<Integer>(Integer.class, 2, 0, 1);
		Axis<Long> axisY = new Axis<Long>(Long.class, 2, 0, 1);

		new Matrix<Integer, Long>(shell, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL, axisX, axisY);

		axisX.getBody().setCount(100);
		axisX.getBody().setDefaultCellWidth(50);
		axisX.getHeader().setDefaultCellWidth(40);
		axisX.getHeader().setVisible(true);

		axisY.getBody().setCount(1000000000000000L);
		axisY.getHeader().setVisible(true);

		AxisItem.create(axisY.getBody(), 5L);


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
