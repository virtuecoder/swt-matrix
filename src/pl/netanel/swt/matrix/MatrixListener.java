package pl.netanel.swt.matrix;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static pl.netanel.swt.matrix.Matrix.CMD_COPY;
import static pl.netanel.swt.matrix.Matrix.CMD_CUT;
import static pl.netanel.swt.matrix.Matrix.CMD_DELETE;
import static pl.netanel.swt.matrix.Matrix.CMD_EDIT_ACTIVATE;
import static pl.netanel.swt.matrix.Matrix.CMD_FOCUS_DOWN;
import static pl.netanel.swt.matrix.Matrix.CMD_FOCUS_LEFT;
import static pl.netanel.swt.matrix.Matrix.CMD_FOCUS_LOCATION;
import static pl.netanel.swt.matrix.Matrix.CMD_FOCUS_LOCATION_ALTER;
import static pl.netanel.swt.matrix.Matrix.CMD_FOCUS_MOST_DOWN;
import static pl.netanel.swt.matrix.Matrix.CMD_FOCUS_MOST_DOWN_RIGHT;
import static pl.netanel.swt.matrix.Matrix.CMD_FOCUS_MOST_LEFT;
import static pl.netanel.swt.matrix.Matrix.CMD_FOCUS_MOST_RIGHT;
import static pl.netanel.swt.matrix.Matrix.CMD_FOCUS_MOST_UP;
import static pl.netanel.swt.matrix.Matrix.CMD_FOCUS_MOST_UP_LEFT;
import static pl.netanel.swt.matrix.Matrix.CMD_FOCUS_PAGE_DOWN;
import static pl.netanel.swt.matrix.Matrix.CMD_FOCUS_PAGE_LEFT;
import static pl.netanel.swt.matrix.Matrix.CMD_FOCUS_PAGE_RIGHT;
import static pl.netanel.swt.matrix.Matrix.CMD_FOCUS_PAGE_UP;
import static pl.netanel.swt.matrix.Matrix.CMD_FOCUS_RIGHT;
import static pl.netanel.swt.matrix.Matrix.CMD_FOCUS_UP;
import static pl.netanel.swt.matrix.Matrix.CMD_ITEM_HIDE;
import static pl.netanel.swt.matrix.Matrix.CMD_ITEM_SHOW;
import static pl.netanel.swt.matrix.Matrix.CMD_PASTE;
import static pl.netanel.swt.matrix.Matrix.CMD_RESIZE_PACK;
import static pl.netanel.swt.matrix.Matrix.CMD_SELECT_ALL;
import static pl.netanel.swt.matrix.Matrix.CMD_SELECT_COLUMN;
import static pl.netanel.swt.matrix.Matrix.CMD_SELECT_COLUMN_ALTER;
import static pl.netanel.swt.matrix.Matrix.CMD_SELECT_DOWN;
import static pl.netanel.swt.matrix.Matrix.CMD_SELECT_FULL_DOWN;
import static pl.netanel.swt.matrix.Matrix.CMD_SELECT_FULL_DOWN_RIGHT;
import static pl.netanel.swt.matrix.Matrix.CMD_SELECT_FULL_LEFT;
import static pl.netanel.swt.matrix.Matrix.CMD_SELECT_FULL_RIGHT;
import static pl.netanel.swt.matrix.Matrix.CMD_SELECT_FULL_UP;
import static pl.netanel.swt.matrix.Matrix.CMD_SELECT_FULL_UP_LEFT;
import static pl.netanel.swt.matrix.Matrix.CMD_SELECT_LEFT;
import static pl.netanel.swt.matrix.Matrix.CMD_SELECT_PAGE_DOWN;
import static pl.netanel.swt.matrix.Matrix.CMD_SELECT_PAGE_LEFT;
import static pl.netanel.swt.matrix.Matrix.CMD_SELECT_PAGE_RIGHT;
import static pl.netanel.swt.matrix.Matrix.CMD_SELECT_PAGE_UP;
import static pl.netanel.swt.matrix.Matrix.CMD_SELECT_RIGHT;
import static pl.netanel.swt.matrix.Matrix.CMD_SELECT_ROW;
import static pl.netanel.swt.matrix.Matrix.CMD_SELECT_ROW_ALTER;
import static pl.netanel.swt.matrix.Matrix.CMD_SELECT_TO_COLUMN;
import static pl.netanel.swt.matrix.Matrix.CMD_SELECT_TO_COLUMN_ALTER;
import static pl.netanel.swt.matrix.Matrix.CMD_SELECT_TO_LOCATION;
import static pl.netanel.swt.matrix.Matrix.CMD_SELECT_TO_LOCATION_ALTER;
import static pl.netanel.swt.matrix.Matrix.CMD_SELECT_TO_ROW;
import static pl.netanel.swt.matrix.Matrix.CMD_SELECT_TO_ROW_ALTER;
import static pl.netanel.swt.matrix.Matrix.CMD_SELECT_UP;
import static pl.netanel.swt.matrix.Matrix.CMD_TRAVERSE_TAB_NEXT;
import static pl.netanel.swt.matrix.Matrix.CMD_TRAVERSE_TAB_PREVIOUS;
import static pl.netanel.swt.matrix.Matrix.isBodySelect;
import static pl.netanel.swt.matrix.Matrix.isExtendingSelect;

import java.math.BigInteger;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;


/**
 * Redirects events to listeners attached to zones.
 * Executes commands bound to zone gestures.
 *
 * @author Jacek
 * @created 16-11-2010
 */
class MatrixListener<X extends Number, Y extends Number> implements Listener {

	// TODO Limit the events that are redirected to zones
	private static final int[] EVENTS = { SWT.KeyDown, SWT.KeyUp, 
		SWT.MouseDown, SWT.MouseUp, SWT.MouseMove, SWT.MouseEnter, SWT.MouseExit, SWT.MouseDoubleClick, 
		/* SWT.Paint, */
		/*SWT.Move, SWT.Resize, SWT.Dispose, */
		SWT.Selection, SWT.DefaultSelection, /* 
		SWT.FocusIn, SWT.FocusOut, SWT.Expand, SWT.Collapse, SWT.Iconify, SWT.Deiconify, SWT.Close, 
		SWT.Show, SWT.Hide, SWT.Modify, SWT.Verify, */
		SWT.Activate, SWT.Deactivate,  
		/* SWT.Help,
		 
    SWT.DragDetect, //listening to DragDetect causes the MouseDown to lag 
       https://bugs.eclipse.org/bugs/show_bug.cgi?id=328396and MouseMove to skip 4 pixels  
    
		/* SWT.Arm, SWT.Traverse, SWT.MouseHover, SWT.HardKeyDown, SWT.HardKeyUp, SWT.MenuDetect, 
		SWT.SetData, SWT.MouseWheel, SWT.Settings, SWT.EraseItem, SWT.MeasureItem, 
		SWT.PaintItem, SWT.ImeComposition */
	};

