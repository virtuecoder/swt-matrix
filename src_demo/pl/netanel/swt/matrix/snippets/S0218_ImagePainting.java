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
public class S0218_ImagePainting {

  private static final int ROW_COUNT = 8;
  private static final int COLUMN_COUNT = 4;

  public static void main(String[] args) {
    Shell shell = new Shell();
    shell.setText(title);
    shell.setBounds(400, 200, 600, 400);
    shell.setLayout(new FillLayout());
    Display display = shell.getDisplay();

    // Create image
    final Image image1 = new Image(display, 16, 16);
    GC gc = new GC(image1);
    gc.setBackground(display.getSystemColor(SWT.COLOR_BLUE));
    gc.setAntialias(SWT.ON);
    gc.fillOval(0, 0, 16, 16);
    gc.dispose();
    final Image image2 = new Image(display, 14, 14);
    gc = new GC(image2);
    gc.setBackground(display.getSystemColor(SWT.COLOR_GREEN));
    gc.setAntialias(SWT.ON);
    gc.fillRectangle(0, 0, 14, 14);
    gc.dispose();
    final Image image3 = new Image(display, 12, 12);
    gc = new GC(image3);
    gc.setBackground(display.getSystemColor(SWT.COLOR_RED));
    gc.setAntialias(SWT.ON);
    gc.fillPolygon(new int[] {0, 10, 6, 2, 12, 10});
    gc.dispose();
    display.addListener(SWT.Dispose, new Listener() {
      @Override
      public void handleEvent(Event event) {
        image1.dispose();
        image2.dispose();
        image3.dispose();
      }
    });

    // Image data model
    final Image[][] images = new Image[ROW_COUNT][];
    images[1] = new Image[COLUMN_COUNT];
    images[1][1] = image1;
    images[5] = new Image[COLUMN_COUNT];
    images[5][3] = image2;
    images[2] = new Image[COLUMN_COUNT];
    images[2][0] = image3;

    Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.V_SCROLL);
    Section<Integer> bodyX = matrix.getAxisX().getBody();
    bodyX.setCount(COLUMN_COUNT);
    bodyX.setDefaultCellWidth(50);

    matrix.getAxisX().getHeader().setVisible(true);
    matrix.getAxisY().getHeader().setVisible(true);
    matrix.getAxisY().getBody().setCount(ROW_COUNT);

    final Painter<Integer, Integer> painter = new Painter<Integer, Integer>(Painter.NAME_CELLS) {
      @Override
      public void setupSpatial(Integer indexX, Integer indexY) {
        super.setupSpatial(indexX, indexY);
        text = indexY.toString() + ", " + indexX;
        imagesBefore = (indexX == 0 && indexY == 0) ? new Image[] {image1, image2} : null;
        imagesAfter = (indexX == 0 && indexY == 0) ? new Image[] {image3} : null;
        Image[] row = images[indexY.intValue()];
        image = row == null ? null : row[indexX.intValue()];
        style.imageAlignX = indexX.intValue() == 1 ? SWT.RIGHT : SWT.LEFT;
        style.imageAlignY = SWT.CENTER;
        style.imageMarginX = 2;
      }
    };
    matrix.getBody().replacePainterPreserveStyle(painter);
    painter.trackPosition(image1, true);
    painter.trackPosition(image2, true);
    painter.trackPosition(image3, true);
    matrix.getBody().replacePainterPreserveStyle(painter);

    matrix.getBody().addListener(SWT.MouseDown, new Listener() {
      @Override
      public void handleEvent(Event e) {
        Image image = painter.getImageAt(e.x, e.y);
        String s = image == image1 ? "image1" :
          image == image2 ? "image2" :
          image == image3 ? "image3" : null;
        if (s != null) {
          System.out.println(s);
        }
      }
    });

    matrix.getAxisX().getBody().setDefaultMoveable(true);

    shell.setBounds(400, 200, 600, 400);
    shell.open();

    matrix.getAxisX().getBody().setCellWidth(0);

    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  // Meta data
  static final String title = "Image painting";
  static final String instructions = "";
}