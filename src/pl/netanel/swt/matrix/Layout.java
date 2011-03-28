package pl.netanel.swt.matrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.netanel.swt.matrix.Direction.Backward;
import pl.netanel.swt.matrix.Direction.Forward;
import pl.netanel.util.Preconditions;
import pl.netanel.util.Util;


class Layout<N extends Number> {
	private static final String FREEZE_ITEM_COUNT_ERROR = "Freeze item count must greater then 0";
	
	Math math;

	private int viewportSize;
	final CountCache head, tail;
	final DistanceCache main;
	final ArrayList<Cache> caches;
	
	final MutableNumber total, maxInteger, maxScroll, scrollTotal;
	final MutableNumber scrollPosition; // for head and tail it stores min and max scroll position
	AxisItem<N> start, end, endNoTrim, current, zeroItem;

	ArrayList<Runnable> callbacks;

	boolean isComputingRequired;

	public Direction forward, backward, forwardNavigator, backwardNavigator;
	final Axis<N> axis;

	private ArrayList<SectionUnchecked<N>> sections;


	
	public Layout(Axis<N> axis) {
		Preconditions.checkArgument(axis.getSectionCount() > 0, "Layout must have at least one section");
		this.axis = axis;
		math = axis.math;
		sections = new ArrayList();
		for (int i = 0, imax = axis.sections.size(); i < imax; i++) {
			SectionUnchecked<N> section2 = axis.sections.get(i).core;
			section2.index = i;
			sections.add(section2);
		}
//		if (sections.length == 0) {
//		sections.add(new Section(numberClass)); // header
//		sections.add(new Section(numberClass)); // body
		
		forward = new Direction.Forward(math, sections, false);
		backward = new Direction.Backward(math, sections, false);
		forwardNavigator = new Direction.Forward(math, sections, true);
		backwardNavigator = new Direction.Backward(math, sections, true);
		
		head = new CountCache(forward);
		main = new DistanceCache();
		tail = new CountCache(backward);
		caches = new ArrayList<Cache>();
		caches.add(head); caches.add(main); caches.add(tail);
		
		Section section = axis.sections.get(0);
		start = new AxisItem(section.core, math.ZERO_VALUE());
		zeroItem = new AxisItem(section.core, math.ZERO_VALUE());
		forwardNavigator.init();
		current = forwardNavigator.getItem();
		total = math.create(0);
		maxInteger = math.create(Integer.MAX_VALUE);
		maxScroll = math.create(0);
		scrollTotal = math.create(0);
		scrollPosition = math.create(0);
		
		isComputingRequired = true;
	}

	public int getViewportSize() {
		return viewportSize;
	}

	public void setViewportSize(int viewportSize) {
		this.viewportSize = viewportSize;
		isComputingRequired = true;
	}

	public Section getSection(int i) {
		return axis.sectionMap.get(sections.get(i));
	}

	/**
	 * Computes forward from the current start position. Used to refresh the layout after some changes.
	 */
	public void compute() {
		compute(start, forward);
	}
	
	/**
	 * Computes the layout.
	 * @param origin
	 * @param sign
	 */
	protected void compute(AxisItem start, Direction direction) {
//		Preconditions.checkState(viewportSize > 0, "Cannot compute for viewport size 0");
		this.start = start;
		
		// Compute total and check if body exists
		total.set(math.ZERO_VALUE()); 
		for (SectionUnchecked section: sections) {
			total.add(section.getVisibleCount());
		}
		
		computeCache(start, direction);
		
		if (current == null) {
			current = forwardNavigator.first();
		}
		adjustHiddenHiddenItem();
		
//		// Run callbacks
//		for (Runnable runnable: callbacks) {
//			runnable.run();
//		}
//		callbacks.clear();
		isComputingRequired = false;
	}
	
