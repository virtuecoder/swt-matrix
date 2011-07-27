package pl.netanel.swt.matrix;

import java.util.List;

/**
 * Iterates over section items in their order and skipping the hidden ones.
 * Iteration yields nothing if the section is not visible. 
 */
abstract class DirectionIndexSequence<N extends Number> implements Sequence {
	protected final SectionCore<N> section;
	protected Math<N> math;
	public MutableNumber<N> number, number2, lastInExtent, last, d;
	protected int i, h;
	public int level;
	protected int sign;
	protected Extent<N> extent, he;
	public boolean moved;
	
	
	public DirectionIndexSequence(SectionCore<N> section) {
		super();
		this.section = section;
		this.math = section.math;
		number = this.section.math.create(0);
		number2 = this.section.math.create(0);
		lastInExtent = this.section.math.create(0);
		last = this.section.math.create(-1);
		d = this.section.math.create(0);
	}

	public void init() {
		i = firstIndex(this.section.order.items);
	}
	
	boolean next(MutableNumber<N> index) {
		moved = false;
		while (true) {
			switch(level) {
			case 0: // extents
				if (hasNextExtent()) {
					i += sign;
					extent = this.section.order.items.get(i);
					number.set(start(extent)).add(-sign);
					lastInExtent.set(end(extent));
					h = firstIndex(this.section.hidden.items);
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
	

	private boolean nextNumber(MutableNumber<N> count) {
		MutableNumber<N> limit = null;

		number2.set(number).add(sign);
		count.decrement();
		while (compare(number, lastInExtent) < 0) {
			nextHidden(lastInExtent);

			// If inside of hidden move beyond
			if (he != null && this.section.math.contains(he, number2)) {
				if (!skipHidden(count, lastInExtent)) return false;
			} 
			else {
				limit = he == null || this.section.math.contains(he, number2) ? 
						lastInExtent : start(he);
				d.set(this.section.math.min(subtract(limit, number2), count));
				add(number2, d.getValue());
				count.subtract(d);
			}
			if (he != null && this.section.math.contains(he, number2)) {
				if (!skipHidden(count, lastInExtent)) return false;
			}			
			moved = this.section.math.compare(number2, last) != 0;
			number.set(number2);
			last.set(number);
			
			if (this.section.math.compare(count, this.section.math.ZERO()) <= 0) return true; 
		}
		return false; 
	}
	

	private void nextHidden(MutableNumber<N> lastIndex) {
		while (true) {
			if (he != null) {
				MutableNumber<N> start = start(he), end = end(he);
				if (compare(start, number2) <= 0 && compare(number2, end) <= 0 || 
					compare(start, number2) > 0 && compare(start, lastIndex) <= 0) break;
			}
			if (hasNextHidden()) {
				he = this.section.hidden.items.get(h += sign);
			} else {
				he = null;
				break;
			}
		}
	}
	
	private boolean skipHidden(MutableNumber<N> count, MutableNumber<N> lastIndex) {
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

	protected abstract int compare(MutableNumber<N> x, MutableNumber<N> y);
	protected abstract void add(MutableNumber<N> x, N y);
	protected abstract MutableNumber<N> subtract(MutableNumber<N> x, MutableNumber<N> y);
	protected abstract boolean hasNextExtent();
	protected abstract boolean hasNextHidden();
	protected abstract int firstIndex(List<Extent<N>> items);
	protected abstract MutableNumber<N> start(Extent<N> e);
	protected abstract MutableNumber<N> end(Extent<N> e);


	public MutableNumber<N> index() {
		return number;
	}


	@Override
	public boolean next() {
		return next(this.section.math.create(1));
	}

	boolean set(N index) {
		i = this.section.order.getExtentIndex(index);
		if (i == -1) return false;
		extent = this.section.order.items.get(i);
		number.set(index).add(-sign);
		lastInExtent.set(end(extent));
		h = firstIndex(this.section.hidden.items);
		he = null;
		return true;
	}
	
	static class Forward<N extends Number> extends DirectionIndexSequence<N> {
		
		public Forward(SectionCore<N> section) {
			super(section);
			sign = 1;
		}

		@Override
		protected int compare(MutableNumber<N> x, MutableNumber<N> y) {
			return math.compare(x, y);
		}
		
		@Override
		protected void add(MutableNumber<N> x, N y) {
			x.add(y);
		}
		
		@Override
		protected MutableNumber<N> subtract(MutableNumber<N> x, MutableNumber<N> y) {
			return math.create(x).subtract(y);
		}
		
		@Override
		protected boolean hasNextExtent() {
			return i < section.order.items.size() - 1;
		}

		@Override
		protected boolean hasNextHidden() {
			return h < section.hidden.items.size() - 1;
		}
		
		@Override
		protected int firstIndex(List<Extent<N>> items) {
			return -1;
		}
		
		@Override
		protected MutableNumber<N> start(Extent<N> e) {
			return e.start;
		}

		@Override
		protected MutableNumber<N> end(Extent<N> e) {
			return e.end;
		}
	}
	
	static class Backward<N extends Number> extends DirectionIndexSequence<N> {
		
		public Backward(SectionCore<N> section) {
			super(section);
			sign = -1;
		}
		
		@Override
		protected int compare(MutableNumber<N> x, MutableNumber<N> y) {
			return math.compare(y, x);
		}
		
		@Override
		protected void add(MutableNumber<N> x, N y) {
			x.subtract(y);
		}
		

		@Override
		protected MutableNumber<N> subtract(MutableNumber<N> x, MutableNumber<N> y) {
			return math.create(y).subtract(x);
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
		protected MutableNumber<N> start(Extent<N> e) {
			return e.end;
		}
		
		@Override
		protected MutableNumber<N> end(Extent<N> e) {
			return e.start;
		}
	}

}