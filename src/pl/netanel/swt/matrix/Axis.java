package pl.netanel.swt.matrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;

import pl.netanel.util.ImmutableIterator;
import pl.netanel.util.Preconditions;

/**
 * Axis represents a horizontal or vertical axis of a matrix. It is divided into sections.
 * <dl>
 * <dt><b>Events:</b></dt>
 * <dd>Selection, DefaultSelection</dd>
 * </dl>
 * 
 * @param <N> defines the indexing class for this axis
 * @see Section
 *
 * @author Jacek
 * @created 27-03-2011
 */
public class Axis<N extends Number> implements Iterable<Section<N>> {
	private static final String FREEZE_ITEM_COUNT_ERROR = "Freeze item count must greater then 0";
	
	final Math<N> math;
	final ArrayList<Section<N>> sections;
	final ArrayList<SectionClient<N>> clientSections;
	final HashMap<Section<N>, SectionClient<N>> sectionMap;
	
	private SectionClient<N> body, header;
	private int autoScrollOffset, resizeOffset;
	final Layout<N> layout;
	final Listeners listeners;
	
	int index;
	Matrix<? extends Number, ? extends Number> matrix;
	private ScrollBar scrollBar;

	/**
	 * Creates axis indexed by Integer class.
	 * @see #Axis(Class)
	 */
	public Axis() {
		this((Class<N>) Integer.class);
	}
	
	/**
	 * Creates axis with the header and body sections indexed by the specified NUmber subclass.
	 * The header section count is set to one and its visibility to false.
	 * @see #Axis(Section...)
	 */
	public Axis(Class<N> numberClass) {
		this(new Section(numberClass), new Section(numberClass));
		sections.get(0).setCount(math.ONE_VALUE());
	}

	/**
	 * Creates axis with the specified sections. 
	 * <p>
	 * At least one section must be provided.
	 * All the sections must be indexed by the same Number subclass.
	 * <p>
	 * If there is one section it becomes the body section. Otherwise the first section 
	 * becomes the header and the second one becomes the body.
	 * @see #Axis(Section...)
	 */
	public Axis(Section<N> ...sections) {
		Preconditions.checkArgument(sections.length > 0, "Model must have at least one section");
		math = sections[0].math;
		this.sections = new ArrayList(sections.length);
		this.clientSections = new ArrayList(sections.length);
		this.sectionMap = new HashMap(sections.length);
		for (int i = 0; i < sections.length; i++) {
			Preconditions.checkArgument(sections[i].math.equals(math), 
				"Section at {0} must be indexed by the same Number subclass as the first section {1}", 
				sections[i].math.getNumberClass(), math.getNumberClass());				
			SectionClient section = new SectionClient(sections[i]);
			section.core.index = i;
			section.core.axis = this;
			this.sections.add(section.core);
			this.clientSections.add(section);
			this.sectionMap.put(section.core, section);
		}
		if (sections.length == 1) {
			setBody(0);
		} else {
			setHeader(0);
			setBody(1);
			header.setNavigationEnabled(false);
			header.setVisible(false);
		}
		
		autoScrollOffset = Matrix.AUTOSCROLL_OFFSET_Y;
		resizeOffset = Matrix.RESIZE_OFFSET_Y;
		layout = new Layout(this);
		listeners = new Listeners();
	}

	/**
	 * Returns the body section wrapped in validation checker.
	 * @return the body section wrapped in validation checker
	 * @see #getSection(int)
	 */
	public Section<N> getBody() {
		return body;
	}
	/**
	 * Returns the header section wrapped in validation checker.
	 * @return the header section wrapped in validation checker
	 * @see #getSection(int)
	 */
	public Section<N> getHeader() {
		return header;
	}

	/**
	 * Specifies which section is the body section.
	 * @param sectionIndex
	 */
	public void setBody(int sectionIndex) {
		Preconditions.checkPositionIndex(sectionIndex, sections.size(), "sectionIndex");
		this.body = sectionMap.get(sections.get(sectionIndex));
	}

	/**
	 * Specifies which section is the header section.
	 * @param sectionIndex
	 */
	public void setHeader(int sectionIndex) {
		Preconditions.checkPositionIndex(sectionIndex, sections.size(), "sectionIndex");
		this.header = sectionMap.get(sections.get(sectionIndex));
	}
	
	/**
	 * Returns the number of sections in the receiver. 
	 * @return the number of sections in the receiver.
	 * @see #setCount(Number)
	 */
	public int getSectionCount() {
		return sections.size();
	}
	
	/**
	 * Returns the checked section at the specified position in this axis.
	 * <p>
	 * A checked section delegates calls to an unchecked section proceeding it with an
	 * argument validation checking.
	 * 
	 * @param sectionIndex index of the section to return
	 * @return the section at the specified position in this axis
	 * 
	 * @see #getSectionUnchecked(int)
	 */
	public Section<N> getSection(int sectionIndex) {
		return clientSections.get(sectionIndex);
	}
	
