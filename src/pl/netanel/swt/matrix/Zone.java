package pl.netanel.swt.matrix;

import java.math.BigInteger;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Listener;


/**
 * Constitutes a region of a matrix where a section from the vertical axis 
 * and a section from the horizontal axis intersect with each other.  
 * <p>
 * Zone has painters to paint itself on the screen and listeners to respond 
 * to user gestures towards it. 
 * </p><p>
 * </p>
 * 
 * @param <X> indexing type for horizontal axis
 * @param <Y> indexing type for vertical axis
 * @see Section
 * 
 * @author Jacek
 * @created 13-10-2010
 */
public interface Zone<X extends Number, Y extends Number> {

  /**
   * Returns a no argument checking implementation for this zone.
   * It may be useful for loop optimization, for example inside of 
   * {@link Painter#paint(Number, Number, int, int, int, int)} 
   * method.
   * @return a no argument checking implementation for this zone
   */
  Zone<X, Y> getUnchecked();
  
  /**
   * Returns the zone section horizontal axis.
   * @return the zone section horizontal axis
   */
  Section<X> getSectionX();

  /**
   * Returns the zone section at vertical axis.
   * @return the zone section at vertical axis
   */
  Section<Y> getSectionY();


  /**
   * Return rectangular bounds of the cell with the given coordinates.
   * If one of the indexes is null it return null.
   * @param indexX cell index on the horizontal axis 
   * @param indexY cell index on the vertical axis 
   * 
   * @return rectangular bounds of the cell with the given coordinates.
   * @throws IllegalArgumentException if <code>indexX</code> or 
   *         <code>indexY</code> is null.
   * @throws IndexOutOfBoundsException if <code>indexY</code> is out of 
   *         0 ... this.getSectionX().getCount() bounds
   * @throws IndexOutOfBoundsException if <code>indexX</code> is out of 
   *         0 ... this.getSectionY().getCount() bounds
   */
  Rectangle getCellBounds(X indexX, Y indexY);

  /**
   * Returns the rectangular boundaries of this zone in the specified freezing area. 
   * @param frozenX frozen area on horizontal axis 
   * @param frozenY frozen area on vertical axis
   * @return the rectangular boundaries of this zone
   */
  Rectangle getBounds(Frozen frozenX, Frozen frozenY);

  /**
   * Returns <code>true</code> if selection is enabled, false otherwise.
   * @return the selection enabled state
   */
  boolean isSelectionEnabled();

  /**
   * Enables cell selection if the argument is <code>true</code>, 
   * or disables it otherwise.
   *
   * @param enabled the new selection ability state.
   */
  void setSelectionEnabled(boolean enabled);

  /**
   * Returns <code>true</code> if the cell at given indexes is selected.
   * Otherwise, <code>false</code> is returned.
   * <p>
   * <code>indexX</code> and <code>indexY</code> refer to the model, 
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations. 
   * @param indexX cell index on the horizontal axis 
   * @param indexY cell index on the vertical axis  
   * 
   * @return the selection state of the specified cell
   * 
   * @throws IllegalArgumentException if <code>indexX</code> or 
   *         <code>indexY</code> is null.
   * @throws IndexOutOfBoundsException if <code>indexX</code> is out of 
   *         0 ... this.getSectionY().getCount() bounds
   * @throws IndexOutOfBoundsException if <code>indexY</code> is out of 
   *         0 ... this.getSectionX().getCount() bounds
   */
  boolean isSelected(X indexX, Y indexY);

  /**
   * Sets the selection state for the range of cells.
   * <p>
   * <code>startX</code>,<code>endX</code>, <code>startY</code> and
   * <code>endY</code> numbers are item indexes in the model, 
   * not the visual position of the item on the screen 
   * which can be altered by move and hide operations.
   * 
   * @param startX first index of the range of column items
   * @param endX last index of the range of column items
   * @param startY first index of the range of row items
   * @param endY last index of the range of row items
   * @param state the new selection state
   * 
   * @throws IllegalArgumentException if <code>startX</code> or <code>endX</code>
   *           or <code>startY</code> or <code>endY</code> is null.
   * @throws IllegalArgumentException if <code>startX</code> is greater then <code>endX</code>
   *           or <code>startY</code> is greater then <code>endY</code>.
   * @throws IndexOutOfBoundsException if <code>startX</code> or
   *           <code>endX</code> is out of 0 ... this.getSectionX().getCount()
   *           bounds
   * @throws IndexOutOfBoundsException if <code>startY</code> or
   *           <code>endY</code> is out of 0 ... this.getSectionY().getCount()
   *           bounds
   */
  void setSelected(X startX, X endX, Y startY, Y endY, boolean state);
  
