package pl.netanel.swt.matrix;

import java.util.ArrayList;
import java.util.List;



abstract class Direction<N extends Number> {
	protected final List<SectionCore<N>> sections;
	protected final Math<N> math;
	SectionCore<N> section;
	DirectionIndexSequence<N> seq;
	int i, level, sign;
	AxisItem<N> freeze, min, start;
	boolean pending, moved, hasMore, skipWithoutCurrent;
	
	public Direction(Math<N> math, List<SectionCore<N>> sections, boolean skipWithoutCurrent2) {
		this.math = math;
		this.sections = sections;
		skipWithoutCurrent = skipWithoutCurrent2;
	}

	public boolean init() {
		i = firstSection();
		level = 0;
		return hasMore = pending = nextInternal(null);
	}
	
	protected abstract int firstSection();
	protected abstract boolean hasNextSection();


	public boolean set(AxisItem<N> item) {
		i = sections.indexOf(item.section);
		section = item.section;
		seq = getSequence(section, sign);
		seq.init();
		if (!seq.set(item.getIndex())) return false;
		seq.level = 1;
		level = !section.isVisible() || skipWithoutCurrent && !section.isFocusItemEnabled() ? 0 : 1;
		pending = false;
		return hasMore = nextInternal(null);
	}
	
	public AxisItem<N> getItem() {
		pending = false;
		return hasMore ? AxisItem.createInternal(section, seq.index().getValue()) : null;
	}
	
	public AxisItem<N> first() {
		if (!init()) return null;
		return next();
	}
	
	public AxisItem<N> nextItem(AxisItem<N> item) {
		if (!set(item)) return null;
		return getItem();
	}

	public AxisItem<N> next() {
		return next(null);
	}
	
	public AxisItem<N> next(MutableNumber<N> count) {
//		Preconditions.checkState(initiated, "direction not initiated, call init() or set() before");
		if (pending && count != null && math.compare(count, math.ONE()) > 0) {
			count.decrement();
			pending = false;
		}
		if (!pending) {
			hasMore = pending = nextInternal(count);
		}
		if (pending) {
			return getItem();
		}
		return null;
	}
	
	private boolean nextInternal(MutableNumber<N> count) {
		int lastSection = -1;
		moved = false;
		if (count == null) count = math.create(1);
		while (true) {
			switch (level) {
			case 0: // sections
				if (hasNextSection()) {
					i += sign;
					section = sections.get(i);
					if (!section.isVisible() || skipWithoutCurrent && !section.isFocusItemEnabled()) {
						lastSection = i - sign;
						continue;
					}
					seq = getSequence(section, sign);
					seq.init();
					level++;
				} else {
					if (skipWithoutCurrent && !section.isFocusItemEnabled()) {
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
	

	
	public DirectionIndexSequence<N> getSequence(Section<N> section2, int sign) {
		return sign > 0 
			? new DirectionIndexSequence.Forward<N>(section) 
			: new DirectionIndexSequence.Backward<N>(section);
	}

	

	static class Forward<N extends Number> extends Direction<N> {

		public Forward(Math<N> math, ArrayList<SectionCore<N>> sections, boolean skipWithoutCurrent) {
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
	static class Backward<N extends Number> extends Direction<N> {

		public Backward(Math<N> math, ArrayList<SectionCore<N>> sections, boolean skipWithoutCurrent) {
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
