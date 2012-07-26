package pl.netanel.swt.matrix;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
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
 * @param <X> indexing type for the horizontal axis
 * @param <Y> indexing type for vertical axis
 *
 * @author Jacek
 * @created 27-03-2011
 */
public class Matrix<X extends Number, Y extends Number> extends Canvas
	implements Iterable<Zone<X, Y>>
{
	static final int CELL_WIDTH = 16;
	static final int LINE_WIDTH = 1;
	static final int RESIZE_OFFSET_X = 3;
	static final int RESIZE_OFFSET_Y = 2;
	static final int AUTOSCROLL_OFFSET_X = 8;
	static final int AUTOSCROLL_OFFSET_Y = 6;
	static final int AUTOSCROLL_RATE = 50;

	static final int TYPE_CELLS = 1;
	static final int TYPE_LINES = 2;

	static final int FORWARD = -1;
	static final int BACKWARD = 1;

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
		return CMD_FOCUS_UP <= id && id <= CMD_SELECT_TO_LOCATION_ALTER ||
		  id == CMD_DND_SELECT;
	}

	static boolean isHeaderSelect(int id) {
		return CMD_SELECT_ROW <= id && id <= CMD_SELECT_TO_COLUMN_ALTER;
	}

	static boolean isExtendingSelect(int id) {
		return CMD_SELECT_UP <= id && id <= CMD_SELECT_TO_LOCATION_ALTER ||
			CMD_SELECT_TO_ROW <= id && id <= CMD_SELECT_TO_COLUMN_ALTER ||
	      id == CMD_DND_SELECT;
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
	public static final int CMD_RESIZE_PACK = 303;

	static final int CMD_DND_RESIZE_START = 320;
	static final int CMD_DND_RESIZE = 321;
	static final int CMD_DND_RESIZE_STOP = 321;

	static final int CMD_DND_MOVE_START = 330;
	static final int CMD_DND_MOVE = 331;
	static final int CMD_DND_MOVE_STOP = 331;

	static final int CMD_DND_SELECT_START = 340;
	static final int CMD_DND_SELECT = 341;
	static final int CMD_DND_SELECT_STOP = 341;

	static final int CMD_DND_SELECT_ALTER_START = 350;
	static final int CMD_DND_SELECT_ALTER = 351;
	static final int CMD_DND_SELECT_ALTER_STOP = 351;

	public static final int CMD_TRAVERSE_TAB_NEXT = 400;       // binding = SWT.TAB
	public static final int CMD_TRAVERSE_TAB_PREVIOUS = 401;       // binding = SWT.MOD2 + SWT.TAB


	/**
	 * State bit indicating that the matrix is in the process of selecting cells
	 * Value is 1&lt;&lt;5
	 */
	static final int STATE_SELECTING = 1 << 5;
	/**
	 * State bit indicating that the matrix is in the process of moving items
	 * Value is 1&lt;&lt;6
	 */
	static final int STATE_MOVING = 1 << 6;
	/**
	 * State bit indicating that the matrix is in the process of resizing items
	 * Value is 1&lt;&lt;7
	 */
	static final int STATE_RESIZING = 1 << 7;
	/**
	 * State bit indicating that the mouse is in the resize area of the zone.
	 * When in resize area the user can resize the item by dragging or double click.
	 * The resize area offset is defined by {@link Axis#setResizeOffset(int)}.
	 * Value is 1&lt;&lt;8
	 */
	static final int STATE_RESIZE_AREA = 1 << 8;

	/**
   * State bit indicating that the an item has been clicked again
   * Value is 1&lt;&lt;9
   */
	static final int STATE_IS_SELECTED = 1 << 9;

	/*------------------------------------------------------------------------
	 * Mouse event modifiers, cannot collide with SWT state masks or mouse button numbers
	 */

//	static final int RESIZE_AREA = 1 << 26;


	MatrixLayout<X, Y> layout;
	final ArrayList<ZoneClient<X, Y>> zones;
	Axis<Y> axisY;
	Axis<X> axisX;
	AxisLayout<Y> layoutY;
	AxisLayout<X> layoutX;
	MatrixListener<X, Y> listener;
	Listener listener2;

	Rectangle area;
	final Painters<X, Y> painters;
	private ScheduledExecutorService executor;

	/**
	 * Calls the {@link #Matrix(Composite, int, Axis, Axis, ZoneCore...)} constructor
	 * with <code>null</code> values for <code>axisY</code> and <code>axisX</code>.
	 * @see #Matrix(Composite, int, Axis, Axis, ZoneCore...)
	 */
	public Matrix(Composite parent, int style) {
		this(parent, style, null, null);
	}


	public Matrix(Shell parent, int style, Class<X> classX, Class<Y> classY) {
	  this(parent, style, new Axis<X>(classX, 2), new Axis<Y>(classY, 2));
	  configureAxises(true, true);
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
	 * It the <code>axisY</code> or <code>axisX</code> is null then
	 * the axis is created with the {@link Axis#Axis()} constructor.
	 * </p><p>
	 * If any of <code>zones</code> has zero painters then default painters are attached.
	 * </p><p>
	 *
	 * @param parent a composite control which will be the parent of the new instance (cannot be null)
	 * @param style the style of control to construct
	 * @param axisY vertical axis for the matrix
	 * @param axisX horizontal axis for the matrix
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
	public Matrix(Composite parent, int style, Axis<X> axisX, Axis<Y> axisY) {
		super(parent, style | SWT.DOUBLE_BUFFERED);
		setBackground(Resources.getColor(SWT.COLOR_LIST_BACKGROUND));
		setForeground(Resources.getColor(SWT.COLOR_LIST_FOREGROUND));


		this.axisX = axisX == null ? new Axis<X>() : axisX;
	  this.axisY = axisY == null ? new Axis<Y>() : axisY;
	  this.layoutX = this.axisX.layout;
	  this.layoutY = this.axisY.layout;

		configureAxises(axisX == null, axisY == null);

		layout = new MatrixLayout<X, Y>(layoutX, layoutY);

		zones = new ArrayList<ZoneClient<X, Y>>();
		for (ZoneCore<X, Y> zone: layout.zones) {
		  zones.add(new ZoneClient<X, Y>(zone));
		}

		painters = new Painters<X, Y>();

		setLayout(layout);
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

	private void configureAxises(boolean x, boolean y) {
	  if (x) {
      axisX.getHeader().setDefaultCellWidth(40);
      axisX.getBody().setDefaultCellWidth(50);
      axisX.setAutoScrollOffset(Matrix.AUTOSCROLL_OFFSET_X);
      axisX.setResizeOffset(Matrix.RESIZE_OFFSET_X);
    }
    if (y) {
      axisY.setAutoScrollOffset(Matrix.AUTOSCROLL_OFFSET_Y);
      axisY.setResizeOffset(Matrix.RESIZE_OFFSET_Y);
    }
	}

  private void setLayout(MatrixLayout<X, Y> model) {
		this.layout = model;
		model.setMatrix(this);

		axisX.setMatrix(this, 'X');
		axisY.setMatrix(this, 'Y');

		this.listener = new MatrixListener<X, Y>(this);
	}



	/*------------------------------------------------------------------------
	 * Painting
	 */

	private void setDefaultPainters() {

		painters.add(new FrozenPainter(Frozen.NONE, Frozen.NONE));
		painters.add(new FrozenPainter(Frozen.NONE, Frozen.TAIL));
		painters.add(new FrozenPainter(Frozen.TAIL, Frozen.NONE));
		painters.add(new FrozenPainter(Frozen.NONE, Frozen.HEAD));
		painters.add(new FrozenPainter(Frozen.HEAD, Frozen.NONE));
		painters.add(new FrozenPainter(Frozen.HEAD, Frozen.TAIL));
		painters.add(new FrozenPainter(Frozen.TAIL, Frozen.HEAD));
		painters.add(new FrozenPainter(Frozen.TAIL, Frozen.TAIL));
		painters.add(new FrozenPainter(Frozen.HEAD, Frozen.HEAD));

		Painter<X, Y> frozenLinePainter =
		  new LinePainter<X, Y>(Painter.NAME_FREEZE_HEAD_LINE_X, Painter.SCOPE_ENTIRE) {
        @Override
        public void paint(int x, int y, int width, int height) {
          if (axisX.getFrozenHead() > 0) {
            int[] bound = axisX.getLineBound(axisX.getItemByViewportPosition(axisX.getFrozenHead()));
            gc.fillRectangle(bound[0], y, bound[1], height);
          }
//          gc.setBackground(background);
        }
      };
    frozenLinePainter.style.background = Resources.getColor(SWT.COLOR_BLACK);
    painters.add(frozenLinePainter);

    frozenLinePainter = new LinePainter<X, Y>(Painter.NAME_FREEZE_HEAD_LINE_Y) {
      @Override
      public void paint(int x, int y, int width, int height) {
        if (axisY.getFrozenHead() > 0) {
          int[] bound = axisY.getLineBound(axisY.getItemByViewportPosition(axisY.getFrozenHead()));
          gc.fillRectangle(x, bound[0], width, bound[1]);
        }
      }
    };
    frozenLinePainter.style.background = Resources.getColor(SWT.COLOR_BLACK);
    painters.add(frozenLinePainter);

    frozenLinePainter = new LinePainter<X, Y>(Painter.NAME_FREEZE_TAIL_LINE_X) {
        @Override
        public void paint(int x, int y, int width, int height) {
          int viewportItemCount = axisX.getViewportItemCount();
          if (axisX.getFrozenTail() > 0 ) {
            int[] bound = axisX.getLineBound(axisX.getItemByViewportPosition(viewportItemCount - axisX.getFrozenTail()));
            gc.fillRectangle(bound[0], y, bound[1], height);
          }
        }
      };
    frozenLinePainter.style.background = Resources.getColor(SWT.COLOR_BLACK);
    painters.add(frozenLinePainter);

    frozenLinePainter = new LinePainter<X, Y>(Painter.NAME_FREEZE_TAIL_LINE_Y) {
        @Override
        public void paint(int x, int y, int width, int height) {
          int viewportItemCount = axisY.getViewportItemCount();
          if (axisY.getFrozenTail() > 0 ) {
            int[] bound = axisY.getLineBound(axisY.getItemByViewportPosition(viewportItemCount - axisY.getFrozenTail()));
            gc.fillRectangle(x, bound[0], width, bound[1]);
          }
        }
      };
    frozenLinePainter.style.background = Resources.getColor(SWT.COLOR_BLACK);
    painters.add(frozenLinePainter);

		painters.add(new Painter<X, Y>(Painter.NAME_FOCUS_CELL) {
			@Override
			public void paint(int x, int y, int width, int height) {
			  AxisItem<X> itemX = axisX.getFocusItem();
				AxisItem<Y> itemY = axisY.getFocusItem();
				if (itemX == null || itemY == null) return;
				ZoneCore<X, Y> zone = layout.getZone(itemX.section, itemY.section);
				if (zone == null) return;
				Rectangle r = zone.getCellBounds(itemX.getIndex(), itemY.getIndex());
				if (r == null) return;
				gc.setLineWidth(2);
				gc.setForeground(Resources.getColor(SWT.COLOR_BLACK));
				gc.drawRectangle(r);
			}
		});

		addPainter(listener.new DragItemPainterX());
		addPainter(listener.new DragItemPainterY());
	}

	class FrozenPainter extends Painter<X, Y> {
		private final Frozen dockX;
		private final Frozen dockY;

		public FrozenPainter(Frozen dockX, Frozen dockY) {
			super("frozen " + dockX.name().toLowerCase() + " " + dockY.name().toLowerCase());
			this.dockX = dockX;
			this.dockY = dockY;
		}

		@Override
		public void paint(int x, int y, int width, int height) {
		  Bound bbX = layoutX.getBound(dockX);
			Bound bbY = layoutY.getBound(dockY);

			for (ZoneCore<X, Y> zone: layout) {
			  Bound bx = layoutX.getBound(dockX, zone.sectionX);
			  Bound by = layoutY.getBound(dockY, zone.sectionY);
			  zone.setBounds(bx.distance, by.distance, bx.width, by.width);

				if (layoutY.contains(dockY, zone.sectionY) &&
					  layoutX.contains(dockX, zone.sectionX) )
				{
				  gc.setClipping(bbX.distance, bbY.distance, bbX.width, bbY.width);

				  // Paint cells
				  zone.paint(gc, layout, dockX, dockY);
				}
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
		layoutX.computeIfRequired();
		layoutY.computeIfRequired();
		layout.computeMerging();

		for (Painter<X, Y> p: painters) {
			if (!p.isEnabled() || !p.init(gc)) continue;
			p.paint(area.x, area.y, area.width, area.height);
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
		if (layoutY == null) return;

		area = getClientArea();

		// Return if the widget does not have an area to draw yet.
		if (area.width == 0) return;

		layoutY.setViewportSize(area.height);
		layoutX.setViewportSize(area.width);

		updateScrollBars();
	}

	/**
	 * Called on resize or item count change.
	 */
	void updateScrollBars() {
		area = getClientArea();
		layoutY.computeIfRequired();
		layoutX.computeIfRequired();

		// Update the scroll bars visibility
		// If at one of the scroll bar visibility has changed then update the other one also
		if (axisX.updateScrollBarVisibility()) {
			area = getClientArea();
			axisY.updateScrollBarVisibility();
			axisX.updateScrollBarVisibility();
			area = getClientArea();
		}
		else if (axisY.updateScrollBarVisibility()) {
			area = getClientArea();
			axisX.updateScrollBarVisibility();
			axisY.updateScrollBarVisibility();
			area = getClientArea();
		}

		axisX.updateScrollBarValues(area.width);
		axisY.updateScrollBarValues(area.height);

		area = getClientArea();

//		listener.state0.item = layoutY.current;
//		listener.state1.item = layoutX.current;
	}

	/**
	 * Returns the column (horizontal) axis.
	 * @return the column (horizontal) axis
	 */
	public Axis<X> getAxisX() {
	  return axisX;
	}

	/**
	 * Returns the row (vertical) axis.
	 * @return the row (vertical) axis
	 */
	public Axis<Y> getAxisY() {
		return axisY;
	}

	/**
	 * Returns the body zone of this matrix.
	 * @return the body zone of this matrix
	 */
	public Zone<X, Y> getBody() {
		return getZone(axisX.getBody(), axisY.getBody());
	}
	/**
	 * Returns the column header zone of this matrix.
	 * @return the column header zone of this matrix
	 */
	public Zone<X, Y> getHeaderX() {
		return getZone(axisX.getBody(), axisY.getHeader());
	}
	/**
	 * Returns the row header zone of this matrix.
	 * @return the row header zone of this matrix
	 */
	public Zone<X, Y> getHeaderY() {
		return getZone(axisX.getHeader(), axisY.getBody());
	}
	/**
	 * Returns the top left zone of this matrix.
	 * @return the top left zone of this matrix
	 */
	public Zone<X, Y> getHeaderXY() {
		return getZone(axisX.getHeader(), axisY.getHeader());
	}

	/**
	 * Returns a zone located at the intersection of the given axis sections.
	 * @param sectionX section of the horizontal axis
	 * @param sectionY section of the vertical axis
	 *
	 * @return zone located at the intersection of the given axis sections
	 * @throws IllegalArgumentException if item is <code>null</code> or section
	 * 		does not belong to an axis of this matrix.
	 */
	public Zone<X, Y> getZone(Section<X> sectionX, Section<Y> sectionY) {
	  for (ZoneClient<X, Y> zone: zones) {
	    if (zone.sectionX.core.equals(axisX.checkSection(sectionX, "sectionX")) &&
	        zone.sectionY.core.equals(axisY.checkSection(sectionY, "sectionY")) ) {
	      return zone;
	    }
	  }
	  return null;
	}

	/**
	 * Enables current cell navigation in the receiver if the argument is <code>true</code>,
	 * and disables it invisible otherwise.
	 * <p>
	 * If the focus cell is disabled the navigation events are ignored and the
	 * {@link Painter#NAME_FOCUS_CELL} painter of the matrix is disabled.
	 *
	 * @param state the new focus cell enablement state
	 */
	void setFocusCellEnabled(boolean state) {
	  Painter<X, Y> painter = getPainter(Painter.NAME_FOCUS_CELL);
	  if (painter == null) return;

	  if (state == true) {
	    if (axisX.isFocusItemEnabled() && axisY.isFocusItemEnabled()) {
	      painter.setEnabled(true);
	    }
	  } else {
	    painter.setEnabled(false);
	  }

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
			Matrix.this.listener.stateY.autoScroll.stop();
			Matrix.this.listener.stateX.autoScroll.stop();
			executor.shutdown();
			try {
				executor.awaitTermination(1, TimeUnit.SECONDS);
			}
			catch (InterruptedException e1) { }
		}

		throw e instanceof RuntimeException ? (RuntimeException) e : new RuntimeException(e);
	}


	@SuppressWarnings("unchecked") <N2 extends Number>
	void selectInZones(char symbol, Section<N2> section, N2 start, N2 end, boolean state, boolean notify) {
	  if (symbol == 'X') {
	    selectInZonesX((Section<X>) section, (X) start, (X) end, state, notify);
	  } else {
	    selectInZonesY((Section<Y>) section, (Y) start, (Y) end, state, notify);
	  }
	}

	void selectInZonesX(Section<X> section, X start, X end, boolean state, boolean notify) {
	  for (ZoneCore<X, Y> zone: layout.zones) {
	    if (zone.sectionX.equals(section)) {
	      Math<Y> mathY = zone.sectionY.math;
	      zone.setSelected(
	        start, end,
	        mathY.ZERO_VALUE(), mathY.decrement(zone.sectionY.getCount()),
	        state, false);

	      if (notify) {
	        zone.addSelectionEvent();
	      }
	    }
	  }
	}

	void selectInZonesY(Section<Y> section, Y start, Y end, boolean state, boolean notify) {
	  for (ZoneCore<X, Y> zone: layout.zones) {
	    if (zone.sectionY.equals(section)) {
	      Math<X> mathX = zone.sectionX.math;
	      zone.setSelected(
	        mathX.ZERO_VALUE(), mathX.decrement(zone.sectionX.getCount()),
	        start, end,
	        state, false);

	      if (notify) {
	        zone.addSelectionEvent();
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
//		layoutY.start = layoutY.current == null ? layoutY.start : layoutY.current;
//		layoutX.start = layoutX.current == null ? layoutX.start : layoutX.current;

//		layoutY.start = layoutY.current;
//		layoutX.start = layoutX.current;
	  layout.compute();
		updateScrollBars();
		listener.refresh();
		for (Zone<X, Y> zone: layout.zones) {
			zone.setSelectedAll(false);
		}
		redraw();
	}


	/**
	 * Binds the command to the user gesture specified by the event type and code.
	 * Code is a logical <i>OR</i> of key, state mask and mouse button codes.
	 */
	public void bind(int commandId, int eventType, int code) {
	  for (Zone<X, Y> zone: this) {
	    zone.bind(commandId, eventType, code);
	  }
	}

	/**
	 * Removes the binding the command to the user gesture specified by the event type and code.
	 * Code is a logical <i>OR</i> of key, state mask and mouse button codes.
	 */
	public void unbind(int commandId, int eventType, int code) {
	  for (Zone<X, Y> zone: this) {
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
		return layout.paintOrder;
	}

	/**
	 * Sets the order in which the zones will be painted.
	 * @param order an array of zone indexes indicating the paint order of zones
	 */
	public void setZonePaintOrder(int[] order) {
	  Preconditions.checkNotNullWithName(order, "order");
		Preconditions.checkArgument(order.length == layout.paintOrder.length,
				"The length of the order array ({0}) must be equal to the number of of zones: ({1})",
				order.length, layout.paintOrder.length);
		layout.paintOrder = order;
	}

	/**
	 * Adds the painter at the end of the receiver's painters list.
	 * @param painter the painter to be added
	 */
	public void addPainter(Painter<X, Y> painter) {
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
  public void addPainter(int index, Painter<X, Y> painter) {
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
	public void setPainter(int index, Painter<X, Y> painter) {
	  checkOwner(painter);
		painters.set(index, painter);
		setPainterMatrixAndZone(painter);
	}

	/**
	 * Replaces the painter at the index of painter with the same name.
	 * If a painter with the specified name does not exist,
	 * then the new painter is added at the end.
	 *
	 * @param painter painter to replace a painter with the same name
	 * @throws IllegalArgumentException if the painter is null
	 */
	public void replacePainter(Painter<X, Y> painter) {
	  checkOwner(painter);
		painters.replacePainter(painter);
		setPainterMatrixAndZone(painter);
	}

	/**
	 * Replaces the painter at the index of painter with the same name.
	 * If a painter with the specified name does not exist,
	 * then the new painter is added at the end.
	 * <p>
	 * The new painter inherits the style from the painter that's being replaced.
	 * This helps for example to change text in the headers without the need to
	 * re-apply all the styling data, like background color, selections colors, etc.
	 *
	 * @param painter painter to replace a painter with the same name
	 * @throws IllegalArgumentException if the painter is null
	 */
	public void replacePainterPreserveStyle(Painter<X, Y> painter) {
	  checkOwner(painter);
	  painters.replacePainterPreserveStyle(painter);
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
	public Painter<X, Y> removePainter(int index) {
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
  public boolean removePainter(Painter<X, Y> painter) {
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
   * @return painter with the specified name
   * @throws IllegalArgumentException if the name is null
   */
	public Painter<X, Y> getPainter(String name) {
		Preconditions.checkNotNullWithName(name, "name");
		int index = indexOfPainter(name);
    return index == -1 ? null : painters.get(index);
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
	public Painter<X, Y> getPainter(int index) {
		Preconditions.checkPositionIndex(index, painters.size());
		return painters.get(index);
	}

	private void checkOwner(Painter<X, Y> painter) {
	  if (painter == null) return;

	  Preconditions.checkArgument(painter.matrix == null || this.equals(painter.matrix),
	    "The painter belongs to a different matrix: %s", painter.matrix);

	  Preconditions.checkArgument(painter.zone == null,
      "The painter belongs to a zone: %s", painter.zone);

  }

	private void setPainterMatrixAndZone(Painter<X, Y> painter) {
		if (painter.scope == Painter.SCOPE_CELLS_X ||
				painter.scope == Painter.SCOPE_CELLS_Y)
		{
			painter.setMatrix(this);
		}
	}

	@Override
	public Iterator<Zone<X, Y>> iterator() {
		final Iterator<ZoneClient<X, Y>> it = zones.iterator();
		return new ImmutableIterator<Zone<X, Y>>() {
			@Override
			public boolean hasNext() {
				return it.hasNext();
			}

			@Override
			public Zone<X, Y> next() {
				return it.next();
			}
		};
	}

	public void execute(int commandId) {
	  Preconditions.checkArgument(CMD_FOCUS_UP <= commandId && commandId <= CMD_TRAVERSE_TAB_PREVIOUS,
	    "Only cell focus navigation commands are permitted");
		listener.executeCommand(new GestureBinding(commandId, 0, 0));
	}



	@SuppressWarnings("unchecked")
	<N extends Number> void pack(char axisSymbol, Section<N> section, N index) {
	  if (axisSymbol == 'X') {
	    packX((Section<X>) section, (X) index);
	  } else {
	    packY((Section<Y>) section, (Y) index);
	  }
	}

	void packX(Section<X> section, X index) {
	  Cursor cursor = getCursor();
	  GC gc = new GC(this);
	  try {
	    int w = 0;
	    for (ZoneCore<X, Y> zone: layout.zones) {
	      if (!zone.sectionX.equals(section)) continue;

	      CellSet<X, Y> set = new CellSet<X, Y>(zone.sectionX.math, zone.sectionY.math);
	      set.add(index, index,
	        zone.sectionY.math.ZERO_VALUE(), zone.sectionY.math.decrement(zone.sectionY.getCount()));
	      NumberPairSequence<X, Y> seq = new NumberPairSequence<X, Y>(set);
	      for (Painter<X, Y> painter: zone.painters) {
	        painter.init(gc);
	        for (seq.init(); seq.next();) {
	          w = java.lang.Math.max(w, painter.computeSize(
	            seq.indexX(), seq.indexY(),
	            SWT.DEFAULT, zone.sectionY.getCellWidth(seq.indexY.getValue())).x);
	        }
	      }
	    }

	    if (w != 0) section.setCellWidth(index, w);
	  }
	  finally {
	    gc.dispose();
	    setCursor(cursor);
	  }
	  layout.compute(true, false);
	  redraw();
	}

	void packY(Section<Y> section, Y index) {
	  Cursor cursor = getCursor();
	  GC gc = new GC(this);
	  try {
	    int w = 0;
	    for (ZoneCore<X, Y> zone: layout.zones) {
	      if (zone.sectionY.equals(section)) {
	        CellSet<X, Y> set = new CellSet<X, Y>(zone.sectionX.math, zone.sectionY.math);
	        set.add(zone.sectionX.math.ZERO_VALUE(), zone.sectionX.math.decrement(zone.sectionX.getCount()),
	          index,
	          index);
	        NumberPairSequence<X, Y> seq = new NumberPairSequence<X, Y>(set);
	        for (Painter<X, Y> painter: zone.painters) {
	          painter.init(gc);
	          for (seq.init(); seq.next();) {
	            w = java.lang.Math.max(w, painter.computeSize(
	              seq.indexX(), seq.indexY(),
	              zone.sectionX.getCellWidth(seq.indexX.getValue()), SWT.DEFAULT).y);
	          }
	        }
	      }
	    }

	    if (w != 0) section.setCellWidth(index, w);
	  }
	  finally {
	    gc.dispose();
	    setCursor(cursor);
	  }
	  layout.compute(false, true);
	  redraw();
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
	  if (changed) {
	    layout.compute();
	  }
	  return computeSize(wHint, hHint);
	}

	@Override
	public Point computeSize(int wHint, int hHint) {
	  area = getClientArea();
	  if (wHint == SWT.DEFAULT) {
	    if (area.width == 0) {
	      wHint = 0;
	    } else {
	      int index = layoutX.indexOf(axisX.getLastItem());
	      if (index != -1) {
	        Bound x = layoutX.getLineBound(index + 1);
	        if (x == null) {
	          wHint = 0;
	        } else {
	          wHint = x.distance + x.width;
	        }
	      }
	    }
	  }
	  if (hHint == SWT.DEFAULT) {
	    if (area.width == 0) {
	      wHint = 0;
	    } else {
	      int index = layoutY.indexOf(axisY.getLastItem());
	      if (index != -1) {
	        Bound y = layoutY.getLineBound(index + 1);
	        if (y == null) {
	          hHint = 0;
	        } else {
	          hHint = y.distance + y.width;
	        }
	      }
	    }
	  }
    return new Point(wHint, hHint);
	}

	@SuppressWarnings("unchecked")
	<N extends Number> void insertInZonesX(SectionCore<N> section, N target, N count) {
	  for (ZoneCore<X, Y> zone: layout.zones) {
	    if (zone.sectionX.equals(section)) {
	      zone.cellSelection.insertX((X) target, (X) count);
	      zone.lastSelection.insertX((X) target, (X) count);
	      zone.cellMerging.insertX((X) target, (X) count);
	    }
	  }
	}

	@SuppressWarnings("unchecked")
  <N extends Number> void insertInZonesY(SectionCore<N> section, N target, N count) {
	  for (ZoneCore<X, Y> zone: layout.zones) {
	    if (zone.sectionY.equals(section)) {
	      zone.cellSelection.insertY((Y) target, (Y) count);
	      zone.lastSelection.insertY((Y) target, (Y) count);
	      zone.cellMerging.insertY((Y) target, (Y) count);
	    }
	  }
	}

	@SuppressWarnings("unchecked")
	<N extends Number> void deleteInZonesX(SectionCore<N> section, N target, N count) {
	  for (ZoneCore<X, Y> zone: layout.zones) {
	    if (zone.sectionX.equals(section)) {
	      zone.cellSelection.deleteX((X) target, (X) count);
	      zone.lastSelection.deleteX((X) target, (X) count);
	      zone.cellMerging.deleteX((X) target, (X) count);
	    }
	  }
	}

	@SuppressWarnings("unchecked")
	<N extends Number> void deleteInZonesY(SectionCore<N> section, N target, N count) {
	  for (ZoneCore<X, Y> zone: layout.zones) {
	    if (zone.sectionY.equals(section)) {
	      zone.cellSelection.deleteY((Y) target, (Y) count);
	      zone.lastSelection.deleteY((Y) target, (Y) count);
	      zone.cellMerging.deleteY((Y) target, (Y) count);
	    }
	  }
	}
}
