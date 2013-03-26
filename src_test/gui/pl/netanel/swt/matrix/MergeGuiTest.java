package pl.netanel.swt.matrix;

import static org.junit.Assert.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
@SuppressWarnings({ "unchecked", "rawtypes" })
public class MergeGuiTest extends SwtTestCase {

  @Test public void merge() throws Exception {
    Matrix matrix = createMatrix();
    Zone body = matrix.getBody();
    body.setMerged(0, 3, 0, 3, true);
    body.getSectionX().setOrder(2, 2, 1);
    matrix.refresh();
    dragAndDrop(body.getCellBounds(2, 2), body.getCellBounds(3, 3));
    assertTrue(body.isSelected(3, 3));
    assertTrue(body.isSelected(3, 0));
  }

  @Test public void merge2() throws Exception {
    Matrix matrix = createMatrix();
    Zone body = matrix.getBody();
    body.setMerged(0, 3, 0, 3, true);
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
    body.setMerged(0, 3, 0, 3, true);
    body.setMerged(6, 3, 6, 3, true);
    body.getSectionX().setOrder(2, 2, 1);
    matrix.refresh();
    body.getSectionY().setOrder(7, 7, 2);
    matrix.refresh();
    dragAndDrop(body.getCellBounds(2, 2), body.getCellBounds(3, 3));
    assertTrue(body.isSelected(3, 3));
    assertFalse(body.isSelected(3, 0));
  }

  @Test public void mergeHidden() throws Exception {
    Matrix matrix = createMatrix();
    shell.setBounds(100, 100, 800, 600);
    matrix.getAxisY().getBody().setCount(10);
    matrix.getAxisX().getBody().setCount(10);
    matrix.refresh();

    Zone body = matrix.getBody();
    body.setMerged(0, 3, 0, 3, true);
    body.setMerged(3, 3, 0, 3, true);
    body.getSectionX().setHidden(1, true);
    matrix.refresh();
    assertEquals(101, body.getCellBounds(0, 0).width);
    body.getSectionX().setHidden(1, false);
    body.getSectionX().setHidden(0, true);
    matrix.refresh();
    assertEquals(101, body.getCellBounds(0, 0).width);
  }

  @Test
  public void wordWrap() throws Exception {
    Matrix matrix = createMatrix();
    shell.setBounds(100, 100, 800, 600);
    matrix.getAxisY().getBody().setCount(10);
    matrix.getAxisX().getBody().setCount(10);

    Zone body = matrix.getBody();
    body.setMerged(0, 1, 0, 2, true);
    Painter<Integer, Integer> painter = new Painter<Integer, Integer>(Painter.NAME_CELLS) {
      @Override
      public void setupSpatial(Integer indexX, Integer indexY) {
        super.setupSpatial(indexX, indexY);
        text = "1234 1234 1234 1234";
      }
    };
    body.replacePainterPreserveStyle(painter);
    painter.style.textAlignY = SWT.CENTER;
    painter.style.hasWordWraping = true;
    matrix.refresh();
    processEvents();

    Rectangle bounds = body.getCellBounds(0, 0);
    RGB rgb = getRGB(bounds.x + 2, bounds.y + 2);

    Color color = null;
    try {
      color = new Color(shell.getDisplay(), rgb);
      assertNotColor(color, bounds.x + 8, bounds.y + 8);
    }
    finally {
      if (color != null) color.dispose();
    }
  }
}
