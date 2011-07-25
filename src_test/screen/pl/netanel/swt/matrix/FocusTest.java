package pl.netanel.swt.matrix;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class) public class  FocusTest extends SwtTestCase {
  
  @Test public void focusInitiated() throws Exception {
    Matrix matrix = createMatrix();
    AxisItem focusItem0 = matrix.getAxisY().getFocusItem();
    AxisItem focusItem1 = matrix.getAxisX().getFocusItem();
    assertEquals(0, focusItem0.getIndex());
    assertEquals(0, focusItem1.getIndex());
  }
  
  @Test public void focusMovedOnHeaderClick() throws Exception {
    Matrix matrix = createMatrix();
    click(matrix, matrix.getColumnHeader().getCellBounds(0, 0));
    assertEquals(0, matrix.getAxisY().getFocusItem().getIndex().intValue());
  }
  
}