  /**
   * Sets the merging state for the range of cells. If the given range of cell contains
   * merged cells the merging will be removed for all of those cells. Otherwise the
   * cells will be merged. 
   * <p>
   * <code>startX</code>,<code>endX</code>, <code>startY</code> and
   * <code>endY</code> numbers are item indexes in the model, 
   * not the visual position of the item on the screen 
   * which can be altered by move and hide operations.
   * 
   * @param startX first index of the range of column items
   * @param endX last index of the range of column items
   * @param startY first index of the range of row items
   * @param endY last index of the range of row items
   * 
   * @throws IllegalArgumentException if <code>startX</code> or <code>endX</code>
   *           or <code>startY</code> or <code>endY</code> is null.
   * @throws IllegalArgumentException if <code>startX</code> is greater then <code>endX</code>
   *           or <code>startY</code> is greater then <code>endY</code>.
   * @throws IndexOutOfBoundsException if <code>startX</code> or
   *           <code>endX</code> is out of 0 ... this.getSectionX().getCount()
   *           bounds
   * @throws IndexOutOfBoundsException if <code>startY</code> or
   *           <code>endY</code> is out of 0 ... this.getSectionY().getCount()
   *           bounds
   */
  void setMerged(X startX, X endX, Y startY, Y endY); 
  
  /**
   * Returns <code>true</code> if the cell at given indexes is merged.
   * Otherwise, <code>false</code> is returned.
   * <p>
   * <code>indexX</code> and <code>indexY</code> refer to the model, 
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations. 
   * @param indexX cell index on the horizontal axis 
   * @param indexY cell index on the vertical axis  
   * 
   * @return the selection state of the specified cell
   * 
   * @throws IllegalArgumentException if <code>indexX</code> or 
   *         <code>indexY</code> is null.
   * @throws IndexOutOfBoundsException if <code>indexX</code> is out of 
   *         0 ... this.getSectionY().getCount() bounds
   * @throws IndexOutOfBoundsException if <code>indexY</code> is out of 
   *         0 ... this.getSectionX().getCount() bounds
   */
  boolean isMerged(X indexX, Y indexY);

  
  /**
   * Sets the selection state for the specified cell.
   * <p>
   * <code>indexX</code> and <code>indexY</code> refer to the model, 
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations. 
   * <p>
   * Ranges of cells should be set selected by 
   * {@link #setSelected(Number, Number, Number, Number, boolean)} 
   * to achieve the best efficiency.
   * @param indexX cell index on the horizontal axis 
   * @param indexY cell index on the vertical axis  
   * 
   * @return the selection state of the specified cell
   *
   * @throws IllegalArgumentException if <code>indexX</code> or 
   *         <code>indexY</code> is null.
   * @throws IndexOutOfBoundsException if <code>indexX</code> is out of 
   *         0 ... this.getSectionY().getCount() bounds
   * @throws IndexOutOfBoundsException if <code>indexY</code> is out of 
   *         0 ... this.getSectionX().getCount() bounds
   * 
   */
  void setSelected(X indexX, Y indexY, boolean state);

  /**
   * Sets the selection state for all the cells in this zone.
   * 
   * @param state the new selection state
   */
  void setSelectedAll(boolean state);

  /**
   * Returns the number of selected cells in this zone.
   * @return the number of selected cells in this zone
   */
  BigInteger getSelectedCount();

  /**
   * Returns the number of selected cells in this zone.
   * <p>
   * If the cell selection is disabled the it always returns a 
   * {@link BigIntegerNumber} with zero value.
   * 
   * @return {@link BigIntegerNumber} with the count of selected cells
   */
  BigInteger getSelectionCount();

