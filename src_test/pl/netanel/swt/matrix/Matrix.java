package pl.netanel.swt.matrix;

import static pl.netanel.util.Preconditions.checkArgument;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TypedListener;

import pl.netanel.swt.Resources;
import pl.netanel.swt.matrix.Layout.CountCache;
import pl.netanel.util.Preconditions;
import pl.netanel.util.Util;


/**
 * Main class in the package which draws a two dimensional grid of cells. 
 *
 * @author jacek.p.kolodziejczyk@gmail.com
 * @created 2010-06-11
 */
public class Matrix extends Canvas {
	
	public Layout layout0, layout1;
	public Painters currentCellPainters; 
	public long autoScrollDelay;
	
	final MatrixAxis axis0, axis1;
	MatrixModel model; 
	MatrixListener listener;
	List<Zone> zones;
	Zone body;	
	ExtentPairSequence seq;

	private Rectangle area;
	private ScheduledExecutorService executor;
	
	long t;
	
	/**
	 * Constructs a matrix with a default matrix model.
	 *  
	 * @param parent
	 * @param style
	 */
	public Matrix(Composite parent, int style) {
		this(parent, style, new DefaultMatrixModel());
	}
	
	/**
	 * Constructs a matrix with a the given matrix model.
	 *  
	 * @param parent
	 * @param style
	 */
	public Matrix(Composite parent, int style, MatrixModel model) {
		super(parent, checkStyle(style));
		
		setBackground(Resources.getColor(SWT.COLOR_LIST_BACKGROUND));
		
		axis0 = MatrixAxis.createVertical(this);
		axis1 = MatrixAxis.createHorizontal(this);
		
		setDefaultZonesWithPainters();
		
		// Initialize current cell settings
		currentCellPainters = new Painters();
		currentCellPainters.add(new BorderPainter(2));
		
    	autoScrollDelay = M.AUTO_SCROLL_DELAY;
    	
    	
		// Initialize event handling
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				try {
					switch (event.type) {
					case SWT.Paint: 		onPaint(event); break;
					case SWT.Resize: 		resize(); break;
					}
				} catch (Exception e) {
					rethrow(e);
				}
			}
		};
		
		addListener(SWT.Paint, listener);
		addListener(SWT.Resize,	listener);
		
		addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				if (executor != null) executor.shutdownNow();
			}
		});
		this.listener = new MatrixListener(this);
		
		setModel(model);
	}
	
	static int checkStyle (int style) {
		checkArgument(!bitSet(style, SWT.SINGLE) || !bitSet(style, SWT.MULTI),
			"Cannot set both SWT.SINGLE and SWT.MULTI");
		checkArgument(!bitSet(style, SWT.NO_SCROLL) || 
				!(bitSet(style, SWT.V_SCROLL) || bitSet(style, SWT.H_SCROLL)),
			"Cannot set both SWT.NO_SCROLL and (SWT.V_SCROLL or SWT.H_SCROLL)");
		
		return style | SWT.DOUBLE_BUFFERED;
	}
	
	static private boolean bitSet(int style, int bit) {
		return (style & bit) != 0;
	}
	
	/**
	 * Returns the row (vertical) axis of the receiver.
	 * @return
	 */
	public MatrixAxis getRowAxis() {
		return axis0;
	}

	/**
	 * Returns the column (horizontal) axis of the receiver.
	 * @return
	 */
	public MatrixAxis getColumnAxis() {
		return axis1;
	}
	

	/**
	 * Resize event handler.
	 * @param event
	 */
	private void resize() {
		// Return if there is no model provided yet.
		if (layout0 == null) return;
				
		Rectangle area = getClientArea();
		
		// Return if the widget does not have an area to draw yet.
		if (area.width == 0) return;
		
//		layout0.setViewportSize(area.height - 1);
//		layout1.setViewportSize(area.width - 1);
		layout0.setViewportSize(area.height);
		layout1.setViewportSize(area.width);
		layout0.compute();
		layout1.compute();
		
		updateScrollBars();
	}
	
	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		Rectangle bounds = getDisplay().getBounds();
		Point size = new Point(
				layout1.computeSize(wHint, bounds.width, changed),
				layout0.computeSize(hHint, bounds.height, changed));
		
		if ((getStyle() & SWT.V_SCROLL) != 0 && layout0.isScrollRequired()) {
			size.x += OS.GetSystemMetrics (OS.SM_CXVSCROLL);
		}
		if ((getStyle() & SWT.H_SCROLL) != 0 && layout1.isScrollRequired()) {
			size.y += OS.GetSystemMetrics (OS.SM_CYHSCROLL);
		}
		
		int border = getBorderWidth() * 2;
		size.x += border;
		size.y += border;
		return size;
	}
	

	/**
	 * Called on resize or item count change.
	 */
	private void updateScrollBars() {
		area = getClientArea();
		
		// Update the scroll bars visibility
		// If at one of the scroll bar visibility has changed then update the other one also
		if (axis1.updateScrollBarVisibility(area.width)) {
			area = getClientArea();
			axis0.updateScrollBarVisibility(area.height);
			axis1.updateScrollBarVisibility(area.width);
			area = getClientArea();
		}
		else if (axis0.updateScrollBarVisibility(area.height)) {
			area = getClientArea();			 
			axis1.updateScrollBarVisibility(area.width);
			axis0.updateScrollBarVisibility(area.height);
			area = getClientArea();
		}
		
		axis1.updateScrollBarValues(area.width);
		axis0.updateScrollBarValues(area.height);
		
		area = getClientArea();
	}
	
	/**
	 * Paint event handler.
	 * <p>
	 * It can be overriden to do some custom painting before or after 
	 * the standard paintng procedure, like for example an image background 
	 * for the whole widget.
	 * 
	 * @param event
	 */
	protected void onPaint(Event event) {
		getModel();
		final GC gc = event.gc;
		axis0.computeIfRequired();
		axis1.computeIfRequired();

		paintDock(gc, Dock.MAIN, Dock.MAIN);
//		paint(gc, Dock.TAIL, Dock.HEAD);
//		paint(gc, Dock.HEAD, Dock.TAIL);
//		paint(gc, Dock.TAIL, Dock.MAIN);
//		paint(gc, Dock.MAIN, Dock.TAIL);
//		paint(gc, Dock.TAIL, Dock.TAIL);
		paintDock(gc, Dock.HEAD, Dock.MAIN);
		paintDock(gc, Dock.MAIN, Dock.HEAD);
		paintDock(gc, Dock.HEAD, Dock.HEAD);
		
		paintFreezeLines(gc);
		
		// Paint current cell
		if (axis0.isCurrentItemEnabled && axis1.isCurrentItemEnabled) {
			gc.setClipping((Rectangle) null);
			AxisItemSequence it0 = layout0.currentIterator();
			AxisItemSequence it1 = layout1.currentIterator();
			if (it0 != null && it1 != null) {
				paintCells(gc, currentCellPainters, new ItemPairIterator(it0, it1));
			}
		}
		System.out.println(BigDecimal.valueOf(System.nanoTime() - t, 6).toString());
	};
	
	private void paintDock(GC gc, Dock dock0, Dock dock1) {
		for (int section0: layout0.getSections(dock0)) {
			for (int section1: layout1.getSections(dock1)) {
				Zone zone = getZone(section0, section1);
				if (zone == null) continue;

				List<Bound> lines0 = layout0.getLineBound(dock0, section0);
				List<Bound> lines1 = layout1.getLineBound(dock1, section1);
				Bound first0 = lines0.get(0);
				Bound first1 = lines1.get(0);
				AxisItemSequence items;
				
				// Paint row lines
 				int x = first1.distance;
 				int y = first0.distance;
				int width = layout1.getEndDistance(dock1, section1);
				int height = layout0.getEndDistance(dock0, section0);
				gc.setClipping(x, y, width, height);
				
				items = layout0.lineIterator(dock0, section0);
				for (Painter painter: zone.linePainters0) {
					if (!painter.isEnabled()) continue;
					painter.init(gc);
					items.init();
					for (Bound bound: lines0) {
						items.next();
						painter.paint(x, bound.distance, width, bound.width);
					}
					painter.clean();
				}
				
				// Paint column lines
				items = layout1.lineIterator(dock1, section1);
				for (Painter painter: zone.linePainters1) {
					if (!painter.isEnabled()) continue;
					painter.init(gc);
					items.init();
					for (Bound bound: lines1) {
						items.next();
						painter.paint(bound.distance, y, bound.width, height);
					}
					painter.clean();
				}
				
				// Paint cells
				paintCells(gc, zone.cellPainters, new ItemPairIterator(
						axis0.layout.cellIterator(dock0, section0),
						axis1.layout.cellIterator(dock1, section1) ));
			}
		}
	}
	
	/**
	 * Paints all the cells from the given cell iterator for each painter in the receiver.  
	 * @param gc
	 * @param iterator
	 */
	public void paintCells(GC gc, Painters painters, ItemPairIterator iterator) {
		for (Painter painter: painters) {
			if (!isEnabled()) return;
			painter.init(gc);
			iterator.init();
			while (iterator.hasNext()) {
				iterator.next();
				Bound b0 = iterator.getBound0();
				Bound b1 = iterator.getBound1();
				painter.beforePaint(iterator.getItem0(), iterator.getItem1());
				if (painter.isItemIncluded()) {
					painter.paint(b1.distance, b0.distance, b1.width, b0.width);
				}
				painter.afterPaint(iterator.getItem0(), iterator.getItem1());
			}
			painter.clean();
		}
	}
	
	
	private void paintFreezeLines(GC gc) {
		gc.setClipping((Rectangle) null);
		int height = layout0.getWidth();
		int width = layout1.getWidth();
		Bound freezeLine;
		// Draw tail freeze lines
		if (shouldPaintFreezeLine(axis0, Dock.TAIL) ) {
			gc.setBackground(axis0.getFreezeTailLineColor());
			ArrayList<Bound> lines = layout0.tail.lines;
			freezeLine = lines.get(lines.size() - 1);
			gc.fillRectangle(area.x, freezeLine.distance, width, freezeLine.width);
		}
		if (shouldPaintFreezeLine(axis1, Dock.TAIL) ) {
			gc.setBackground(axis1.getFreezeTailLineColor());
			ArrayList<Bound> lines = layout1.tail.lines;
			freezeLine = lines.get(lines.size() - 1);
			gc.fillRectangle(freezeLine.distance, area.y, freezeLine.width, height);
		}

		// Draw head freeze lines
		if (shouldPaintFreezeLine(axis0, Dock.HEAD) ) {
			gc.setBackground(axis0.getFreezeHeadLineColor());
			ArrayList<Bound> lines = layout0.head.lines;
			freezeLine = lines.get(lines.size() - 1);
			gc.fillRectangle(area.x, freezeLine.distance, width, freezeLine.width);
		}
		if (shouldPaintFreezeLine(axis1, Dock.HEAD) ) {
			gc.setBackground(axis1.getFreezeHeadLineColor());
			ArrayList<Bound> lines = layout1.head.lines;
			freezeLine = lines.get(lines.size() - 1);
			gc.fillRectangle(freezeLine.distance, area.y, freezeLine.width, height);
		}
	}
	
	private boolean shouldPaintFreezeLine(MatrixAxis axis, Dock dock) {
		CountCache cache = dock == Dock.HEAD ? axis.layout.head : axis.layout.tail;
		if (cache.count == 0) return false;
		int width = axis.getFreezeHeadLineWidth();
		if (width == 0) return false;
		Color color = dock == Dock.HEAD ? axis.getFreezeHeadLineColor() : axis.getFreezeTailLineColor();
		if (color == null) return false;
		return true;
	}
	
	
	
	/*------------------------------------------------------------------------
	 * Model
	 */

	/**
	 * Returns the matrix model.
	 * <p>
	 * It creates a {@link DefaultMatrixModel} if the model was not 
	 * set explicitly by <code>setModel(MatrixModel)</code>.
	 */
	public MatrixModel getModel() {
		return model;
	}

	/**
	 * Sets the matrix model for the receiver.
	 */
	public void setModel(MatrixModel model) {
		this.model = model;
		
		AxisModel model0 = model.getRowModel();
		axis0.layout = layout0 = new AxisLayout(model0);
		layout0.isComputingRequired = true;

		AxisModel model1 = model.getColumnModel();
		axis1.layout = layout1 = new AxisLayout(model1);
		layout1.isComputingRequired = true;
		
		seq = new ExtentPairSequence();
		
		// Set zone sections	
		setZoneSections(Zone.BODY, model0.getBody(), model1.getBody());
		setZoneSections(Zone.ROW_HEADER, model1.getBody(), model0.getHeader());
		setZoneSections(Zone.COLUMN_HEADER, model0.getHeader(), model1.getBody());
		setZoneSections(Zone.TOP_LEFT, model0.getHeader(), model1.getHeader());
		body = zones.get(0);
		
		// Make sure all zones exist
		int len0 = model0.getSectionCount();
		for (int section0 = 0; section0 < len0; section0++) {
			int len1 = model1.getSectionCount();
			for (int section1 = 0; section1 < len1; section1++) {
				getZone(section0, section1);
			}	
		}
		
		if (bitSet(getStyle(), SWT.FULL_SELECTION)) {
			axis0.setSelectionOptions(SWT.FULL_SELECTION);
		}
		
		// Default background and foreground colors
		if (model.getDefaultBackground() == null) {
			model.setDefaultBackground(getBackground());
		}
		if (model.getDefaultForeground() == null) {
			model.setDefaultForeground(getForeground());
		}
		if (model.getDefaultFont() == null) {
			model.setDefaultFont(getFont());
		}
		// Set layout viewport size
		Point shellSize = getShell().getSize();
		layout0.setViewportSize(area == null || area.height == 0 ? shellSize.y: area.height);
		layout1.setViewportSize(area == null || area.width == 0 ? shellSize.x: area.width);
		
		listener.setLayout(layout0, layout1);
		selectCurrentCell();
		redraw();
	}
	
	private void setZoneSections(int id, int section0, int section1) {
		Zone zone = getZone(id);
		zone.section0 = section0;
		zone.section1 = section1;
		zone.cellSelection = new CellSet(layout0.factory, layout1.factory);
		zone.lastCells = new CellSet(layout0.factory, layout1.factory);
		
	}

	
	
	/*------------------------------------------------------------------------
	 * Zones
	 */
	
	/**
	 * Returns a zone by an identifier defined in {@link Zone}.  
	 * The parameter can have four possible values: <ul>
	 * <li>BODY</li>
	 * <li>ROW_HEADER</li>
	 * <li>COLUMN_HEADER</li>
	 * <li>TOP_LEFT</li>
	 * </ul>
	 * Otherwise the function returns null.
	 * <p>
	 * @param id 
	 * @return
	 */
	public Zone getZone(int id) {
		for (Zone zone: zones) {
			if (zone.is(id)) {
				return zone;
			}
		}
		return null;
	}
	
	/**
	 * Returns a zone located at the intersection of the given axis sections.
	 * 
	 * @param section0 section of the row axis
	 * @param section1 section of the column axis
	 * @return
	 * @exception IllegalArgumentException 
	 * 	 	if the any of the section parameters is out of scope.
	 */
	public Zone getZone(int section0, int section1) {
		Matrix.checkSection(axis0, section0, true);
		Matrix.checkSection(axis1, section1, true);
		
		for (Zone zone: zones) {
			if (zone.section0 == section0 && zone.section1 == section1 ) {
				return zone;
			}
		}
		Zone zone = new Zone(Zone.NONE);
		zone.section0 = section0;
		zone.section1 = section1;
		return zone;
	}
	
	private void setDefaultZonesWithPainters() {
		Zone body, topLeft;
		zones = new ArrayList<Zone>();
		zones.add(body = new Zone(Zone.BODY));
		zones.add(setHeaderStyle(new Zone(Zone.ROW_HEADER)));
		zones.add(setHeaderStyle(new Zone(Zone.COLUMN_HEADER)));
		zones.add(topLeft = new Zone(Zone.TOP_LEFT));
		
		body.cellPainters.add(new DefaultBackgroundPainter(this, null, 
				BackgroundPainter.getDefaultBodySelectionColor()));
		body.cellPainters.add(new DefaultTextPainter(this));
		
		topLeft.cellPainters.add(new DefaultBackgroundPainter(this, 
				Resources.getColor(SWT.COLOR_WIDGET_BACKGROUND), null));
		Color color = Resources.getColor(SWT.COLOR_WIDGET_DARK_SHADOW);
		topLeft.linePainters0.get(LinePainter.class).setBackground(color);
		topLeft.linePainters1.get(LinePainter.class).setBackground(color);

//		zones.add(new Zone(Zone.BOTTOM_RIGHT, FOOTER, FOOTER));
//		zones.add(new Zone(Zone.ROW_FOOTER, BODY, FOOTER).setHeaderStyle());
//		zones.add(new Zone(Zone.COLUMN_FOOTER, FOOTER, BODY).setHeaderStyle());
		
		// Headers selection background painters
//		Color headerSelectionColor = BooleanBackgroundPainter.getDefaultHeaderSelectionColor(); 
//		Color headerColor = Resources.getColor(SWT.COLOR_WIDGET_BACKGROUND);
//		rowHeader.cellPainters.add(new BooleanBackgroundPainter(
//				axis0.getSelectionModel(), headerColor, headerSelectionColor));
//		columnHeader.cellPainters.add(new BooleanBackgroundPainter(
//				axis1.getSelectionModel(), headerColor, headerSelectionColor));
		
	}
	
	private Zone setHeaderStyle(Zone zone) {
		DefaultBackgroundPainter backgroundPainter = new DefaultBackgroundPainter(this,
				Resources.getColor(SWT.COLOR_WIDGET_BACKGROUND), 
				BackgroundPainter.getDefaultHeaderSelectionColor());
		
		zone.cellPainters.add(backgroundPainter);
		zone.cellPainters.add(new DefaultTextPainter(this));
		Color color = Resources.getColor(SWT.COLOR_WIDGET_DARK_SHADOW);
		zone.linePainters0.get(LinePainter.class).setBackground(color);
		zone.linePainters1.get(LinePainter.class).setBackground(color);
		return zone;
	}

	
	/*------------------------------------------------------------------------
	 * Cells 
	 */
	
	/**
	 * Returns a rectangle describing the given cell size and location 
	 * relative to its parent.
	 * @param item0 item on the row axis
	 * @param item1 item on the column axis
	 * @return 
	 */
	public Rectangle getCellBounds(AxisItem item0, AxisItem item1) {
		Preconditions.checkNotNullWithName(item0, "item0");
		Preconditions.checkNotNullWithName(item0, "item1");
		Bound b0 = layout0.getCellBound(item0.section, item0.index);
		Bound b1 = layout1.getCellBound(item1.section, item1.index);
		if (b0 == null || b1 == null) return null;
		return new Rectangle(b1.distance, b0.distance, b1.width, b0.width);
	}
	
	public Rectangle getLineBounds(AxisItem item0, AxisItem item1) {
		Preconditions.checkNotNullWithName(item0, "item0");
		Preconditions.checkNotNullWithName(item0, "item1");
		Bound b0 = layout0.getLineBound(item0.section, item0.index);
		Bound b1 = layout1.getLineBound(item1.section, item1.index);
		if (b0 == null || b1 == null) return null;
		return new Rectangle(b1.distance, b0.distance, b1.width, b0.width);
	}

	/**
	 * Returns true if both row axis and column axis have the current item enabled.
	 * 
	 * @return true if the current cell is enabled
	 */
	public boolean isCurrentCellEnabled() {
		return axis0.isCurrentItemEnabled && axis1.isCurrentItemEnabled;
	}

	/**
	 * Sets the current item enabled state for both row axis and column axis. 
	 * If the current cell is enabled then individual cells can be selected.
	 * 
	 * @param enabled new current item enabled state.
	 */
	public void setCurrentCellEnabled(boolean enabled) {
		axis0.isCurrentItemEnabled = enabled;
		axis1.isCurrentItemEnabled = enabled;
		selectCurrentCell();
	}

	
	
	/*------------------------------------------------------------------------
	 * Selection 
	 */
	
