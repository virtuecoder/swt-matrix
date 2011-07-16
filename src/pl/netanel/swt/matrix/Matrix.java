package pl.netanel.swt.matrix;

import java.util.Iterator;
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

import pl.netanel.util.ImmutableIterator;
import pl.netanel.util.Preconditions;

/**
 * This class represents a two dimensional grid of cells. 
 * Uses custom painter to paint itself on the screen and responds to the user generated
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
public class Matrix<N0 extends Number, N1 extends Number> extends Canvas 
	implements Iterable<Zone<N0, N1>> 
{
	static final int CELL_WIDTH = 16;			
	static final int LINE_WIDTH = 1;
	static final int RESIZE_OFFSET_X = 3;
	static final int RESIZE_OFFSET_Y = 2;
	static final int AUTOSCROLL_OFFSET_X = 8;
	static final int AUTOSCROLL_OFFSET_Y = 6;
	static final int AUTOSCROLL_RATE = 50;

	public static final int PRINTABLE_CHARS = 10;
	
	/*
	 *  Navigation Key Actions. Key bindings for the actions are set
	 *  by the StyledText widget.
	 */	
	public static final int CMD_FOCUS_UP = 1; 					// binding = SWT.ARROW_UP
	public static final int CMD_FOCUS_DOWN = 2; 				// binding = SWT.ARROW_DOWN
	public static final int CMD_FOCUS_LEFT = 3; 				// binding = SWT.ARROW_LEFT
	public static final int CMD_FOCUS_RIGHT = 4; 				// binding = SWT.ARROW_RIGHT
	public static final int CMD_FOCUS_PAGE_UP = 5; 			// binding = SWT.PAGE_UP
	public static final int CMD_FOCUS_PAGE_DOWN = 6; 			// binding = SWT.PAGE_DOWN
	public static final int CMD_FOCUS_PAGE_LEFT = 7; 			// binding = SWT.MOD3 + SWT.PAGE_UP
	public static final int CMD_FOCUS_PAGE_RIGHT = 8; 			// binding = SWT.MOD3 + SWT.PAGE_DOWN
	public static final int CMD_FOCUS_MOST_LEFT = 9;			// binding = SWT.HOME
	public static final int CMD_FOCUS_MOST_RIGHT = 10; 		// binding = SWT.END
	public static final int CMD_FOCUS_MOST_UP = 11; 			// binding = SWT.MOD1 + SWT.PAGE_UP
	public static final int CMD_FOCUS_MOST_DOWN = 12; 			// binding = SWT.MOD1 + SWT.PAGE_DOWN
	public static final int CMD_FOCUS_MOST_UP_LEFT = 13; 				// binding = SWT.MOD1 + SWT.HOME
	public static final int CMD_FOCUS_MOST_DOWN_RIGHT = 14; 				// binding = SWT.MOD1 + SWT.END
	public static final int CMD_FOCUS_LOCATION = 15; 			// binding = SWT.MouseDown
	public static final int CMD_FOCUS_LOCATION_ALTER = 16; 			// binding = SWT.MOD1 + SWT.MouseDown
