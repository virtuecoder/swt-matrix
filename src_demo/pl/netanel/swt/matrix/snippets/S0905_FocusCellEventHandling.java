package pl.netanel.swt.matrix.snippets;

import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.AxisItem;
import pl.netanel.swt.matrix.Extent;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Section;

/**
 * Selection and control event handling.
 */
public class S0905_FocusCellEventHandling {
  public static void main(String[] args) {
    Shell shell = new Shell();
    shell.setText(title);
    shell.setLayout(new FillLayout());

    final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.H_SCROLL);
    matrix.getAxisY().getHeader().setVisible(true);
    matrix.getAxisX().getHeader().setVisible(true);
    final Section<Integer> bodyX = matrix.getAxisX().getBody();
    final Section<Integer> bodyY = matrix.getAxisY().getBody();
    bodyX.setCount(4);
    bodyX.setDefaultResizable(true);
    bodyX.setDefaultMoveable(true);
    bodyY.setCount(10);

    bodyX.addFocusItemCallback(new Runnable() {
      public void run() {
        AxisItem<Integer> item = matrix.getAxisX().getFocusItem();
        if (item == null) return;
        System.out.println("Focus coloumn is: " + item.getIndex());
      }
    });
    bodyY.addFocusItemCallback(new Runnable() {
      public void run() {
        AxisItem<Integer> item = matrix.getAxisY().getFocusItem();
        if (item == null) return;
        System.out.println("Focus row is: " + item.getIndex());
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

  static void selectedItems(String caption, Iterator<Extent<Integer>> it) {
    StringBuilder sb = new StringBuilder();
    while (it.hasNext()) {
      if (sb.length() > 0) sb.append(", ");
      Extent<Integer> n = it.next();
      sb.append(n.getStart()).append("-").append(n.getEnd());
    }
    sb.insert(0, caption);
    System.out.println(sb.toString());
  }

  // Meta data
  static final String title = "Focus cell event handling";
  static final String instructions = "";
}