//	/**
//	 * Enables or disables individual cell selection for the matrix depending on the 
//	 * <code>enabled</code> parameter. 
//	 * 
//	 * @param flag a new receiver's cell selection possibility state
//	 */
//	public void setCellSelectionEnabled(boolean flag) {
//        ensureModelExist();
//        isCellSelectionEnabled = flag;
//        selectCurrentCell();
//        redraw();
//	}
//	
//	/**
//	 * Returns true if the receiver's header is visible, and false otherwise. 
//	 * @return the receiver's cell selection possibility state
//	 */
//	public boolean isCellSelectionEnabled() {
//		return isCellSelectionEnabled;
//	}
	
	/**
	 * Selects a rectangular set of cells limited by the given row and column 
	 * items inclusively. Cells out of range are ignored.
	 * <p>
	 * Cell selection must be enabled for this function to take an effect.
	 * @param start0
	 * @param end0
	 * @param start1
	 * @param end1
	 */
	public void select(AxisItem start0, AxisItem end0, AxisItem start1, AxisItem end1) {
		AxisItem[] range = adjustRange(start0, end0, start1, end1);
		if (range == null) return;
		selectRange(true, range[0], range[1], range[2], range[3]);
	}
	

	public void selectAlso(AxisItem start0, AxisItem end0, AxisItem start1, AxisItem end1) {
		AxisItem[] range = adjustRange(start0, end0, start1, end1);
		if (range == null) return; 
		selectRange(false, range[0], range[1], range[2], range[3]);
	}

	AxisItem[] adjustRange(AxisItem start0, AxisItem end0, AxisItem start1, AxisItem end1) {
		if (start0 == null || end0 == null ||  
			start1 == null || end1 == null) 	return null; 
//		 || start0.section > end0.section 
//		 || start1.section > end1.section
		AxisItem start0a = axis0.adjustItem(start0);
		AxisItem end0a = axis0.adjustItem(end0);
		AxisItem start1a = axis1.adjustItem(start1);
		AxisItem end1a = axis1.adjustItem(end1);

		// Return null if both start and end have been reduced to the same edge
		if (start0a.compareTo(end0a) == 0 && 
			start0a.compareTo(start0) != 0 && end0a.compareTo(end0) != 0 ||
			start1a.compareTo(end1a) == 0 && 
			start1a.compareTo(start1) != 0 && end1a.compareTo(end1) != 0 ) 
		{
			return null;
		}
		
		return new AxisItem[] {start0a, end0a, start1a, end1a};
	}
	
	
	
	/**
	 * Selects all of the cells in the receiver. 
	 * Does not select items in its row axis nor column axis.
	 * <P>
	 */
	
	
	



	
