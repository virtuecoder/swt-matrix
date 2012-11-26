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
class ExtentPairScopeSequence<X extends Number, Y extends Number> extends ExtentPairSequence<X, Y> {

  private X scopeStartX;
  private X scopeEndX;
  private Y scopeStartY;
  private Y scopeEndY;
  private MutableExtent<X> extentX;
  private MutableExtent<Y> extentY;

  public ExtentPairScopeSequence(CellSet<X, Y> set) {
    super(set);
  }

  @Override
  public void init() {
    if (set.isEmpty()) return;
    i = 0;
    len = set.size();
  }

  @Override
  public boolean next() {
    if (!nextInScope()) return false;
    startX = scopeStartX == null ? extentX.start() : set.mathX.max(extentX.start(), scopeStartX);
    endX = scopeEndX == null ? extentX.end() : set.mathX.min(extentX.end(), scopeEndX);
    startY = scopeStartY == null ? extentY.start() : set.mathY.max(extentY.start(), scopeStartY);
    endY = scopeEndY == null ? extentY.end() : set.mathY.min(extentY.end(), scopeEndY);

    i++;
    return true;
  }

  private boolean nextInScope() {
    for (;i < len; i++) {
      extentX = set.itemsX.get(i);
      extentY = set.itemsY.get(i);

      if (
          (scopeStartX == null || set.mathX.compare(scopeStartX, extentX.end()) <= 0) &&
          (scopeEndX == null || set.mathX.compare(scopeEndX, extentX.start()) >= 0) &&
          (scopeStartY == null || set.mathY.compare(scopeStartY, extentY.end()) <= 0) &&
          (scopeEndY == null || set.mathY.compare(scopeEndY, extentY.start()) >= 0))
//         set.mathY.intersects(extentY.start(), extentY.end(), scopeStartY, scopeEndY))
      {
        return true;
      }
    }
    return false;
  }

  @Override
  public X getStartX() {
    return startX;
  }

  @Override
  public X getEndX() {
    return endX;
  }

  @Override
  public Y getStartY() {
    return startY;
  }

  @Override
  public Y getEndY() {
    return endY;
  }

  @Override
  public Iterator<CellExtent<X, Y>> iterator() {
    return null;
  }

  public ExtentPairScopeSequence<X, Y> scope(X scopeStartX, X scopeEndX, Y scopeStartY, Y scopeEndY) {
    this.scopeStartX = scopeStartX;
    this.scopeEndX = scopeEndX;
    this.scopeStartY = scopeStartY;
    this.scopeEndY = scopeEndY;
    return this;
  }

}