	Matrix<X, Y> matrix;
//	ArrayList<GestureBinding> bindings;
	AxisListener<X> stateX;
	AxisListener<Y> stateY;
	boolean instantMoving, ctrlSelectionMoving, mouseDown;
	ZoneCore<X, Y> zone;
	Cursor cursor;
	@SuppressWarnings("rawtypes") AxisItem[] lastRange;
	ZoneCore<X, Y> body, columnHeader, rowHeader, topLeft;
	
	Event mouseMoveEvent;	
  Point imageOffset, lastImageLocation, imageSize;
	private Move mY, mX;
	
	public MatrixListener(final Matrix<X, Y> matrix) {
		this.matrix = matrix;
		lastRange = new AxisItem[4]; 
		body = ZoneCore.from(matrix.getBody());
		SectionCore<X> bodyX = SectionCore.from(matrix.axisX.getBody());
		SectionCore<Y> bodyY = SectionCore.from(matrix.axisY.getBody());
		SectionCore<X> headerX = SectionCore.from(matrix.axisX.getHeader());
		SectionCore<Y> headerY = SectionCore.from(matrix.axisY.getHeader());
    rowHeader = matrix.model.getZone(headerX, bodyY);
		columnHeader = matrix.model.getZone(bodyX, headerY);
		topLeft = matrix.model.getZone(headerX, headerY);
		
		// Initialize fields
		stateX = new AxisListener<X>(matrix.axisX);
		stateY = new AxisListener<Y>(matrix.axisY);
		
//		bindings = new ArrayList<GestureBinding>();
  	bindCommands();

		// Remove previous listener 
		if (matrix.listener != null) {
			for (int i = 0; i < EVENTS.length; i++) {
				matrix.removeListener(EVENTS[i], matrix.listener);
			}
		}

		// Add new Listener 
		for (int i = 0; i < EVENTS.length; i++) {
		  matrix.addListener(EVENTS[i], this);
		}
		matrix.listener = this; 

//		instantMoving = true;
	}


	@Override
	public void handleEvent(Event e) {
//	  System.out.println(SwtTestCase.getTypeName(e.type));
		try {
			stateY.setItem(e);
			stateX.setItem(e);

			boolean keyEvent = e.type == SWT.KeyDown || e.type == SWT.KeyUp;
			if (keyEvent) {
			  AxisItem<X> focusItemX = matrix.getAxisX().getFocusItem();
			  AxisItem<Y> focusItemY = matrix.getAxisY().getFocusItem();
			  if (focusItemX != null && focusItemY != null) {
			    zone = matrix.model.getZone(focusItemX.section, focusItemY.section);
			  }
			}
			else if (stateY.item != null && stateX.item != null) {
				zone = matrix.model.getZone(stateX.item.section, stateY.item.section);
			}

			if (e.type == SWT.MouseDown && e.button == 1) {
			  mouseDown = true;
			  matrix.forceFocus();
			}
			stateY.update(e, e.y);
			stateX.update(e, e.x);
			
			// Execute zone listeners
			/* TODO Make zone detection only for x,y events (mouse and maybe something else),
			 * for other ??? 
			 */
//			if (zone != null) {
//				//e.data = AxisItem.create[] {state0.item, state1.item}; 
//				zone.listeners.sendEvent(e);
//			}

			// Execute bound commands
			
			if (zone != null) {
			  boolean mouseEvent = SWT.MouseDown <= e.type && e.type <= SWT.MouseDoubleClick;
			  if (e.type == SWT.MouseMove && !(stateY.itemModified || stateX.itemModified)) {
			    mouseEvent = false;
			  }
			  if (e.doit && (mouseEvent || keyEvent)) {
			    //				matrix.t = System.nanoTime();
			    
			    // Find a command for the current zone or otherwise a command for any zone
			    for (GestureBinding b: zone.getBindings()) {
			      if (b.isMatching(e)) {
			        if (e.type != SWT.MouseMove || mouseDown) {
			          executeCommand(b);
			        }
			        // does not quit the loop because can execute many bindings 
			      }
			    }
			  }
			}
			sendEvents();
		
		} 
		catch (Exception ex) {
			matrix.rethrow(ex);
		} 
	}
	
	class AxisListener<N extends Number> {
		Axis<N> axis;
		Layout<N> layout;
		AxisItem<N> last, item, prev, resizeItem, lastFocus, mouseDownItem;
		boolean moving, resizing, itemModified = true;
		
		Cursor resizeCursor;
		int resizeStartDistance, resizeCellWidth, newCellWidth, distance, lastDistance;
		AutoScroll autoScroll;
		boolean focusMoved = true;
		private int resizeEvent;
    private boolean selectState;

		public AxisListener(Axis<N> axis) {
			this.axis = axis; 
			if (axis.symbol == 'X') {
				resizeCursor = Resources.getCursor(SWT.CURSOR_SIZEWE);
			}
//			item = last = axis.getFocusItem();
      else {
				resizeCursor = Resources.getCursor(SWT.CURSOR_SIZENS);
			}
		}
		
		public void setItem(Event e) {
		  if (e.type == SWT.Activate) {
		    item = last = axis.getFocusItem();
		  }
		  else if (e.type == SWT.MouseMove) {
				distance = axis.symbol == 'X' ? e.x : e.y;
				AxisItem<N> item2 = autoScroll.future != null && autoScroll.item != null  
					? autoScroll.item  
					: layout.getItemByDistance(distance);
				
				if (item2 != null) {
					if (item != null) {
					  itemModified = layout.compare(item, item2) != 0;
					}
//					last = item;
					item = item2;
					if (last == null) last = item2;
				}
				mouseMoveEvent = e;
			} 
		}

