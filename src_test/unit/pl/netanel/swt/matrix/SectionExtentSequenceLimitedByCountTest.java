package pl.netanel.swt.matrix;

import static org.junit.Assert.*;
import org.junit.*;

public class SectionExtentSequenceLimitedByCountTest {
  private SectionExtentSequenceLimitedByCount<Integer> seq;
  private SectionCore<Integer> section;

  @Before
  public void setUp() {
    section = new SectionCore<Integer>(int.class);
  }

  @Test
  public void _0_00() throws Exception {
    section.setCount(0);
    seq = new SectionExtentSequenceLimitedByCount<Integer>(section, 0, 0);
    seq.init();
    assertFalse(seq.next());
  }

  @Test
  public void _0_01() throws Exception {
    section.setCount(0);
    seq = new SectionExtentSequenceLimitedByCount<Integer>(section, 0, 1);
    seq.init();
    assertFalse(seq.next());
  }

  @Test
  public void _1_00() throws Exception {
    section.setCount(1);
    seq = new SectionExtentSequenceLimitedByCount<Integer>(section, 0, 0);
    seq.init();
    assertFalse(seq.next());
  }

  @Test
  public void _1_01() throws Exception {
    section.setCount(1);
    seq = new SectionExtentSequenceLimitedByCount<Integer>(section, 0, 1);
    seq.init();
    assertTrue(seq.next());
    assertEquals("0-0", seq.extent.toString());
    assertFalse(seq.next());
  }

  /**
   * Over extend.
   * @throws Exception
   */
  @Test(expected=IllegalArgumentException.class)
  public void _1_02() throws Exception {
    section.setCount(1);
    seq = new SectionExtentSequenceLimitedByCount<Integer>(section, 0, 2);
    seq.init();
    assertTrue(seq.next());
    assertEquals("0-0", seq.extent.toString());
    assertFalse(seq.next());
  }

  @Test
  public void _3_02() throws Exception {
    section.setCount(3);
    seq = new SectionExtentSequenceLimitedByCount<Integer>(section, 0, 2);
    seq.init();
    assertTrue(seq.next());
    assertEquals("0-1", seq.extent.toString());
    assertFalse(seq.next());
  }

  @Test
  public void _3_12() throws Exception {
    section.setCount(3);
    seq = new SectionExtentSequenceLimitedByCount<Integer>(section, 1, 2);
    seq.init();
    assertTrue(seq.next());
    assertEquals("1-2", seq.extent.toString());
    assertFalse(seq.next());
  }

  @Test
  public void _10_2() throws Exception {
    section.setCount(10);
    section.setOrder(2, 4, 7); // 0-1, 5-6, 2-4, 7-9
    seq = new SectionExtentSequenceLimitedByCount<Integer>(section, 1, 5);
    seq.init();
    assertTrue(seq.next());
    assertEquals("1-1", seq.extent.toString());
    assertTrue(seq.next());
    assertEquals("5-6", seq.extent.toString());
    assertTrue(seq.next());
    assertEquals("2-3", seq.extent.toString());
    assertFalse(seq.next());
  }


}






























