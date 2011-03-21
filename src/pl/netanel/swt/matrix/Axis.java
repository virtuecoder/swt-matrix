package pl.netanel.swt.matrix;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;

import pl.netanel.swt.Listeners;
import pl.netanel.util.Arrays;
import pl.netanel.util.Preconditions;

public class Axis<N extends Number> implements Iterable<Section<N>> {
	private static final Section[] EMPTY = new Section[] {};
	
	final Math<N> math;
	final ArrayList<Section<N>> sections;
	private Section<N>[] zOrder;
	private Section<N> body, header;
	private int autoScrollOffset;
	Layout layout;
	final Listeners listeners;
	
//	Section<N> currentSection;
//	N currentIndex;

	private ScrollBar scrollBar;

//	int axisIndex;

	
	public Axis() {
		this(new Section<N>(), new Section<N>());
		sections.get(0).setCount(math.ONE_VALUE());
	}
	
	public Axis(Class<N> numberClass) {
		this(new Section(numberClass), new Section(numberClass));
	}

	public Axis(Section<N> ...sections) {
		Preconditions.checkArgument(sections.length > 0, "Model must have at least one section");
		math = sections[0].math;
		this.sections = new ArrayList(sections.length);
		for (int i = 0; i < sections.length; i++) {
			Section section = sections[i];
			section.index = i;
			this.sections.add(section);
		}
		if (sections.length == 0) {
			this.sections.add(body = new Section(math));
		} else {
			body = sections.length > 1 ? sections[1] : sections.length == 1 ? sections[0] : null;
			header = sections.length > 1 ? sections[0] : null;
			if (header != null) {
				header.setNavigationEnabled(false);
			}
		}
		
		// Calculate z-order
		zOrder = new Section[this.sections.size()];
		int j = 0;
		int bodyIndex = this.sections.indexOf(body);
		for (int i = bodyIndex, imax = this.sections.size(); i < imax; i++) {
			zOrder[j++] = this.sections.get(i);
		}
		for (int i = bodyIndex; i-- > 0;) {
			zOrder[j++] = this.sections.get(i);
		}
		
		autoScrollOffset = M.AUTOSCROLL_OFFSET_Y;
		layout = new Layout(this);
		listeners = new Listeners();
	}

	
	public Section<N> getBody() {
		return body;
	}
	public Section<N> getHeader() {
		return header;
	}

	public void setBody(int sectionIndex) {
		Preconditions.checkPositionIndex(sectionIndex, sections.size(), "sectionIndex");
		this.body = sections.get(sectionIndex);
	}
	
	public void setHeader(int sectionIndex) {
		Preconditions.checkPositionIndex(sectionIndex, sections.size(), "sectionIndex");
		this.header = sections.get(sectionIndex);;
	}
	
	
	public int getSectionCount() {
		return sections.size();
	}
	
	public Section<N> getSection(int i) {
		return sections.get(i);
	}
	
	public Section[] getSections() {
		return sections.toArray(EMPTY);
	}

	public Section[] getZOrder() {
		return zOrder;
	}
	
	public int getZIndex(Section section) {
		return Arrays.indexOf(zOrder, section);
	}

	@Override
	public Iterator<Section<N>> iterator() {
		return sections.iterator();
	}

	
	/*------------------------------------------------------------------------
	 * Navigation 
	 */
	
	public Section getNavigationSection() {
		layout.computeIfRequired();
		return layout.current == null ? null : layout.current.section;
	}
	
	public Number getNavigationIndex() {
		layout.computeIfRequired();
		return layout.current == null ? null : layout.current.index;
	}

	public void navigate(Section<N> section, N index) {
		layout.setCurrentItem(new AxisItem(section, index));
	}

	
	/*------------------------------------------------------------------------
	 * Freeze
	 */

	public void freezeHead(int freezeItemCount) {
		layout.freezeHead(freezeItemCount);
	}

	public void freezeTail(int freezeItemCount) {
		layout.freezeTail(freezeItemCount);
	}
	
	
	/*------------------------------------------------------------------------
	 *
	 */
	
	public int getAutoScrollOffset() {
		return autoScrollOffset;
	}

	public void setAutoScrollOffset(int autoScrollOffset) {
		this.autoScrollOffset = autoScrollOffset;
	}

	
	/*------------------------------------------------------------------------
	 * Non public 
	 */

	void setMatrix(final Matrix matrix, int axisIndex) {
//		this.axisIndex = axisIndex;
		
		scrollBar = axisIndex == 0 ? matrix.getVerticalBar() : matrix.getHorizontalBar();
		if (scrollBar != null) {
			scrollBar.addListener(SWT.Selection, new Listener() {
				private int selection = -1;

				public void handleEvent(Event e) {
					int newSelection = scrollBar.getSelection();
					if (newSelection == selection) return;
					selection = newSelection;
					//				debugSWT(e.detail);
					Move move = 
						e.detail == SWT.ARROW_DOWN 	? Move.NEXT :
							e.detail == SWT.ARROW_UP 	? Move.PREVIOUS : 
								e.detail == SWT.PAGE_DOWN 	? Move.NEXT_PAGE: 
									e.detail == SWT.PAGE_UP 	? Move.PREVIOUS_PAGE: 
										Move.NULL;

					layout.setScrollPosition(selection, move);
					scrollBar.setThumb(layout.getScrollThumb());
					matrix.redraw();
				}
			});
		}
	}
	
