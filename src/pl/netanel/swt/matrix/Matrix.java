package pl.netanel.swt.matrix;

import java.math.BigDecimal;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import pl.netanel.swt.Resources;
import pl.netanel.swt.matrix.Layout.LayoutSequence;


public class Matrix extends Canvas {

	private MatrixModel model;
	private Axis axis0, axis1;
	private Layout layout0, layout1;
	private Rectangle area; 
	private Painter backgroundPainter, navigationPainter;

	public Matrix(Composite parent, int style) {
		this(parent, style, new MatrixModel());
	}
	
	public Matrix(Composite parent, int style, MatrixModel model) {
		super(parent, style | SWT.DOUBLE_BUFFERED);
		setBackground(Resources.getColor(SWT.COLOR_LIST_BACKGROUND));
		setForeground(Resources.getColor(SWT.COLOR_LIST_FOREGROUND));
		
		setModel(model);
		
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
	}

	private void setModel(MatrixModel model) {
		this.model = model;
		
		axis0 = new Axis(this, 0);
		axis1 = new Axis(this, 1);
		layout0 = axis0.layout;
		layout1 = axis1.layout;
		
		Zone body = model.getBody();
		if (body.getDefaultBackground() == null) {
			body.setDefaultBackground(getBackground());
		}
		if (body.getDefaultForeground() == null) {
			body.setDefaultForeground(getForeground());
		}
	}

	
	protected void onPaint(Event event) {
		long t = System.nanoTime();
		final GC gc = event.gc;
		layout0.computeIfRequired();
		layout1.computeIfRequired();
		
		paint(gc, backgroundPainter, area.x, area.y, area.width, area.height);

		paintDock(gc, Dock.MAIN, Dock.MAIN);
		
		System.out.println(BigDecimal.valueOf(System.nanoTime() - t, 6).toString());
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
			
//				if (zone == null || !zone.isVisible()) continue;

			Bound b0 = layout0.getBound(dock0, zone.section0);
			Bound b1 = layout1.getBound(dock1, zone.section1);
			
			zone.setBounds(b1.distance, b0.distance, b1.width, b0.width);
//			gc.setClipping(b1.distance, b0.distance, b1.width, b0.width);
			
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
				for (seq1.init(); seq1.next();) {
					painter.beforePaint(seq0.item.index, seq1.item.index);
					Bound b0 = seq0.bound;
					Bound b1 = seq1.bound;
					painter.paint(b1.distance, b0.distance, b1.width, b0.width);
					painter.afterPaint(seq0.item.index, seq1.item.index);
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
//		layout0.compute();
//		layout1.compute();
		
//		updateScrollBars();
		
	}
	
	public Axis getAxis0() {
		return axis0;
	}
	
	public Axis getAxis1() {
		return axis1;
	}
	
	public MatrixModel getModel() {
		return model;
	}
	
	
	
	/*------------------------------------------------------------------------
	 * Helper 
	 */

	/**
	 * Stops the executor and re-throws wrapped in RuntimeException
	 */
	protected void rethrow(Throwable e) {
		// Stop auto scroll
//		if (executor != null) {
//			Matrix.this.listener.state0.autoScroll.stop();
//			Matrix.this.listener.state1.autoScroll.stop();
//			executor.shutdown();
//			try {
//				executor.awaitTermination(1, TimeUnit.SECONDS);
//			} 
//			catch (InterruptedException e1) { }
//		}
		
		throw e instanceof RuntimeException ? (RuntimeException) e : new RuntimeException(e); 
	}

	
}
