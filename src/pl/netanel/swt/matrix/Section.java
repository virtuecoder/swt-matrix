/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import java.util.Iterator;

import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

/**
 * Section represents a continuous segment of a matrix {@link Axis},
 * for example a header, body or footer.
 * It contains a number of items indexed by the instances of a class
 * specified by the <code>&lt;N extends {@link Number}&gt;</code>
 * type parameter.<br>
 *
 * Item width consists of the line width and the cell width -
 * the line precedes the cell. The last line index equals to getCount().
 * If the item is moved then both the cell and the preceding line are moved.
 * <p>
 * Item attributes include cell width, line width, moveable, resizable,
 * hideable, hidden, selected. To optimize data storage of those attributes one
 * value can be set for a range of items enclosed between the start and end
 * items, for example setCellWidth(start, end, width).
 * Also default values can be defined to save memory. If 1000000 items
 * have the same width, then its a waste to store 1000000 ints with the same
 * values. An example of such function: setDefaultCellWidth(width).
 * <p>
 * Section visibility and focus item can be enabled or disabled.
 *
 * @param <N> specifies the indexing class for the receiver
 */
public interface Section<N extends Number> {

  /**
   * Returns the <code>Class&lt;Number&gt;</code> instance
   * that is used to index the section cells and lines.
   *
   * @return the <code>Class&lt;Number&gt;</code> instance
   * that is used to index the section cells and lines
   */
  Class<N> getIndexClass();

  /**
   * Returns a better performing but less user friendly implementation
   * for this section that is more loop efficient:
   * <ul>
   * <li>does not check validity of the method arguments</li>
   * <li>does not mark the layout as required computing on every method call,
   * instead relying on the client to call {@link Matrix#refresh()}</li>
   * </ul>
   * It may be useful for loop optimization, for example inside of
   * {@link Painter#paint(int, int, int, int)}
   * method.
   * @return a better performing but less user friendly implementation for this section
   */
  Section<N> getUnchecked();

  /**
   * Specifies the number of section items.
   *
   * @param count the new count of the receiver's items
   * @see #getCount()
   * @throws NullPointerException if the count is <tt>null</tt> or less then <tt>0</tt>.
   */
  void setCount(N count);

  /**
   * Returns the number of items in the receiver.
   * @return the number of items in the receiver
   * @see #setCount(Number)
   */
  N getCount();

  /**
   * Returns <tt>true</tt> if the receiver contains no items.
   * Otherwise <code>false</code> is returned.
   * @return <tt>true</tt> if this contains contains no items
   */
  boolean isEmpty();

  /**
   * Marks the receiver as visible if the argument is <tt>true</tt>,
   * and marks it invisible otherwise.
   *
   * @param visible the new visibility state
   */
  void setVisible(boolean visible);

  /**
   * Returns <tt>true</tt> if the receiver is visible.
   * Otherwise, <tt>false</tt> is returned.
   *
   * @return the receiver's visibility state
   */
  boolean isVisible();

  /**
   * Enables focus item navigation in the receiver if the argument is <tt>true</tt>,
   * and disables it invisible otherwise.
   *
   * @param enabled the new focus item enablement state
   */
  void setFocusItemEnabled(boolean enabled);

  /**
   * Returns <tt>true</tt> if the focus item navigation is enabled in the receiver.
   * Otherwise, <tt>false</tt> is returned.
   *
   * @return the receiver's focus item enablement state
   */
  boolean isFocusItemEnabled();

//  /**
//  * Returns the maximum number of cells that can be merged along this axis.
//  * Merging higher number of cells will throws an exception.
//  * The reason for limitation of merged cells is to avoid performance degradation
//  * when summing sizes of large merged extents during layout computation.
//  * @return the maxSpan
//  * @see {@link #setMaxSpan(Number)}
//  */
// public N getMaxSpan();
//
// /**
//  * Sets the maximum number of cells that can be merged along this axis.
//  * The reason for limitation of merged cells is to avoid performance degradation
//  * when summing sizes of large merged extents during layout computation.
//  * <p>
//  * The default number is 1000.
//  * @param maxSpan the maximum number of cells that can be merged.
//  * @see {@link #getMaxSpan()}
//  */
// public void setMaxSpan(N maxSpan);



