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
import pl.netanel.swt.matrix.Layout.LayoutSequence;
import pl.netanel.swt.matrix.painter.BorderPainter;
import pl.netanel.swt.matrix.painter.Painter;
import pl.netanel.swt.matrix.painter.Painters;


public class Matrix extends Canvas {

	MatrixModel model;
	Axis axis0, axis1;
	Layout layout0, layout1;
	MatrixListener listener;
	Listener listener2;
	
	Rectangle area; 
	private Painter backgroundPainter, navigationPainter;
	private ScheduledExecutorService executor;
	
	public Matrix(Composite parent, int style) {
		this(parent, style, null, null);
	}
	
	public Matrix(Composite parent, int style, Axis axis0, Axis axis1, Zone ...zones) {
		super(parent, style | SWT.DOUBLE_BUFFERED);
		setBackground(Resources.getColor(SWT.COLOR_LIST_BACKGROUND));
		setForeground(Resources.getColor(SWT.COLOR_LIST_FOREGROUND));
		navigationPainter = new BorderPainter(2);
		
		if (axis0 == null) {
			axis0 = new Axis<Integer>();
			axis0.getHeader().setVisible(false);
			axis0.setAutoScrollOffset(M.AUTOSCROLL_OFFSET_Y);
			axis0.setResizeOffset(M.RESIZE_OFFSET_Y);
		}
		if (axis1 == null) {
			axis1 = new Axis<Integer>();
			axis1.getHeader().setDefaultCellWidth(40);
			axis1.getHeader().setVisible(false);
			axis1.getBody().setDefaultCellWidth(50);
			axis1.setAutoScrollOffset(M.AUTOSCROLL_OFFSET_X);
			axis1.setResizeOffset(M.RESIZE_OFFSET_X);
		}
		axis0.matrix = this; axis0.index = 0;
		axis1.matrix = this; axis1.index = 1;
		model = new MatrixModel(axis0, axis1, zones);
		setModel(model);
		
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
		
		axis0 = model.axis0;
		axis1 = model.axis1;
		layout0 = model.axis0.layout;
		layout1 = model.axis1.layout;
		axis0.setMatrix(this, 0);
		axis1.setMatrix(this, 1);
		
		Zone body = model.getBody();
//		if (body.getDefaultBackground() == null) {
//			body.setDefaultBackground(getBackground());
//		}
		if (body.getDefaultForeground() == null) {
			body.setDefaultForeground(getForeground());
		}
		
		this.listener = new MatrixListener(this);
		listener.setLayout(layout0, layout1);
		selectCurrent();
	}
	
	private void selectCurrent() {
		layout0.compute();
		layout1.compute();
		if (model.getBody().isSelectionEnabled() && layout0.current != null && layout1.current != null) {
			Zone zone = model.getZone(layout0.current.section, layout1.current.section);
			Number index0 = layout0.current.index;
			Number index1 = layout1.current.index;
			zone.setSelected(index0, index0, index1, index1, true);
		}
	}

	
	protected void onPaint(Event event) {
//		long t = System.nanoTime();
		final GC gc = event.gc;
		layout0.computeIfRequired();
		layout1.computeIfRequired();
		
		paint(gc, backgroundPainter, area.x, area.y, area.width, area.height);

		paintDock(gc, Dock.MAIN, Dock.MAIN);
		paintDock(gc, Dock.TAIL, Dock.HEAD);
		paintDock(gc, Dock.HEAD, Dock.TAIL);
		paintDock(gc, Dock.TAIL, Dock.MAIN);
		paintDock(gc, Dock.MAIN, Dock.TAIL);
		paintDock(gc, Dock.TAIL, Dock.TAIL);
		paintDock(gc, Dock.HEAD, Dock.MAIN);
		paintDock(gc, Dock.MAIN, Dock.HEAD);
		paintDock(gc, Dock.HEAD, Dock.HEAD);
		
		gc.setClipping((Rectangle) null);
		if (layout0.current != null && layout1.current != null) {
			Bound b0 = layout0.getBound(layout0.current);
			Bound b1 = layout1.getBound(layout1.current);
			if (b0 != null && b1 != null) {
				paint(gc, navigationPainter, b1.distance, b0.distance, b1.width, b0.width);
			}
		}
		
//		System.out.println(BigDecimal.valueOf(System.nanoTime() - t, 6).toString());
	}

