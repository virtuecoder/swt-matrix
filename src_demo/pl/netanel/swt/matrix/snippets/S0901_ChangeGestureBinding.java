package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;

/**
 * Change gesture binding.
 */
public class S0901_ChangeGestureBinding {
  public static void main(String[] args) {
    Shell shell = new Shell();
    shell.setText(title);
    shell.setLayout(new FillLayout());

    Matrix<Integer, Integer> matrix = 
      new Matrix<Integer, Integer>(shell, SWT.NONE);
    matrix.getAxisX().getBody().setCount(4);
    matrix.getAxisY().getBody().setCount(10);

    // Replace HOME with CTRL+ARROW_LEFT
    matrix.unbind(Matrix.CMD_FOCUS_MOST_LEFT, SWT.KeyDown, SWT.HOME);
    matrix.bind(Matrix.CMD_FOCUS_MOST_LEFT, SWT.KeyDown, SWT.MOD1
      | SWT.ARROW_LEFT);

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
  static final String title = "Change gesture binding";
  static final String instructions = "";
}