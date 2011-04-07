package pl.netanel.swt.matrix;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import pl.netanel.swt.Resources;
import pl.netanel.util.Preconditions;

/**
 * The main responsibility of this class is to draw a two dimensional 
 * grid of cells and respond to  the user generated events. 
 * This is the main class in the package.
 *
 * <dl>
 * <dt><b>Styles:</b></dt>
 * <dd>SWT.SINGLE, SWT.MULTI, SWT.NO_FOCUS, SWT.CHECK, SWT.VIRTUAL</dd>
 * <dt><b>Events:</b></dt>
 * <dd>Selection, DefaultSelection</dd>
 * </dl>
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
	
	public Matrix(Composite parent, int style) {
		this(parent, style, null, null);
	}
	
	public Matrix(Composite parent, int style, Axis<N0> axis0, Axis<N1> axis1, Zone<N0, N1> ...zones) {
		super(parent, style | SWT.DOUBLE_BUFFERED);
		setBackground(Resources.getColor(SWT.COLOR_LIST_BACKGROUND));
		setForeground(Resources.getColor(SWT.COLOR_LIST_FOREGROUND));
		
		if (axis0 == null) {
			axis0 = new Axis();
			axis0.getHeader().setVisible(false);
			axis0.setAutoScrollOffset(M.AUTOSCROLL_OFFSET_Y);
			axis0.setResizeOffset(M.RESIZE_OFFSET_Y);
		}
		if (axis1 == null) {
			axis1 = new Axis();
			axis1.getHeader().setDefaultCellWidth(40);
			axis1.getHeader().setVisible(false);
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
	
	
	protected void onPaint(Event event) {
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

	public Axis<N0> getAxis0() {
		return axis0;
	}
	
	public Axis<N1> getAxis1() {
		return axis1;
	}
	
	public Zone<N0, N1> getBody() {
		return getZone(axis0.getBody(), axis1.getBody());
	}
	public Zone<N0, N1> getColumneHeader() {
		return getZone(axis0.getHeader(), axis1.getBody());
	}
	public Zone<N0, N1> getRowHeader() {
		return getZone(axis0.getBody(), axis1.getHeader());
	}
	public Zone<N0, N1> getTopLeft() {
		return getZone(axis0.getHeader(), axis1.getHeader());
	}
	
	public int getZoneCount() {
		return model.zones.size();
	}
	
	/**
	 * Returns a zone by its creation index.  
	 * <p>
	 * @param index
	 * @return zone with the given creation index
	 */
	public Zone<N0, N1> getZone(int index) {
		return model.zoneClients.get(index);
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
	
	
	
	public Rectangle getCellBounds(Section<N0> section0, N0 index0, Section<N1> section1, N1 index1) {
		if (layout0.current != null && layout1.current != null) {
			Bound b0 = axis0.getCellBound(section0, index0);
			Bound b1 = axis1.getCellBound(section1, index1);
			if (b0 != null && b1 != null) {
				return new Rectangle(b1.distance, b0.distance, b1.width, b0.width);
			}
		}
		return null; 
	}
	
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
	
	public void setFocusCellEnabled(boolean enabled) {
		Painter<N0, N1> painter = getPainter("focus cell");
		if (painter != null) painter.setEnabled(enabled);
		focusCellEnabled = enabled;
	}

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

	public void refresh() {
		layout0.compute();
		layout1.compute();
		updateScrollBars();
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

	public void addPainter(Painter<N0, N1> painter) {
		painters.add(painter);
	}

	public void addPainter(int index, Painter<N0, N1> painter) {
		// Check uniqueness of painters names
		painters.add(index, painter);
	}
	
	public void setPainter(int index, Painter<N0, N1> painter) {
		painters.set(index, painter);
	}
	
	public void replacePainter(Painter<N0, N1> painter) {
		painters.replacePainter(painter);
	}
	
	public void removePainter(int index) {
		painters.remove(index);
	}
	
	public int indexOfPainter(String name) {
		return painters.indexOfPainter(name);
	}
	
	public Painter<N0, N1> getPainter(String name) {
		int index = indexOfPainter(name);
		return index == -1 ? null : painters.get(index);
	}
	
	int getPainterCount() {
		return painters.size();
	}
	
	public Painter<N0, N1> get(int index) {
		return painters.get(index);
	}

}
