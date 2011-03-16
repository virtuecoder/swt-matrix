package pl.netanel.swt.matrix;

import java.util.List;

/**
 * Uses plain Number parameters which are validated in preconditions.
 * 
 * @author Jacek Kolodziejczyk created 02-03-2011
 */
public class Section {

	static final int DEFAULT_CELL_WIDTH = 16;
	static final int DEFAULT_LINE_WIDTH = 1;
	
	final Math math;
	private final MutableNumber count;
	final NumberOrder order;
	private final NumberSet hidden;
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
	
	public Section(Class<? extends Number> numberClass) {
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
		return count;
	}

	public void setCount(Number count) {
		this.count.set(count);
		order.setCount(count);
	}

	MutableNumber getVisibleCount() {
		return math.subtract(count, hidden.getCount());
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
		return hidden.getCount(start, end);
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
			selection.add(math.ZERO(), math.decrement(count));
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
	
	
	public void setCellSpan(Number index, Number value) {
		cellSpan.setValue(index, value);
	}
	
	public Number getCellSpan(Number index) {
		return cellSpan.getValue(index);
	}


	public Number getPosition(Number index) {
		if (hidden.contains(index)) return null;
		MutableNumber hiddenCount = math.create(0);
		MutableNumber pos1 = math.create(0);
		MutableNumber pos2 = math.create(0);
		
		for (int i = 0, size = order.items.size(); i < size; i++) {
			Extent e = order.items.get(i);
			boolean contains = math.contains(e.start, e.end, index);
			hiddenCount.add(hidden.getCount(e.start, contains ? index : e.end));
			if (contains) {
				return pos2.add(math.getValue(index)).subtract(e.start).subtract(hiddenCount);
			}
			pos1.set(pos2);
			pos2.add(e.end).subtract(e.start).increment(); //.subtract(hiddenCount);
		}
		return null;
	}

	
	MutableNumber getByPosition(Number position) {
		if (math.compare(position, getVisibleCount()) >= 0) return null;
		
		MutableNumber pos1 = math.create(0);
		MutableNumber pos2 = math.create(0);
		
		for (int i = 0, size = order.items.size(); i < size; i++) {
			Extent e = order.items.get(i);
			pos2.add(e.end).subtract(e.start).subtract(hidden.getCount(e.start, e.end));
			if (math.compare(pos2, position) >= 0) {
				Number count = hidden.getCount(e.start, pos1);
				return pos1.subtract(position).negate().add(e.start).add(count);
			}
			pos2.increment();
			pos1.set(pos2); 
		}
		return null;
		
//		ForwardOrderIterator it = order.new ForwardOrderIterator();
//		Number hiddenCount = factory.zero();
//		Number pos1 = factory.zero();
//		Number pos2 = factory.zero();
//		while (it.hasNext()) {
//			Extent extent = it.next();
//			pos1.set(it.pos1).subtract(hiddenCount);
//			hiddenCount.add(hidden.getCount(extent.getStart(), extent.getEnd()));
//			pos2.set(it.pos2).subtract(hiddenCount);
//			if (pos2.compareTo(position) >= 0) {
//				Number count = hidden.getCount(extent.getStart(), pos1);
//				return pos1.subtract(position).negate().add(extent.getStart()).add(count);
//			}
//		}		
//		return null;

	}

	public int comparePosition(Number n1, Number n2) {
		return math.compare(order.indexOf(n1), order.indexOf(n2));
	}
	
	
	
	public IndexSequence getSequence(int sign) {
		return sign > 0 ? new Forward() : new Backward();
	}

	
	/**
	 * Iterates over section items in their order and skipping the hidden ones.
	 * Iteration yields nothing if the section is not visible. 
	 */
	abstract class IndexSequence {
		public MutableNumber number, number2, lastInExtent, last, d;
		protected int i, h;
		public int level;
		protected int sign;
		protected Extent extent, he;
		public boolean moved;
		
		
		public IndexSequence() {
			super();
			number = math.create(0);
			number2 = math.create(0);
			lastInExtent = math.create(0);
			last = math.create(-1);
			d = math.create(0);
		}

		public void init() {
			i = firstIndex(order.items);
		}
		
		public boolean next(MutableNumber index) {
			moved = false;
			while (true) {
				switch(level) {
				case 0: // extents
					if (hasNextExtent()) {
						i += sign;
						extent = order.items.get(i);
						number.set(start(extent)).add(-sign);
						lastInExtent.set(end(extent));
						h = firstIndex(hidden.items);
						he = null;
						level++;
					} else {
						return false;
					}
					break;
					
				case 1: // numbers
					if (hasNextNumber()) {
						if (nextNumber(index)) return true;
					}
					level--;
					break;
				}
			}
		}
		
	
		private boolean nextNumber(MutableNumber count) {
			MutableNumber limit = null;

			number2.set(number).add(sign);
			count.decrement();
			while (compare(number, lastInExtent) < 0) {
				nextHidden(lastInExtent);

				// If inside of hidden move beyond
				if (he != null && math.contains(he.start, he.end, number2)) {
					if (!skipHidden(count, lastInExtent)) return false;
				} 
				else {
					limit = he == null || math.contains(he.start, he.end, number2) ? 
							lastInExtent : start(he);
					d.set(math.min(subtract(limit, number2).getValue(), count.getValue()));
					add(number2, d.getValue());
					count.subtract(d);
				}
				if (he != null && math.contains(he.start, he.end, number2)) {
					if (!skipHidden(count, lastInExtent)) return false;
				}			
				moved = math.compare(number2, last) != 0;
				number.set(number2);
				last.set(number);
				
				if (math.compare(count, math.ZERO()) <= 0) return true; 
			}
			return false; 
		}
		

		private void nextHidden(MutableNumber lastIndex) {
			while (true) {
				if (he != null) {
					MutableNumber start = start(he), end = end(he);
					if (compare(start, number2) <= 0 && compare(number2, end) <= 0 || 
						compare(start, number2) > 0 && compare(start, lastIndex) <= 0) break;
				}
				if (hasNextHidden()) {
					he = hidden.items.get(h += sign);
				} else {
					he = null;
					break;
				}
			}
		}
		
		private boolean skipHidden(MutableNumber count, MutableNumber lastIndex) {
			number2.set(end(he)).add(sign);
			if (compare(number2, lastIndex) > 0) {
				moved = false;
				count.increment();
				return false;
			}
			return true;
		}
		

		protected boolean hasNextNumber() {
			return extent != null && compare(number, lastInExtent) < 0;// && compare(index, nullIndex) != 0; 
		}

		protected abstract int compare(MutableNumber x, MutableNumber y);
		protected abstract void add(MutableNumber x, Number y);
		protected abstract MutableNumber subtract(MutableNumber x, MutableNumber y);
		protected abstract boolean hasNextExtent();
		protected abstract boolean hasNextHidden();
		protected abstract int firstIndex(List<Extent> items);
		protected abstract MutableNumber start(Extent e);
		protected abstract MutableNumber end(Extent e);


		public Number index() {
			return number;
		}


		public boolean next() {
			return next(math.create(1));
		}

		public void set(Number index) {
			i = order.getExtentIndex(index);
			if (i == -1) return;
			extent = order.items.get(i);
			number.set(index).add(-sign);
			lastInExtent.set(end(extent));
			h = firstIndex(hidden.items);
			he = null;
		}
	}
	
	class Forward extends IndexSequence {
		
		public Forward() {
			sign = 1;
		}

		@Override
		protected int compare(MutableNumber x, MutableNumber y) {
			return math.compare(math.getValue(x), math.getValue(y));
		}
		
		@Override
		protected void add(MutableNumber x, Number y) {
			x.add(y);
		}
		
		@Override
		protected MutableNumber subtract(MutableNumber x, MutableNumber y) {
			return math.subtract(x, y);
		}
		
		@Override
		protected boolean hasNextExtent() {
			return i < order.items.size() - 1;
		}

		@Override
		protected boolean hasNextHidden() {
			return h < hidden.items.size() - 1;
		}
		
		@Override
		protected int firstIndex(List<Extent> items) {
			return -1;
		}
		
		@Override
		protected MutableNumber start(Extent e) {
			return e.start;
		}

		@Override
		protected MutableNumber end(Extent e) {
			return e.end;
		}
	}
	class Backward extends IndexSequence {
		
		public Backward() {
			sign = -1;
		}
		
		@Override
		protected int compare(MutableNumber x, MutableNumber y) {
			return math.compare(math.getValue(y), math.getValue(x));
		}
		
		@Override
		protected void add(MutableNumber x, Number y) {
			x.subtract(y);
		}
		

		@Override
		protected MutableNumber subtract(MutableNumber x, MutableNumber y) {
			return math.subtract(y, x);
		}
		
		
		@Override
		protected boolean hasNextExtent() {
			return i > 0;
		}
		
		@Override
		protected boolean hasNextHidden() {
			return h > 0;
		}
		
		@Override
		protected int firstIndex(List<Extent> items) {
			return items.size();
		}
		
		@Override
		protected MutableNumber start(Extent e) {
			return e.end;
		}
		
		@Override
		protected MutableNumber end(Extent e) {
			return e.start;
		}
	}

	public void setHidden(Number index, boolean flag) {
		setHidden(index, index, flag);
	}
	
	public void setHidden(boolean flag) {
		for (int i = 0, imax = selection.items.size(); i < imax; i++) {
			Extent e = selection.items.get(i);
			setHidden(e.start, e.end, flag);
		}
	}
	public Number getLastIndex() {
		return math.decrement(count);
	}

}
