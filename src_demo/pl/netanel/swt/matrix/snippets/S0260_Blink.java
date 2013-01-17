package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;

/**
 * Shows how make the cells to blink.
 */
public class S0260_Blink {

  public static void main(String[] args) {
    Shell shell = new Shell();
    final Display display = shell.getDisplay();
    shell.setText(title);
    shell.setBounds(400, 200, 600, 400);
    shell.setLayout(new FillLayout());

    final Matrix<Integer, Integer> matrix =
      new Matrix<Integer, Integer>(shell, SWT.H_SCROLL | SWT.V_SCROLL);
    matrix.getAxisX().getBody().setCount(4);
    matrix.getAxisX().getBody().setDefaultResizable(true);
    matrix.getAxisY().getBody().setCount(10);
    matrix.getAxisY().getHeader().setVisible(true);

    final Blinker blinker = new Blinker(matrix);

    matrix.getBody().replacePainterPreserveStyle(
      new Painter<Integer, Integer>(Painter.NAME_CELLS) {
        @Override
        public void setup(Integer indexX, Integer indexY) {
          style.background = indexX % 3 == 0 && indexY % 4 == 0 ?
              blinker.blinkColor : matrix.getBackground();
          super.setup(indexX, indexY);
        }
        @Override
        public void setupSpatial(Integer indexX, Integer indexY) {
          super.setupSpatial(indexX, indexY);
          text = indexY.toString() + ", " + indexX;
        }
      });

    shell.open();

    // Start blinking
    blinker.run();

    // Stop blinking after 10 seconds
    display.timerExec(10 * 1000, new Runnable() {
      @Override
      public void run() {
        blinker.stop();
      }
    });

    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  static class Blinker implements Runnable {
    Color background1, background2, blinkColor;
    final int blinkInterval = 500;
    int blinkIndex = 1;
    Matrix<Integer, Integer> matrix;
    boolean stop;

    public Blinker(Matrix<Integer, Integer> matrix) {
      this.matrix = matrix;
      background1 = matrix.getBackground();
      background2 = matrix.getDisplay().getSystemColor(SWT.COLOR_GREEN);
    }

    public void run() {
      if (stop) return;
      blinkIndex = blinkIndex*-1;
      blinkColor = blinkIndex > 0 ? background1 : background2;
      matrix.redraw();
      matrix.getDisplay().timerExec(blinkInterval, this);
    }

    public void stop() {
      // Set the color back to normal
      blinkColor = matrix.getBackground();
      matrix.redraw();
      stop = true;
    }
  }

  // Meta data
  static final String title = "Blink";
  static final String instructions = "";
}