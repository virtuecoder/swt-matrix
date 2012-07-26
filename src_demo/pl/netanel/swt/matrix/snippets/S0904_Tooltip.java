package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.AxisItem;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.util.Util;

public class S0904_Tooltip {

  public static void main(String[] args) {
    final Shell shell = new Shell();
    shell.setText("hehe");
    shell.setLayout(new FillLayout());

    final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell,
        SWT.V_SCROLL);

    matrix.getAxisX().getBody().setCount(10);
    matrix.getAxisY().getBody().setCount(10);
    matrix.getAxisX().getHeader().setVisible(true);
    matrix.getAxisY().getHeader().setVisible(true);


    matrix.addListener(SWT.MouseMove, new Listener() {
      private AxisItem<Integer> lastItemX;
      private AxisItem<Integer> lastItemY;

      @Override
      public void handleEvent(Event e) {
        // If cell is different
        AxisItem<Integer> itemX = matrix.getAxisX().getItemByViewportDistance(e.x);
        AxisItem<Integer> itemY = matrix.getAxisY().getItemByViewportDistance(e.y);
        if (itemX == null || itemY == null) {
          matrix.setToolTipText(null);
        }
        else if (!Util.equals(itemX, lastItemX) || !Util.equals(itemY, lastItemY)) {
          lastItemX = itemX;
          lastItemY = itemY;
          matrix.setToolTipText(lastItemY.getIndex() + ", " + lastItemX.getIndex());
        }
      }
    });

    shell.setBounds(400, 200, 600, 400);
    shell.open();
    Display display = shell.getDisplay();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }
}
