package pl.netanel.swt.matrix;

import java.util.Iterator;

import pl.netanel.util.ImmutableIterator;

/**
 * Section represents a continuous segment of a matrix axis, for example a
 * header, body, footer. It contains a number of items indexed by the 
 * <code>&lt;N extends {@link Number}&gt;</code> type parameter.<br>
 * <p>
 * <img src="../../../../../javadoc/images/Section.png"/>
 * <p>
 * Index item width consists of the line width and the cell width - 
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
 * Section has boolean flags for visibility and navigation enablement. On the
 * diagram above the current item is 0 in body section of column axis and 2 in
 * the body section of row axis. Only one item on the axis can the be the
 * current one, as opposed to each section having its own current item.
 * 
 * 
 * @author Jacek Kolodziejczyk created 02-03-2011
 */
class SectionUnchecked<N extends Number> {

	static final int DEFAULT_CELL_WIDTH = 16;
	static final int DEFAULT_LINE_WIDTH = 1;
	
	final Math<N> math;
	N count;
	
	final NumberOrder<N> order;
	final NumberSet<N> hidden;
	private final NumberSet<N> resizable;
	private final NumberSet<N> moveable;
	private final NumberSet<N> hideable;
	private final IntAxisState cellWidth;
	private final IntAxisState lineWidth;
	private final ObjectAxisState<N> cellSpan;
	
	private final NumberQueueSet<N> selection;
	private final NumberQueueSet<N> lastSelection;

	
	private boolean defaultResizable, defaultMoveable, defaultHideable; 
	private boolean isNavigationEnabled, isVisible;
	
	Axis<N> axis;
	int index; 
	
	/**
	 * Constructs a section indexed by int.class.
	 */
	public SectionUnchecked() {
		this(Math.getInstance(int.class));
	}

	/**
	 * Constructs a section indexed by the given sub-class of {@link Number}.
	 * 
	 * @param numberClass defines the class used for indexing
	 */
	public SectionUnchecked(Class<N> numberClass) {
		this(Math.getInstance(numberClass));
	}
	
	SectionUnchecked(Math<N> math) {
		super();
		this.math = math;
		count = math.ZERO_VALUE();
		
		order = new NumberOrder<N>(math);
		hidden = new NumberSet(math, true);
		resizable = new NumberSet(math, true);
		moveable = new NumberSet(math, true);
		hideable = new NumberSet(math, true);
		
		cellWidth = new IntAxisState(math, DEFAULT_CELL_WIDTH);
		lineWidth = new IntAxisState(math, DEFAULT_LINE_WIDTH);
		cellSpan = new ObjectAxisState(math, 1);
		
		selection = new NumberQueueSet(math);
		lastSelection = new NumberQueueSet(math);
		
		isNavigationEnabled = isVisible = true;
	}
	

	@Override
	public String toString() {
		return Integer.toString(index);
	}

	/*------------------------------------------------------------------------
	 * Collection like  
	 */

	/**
	 * Specifies the number of section items.
	 * <p>
	 * Section does not have add/remove methods for items, since item does not have a notion of content.
	 * It is only a reference to the data element. Whenever the data model adds / remove elements then 
	 * the section should adjusts its item count to match the current size the data model.
	 * @see #getCount()
	 */
	public void setCount(N count) {
		this.count = count;
		order.setCount(count);
	}
	
	/**
	 * Returns the number of sections items. 
	 * @return the number of sections items.
	 * @see #setCount(Number)
	 */
	public N getCount() {
		return count;
	}

	/**
     * Returns <tt>true</tt> if this contains contains no items.
     *
     * @return <tt>true</tt> if this contains contains no items
     */
	public boolean isEmpty() {
		return order.items.isEmpty();
	}

