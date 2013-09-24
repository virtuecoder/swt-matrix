package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;

public class S0220_Padding {

  public static void main(String[] args) {
    Shell shell = new Shell();
    Display display = shell.getDisplay();
    shell.setText(title);
    shell.setLayout(new FillLayout());

    final Image image1 = new Image(display, 16, 16);
    GC gc = new GC(image1);
    gc.setBackground(display.getSystemColor(SWT.COLOR_BLUE));
    gc.setAntialias(SWT.ON);
    gc.fillOval(0, 0, 16, 16);
    gc.dispose();

    Matrix<Integer, Integer> matrix =
      new Matrix<Integer, Integer>(shell, SWT.H_SCROLL | SWT.V_SCROLL);
    matrix.getAxisX().getBody().setCount(12);
    matrix.getAxisX().getBody().setDefaultResizable(true);
    matrix.getAxisY().getBody().setCount(12);
    matrix.getAxisY().getHeader().setVisible(true);

    matrix.getBody().replacePainterPreserveStyle(
      new Painter<Integer, Integer>(Painter.NAME_CELLS) {
        @Override
        public void setupSpatial(Integer indexX, Integer indexY) {
          int x = indexX.intValue();
          int y = indexY.intValue();
          style.textAlignX = x < 3 ? SWT.LEFT : SWT.RIGHT;
          style.textAlignY = y < 3 ? SWT.TOP : SWT.BOTTOM;
          style.textMarginLeft = x < 3 ? x % 3 * 2 : 0;
          style.textMarginRight = x >= 3 && x < 6 ? x % 3 * 2 : 0;
          style.textMarginTop = y < 3 ? y % 3 * 2 : 0;
          style.textMarginBottom = y >= 3 && y < 6 ? y % 3 * 2 : 0;

          style.imageMarginLeft = x >= 6 && x < 9 ? x % 3 * 2 : 0;
          style.imageMarginRight = x >= 9 && x < 12 ? x % 3 * 2 : 0;
          style.imageMarginTop = y >= 6 && y < 9 ? y % 3 * 2 : 0;
          style.imageMarginBottom = y >= 9 && y < 12 ? y % 3 * 2 : 0;

          image = x >= 6 ? image1 : null;
          text = indexY.toString() + ", " + indexX;
        }
      });

    matrix.getAxisX().pack();
    matrix.getAxisY().pack();

    shell.pack();
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  // Meta data
  static final String title = "Cell padding";
  static final String instructions = "";
}