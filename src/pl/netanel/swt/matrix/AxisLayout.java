/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.netanel.swt.matrix.AxisSequence.Backward;
import pl.netanel.swt.matrix.AxisSequence.Forward;
import pl.netanel.util.Util;


class AxisLayout<N extends Number> {

	Math<N> math;
	ArrayList<SectionCore<N>> sections;
	SectionCore<N> body, header;

	private int viewportSize;
	CountCache head, tail;
	DistanceCache main;
	ArrayList<Cache> caches;

	MutableNumber<N> total, maxInteger, maxScroll, scrollTotal;
	MutableNumber<N> scrollPosition; // for head and tail it stores min and max scroll position
	AxisItem<N> start, end, endNoTrim, current, zeroItem;
	boolean isTrimmed;
	int trim;
	int autoScrollOffset, resizeOffset, minimalCellWidth = 5;

	ArrayList<Runnable> callbacks;

	boolean isComputingRequired;
	boolean isFocusItemEnabled;

	public AxisSequence<N> forward, backward, forwardNavigator, backwardNavigator;

  private MutableNumber<N> maxIntegerLessOne;
  private SpanSequence<N> spanSeq;

  @SuppressWarnings("unchecked")
  public AxisLayout() {
    createSections((Class<N>) Integer.class, 2, 0, 1);
  }


  public AxisLayout(Class<N> numberClass, int sectionCount, int headerIndex, int bodyIndex) {
    createSections(numberClass, sectionCount, headerIndex, bodyIndex);
  }

  public AxisLayout(int headerIndex, int bodyIndex, List<SectionCore<N>> sections) {
    this.sections = new ArrayList<SectionCore<N>>(sections.size());
    math = sections.get(0).math;
    for (int i = 0; i < sections.size(); i++) {
      SectionCore<N> section = sections.get(i);
      section.index = i;
      this.sections.add(section);
    }
    init(headerIndex, bodyIndex);
  }

  private void createSections(Class<N> numberClass, int sectionCount, int headerIndex, int bodyIndex) {
    math = Math.getInstance(numberClass);
    sections = new ArrayList<SectionCore<N>>(sectionCount);
    for (int i = 0; i < sectionCount; i++) {
      SectionCore<N> section = new SectionCore<N>(numberClass);
      section.index = i;
      sections.add(section);
    }

    init(headerIndex, bodyIndex);
	}

  private void init(int headerIndex, int bodyIndex) {

    if (headerIndex < sections.size()) {
      header = sections.get(headerIndex);
      header.setCount(math.ONE_VALUE());
      header.setFocusItemEnabled(false);
      header.setVisible(false);
    }

    if (bodyIndex < sections.size()) {
      body = sections.get(bodyIndex);
    }

//		if (sections.length == 0) {
//		sections.add(new Section(numberClass)); // header
//		sections.add(new Section(numberClass)); // body

		forward = new AxisSequence.Forward<N>(sections);
		backward = new AxisSequence.Backward<N>(sections);
		forwardNavigator = new AxisNavigationSequence.Forward<N>(sections);
		backwardNavigator = new AxisNavigationSequence.Backward<N>(sections);

		spanSeq = new SpanSequence<N>(sections);

		head = new CountCache(forward);
		main = new DistanceCache();
		tail = new CountCache(backward);
		caches = new ArrayList<Cache>();
		caches.add(head); caches.add(main); caches.add(tail);

		SectionCore<N> section = this.sections.get(0);
		start = AxisItem.createInternal(section, math.ZERO_VALUE());
		zeroItem = AxisItem.createInternal(section, math.ZERO_VALUE());
		current = forwardNavigator.first();
		total = math.create(0);
		maxInteger = math.create(Integer.MAX_VALUE);
		maxIntegerLessOne = math.create(maxInteger).decrement();
		maxScroll = math.create(0);
		scrollTotal = math.create(0);
		scrollPosition = math.create(0);

		callbacks = new ArrayList<Runnable>();
		isComputingRequired = true;
		isFocusItemEnabled = true;

    autoScrollOffset = Matrix.AUTOSCROLL_OFFSET_Y;
    resizeOffset = Matrix.RESIZE_OFFSET_Y;
  }