	/**
	 * Returns the unchecked section at the specified position in this axis.
	 * <p>
	 * Unchecked section skips argument validation checking in getters 
	 * to improve performance. 
	 * 
	 * @param sectionIndex index of the section to return
	 * @return the section at the specified position in this axis
	 * 
	 * @see #getSection(int)
	 */
	public Section<N> getSectionUnchecked(int sectionIndex) {
		return sections.get(sectionIndex);
	}
	
	/**
	 * Returns iterator for checked sections.
	 */
	@Override
	public Iterator<Section<N>> iterator() {
		final Iterator<SectionClient<N>> it = clientSections.iterator();
		return new ImmutableIterator<Section<N>>() {
			@Override
			public boolean hasNext() {
				return it.hasNext();
			}
			@Override
			public Section<N> next() {
				return it.next();
			}
		};
	}

	/*------------------------------------------------------------------------
	 * Viewport 
	 */

	/**
	 * Returns number of items visible in the viewport.
	 * @return number of items visible in the viewport
	 */
	public int getViewportItemCount() {
		layout.computeIfRequired();
		return layout.head.count + layout.tail.count + 
			layout.main.cells.size(); // - layout.trim;
	}
	
	
	boolean isLastCellTrimmed() {
		layout.computeIfRequired();
		return layout.isTrimmed;
	}
	
	/**
	 * Returns the position of the given item in the viewport or <code>null</code>
	 * if the the viewport does not contain the item.
	 * 
	 * @param section 
	 * @param index
	 * @return the position of the given item in the viewport
	 */
	public int getViewportPosition(AxisItem<N> item) {
		layout.computeIfRequired();
		return layout.indexOf(item);
	}
	
	
	public N getIndexAt(int i) {
		AxisItem<N> item = layout.getIndexAt(i);
		return item == null ? null : item.getIndex();
	}
	
	/**
	 * 
	 * @param i
	 * @return
	 */
	public int[] getLineBound(int i) {
		Bound bound = layout.getLineBound(i);
		return bound == null ? null : new int[] {bound.distance, bound.width};
	}
	
	/*------------------------------------------------------------------------
	 * Navigation 
	 */

	/**
	 * Returns the focus item. Or <code>null</code> if no item has focus.
	 * @return the focus item
	 */
	public AxisItem<N> getFocusItem() {
		layout.computeIfRequired();
		return layout.current;
	}
	
	/**
	 * Sets the focus marker to the item at given index in the given section.
	 * <p>
	 * If section has the focus item disabled (see {@link Section#setFocusItemEnabled(boolean)}) 
	 * then this method does nothing.
	 *   
	 * @param section section in which to set the focus
	 * @param index index in the section at which to set the focus 
	 */
	public void setFocusItem(Section<N> section, N index) {
		layout.setCurrentItem(AxisItem.create(section, index));
	}

	
	/*------------------------------------------------------------------------
	 * Freeze
	 */

	/**
	 * Freezes the specified amount of first items on this axis. 
	 * @param freezeItemCount amount of first items to freeze
	 */
	public void freezeHead(int freezeItemCount) {
		Preconditions.checkArgument(freezeItemCount >= 0, FREEZE_ITEM_COUNT_ERROR);
		layout.freezeHead(freezeItemCount);
	}

	/**
	 * Freezes the specified amount of last items on this axis. 
	 * @param freezeItemCount amount of last items to freeze
	 */
	public void freezeTail(int freezeItemCount) {
		Preconditions.checkArgument(freezeItemCount >= 0, FREEZE_ITEM_COUNT_ERROR);
		layout.freezeTail(freezeItemCount);
	}
	
//	/**
//	 * Freezes the amount of first items on this axis that is between 
//	 * the first visible item (inclusively) the specified item (exclusively).
//	 *  
//	 * @param section section of the item bounding the head frozen area
//	 * @param index index of the item bounding the head frozen area
//	 * @return number of frozen items
//	 */
//	public int freezeHead(AxisItem<N> item) {
//		layout.freezeHead(item);
//		return layout.head.count;
//	}
//	
//	/**
//	 * Freezes the amount of last items on this axis that is between 
//	 * the specified item (exclusively) and the last visible item (inclusively). 
//	 *  
//	 * @param section section of the item bounding the tail frozen area
//	 * @param index index of the item bounding the tail frozen area
//	 * @return number of frozen items
//	 */
//	public int freezeTail(AxisItem<N> item) {
//		layout.freezeTail(item);
//		return layout.tail.count;
//	}
	
	
	/*------------------------------------------------------------------------
	 *
	 */

	/**
	 * Returns the offset from the edge of scrolling area within which dragging causes 
	 * the content to scroll automatically and extend the dragged distance. 
	 * The default value is 8 for horizontal and 6 for vertical axis.  
	 */
	public int getAutoScrollOffset() {
		return autoScrollOffset;
	}

	/**
	 * Sets the offset from the edge of scrolling area within which dragging causes 
	 * the content to scroll automatically and extend the dragged distance.
	 */  
	public void setAutoScrollOffset(int offset) {
		this.autoScrollOffset = offset;
	}

	/**
	 * Returns the offset from the dividing line within which dragging changes the axis item width. 
	 * The default value is 3 for horizontal and 2 for vertical axis.  
	 */
	public int getResizeOffset() {
		return resizeOffset;
	}

