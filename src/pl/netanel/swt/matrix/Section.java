package pl.netanel.swt.matrix;

import java.math.BigInteger;
import java.util.Iterator;

import pl.netanel.util.ImmutableIterator;


/**
 * Section represents a continuous segment of an {@link Axis}, for example a header, body, footer.<br>
 * It contains a number of items.<br>
 * <img src="../../../../images/Section.png"/>
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
public class Section {

	static final int DEFAULT_CELL_WIDTH = 16;
	static final int DEFAULT_LINE_WIDTH = 1;
	
	final Math math;
	private final MutableNumber count;
	final NumberOrder order;
	final NumberSet hidden;
	private final NumberSet resizable;
	private final NumberSet moveable;
	private final NumberSet hideable;
	private final IntAxisState cellWidth;
	private final IntAxisState lineWidth;
	private final ObjectAxisState<Number> cellSpan;
	
	private final NumberQueueSet selection;
	private final NumberQueueSet lastSelection;

	
	private boolean defaultResizable, defaultMoveable, defaultHideable; 
	private boolean isNavigationEnabled, isVisible;
	
	int index; 
	
	/**
	 * The default constructor utilizing BigInteger indexing.
	 */
	public Section() {
		this(Math.getInstance(BigInteger.class));
	}
	
	Section(Class<? extends Number> numberClass) {
		this(Math.getInstance(numberClass));
	}
	
	Section(Math math) {
		super();
		this.math = math;
		count = math.create(0);
		
		order = new NumberOrder(math);
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


	public Number getCount() {
		return count.getValue();
	}

	public void setCount(Number count) {
		this.count.set(count);
		order.setCount(count);
	}

	Number getVisibleCount() {
		return math.subtract(count, hidden.getCount()).getValue();
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

	public void move(Number start, Number end, Number target) {
		order.move(start, end, target);
	}
	
	public void setHidden(Number start, Number end, boolean flag) {
		hidden.change(start, end, flag);
	}
	
	public boolean isHidden(Number index) {
		return hidden.contains(index);
	}
	
	public Number getHiddenCount(Number start, Number end) {
		return hidden.getCount(start, end).getValue();
	}
	
	public void setMoveable(Number start, Number end, boolean flag) {
		moveable.change(start, end, flag != defaultMoveable);
	}
	
	public boolean isMoveable(Number index) {
		return moveable.contains(index) != defaultMoveable;
	}
	
	public void setResizable(Number start, Number end, boolean flag) {
		resizable.change(start, end, flag != defaultResizable);
	}
	
	public boolean isResizable(Number index) {
		return resizable.contains(index) != defaultResizable;
	}
	
	public void setHideable(Number start, Number end, boolean flag) {
		hideable.change(start, end, flag != defaultHideable);
	}
	
	public boolean isHideable(Number index) {
		return hideable.contains(index) != defaultHideable;
	}
	
	
	public void setSelected(Number start, Number end, boolean selected) {
		selection.change(start, end, selected);
	}
	
	public void setSelected(boolean selected) {
		if (selected) {
			selection.add(math.ZERO_VALUE(), math.decrement(count.getValue()));
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
	
	public boolean isSelected(Number index) {
		return selection.contains(index);
	}
	
	public Number getSelectionCount() {
		return selection.getCount().getValue();
	}
	
	public Iterator<Number> getSelection() {
		return new SelectionIterator(new IndexSequence(selection));
	}
	
	
	
	public void setCellWidth(Number start, Number end, int width) {
		cellWidth.setValue(start, end, width);
	}
	
	public int getCellWidth(Number index) {
		return cellWidth.getValue(index);
	}
	
	public void setLineWidth(Number start, Number end, int width) {
		lineWidth.setValue(start, end, width);
	}
	
	public int getLineWidth(Number index) {
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
	
	
	void setCellSpan(Number index, Number value) {
		cellSpan.setValue(index, value);
	}
	
	Number getCellSpan(Number index) {
		return cellSpan.getValue(index);
	}


	public Number getPosition(Number index) {
		if (hidden.contains(index)) return null;
		MutableNumber hiddenCount = math.create(0);
		MutableNumber pos1 = math.create(0);
		MutableNumber pos2 = math.create(0);
		
		for (int i = 0, size = order.items.size(); i < size; i++) {
			Extent e = order.items.get(i);
			boolean contains = math.contains(e.start(), e.end(), index);
			hiddenCount.add(hidden.getCount(e.start(), contains ? index : e.end()));
			if (contains) {
				return pos2.add(index).subtract(e.start).subtract(hiddenCount).getValue();
			}
			pos1.set(pos2);
			pos2.add(e.end).subtract(e.start).increment(); //.subtract(hiddenCount);
		}
		return null;
	}

	
	public Number getByPosition(Number position) {
		if (math.compare(position, getVisibleCount()) >= 0) return null;
		
		MutableNumber pos1 = math.create(0);
		MutableNumber pos2 = math.create(0);
		
		for (int i = 0, size = order.items.size(); i < size; i++) {
			Extent e = order.items.get(i);
			pos2.add(e.end).subtract(e.start).subtract(hidden.getCount(e.start(), e.end()));
			if (math.compare(pos2.getValue(), position) >= 0) {
				MutableNumber count = hidden.getCount(e.start(), pos1.getValue());
				return pos1.subtract(position).negate().add(e.start).add(count).getValue();
			}
			pos2.increment();
			pos1.set(pos2); 
		}
		return null;
	}

	public int comparePosition(Number n1, Number n2) {
		return math.compare(order.indexOf(n1), order.indexOf(n2));
	}
	
	
	public void setHidden(Number index, boolean flag) {
		setHidden(index, index, flag);
	}
	
	public void setHidden(boolean flag) {
		for (int i = 0, imax = selection.items.size(); i < imax; i++) {
			Extent e = selection.items.get(i);
			setHidden(e.start(), e.end(), flag);
		}
	}
	public Number getLastIndex() {
		return math.decrement(count.getValue());
	}

	
	
	class SelectionIterator extends ImmutableIterator<Number> {
		
		private IndexSequence seq;

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
		public Number next() {
			seq.next();
			return seq.index();
		}

	}

}
