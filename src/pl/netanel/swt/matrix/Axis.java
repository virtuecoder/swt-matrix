package pl.netanel.swt.matrix;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;

import pl.netanel.util.Preconditions;

/**
 * Axis represents a horizontal or vertical axis of a matrix. 
 * It is divided into sections.
 * <dl>
 * <dt><b>Events:</b></dt>
 * <dd>Selection, DefaultSelection</dd>
 * </dl>
 * 
 * @param <N> specifies the indexing class for this axis
 * @see Section
 *
 * @author Jacek
 * @created 27-03-2011
 */
public class Axis<N extends Number>  {
	private static final String FREEZE_ITEM_COUNT_ERROR = "Freeze item count cannot be negative";
	
	Math<N> math;
	ArrayList<SectionClient<N>> sections;
	
	SectionClient<N> body, header;
	private int autoScrollOffset, resizeOffset, minimalCellWidth = 5;
	Layout<N> layout;
	
	char symbol;
	Matrix<? extends Number, ? extends Number> matrix;
	private ScrollBar scrollBar;

	/**
	 * Constructs axis indexed by Integer class with two sections.
	 * @see #Axis(Class, int)
	 */
	@SuppressWarnings("unchecked") 
	public Axis() {
		this((Class<N>) Integer.class, 2, 0, 1);
	}
	
  /**
   * Constructs axis indexed by the specified Number subclass containing the
   * specified number of sections. The header section count is set to one,
   * its visibility to false and focus item enabled to false.
   * 
   * @param numberClass sub-type of {@link Number} class to index the sections
   * @param sectionCount number of sections to create
   * @throws IllegalArgumentException if <code>numberClass</code> is other then
   *  int.class, Integer.class, long.class, Long.class, BigInteger.class
   * @throws IllegalArgumentException if the sectionCount is lower then 2 
   * @throws IndexOutOfBoundsException if the headerIndex or bodyIndex is out of range
   *         (<tt>index &lt; 0 || index &gt;= sectionCount</tt>)
   * @throws IllegalArgumentException if the headerIndex equals bodyIndex 
   */
	public Axis(Class<N> numberClass, int sectionCount, int headerIndex, int bodyIndex) {
	  Preconditions.checkNotNullWithName(numberClass, "numberClass");
	  Preconditions.checkArgument(sectionCount > 1, "sectionCount must be greater then 1");
	  Preconditions.checkPositionIndex(headerIndex, sectionCount, "headerIndex");
	  Preconditions.checkPositionIndex(bodyIndex, sectionCount, "bodyIndex");
	  Preconditions.checkArgument(bodyIndex != headerIndex, "headerIndex cannot equal bodyIndex");
	  
	  init(numberClass, sectionCount, headerIndex, bodyIndex);
	}

	/**
	 * Needed only for test! 
	 * TODO: refactor DirectionTests
	 * @param numberClass
	 * @param sectionCount
	 */
	Axis(Class<N> numberClass, int sectionCount) {
	  init(numberClass, sectionCount, 0, 1);
	}
	
  private void init(Class<N> numberClass, int sectionCount,
    int headerIndex, int bodyIndex) {
    math = Math.getInstance(numberClass);
	  sections = new ArrayList<SectionClient<N>>(sectionCount);
	  for (int i = 0; i < sectionCount; i++) {
	    SectionCore<N> section = new SectionCore<N>(numberClass);
//	    Preconditions.checkArgument( 
//	      section.getIndexClass().equals(numberClass),
//	      "Section at %s position is indexed by a different Number subclass " +
//	        "then the first section: %s != %s",
//	        i, section.getIndexClass(), numberClass);
	    
	    section.index = i;
      section.axis = this;
      sections.add(new SectionClient<N>(section));
	  }
	  
	  if (headerIndex < sectionCount) {
	    header = sections.get(headerIndex);
	    header.setCount(math.ONE_VALUE());
	    header.setFocusItemEnabled(false);
	    header.setVisible(false);
	  }
	  
	  if (bodyIndex < sectionCount) {
	    body = sections.get(bodyIndex);
	  }
	  
	  autoScrollOffset = Matrix.AUTOSCROLL_OFFSET_Y;
	  resizeOffset = Matrix.RESIZE_OFFSET_Y;
	  layout = new Layout<N>(this);
  }

	