		public void update(Event e, int distance) {
			if (layout.isEmpty()) return;
			this.distance = distance;
			if (item == null) item = last = layout.current;
			
			switch (e.type) {
			case SWT.MouseMove:
				if (mouseDown) {
					handleDrag(e);
				} 
				else {
					if (isInHeader() && (resizeItem = layout.getResizeItem(distance)) != null) {
						if (cursor != resizeCursor) {
							matrix.setCursor(cursor = resizeCursor);
						}
					} else if (cursor == resizeCursor) {
						matrix.setCursor(cursor = null);
					}
				}
				break;
				
			case SWT.MouseDown:
			  lastFocus = axis.getFocusItem();
			  lastDistance = distance;
			  mouseDownItem = item;
			  prev = null;
				boolean noModifiers = (e.stateMask & SWT.MOD1) == 0 && (e.stateMask & SWT.MOD2) == 0
				  && (e.stateMask & SWT.MOD3) == 0;
				if (mouseDown && isInHeader() && noModifiers) {
					if (resizeItem != null) {
						resizing = true;
						resizeStartDistance = distance;
						resizeCellWidth = resizeItem.section.getCellWidth(resizeItem.getIndex());
					}
					else if (item.section.isMoveable(item.getIndex())) {
					  if (item.section.isSelected(item.getIndex())) {
					    // Start moving
					    moving = true;
					    matrix.setCursor(cursor = Resources.getCursor(SWT.CURSOR_HAND));

					    // Remember image offset for dragging
				      int x, y; //, w, h;
				      Bound b = layout.getCellBound(item);
				      if (axis.symbol == 'X') {
				        x = b.distance - 1;
				        y = 0;
				      }
              else {
				        x = 0;
				        y = b.distance - 1;
				      }
				      imageOffset = new Point(e.x - x, e.y - y);
					  }
					}
				}
				break;
				
			case SWT.MouseUp:
			  mouseDownItem = null;
				// Resize all selected except the current one
				if (resizeEvent == SWT.MouseMove) {
					int len = axis.sections.size();
					for (int i = 0; i < len; i++) {
						SectionCore<N> section = layout.sections.get(i);
						ExtentSequence<N> seq = section.getSelectedExtentResizableSequence();
						for (seq.init(); seq.next();) {
							
							if (item.section.equals(section) && 
									layout.math.compare(seq.start, item.getIndex()) == 0 &&
									layout.math.compare(seq.end, item.getIndex()) == 0) {
								continue;
							}
							section.setCellWidth(seq.start, seq.end, newCellWidth);
						}
//						addEvent(section, SWT.Resize, resizeItem);
						resizeEvent = 0;
						layout.compute();
						matrix.redraw();
					}
				}
				else if (resizeEvent == SWT.MouseDoubleClick) {
					int len = axis.sections.size();
					for (int i = 0; i < len; i++) {
						SectionCore<N> section = layout.sections.get(i);
						NumberSequence<N> seq = section.getSelectedSequence();
						for (seq.init(); seq.next();) {
							section.setCellWidth(seq.index());
						}
						addEvent(section, SWT.Resize, resizeItem);
						layout.compute();
						matrix.redraw();
					}
					
				}
				else if (moving) {
				  if (!instantMoving) {
				    reorder();
				  }
				  moving = false;
				  Painter<X, Y> painter = matrix.getPainter(Painter.NAME_DRAG_ITEM_X);
				  if (painter != null) painter.setEnabled(false);
				  painter = matrix.getPainter(Painter.NAME_DRAG_ITEM_Y);
				  if (painter != null) painter.setEnabled(false);
				  matrix.redraw();
				  matrix.update();
				}
				resizing = mouseDown = false;
				lastRange = null;
				if (cursor == Resources.getCursor(SWT.CURSOR_HAND)) {
					matrix.setCursor(cursor = null);
				}
				autoScroll.stop();
				prev = null;
				break;
			}
		}

		private void handleDrag(Event e) {
			resizeEvent = 0;
			// Resize item
			if (resizing && resizeItem != null) {
				newCellWidth = resizeCellWidth + distance - resizeStartDistance;
				if (newCellWidth < axis.getMinimalCellWidth()) {
          newCellWidth = axis.getMinimalCellWidth();
        }
				resizeItem.section.setCellWidth(
						resizeItem.getIndex(), resizeItem.getIndex(), newCellWidth);
				layout.compute();
				matrix.updateScrollBars();
				matrix.redraw();
				resizeEvent = SWT.MouseMove;
				addEvent(SectionCore.from(resizeItem), SWT.Resize, resizeItem);
				//event.data = matrix.getZone(axisIndex == 0 ? Zone.ROW_HEADER : Zone.COLUMN_HEADER);
			}
			else {
				// Auto-scroll
				if (!(e.data instanceof AxisItem[])) {
					autoScroll.handle();
				}

				// Move item
				if (moving) {
				  String name = axis.symbol == 'X' ? Painter.NAME_DRAG_ITEM_X : Painter.NAME_DRAG_ITEM_Y;
				  Painter<X, Y> painter = matrix.getPainter(name);
				  if (zone != null && painter != null) {
				    painter.setEnabled(true);
				  }
		      
				  if (instantMoving && itemModified) {
				    reorder();
				  }
				  matrix.redraw();
				  matrix.update();
				} 
			}
		}
		
		public boolean isSelectable() {
			return !moving && !resizing; //resizeItem == null;
		}
		
		private boolean isInHeader() {
			if (zone == null) return false;
			Axis<? extends Number> axis2 = axis.symbol == 'Y' ? matrix.axisX : matrix.axisY;
			Section<? extends Number> header = axis2.getHeader();
			return header != null && header.equals(
					axis.symbol == 'X' ? zone.sectionY : zone.sectionX);
		}

		public void setCurrentItem() {
			if (item != null) { // && item.section.isNavigationEnabled()) {
				layout.setFocusItem(item);
			}
		}

		public void moveFocusItem(Move move) {
			if (matrix.isFocusCellEnabled() && item != null)  {
//				matrix.model.setSelected(false, false);
				focusMoved = layout.moveFocusItem(move);
				if (focusMoved) {
					axis.scroll();
				}
			}
		}
		
		private boolean isSelected(AxisItem<N> item) {
			return item.section.isSelected(item.getIndex());
		}
		
