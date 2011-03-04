package pl.netanel.swt.matrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.netanel.swt.matrix.Direction.Backward;
import pl.netanel.swt.matrix.Direction.Forward;
import pl.netanel.util.Preconditions;
import pl.netanel.util.Util;


class Layout {
	Math math;
	final ArrayList<Section> sections;

	private int viewportSize;
	final CountCache head, tail;
	final DistanceCache main;
	
	final MutableNumber total, maxInteger, maxScroll, scrollTotal;
	final MutableNumber scrollPosition; // for head and tail it stores min and max scroll position
	AxisItem start, end, endNoTrim, current, zeroItem;

	ArrayList<Runnable> callbacks;

	boolean isComputingRequired;

	public Direction forward, backward, forwardNavigator, backwardNavigator;
	final AxisModel model;


	
	public Layout(AxisModel model) {
		Preconditions.checkArgument(model.getSections().length > 0, "Layout must have at least one section");
		this.model = model;
		math = Math.getInstance(model.getNumberClass());
		Section[] sections2 = model.getSections();
		sections = new ArrayList<Section>(sections2.length);
		for (int i = 0; i < sections2.length; i++) {
			Section section = sections2[i];
			section.index = i;
			sections.add(section);
		}
//		if (sections.length == 0) {
//		sections.add(new Section(numberClass)); // header
//		sections.add(new Section(numberClass)); // body
		
		forward = new Direction.Forward(math, this.sections, false);
		backward = new Direction.Backward(math, this.sections, false);
		forwardNavigator = new Direction.Forward(math, this.sections, true);
		backwardNavigator = new Direction.Backward(math, this.sections, true);
		
		head = new CountCache(forward);
		main = new DistanceCache();
		tail = new CountCache(backward);
		
		Section section = sections2[0];
		start = new AxisItem(section, math.create(0));
		zeroItem = new AxisItem(section, math.create(0));
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
		return sections.get(i);
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
		total.set(0); 
		for (Section section: sections) {
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
		
		if (!head.isEmpty() && comparePosition(origin, forward.min) < 0) {
			origin = forward.getItem();
		} else if (!tail.isEmpty() && comparePosition(origin, backward.min) > 0) {
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
//		if (current == null) return;
//		for (int section = 0; section < model.getSectionCount(); section++) {
//			for (Extent extent: sections[section].hidden) {
//				if (extent.contains(current.index)) {
//					current.index = extent.getLimit();
//					break;
//				}
//			}
//			if math.compare((current.index, model.getItemCount(section)) < 0) return;
//		}
	}
	
	private Direction opposite(Direction direction) {
		return direction instanceof Forward ? backward : forward;
	}
	
	private int comparePosition(AxisItem item1, AxisItem item2) {
		int diff = item1.section.index - item2.section.index;
		if (diff != 0) return diff; 
		return item1.section.comparePosition(item1.index, item2.index);
	}
	
	int compare(AxisItem item1, AxisItem item2) {
		int diff = item1.section.index - item2.section.index;
		if (diff != 0) return diff;
		return math.compare(item1.index, item2.index);
	}
	
	/*------------------------------------------------------------------------
	 * Scroll 
	 */

	public void show(AxisItem item) {
		if (item == null) return;
		if (isComputingRequired) compute();
		
		if (comparePosition(item, endNoTrim) > 0) {
			compute(item, backward);
		} 
		else if (comparePosition(item, start) < 0) {
			compute(item, forward);
		} 
		// else it is visible already
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

	public int getAutoScrollOffset(int lastDistance, int distance, int margin) {
		if (lastDistance < main.distance || lastDistance > main.distance + main.outerWidth) return 0;
		
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
	 * Override this method to throw IndexOutOfBoundsException 
	 * in case index is not lower then the section count.  
	 */
	protected boolean isOutOfBounds(AxisItem item) {
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
		ArrayList<Section> sections;
		int distance, innerWidth, outerWidth, freezeLineWidth, lastLineWidth;
		Direction direction;

		AxisItem item;


		public Cache() {
			sections = new ArrayList<Section>();
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
				lastLine(dir.section, dir.seq.index());
			}
			for (int i = 0; condition() && item != null; i++) {
				bound1 = new Bound(0, dir.section.getLineWidth(dir.seq.index()));
				bound2 = new Bound(0, dir.section.getCellWidth(dir.seq.index()));
				int width = bound1.width + bound2.width;

				if (!canTrim && innerWidth + width > maxWidth) break;

				innerWidth += bound1.width + bound2.width;
				items.add(item); // = dir.getItem());
				lines.add(bound1);
				cells.add(bound2);
				if (!sections.contains(dir.section)) sections.add(dir.section);

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

		Bound lastLine(Section section, MutableNumber index) {
			MutableNumber index2 = section.math.increment(index);
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
	
	MutableNumber getItemPosition(AxisItem item) {
		MutableNumber position = math.create(0);
		for (int i = 0, size = sections.size(); i < size; i++) {
			Section section = sections.get(i);
			if (item.section.equals(section)) {
				return position.add(section.getPosition(item.index));
			}
			position.add(section.getVisibleCount());
		}
		return null;
	}
	
	AxisItem getItemByPosition(MutableNumber position) {
		MutableNumber pos1 = math.create(0);
		MutableNumber pos2 = math.create(0);
		
		for (int i = 0, size = sections.size(); i < size; i++) {
			Section section = sections.get(i);
			pos1.set(pos2);
			pos2.add(section.getVisibleCount());
			if (math.compare(pos2, position) > 0) {
				return new AxisItem(section, section.getByPosition(pos1.subtract(position).negate()));
			}
		}
		return null;
	}

	
	
	public LayoutSequence cellSequence(Dock dock, Section section) {
		if (isComputingRequired) compute();
		Cache cache = getCache(dock);
		return new LayoutSequence(cache.items, cache.cells, section);
	}
	
	public LayoutSequence lineSequence(Dock dock, Section section) {
		if (isComputingRequired) compute();
		Cache cache = getCache(dock);
		return new LayoutSequence(cache.items, cache.lines, section);
	}
	
	
	public class LayoutSequence {

		private final List<AxisItem> items;
		private final List<Bound> bounds;
		private final Section section;
		private int i;
		Bound bound;
		AxisItem item;

		public LayoutSequence(List<AxisItem> items, List<Bound> bounds, Section section) { 
			this.items = items;
			this.bounds = bounds;
			this.section = section;
		}

		public void init() {
			for (i = 0; i < items.size(); i++) {
				if (items.get(i).section.equals(section)) break;
			}
			i--;
		}
		
		public boolean next() {
			if (++i == bounds.size() || !items.get(i).section.equals(section)) return false;
			bound = bounds.get(i);
			item = items.get(i);
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
	}

	public List<Section> getSections(Dock dock) {
		ArrayList a = new ArrayList();
		List<Section> sections = getCache(dock).sections;
		Section[] order = model.getSectionLayerOrder();
		for (int i = 0; i < order.length; i++) {
			if (sections.contains(order[i])) {
				a.add(order[i]);
			}
		}
		return a;
	}

	// TODO cache the section bonds in a dock 
	public Bound getBound(Dock dock, Section section) {
		Cache cache = getCache(dock);
		int first = -1, last = -1;
		for (int i = 0, size = cache.items.size(); i < size; i++) {
			if (first == -1 && cache.items.get(i).section.equals(section)) {
				first = i;
			} else if (first != -1) {
				last = i;
			}
		}
		Bound b = cache.lines.get(last == -1 ? cache.items.size() - 1 : last);
		return first == -1 ? new Bound(0, 0) :
			new Bound(cache.lines.get(first).distance, b.distance + b.width);
	}

	
	
//	class ItemSequence {
//		Section section;
//		MutableNumber start, end;
//		private int i, istart, iend;
//		private ArrayList<Extent> items;
//		private AxisItem startItem, endItem;
//		
//		void init(AxisItem startItem, AxisItem endItem) {
//			this.startItem = startItem;
//			this.endItem = endItem;
//			istart = startItem.section.order.items.isEmpty() ? 0 : sl.order.getExtentIndex(startItem.index);
//			iend = endItem.section.order.items.isEmpty() ? 0 : sl.order.getExtentIndex(endItem.index);
//			
//			section = startItem.section;
//			items = sections[section].order.items;
//			i = istart;
//		}
//		
//		boolean next() {
//			if (i >= items.size()) {
//				section++;
//				if (section > endItem.section) return false;
//				items = sections[section].order.items;
//				i = 0;
//			}
//			Extent e = items.get(i);	
//			start = section == startItem.section && i == istart ? 
//					startItem.index : e.getStart();
//			end = section == endItem.section && i == iend ? 
//					endItem.index : e.getEnd();
//			if (i >= iend && end.compareTo(endItem.index) == 0) {
//				i = items.size();
//			}
//			i++;
//			return true;
//		}
//	}

}
