package pl.netanel.swt.matrix;

import static org.junit.Assert.assertFalse;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
@SuppressWarnings({ "unchecked", "rawtypes" })
public class PainterTest extends SwtTestCase {
  @Test
  public void gradientCurrentRowBackground() throws Exception {
    final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, 0);
    // listenToAll(matrix);
    matrix.getAxisY().getBody().setCount(5);
    matrix.getAxisX().getBody().setCount(5);

    final Zone<Integer, Integer> body = matrix.getBody();
    body.getPainter(Painter.NAME_LINES_X).setEnabled(false);
    body.getPainter(Painter.NAME_LINES_Y).setEnabled(false);

    body.addPainter(0, new Painter<Integer, Integer>("gradient row background",
      Painter.SCOPE_CELLS_ITEM_Y) {
      int matrixWidth;

      @Override
      protected boolean init() {
        gc.setForeground(display.getSystemColor(SWT.COLOR_RED));
        gc.setBackground(display.getSystemColor(SWT.COLOR_LIST_BACKGROUND));
        gc.setAdvanced(true);
        if (gc.getAdvanced()) gc.setAlpha(127);
        matrixWidth = matrix.getClientArea().width;
        return true;
      }

      @Override
      public void clean() {
        gc.setAlpha(255);
      }

      @Override
      public void paint(Integer indexX, Integer indexY, int x, int y,
        int width, int height) {
        Axis axisY = matrix.getAxisY();
        AxisItem focusItem = axisY.getFocusItem();
        if (body.getSectionY().equals(focusItem.getSection())
          && indexY.equals(focusItem.getIndex())) {
          gc.fillGradientRectangle(0, y - 1, matrixWidth, height + 2, false);
        }
      }
    });

    shell.open();
    processEvents();

    RGB rgb1 = matrix.getBackground().getRGB();
    RGB rgb2 = getRGB(body.getCellBounds(0, 0), 2);
    assertFalse("The background color should be different then default one",
      rgb1.equals(rgb2));
  }

  // Ticket #10
  // @Ignore
  @Test
  public void righAlignWhenAfterScroll() throws Exception {
    Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell,
      SWT.H_SCROLL | SWT.V_SCROLL);
    matrix.getAxisX().getBody().setCount(4);
    matrix.getAxisY().getBody().setCount(10);
    Zone<Integer, Integer> body = matrix.getBody();
    Painter<Integer, Integer> painter = new Painter<Integer, Integer>("cells",
      Painter.SCOPE_CELLS_Y) {
      @Override
      public String getText(Integer indexX, Integer indexY) {
        textAlignX = indexX.intValue() == 2 ? SWT.RIGHT : SWT.LEFT;
        return indexY.toString() + ", " + indexX;
      }
    };
    body.replacePainter(painter);

    shell.setBounds(300, 300, 150, 150);
    shell.open();
    processEvents();

    press(SWT.END);

    Rectangle r = matrix.getBody().getCellBounds(2, 0);
    Image actual = getImage(toDisplay(r));

    Rectangle clientArea = matrix.getClientArea();
    final Image expected = new Image(display, r.width, r.height);
    GC gc = new GC(matrix);
    gc.fillRectangle(clientArea);
    String text = painter.getText(2, 0);
    int x = r.x + r.width - painter.textMarginX - gc.stringExtent(text).x;
    int y = r.y + painter.textMarginY;
    gc.drawText(text, x, y);
    gc.copyArea(expected, 1, 1);
    painter.printGC(gc);
    gc.dispose();
    shell.addDisposeListener(new DisposeListener() {
      @Override
      public void widgetDisposed(DisposeEvent e) {
        expected.dispose();
      }
    });

    assertEqualImage(expected, actual);

  }

  Image paintCell(Zone zone, Number indexY, Number indexX) {
    Matrix matrix = zone.getMatrix();
    Rectangle r = zone.getCellBounds(indexX, indexY);
    Painter painter = matrix.getBody().getPainter(Painter.NAME_CELLS);
    Rectangle clientArea = matrix.getClientArea();

    final Image image = new Image(display, clientArea.width, clientArea.height);
    GC gc = new GC(matrix);
    painter.init(gc);
    painter.paint(2, 0, r.x, r.y, r.width, r.height);
    gc.copyArea(image, 0, 0);
    gc.dispose();
    shell.addDisposeListener(new DisposeListener() {
      @Override
      public void widgetDisposed(DisposeEvent e) {
        image.dispose();
      }
    });
    return image;
  }
}
