package pl.netanel.swt.matrix;

import static org.junit.Assert.*;

import java.text.MessageFormat;

import org.junit.Before;
import org.junit.Test;

public class MatrixLayoutSequenceTest {
  private ZoneCore<Integer, Integer> zone;
  private AxisLayout<Integer> layoutX;
  private AxisLayout<Integer> layoutY;
  private MatrixLayout<Integer, Integer> layout;
  private MatrixLayoutSequence<Integer, Integer> seq;

  @Before
  public void setUp() {
    layoutX = new AxisLayout<Integer>(); layoutX.setViewportSize(100000);
    layoutY = new AxisLayout<Integer>(); layoutY.setViewportSize(100000);
    layout = new MatrixLayout<Integer, Integer>(layoutX, layoutY);
    zone = layout.getZone(layoutX.body, layoutY.body);
  }

  @Test
  public void cellSequence0x0() throws Exception {
    layout.compute();
    seq = new MatrixLayoutSequence<Integer, Integer>(layout, Frozen.NONE, Frozen.NONE, zone, Matrix.TYPE_CELLS);
    seq.init();
    assertFalse(seq.next());
  }

  @Test
  public void cellSequence1x0() throws Exception {
    layout.compute();
    seq = new MatrixLayoutSequence<Integer, Integer>(layout, Frozen.NONE, Frozen.NONE, zone, Matrix.TYPE_CELLS);
    seq.init();
    assertFalse(seq.next());
  }

  @Test
  public void cellSequence0x1() throws Exception {
    layout.compute();
    seq = new MatrixLayoutSequence<Integer, Integer>(layout, Frozen.NONE, Frozen.NONE, zone, Matrix.TYPE_CELLS);
    seq.init();
    assertFalse(seq.next());
  }

  @Test
  public void cellSequence1x1() throws Exception {
    layoutX.body.setCount(1);
    layoutY.body.setCount(1);
    layout.compute();
    seq = new MatrixLayoutSequence<Integer, Integer>(layout, Frozen.NONE, Frozen.NONE, zone, Matrix.TYPE_CELLS);
    seq.init();
    assertTrue(seq.next());
    assertCell(0, 0, 1, 1, 16, 16);
    assertFalse(seq.next());
  }

  @Test
  public void cellSequence2x2() throws Exception {
    layoutX.body.setCount(2);
    layoutY.body.setCount(2);
    layout.compute();
    seq = new MatrixLayoutSequence<Integer, Integer>(layout, Frozen.NONE, Frozen.NONE, zone, Matrix.TYPE_CELLS);
    seq.init();
    assertTrue(seq.next());
    assertCell(0, 0, 1, 1, 16, 16);
    assertTrue(seq.next());
    assertCell(1, 0, 18, 1, 16, 16);
    assertTrue(seq.next());
    assertCell(0, 1, 1, 18, 16, 16);
    assertTrue(seq.next());
    assertCell(1, 1, 18, 18, 16, 16);
    assertFalse(seq.next());
  }

  @Test
  public void cellSequence2x2_merge2x2_smallViewport() throws Exception {
    layoutX.setViewportSize(10);
    layoutY.setViewportSize(10);
    layoutX.body.setCount(2);
    layoutY.body.setCount(2);
    zone.setMerged(0, 1, 0, 1);
    layout.compute();
    seq = new MatrixLayoutSequence<Integer, Integer>(layout, Frozen.NONE, Frozen.NONE, zone, Matrix.TYPE_CELLS);
    seq.init();
    assertTrue(seq.next());
    assertCell(0, 0, 1, 1, 33, 33);
    assertFalse(seq.next());
  }

  @Test
  public void cellSequence3x3Merge2x2() throws Exception {
    layoutX.body.setCount(3);
    layoutY.body.setCount(3);
    zone.setMerged(0, 1, 0, 1);
    layout.compute();
    seq = new MatrixLayoutSequence<Integer, Integer>(layout, Frozen.NONE, Frozen.NONE, zone, Matrix.TYPE_CELLS);
    seq.init();
    assertTrue(seq.next());
    assertCell(0, 0, 1, 1, 33, 33);
    assertTrue(seq.next());
    assertCell(2, 0, 35, 1, 16, 16);
    assertTrue(seq.next());
    assertCell(2, 1, 35, 18, 16, 16);
    assertTrue(seq.next());
    assertCell(0, 2, 1, 35, 16, 16);
    assertTrue(seq.next());
    assertCell(1, 2, 18, 35, 16, 16);
    assertTrue(seq.next());
    assertCell(2, 2, 35, 35, 16, 16);
    assertFalse(seq.next());
  }

