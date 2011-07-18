package pl.netanel.swt.matrix;

import java.util.Iterator;

import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionListener;

import pl.netanel.util.Preconditions;


class SectionClient<N extends Number> implements Section<N> {
	SectionCore<N> core;

  /**
   * Constructs a section indexed by the given sub-class of {@link Number}.
   * 
   * @param numberClass defines the class used for indexing
   */
  public SectionClient(Class<N> numberClass) {
    core = new SectionCore<N>(numberClass);
  }

	/**
	 * Indicates whether some other object is "equal to" this one.
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Section))
			return false;
		if (obj instanceof SectionClient)
			obj = ((SectionClient) obj).core;
		return core.equals(obj);
	}

	/**
	 * Returns the section position on the axis.
	 */
	@Override
	public String toString() {
		return core.toString();
	}

	@Override public Section getCore() {
	  return core;
	}

  public Class getIndexClass() {
    return core.getIndexClass();
  };
	
	/**
	 * Specifies the number of section items.
	 * 
	 * @param count
	 *            the new count of the receiver's items
	 *            <p>
	 * @see #getCount()
	 */
	public void setCount(N count) {
		core.setCount(count);
	}

	/**
	 * Returns the number of sections items.
	 * 
	 * @return the number of sections items.
	 * @see #setCount(Number)
	 */
	public N getCount() {
		return core.count;
	}

	/**
	 * Returns <code>true</code> if the receiver contains contains no items.
	 * Otherwise <code>false</code> is returned.
	 * 
	 * @return <code>true</code> if the receiver contains contains no items
	 */
	public boolean isEmpty() {
		return core.isEmpty();
	}

	/*------------------------------------------------------------------------
	 * Section properties
	 */

	/**
	 * Marks the receiver as visible if the argument is <code>true</code>, and
	 * marks it invisible otherwise.
	 * 
	 * @param visible
	 *            the new visibility state
	 */
	public void setVisible(boolean visible) {
		core.setVisible(visible);
	}

	/**
	 * Returns <code>true</code> if the receiver is visible. Otherwise,
	 * <code>false</code> is returned.
	 * 
	 * @return the receiver's visibility state
	 */
	public boolean isVisible() {
		return core.isVisible();
	}

	/**
	 * Enables current item navigation in the receiver if the argument is
	 * <code>true</code>, and disables it invisible otherwise.
	 * 
	 * @param enabled
	 *            the new visibility state
	 */
	public void setNavigationEnabled(boolean enabled) {
		core.setFocusItemEnabled(enabled);
	}

	/**
	 * Returns <code>true</code> if the current item navigation is enabled in
	 * the receiver. Otherwise, <code>false</code> is returned.
	 * 
	 * @return the receiver's visibility state
	 */
	public boolean isFocusItemEnabled() {
		return core.isFocusItemEnabled();
	}

	/*------------------------------------------------------------------------
	 * Default values 
	 */

	/**
	 * Sets the default width of cells in this section to the given value.
	 * Negative argument values are ignored.
	 * <p>
	 * Recommended to set a value of majority of the receiver's items to save on
	 * memory storage.
	 * 
	 * @param width
	 *            new value for default width.
	 */
	public void setDefaultCellWidth(int width) {
		if (width < 0)
			return;
		core.setDefaultCellWidth(width);
	}

	/**
	 * Returns the default cell width of the receiver's items.
	 * 
	 * @return default width of cells in this section.
	 */
	public int getDefaultCellWidth() {
		return core.getDefaultCellWidth();
	}

	/**
	 * Sets default width of lines in this section to the given value. Negative
	 * argument values are ignored.
	 * <p>
	 * Recommended to set a value of majority of the receiver's items to save on
	 * memory storage. Also any additional
	 * 
	 * @param width
	 *            new value for default width.
	 */
	public void setDefaultLineWidth(int width) {
		if (width < 0)
			return;
		core.setDefaultLineWidth(width);
	}