	public void computeCache(AxisItem origin, Direction dir) {
		// Frozen
		head.compute(viewportSize);
		tail.compute(viewportSize - head.innerWidth);
		
		if (!head.isEmpty() && axis.comparePosition(origin, forward.min) < 0) {
			origin = forward.getItem();
		} else if (!tail.isEmpty() && axis.comparePosition(origin, backward.min) > 0) {
			origin = backward.getItem();
		}
		dir.set(origin);
		
		// Main
		int mainMaxWidth = viewportSize - head.innerWidth - tail.innerWidth;
		main.compute(dir, mainMaxWidth);
		Direction opposite = opposite(dir);
		if (main.outerWidth < mainMaxWidth && (dir.min == null || !dir.start.equals(dir.min)))
		{
			AxisItem origin2 = opposite.start;
			dir = opposite(dir);
			dir.set(origin2);
			main.compute(dir, mainMaxWidth);
		}
		
		// Set distances
		head.setDistances(0);
		main.setDistances(head.innerWidth);
//		tail.setDistances(main.outerWidth > mainMaxWidth ?
		tail.setDistances(isScrollRequired() ?
				viewportSize - tail.outerWidth :
				head.innerWidth + main.outerWidth - tail.lastLineWidth
		);
	}
	
	public void computeIfRequired() {
		if (isComputingRequired) {
			compute();
		}
	}
	public int computeSize(int hint, int max, boolean changed) {
		int oldSize = viewportSize;
		viewportSize = hint == -1 ? max : hint;
		compute();
		int newSize = tail.distance + tail.outerWidth;
		viewportSize = oldSize;
		compute();
		return newSize;
	}
	
	public void adjustHiddenHiddenItem() {
		if (current == null) return;
		for (int section = current.section.index; section < axis.getSectionCount(); section++) {
			N index2 = current.section.nextNotHiddenIndex(current.index, 1);
			if (index2 != null) {
				current.index = index2;
			}
			if (math.compare(current.index, current.section.getCount()) < 0) return;
		}
	}
	
	private Direction opposite(Direction direction) {
		return direction instanceof Forward ? backward : forward;
	}
	
	int compare(AxisItem<N> item1, AxisItem<N> item2) {
		int diff = item1.section.index - item2.section.index;
		if (diff != 0) return diff;
		return math.compare(
				item1.section.indexOf(item1.index), 
				item2.section.indexOf(item2.index));
	}
	
	
	
	/*------------------------------------------------------------------------
	 * Navigation 
	 */

	public void setCurrentItem(AxisItem item) {
		if (isComputingRequired) compute();
		
    		 if (forwardNavigator.set(item))  	current = forwardNavigator.getItem();
		else if (backwardNavigator.set(item))   current = backwardNavigator.getItem();
		else 									current = null;
	}
	
	/**
	 * Return true if the current item has changed.
	 * @param move
	 * @return 
	 * @return
	 */
	// TODO Performance: prevent computation if current does not change
	public boolean moveCurrentItem(Move move) {
		AxisItem current2 = null;
		switch (move) {
		case HOME: 				current2 = forwardNavigator.first(); break;
		case END: 				current2 = backwardNavigator.first(); break;
		case NEXT: 				current2 = nextItem(current, forwardNavigator); break;
		case PREVIOUS: 			current2 = nextItem(current, backwardNavigator); break;
		case NEXT_PAGE:			show(current); current = nextPage(current, forwardNavigator); return true;
		case PREVIOUS_PAGE:		show(current); current = nextPage(current, backwardNavigator); return true;
		}
		if (current2 != null) {
			show(current = current2);
		}
		return true;
	}
	
	/*------------------------------------------------------------------------
	 * Scroll 
	 */

	public void show(AxisItem item) {
		if (item == null) return;
		if (isComputingRequired) compute();
		
		if (axis.comparePosition(item, endNoTrim) > 0) {
			compute(item, backward);
		} 
		else if (axis.comparePosition(item, start) < 0) {
			compute(item, forward);
		} 
		// else it is visible already
	}
	
