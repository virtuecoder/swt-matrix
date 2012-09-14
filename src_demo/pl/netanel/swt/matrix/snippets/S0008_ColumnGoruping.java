package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.ColumnGrouping;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Section;

public class S0008_ColumnGoruping {
  public static void main(String[] args) {
    final Shell shell = new Shell();
    Display display = shell.getDisplay();
    shell.setLayout(new FillLayout());

    final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.V_SCROLL);

    final Axis<Integer> axisX = matrix.getAxisX();
    axisX.getHeader().setVisible(true);
    final Section<Integer> bodyX = axisX.getBody();
    bodyX.setCount(10);
    bodyX.setDefaultMoveable(true);
    bodyX.setDefaultHideable(true);

    final Axis<Integer> axisY = matrix.getAxisY();
    axisY.getHeader().setVisible(true);
    final Section<Integer> bodyY = axisY.getBody();
    bodyY.setCount(10);

    ColumnGrouping<Integer, Integer> grouping = new ColumnGrouping<Integer, Integer>(matrix);
    grouping.group(1, 2, 0, true);
    grouping.group(3, 5, 1, true);
    grouping.group(6, 7, 9, false);

    bodyX.setExpanded(0, true);

    shell.setBounds(400, 200, 600, 400);
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  static final String title = "ColumnGoruping";
  static final String instructions = "";
  static final String code = "0008";
}
