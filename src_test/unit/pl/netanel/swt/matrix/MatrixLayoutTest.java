package pl.netanel.swt.matrix;

import static org.junit.Assert.assertEquals;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Shell;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings({"rawtypes", "unchecked"})
public class MatrixLayoutTest {
  @Test
  public void empty() throws Exception {
    AxisLayout<Integer> layoutX = new AxisLayout<Integer>();
    AxisLayout<Integer> layoutY = new AxisLayout<Integer>();
    MatrixLayout<Integer, Integer> layout = new MatrixLayout<Integer, Integer>(layoutX, layoutY);
    layout.compute();
    Assert.assertTrue(layout.layoutX.main.items.isEmpty());
  }

  @Test
  public void getCellBounds() throws Exception {
    Matrix matrix = new Matrix(new Shell(), SWT.None);
    matrix.getAxisX().getBody().setCount(10);
    matrix.getAxisY().getBody().setCount(10);
    matrix.setSize(1000, 1000);
    Zone zone = matrix.getBody();
    zone.setMerged(1, 2, 1, 1, true);
    matrix.refresh();
    Rectangle r = zone.getCellBounds(1, 1);
    assertEquals(101, r.width);
  }

  @Test
  public void getCellBoundsWhenFirstCellIsHiddenAndSecondHasBiggerLine() throws Exception {
    Matrix matrix = new Matrix(new Shell(), SWT.None);
    matrix.getAxisX().getBody().setCount(10);
    matrix.getAxisY().getBody().setCount(10);
    matrix.setSize(1000, 1000);
    Zone zone = matrix.getBody();
    zone.setMerged(1, 2, 1, 1, true);
    zone.getSectionX().setHidden(1, true);
    zone.getSectionX().setLineWidth(2, 5);
    matrix.refresh();
    Rectangle r = zone.getCellBounds(1, 1);
    assertEquals(50, r.width);
  }

}
