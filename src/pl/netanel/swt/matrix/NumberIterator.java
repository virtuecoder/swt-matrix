/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import pl.netanel.util.ImmutableIterator;

class NumberIterator<N extends Number> extends ImmutableIterator<N> {

  private final NumberSequence<N> seq;
  private boolean hasNext;

  NumberIterator(NumberSequence<N> seq) {
    super();
    this.seq = seq;
    seq.init();
    hasNext = seq.next();
  }

  @Override
  public boolean hasNext() {
    return hasNext;
  }

  @Override
  public N next() {
    N current = seq.index();
    hasNext = seq.next();
    return current;
  }
}
