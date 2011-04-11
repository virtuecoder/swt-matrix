package pl.netanel.swt.matrix;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Widget;

import pl.netanel.swt.Resources;
import pl.netanel.util.Preconditions;

/**
 * Draws a two dimensional grid of cells and responds to the user generated
 * events. This is the main class in the package.
 * 
 * <dl>
 * <dt><b>Styles:</b></dt>
 * <dd>SWT.SINGLE, SWT.MULTI, SWT.NO_FOCUS, SWT.CHECK, SWT.VIRTUAL</dd>
 * <dt><b>Events:</b></dt>
 * <dd>Selection, DefaultSelection</dd>
 * </dl>
 * @param <N0> defines indexing type for rows
 * @param <N1> defines indexing type for columns 
 * 
 * @author Jacek
 * @created 27-03-2011
 */
public class Matrix<N0 extends Number, N1 extends Number> extends Canvas {

	MatrixModel<N0, N1> model;
	Axis<N0> axis0;
	Axis<N1> axis1;
	Layout<N0> layout0;
	Layout<N1> layout1;
	MatrixListener listener;
	Listener listener2;
	
	Rectangle area; 
	final Painters<N0, N1> painters;
	private ScheduledExecutorService executor;
	
	private boolean focusCellEnabled = true;
	
	/**
	 * Calls the {@link #Matrix(Composite, int, Axis, Axis, Zone...)} constructor 
	 * with <code>null</code> values for <code>axis0</code> and <code>axis1</code>. 
	 * @see #Matrix(Composite, int, Axis, Axis, Zone...)
	 */
	public Matrix(Composite parent, int style) {
		this(parent, style, null, null);
	}
	
