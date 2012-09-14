package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.Frozen;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.Zone;

public class S0006_ColumnFillSpace {
  public static void main(String[] args) {
    Shell shell = new Shell();
    shell.setLayout(new FillLayout());

    final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(
        shell, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
    Axis<Integer> axisX = matrix.getAxisX();
    Axis<Integer> axisY = matrix.getAxisY();

    axisX.getBody().setCount(10);
    axisX.setFrozenHead(1);
    axisX.getBody().setDefaultCellWidth(50);
    axisX.getHeader().setDefaultCellWidth(40);
    axisX.getHeader().setVisible(true);

    final int itemCount = 10;
    axisY.getBody().setCount(itemCount);
    axisY.getHeader().setVisible(true);


    int[] weight = new int[] {5, 10, 5, 15, 5, 5, 20, 10, 10, 15};
    int weightSum = 0;
    for (int i = 0; i < itemCount; i++) {
      weightSum += weight[i];
    }
    final float[] factor = new float[itemCount];
    for (int i = 0; i < itemCount; i++) {
      factor[i] = ((float) weight[i]) / weightSum;
    }

    final Section<Integer> bodyX = axisX.getBody();
    final Zone<Integer, Integer> body = matrix.getBody();
    matrix.addControlListener(new ControlAdapter() {
      @Override
      public void controlResized(ControlEvent e) {
        Rectangle area = matrix.getClientArea();
        Rectangle bounds = body.getBounds(Frozen.NONE, Frozen.NONE);
        int width = area.width - bounds.x - sumOfLines();

        int i = 0;
        int sum = 0;
        for (; i < itemCount - 1; i++) {
          int w = (int) (width * factor[i]);
          bodyX.setCellWidth(i, w);
          sum += w;
        }
        // Make last fill the remaining space accurately
        bodyX.setCellWidth(i, width - sum);

        matrix.refresh();
      }

      private int sumOfLines() {
        int sum = 0;
        for (int i = 0; i <= itemCount; i++) {
          sum += bodyX.getLineWidth(i);
        }
        return sum;
      }
    });

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