		public void setSelected(int commandId) {
			if (last == null || item == null) return;
			if (!last.section.equals(item.section)) return;
			

      boolean ctrlSelection = 
        commandId == CMD_SELECT_COLUMN_ALTER || commandId == CMD_SELECT_TO_COLUMN_ALTER ||
        commandId == CMD_SELECT_ROW_ALTER || commandId == CMD_SELECT_TO_ROW_ALTER;
      
      selectState = ctrlSelection 
        ? prev == null ? !isSelected(last) : selectState   
        : true;
      
//      TestUtil.log(last, item, isSelected(last), prev, selectState);
      
      // Make sure start < end
      boolean forward = axis.comparePosition(last, item) <= 0;
      AxisItem<N> start = forward ? last : item; 
      AxisItem<N> end = forward ? item : last; 
      
			if (commandId == CMD_SELECT_COLUMN || commandId == CMD_SELECT_COLUMN_ALTER ||
				commandId == CMD_SELECT_ROW || commandId == CMD_SELECT_ROW_ALTER) {
				
				// Backup all sections cell selection
				for (int i = 0, imax = axis.getSectionCount(); i < imax; i++) {
					layout.sections.get(i).backupSelection();
				}
				
			} 
			else if (commandId == CMD_SELECT_TO_COLUMN_ALTER || commandId == CMD_SELECT_TO_ROW_ALTER) {
				// Restore previous selection from the backup
				for (int i = 0, imax = axis.getSectionCount(); i < imax; i++) {
					layout.sections.get(i).restoreSelection();
				}
			}
			
			if (!ctrlSelection) {
				axis.setSelected(false, false, false);
//				matrix.model.setSelected(false);
			}
			
			axis.setSelected(start, end, selectState);
//			TestUtil.log(Arrays.toString(start.getSection().selection.items.toArray())); 
			
			prev = ctrlSelection ? item : null;
		}
		
		private void addEvent(SectionCore<N> section, int type, Object data) {
			Event event = new Event();
			event.type = type;
			event.widget = matrix;
			event.data = data;
			//event.data = matrix.getZone(axisIndex == 0 ? Zone.ROW_HEADER : Zone.COLUMN_HEADER);
			section.listeners.add(event);
		}

		void reorder() {
			if (layout.reorder(last, item)) {
			  
			  // Adjust cursor location if moving smaller to bigger
			  Bound r1 = layout.getCellBound(last);
			  Bound r2 = layout.getCellBound(item);
			  if (r1.width < r2.width) {
			    Display display = matrix.getDisplay();
			    Point p = display.getCursorLocation();
			    if (axis.symbol == 'X') {
			      p.x = matrix.toDisplay(r1.distance + r1.width / 2, 0).x;
			    }
          else {
			      p.y = matrix.toDisplay(0, r1.distance + r1.width / 2).y;
			    }
			    display.setCursorLocation(p);
			  }
			  
				addEvent(SectionCore.from(item), SWT.Move, item);
				axis.scroll();
				item = last;
//				return true;
			}
		}
		
		public void pack() {
			if (resizeItem == null) return;
			resizeItem.section.setCellWidth(resizeItem.getIndex());
			resizeEvent = SWT.MouseDoubleClick;
			addEvent(SectionCore.from(resizeItem), SWT.Resize, resizeItem);
			if ((resizeItem = layout.getResizeItem(distance)) == null) {
				matrix.setCursor(cursor = null);
			}
		}

		public void hide(boolean b) {
			axis.setHidden(b);
			layout.isComputingRequired = true;
		}
		
		
		/*------------------------------------------------------------------------
		 * Auto-scrolling
		 */
		
		class AutoScroll implements Runnable {
			ScheduledFuture<?> future;
			volatile int offset, nextCycleCount, cycleCount, itemCount;
			AxisItem<N> item;
			MutableNumber<N> itemCountIndex;
			
			public AutoScroll() {
				itemCountIndex = layout.math.create(1);
			}

			public void handle() {
				offset = layout.getAutoScrollOffset(lastDistance, distance);
				if (offset != 0 ) { 
					int m = axis.getAutoScrollOffset();
					
					// TODO Improve the auto scroll formula to get slower close the edge, exponential 
					itemCount = (int) (abs(offset) - m * 1.25 + 1);
					if (itemCount > 1) {
						nextCycleCount = cycleCount = 0;
					} else {
						nextCycleCount = max(20 - 16 * abs(offset) / m, 0);
						itemCount = 1; // in case it is 0
					}
					
					// TODO Prevent blocking thread in case of exception in paint)
					if (future == null) {
						future = matrix.getExecutor().scheduleAtFixedRate(
							autoScroll, 0, Matrix.AUTOSCROLL_RATE, TimeUnit.MILLISECONDS);
					}
				} else {
					stop();
				}
			}

			/**
			 * Runs in a separate thread
			 */
			@Override
			public void run() {
				if (matrix.isDisposed()) return;
				matrix.getDisplay().asyncExec(new Runnable() {
					@Override public void run() {
						try {
							if (matrix.isDisposed()) return;
							doRun();
						} 
						catch (Throwable e) {
							matrix.rethrow(e);
						}
					}
				});
			}
			
			/**
			 * Runs in a separate thread
			 */
			private void doRun() {
				if (cycleCount > 0) {
					cycleCount--;
				} 
				else if (offset != 0) {
					cycleCount = nextCycleCount;
					
					MutableNumber<N> count = itemCountIndex.set(axis.math.create(itemCount));
          item = layout.scroll(count, offset > 0 ? layout.forward : layout.backward);
					if (item != null) {
						matrix.redraw();
						axis.scroll();
						
						/* In order for the mouse move commands to execute during auto scroll, 
					       like drag selection for example */
						@SuppressWarnings("unchecked")
            AxisItem<N>[] data = new AxisItem[2];
						data[axis.symbol == 'X' ? 0 : 1] = item;
						mouseMoveEvent.data = data;
						matrix.notifyListeners(SWT.MouseMove, mouseMoveEvent);
					}
				}
			}
			
			void stop() {
				if (future != null && future.cancel(true)) {
					future = null;
					item = null;
				}
			}
		}

		public void refresh() {
			if (item == null) return;
			N count = item.section.getCount();
			if (axis.math.compare(item.getIndex(), count) >= 0) {
				item = axis.math.compare(count, axis.math.ZERO_VALUE()) == 0 ? null : 
					AxisItem.create(item.section, axis.math.decrement(count));
			}
		}

