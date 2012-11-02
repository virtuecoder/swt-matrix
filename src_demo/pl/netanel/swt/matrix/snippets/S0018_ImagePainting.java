package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Section;

/**
 * Image painting
 */
public class S0018_ImagePainting {

  private static final int ROW_COUNT = 8;
  private static final int COLUMN_COUNT = 4;

  public static void main(String[] args) {
    Shell shell = new Shell();
    shell.setText(title);
    shell.setBounds(400, 200, 600, 400);
    shell.setLayout(new FillLayout());
    Display display = shell.getDisplay();

    // Create image
    final Image image = new Image(display, 16, 16);
    GC gc = new GC(image);
    gc.setBackground(display.getSystemColor(SWT.COLOR_BLUE));
    gc.setAntialias(SWT.ON);
    gc.fillOval(0, 0, 16, 16);
    gc.dispose();
    display.addListener(SWT.Dispose, new Listener() {
      @Override
      public void handleEvent(Event event) {
        image.dispose();
      }
    });

    // Image data model
    final Image[][] images = new Image[ROW_COUNT][];
    images[1] = new Image[COLUMN_COUNT];
    images[1][1] = image;
    images[5] = new Image[COLUMN_COUNT];
    images[5][3] = image;
    images[2] = new Image[COLUMN_COUNT];
    images[2][0] = image;

    Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.V_SCROLL);
    Section<Integer> bodyX = matrix.getAxisX().getBody();
    bodyX.setCount(COLUMN_COUNT);
    bodyX.setDefaultCellWidth(50);

    matrix.getAxisY().getBody().setCount(ROW_COUNT);

    matrix.getBody().replacePainterPreserveStyle(
      new Painter<Integer, Integer>(Painter.NAME_CELLS) {
        @Override
        public void setup(Integer indexX, Integer indexY) {
          super.setup(indexX, indexY);
          text = indexY.toString() + ", " + indexX;
          Image[] row = images[indexY.intValue()];
          image = row == null ? null : row[indexX.intValue()];
          style.imageAlignX = indexX.intValue() == 1 ? SWT.RIGHT : SWT.LEFT;
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

  // Meta data
  static final String title = "Image painting";
  static final String instructions = "";
  static final String code = "0018";
}