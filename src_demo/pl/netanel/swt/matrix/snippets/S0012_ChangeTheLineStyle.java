package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;

/**
 * Change the line style.
 */
public class S0012_ChangeTheLineStyle {
  public static void main(String[] args) {
    Shell shell = new Shell();
    shell.setText(title);
    shell.setBounds(400, 200, 600, 400);
    shell.setLayout(new FillLayout());
    final Display display = shell.getDisplay();

    Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);
    matrix.getAxisX().getBody().setCount(4);
    matrix.getAxisY().getBody().setCount(10);

    matrix.getBody().replacePainter(
      new Painter<Integer, Integer>(Painter.NAME_LINES_X) {
        private int row;

        @Override
        public void setup(Integer indexX, Integer indexY) {
          row = indexY.intValue();
        };

        @Override
        public void paint(int x, int y, int width, int height) {
          switch (row) {
          case 1:
            gc.setBackground(display.getSystemColor(SWT.COLOR_BLUE));
            gc.fillRectangle(x, y, width, height);
            break;
          case 2:
            gc.setLineDash(new int[] { 4, 2 });
            gc.drawLine(x, y, x + width - 1, y + height - 1);
            gc.setLineDash(null);
            break;
          case 3:
            gc.setLineWidth(5);
            gc.setLineCap(SWT.CAP_ROUND);
            gc.drawLine(x, y, x + width - 1, y + height - 1);
            gc.setLineWidth(1);
            break;
          default:
            gc.setBackground(display
              .getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
            gc.fillRectangle(x, y, width, height);
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
  static final String title = "Change the line style";
  static final String instructions = "";
  static final String code = "0012";
}