	AxisItem scroll(MutableNumber itemCount, Direction direction) {
		if (isComputingRequired) compute();
		AxisItem start2 = nextItem(start, itemCount, direction);
		if (start2 == null || compare(start2, start) == 0) return null;
		start = start2;
		compute();
		return direction instanceof Forward ? end : start;
	}

	public void scrollTo(AxisItem item) {
		if (item.equals(start) || isOutOfBounds(item)) return;
		if (forward.set(start = item)) {
			start = forward.getItem();
		}
//		if (forward.hasNext()) {
//			start = backward.set(start).getItem();
//		}
	}

	public int getScrollMin() {
		return head.count;
	}

	public int getScrollMax() {
		return math.compare(maxScroll, maxInteger) <= 0 ? 
				maxScroll.intValue() : maxInteger.intValue(); 
	}

	public int getScrollThumb() {
		return main.cells.size() - (compare(endNoTrim, end) == 0 ? 0 : 1);
	}

	public int getScrollPosition() {
		MutableNumber index = math.create(scrollPosition); 
		if (isIntegerTooSmall()) {
			index.set(scrollPosition).multiply(maxInteger).divide(scrollTotal);
		}
		return index.intValue();
	}

	
	/**
	 * Moves the scroll position to given position or by the given move amount.
	 * When the move value is given then position is ignored.
	 * @param position
	 * @param move
	 */
	// TODO Performance: prevent computing in edge cases or when the position is the same 
	public void setScrollPosition(int position, Move move) {
//		if (main.scroll == position) return;
 		AxisItem item = start;
		switch (move) {
		case NEXT: 			item = nextItem(start, forward); break;
		case PREVIOUS: 		item = nextItem(start, backward); break;
		case NEXT_PAGE: 	nextPage(backward.start, forward); return;
		case PREVIOUS_PAGE: nextPage(forward.start, backward); return;
			
		default:
			MutableNumber index = math.create(position);
			if (isIntegerTooSmall()) {
				if (position >= getScrollMax() - getScrollMin() - getScrollThumb()) {
					/* Enforce going to the end by dragging, 
						since the thumb number of items will not fit in the viewport */
					item = backward.min;
				} else {
					index.multiply(total);
					index.divide(maxInteger);
					item = getItemByPosition(index);
				}
			} else {
				item = getItemByPosition(index);
			}
		}
		// item can be null if position is out of scope
		if (item != null) {
			compute(item, forward);
		}
	}

	private boolean isIntegerTooSmall() {
		return math.compare(total, maxInteger) >= 0;
	}

	public boolean isScrollRequired() {
		if (main.isEmpty()) return false;
		return compare(start, forward.min) != 0 || compare(endNoTrim, backward.min) != 0; 
	}
	
	public boolean autoScroll(int diff) {
		if (diff > 0) {
			if (compare(endNoTrim, backward.min) >= 0 ) return false;
			start = nextItem(start, forward);
		}
		else {
			if (compare(start, forward.min) <= 0) return false;
			start = nextItem(start, backward);
		}
		isComputingRequired = true;
		return true;
	}

	public int getAutoScrollOffset(int lastDistance, int distance) {
		if (lastDistance < main.distance || lastDistance > main.distance + main.outerWidth) return 0;
		
		int margin = axis.getAutoScrollOffset();
		int offset = distance - (tail.distance - margin);
		if (offset > 0 && lastDistance < distance && compare(endNoTrim, backward.min) < 0) {
			return offset;
		}
		offset = distance - (head.outerWidth + margin);
		if (offset < 0 && lastDistance > distance && compare(start, forward.min) > 0 ) {
			return offset;
		}
		return 0;
	}
	
	private AxisItem nextItem(AxisItem item, Direction direction) {
		if (item == null) item = direction.first();
		if (item == null) return null;
		direction.set(item);
		return direction.next();
	}

	AxisItem nextItem(AxisItem item, MutableNumber itemCount, Direction direction) {
		// TODO skip the hidden items
		if (!direction.init()) return item;
		direction.set(item);
		return direction.next(math.create(itemCount));
	}
	
