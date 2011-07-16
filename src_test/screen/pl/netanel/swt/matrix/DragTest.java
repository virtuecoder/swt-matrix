package pl.netanel.swt.matrix;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.junit.*;

public class DragTest extends SwtTestCase {
  @Test public void moveForthAndBack() throws Exception {
    final Matrix matrix = new Matrix(shell, 0);
//    listenToAll(matrix);
    matrix.getAxis0().getBody().setCount(5);
    matrix.getAxis0().getHeader().setVisible(true);
    matrix.getAxis1().getBody().setCount(5);
    matrix.getAxis1().getBody().setDefaultMoveable(true);
    
    shell.open();
    
    processEvents();
    Rectangle bounds1 = matrix.getColumnHeader().getCellBounds(0, 0);
    Rectangle bounds2 = matrix.getColumnHeader().getCellBounds(0, 1);
    Point p1 = middle(bounds1);
    Point p2 = middle(bounds2);
    click(bounds1);
    dragAndDrop(SWT.BUTTON1, p1, p2, p1);
    Assert.assertEquals(0, matrix.getAxis1().getBody().get(0).intValue());
  }
  
  @Test public void moveBigToSmall() throws Exception {
    final Matrix matrix = new Matrix(shell, 0);
//    listenToAll(matrix);
    matrix.getAxis0().getBody().setCount(5);
    matrix.getAxis0().getHeader().setVisible(true);
    matrix.getAxis1().getBody().setCount(5);
    matrix.getAxis1().getBody().setDefaultMoveable(true);
    matrix.getAxis1().getBody().setCellWidth(0, 100);
    
    shell.open();
    
    processEvents();
    Rectangle bounds1 = matrix.getColumnHeader().getCellBounds(0, 0);
    Rectangle bounds2 = matrix.getColumnHeader().getCellBounds(0, 1);
    Point p1 = middle(bounds1);
    Point p2 = middle(bounds2);
    click(bounds1);
    dragAndDrop(SWT.BUTTON1, p1, p2);
    Assert.assertEquals(0, matrix.getAxis1().getBody().get(1).intValue());
  }
  
  @Test public void moveSmallToBigAndOutside() throws Exception {
    final Matrix matrix = new Matrix(shell, 0);
//    listenToAll(matrix);
    matrix.getAxis0().getBody().setCount(5);
    matrix.getAxis0().getHeader().setVisible(true);
    matrix.getAxis1().getBody().setCount(5);
    matrix.getAxis1().getBody().setDefaultMoveable(true);
    matrix.getAxis1().getBody().setCellWidth(1, 100);
    
    shell.open();
    
    processEvents();
    Rectangle r1 = matrix.getColumnHeader().getCellBounds(0, 0);
    Rectangle r2 = matrix.getColumnHeader().getCellBounds(0, 1);
    Point p1 = middle(r1);
    Point p2 = middle(r2);
    click(r1);
    dragAndDrop(SWT.BUTTON1, p1, p2);
    Assert.assertEquals(0, matrix.getAxis1().getBody().get(1).intValue());
    
    // Move outside
    Point p3 = new Point(matrix.toDisplay(0, 0).x, p1.y);
    dragAndDrop(SWT.BUTTON1, display.getCursorLocation(), p1, p3);
    Assert.assertEquals(0, matrix.getAxis1().getBody().get(0).intValue());
  }
  
  @Test public void moveBigOutside() throws Exception {
    final Matrix matrix = new Matrix(shell, 0);
//    listenToAll(matrix);
    matrix.getAxis0().getBody().setCount(5);
    matrix.getAxis0().getHeader().setVisible(true);
    matrix.getAxis1().getBody().setCount(5);
    matrix.getAxis1().getBody().setDefaultMoveable(true);
    matrix.getAxis1().getBody().setCellWidth(0, 100);
    
    shell.open();
    
    processEvents();
    Rectangle r = matrix.getColumnHeader().getCellBounds(0, 0);
    Point p1 = middle(r);
    Point p2 = toDisplay(r.x-10, r.y + 5);
    click(r);
    dragAndDrop(SWT.BUTTON1, p1, p2);
    Assert.assertEquals(0, matrix.getAxis1().getBody().get(0).intValue());
  }

}
