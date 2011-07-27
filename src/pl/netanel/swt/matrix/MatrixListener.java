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
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
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
	AxisListener<Y> stateY;
	AxisListener<X> stateX;
	boolean instantMoving, ctrlSelectionMoving, mouseDown;
	ZoneCore<X, Y> zone;
	Cursor cursor;
	AxisPointer[] lastRange;
	ZoneCore body, columnHeader, rowHeader, topLeft;
	
	Event mouseMoveEvent;	
  Point imageOffset, lastImageLocation, imageSize;
	private Move mY, mX;
	
	public MatrixListener(final Matrix matrix) {
		this.matrix = matrix;
		lastRange = new AxisPointer[4]; 
		body = ZoneCore.from(matrix.getBody());
		SectionCore bodyY = SectionCore.from(matrix.axisY.getBody());
		SectionCore bodyX = SectionCore.from(matrix.axisX.getBody());
		SectionCore headerY = SectionCore.from(matrix.axisY.getHeader());
		SectionCore headerX = SectionCore.from(matrix.axisX.getHeader());
    rowHeader = matrix.model.getZone(headerX, bodyY);
		columnHeader = matrix.model.getZone(bodyX, headerY);
		topLeft = matrix.model.getZone(headerX, headerY);
		
		// Initialize fields
		stateY = new AxisListener(matrix.axisY);
		stateX = new AxisListener(matrix.axisX);
		
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

		instantMoving = true;
	}

	Painter<X, Y> getDragItemPainter() {
	  return new Painter<X, Y>(Painter.NAME_DRAG_ITEM, Painter.SCOPE_ENTIRE) {
      Painter<X, Y> painter;
      Rectangle bounds;
      private boolean highlight;
      private ZoneClient<X, Y> zone;

      @Override protected boolean init() {
        clipping = gc.getClipping();
        if (stateY.moving ) {
          zone = (ZoneClient<X, Y>) matrix.getRowHeader();
          if (zone == null) return false;
          painter = zone.getPainter(Painter.NAME_CELLS);
          if (painter != null) {
            highlight = painter.selectionHighlight;
            painter.selectionHighlight = false;
          }
          bounds = zone.core.getCellBounds(stateX.axis.math.ZERO_VALUE(), stateY.item.getIndex());
          return true;
        }
        if (stateX.moving) {
          zone = (ZoneClient<X, Y>) matrix.getColumnHeader();
          if (zone == null) return false;
          painter = zone.getPainter(Painter.NAME_CELLS);
          if (painter != null) {
            highlight = painter.selectionHighlight;
            painter.selectionHighlight = false;
          }
          bounds = zone.core.getCellBounds(stateX.item.getIndex(), stateY.axis.math.ZERO_VALUE());
          return true;
        }
        return false;
      }
      
      @Override public void clean() {
        super.clean();
        if (painter != null) {
          painter.selectionHighlight = highlight;
        }
      }
      
      @Override public void paint(X indexX, Y indexY, int x, int y, int width, int height) {
        int x2 = stateY.moving ? 1 : mouseMoveEvent.x - imageOffset.x;
        int y2 = stateX.moving ? 1 : mouseMoveEvent.y - imageOffset.y;
        
        // Background
        gc.setBackground(zone.getDefaultBackground());
        gc.fillRectangle(x2, y2, bounds.width, bounds.height);
        
        // Text
        if (painter == null) {
          gc.drawText((stateY.moving ? stateY : stateX).item.getIndex().toString(),
            x2 + 1, y2 + 1);
        } 
        else {
          painter.paint(stateX.item.getIndex(), stateY.item.getIndex(),
            x2, y2, bounds.width, bounds.height);
        }
        
        // Border
        Painter linePainter = zone.getPainter(Painter.NAME_ROW_LINES);
        if (linePainter == null) {
          linePainter = zone.getPainter(Painter.NAME_COLUMN_LINES);
        }
        Color color = linePainter == null 
          ? matrix.getDisplay().getSystemColor(SWT.COLOR_WIDGET_DARK_SHADOW)
          : linePainter.background;
        gc.setForeground(color);
        gc.setLineWidth(1);
        gc.drawRectangle(x2 - 1, y2 - 1, bounds.width + 1, bounds.height + 1);
      }
    };
	}
	
	@Override
	public void handleEvent(Event e) {
//	  System.out.println(SwtTestCase.getTypeName(e.type));
		try {
			stateY.setItem(e);
			stateX.setItem(e);

			boolean keyEvent = e.type == SWT.KeyDown || e.type == SWT.KeyUp;
			if (e.data instanceof Zone) {
				zone = (ZoneCore) e.data;
			}
			else if (keyEvent) {
			  AxisPointer<? extends Number> focusItemX = matrix.getAxisX().getFocusItem();
			  AxisPointer<? extends Number> focusItemY = matrix.getAxisY().getFocusItem();
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
		AxisPointer<N> last, item, prev, resizeItem, lastFocus, mouseDownItem;
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
				AxisPointer item2 = autoScroll.future != null && autoScroll.item != null  
					? autoScroll.item  
					: layout.getItemByDistance(distance);
				
				if (item2 != null && item != null) {
					itemModified = layout.compare(item, item2) != 0;
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
						NumberSequence<N> seq = section.getSelected();
						for (seq.init(); seq.next();) {
							axis.pack(AxisPointer.create(section, seq.index()));
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
				if (newCellWidth < 1) newCellWidth = 1;
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
				if (!(e.data instanceof AxisPointer[])) {
					autoScroll.handle();
				}

				// Move item
				if (moving) {
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
			Axis axis2 = axis.symbol == 'Y' ? matrix.axisX : matrix.axisY;
			Section header = axis2.getHeader();
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
		
		private boolean isSelected(AxisPointer item) {
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
      AxisPointer start = forward ? last : item; 
      AxisPointer end = forward ? item : last; 
      
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
			axis.pack(resizeItem);
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
			AxisPointer item;
			MutableNumber itemCountIndex;
			
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
					
					item = layout.scroll(itemCountIndex.set(itemCount), 
							offset > 0 ? layout.forward : layout.backward);
					if (item != null) {
						matrix.redraw();
						axis.scroll();
						
						/* In order for the mouse move commands to execute during auto scroll, 
					       like drag selection for example */
						AxisPointer[] data = new AxisPointer[2];
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
					AxisPointer.create(item.section, axis.math.decrement(count));
			}
		}

		public void sendEvents() {
			for (SectionCore section: layout.sections) {
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
			for (ZoneCore zone: matrix.model.zones) {
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
		
		if (ctrlSelection && isSelected(stateY.last, stateX.last)) {
			matrix.model.setSelected(stateY.last, stateY.item, stateX.last, stateX.item, false);
		}
		else {
			if (ctrlSelection) {
			  if (stateY.lastFocus != null && stateX.lastFocus != null) {
			    ZoneCore zone = matrix.model.getZone(
			      stateX.lastFocus.section, stateY.lastFocus.section);
			    if (BigInteger.ZERO.equals(zone.getSelectionCount())) {
			      Number indexY = stateY.lastFocus.getIndex();
			      Number indexX = stateX.lastFocus.getIndex();
			      zone.setSelected(indexX, indexX, indexY, indexY, true);
			    }
			  }
			}
			else {
			  boolean notify = commandId <= CMD_FOCUS_LOCATION_ALTER;
			  if (notify) {
			    notify = false;
			    for (Zone zone: matrix) {
			      if (BigInteger.ZERO.compareTo(zone.getSelectedCount()) < 0) {
			        notify = true;
			        break;
			      }
			    }
			  }
				matrix.model.setSelected(false, notify);
			}
			if (commandId > CMD_FOCUS_LOCATION) {
			  matrix.model.setSelected(stateY.last, stateY.item, stateX.last, stateX.item, true);
			}
		}
	}
	
	private boolean isSelected(AxisPointer lastY, AxisPointer lastX) {
		return matrix.model.getZone(lastX.section, lastY.section).
			isSelected(lastX.getIndex(), lastY.getIndex());
	}


	private void sendEvents() {
		stateY.sendEvents();
		stateX.sendEvents();
		for (ZoneCore zone: matrix.model.zones) {
			zone.sendEvents();
		}
	}
	
	void setLayout(Layout layoutX, Layout layoutY) {
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
	
	static void listenToAll(Control control, Listener listener) {
    	control.addListener(SWT.KeyDown, listener);
    	control.addListener(SWT.KeyUp, listener);
    	control.addListener(SWT.MouseDown, listener);
    	control.addListener(SWT.MouseUp, listener);
//    	control.addListener(SWT.MouseMove, listener);
//    	control.addListener(SWT.MouseEnter, listener);
//    	control.addListener(SWT.MouseExit, listener);
    	control.addListener(SWT.MouseDoubleClick, listener);
    	control.addListener(SWT.Paint, listener);
    	control.addListener(SWT.Move, listener);
    	control.addListener(SWT.Resize, listener);
    	control.addListener(SWT.Dispose, listener);
    	control.addListener(SWT.Selection, listener);
    	control.addListener(SWT.DefaultSelection, listener);
    	control.addListener(SWT.FocusIn, listener);
    	control.addListener(SWT.FocusOut, listener);
    	control.addListener(SWT.Expand, listener);
    	control.addListener(SWT.Collapse, listener);
    	control.addListener(SWT.Iconify, listener);
    	control.addListener(SWT.Deiconify, listener);
    	control.addListener(SWT.Close, listener);
    	control.addListener(SWT.Show, listener);
    	control.addListener(SWT.Hide, listener);
    	control.addListener(SWT.Modify, listener);
    	control.addListener(SWT.Verify, listener);
    	control.addListener(SWT.Activate, listener);
    	control.addListener(SWT.Deactivate, listener);
    	control.addListener(SWT.Help, listener);
    	control.addListener(SWT.DragDetect, listener);
    	control.addListener(SWT.Arm, listener);
    	control.addListener(SWT.Traverse, listener);
//    	control.addListener(SWT.MouseHover, listener);
    	control.addListener(SWT.HardKeyDown, listener);
    	control.addListener(SWT.HardKeyUp, listener);
    	control.addListener(SWT.MenuDetect, listener);
    	control.addListener(SWT.SetData, listener);
    	control.addListener(SWT.MouseWheel, listener);
    	control.addListener(SWT.Settings, listener);
    	control.addListener(SWT.EraseItem, listener);
    	control.addListener(SWT.MeasureItem, listener);
    	control.addListener(SWT.PaintItem, listener);
    	control.addListener(SWT.ImeComposition, listener);
    }
	
	
    

}

