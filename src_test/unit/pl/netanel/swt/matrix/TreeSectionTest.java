package pl.netanel.swt.matrix;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class TreeSectionTest {
  SectionCore<Integer> section;

  @Before
  public void setUp() {
    section = new SectionCore<Integer>(Integer.class);
  }

  public void setUp20() {
    int itemCount = 20;

    section.setCount(itemCount);
    for (int i = 3; i < 10; i++) {
      section.setParent(i, 1);
    }
    section.setParent(11, 3);
    section.setParent(12, 11);
    section.setParent(13, 15);
  }

  @Test
  public void getChildrenCount() throws Exception {
    setUp20();
    assertEquals(11, section.getChildrenCount(null).intValue());
    assertEquals(0, section.getChildrenCount(0).intValue());
    assertEquals(7, section.getChildrenCount(1).intValue());
    assertEquals(1, section.getChildrenCount(11).intValue());
  }

  /**
   * Initially everything is burried
   * @throws Exception
   */
  @Test
  public void isExposedInitial() throws Exception {
    setUp20();
    assertFalse(section.isBuried(0));
    assertFalse(section.isBuried(1));
    assertTrue(section.isBuried(3));
    assertTrue(section.isBuried(9));
    assertTrue(section.isBuried(11));
  }

  @Test
  public void setExpandedRoot() throws Exception {
    setUp20();
    section.setExpanded(null, false);
    assertFalse(section.isBuried(0));
    assertFalse(section.isBuried(1));
    assertTrue(section.isBuried(3));
    assertTrue(section.isBuried(9));
    assertTrue(section.isBuried(11));
  }

  @Test
  public void setExpanded() throws Exception {
    setUp20();
    section.setExpanded(null, false);
    section.setExpanded(1, true);
    assertFalse(section.isBuried(0));
    assertFalse(section.isBuried(1));
    assertFalse(section.isBuried(3));
    assertTrue(section.isBuried(11));

    section.setExpanded(3, true);
    assertFalse(section.isBuried(11));

    section.setExpanded(null, false);
    assertFalse(section.isBuried(0));
    assertFalse(section.isBuried(1));
    assertTrue(section.isBuried(3));
    assertTrue(section.isBuried(9));
    assertTrue(section.isBuried(11));
  }

  @Test
  public void order() throws Exception {
    setUp20();
    section.setExpanded(1, true);

    Iterator<Integer> it = section.getOrder().numberIterator(null);
    assertEquals("0", it.next().toString());
    assertEquals("1", it.next().toString());
    assertEquals("3", it.next().toString());
    assertEquals("11", it.next().toString());

    assertEquals(7, section.getChildrenCount(1).intValue());
    section.setOrder(5, 1);
    assertEquals(6, section.getChildrenCount(1).intValue());
  }
}