		public void sendEvents() {
			for (SectionCore<N> section: layout.sections) {
				section.listeners.sendEvents();
			}
		}
	}
	
	
	protected void bindCommands() {
		// Key navigation
		bindKey(Matrix.CMD_FOCUS_UP, SWT.ARROW_UP);
		bindKey(Matrix.CMD_FOCUS_DOWN, SWT.ARROW_DOWN);
		bindKey(Matrix.CMD_FOCUS_LEFT, SWT.ARROW_LEFT);
		bindKey(Matrix.CMD_FOCUS_RIGHT, SWT.ARROW_RIGHT);
		bindKey(Matrix.CMD_FOCUS_PAGE_DOWN, SWT.PAGE_DOWN);
		bindKey(Matrix.CMD_FOCUS_PAGE_UP, SWT.PAGE_UP);
		bindKey(Matrix.CMD_FOCUS_PAGE_RIGHT, SWT.MOD3 | SWT.PAGE_DOWN);
		bindKey(Matrix.CMD_FOCUS_PAGE_LEFT, SWT.MOD3 | SWT.PAGE_UP);
		bindKey(Matrix.CMD_FOCUS_MOST_LEFT, SWT.HOME);
		bindKey(Matrix.CMD_FOCUS_MOST_RIGHT, SWT.END);
		bindKey(Matrix.CMD_FOCUS_MOST_UP, SWT.MOD1 | SWT.PAGE_UP);
		bindKey(Matrix.CMD_FOCUS_MOST_DOWN, SWT.MOD1 | SWT.PAGE_DOWN);
		bindKey(Matrix.CMD_FOCUS_MOST_UP_LEFT, SWT.MOD1 | SWT.HOME);
		bindKey(Matrix.CMD_FOCUS_MOST_DOWN_RIGHT, SWT.MOD1 | SWT.END);
		bindKey(Matrix.CMD_TRAVERSE_TAB_NEXT, SWT.TAB);
		bindKey(Matrix.CMD_TRAVERSE_TAB_PREVIOUS, SWT.MOD2 | SWT.TAB);
		
		// Key Selection
		bindKey(Matrix.CMD_SELECT_ALL, SWT.MOD1 | 'a');
		bindKey(Matrix.CMD_SELECT_UP, SWT.MOD2 | SWT.ARROW_UP);
		bindKey(Matrix.CMD_SELECT_DOWN, SWT.MOD2 | SWT.ARROW_DOWN);
		bindKey(Matrix.CMD_SELECT_LEFT, SWT.MOD2 | SWT.ARROW_LEFT);
		bindKey(Matrix.CMD_SELECT_RIGHT, SWT.MOD2 | SWT.ARROW_RIGHT);
		bindKey(Matrix.CMD_SELECT_PAGE_UP, SWT.MOD2 | SWT.PAGE_UP);
		bindKey(Matrix.CMD_SELECT_PAGE_DOWN, SWT.MOD2 | SWT.PAGE_DOWN);
		bindKey(Matrix.CMD_SELECT_PAGE_LEFT, SWT.MOD2 | SWT.MOD3 | SWT.ARROW_LEFT);
		bindKey(Matrix.CMD_SELECT_PAGE_RIGHT, SWT.MOD2 | SWT.MOD3 | SWT.ARROW_RIGHT);
		bindKey(Matrix.CMD_SELECT_FULL_UP, SWT.MOD1 | SWT.MOD2 | SWT.PAGE_UP);
		bindKey(Matrix.CMD_SELECT_FULL_DOWN, SWT.MOD1 | SWT.MOD2 | SWT.PAGE_DOWN);
		bindKey(Matrix.CMD_SELECT_FULL_LEFT, SWT.MOD2 | SWT.HOME);
		bindKey(Matrix.CMD_SELECT_FULL_RIGHT, SWT.MOD2 | SWT.END);
		bindKey(Matrix.CMD_SELECT_FULL_UP_LEFT, SWT.MOD1 | SWT.MOD2 | SWT.HOME);
		bindKey(Matrix.CMD_SELECT_FULL_DOWN_RIGHT, SWT.MOD1 | SWT.MOD2 | SWT.END);
		
		
//		bindKey(Matrix.CMD_COPY, SWT.MOD1 | 'c');
		
		// Mouse current item 
//		body.bind(new GestureBinding(Matrix.CMD_FOCUS_LOCATION, SWT.MouseDown, 1));
//		body.bind(new GestureBinding(Matrix.CMD_FOCUS_LOCATION_ALTER, SWT.MouseDown, SWT.MOD1 | 1));
		
    matrix.bind(Matrix.CMD_FOCUS_LOCATION, SWT.MouseDown, 1);
    matrix.bind(Matrix.CMD_FOCUS_LOCATION_ALTER, SWT.MouseDown, SWT.MOD1 | 1);

		
		// Mouse selection
		/* 1 is for e.button == 1 */
		matrix.bind(Matrix.CMD_SELECT_TO_LOCATION, SWT.MouseDown, SWT.MOD2 | 1);
		matrix.bind(Matrix.CMD_SELECT_TO_LOCATION_ALTER, SWT.MouseDown, SWT.MOD1 | SWT.MOD2 | 1);
		matrix.bind(Matrix.CMD_SELECT_TO_LOCATION, SWT.MouseMove, SWT.BUTTON1);
		matrix.bind(Matrix.CMD_SELECT_TO_LOCATION_ALTER, SWT.MouseMove, SWT.MOD1 | SWT.BUTTON1);
		
		if (rowHeader != null) {
			rowHeader.bind(new GestureBinding(Matrix.CMD_SELECT_ROW, SWT.MouseDown, 1));
			rowHeader.bind(new GestureBinding(Matrix.CMD_SELECT_ROW_ALTER, SWT.MouseDown, SWT.MOD1 | 1));
			rowHeader.bind(new GestureBinding(Matrix.CMD_SELECT_TO_ROW, SWT.MouseDown, SWT.MOD2 | 1));
			rowHeader.bind(new GestureBinding(Matrix.CMD_SELECT_TO_ROW_ALTER, SWT.MouseDown, SWT.MOD1 | SWT.MOD2 | 1));
			rowHeader.bind(new GestureBinding(Matrix.CMD_SELECT_TO_ROW, SWT.MouseMove, SWT.BUTTON1));
			rowHeader.bind(new GestureBinding(Matrix.CMD_SELECT_TO_ROW_ALTER, SWT.MouseMove, SWT.MOD1 | SWT.BUTTON1));
			rowHeader.bind(new GestureBinding(Matrix.CMD_RESIZE_PACK, SWT.MouseDoubleClick, 1));

			rowHeader.unbind(Matrix.CMD_SELECT_TO_LOCATION, SWT.MouseDown, SWT.MOD2 | 1);
			rowHeader.unbind(Matrix.CMD_SELECT_TO_LOCATION_ALTER, SWT.MouseDown, SWT.MOD1 | SWT.MOD2 | 1);
			rowHeader.unbind(Matrix.CMD_SELECT_TO_LOCATION, SWT.MouseMove, SWT.BUTTON1);
			rowHeader.unbind(Matrix.CMD_SELECT_TO_LOCATION_ALTER, SWT.MouseMove, SWT.MOD1 | SWT.BUTTON1);
			rowHeader.unbind(Matrix.CMD_FOCUS_LOCATION, SWT.MouseDown, 1);
			rowHeader.unbind(Matrix.CMD_FOCUS_LOCATION_ALTER, SWT.MouseDown, SWT.MOD1 | 1);

		}
		if (columnHeader != null) {
			columnHeader.bind(new GestureBinding(Matrix.CMD_SELECT_COLUMN, SWT.MouseDown, 1));
			columnHeader.bind(new GestureBinding(Matrix.CMD_SELECT_COLUMN_ALTER, SWT.MouseDown, SWT.MOD1 | 1));
			columnHeader.bind(new GestureBinding(Matrix.CMD_SELECT_TO_COLUMN, SWT.MouseDown, SWT.MOD2 | 1));
			columnHeader.bind(new GestureBinding(Matrix.CMD_SELECT_TO_COLUMN_ALTER, SWT.MouseDown, SWT.MOD1 | SWT.MOD2 | 1));
			columnHeader.bind(new GestureBinding(Matrix.CMD_SELECT_TO_COLUMN, SWT.MouseMove, SWT.BUTTON1));
			columnHeader.bind(new GestureBinding(Matrix.CMD_SELECT_TO_COLUMN_ALTER, SWT.MouseMove, SWT.MOD1 | SWT.BUTTON1));
			columnHeader.bind(new GestureBinding(Matrix.CMD_RESIZE_PACK, SWT.MouseDoubleClick, 1));
			
			columnHeader.unbind(Matrix.CMD_SELECT_TO_LOCATION, SWT.MouseDown, SWT.MOD2 | 1);
			columnHeader.unbind(Matrix.CMD_SELECT_TO_LOCATION_ALTER, SWT.MouseDown, SWT.MOD1 | SWT.MOD2 | 1);
			columnHeader.unbind(Matrix.CMD_SELECT_TO_LOCATION, SWT.MouseMove, SWT.BUTTON1);
			columnHeader.unbind(Matrix.CMD_SELECT_TO_LOCATION_ALTER, SWT.MouseMove, SWT.MOD1 | SWT.BUTTON1);
			columnHeader.unbind(Matrix.CMD_FOCUS_LOCATION, SWT.MouseDown, 1);
			columnHeader.unbind(Matrix.CMD_FOCUS_LOCATION_ALTER, SWT.MouseDown, SWT.MOD1 | 1);
		}
		if (topLeft != null) {
			// Select all on top left click
			topLeft.bind(new GestureBinding(Matrix.CMD_SELECT_ALL, SWT.MouseDown, 1));
			topLeft.unbind(Matrix.CMD_FOCUS_LOCATION, SWT.MouseDown, 1);
			topLeft.unbind(Matrix.CMD_FOCUS_LOCATION_ALTER, SWT.MouseDown, SWT.MOD1 | 1);
		}
		
		// Modification
		bindKey(Matrix.CMD_ITEM_HIDE, SWT.MOD3 | SWT.DEL);
		bindKey(Matrix.CMD_ITEM_SHOW, SWT.MOD3 | SWT.INSERT);
	}