	@Override public String toString() {
	  return Character.toString(symbol);
	}
	
	/**
	 * Returns the body section, which is by default a second section of the axis.
	 * Another section can be set as body by specifying <code>bodyIndex</code> argument
	 * in the Axis constructor.
	 * @return the body section
	 * @see #getHeader()
	 */
	public Section<N> getBody() {
		return body;
	}
	
	/**
	 * Returns the header section, which is by default the first section of the axis.
	 * Another section can be set as header by specifying <code>headerIndex</code> argument
	 * in the Axis constructor.
	 * @return the header section.
	 * @see #getBody()
	 */
	public Section<N> getHeader() {
		return header;
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
	 * 		of 0 ... {@link SectionCore#getCount()}-1 bounds
	 */
	public int getViewportPosition(AxisItem<N> item) {
		Preconditions.checkNotNullWithName(item, "item");
		SectionCore<N> section = item.section;
		section = checkSection(section, "item section");
		section.checkCellIndex(item.getIndex(), "item index");
		
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
	 * Returns the cell bound of the specified item in the viewport or <code>null</code>
	 * if the position is outside of the viewport scope. Cell bound is an array of integers, 
	 * where the first one is the distance from the beginning of viewport and the second one
	 * is the cell width. 
	 * 
	 * @param position the position the line bound the item for
	 * @return the cell bound at the specified position in the viewport
	 */
	public int[] getCellBound(AxisItem<N> item) {
		Bound bound = layout.getCellBound(item);
		return bound == null ? null : new int[] {bound.distance, bound.width};
	}
	
	/**
	 * Returns the line bound of the specified item in the viewport or <code>null</code>
	 * if the position is outside of the viewport scope. Line bound is an array of integers, 
	 * where the first one is the distance from the beginning of viewport and the second one
	 * is the line width. 
	 * 
	 * @param position the position the line bound the item for
	 * @return the line bound at the specified position in the viewport
	 */
	public int[] getLineBound(AxisItem<N> item) {
		Bound bound = layout.getLineBound(item);
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
	
//	/**
//	 * Returns the item at which a SWT.MouseDown event happened. 
//	 * Or <code>null</code> if no item has been clicked on.
//	 * @return the item at which a SWT.MouseDown event happened
//	 */
//	public AxisItem<N> getMouseItem(int event) {
//	  layout.computeIfRequired();
//	  return (symbol == 'Y' ? matrix.listener.state0 : matrix.listener.state1).mouseDownItem;
//	}
	
	
	
	/**
	 * Sets the focus marker to the item at given index in the given section.
	 * <p>
	 * If section has the focus item disabled (see {@link SectionCore#setFocusItemEnabled(boolean)}) 
	 * then this method does nothing.
	 *   
	 * @param section section in which to set the focus
	 * @param index index in the section at which to set the focus 
	 * @throws IllegalArgumentException if the section is <code>null</code> or 
	 * 		does not belong to this axis.
	 * @throws IndexOutOfBoundsException if index is out 
	 * 		of 0 ... {@link SectionCore#getCount()}-1 bounds
	 */
	public void setFocusItem(Section<N> section, N index) {
	  SectionCore<N> section2 = SectionCore.from(section);
		section2 = checkSection(section2, "section");
		section2.checkCellIndex(index, "index");
		
		layout.setFocusItem(AxisItem.create(section2, index));
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
	
	public int getMinimalCellWidth() {
	  return minimalCellWidth;
	}
	
	public void setMinimalCellWidth(int minimalCellWidth) {
	  this.minimalCellWidth = minimalCellWidth;
	}
	
	
	/*------------------------------------------------------------------------
	 * Non public 
	 */


  void setMatrix(final Matrix matrix, char symbol) {
	  this.symbol = symbol;
		this.matrix = matrix;
		
		scrollBar = symbol == 'Y' ? matrix.getVerticalBar() : matrix.getHorizontalBar();
		if (scrollBar != null) {
			scrollBar.addListener(SWT.Selection, new Listener() {
				private int selection = -1;

				@Override public void handleEvent(Event e) {
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
	boolean updateScrollBarVisibility() {
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
			max += layout.trim;
		}
		scrollBar.setValues(layout.getScrollPosition(), min, max, thumb, 1, thumb);
	}

	
	void setSelected(AxisItem<N> start, AxisItem end, boolean select) {
		// Make sure start < end 
		if (comparePosition(start, end) > 0) {
			AxisItem tmp = start; start = end; end = tmp;
		}
		
		Section lastSection = null;
    AxisExtentSequence seq = new AxisExtentSequence(math, layout.sections);
    for (seq.init(start, end); seq.next();) {
      boolean sameSection = seq.section.equals(lastSection);
      seq.section.setSelected(seq.start, seq.end, select, !sameSection);
    }

//		for (int i = start.getSection().index; i <= end.getSection().index; i++) {
//			Section section = sections.get(i);
//			Number startIndex = i == start.getSection().index ? start.getIndex() : math.ZERO_VALUE();
//			Number endIndex = i == end.getSection().index ? end.getIndex() : 
//				math.increment(section.getCount());
//			
//			section.setSelected(startIndex, endIndex, select);
//			
//			if (select == true) {
//				Event event = new Event();
//				event.type = SWT.Selection;
//				event.widget = matrix;
//				//event.data = matrix.getZone(axisIndex == 0 ? Zone.ROW_HEADER : Zone.COLUMN_HEADER);
//				section.listeners.add(event);
//
//			}
//		}
	}

	void setSelected(boolean selectState, boolean notify, boolean notifyInZones) {
//	  // Determine if there is a selection change
//    boolean modified = false;
//    if (notify) {
//      for (Section section: sections) {
//        if (selectState) {
//          boolean allSelected = section.getSelectedCount().equals(section.getCount());
//          if (!allSelected) {
//            modified = true;
//            break;
//          }
//        }
//        else {
//          boolean nothingSelected = section.getSelectedCount().equals(section.math.ZERO_VALUE());
//          if (!nothingSelected) {
//            modified = true;
//            break;
//          }
//        }
//      }
//    }
    
		for (int i = 0, imax = sections.size(); i < imax; i++) {
			SectionCore<N> section = SectionCore.from(sections.get(i));
			section.setSelectedAll(selectState, notify, notifyInZones);
			
			if (notify) {
			  section.addSelectionEvent();
			}
		}
	}

	void selectInZones(Section<N> section, N start, N end, boolean state, boolean notify) {
		if (matrix != null) {
			matrix.selectInZones(symbol, section, start, end, state, notify);
		}
	}
	
	AxisItem<N> getLastItem() {
		for (int i = sections.size(); i-- > 0;) {
			SectionCore<N> section = layout.sections.get(i);
			if (section.isEmpty()) continue;
			return AxisItem.create(section, math.decrement(section.getCount()));
		}
		return getFirstItem();
	}

	AxisItem<N> getFirstItem() {
		return AxisItem.create(layout.sections.get(0), math.ZERO_VALUE());
	}

  /**
   * Compares section positions on this axis and returns value greater then 0 if
   * section1 is behind section 2, value lower then zero if section1 is before
   * section2 and 0 if sections are the same.
   * 
   * @param section1 a section to compare
   * @param section2 another section section to compare
   * @return comparison result
   * 
   * @throws IllegalArgumentException if section1 or section2 is <code>null</code> or 
   *    does not belong to this axis.
   */
	public int compare(Section<N> section1, Section<N> section2) {
	  return 
	    checkSection(section1, "section 1").index -
	    checkSection(section2, "section 2").index;
	}
	
	int comparePosition(SectionCore<N> section1, SectionCore<N> section2) {
	  return section1.index - section2.index;
	}
	
	public int compare(AxisItem<N> item1, AxisItem<N> item2) {
	  checkItem(item1, "item1");
    checkItem(item2, "item2");
    return comparePosition(item1, item2);
	}
	
	int comparePosition(AxisItem<N> item1, AxisItem<N> item2) {
	  int compareSections = comparePosition(item1.section, item2.section);
    if (compareSections != 0) return compareSections;
		return math.compare(
				item1.section.indexOf(item1.getIndex()), 
				item2.section.indexOf(item2.getIndex()));
	}
	
	/**
	 * Sets the hidden state of selected indexes in each section.
	 * @param hidden new hiding state
	 */
	void setHidden(boolean hidden) {
		for (int i = 0, imax = sections.size(); i < imax; i++) {
			Section<N> section = sections.get(i);
			section.setHiddenSelected(hidden);
		}
	}
	

	
	/**
	 * Iterates over extents between the start and end items. 
	 * 
	 * @author Jacek Kolodziejczyk created 11-03-2011
	 */
	class ExtentSequence {
		SectionCore<N> section;
		MutableNumber<N> start, end, startItemIndex, endItemIndex;
		private int i, istart, iend, sectionIndex, lastSectionIndex;
		private ArrayList<MutableExtent<N>> items;
		private AxisItem startItem, endItem;
	
		void init(AxisItem<N> startItem, AxisItem<N> endItem) {
			this.startItem = startItem;
			this.endItem = endItem;
			SectionCore<N> startSection = startItem.section;
	    SectionCore<N> endSection = endItem.section;
			istart = startSection.order.items.isEmpty() ? 0 : 
				startSection.order.getExtentIndex(startItem.getIndex());
			iend = endSection.order.items.isEmpty() ? 0 : 
				endSection.order.getExtentIndex(endItem.getIndex());
			startItemIndex = math.create(startItem.getIndex());
			endItemIndex = math.create(endItem.getIndex());
			
			section = startSection;
			sectionIndex = section.index; 
			items = section.order.items;
			lastSectionIndex = layout.sections.indexOf(endItem.section); 
			i = istart;
		}
		
		boolean next() {
			while (i >= items.size()) {
				sectionIndex++;
				if (sectionIndex > lastSectionIndex) return false;
				section = SectionCore.from(sections.get(sectionIndex));
				items = section.order.items;
				i = 0;
			}
			MutableExtent<N> e = items.get(i);	
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

	Bound getCellBound(SectionCore<N> section, N index) {
		if (section == null || index == null) return null;
		Bound bound = layout.getCellBound(AxisItem.create(section, index));
		return bound == null ? null : bound.copy();
	}

	Bound getLineBound(SectionCore<N> section, N index) {
		if (section == null || index == null) return null;
		Bound bound = layout.getLineBound(AxisItem.create(section, index));
		return bound == null ? null : bound.copy();
	}
	
	
	
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

	void deleteInZones(SectionCore<N> section, N start, N end) {
	  for (ZoneCore zone: matrix.model.zones) {
      if (symbol == 'X') {
        if (zone.sectionX.equals(this)) {
          zone.cellSelection.deleteX(start, end);
          zone.lastSelection.deleteX(start, end);
        }
      }
      else {
        if (zone.sectionY.equals(this)) {
          zone.cellSelection.deleteY(start, end);
          zone.lastSelection.deleteY(start, end);
        }
      }
    }
		if (layout.current.section.equals(section) && 
				layout.math.contains(start, end, layout.current.getIndex())) {
			layout.ensureCurrentIsValid();
		}
	}
	
	void insertInZones(SectionCore<N> section, N target, N count) {
	  for (ZoneCore zone: matrix.model.zones) {
      if (symbol == 'X') {
        if (zone.sectionX.equals(this)) {
          zone.cellSelection.insertX(target, count);
          zone.lastSelection.deleteX(target, count);
        }
      }
      else {
        if (zone.sectionY.equals(this)) {
          zone.cellSelection.insertY(target, count);
          zone.lastSelection.insertY(target, count);
        }
      }
	  }
		layout.show(AxisItem.create(section, target));
	}

	SectionCore<N> checkSection(Section<N> section, String name) {
		Preconditions.checkNotNullWithName(section, name);
		SectionCore<N> section1 = SectionCore.from(section);
		end: {
			for (SectionCore<N> section2 : layout.sections) {
				if (section2.equals(section1)) {
					break end;
				}
			}
			Preconditions.checkArgument(false,
					"Section {0} does not belong to axis {1}", name, symbol);
		}
		return section1;
	}
	
	void checkItem(AxisItem<N> item, String name) {
	  Preconditions.checkNotNullWithName(item, name);
	  checkSection(item.section, "item section");
	}
	
	SectionClient<N> sectionClient(Section<N> section) {
	  if (section instanceof SectionClient) return (SectionClient<N>) section;
	  for (SectionClient<N> section2: sections) {
	    if (section2.core.equals(section)) {
	      return section2;
	    }
	  }
	  return null;
	}
}