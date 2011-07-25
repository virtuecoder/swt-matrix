package pl.netanel.swt.matrix;

import static org.junit.Assert.assertEquals;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.ScrollBar;
import org.junit.Assert;
import org.junit.Test;

public class ScrollTest extends SwtTestCase {
  
  @Test public void scrollBarvisibleAfterOpen() throws Exception {
    Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell,
      SWT.H_SCROLL | SWT.V_SCROLL);
    matrix.getAxis1().getBody().setCount(10);
    matrix.getAxis0().getBody().setCount(10);
    shell.setBounds(100, 100, 200, 200);
    
    shell.open();
    processEvents();
    
    Assert.assertTrue(matrix.getHorizontalBar().isVisible());
    Assert.assertTrue(matrix.getVerticalBar().isVisible());
  }

  // Ticket #8
  @Test public void showScrollBarWhenResizedOutside() throws Exception {
    Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(
      shell, SWT.H_SCROLL | SWT.V_SCROLL);
    matrix.getAxis1().getBody().setCount(4);
    matrix.getAxis1().getBody().setDefaultResizable(true);
    matrix.getAxis0().getBody().setCount(10);
    matrix.getAxis0().getHeader().setVisible(true);
    
    shell.open();
    processEvents();

    Rectangle cell = matrix.getColumnHeader().getCellBounds(0, 0);
    Rectangle window = shell.getBounds();
    int y = cell.y + 5;
    Point p1 = toDisplay(cell.x + cell.width, y);
    Point p2 = new Point(window.x + window.width + 15, y);
    dragAndDrop(SWT.BUTTON1, p1, p2);
    
    Assert.assertTrue(matrix.getHorizontalBar().isVisible());
  }

  
  @Test public void scrollBarSelectionAtTheEndWhenCellsNotEven() throws Exception {
    Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell,
      SWT.H_SCROLL | SWT.V_SCROLL);

    matrix.getAxis0().getBody().setCount(10);
    Section<Integer> body0 = matrix.getAxis1().getBody();
    body0.setCount(4);
    body0.setCellWidth(0, 300);
    body0.setCellWidth(1, 200);
    body0.setDefaultResizable(true);
    matrix.getAxis0().getHeader().setVisible(true);
    
    ScrollBar scroll = matrix.getHorizontalBar();
    shell.setBounds(600, 400, 300, 200);
    shell.open();
    processEvents();
//    show();

    assertEquals(4, scroll.getMaximum());
    
    press(SWT.END);
    assertEquals(2, scroll.getThumb());
    assertEquals(2, scroll.getSelection());
    assertEquals(4, scroll.getMaximum());
//    assertEquals(scroll.getMaximum() - scroll.getThumb(), scroll.getSelection());
  }
}