	/**
	 * Returns the default line width of the receiver's items.
	 * 
	 * @return default width of lines in this section.
	 */
	public int getDefaultLineWidth() {
		return core.getDefaultLineWidth();
	}

	/**
	 * Returns <code>true</code> if the section items can be resized by default.
	 * Otherwise, <code>false</code> is returned.
	 * 
	 * @return the default resizable ability state of the section items
	 */
	public boolean isDefaultResizable() {
		return core.isDefaultResizable();
	}

	/**
	 * Sets the default resize ability of the receiver's items to the given
	 * argument.
	 * <p>
	 * Recommended to set a value of majority of the receiver's items to save on
	 * memory storage.
	 * <p>
	 * The value of the <code>resizable</code> argument is returned by
	 * {@link #isResizable(Number)} method if a different value for the
	 * particular index has not been set by
	 * {@link #setResizable(Number, Number, boolean)}.
	 * 
	 * @param resizable
	 *            the new resize ability state
	 */
	public void setDefaultResizable(boolean resizable) {
		core.setDefaultResizable(resizable);
	}

	/**
	 * Returns <code>true</code> if the section items can be moved by default.
	 * Otherwise, <code>false</code> is returned.
	 * 
	 * @return the default move ability state of the section items
	 */
	public boolean isDefaultMoveable() {
		return core.isDefaultMoveable();
	}

	/**
	 * Sets the default move ability of the receiver's items to the given
	 * argument.
	 * <p>
	 * Recommended to set a value of majority of the receiver's items to save on
	 * memory storage.
	 * <p>
	 * The value of the <code>movable</code> argument is returned by
	 * {@link #isMoveable(Number)} method if a different value for the
	 * particular index has not been set by
	 * {@link #setMoveable(Number, Number, boolean)}.
	 * 
	 * @param moveable
	 *            the new move ability state
	 */
	public void setDefaultMoveable(boolean moveable) {
		core.setDefaultMoveable(moveable);
	}

	/**
	 * Returns <code>true</code> if the section items can be hidden by default.
	 * Otherwise, <code>false</code> is returned.
	 * 
	 * @return the default hide ability state of the section items
	 */
	public boolean isDefaultHideable() {
		return core.isDefaultHideable();
	}

	/**
	 * Sets the default hide ability of the receiver's items to the given
	 * argument.
	 * <p>
	 * Recommended to set a value of majority of the receiver's items to save on
	 * memory storage.
	 * <p>
	 * The value of the <code>hideable</code> argument is returned by
	 * {@link #isHideable(Number)} method if a different value for the
	 * particular index has not been set by
	 * {@link #setHideable(Number, Number, boolean)}.
	 * 
	 * @param hideable
	 *            the new hide ability state
	 */
	public void setDefaultHideable(boolean hideable) {
		core.setDefaultHideable(hideable);
	}

	/*------------------------------------------------------------------------
	 * Item properties 
	 */

	/**
	 * Sets the line width for the range of items.
	 * <p>
	 * <code>start</code> and <code>end</code> indexes refer to the model, not
	 * the visual position which can be altered by move and hide operations.
	 * <code>width</code> that is lower then zero is ignored.
	 * 
	 * @param start
	 *            first index of the range of items
	 * @param end
	 *            last index of the range of items
	 * @param width
	 *            the new line width
	 * @throws IndexOutOfBoundsException
	 *             if start or end are out of 0 ... {@link #getCount()} bounds
	 * @throws IllegalArgumentException
	 *             if start is greater then end
	 */
	public void setLineWidth(N start, N end, int width) {
		if (width < 0)
			return;
		checkRange(start, end, core.math.increment(core.count));
		core.setLineWidth(start, end, width);
	}

