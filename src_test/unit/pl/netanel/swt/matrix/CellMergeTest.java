package pl.netanel.swt.matrix;

import org.junit.Assert;
import org.junit.Test;

public class CellMergeTest {
  @Test
  public void setMerged() {
    SectionCore<Integer> sectionX = new SectionCore<Integer>(Integer.class);
    SectionCore<Integer> sectionY = new SectionCore<Integer>(Integer.class);
    sectionX.setCount(10);
    sectionY.setCount(10);
    ZoneCore<Integer, Integer> zone = new ZoneCore<Integer, Integer>(sectionX, sectionY);

    zone.setMerged(1, 3, 1, 2);
    Assert.assertFalse(zone.isMerged(0, 0));
    Assert.assertTrue(zone.isMerged(1, 1));

    // Merge inside
    zone.setMerged(2, 2, 2, 2);
    Assert.assertFalse(zone.isMerged(1, 1));
    Assert.assertFalse(zone.isMerged(1, 3));
    Assert.assertFalse(zone.isMerged(2, 1));
    Assert.assertFalse(zone.isMerged(2, 2));
  }



}
