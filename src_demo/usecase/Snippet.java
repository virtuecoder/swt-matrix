package usecase;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.ZoneEditor;

public class Snippet {
  public static void main(String[] args) throws InterruptedException {
    final Shell shell = new Shell();
    shell.setLayout(new FillLayout());


    Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.V_SCROLL);

    matrix.getAxisX().getBody().setCount(10);
    matrix.getAxisY().getBody().setCount(10);
    matrix.getAxisX().getHeader().setVisible(true);
    matrix.getAxisY().getHeader().setVisible(true);

    new ZoneEditor<Integer, Integer>(matrix.getBody());

    Text control = new Text(shell, SWT.BORDER);

    shell.setBounds(400, 200, 600, 400);
    shell.open();

    control.forceFocus();
    matrix.refresh();
    System.out.println(shell.getDisplay().getFocusControl().getClass());

    Display display = shell.getDisplay();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }
}