//	public static final int WORD_PREVIOUS = 17039363;		// binding = SWT.MOD1 + SWT.ARROW_LEFT
//	public static final int WORD_NEXT = 17039364; 			// binding = SWT.MOD1 + SWT.ARROW_RIGHT
	
	
	static boolean isCursorMove(int id) {
		return CMD_FOCUS_UP <= id && id <= CMD_FOCUS_LOCATION_ALTER;
	}
	

	/* 
	 * Selection Key Actions 
	 */
	public static final int CMD_SELECT_ALL = 100; 				// binding = SWT.MOD1 + 'A'
	public static final int CMD_SELECT_UP = 101; 				// binding = SWT.MOD2 + SWT.ARROW_UP
	public static final int CMD_SELECT_DOWN = 102; 				// binding = SWT.MOD2 + SWT.ARROW_DOWN
	public static final int CMD_SELECT_LEFT = 103; 				// binding = SWT.MOD2 + SWT.ARROW_LEFT
	public static final int CMD_SELECT_RIGHT = 104; 			// binding = SWT.MOD2 + SWT.ARROW_RIGHT
	public static final int CMD_SELECT_PAGE_UP = 105; 			// binding = SWT.MOD2 + SWT.PAGE_UP
	public static final int CMD_SELECT_PAGE_DOWN = 106; 		// binding = SWT.MOD2 + SWT.PAGE_DOWN
	public static final int CMD_SELECT_PAGE_LEFT = 107; 		// binding = SWT.MOD2 + SWT.MOD3 + SWT.ARROW_LEFT
	public static final int CMD_SELECT_PAGE_RIGHT = 108; 		// binding = SWT.MOD2 + SWT.MOD3 + SWT.ARROW_RIGHT
	public static final int CMD_SELECT_FULL_UP = 109; 			// binding = SWT.MOD1 + SWT.MOD2 + SWT.ARROW_UP
	public static final int CMD_SELECT_FULL_DOWN = 110;   		// binding = SWT.MOD1 + SWT.MOD2 + SWT.ARROW_DOWN
	public static final int CMD_SELECT_FULL_LEFT = 111; 		// binding = SWT.MOD2 + SWT.HOME
	public static final int CMD_SELECT_FULL_RIGHT = 112;   		// binding = SWT.MOD2 + SWT.END
	public static final int CMD_SELECT_FULL_UP_LEFT = 113; 			// binding = SWT.MOD1 + SWT.MOD2 + SWT.HOME
	public static final int CMD_SELECT_FULL_DOWN_RIGHT = 114; 				// binding = SWT.MOD1 + SWT.MOD2 + SWT.END
	public static final int CMD_SELECT_TO_LOCATION = 115; 		// binding = SWT.MOD2 + SWT.MouseDown
	public static final int CMD_SELECT_TO_LOCATION_ALTER = 116; 		// binding = SWT.MOD2 + SWT.MouseDown
	public static final int CMD_SELECT_ROW = 120; 				// binding = SWT.MouseDown + Zone.ROW_HEADER
	public static final int CMD_SELECT_ROW_ALTER = 121; 				// binding = SWT.MOD1 + SWT.MouseDown + Zone.ROW_HEADER
	public static final int CMD_SELECT_COLUMN = 122; 			// binding = SWT.MouseDown + Zone.COLUMN_HEADER
	public static final int CMD_SELECT_COLUMN_ALTER = 123; 			// binding = SWT.MOD1 + SWT.MouseDown + Zone.COLUMN_HEADER
	public static final int CMD_SELECT_TO_ROW = 124; 			// binding = SWT.MouseDown + Zone.ROW_HEADER
	public static final int CMD_SELECT_TO_ROW_ALTER = 125; 			// binding = SWT.MOD1 + SWT.MouseDown + Zone.ROW_HEADER
	public static final int CMD_SELECT_TO_COLUMN = 126;			// binding = SWT.MouseDown + Zone.COLUMN_HEADER
	public static final int CMD_SELECT_TO_COLUMN_ALTER = 127;		// binding = SWT.MOD1 + SWT.MouseDown + Zone.COLUMN_HEADER
	
	static boolean isBodySelect(int id) {
		return CMD_FOCUS_UP <= id && id <= CMD_SELECT_TO_LOCATION_ALTER;
	}
	
	static boolean isHeaderSelect(int id) {
		return CMD_SELECT_ROW <= id && id <= CMD_SELECT_TO_COLUMN_ALTER;
	}
	
	static boolean isExtendingSelect(int id) {
		return CMD_SELECT_UP <= id && id <= CMD_SELECT_TO_LOCATION_ALTER || 
			CMD_SELECT_TO_ROW <= id && id <= CMD_SELECT_TO_COLUMN_ALTER;
	}

	/*
	 *  Modification Key Actions 
	 */
	public static final int CMD_CUT = 200; 						// binding = SWT.MOD2 + SWT.DEL
	public static final int CMD_COPY = 201; 					// binding = SWT.MOD1 + SWT.INSERT;
	public static final int CMD_PASTE = 202;					// binding = SWT.MOD2 + SWT.INSERT ;

	/**
	 * Command to activate the editor control by setting focus on it.
	 */
	public static final int CMD_EDIT_ACTIVATE = 205;						
  /**
   * Command to deactivate the editor control with applying it's value to the model. 
   */
	public static final int CMD_EDIT_DEACTIVATE_APPLY = 206;						
  /**
   * Command to deactivate the editor control without applying it's value to the model.
   */
	public static final int CMD_EDIT_DEACTIVATE_CANCEL = 207;						
	/**
	 * Command to apply the value from the editor control to the model without deactivating the control.
	 */
	public static final int CMD_EDIT_APPLY = 208;			
	/**
	 * Command to delete (set to null) the values of the selected cells.
	 */
	public static final int CMD_DELETE = 209;						
	
	public static final int CMD_ITEM_HIDE = 301;						// binding = SWT.MOD3 + SWT.DEL;
	public static final int CMD_ITEM_SHOW = 302;					// binding = SWT.MOD3 + SWT.INSERT;
	
	static final int RESIZE_START = 320;					    
	static final int RESIZE_STOP = 321;					    
	static final int CMD_RESIZE_PACK = 322;					   
	
	public static final int CMD_TRAVERSE_TAB_NEXT = 400;       // binding = SWT.TAB
	public static final int CMD_TRAVERSE_TAB_PREVIOUS = 401;       // binding = SWT.MOD2 + SWT.TAB

	

	/*------------------------------------------------------------------------
	 * Mouse event modifiers, cannot collide with SWT state masks or mouse button numbers
	 */