	/**
	 * Returns the stored line width at the given index. Or the default line
	 * width if the width has not been set at this index.
	 * <p>
	 * <code>index</code> refers to the model, not the visual position which can
	 * be altered by move and hide operations.
	 * 
	 * @param index
	 *            the item index
	 * @return the line width at the given item index in the receiver
	 * @throws IndexOutOfBoundsException
	 *             if index is out of 0 ... {@link #getCount()}-1 bounds
	 */
	public int getLineWidth(N index) {
		checkLineIndex(index,  "index");
		return core.getLineWidth(index);
	}

	/**
	 * Sets the line width for the range of items.
	 * <p>
	 * <code>start</code> and <code>end</code> indexes refer to the model, not
	 * the visual position which can be altered by move and hide operations.
	 * <code>width</code> that is lower the zero is ignored.
	 * 
	 * @param start
	 *            first index of the range of items
	 * @param end
	 *            last index of the range of items
	 * @param width
	 *            the new cell width
	 * @throws IndexOutOfBoundsException
	 *             if start or end are out of 0 ... {@link #getCount()}-1 bounds
	 * @throws IllegalArgumentException
	 *             if start is greater then end
	 */
	public void setCellWidth(N start, N end, int width) {
		if (width < 0)
			return;
		checkRange(start, end, core.count);
		core.setCellWidth(start, end, width);
	}

	/**
	 * Returns the stored cell width at the given index in the receiver. Or the
	 * default cell width if the width has not been set at this index.
	 * <p>
	 * <code>index</code> refers to the model, not the visual position which can
	 * be altered by move and hide operations.
	 * 
	 * @param index
	 *            the item index
	 * @return the cell width at the given item index in the receiver
	 * @throws IndexOutOfBoundsException
	 *             if index is out of 0 ... {@link #getCount()}-1 bounds
	 */
	public int getCellWidth(N index) {
		checkCellIndex(index, "index");
		return core.getCellWidth(index);
	}

	/**
	 * Sets the move ability for the range of items. An item that is moveable
	 * can be reordered by the user by dragging the header. An item that is not
	 * moveable cannot be dragged by the user but may be reordered by the
	 * programmer.
	 * <p>
	 * <code>start</code> and <code>end</code> indexes refer to the model, not
	 * the visual position which can be altered by move and hide operations.
	 * 
	 * @param start
	 *            first index of the range of items
	 * @param end
	 *            last index of the range of items
	 * @param enabled
	 *            the new move ability state
	 * @throws NullPointerException
	 *             if the start or end is null
	 * @throws IndexOutOfBoundsException
	 *             if start or end are out of 0 ... {@link #getCount()}-1 bounds
	 * @throws IllegalArgumentException
	 *             if start is greater then end
	 */
	public void setMoveable(N start, N end, boolean enabled) {
		checkRange(start, end, core.count);
		core.setMoveable(start, end, enabled);
	}

	/**
	 * Returns <code>true</code> if the item at given index can be moved by end
	 * user. Otherwise, <code>false</code> is returned. An item that is moveable
	 * can be reordered by the user by dragging the header. An item that is not
	 * moveable cannot be dragged by the user but may be reordered by the
	 * programmer.
	 * <p>
	 * Returns the stored item move ability at given index or the default item
	 * move ability if it has not been set at this index.
	 * <p>
	 * <code>index</code> refers to the model, not the visual position which can
	 * be altered by move and hide operations.
	 * 
	 * @param index
	 *            the item index
	 * @return the move ability state at the given index
	 * @throws NullPointerException
	 *             if the index is null
	 * @throws IndexOutOfBoundsException
	 *             if index is out of 0 ... {@link #getCount()}-1 bounds
	 */
	public boolean isMoveable(N index) {
		checkCellIndex(index, "index");
		return core.isMoveable(index);
	}

