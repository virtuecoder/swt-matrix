package usecase;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.SwtTestCase;

public class Snippet {
  public static void main(String[] args) throws InterruptedException {
    final Shell shell = new Shell();
    final Display display = shell.getDisplay();
    shell.setLayout(new FillLayout());

    Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.V_SCROLL);

    matrix.getAxisX().getBody().setCount(10);
    matrix.getAxisY().getBody().setCount(10);
    matrix.getAxisX().getHeader().setVisible(true);
    matrix.getAxisY().getHeader().setVisible(true);

    shell.setBounds(400, 200, 600, 400);

    SwtTestCase.listenToAll(shell);
    shell.open();

    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }
}
