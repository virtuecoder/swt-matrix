package pl.netanel.swt.matrix;

import java.util.Iterator;

import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

public interface Section<N extends Number> {

  Class getIndexClass();
  Section getCore();
  
  /**
   * Specifies the number of section items.
   * <p>
   * <code>count</code> input parameter is not validated against negative or null values.
   * 
   * @param count the new count of the receiver's items
   * @see #getCount()
   * @throws NullPointerException if the count is <tt>null</tt>.
   */
  void setCount(N count);

  /**
   * Returns the number of items in the receiver. 
   * @return the number of items in the receiver
   * @see #setCount(Number)
   */
  N getCount();

  /**
   * Returns <tt>true</tt> if this contains contains no items.
   * Otherwise <code>false</code> is returned.
   * @return <tt>true</tt> if this contains contains no items
   */
  boolean isEmpty();

  /**
   * Returns the item at given visual position according 
   * to the current order of items and skipping the hidden ones.
   * <p>
   * If the <code>index</code> is out of range null value is returned.
   * 
   * @param position visual index of given item
   * @return item at the given position
   * 
   * @see #indexOf(Number)
   */
  N get(N position);

  /**
   * Returns the visual position of the given item according to the current order of items.
   * Hidden items are not skipped. 
   * If the item is null or out of scope then the method returns null.
   *  
   * @param item to get the position for
   * @return visual position of the item at the given index
   * 
   * @see #indexOfNotHidden(Number)
   */
  N indexOf(N item);

  /**
   * Returns the visual position of an item according to the current order of items 
   * and skipping the hidden ones. 
   * If the item is null, out of scope or is hidden then the method returns null.
   *  
   * @param index to get the position for
   * @return visual position of the item at the given index
   * 
   * @see #indexOf(Number)
   */
  N indexOfNotHidden(N index);

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

  /**
   * Sets the default width of cells in this section to the given value. Cell
   * width excludes the width of lines. Negative argument values are ignored.
   * <p>
   * Default value allows to save storage memory of the width attribute if many
   * cells share the same value and the newly created items to have the default
   * value automatically.
   * 
   * @param width new value for default width.
   */
  void setDefaultCellWidth(int width);

  /**
   * Returns the default cell width of the receiver's items. 
   * Cell with excludes the width of lines.
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
   * Sets the line width for the item at the specified index.
   * <p>
   * <code>index</code> index refers to the model, not the visual position of the item on the screen 
   * which can be altered by move and hide operations. 
   * <code>width</code> that is lower the zero is ignored. 
   * <p>
   * Cell width for a range of items should be set by {@link #setLineWidth(Number, Number, int)} 
   * to achieve the best efficiency.
   *
   * @param index index of the item to set the line width for 
   * @param width the new line width
   * @see #setLineWidth(Number, Number, int)
   */
  void setLineWidth(N index, int width);

  /**
   * Sets the line width for the range of items.
   * <p>
   * <code>start</code> and <code>end</code> indexes refer to the model, not the visual position of the item on the screen 
   * which can be altered by move and hide operations. 
   * <code>width</code> that is lower then zero is ignored. 
   *  
   * @param start first index of the range of items  
   * @param end last index of the range of items  
   * @param width the new line width
   */
  void setLineWidth(N start, N end, int width);

  /**
   * Returns the line width at the given index in the receiver.  
   * If the width has not been set at this index by {@link #setLineWidth(Number)} 
   * method then the default line width is returned.
   * <p>
   * Line at index i is on the left side of the cell at index i.
   * Last line to the right is at index equal to section item count.
   * <code>index</code> refers to the model, not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   * 
   * @param index the item index 
   * @return the line width at the given item index in the receiver
   */
  int getLineWidth(N index);

  /**
   * Sets the cell width for the item at the specified index.
   * <p>
   * <code>index</code> index refers to the model, not the visual position of the item on the screen 
   * which can be altered by move and hide operations. 
   * <code>width</code> that is lower the zero is ignored. 
   * <p>
   * Cell width for a range of items should be set by {@link #setCellWidth(Number, Number, int)} 
   * to achieve the best efficiency.
   *
   * @param index index of the item to set the cell width for 
   * @param width the new cell width
   * @see #setCellWidth(Number, Number, int)
   */
  void setCellWidth(N index, int width);

  /**
   * Sets the cell width for the range of items.
   * <p>
   * <code>start</code> and <code>end</code> indexes refer to the model, not the visual position of the item on the screen 
   * which can be altered by move and hide operations. 
   * <code>width</code> that is lower the zero is ignored. 
   *
   * @param start first index of the range of items  
   * @param end last index of the range of items  
   * @param width the new cell width
   */
  void setCellWidth(N start, N end, int width);

  /**
   * Returns the cell width at the given index in the receiver.  
   * If the width has not been set at this index by {@link #setCellWidth(Number)} 
   * method then the default cell width is returned.
   * <p>
   * <code>index</code> refers to the model, not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   * 
   * @param index the item index 
   * @return the cell width at the given item index in the receiver
   */
  int getCellWidth(N index);

