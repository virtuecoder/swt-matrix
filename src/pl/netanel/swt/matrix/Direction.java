package pl.netanel.swt.matrix;

import java.util.ArrayList;
import java.util.List;



abstract class Direction<N extends Number> {
	protected final List<SectionUnchecked> sections;
	protected final Math math;
	SectionUnchecked section;
	DirectionIndexSequence seq;
	int i, level, sign;
	AxisItem freeze, min, start;
	boolean pending, moved, hasMore, skipWithoutCurrent;
	
	public Direction(Math math, List<SectionUnchecked> sections, boolean skipWithoutCurrent2) {
		this.math = math;
		this.sections = sections;
		skipWithoutCurrent = skipWithoutCurrent2;
	}

	public boolean init() {
		i = firstSection();
		level = 0;
		return hasMore = pending = nextItem(null);
	}
	
	protected abstract int firstSection();
	protected abstract boolean hasNextSection();


	public boolean set(AxisItem item) {
		i = sections.indexOf(item.section);
		section = item.section;
		seq = getSequence(section, sign);
		seq.init();
		seq.set(item.index);
		seq.level = 1;
		level = !section.isVisible() || skipWithoutCurrent && !section.isNavigationEnabled() ? 0 : 1;
		pending = false;
		return hasMore = nextItem(null);
	}
	
	public AxisItem getItem() {
		pending = false;
		return hasMore ? new AxisItem(section, seq.index().getValue()) : null;
	}
	
	public AxisItem first() {
		if (!init()) return null;
		return next();
	}

	public AxisItem next() {
		return next(null);
	}
	
	public AxisItem next(MutableNumber count) {
//		Preconditions.checkState(initiated, "direction not initiated, call init() or set() before");
		if (pending && count != null && math.compare(count, math.ONE()) > 0) {
			count.decrement();
			pending = false;
		}
		if (!pending) {
			hasMore = pending = nextItem(count);
		}
		if (pending) {
			return getItem();
		}
		return null;
	}
	
	private boolean nextItem(MutableNumber count) {
		int lastSection = -1;
		moved = false;
		if (count == null) count = math.create(1);
		while (true) {
			switch (level) {
			case 0: // sections
				if (hasNextSection()) {
					i += sign;
					section = sections.get(i);
					if (!section.isVisible() || skipWithoutCurrent && !section.isNavigationEnabled()) {
						lastSection = i - sign;
						continue;
					}
					seq = getSequence(section, sign);
					seq.init();
					level++;
				} else {
					if (skipWithoutCurrent && !section.isNavigationEnabled()) {
						i = lastSection;
						if (0 <= i && i < sections.size()) {
							section = sections.get(i);
						}
					}
					return moved;
				}
				break;
				
			case 1: // extents
				if (seq.next(count)) return true;
				moved = seq.moved;
				level--;
				break;
			}
		}
	}
	

	
	public DirectionIndexSequence getSequence(SectionUnchecked section2, int sign) {
		return sign > 0 
			? new DirectionIndexSequence.Forward(section) 
			: new DirectionIndexSequence.Backward(section);
	}

	

	static class Forward<N extends Number> extends Direction {

		public Forward(Math<N> math, ArrayList<SectionUnchecked<N>> sections, boolean skipWithoutCurrent) {
			super(math, sections, skipWithoutCurrent);
			sign = 1;
		}

		@Override
		protected int firstSection() {
			return -1;
		}

		@Override
		protected boolean hasNextSection() {
			return i < sections.size() - 1;
		}
		
	}
	static class Backward<N extends Number> extends Direction {

		public Backward(Math<N> math, ArrayList<SectionUnchecked<N>> sections, boolean skipWithoutCurrent) {
			super(math, sections, skipWithoutCurrent);
			sign = -1;
		}

		@Override
		protected int firstSection() {
			return sections.size();
		}

		@Override
		protected boolean hasNextSection() {
			return i > 0;
		}
		
	}
}