//	public void setFreezeLineWidth(int width) {
//		freezeLineWidth = width;
//		axis0.setFreezeHeadLineWidth(width);
//		axis0.setFreezeTailLineWidth(width);
//		axis1.setFreezeHeadLineWidth(width);
//		axis1.setFreezeTailLineWidth(width);
//	}
//
//	public void setFreezeLineColor(final Color color) {
//		axis0.setFreezeHeadLineColor(color);
//		axis1.setFreezeHeadLineColor(color);
//		axis0.setFreezeTailLineColor(color);
//		axis1.setFreezeTailLineColor(color);
//	}
//
//	
//	public void freezeHead() {
//		axis0.setFreezeHead(layout0.indexOf(layout0.current, M.FORWARD));
//		axis1.setFreezeHead(layout1.indexOf(layout1.current, M.FORWARD));
//		layout0.show(layout0.current);
//		layout1.show(layout1.current);
//	}
//	
//	public void freezeTail() {
//		axis0.setFreezeTail(layout0.indexOf(layout0.current, M.BACKWARD));
//		axis1.setFreezeTail(layout1.indexOf(layout1.current, M.BACKWARD));
//		layout0.show(layout0.current);
//		layout1.show(layout1.current);
//	}


	/*------------------------------------------------------------------------
	 * Protected methods 
	 */

	protected void rethrow(Throwable e) {
		// Stop auto scroll
		if (executor != null) {
			Matrix.this.listener.state0.autoScroll.stop();
			Matrix.this.listener.state1.autoScroll.stop();
			executor.shutdown();
			try {
				executor.awaitTermination(1, TimeUnit.SECONDS);
			} 
			catch (InterruptedException e1) { }
		}
		
		Util.rethrow(e);
	}
	
	ScheduledExecutorService getExecutor() {
		if (executor == null || executor.isShutdown()) {
			// TODO Catch exceptions from tasks
			executor = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
				@Override
				public Thread newThread(Runnable r) {
					return new Thread(r, "matrix thread");
				}
			});
		}
		return executor;
	}
	
	
	/**
	 * Wraps call of protected method checkWidget() in a package privilege 
	 * to be accessible by classes in this package.
	 */
	void checkWidget2() {
		super.checkWidget();
		getModel();
	}
	
	
	/*------------------------------------------------------------------------
	 * Action 
	 */

	/**
	 * Invokes a command with the given id and arguments. Command is an action bound to 
	 * mouse and keyboard gestures of the user. For example the current cell movement to 
	 * the last cell in the matrix (command id = M.CURRENT_END) is bound to SWT.MOD1 | SWT.END 
	 * keyboard gesture.
	 * 
	 * @param commandId constant defined in {@link M}
	 * @param args 0-4 axis items that are interpreted as follows:<br>
	 * 1) current item of rows axis <br>
	 * 2) current item of column axis <br>
	 * 3) last item of rows axis <br>
	 * 4) last item of column axis <br>
	 * 
	 */
	public void executeCommand(int commandId, AxisItem ...args) {
		if (args.length > 0) listener.state0.item = args[0];
		if (args.length > 1) listener.state1.item = args[1];
		if (args.length > 2) listener.state0.last = args[2];
		if (args.length > 3) listener.state1.last = args[3];
		listener.executeCommand(commandId);
	}
	
	/**
	 * Gets binding for given command id and event type, which is usually a
	 * mouse or a keyboard event. The {@link Binding} allows to easily modify the 
	 * detail condition for the command invocation, like the combination of 
	 * keys for instance. 
	 * 
	 * @param commandId command id defined by a constant in {@link M}
	 * @param eventType type of SWT {@link Event} 
	 * @return
	 */
	public Binding getBinding(int commandId, int eventType) {
		return listener.getBinding(commandId, eventType);
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
		Preconditions.checkNotNullWithName(listener, "listener");
		TypedListener typedListener = new TypedListener(listener);
		this.listener.listeners.add(SWT.Selection, typedListener);
		this.listener.listeners.add(SWT.DefaultSelection, typedListener);
	}
	
	
	
	/*------------------------------------------------------------------------
	 * Preconditions
	 */

	protected static boolean checkSection(MatrixAxis axis, int section,
			boolean throwException) 
	{
		int sectionCount = axis.layout.model.getSectionCount();
		if (sectionCount < 0) {
			if (throwException) { 
				throw new IllegalArgumentException("Negative item count: " + sectionCount);
			}
			return false;
		}
		if (section < 0) {
			if (throwException) { 
				throw new IndexOutOfBoundsException(MessageFormat.format(
					"Section index ({0}) cannot be negative", section));
			}
			return false;
		}
		if (section >= sectionCount) {
			if (throwException) { 
				throw new IndexOutOfBoundsException(MessageFormat.format(
					"Section index ({0}) must be lower than size ({1})", 
					section, sectionCount));
			}
			return false;
		}
		return true;
	}
	
	protected static boolean checkItem(MatrixAxis axis, AxisItem item, boolean nullable, 
			boolean throwException) 
	{
		// Not null
		if (item == null) {
			if (nullable) return true;
			if (throwException) {
				throw new NullPointerException("Item cannot be null");
			}
			return false;
		}
		
		// Section
		if (!checkSection(axis, item.section, throwException)) return false;
		
		// Index
		Index itemCount = axis.layout.model.getItemCount(item.section);
		Index ZERO = axis.layout.factory.ZERO();
		if (itemCount.compareTo(ZERO) < 0) {
			if (throwException) { 
				throw new IllegalArgumentException("Negative item count: " + itemCount);
			}
			return false;
		}
		if (item.index.compareTo(ZERO) < 0) {
			if (throwException) { 
				throw new IndexOutOfBoundsException(MessageFormat.format(
					"Item index ({0}) cannot be negative", item.index));
			}
			return false;
		}
		if (item.index.compareTo(itemCount) >= 0) {
			if (throwException) { 
				throw new IndexOutOfBoundsException(MessageFormat.format(
					"Item index ({0}) must be lower than size ({1})", 
					item.index, itemCount));
			}
			return false;
		}
		return true;
	}


	

}