	private AxisItem nextPage(AxisItem item, Direction direction) {
		if (item == null) item = direction.first();
		if (item == null) return null;
		Direction opposite = opposite(direction);
		if (item.equals(opposite.start)) {
			AxisItem item2 = nextItem(item, direction);
//			boolean math.compare(notEdge = item2, opposite.min) < 0;
//			notEdge = iterator == forward ? notEdge : !notEdge; 
			if (item2 != null) { // && notEdge) {
				compute(item2, direction instanceof Forward ? forward : backward);
			}
		}
		return opposite.start;
	}
	
	/**
	 * Returns item that can be resized due to the distance in the viewport 
	 * or null if distance does not imply resizing or the item is not resizable.
	 * 
	 * @param distance
	 * @return
	 */
	public AxisItem getResizeItem(int distance) {
		int resizeMargin = axis.getResizeOffset();
		Cache cache = getCache(distance - resizeMargin);
			
		for (int i = 1; i < cache.lines.size(); i++) {
			Bound bound = cache.lines.get(i);
			int left = bound.distance - resizeMargin;
			int right = bound.distance + bound.width + resizeMargin;
			if (left <= distance && distance <= right) {
				AxisItem item = cache.items.get(i - 1);
				return item.section.isResizable(item.index) ? item : null;		
			}
		}
		return null; 
	}
	/**
	 * Override this method to throw IndexOutOfBoundsException 
	 * in case index is not lower then the section count.  
	 */
	protected boolean isOutOfBounds(AxisItem<N> item) {
		return math.compare(item.index, item.section.getCount()) >= 0;
	}

	
	
	/*------------------------------------------------------------------------
	 * Cache 
	 */

	/**
	 * Stores index data for a dock area of an <code>AxisLayout</code>.
	 * 
	 * @author Jacek created 08-12-2010
	 */
	abstract class Cache {
		ArrayList<AxisItem> items;
		ArrayList<Bound> cells, lines;
		ArrayList<SectionUnchecked> sections;
		int distance, innerWidth, outerWidth, freezeLineWidth, lastLineWidth;
		Direction direction;

		AxisItem item;


		public Cache() {
			sections = new ArrayList<SectionUnchecked>();
			items = new ArrayList<AxisItem>();
			cells = new ArrayList<Bound>();
			lines = new ArrayList<Bound>();
			freezeLineWidth = -1;
		}

		/**
		 * 
		 * @param cache
		 * @param maxWidth
		 * @param count if count <= 0 then it is ignored in the loop
		 * @param canTrim
		 * @return
		 */
		void compute(Direction dir, int maxWidth, int count, boolean canTrim) {
			item = dir.getItem();
			if (item == null || maxWidth <= 0) return;

			this.direction = dir;
			innerWidth = 0;
			Bound bound1, bound2;

			if (dir instanceof Backward) {
				lastLine(dir.section, dir.seq.index().getValue());
			}
			for (int i = 0; condition() && item != null; i++) {
				bound1 = new Bound(0, dir.section.getLineWidth(dir.seq.index().getValue()));
				bound2 = new Bound(0, dir.section.getCellWidth(dir.seq.index().getValue()));
				int width = bound1.width + bound2.width;

				if (!canTrim && innerWidth + width > maxWidth) break;

				innerWidth += bound1.width + bound2.width;
				items.add(item); // = dir.getItem());
				lines.add(bound1);
				cells.add(bound2);
				if (!sections.contains(dir.section)) {
//					// Decide from which section line at the edge is to be drawn.  
//					if (!sections.isEmpty()) {
//						Section lastSection = sections.get(sections.size() - 1);
//						if (model.getZIndex(lastSection) > model.getZIndex(item.section)) {
//							AxisItem item2 = item.copy();
//							item.section = lastSection;
//							items.add(item2);
//						}
//					}
					sections.add(dir.section);
				}

				if (canTrim && innerWidth > maxWidth) break;
				item = dir.next();
			}

			if (count >= 0) {
				// It is needed for distance cache limits
				direction.freeze = isEmpty() ? null : items.get(items.size() - 1);
				//direction.min = (direction == forward ? forwardNavigator : backwardNavigator).first();
				direction.min = item == null ? direction.first() : item;
			}
			if (dir instanceof Backward) reverse();

			outerWidth = innerWidth;
			Bound lastLine = null;
			if (!isEmpty()) {
				if (dir instanceof Forward) { 
					AxisItem item2 = items.get(items.size() - 1);
					lastLine = lastLine(item2.section, item2.index);
				} else {
					lastLine = lines.get(0);
				}
				outerWidth += lastLineWidth = lastLine.width;

				if (freezeLineWidth >= 0) {
					//				int sign = direction instanceof Forward ? 1 : -1;
					int diff = (freezeLineWidth - lastLine.width);// * sign;
					innerWidth += diff;
					outerWidth += diff;
					lastLine.width = freezeLineWidth;
				}
			} 
		}