//	static final int RESIZE_AREA = 1 << 26;
	
	
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
	 * </p><p>
	 * If any of <code>zones</code> has zero painters then default painters are attached. 
	 * </p><p> 
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
	public Matrix(Composite parent, int style, Axis<N0> axis0, Axis<N1> axis1, Zone ...zones) {
		super(parent, style | SWT.DOUBLE_BUFFERED);
		setBackground(Resources.getColor(SWT.COLOR_LIST_BACKGROUND));
		setForeground(Resources.getColor(SWT.COLOR_LIST_FOREGROUND));
		
		if (axis0 == null) {
			axis0 = new Axis();
			axis0.setAutoScrollOffset(Matrix.AUTOSCROLL_OFFSET_Y);
			axis0.setResizeOffset(Matrix.RESIZE_OFFSET_Y);
		}
		if (axis1 == null) {
			axis1 = new Axis();
			axis1.getHeader().setDefaultCellWidth(40);
			axis1.getBody().setDefaultCellWidth(50);
			axis1.setAutoScrollOffset(Matrix.AUTOSCROLL_OFFSET_X);
			axis1.setResizeOffset(Matrix.RESIZE_OFFSET_X);
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
		
//		parent.addControlListener(new ControlListener() {
//      @Override public void controlResized(ControlEvent e) {
//        for (Zone zone: Matrix.this) {
//          if (zone.editor != null) {
//            zone.editor.embedded.clearControls();
//          }
//        }
//      }
//      
//      @Override public void controlMoved(ControlEvent e) {}
//    });
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
	}
	
	
	
	/*------------------------------------------------------------------------
	 * Painting 
	 */
	
	private void setDefaultPainters() {
		
		painters.add(new DockPainter(Frozen.NONE, Frozen.NONE));
		painters.add(new DockPainter(Frozen.NONE, Frozen.TAIL));
		painters.add(new DockPainter(Frozen.TAIL, Frozen.NONE));
		painters.add(new DockPainter(Frozen.NONE, Frozen.HEAD));
		painters.add(new DockPainter(Frozen.HEAD, Frozen.NONE));
		painters.add(new DockPainter(Frozen.HEAD, Frozen.TAIL));
		painters.add(new DockPainter(Frozen.TAIL, Frozen.HEAD));
		painters.add(new DockPainter(Frozen.TAIL, Frozen.TAIL));
		painters.add(new DockPainter(Frozen.HEAD, Frozen.HEAD));
		
		painters.add(new Painter("focus cell") {
			@Override
			public void paint(Number index0, Number index1, int x, int y, int width, int height) {
				AxisItem<N0> item0 = axis0.getFocusItem();
				AxisItem<N1> item1 = axis1.getFocusItem();
				if (item0 == null || item1 == null) return;
				Zone zone = Matrix.this.getZone(item0.getSection(), item1.getSection());
				if (zone == null) return;
				Rectangle r = zone.getCellBounds(item0.getIndex(), item1.getIndex());
				if (r == null) return;
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
		
		@Override
		public void clean() {
			super.clean();
			gc.setClipping((Rectangle) null);
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
			p.clean();
		}
//		gc.setBackground(Resources.getColor(SWT.COLOR_RED));
//		gc.fillRectangle(51, 193, 3, 18);
		
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
		
//		listener.state0.item = layout0.current;
//		listener.state1.item = layout1.current;
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
	 * Returns the body zone of this matrix.
	 * @return the body zone of this matrix
	 */
	public Zone<N0, N1> getBody() {
		return getZone(axis0.getBody(), axis1.getBody());
	}
	/**
	 * Returns the column header zone of this matrix.
	 * @return the column header zone of this matrix
	 */
	public Zone<N0, N1> getColumnHeader() {
		return getZone(axis0.getHeader(), axis1.getBody());
	}
	/**
	 * Returns the row header zone of this matrix.
	 * @return the row header zone of this matrix
	 */
	public Zone<N0, N1> getRowHeader() {
		return getZone(axis0.getBody(), axis1.getHeader());
	}
	/**
	 * Returns the top left zone of this matrix.
	 * @return the top left zone of this matrix
	 */
	public Zone<N0, N1> getTopLeft() {
		return getZone(axis0.getHeader(), axis1.getHeader());
	}

	/**
	 * Returns a zone located at the intersection of the given axis sections.
	 * 
	 * @param section0 section of the row axis
	 * @param section1 section of the column axis
	 * @return zone located at the intersection of the given axis sections
	 * @throws IllegalArgumentException if item is <code>null</code> or section
	 * 		does not belong to an axis of this matrix.
	 */
	public Zone<N0, N1> getZone(Section section0, Section section1) {
	  Preconditions.checkNotNullWithName(section0, "section0");
	  Preconditions.checkNotNullWithName(section1, "section1");
	  axis0.checkSection(section0);
	  axis1.checkSection(section1);
	  return model.getZoneUnchecked(section0, section1);
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
	void rethrow(Throwable e) {
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
	
	void selectInZones(int axisIndex, Section section, Number start, Number end, 
	    boolean state, boolean notify) 
	{
		if (axisIndex == 0) {
			for (Zone zone: model.zones) {
				if (zone.section0.equals(section)) {
					Math math1 = zone.section1.math;
					zone.setSelected(
							(N0) start, (N0) end, 
							math1.ZERO_VALUE(), math1.decrement(zone.section1.getCount()), 
							state);
					
					if (notify) {
	          zone.addSelectionEvent();
	        }
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
							state);
					
					if (notify) {
					  zone.addSelectionEvent();
					}
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
//		layout0.start = layout0.current == null ? layout0.start : layout0.current;
//		layout1.start = layout1.current == null ? layout1.start : layout1.current;

		layout0.start = layout0.current;
		layout1.start = layout1.current;
		layout0.compute();
		layout1.compute();
		updateScrollBars();
		listener.refresh();
		for (Zone zone: model.zones) {
			zone.setSelectedAll(false);
		}
		redraw();
	}

	
	/**
	 * Binds the command to the user gesture specified by the event type and code.
	 * Code is a logical <i>OR</i> of key, state mask and mouse button codes. 
	 */
	public void bind(int commandId, int eventType, int code) {
	  for (Zone zone: this) {
	    zone.bind(commandId, eventType, code);
	  }
	}
	
	/**
	 * Removes the binding the command to the user gesture specified by the event type and code.
	 * Code is a logical <i>OR</i> of key, state mask and mouse button codes. 
	 */
	public void unbind(int commandId, int eventType, int code) {
	  for (Zone zone: this) {
	    zone.unbind(commandId, eventType, code);
	  }
	}
	
	
	/*------------------------------------------------------------------------
	 * Painters 
	 */

	/**
	 * Returns order in which the zones will be painted.
	 * @return an array of zone indexes indicating the paint order of zones 
	 */
	public int[] getZonePaintOrder() {
		return model.paintOrder;
	}

	/**
	 * Sets the order in which the zones will be painted.
	 * @param order an array of zone indexes indicating the paint order of zones
	 */
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
	  checkOwner(painter);
		painters.add(painter);
		setPainterMatrixAndZone(painter);
	}

	/**
	 * Inserts the painter at the given index of the receiver's painters list.
	 * @param index at which the specified painter is to be inserted
	 * @param painter painter to be inserted
	 * @throws IndexOutOfBoundsException if the index is out of range
   *         (<tt>index &lt; 0 || index &gt;= getPainterCount()</tt>)
   * @throws IllegalArgumentException if the painter is null
   * @throws IllegalArgumentException if the painter's name already exists 
   *         in the collection of painters. 
	 */
  public void addPainter(int index, Painter<N0, N1> painter) {
    checkOwner(painter);
		painters.add(index, painter);
		setPainterMatrixAndZone(painter);
	}
	
	/**
	 * Replaces the painter at the given index of the receiver's painters list. 
	 * @param index index of the element to replace
	 * @param painter painter to be stored at the specified position
	 * @throws IndexOutOfBoundsException if the index is out of range
   *         (<tt>index &lt; 0 || index &gt;= getPainterCount()</tt>)   *         
   * @throws IllegalArgumentException if the painter is null
   * @throws IllegalArgumentException if the painter's name already exists 
   *         in the collection of painters. 
	 */
	public void setPainter(int index, Painter<N0, N1> painter) {
	  checkOwner(painter);
		painters.set(index, painter);
		setPainterMatrixAndZone(painter);
	}
	
	/**
	 * Replaces the painter at the index of painter with the same name.
	 * If a painter with the specified name does not exist, 
	 * then the new painter is added at the end.
	 * @param painter painter to replace a painter with the same name
	 * @throws IllegalArgumentException if the painter is null
	 */
	public void replacePainter(Painter<N0, N1> painter) {
	  checkOwner(painter);
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
   * @return the painter previously at the specified position
   * @throws IndexOutOfBoundsException if the index is out of range
   *         (<tt>index &lt; 0 || index &gt;= getPainterCount()</tt>)
   */
	public Painter<N0, N1> removePainter(int index) {
		return painters.remove(index);
	}
	
	
	/**
   * Removes the first occurrence of the specified element from this list,
   * if it is present (optional operation). If this list does not contain
   * the element, it is unchanged.
   * @param painter element to be removed from this list, if present
   * @return <tt>true</tt> if this list contained the specified element
   * @throws ClassCastException if the type of the specified element
   *         is incompatible with this list (optional)
   * @throws IllegalArgumentException if the painter is null
   */
  public boolean removePainter(Painter painter) {
    checkOwner(painter);
    return painters.remove(painter);
  }
  
	/**
   * Returns the index of a painter with the specified name
   * in the list of the receiver's painters, or -1 
   * if this list does not contain the element.
   *
   * @param name painter name to search for
   * @return the index of a painter with the specified name
   * @throws IllegalArgumentException if the name is null
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
   * @throws IllegalArgumentException if the name is null
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
	
	private void checkOwner(Painter<N0, N1> painter) {
	  if (painter == null) return;
	  
	  Preconditions.checkArgument(painter.matrix == null || this.equals(painter.matrix), 
	    "The painter belongs to a different matrix: %s", painter.matrix);

	  Preconditions.checkArgument(painter.zone == null, 
      "The painter belongs to a zone: %s", painter.zone);
    
  }

	private void setPainterMatrixAndZone(Painter painter) {
		if (painter.scope == Painter.SCOPE_CELLS_HORIZONTALLY ||
				painter.scope == Painter.SCOPE_CELLS_VERTICALLY) 
		{
			painter.setMatrix(this);
		}
	}

	@Override
	public Iterator<Zone<N0, N1>> iterator() {
		final Iterator<Zone<N0, N1>> it = model.zones.iterator();
		return new ImmutableIterator<Zone<N0, N1>>() {
			@Override
			public boolean hasNext() {
				return it.hasNext();
			}

			@Override
			public Zone<N0, N1> next() {
				return it.next();
			}
		};
	}

	public void execute(int commandId) {
	  Preconditions.checkArgument(CMD_FOCUS_UP <= commandId && commandId <= CMD_TRAVERSE_TAB_PREVIOUS,
	    "Only cell focus navigation commands are permitted");
		listener.executeCommand(new GestureBinding(commandId, 0, 0));
	}
	
 

}
