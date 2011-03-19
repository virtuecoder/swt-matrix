package pl.netanel.swt.matrix;

import java.util.Iterator;

import pl.netanel.util.ImmutableIterator;

/**
 * Section represents a continuous segment of an {@link Axis}, for example a
 * header, body, footer.<br>
 * It contains a number of items indexed by the
 * <code>&lt;N extends {@link Number}&gt;</code> type parameter.<br>
 * <p>
 * <img src="../../../../../javadoc/images/Section.png"/>
 * 
 * <p>
 * Section has boolean flags for visibility and navigation enablement. On the
 * diagram above the current item is 0 in body section of column axis and 2 in
 * the body section of row axis. Only one item on the axis can the be the
 * current one, as opposed to each section having its own current item.
 * <p>
 * Item attributes include cell width, line width, moveable, resizable,
 * hideable, hidden, selected. To optimize data storage of those attributes one
 * value can be set for a range of items enclosed between the start and end
 * items. Also default values can be defined to save memory. If 1000000 items
 * have the same width, then its a waste to store 1000000 ints with the same
 * values.
 * 
 * 
 * @author Jacek Kolodziejczyk created 02-03-2011
 */
public class Section<N extends Number> {

	static final int DEFAULT_CELL_WIDTH = 16;
	static final int DEFAULT_LINE_WIDTH = 1;
	
	final Math<N> math;
	private N count;
	
	final NumberOrder<N> order;
	final NumberSet<N> hidden;
	private final NumberSet resizable;
	private final NumberSet moveable;
	private final NumberSet hideable;
	private final IntAxisState cellWidth;
	private final IntAxisState lineWidth;
	private final ObjectAxisState<N> cellSpan;
	
	private final NumberQueueSet<N> selection;
	private final NumberQueueSet lastSelection;

	
	private boolean defaultResizable, defaultMoveable, defaultHideable; 
	private boolean isNavigationEnabled, isVisible;
	
	int index; 
	
	/**
	 * Constructs a section indexed by int.class.
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
		resizable = new NumberSet(math);
		moveable = new NumberSet(math);
		hideable = new NumberSet(math);
		
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
	 * Returns the visual position of the given item according to the current order of items 
	 * and skipping the hidden ones. 
	 * If the item is null, out of scope or is hidden then the method returns null.
	 *  
	 * @param item to get the position for
	 * @return visual position of the item at the given index
	 * 
	 * @see #indexOf(Number)
	 */
	public N indexOfNotHidden(N item) {
		if (item == null || hidden.contains(item)) return null;
		MutableNumber<N> hiddenCount = math.create(0);
		MutableNumber<N> pos1 = math.create(0);
		MutableNumber<N> pos2 = math.create(0);
		
		for (int i = 0, size = order.items.size(); i < size; i++) {
			Extent<N> e = order.items.get(i);
			boolean contains = math.contains(e, item);
			hiddenCount.add(hidden.getCount(e.start(), contains ? item : e.end()));
			if (contains) {
				return pos2.add(item).subtract(e.start).subtract(hiddenCount).getValue();
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
	
	public void setDefaultCellWidth(int width) {
		cellWidth.setDefault(width);
	}
	
	public int getDefaultCellWidth() {
		return cellWidth.getDefault();
	}
	
	public void setDefaultLineWidth(int width) {
		lineWidth.setDefault(width);
	}
	
	public int getDefaultLineWidth() {
		return lineWidth.getDefault();
	}
	
	
	public boolean isDefaultResizable() {
		return defaultResizable;
	}

	public void setDefaultResizable(boolean defaultResizable) {
		this.defaultResizable = defaultResizable;
	}

	public boolean isDefaultMoveable() {
		return defaultMoveable;
	}

	public void setDefaultMoveable(boolean defaultMoveable) {
		this.defaultMoveable = defaultMoveable;
	}

	public boolean isDefaultHideable() {
		return defaultHideable;
	}

	public void setDefaultHideable(boolean defaultHideable) {
		this.defaultHideable = defaultHideable;
	}
	
	
	
	/*------------------------------------------------------------------------
	 * Item properties 
	 */

	
	public void setCellWidth(N start, N end, int width) {
		cellWidth.setValue(start, end, width);
	}
	
	public int getCellWidth(N index) {
		return cellWidth.getValue(index);
	}
	
	public void setLineWidth(N start, N end, int width) {
		lineWidth.setValue(start, end, width);
	}
	
	public int getLineWidth(N index) {
		return lineWidth.getValue(index);
	}
	
	
	public void setMoveable(N start, N end, boolean flag) {
		moveable.change(start, end, flag != defaultMoveable);
	}
	
	public boolean isMoveable(N index) {
		return moveable.contains(index) != defaultMoveable;
	}
	
	public void setResizable(N start, N end, boolean flag) {
		resizable.change(start, end, flag != defaultResizable);
	}
	
	public boolean isResizable(N index) {
		return resizable.contains(index) != defaultResizable;
	}
	
	public void setHideable(N start, N end, boolean flag) {
		hideable.change(start, end, flag != defaultHideable);
	}
	
	public boolean isHideable(N index) {
		return hideable.contains(index) != defaultHideable;
	}

	
	/*------------------------------------------------------------------------
	 * Hiding 
	 */
	
	public void hide(N start, N end, boolean flag) {
		hidden.change(start, end, flag);
	}

	public void hideSelected(boolean flag) {
		for (int i = 0, imax = selection.items.size(); i < imax; i++) {
			Extent<N> e = selection.items.get(i);
			hide(e.start(), e.end(), flag);
		}
	}
	
	public boolean isHidden(N index) {
		return hidden.contains(index);
	}
	
	public N getHiddenCount(N start, N end) {
		return hidden.getCount(start, end).getValue();
	}
	
	public Iterator<N> getHidden() {
		return new IndexIterator(new IndexSequence(hidden));
	}
	
	
	
	/*------------------------------------------------------------------------
	 * Selection 
	 */
	
	public void select(N start, N end, boolean selected) {
		selection.change(start, end, selected);
	}
	
	public void selectAll(boolean selected) {
		if (selected) {
			selection.add(math.ZERO_VALUE(), math.decrement(count));
		} else {
			selection.clear();
		}
	}
	
	public boolean isSelected(N index) {
		return selection.contains(index);
	}
	
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

	void setCellSpan(N index, N value) {
		cellSpan.setValue(index, value);
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

}