  @Test
  public void cellSequence3x3Merge0101_2212() throws Exception {
    layoutX.body.setCount(3);
    layoutY.body.setCount(3);
    zone.setMerged(0, 1, 0, 1);
    zone.setMerged(2, 2, 1, 2);
    layout.compute();
    seq = new MatrixLayoutSequence<Integer, Integer>(layout, Frozen.NONE, Frozen.NONE, zone, Matrix.TYPE_CELLS);
    seq.init();
    assertTrue(seq.next());
    assertCell(0, 0, 1, 1, 33, 33);
    assertTrue(seq.next());
    assertCell(2, 0, 35, 1, 16, 16);
    assertTrue(seq.next());
    assertCell(2, 1, 35, 18, 16, 33);
    assertTrue(seq.next());
    assertCell(0, 2, 1, 35, 16, 16);
    assertTrue(seq.next());
    assertCell(1, 2, 18, 35, 16, 16);
    assertFalse(seq.next());
  }

  @Test
  public void cellSequence3x3Merge0101_0222() throws Exception {
    layoutX.body.setCount(3);
    layoutY.body.setCount(3);
    zone.setMerged(0, 1, 0, 1);
    zone.setMerged(0, 2, 2, 2);
    layout.compute();
    seq = new MatrixLayoutSequence<Integer, Integer>(layout, Frozen.NONE, Frozen.NONE, zone, Matrix.TYPE_CELLS);
    seq.init();
    assertTrue(seq.next());
    assertCell(0, 0, 1, 1, 33, 33);
    assertTrue(seq.next());
    assertCell(2, 0, 35, 1, 16, 16);
    assertTrue(seq.next());
    assertCell(2, 1, 35, 18, 16, 16);
    assertTrue(seq.next());
    assertCell(0, 2, 1, 35, 50, 16);
    assertFalse(seq.next());
  }

  @Test
  public void cellSequence3x3Merge0122_2201() throws Exception {
    layoutX.body.setCount(3);
    layoutY.body.setCount(3);
    zone.setMerged(0, 1, 2, 2);
    zone.setMerged(2, 2, 0, 1);
    layout.compute();
    seq = new MatrixLayoutSequence<Integer, Integer>(layout, Frozen.NONE, Frozen.NONE, zone, Matrix.TYPE_CELLS);
    seq.init();
    assertTrue(seq.next());
    assertCell(0, 0, 1, 1, 16, 16);
    assertTrue(seq.next());
    assertCell(1, 0, 18, 1, 16, 16);
    assertTrue(seq.next());
    assertCell(2, 0, 35, 1, 16, 33);
    assertTrue(seq.next());
    assertCell(0, 1, 1, 18, 16, 16);
    assertTrue(seq.next());
    assertCell(1, 1, 18, 18, 16, 16);
    assertTrue(seq.next());
    assertCell(0, 2, 1, 35, 33, 16);
    assertTrue(seq.next());
    assertCell(2, 2, 35, 35, 16, 16);
    assertFalse(seq.next());
  }

  private void assertCell(int indexX, int indexY, int distanceX, int distanceY, int widthX, int widthY) {
    try {
      assertEquals(indexX, seq.indexX.intValue());
      assertEquals(indexY, seq.indexY.intValue());
      assertEquals(distanceX, seq.boundX.distance);
      assertEquals(distanceY, seq.boundY.distance);
      assertEquals(widthX, seq.boundX.width);
      assertEquals(widthY, seq.boundY.width);
    }
    catch (AssertionError e) {
      throw new AssertionError(MessageFormat.format(
          "Expected [{0}, {1}, {2}, {3}, {4}, {5}], but was [{6}, {7}, {8}, {9}, {10}, {11}]",
          indexX, indexY, distanceX, distanceY, widthX, widthY,
          seq.indexX.intValue(), seq.indexY.intValue(),
          seq.boundX.distance, seq.boundY.distance,
          seq.boundX.width, seq.boundY.width));
    }
  }
}
