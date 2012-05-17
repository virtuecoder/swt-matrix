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
 * @param <N> specifies the indexing class for the receiver
 * @see Section
 *
 * @author Jacek
 * @created 27-03-2011
 */
public class Axis<N extends Number>  {
	private static final String FREEZE_ITEM_COUNT_ERROR = "Freeze item count cannot be negative";

	Math<N> math;
	ArrayList<SectionClient<N>> sections;

	AxisLayout<N> layout;

	char symbol;
	Matrix<? extends Number, ? extends Number> matrix;
	ScrollBar scrollBar;

  private boolean scrollBarVisible;

	/**
	 * Constructs axis indexed by Integer class with two sections.
	 * @see #Axis(Class, int)
	 */
	@SuppressWarnings("unchecked")
	public Axis() {
		init((Class<N>) Integer.class, 2, 0, 1);
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

  private void init(Class<N> numberClass, int sectionCount, int headerIndex, int bodyIndex) {
    math = Math.getInstance(numberClass);

    layout = new AxisLayout<N>(numberClass, sectionCount, headerIndex, bodyIndex);


	  sections = new ArrayList<SectionClient<N>>(sectionCount);
	  for (int i = 0; i < sectionCount; i++) {
	    SectionCore<N> section = layout.sections.get(i);
//	    Preconditions.checkArgument(
//	      section.getIndexClass().equals(numberClass),
//	      "Section at %s position is indexed by a different Number subclass " +
//	        "then the first section: %s != %s",
//	        i, section.getIndexClass(), numberClass);

	    section.index = i;
      section.axis = this;
      sections.add(new SectionClient<N>(section));
	  }
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
		return layout.body == null ? null : layout.body.client;
	}

	/**
	 * Returns the header section, which is by default the first section of the axis.
	 * Another section can be set as header by specifying <code>headerIndex</code> argument
	 * in the Axis constructor.
	 * @return the header section.
	 * @see #getBody()
	 */
	public Section<N> getHeader() {
	  return layout.header == null ? null : layout.header.client;
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
   * Returns the number of sections in the receiver.
   * @return the number of sections in the receiver.
   */
  public int getSectionCount() {
  	return sections.size();
  }

  /**
	 * Returns number of items visible in the viewport. Including partially visible items.
	 * @return number of items visible in the viewport
	 */
	public int getViewportItemCount() {
		layout.computeIfRequired();
		return layout.head.count + layout.tail.count +
			layout.main.cells.size(); // - layout.trim;
	}


	/**
   * Returns the position of the given item in the sequence of items
   * visible in the viewport or -1 if the viewport does not display the item.
   *
   * @param item the item to get position for
   * @return the position of the given item in the viewport
   * @throws IllegalArgumentException if item is <code>null</code> or item's section
   * 		does not belong to this axis.
   * @throws IndexOutOfBoundsException if item's index is out
   * 		of 0 ... {@link SectionCore#getCount()}-1 bounds
   */
  public int getViewportPosition(AxisItem<N> item) {
  	checkItem(item, "item");
  	layout.computeIfRequired();
  	return layout.indexOf(item);
  }

  /**
	 * Returns the item visible at the specified position in the viewport or <code>null</code>
	 * if the position is outside of the viewport bounds.
	 *
	 * @param position the position the get the item for
	 * @return the item visible at the specified position in the viewport
	 */
	public AxisItem<N> getItemByViewportPosition(int position) {
		AxisItem<N> item = layout.getIndexAt(position);
		return item == null ? null : item;
	}

  /**
   * Returns the item visible at the specified distance from the beginning of
   * viewport area or <code>null</code> if the distance is outside of the
   * viewport bounds.
   *
   * @param position the position the get the item for
   * @return the item visible at the specified distance
   */
	public AxisItem<N> getItemByViewportDistance(int distance) {
		AxisItem<N> item = layout.getItemByDistance(distance);
		return item == null ? null : item;
	}

	/**
	 * Returns the item visible at the specified offset from the item specified
	 * by the given section and index or <code>null</code> if the such an item
	 * is outside of the viewport bounds.
	 * <p>
	 * If the <code>offset</code> is positive the item to returned will be computed
	 * in the forward direction. Otherwise item in the backward direction will
	 * be returned.
   *
   * @param item the item to set the focus for
   * @param offset number of items to move away from the referenced item
   * @throws IllegalArgumentException if item is <code>null</code> or item's
   *           section does not belong to this axis.
   * @throws IndexOutOfBoundsException if item's index is out of 0 ...
   *           {@link SectionCore#getCount()}-1 bounds
   */
  public AxisItem<N> getItemByViewportOffset(AxisItem<N> item, int offset) {
    checkItem(item, "item");
    AxisItem<N> nextItem = layout.nextItem(item,
      math.create(java.lang.Math.abs(offset)),
      offset > 0 ? layout.forward : layout.backward);

    return layout.contains(nextItem) ? nextItem : null;
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
   * Compares positions of sections on this axis and returns value greater then 0 if
   * section1 is behind section2, value lower then zero if section1 is before
   * section2 and 0 if sections are the same.
   *
   * @param section1 a section to compare
   * @param section2 another section to compare
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

  /**
   * Compares positions of items on this axis and returns value greater then 0 if
   * item1 is behind item2, value lower then zero if item1 is before
   * item2 and 0 if items are the same.
   *
   * @param item1 one of the items to compare
   * @param item2 another item to compare
   * @return comparison result
   *
   * @throws IllegalArgumentException if item1 or item2 is <code>null</code> or item's
   *           section does not belong to this axis.
   * @throws IndexOutOfBoundsException if index of item1 or item2 is out of 0 ...
   *           {@link SectionCore#getCount()}-1 bounds
   */
  public int compare(AxisItem<N> item1, AxisItem<N> item2) {
    checkItem(item1, "item1");
    checkItem(item2, "item2");
    return layout.comparePosition(item1, item2);
  }

  /**
   * Returns <code>true</code> if the focus item navigation is enabled in the receiver.
   * Otherwise <code>false</code> is returned.
   *
   * @return the receiver's focus item enablement state
   */
  public boolean isFocusItemEnabled() {
    return layout.isFocusItemEnabled;
  }

  /**
   * Enables current item navigation in the receiver if the argument is <code>true</code>,
   * and disables it otherwise.
   * <p>
   * If the focus cell is disabled the navigation events are ignored and the
   * {@link Painter#NAME_FOCUS_CELL} painter of the matrix is disabled.
   *
   * @param state the new focus item enablement state
   */
  public void setFocusItemEnabled(boolean state) {
    layout.isFocusItemEnabled = state;
    matrix.setFocusCellEnabled(state);
  }

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
   * Sets the focus marker to the given item.
   * <p>
   * If section has the focus item disabled (see
   * {@link SectionCore#setFocusItemEnabled(boolean)}) then this method does
   * nothing.
   *
   * @param item the item to set the focus for
   * @throws IllegalArgumentException if item is <code>null</code> or item's
   *           section does not belong to this axis.
   * @throws IndexOutOfBoundsException if item's index is out of 0 ...
   *           {@link SectionCore#getCount()}-1 bounds
   */
	public void setFocusItem(AxisItem<N> item) {
	  checkItem(item, "item");
		layout.setFocusItem(item);
		if (matrix != null) matrix.redraw();
	}

	/**
	 * Scrolls to the given making it visible in the viewport.
	 * <p>
	 * It works only when the matrix size has been set, which usually happens
	 * when the shell to which the matrix belongs gets open.
	 *
   * @param item the item to show
   * @throws IllegalArgumentException if item is <code>null</code> or item's
   *           section does not belong to this axis.
   * @throws IndexOutOfBoundsException if item's index is out of 0 ...
   *           {@link SectionCore#getCount()}-1 bounds
	 */
	public void showItem(AxisItem<N> item) {
	  checkItem(item, "item");

	  if (layout.show(item)) {
	    scroll();
	    if (matrix != null) matrix.redraw();
	  }
	}




	/*------------------------------------------------------------------------
	 * Freeze
	 */


  /**
	 * Freezes the specified amount of items at the beginning this axis.
	 * @param amount of items to freeze
	 * @throws IllegalArgumentException if the argument is lower then zero
	 */
	public void setFrozenHead(int amount) {
		Preconditions.checkArgument(amount >= 0, FREEZE_ITEM_COUNT_ERROR);
		layout.freezeHead(amount);
	}

	/**
   * Returns the amount of items frozen at the beginning this axis.
   * @return the amount of items frozen at the beginning this axis
   */
  public int getFrozenHead() {
    return layout.head.count;
  }

  /**
	 * Freezes the specified amount of last items at the end of this axis.
	 * @param amount of items to freeze
	 * @throws IllegalArgumentException if the argument is lower then zero
	 */
	public void setFrozenTail(int amount) {
		Preconditions.checkArgument(amount >= 0, FREEZE_ITEM_COUNT_ERROR);
		layout.freezeTail(amount);
	}

	/**
	 * Returns the amount of items frozen at the end this axis.
	 * @return the amount of items frozen at the end this axis
	 */
	public int getFrozenTail() {
	  return layout.tail.count;
	}


	/*------------------------------------------------------------------------
	 *
	 */

	/**
	 * Returns the offset from the edge of scrolling area within which dragging causes
	 * the content to scroll automatically and extend the dragged distance.
	 * The default value is 8 for horizontal and 6 for vertical axis.
	 */
	public int getAutoScrollOffset() {
		return layout.autoScrollOffset;
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
		layout.autoScrollOffset = offset;
	}

	/**
	 * Returns the offset from the dividing line within which dragging changes the width of the axis item.
	 * The default value is 3 for horizontal and 2 for vertical axis.
	 */
	public int getResizeOffset() {
		return layout.resizeOffset;
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
		layout.resizeOffset = offset;
	}

	/**
	 * Returns the minimal cell width to be achieved by a user driven cell resizing.
	 * @return the minimal cell width to be achieved by a user driven cell resizing
	 */
	public int getMinimalCellWidth() {
	  return layout.minimalCellWidth;
	}

	/**
	 * Sets the minimal cell width to be achieved by a user driven cell resizing.
	 * <p>
	 * The default cell size of any section in this axis lower then minimal cell width
	 * is increased to then minimal cell width.
	 * @param width new minimal width to set
	 */
	public void setMinimalCellWidth(int minimalCellWidth) {
	  layout.minimalCellWidth = minimalCellWidth;
	  for (Section<N> section: sections) {
	    if (section.getDefaultCellWidth() < minimalCellWidth) {
	      section.setDefaultCellWidth(minimalCellWidth);
	    }
	  }
	}


	/*------------------------------------------------------------------------
	 * Non public
	 */


  void setMatrix(final Matrix<?, ?> matrix, char symbol) {
	  this.symbol = symbol;
		this.matrix = matrix;

		scrollBar = symbol == 'Y' ? matrix.getVerticalBar() : matrix.getHorizontalBar();
		if (scrollBar != null) {
			scrollBar.addListener(SWT.Selection, new Listener() {

				@Override public void handleEvent(Event e) {
					int newSelection = scrollBar.getSelection();
//					selection = newSelection;
					//				debugSWT(e.detail);

					Move move =
						e.detail == SWT.ARROW_DOWN 	? Move.NEXT :
						e.detail == SWT.ARROW_UP 	  ? Move.PREVIOUS :
						e.detail == SWT.PAGE_DOWN 	? Move.NEXT_PAGE:
						e.detail == SWT.PAGE_UP 	  ? Move.PREVIOUS_PAGE:
					  newSelection == scrollBar.getMinimum() ? Move.HOME :
				    newSelection == scrollBar.getMaximum() - scrollBar.getThumb() ? Move.END :
								Move.NULL;

					if (layout.setScrollPosition(newSelection, move)) {
					  scrollBar.setThumb(layout.getScrollThumb());
					  scrollBar.setSelection(layout.getScrollPosition());
//					  TestUtil.log(move, newSelection, layout.getScrollPosition(), layout.getScrollThumb());
					  matrix.redraw();
					}
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
		if (scrollBarVisible != scrollBar.getVisible()) {
//		  System.out.println("was visible: " + scrollBarVisible);
//		  System.out.println(layout.isScrollRequired());
		}
		scrollBarVisible = scrollBar.getVisible();
		scrollBar.setVisible(layout.isScrollRequired());
		return scrollBarVisible != scrollBar.isVisible();
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

//		System.out.println("update " + matrix.getDisplay().getCursorLocation().x);

		layout.setViewportSize(size);
		int min = layout.getScrollMin();
		int max = layout.getScrollMax();
		int thumb = layout.getScrollThumb();
		if (thumb == max) thumb = max-1;
		if (thumb == 0) thumb = 1;
		// Extend the maximum to show the last trimmed element
		if (thumb < 1) {
			thumb = 1;
			max += layout.trim;
		}
		scrollBar.setValues(layout.getScrollPosition(), min, max, thumb, 1, thumb);
	}


	void setSelected(AxisItem<N> start, AxisItem<N> end, boolean select) {
		// Make sure start < end
		if (layout.comparePosition(start, end) > 0) {
			AxisItem<N> tmp = start; start = end; end = tmp;
		}

    AxisExtentSequence<N> seq = new AxisExtentSequence<N>(math, layout.sections);
    for (seq.init(start, end); seq.next();) {
      seq.section.setSelected(seq.start, seq.end, select, true);
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
			return AxisItem.createInternal(section, math.decrement(section.getCount()));
		}
		return getFirstItem();
	}

	AxisItem<N> getFirstItem() {
		return AxisItem.createInternal(layout.sections.get(0), math.ZERO_VALUE());
	}

  boolean isLastCellTrimmed() {
  	layout.computeIfRequired();
  	return layout.isTrimmed;
  }


	/**
	 * Sets the hidden state of selected indexes in each section.
	 * To be called from UI only.
	 * @param state new hiding state
	 */
	void setHidden(boolean state) {
		for (int i = 0, imax = sections.size(); i < imax; i++) {
			SectionCore<N> section = layout.sections.get(i);
			ArrayList<MutableExtent<N>> items = section.selection.items;
      for (int j = 0, jmax = items.size(); j < jmax; j++) {
	      MutableExtent<N> e = items.get(j);
	      section.setHidden(e.start(), e.end(), state);
	    }
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
		private AxisItem<N> startItem, endItem;

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
		Bound bound = layout.getCellBound(AxisItem.createInternal(section, index));
		return bound == null ? null : bound.copy();
	}

	Bound getLineBound(SectionCore<N> section, N index) {
		if (section == null || index == null) return null;
		Bound bound = layout.getLineBound(AxisItem.createInternal(section, index));
		return bound == null ? null : bound.copy();
	}



	int[] getZOrder() {
		// Calculate z-order
		int[] order = new int[sections.size()];
		int j = 0;
		int bodyIndex = sections.indexOf(layout.body.client);
		for (int i = bodyIndex, imax = this.sections.size(); i < imax; i++) {
			order[j++] = i;
		}
		for (int i = bodyIndex; i-- > 0;) {
			order[j++] = i;
		}
		return order;
	}

  void deleteInZones(SectionCore<N> section, N start, N end) {
    if (symbol == 'X') {
      matrix.deleteInZonesX(section, start, end);
    } else {
      matrix.deleteInZonesY(section, start, end);
    }
    if (layout.current.section.equals(section) &&
      layout.math.contains(start, end, layout.current.getIndex())) {
      layout.ensureCurrentIsValid();
    }
	}

  void insertInZones(SectionCore<N> section, N target, N count) {
    if (symbol == 'X') {
      matrix.insertInZonesX(section, target, count);
    } else {
      matrix.insertInZonesY(section, target, count);
    }
    layout.show(AxisItem.createInternal(section, target));
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