	/**
	 * Returns the item at given visual position according 
	 * to the current order of items and skipping the hidden ones.
	 * 
	 * @param position visual index of given item
	 * @return item at the given position
	 * 
	 * @see #indexOf(Number)
	 */
	public N get(N position) {
		if (math.compare(position, getVisibleCount()) >= 0) return null;
		
		MutableNumber<N> pos1 = math.create(0);
		MutableNumber<N> pos2 = math.create(0);
		
		for (int i = 0, size = order.items.size(); i < size; i++) {
			Extent<N> e = order.items.get(i);
			pos2.add(e.end).subtract(e.start).subtract(hidden.getCount(e.start(), e.end()));
			if (math.compare(pos2, position) >= 0) {
				MutableNumber count = hidden.getCount(e.start(), pos1.getValue());
				return pos1.subtract(position).negate().add(e.start).add(count).getValue();
			}
			pos2.increment();
			pos1.set(pos2); 
		}
		return null;
	}
	
	/**
	 * Returns the visual position of the given item according to the current order of items.
	 * Hidden items are included. 
	 * If the item is null or out of scope then the method returns null.
	 *  
	 * @param item to get the position for
	 * @return visual position of the item at the given index
	 * 
	 * @see #indexOfNotHidden(Number)
	 */
	public N indexOf(N item) {
		return item == null ? null : order.indexOf(item);
	}
	
	
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
	public N indexOfNotHidden(N index) {
		if (index == null || hidden.contains(index)) return null;
		MutableNumber<N> hiddenCount = math.create(0);
		MutableNumber<N> pos1 = math.create(0);
		MutableNumber<N> pos2 = math.create(0);
		
		for (int i = 0, size = order.items.size(); i < size; i++) {
			Extent<N> e = order.items.get(i);
			boolean contains = math.contains(e, index);
			hiddenCount.add(hidden.getCount(e.start(), contains ? index : e.end()));
			if (contains) {
				return pos2.add(index).subtract(e.start).subtract(hiddenCount).getValue();
			}
			pos1.set(pos2);
			pos2.add(e.end).subtract(e.start).increment(); //.subtract(hiddenCount);
		}
		return null;
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
		this.isVisible = visible;
	}
	
	/**
	 * Returns <code>true</code> if the receiver is visible. 
	 * Otherwise, <code>false</code> is returned.
	 *
	 * @return the receiver's visibility state
	 */
	public boolean isVisible() {
		return isVisible;
	}

	/**
	 * Enables current item navigation in the receiver if the argument is <code>true</code>,
	 * and disables it invisible otherwise. 
	 *
	 * @param enabled the new visibility state
	 */
	public void setNavigationEnabled(boolean enabled) {
		this.isNavigationEnabled = enabled;
	}
	
	/**
	 * Returns <code>true</code> if the current item navigation is enabled in the receiver. 
	 * Otherwise, <code>false</code> is returned.
	 *
	 * @return the receiver's visibility state
	 */
	public boolean isNavigationEnabled() {
		return isNavigationEnabled;
	}

	
	
	/*------------------------------------------------------------------------
	 * Default values 
	 */
	
	/**
	 * Sets default width of cells in this section to the given value.  
	 * <p>
	 * This allows to save storage memory of the width attribute if many cells share the same value. 
	 * @param width new value for default width.
	 */
	public void setDefaultCellWidth(int width) {
		cellWidth.setDefault(width);
	}
	
	/**
	 * Returns the default width of cells in this 
	 * @return default width of cells in this 
	 */
	public int getDefaultCellWidth() {
		return cellWidth.getDefault();
	}
	
	/**
	 * Sets default width of lines in this section to the given value.  
	 * <p>
	 * This allows to save storage memory of the width attribute if many lines share the same value. 
	 * @param width new value for default width.
	 */
	public void setDefaultLineWidth(int width) {
		lineWidth.setDefault(width);
	}
	
	/**
	 * Returns the default width of lines in this 
	 * @return default width of lines in this 
	 */
	public int getDefaultLineWidth() {
		return lineWidth.getDefault();
	}
	
	
	/**
	 * Returns <code>true</code> if the section items can be resized by default. 
	 * Otherwise, <code>false</code> is returned.
	 *
	 * @return the default resizable ability state of the section items
	 */
	public boolean isDefaultResizable() {
		return defaultResizable;
	}

	/**
	 * Enables all section items to be resizable by default if the argument is <code>true</code>,
	 * and non element can be resized by default.
	 * <p>
	 * The resizable state can be set for individual items with 
	 * {@link #setResizable(Number, Number, boolean)} 
	 *
	 * @param resizable the new resize ability state
	 */
	public void setDefaultResizable(boolean resizable) {
		this.defaultResizable = resizable;
	}
	