  /**
   * Returns iterator for selected cells. First number in the array 
   * returned by the {@link Iterator#next()} method is a
   * vertical axis index, the second one is a horizontal axis index.
   * <p>
   * <code>indexX</code> and <code>indexY</code> refer to the model, 
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.  
   * <p>
   * <strong>Warning</strong> iterating index by index over large extents 
   * may cause a performance problem.
   */
  Iterator<Cell<X, Y>> getSelectedIterator();

  /**
   * Returns iterator for selected cell extents. First two numbers in the array 
   * returned by the {@link Iterator#next()} method define start and end of a
   * vertical axis extent, the second two the start and end of a horizontal axis extent.
   * <p>
   * <code>indexX</code> and <code>indexY</code> refer to the model, 
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations. 
   */
  Iterator<CellExtent<X, Y>> getSelectedExtentIterator();

  /**
   * Returns selection index bounds. The numbers in the returning array are 
   * minimal axisY index, maximal axisY index, minimal axisX index, maximal axisX index. 
   * @return selection index bounds
   */
  CellExtent<X, Y> getSelectedExtent();

  /**
   * Binds the command to the user gesture specified by the event type and code.
   * Code is a logical <i>OR</i> of key, state mask and mouse button codes. 
   * @param commandId identifier of a command from Matrix
   * @param eventType event type from SWT class
   * @param code || combination of keyCode, button and stateMask 
   * @see #unbind(int, int, int)
   */
  void bind(int commandId, int eventType, int code);
  
  /**
   * Removes the binding the command to the user gesture specified by the event type and code.
   * Code is a logical <i>OR</i> of key, state mask and mouse button codes. 
   * @param commandId identifier of a command from Matrix
   * @param eventType event type from SWT class
   * @param code || combination of keyCode, button and stateMask
   * @see #bind(int, int, int) 
   */
  void unbind(int commandId, int eventType, int code);

  /**
   * Adds the listener to the collection of listeners who will
   * be notified when a zone cell is selected by the user, by sending
   * it one of the messages defined in the <code>SelectionListener</code>
   * interface. 
   * <p>
   * The selection event is not emitted by the zone API methods that are
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
   * be notified when a zone cell is selected by the user.
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
   * Adds the listener to the collection of listeners who will
   * be notified when an event of the given type occurs. When the
   * event does occur in the zone, the listener is notified by
   * sending it the <code>handleEvent()</code> message. The event
   * type is one of the event constants defined in class <code>SWT</code>.
   *
   * @param eventType the type of event to listen for
   * @param listener the listener which should be notified when the event occurs
   *
   * @exception IllegalArgumentException <ul>
   *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
   * </ul>
   * @exception SWTException <ul>
   *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
   *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
   * </ul>
   *
   * @see Listener
   * @see SWT
   * @see #removeListener(int, Listener)
   */
  void addListener(int eventType, final Listener listener);

  /**
   * Adds the painter at the end of the receiver's painters list.
   * @param painter the painter to be added
   */
  void addPainter(Painter<X, Y> painter);

  /**
   * Inserts the painter at the given index of the receiver's painters list.
   * @param index at which the specified painter is to be inserted
   * @param painter painter to be inserted
   * @throws IndexOutOfBoundsException if the index is out of range
   *         (<tt>index &lt; 0 || index &gt;= getPainterCount()</tt>)
   * @throws IllegalArgumentException if the painter is null
   * @throws IllegalArgumentException if the painter's name already exists 
   *         in the collection of painters. 
   */
  void addPainter(int index, Painter<X, Y> painter);

  /**
   * Replaces the painter at the given index of the receiver's painters list. 
   * @param index index of the element to replace
   * @param painter painter to be stored at the specified position
   * @throws IndexOutOfBoundsException if the index is out of range
   *         (<tt>index &lt; 0 || index &gt;= getPainterCount()</tt>)   *         
   * @throws IllegalArgumentException if the painter is null
   * @throws IllegalArgumentException if the painter's name already exists 
   *         in the collection of painters. 
   */
  void setPainter(int index, Painter<X, Y> painter);

  /**
   * Replaces the painter at the index of painter with the same name.
   * If a painter with the specified name does not exist, 
   * then the new painter is added at the end.
   * 
   * @param painter painter to replace a painter with the same name
   * @throws IllegalArgumentException if the painter is null
   */
  void replacePainter(Painter<X, Y> painter);
  
