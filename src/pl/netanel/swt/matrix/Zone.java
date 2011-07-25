package pl.netanel.swt.matrix;

/**
 * Constitutes a region of a matrix where a section from the row axis 
 * and a section from the column axis intersect with each other.  
 * <p>
 * Zone has painters to paint itself on the screen.
 * </p><p>
 * </p>
 * 
 * @param <N0> indexing type for rows
 * @param <N1> indexing type for columns
 * 
 * @author Jacek
 * @created 13-10-2010
 */
import java.math.BigInteger;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Listener;

public interface Zone<N0 extends Number, N1 extends Number> {

  /**
   * Returns a zone implementation that does not perform 
   * validation of methods parameters. This is needed for 
   * loop optimization, like for example inside of 
   * {@link Painter#paint(Number, Number, int, int, int, int)} 
   * method.
   * @return
   */
  Zone getCore();
  
  /**
   * Returns the zone section at row axis.
   * @return the zone section at row axis
   * 
   * @see #getSectionUnchecked0()
   */
  Section<N0> getSection0();

  /**
   * Returns the zone section column axis.
   * @return the zone section column axis
   */
  Section<N1> getSection1();

  /**
   * Return rectangular bounds of the cell with the given coordinates.
   * If one of the indexes is null it return null.
   * 
   * @param index0 cell index on <code>axis0</code> 
   * @param index1 cell index on <code>axis1</code> 
   * @return rectangular bounds of the cell with the given coordinates.
   * @throws IllegalArgumentException if <code>index0</code> or 
   *         <code>index1</code> is null.
   * @throws IndexOutOfBoundsException if <code>index0</code> is out of 
   *         0 ... this.getSection0().getCount() bounds
   * @throws IndexOutOfBoundsException if <code>index1</code> is out of 
   *         0 ... this.getSection1().getCount() bounds
   */
  Rectangle getCellBounds(N0 index0, N1 index1);

  /**
   * Sets the default background color for the receiver's cells. 
   * @param color color to set
   * @throws IllegalArgumentException if <code>color</code> or 
   *         <code>index1</code> is null.
   */
  void setDefaultBackground(Color color);

  /**
   * Returns the default background color of the receiver's cells. 
   * @return default width of lines in this 
   */
  Color getDefaultBackground();

  /**
   * Sets the default foreground color for the receiver's cells. 
   * @param color color to set
   * @throws IllegalArgumentException if <code>color</code> or 
   *         <code>index1</code> is null.
   */
  void setDefaultForeground(Color color);

  /**
   * Returns the default foreground color of the receiver's cells. 
   * @return default width of lines in this 
   */
  Color getDefaultForeground();

  /**
   * Returns the rectangular boundaries of this zone. 
   * @return the rectangular boundaries of this zone
   */
  Rectangle getBounds();

  /**
   * Sets selection foreground color for the receiver. 
   * @param color color to set
   * @throws IllegalArgumentException if <code>color</code> or 
   *         <code>index1</code> is null.
   */
  void setSelectionForeground(Color color);

  /**
   * Returns the selection foreground color for the receiver.
   * @return selection foreground color for the receiver
   */
  Color getSelectionForeground();

  /**
   * Sets selection background color for the receiver. 
   * @param color color to set
   * @throws IllegalArgumentException if <code>color</code> or 
   *         <code>index1</code> is null.
   */
  void setSelectionBackground(Color color);

  /**
   * Returns the selection background color for the receiver.
   * @return selection background color for the receiver
   */
  Color getSelectionBackground();

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
   * <code>index0</code> and <code>index1</code> refer to the model, 
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations. 
   * 
   * @param index0 row index of the cell  
   * @param index1 column index of the cell 
   * @return the selection state of the specified cell
   * 
   * @throws IllegalArgumentException if <code>index0</code> or 
   *         <code>index1</code> is null.
   * @throws IndexOutOfBoundsException if <code>index0</code> is out of 
   *         0 ... this.getSection0().getCount() bounds
   * @throws IndexOutOfBoundsException if <code>index1</code> is out of 
   *         0 ... this.getSection1().getCount() bounds
   */
  boolean isSelected(N0 index0, N1 index1);