	/**
	 * Returns <code>true</code> if the section items can be moved by default. 
	 * Otherwise, <code>false</code> is returned.
	 *
	 * @return the default move ability state of the section items
	 */
	public boolean isDefaultMoveable() {
		return defaultMoveable;
	}

	/**
	 * Enables all section items to be hideable by default if the argument is <code>true</code>,
	 * and non element can be hidden by default. 
	 * <p>
	 * The moveable state can be set for individual items with 
	 * {@link #setMoveable(Number, Number, boolean)}
	 *
	 * @param moveable the new move ability state
	 */
	public void setDefaultMoveable(boolean moveable) {
		this.defaultMoveable = moveable;
	}

	/**
	 * Returns <code>true</code> if the section items can be hidden by default. 
	 * Otherwise, <code>false</code> is returned.
	 *
	 * @return the default hide ability state of the section items
	 */
	public boolean isDefaultHideable() {
		return defaultHideable;
	}

	/**
	 * Enables all section items to be hideable by default if the argument is <code>true</code>,
	 * and non element can be hidden by default. 
	 * <p>
	 * The moveable state can be set for individual items with 
	 * {@link #setMoveable(Number, Number, boolean)}
	 * @param hideable the new hide ability state
	 */
	public void setDefaultHideable(boolean hideable) {
		this.defaultHideable = hideable;
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
		lineWidth.setValue(start, end, width);
	}
	
	/**
	 * Returns the stored line width at the given index in the receiver 
	 * without argument checking or the default line width 
	 * if the width has not been set at this index.
	 * <p>
	 * Argument is not checked for validity to sustain a better performance. It's useful in loops.
	 * <p> 
	 * <code>index</code> refers to the model, not the visual position
	 * which can be altered by move and hide operations.
	 * 
	 * @param index the item index 
	 * @return the line width at the given item index in the receiver
	 */
	public int getLineWidth(N index) {
		return lineWidth.getValue(index);
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
		cellWidth.setValue(start, end, width);
	}
	
	/**
	 * Returns the stored cell width at the given index in the receiver 
	 * without argument checking or the default cell width 
	 * if the width has not been set at this index.
	 * <p>
	 * Argument is not checked for validity to sustain a better performance. It's useful in loops.
	 * <p>
	 * <code>index</code> refers to the model, not the visual position
	 * which can be altered by move and hide operations. 
	 * 
	 * @param index the item index 
	 * @return the cell width at the given item index in the receiver
	 */
	public int getCellWidth(N index) {
		return cellWidth.getValue(index);
	}

	
	
