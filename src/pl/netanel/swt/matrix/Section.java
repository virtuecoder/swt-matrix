package pl.netanel.swt.matrix;

import java.util.List;

import pl.netanel.util.Preconditions;

/**
 * Uses plain Number parameters which are validated in preconditions.
 * 
 * @author Jacek Kolodziejczyk created 02-03-2011
 */
public class Section<N extends MutableNumber> {

	static final int DEFAULT_CELL_WIDTH = 16;
	static final int DEFAULT_LINE_WIDTH = 1;
	
	final Math math;
	private final MutableNumber count;
	final NumberOrder<N> order;
	private final NumberSet<N> hidden;
	private final NumberSet resizable;
	private final NumberSet moveable;
	private final NumberSet hideable;
	private final IntAxisState cellWidth;
	private final IntAxisState lineWidth;
	private final ObjectAxisState<MutableNumber> cellSpan;
	
	private final NumberQueueSet selection;
	private final NumberQueueSet lastSelection;

	
	private boolean defaultResizable, defaultMoveable, defaultHideable; 
	private boolean isNavigationEnabled, isVisible;
	
	int index; 
	
	public Section(Class<? extends Number> numberClass) {
		super();
		Preconditions.checkNotNullWithName(numberClass, "numberClass");
		math = Math.getInstance(numberClass);
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


	public MutableNumber getCount() {
		return count;
	}

	public void setCount(MutableNumber count) {
		this.count.set(count);
		order.setCount(count);
	}

	public MutableNumber getVisibleCount() {
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

	public void move(MutableNumber start, MutableNumber end, MutableNumber target) {
		order.move(start, end, target);
	}
	
	public void setHidden(MutableNumber start, MutableNumber end, boolean flag) {
		hidden.change(start, end, flag);
	}
	
	public boolean isHidden(MutableNumber index) {
		return hidden.contains(index);
	}
	
	public MutableNumber getHiddenCount(MutableNumber start, MutableNumber end) {
		return hidden.getCount(start, end);
	}
	
	public void setMoveable(MutableNumber start, MutableNumber end, boolean flag) {
		moveable.change(start, end, flag != defaultMoveable);
	}
	
	public boolean isMoveable(MutableNumber index) {
		return moveable.contains(index) != defaultMoveable;
	}
	
	public void setResizable(MutableNumber start, MutableNumber end, boolean flag) {
		resizable.change(start, end, flag != defaultResizable);
	}
	
	public boolean isResizable(MutableNumber index) {
		return resizable.contains(index) != defaultResizable;
	}
	
	public void setHideable(MutableNumber start, MutableNumber end, boolean flag) {
		hideable.change(start, end, flag != defaultHideable);
	}
	
	public boolean isHideable(MutableNumber index) {
		return hideable.contains(index) != defaultHideable;
	}
	
	
	public void setSelected(MutableNumber start, MutableNumber end, boolean flag) {
		selection.change(start, end, flag);
	}
	
	public boolean isSelected(MutableNumber index) {
		return selection.contains(index);
	}
	
	public void backupSelection() {
		lastSelection.replace(selection);
	}
	
	public void restoreSelection() {
		selection.replace(lastSelection);
	}
	
	
	
	public void setCellWidth(MutableNumber start, MutableNumber end, int width) {
		cellWidth.setValue(start, end, width);
	}
	
	public int getCellWidth(MutableNumber index) {
		return cellWidth.getValue(index);
	}
	
	public void setLineWidth(MutableNumber start, MutableNumber end, int width) {
		lineWidth.setValue(start, end, width);
	}
	
	public int getLineWidth(MutableNumber index) {
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
	
	
	public void setCellSpan(MutableNumber index, MutableNumber value) {
		cellSpan.setValue(index, value);
	}
	
	public MutableNumber getCellSpan(MutableNumber index) {
		return cellSpan.getValue(index);
	}


	public MutableNumber getPosition(MutableNumber index) {
		if (hidden.contains(index)) return null;
		MutableNumber hiddenCount = math.create(0);
		MutableNumber pos1 = math.create(0);
		MutableNumber pos2 = math.create(0);
		
		for (int i = 0, size = order.items.size(); i < size; i++) {
			Extent<N> e = order.items.get(i);
			boolean contains = Extent.contains(math, e, index);
			hiddenCount.add(hidden.getCount(e.start, contains ? index : e.end));
			if (contains) {
				return pos2.add(index).subtract(e.start).subtract(hiddenCount);
			}
			pos1.set(pos2);
			pos2.add(e.end).subtract(e.start).increment(); //.subtract(hiddenCount);
		}
		return null;
	}

	
	public MutableNumber getByPosition(MutableNumber position) {
		if (math.compare(position, getVisibleCount()) >= 0) return null;
		
		MutableNumber pos1 = math.create(0);
		MutableNumber pos2 = math.create(0);
		
		for (int i = 0, size = order.items.size(); i < size; i++) {
			Extent<N> e = order.items.get(i);
			pos2.add(e.end).subtract(e.start).subtract(hidden.getCount(e.start, e.end));
			if (math.compare(pos2, position) >= 0) {
				MutableNumber count = hidden.getCount(e.start, pos1);
				return pos1.subtract(position).negate().add(e.start).add(count);
			}
			pos2.increment();
			pos1.set(pos2); 
		}
		return null;
		
//		ForwardOrderIterator it = order.new ForwardOrderIterator();
//		MutableNumber hiddenCount = factory.zero();
//		MutableNumber pos1 = factory.zero();
//		MutableNumber pos2 = factory.zero();
//		while (it.hasNext()) {
//			Extent extent = it.next();
//			pos1.set(it.pos1).subtract(hiddenCount);
//			hiddenCount.add(hidden.getCount(extent.getStart(), extent.getEnd()));
//			pos2.set(it.pos2).subtract(hiddenCount);
//			if (pos2.compareTo(position) >= 0) {
//				MutableNumber count = hidden.getCount(extent.getStart(), pos1);
//				return pos1.subtract(position).negate().add(extent.getStart()).add(count);
//			}
//		}		
//		return null;

	}

	public int comparePosition(MutableNumber n1, MutableNumber n2) {
		return math.compare(order.indexOf(n1), order.indexOf(n2));
	}
	
	
	
	public SectionSequence getSequence(int sign) {
		return sign > 0 ? new Forward() : new Backward();
	}

	
	/**
	 * Iterates over section items in their order and skipping the hidden ones.
	 * Iteration yields nothing if the section is not visible. 
	 */
	public abstract class SectionSequence {
		public MutableNumber number, number2, lastInExtent, last, d;
		protected int i, h;
		public int level;
		protected int sign;
		protected Extent extent, he;
		public boolean moved;
		
		
		public SectionSequence() {
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
				if (he != null && Extent.contains(math, he, number2)) {
					if (!skipHidden(count, lastInExtent)) return false;
				} 
				else {
					limit = he == null || Extent.contains(math, he, number2) ? lastInExtent : start(he);
					d.set(math.min(subtract(limit, number2), count));
					add(number2, d);
					count.subtract(d);
				}
				if (he != null && Extent.contains(math, he, number2)) {
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
		protected abstract void add(MutableNumber x, MutableNumber y);
		protected abstract MutableNumber subtract(MutableNumber x, MutableNumber y);
		protected abstract boolean hasNextExtent();
		protected abstract boolean hasNextHidden();
		protected abstract int firstIndex(List<Extent<N>> items);
		protected abstract MutableNumber start(Extent e);
		protected abstract MutableNumber end(Extent e);


		public MutableNumber index() {
			return number;
		}


		public boolean next() {
			return next(math.create(1));
		}

		public void set(MutableNumber index) {
			i = order.getExtentIndex(index);
			if (i == -1) return;
			extent = order.items.get(i);
			number.set(index).add(-sign);
			lastInExtent.set(end(extent));
			h = firstIndex(hidden.items);
			he = null;
		}
	}
	
	public class Forward extends SectionSequence {
		
		public Forward() {
			sign = 1;
		}

		@Override
		protected int compare(MutableNumber x, MutableNumber y) {
			return math.compare(x, y);
		}
		
		@Override
		protected void add(MutableNumber x, MutableNumber y) {
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
		protected int firstIndex(List<Extent<N>> items) {
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
	public class Backward extends SectionSequence {
		
		public Backward() {
			sign = -1;
		}
		
		@Override
		protected int compare(MutableNumber x, MutableNumber y) {
			return math.compare(y, x);
		}
		
		@Override
		protected void add(MutableNumber x, MutableNumber y) {
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
		protected int firstIndex(List<Extent<N>> items) {
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

	
	
	
	/*------------------------------------------------------------------------
	 * Derived methods
	 */
	
	public Section setCount(Number count) {
		Preconditions.checkNotNull(count);
		MutableNumber count2 = math.getMutable(count);
		Preconditions.checkArgument(math.compare(count2, math.ZERO()) >= 0,
				"Item count must be greater or equal to zero");
		setCount(count2);
		return this;
	}

	public void move(Number start, Number end, Number target) {
		move(math.getMutable(start), math.getMutable(end), math.getMutable(target));
	}
	
	public void setHidden(Number index, boolean flag) {
		setHidden(index, index, flag);
	}
	
	public void setHidden(Number start, Number end, boolean flag) {
		setHidden(math.getMutable(start), math.getMutable(end), flag);
	}
	
	public boolean isHidden(Number index) {
		return isHidden(math.getMutable(index));
	}
	
	public void setMoveable(Number start, Number end, boolean flag) {
		setMoveable(math.getMutable(start), math.getMutable(end), flag);
	}
	
	public boolean isMoveable(Number index) {
		return isMoveable(math.getMutable(index));
	}
	
	public void setResizable(Number start, Number end, boolean flag) {
		setResizable(math.getMutable(start), math.getMutable(end), flag);
	}
	
	public boolean isResizable(Number index) {
		return isResizable(math.getMutable(index));
	}
	
	public void setHideable(Number start, Number end, boolean flag) {
		setHideable(math.getMutable(start), math.getMutable(end), flag);
	}
	
	public boolean isHideable(Number index) {
		return isHideable(math.getMutable(index));
	}
	
	public void setSelected(Number start, Number end, boolean flag) {
		setSelected(math.getMutable(start), math.getMutable(end), flag);
	}
	
	public boolean isSelected(Number index) {
		return isSelected(math.getMutable(index));
	}

	public void setCellWidth(Number start, Number end, int width) {
		setCellWidth(math.getMutable(start), math.getMutable(end), width);
	}
	
	public int getCellWidth(Number index) {
		return getCellWidth(math.getMutable(index));
	}
	
	public void setLineWidth(Number start, Number end, int width) {
		setLineWidth(math.getMutable(start), math.getMutable(end), width);
	}

	public int getLineWidth(Number index) {
		return getLineWidth(math.getMutable(index));
	}

	public MutableNumber getLast() {
		return math.decrement(getCount());
	}




}
