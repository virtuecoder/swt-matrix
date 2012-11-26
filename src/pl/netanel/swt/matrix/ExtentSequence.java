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
 * Iterates over extents of the given set.
 */
abstract class ExtentSequence<N extends Number> implements Sequence, Iterable<Extent<N>> {

  protected final NumberSet<N> set;
  protected final Math<N> math;

  protected int i;
  protected int len;
  protected MutableExtent<N> extent;

  protected N start, end, origin, finish;
  protected int originIndex, finishIndex;

  public ExtentSequence(NumberSet<N> set) {
    this.set = set;
    this.math = set.math;
  }

  @Override
  public void init() {
    len = set.items.size();
    if (len == 0) return;

    if (origin == null && finish == null) {
      firstExtentIndex();
      return;
    }

//    // Finish
//    if (finish != null) {
//      do {
//        extent = set.items.get(i);
//        if (math.compare(extent.start.getValue(), finish) <= 0) {
//          finishIndex = i;
//          break;
//        }
//        nextExtentIndex();
//      }
//      while(!hasCompleted());
//
//      if (hasCompleted()) return;
//    }

    // Origin
    if (origin != null) {
      i = 0;
      do {
        extent = set.items.get(i);
        if (math.compare(extent.end.getValue(), origin) >= 0) {
          originIndex = i;
          break;
        }
        nextExtentIndex();
      }
      while(!hasCompleted());

      if (hasCompleted()) return;
    }
  }

  @Override
  public boolean next() {
    if (hasCompleted()) {
      start = null;
      end = null;
      return false;
    }
    extent = set.items.get(i);
    setEdges();
    nextExtentIndex();
    return true;
  }

  public boolean more() {
    return !hasCompleted();
  }

  public N getStart() {
    return start;
  }

  public N getEnd() {
    return end;
  }

  abstract void firstExtentIndex();
  abstract void lastExtentIndex();
  abstract void nextExtentIndex();
  abstract boolean hasCompleted();
  abstract void makeCompleted();
  abstract boolean setEdges();


  public ExtentSequence<N> origin(N origin) {
    this.origin = origin;
    return this;
  }

  public ExtentSequence<N> finish(N finish) {
    this.finish = finish;
    return this;
  }


  /**
   * Costs creation of extra object on each iteration step.
   */
  @Override
  public Iterator<Extent<N>> iterator() {
    return null;
  }


  public static class Forward<N extends Number> extends ExtentSequence<N> {
    public Forward(NumberSet<N> set) {
      super(set);
    }

    @Override
    void firstExtentIndex() {
      i = 0;
    }

    @Override
    void lastExtentIndex() {
      i = len - 1;
    }

    @Override
    boolean hasCompleted() {
      return i >= len;
    }

    @Override
    void nextExtentIndex() {
      i++;
    }

    @Override
    void makeCompleted() {
      i = len;
    }

    @Override
    boolean setEdges() {
      if (i == originIndex && origin != null) {
        start = math.max(origin, extent.start.getValue());
        end = extent.end();
      }
      else if (finish != null) {
        if (math.contains(extent, finish)) {
          start = extent.start();
          end = finish;
        }
        else {
          return false;
        }
      }
      else {
        start = extent.start();
        end = extent.end();
      }
      return true;
    }
  }

//  class ExtentSequence<N> extends ExtentSequenceBase<N> {
//    private int i;
//    private MutableExtent<N> extent;
//    N start, end;
//    private int len;
//
//    @Override
//    public void init() {
//      if (items.isEmpty()) return;
//      len = items.size();
//      i = 0;
//      tmp.start.set(start);
//      tmp.end.set(end);
//    }
//
//    @Override
//    public boolean next() {
//      while (i < len) {
//        extent = items.get(0);
//
//      }
//      while (math.compare(tmp.end, extent.start) < 0 ||
//          math.compare(tmp.start, extent.end) > 0) {
//        tmp
//      }
//      return false;
//    }
//
//    @Override
//    public Iterator<Extent<N>> iterator() {
//      return null;
//    }
//
//    @Override
//    public N getStart() {
//      return null;
//    }
//
//    @Override
//    public N getEnd() {
//      return null;
//    }
//
//  };
//  for (MutableExtent<N> e: items) {
//    if (math.compare(tmp.end, e.start) < 0 &&
//        math.compare(tmp.start, e.end) > 0) {
//      continue;
//    }
//    else {
//      tmp.start.max(e.start);
//      tmp.end.min(e.end);
//      return tmp;
//    }
//  }

  public static class SubtractForward<N extends Number> extends Forward<N> {
    private ExtentSequence<N> subtractSeq;

    public SubtractForward(NumberSet<N> set, NumberSet<N> subtract) {
      super(set);
      subtractSeq = new Forward<N>(subtract);
    }

    @Override
    public boolean next() {
      if (hasCompleted()) {
        start = null;
        end = null;
        return false;
      }
      extent = set.items.get(i);
      if (!setEdges()) {
        start = extent.start();
        end = extent.end();
      }
      // subtract
      if (!subtractSeq.more()) {
        subtractSeq.origin(start);
        subtractSeq.finish(end);
        subtractSeq.init();
      }
      if (subtractSeq.next()) {
        if (math.compare(subtractSeq.getStart(), start) == 0 &&
            math.compare(subtractSeq.getEnd(), end) == 0) {
          // When whole current extent should be subtracted
          nextExtentIndex();
        } else {
          start = math.min(start, subtractSeq.getStart());
          end = math.max(end, subtractSeq.getEnd());
        }
      } else {
        nextExtentIndex();
      }

      return true;
    }
  }

  public static class SubtractBackward<N extends Number> extends Forward<N> {
    public SubtractBackward(NumberSet<N> set, NumberSet<N> subtract) {
      super(set);
    }
  }


  public static class Backward<N extends Number> extends ExtentSequence<N> {
    public Backward(NumberSet<N> set) {
      super(set);
    }

    @Override
    void firstExtentIndex() {
      i = len - 1;
    }

    @Override
    void lastExtentIndex() {
      i = 0;
    }

    @Override
    boolean hasCompleted() {
      return i < 0;
    }

    @Override
    void nextExtentIndex() {
      i--;
    }

    @Override
    void makeCompleted() {
      i = -1;
    }

    @Override
    boolean setEdges() {
      if (i == originIndex && origin != null) {
        start = extent.start();
        end = origin;
      }
      else if (finish != null) {
        if (math.contains(extent, finish)) {
          start = finish;
          end = extent.end();
        }
        else {
          return false;
        }
      }
      else {
        start = extent.start();
        end = extent.end();
      }
      return true;
    }
  }

}
