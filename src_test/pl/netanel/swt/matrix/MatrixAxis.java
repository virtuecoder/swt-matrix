package pl.netanel.swt.matrix;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.TypedListener;

import pl.netanel.swt.Resources;
import pl.netanel.swt.matrix.Move;
import pl.netanel.swt.Listeners;
import pl.netanel.util.Preconditions;

/**
 * Provides methods to operate on the matrix axises, both horizontal and vertical ones.
 * <p> 
 * Sets the scroll bar properties according to the layout state (visibility, thumb, selection)
 * Triggers layout viewport computation by the scroll bar selection event.</p>
 * 
 * @author jacek.p.kolodziejczyk@gmail.com
 * @created 2010-06-16
 */
public class MatrixAxis {
	private static final String FREEZE_ITEM_COUNT_ERROR = "Freeze item count must be greater then 0";

	protected Matrix matrix;
	public int axisIndex;
	public Layout layout;
	protected ScrollBar scrollBar;
	protected boolean resizable;
	protected boolean moveable;
	protected boolean excessLineLength;
	
	private int selectionOptions;
	boolean isSelectionEnabled, isCurrentItemEnabled;
	Color freezeHeadLineColor, freezeTailLineColor;

	final Listeners listeners;
	
	
	static MatrixAxis createVertical(Matrix matrix) {
		MatrixAxis axis = new MatrixAxis(matrix);
		axis.setScrollBar(matrix.getVerticalBar());
		axis.axisIndex = 0;
		axis.isCurrentItemEnabled = true;
		return axis;
	}
	
	static MatrixAxis createHorizontal(Matrix matrix) {
		MatrixAxis axis = new MatrixAxis(matrix);
		axis.setScrollBar(matrix.getHorizontalBar());
		axis.axisIndex = 1;
		return axis;
	}
	
	private MatrixAxis(Matrix matrix) {
		this.matrix = matrix;
		listeners = new Listeners();
		isSelectionEnabled = true;
	}

	public void setScrollBar(final ScrollBar scrollBar) {
		if (scrollBar == null) return;
		
		this.scrollBar = scrollBar;
		scrollBar.addListener(SWT.Selection, new Listener() {
			private int selection = -1;

			public void handleEvent(Event e) {
				int newSelection = MatrixAxis.this.scrollBar.getSelection();
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
				MatrixAxis.this.matrix.redraw();
			}
		});
	}
	
	protected void checkWidget() {
		matrix.checkWidget2();
	}

	public AxisModel getModel() {
		MatrixModel model = matrix.getModel();
		return axisIndex == 0 ? model.getRowModel(): model.getColumnModel();
	}
	
	/**
	 * Should be called after the model changes in a way that effects the layout,
	 * for example, the section item count, item width etc.
	 */
	public void updateLayout() {
		layout.compute();
	}
	
