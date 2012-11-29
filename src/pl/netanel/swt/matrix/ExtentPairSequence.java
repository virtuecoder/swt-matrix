/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import java.util.Iterator;

/**
 * Iterates over pairs of extents. Models two dimensional ranges iteration.
 */
class ExtentPairSequence<X extends Number, Y extends Number> implements Sequence, Iterable<CellExtent<X, Y>> {
  protected CellSet<X, Y> set;
  protected X startX, endX;
  protected Y startY, endY;
  protected int i, len;

  public ExtentPairSequence(CellSet<X, Y> set) {
    this.set = set;
  }

  @Override
  public void init() {
    if (set.isEmpty()) return;
    i = 0;
    len = set.size();
  }

  @Override
  public boolean next() {
    if (i >= len) return false;
    MutableExtent<X> extentX = set.itemsX.get(i);
    MutableExtent<Y> extentY = set.itemsY.get(i);
    startX = extentX.start();
    endX = extentX.end();
    startY = extentY.start();
    endY = extentY.end();
    i++;
    return true;
  }

  public X getStartX() {
    return startX;
  }

  public X getEndX() {
    return endX;
  }

  public Y getStartY() {
    return startY;
  }

  public Y getEndY() {
    return endY;
  }

  Math<X> getMathX() {
    return set.mathX;
  }

  Math<Y> getMathY() {
    return set.mathY;
  }


  @Override
  public Iterator<CellExtent<X, Y>> iterator() {
    return null;
  }

//  static class ExtentPairSequenceSubtract<X extends Number, Y extends Number> extends ExtentPairSequence<X, Y> {
//
//    private final CellSet<X, Y> set2;
//
//    public ExtentPairSequenceSubtract(CellSet<X, Y> set1, CellSet<X, Y> set2) {
//      super(set1);
//      this.set2 = set2;
//    }
//
//  }

}
