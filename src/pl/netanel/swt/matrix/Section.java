package pl.netanel.swt.matrix;

import java.text.MessageFormat;
import java.util.Iterator;

import pl.netanel.util.Preconditions;


/**
 * Section represents a continuous segment of a matrix axis, for example a
 * header, body, footer. It contains a number of items indexed by the 
 * <code>&lt;N extends {@link Number}&gt;</code> type parameter.<br>
 * <p>
 * <img src="../../../../../javadoc/images/Section.png"/>
 * <p>
 * Index item width consists of the line width and the cell width - 
 * the line precedes the cell. The last line index equals to section.core.count.
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
 * Section has boolean flags for visibility and navigation enablement. On the
 * diagram above the current item is 0 in body section of column axis and 2 in
 * the body section of row axis. Only one item on the axis can the be the
 * current one, as opposed to each section having its own current item.
 * 
 * 
 * @author Jacek Kolodziejczyk 
 * @created 02-03-2011
 */
public class Section<N extends Number> {
	SectionUnchecked<N> core;

	/**
	 * Constructs a section indexed by int.class, which is equivalent to Integer.class.
	 */
	public Section() {
		this.core = new SectionUnchecked(Math.getInstance(int.class));
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Section)) return false;
		return core.equals(((Section) obj).core);
	}
	
	/**
	 * Constructs a section indexed by the given sub-class of {@link Number}.
	 * 
	 * @param numberClass defines the class used for indexing
	 */
	public Section(Class<N> numberClass) {
		this.core = new SectionUnchecked(Math.getInstance(numberClass));
	}
	
	Section(Math<N> math) {
		this.core = new SectionUnchecked(math);
	}

	@Override
	public String toString() {
		return core.toString();
	}
	
	/**
	 * Specifies the number of section items.
	 * @param count the new count of the receiver's items
	 * <p>
	 * @see #getCount()
	 */
	public void setCount(N count) {
		core.setCount(count);
	}

	/**
	 * Returns the number of sections items. 
	 * @return the number of sections items.
	 * @see #setCount(Number)
	 */
	public N getCount() {
		return core.count;
	}

	/**
     * Returns <code>true</code> if the receiver contains contains no items. 
     * Otherwise <code>false</code> is returned.
     * @return <code>true</code> if the receiver contains contains no items
     */
	public boolean isEmpty() {
		return core.isEmpty();
	}

	
	/*------------------------------------------------------------------------
	 * Section properties
	 */

	/**
	 * Marks the receiver as visible if the argument is <code>true</code>,
	 * and marks it invisible otherwise. 
	 *
	 * @param visible the new visibility state
	 */
	public void setVisible(boolean visible) {
		core.setVisible(visible);
	}

	/**
	 * Returns <code>true</code> if the receiver is visible. 
	 * Otherwise, <code>false</code> is returned.
	 *
	 * @return the receiver's visibility state
	 */
	public boolean isVisible() {
		return core.isVisible();
	}

	/**
	 * Enables current item navigation in the receiver if the argument is <code>true</code>,
	 * and disables it invisible otherwise. 
	 *
	 * @param enabled the new visibility state
	 */
	public void setNavigationEnabled(boolean enabled) {
		core.setNavigationEnabled(enabled);
	}
	
	/**
	 * Returns <code>true</code> if the current item navigation is enabled in the receiver. 
	 * Otherwise, <code>false</code> is returned.
	 *
	 * @return the receiver's visibility state
	 */
	public boolean isNavigationEnabled() {
		return core.isNavigationEnabled();
	}

	
	/*------------------------------------------------------------------------
	 * Default values 
	 */
	
	/**
	 * Sets default width of cells in this section to the given value. 
	 * Negative argument values are ignored.  
	 * <p>
	 * Recommended to set a value of majority of the receiver's items to save on memory storage. 
	 * @param width new value for default width.
	 */
	public void setDefaultCellWidth(int width) {
		if (width < 0) return;
		core.setDefaultCellWidth(width);
	}

	/**
	 * Returns the default cell width of the receiver's items.
	 * @return default width of cells in this section.
	 */
	public int getDefaultCellWidth() {
		return core.getDefaultCellWidth();
	}

	/**
	 * Sets default width of lines in this section to the given value. 
	 * Negative argument values are ignored.
	 * <p>
	 * Recommended to set a value of majority of the receiver's items to save on memory storage.
	 * Also any additional 
	 * @param width new value for default width.
	 */
	public void setDefaultLineWidth(int width) {
		if (width < 0) return; 
		core.setDefaultLineWidth(width);
	}

	
	/**
	 * Returns the default line width of the receiver's items.
	 * @return default width of lines in this section.
	 */
	public int getDefaultLineWidth() {
		return core.getDefaultLineWidth();
	}

	/**
	 * Returns <code>true</code> if the section items can be resized by default. 
	 * Otherwise, <code>false</code> is returned.
	 * @return the default resizable ability state of the section items
	 */
	public boolean isDefaultResizable() {
		return core.isDefaultResizable();
	}

	/**
	 * Sets the default resize ability of the receiver's items to the given argument. 
	 * <p>
	 * Recommended to set a value of majority of the receiver's items to save on memory storage.
	 * <p>
	 * The value of the <code>resizable</code> argument is returned by 
	 * {@link #isResizable(Number)} method if a different value for the particular index 
	 * has not been set by {@link #setResizable(Number, Number, boolean)}.
	 * 
	 * @param resizable the new resize ability state
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
	 * Sets the default move ability of the receiver's items to the given argument. 
	 * <p>
	 * Recommended to set a value of majority of the receiver's items to save on memory storage.
	 * <p>
	 * The value of the <code>movable</code> argument is returned by 
	 * {@link #isMoveable(Number)} method if a different value for the particular index 
	 * has not been set by {@link #setMoveable(Number, Number, boolean)}.
	 * 
	 * @param moveable the new move ability state
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
	 * Sets the default hide ability of the receiver's items to the given argument. 
	 * <p>
	 * Recommended to set a value of majority of the receiver's items to save on memory storage.
	 * <p>
	 * The value of the <code>hideable</code> argument is returned by 
	 * {@link #isHideable(Number)} method if a different value for the particular index 
	 * has not been set by {@link #setHideable(Number, Number, boolean)}.
	 * 
	 * @param hideable the new hide ability state
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
	 * <code>start</code> and <code>end</code> indexes refer to the model, not the visual position 
	 * which can be altered by move and hide operations. 
	 * <code>width</code> that is lower then zero is ignored. 
	 *  
	 * @param start first index of the range of items  
	 * @param end last index of the range of items  
	 * @param width the new line width
	 * @throws IndexOutOfBoundsException if start or end are out of 0 ... {@link #getCount()} bounds
	 * @throws IllegalArgumentException if start is greater then end
	 */ 
	public void setLineWidth(N start, N end, int width) {
		if (width < 0) return;
		checkRange(start, end, core.math.increment(core.count));
		core.setLineWidth(start, end, width);
	}
	
	/**
	 * Returns the stored line width at the given index. Or the default line width 
	 * if the width has not been set at this index.
	 * <p> 
	 * <code>index</code> refers to the model, not the visual position
	 * which can be altered by move and hide operations.
	 * 
	 * @param index the item index 
	 * @return the line width at the given item index in the receiver
	 * @throws IndexOutOfBoundsException if index is out of 0 ... {@link #getCount()}-1 bounds
	 */
	public int getLineWidth(N index) {
		checkIndex(index, core.math.increment(core.count), "index");
		return core.getLineWidth(index);
	}

	/**
	 * Sets the line width for the range of items.
	 * <p>
	 * <code>start</code> and <code>end</code> indexes refer to the model, not the visual position 
	 * which can be altered by move and hide operations. 
	 * <code>width</code> that is lower the zero is ignored. 
	 *
	 * @param start first index of the range of items  
	 * @param end last index of the range of items  
	 * @param width the new cell width
	 * @throws IndexOutOfBoundsException if start or end are out of 0 ... {@link #getCount()}-1 bounds
	 * @throws IllegalArgumentException if start is greater then end
	 */ 
	public void setCellWidth(N start, N end, int width) {
		if (width < 0) return;
		checkRange(start, end, core.count);
		core.setCellWidth(start, end, width);
	}
	
	/**
	 * Returns the stored cell width at the given index in the receiver. 
	 * Or the default cell width if the width has not been set at this index.
	 * <p>
	 * <code>index</code> refers to the model, not the visual position 
	 * which can be altered by move and hide operations. 
	 * 
	 * @param index the item index 
	 * @return the cell width at the given item index in the receiver
	 * @throws IndexOutOfBoundsException if index is out of 0 ... {@link #getCount()}-1 bounds
	 */
	public int getCellWidth(N index) {
		checkIndex(index, core.count, "index");
		return core.getCellWidth(index);
	}
	
	/**
	 * Sets the move ability for the range of items. 
	 * An item that is moveable can be reordered by the user by dragging the header. 
	 * An item that is not moveable cannot be dragged by the user but may be reordered by the programmer.
	 * <p>
	 * <code>start</code> and <code>end</code> indexes refer to the model, not the visual position
	 * which can be altered by move and hide operations.  
	 *
	 * @param start first index of the range of items  
	 * @param end last index of the range of items  
	 * @param enabled the new move ability state
	 * @throws NullPointerException if the start or end is null
	 * @throws IndexOutOfBoundsException if start or end are out of 0 ... {@link #getCount()}-1 bounds
	 * @throws IllegalArgumentException if start is greater then end
	 */ 
	public void setMoveable(N start, N end, boolean enabled) {
		checkRange(start, end, core.count);
		core.setMoveable(start, end, enabled);
	}

	/**
	 * Returns <code>true</code> if the item at given index can be moved by end user.
	 * Otherwise, <code>false</code> is returned. 
	 * An item that is moveable can be reordered by the user by dragging the header. 
	 * An item that is not moveable cannot be dragged by the user but may be reordered by the programmer.
	 * <p>
	 * Returns the stored item move ability at given index  
	 * or the default item move ability if it has not been set at this index. 
	 * <p>
	 * <code>index</code> refers to the model, not the visual position
	 * which can be altered by move and hide operations. 
	 * 
	 * @param index the item index 
	 * @return the move ability state at the given index
	 * @throws NullPointerException if the index is null
	 * @throws IndexOutOfBoundsException if index is out of 0 ... {@link #getCount()}-1 bounds
	 */
	public boolean isMoveable(N index) {
		checkIndex(index, core.count, "index");
		return core.isMoveable(index);
	}

	/**
	 * Sets the resize ability for the range of items.
	 * An item that is resizable can be resized by the user dragging the edge of the header. 
	 * An item that is not resizable cannot be dragged by the user but may be resized by the programmer.
	 * <p>
	 * <code>start</code> and <code>end</code> indexes refer to the model, not the visual position
	 * which can be altered by move and hide operations.  
	 *
	 * @param start first index of the range of items  
	 * @param end last index of the range of items  
	 * @param enabled the new resize ability state
	 * @throws IndexOutOfBoundsException if start or end are out of 0 ... {@link #getCount()}-1 bounds
	 * @throws IllegalArgumentException if start is greater then end
	 */ 
	public void setResizable(N start, N end, boolean enabled) {
		checkRange(start, end, core.count);
		core.setResizable(start, end, enabled);
	}
	
	/**
	 * Returns <code>true</code> if the item at given index can be resized by end user.
	 * Otherwise, <code>false</code> is returned.
	 * An item that is resizable can be resized by the user dragging the edge of the header. 
	 * An item that is not resizable cannot be dragged by the user but may be resized by the programmer.
	 * <p>
	 * Returns the stored item resize ability at given index  
	 * or the default item resize ability if it has not been set at this index. 
	 * <p>
	 * <code>index</code> refers to the model, not the visual position
	 * which can be altered by move and hide operations. 
	 * 
	 * @param index the item index 
	 * @return the move ability state at the given index
	 * @throws NullPointerException if the index is null
	 * @throws IndexOutOfBoundsException if index is out of 0 ... {@link #getCount()}-1 bounds
	 */
	public boolean isResizable(N index) {
		checkIndex(index, core.count, "index");
		return core.isResizable(index);
	}
	
	/**
	 * Sets the hide ability for the range of items.
	 * An item that is hideable can be hidden by the user gesture bound to the hide command. 
	 * An item that is not hideable cannot be hidden by the user but may be hidden by the programmer.
	 * <p>
	 * <code>start</code> and <code>end</code> indexes refer to the model, not the visual position
	 * which can be altered by move and hide operations.  
	 *
	 * @param start first index of the range of items  
	 * @param end last index of the range of items  
	 * @param enabled the new hide ability state
	 * @throws NullPointerException if the start or end is null
	 * @throws IndexOutOfBoundsException if start or end are out of 0 ... {@link #getCount()}-1 bounds
	 * @throws IllegalArgumentException if start is greater then end
	 */ 
	public void setHideable(N start, N end, boolean enabled) {
		checkRange(start, end, core.count);
		core.setHideable(start, end, enabled);
	}
	
	/**
	 * Returns <code>true</code> if the item at given index can be hidden by end user.
	 * Otherwise, <code>false</code> is returned.
	 * An item that is hideable can be hidden by the user gesture bound to the hide command. 
	 * An item that is not hideable cannot be hidden by the user but may be hidden by the programmer.
	 * <p>
	 * Returns the stored item hide ability at given index  
	 * or the default item resize ability if it has not been set at this index. 
	 * <p>
	 * <code>index</code> refers to the model, not the visual position
	 * which can be altered by move and hide operations. 
	 * 
	 * @param index the item index 
	 * @return the hide ability state at the given index
	 * @throws NullPointerException if the index is null
	 * @throws IndexOutOfBoundsException if index is out of 0 ... {@link #getCount()}-1 bounds
	 * @throws IndexOutOfBoundsException if index is out of 0 ... {@link #getCount()}-1 bounds
	 */
	public boolean isHideable(N index) {
		checkIndex(index, core.count, "index");
		return core.isHideable(index);
	}


	/*------------------------------------------------------------------------
	 * Hiding 
	 */
	
	/**
	 * Sets the hiding state for the range of items.
	 * <p>
	 * <code>start</code> and <code>end</code> indexes refer to the model, not the visual position
	 * which can be altered by move and hide operations.  
	 *
	 * @param start first index of the range of items  
	 * @param end last index of the range of items  
	 * @param state the new hiding state

	 * @throws NullPointerException if start or end is null
	 * @throws IndexOutOfBoundsException if start or end are out of 0 ... {@link #getCount()}-1 bounds
	 * @throws IllegalArgumentException if start is greater then end
	 */ 
	public void setHidden(N start, N end, boolean state) {
		checkRange(start, end, core.count);
		core.setHidden(start, end, state);
	}

	/**
	 * Returns <code>true</code> if the item at given index is hidden.
	 * Otherwise, <code>false</code> is returned.
	 * <p>
	 * <code>index</code> refers to the model, not the visual position
	 * which can be altered by move and hide operations. 
	 * 
	 * @param index the item index 
	 * @return the hiding state at the given index
	 * 
	 * @throws NullPointerException if the index is null
	 * @throws IndexOutOfBoundsException if index is out of 0 ... {@link #getCount()}-1 bounds
	 */
	public boolean isHidden(N index) {
		checkIndex(index, core.count, "index");
		return core.isHidden(index);
	}

	/**
	 * Returns the number of hidden items.
	 * @return the number of hidden items 
	 */
	public N getHiddenCount() {
		return core.getHiddenCount();
	}

	/**
	 * Returns an iterator for the indexes of the hidden items.
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
	 * <code>start</code> and <code>end</code> indexes refer to the model, not the visual position
	 * which can be altered by move and hide operations.  
	 *
	 * @param start first index of the range of items  
	 * @param end last index of the range of items  
	 * @param state the new selection state

	 * @throws NullPointerException if start or end is null
	 * @throws IndexOutOfBoundsException if start or end are out of 0 ... {@link #getCount()}-1 bounds
	 * @throws IllegalArgumentException if start is greater then end
	 */ 
	public void setSelected(N start, N end, boolean state) {
		checkRange(start, end, core.count);
		core.setSelected(start, end, state);
	}

	/**
	 * Sets the selection state for all the items.
	 * 
	 * @param state the new selection state
	 */ 
	public void setSelectedAll(boolean selected) {
		core.setSelectedAll(selected);
	}
	
	/**
	 * Returns <code>true</code> if the item at given index is selected.
	 * Otherwise, <code>false</code> is returned.
	 * <p>
	 * <code>index</code> refers to the model, not the visual position
	 * which can be altered by move and hide operations. 
	 * 
	 * @param index the item index 
	 * @return the selection state at the given index
	 * 
	 * @throws NullPointerException if the index is null
	 * @throws IndexOutOfBoundsException if index is out of 0 ... {@link #getCount()}-1 bounds
	 */
	public boolean isSelected(N index) {
		checkIndex(index, core.count, "index");
		return core.isSelected(index);
	}
	
	/**
	 * Returns the number of selected items.
	 * @return the number of hidden items 
	 */
	public N getSelectedCount() {
		return core.getSelectedCount();
	}

	/** 
	 * Returns an iterator for the indexes of selected items.
	 * @return an iterator for the indexes of selected items
	 */
	public Iterator getSelected() {
		return core.getSelected();
	}

	
	
	/*------------------------------------------------------------------------
	 * Moving 
	 */
	
	/**
	 * Moves a range of items before the target index changing this way their visible order.
	 * <p>
	 * <code>start</code>, <code>end</code> and <code>target</code>indexes refer to the model, 
	 * not the visual position which can be altered by move and hide operations. 
	 *
	 * @param start first index of the range of items  
	 * @param end last index of the range of items  
	 * @param target the index of the target item

	 * @throws NullPointerException if start or end or target is null
	 * @throws IndexOutOfBoundsException if start or end or target are 
	 * 		   out of 0 ... {@link #getCount()}-1 bounds
	 * @throws IllegalArgumentException if start is greater then end  
	 */
	public void move(N start, N end, N target) {
		checkRange(start, end, core.count);
		checkIndex(target, core.math.increment(core.count), "target");
		core.move(start, end, target);
	}
	

	
	/*------------------------------------------------------------------------
	 * Non public methods 
	 */

	private void checkRange(N start, N end, N limit) {
		Preconditions.checkNotNull(start, "start");
		Preconditions.checkNotNull(end, "end");
		if (core.math.compare(start, core.math.ZERO_VALUE()) < 0) {
			throw new IndexOutOfBoundsException(MessageFormat.format(
				"start ({0}) cannot be negative", start)) ;
		}
		if (core.math.compare(end, core.math.ZERO_VALUE()) < 0) {
			throw new IndexOutOfBoundsException(MessageFormat.format(
				"end ({0}) cannot be negative", end)) ;
		}
		if (core.math.compare(start, limit) >= 0) {
			throw new IndexOutOfBoundsException(MessageFormat.format(
				"start ({0}) must be lower then limit {1}", start, limit)) ;
		}
		if (core.math.compare(end, limit) >= 0) {
			throw new IndexOutOfBoundsException(MessageFormat.format(
				"end ({0}) must be lower then limit {1}", end, limit)) ;
		}
		if (core.math.compare(start, end) > 0) {
			throw new IllegalArgumentException(MessageFormat.format(
				"start ({0}) cannot be greater then end {1}", start, end)) ;
		}
	}
	
	private void checkIndex(N index, N limit, String name) {
		Preconditions.checkNotNull(index, "index");
		if (core.math.compare(index, core.math.ZERO_VALUE()) < 0) {
			throw new IndexOutOfBoundsException(MessageFormat.format(
					"index ({0}) cannot be negative", index)) ;
		}
		if (core.math.compare(index, limit) >= 0) {
			throw new IndexOutOfBoundsException(MessageFormat.format(
					"index ({0}) must be lower then limit {1}", index, limit)) ;
		}
	}
}