	/**
	 * Sets the resize ability for the range of items. An item that is resizable
	 * can be resized by the user dragging the edge of the header. An item that
	 * is not resizable cannot be dragged by the user but may be resized by the
	 * programmer.
	 * <p>
	 * <code>start</code> and <code>end</code> indexes refer to the model, not
	 * the visual position which can be altered by move and hide operations.
	 * 
	 * @param start
	 *            first index of the range of items
	 * @param end
	 *            last index of the range of items
	 * @param enabled
	 *            the new resize ability state
	 * @throws IndexOutOfBoundsException
	 *             if start or end are out of 0 ... {@link #getCount()}-1 bounds
	 * @throws IllegalArgumentException
	 *             if start is greater then end
	 */
	public void setResizable(N start, N end, boolean enabled) {
		checkRange(start, end, core.count);
		core.setResizable(start, end, enabled);
	}

	/**
	 * Returns <code>true</code> if the item at given index can be resized by
	 * end user. Otherwise, <code>false</code> is returned. An item that is
	 * resizable can be resized by the user dragging the edge of the header. An
	 * item that is not resizable cannot be dragged by the user but may be
	 * resized by the programmer.
	 * <p>
	 * Returns the stored item resize ability at given index or the default item
	 * resize ability if it has not been set at this index.
	 * <p>
	 * <code>index</code> refers to the model, not the visual position which can
	 * be altered by move and hide operations.
	 * 
	 * @param index
	 *            the item index
	 * @return the move ability state at the given index
	 * @throws NullPointerException
	 *             if the index is null
	 * @throws IndexOutOfBoundsException
	 *             if index is out of 0 ... {@link #getCount()}-1 bounds
	 */
	public boolean isResizable(N index) {
		checkCellIndex(index, "index");
		return core.isResizable(index);
	}

	/**
	 * Sets the hide ability for the range of items. An item that is hideable
	 * can be hidden by the user gesture bound to the hide command. An item that
	 * is not hideable cannot be hidden by the user but may be hidden by the
	 * programmer.
	 * <p>
	 * <code>start</code> and <code>end</code> indexes refer to the model, not
	 * the visual position which can be altered by move and hide operations.
	 * 
	 * @param start
	 *            first index of the range of items
	 * @param end
	 *            last index of the range of items
	 * @param enabled
	 *            the new hide ability state
	 * @throws NullPointerException
	 *             if the start or end is null
	 * @throws IndexOutOfBoundsException
	 *             if start or end are out of 0 ... {@link #getCount()}-1 bounds
	 * @throws IllegalArgumentException
	 *             if start is greater then end
	 */
	public void setHideable(N start, N end, boolean enabled) {
		checkRange(start, end, core.count);
		core.setHideable(start, end, enabled);
	}

	/**
	 * Returns <code>true</code> if the item at given index can be hidden by end
	 * user. Otherwise, <code>false</code> is returned. An item that is hideable
	 * can be hidden by the user gesture bound to the hide command. An item that
	 * is not hideable cannot be hidden by the user but may be hidden by the
	 * programmer.
	 * <p>
	 * Returns the stored item hide ability at given index or the default item
	 * hide ability if it has not been set at this index.
	 * <p>
	 * <code>index</code> refers to the model, not the visual position which can
	 * be altered by move and hide operations.
	 * 
	 * @param index
	 *            the item index
	 * @return the hide ability state at the given index
	 * @throws NullPointerException
	 *             if the index is null
	 * @throws IndexOutOfBoundsException
	 *             if index is out of 0 ... {@link #getCount()}-1 bounds
	 * @throws IndexOutOfBoundsException
	 *             if index is out of 0 ... {@link #getCount()}-1 bounds
	 */
	public boolean isHideable(N index) {
		checkCellIndex(index, "index");
		return core.isHideable(index);
	}

	/*------------------------------------------------------------------------
	 * Hiding 
	 */

	/**
	 * Sets the hiding state for the range of items.
	 * <p>
	 * <code>start</code> and <code>end</code> indexes refer to the model, not
	 * the visual position which can be altered by move and hide operations.
	 * 
	 * @param start
	 *            first index of the range of items
	 * @param end
	 *            last index of the range of items
	 * @param state
	 *            the new hiding state
	 * 
	 * @throws NullPointerException
	 *             if start or end is null
	 * @throws IndexOutOfBoundsException
	 *             if start or end are out of 0 ... {@link #getCount()}-1 bounds
	 * @throws IllegalArgumentException
	 *             if start is greater then end
	 */
	public void setHidden(N start, N end, boolean state) {
		checkRange(start, end, core.count);
		core.setHidden(start, end, state);
	}

