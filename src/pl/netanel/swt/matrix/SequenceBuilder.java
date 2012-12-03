/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

/**
 * Build sequences of numbers and extents.
 */
class SequenceBuilder<N extends Number> {
  private NumberSetCore<N> set;
  private N origin;
  private N finish;
  private boolean backward;
  private NumberSetCore<N> subtract;

  public SequenceBuilder(NumberSetCore<N> set) {
    this.set = set;
  }

  public SequenceBuilder<N> origin(N origin) {
    this.origin = origin;
    return this;
  }

  public SequenceBuilder<N> finish(N finish) {
    this.finish = finish;
    return this;
  }

  public SequenceBuilder<N> backward() {
    this.backward = true;
    return this;
  }

  public SequenceBuilder<N> subtract(NumberSetCore<N> set) {
    this.subtract = set;
    return this;
  }

//  public SequenceBuilder<N> intersect(NumberSet<N> set) {
//    this.intersect = set;
//    return this;
//  }


  /*------------------------------------------------------------------------
   * Constructors
   */

  public ExtentSequence<N> extents() {
    ExtentSequence<N> seq;
    if (subtract != null) {
      NumberSetCore<N> copy = set.copy();
      copy.removeAll(subtract);
      seq = backward ?
          new ExtentSequence.Backward<N>(copy) :
          new ExtentSequence.Forward<N>(copy);
    }
    else {
      seq = backward ?
          new ExtentSequence.Backward<N>(set) :
          new ExtentSequence.Forward<N>(set);
    }

    if (origin != null) seq.origin(origin);
    if (finish != null) seq.finish(finish);

    return seq;
  }

  public NumberSequence<N> numbers() {
    return new NumberSequence<N>(set.math, extents());
  }
}
