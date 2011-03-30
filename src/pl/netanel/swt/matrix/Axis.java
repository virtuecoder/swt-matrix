package pl.netanel.swt.matrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;

import pl.netanel.swt.Listeners;
import pl.netanel.util.Preconditions;

/**
 * <dl>
 * <dt><b>Events:</b></dt>
 * <dd>Selection, DefaultSelection</dd>
 * </dl>
 *
 * @author Jacek
 * @created 27-03-2011
 */
public class Axis<N extends Number> implements Iterable<Section<N>> {
	
	final Math<N> math;
	final ArrayList<Section<N>> sections;
	final HashMap<SectionUnchecked<N>, Section<N>> sectionMap;
	Section<N> body, header;
	private int autoScrollOffset, resizeOffset;
	Layout<N> layout;
	final Listeners listeners;
	
//	Section<N> currentSection;
//	N currentIndex;

	private ScrollBar scrollBar;
	int index;
	Matrix matrix;
	
	public Axis() {
		this(new Section<N>(), new Section<N>());
		sections.get(0).setCount(math.ONE_VALUE());
	}
	
	public Axis(Class<N> numberClass) {
		this(new Section(numberClass), new Section(numberClass));
	}

	public Axis(Section<N> ...sections) {
		Preconditions.checkArgument(sections.length > 0, "Model must have at least one section");
		math = sections[0].core.math;
		this.sections = new ArrayList(sections.length);
		this.sectionMap = new HashMap(sections.length);
		for (int i = 0; i < sections.length; i++) {
			Section section = sections[i];
			section.core.index = i;
			section.core.axis = this;
			this.sections.add(section);
			this.sectionMap.put(section.core, section);
		}
		if (sections.length == 0) {
			this.sections.add(new Section(math));
			setBody(0);
		} else {
			if (sections.length == 1) {
				setBody(0);
			} else {
				setHeader(0);
				setBody(1);
				header.setNavigationEnabled(false);
			}
		}
		
		autoScrollOffset = M.AUTOSCROLL_OFFSET_Y;
		resizeOffset = M.RESIZE_OFFSET_Y;
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
		this.header = sections.get(sectionIndex);
	}
	
	
	public int getSectionCount() {
		return sections.size();
	}
	
	public Section<N> getSection(int i) {
		return sections.get(i);
	}
	

	@Override
	public Iterator<Section<N>> iterator() {
		return sections.iterator();
	}

	
	/*------------------------------------------------------------------------
	 * Navigation 
	 */
	
	public Section<N> getFocusSection() {
		layout.computeIfRequired();
		return layout.current == null ? null : sectionMap.get(layout.current.section);
	}
	
	public N getFocusIndex() {
		layout.computeIfRequired();
		return layout.current == null ? null : layout.current.index;
	}

	public Section<N> getTopSection() {
		layout.computeIfRequired();
		return layout.start == null ? null : sectionMap.get(layout.start.section);
	}
	
	public N getTopIndex() {
		layout.computeIfRequired();
		return layout.start == null ? null : layout.start.index;
	}
	
	public void navigate(SectionUnchecked<N> section, N index) {
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

	public int getResizeOffset() {
		return resizeOffset;
	}

	public void setResizeOffset(int autoScrollOffset) {
		this.resizeOffset = autoScrollOffset;
	}
	
	/*------------------------------------------------------------------------
	 * Non public 
	 */

	void setMatrix(final Matrix matrix, int axisIndex) {
		this.index = axisIndex;
		this.matrix = matrix;
		
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
			section.setSelected(startIndex, endIndex, select);
		}
	}

	void setSelected(boolean selected) {
		for (int i = 0, imax = sections.size(); i < imax; i++) {
			Section section = sections.get(i);
			section.setSelectedAll(selected);
		}
	}

	void selectInZones(SectionUnchecked<N> section, N start, N end) {
		if (matrix != null) {
			matrix.selectInZones(index, section, start, end);
		}
	}
	
	AxisItem getLastItem() {
		for (int i = sections.size(); i-- > 0;) {
			Section section = sections.get(i);
			if (section.core.isEmpty()) continue;
			return new AxisItem(section.core, math.decrement(section.getCount()));
		}
		return getFirstItem();
	}

	AxisItem getFirstItem() {
		return new AxisItem(sections.get(0).core, math.ZERO_VALUE());
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
			section.core.setHiddenSelected(hidden);
		}
	}
	

	
	/**
	 * Iterates over extents between the start and end items. 
	 * 
	 * @author Jacek Kolodziejczyk created 11-03-2011
	 */
	class ExtentSequence {
		SectionUnchecked<N> section;
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
			sectionIndex = section.index; 
			lastSectionIndex = sections.indexOf(endItem.section); 
			items = sections.get(sectionIndex).core.order.items;
			i = istart;
		}
		
		boolean next() {
			if (i >= items.size()) {
				sectionIndex++;
				if (sectionIndex > lastSectionIndex) return false;
				section = sections.get(sectionIndex).core;
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

	Bound getCellBound(Section<N> section, N index) {
		if (section == null || index == null) return null;
		Bound bound = layout.getBound(new AxisItem<N>(section.core, index));
		return bound == null ? null : bound.copy();
	}

	
	
	/*------------------------------------------------------------------------
	 * Non public 
	 */

	int[] getZOrder() {
		// Calculate z-order
		int[] order = new int[sections.size()];
		int j = 0;
		int bodyIndex = sections.indexOf(body);
		for (int i = bodyIndex, imax = this.sections.size(); i < imax; i++) {
			order[j++] = i;
		}
		for (int i = bodyIndex; i-- > 0;) {
			order[j++] = i;
		}
		return order;
	}
	

}