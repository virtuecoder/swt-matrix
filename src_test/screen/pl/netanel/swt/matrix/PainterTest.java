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

@RunWith(JUnit4.class) public class  PainterTest extends SwtTestCase {
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
  
  // Ticket #10
  //@Ignore
  @Test public void righAlignWhenAfterScroll() throws Exception {
    Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(
      shell, SWT.H_SCROLL | SWT.V_SCROLL);
    matrix.getAxis1().getBody().setCount(4);
    matrix.getAxis0().getBody().setCount(10);
    Zone<Integer, Integer> body = matrix.getBody();
    Painter<Integer, Integer> painter = new Painter<Integer, Integer>("cells", Painter.SCOPE_CELLS_VERTICALLY) {
      @Override
      public String getText(Integer index0, Integer index1) {
        textAlignX = index1.intValue() == 2 ? SWT.RIGHT : SWT.LEFT;
        return index0.toString() + ", " + index1;
      }
    };
    body.replacePainter(painter);
    
    shell.setBounds(300, 300, 150, 150);
    shell.open();
    processEvents();
    
    press(SWT.END);

    Rectangle r = matrix.getBody().getCellBounds(0, 2);
    Image actual = getImage(toDisplay(r));
    
    Rectangle clientArea = matrix.getClientArea();
    final Image expected = new Image(display, r.width, r.height);
    GC gc = new GC(matrix);
    gc.fillRectangle(clientArea);
    String text = painter.getText(0, 2);
    int x = r.x + r.width - painter.textMarginX - gc.stringExtent(text).x; 
    int y = r.y + painter.textMarginY; 
    gc.drawText(text, x, y);
    gc.copyArea(expected, 1, 1);
    painter.printGC(gc);
    gc.dispose();
    shell.addDisposeListener(new DisposeListener() {
      @Override public void widgetDisposed(DisposeEvent e) {
        expected.dispose();
      }
    });
    saveImage(expected, "1.png");
    saveImage(actual, "2.png");
    
    assertEqualImage(expected, actual);
    
  }

   Image paintCell(Zone zone, Number index0, Number index1) {
    Matrix matrix = zone.getMatrix();
    Rectangle r = zone.getCellBounds(index0, index1);
    Painter painter = matrix.getBody().getPainter(Painter.NAME_CELLS);
    Rectangle clientArea = matrix.getClientArea();
    
    final Image image = new Image(display, clientArea.width, clientArea.height);
    GC gc = new GC(matrix);
    painter.init(gc);
    painter.paint(0, 2, r.x, r.y, r.width, r.height);
    gc.copyArea(image, 0, 0);
    gc.dispose();
    shell.addDisposeListener(new DisposeListener() {
      @Override public void widgetDisposed(DisposeEvent e) {
        image.dispose();
      }
    });
    return image;
  }
}