  /**
   * Replaces the painter at the index of painter with the same name.
   * If a painter with the specified name does not exist, 
   * then the new painter is added at the end.
   * <p>
   * The new painter inherits the style from the painter that's being replaced. 
   * This helps for example to customize text in the cells without the need to
   * re-apply all the styling data, like background color, selection colors, etc.
   * 
   * @param painter painter to replace a painter with the same name
   * @throws IllegalArgumentException if the painter is null
   */
  void replacePainterPreserveStyle(Painter<X, Y> painter);

  /**
   * Removes the element at the specified position in the list of painters. 
   * Shifts any subsequent painters to the left (subtracts one
   * from their indices). Returns the painter that was removed from the
   * list.
   *
   * @param index the index of the painter to be removed
   * @return the painter previously at the specified position
   * @throws IndexOutOfBoundsException if the index is out of range
   *         (<tt>index &lt; 0 || index &gt;= getPainterCount()</tt>)
   * @see Painter#NAME_CELLS
   */
  Painter<X, Y> removePainter(int index);

  /**
   * Removes the specified element from this list,
   * if it is present (optional operation). If this list does not contain
   * the element, it is unchanged.
   * @param painter element to be removed from this list, if present
   * @return <tt>true</tt> if this list contained the specified element
   * @throws ClassCastException if the type of the specified element
   *         is incompatible with this list (optional)
   * @throws IllegalArgumentException if the painter is null
   * @see Painter#NAME_CELLS
   */
  boolean removePainter(Painter<X, Y> painter);
  
  /**
   * Removes a painter with the specified name from this list,
   * if it is present (optional operation). If this list does not contain
   * the element, it is unchanged.
   * @param painter element to be removed from this list, if present
   * @return <tt>true</tt> if this list contained the specified element
   * @throws ClassCastException if the type of the specified element
   *         is incompatible with this list (optional)
   * @throws IllegalArgumentException if the painter is null
   * @see Painter#NAME_CELLS
   */
  boolean removePainter(String name);

  /**
   * Returns the index of a painter with the specified name
   * in the list of the receiver's painters, or -1 
   * if this list does not contain the element.
   *
   * @param name painter name to search for
   * @return the index of a painter with the specified name
   * @throws IllegalArgumentException if the name is null
   */
  int indexOfPainter(String name);

  /**
   * Returns a painter with the specified name, or <code>null</code>
   * if the painters list does not contain such painter.
   *
   * @param name painter name to search for
   * @return the index of a painter with the specified name
   * @throws IllegalArgumentException if the name is null
   */
  Painter<X, Y> getPainter(String name);

  /**
   * Returns the number of the receiver's painters. 
   *
   * @return the number of the receiver's painters
   */
  int getPainterCount();

  /**
   * Returns the painter at the specified position in the receiver's list of painters.
   *
   * @param index index of the painter to return
   * @return the painter at the specified position in the receiver's list of painters.
   * @throws IndexOutOfBoundsException if the index is out of range
   *         (<tt>index &lt; 0 || index &gt;= getPainterCount()</tt>)
   */
  Painter<X, Y> getPainter(int index);

  /**
//   * Sets the default body style for the painters of this zone. 
//   * <p>
//   * It sets the foreground, background, selection foreground, selection background 
//   * colors for the {@link Painter#NAME_CELLS} painter and line (foreground) color 
//   * for the  {@link Painter#NAME_LINES_X} and {@link Painter#NAME_LINES_Y} painters.
//   */
//  void setBodyStyle();
//  
//  /**
//   * Sets the default header style for the painters of this zone. 
//   * <p>
//   * It sets the foreground, background, selection foreground, selection background 
//   * colors for the {@link Painter#NAME_CELLS} painter and line (foreground) color 
//   * for the  {@link Painter#NAME_LINES_X} and {@link Painter#NAME_LINES_Y} painters.
//   */
//  void setHeaderStyle();

  /**
   * Returns the matrix to which the zone belongs.
   * @return the matrix to which the zone belongs
   */
  Matrix<X, Y> getMatrix();

  boolean contains(CellExtent<X, Y> cellExtent, X indexX, Y indexY);

}