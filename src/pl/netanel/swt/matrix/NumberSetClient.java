/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import java.util.Iterator;


class NumberSetClient<N extends Number> implements NumberSet<N> {
  NumberSetCore<N> core;

  public NumberSetClient(NumberSetCore<N> core) {
    this.core = core;
  }

  @Override
  public int hashCode() {
    return core.hashCode();
  }

  @Override
  public String toString() {
    return core.toString();
  }

  public boolean contains(N n) {
    return core.contains(n);
  }

  public boolean contains(N start, N end) {
    return core.contains(start, end);
  }

  public boolean contains(MutableExtent<N> extent) {
    return core.contains(extent);
  }

  public boolean contains(Extent<N> extent) {
    return core.contains(extent);
  }

  public boolean add(N n) {
    return core.add(n);
  }

  @Override
  public boolean equals(Object obj) {
    return core.equals(obj);
  }

  public boolean add(MutableExtent<N> e) {
    return core.add(e);
  }

  public boolean add(N start, N end) {
    return core.add(start, end);
  }

  public boolean addAll(NumberSet<N> set) {
    return core.addAll(set);
  }

  public MutableNumber<N> getMutableCount() {
    return core.getMutableCount();
  }

  public MutableNumber<N> getMutableCount(N start, N end) {
    return core.getMutableCount(start, end);
  }

  public N getCount(N start, N end) {
    return core.getCount(start, end);
  }

  public void clear() {
    core.clear();
  }

  public void replace(NumberSetCore<N> set) {
    core.replace(set);
  }

  public boolean isEmpty() {
    return core.isEmpty();
  }

  public NumberSetCore<N> copy() {
    return core.copy();
  }

  public void change(N start, N end, boolean add) {
    core.change(start, end, add);
  }

  public int getExtentIndex(N n) {
    return core.getExtentIndex(n);
  }

  public N firstExcluded(N n, int direction) {
    return core.firstExcluded(n, direction);
  }

  public void delete(N start, N end) {
    core.delete(start, end);
  }

  public void insert(N target, N count) {
    core.insert(target, count);
  }

  public Iterator<N> numberIterator() {
    return core.numberIterator();
  }

  public Iterator<Extent<N>> extentIterator() {
    return core.extentIterator();
  }

  public void addListener(ContentChangeListener<N> listener) {
    core.addListener(listener);
  }

  public void removeListener(ContentChangeListener<N> listener) {
    core.removeListener(listener);
  }

  public N getCount() {
    return core.getCount();
  }

  public boolean add(Extent<N> extent) {
    return core.add(extent);
  }

  public boolean remove(Extent<N> extent) {
    return core.remove(extent);
  }

  public boolean remove(N n) {
    return core.remove(n);
  }

  public boolean remove(MutableExtent<N> e) {
    return core.remove(e);
  }

  public boolean remove(N start, N end) {
    return core.remove(start, end);
  }

  public boolean removeAll(NumberSet<N> set) {
    return core.removeAll(set);
  }

  public void delete(N number) {
    core.delete(number);
  }

  public void delete(Extent<N> extent) {
    core.delete(extent);
  }

  public Iterator<Extent<N>> extentIterator(Query<N> query) {
    return core.extentIterator(query);
  }

  public Iterator<N> numberIterator(Query<N> query) {
    return core.numberIterator(query);
  }

  @Override
  public NumberSet<N> getUnchecked() {
    return core;
  }


}
