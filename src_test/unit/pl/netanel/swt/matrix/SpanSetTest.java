package pl.netanel.swt.matrix;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SpanSetTest {
  private SpanSet<Integer, Integer> set;
  private NumberOrder<Integer> orderX;
  private NumberOrder<Integer> orderY;

  @Before
  public void setUp() {
    Math<Integer> math = Math.getInstance(int.class);
    orderX = new NumberOrder<Integer>(math);
    orderY = new NumberOrder<Integer>(math);
    set = new SpanSet<Integer, Integer>(orderX, orderY);
  }

  @Test
  public void containsRegular() throws Exception {
    orderX.add(0, 9);
    orderY.add(0, 9);
    set.add(1, 3, 5, 2);
    assertFalse(set.contains(0, 0));
    assertTrue(set.contains(1, 5));
    assertTrue(set.contains(3, 5));
    assertTrue(set.contains(3, 6));
    assertFalse(set.contains(4, 5));
  }

  @Test
  public void containsIrregular() throws Exception {
    orderX.add(0, 1);
    orderX.add(5, 8);
    orderX.add(3, 4);
    orderY.add(0, 9);
    set.add(1, 3, 0, 2);
    assertFalse(set.contains(0, 0));
    assertFalse(set.contains(2, 0));
    assertTrue(set.contains(1, 0));
    assertTrue(set.contains(5, 0));
    assertTrue(set.contains(6, 0));
    assertFalse(set.contains(7, 0));
  }



}
