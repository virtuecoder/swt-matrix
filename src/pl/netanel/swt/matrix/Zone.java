package pl.netanel.swt.matrix;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TypedListener;

import pl.netanel.swt.matrix.Layout.LayoutSequence;
import pl.netanel.util.ImmutableIterator;
import pl.netanel.util.Preconditions;


/**
 * Constitutes a region of a matrix where a section from the row axis 
 * and a section from the column axis intersect with each other.  
 * <p>
 * Zone has painters to paint itself on the screen.
 * </p><p>
 * </p>
 * 
 * @param <N0> defines indexing type for rows
 * @param <N1> defines indexing type for columns
 * @see Section
 * 
 * @author Jacek
 * @created 13-10-2010
 */
public class Zone<N0 extends Number, N1 extends Number> {
	
	final Painters<N0, N1> painters;
	Section<N0> section0;
	Section<N1> section1;
	SectionClient<N0> sectionClient0;
	SectionClient<N1> sectionClient1;
	CellSet cellSelection;
	CellSet lastSelection; // For adding selection
	ZoneEditor<N0, N1> editor;
	
	final Listeners listeners;
	final ArrayList<GestureBinding> bindings;
	boolean selectionEnabled;
	
	private Matrix<N0, N1> matrix;
	private Color selectionBackground, selectionForeground;
	private final Rectangle bounds;
	
	private CellValues<N0, N1, Color> background, foreground;
	
	
//	private CellValues<N0, N1, String> text;
//	private CellValues<N0, N1, Image> image;
//	private boolean backgroundEnabled, foregroundEnabled;
	
	/**
	 * Constructs zone at intersection of the specified sections.
	 * 
	 * @param section0 section of the row axis
	 * @param section1 section of the column axis
	 */
	public Zone(Section<N0> section0, Section<N1> section1) {
		this();
		this.section0 = section0 instanceof SectionClient ? ((SectionClient) section0).core : section0;
		this.section1 = section1 instanceof SectionClient ? ((SectionClient) section1).core : section1;
		cellSelection = new CellSet(section0.math, section1.math);
		lastSelection = new CellSet(section0.math, section1.math);
		
		RGB selectionColor = Resources.getColor(SWT.COLOR_LIST_SELECTION).getRGB();
		RGB whiteColor = Resources.getColor(SWT.COLOR_LIST_BACKGROUND).getRGB();
		RGB color = Painter.blend(selectionColor, whiteColor, 40);
		selectionBackground = Resources.getColor(color);
		
		background = new MapValueToCellSet(this.section0.math, this.section1.math);
		foreground = new MapValueToCellSet(this.section0.math, this.section1.math);
//		text = new MapValueToCellSet(this.section0.math, this.section1.math);
//		image = new MapValueToCellSet(this.section0.math, this.section1.math);
	}
	