	/**
	 * Sets the move ability for the range of items. 
	 * An item that is moveable can be reordered by the user by dragging the header. 
	 * An item that is not moveable cannot be dragged by the user but may be reordered by the programmer.
	 * <p>
	 * <code>start</code> and <code>end</code> indexes refer to the model, not the visual position
	 * which can be altered by move and hide operations. Width that is lower then zero is ignored. 
	 *
	 * @param start first index of the range of items  
	 * @param end last index of the range of items  
	 * @param enabled the new move ability state
	 * @throws NullPointerException if the start or end is null
	 * @throws IndexOutOfBoundsException if start or end are out of 0 ... {@link #getCount()}-1 bounds
	 * @throws IllegalArgumentException if start is greater then end
	 */ 
	public void setMoveable(N start, N end, boolean enabled) {
		moveable.change(start, end, enabled != defaultMoveable);
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
		return moveable.contains(index) != defaultMoveable;
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
	 * Argument is not checked for validity to sustain a better performance. It's useful in loops.
	 * <p>
	 * <code>index</code> refers to the model, not the visual position
	 * which can be altered by move and hide operations. 
	 * 
	 * @param index the item index 
	 * @return the move ability state at the given index
	 */
	public boolean isMoveableUnchecked(N index) {
		return moveable.contains(index) != defaultMoveable;
	}
	
	/**
	 * Sets the resize ability for the range of items.
	 * An item that is resizable can be resized by the user dragging the edge of the header. 
	 * An item that is not resizable cannot be dragged by the user but may be resized by the programmer.
	 * <p>
	 * <code>start</code> and <code>end</code> indexes refer to the model, not the visual position
	 * which can be altered by move and hide operations. Width that is lower then zero is ignored. 
	 *
	 * @param start first index of the range of items  
	 * @param end last index of the range of items  
	 * @param enabled the new resize ability state
	 * @throws IndexOutOfBoundsException if start or end are out of 0 ... {@link #getCount()}-1 bounds
	 * @throws IllegalArgumentException if start is greater then end
	 */ 
	public void setResizable(N start, N end, boolean enabled) {
		resizable.change(start, end, enabled != defaultResizable);
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
	 * Argument is not checked for validity to sustain a better performance. It's useful in loops.
	 * <p>
	 * <code>index</code> refers to the model, not the visual position
	 * which can be altered by move and hide operations. 
	 * 
	 * @param index the item index 
	 * @return the move ability state at the given index
	 */
	public boolean isResizable(N index) {
		return resizable.contains(index) != defaultResizable;
	}
	
	/**
	 * Sets the hide ability for the range of items.
	 * An item that is hideable can be hidden by the user gesture bound to the hide command. 
	 * An item that is not hideable cannot be hidden by the user but may be hidden by the programmer.
	 * <p>
	 * <code>start</code> and <code>end</code> indexes refer to the model, not the visual position
	 * which can be altered by move and hide operations. Width that is lower then zero is ignored. 
	 *
	 * @param start first index of the range of items  
	 * @param end last index of the range of items  
	 * @param enabled the new hide ability state
	 * @throws NullPointerException if the start or end is null
	 * @throws IndexOutOfBoundsException if start or end are out of 0 ... {@link #getCount()}-1 bounds
	 * @throws IllegalArgumentException if start is greater then end
	 */ 
	public void setHideable(N start, N end, boolean enabled) {
		hideable.change(start, end, enabled != defaultHideable);
	}
	
	/**
	 * Returns <code>true</code> if the item at given index can be hidden by end user.
	 * Otherwise, <code>false</code> is returned.
	 * An item that is hideable can be hidden by the user gesture bound to the hide command. 
	 * An item that is not hideable cannot be hidden by the user but may be hidden by the programmer.
	 * <p>
	 * Returns the stored item hide ability at given index  
	 * or the default item hide ability if it has not been set at this index. 
	 * <p>
	 * Argument is not checked for validity to sustain a better performance. It's useful in loops.
	 * <p>
	 * <code>index</code> refers to the model, not the visual position
	 * which can be altered by move and hide operations. 
	 * 
	 * @return the hide ability state at the given index
	 */
	public boolean isHideable(N index) {
		return hideable.contains(index) != defaultHideable;
	}

	
	/*------------------------------------------------------------------------
	 * Hiding 
	 */
	
	/**
	 * Sets the hiding state for the range of items.
	 * <p>
	 * <code>start</code> and <code>end</code> indexes refer to the model, not the visual position
	 * which can be altered by move and hide operations. Width that is lower then zero is ignored. 
	 *
	 * @param start first index of the range of items  
	 * @param end last index of the range of items  
	 * @param state the new hiding state

	 * @throws NullPointerException if start or end is null
	 * @throws IndexOutOfBoundsException if start or end are out of 0 ... {@link #getCount()}-1 bounds
	 * @throws IllegalArgumentException if start is greater then end
	 */ 
	public void setHidden(N start, N end, boolean state) {
		hidden.change(start, end, state);
	}

	/**
	 * Sets the hiding state for the items that are selected.
	 * <p>
	 * <code>start</code> and <code>end</code> indexes refer to the model, not the visual position
	 * which can be altered by move and hide operations. Width that is lower then zero is ignored. 
	 *
	 * @param state the new hiding state
	 * 
	 * @throws NullPointerException if start or end is null
	 * @throws IndexOutOfBoundsException if start or end are out of 0 ... {@link #getCount()}-1 bounds
	 * @throws IllegalArgumentException if start is greater then end
	 * <p>
	 * @see #setSelected(Number, Number, boolean)
	 */ 
	public void setHiddenSelected(boolean state) {
		for (int i = 0, imax = selection.items.size(); i < imax; i++) {
			Extent<N> e = selection.items.get(i);
			setHidden(e.start(), e.end(), state);
		}
	}
	
	/**
	 * Returns <code>true</code> if the item at given index is hidden.
	 * Otherwise, <code>false</code> is returned.
	 * <p>
	 * Argument is not checked for validity to sustain a better performance. It's useful in loops.
	 * <p>
	 * <code>index</code> refers to the model, not the visual position
	 * which can be altered by move and hide operations. 
	 * 
	 * @param index the item index 
	 * @return the hiding state at the given index
	 */
	public boolean isHidden(N index) {
		return hidden.contains(index);
	}
	
	/**
	 * Returns the number of hidden items in given range of items.
	 * 
	 * <code>start</code> and <code>end</code> indexes refer to the model, not the visual position
	 * which can be altered by move and hide operations. Width that is lower then zero is ignored. 
	 *
	 * @param start first index of the range of items  
	 * @param end last index of the range of items  

	 * @throws NullPointerException if start or end is null
	 * @throws IndexOutOfBoundsException if start or end are out of 0 ... {@link #getCount()}-1 bounds
	 * @throws IllegalArgumentException if start is greater then end
	 * @return the number of hidden items 
	 */
	public N getHiddenCount(N start, N end) {
		return hidden.getCount(start, end).getValue();
	}
	
	/**
	 * Returns the number of hidden items.
	 * @return the number of hidden items 
	 */
	public N getHiddenCount() {
		return hidden.getCount().getValue();
	}
	
	/**
	 * Returns iterator for indexes of the hidden items.
	 * 
	 * @return the hidden items iterator
	 */
	public Iterator<N> getHidden() {
		return new IndexIterator(new IndexSequence(hidden));
	}
	
	
	
	/*------------------------------------------------------------------------
	 * Selection 
	 */
	/**
	 * Sets the selection state for the range of items.
	 * <p>
	 * <code>start</code> and <code>end</code> indexes refer to the model, not the visual position
	 * which can be altered by move and hide operations. Width that is lower then zero is ignored. 
	 *
	 * @param start first index of the range of items  
	 * @param end last index of the range of items  
	 * @param state the new selection state

	 * @throws NullPointerException if start or end is null
	 * @throws IndexOutOfBoundsException if start or end are out of 0 ... {@link #getCount()}-1 bounds
	 * @throws IllegalArgumentException if start is greater then end
	 */ 
	public void setSelected(N start, N end, boolean state) {
		selection.change(start, end, state);
		
		if (axis != null) {
			axis.selectInZones(this, start, end);
		}
	}
	
	/**
	 * Sets the selection state for all the items.
	 * 
	 * @param state the new selection state
	 */ 
	public void setSelectedAll(boolean selected) {
		if (selected) {
			selection.add(math.ZERO_VALUE(), math.decrement(count));
		} else {
			selection.clear();
		}
	}
	
	/**
	 * Returns <code>true</code> if the item at given index is selected.
	 * Otherwise, <code>false</code> is returned.
	 * <p>
	 * Argument is not checked for validity to sustain a better performance. It's useful in loops.
	 * <p>
	 * <code>index</code> refers to the model, not the visual position
	 * which can be altered by move and hide operations. 
	 * 
	 * @param index the item index 
	 * @return the selection state at the given index
	 */
	public boolean isSelected(N index) {
		return selection.contains(index);
	}
	
	/**
	 * Returns the number of selected items in given range of items.
	 * 
	 * <code>start</code> and <code>end</code> indexes refer to the model, not the visual position
	 * which can be altered by move and hide operations. Width that is lower then zero is ignored. 
	 *
	 * @param start first index of the range of items  
	 * @param end last index of the range of items  

	 * @throws NullPointerException if start or end is null
	 * @throws IndexOutOfBoundsException if start or end are out of 0 ... {@link #getCount()}-1 bounds
	 * @throws IllegalArgumentException if start is greater then end
	 * @return the number of hidden items 
	 */
	public N getSelectedCount() {
		return selection.getCount().getValue();
	}
	
	public Iterator<N> getSelected() {
		return new IndexIterator(new IndexSequence(selection));
	}
	
	public void backupSelection() {
		lastSelection.replace(selection);
	}
	
	public void restoreSelection() {
		selection.replace(lastSelection);
	}
	
	
	
	/*------------------------------------------------------------------------
	 * Moving
	 */
	
	public void move(N start, N end, N target) {
		order.move(start, end, target);
	}
	
	
	
	/*------------------------------------------------------------------------
	 * Non public 
	 */
	
	N getVisibleCount() {
		return math.create(count).subtract(hidden.getCount()).getValue();
	}
	
	N getCellSpan(N index) {
		return cellSpan.getValue(index);
	}

	N nextNotHiddenIndex(N item, int direction) {
		for (Extent e: hidden.items) {
			if (math.contains(e, item)) {
				if (direction > 0) {
					item = math.increment(e.end());
					if (math.compare(item, count) >= 0) {
						item = null;
					}
				} else {
					item = math.decrement(e.start());
					if (math.compare(item, math.ZERO_VALUE()) < 0) {
						item = null;
					}
				}
				break;
			}
		}
		return item;
	}

	ExtentSequence<N> getSelectedExtentSequence() {
		return new ExtentSequence(selection.items);
	}
	
	ExtentSequence<N> getSelectedExtentResizableSequence() {
		return new ExtentSequence<N>(selection.items) {
//			private int j;
//			@Override
//			public void init() {
//				super.init();
//				j = -1;
//			}
			@Override
			public boolean next() {
				return super.next();
//				if (++i >= items.size() ) return false;
//				Extent<N> e = items.get(i);
//				start = e.start();
//				end = e.end();
//				
//				boolean quit = false;
//				while (++j < resizable.items.size()) {
//					Extent<N> e2 = resizable.items.get(j);
//					int location = math.compare(e.start(), e.end(), e2.start(), e2.end());
//					switch (location) {
//					case AFTER: 			continue;
//					case BEFORE: 		
//						j = resizable.items.size(); // Quit the loop
//						break;
//					
//					case CROSS_BEFORE:	
//						start = math.increment(end);
//						break;
//						
//					case CROSS_AFTER:	
//						item.end.set(math.max(math.decrement(start), item.start()));
//						break;
//						
//					case EQUAL:	
//					case OVERLAP:	
//						toRemove.add(0, item);
//						break;
//						
//					case INSIDE:
//						MutableNumber newEnd = item.end.copy();
//						item.end.set(math.max(math.decrement(start), item.start()));
//						items.add(i+1, new Extent(math.create(end).increment(), newEnd));
//					}
//					
//					
//					int compare = math.compare(e.start(), e.end(), e2.start(), e2.end());
//					switch (compare) {
//					case AFTER: 		quit = true; break; 
//					case EQUAL:
//					case INSIDE:		return false;
//					
//					case CROSS_BEFORE: 			
//					case CROSS_AFTER:		
//					case OVERLAP:		
//						if (math.contains(e, e2.start()) || math.contains(e, e2.end())) {
//							if (defaultResizable == true) {
//								start = math.min(e.start(), e2.start());
//								end = math.min(e.start(), e2.start());
//							}
//						} 
//						break; 
//					}
//					
//					if (quit) break;
//				}
//				return true;
			}
		};
	}
	
	class IndexIterator extends ImmutableIterator<N> {
		
		private IndexSequence<N> seq;

		IndexIterator(IndexSequence seq) {
			super();
			this.seq = seq;
			seq.init();
		}

		@Override
		public boolean hasNext() {
			return seq.hasNext();
		}

		@Override
		public N next() {
			seq.next();
			return seq.index();
		}

	}

	boolean moveSelected(N source, N target) {
		assert selection.contains(source);
		if (selection.isEmpty() || selection.contains(target)) return false;
			
		int position = math.compare(indexOf(target), indexOf(source));
		if (position == 0) return false;
		N index = position < 0 ? target : math.increment(target);

		order.move(selection, index);
		return true;
	}


	

}
