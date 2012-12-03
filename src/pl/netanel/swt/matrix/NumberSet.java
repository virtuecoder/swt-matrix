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
 * Set of number extents that don't overlap. They don't have to be sorted.
 */
public interface NumberSet<N extends Number> {
  /**
   * Returns <tt>true</tt> if the receiver contains no items.
   * Otherwise <code>false</code> is returned.
   * @return <tt>true</tt> if this contains contains no items
   */
  boolean isEmpty();
  /**
   * Returns the number of items in the receiver.
   * @return the number of items in the receiver
   */
  N getCount();

  /**
   * Returns the number of items in the receiver between the values
   * <code>start</code> and <code>end</code> inclusively.
   *
   * @return the number of items in the receiver between the values
   * <code>start</code> and <code>end</code>
   */
  N getCount(N start, N end);

  /**
   * Adds an extent of numbers between start end end to the set. Adding extents
   * performs better then adding single numbers.
   *
   * @param start first index of the range of items
   * @param end last index of the range of items
   * @return <code>true</code> if this operation has changed the set content.
   */
  boolean add(N start, N end);

  /**
   * Adds the given number to the set.
   *
   * @param number to be added
   * @return <code>true</code> if this operation has changed the set content.
   */
  boolean add(N number);

  /**
   * Adds the given extent of numbers to the set. Adding extents performs better
   * then adding single numbers.
   *
   * @param start first index of the range of items
   * @param end last index of the range of items
   * @return <code>true</code> if this operation has changed the set content.
   */
  boolean add(Extent<N> extent);

  /**
   * Adds the content of the given set to this set.
   *
   * @param set to be added
   * @return <code>true</code> if this operation has changed the set content.
   */
  boolean addAll(NumberSet<N> set);

  /**
   * Removes an extent of numbers between start end end from this set. Removing extents
   * performs better then adding single numbers.
   *
   * @param start first index of the range of items
   * @param end last index of the range of items
   * @return <code>true</code> if this operation has changed the set content.
   */
  boolean remove(N start, N end);

  /**
   * Removes the given number from this set.
   *
   * @param number to remove.
   * @return <code>true</code> if this operation has changed the set content.
   */
  boolean remove(N number);

  /**
   * Removes the given extent of numbers from this set. Removing extents
   * performs better then adding single numbers.
   *
   * @param start first index of the range of items
   * @param end last index of the range of items
   * @return <code>true</code> if this operation has changed the set content.
   */
  boolean remove(Extent<N> extent);

  /**
   * Removes the content of the given set from this set.
   *
   * @param set to remove
   * @return <code>true</code> if this operation has changed the set content.
   */
  boolean removeAll(NumberSet<N> set);

  /**
   * Inserts numbers before the given target number increasing the following numbers
   * by the amount of the inserted numbers.
   * <p>
   * Numbers are inserted before the given target number or at the end if the target equals
   * to the (last number in the set) + 1.
   * <p>
   *
   * @param target the number before which the new numbers are inserted
   * @param count the number of items to insert
   *
   * @throws IndexOutOfBoundsException if target or count is <code>null</code>
   * @throws IndexOutOfBoundsException if target is out of 0 ...
   *         {@link #getCount()} bounds
   * @throws IllegalArgumentException if start is greater then end
   *
   */
  void insert(N target, N count);

  /**
   * Removes a range of numbers decreasing the following numbers
   * by the amount of the removed items.
   *
   * @param start first index of the range of items
   * @param end last index of the range of items
   *
   * @throws IndexOutOfBoundsException if start or end is <code>null</code>
   * @throws IndexOutOfBoundsException if start or end is negative
   * @throws IllegalArgumentException if start is greater then end
   *
   */
  void delete(N start, N end);

  /**
   * Removes a range of numbers decreasing the following numbers by 1.
   *
   * @param number number to be deleted
   *
   * @throws IndexOutOfBoundsException if number is <code>null</code>
   * @throws IndexOutOfBoundsException if number is negative
   * @throws IllegalArgumentException if start is greater then end
   *
   */
  void delete(N number);

  /**
   * Removes a range of numbers decreasing the following numbers
   * by the amount of the removed items.
   *
   * @param extent first index of the range of items to remove
   *
   * @throws IndexOutOfBoundsException if extent is <code>null</code>
   */
  void delete(Extent<N> extent);

  /**
   * Removes all of the numbers from this set.
   * The set will be empty after this call returns.
   */
  void clear();

  /**
   * Returns <code>true</code> if this set contains the given number.
   * @param number number whose presence in this set is to be tested
   * @return <code>true</code> if this set contains the given number
   */
  boolean contains(N number);

  /**
   * Returns <code>true</code> if this set contains all the numbers from the given range inclusively.
   * @param start first index of the range of items
   * @param end last index of the range of items
   * @return <code>true</code> if this set contains the given range of numbers
   */
  boolean contains(N start, N end);

  /**
   * Returns <code>true</code> if this set contains all the numbers from the given range inclusively.
   * @param extent extent of numbers whose presence in this set is to be tested
   * @return <code>true</code> if this set contains the given range of numbers
   */
  boolean contains(Extent<N> extent);

  /**
   * Returns an extent iterator according to the given query.
   * @param query parameters changing the default iteration which is forward over all items. See {@link Query}.
   * <code>null</code> value means the default query.
   * @return extent iterator according to the given query
   */
  Iterator<Extent<N>> extentIterator(Query<N> query);

  /**
   * Returns an number iterator according to the given query.
   * @param query parameters changing the default iteration which is forward over all items. See {@link Query}.
   * <code>null</code> value means the default query.
   * @return number iterator according to the given query
   */
  Iterator<N> numberIterator(Query<N> query);

  /**
   * Returns a copy of this set.
   * @return copy of this set
   */
  NumberSet<N> copy();

  /**
   * Returns a better performing but less user friendly implementation
   * for this number set that is more loop efficient:
   * <ul>
   * <li>does not check validity of the method arguments</li>
   * <li>does not mark the layout as required computing on every method call,
   * instead relying on the client to call {@link Matrix#refresh()}</li>
   * </ul>
   * @return a better performing but less user friendly implementation for this set
   */
  NumberSet<N> getUnchecked();

  /**
   * Specifies options for iteration over extent set.
   */
  public static class Query<N extends Number> {

    NumberSetCore<N> set;
    N origin;
    N finish;
    boolean backward;
    NumberSetCore<N> subtract;

    /**
     * Defines the scope of iteration.
     *
     * @param start first item to start the sequence from. <code>null</code> value means from the very beginning of the set.
     * @param end last item to end the sequence with. <code>null</code> value means to the very end of the set.
     * @return this object
     */
    public Query<N> scope(N start, N end) {
      this.origin = start;
      this.finish = end;
      return this;
    }

//    /**
//     * Indicates backward order of iteration compared to the natural order of the set.
//     * @return
//     */
//    public SequenceQuery<N> backward() {
//      this.backward = true;
//      return this;
//    }

//    public SequenceQuery<N> subtract(NumberSet<N> set) {
//      this.subtract = set;
//      return this;
//    }

//    public SequenceBuilder<N> intersect(NumberSet<N> set) {
//      this.intersect = set;
//      return this;
//    }

  }

}
