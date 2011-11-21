package usecase;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;

/**
 * Simplest matrix.
 */
public class S0001_SimplestMatrix {
  public static void main(String[] args) {
    Shell shell = new Shell();
    shell.setText(title);
    shell.setLayout(new FillLayout());

    Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);
    matrix.getAxisX().getBody().setCount(4);
    matrix.getAxisY().getBody().setCount(10);

    shell.setBounds(400, 200, 600, 400);
    shell.open();
    shell.pack();
    
    Display display = shell.getDisplay();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  // Meta data
  static final String title = "Simplest matrix";
  static final String instructions = "";
  static final String code = "0001";
}