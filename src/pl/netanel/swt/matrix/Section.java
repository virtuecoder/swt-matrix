package pl.netanel.swt.matrix;

import java.util.Iterator;

import pl.netanel.util.ImmutableIterator;


/**
 * Section represents a continuous segment of an {@link Axis}, for example a header, body, footer.<br>
 * It contains a number of items.<br>
 * <img src="../../../../../javadoc/images/Section.png"/>
 *
 * <p> 
 * Section has boolean flags for visibility and navigation enablement. 
 * On the diagram above the current item is 0 in body section of column axis and 2 in the body section of row axis.
 * Only one item on the axis can the be the current one, as opposed to each section having its own current item.  
 * <p>
 * Item attributes include cell width, line width, moveable, resizable, hideable, hidden, selected. 
 * To optimize data storage of those attributes one value can be set for a range of items enclosed between  
 * the start and end items. Also default values can be defined to save memory. If 1000000 items have the same width,
 * then its a waste to store 1000000 ints with the same values.
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
	 * Constructs a section indexed by the given class of numbers.
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
	 * Core 
	 */


	public N getCount() {
		return count;
	}

	public void setCount(N count) {
		this.count = count;
		order.setCount(count);
	}

	N getVisibleCount() {
		return math.create(count).subtract(hidden.getCount()).getValue();
	}

	public boolean isEmpty() {
		return order.items.isEmpty();
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
	
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	public boolean isVisible() {
		return isVisible;
	}

	public boolean isNavigationEnabled() {
		return isNavigationEnabled;
	}

	public void setNavigationEnabled(boolean isCurrentMarkerEnabled) {
		this.isNavigationEnabled = isCurrentMarkerEnabled;
	}

	
	public void setHidden(N start, N end, boolean flag) {
		hidden.change(start, end, flag);
	}
	
	public boolean isHidden(N index) {
		return hidden.contains(index);
	}
	
	public N getHiddenCount(N start, N end) {
		return hidden.getCount(start, end).getValue();
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
	
	
	public void setSelected(N start, N end, boolean selected) {
		selection.change(start, end, selected);
	}
	
	public void setSelected(boolean selected) {
		if (selected) {
			selection.add(math.ZERO_VALUE(), math.decrement(count));
		} else {
			selection.clear();
		}
	}
	
	public void backupSelection() {
		lastSelection.replace(selection);
	}
	
	public void restoreSelection() {
		selection.replace(lastSelection);
	}
	
	public boolean isSelected(N index) {
		return selection.contains(index);
	}
	
	public N getSelectionCount() {
		return selection.getCount().getValue();
	}
	
	public Iterator<N> getSelection() {
		return new SelectionIterator(new IndexSequence(selection));
	}
	
	
	
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
	
	
	void setCellSpan(N index, N value) {
		cellSpan.setValue(index, value);
	}
	
	N getCellSpan(N index) {
		return cellSpan.getValue(index);
	}

	
	
	/*------------------------------------------------------------------------
	 * Order 
	 */

	/**
	 * Returns the visual position of the given index according to the current order of items 
	 * and skipping the hidden ones. 
	 *  
	 * @param index of the item to get the position for
	 * @return visual position of the item at the given index
	 * 
	 * @see #move(Number, Number, Number)
	 * @see #getByPosition(Number)
	 * @see #comparePosition(Number, Number) 
	 */
	public N getPosition(N index) {
		if (hidden.contains(index)) return null;
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

	/**
	 * Returns the index of the item at given visual position according 
	 * to the current order of items and skipping the hidden ones.
	 * 
	 * @param position visual index of given item
	 * @return index of the item at the given position
	 * 
	 * @see #move(Number, Number, Number)
	 * @see #getPosition(Number)
	 * @see #comparePosition(Number, Number) 
	 */
	public N getByPosition(N position) {
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
	 * Returns the comparison result of visual positions of given items.
	 *   
	 * @param index1 index one of the items
	 * @param index2 index of the second item
	 * @return the comparison result of visual positions of given items
	 * 
	 * @see #getPosition(Number)
	 * @see #getByPosition(Number)
	 * @see #move(Number, Number, Number)
	 */
	public int comparePosition(N index1, N index2) {
		return math.compare(order.indexOf(index1), order.indexOf(index2));
	}
	

	public void move(N start, N end, N target) {
		order.move(start, end, target);
	}
	
	
	
	/*------------------------------------------------------------------------
	 * Hiding 
	 */
	
	public void setHidden(N index, boolean flag) {
		setHidden(index, index, flag);
	}
	
	public void setHidden(boolean flag) {
		for (int i = 0, imax = selection.items.size(); i < imax; i++) {
			Extent<N> e = selection.items.get(i);
			setHidden(e.start(), e.end(), flag);
		}
	}
	
	
	class SelectionIterator extends ImmutableIterator<N> {
		
		private IndexSequence<N> seq;

		SelectionIterator(IndexSequence seq) {
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
