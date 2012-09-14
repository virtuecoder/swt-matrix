package pl.netanel.swt.matrix;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MergeTest {

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
  public void setMergedInclusive() {
    zone.sectionX.setOrder(5, 7, 1);
    zone.setMerged(5, 3, 1, 2, true);
    assertFalse(zone.isMerged(0, 0));
    assertTrue(zone.isMerged(5, 1));

    // Merge part of another merge removes the other merge
    zone.setMerged(0, 2, 5, 1, true);
    assertFalse(zone.isMerged(1, 1));
    assertFalse(zone.isMerged(1, 3));
    assertFalse(zone.isMerged(2, 1));
    assertFalse(zone.isMerged(2, 2));
  }

  @Test
  public void setMergedExclusive() {
    zone.setMerged(1, 2, 1, 2, true);
    zone.setMerged(4, 2, 4, 2, true);

    // Both are merged
    assertTrue(zone.isMerged(1, 1));
    assertTrue(zone.isMerged(2, 2));
    assertTrue(zone.isMerged(4, 4));
    assertTrue(zone.isMerged(5, 5));

    // Remove only second by merging part of it
    assertFalse(zone.setMerged(4, 1, 4, 1, true));
    assertTrue(zone.isMerged(1, 1));
    assertTrue(zone.isMerged(2, 2));
    assertFalse(zone.isMerged(4, 4));
    assertFalse(zone.isMerged(5, 5));
  }

  @Test
  public void mergeSingle() throws Exception {
    zone.setMerged(1, 1, 1, 1, true);
    assertFalse(zone.isMerged(1, 1));
  }

  @Test
  public void mergeTwoX() throws Exception {
    zone.setMerged(3, 2, 1, 1, true);
    assertTrue(zone.isMerged(3, 1));
    assertTrue(zone.isMerged(4, 1));
  }

  @Test
  public void mergeTwoY() throws Exception {
    zone.setMerged(1, 1, 1, 2, true);
    assertTrue(zone.isMerged(1, 1));
    assertTrue(zone.isMerged(1, 2));
  }

  @Test
  public void isMergedAxisItem() throws Exception {
    zone.setMerged(1, 1, 1, 2, true);
    assertTrue(zone.isMerged(1, null));
    assertTrue(zone.isMerged(null, 2));
  }

}