  /**
   * Sets the default width of cells in this section to the given value. Cell
   * width excludes the width of lines. Negative argument values are ignored.
   * <p>
   * Default value allows to save storage memory of the width attribute if many
   * cells share the same value and the newly created items to have the default
   * value automatically.
   * <p>
   * If the <code>width</code> is lower then 0 then the method does nothing.
   *
   * @param width new value for default width.
   *
   * @throws IllegalArgumentException if the width is lower then
   *  the minimal cell width of the axis
   */
  void setDefaultCellWidth(int width);

  /**
   * Returns the default cell width of the receiver's items.
   * Cell width excludes the width of lines.
   * @return default width of cells in this
   */
  int getDefaultCellWidth();

  /**
   * Sets default width of lines in this section to the given value. Negative
   * argument values are ignored.
   * <p>
   * Default value allows to save storage memory of the width attribute if many
   * lines share the same value and the newly created items to have the default
   * value automatically.
   * <p>
   * If the <code>width</code> is lower then 0 then the method does nothing.
   *
   * @param width new value for default width.
   */
  void setDefaultLineWidth(int width);

  /**
   * Returns the default line width of the receiver's items.
   * @return default width of lines in this
   */
  int getDefaultLineWidth();

  /**
   * Returns <code>true</code> if the section items can be resized by default.
   * Otherwise, <code>false</code> is returned.
   *
   * @return the default resizable ability state of the section items
   */
  boolean isDefaultResizable();

  /**
   * Sets the default resize ability of the receiver's items to the given
   * argument.
   * <p>
   * Default value allows to save storage memory of the "resizable" attribute if
   * many items share the same value and the newly created items to have this
   * default value automatically.
   *
   * @param resizable the new resize ability state
   */
  void setDefaultResizable(boolean resizable);

  /**
   * Returns <code>true</code> if the section items can be moved by default.
   * Otherwise, <code>false</code> is returned.
   *
   * @return the default move ability state of the section items
   */
  boolean isDefaultMoveable();

  /**
   * Sets the default move ability of the receiver's items to the given
   * argument.
   * <p>
   * Default value allows to save storage memory of the "moveable" attribute if
   * many items share the same value and the newly created items to have this
   * default value automatically.
   *
   * @param moveable the new move ability state
   */
  void setDefaultMoveable(boolean moveable);

  /**
   * Returns <code>true</code> if the section items can be hidden by default.
   * Otherwise, <code>false</code> is returned.
   *
   * The default return value is false.
   *
   * @return the default hide ability state of the section items
   */
  boolean isDefaultHideable();

  /**
   * Sets the default hide ability of the receiver's items to the given
   * argument.
   * <p>
   * Default value allows to save storage memory of the "hideable" attribute if
   * many items share the same value and the newly created items to have this
   * default value automatically.
   *
   * @param hideable the new hide ability state
   */
  void setDefaultHideable(boolean hideable);

  /**
   * Sets the cell width for a range of items.
   * <p>
   * <code>start</code> and <code>end</code>
   * numbers are item indexes in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   *
   * @param start first index of the range of items
   * @param end last index of the range of items
   * @param width the new cell width
   *
   * @throws IllegalArgumentException if start or end is <code>null</code>
   * @throws IllegalArgumentException if start is greater then end
   * @throws IndexOutOfBoundsException if start or end is out of 0 ...
   *         {@link #getCount()}-1 bounds
   *
   * @see #setHideable(Number, Number, boolean)
   */
  void setCellWidth(N start, N end, int width);

  /**
   * Sets the cell width for item with the given index in the model.
   * <p>
   * <code>index</code> is the item index in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   * <p>
   * If the <code>width</code> is lower then 0 then the method does nothing.
   * <p>
   * Cell width for a range of items should be set by
   * {@link #setCellWidth(Number, Number, int)} to achieve the best efficiency.
   *
   * @param index index of the item to set the cell width for
   * @param width the new cell width
   * @throws IllegalArgumentException if index is <code>null</code>
   * @throws IndexOutOfBoundsException if index is out of 0 ...
   *           {@link #getCount()}-1 bounds
   * @see #setCellWidth(Number, Number, int)
   */
  void setCellWidth(N index, int width);

