package pl.netanel.swt.matrix;

import static org.junit.Assert.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
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
    assertEquals(0, matrix.getAxisX().getBody().getIndex(0).intValue());
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
    assertEquals(0, matrix.getAxisX().getBody().getIndex(1).intValue());
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
    assertEquals(0, matrix.getAxisX().getBody().getIndex(1).intValue());

    // Move outside
    r1 = matrix.getHeaderX().getCellBounds(0, 0);
    r2 = matrix.getHeaderX().getCellBounds(1, 0);
    p1 = middle(r1);
    p2 = middle(r2);
    click(r1);
    Point p3 = new Point(matrix.toDisplay(0, 0).x, p1.y);
    dragAndDrop(SWT.BUTTON1, p1, p2, p3);
    assertEquals(0, matrix.getAxisX().getBody().getIndex(0).intValue());
  }

  @Test public void dragBigOutside() throws Exception {
    final Matrix matrix = new Matrix(shell, 0);
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
    assertEquals(0, matrix.getAxisX().getBody().getIndex(0).intValue());
  }

  @Test public void dragToFrontInside() throws Exception {
    final Matrix matrix = new Matrix(shell, 0);
    matrix.getAxisX().getBody().setCount(100);
    matrix.getAxisY().getBody().setCount(5);
    matrix.getAxisX().getBody().setDefaultMoveable(true);
    matrix.getAxisX().getHeader().setVisible(true);
    matrix.getAxisY().getHeader().setVisible(true);

    shell.open();

    Rectangle bounds1 = matrix.getHeaderX().getCellBounds(1, 0);
    Rectangle bounds2 = matrix.getHeaderX().getCellBounds(0, 0);
    Point p1 = middle(bounds1);
    Point p2 = middle(bounds2);
    p2.x -= 10;
    click(bounds1);
    dragAndDrop(SWT.BUTTON1, p1, p2);
    assertEquals(1, matrix.getAxisX().getBody().getIndex(0).intValue());
    click(bounds1);
    dragAndDrop(SWT.BUTTON1, p1, p2);
    assertEquals(0, matrix.getAxisX().getBody().getIndex(0).intValue());
  }

  @Test public void dragToFrontHeader() throws Exception {
    final Matrix matrix = new Matrix(shell, 0);
    matrix.getAxisX().getBody().setCount(100);
    matrix.getAxisY().getBody().setCount(5);
    matrix.getAxisX().getBody().setDefaultMoveable(true);
    matrix.getAxisX().getHeader().setVisible(true);
    matrix.getAxisY().getHeader().setVisible(true);

    shell.open();

    Rectangle bounds1 = matrix.getHeaderX().getCellBounds(1, 0);
    Rectangle bounds2 = matrix.getHeaderX().getCellBounds(0, 0);
    Point p1 = middle(bounds1);
    Point p2 = middle(bounds2);
    Point p3 = middle(bounds2);
    p3.x -= 50;
    click(bounds1);
    dragAndDrop(SWT.BUTTON1, p1, p2, p3);
    assertEquals(1, matrix.getAxisX().getBody().getIndex(0).intValue());
  }

  @Test public void dragToFrontOutside() throws Exception {
    final Matrix matrix = new Matrix(shell, 0);
    matrix.getAxisX().getBody().setCount(100);
    matrix.getAxisY().getBody().setCount(5);
    matrix.getAxisX().getBody().setDefaultMoveable(true);
    matrix.getAxisX().getHeader().setVisible(true);
    matrix.getAxisY().getHeader().setVisible(true);

    shell.open();

    Rectangle bounds1 = matrix.getHeaderX().getCellBounds(1, 0);
    Rectangle bounds2 = matrix.getHeaderX().getCellBounds(0, 0);
    Point p1 = middle(bounds1);
    Point p2 = middle(bounds2);
    Point p3 = middle(bounds2);
    p3.x -= 100;
    click(bounds1);
    dragAndDrop(SWT.BUTTON1, p1, p2, p3);
    assertEquals(1, matrix.getAxisX().getBody().getIndex(0).intValue());
  }

  // There was an NLP
  @Test public void dragBehindLastHidden() throws Exception {
    final Matrix matrix = new Matrix(shell, 0);
    matrix.getAxisY().getBody().setCount(5);
    matrix.getAxisY().getHeader().setVisible(true);
    matrix.getAxisX().getBody().setCount(5);
    matrix.getAxisX().getBody().setDefaultMoveable(true);
    matrix.getAxisX().getBody().setHidden(4, true);

    shell.open();

    processEvents();
    Rectangle r = matrix.getHeaderX().getCellBounds(2, 0);
    Point p1 = middle(r);
    r = matrix.getHeaderX().getCellBounds(3, 0);
    Point p2 = toDisplay(r.x+r.width + 10, r.y + 5);
    click(r);
    dragAndDrop(SWT.BUTTON1, p1, p2);
  }

  @Test public void dragWithAutoscroll() throws Exception {
    final Matrix matrix = new Matrix(shell, 0);
    matrix.getAxisX().getBody().setCount(100);
    matrix.getAxisY().getBody().setCount(5);
    matrix.getAxisX().getBody().setDefaultMoveable(true);
    matrix.getAxisX().getBody().setHidden(4, true);
    matrix.getAxisY().getHeader().setVisible(true);

    shell.open();

    processEvents();
    Rectangle r = matrix.getHeaderX().getCellBounds(1, 0);
    Point p1 = middle(r);
    r = matrix.getBounds();
    Point p2 = toDisplay(r.x + r.width + 10, r.y + 5);
    click(p1);
    dragAndDrop(SWT.BUTTON1, p1, p2);
    assertTrue(matrix.getAxisX().isItemInViewport(matrix.getAxisX().getBody(), 1));
  }
}
