/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import pl.netanel.swt.matrix.MatrixLayout.MergeCache;


/**
 * Iterates over cells of a matrix considering the merged ones.
 * <p>
 * Cells that are merged are marked beforehand by {@link MatrixLayout#computeMerging()}.
 *
 * @author jacek.p.kolodziejczyk@gmail.com
 * @created 25-06-2012
 */
class MatrixLayoutCellSequence<X extends Number, Y extends Number> implements Sequence {
  // Sequence current state
  X indexX;
  Y indexY;
  Bound boundY;
  Bound boundX;

  // Private
//  private final MatrixLayout<X, Y> layout;
  private MergeCache<X, Y> cache;
  private int i;
  private final ZoneCore<X, Y> zone;
  private AxisItem<X> itemX;
  private AxisItem<Y> itemY;

  /**
   * @param layout
   * @param frozen
   * @param zone
   * @param type Matrix.TYPE_CELLS or Matrix.TYPE_LINES
   */
  public MatrixLayoutCellSequence(MatrixLayout<X, Y> layout, Frozen frozenX, Frozen frozenY,
      ZoneCore<X, Y> zone) {
    this.zone = zone;
//    seqX = layout.layoutX.cellSequence(frozenX, zone.sectionX);
//    seqY = layout.layoutY.cellSequence(frozenY, zone.sectionY);
    cache = layout.mergeCache.get(Frozen.getIndex(frozenX, frozenY));
  }

  @Override
  public void init() {
    i = -1;
  }

  @Override
  public boolean next() {
    while (++i < cache.itemsX.size()) {
      itemX = cache.itemsX.get(i);
      itemY = cache.itemsY.get(i);
      if (
          itemX.section.equals(zone.sectionX) &&
          itemY.section.equals(zone.sectionY) ) break;
    }

    if (i == cache.itemsX.size()) return false;

    indexX = itemX.index;
    indexY = itemY.index;
    boundX = cache.boundsX.get(i);
    boundY = cache.boundsY.get(i);
    return true;
  }
}
