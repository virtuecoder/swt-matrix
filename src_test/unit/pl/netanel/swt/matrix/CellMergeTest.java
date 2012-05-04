package pl.netanel.swt.matrix;

import org.junit.Test;

public class CellMergeTest {
  @Test
  public void basic() {
    SectionCore<Integer> sectionX = new SectionCore<Integer>(Integer.class);
    SectionCore<Integer> sectionY = new SectionCore<Integer>(Integer.class);
    sectionX.setCount(10);
    sectionY.setCount(10);
    ZoneCore<Integer, Integer> zone = new ZoneCore<Integer, Integer>(sectionX, sectionY);
    
    zone.setMerged(0, 0, 3, 2);
  }

}
