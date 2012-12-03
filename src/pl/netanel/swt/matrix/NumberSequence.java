/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import java.util.Iterator;

import pl.netanel.util.ImmutableIterator;

/**
 * Number sequence.
 */
class NumberSequence<N extends Number> implements ObjectSequence<N>, Iterable<N> {

  MutableNumber<N> index;
  private final ExtentSequence<N> seq;
  private boolean more;
  private Math<N> math;

  public NumberSequence(Math<N> math, ExtentSequence<N> seq) {
    this.math = math;
    this.seq = seq;
  }

  @Override
  public void init() {
    seq.init();
    more = seq.next();
    if (more) {
      this.index = math.create(seq.getStart()).decrement();
    }
    else {
      index = null;
    }
  }

  @Override
  public boolean next() {
    if (!more) return false;
    if (math.compare(index.increment(), seq.getEnd()) > 0) {
      more = seq.next();
      if (!more) {
        index = null;
        return false;
      }
      index.set(seq.getStart());
    }
    return true;
  }

  public boolean more() {
    return seq.more() || math.compare(index, seq.getEnd()) <= 0;
  }

  @Override
  public N item() {
    return index.getValue();
  }

  @Override
  public Iterator<N> iterator() {
    return new ImmutableIterator<N>() {
      private boolean hasMore;
      private N pending, current;
      {
        init();
        stepAhead();
      }
      private void stepAhead() {
        hasMore = NumberSequence.this.next();
        if (hasMore) pending = item();
      }
      @Override
      public boolean hasNext() {
        return hasMore;
      }

      @Override
      public N next() {
        if (!hasMore) throw new IllegalStateException("No more items to iterate");
        current = pending;
        stepAhead();
        return current;
      }
    };
  }

}
