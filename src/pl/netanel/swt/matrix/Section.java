package pl.netanel.swt.matrix;

import java.text.MessageFormat;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.TypedListener;

import pl.netanel.util.ImmutableIterator;
import pl.netanel.util.Preconditions;

/**
 * Section represents a continuous segment of a matrix axis, for example a
 * header, body, footer. It contains a number of items indexed by the 
 * <code>&lt;N extends {@link Number}&gt;</code> type parameter.<br>
 * 
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
 * Section has boolean flags for visibility and navigation enablement. 
 * 
 * 
 * @author Jacek Kolodziejczyk created 02-03-2011
 */
public class Section<N extends Number> {

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
	final Listeners listeners;
	
	/**
	 * Constructs a section indexed by int.class, which is equivalent to Integer.class.
	 */
	public Section() {
		this(Math.getInstance(int.class));
	}

	/**
	 * Constructs a section indexed by the given sub-class of {@link Number}.
	 * 
	 * @param numberClass defines the class used for indexing
	 */
	public Section(Class<N> numberClass) {
		this(Math.getInstance(numberClass));
	}
	
	Section(Math<N> math) {
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
		listeners = new Listeners();
	}
	

	@Override
	public String toString() {
		return Integer.toString(index);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SectionClient) obj = ((SectionClient) obj).core;
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		if (this instanceof SectionClient) return ((SectionClient) this).hashCode();
		return super.hashCode();
	}

	/*------------------------------------------------------------------------
	 * Collection like  
	 */

	/**
	 * Specifies the number of section items.
	 * <p>
	 * <code>count</code> input parameter is not validated against negative or null values.
	 * 
	 * @param count the new count of the receiver's items
	 * @see #getCount()
	 * @throws NullPointerException if the count is <tt>null</tt>.
	 */
	public void setCount(N count) {
		this.count = count;
		order.setCount(count);
	}
	
	/**
	 * Returns the number of items in the receiver. 
	 * @return the number of items in the receiver
	 * @see #setCount(Number)
	 */
	public N getCount() {
		return count;
	}

	/**
     * Returns <tt>true</tt> if this contains contains no items.
     * Otherwise <code>false</code> is returned.
     * @return <tt>true</tt> if this contains contains no items
     */
	public boolean isEmpty() {
		return order.items.isEmpty();
	}

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
	 * Hidden items are not skipped. 
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
	 * Marks the receiver as visible if the argument is <tt>true</tt>,
	 * and marks it invisible otherwise. 
	 *
	 * @param visible the new visibility state
	 */
 	public void setVisible(boolean visible) {
		this.isVisible = visible;
	}
	
	/**
	 * Returns <tt>true</tt> if the receiver is visible. 
	 * Otherwise, <tt>false</tt> is returned.
	 *
	 * @return the receiver's visibility state
	 */
	public boolean isVisible() {
		return isVisible;
	}

	/**
	 * Enables focus item navigation in the receiver if the argument is <tt>true</tt>,
	 * and disables it invisible otherwise. 
	 *
	 * @param enabled the new focus item enablement state
	 */
	public void setFocusItemEnabled(boolean enabled) {
		this.isNavigationEnabled = enabled;
	}
	
	/**
	 * Returns <tt>true</tt> if the focus item navigation is enabled in the receiver. 
	 * Otherwise, <tt>false</tt> is returned.
	 *
	 * @return the receiver's focus item enablement state
	 */
	public boolean isFocusItemEnabled() {
		return isNavigationEnabled;
	}

	
	
	/*------------------------------------------------------------------------
	 * Default values 
	 */
	
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
	public void setDefaultCellWidth(int width) {
		if (width < 0) return;
		cellWidth.setDefault(width);
	}
	
	/**
	 * Returns the default cell width of the receiver's items. 
	 * Cell with excludes the width of lines.
	 * @return default width of cells in this 
	 */
	public int getDefaultCellWidth() {
		return cellWidth.getDefault();
	}
	
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
	public void setDefaultLineWidth(int width) {
		if (width < 0) return;
		lineWidth.setDefault(width);
	}
	
	/**
	 * Returns the default line width of the receiver's items. 
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
   * Sets the default resize ability of the receiver's items to the given
   * argument.
   * <p>
   * Default value allows to save storage memory of the "resizable" attribute if
   * many items share the same value and the newly created items to have this
   * default value automatically.
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
   * Sets the default move ability of the receiver's items to the given
   * argument.
   * <p>
   * Default value allows to save storage memory of the "moveable" attribute if
   * many items share the same value and the newly created items to have this
   * default value automatically.
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
   * Sets the default hide ability of the receiver's items to the given
   * argument.
   * <p>
   * Default value allows to save storage memory of the "hideable" attribute if
   * many items share the same value and the newly created items to have this
   * default value automatically.
   * 
   * @param hideable the new hide ability state
   */
	public void setDefaultHideable(boolean hideable) {
		this.defaultHideable = hideable;
	}
	
	
	
	/*------------------------------------------------------------------------
	 * Item properties 
	 */
	
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
	public void setLineWidth(N index, int width) {
		lineWidth.setValue(index, index, width);
	}
	
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
	public void setLineWidth(N start, N end, int width) {
		lineWidth.setValue(start, end, width);
	}
	
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
	public int getLineWidth(N index) {
		return lineWidth.getValue(index);
	}
	
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
	public void setCellWidth(N index, int width) {
		cellWidth.setValue(index, index, width);
	}
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
	public void setCellWidth(N start, N end, int width) {
		cellWidth.setValue(start, end, width);
	}
	
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
	public int getCellWidth(N index) {
		return cellWidth.getValue(index);
	}

	
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
	public void setMoveable(N index, boolean enabled) {
		moveable.change(index, index, enabled != defaultMoveable);
	}
	
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
	public void setMoveable(N start, N end, boolean enabled) {
		moveable.change(start, end, enabled != defaultMoveable);
	}
	
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
	public boolean isMoveable(N index) {
		return moveable.contains(index) != defaultMoveable;
	}
	
	
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
	public void setResizable(N index, boolean enabled) {
		resizable.change(index, index, enabled != defaultMoveable);
	}
	
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
	 * <code>index</code> refers to the model, not the visual position of the item on the screen
	 * which can be altered by move and hide operations. 
	 * 
	 * @param index the item index 
	 * @return the move ability state at the given index
	 */
	public boolean isResizable(N index) {
		return resizable.contains(index) != defaultResizable;
	}
	
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
	public void setHideable(N index, boolean enabled) {
		hideable.change(index, index, enabled != defaultMoveable);
	}
	
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
	 * <code>index</code> refers to the model, not the visual position of the item on the screen
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
	public void setHidden(N index, boolean state) {
		hidden.change(index, index, state);
	}
	
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
	public void setHidden(N start, N end, boolean state) {
		hidden.change(start, end, state);
	}

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
	 * <code>index</code> refers to the model, not the visual position of the item on the screen
	 * which can be altered by move and hide operations. 
	 * 
	 * @param index the item index 
	 * @return the hiding state at the given index
	 */
	public boolean isHidden(N index) {
		return hidden.contains(index);
	}
	
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
		return new IndexIterator(new NumberSequence(hidden));
	}
	
	
	
	/*------------------------------------------------------------------------
	 * Selection 
	 */
	
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
	 * @see #setSelected(Number, Number, boolean)
	 */ 
	public void setSelected(N index, boolean state) {
		hidden.change(index, index, state);
	}
	
	/**
	 * Sets the selection state for the range of items.
	 * <p>
	 * <code>start</code> and <code>end</code> indexes refer to the model, not the visual position of the item on the screen
	 * which can be altered by move and hide operations.
	 *
	 * @param start first index of the range of items  
	 * @param end last index of the range of items  
	 * @param state the new selection state
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
	public void setSelectedAll(boolean state) {
		if (state) {
			selection.add(math.ZERO_VALUE(), math.decrement(count));
		} else {
			selection.clear();
		}
	}
	
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
	public boolean isSelected(N index) {
		return selection.contains(index);
	}
	
	/**
	 * Returns the number of selected items in this section.
	 * @return the number of selected items in this section
	 */
	public N getSelectedCount() {
		return selection.getCount().getValue();
	}
	
	/**
	 * Returns a sequence of indexes of selected items. 
	 * @return a sequence of indexes of selected items
	 */
	NumberSequence<N> getSelected() {
		return new NumberSequence(selection);
	}
	
	/** 
	 * Returns iterator for selected items. 
	 * <p>
	 * <code>index</code> that is returned by {@link Iterator#next()} 
	 * method refers to the model, 
	 * not the visual position of the item on the screen
	 * which can be altered by move and hide operations.   
	 */
	public Iterator<N> getSelectedIterator() {
		return new ImmutableIterator<N>() {
			NumberSequence<N> seq = new NumberSequence<N>(selection.copy());
			private boolean next;
			{
				seq.init();
			}
			@Override
			public boolean hasNext() {
				next = seq.next();
				return next;
			}

			@Override
			public N next() {
				return next ? seq.index() : null;
			}
		};
	}
	
	/** 
	 * Returns iterator for selected items. 
	 * <p>
	 * <code>index</code> that is returned by {@link Iterator#next()} 
	 * method refers to the model, 
	 * not the visual position of the item on the screen
	 * which can be altered by move and hide operations.   
	 */
	public Iterator<N[]> getSelectedExtentIterator() {
		return new ImmutableIterator<N[]>() {
			NumberSequence<N> seq = new NumberSequence<N>(selection.copy());
			private boolean next;
			{
				seq.init();
			}
			@Override
			public boolean hasNext() {
				next = seq.nextExtent();
				return next;
			}
			
			@Override
			public N[] next() {
				return (N[]) (next ? new Number[] {seq.start(), seq.end()} : null);
			}
		};
	}
	
	void backupSelection() {
		lastSelection.replace(selection);
	}
	
	void restoreSelection() {
		selection.replace(lastSelection);
	}
	
	
	
	/*------------------------------------------------------------------------
	 * Moving
	 */
	
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
	public void move(N start, N end, N target) {
		order.move(start, end, target);
	}
	
	 /**
	 * Removes a range of items decreasing the following indexes by the number of removed items. 
	 * 
	 * @param start first index of the range of items  
	 * @param end last index of the range of items
	 */
	public void delete(N start, N end) {
		cellWidth.delete(start, end);
		lineWidth.delete(start, end);
		cellSpan.delete(start, end);
		resizable.delete(start, end);
		moveable.delete(start, end);
		hideable.delete(start, end);
		hidden.delete(start, end);
		order.delete(start, end);
		selection.delete(start, end);
		lastSelection.delete(start, end);
		count = math.create(count).subtract(end).add(start).decrement().getValue();
		
		if (axis != null) {
			axis.deleteInZones(this, start, end);
		}
	}

	/**
	 * Inserts items before the given target item increasing the following indexes by the number of inserted items. 
	 * <p>
	 * Items are inserted before the given target index or at the end if the target equals
	 * to the receiver item count. 
	 * 
	 * @param target the index before which items are inserted  
	 * @param count the number of items to insert
	 */
	public void insert(N target, N count) {
		cellWidth.insert(target, count);
		lineWidth.insert(target, count);
		cellSpan.insert(target, count);
		resizable.insert(target, count);
		moveable.insert(target, count);
		hideable.insert(target, count);
		hidden.insert(target, count);
		order.insert(target, count);
		selection.insert(target, count);
		lastSelection.insert(target, count);
		this.count = math.create(this.count).add(count).getValue();
		
		if (axis != null) {
			axis.insertInZones(this, target, count);
		}
	}
	
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
	public void addControlListener(ControlListener listener) {
		TypedListener typedListener = new TypedListener(listener);
		listeners.add(SWT.Resize, typedListener);
		listeners.add(SWT.Move, typedListener);
	}
	
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
	public void addSelectionListener (SelectionListener listener) {
		Preconditions.checkNotNullWithName(listener, "listener");
		TypedListener typedListener = new TypedListener(listener);
		listeners.add(SWT.Selection, typedListener);
		listeners.add(SWT.DefaultSelection, typedListener);
	}

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
	public void removeControlListener (ControlListener listener) {
		Preconditions.checkNotNullWithName(listener, "listener");
		listeners.remove(SWT.Move, listener);
		listeners.remove(SWT.Resize, listener);
	}

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
	public void removeSelectionListener(SelectionListener listener) {
		Preconditions.checkNotNullWithName(listener, "listener");
		listeners.remove(SWT.Selection, listener);
		listeners.remove(SWT.DefaultSelection, listener);
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

	N nextNotHiddenIndex(N index, int direction) {
		for (Extent e: hidden.items) {
			if (math.contains(e, index)) {
				if (direction > 0) {
					index = math.increment(e.end());
					if (math.compare(index, count) >= 0) {
						return null;
					}
				} else {
					index = math.decrement(e.start());
					if (math.compare(index, math.ZERO_VALUE()) < 0) {
						return null;
					}
				}
				break;
			}
		}
		return math.compare(index, count) < 0 ? index : null;
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
		
		private NumberSequence<N> seq;

		IndexIterator(NumberSequence seq) {
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
	
	protected void checkRange(N start, N end, N limit) {
		Preconditions.checkNotNull(start, "start");
		Preconditions.checkNotNull(end, "end");
		if (math.compare(start, math.ZERO_VALUE()) < 0) {
			throw new IndexOutOfBoundsException(MessageFormat.format(
				"start ({0}) cannot be negative", start)) ;
		}
		if (math.compare(end, math.ZERO_VALUE()) < 0) {
			throw new IndexOutOfBoundsException(MessageFormat.format(
				"end ({0}) cannot be negative", end)) ;
		}
		if (math.compare(start, limit) >= 0) {
			throw new IndexOutOfBoundsException(MessageFormat.format(
				"start ({0}) must be lower then limit {1}", start, limit)) ;
		}
		if (math.compare(end, limit) >= 0) {
			throw new IndexOutOfBoundsException(MessageFormat.format(
				"end ({0}) must be lower then limit {1}", end, limit)) ;
		}
		if (math.compare(start, end) > 0) {
			throw new IllegalArgumentException(MessageFormat.format(
				"start ({0}) cannot be greater then end {1}", start, end)) ;
		}
	}
	
	protected void checkIndex(N index, N limit, String name) {
		Preconditions.checkNotNull(index, "index");
		if (math.compare(index, math.ZERO_VALUE()) < 0) {
			throw new IndexOutOfBoundsException(MessageFormat.format(
					"index ({0}) cannot be negative", index)) ;
		}
		if (math.compare(index, limit) >= 0) {
			throw new IndexOutOfBoundsException(MessageFormat.format(
					"index ({0}) must be lower then limit {1}", index, limit)) ;
		}
	}

	void addSelectionEvent() {
    Event event = new Event();
    event.type = SWT.Selection;
    event.widget = axis.matrix;
    listeners.add(event);
  }
}