  /**
   * Sets the optimal cell width that fits its content. <br>
   * Warning: for a large number of items it may take a long time to compute,
   * because of the necessity to iterate over all items.
   * <p>
   * <code>index</code> is the item index in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   * <p>
   * The width is calculated by {@link Painter#computeSize(Number, Number, int, int)}
   * of the zones to which the section belongs.
   *
   * @param index index of the item to set the cell width for
   *
   * @throws IllegalArgumentException if index is <code>null</code>
   * @throws IndexOutOfBoundsException if index is out of 0 ...
   *         {@link #getCount()}-1 bounds
   */
  void setCellWidth(N index);

  /**
   * Computes the optimal cell width that fits its content. <br>
   * Warning: for a large number of items it may take a long time to compute,
   * because of the necessity to iterate over all items.
   * <p>
   * <code>index</code> is the item index in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   * <p>
   * The width is calculated by {@link Painter#computeSize(Number, Number, int, int)}
   * of the zones to which the section belongs.
   *
   * @param index index of the item to set the cell width for
   * @return the optimal width
   *
   * @throws IllegalArgumentException if index is <code>null</code>
   * @throws IndexOutOfBoundsException if index is out of 0 ...
   *         {@link #getCount()}-1 bounds
   */
  int computeSize(N index);

  /**
   * Sets the cell width that best fits its content for all items in this section. <br>
   * Warning: for a large number of items it may take a long time to compute,
   * because of the necessity to iterate over all items.
   * <p>
   * The width is calculated by {@link Painter#computeSize(Number, Number, int, int)}
   * of the zones to which the section belongs.
   */
  void setCellWidth();

  /**
   * Returns the cell width of the item with the given index in the model.
   * If the width has not been set at this index by {@link #setCellWidth(Number)}
   * method then the default cell width is returned.
   * <p>
   * <code>index</code> is the item index in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   * <p>
   *
   * @param index the item index
   * @return the cell width at the given item index in the receiver
   *
   * @throws IllegalArgumentException if index is <code>null</code>
   * @throws IndexOutOfBoundsException if index is out of 0 ...
   *         {@link #getCount()}-1 bounds
   */
  int getCellWidth(N index);

  /**
   * Sets the line width for a range of items.
   * <p>
   * <code>start</code> and <code>end</code>
   * numbers are item indexes in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   *
   * @param start first index of the range of items
   * @param end last index of the range of items
   * @param width the new line width
   *
   * @throws IllegalArgumentException if start or end is <code>null</code>
   * @throws IndexOutOfBoundsException if start or end is out of 0 ...
   *         {@link #getCount()} bounds
   * @throws IllegalArgumentException if start is greater then end
   *
   */
  void setLineWidth(N start, N end, int width);

  /**
   * Sets the line width for item with the given index in the model.
   * <p>
   * <code>index</code> is the item index in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   * <p>
   * If the <code>width</code> is lower then 0 then the method does nothing.
   * <p>
   * Cell width for a range of items should be set by
   * {@link #setLineWidth(Number, Number, int)} to achieve the best efficiency.
   *
   * @param index index of the item to set the line width for
   * @param width the new line width
   *
   * @throws IllegalArgumentException if index is <code>null</code>
   * @throws IndexOutOfBoundsException if index is out of 0 ...
   *         {@link #getCount()} bounds
   *
   * @see #setLineWidth(Number, Number, int)
   */
  void setLineWidth(N index, int width);

  /**
   * Returns the line width of the item with the given index in the model. If
   * the width has not been set at this index by one of the <code>setLineWidth</code>
   * methods then the default line width is returned.
   * <p>
   * Line at index i is on the left side of the cell at index i. Last line to
   * the right is at index equal to section item count. <code>index</code> is
   * the item index in the model, not the visual position of the item on the
   * screen which can be altered by move and hide operations.
   * <p>
   *
   * @param index
   *          the item index
   * @return the line width at the given item index in the receiver
   *
   * @throws IllegalArgumentException
   *           if index is <code>null</code>
   * @throws IndexOutOfBoundsException
   *           if index is out of 0 ... {@link #getCount()} bounds
   */
  int getLineWidth(N index);

