package pl.netanel.swt.matrix;

import org.junit.Assert;
import org.junit.Test;

public class MatrixLayoutTest {
  @Test
  public void empty() throws Exception {
    AxisLayout<Integer> layoutX = new AxisLayout<Integer>();
    AxisLayout<Integer> layoutY = new AxisLayout<Integer>();
    MatrixLayout<Integer, Integer> layout = new MatrixLayout<Integer, Integer>(layoutX, layoutY);
    layout.compute();
    Assert.assertTrue(layout.layoutX.main.items.isEmpty());
  }
}