	protected void bindKey(int commandId, int condition) {
		matrix.bind(commandId, SWT.KeyDown, condition);
	}
	
	private boolean isSelectable() {
		return stateY.isSelectable() && stateX.isSelectable();
	}
	
	protected void executeCommand(GestureBinding b) {
	  int commandId = b.commandId;
	  
//		System.out.println("execute " + commandId);
		switch (commandId) {
		case CMD_RESIZE_PACK:		stateY.pack(); stateX.pack(); break;
		
		case CMD_EDIT_ACTIVATE:				if (zone.editor != null) zone.editor.edit(b); return;
		case CMD_CUT:				if (zone.editor != null) zone.editor.cut(); return;
		case CMD_COPY:				if (zone.editor != null) zone.editor.copy(); return;
		case CMD_PASTE:				if (zone.editor != null) zone.editor.paste(); return;
		case CMD_DELETE:			if (zone.editor != null) zone.editor.delete(); return;
		case CMD_TRAVERSE_TAB_NEXT:			matrix.traverse(SWT.TRAVERSE_TAB_NEXT); return;
		case CMD_TRAVERSE_TAB_PREVIOUS: matrix.traverse(SWT.TRAVERSE_TAB_PREVIOUS); return;
		}
		
		if (!isSelectable()) return;
		
		if (!moveCursor(commandId)) return;
		if (!isExtendingSelect(commandId)) {
			stateY.last = stateY.item;
			stateX.last = stateX.item;
//			matrix.selectFocusCell();
		}
		
		backupRestoreSelection(commandId);
		
		switch (commandId) {
		case CMD_SELECT_ALL:		    matrix.model.setSelected(true, true); matrix.redraw(); return;
		// Header Selection
		case CMD_SELECT_ROW:		    case CMD_SELECT_ROW_ALTER:		    stateY.setSelected(commandId); break;	
		case CMD_SELECT_COLUMN: 	  case CMD_SELECT_COLUMN_ALTER: 	  stateX.setSelected(commandId); break;
		case CMD_SELECT_TO_ROW:		  case CMD_SELECT_TO_ROW_ALTER:	    stateY.setSelected(commandId); break;	
		case CMD_SELECT_TO_COLUMN:	case CMD_SELECT_TO_COLUMN_ALTER:	stateX.setSelected(commandId); break;
			
		// Hiding
		case CMD_ITEM_HIDE:			stateY.hide(true);  stateX.hide(true);  break; 
		case CMD_ITEM_SHOW:			stateY.hide(false); stateX.hide(false); break;
		case CMD_RESIZE_PACK:		stateY.pack(); break;
		}

		if (isBodySelect(commandId)) { // || state0.focusMoved || state1.focusMoved) {
			selectCells(commandId);
		}
		matrix.redraw();
		// forceFocus(); // in order to make focus go out of a pop-up editor
	}
	