  /**
   * Sets the move ability for the item at the given index.
   * <p>
   * <code>index</code> refers to the model, not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   * <p>
   * Ranges of items should be set moveable by {@link #setMoveable(Number, Number, boolean)} 
   * to achieve the best efficiency.
   *
   * @param index index of the item to move   
   * @param enabled the new move ability state
   * @see #setMoveable(Number, Number, boolean)
   */
  void setMoveable(N index, boolean enabled);

  /**
   * Sets the move ability for the range of items. 
   * An item that is moveable can be reordered by the user by dragging the header. 
   * An item that is not moveable cannot be dragged by the user but may be reordered by the programmer.
   * <p>
   * <code>start</code> and <code>end</code> indexes refer to the model, not the visual position of the item on the screen
   * which can be altered by move and hide operations. 
   *
   * @param start first index of the range of items  
   * @param end last index of the range of items  
   * @param enabled the new move ability state
   */
  void setMoveable(N start, N end, boolean enabled);

  /**
   * Returns <code>true</code> if the item at given index can be moved by end user.
   * Otherwise, <code>false</code> is returned. 
   * An item that is moveable can be reordered by the user by dragging the header. 
   * An item that is not moveable cannot be dragged by the user but may be reordered by the programmer.
   * <p>
   * If the move ability has not been set at this index by {@link #setMoveable(Number)} 
   * method then the default cell width is returned.
   * <p>
   * <code>index</code> refers to the model, not the visual position of the item on the screen
   * which can be altered by move and hide operations. 
   * 
   * @param index the item index 
   * @return the move ability state at the given index
   */
  boolean isMoveable(N index);

  /**
   * Sets the resize ability for the item at the given index.
   * <p>
   * <code>index</code> refers to the model, not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   * <p>
   * Ranges of items should be set resizable by {@link #setResizable(Number, Number, boolean)} 
   * to achieve the best efficiency.
   *
   * @param index index of the item to resize   
   * @param enabled the new resize ability state
   * @see #setResizable(Number, Number, boolean)
   */
  void setResizable(N index, boolean enabled);

  /**
   * Sets the resize ability for the range of items.
   * An item that is resizable can be resized by the user dragging the edge of the header. 
   * An item that is not resizable cannot be dragged by the user but may be resized by the programmer.
   * <p>
   * <code>start</code> and <code>end</code> indexes refer to the model, not the visual position of the item on the screen
   * which can be altered by move and hide operations. Width that is lower then zero is ignored. 
   *
   * @param start first index of the range of items  
   * @param end last index of the range of items  
   * @param enabled the new resize ability state
   */
  void setResizable(N start, N end, boolean enabled);

  /**
   * Returns <code>true</code> if the item at given index can be resized by end user.
   * Otherwise, <code>false</code> is returned.
   * An item that is resizable can be resized by the user dragging the edge of the header. 
   * An item that is not resizable cannot be dragged by the user but may be resized by the programmer.
   * <p>
   * Returns the stored item resize ability at given index  
   * or the default item resize ability if it has not been set at this index. 
   * <p>
   * <code>index</code> refers to the model, not the visual position of the item on the screen
   * which can be altered by move and hide operations. 
   * 
   * @param index the item index 
   * @return the move ability state at the given index
   */
  boolean isResizable(N index);

  /**
   * Sets the hide ability for the item at the given index.
   * <p>
   * <code>index</code> refers to the model, not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   * <p>
   * Ranges of items should be set hideable by {@link #setHideable(Number, Number, boolean)} 
   * to achieve the best efficiency.
   *
   * @param index index of the item to hide   
   * @param enabled the new hide ability state
   * @see #setHideable(Number, Number, boolean)
   */
  void setHideable(N index, boolean enabled);


  /**
   * Sets the hide ability for the range of items.
   * An item that is hideable can be hidden by the user gesture bound to the hide command. 
   * An item that is not hideable cannot be hidden by the user but may be hidden by the programmer.
   * <p>
   * <code>start</code> and <code>end</code> indexes refer to the model, not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   *
   * @param start first index of the range of items  
   * @param end last index of the range of items  
   * @param enabled the new hide ability state
   */
  void setHideable(N start, N end, boolean enabled);


  /**
   * Returns <code>true</code> if the item at given index can be hidden by end user.
   * Otherwise, <code>false</code> is returned.
   * An item that is hideable can be hidden by the user gesture bound to the hide command. 
   * An item that is not hideable cannot be hidden by the user but may be hidden by the programmer.
   * <p>
   * Returns the stored item hide ability at given index  
   * or the default item hide ability if it has not been set at this index. 
   * <p>
   * <code>index</code> refers to the model, not the visual position of the item on the screen
   * which can be altered by move and hide operations. 
   * 
   * @return the hide ability state at the given index
   */
  boolean isHideable(N index);

  /**
   * Sets the hiding state for the item at the given index.
   * <p>
   * <code>index</code> refers to the model, not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   * <p>
   * Ranges of items should be set hidden by {@link #setHidden(Number, Number, boolean)} 
   * to achieve the best efficiency.
   * 
   * @param index index of the item to hide   
   * @param state the new hiding state
   */
  void setHidden(N index, boolean state);

