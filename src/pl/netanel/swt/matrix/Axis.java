package pl.netanel.swt.matrix;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;

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
public class Axis<N extends Number>  {
	private static final String FREEZE_ITEM_COUNT_ERROR = "Freeze item count cannot be negative";
	
	final Math<N> math;
	final ArrayList<Section<N>> sections;
	final ArrayList<SectionClient<N>> clientSections;
	final HashMap<Section<N>, SectionClient<N>> sectionMap;
	
	private SectionClient<N> body, header;
	private int autoScrollOffset, resizeOffset;
	final Layout<N> layout;
	
	int index;
	Matrix<? extends Number, ? extends Number> matrix;
	private ScrollBar scrollBar;

	/**
	 * Creates axis indexed by Integer class.
	 * @see #Axis(Class)
	 */
	public Axis() {
		this((Class<N>) Integer.class, 2);
	}
	
	/**
	 * Creates axis with the specified number of sections indexed by the specified NUmber subclass.
	 * The header section count is set to one and its visibility to false.
	 * @param numberClass sub-type of {@link Number} class to index the sections
	 * @param sectionCount number of sections to create
	 * @see #Axis(Section...)
	 */
	public Axis(Class<N> numberClass, int sectionCount) {
		this(createSections(numberClass, sectionCount));
		sections.get(0).setCount(math.ONE_VALUE());
	}

	private static Section[] createSections(Class numberClass, int sectionCount) {
		Section[] sections = new Section[sectionCount];
		for (int i = 0; i < sectionCount; i++) {
			sections[i] = new Section(numberClass);
		}
		return sections;
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
	}

	/**
	 * Returns the body section wrapped in validation checker.
	 * @return the body section wrapped in validation checker
	 */
	public Section<N> getBody() {
		return body;
	}
	
	/**
	 * Returns the header section wrapped in validation checker.
	 * @return the header section wrapped in validation checker
	 */
	public Section<N> getHeader() {
		return header;
	}

//	private ArrayList<Zone> getZones(Section section) {
//		ArrayList zones = new ArrayList();
//		if (matrix != null) {
//			if (index == 0) {
//				for (Zone zone: matrix.model.zones) {
//					if (zone.getSection0().equals(section)) {
//						zones.add(zone);
//					}
//				}
//			} else {
//				for (Zone zone: matrix.model.zones) {
//					if (zone.getSection1().equals(section)) {
//						zones.add(zone);
//					}
//				}
//			}
//		}
//		return zones;
//	}
	
	/**
	 * Specifies which section is the body section.
	 * @param sectionIndex
	 */
	public void setBody(int sectionIndex) {
		Preconditions.checkPositionIndex(sectionIndex, sections.size(), "sectionIndex");
		SectionClient<N> section = sectionMap.get(sections.get(sectionIndex));

//		if (body != null) {
//			for (Zone oldZone: getZones(body.core)) {
//				Zone newZone = matrix.model.getZoneUnchecked(section.core, oldZone.getSection1());
//
//				newZone.painters.clear();
//				newZone.painters.addAll(oldZone.painters);
//				oldZone.painters.clear();
//
//				newZone.bindings.clear();
//				newZone.bindings.add(oldZone.bindings);
//				oldZone.bindings.clear();
//			}
//		}
		this.body = section;
	}

	/**
	 * Specifies which section is the header section.
	 * @param sectionIndex
	 * @throws IndexOutOfBoundsException if sectionIndex is out 
	 * 		of 0 ... {@link #getSectionCount()}-1 bounds
	 */
	public void setHeader(int sectionIndex) {
		Preconditions.checkPositionIndex(sectionIndex, sections.size(), "sectionIndex");
		this.header = sectionMap.get(sections.get(sectionIndex));
	}
	
	/**
	 * Returns the number of sections in the receiver. 
	 * @return the number of sections in the receiver.
	 */
	public int getSectionCount() {
		return sections.size();
	}
	