  /**
   * Sets the move ability for a range of items. An item that is moveable can
   * be reordered by the user by dragging the header. An item that is not
   * moveable cannot be dragged by the user but may be reordered by the
   * programmer.
   * <p>
   * <code>start</code> and <code>end</code>
   * numbers are item indexes in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   *
   * @param start first index of the range of items
   * @param end last index of the range of items
   * @param enabled the new move ability state
   * @throws IllegalArgumentException if start or end is <code>null</code>
   * @throws IndexOutOfBoundsException if start or end is out of 0 ...
   *           {@link #getCount()}-1 bounds
   * @throws IllegalArgumentException if start is greater then end
   */
  void setMoveable(N start, N end, boolean enabled);

  /**
   * Sets the move ability for the item with the given index in the model.
   * <p>
   * <code>index</code> is the item index in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   * <p>
   * Ranges of items should be set moveable by {@link #setMoveable(Number, Number, boolean)}
   * to achieve the best efficiency.
   *
   * @param index index of the item to move
   * @param enabled the new move ability state
   *
   * @throws IllegalArgumentException if index is <code>null</code>
   * @throws IndexOutOfBoundsException if index is out of 0 ...
   *         {@link #getCount()}-1 bounds
   *
   * @see #setMoveable(Number, Number, boolean)
   */
  void setMoveable(N index, boolean enabled);

  /**
   * Returns <code>true</code> if the item with the given index in the model can be moved by end user.
   * Otherwise, <code>false</code> is returned.
   * An item that is moveable can be reordered by the user by dragging the header.
   * An item that is not moveable cannot be dragged by the user but may be reordered by the programmer.
   * <p>
   * If the move ability has not been set at this index by <code>setMoveable</code>
   * method then the default cell width is returned.
   * <p>
   * <code>index</code> is the item index in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   * <p>
   *
   * @param index the item index
   * @return the move ability state at the given index
   *
   * @throws IllegalArgumentException if index is <code>null</code>
   * @throws IndexOutOfBoundsException if index is out of 0 ...
   *         {@link #getCount()}-1 bounds
   *
   */
  boolean isMoveable(N index);

  /**
   * Sets the resize ability for a range of items. An item that is resizable
   * can be resized by the user dragging the edge of the header. An item that is
   * not resizable cannot be dragged by the user but may be resized by the
   * programmer.
   * <p>
   * <code>start</code> and <code>end</code> numbers are item indexes in the
   * model, not the visual position of the item on the screen which can be
   * altered by move and hide operations.
   *
   * @param start first index of the range of items
   * @param end last index of the range of items
   * @param enabled the new resize ability state
   * @throws IllegalArgumentException if start or end is <code>null</code>
   * @throws IndexOutOfBoundsException if start or end is out of 0 ...
   *           {@link #getCount()}-1 bounds
   * @throws IllegalArgumentException if start is greater then end
   */
  void setResizable(N start, N end, boolean enabled);

  /**
   * Sets the resize ability for the item with the given index in the model.
   * <p>
   * <code>index</code> is the item index in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   * <p>
   * Ranges of items should be set resizable by {@link #setResizable(Number, Number, boolean)}
   * to achieve the best efficiency.
   *
   * @param index index of the item to resize
   * @param enabled the new resize ability state
   *
   * @throws IllegalArgumentException if index is <code>null</code>
   * @throws IndexOutOfBoundsException if index is out of 0 ...
   *         {@link #getCount()}-1 bounds
   *
   * @see #setResizable(Number, Number, boolean)
   */
  void setResizable(N index, boolean enabled);

  /**
   * Returns <code>true</code> if the item with the given index in the model can be resized by end
   * user. Otherwise, <code>false</code> is returned. An item that is resizable
   * can be resized by the user dragging the edge of the header. An item that is
   * not resizable cannot be dragged by the user but may be resized by the
   * programmer.
   * <p>
   * Returns the stored item resize ability at the given index or the default item
   * resize ability if it has not been set at this index.
   * <p>
   * <code>index</code> is the item index in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   * <p>
   *
   * @param index the item index
   * @throws IllegalArgumentException if index is <code>null</code>
   * @throws IndexOutOfBoundsException if index is out of 0 ...
   *           {@link #getCount()}-1 bounds
   * @return the move ability state at the given index
   */
  boolean isResizable(N index);