  /**
   * Sets the hiding state for the range of items.
   * <p>
   * <code>start</code> and <code>end</code> indexes refer to the model, not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   *
   * @param start first index of the range of items  
   * @param end last index of the range of items  
   * @param state the new hiding state
   */
  void setHidden(N start, N end, boolean state);

  /**
   * Sets the hiding state for the items that are selected.
   * <p>
   * <code>start</code> and <code>end</code> indexes refer to the model, not the visual position of the item on the screen
   * which can be altered by move and hide operations. Width that is lower then zero is ignored. 
   *
   * @param state the new hiding state
   * <p>
   * @see #setSelected(Number, Number, boolean)
   */
  void setHiddenSelected(boolean state);

  /**
   * Returns <code>true</code> if the item at given index is hidden.
   * Otherwise, <code>false</code> is returned.
   * <p>
   * <code>index</code> refers to the model, not the visual position of the item on the screen
   * which can be altered by move and hide operations. 
   * 
   * @param index the item index 
   * @return the hiding state at the given index
   */
  boolean isHidden(N index);

  /**
   * Returns the number of hidden items in the given range of items.
   * 
   * <code>start</code> and <code>end</code> indexes refer to the model, not the visual position of the item on the screen
   * which can be altered by move and hide operations. Width that is lower then zero is ignored. 
   *
   * @param start first index of the range of items  
   * @param end last index of the range of items  
   * @return the number of hidden items 
   */
  N getHiddenCount(N start, N end);

  /**
   * Returns the number of hidden items.
   * @return the number of hidden items 
   */
  N getHiddenCount();

  /**
   * Returns iterator for indexes of the hidden items.
   * 
   * @return the hidden items iterator
   */
  Iterator<N> getHidden();

  /**
   * Sets the hiding state for the item at the given index.
   * <p>
   * <code>index</code> refers to the model, not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   * <p>
   * Ranges of items should be set selected by {@link #setSelected(Number, Number, boolean)} 
   * to achieve the best efficiency.
   *
   * @param index index of the item to hide   
   * @param state the new hiding state
   * @param notify 
   * @see #setSelected(Number, Number, boolean)
   */
  void setSelected(N index, boolean state);

  /**
   * Sets the selection state for the range of items.
   * <p>
   * <code>start</code> and <code>end</code> indexes refer to the model, 
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   *
   * @param start first index of the range of items  
   * @param end last index of the range of items  
   * @param state the new selection state
   */
  void setSelected(N start, N end, boolean state);

  /**
   * Sets the selection state for all the items.
   * 
   * @param state the new selection state
   * @param notify 
   */
  void setSelectedAll(boolean state, boolean notify, boolean notifyInZones);

  /**
   * Returns <code>true</code> if the item at given index is selected.
   * Otherwise, <code>false</code> is returned.
   * <p>
   * <code>index</code> refers to the model, not the visual position of the item on the screen
   * which can be altered by move and hide operations. 
   * 
   * @param index the item index 
   * @return the selection state at the given index
   */
  boolean isSelected(N index);

  /**
   * Returns the number of selected items in this section.
   * @return the number of selected items in this section
   */
  N getSelectedCount();

  /** 
   * Returns iterator for selected items. 
   * <p>
   * <code>index</code> that is returned by {@link Iterator#next()} 
   * method refers to the model, 
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.   
   */
  Iterator<N> getSelectedIterator();

  /** 
   * Returns iterator for selected items. 
   * <p>
   * <code>index</code> that is returned by {@link Iterator#next()} 
   * method refers to the model, 
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.   
   */
  Iterator<Number[]> getSelectedExtentIterator();

  /**
   * Moves a range of items before the target index thus changing their visible order.
   * <p>
   * <code>start</code>, <code>end</code> and <code>target</code>indexes refer to the model, 
   * not the visual position of the item on the screen which can be altered by move and hide operations. 
   *
   * @param start first index of the range of items  
   * @param end last index of the range of items  
   * @param target the index of the target item
   */
  void move(N start, N end, N target);

  /**
   * Removes a range of items decreasing the following indexes by the number of removed items. 
   * 
   * @param start first index of the range of items  
   * @param end last index of the range of items
   */
  void delete(N start, N end);

  /**
   * Inserts items before the given target item increasing the following indexes by the number of inserted items. 
   * <p>
   * Items are inserted before the given target index or at the end if the target equals
   * to the receiver item count. 
   * 
   * @param target the index before which items are inserted  
   * @param count the number of items to insert
   */
  void insert(N target, N count);

  /**
   * Adds the listener to the collection of listeners who will
   * be notified when a section item is moved or resized, by sending
   * it one of the messages defined in the <code>ControlListener</code>
   * interface.
   * <p> The data property of the {@link ControlEvent} contains the item being resized
   * or the target item for the moved items. In order to get moved items or a set of 
   * resized items (if more then one is resized) {@link #getSelectedExtentIterator()} or 
   * {@link #getSelectedExtentResizableSequence()} can be utilized.
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
   * @see #removeSelectionListener
   * @see SelectionEvent
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

  void checkCellIndex(N index, String name);
  void checkLineIndex(N index, String name);

}