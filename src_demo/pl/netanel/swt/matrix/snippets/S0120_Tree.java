package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.AxisItem;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Section;

public class S0120_Tree {
  public static void main(String[] args) {
    final Shell shell = new Shell();
    Display display = shell.getDisplay();
    shell.setLayout(new FillLayout());

    final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.V_SCROLL);

    final Axis<Integer> axisX = matrix.getAxisX();
    axisX.getHeader().setVisible(true);
    final Section<Integer> bodyX = axisX.getBody();
    bodyX.setCount(4);

    final Axis<Integer> axisY = matrix.getAxisY();
    axisY.getHeader().setVisible(true);
    final Section<Integer> bodyY = axisY.getBody();
    bodyY.setDefaultMoveable(true);

    bodyY.setCount(20);
    bodyY.setParent(3, 9, 1);
    bodyY.setParent(11, 3);
    bodyY.setParent(12, 11);
    bodyY.setParent(13, 15);

    matrix.getBody().getPainter(Painter.NAME_CELLS).setTreeVisible(true);
    bodyY.setExpanded(null, false);


    matrix.addKeyListener(new KeyAdapter() {
      @Override
      public void keyReleased(KeyEvent e) {
        AxisItem<Integer> focusItem = axisY.getFocusItem();
        if (focusItem == null) return;
        Integer index = focusItem.getIndex();
        switch (e.character) {
        case '+': bodyY.setExpanded(index, true); matrix.refresh(); break;
        case '-': bodyY.setExpanded(index, false); matrix.refresh(); break;
        }
      }
    });

    shell.setBounds(400, 200, 600, 400);
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  static final String title = "Tree";
  static final String instructions = "";
}