  /**
   * Sets the hide ability for a range of items.
   * An item that is hideable can be hidden by the user gesture bound to the hide command.
   * An item that is not hideable cannot be hidden by the user but may be hidden by the programmer.
   * <p>
   * <code>start</code> and <code>end</code>
   * numbers are item indexes in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   *
   * @param start first index of the range of items
   * @param end last index of the range of items
   * @param enabled the new hide ability state
   *
   * @throws IllegalArgumentException if start or end is <code>null</code>
   * @throws IndexOutOfBoundsException if start or end is out of 0 ...
   *         {@link #getCount()}-1 bounds
   * @throws IllegalArgumentException if start is greater then end
   *
   */
  void setHideable(N start, N end, boolean enabled);

  /**
   * Sets the hide ability for the item with the given index in the model.
   * <p>
   * <code>index</code> is the item index in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   * <p>
   * <p>
   * Ranges of items should be set hideable by {@link #setHideable(Number, Number, boolean)}
   * to achieve the best efficiency.
   *
   * @param index index of the item to hide
   * @param enabled the new hide ability state
   *
   * @throws IllegalArgumentException if index is <code>null</code>
   * @throws IndexOutOfBoundsException if index is out of 0 ...
   *         {@link #getCount()}-1 bounds
   *
   * @see #setHideable(Number, Number, boolean)
   */
  void setHideable(N index, boolean enabled);


  /**
   * Returns <code>true</code> if the item with the given index in the model can be hidden by end user.
   * Otherwise, <code>false</code> is returned.
   * An item that is hideable can be hidden by the user gesture bound to the hide command.
   * An item that is not hideable cannot be hidden by the user but may be hidden by the programmer.
   * <p>
   * Returns the stored item hide ability at the given index
   * or the default item hide ability if it has not been set at this index.
   * <p>
   * <code>index</code> is the item index in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   * <p>
   *
   * @return the hide ability state at the given index
   *
   * @throws IllegalArgumentException if index is <code>null</code>
   * @throws IndexOutOfBoundsException if index is out of 0 ...
   *         {@link #getCount()}-1 bounds
   *
   */
  boolean isHideable(N index);

  /**
   * Sets the hiding state for a range of items.
   * <p>
   * <code>start</code> and <code>end</code>
   * numbers are item indexes in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   *
   * @param start first index of the range of items
   * @param end last index of the range of items
   * @param state the new hiding state
   *
   * @throws IllegalArgumentException if start or end is <code>null</code>
   * @throws IndexOutOfBoundsException if start or end is out of 0 ...
   *         {@link #getCount()}-1 bounds
   * @throws IllegalArgumentException if start is greater then end
   *
   */
  void setHidden(N start, N end, boolean state);

  /**
   * Sets the hiding state for the item with the given index in the model.
   * <p>
   * <code>index</code> is the item index in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   * <p>
   * Ranges of items should be set hidden by {@link #setHidden(Number, Number, boolean)}
   * to achieve the best efficiency.
   *
   * @param index index of the item to hide
   * @param state the new hiding state
   *
   * @throws IllegalArgumentException if index is <code>null</code>
   * @throws IndexOutOfBoundsException if index is out of 0 ...
   *         {@link #getCount()}-1 bounds
   *
   */
  void setHidden(N index, boolean state);


  /**
   * Returns <code>true</code> if the item with the given index in the model is hidden.
   * Otherwise, <code>false</code> is returned.
   * <p>
   * <code>index</code> is the item index in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   *
   * @param index the item index
   * @return the hiding state at the given index
   *
   * @throws IllegalArgumentException if index is <code>null</code>
   * @throws IndexOutOfBoundsException if index is out of 0 ...
   *         {@link #getCount()}-1 bounds
   *
   */
  boolean isHidden(N index);

  /**
   * Returns the number of hidden items.
   * @return the number of hidden items
   */
  N getHiddenCount();

