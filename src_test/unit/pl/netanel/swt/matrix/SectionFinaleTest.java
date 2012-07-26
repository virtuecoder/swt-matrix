package pl.netanel.swt.matrix;

import static org.junit.Assert.*;

import org.junit.*;

public class SectionFinaleTest {
  private SectionCore<Integer> section;

  @Before
  public void setUp() {
    section = new SectionCore<Integer>(int.class);
  }

  @Test
  public void setCount() throws Exception {
    section.setCount(10);
    assertEquals("0-9", section.finale.toString());
  }

  @Test
  public void setOrder() throws Exception {
    section.setCount(10);
    section.setOrder(2, 4, 8);
    assertEquals("0-1, 5-7, 2-4, 8-9", section.finale.toString());
    section.setHidden(4, 6, true);
    assertEquals("0-1, 7, 2-3, 8-9", section.finale.toString());
  }
}
