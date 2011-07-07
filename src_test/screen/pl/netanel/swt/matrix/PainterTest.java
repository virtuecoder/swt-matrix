package pl.netanel.swt.matrix;

import static org.junit.Assert.assertFalse;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.junit.Test;

public class PainterTest extends SwtTestCase {
  @Test public void gradientCurrentRowBackground() throws Exception {
    final Matrix matrix = new Matrix(shell, 0);
    // listenToAll(matrix);
    matrix.getAxis0().getBody().setCount(5);
    matrix.getAxis1().getBody().setCount(5);
    
    final Zone body = matrix.getBody();
    body.getPainter("row lines").setEnabled(false);
    body.getPainter("column lines").setEnabled(false);
    
    body.addPainter(0, new Painter("gradient row background", Painter.SCOPE_ROW_CELLS) {
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
      public void paint(Number index0, Number index1, int x, int y, int width, int height) {
        Axis axis0 = matrix.getAxis0();
        AxisItem focusItem = axis0.getFocusItem();
        if (body.getSection0().equals(focusItem.getSection()) &&
          index0.equals(focusItem.getIndex())) 
        {
          gc.fillGradientRectangle(0, y - 1, matrixWidth, height + 2, false);
        }
      }
    });
    
    shell.open();
    processEvents();
    
    RGB rgb1 = matrix.getBackground().getRGB();
    RGB rgb2 = getRGB(body.getCellBounds(0, 0), 2);
    assertFalse("The background color should be different then default one", rgb1.equals(rgb2));
  }
}