	Zone() {
		painters = new Painters();
		listeners = new Listeners();
		bindings = new ArrayList();
		bounds = new Rectangle(0, 0, 0, 0);
		selectionEnabled = true;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ZoneClient) obj = ((ZoneClient) obj).core;
		return super.equals(obj);
	}
	
	@Override
	public String toString() {
		return section0.toString() + " " + section1.toString();
	}
	
	/**
	 * Returns the row axis section that is unchecked.
	 * <p>
	 * Unchecked section skips argument validation checking in getters 
	 * to improve performance. 
	 * 
	 * @return the row axis section that is unchecked
	 * 
	 * @see #getSection0()
	 */
	public Section getSectionUnchecked0() {
		return section0;
	}
	/**
	 * Returns the column axis section that is unchecked.
	 * <p>
	 * Unchecked section skips argument validation checking in getters 
	 * to improve performance. 
	 * 
	 * @return the column axis section that is unchecked
	 * 
	 * @see #getSection1()
	 */
	public Section getSectionUnchecked1() {
		return section1;
	}

	/**
	 * Returns the row axis section that is checked.
	 * <p>
	 * A checked section delegates calls to an unchecked section proceeding it with an
	 * argument validation checking.
	 * 
	 * @return the row axis section that is unchecked
	 * 
	 * @see #getSectionUnchecked0()
	 */
	public Section getSection0() {
		return matrix.axis0.sections.get(section0.index);
	}
	/**
	 * Returns the column axis section that is checked.
	 * <p>
	 * A checked section delegates calls to an unchecked section proceeding it with an
	 * argument validation checking.
	 * 
	 * @return the column axis section that is unchecked
	 * 
	 * @see #getSectionUnchecked1()
	 */
	public Section getSection1() {
		return matrix.axis1.sections.get(section1.index);
	}

	
	void setDefaultBodyStyle() {
		Painter painter = new Painter("cells", Painter.SCOPE_CELLS_HORIZONTALLY) {
			@Override
			public String getText(Number index0, Number index1) {
				return index0.toString() + ", " + index1.toString();
			}
		};
		painter.setMatrix(matrix);
		painter.zone = this;
		addPainter(painter);
		Color color = Resources.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW);
		addPainter(new LinePainter("row lines", Painter.SCOPE_HORIZONTAL_LINES, color ));
		addPainter(new LinePainter("column lines", Painter.SCOPE_VERTICAL_LINES, color));
	}
	
	void setDefaultHeaderStyle(Painter cellsPainte) {
		setDefaultForeground(Resources.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		setDefaultBackground(Resources.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		
		RGB selectionColor = Resources.getColor(SWT.COLOR_LIST_SELECTION).getRGB();
		RGB whiteColor = Resources.getColor(SWT.COLOR_WIDGET_BACKGROUND).getRGB();
		RGB rgb = Painter.blend(selectionColor, whiteColor, 90);
		setSelectionBackground(Resources.getColor(rgb));
		
		if (getPainterCount() == 0) {
			cellsPainte.setMatrix(matrix);
			cellsPainte.zone = this;
			addPainter(cellsPainte);
			final Color color = Resources.getColor(SWT.COLOR_WIDGET_DARK_SHADOW);
			addPainter(new LinePainter("row lines", Painter.SCOPE_HORIZONTAL_LINES, color));
			addPainter(new LinePainter("column lines", Painter.SCOPE_VERTICAL_LINES, color));
		}
	}
	
	/**
	 * Return rectangular bounds of the cell with the given coordinates.
	 * If one of the indexes is null it return null.
	 * 
	 * @param index0 index in <code>section0</code> of the cell 
	 * @param index1 index in <code>section1</code> of the cell 
	 * @return rectangular bounds of the cell with the given coordinates.
	 */
	public Rectangle getCellBounds(N0 index0, N1 index1) {
		if (index0 == null || index1 == null) return null;
		Bound b0 = section0.axis.getCellBound(section0, index0);
		Bound b1 = section1.axis.getCellBound(section1, index1);
		if (b0 != null && b1 != null) {
			return new Rectangle(b1.distance, b0.distance, b1.width, b0.width);
		}
		return null; 
	}
	
	
	/**
	 * Sets the default background color for the receiver's cells. 
	 * @param color color to set
	 */
	public void setDefaultBackground(Color color) {
		background.setDefaultValue(color);
	}
	
	/**
	 * Returns the default background color of the receiver's cells. 
	 * @return default width of lines in this 
	 */
	public Color getDefaultBackground() {
		return background.getDefaultValue();
	}	
	
	
	/**
	 * Sets the default foreground color for the receiver's cells. 
	 * @param color color to set
	 */
	public void setDefaultForeground(Color color) {
		foreground.setDefaultValue(color);
	}

	/**
	 * Returns the default foreground color of the receiver's cells. 
	 * @return default width of lines in this 
	 */
	public Color getDefaultForeground() {
		return foreground.getDefaultValue();
	}	

	
	/**
	 * Returns the rectangular boundaries of this zone. 
	 * @return the rectangular boundaries of this zone
	 */
	public Rectangle getBounds() {
		return bounds;
	}

	void setBounds(int x, int y, int width, int height) {
		bounds.x = x;
		bounds.y = y;
		bounds.width = width;
		bounds.height = height;
	}

//	
//	public boolean isVisible() {
//		return section0.isVisible() && section1.isVisible();
//	}

	
	
	
	/*------------------------------------------------------------------------
	 * Selection
	 */

	/**
	 * Sets selection foreground color for the receiver. 
	 * @param color color to set
	 */
	public void setSelectionForeground(Color color) {
		selectionForeground = color;
	}
	
	/**
	 * Returns the selection foreground color for the receiver.
	 * @return selection foreground color for the receiver
	 */
	public Color getSelectionForeground() {
		return selectionForeground;
	}
	
	/**
	 * Sets selection background color for the receiver. 
	 * @param color color to set
	 */
	public void setSelectionBackground(Color color) {
		selectionBackground = color;
	}
	
	/**
	 * Returns the selection background color for the receiver.
	 * @return selection background color for the receiver
	 */
	public Color getSelectionBackground() {
		return selectionBackground;
	}
	

	/**
     * Returns <code>true</code> if selection is enabled, false otherwise.
     * @return the selection enabled state
	 */
	public boolean isSelectionEnabled() {
		return selectionEnabled;
	}

	/**
     * Enables cell selection if the argument is <code>true</code>, 
     * or disables it otherwise.
     *
	 * @param enabled the new selection ability state.
	 */
	public void setSelectionEnabled(boolean enabled) {
		if (enabled == false) {
			cellSelection.clear();
		}
		this.selectionEnabled = enabled;
	}

	
	/**
	 * Returns <code>true</code> if the cell at given indexes is selected.
	 * Otherwise, <code>false</code> is returned.
	 * <p>
	 * <code>index0</code> and <code>index1</code> refer to the model, 
	 * not the visual position of the item on the screen
	 * which can be altered by move and hide operations. 
	 * 
	 * @param index0 row index of the cell  
	 * @param index1 column index of the cell 
	 * @return the selection state of the specified cell
	 */
	public boolean isSelected(N0 index0, N1 index1) {
		return cellSelection.contains(index0, index1);
	}
	
	/**
	 * Sets the selection state for the range of cells.
	 * <p>
	 * <code>start0</code>,<code>end0</code>, <code>start1</code> and <code>end1</code> 
	 * indexes refer to the model, not the visual position of the item on the screen
	 * which can be altered by move and hide operations.
	 *
	 * @param start0 first index of the range of row items  
	 * @param end0 last index of the range of row items  
	 * @param start1 first index of the range of column items  
	 * @param end1 last index of the range of column items  
	 * @param state the new selection state
	 */
	public void setSelected( N0 start0, N0 end0, N1 start1, N1 end1, 
			boolean state) {
		
		if (!selectionEnabled) return;
		if (state) {
			cellSelection.add(start0, end0, start1, end1);
		} else {
			cellSelection.remove(start0, end0, start1, end1);			
		}
	}
	
	/**
	 * Sets the selection state for the specified cell.
	 * <p>
	 * <code>index0</code> and <code>index1</code> refer to the model, 
	 * not the visual position of the item on the screen
	 * which can be altered by move and hide operations. 
	 * <p>
	 * Ranges of cells should be set selected by 
	 * {@link #setSelected(Number, Number, Number, Number, boolean)} 
	 * to achieve the best efficiency.
	 * 
	 * @param index0 row index of the cell  
	 * @param index1 column index of the cell 
	 * @return the selection state of the specified cell
	 * @see #setSelected(Number, Number, boolean)
	 */
	public void setSelected(N0 index0, N1 index1, boolean state) {
		setSelected(index0, index0, index1, index1, state);
	}

	/**
	 * Sets the selection state for all the cells in this zone.
	 * 
	 * @param state the new selection state
	 */ 
	public void setSelectedAll(boolean state) {
		if (!selectionEnabled) return;
		if (state) {
			cellSelection.add(
					section0.math.ZERO_VALUE(), section0.math.decrement(section0.getCount()), 
					section1.math.ZERO_VALUE(), section1.math.decrement(section1.getCount()));
		} else {
			cellSelection.clear();
			lastSelection.clear();
		}
		section0.setSelectedAll(state);
		section1.setSelectedAll(state);
	}
	
	/**
	 * Returns the number of selected cells in this zone.
	 * @return the number of selected cells in this zone
	 */
	public BigInteger getSelectedCount() {
		return cellSelection.getCount().getValue();
	}

	
	/**
	 * Returns a sequence of index pairs for selected cells. 
	 * @return a sequence of index pairs for selected cells
	 */
/*
	public NumberPairSequence<N0, N1> getSelected() {
		return new NumberPairSequence(cellSelection.copy());
	}
*/
	
	/**
	 * Returns the number of selected cells in this zone.
	 * <p>
	 * If the cell selection is disabled the it always returns a 
	 * {@link BigIntegerNumber} with zero value.
	 * 
	 * @return {@link BigIntegerNumber} with the count of selected cells
	 */
	public BigInteger getSelectionCount() {
		if (!selectionEnabled) {
			return BigInteger.ZERO;
		}
		return cellSelection.getCount().value;
	}
	

	/**
	 * Returns iterator for selected cells. First number in the array 
	 * returned by the {@link Iterator#next()} method is a
	 * row axis index, the second one is a column axis index.
	 * <p>
	 * <code>index0</code> and <code>index1</code> refer to the model, 
	 * not the visual position of the item on the screen
	 * which can be altered by move and hide operations.  
	 * <p>
	 * <strong>Warning</strong> iterating index by index over large extents 
	 * may cause a performance problem.
	 */
	public Iterator<Number[]> getSelectedIterator() {
		return new ImmutableIterator<Number[]>() {
			NumberPairSequence seq = new NumberPairSequence(cellSelection.copy());
			private boolean next;
			{
				seq.init();
			}
			@Override
			public boolean hasNext() {
				next = seq.next();
				return next;
			}

			@Override
			public Number[] next() {
				return next ? new Number[] {seq.index0(), seq.index1()} : null;
			}
		};
	}
	
	
	
	/*static class Cell<N0, N1> {
		public N0 index0;
		public N1 index1;
		public Cell(N0 index0, N1 index1) {
			this.index0 = index0;
			this.index1 = index1;
		}
	}*/

	/**
	 * Returns iterator for selected cell extents. First two numbers in the array 
	 * returned by the {@link Iterator#next()} method define start and end of a
	 * row axis extent, the second two the start and end of a column axis extent.
	 * <p>
	 * <code>index0</code> and <code>index1</code> refer to the model, 
	 * not the visual position of the item on the screen
	 * which can be altered by move and hide operations. 
	 */
	public Iterator<Number[]> getSelectedExtentIterator() {
		return new ImmutableIterator<Number[]>() {
			NumberPairSequence seq = new NumberPairSequence(cellSelection.copy());
			private boolean next;
			{
				seq.init();
			}
			@Override
			public boolean hasNext() {
				next = seq.nextExtent();
				return next;
			}

			@Override
			public Number[] next() {
				return next ? new Number[] {seq.start0(), seq.end0(), seq.start1(), seq.end1()} : null;
			}
		};
	}
	
	
	/**
	 * Returns iterator for a minimal rectangular set of cells covering the selected cells. 
	 * First number in the array returned by the {@link Iterator#next()} method 
	 * is a row axis index, the second one is a column axis index.
	 * <p>
	 * <code>index0</code> and <code>index1</code> refer to the model, 
	 * not the visual position of the item on the screen
	 * which can be altered by move and hide operations.  
	 * <p>
	 * <strong>Warning</strong> iterating index by index over large extents 
	 * may cause a performance problem.
	 */
	Iterator<Number[]> getSelectedBoundsIterator() {
		return new ImmutableIterator<Number[]>() {
			NumberPairSequence seq;
			{
				Number[] e = cellSelection.getExtent();
				CellSet set = new CellSet(section0.math, section1.math);
				set.add(e[0], e[1], e[2], e[3]);
				seq = new NumberPairSequence(set);
			}
			
			private boolean next;
			{
				seq.init();
			}
			@Override
			public boolean hasNext() {
				next = seq.next();
				return next;
			}

			@Override
			public Number[] next() {
				return next ? new Number[] {seq.index0(), seq.index1()} : null;
			}
		};
	}
	
	
	
	/**
	 * Returns selection index bounds. The numbers in the returning array are 
	 * minimal axis0 index, maximal axis0 index, minimal axis1 index, maximal axis1 index. 
	 * @return selection index bounds
	 */
	public Number[] getSelectedExtent() {
		return cellSelection.getExtent();
	}
//	
//	/**
//	 * Copies selected data to the clip board using tab for cells and new line for rows concatenation.
//	 */
//	void copy() {
//		StringBuilder sb = new StringBuilder();
//		Number[] e = cellSelection.getExtent();
//		NumberSet set0 = new NumberSet(section0.math);
//		NumberSet set1 = new NumberSet(section1.math);
//		set0.add(e[0], e[1]);
//		set1.add(e[2], e[3]);
//		NumberSequence seq0 = new NumberSequence(set0);
//		NumberSequence seq1 = new NumberSequence(set1);
//		for (seq0.init(); seq0.next();) {
//			int i = 0;
//			for (seq1.init(); seq1.next();) {
//				if (i++ > 0) sb.append("\t");
//				sb.append(NEW_LINE);
//			}
//			sb.append(NEW_LINE);
//		}
//	}
	
	void backupSelection() {
		lastSelection = cellSelection.copy();
	}
	
	void restoreSelection() {
		cellSelection = lastSelection.copy();
	}


	/*------------------------------------------------------------------------
	 * Gesture 
	 */
	
	/**
	 * Binds the command to the user gesture specified by the event type and code.
	 * Code is a logical <i>OR</i> of key, state mask and mouse button codes. 
	 */
	public void bind(int commandId, int eventType, int code) {
		bindings.add(new GestureBinding(commandId, eventType, code));
		if (editor != null && (
				commandId == Matrix.CMD_APPLY_EDIT || 
				commandId == Matrix.CMD_CANCEL_EDIT )) {
			editor.controlListener.bind(commandId, eventType, code);
		}
	}
	
	/**
	 * Removes the binding the command to the user gesture specified by the event type and code.
	 * Code is a logical <i>OR</i> of key, state mask and mouse button codes. 
	 */
	public void unbind(int commandId, int eventType, int code) {
		if (editor != null && (
				commandId == Matrix.CMD_APPLY_EDIT || 
				commandId == Matrix.CMD_CANCEL_EDIT )) {
			editor.controlListener.unbind(commandId, eventType, code);
		}
		else {
			for (int i = bindings.size(); i-- > 0;) {
				GestureBinding binding = bindings.get(i);
				if (binding.commandId == commandId && binding.eventType == eventType 
						&& binding.key == code) {
					bindings.remove(i);
				};
			}
		}
	}
	
	/**
	 * Adds the listener to the collection of listeners who will
	 * be notified when a zone cell is selected by the user, by sending
	 * it one of the messages defined in the <code>SelectionListener</code>
	 * interface. 
	 * <p>
	 * The selection event is not emitted by the zone API methods that are
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
		Preconditions.checkNotNullWithName(listener, "listener");
		TypedListener typedListener = new TypedListener(listener);
		this.listeners.add(SWT.Selection, typedListener);
		this.listeners.add(SWT.DefaultSelection, typedListener);
	}
	
	/**
	 * Removes the listener from the collection of listeners who will
	 * be notified when a zone cell is selected by the user.
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
		Preconditions.checkNotNullWithName(listener, "listener");
		listeners.remove(SWT.Selection, listener);
		listeners.remove(SWT.DefaultSelection, listener);
	}

	/**
	 * Adds the listener to the collection of listeners who will
	 * be notified when an event of the given type occurs. When the
	 * event does occur in the zone, the listener is notified by
	 * sending it the <code>handleEvent()</code> message. The event
	 * type is one of the event constants defined in class <code>SWT</code>.
	 *
	 * @param eventType the type of event to listen for
	 * @param listener the listener which should be notified when the event occurs
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 * </ul>
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
	 * </ul>
	 *
	 * @see Listener
	 * @see SWT
	 * @see #getListeners(int)
	 * @see #removeListener(int, Listener)
	 * @see #notifyListeners
	 */
	public void addListener(int eventType, final Listener listener) {
		matrix.addListener(eventType, new Listener() {
			@Override
			public void handleEvent(Event e) {
				AxisItem<N0> item0 = matrix.getAxis0().getItemByDistance(e.y);
				AxisItem<N1> item1 = matrix.getAxis1().getItemByDistance(e.x);
				if (item0 != null && item1 != null && Zone.this == 
						matrix.getZoneUnchecked(
								item0.getSectionUnchecked(), 
								item1.getSectionUnchecked())) 
				{
					listener.handleEvent(e);
				}
			}
		});
	}

	
	/*------------------------------------------------------------------------
	 * Painting 
	 */

	void paint(GC gc, final Layout layout0, final Layout layout1, final Frozen dock0, final Frozen dock1) {
		for (Painter p: painters) {
			if (!p.isEnabled() || !p.init(gc)) continue;
			
			int distance = 0, width = 0;
			Number index;
			switch (p.scope) {
			
			case Painter.SCOPE_CELLS_HORIZONTALLY:
				LayoutSequence seq0 = layout0.cellSequence(dock0, section0);
				LayoutSequence seq1 = layout1.cellSequence(dock1, section1);
				for (seq0.init(); seq0.next();) {
					distance = seq0.getDistance();
					width = seq0.getWidth();
					index = seq0.item.getIndex();
					for (seq1.init(); seq1.next();) {
						p.paint(index, seq1.item.getIndex(), 
							seq1.getDistance(), distance, seq1.getWidth(), width);
					}
				}
				break;
				
			case Painter.SCOPE_CELLS_VERTICALLY:
				seq0 = layout0.cellSequence(dock0, section0);
				seq1 = layout1.cellSequence(dock1, section1);
				for (seq1.init(); seq1.next();) {
					distance = seq1.getDistance();
					width = seq1.getWidth();
					index = seq1.item.getIndex();
					for (seq0.init(); seq0.next();) {
						p.paint(seq0.item.getIndex(), index,
							distance, seq0.getDistance(), width, seq0.getWidth());
					}
				}
				break;
			
			case Painter.SCOPE_ROW_CELLS:
				seq0 = layout0.cellSequence(dock0, section0);
				distance = bounds.x;
				width = bounds.width;
				for (seq0.init(); seq0.next();) {
					p.paint(seq0.item.getIndex(), null, distance, seq0.getDistance(), width, seq0.getWidth());
				}
				break;
				
			case Painter.SCOPE_COLUMN_CELLS:
				seq1 = layout1.cellSequence(dock1, section1);
				distance = bounds.y;
				width = bounds.height;
				for (seq1.init(); seq1.next();) {
					p.paint(null, seq1.item.getIndex(), seq1.getDistance(), distance, seq1.getWidth(), width);
				}
				break;
				
			case Painter.SCOPE_HORIZONTAL_LINES:
				seq0 = layout0.lineSequence(dock0, section0);
				distance = bounds.x;
				width = bounds.width;
				for (seq0.init(); seq0.next();) {
					p.paint(seq0.item.getIndex(), null, distance, seq0.getDistance(), width, seq0.getWidth());
				}
				break;
			
			case Painter.SCOPE_VERTICAL_LINES:
				seq1 = layout1.lineSequence(dock1, section1);
				distance = bounds.y;
				width = bounds.height;
				for (seq1.init(); seq1.next();) {
					p.paint(null, seq1.item.getIndex(), seq1.getDistance(), distance, seq1.getWidth(), width);
				}
				break;
				
			}
			p.clean();
		}
	}
	
	/**
	 * Adds the painter at the end of the receiver's painters list.
	 * @param painter the painter to be added
	 */
	public void addPainter(Painter<N0, N1> painter) {
		Preconditions.checkNotNullWithName(painter, "painter");
		painters.add(painter);
		setPainterMatrixAndZone(painter);
	}

	/**
	 * Inserts the painter at the given index of the receiver's painters list.
	 * @param index at which the specified painter is to be inserted
	 * @param painter painter to be inserted
	 * @throws IndexOutOfBoundsException if the index is out of range
     *         (<tt>index &lt; 0 || index &gt;= getPainterCount()</tt>)
	 */
	public void addPainter(int index, Painter<N0, N1> painter) {
		Preconditions.checkNotNullWithName(painter, "painter");
		// Check uniqueness of painters names
		painters.add(index, painter);
		setPainterMatrixAndZone(painter);
	}
	
	/**
	 * Replaces the painter at the given index of the receiver's painters list. 
	 * @param index index of the element to replace
	 * @param painter painter to be stored at the specified position
	 * @throws IndexOutOfBoundsException if the index is out of range
     *         (<tt>index &lt; 0 || index &gt;= getPainterCount()</tt>)
	 */
	public void setPainter(int index, Painter<N0, N1> painter) {
		Preconditions.checkNotNullWithName(painter, "painter");
		painters.set(index, painter);
		setPainterMatrixAndZone(painter);
	}
	
	/**
	 * Replaces the painter at the index of painter with the same name.
	 * If a painter with the specified name does not exist, 
	 * then the new painter is added at the end.
	 * @param painter painter to replace a painter with the same name
	 * @throws IndexOutOfBoundsException if there is no painter with the same name
	 * @see #getPainter(String)
	 */
	public void replacePainter(Painter<N0, N1> painter) {
		Preconditions.checkNotNull(painter);
		painters.replacePainter(painter);
		setPainterMatrixAndZone(painter);
	}
	
	/**
     * Removes the element at the specified position in the list of painters. 
     * Shifts any subsequent painters to the left (subtracts one
     * from their indices). Returns the painter that was removed from the
     * list.
     *
     * @param index the index of the painter to be removed
	 * @return 
     * @return the painter previously at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (<tt>index &lt; 0 || index &gt;= getPainterCount()</tt>)
     */
	public Painter<N0, N1> removePainter(int index) {
		Preconditions.checkPositionIndex(index, painters.size());
		return painters.remove(index);
	}
	
	/**
     * Returns the index of a painter with the specified name
     * in the list of the receiver's painters, or -1 
     * if this list does not contain the element.
     *
     * @param name painter name to search for
     * @return the index of a painter with the specified name
     */
	public int indexOfPainter(String name) {
		Preconditions.checkNotNullWithName(name, "name");
		return painters.indexOfPainter(name);
	}
	
	/**
     * Returns a painter with the specified name, or <code>null</code>
     * if the painters list does not contain such painter.
     *
     * @param name painter name to search for
     * @return the index of a painter with the specified name
     */
	public Painter<N0, N1> getPainter(String name) {
		Preconditions.checkNotNullWithName(name, "name");
		return painters.get(indexOfPainter(name));
	}
	
	/**
     * Returns the number of the receiver's painters. 
     *
     * @return the number of the receiver's painters
     */
	public int getPainterCount() {
		return painters.size();
	}
	
	/**
     * Returns the painter at the specified position in the receiver's list of painters.
     *
     * @param index index of the painter to return
     * @return the painter at the specified position in the receiver's list of painters.
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (<tt>index &lt; 0 || index &gt;= getPainterCount()</tt>)
     */
	public Painter<N0, N1> getPainter(int index) {
		Preconditions.checkPositionIndex(index, painters.size());
		return painters.get(index);
	}

	private void setPainterMatrixAndZone(Painter painter) {
		if (painter.scope == Painter.SCOPE_CELLS_HORIZONTALLY ||
				painter.scope == Painter.SCOPE_CELLS_VERTICALLY) 
		{
			painter.zone = this;
			painter.setMatrix(matrix);
		}
	}
	
	private static class LinePainter extends Painter {
		private final Color color;

		public LinePainter(String name, int scope, Color color) {
			super(name, scope);
			this.color = color;
		}
		
		@Override
		public void paint(Number index0, Number index1, int x, int y, int width, int height) {
			gc.setBackground(color);
			gc.fillRectangle(x, y, width, height);
		}
	}

	
	void insert(int axisIndex, Section section, Number target, Number count) {
		if (axisIndex == 0) {
			if (section0.equals(section)) {
				cellSelection.insert0(target, count);
				lastSelection.insert0(target, count);
			}
		}
		else {
			if (section1.equals(section1)) {
				cellSelection.insert1(target, count);
				lastSelection.insert1(target, count);
			}
		}
	}

	void delete(int axisIndex, Section section, Number start, Number end) {
		if (axisIndex == 0) {
			if (section0.equals(section)) {
				cellSelection.delete0(start, end);
				lastSelection.delete0(start, end);
			}
		}
		else {
			if (section1.equals(section1)) {
				cellSelection.delete1(start, end);
				lastSelection.delete1(start, end);
			}
		}
	}
	
	
	/*------------------------------------------------------------------------
	 * Non-public 
	 */
	
	void setMatrix(Matrix<N0, N1> matrix) {
		this.matrix = matrix;
		for (Painter painter: painters) {
			if (painter.scope == Painter.SCOPE_CELLS_HORIZONTALLY ||
					painter.scope == Painter.SCOPE_CELLS_VERTICALLY) 
			{
				painter.zone = this;
				painter.setMatrix(matrix);
			}
		}
	}

	public Matrix<N0, N1> getMatrix() {
		return matrix;
	}
	
	void setEditor(ZoneEditor editor) {
		this.editor = editor;
	}

}
