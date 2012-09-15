package pl.netanel.swt.matrix;

import static org.junit.Assert.assertFalse;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
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
      AxisItem focusItem;
      boolean isCurrent;

      @Override
      protected boolean init() {
        gc.setForeground(display.getSystemColor(SWT.COLOR_RED));
        gc.setBackground(display.getSystemColor(SWT.COLOR_LIST_BACKGROUND));
        gc.setAdvanced(true);
        if (gc.getAdvanced()) gc.setAlpha(127);
        matrixWidth = matrix.getClientArea().width;

        // Get the focus item
        Axis axisY = matrix.getAxisY();
        focusItem = axisY.getFocusItem();

        return true;
      }

      @Override
      public void clean() {
        gc.setAlpha(255);
      }

      @Override
        public void setup(Integer indexX, Integer indexY) {
          super.setup(indexX, indexY);
          isCurrent = body.getSectionY().equals(focusItem.getSection())
            && indexY.equals(focusItem.getIndex());
        }

      @Override
      public void paint(int x, int y, int width, int height) {
        if (isCurrent) {
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
      public void setupSpatial(Integer indexX, Integer indexY) {
        style.textAlignX = indexX.intValue() == 2 ? SWT.RIGHT : SWT.LEFT;
        text = indexY.toString() + ", " + indexX;
      }
    };
    body.replacePainterPreserveStyle(painter);

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
    painter.setupSpatial(2, 0);
    String text = painter.text;
    int x = r.x + r.width - painter.style.textMarginX - gc.stringExtent(text).x;
    int y = r.y + painter.style.textMarginY;
    gc.drawText(text, x, y);
    gc.copyArea(expected, 1, 1);
//    painter.printGC(gc);
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
    Painter painter = new TestPainter(matrix.getBody().getPainter(Painter.NAME_CELLS));
    Rectangle clientArea = matrix.getClientArea();

    final Image image = new Image(display, clientArea.width, clientArea.height);
    GC gc = new GC(matrix);
    painter.init(gc, Frozen.NONE, Frozen.NONE);
    painter.setup(0, 0);
    painter.paint(r.x, r.y, r.width, r.height);
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

  static class TestPainter extends Painter {
    Painter painter;

    public TestPainter(Painter painter) {
      super(painter.getName());
      this.painter = painter;
    }

    @Override
    public String toString() {
      return painter.toString();
    }

    @Override
    public String getName() {
      return painter.getName();
    }

    @Override
    public int getScope() {
      return painter.getScope();
    }

    @Override
    public void clean() {
      painter.clean();
    }

    @Override
    public void setup(Number indexX, Number indexY) {
      painter.setup(indexX, indexY);
    }

    @Override
    public void setupSpatial(Number indexX, Number indexY) {
      painter.setupSpatial(indexX, indexY);
    }

    public void test(int x, int y, int width, int height) {
      painter.paint(x, y, width, height);
    }

    @Override
    public void setData(Object data) {
      painter.setData(data);
    }

    @Override
    public Object getData() {
      return painter.getData();
    }

    @Override
    public void setEnabled(boolean enabled) {
      painter.setEnabled(enabled);
    }

    @Override
    public boolean isEnabled() {
      return painter.isEnabled();
    }

    @Override
    public Point computeSize(Number indexX, Number indexY, int wHint, int hHint) {
      return painter.computeSize(indexX, indexY, wHint, hHint);
    }
  }
}