  public int getViewportSize() {
		return viewportSize;
	}

	public void setViewportSize(int viewportSize) {
		this.viewportSize = viewportSize;
		isComputingRequired = true;
	}

	public SectionCore<N> getSection(int i) {
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
	protected void compute(AxisItem<N> start, AxisSequence<N> AxisSequence) {
//		Preconditions.checkState(viewportSize > 0, "Cannot compute for viewport size 0");
		this.start = start;

		// Compute total and check if body exists
		total.set(math.ZERO_VALUE());
		for (SectionCore<N> section: sections) {
			if (section.isVisible()) {
				total.add(section.getVisibleCount());
			}
		}

		computeCache(start, AxisSequence);

		if (current == null) {
			current = forwardNavigator.first();
		}
		ensureCurrentIsValid();

		isComputingRequired = false;
		for (SectionCore<N> section: sections) {
		  section.isDirty = false;
		}

		for (Runnable r: callbacks) {
			r.run();
		}

	}

	public void computeCache(AxisItem<N> origin, AxisSequence<N> dir) {
		// Frozen
	  forward.rewind(); forward.init(); forward.next();
	  backward.rewind(); backward.init(); backward.next();
		head.compute(viewportSize);
		tail.compute(viewportSize - head.innerWidth);

		if (origin != null) {
			if (!head.isEmpty() && comparePosition(origin, forward.min) < 0) {
				origin = forward.getItem();
			} else if (!tail.isEmpty() && comparePosition(origin, backward.min) > 0) {
				origin = backward.getItem();
			}
			dir.init(origin);
			dir.next();
		}

		// Main
		int mainMaxWidth = viewportSize - head.innerWidth - tail.innerWidth;
		main.compute(dir, mainMaxWidth);
		AxisSequence<N> opposite = opposite(dir);
		if (main.outerWidth < mainMaxWidth && (dir.min == null || !dir.start.equals(dir.min)))
		{
			AxisItem<N> origin2 = opposite.start;
			dir = opposite(dir);
			dir.init(origin2);
			dir.next();
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
		if (isComputingRequired()) {
			compute();
		}
	}

	private boolean isComputingRequired() {
	  for(SectionCore<N> section: sections) {
	    if (section.isDirty) {
	      isComputingRequired = true;
	      break;
	    }
	  }
    return isComputingRequired;
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

	/**
	 * Avoid current to be hidden or out of scope.
	 */
	public void ensureCurrentIsValid() {
		if (current == null) return;
		SectionCore<N> currentSection = SectionCore.from(current);

		// Out of scope
		N count = currentSection.getCount();
		if (math.compare(current.getIndex(), count) >= 0) {
			current = math.compare(count, math.ZERO_VALUE()) == 0 ? null :
				AxisItem.createInternal(currentSection, math.decrement(count));
		}

		// Hidden
		if (current != null && currentSection.hidden.contains(current.getIndex())) {
			AxisItem<N> item2 = forwardNavigator.nextItem(current);
			if (item2 == null) {
				item2 = backwardNavigator.nextItem(current);
			}
			current = item2;
		}
	}

	private AxisSequence<N> opposite(AxisSequence<N> AxisSequence) {
		return AxisSequence instanceof Forward ? backward : forward;
	}

	int compare(AxisItem<N> item1, AxisItem<N> item2) {
	  SectionCore<N> section1 = SectionCore.from(item1);
	  SectionCore<N> section2 = SectionCore.from(item2);
		int diff = section1.index - section2.index;
		if (diff != 0) return diff;
		return math.compare(
				section1.getOrder(item1.getIndex()),
				section2.getOrder(item2.getIndex()));
	}

  int comparePosition(AxisItem<N> item1, AxisItem<N> item2) {
    return comparePosition(item1.section, item1.index, item2.section, item2.index);
  }

  int comparePosition(SectionCore<N> section1, N index1, SectionCore<N> section2, N index2) {
    int compareSections = comparePosition(section1, section2);
    if (compareSections != 0) return compareSections;
    N position1 = section1.getOrder(index1);
    N position2 = section2.getOrder(index2);
    if (position1 == null || position2 == null) {
      return 0;
    } else {
      return math.compare( position1, position2);
    }
  }

  int comparePosition(SectionCore<N> section1, SectionCore<N> section2) {
    return section1.index - section2.index;
  }


	/*------------------------------------------------------------------------
	 * Navigation
	 */

	public boolean setFocusItem(AxisItem<N> item) {
	  if (!isFocusItemEnabled || item == null || !item.section.isFocusItemEnabled()) return false;

	  computeIfRequired();

	  AxisItem<N> current2 = null;
	  forwardNavigator.init(item);
	  if (forwardNavigator.next()) {
	    current2 = forwardNavigator.getItem();
	  }
	  else {
	    backwardNavigator.init(item);
	    if (backwardNavigator.next()) {
	      current2 = backwardNavigator.getItem();
	    }
	  }

    if (current2 == null) return false;
    boolean result = compare(current, current2) != 0;
    current = current2;
    return result;
	}

	/**
	 * Return true if the current item has changed.
	 * @param move
	 * @return
	 */
	// TODO Performance: prevent computation if current does not change
	public boolean moveFocusItem(Move move) {
	  if (!isFocusItemEnabled) return false;

  	AxisItem<N> current2 = null;
		switch (move) {
		case HOME: 				current2 = forwardNavigator.first(); break;
		case END: 				current2 = backwardNavigator.first(); break;
		case NEXT: 				current2 = nextItem(current, forwardNavigator); break;
		case PREVIOUS: 			current2 = nextItem(current, backwardNavigator); break;
		case NEXT_PAGE:			show(current); current2 = nextPage(current, forwardNavigator); break;
		case PREVIOUS_PAGE:		show(current); current2 = nextPage(current, backwardNavigator); break;
		case NULL: 				break;
		}
		boolean result = current2 != null && compare(current, current2) != 0;
		if (current2 != null) {
			show(current = current2);
		}
		return result;
	}

	/*------------------------------------------------------------------------
	 * Scroll
	 */

	public boolean show(AxisItem<N> item) {
		if (item == null) return false;
		computeIfRequired();

		if (comparePosition(item, endNoTrim) > 0) {
			compute(item, backward);
			return true;
		}
		else if (comparePosition(item, start) < 0) {
			compute(item, forward);
			return true;
		}
		// else it is visible already
		return false;
	}

	AxisItem<N> scroll(MutableNumber<N> itemCount, AxisSequence<N> AxisSequence) {
		computeIfRequired();
		AxisItem<N> start2 = nextItem(start, itemCount, AxisSequence);
		if (start2 == null || compare(start2, start) == 0) return null;
		start = start2;
		compute();
		return AxisSequence instanceof Forward ? end : start;
	}

	public void scrollTo(AxisItem<N> item) {
		if (item.equals(start) || isOutOfBounds(item)) return;
		if (forward.setHasMore(start = item)) {
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
	  int thumb = main.cells.size() - trim;
    if (isIntegerTooSmall()) {
      MutableNumber<N> index = math.create(thumb);
      index.multiply(maxInteger).divide(scrollTotal);
      thumb = index.compareTo(math.ZERO_VALUE()) == 0 ? 1 : index.intValue();
	  }
    return thumb;
	}

	public int getScrollPosition() {
		MutableNumber<N> index = math.create(scrollPosition);
		if (isIntegerTooSmall()) {
			index.set(scrollPosition).multiply(maxInteger).divide(scrollTotal);
			if (math.compare(index, maxIntegerLessOne) == 0) {
			  index.add(-2);
			}
//			if (index.intValue() < head.count) {
//			  return head.count + main.cells.size() + 1;
//			}
		}
		return index.intValue();
	}


	/**
	 * Moves the scroll position to given position or by the given move amount.
	 * When the move value is given then position is ignored.
	 * @param position
	 * @param move
	 */
	public boolean setScrollPosition(int position, Move move) {
    //		if (main.scroll == position) return;
		switch (move) {
		case NEXT:
		  if (compare(endNoTrim, backward.min) == 0) return false;
		  compute(nextItem(start, forward), forward);
		  return true;

		case PREVIOUS:
		  if (compare(start, forward.min) == 0) return false;
		  compute(nextItem(start, backward), forward);
		  return true;

		case NEXT_PAGE:
		  if (compare(endNoTrim, backward.min) == 0) return false;
		  compute(nextItem(backward.start, forward), forward);
		  return true;

		case PREVIOUS_PAGE:
		  if (compare(start, forward.min) == 0) return false;
		  compute(nextItem(forward.start, backward), backward);
		  return true;

		case HOME:
		  if (compare(start, forward.min) == 0) return false;
		  compute(forward.min, forward);
		  return true;

		case END:
		  if (compare(endNoTrim, backward.min) == 0) return false;
		  compute(backward.min, backward);
		  return true;

		default:
			MutableNumber<N> index = math.create(position);
			AxisItem<N> item;
			if (isIntegerTooSmall()) {
				if (position >= getScrollMax() - getScrollMin() - getScrollThumb()) {
					/* Enforce going to the end by dragging,
						since the thumb number of items will not fit in the viewport */
					item = backward.min;
				}
				else if (position <= head.count) {
				  // Enforce going to the start
				  item = forward.min;
				}
				else {
					index.multiply(total);
					index.divide(maxInteger);
					item = getItemByPosition(index);
				}
			} else {
				item = getItemByPosition(index);
			}
			if (item != null) {
			  compute(item, forward);
			  return true;
			}
		}
		return false;
	}

	private boolean isIntegerTooSmall() {
		return math.compare(total, maxInteger) >= 0;
	}

	public boolean isScrollRequired() {
		if (main.isEmpty()) return false;
//		if (axis.symbol == 'X') {
//		  System.out.println("is required " + (compare(start, forward.min) != 0 || compare(endNoTrim, backward.min) != 0));
//		}
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

		int offset = distance - (tail.distance - autoScrollOffset);
		if (offset > 0 && lastDistance < distance && compare(endNoTrim, backward.min) < 0) {
			return offset;
		}
		offset = distance - (head.outerWidth + autoScrollOffset);
		if (offset < 0 && lastDistance > distance && compare(start, forward.min) > 0 ) {
			return offset;
		}
		return 0;
	}

	AxisItem<N> nextItem(AxisItem<N> item, AxisSequence<N> AxisSequence) {
		if (item == null) item = AxisSequence.first();
		if (item == null) return null;
		AxisSequence.setHasMore(item);
		return AxisSequence.nextItem();
	}

	AxisItem<N> nextItem(AxisItem<N> item, MutableNumber<N> itemCount, AxisSequence<N> seq) {
	  MutableNumber<N> counter = math.create(0);
	  seq.init(item);
	  for (seq.init(); seq.next();) {
	    itemCount.increment();
	    if (math.compare(counter, itemCount) >= 0) break;
	  }
	  return seq.getItem();
	}

	private AxisItem<N> nextPage(AxisItem<N> item, AxisSequence<N> dir) {
		if (item == null) item = dir.first();
		if (item == null) return null;
		AxisItem<N> item2;
		if (dir instanceof Forward) {
			dir.init(endNoTrim);
			dir.next();
			if (compare(item, endNoTrim) < 0) {
				item2 = dir.getItem();
			} else {
				item2 = dir.nextItem();
				if (item2 != null) {
					compute(item2, dir);
					item2 = endNoTrim;
				}
			}
		} else {
			if (dir.init(start)) {
				if (compare(item, start) > 0) {
					item2 = dir.getItem();
				} else {
					item2 = dir.nextItem();
					if (item2 != null) {
						compute(item2, dir);
						item2 = start;
					}
				}
			} else {
				item2 = forwardNavigator.first();
			}
		}
		isComputingRequired = true;
		return item2;
	}

	/**
	 * Returns item that can be resized due to the distance in the viewport
	 * or null if distance does not imply resizing or the item is not resizable.
	 *
	 * @param distance
	 * @return
	 */
	public AxisItem<N> getResizeItem(int distance) {
		Cache cache = getCache(distance - resizeOffset);

		for (int i = 1; i < cache.lines.size(); i++) {
			Bound bound = cache.lines.get(i);
			int left = bound.distance - resizeOffset;
			int right = bound.distance + bound.width + resizeOffset;
			if (left <= distance && distance <= right) {
				AxisItem<N> item = cache.items.get(i - 1);
				return item.section.isResizable(item.getIndex()) ? item : null;
			}
		}
		return null;
	}
	/**
	 * Override this method to throw IndexOutOfBoundsException
	 * in case index is not lower then the section count.
	 */
	protected boolean isOutOfBounds(AxisItem<N> item) {
		return math.compare(item.getIndex(), item.section.getCount()) >= 0;
	}



	/*------------------------------------------------------------------------
	 * Cache
	 */

	/**
	 * Stores index data for a frozen area of an <code>AxisLayout</code>.
	 *
	 * @author Jacek created 08-12-2010
	 */
	abstract class Cache {
		ArrayList<AxisItem<N>> items;
		ArrayList<Bound> cells, lines;
		ArrayList<SectionCore<N>> sections;
		int distance, innerWidth, outerWidth, freezeLineWidth, lastLineWidth;
		AxisSequence<N> seq;

		AxisItem<N> item;


		public Cache() {
			sections = new ArrayList<SectionCore<N>>();
			items = new ArrayList<AxisItem<N>>();
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
		void compute(AxisSequence<N> dir, int maxWidth, int count, boolean canTrim) {
			item = dir.getItem();
			if (item == null || maxWidth <= 0) return;

			this.seq = dir;
			innerWidth = 0;
			Bound bound1, bound2;

			if (dir instanceof Backward) {
				lastLine(dir.section, dir.getIndex());
			}
			while (condition() && item != null) {
				bound1 = new Bound(0, dir.section.getLineWidth(dir.getIndex()));
				bound2 = new Bound(0, dir.section.getCellWidth(dir.getIndex()));
				int width = bound1.width + bound2.width;

				if (!canTrim && innerWidth + width > maxWidth || innerWidth == maxWidth) break;

				innerWidth += bound1.width + bound2.width;
				items.add(item); // = dir.getItem());
				lines.add(bound1);
				cells.add(bound2);
				if (!sections.contains(dir.section)) {
//					TODO Decide from which section line at the edge is to be drawn.
//					if (!sections.isEmpty()) {
//						Section lastSection = sections.get(sections.size() - 1);
//						if (model.getZIndex(lastSection) > model.getZIndex(item.section)) {
//							AxisItem<N> item2 = item.copy();
//							item.section = lastSection;
//							items.add(item2);
//						}
//					}
					sections.add(dir.section);
				}

				if (canTrim && innerWidth > maxWidth) break;
				item = dir.nextItem();
			}

			if (count >= 0) {
				// It is needed for distance cache limits
				seq.freeze = isEmpty() ? null : items.get(items.size() - 1);
				//seq.min = (seq == forward ? forwardNavigator : backwardNavigator).first();
				seq.min = item == null ? items.get(items.size()-1) : item;
			}
			if (dir instanceof Backward) reverse();

			outerWidth = innerWidth;
			Bound lastLine = null;
			if (!isEmpty()) {
				if (dir instanceof Forward) {
					AxisItem<N> item2 = items.get(items.size() - 1);
					lastLine = lastLine(SectionCore.from(item2), item2.getIndex());
				} else {
					lastLine = lines.get(0);
				}
				outerWidth += lastLineWidth = lastLine.width;

				if (freezeLineWidth >= 0) {
					//				int sign = seq instanceof Forward ? 1 : -1;
					int diff = freezeLineWidth - lastLine.width;// * sign;
					innerWidth += diff;
					outerWidth += diff;
					lastLine.width = freezeLineWidth;
				}
			}
		}

		Bound lastLine(SectionCore<N> section, N index) {
			N index2 = section.math.increment(index);
			Bound bound = new Bound(0, section.getLineWidth(index2));
			lines.add(bound);
			items.add(AxisItem.createInternal(section, index2));
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
	 */
	class CountCache extends Cache {
		int count;

		public CountCache(AxisSequence<N> AxisSequence) {
			this.seq = AxisSequence;
		}

		@Override
		protected boolean condition() {
			return cells.size() < count;
		}

		public void compute(int maxWidth) {
			clear();
//			if (!AxisSequence.initNotEmpty()) return;
			super.compute(seq, maxWidth, count, false);
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

    public void compute(AxisSequence<N> direction, int maxWidth) {
			clear();

			super.compute(direction, maxWidth, -1, direction == forward);

			start = Util.notNull(super.isEmpty() ? forward.min : items.get(0), zeroItem);
			end = Util.notNull(super.isEmpty() ? forward.min: items.get(items.size() - 2), zeroItem);

			// Compute last index that is fully visible (not trimmed)
			endNoTrim = end;
			isTrimmed = false;
			trim = 0;
			if (cells.size() > 1) {
				if (outerWidth > maxWidth /*&& */) {
					endNoTrim = items.get(items.size() - 3);
					if (outerWidth != maxWidth +
            cells.get(cells.size() - 1).width +
            lines.get(lines.size() - 2).width)
					{
					  isTrimmed = true;
					}
					trim = 1;
				}
			}
//			if (start.section.index == 1) {
//			  TestUtil.log(axis.matrix.getDisplay().getCursorLocation().x, endNoTrim, end, isTrimmed);
//			}

			forward.start = start;
			backward.start = endNoTrim;

			maxScroll.set(total).add(-tail.count);
			scrollTotal.set(maxScroll).add(-head.count);
			if (!super.isEmpty()) {
				MutableNumber<N> itemPosition = getItemPosition(start);
				if (itemPosition != null) {
					scrollPosition.set(itemPosition);
				}
			}
		}
	}

	Cache getCache(Frozen frozen) {
		return 	frozen == Frozen.NONE ? main :
				frozen == Frozen.HEAD ? head : tail;
	}

	private Cache getCache(int distance) {
		return
		    main.distance <= distance ?
		        (distance <= tail.distance ?
		            main :
		            tail) :
		        head;
//		    distance < main.distance && !head.isEmpty() ? head :
//			   distance > tail.distance && !tail.isEmpty() ? tail : main;
	}

	private Cache getCache(Section<N> section, N index) {
		for (Cache cache: caches) {
			int len = cache.cells.size();
			for (int i = 0; i < len; i++) {
				AxisItem<N> item = cache.items.get(i);
				if (item.section.equals(section) && math.compare(item.getIndex(), index) == 0) {
					return cache;
				}
			}
		}
		return null;
	}

	MutableNumber<N> getItemPosition(AxisItem<N> item) {
		MutableNumber<N> position = math.create(0);
		for (int i = 0, size = sections.size(); i < size; i++) {
			SectionCore<N> section = sections.get(i);
			if (item.section.equals(section)) {
				N index = section.getPosition(item.getIndex());
				return index == null ? null : position.add(index);
			}
			if (section.isVisible()) {
				position.add(section.getVisibleCount());
			}
		}
		return null;
	}

	AxisItem<N> getItemByPosition(MutableNumber<N> position) {
		MutableNumber<N> pos1 = math.create(0);
		MutableNumber<N> pos2 = math.create(0);

		for (int i = 0, size = sections.size(); i < size; i++) {
			SectionCore<N> section = sections.get(i);
			if (!section.isVisible()) continue;
			pos1.set(pos2);
			pos2.add(section.getVisibleCount());
			if (math.compare(pos2, position) > 0) {
				return AxisItem.createInternal(section, section.getIndex(pos1.subtract(position).negate().getValue()));
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
	AxisItem<N> getItemByDistance(int distance) {
		Cache cache = getCache(distance);
		if (cache.cells.isEmpty()) return null;
//		if (distance < cache.cells.get(0).distance) return null; //cache.items.get(0);

		AxisItem<N> item = null;
		for (int i = 0; i < cache.cells.size(); i++) {
			Bound bounds = cache.cells.get(i);
			item = cache.items.get(i);
			if (distance <= bounds.distance + bounds.width) {
//			  if (bounds.distance <= distance) {
			    return item;
//			  }
			}
		}
		return null; //item;
//		return getBeyond && !cache.isEmpty() ? cache.items.get(cache.items.size() - 2) : null;
	}


	public AxisLayoutSequence<N> sequence(Frozen frozen, SectionCore<N> section, int type) {
	  computeIfRequired();
	  Cache cache = getCache(frozen);
	  ArrayList<Bound> space = type == Matrix.TYPE_CELLS ? cache.cells : cache.lines;
    return new AxisLayoutSequence<N>(cache.items, space, section);
	}

	public AxisLayoutSequence<N> cellSequence(Frozen frozen, SectionCore<N> section) {
		computeIfRequired();
		Cache cache = getCache(frozen);
		return new AxisLayoutSequence<N>(cache.items, cache.cells, section);
	}

	public AxisLayoutSequence<N> lineSequence(Frozen frozen, SectionCore<N> section) {
		computeIfRequired();
		Cache cache = getCache(frozen);
		return new AxisLayoutSequence<N>(cache.items, cache.lines, section);
	}

//	public LayoutSequence<N> singleSequence(int distance, int width) {
//		LayoutSequence<N> seq = new LayoutSequence<N>(null, null, null) {
//			private boolean started;
//
//			@Override public void init() {
//				started = false;
//			}
//			@Override
//			public boolean next() {
//				if (started) return false;
//				return started = true;
//			}
//		};
//		seq.bound = new Bound(distance, width);
//		return seq;
//	}



	public boolean contains(Frozen frozen, SectionCore<N> section) {
		List<SectionCore<N>> sections = getCache(frozen).sections;
		if (sections.contains(section)) {
			return true;
		}
		return false;
	}

	public boolean contains(AxisItem<N> item) {
	  return getCache(item.section, item.index) != null;
	}

	public Bound getBound(Frozen frozen) {
		Cache cache = getCache(frozen);
		return new Bound(cache.distance, cache == main
				? viewportSize - head.innerWidth - tail.innerWidth
				: cache.outerWidth);
	}

	// TODO cache the section bonds in a frozen
	public Bound getBound(Frozen frozen, SectionCore<N> section) {
		Cache cache = getCache(frozen);
		int first = -1, last = -1;
		if (cache.cells.isEmpty()) {
		  return new Bound(cache.distance, 0);
		}
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
      if (section.index < cache.items.get(0).section.index) {
        return new Bound(cache.cells.get(0).distance - 1, 0);
		  } else {
		    Bound bound = cache.cells.get(cache.cells.size() - 1);
        return new Bound(bound.distance + bound.width + 1, 0);
		  }
		} else {
			Bound b = cache.lines.get(last == -1 ? cache.items.size() - 1 : last);
			int distance = cache.lines.get(first).distance;
			return new Bound(distance, b.distance + b.width - distance);

		}
	}

	public Bound getCellBound(AxisItem<N> item) {
		Cache cache = getCache(item.section, item.getIndex());
		if (cache == null) return null;
		for (int i = 0, size = cache.cells.size(); i < size; i++) {
			if (cache.items.get(i).equals(item)) {
				return cache.cells.get(i);
			}
		}
		return null;
	}

	public Bound getLineBound(AxisItem<N> item) {
		N index = item.getIndex();
		if (math.compare(index, item.section.getCount()) == 0) {
		  index = math.decrement(index);
		}
    Cache cache = getCache(item.section, index);
		if (cache == null) return null;
		for (int i = 0, size = cache.lines.size(); i < size; i++) {
			if (cache.items.get(i).equals(item)) {
				return cache.lines.get(i);
			}
		}
		return null;
	}

	public boolean isEmpty() {
		return head.isEmpty() && main.isEmpty() && tail.isEmpty();
	}


	public void freezeHead(int freezeItemCount) {
		isComputingRequired = head.count != freezeItemCount;
		head.count = freezeItemCount;
	}

	public void freezeTail(int freezeItemCount) {
		isComputingRequired = tail.count != freezeItemCount;
		tail.count = freezeItemCount;
	}

	public void freezeHead(AxisItem<N> item) {
		int count = 0;
		for (Cache cache: caches) {
			for (int i = 0, imax = cache.cells.size(); i < imax; i++, count++) {
				AxisItem<N> item2 = cache.items.get(i);
				if (item2.section.equals(item.section) &&
						math.compare(item2.getIndex(), item.getIndex()) == 0) {
					freezeHead(count);
					return;
				}
			}
		}
	}

	public void freezeTail(AxisItem<N> item) {
		int count = 0;
    @SuppressWarnings("unchecked")
    List<Cache> list = (List<Cache>) caches.clone();
		Collections.reverse(list);
		for (Cache cache: list) {
			for (int i = cache.cells.size(); i-- > 0; count++) {
				AxisItem<N> item2 = cache.items.get(i);
				if (item2.section.equals(item.section) &&
						math.compare(item2.getIndex(), item.getIndex()) == 0) {
					freezeTail(count);
					return;
				}
			}
		}
	}

	public int indexOf(AxisItem<N> item) {
		int count = 0;
		for (Cache cache: caches) {
			for (int i = 0, imax = cache.cells.size(); i < imax; i++, count++) {
				AxisItem<N> item2 = cache.items.get(i);
				if (item2.section.equals(item.section) &&
						math.compare(item2.getIndex(), item.getIndex()) == 0) {
					return count;
				}
			}
		}
		return -1;
	}

	public AxisItem<N> getIndexAt(int index) {
		if (index < head.count) return head.items.get(index);
		index -= head.count;
		if (index < main.cells.size()) return main.items.get(index);
		index -= main.cells.size();
		if (index < tail.count) return tail.items.get(index);
		return null;
	}

	public Bound getCellBound(int index) {
		if (index < head.count) return head.cells.get(index);
		index -= head.count;
		if (index < main.cells.size()) return main.cells.get(index);
		index -= main.cells.size();
		if (index < tail.count) return tail.cells.get(index);
		return null;
	}
	public Bound getLineBound(int index) {
		if (index < head.count) return head.lines.get(index);
		index -= head.count;
		if (index < main.lines.size()) return main.lines.get(index);
		index -= main.cells.size();
		if (index < tail.count) return tail.lines.get(index);
		return null;
	}

	public boolean reorder(AxisItem<N> source, AxisItem<N> target) {
		SectionCore<N> sourceSection = SectionCore.from(source);
		SectionCore<N> targetSection = SectionCore.from(target);
    if (!sourceSection.equals(targetSection)) return false;

		int position = compare(target, start);

		if (!sourceSection.moveSelected(source.getIndex(), target.getIndex())) return false;

		// Adjust the scroll position if moving before the start
		if (position <= 0) {
			NumberSequence2<N> selected = sourceSection.getSelectedSequence();
			selected.init(); selected.next();
			start = AxisItem.createInternal(sourceSection, selected.index());
		}
		// Adjust the scroll position if moving the start
		else if (sourceSection.isSelected(start.getIndex())) {
			start = AxisItem.createInternal(sourceSection, target.getIndex());
		}

		compute();
		return true;
	}

	public Bound getBound(SectionCore<N> section, MutableExtent<N>span, int distance) {
	  N start = span.start.getValue();
    spanSeq.set(AxisItem.createInternal(section, start), span.end.getValue());
    int w = section.isHidden(start) ? 0 : - section.getLineWidth(start);
	  for (spanSeq.init(); spanSeq.next();) {
	    N index = spanSeq.index.getValue();
	    int lineWidth = section.getLineWidth(index);
	    if (w == 0) w -= lineWidth;
      w += lineWidth + section.getCellWidth(index);
    }

    return new Bound(distance, java.lang.Math.max(w, 0));
  }

  public int computeSize(int viewportSize) {
    setViewportSize(viewportSize);
    compute();

    int w = 0;
    if (!tail.lines.isEmpty()) {
      Bound bound = tail.lines.get(tail.lines.size() - 1);
      w += bound.distance;
    }
    if (!main.lines.isEmpty()) {
      Bound bound = main.lines.get(main.lines.size() - 1);
      w += bound.distance + bound.width;
    }
    if (!head.lines.isEmpty()) {
      Bound bound = head.lines.get(head.lines.size() - 1);
      w += bound.distance;
    }
    return w;
  }
}
