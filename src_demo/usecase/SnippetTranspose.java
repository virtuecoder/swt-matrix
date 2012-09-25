package usecase;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Section;

public class SnippetTranspose {

  private Matrix<Integer, Integer> matrix;

  public SnippetTranspose(Shell shell) {
    matrix = new Matrix<Integer, Integer>(shell, SWT.V_SCROLL | SWT.H_SCROLL);
    matrix.getAxisX().getHeader().setVisible(true);
    matrix.getAxisY().getHeader().setVisible(true);

    Section<Integer> bodyY = matrix.getAxisY().getBody();
    Section<Integer> bodyX = matrix.getAxisX().getBody();

    bodyX.setCount(10);
    bodyY.setCount(1);

    // Set order
    bodyX.setOrder(bodyX.getOrder());
    matrix.refresh();

    // Set Orientation
    Integer captionCount = bodyX.getCount();
    Integer entryCount = bodyY.getCount();
    if (captionCount != 0) bodyX.delete(0, captionCount - 1);
    if (entryCount != 0) bodyY.delete(0, entryCount - 1);
    bodyX.insert(0, captionCount);
    bodyY.insert(0, entryCount);
  }

  public static void main(String[] args) {
    final Shell shell = new Shell();
    shell.setLayout(new FillLayout());

    new SnippetTranspose(shell);

    shell.setBounds(400, 200, 900, 400);
    shell.open();

    Display display = shell.getDisplay();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }
}