	/**
	 * Returns the section at the specified position in this axis.
	 * 
	 * @param sectionIndex index of the section to return
	 * @return the section at the specified position in this axis
	 * @throws IndexOutOfBoundsException if sectionIndex 
	 * 		is out of 0 ... {@link #getSectionCount()}-1 bounds
	 * 
	 */
	public Section<N> getSection(int sectionIndex) {
	  Preconditions.checkPositionIndex(sectionIndex, sections.size(), "sectionIndex");
	  return sections.get(sectionIndex);
	}
	
	
	/*------------------------------------------------------------------------
	 * Viewport 
	 */

	/**
	 * Returns number of items visible in the viewport. Including items partially visible.
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
	 * Returns the position of the given item in the viewport or -1
	 * if the viewport does not display the item.
	 * 
	 * @param item the item to get position for
	 * @return the position of the given item in the viewport
	 * @throws IllegalArgumentException if item is <code>null</code> or item's section
	 * 		does not belong to this axis.
	 * @throws IndexOutOfBoundsException if item's index is out 
	 * 		of 0 ... {@link Section#getCount()}-1 bounds
	 */
	public int getViewportPosition(AxisItem<N> item) {
		Preconditions.checkNotNullWithName(item, "item");
		Section<N> section = item.getSection();
		section = checkSection(section);
		section.checkIndex(item.getIndex(), section.getCount(), "item index");
		
		layout.computeIfRequired();
		return layout.indexOf(item);
	}
	
	/**
	 * Returns item visible at the specified position in the viewport or <code>null</code>
	 * if the position is outside of the viewport bounds.
	 * 
	 * @param position the position the get the item for
	 * @return item visible at the specified position in the viewport
	 */
	public AxisItem<N> getItemAt(int position) {
		AxisItem<N> item = layout.getIndexAt(position);
		return item == null ? null : item;
	}
	
	/**
	 * Returns item visible at the specified distance from the beginning of viewport area 
	 * or <code>null</code> if the distance is outside of the viewport bounds.
	 * 
	 * @param position the position the get the item for
	 * @return the position of the given item in the viewport
	 */
	public AxisItem<N> getItemByDistance(int distance) {
		AxisItem<N> item = layout.getItemByDistance(distance);
		return item == null ? null : item;
	}
	
	/**
	 * Returns the cell bound at the specified position in the viewport or <code>null</code>
	 * if the position is outside of the viewport scope. Cell bound is an array of integers, 
	 * where the first one is the distance from the beginning of viewport and the second one
	 * is the cell width. 
	 * 
	 * @param position the position the line bound the item for
	 * @return the cell bound at the specified position in the viewport
	 */
	public int[] getCellBound(int position) {
		Bound bound = layout.getCellBound(position);
		return bound == null ? null : new int[] {bound.distance, bound.width};
	}
	