  /**
   * Returns the set of numbers representing indexes of the hidden items.
   * <p>
   * Number that is returned by {@link Iterator#next()}
   * method is the index of the item in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   *
   * @return the set of indexes of the hidden items
   */
  NumberSet<N> getHidden();

  /**
   * Returns iterator for extents of the hidden items.
   * <p>
   * Number that is returned by {@link Iterator#next()}
   * method is the index of the item in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   *
   * @return the iterator for extents of the hidden items
   */
  Iterator<Extent<N>> getHiddenExtents();

  /**
   * Sets the selection state for a range of items.
   * <p>
   * <code>start</code> and <code>end</code>
   * numbers are item indexes in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   *
   * @param start first index of the range of items
   * @param end last index of the range of items
   * @param state the new selection state
   *
   * @throws IllegalArgumentException if start or end is <code>null</code>
   * @throws IndexOutOfBoundsException if start or end is out of 0 ...
   *         {@link #getCount()}-1 bounds
   * @throws IllegalArgumentException if start is greater then end
   *
   */
  void setSelected(N start, N end, boolean state);

  /**
   * Sets the hiding state for the item with the given index in the model.
   * <p>
   * <code>index</code> is the item index in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   * <p>
   * Ranges of items should be set selected by {@link #setSelected(Number, Number, boolean)}
   * to achieve the best efficiency.
   *
   * @param index index of the item to hide
   * @param state the new hiding state
   *
   * @throws IllegalArgumentException if index is <code>null</code>
   * @throws IndexOutOfBoundsException if index is out of 0 ...
   *         {@link #getCount()}-1 bounds
   *
   * @see #setSelected(Number, Number, boolean)
   */
  void setSelected(N index, boolean state);

  /**
   * Sets the selection state for all the items.
   *
   * @param state the new selection state
   */
   void setSelected(boolean state);

  /**
   * Returns <code>true</code> if the item with the given index in the model is selected.
   * Otherwise, <code>false</code> is returned.
   * <p>
   * <code>index</code> is the item index in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   *
   * @param index the item index
   * @return the selection state at the given index
   *
   * @throws IllegalArgumentException if index is <code>null</code>
   * @throws IndexOutOfBoundsException if index is out of 0 ...
   *         {@link #getCount()}-1 bounds
   *
   */
  boolean isSelected(N index);

  /**
   * Returns the number of selected items in this section.
   * @return the number of selected items in this section
   */
  N getSelectedCount();

  /**
   * Returns the set of numbers representing indexes of the selected items.
   * <p>
   * Number that is returned by {@link Iterator#next()}
   * method is the index of the item in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   *
   * @return the set of indexes of the selected items
   */
  NumberSet<N> getSelected();

  /**
   * Returns iterator for extents of the selected items.
   * <p>
   * Number that is returned by {@link Iterator#next()}
   * method is the index of the item in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   *
   * @return the iterator for extents of the selected items
   */
  Iterator<Extent<N>> getSelectedExtents();

  /**
   * Sets the order rank of a range of items to start from the target index.
   * For example having 5 items in the section setOrder(2, 3, 1) will result in the
   * following order: 0, 2, 3, 1, 4.
   * <p>
   * <code>start</code>, <code>end</code> and <code>target</code>
   * numbers are item indexes in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   *
   * @param start first index of the range of items
   * @param end last index of the range of items
   * @param target the index of the target item
   *
   * @throws IllegalArgumentException if start or end is <code>null</code>
   * @throws IndexOutOfBoundsException if start or end is out of 0 ...
   *         {@link #getCount()}-1 bounds
   * @throws IndexOutOfBoundsException if target is out of 0 ...
   *         {@link #getCount()} bounds
   * @throws IllegalArgumentException if start is greater then end
   *
   */
  void setOrder(N start, N end, N target);

