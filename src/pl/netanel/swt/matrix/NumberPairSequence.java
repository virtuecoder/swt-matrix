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
 * Iterates over pairs of numbers. Models matrix cells iteration.
 */
class NumberPairSequence<X extends Number, Y extends Number> implements Sequence, Iterable<Cell<X, Y>>{
  ExtentPairSequence<X, Y> seq;
  MutableNumber<X> indexX;
  MutableNumber<Y> indexY;
  private boolean more;

  public NumberPairSequence(ExtentPairSequence<X, Y> seq) {
    this.seq = seq;
  }

  @Override
  public void init() {
    seq.init();
    more = seq.next();
    if (more) {
      indexX = seq.getMathX().create(seq.getStartX()).decrement();
      indexY = seq.getMathY().create(seq.getStartY());
    }
    else {
      indexX = null;
      indexY = null;
    }
  }

  @Override
  public boolean next() {
    if (!more) return false;
    if (seq.getMathX().compare(indexX.increment(), seq.getEndX()) > 0) {
      if (seq.getMathY().compare(indexY.increment(), seq.getEndY()) > 0) {
        more = seq.next();
        if (!more) {
          indexX = null;
          indexY = null;
          return false;
        }
      }
      indexX.set(seq.getStartX());
    }
    return true;
  }


  public X getX() {
    return indexX.getValue();
  }

  public Y getY() {
    return indexY.getValue();
  }

  @Override
  public Iterator<Cell<X, Y>> iterator() {
    return null;
  }

}
