package pl.netanel.swt.matrix;

import static org.junit.Assert.assertEquals;

import org.eclipse.swt.widgets.Shell;
import org.junit.Test;

import pl.netanel.swt.matrix.AxisItem;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.TestUtil;

public class FocusTest {
  
  @Test public void focusInitiated() throws Exception {
    Matrix matrix = createMatrix();
    AxisItem focusItem0 = matrix.getAxis0().getFocusItem();
    AxisItem focusItem1 = matrix.getAxis1().getFocusItem();
    assertEquals(0, focusItem0.getIndex());
    assertEquals(0, focusItem1.getIndex());
  }
  
  @Test public void focusMovedOnHeaderClick() throws Exception {
    Matrix matrix = createMatrix();
    TestUtil.click(matrix, matrix.getColumnHeader().getCellBounds(0, 0), 1, 0);
    assertEquals(0, matrix.getAxis0().getFocusItem().getIndex().intValue());
  }
  
  static Matrix createMatrix() {
    Matrix matrix = new Matrix(new Shell(), 0);
    matrix.getAxis0().getHeader().setVisible(true);
    matrix.getAxis1().getHeader().setVisible(true);
    matrix.getAxis0().getBody().setCount(5);
    matrix.getAxis1().getBody().setCount(5);
    matrix.setSize(1000, 1000);
    return matrix;
  }
}