  /**
   * Sets the order rank of the item with the given index in the model to the target index.
   * <p>
   * <code>index</code> is the item index in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   * <p>
   * Ranges of items should be set moved by one of the <code>setOrder</code> methods.
   * to achieve the best efficiency.
   *
   * @param index index of the item to move
   * @param target the index of the target item
   *
   * @throws IllegalArgumentException if index or target is <code>null</code>
   * @throws IndexOutOfBoundsException if index is out of 0 ...
   *         {@link #getCount()}-1 bounds
   * @throws IndexOutOfBoundsException if target is out of 0 ...
   *         {@link #getCount()} bounds
   *
   * @see #setOrder(Number, Number, Number)
   */
  void setOrder(N index, N target);

  void setOrder(Iterator<N> iterator);

  void setOrderExtents(Iterator<Extent<N>> iterator);

  /**
   * Returns the order rank of the item with the given index in the model.
   * It is calculated according to the current order including the hidden items.
   * If the index is null or out of scope then the method returns null.
   *
   * @param index to get the position for
   * @return order index of the item with the given index in the model
   *
   * @throws IllegalArgumentException if index is <code>null</code>
   * @throws IndexOutOfBoundsException if index is out of 0 ...
   *         {@link #getCount()}-1 bounds
   */
  N getOrder(N index);

  /**
   * Returns set of number extents representing the order of the items.
   * <p>
   * Number that is returned by {@link Iterator#next()}
   * method is the index of the item in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   *
   * @return the iterator for indexes of the selected items
   */
  NumberSet<N> getOrder();

  /**
   * Returns iterator for extents of the order ranks of the items.
   * <p>
   * Number that is returned by {@link Iterator#next()}
   * method is the index of the item in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   *
   * @return the iterator for extents of the selected items
   */
  Iterator<Extent<N>> getOrderExtents();


  /**
   * Returns the item index at the given position in the layout.
   * The position is according to the current order excluding the hidden items.
   * <p>
   * If the <code>index</code> is out of range null value is returned.
   *
   * @param position visual index of given item
   * @return item at the given position
   *
   * @throws IllegalArgumentException if position is <code>null</code>
   * @throws IndexOutOfBoundsException if position is out of 0 ...
   *         {@link #getCount()}-1 bounds
   * @see #getOrder(Number)
   */
  N getIndex(N position);

  /**
   * Returns the position in the layout of the item with the given index in the model.
   * The position is according to the current order excluding the hidden items.
   * <p>
   * If the <code>index</code> is out of range null value is returned.
   *
   * @param index index of the given item
   * @return item at the given position
   *
   * @throws IllegalArgumentException if position is <code>null</code>
   * @throws IndexOutOfBoundsException if position is out of 0 ...
   *         {@link #getCount()}-1 bounds
   * @see #getOrder(Number)
   */
  N getPosition(N index);


  /**
   * Removes a range of items decreasing the indexes of
   * the following items by the number of removed items.
   * <p>
   * It changes the state of cell width, line width, resizable, movable, hideable, hidden, order, selection.
   * <p>
   * <code>start</code> and <code>end</code>
   * numbers are item indexes in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   *
   * @param start first index of the range of items
   * @param end last index of the range of items
   *
   * @throws IndexOutOfBoundsException if start or end is <code>null</code>
   * @throws IndexOutOfBoundsException if start or end is out of 0 ...
   *         {@link #getCount()}-1 bounds
   * @throws IllegalArgumentException if start is greater then end
   *
   */
  void delete(N start, N end);

  /**
   * Inserts items before the given target item increasing the indexes
   * of the following items by the number of inserted items.
   * <p>
   * Items are inserted before the given target index or at the end if the target equals
   * to the receiver item count.
   * <p>
   * It changes the state of cell width, line width, resizable, movable, hideable, hidden, order, selection.
   * <p>
   * <code>target</code>, <code>count</code>
   * numbers are item indexes in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   *
   * @param target the index before which items are inserted
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
   * Adds the listener to the collection of listeners who will
   * be notified when a section item is moved or resized, by sending
   * it one of the messages defined in the <code>ControlListener</code>
   * interface.
   * <p> The data property of the {@link ControlEvent} contains the item being resized
   * or the target item for the moved items. In order to get moved items or a set of
   * resized items (if more then one is resized) {@link #getSelectedExtents()}
   * can be utilized.
   *
   * @param listener the listener which should be notified
   *
   * @exception IllegalArgumentException <ul>
   *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
   * </ul>
   * @exception SWTException <ul>
   *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
   *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
   * </ul>
   *
   * @see ControlListener
   * @see #removeControlListener
   */
  void addControlListener(ControlListener listener);