	/**
	 * Returns <code>true</code> if the item at given index is hidden.
	 * Otherwise, <code>false</code> is returned.
	 * <p>
	 * <code>index</code> refers to the model, not the visual position which can
	 * be altered by move and hide operations.
	 * 
	 * @param index
	 *            the item index
	 * @return the hiding state at the given index
	 * 
	 * @throws NullPointerException
	 *             if the index is null
	 * @throws IndexOutOfBoundsException
	 *             if index is out of 0 ... {@link #getCount()}-1 bounds
	 */
	public boolean isHidden(N index) {
		checkCellIndex(index, "index");
		return core.isHidden(index);
	}

	/**
	 * Returns the number of hidden items.
	 * 
	 * @return the number of hidden items
	 */
	public N getHiddenCount() {
		return core.getHiddenCount();
	}

	/**
	 * Returns an iterator for the indexes of the hidden items.
	 * 
	 * @return the hidden items iterator
	 */
	public Iterator getHidden() {
		return core.getHidden();
	}

	/*------------------------------------------------------------------------
	 * Selection 
	 */

	/**
	 * Sets the selection state for the range of items.
	 * <p>
	 * <code>start</code> and <code>end</code> indexes refer to the model, not
	 * the visual position which can be altered by move and hide operations.
	 * 
	 * @param start
	 *            first index of the range of items
	 * @param end
	 *            last index of the range of items
	 * @param state
	 *            the new selection state
	 * 
	 * @throws NullPointerException
	 *             if start or end is null
	 * @throws IndexOutOfBoundsException
	 *             if start or end are out of 0 ... {@link #getCount()}-1 bounds
	 * @throws IllegalArgumentException
	 *             if start is greater then end
	 */
	public void setSelected(N start, N end, boolean state) {
		checkRange(start, end, core.count);
		core.setSelected(start, end, state);
	}

	/**
	 * Sets the selection state for all the items.
	 * @param notify 
	 * 
	 * @param state
	 *            the new selection state
	 */
	public void setSelectedAll(boolean selected, boolean notify, boolean notifyInZones) {
		core.setSelectedAll(selected, notify, notifyInZones);
	}

	/**
	 * Returns <code>true</code> if the item at given index is selected.
	 * Otherwise, <code>false</code> is returned.
	 * <p>
	 * <code>index</code> refers to the model, not the visual position which can
	 * be altered by move and hide operations.
	 * 
	 * @param index
	 *            the item index
	 * @return the selection state at the given index
	 * 
	 * @throws NullPointerException
	 *             if the index is null
	 * @throws IndexOutOfBoundsException
	 *             if index is out of 0 ... {@link #getCount()}-1 bounds
	 */
	public boolean isSelected(N index) {
		checkCellIndex(index, "index");
		return core.isSelected(index);
	}

	/**
	 * Returns the number of selected items.
	 * 
	 * @return the number of hidden items
	 */
	public N getSelectedCount() {
		return core.getSelectedCount();
	}

	/**
	 * Returns an iterator for the indexes of selected items.
	 * 
	 * @return an iterator for the indexes of selected items
	 */
	// public NumberSequence getSelected() {
	// return core.getSelected();
	// }

	@Override
	public Iterator<Number[]> getSelectedExtentIterator() {
		return core.getSelectedExtentIterator();
	}

	@Override
	public Iterator<N> getSelectedIterator() {
		return core.getSelectedIterator();
	}

	@Override
	public void addControlListener(ControlListener listener) {
		core.addControlListener(listener);
	}

	@Override
	public void addSelectionListener(SelectionListener listener) {
		core.addSelectionListener(listener);
	}

