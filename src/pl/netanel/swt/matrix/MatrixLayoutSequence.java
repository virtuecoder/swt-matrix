package pl.netanel.swt.matrix;

import java.util.Map;

public class MatrixLayoutSequence<X extends Number, Y extends Number> implements Sequence {
  // Sequence current state
  X indexX;
  Y indexY;
  public Bound boundX, boundY;

  // Private
//  private final MatrixLayout<X, Y> layout;
  private final ZoneCore<X, Y> zone;
  private AxisLayoutSequence<X> seqX;
  private AxisLayoutSequence<Y> seqY;
  private boolean empty;

  private Map<CellExtent<X, Y>, Bound[]> cache;

  private CellExtent<X, Y> lastExtent;

  /**
   * @param layout
   * @param frozen
   * @param zone
   * @param type Matrix.TYPE_CELLS or Matrix.TYPE_LINES
   */
  public MatrixLayoutSequence(MatrixLayout<X, Y> layout, Frozen frozenX, Frozen frozenY, ZoneCore<X, Y> zone, int type) {
    this.zone = zone;
    seqX = layout.layoutX.sequence(frozenX, zone.sectionX, type);
    seqY = layout.layoutY.sequence(frozenY, zone.sectionY, type);
    cache = layout.mergingCache.get(frozenX.ordinal()).get(frozenY.ordinal()).bounds;
  }

  @Override
  public void init() {
    seqX.init();
    seqY.init();
    empty = !seqY.next();
  }

  @Override
  public boolean next() {
    if (empty) return false;
    if (seqX.next()) {
      return setState();
    }
    if (seqY.next()) {
      seqX.init();
      if (seqX.next()) {
        return setState();
      }
    }
    return false;
  }

  private boolean setState() {
    CellExtent<X, Y> extent;
    do {
      extent = zone.cellMerging.getSpan(seqX.getIndex(), seqY.getIndex());
      if (extent == null) {
        indexX = seqX.index;
        indexY = seqY.index;
        boundX = seqX.bound;
        boundY = seqY.bound;
        return true;
      }
      if (!seqX.next()) {
        if (!seqY.next()) return false;
        seqX.init();
        continue;
      }
    }
    while (extent.equals(lastExtent));
    lastExtent = extent;
    //int compareX = zone.sectionX.math.compare(mergeExtent.startX, mergeExtent.endX, seqX.getIndex());
    indexX = extent.startX;
    indexY = extent.startY;
    Bound[] bounds = cache.get(extent);
    boundX = bounds[0];
    boundY = bounds[1];
    return true;
  }

}