	/**
	 * Sets the offset from the dividing line within which dragging changes the axis item width. 
	 * @param offset
	 */
	public void setResizeOffset(int offset) {
		this.resizeOffset = offset;
	}
	
	
	public void pack(Section<N> section, N index) {
//		Cursor cursor = matrix.getCursor();
//		GC gc = new GC(matrix.getDisplay());
//		try {
//			int x = 0, y = 0, w = 0;
//			Point size = new Point(0, 0);
//			for (Zone<? extends Number, ? extends Number> zone: matrix.model.zones) {
//				if (this.index == 0 && zone.section0.equals(section)) {
//					for (Painter painter: zone.painters) {
//						painter.init();
//						AxisItemSequence seq = AxisItem.createSequence(matrix.axis1);
//						for (seq.init(); seq.next();) {
//							painter.computeSize1(seq.index0, seq.index1, size);
//							x = java.lang.Math.max(x, size.x);
//							y = java.lang.Math.max(y, size.y);
//						}
//						
//					}
//				}
//			}
//			
//			if (w != 0) section.setCellWidth(index, index, w);
//			layout.compute();
//			matrix.redraw();
//		} finally {
//			matrix.setCursor(cursor);
//			gc.dispose();
//		}
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
	boolean updateScrollBarVisibility(int size) {
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
	void updateScrollBarValues(int size) {
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
		
		for (int i = start.getSection().index; i <= end.getSection().index; i++) {
			Section section = sections.get(i);
			Number startIndex = i == start.getSection().index ? start.getIndex() : math.ZERO_VALUE();
			Number endIndex = i == end.getSection().index ? end.getIndex() : 
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

	void selectInZones(Section<N> section, N start, N end) {
		if (matrix != null) {
			matrix.selectInZones(index, section, start, end);
		}
	}
	
	AxisItem getLastItem() {
		for (int i = sections.size(); i-- > 0;) {
			Section section = sections.get(i);
			if (section.isEmpty()) continue;
			return AxisItem.create(section, math.decrement(section.getCount()));
		}
		return getFirstItem();
	}

	AxisItem getFirstItem() {
		return AxisItem.create(sections.get(0), math.ZERO_VALUE());
	}

	int comparePosition(AxisItem<N> item1, AxisItem<N> item2) {
		int diff = item1.getSection().index - item2.getSection().index;
		if (diff != 0) return diff; 
		return math.compare(
				item1.getSection().indexOf(item1.getIndex()), 
				item2.getSection().indexOf(item2.getIndex()));
	}
	
	/**
	 * Sets the hidden state of selected indexes in each section.
	 * @param hidden new hiding state
	 */
	void setHidden(boolean hidden) {
		for (int i = 0, imax = sections.size(); i < imax; i++) {
			Section section = sections.get(i);
			section.setHiddenSelected(hidden);
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
			istart = startItem.getSection().order.items.isEmpty() ? 0 : 
				startItem.getSection().order.getExtentIndex(startItem.getIndex());
			iend = endItem.getSection().order.items.isEmpty() ? 0 : 
				endItem.getSection().order.getExtentIndex(endItem.getIndex());
			startItemIndex = math.create(startItem.getIndex());
			endItemIndex = math.create(endItem.getIndex());
			
			section = startItem.getSection();
			sectionIndex = section.index; 
			lastSectionIndex = sections.indexOf(endItem.getSection()); 
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
			start = section == startItem.getSection() && i == istart ? 
					startItemIndex : e.start;
			end = section == endItem.getSection() && i == iend ? 
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
		if (section instanceof SectionClient) section = ((SectionClient) section).core;
		Bound bound = layout.getCellBound(AxisItem.create(section, index));
		return bound == null ? null : bound.copy();
	}

	Bound getLineBound(Section<N> section, N index) {
		if (section == null || index == null) return null;
		if (section instanceof SectionClient) section = ((SectionClient) section).core;
		Bound bound = layout.getLineBound(AxisItem.create(section, index));
		return bound == null ? null : bound.copy();
	}
	
	
	
	int[] getZOrder() {
		// Calculate z-order
		int[] order = new int[sections.size()];
		int j = 0;
		int bodyIndex = sections.indexOf(body.core);
		for (int i = bodyIndex, imax = this.sections.size(); i < imax; i++) {
			order[j++] = i;
		}
		for (int i = bodyIndex; i-- > 0;) {
			order[j++] = i;
		}
		return order;
	}

	void deleteInZones(Section section, N start, N end) {
		matrix.model.deleteInZones(index, section, start, end);
		if (layout.current.getSection().equals(section) && layout.math.contains(start, end, layout.current.getIndex())) {
			layout.ensureCurrentIsValid();
		}
	}
	
	void insertInZones(Section section, N target, N count) {
		matrix.model.insertInZones(index, section, target, count);
		layout.show(AxisItem.create(section, target));
	}

	void checkSection(Section section) {
		end: {
			for (Section section2 : sections) {
				if (section2.equals(section)) {
					break end;
				}
			}
			Preconditions.checkArgument(false,
					"Section {0} does not belong to axis {1}", section, index);
		}
	}
}