  /**
   * Adds the listener to the collection of listeners who will
   * be notified when a section item is selected by the user, by sending
   * it one of the messages defined in the <code>SelectionListener</code>
   * interface.
   * <p>
   * The selection event is not emitted by the axis API methods that are
   * responsible for selection and deselection of items. It can only be
   * triggered by another SWT event bound to the selection command.
   * </p>
   * <p>
   * <code>widgetSelected</code> is called when the axis item is selected
   * <code>widgetDefaultSelected</code> is not called.
   * </p>
   *
   * @param listener the listener which should be notified when the axis item
   * is selected by the user
   *
   * @exception IllegalArgumentException <ul>
   *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
   * </ul>
   * @exception SWTException <ul>
   *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
   *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
   * </ul>
   *
   * @see SelectionListener
   * @see SelectionEvent
   * @see #removeSelectionListener
   */
  void addSelectionListener(SelectionListener listener);

  /**
   * Removes the listener from the collection of listeners who will
   * be notified a section item is moved or resized.
   *
   * @param listener the listener which should no longer be notified
   *
   * @exception IllegalArgumentException <ul>
   *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
   * </ul>
   * @exception SWTException <ul>
   *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
   *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
   * </ul>
   *
   * @see ControlListener
   * @see #addControlListener
   */
  void removeControlListener(ControlListener listener);

  /**
   * Removes the listener from the collection of listeners who will
   * be notified when a section item is selected by the user.
   *
   * @param listener the listener which should no longer be notified
   *
   * @exception IllegalArgumentException <ul>
   *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
   * </ul>
   * @exception SWTException <ul>
   *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
   *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
   * </ul>
   *
   * @see SelectionListener
   * @see #addSelectionListener
   */
  void removeSelectionListener(SelectionListener listener);

  /**
   * Adds the given set to the collection of hidden sets. Each hidden set can be managed
   * independently to reflect items hidden by the user, application logic filtering,
   * or tree/grouping collapsing. The final hidden state will be union of all the individual
   * hidden sets.
   * @param set hidden set to be added
   */
  public abstract void addHiddenSet(NumberSet<N> set);

  /**
   * Removes hidden set. The default hidden set cannot be removed.
   * @param set hidden set to be removed
   */
  public abstract void removeHiddenSet(NumberSet<N> set);

  /**
   * Returns the default hidden set representing items hidden by the user with
   * the commands bound to the user gestures.
   *
   * @return the default hidden set
   */
  public NumberSet<N> getDefaultHiddenSet();


  /**
   * Provisional tree API.
   */
  public abstract boolean isExpanded(N index);

  /**
   * Provisional tree API.
   */
  public abstract void setExpanded(N start, N end, boolean state);

  /**
   * Provisional tree API.
   */
  public abstract void setExpanded(N parent, boolean state);

  /**
   * Provisional tree API.
   */
  public abstract N getLevelInTree(N index);

  /**
   * Provisional tree API.
   */
  public abstract N getParent(N index);

  /**
   * Provisional tree API.
   */
  public abstract N getChildrenCount(N parent);

  /**
   * Provisional tree API.
   */
  public abstract Iterator<N> getChildren(N parent);

  /**
   * Provisional tree API.
   */
  public abstract Iterator<Extent<N>> getChildrenExtents(N parent);

  /**
   * Provisional tree API.
   */
  public abstract void setParent(N start, N end, N parent);

  /**
   * Provisional tree API.
   */
  public abstract void setParent(N child, N parent);

  /**
   * Provisional tree API.
   */
  boolean hasChildren(N parent);

  /**
   * Returns the axis that this section belongs to.
   * @return the axis that this section belongs to
   */
  public abstract Axis<N> getAxis();


//  /**
//   * Returns <code>true</code> if the item with the given index in the model is merged be moved by end user.
//   * Otherwise, <code>false</code> is returned.
//   * @param index
//   * @return
//   */
//  boolean isMerged(N index);



}