		Bound lastLine(SectionUnchecked section, Number index) {
			Number index2 = section.math.increment(index);
			Bound bound = new Bound(0, section.getLineWidth(index2));
			lines.add(bound);
			items.add(new AxisItem(section, index2));
			return bound;
		}


		void reverse() {
			Collections.reverse(items);
			Collections.reverse(lines);
			Collections.reverse(cells);
			Collections.reverse(sections);
		}

		void setDistances(int distance) {
			this.distance = distance;
			for (int i = 0; i < cells.size(); i++) {
				Bound line = lines.get(i);
				line.distance = distance;
				distance += line.width;
				Bound cell = cells.get(i);
				cell.distance = distance;
				distance += cell.width;
			}
			if (!isEmpty()) {
				lines.get(lines.size() - 1).distance = distance;
			}
		}

		public void clear() {
			items.clear();
			lines.clear();
			cells.clear();
			sections.clear();
		}

		protected boolean condition() { return true; }
		boolean isEmpty() { return cells.isEmpty(); }
	}
	
	/**
	 * Computes given amount of items. Used for frozen areas.  
	 * 
	 * @author Jacek created 08-12-2010
	 */
	class CountCache extends Cache {
		int count;
		
		public CountCache(Direction direction) {
			this.direction = direction;
		}

		@Override
		protected boolean condition() {
			return cells.size() < count;
		}
		
		public void compute(int maxWidth) {
			clear();
			if (!direction.init()) return;
			super.compute(direction, maxWidth, count, false);
			
//			iterator.min = iterator.item;
//			iterator.freeze = iterator.last;
		}
	}

	/**
	 * Computes items to fit in the given distance range. 
	 * Start distance is passed to the compute method.
	 * 
	 * @author Jacek created 08-12-2010
	 */
	class DistanceCache extends Cache {
		@Override
		protected boolean condition() {
			return item == null || !item.equals(forward.freeze) && !item.equals(backward.freeze);
		}
		
		public void compute(Direction direction, int maxWidth) {
			clear();
			
			super.compute(direction, maxWidth, -1, direction == forward);

			start = Util.notNull(isEmpty() ? forward.min : items.get(0), zeroItem);
			end = Util.notNull(isEmpty() ? forward.min: items.get(items.size() - 2), zeroItem);
			
			// Compute last index that fully visible (not trimmed) 
			endNoTrim = end;
			if (cells.size() > 1) {
				if (outerWidth > maxWidth) {
					endNoTrim = items.get(items.size() - 3); 
				}
			}
			forward.start = start;
			backward.start = endNoTrim;
			
			maxScroll.set(total).add(-tail.count);
			scrollTotal.set(maxScroll).add(-head.count);
			if (!main.isEmpty()) scrollPosition.set(getItemPosition(start));
		}
	}

