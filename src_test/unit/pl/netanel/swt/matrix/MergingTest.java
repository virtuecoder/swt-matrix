package pl.netanel.swt.matrix;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MergingTest {

  private SectionCore<Integer> sectionX;
  private SectionCore<Integer> sectionY;
  private ZoneCore<Integer, Integer> zone;

  @Before
  public void setUp() {
    sectionX = new SectionCore<Integer>(Integer.class);
    sectionY = new SectionCore<Integer>(Integer.class);
    sectionX.setCount(10);
    sectionY.setCount(10);
    zone = new ZoneCore<Integer, Integer>(sectionX, sectionY);
  }

  @Test
  public void setMerged() {

    zone.setMerged(1, 3, 1, 2);
    assertFalse(zone.isMerged(0, 0));
    assertTrue(zone.isMerged(1, 1));

    // Merge inside
    zone.setMerged(2, 2, 2, 2);
    assertFalse(zone.isMerged(1, 1));
    assertFalse(zone.isMerged(1, 3));
    assertFalse(zone.isMerged(2, 1));
    assertFalse(zone.isMerged(2, 2));
  }

  @Test
  public void mergeSingle() throws Exception {
    zone.setMerged(1, 1, 1, 1);
    assertFalse(zone.isMerged(1, 1));
  }

}