	/**
	 * Constructs a new instance of this class given its parent
	 * and a style value describing its behavior and appearance.
	 * <p>
	 * The style value is either one of the style constants defined in
	 * class <code>SWT</code> which is applicable to instances of this
	 * class, or must be built by <em>bitwise OR</em>'ing together 
	 * (that is, using the <code>int</code> "|" operator) two or more
	 * of those <code>SWT</code> style constants. The class description
	 * lists the style constants that are applicable to the class.
	 * Style bits are also inherited from superclasses.
	 * </p><p>
	 * It the <code>axis0</code> or <code>axis1</code> is null then 
	 * the axis is created with the {@link Axis#Axis()} constructor.
	 * 
	 * </p><p>
	 * The default colors are 
	 *
	 * @param parent a composite control which will be the parent of the new instance (cannot be null)
	 * @param style the style of control to construct
	 * @param axis0 vertical axis for the matrix
	 * @param axis1 horizontal axis for the matrix
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the parent is null</li>
	 * </ul>
	 * @exception SWTException <ul>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the parent</li>
	 *    <li>ERROR_INVALID_SUBCLASS - if this class is not an allowed subclass</li>
	 * </ul>
	 *
	 * @see Widget#getStyle
	 */
	public Matrix(Composite parent, int style, Axis<N0> axis0, Axis<N1> axis1, Zone<N0, N1> ...zones) {
		super(parent, style | SWT.DOUBLE_BUFFERED);
		setBackground(Resources.getColor(SWT.COLOR_LIST_BACKGROUND));
		setForeground(Resources.getColor(SWT.COLOR_LIST_FOREGROUND));
		
		if (axis0 == null) {
			axis0 = new Axis();
			axis0.setAutoScrollOffset(M.AUTOSCROLL_OFFSET_Y);
			axis0.setResizeOffset(M.RESIZE_OFFSET_Y);
		}
		if (axis1 == null) {
			axis1 = new Axis();
			axis1.getHeader().setDefaultCellWidth(40);
			axis1.getBody().setDefaultCellWidth(50);
			axis1.setAutoScrollOffset(M.AUTOSCROLL_OFFSET_X);
			axis1.setResizeOffset(M.RESIZE_OFFSET_X);
		}
		model = new MatrixModel(axis0, axis1, zones);
		painters = new Painters();

		setModel(model);
		setDefaultPainters();
		
		listener2 = new Listener() {
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
		
		addListener(SWT.Paint, listener2);
		addListener(SWT.Resize,	listener2);
		
		addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				if (executor != null) executor.shutdownNow();
			}
		});
		
	}


	private void setModel(MatrixModel model) {
		this.model = model;
		model.setMatrix(this);
		
		axis0 = model.axis0;
		axis1 = model.axis1;
		layout0 = model.axis0.layout;
		layout1 = model.axis1.layout;
		axis0.setMatrix(this, 0);
		axis1.setMatrix(this, 1);
		
		Zone<N0, N1> body = model.getBody();
//		if (body.getDefaultBackground() == null) {
//			body.setDefaultBackground(getBackground());
//		}
		if (body.getDefaultForeground() == null) {
			body.setDefaultForeground(getForeground());
		}
		
		this.listener = new MatrixListener(this);
		listener.setLayout(layout0, layout1);
		selectFocusCell();
	}
	
	
	
	/*------------------------------------------------------------------------
	 * Painting 
	 */
	
	private void setDefaultPainters() {
		
		painters.add(new DockPainter(Frozen.NONE, Frozen.NONE));
		painters.add(new DockPainter(Frozen.TAIL, Frozen.HEAD));
		painters.add(new DockPainter(Frozen.HEAD, Frozen.TAIL));
		painters.add(new DockPainter(Frozen.NONE, Frozen.TAIL));
		painters.add(new DockPainter(Frozen.TAIL, Frozen.NONE));
		painters.add(new DockPainter(Frozen.TAIL, Frozen.TAIL));
		painters.add(new DockPainter(Frozen.NONE, Frozen.HEAD));
		painters.add(new DockPainter(Frozen.HEAD, Frozen.NONE));
		painters.add(new DockPainter(Frozen.HEAD, Frozen.HEAD));
		
		painters.add(new Painter("focus cell") {
			@Override
			public void paint(Number index0, Number index1, int x, int y, int width, int height) {
				Rectangle r = getCellBounds(
						axis0.getFocusSection(), axis0.getFocusIndex(), 
						axis1.getFocusSection(), axis1.getFocusIndex() );
				if (r == null) return;
				gc.setClipping((Rectangle) null);
				gc.setLineWidth(2);
				gc.setForeground(Resources.getColor(SWT.COLOR_BLACK));
				gc.drawRectangle(r);
			}
		});
	}
	
	class DockPainter extends Painter {
		private final Frozen dock0;
		private final Frozen dock1;

		public DockPainter(Frozen dock0, Frozen dock1) {
			super("frozen " + dock0.name().toLowerCase() + " " + dock1.name().toLowerCase());
			this.dock0 = dock0;
			this.dock1 = dock1;
		}
		
		@Override
		public void paint(Number index0, Number index1, int x, int y, int width, int height) {
			Bound bb0 = layout0.getBound(dock0);
			Bound bb1 = layout1.getBound(dock1);
			
			for (Zone<N0, N1> zone: model) {
				if (!layout0.contains(dock0, zone.section0) ||
					!layout1.contains(dock1, zone.section1) ) continue;
				
//				if (zone == null || !zone.isVisible()) continue;
				gc.setClipping(bb1.distance, bb0.distance, bb1.width, bb0.width);
				
				Bound b0 = layout0.getBound(dock0, zone.section0);
				Bound b1 = layout1.getBound(dock1, zone.section1);
				zone.setBounds(b1.distance, b0.distance, b1.width, b0.width);
				
				// Paint cells
				zone.paint(gc, layout0, layout1, dock0, dock1);
			}
		}
	}
	
	void onPaint(Event event) {
//		long t = System.nanoTime();
		final GC gc = event.gc;
		layout0.computeIfRequired();
		layout1.computeIfRequired();
		
		for (Painter<N0, N1> p: painters) {
			if (!p.isEnabled() || !p.init(gc)) continue;
			p.paint(null, null, area.x, area.y, area.width, area.height);
		}
		
//		System.out.println(BigDecimal.valueOf(System.nanoTime() - t, 6).toString());
	}


	
	
	/*------------------------------------------------------------------------
	 * Resize and scrolling
	 */

	/**
	 * Resize event handler.
	 * @param event
	 */
	private void resize() {
		// Return if there is no model provided yet.
		if (layout0 == null) return;
				
		area = getClientArea();
		
		// Return if the widget does not have an area to draw yet.
		if (area.width == 0) return;
		
		layout0.setViewportSize(area.height);
		layout1.setViewportSize(area.width);
		selectFocusCell();
//		layout0.compute();
//		layout1.compute();
		
		updateScrollBars();
		
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
			model.axis0.updateScrollBarVisibility(area.height);
			model.axis1.updateScrollBarVisibility(area.width);
			area = getClientArea();
		}
		else if (model.axis0.updateScrollBarVisibility(area.height)) {
			area = getClientArea();			 
			model.axis1.updateScrollBarVisibility(area.width);
			model.axis0.updateScrollBarVisibility(area.height);
			area = getClientArea();
		}
		
		model.axis1.updateScrollBarValues(area.width);
		model.axis0.updateScrollBarValues(area.height);
		
		area = getClientArea();
	}

	/**
	 * Returns the row (vertical) axis.
	 * @return the row (vertical) axis
	 */
	public Axis<N0> getAxis0() {
		return axis0;
	}
	
	/**
	 * Returns the column (horizontal) axis.
	 * @return the column (horizontal) axis
	 */
	public Axis<N1> getAxis1() {
		return axis1;
	}
	
	/**
	 * Returns the checked body zone of this matrix.
	 * <p>
	 * A checked zone delegates calls to an unchecked zone proceeding it with 
	 * an argument validation checking.
	 * @return
	 */
	public Zone<N0, N1> getBody() {
		return getZone(axis0.getBody(), axis1.getBody());
	}
	/**
	 * Returns the checked column header zone of this matrix.
	 * <p>
	 * A checked zone delegates calls to an unchecked zone proceeding it with 
	 * an argument validation checking.
	 * @return
	 */
	public Zone<N0, N1> getColumneHeader() {
		return getZone(axis0.getHeader(), axis1.getBody());
	}
	/**
	 * Returns the checked row header zone of this matrix.
	 * <p>
	 * A checked zone delegates calls to an unchecked zone proceeding it with 
	 * an argument validation checking.
	 * @return
	 */
	public Zone<N0, N1> getRowHeader() {
		return getZone(axis0.getBody(), axis1.getHeader());
	}
	/**
	 * Returns the checked top left zone of this matrix.
	 * <p>
	 * A checked zone delegates calls to an unchecked zone proceeding it with 
	 * an argument validation checking.
	 * @return
	 */
	public Zone<N0, N1> getTopLeft() {
		return getZone(axis0.getHeader(), axis1.getHeader());
	}
	
	/**
	 * Returns the number of zones in this matrix.
	 * @return the number of zones in this matrix
	 */
	public int getZoneCount() {
		return model.zones.size();
	}
	
	/**
	 * Returns a unchecked zone by its creation index.  
	 * <p>
	 * @param index
	 * @return zone with the given creation index
	 */
	public Zone<N0, N1> getZone(int index) {
		return model.zoneClients.get(index);
	}

	/**
	 * Returns a checked zone for the specified zone. 
	 * If the given zone is checked then the same object is returned.  
	 * <p>
	 * @param index
	 * @return zone with the given creation index
	 */
	public Zone<N0, N1> getZone(Zone<N0, N1> zone) {
		if (zone instanceof ZoneClient) return zone;
		return model.zoneClients.get(model.zones.indexOf(zone));
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
	public Zone<N0, N1> getZone(Section section0, Section section1) {
		return model.getZone(section0, section1);
	}
	
	
	/**
	 * Return the rectangular bounds of the cell with the given coordinates.
	 * 
	 * @param section0 section in the row (vertical) axis where the cell is located 
	 * @param index0 index in <code>section0</code> of the cell 
	 * @param section1 section in the column (horizontal) axis where the cell is located
	 * @param index1 index in <code>section1</code> of the cell 
	 * @return
	 */
	public Rectangle getCellBounds(Section<N0> section0, N0 index0, Section<N1> section1, N1 index1) {
		Bound b0 = axis0.getCellBound(section0, index0);
		Bound b1 = axis1.getCellBound(section1, index1);
		if (b0 != null && b1 != null) {
			return new Rectangle(b1.distance, b0.distance, b1.width, b0.width);
		}
		return null; 
	}
	
//	public Rectangle getLineBounds(Section<N0> section0, N0 index0, Section<N1> section1, N1 index1) {
//		Bound b0 = index0 == null ? axis0.layout.getaxis0.getLineBound(section0, index0);
//		Bound b1 = axis1.getCellBound(section1, index1);
//		if (b0 != null && b1 != null) {
//			return new Rectangle(b1.distance, b0.distance, b1.width, b0.width);
//		}
//		return null; 
//	}
	
	private void selectFocusCell() {
		if (!focusCellEnabled) return;
		layout0.compute();
		layout1.compute();
		if (layout0.current != null && layout1.current != null) {
			Zone<N0, N1> zone = model.getZone(layout0.current.section, layout1.current.section);
			N0 index0 = layout0.current.index;
			N1 index1 = layout1.current.index;
			zone.setSelected(index0, index0, index1, index1, true);
		}
	}
	
	/**
	 * Enables current cell navigation in the receiver if the argument is <code>true</code>,
	 * and disables it invisible otherwise.
	 * <p>
	 * If the focus cell is disabled the navigation events are ignored and the 
	 * "focus cell" painter of the matrix is disabled. 
	 *
	 * @param enabled the new focus cell enablement state
	 */
	public void setFocusCellEnabled(boolean enabled) {
		Painter<N0, N1> painter = getPainter("focus cell");
		if (painter != null) painter.setEnabled(enabled);
		focusCellEnabled = enabled;
	}

	/**
	 * Returns <code>true</code> if the focus cell navigation is enabled in the receiver. 
	 * Otherwise, <code>false</code> is returned.
	 *
	 * @return the receiver's focus item enablement state
	 */
	public boolean isFocusCellEnabled() {
		return focusCellEnabled;
	}
	
	
	/*------------------------------------------------------------------------
	 * Helper 
	 */

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
	 * Stops the executor and re-throws wrapped in RuntimeException
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
		
		throw e instanceof RuntimeException ? (RuntimeException) e : new RuntimeException(e); 
	}
	
	void selectInZones(int axisIndex, Section section, Number start, Number end) {
		if (axisIndex == 0) {
			for (Zone zone: model.zones) {
				if (zone.section0.equals(section)) {
					Math math1 = zone.section1.math;
					zone.setSelected(
							(N0) start, (N0) end, 
							math1.ZERO_VALUE(), math1.decrement(zone.section1.getCount()), 
							true);
				}
			}
		}
		else { // assert index == 1
			for (Zone zone: model.zones) {
				if (zone.section1.equals(section)) {
					Math math0 = zone.section0.math;
					zone.setSelected( 
							math0.ZERO_VALUE(), math0.decrement(zone.section0.getCount()),
							start, end,
							true);
				}
			}
		}
	}

	/**
	 * Recalculates the matrix layout and repaints it. 
	 * It can be called after changing the model for example to reflect the changes on the screen. 
	 */
	public void refresh() {
		// After freeze head to 0 is it would scroll to previous head count.
		layout0.start = layout0.current;
		layout1.start = layout1.current;
		layout0.compute();
		layout1.compute();
		updateScrollBars();
		listener.refresh();
		redraw();
	}

