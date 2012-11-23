/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

/**
 * Sequence of objects of type T.
 * @param T type of objects in the sequence.
 */
public interface ObjectSequence<T> extends Sequence {
  /**
   * Returns current item of the sequence.
   * @return current item of the sequence.
   */
  T item();
}