	private Cache getCache(Dock dock) {
		return 	dock == Dock.MAIN ? main : 
				dock == Dock.HEAD ? head : tail;
	}
	
	private Cache getCache(int distance) {
		return distance < main.distance && !head.isEmpty() ? head :
			   distance > tail.distance && !tail.isEmpty() ? tail : main;
	}
	
	private Cache getCache(SectionUnchecked section, Number index) {
		for (Cache cache: caches) {
			int len = cache.cells.size();
			for (int i = 0; i < len; i++) {
				AxisItem<N> item = cache.items.get(i);
				if (item.section.equals(section) && math.compare(item.index, index) == 0) {
					return cache;
				}
			}
		}
		return null;
	}

	
//	private Cache getCache(int distance) {
//		return distance < main.distance && !head.isEmpty() ? head :
//			   distance > tail.distance && !tail.isEmpty() ? tail : main;
//	}
//	
//	private Cache getCache(int section, MutableNumber index) {
//		for (Cache cache: new Cache[] {head, main, tail}) {
//			int len = cache.cells.size();
//			for (int i = 0; i < len; i++) {
//				AxisItem item = cache.items.get(i);
//				if (item.section.equals(section) && math.compare(item.index, index) == 0) {
//					return cache;
//				}
//			}
//		}
//		return null;
//	}
	
	MutableNumber getItemPosition(AxisItem<N> item) {
		MutableNumber position = math.create(0);
		for (int i = 0, size = sections.size(); i < size; i++) {
			SectionUnchecked<N> section = sections.get(i);
			if (item.section.equals(section)) {
				return position.add(math.getValue(section.indexOfNotHidden(item.index)));
			}
			position.add(section.getVisibleCount());
		}
		return null;
	}
	
	AxisItem getItemByPosition(MutableNumber<N> position) {
		MutableNumber<N> pos1 = math.create(0);
		MutableNumber<N> pos2 = math.create(0);
		
		for (int i = 0, size = sections.size(); i < size; i++) {
			SectionUnchecked<N> section = sections.get(i);
			pos1.set(pos2);
			pos2.add(section.getVisibleCount());
			if (math.compare(pos2, position) > 0) {
				return new AxisItem(section, section.get(pos1.subtract(position).negate().getValue()));
			}
		}
		return null;
	}

	/**
	 * Gets n-th item from the viewport caches that meets the condition:
	 * lines[n].distance < distance <= cells[n].(distance + width)
	 * @param distance in the viewport
	 * @return
	 */
	public AxisItem getItemByDistance(int distance) {
		Cache cache = getCache(distance);
		AxisItem item = null;
		for (int i = 0; i < cache.cells.size(); i++) {
			Bound bounds = cache.cells.get(i);
			item = cache.items.get(i);
			if (distance <= bounds.distance + bounds.width) {
				return item;
			}
		}
		return item;
//		return getBeyond && !cache.isEmpty() ? cache.items.get(cache.items.size() - 2) : null;
	}

	
	public LayoutSequence cellSequence(Dock dock, SectionUnchecked section) {
		if (isComputingRequired) compute();
		Cache cache = getCache(dock);
		return new LayoutSequence(cache.items, cache.cells, section);
	}
	
	public LayoutSequence lineSequence(Dock dock, SectionUnchecked section) {
		if (isComputingRequired) compute();
		Cache cache = getCache(dock);
		return new LayoutSequence(cache.items, cache.lines, section);
	}
	
	public LayoutSequence singleSequence(int distance, int width) {
		LayoutSequence seq = new LayoutSequence(null, null, null) {
			private boolean started;

			public void init() {
				started = false;
			}
			@Override
			public boolean next() {
				if (started) return false;
				return started = true;
			}
		};
		seq.bound = new Bound(distance, width);
		return seq;
	}

	
	
	public class LayoutSequence {

		private final List<AxisItem> items;
		private final List<Bound> bounds;
		private final SectionUnchecked section;
		private int i;
		Bound bound;
		AxisItem<N> item;

