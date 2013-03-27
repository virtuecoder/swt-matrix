package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.AxisItem;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Section;

/**
 * Identifying items by distance.
 */
public class S0903_IdentifyingItemsByDistance {
  public static void main(String[] args) {
    Shell shell = new Shell();
    shell.setText(title);
    shell.setLayout(new FillLayout());

    final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);
    matrix.getAxisY().getHeader().setVisible(true);
    matrix.getAxisX().getHeader().setVisible(true);
    final Section<Integer> bodyX = matrix.getAxisX().getBody();
    final Section<Integer> bodyY = matrix.getAxisY().getBody();
    bodyX.setCount(4);
    bodyY.setCount(10);

    matrix.addMouseMoveListener(new MouseMoveListener() {
      @Override
      public void mouseMove(MouseEvent e) {
        AxisItem<Integer> itemX = matrix.getAxisX().getMouseItem();
        AxisItem<Integer> itemY = matrix.getAxisY().getMouseItem();
        System.out.println(
            (itemX == null ? "" : itemX.toString()) + ":" +
            (itemY == null ? "" : itemY.toString()));
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

  // Meta data
  static final String title = "Identifying items by distance";
  static final String instructions = "";
}