	/**
	 * Returns the line bound at the specified position in the viewport or <code>null</code>
	 * if the position is outside of the viewport scope. Line bound is an array of integers, 
	 * where the first one is the distance from the beginning of viewport and the second one
	 * is the line width. 
	 * 
	 * @param position the position the line bound the item for
	 * @return the line bound at the specified position in the viewport
	 */
	public int[] getLineBound(int position) {
		Bound bound = layout.getLineBound(position);
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
	 * @throws IllegalArgumentException if the section is <code>null</code> or 
	 * 		does not belong to this axis.
	 * @throws IndexOutOfBoundsException if index is out 
	 * 		of 0 ... {@link Section#getCount()}-1 bounds
	 */
	public void setFocusItem(Section<N> section, N index) {
		section = checkSection(section);
		section.checkIndex(index, section.getCount(), "index");
		
		layout.setCurrentItem(AxisItem.create(section, index));
		if (matrix != null) matrix.redraw();
	}

	
	/*------------------------------------------------------------------------
	 * Freeze
	 */

	/**
	 * Freezes the specified amount of first items on this axis. 
	 * @param freezeItemCount amount of first items to freeze
	 * @throws IllegalArgumentException if the argument is lower then zero
	 */
	public void freezeHead(int freezeItemCount) {
		Preconditions.checkArgument(freezeItemCount >= 0, FREEZE_ITEM_COUNT_ERROR);
		layout.freezeHead(freezeItemCount);
	}

	/**
	 * Freezes the specified amount of last items on this axis. 
	 * @param freezeItemCount amount of last items to freeze
	 * @throws IllegalArgumentException if the argument is lower then zero
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
	 * 
	 * @param offset maximum distance from the edge of the scrolling area in which dragging will 
	 * 	cause the content to scroll automatically 
	 * @throws IllegalArgumentException if the argument is lower then zero
	 */  
	public void setAutoScrollOffset(int offset) {
		Preconditions.checkArgument(offset >= 0, "offset cannot be negative");
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
	 * 
	 * @param offset maximum distance from the edge of the line in which dragging will 
	 * 	cause cell width change
	 * @throws IllegalArgumentException if the argument is lower then zero
	 */
	public void setResizeOffset(int offset) {
		Preconditions.checkArgument(offset >= 0, "offset cannot be negative");
		this.resizeOffset = offset;
	}
	
	
	public void pack(AxisItem<N> item) {
		Cursor cursor = matrix.getCursor();
		GC gc = new GC(matrix);
		try {
			int w = 0;
			for (Zone<? extends Number, ? extends Number> zone: matrix.model.zones) {
				if (this.index == 0 && zone.section0.equals(item.getSection())) {
					CellSet set = new CellSet(zone.section0.math, zone.section1.math);
					set.add(item.getIndex(), item.getIndex(),
						zone.section1.math.ZERO_VALUE(), 
						zone.section1.math.decrement(zone.section1.getCount()));
					NumberPairSequence seq = new NumberPairSequence(set);
					for (Painter painter: zone.painters) {
						painter.init(gc);
						for (seq.init(); seq.next();) {
							w = java.lang.Math.max(w, painter.computeHeight(seq.index0(), seq.index1()));
						}
					}
				}
				else if (this.index == 1 && zone.section1.equals(item.getSection())) {
					CellSet set = new CellSet(zone.section0.math, zone.section1.math);
					set.add(zone.section0.math.ZERO_VALUE(), 
							zone.section0.math.decrement(zone.section0.getCount()), 
							item.getIndex(), item.getIndex());
					NumberPairSequence seq = new NumberPairSequence(set);
					for (Painter painter: zone.painters) {
						painter.init(gc);
						for (seq.init(); seq.next();) {
							w = java.lang.Math.max(w, painter.computeWidth(seq.index0(), seq.index1()));
						}
					}
				}
			}
			
			if (w != 0) item.getSection().setCellWidth(item.getIndex(), w);
		} 
		finally {
			gc.dispose();
			matrix.setCursor(cursor);
		}
		layout.compute();
		matrix.redraw();
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
			
			if (select == true) {
				Event event = new Event();
				event.type = SWT.Selection;
				event.widget = matrix;
				//event.data = matrix.getZone(axisIndex == 0 ? Zone.ROW_HEADER : Zone.COLUMN_HEADER);
				section.listeners.add(event);

			}
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
			while (i >= items.size()) {
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
		if (layout.current.getSection().equals(section) && 
				layout.math.contains(start, end, layout.current.getIndex())) {
			layout.ensureCurrentIsValid();
		}
	}
	
	void insertInZones(Section section, N target, N count) {
		matrix.model.insertInZones(index, section, target, count);
		layout.show(AxisItem.create(section, target));
	}

	Section checkSection(Section section) {
		Preconditions.checkNotNullWithName(section, "section");
		end: {
			for (Section section2 : sections) {
				if (section2.equals(section)) {
					break end;
				}
			}
			Preconditions.checkArgument(false,
					"Section {0} does not belong to axis {1}", section, index);
		}
		if (section instanceof SectionClient) return ((SectionClient) section).core;
		return section;
	}
}