		public LayoutSequence(List<AxisItem> items, List<Bound> bounds, SectionUnchecked section) { 
			this.items = items;
			this.bounds = bounds;
			this.section = section;
		}

		public void init() {
			for (i = 0; i < items.size(); i++) {
				if (items.get(i).section.equals(section)) break;
			}
		}
		
		public boolean next() {
			if (i >= bounds.size()) return false;
			SectionUnchecked section2 = items.get(i).section;
			if (!section2.equals(section)) {
				// Make sure last line is included between sections  
				if (items.size() == bounds.size() && 
					axis.getZIndex(section2) < axis.getZIndex(item.section)) 
				{
					item = new AxisItem(item.section, math.increment(item.index));
					bound = bounds.get(i);
					i = bounds.size();
					return true;
				}
				return false;
			}
			bound = bounds.get(i);
			item = items.get(i++);
			return true;
		}
		
		public AxisItem getItem() {
			return item;
		}
		
		public int getDistance() {
			return bound.distance;
		}
		
		public int getWidth() {
			return bound.width;
		}

		public N getIndex() {
			return item == null ? null : item.index;
		}
	}

	public boolean contains(Dock dock, SectionUnchecked section) {
		List<SectionUnchecked> sections = getCache(dock).sections;
		if (sections.contains(section)) {
			return true;
		}
		return false;
	}

	public Bound getBound(Dock dock) {
		Cache cache = getCache(dock);
		return new Bound(cache.distance, cache == main 
				? viewportSize - head.innerWidth - tail.innerWidth
				: cache.outerWidth);
	}
	
	// TODO cache the section bonds in a dock 
	public Bound getBound(Dock dock, SectionUnchecked section) {
		Cache cache = getCache(dock);
		int first = -1, last = -1;
		for (int i = 0, size = cache.items.size(); i < size; i++) {
			if (cache.items.get(i).section.equals(section)) {
				if (first == -1) {
					first = i;
				}
			} else if (first != -1) {
				last = i;
				break;
			}
		}
		if (first == -1) {
			return new Bound(0, 0);
		} else {
			Bound b = cache.lines.get(last == -1 ? cache.items.size() - 1 : last);
			int distance = cache.lines.get(first).distance;
			return new Bound(distance, b.distance + b.width - distance);
			
		}
	}
	
	public Bound getBound(AxisItem item) {
		Cache cache = getCache(item.section, item.index);
		if (cache == null) return null;
		for (int i = 0, size = cache.cells.size(); i < size; i++) {
			if (cache.items.get(i).equals(item)) {
				return cache.cells.get(i);
			}
		}
		return null;
	}


	public boolean isEmpty() {
		return head.isEmpty() && main.isEmpty() && tail.isEmpty();
	}

	
	public void freezeHead(int freezeItemCount) {
		Preconditions.checkArgument(freezeItemCount >= 0, FREEZE_ITEM_COUNT_ERROR);
		isComputingRequired = head.count != freezeItemCount;
		head.count = freezeItemCount;
	}

	public void freezeTail(int freezeItemCount) {
		Preconditions.checkArgument(freezeItemCount >= 0, FREEZE_ITEM_COUNT_ERROR);
		isComputingRequired = tail.count != freezeItemCount;
		tail.count = freezeItemCount;
	}

	public boolean reorder(AxisItem<N> source, AxisItem<N> target) {
		SectionUnchecked<N> section = source.section;
		if (!section.equals(target.section)) return false;
		
		int position = compare(target, start);
		
		if (!section.moveSelected(source.index, target.index)) return false;
		
		// Adjust the scroll position if moving before the start
		if (position <= 0) {
			start = new AxisItem(section, section.getSelected().next());
		} 
		// Adjust the scroll position if moving the start
		else if (section.isSelected(start.index)) {
			start = new AxisItem(section, target.index);
		}
		
		compute();
		return true;
	}

		
}