//	@Override
//	public Iterator<Zone<N0, N1>> iterator() {
//		return model.zoneClients.iterator();
//	}

	
	
	/*------------------------------------------------------------------------
	 * Painters 
	 */

	public int[] getZonePaintOrder() {
		return model.paintOrder;
	}
	
	public void setZonePaintOrder(int[] order) {
		Preconditions.checkArgument(order.length == model.paintOrder.length, 
				"The length of the order array ({0}) must be equal to the number of of zones: ({1})", 
				order.length, model.paintOrder.length);
		model.paintOrder = order;
	}


	/**
	 * Adds the painter at the end of the receiver's painters list.
	 * @param painter the painter to be added
	 */
	public void addPainter(Painter<N0, N1> painter) {
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
		painters.set(index, painter);
		setPainterMatrixAndZone(painter);
	}
	
	/**
	 * Replaces the painter at the index of painter with the same name.
	 * @param painter painter to replace a painter with the same name
	 * @throws IndexOutOfBoundsException if there is no painter with the same name
	 * @see #getPainter(String)
	 */
	public void replacePainter(Painter<N0, N1> painter) {
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
		return painters.get(index);
	}

	private void setPainterMatrixAndZone(Painter painter) {
		if (painter.scope == Painter.SCOPE_CELLS_HORIZONTALLY ||
				painter.scope == Painter.SCOPE_CELLS_VERTICALLY) 
		{
			painter.matrix = this;
		}
	}
}
