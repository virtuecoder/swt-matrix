package pl.netanel.swt.matrix;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class) 
@SuppressWarnings({"unchecked", "rawtypes"}) 
public class  DragMoveTest extends SwtTestCase {
  
  @Test public void dragForthAndBack() throws Exception {
    final Matrix matrix = new Matrix(shell, 0);
//    listenToAll(matrix);
    matrix.getAxisY().getBody().setCount(5);
    matrix.getAxisY().getHeader().setVisible(true);
    matrix.getAxisX().getBody().setCount(5);
    matrix.getAxisX().getBody().setDefaultMoveable(true);
    
    shell.open();
    
    processEvents();
    Rectangle bounds1 = matrix.getHeaderX().getCellBounds(0, 0);
    Rectangle bounds2 = matrix.getHeaderX().getCellBounds(1, 0);
    Point p1 = middle(bounds1);
    Point p2 = middle(bounds2);
    click(bounds1);
    dragAndDrop(SWT.BUTTON1, p1, p2, p1);
    Assert.assertEquals(0, matrix.getAxisX().getBody().getIndex(0).intValue());
  }
  
  @Test public void dragBigToSmall() throws Exception {
    final Matrix matrix = new Matrix(shell, 0);
//    listenToAll(matrix);
    matrix.getAxisY().getBody().setCount(5);
    matrix.getAxisY().getHeader().setVisible(true);
    matrix.getAxisX().getBody().setCount(5);
    matrix.getAxisX().getBody().setDefaultMoveable(true);
    matrix.getAxisX().getBody().setCellWidth(0, 100);
    
    shell.open();
    
    processEvents();
    Rectangle bounds1 = matrix.getHeaderX().getCellBounds(0, 0);
    Rectangle bounds2 = matrix.getHeaderX().getCellBounds(1, 0);
    Point p1 = middle(bounds1);
    Point p2 = middle(bounds2);
    click(bounds1);
    dragAndDrop(SWT.BUTTON1, p1, p2);
    Assert.assertEquals(0, matrix.getAxisX().getBody().getIndex(1).intValue());
  }
  
  @Test public void dragSmallToBigAndOutside() throws Exception {
    final Matrix matrix = new Matrix(shell, 0);
//    listenToAll(matrix);
    matrix.getAxisY().getBody().setCount(5);
    matrix.getAxisY().getHeader().setVisible(true);
    matrix.getAxisX().getBody().setCount(5);
    matrix.getAxisX().getBody().setDefaultMoveable(true);
    matrix.getAxisX().getBody().setCellWidth(1, 100);
    
    shell.open();
    
    processEvents();
    Rectangle r1 = matrix.getHeaderX().getCellBounds(0, 0);
    Rectangle r2 = matrix.getHeaderX().getCellBounds(1, 0);
    Point p1 = middle(r1);
    Point p2 = middle(r2);
    click(r1);
    dragAndDrop(SWT.BUTTON1, p1, p2);
    Assert.assertEquals(0, matrix.getAxisX().getBody().getIndex(1).intValue());
    
    // Move outside
    Point p3 = new Point(matrix.toDisplay(0, 0).x, p1.y);
    dragAndDrop(SWT.BUTTON1, display.getCursorLocation(), p1, p3);
    Assert.assertEquals(0, matrix.getAxisX().getBody().getIndex(0).intValue());
  }
  
  @Test public void dragBigOutside() throws Exception {
    final Matrix matrix = new Matrix(shell, 0);
//    listenToAll(matrix);
    matrix.getAxisY().getBody().setCount(5);
    matrix.getAxisY().getHeader().setVisible(true);
    matrix.getAxisX().getBody().setCount(5);
    matrix.getAxisX().getBody().setDefaultMoveable(true);
    matrix.getAxisX().getBody().setCellWidth(0, 100);
    
    shell.open();
    
    processEvents();
    Rectangle r = matrix.getHeaderX().getCellBounds(0, 0);
    Point p1 = middle(r);
    Point p2 = toDisplay(r.x-10, r.y + 5);
    click(r);
    dragAndDrop(SWT.BUTTON1, p1, p2);
    Assert.assertEquals(0, matrix.getAxisX().getBody().getIndex(0).intValue());
  }

  @Ignore
  @Test public void dragIndicator() throws Exception {
    final Matrix matrix = new Matrix(shell, 0);
//    listenToAll(matrix);
    matrix.getAxisY().getBody().setCount(5);
    matrix.getAxisY().getHeader().setVisible(true);
    matrix.getAxisX().getBody().setCount(5);
    matrix.getAxisX().getBody().setDefaultMoveable(true);
    
    shell.open();
    
    processEvents();
//    Rectangle bounds1 = matrix.getHeaderX().getCellBounds(0, 0);
//    Rectangle bounds2 = matrix.getHeaderX().getCellBounds(1, 0);
//    Point pt = middle(bounds1);
    
//    click(bounds1);
    
//    Event event = new Event();
//    event.type = SWT.MouseEnter;
//    event.x = pt.x;
//    event.y = pt.y;
//    postEvent(event);
//
//    event.type = SWT.MouseMove;
//    postEvent(event);
//    
//    event.type = SWT.MouseDown;
//    event.button = 1;
//    postEvent(event);
    
//    move(SWT.BUTTON1, middle(bounds2));
//    Assert.assertEquals(0, matrix.getAxisX().getBody().getIndex(0).intValue());
  }
}
