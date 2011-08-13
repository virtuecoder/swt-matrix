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
public class _0002_UnlimitedNumberOfItems {
  public static void main(String[] args) {
    Shell shell = (new Shell());
    shell.setText(title);
    shell.setLayout(new FillLayout());

    Axis<BigInteger> axisY = new Axis<BigInteger>(BigInteger.class, 2, 0, 1);

    Matrix<Integer, BigInteger> matrix = new Matrix<Integer, BigInteger>(
      shell, SWT.V_SCROLL | SWT.H_SCROLL, null, axisY);

    matrix.getAxisX().getBody().setCount(2);
    matrix.getAxisX().getBody().setDefaultCellWidth(200);
    matrix.getAxisX().getHeader().setVisible(true);
    matrix.getAxisX().getHeader().setDefaultCellWidth(200);
    matrix.getAxisX().setFreezeHead(1);
    matrix.getAxisY().getBody().setCount(new BigInteger("123456789012345678901234567890"));

    shell.setBounds(400, 200, 600, 400);
    shell.open();
    Display display = shell.getDisplay();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

	// Meta data
	static final String title = "Unlimited number of items";
	static final String instructions = "";
	static final String code = "0002";
}