	 /**
	  * Computes the layout if computing is required
	  */
	void computeIfRequired() {
		if (layout.isComputingRequired) {
			layout.compute();
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

//	/**
//	 * @return the excessLineLength
//	 */
//	public boolean isLineLengthExcessive() {
//		return excessLineLength;
//	}
//	
//
//	/**
//	 * @return the lineColor
//	 */
//	public Color getLineColor() {
//		LinePainter painter = getLinePainter();
//		if (painter == null) return null;
//		return painter.getBackground();
//	}
//
//	/**
//	 * @param lineColor the lineColor to set
//	 */
//	public void setLineColor(Color lineColor) {
//		LinePainter painter = getLinePainter();
//		if (painter == null) return;
//		painter.setBackground(lineColor);
//	}
//
//	/**
//	 * @return the lineVisible
//	 */
//	public boolean isLineVisible() {
//		LinePainter painter = getLinePainter();
//		if (painter == null) return false;
//		return painter.isEnabled();
//	}
//
//	/**
//	 * @param lineVisible the lineVisible to set
//	 */
//	public void setLineVisible(boolean lineVisible) {
//		LinePainter painter = getLinePainter();
//		if (painter == null) return;
//		painter.setEnabled(lineVisible);
//	}
//
//	private LinePainter getLinePainter() {
//		Zone body = matrix.getZone(Zone.BODY);
//		Painters painters = axisIndex == 0 ? body.linePainters0 : body.linePainters1;
//		return painters.get(LinePainter.class);
//	}
//
//	/**
//	 * @return the headerVisible
//	 */
//	public boolean isHeaderVisible() {
//		return layout.model.isVisible(layout.model.getHeader());
//	}
//
//	/**
//	 * @param show the new visibility state
//	 */
//	public void setHeaderVisible(boolean show) {
//		AxisModel model = getModel();
//		int header = model.getHeader();
//		if (!Matrix.checkSection(this, header, false)) return;
//		model.setVisible(header, show);
//		if (show) {
//			if (model.getItemCount(header).compareTo(model.getFactory().ZERO()) == 0) {
//				Index itemCount = model.getFactory().one();
//				model.setItemCount(header, itemCount);
//				layout.sections[header].order.setCount(itemCount);
//				
//				// Select in headers
//				Zone headerZone = getHeaderZone();
//				if (headerZone.isSelectionEnabled()) {
//					CellSet selection = matrix.getZone(Zone.BODY).cellSelection;
//					ArrayList<Extent> items = axisIndex == 0 ? 
//							selection.items0 : selection.items1;
//					for (Extent extent: items) {
//						if (axisIndex == 0) {
//							matrix.selectInZone(headerZone, 
//								extent.getStart(), extent.getEnd(),
//								matrix.layout1.ZERO(), matrix.layout1.ZERO());
//						} else {
//							matrix.selectInZone(headerZone, 
//								matrix.layout0.ZERO(), matrix.layout0.ZERO(), 
//								extent.getStart(), extent.getEnd());
//						}
//					}
//				}
//			}
//			layout.head.count++;
//		}
//		else {
//			layout.head.count--;
//		}
//		layout.isComputingRequired = true;
//	}
//	
//
//	/**
//	 * Returns the Zone.COLUMN_HEADER zone if the receiver is a column axis and 
//	 * Zone.ROW_HEADERheader zone if the receiver is a row axis.
//	 * @return the header zone
//	 */
//	public Zone getHeaderZone() {
//		return matrix.getZone(axisIndex == 0 ? Zone.ROW_HEADER : Zone.COLUMN_HEADER);
//	}

	

	/*------------------------------------------------------------------------
	 * Selection 
	 */

//	/**
//	 * Returns true if item selection is enabled, false otherwise.
//	 * @return
//	 */
//	public boolean isSelectionEnabled() {
//		return isSelectionEnabled;
//	}
//	
//	/**
//	 * Enables axis item selection if the argument is true.
//	 * 
//	 * @param enabled
//	 */
//	public void setSelectionEnabled(boolean enabled) {
//		isSelectionEnabled = enabled;
//	}
	

	/**
	 * Sets the selection options combined with the | operator, including:<ul>
	 * <li> SWT.FULL_SELECTION option enforces whole row or column selection,
	 * <li> SWT.SINGLE option enforces only single item selection.
	 * </ul>
	 * <p>
	 * If the SWT.Selection is set then the header zone selection is disabled.
	 */
	public void setSelectionOptions(int options) {
		this.selectionOptions |= options;
		getHeaderZone().setSelectionEnabled(false);
		matrix.selectCurrentCell();
	}

	/**
	 * Returns true if the given bit is set or false otherwise.
	 * 
	 * @param option
	 * @return
	 * @see setSelectionMode()
	 */
	public boolean hasSelectionOption(int option) {
		return (selectionOptions & option) != 0;
	}
	
	/**
	 * Returns true if the given axis item is selected, and false otherwise. 
	 * Items out of range are ignored.
	 * <p>
	 * If cell selection is disabled it always returns false.
	 * @param item axis item
	 * @return the selection state of the given item 
	 */
	public boolean isSelected(AxisItem item) {
		if (!isSelectionEnabled) return false;
		
		// Check section index bounds
		int sectionCount = layout.model.getSectionCount();
		if (item == null || item.section < 0 || item.section >= sectionCount) {
			return false;
		}
		
		return layout.sections[item.section].selection.contains(item.index);
	}
	
//	public Index getSelectionCount(int section) {
//		return layout.sections[section].selection.getCount();
//	}
//	
//	public AxisItemSequence getSelection(int section) {
//		return new AxisItemSequence(layout.sections[section].selection.copy());
//	}
//	
	
	
//	/**
//	 * Selects the given axis item in the receiver. If the item was already selected, 
//	 * it remains selected. Axis items that are out of range are ignored.
//	 * 
//	 * @param item axis item to select.
//	 * @see AxisItem
//	 */
//	public void select(AxisItem item) {
//		select(item, item);
//	}

	/**
	 * Selects the axis items between start and end item inclusively. 
	 * If the items were already selected, they remains selected. 
	 * Axis items that are out of range are ignored.
	 * 
	 * @param start
	 * @param end
	 */
	public void select(AxisItem start, AxisItem end) {
		AxisItem[] range = adjustRange(start, end);
		if (range == null) return;
		
		selectInternal(range[0], range[1], true, true);
		matrix.redraw();
	}
	
	public void selectAlso(AxisItem start, AxisItem end) {
		AxisItem[] range = adjustRange(start, end);
		if (range == null) return;
		
		selectInternal(range[0], range[1], false, true);
		matrix.redraw();
	}
	
	/**
	 * Select without argument adjusting
	 * @param start
	 * @param end
	 * @param selectInMatrix 
	 */
	void selectInternal(AxisItem start, AxisItem end, boolean deselect, boolean selectInMatrix) {
		if (deselect) deselectAll(true);
		
		// Make sure start < end 
		if (layout.comparePosition(start, end) > 0) {
			AxisItem tmp = start; start = end; end = tmp;
		}
		
		for (int section = start.section; section <= end.section; section++) {
			Index startIndex = section == start.section ? start.index : layout.ZERO();
			Index endIndex = section == end.section ? end.index : layout.lastIndex(section);
			layout.sections[section].select(startIndex, endIndex);
		}
		
		// Cell selection in the matrix
		if (selectInMatrix && !layout.model.isEmpty()) {
			AxisLayout layout2 = axisIndex == 0 ? matrix.layout1 : matrix.layout0;
			AxisItem lastItem = layout2.lastItem();
			if (axisIndex == 0) {
				matrix.selectInZones(start, end, layout2.firstItem(), lastItem);
			} else {
				matrix.selectInZones(layout2.firstItem(), lastItem, start, end);
			}
		}
	}
	
	public void deselect(AxisItem start, AxisItem end) {
		AxisItem[] range = adjustRange(start, end);
		if (range == null) return;
		
		// Deselect in sections
		for (int section = start.section; section <= end.section; section++) {
			Index startIndex = section == start.section ? start.index : layout.ZERO();
			Index endIndex = section == end.section ? end.index : layout.lastIndex(section);
			if (endIndex != null) {
				layout.sections[section].deselect(startIndex, endIndex);
			}
		}
		
		// Cell deselection in the matrix
		if (!layout.model.isEmpty()) {
			AxisLayout layout2 = axisIndex == 0 ? matrix.layout1 : matrix.layout0;
			if (axisIndex == 0) {
				matrix.deselectInternal(
					range[0], range[1], layout2.firstItem(), layout2.lastItem());
			} else {
				matrix.deselectInternal(
					layout2.firstItem(), layout2.lastItem(), range[0], range[1]);
			}
		}

		matrix.redraw();
	}
	
	private AxisItem[] adjustRange(AxisItem start, AxisItem end) {
		if (!isSelectionEnabled) return null;

		if (start == null || end == null /*|| start.section > end.section*/) return null;
		
		AxisItem start2 = adjustItem(start);
		AxisItem end2 = adjustItem(end);
		
		if (start2.compareTo(end2) == 0 && 
			start2.compareTo(start) != 0 && end2.compareTo(end) != 0) return null;
		
		return new AxisItem[] {start2, end2};
	}
	
	/**
	 * Return the closest item if the given item is out of scope,
	 * otherwise returns the same item.
	 * @param item
	 * @return
	 */
	AxisItem adjustItem(AxisItem item) {
		int section = -1;
		Index index = null;
		
		// Section
		if (item.section < 0) section = 0;
		else {
			int sectionCount = layout.model.getSectionCount();
			if (item.section >= sectionCount) {
				section = sectionCount - 1;
			} else {
				section = item.section;
			}
		}
		// Index
		if (item.index.compareTo(layout.ZERO()) < 0) {
			index = layout.factory.zero();
		} else {
			Index itemCount = layout.model.getItemCount(section);
			if (item.index.compareTo(itemCount) >= 0 || section < item.section) {
				index = layout.factory.decrement(itemCount);
			} else {
				index = item.index;
			}
		}
		
		return section == item.section && index == item.index 
			? item
			: new AxisItem(section, index);
	}

	
	/**
	 * Selects all of the items in the receiver.
	 * <p>
	 * If the receiver is single-select, do nothing. 
	 */
	public void selectAll() {
		if (!isSelectionEnabled || (matrix.getStyle() & SWT.SINGLE) != 0) return;
		
		AxisItem firstItem = layout.firstItem();
		AxisItem lastItem = layout.lastItem();
		if (firstItem == null || lastItem == null) return;
		selectInternal(firstItem, lastItem, true, true);
		matrix.redraw();
	}
	
	/**
	 * Deselects all selected items in the receiver.
	 */
	public void deselectAll() {
		deselectAll(true);
		matrix.redraw();
	}
	
	void deselectAll(boolean deselectInMatrix) {
		if (!isSelectionEnabled) return;
		
		if (deselectInMatrix) matrix.deselectAllInternal();
//		// For each section selection
//		AxisLayout layout2 = axisIndex == 0 ? matrix.layout1 : matrix.layout0;
//		AxisItem firstItem = layout2.firstItem();
//		AxisItem lastItem = layout2.lastItem();
//		boolean empty = layout.model.isEmpty();
		
		int len = getModel().getSectionCount();
		for (int section = 0; section < len; section++) {
			Section sl = layout.sections[section];
			
//			// Clear cells for selected items in the matrix
//			if (deselectInMatrix && !empty) {
//				for (Extent extent: sl.selection.items) {
//					if (axisIndex == 0) {
//						matrix.deselectInternal(
//							new AxisItem(section, extent.getStart()), 
//							new AxisItem(section, extent.getEnd()), 
//							firstItem, lastItem);
//					} else {
//						matrix.deselectInternal(
//							firstItem, lastItem, 
//							new AxisItem(section, extent.getStart()), 
//							new AxisItem(section, extent.getEnd())); 
//					}
//				}
//			}
			
			sl.selection.clear();
		}
	}
	
	/*------------------------------------------------------------------------
	 * Hiding 
	 */

	void hide(boolean b) {
		int len = getModel().getSectionCount();
		for (int section = 0; section < len; section++) {
			layout.sections[section].hide(b);
		}
		
		layout.compute();
		layout.adjustHiddenHiddenItem();
		matrix.redraw();
	}
	
//	public void reorder(IndexList indexes, AxisItem target) {
//		matrix.ensureModelExist();
//		layout.sections[target.section].;
//		layout.reorder(target);
//	}



	/*------------------------------------------------------------------------
	 * Freezing 
	 */

	/**
	 * Gets freeze head item count.
	 * @return the freezeEnd
	 */
	public int getFreezeHead() {
		return layout.head.count;
	}
	
	/**
	 * Sets freeze head item count.
	 * Requires recomputing.
	 * @param freeezeStartItemCount the freezeStart to set
	 */
	public void setFreezeHead(int freezeItemCount) {
		Preconditions.checkArgument(freezeItemCount >= 0, FREEZE_ITEM_COUNT_ERROR);
		layout.computingRequiredIf(layout.head.count != freezeItemCount);
		layout.head.count = freezeItemCount;
	}

	/**
	 * Gets freeze tail item count.
	 * @return the freezeEnd
	 */
	public int getFreezeTail() {
		return layout.tail.count;
	}
	
	/**
	 * Sets freeze tail item count
	 * Requires recomputing.
	 * @param tail.count the freezeEnd to set
	 */
	public void setFreezeTail(int freezeItemCount) {
		Preconditions.checkArgument(freezeItemCount >= 0, FREEZE_ITEM_COUNT_ERROR);
		layout.computingRequiredIf(layout.tail.count != freezeItemCount);
		layout.tail.count = freezeItemCount;
	}
	
	public void setFreezeHeadLineWidth(int width) {
		Preconditions.checkNotNull(width, "Width must greater or equal to 0");
		layout.computingRequiredIf(layout.head.freezeLineWidth != width);
		layout.head.freezeLineWidth = width;
	}

	public void setFreezeTailLineWidth(int width) {
		Preconditions.checkNotNull(width, "Width must greater or equal to 0");
		layout.computingRequiredIf(layout.tail.freezeLineWidth != width);
		layout.tail.freezeLineWidth = width;
		
	}
	
	public int getFreezeHeadLineWidth() {
		return layout.head.freezeLineWidth;
	}
	
	public int getFreezeTailLineWidth() {
		return layout.tail.freezeLineWidth;
	}
	
	public void setFreezeHeadLineColor(final Color color) {
		Preconditions.checkNotNull(color, "Color cannot be null");
		layout.computingRequiredIf(color.equals(freezeHeadLineColor));
		this.freezeHeadLineColor = color;
	}
	
	public void setFreezeTailLineColor(final Color color) {
		Preconditions.checkNotNull(color, "Color cannot be null");
		layout.computingRequiredIf(color.equals(freezeTailLineColor));
		this.freezeTailLineColor = color;
	}

	public Color getFreezeHeadLineColor() {
		return freezeHeadLineColor;
	}
	
	public Color getFreezeTailLineColor() {
		return freezeTailLineColor;
	}

	
	public void pack(int section, Index index) {
		MatrixModel model = matrix.getModel();
		AxisModel thisModel = getModel();
		AxisModel model0 = model.getRowModel();
		AxisModel model1 = model.getColumnModel();
		
		int w = 0;
		
		Cursor cursor = matrix.getCursor();
		GC gc = new GC(matrix.getDisplay());
		try {
			matrix.setCursor(Resources.getCursor(SWT.CURSOR_WAIT));
			if (axisIndex == 0) {
				int section0 = section;
				AxisItem item0 = new AxisItem(section0, index);
				for (int section1 = 0; section1 < model1.getSectionCount(); section1++) {
					Zone zone = matrix.getZone(section0, section1);
					ItemPairSequence seq = new ItemPairSequence(model, item0, item0, 
							new AxisItem(section1, model1.getFactory().zero()), 
							new AxisItem(section1, model1.getItemCount(section1)));
					for (Painter painter: zone.cellPainters) {
						painter.init(gc);
						SizeMeter meter = painter.getSizeMeter();
						if (meter != null) {
							w = Math.max(w, meter.measureHeight(seq));
						}
					}
				}
			} else {
				int section1 = section;
				AxisItem item1 = new AxisItem(section1, index);
				for (int section0 = 0; section0 < model0.getSectionCount(); section0++) {
					Zone zone = matrix.getZone(section0, section1);
					ItemPairSequence seq = new ItemPairSequence(model, 
							new AxisItem(section0, model0.getFactory().zero()), 
							new AxisItem(section0, model0.getItemCount(section0)), 
							item1, item1);
					for (Painter painter: zone.cellPainters) {
						painter.init(gc);
						SizeMeter meter = painter.getSizeMeter();
						if (meter != null) {
							w = Math.max(w, meter.measureWidth(seq));
						}
					}
				}
			}
			
		} finally {
			matrix.setCursor(cursor);
			gc.dispose();
		}
		if (w != 0) thisModel.setCellWidth(section, index, index, w);
		layout.compute();
		matrix.redraw();
	}

	/**
	 * Returns true if the current item is enabled, false otherwise.
	 * Current item is used for navigation.
	 * @return
	 */
	public boolean isCurrentItemEnabled() {
		return isCurrentItemEnabled;
	}
	
	/**
	 * Enables current item navigation if the argument is true, 
	 * and disables it otherwise.
	 * @param enabled
	 */
	public void setCurrentItemEnabled(boolean enabled) {
		isCurrentItemEnabled = enabled;
	}

	/**
	 * Return the current axis item
	 * If current item is not enabled then always returns null.
	 * @return
	 */
	public AxisItem getCurrentItem() {
		if (!isCurrentItemEnabled) return null;
		layout.recompute();
		return layout.current == null ? null : layout.current.copy();
	}
	
	public void setCurrentItem(AxisItem item) {
		if (!isCurrentItemEnabled) return; 
		Matrix.checkItem(this, item, false, true);
		layout.setCurrentItem(item);
		matrix.selectCurrentCell();
	}


	/**
	 * Adds the listener to the collection of listeners who will
	 * be notified when the axis item is moved or resized, by sending
	 * it one of the messages defined in the <code>ControlListener</code>
	 * interface.
	 *
	 * @param listener the listener which should be notified
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 * </ul>
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
	 * </ul>
	 *
	 * @see ControlListener
	 * @see #removeControlListener
	 */
	public void addControlListener(ControlListener listener) {
		checkWidget();
		TypedListener typedListener = new TypedListener(listener);
		listeners.add(SWT.Resize, typedListener);
		listeners.add(SWT.Move, typedListener);
	}
	
	/**
	 * Adds the listener to the collection of listeners who will
	 * be notified when the axis item is selected by the user, by sending
	 * it one of the messages defined in the <code>SelectionListener</code>
	 * interface. 
	 * <p>
	 * The selection event is not emitted by the axis API methods that are
	 * responsible for selection and deselection of items. It can only be 
	 * triggered by another SWT event bound to the selection command.
	 * </p> 
	 * <p>
	 * <code>widgetSelected</code> is called when the axis item is selected
	 * <code>widgetDefaultSelected</code> is not called.
	 * </p>
	 *
	 * @param listener the listener which should be notified when the axis item 
	 * is selected by the user
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 * </ul>
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
	 * </ul>
	 *
	 * @see SelectionListener
	 * @see #removeSelectionListener
	 * @see SelectionEvent
	 */
	public void addSelectionListener (SelectionListener listener) {
		checkWidget ();
		Preconditions.checkNotNullWithName(listener, "listener");
		TypedListener typedListener = new TypedListener(listener);
		listeners.add(SWT.Selection, typedListener);
		listeners.add(SWT.DefaultSelection, typedListener);
	}

	/**
	 * Removes the listener from the collection of listeners who will
	 * be notified when the control is moved or resized.
	 *
	 * @param listener the listener which should no longer be notified
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 * </ul>
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
	 * </ul>
	 *
	 * @see ControlListener
	 * @see #addControlListener
	 */
	public void removeControlListener (ControlListener listener) {
		checkWidget ();
		Preconditions.checkNotNullWithName(listener, "listener");
		listeners.remove(SWT.Move, listener);
		listeners.remove(SWT.Resize, listener);
	}

	/**
	 * Removes the listener from the collection of listeners who will
	 * be notified when the control is selected by the user.
	 *
	 * @param listener the listener which should no longer be notified
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 * </ul>
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
	 * </ul>
	 *
	 * @see SelectionListener
	 * @see #addSelectionListener
	 */
	public void removeSelectionListener(SelectionListener listener) {
		checkWidget ();
		Preconditions.checkNotNullWithName(listener, "listener");
		listeners.remove(SWT.Selection, listener);
		listeners.remove(SWT.DefaultSelection, listener);
	}

	/**
	 * Moves the items enclosed by start end end before the target item. 
	 * In order to move after the last item make target index equal to item count of the section.
	 * Start, end and target items must be from the same section. 
	 * Items cannot be moved from one section to another.   
	 * 
	 * @param start start of the items range
	 * @param end start of the items range
	 * @param target
	 */
	public void reorder(int section, IndexQueueSet subject, Index target) {
		Preconditions.checkPositionIndex(section, layout.sections.length, "section");
		layout.sections[section].order.move(subject, target);
		layout.isComputingRequired = true;
	}

//	public Section getSection(int sectionIndex) {
//		matrix.ensureModelExist();		
//		Preconditions.checkPositionIndex(sectionIndex, layout.sections.length, "sectionIndex");
//		return layout.sections[sectionIndex];
//	}	
}