package pl.netanel.swt.matrix;

import static org.junit.Assert.*;

import org.eclipse.swt.graphics.Rectangle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
@SuppressWarnings({ "unchecked", "rawtypes" })
public class MergeGuiTest
  extends SwtTestCase {

  @Test public void merge() throws Exception {
    Matrix matrix = createMatrix();
    Zone body = matrix.getBody();
    body.setMerged(0, 3, 0, 3);
    body.getSectionX().setOrder(2, 2, 1);
    matrix.refresh();
    dragAndDrop(body.getCellBounds(2, 2), body.getCellBounds(3, 3));
    assertTrue(body.isSelected(3, 3));
    assertTrue(body.isSelected(3, 0));
  }

  @Test public void merge2() throws Exception {
    Matrix matrix = createMatrix();
    Zone body = matrix.getBody();
    body.setMerged(0, 3, 0, 3);
    body.getSectionY().setOrder(4, 4, 1);
    matrix.refresh();

    Rectangle bounds2 = body.getCellBounds(3, 2);
    Rectangle bounds1 = new Rectangle(bounds2.x - 50, bounds2.y - 16, bounds2.width, bounds2.height);
    dragAndDrop(bounds1, bounds2);
    assertTrue(body.isSelected(0, 2));
    assertTrue(body.isSelected(3, 0));
  }

  @Test public void mergeTwo() throws Exception {
    Matrix matrix = createMatrix();
    shell.setBounds(100, 100, 800, 600);
    matrix.getAxisY().getBody().setCount(10);
    matrix.getAxisX().getBody().setCount(10);
    matrix.refresh();

    Zone body = matrix.getBody();
    body.setMerged(0, 3, 0, 3);
    body.setMerged(6, 3, 6, 3);
    body.getSectionX().setOrder(2, 2, 1);
    body.getSectionY().setOrder(7, 7, 2);
    matrix.refresh();
    dragAndDrop(body.getCellBounds(2, 2), body.getCellBounds(3, 3));
    pause();
    assertTrue(body.isSelected(3, 3));
    assertTrue(body.isSelected(3, 0));
  }
}
