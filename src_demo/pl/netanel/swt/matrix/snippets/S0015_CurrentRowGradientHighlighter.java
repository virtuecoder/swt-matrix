package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.AxisItem;
import pl.netanel.swt.matrix.Frozen;
import pl.netanel.swt.matrix.Zone;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;

/**
 * Current row gradient highlighter.
 */
public class S0015_CurrentRowGradientHighlighter {
  public static void main(String[] args) {
    Shell shell = new Shell();
    shell.setText(title);
    shell.setBounds(400, 200, 600, 400);
    shell.setLayout(new FillLayout());
    final Display display = shell.getDisplay();

    final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);

    matrix.getAxisY().getBody().setCount(10);
    matrix.getAxisX().getBody().setCount(4);

    final Zone<Integer, Integer> body = matrix.getBody();
    body.getPainter(Painter.NAME_LINES_X).setEnabled(false);
    body.getPainter(Painter.NAME_LINES_Y).setEnabled(false);

    body.addPainter(0, new Painter<Integer, Integer>("gradient row background",
      Painter.SCOPE_CELLS_ITEM_Y) {
      int matrixWidth;
      AxisItem<Integer> focusItem;
      boolean isFocused;

      @Override
      protected boolean init() {
        gc.setForeground(display.getSystemColor(SWT.COLOR_RED));
        gc.setBackground(display.getSystemColor(SWT.COLOR_LIST_BACKGROUND));
        gc.setAdvanced(true);
        if (gc.getAdvanced()) gc.setAlpha(127);
        // matrixWidth = matrix.getClientArea().width;
        matrixWidth = body.getBounds(Frozen.NONE, Frozen.NONE).width;

        // Get focus item
        Axis<Integer> axisY = matrix.getAxisY();
        focusItem = axisY.getFocusItem();

        return true;
      }

      @Override
      public void clean() {
        gc.setAlpha(255);
        gc.setAdvanced(false);
      }

      @Override
      public void setup(Integer indexX, Integer indexY) {
        isFocused = focusItem == null ? false :
          body.getSectionY().equals(focusItem.getSection())
          && indexY.equals(focusItem.getIndex());
      }

      @Override
      public void paint(int x, int y, int width, int height) {
        if (isFocused) {
          gc.fillGradientRectangle(0, y - 1, matrixWidth, height + 2, false);
        }
      }
    });

    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  // Meta data
  static final String title = "Current row gradient highlighter";
  static final String instructions = "";
  static final String code = "0015";
}