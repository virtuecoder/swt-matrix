package pl.netanel.swt.matrix;

import static org.junit.Assert.*;
import org.junit.*;

public class SpanExtentSequenceTest {
  private NumberOrder<Integer> set;
  private NumberOrder<Integer>.SpanExtentSequence seq;

  @Before
  public void setUp() {
    set = new NumberOrder<Integer>(Math.getInstance(Integer.class));
    seq = set.spanExtents;
  }

  @Test
  public void _0_00() throws Exception {
    seq.configure(0, 0);
    seq.init();
    assertFalse(seq.next());
  }

  @Test
  public void _0_01() throws Exception {
    seq.configure(0, 1);
    seq.init();
    assertFalse(seq.next());
  }

  @Test
  public void _0_0_1() throws Exception {
    seq.configure(0, -1);
    seq.init();
    assertFalse(seq.next());
  }

  @Test
  public void _0__11() throws Exception {
    seq.configure(-1, 1);
    seq.init();
    assertFalse(seq.next());
  }

  @Test
  public void _1_00() throws Exception {
    set.add(0);
    seq.configure(0, 0);
    seq.init();
    assertFalse(seq.next());
  }

  @Test
  public void _1_01() throws Exception {
    set.add(0);
    seq.configure(0, 1);
    seq.init();
    assertTrue(seq.next());
    assertEquals("0-0", seq.current.toString());
    assertFalse(seq.next());
  }

  /**
   * Over extend.
   * @throws Exception
   */
  @Test //(expected=IllegalArgumentException.class)
  public void _1_02() throws Exception {
    set.add(0);
    seq.configure(0, 2);
    seq.init();
    assertTrue(seq.next());
    assertEquals("0-0", seq.current.toString());
    assertFalse(seq.next());
  }

  @Test
  public void _3_02() throws Exception {
    set.add(0, 2);
    seq.configure(0, 2);
    seq.init();
    assertTrue(seq.next());
    assertEquals("0-1", seq.current.toString());
    assertFalse(seq.next());
  }

  @Test
  public void _3_12() throws Exception {
    set.add(0, 2);
    seq.configure(1, 2);
    seq.init();
    assertTrue(seq.next());
    assertEquals("1-2", seq.current.toString());
    assertFalse(seq.next());
  }

  @Test
  public void _10_2() throws Exception {
    set.add(0, 1);
    set.add(5, 6);
    set.add(2, 4);
    set.add(7, 9);
    assertEquals("0-1, 5-6, 2-4, 7-9", set.toString());
    // 0-1, 5-6, 2-4, 7-9
    seq.configure(1, 5);
    seq.init();
    assertTrue(seq.next());
    assertEquals("1-1", seq.current.toString());
    assertTrue(seq.next());
    assertEquals("5-6", seq.current.toString());
    assertTrue(seq.next());
    assertEquals("2-3", seq.current.toString());
    assertFalse(seq.next());
    assertEquals("0-1, 5-6, 2-4, 7-9", set.toString());
  }

  @Test
  public void _10_2hidden2() throws Exception {
    set.add(0, 1);
    set.add(5, 6);
    set.add(3, 4);
    set.add(7, 9);
    seq.configure(1, 5);
    seq.init();
    assertTrue(seq.next());
    assertEquals("1-1", seq.current.toString());
    assertTrue(seq.next());
    assertEquals("5-6", seq.current.toString());
    assertTrue(seq.next());
    assertEquals("3-4", seq.current.toString());
    assertFalse(seq.next());
  }
}






























