/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import pl.netanel.util.ImmutableIterator;


/**
 * Represents an range of numbers between start and end values inclusive.
 * <p>
 * Instances of this class are immutable.
 *
 * @param <N> specifies the indexing class for the receiver
 */
public class Extent<N extends Number> implements Iterable<N> {
  final N start, end;

  /**
   * Creates extent of numbers between start and end values inclusively.
   * Arguments are validated.
   * <p>
   * <code>start</code> and <code>end</code>
   * numbers are item indexes in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   *
   * @param start first index of the range of items
   * @param end last index of the range of items
   * @return new instance of this class
   *
   * @throws IllegalArgumentException if start or end is <code>null</code>
   * @throws IllegalArgumentException if start is greater then end
   * @throws IndexOutOfBoundsException if start or end is negative
   */
  public static <N extends Number> Extent<N> create(N start, N end) {
    Math.checkRange(start, end);
    return new Extent<N>(start, end);
  }

  /**
   * Creates extent of numbers between start and end values inclusively
   * without arguments checking.
   * <p>
   * <code>start</code> and <code>end</code>
   * numbers are item indexes in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   *
   * @param start first index of the range of items
   * @param end last index of the range of items
   * @return new instance of this class
   *
   */
  public static <N extends Number> Extent<N> createUnchecked(N start, N end) {
    return new Extent<N>(start, end);
  }

  private Extent(N start, N end) {
    this.start = start;
    this.end = end;
  }

  /**
   * Returns first index of the range of items.
   * @return first index of the range of items
   */
  public N getStart() {
    return start;
  }

  /**
   * Returns the last index of the range of items.
   * @return the last index of the range of items
   */
  public N getEnd() {
    return end;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((end == null) ? 0 : end.hashCode());
    result = prime * result + ((start == null) ? 0 : start.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) { return true; }
    if (obj == null) { return false; }
    if (!(obj instanceof Extent)) { return false; }
    @SuppressWarnings("rawtypes")
    Extent other = (Extent) obj;
    if (other.start.getClass() != start.getClass()) return false;
    if (!end.equals(other.end)) { return false; }
    else if (!start.equals(other.start)) { return false; }
    return true;
  }

  @Override
  public String toString() {
    return start.toString() + "-" + end;
  }

  @Override
  public Iterator<N> iterator() {
    return new ImmutableIterator<N>() {
      @SuppressWarnings("unchecked")
      Math<N> math = (Math<N>) Math.getInstance(start.getClass());
      MutableNumber<N> index = math.create(start);

      @Override
      public boolean hasNext() {
        return math.compare(index, end) <= 0;
      }

      @Override
      public N next() {
        N result = index.getValue();
        index.increment();
        return result;
      }
    };
  }


  /**
   * Returns extent iterator for the given collection of numbers.
   * @param coll
   * @return extent iterator for the given collection
   */
  public static <N extends Number> Iterator<Extent<N>> extentIterator(final Collection<N> coll) {
    return new ImmutableIterator<Extent<N>>() {
      Iterator<N> it = coll.iterator();

      @Override
      public boolean hasNext() {
        return it.hasNext();
      }

      @Override
      public Extent<N> next() {
        // TODO low: accumulate values in extents
        N n = it.next();
        return new Extent<N>(n, n);
      }
    };
  }

  public static <N extends Number> Iterator<N> numberIterator(
      final Math<N> math, final List<MutableExtent<N>> items) {
    return new ImmutableIterator<N>() {
      NumberSequence<N> seq = new NumberSequence<N>(math, items);
      private boolean hasNext;
      {
        seq.init();
        hasNext = seq.next();
      }
      @Override
      public boolean hasNext() {
        return hasNext;
      }

      @Override
      public N next() {
        N next = hasNext ? seq.index() : null;
        hasNext = seq.next();
        return next;
      }
    };
  }

}