	@Override
	public void removeControlListener(ControlListener listener) {
		core.removeControlListener(listener);
	}

	@Override
	public void removeSelectionListener(SelectionListener listener) {
		core.removeSelectionListener(listener);
	}

	/*------------------------------------------------------------------------
	 * Moving 
	 */

	/**
	 * Moves a range of items before the target index changing this way their
	 * visible order.
	 * <p>
	 * <code>start</code>, <code>end</code> and <code>target</code>indexes refer
	 * to the model, not the visual position which can be altered by move and
	 * hide operations.
	 * 
	 * @param start
	 *            first index of the range of items
	 * @param end
	 *            last index of the range of items
	 * @param target
	 *            the index of the target item
	 * 
	 * @throws NullPointerException
	 *             if start or end or target is null
	 * @throws IndexOutOfBoundsException
	 *             if start or end or target are out of 0 ...
	 *             {@link #getCount()}-1 bounds
	 * @throws IllegalArgumentException
	 *             if start is greater then end
	 */
	public void move(N start, N end, N target) {
		checkRange(start, end, core.count);
		checkLineIndex(target, "target");
		core.move(start, end, target);
	}

	/**
	 * Removes a range of items decreasing the section item count.
	 * 
	 * @param start
	 *            first index of the range of items
	 * @param end
	 *            last index of the range of items
	 * 
	 * @throws NullPointerException
	 *             if start or end is null
	 * @throws IndexOutOfBoundsException
	 *             if start or end are out of 0 ... {@link #getCount()}-1 bounds
	 * @throws IllegalArgumentException
	 *             if start is greater then end
	 */
	public void delete(N start, N end) {
		checkRange(start, end, core.count);
		core.delete(start, end);
	}

	/**
	 * Inserts a number of items at the given position increasing the section
	 * item count.
	 * <p>
	 * Items are inserted before the given target index or at the end if the
	 * target equals section.getCount().
	 * 
	 * @param target
	 *            the index before which items are inserted
	 * @param count
	 *            the number of items to insert
	 * 
	 * @throws NullPointerException
	 *             if target or count is null
	 * @throws IndexOutOfBoundsException
	 *             if target is out of 0 ... {@link #getCount()}-1 bounds
	 */
	public void insert(N target, N count) {
		Preconditions.checkNotNullWithName(count, "count");
		checkLineIndex(target, "target");
		core.insert(target, count);
	}

	public int hashCode() {
		return core.hashCode();
	}

	public N get(N position) {
		return core.get(position);
	}

	public N indexOf(N item) {
		return core.indexOf(item);
	}

	public N indexOfNotHidden(N index) {
		return core.indexOfNotHidden(index);
	}

	public void setFocusItemEnabled(boolean enabled) {
		core.setFocusItemEnabled(enabled);
	}

	public void setLineWidth(N index, int width) {
		core.setLineWidth(index, width);
	}

	public void setCellWidth(N index, int width) {
		core.setCellWidth(index, width);
	}

	public void setMoveable(N index, boolean enabled) {
		core.setMoveable(index, enabled);
	}

	public void setResizable(N index, boolean enabled) {
		core.setResizable(index, enabled);
	}

	public void setHideable(N index, boolean enabled) {
		core.setHideable(index, enabled);
	}

	public void setHidden(N index, boolean state) {
		core.setHidden(index, state);
	}

	public void setHiddenSelected(boolean state) {
		core.setHiddenSelected(state);
	}

	public N getHiddenCount(N start, N end) {
		return core.getHiddenCount(start, end);
	}

	public void setSelected(N index, boolean state) {
	  core.setSelected(index, state);
	}

	/*------------------------------------------------------------------------
	 * Non public methods 
	 */

	public void checkCellIndex(N index, String name) {
    core.checkCellIndex(index, name);
  }

  public void checkLineIndex(N index, String name) {
    core.checkLineIndex(index, name);
  }

	protected void checkRange(N start, N end, N limit) {
		core.checkRange(start, end, limit);
	};
}
