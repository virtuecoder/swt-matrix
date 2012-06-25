package pl.netanel.swt.matrix;

import java.util.Map;

public class MatrixLayoutLineSequenceX<X extends Number, Y extends Number> implements Sequence {
  // Sequence current state
  X indexX;
  Y indexY;
  public Bound boundX, boundY;

  // Private
  private final ZoneCore<X, Y> zone;
  private AxisLayoutSequence<X> seqX;
  private AxisLayoutSequence<Y> seqY;
  private boolean empty;

  private Map<CellExtent<X, Y>, Bound[]> cache;

  private CellExtent<X, Y> lastExtent;
  private CellExtent<X, Y> spanSequence;

  /**
   * @param layout
   * @param frozen
   * @param zone
   */
  public MatrixLayoutLineSequenceX(MatrixLayout<X, Y> layout, Frozen frozenX, Frozen frozenY,
      ZoneCore<X, Y> zone) {
    this.zone = zone;
    seqX = layout.layoutX.lineSequence(frozenX, zone.sectionX);
    seqY = layout.layoutY.lineSequence(frozenY, zone.sectionY);
    cache = layout.cellMergingCache.get(frozenX.ordinal()).get(frozenY.ordinal()).bounds;
  }

  @Override
  public void init() {
    seqX.init();
    seqY.init();
    empty = !seqY.next();
  }

  @Override
  public boolean next() {
    boolean next = seqX.next();
    if (!next) return false;
    spanSequence = zone.cellMerging.getSpanSequence(seqX.index, null);

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