	protected boolean moveCursor(int commandId) {
		if (!matrix.isFocusCellEnabled()) return true;
		stateY.focusMoved = true;
		stateX.focusMoved = true;
		mY = null; mX = null;
		switch (commandId) {
		
		case CMD_FOCUS_DOWN: 		case CMD_SELECT_DOWN: 		mY = Move.NEXT; break;
		case CMD_FOCUS_UP:   		case CMD_SELECT_UP:			mY = Move.PREVIOUS; break;
		case CMD_FOCUS_RIGHT: 		case CMD_SELECT_RIGHT:		mX = Move.NEXT; break;
		case CMD_FOCUS_LEFT: 		case CMD_SELECT_LEFT:		mX = Move.PREVIOUS; break;
		
		case CMD_FOCUS_PAGE_DOWN:	case CMD_SELECT_PAGE_DOWN:	mY = Move.NEXT_PAGE; break;
		case CMD_FOCUS_PAGE_UP:		case CMD_SELECT_PAGE_UP: 	mY = Move.PREVIOUS_PAGE; break;
		case CMD_FOCUS_PAGE_RIGHT:	case CMD_SELECT_PAGE_RIGHT:	mX = Move.NEXT_PAGE; break;
		case CMD_FOCUS_PAGE_LEFT:	case CMD_SELECT_PAGE_LEFT:	mX = Move.PREVIOUS_PAGE; break;

		case CMD_FOCUS_MOST_DOWN:	case CMD_SELECT_FULL_DOWN:	mY = Move.END; break;
		case CMD_FOCUS_MOST_UP:		case CMD_SELECT_FULL_UP:	mY = Move.HOME; break;
		case CMD_FOCUS_MOST_RIGHT:	case CMD_SELECT_FULL_RIGHT:	mX = Move.END; break;
		case CMD_FOCUS_MOST_LEFT:	case CMD_SELECT_FULL_LEFT:	mX = Move.HOME; break;
		
		case CMD_FOCUS_MOST_UP_LEFT:	case CMD_SELECT_FULL_UP_LEFT:	 mX = Move.HOME;
					 													 mY = Move.HOME; break;
						
		case CMD_FOCUS_MOST_DOWN_RIGHT:	case CMD_SELECT_FULL_DOWN_RIGHT: mX = Move.END;
																		 mY = Move.END; break;
		
		case CMD_FOCUS_LOCATION: 		case CMD_FOCUS_LOCATION_ALTER:
		case CMD_SELECT_TO_LOCATION:	case CMD_SELECT_TO_LOCATION_ALTER:
		case CMD_SELECT_TO_COLUMN: 		case CMD_SELECT_TO_COLUMN_ALTER:
		case CMD_SELECT_COLUMN:			case CMD_SELECT_COLUMN_ALTER:
		case CMD_SELECT_TO_ROW: 		case CMD_SELECT_TO_ROW_ALTER:
		case CMD_SELECT_ROW:			case CMD_SELECT_ROW_ALTER:
			stateY.setCurrentItem();
			stateX.setCurrentItem();
			break;														
		}
		
		if (mY != null) stateY.moveFocusItem(mY);
		if (mX != null) stateX.moveFocusItem(mX);
		if (mY != null || mX != null) {
			stateY.item = stateY.layout.current;
			stateX.item = stateX.layout.current;
		}
		return stateY.focusMoved && stateX.focusMoved;
	}

	
	private void backupRestoreSelection(int commandId) {
		if (commandId == CMD_FOCUS_LOCATION_ALTER ||  
			commandId == CMD_SELECT_COLUMN_ALTER || commandId == CMD_SELECT_ROW_ALTER) 
		{
			// Backup the zones cell selection
			for (ZoneCore<? extends Number, ? extends Number> zone: matrix.model.zones) {
				if (zone.isSelectionEnabled()) {
					zone.backupSelection();
				}
			}
		} 
		else if (commandId == CMD_SELECT_TO_LOCATION_ALTER ||  
				 commandId == CMD_SELECT_TO_COLUMN_ALTER || commandId == CMD_SELECT_TO_ROW_ALTER) 
		{
			for (ZoneCore<X, Y> zone: matrix.model.zones) {
				if (zone.isSelectionEnabled()) {
					zone.restoreSelection();
				}
			}
		}
	}
	
	private void selectCells(int commandId) {
		if (stateY.item == null || stateX.item == null) return;
		if (stateY.last == null) stateY.last = stateY.item;
		if (stateX.last == null) stateX.last = stateX.item;
		
		boolean ctrlSelection = commandId == CMD_FOCUS_LOCATION_ALTER || 
		  commandId == CMD_SELECT_TO_LOCATION_ALTER;
		
		if (ctrlSelection && isSelected(stateX.last, stateY.last)) {
			matrix.model.setSelected(stateX.last, stateX.item, stateY.last, stateY.item, false);
		}
		else {
			if (ctrlSelection) {
			  if (stateY.lastFocus != null && stateX.lastFocus != null) {
			    ZoneCore<X, Y> zone = matrix.model.getZone(
			      stateX.lastFocus.section, stateY.lastFocus.section);
			    if (BigInteger.ZERO.equals(zone.getSelectionCount())) {
			      X indexX = stateX.lastFocus.getIndex();
			      Y indexY = stateY.lastFocus.getIndex();
			      zone.setSelected(indexX, indexX, indexY, indexY, true);
			    }
			  }
			}
			else {
			  boolean notify = commandId <= CMD_FOCUS_LOCATION_ALTER;
			  if (notify) {
			    notify = false;
			    for (ZoneClient<X, Y> zone: matrix.zones) {
			      if (BigInteger.ZERO.compareTo(zone.getSelectedCount()) < 0) {
			        notify = true;
			        break;
			      }
			    }
			  }
				matrix.model.setSelected(false, notify);
			}
			if (commandId > CMD_FOCUS_LOCATION) {
			  matrix.model.setSelected(stateX.last, stateX.item, stateY.last, stateY.item, true);
			}
		}
	}
	
	private boolean isSelected(AxisItem<X> lastX, AxisItem<Y> lastY) {
		return matrix.model.getZone(lastX.section, lastY.section).
			isSelected(lastX.getIndex(), lastY.getIndex());
	}


	private void sendEvents() {
		stateY.sendEvents();
		stateX.sendEvents();
		for (ZoneCore<X, Y> zone: matrix.model.zones) {
			zone.sendEvents();
		}
	}
	
	void setLayout(Layout<X> layoutX, Layout<Y> layoutY) {
		stateY.layout = layoutY;
		stateY.axis = layoutY.axis;
		stateX.layout = layoutX;
		stateX.axis = layoutX.axis;
		stateY.autoScroll = stateY.new AutoScroll();
		stateX.autoScroll = stateX.new AutoScroll();
	}


	/**
	 * Make sure the state does not contain indexes out of scope after deleting model items.
	 */
	public void refresh() {
		stateY.refresh();
		stateX.refresh();
	}
	
	class DragItemPainter extends Painter<X, Y> {
	  ZoneCore<X, Y> header;
	  Painter<X, Y> painter;
	  Rectangle bounds, bounds2;
	  protected int x2, y2;
	  private boolean highlight;
	  private boolean advanced;
	  protected int feedback;

    public DragItemPainter(String name) {
      super(name, Painter.SCOPE_ENTIRE);
      setEnabled(false);
      matrix = MatrixListener.this.matrix;
    }
	  