  /**
   * Sets the selection state for the range of cells.
   * <p>
   * <code>start0</code>,<code>end0</code>, <code>start1</code> and <code>end1</code> 
   * indexes refer to the model, not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   *
   * @param start0 first index of the range of row items  
   * @param end0 last index of the range of row items  
   * @param start1 first index of the range of column items  
   * @param end1 last index of the range of column items  
   * @param state the new selection state
   * 
   * @throws IllegalArgumentException if <code>start0</code> or 
   *         <code>end0</code> or <code>start1</code>  or <code>end1</code> is null.
   * @throws IndexOutOfBoundsException if <code>start0</code> or <code>end0</code>
   *         is out of 0 ... this.getSection0().getCount() bounds
   * @throws IndexOutOfBoundsException if <code>start1</code> or <code>end1</code>
   *         is out of 0 ... this.getSection1().getCount() bounds
   */
  void setSelected(N0 start0, N0 end0, N1 start1, N1 end1, boolean state);

  /**
   * Sets the selection state for the specified cell.
   * <p>
   * <code>index0</code> and <code>index1</code> refer to the model, 
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations. 
   * <p>
   * Ranges of cells should be set selected by 
   * {@link #setSelected(Number, Number, Number, Number, boolean)} 
   * to achieve the best efficiency.
   * 
   * @param index0 row index of the cell  
   * @param index1 column index of the cell 
   * @return the selection state of the specified cell
   *
   * @throws IllegalArgumentException if <code>index0</code> or 
   *         <code>index1</code> is null.
   * @throws IndexOutOfBoundsException if <code>index0</code> is out of 
   *         0 ... this.getSection0().getCount() bounds
   * @throws IndexOutOfBoundsException if <code>index1</code> is out of 
   *         0 ... this.getSection1().getCount() bounds
   * 
   */
  void setSelected(N0 index0, N1 index1, boolean state);

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
   * row axis index, the second one is a column axis index.
   * <p>
   * <code>index0</code> and <code>index1</code> refer to the model, 
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.  
   * <p>
   * <strong>Warning</strong> iterating index by index over large extents 
   * may cause a performance problem.
   */
  Iterator<Number[]> getSelectedIterator();

  /**
   * Returns iterator for selected cell extents. First two numbers in the array 
   * returned by the {@link Iterator#next()} method define start and end of a
   * row axis extent, the second two the start and end of a column axis extent.
   * <p>
   * <code>index0</code> and <code>index1</code> refer to the model, 
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations. 
   */
  Iterator<Number[]> getSelectedExtentIterator();

  /**
   * Returns selection index bounds. The numbers in the returning array are 
   * minimal axis0 index, maximal axis0 index, minimal axis1 index, maximal axis1 index. 
   * @return selection index bounds
   */
  Number[] getSelectedExtent();

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
  void addPainter(Painter<N0, N1> painter);

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
  void addPainter(int index, Painter<N0, N1> painter);

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
  void setPainter(int index, Painter<N0, N1> painter);

  /**
   * Replaces the painter at the index of painter with the same name.
   * If a painter with the specified name does not exist, 
   * then the new painter is added at the end.
   * @param painter painter to replace a painter with the same name
   * @throws IllegalArgumentException if the painter is null
   */
  void replacePainter(Painter<N0, N1> painter);

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
  Painter<N0, N1> removePainter(int index);

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
  boolean removePainter(Painter painter);
  
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
  Painter<N0, N1> getPainter(String name);

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
  Painter<N0, N1> getPainter(int index);

  /**
   * Returns the matrix to which the zone belongs.
   * @return the matrix to which the zone belongs
   */
  Matrix<N0, N1> getMatrix();



}