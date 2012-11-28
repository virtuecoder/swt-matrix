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
 * Set of extents that are sorted and don't overlap.
 */
public interface ExtentSet<N extends Number> {
  boolean isEmpty();
  N getCount();
  N getCount(N start, N end);

  boolean add(N start, N end);
  boolean add(N number);
  boolean add(Extent<N> extent);
  boolean addAll(ExtentSet<N> set);
  boolean remove(N start, N end);
  boolean remove(N number);
  boolean remove(Extent<N> extent);
  boolean removeAll(ExtentSet<N> set);
  void insert(N target, N count);
  void delete(N start, N end);
  void delete(N number);
  void delete(Extent<N> extent);
  void clear();

  boolean contains(N number);
  boolean contains(N start, N end);
  boolean contains(Extent<N> extent);

  Iterator<Extent<N>> getExtents(SequenceQuery<N> query);
  Iterator<N> getNumbers(SequenceQuery<N> query);

  ExtentSet<N> copy();
}