    @Override protected boolean init() {
      painter = header.getPainter(Painter.NAME_CELLS);
      if (painter != null) {
        highlight = painter.selectionHighlight;
        painter.selectionHighlight = false;
      }
      return true;
    }

    @Override public void clean() {
      super.clean();
      if (painter != null) {
        painter.selectionHighlight = highlight;
      }
      gc.setLineWidth(1);
      gc.setAlpha(255);
      gc.setAdvanced(advanced);
    }
    
    @Override public void paint(X indexX, Y indexY, int x, int y, int width, int height) {
      if (!instantMoving) {
        gc.setForeground(matrix.getDisplay().getSystemColor(SWT.COLOR_BLACK));
        advanced = gc.getAdvanced();
        gc.setAdvanced(true);
        gc.setAlpha(128);
      }
      
      if ((stateX.moving || stateY.moving) && bounds != null) {
        // Background
        gc.setBackground(header.getDefaultBackground());
        gc.fillRectangle(x2, y2, bounds.width, bounds.height);
        
        // Text
        if (painter == null) {
          gc.drawText((stateY.moving ? stateY : stateX).last.getIndex().toString(),
            x2 + 1, y2 + 1);
        } 
        else if (bounds != null) {
          painter.paint(stateX.last.getIndex(), stateY.last.getIndex(),
            x2, y2, bounds.width, bounds.height);
        }
        
        // Border
        Painter<X, Y> linePainter = header.getPainter(Painter.NAME_LINES_X);
        if (linePainter == null) {
          linePainter = header.getPainter(Painter.NAME_LINES_Y);
        }
        Color color = linePainter == null 
          ? matrix.getDisplay().getSystemColor(SWT.COLOR_WIDGET_DARK_SHADOW)
            : linePainter.background;
          gc.setForeground(color);
          gc.setLineWidth(1);
          gc.drawRectangle(x2 - 1, y2 - 1, bounds.width + 1, bounds.height + 1);
      }
    }
    
    @Override
    public void setData(Object data) {
      super.setData(data);
      if (data instanceof DropTargetEvent) {
        matrix.redraw();
      }
    }
	}
	
	class DragItemPainterX extends DragItemPainter {

    public DragItemPainterX() {
      super(Painter.NAME_DRAG_ITEM_X);
      header = (ZoneCore<X, Y>) MatrixListener.this.matrix.getHeaderX().getCore();
    }
	  
    @Override public void paint(X indexX, Y indexY, int x, int y, int width, int height) {
      if (mouseMoveEvent == null) return;
      x2 = mouseMoveEvent.x - imageOffset.x;
      y2 = 1;
      if (stateX.last != null) {
        bounds = header.getCellBounds(stateX.last.getIndex(), stateY.axis.math.ZERO_VALUE());
      }
      if (!instantMoving) {
        Rectangle area = matrix.getClientArea();
        int x3 = header.getBounds(Frozen.NONE, Frozen.NONE).x;
        Object d = getData();
        if (d instanceof DropTargetEvent) {
          DropTargetEvent event = (DropTargetEvent) d;
          x3 = matrix.toControl(event.x, event.y).x;
          AxisItem<X> item = matrix.axisX.getItemByDistance(x3);
          feedback = DND.FEEDBACK_INSERT_BEFORE;
          if (item == null) {
            x3 = 0;
          }
          else if (item.section.index < matrix.axisX.body.core.index) {
            x3 = matrix.getBody().getBounds(Frozen.NONE, Frozen.NONE).x;
          }
          else if (item.section.index > matrix.axisX.body.core.index) {
            x3 = matrix.getBody().getBounds(Frozen.NONE, Frozen.NONE).x + 
              header.getBounds(Frozen.NONE, Frozen.NONE).width;
          }
          else {
            bounds2 = header.getCellBounds(item.index, stateY.axis.math.ZERO_VALUE());  
            boolean before = x3 < bounds2.x + bounds2.width / 2;
            x3 = bounds2.x + (before ? -1 : bounds2.width);
            feedback = before ? DND.FEEDBACK_INSERT_BEFORE : DND.FEEDBACK_INSERT_AFTER;
          }
        }
        gc.setLineWidth(2);
        gc.drawLine(x3, area.y, x3, area.height);
      }
      super.paint(indexX, indexY, x, y, width, height);
    };    
	}
	
	class DragItemPainterY extends DragItemPainter{
	  public DragItemPainterY() {
	    super(Painter.NAME_DRAG_ITEM_Y);
	    header = (ZoneCore<X, Y>) MatrixListener.this.matrix.getHeaderY().getCore();
	  }
	  
	  @Override public void paint(X indexX, Y indexY, int x, int y, int width, int height) {
	    if (mouseMoveEvent == null) return;
	    x2 = 1;
	    y2 = mouseMoveEvent.y - (imageOffset == null ? 0 : imageOffset.y);
	    if (stateY.last != null) {
	      bounds = header.getCellBounds(stateX.axis.math.ZERO_VALUE(), stateY.last.getIndex());
	    }
      if (!instantMoving) {
        Rectangle area = matrix.getClientArea();
        int y3 = header.getBounds(Frozen.NONE, Frozen.NONE).y;
        Object d = getData();
        if (d instanceof DropTargetEvent) {
          DropTargetEvent event = (DropTargetEvent) d;
          y3 = matrix.toControl(event.x, event.y).y;
          AxisItem<Y> item = matrix.axisY.getItemByDistance(y3);
          feedback = DND.FEEDBACK_INSERT_BEFORE;
          if (item == null) {
            y3 = 0;
          }
          else if (item.section.index < matrix.axisY.body.core.index) {
            y3 = matrix.getBody().getBounds(Frozen.NONE, Frozen.NONE).y;
          }
          else if (item.section.index > matrix.axisY.body.core.index) {
            y3 = matrix.getBody().getBounds(Frozen.NONE, Frozen.NONE).y + 
              header.getBounds(Frozen.NONE, Frozen.NONE).height;
          }
          else {
            bounds2 = header.getCellBounds(stateX.axis.math.ZERO_VALUE(), item.index);  
            boolean before = y3 < bounds2.y + bounds2.height / 2;
            y3 = bounds2.y + (before ? -1 : bounds2.height);
            feedback = before ? DND.FEEDBACK_INSERT_BEFORE : DND.FEEDBACK_INSERT_AFTER;
          }
        }
        gc.setLineWidth(2);
        gc.drawLine(area.x, y3, area.width, y3);
      }
	    super.paint(indexX, indexY, x, y, width, height);
	  };   
	}
}