	private void paint(GC gc, Painter painter, int x, int y, int width, int height) {
		if (painter == null || !painter.isEnabled()) return;
		painter.init(gc);
		painter.paint(x, y, width, height);
		painter.clean();
	}

	private void paintDock(GC gc, Dock dock0, Dock dock1) {
		for (Zone zone: model) {
			if (!layout0.contains(dock0, zone.section0) ||
				!layout1.contains(dock1, zone.section1) ) continue;
			
//			if (zone == null || !zone.isVisible()) continue;
			
			Bound bb0 = layout0.getBound(dock0);
			Bound bb1 = layout1.getBound(dock1);
//			if (dock0 == Dock.MAIN && dock1 == Dock.MAIN) {
//				gc.setForeground(Resources.getColor(SWT.COLOR_BLUE));
//				gc.drawRectangle(bb1.distance, bb0.distance, bb1.width, bb0.width);;
//			}
			gc.setClipping(bb1.distance, bb0.distance, bb1.width, bb0.width);
			
			Bound b0 = layout0.getBound(dock0, zone.section0);
			Bound b1 = layout1.getBound(dock1, zone.section1);
			zone.setBounds(b1.distance, b0.distance, b1.width, b0.width);
			
			// Paint cells
			paintCells(gc, zone.cellPainters, 
					layout0.cellSequence(dock0, zone.section0),
					layout1.cellSequence(dock1, zone.section1) );
			
			// Paint row lines
			LayoutSequence seq;
			seq = layout0.lineSequence(dock0, zone.section0);
			for (Painter painter: zone.linePainters0) {
				if (!painter.isEnabled()) continue;
				painter.init(gc);
				for (seq.init(); seq.next();) {
					painter.paint(b1.distance, seq.getDistance(), b1.width, seq.getWidth());
				}
				painter.clean();
			}
			
			
			// Paint column lines
			seq = layout1.lineSequence(dock1, zone.section1);
			for (Painter painter: zone.linePainters1) {
				if (!painter.isEnabled()) continue;
				painter.init(gc);
				for (seq.init(); seq.next();) {
					painter.paint(seq.getDistance(), b0.distance, seq.getWidth(), b0.width);
				}
				painter.clean();
			}
			
		}
	}

	private void paintCells(GC gc, Painters painters, LayoutSequence seq0, LayoutSequence seq1) {
		for (Painter painter: painters) {
			if (!isEnabled()) return;
			painter.init(gc);
			for (seq0.init(); seq0.next();) {
				Bound b0 = seq0.bound;
				for (seq1.init(); seq1.next();) {
					painter.beforePaint(seq0.item.index, seq1.item.index);
					Bound b1 = seq1.bound;
					painter.paint(b1.distance, b0.distance, b1.width, b0.width);
				}
			}
			painter.clean();
		}
	}
	

	public Painter getBackgroundPainter() {
		return backgroundPainter;
	}

	public void setBackgroundPainter(Painter backgroundPainter) {
		this.backgroundPainter = backgroundPainter;
	}

	public Painter getNavigationPainter() {
		return navigationPainter;
	}

	public void setNavigationPainter(Painter navigationPainter) {
		this.navigationPainter = navigationPainter;
	}

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
		selectCurrent();
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

	public MatrixModel getModel() {
		return model;
	}
	
	
	public Axis getAxis0() {
		return axis0;
	}
	
	public Axis getAxis1() {
		return axis1;
	}
	
	public Zone getBody() {
		return getZone(axis0.getBody(), axis1.getBody());
	}
	public Zone getColumneHeader() {
		return getZone(axis0.getHeader(), axis1.getBody());
	}
	public Zone getRowHeader() {
		return getZone(axis0.getBody(), axis1.getHeader());
	}
	public Zone getTopLeft() {
		return getZone(axis0.getHeader(), axis1.getHeader());
	}
	
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
		return model.getZone(id);
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
	public Zone getZone(Section section0, Section section1) {
		return model.getZone(section0, section1);
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

	public void refresh() {
		layout0.compute();
		layout1.compute();
		updateScrollBars();
		redraw();
	}

	
	

	
	
}