	/**
	 * Scrolls the bar according to the axis state.
	 */
	void scroll() {
		if (scrollBar == null) return;
		scrollBar.setSelection(layout.getScrollPosition());
		scrollBar.setThumb(layout.getScrollThumb());
	}

	/**
	 * Update the scroll bar visibility.
	 * 
	 * @param size
	 * @return true if the visibility has changed/
	 */
	protected boolean updateScrollBarVisibility(int size) {
		if (scrollBar == null) return false;
		boolean b = scrollBar.getVisible();
		scrollBar.setVisible(layout.isScrollRequired());
		return b != scrollBar.isVisible();
	}

	/**
	 * Calibrates the scroll bar after change of display area size or the number of items.
	 * 
	 * @param size
	 * @return true if the visibility of the scroll bar has change to allow
	 *         recalculation of the matrix visibility information.
	 */
	protected void updateScrollBarValues(int size) {
		// Quit if there is no scroll bar or the visible area is not initialized
		if (scrollBar == null || size == 0) return;
	
		layout.setViewportSize(size);
		int min = layout.getScrollMin();
		int max = layout.getScrollMax();
		int thumb = layout.getScrollThumb();
		if (thumb == max) thumb = max-1;
		if (thumb == 0) thumb = 1;
		// Extend the maximum to show the last trimmed element
		if (thumb <= 1) { 
			thumb = 1;
			max++;
		}
		scrollBar.setValues(layout.getScrollPosition(), min, max, thumb, 1, thumb);
	}

	
	void setSelected(AxisItem start, AxisItem end, boolean select) {
		// Make sure start < end 
		if (comparePosition(start, end) > 0) {
			AxisItem tmp = start; start = end; end = tmp;
		}
		
		for (int i = start.section.index; i <= end.section.index; i++) {
			Section section = sections.get(i);
			Number startIndex = i == start.section.index ? start.index : math.ZERO_VALUE();
			Number endIndex = i == end.section.index ? end.index : 
				math.increment(section.getCount());
			section.select(startIndex, endIndex, select);
		}
	}

	void setSelected(boolean selected) {
		for (int i = 0, imax = sections.size(); i < imax; i++) {
			Section section = sections.get(i);
			section.selectAll(selected);
		}
	}

	
	AxisItem getLastItem() {
		for (int i = sections.size(); i-- > 0;) {
			Section section = sections.get(i);
			if (section.isEmpty()) continue;
			return new AxisItem(section, math.decrement(section.getCount()));
		}
		return getFirstItem();
	}

	AxisItem getFirstItem() {
		return new AxisItem(sections.get(0), math.ZERO_VALUE());
	}

	int comparePosition(AxisItem<N> item1, AxisItem<N> item2) {
		int diff = item1.section.index - item2.section.index;
		if (diff != 0) return diff; 
		return math.compare(
				item1.section.indexOf(item1.index), 
				item2.section.indexOf(item2.index));
	}
	
	/**
	 * Sets the hidden state of selected indexes in each section.
	 * @param hidden new hiding state
	 */
	void setHidden(boolean hidden) {
		for (int i = 0, imax = sections.size(); i < imax; i++) {
			Section section = sections.get(i);
			section.hideSelected(hidden);
		}
	}
	

	
	/**
	 * Iterates over extents between the start and end items. 
	 * 
	 * @author Jacek Kolodziejczyk created 11-03-2011
	 */
	class ExtentSequence {
		Section<N> section;
		MutableNumber<N> start, end, startItemIndex, endItemIndex;
		private int i, istart, iend, sectionIndex, lastSectionIndex;
		private ArrayList<Extent<N>> items;
		private AxisItem startItem, endItem;
	
		void init(AxisItem<N> startItem, AxisItem<N> endItem) {
			this.startItem = startItem;
			this.endItem = endItem;
			istart = startItem.section.order.items.isEmpty() ? 0 : 
				startItem.section.order.getExtentIndex(startItem.index);
			iend = endItem.section.order.items.isEmpty() ? 0 : 
				endItem.section.order.getExtentIndex(endItem.index);
			startItemIndex = math.create(startItem.index);
			endItemIndex = math.create(endItem.index);
			
			section = startItem.section;
			sectionIndex = sections.indexOf(section); 
			lastSectionIndex = sections.indexOf(endItem.section); 
			items = sections.get(sectionIndex).order.items;
			i = istart;
		}
		
		boolean next() {
			if (i >= items.size()) {
				sectionIndex++;
				if (sectionIndex > lastSectionIndex) return false;
				section = sections.get(sectionIndex);
				items = section.order.items;
				i = 0;
			}
			Extent e = items.get(i);	
			start = section == startItem.section && i == istart ? 
					startItemIndex : e.start;
			end = section == endItem.section && i == iend ? 
					endItemIndex : e.end;
			if (i >= iend && math.compare(end, endItemIndex) == 0) {
				i = items.size();
			}
			i++;
			return true;
		}
	}
}
