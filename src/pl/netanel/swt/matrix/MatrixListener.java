package pl.netanel.swt.matrix;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static pl.netanel.swt.matrix.Matrix.*;

import java.math.BigInteger;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
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
class MatrixListener<N0 extends Number, N1 extends Number> implements Listener {

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

	Matrix<? extends Number, ? extends Number> matrix;
//	ArrayList<GestureBinding> bindings;
	AxisListener state0, state1;
	boolean instantMoving, ctrlSelectionMoving, mouseDown;
	ZoneCore<N0, N1> zone;
	Cursor cursor;
	AxisItem[] lastRange;
	ZoneCore body, columnHeader, rowHeader, topLeft;
	
  private Image image, lastImage;
  private Point imageOffset, lastImageLocation, imageSize;
	private Move m0, m1;
	
	public MatrixListener(Matrix matrix) {
		this.matrix = matrix;
		lastRange = new AxisItem[4]; 
		body = ZoneCore.from(matrix.getBody());
		SectionCore body0 = SectionCore.from(matrix.axis0.getBody());
		SectionCore body1 = SectionCore.from(matrix.axis1.getBody());
		SectionCore header0 = SectionCore.from(matrix.axis0.getHeader());
		SectionCore header1 = SectionCore.from(matrix.axis1.getHeader());
    rowHeader = matrix.model.getZone(body0, header1);
		columnHeader = matrix.model.getZone(header0, body1);
		topLeft = matrix.model.getZone(header0, header1);
		
		// Initialize fields
		state0 = new AxisListener(matrix.axis0);
		state1 = new AxisListener(matrix.axis1);
		
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

	
	@Override
	public void handleEvent(Event e) {
//	  System.out.println(SwtTestCase.getTypeName(e.type));
		try {
			state0.setItem(e);
			state1.setItem(e);

			boolean keyEvent = e.type == SWT.KeyDown || e.type == SWT.KeyUp;
			if (e.data instanceof Zone) {
				zone = (ZoneCore) e.data;
			}
			else if (keyEvent) {
			  AxisItem<? extends Number> focusItem0 = matrix.getAxis0().getFocusItem();
			  AxisItem<? extends Number> focusItem1 = matrix.getAxis1().getFocusItem();
			  if (focusItem0 != null && focusItem1 != null) {
			    zone = matrix.model.getZone(focusItem0.section.core, focusItem1.section.core);
			  }
			}
			else if (state0.item != null && state1.item != null) {
				zone = matrix.model.getZone(state0.item.section.core, state1.item.section.core);
			}

			if (e.type == SWT.MouseDown && e.button == 1) {
			  mouseDown = true;
			  matrix.forceFocus();
			}
			else if (e.type == SWT.MouseUp) {
	       if (lastImage != null && !lastImage.isDisposed()) {
	          GC gc = new GC(matrix);
	          gc.drawImage(lastImage, lastImageLocation.x, lastImageLocation.y);
	          gc.dispose();
	          matrix.redraw();
	          lastImage.dispose();
	          lastImage = null;
	        }
	        if (image != null) image.dispose();
			}
			state0.update(e, e.y);
			state1.update(e, e.x);
			
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
			  if (e.type == SWT.MouseMove && !(state0.itemModified || state1.itemModified)) {
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
		private final int axisIndex;
		Axis<N> axis;
		Layout<N> layout;
		AxisItem<N> last, item, prev, resizeItem, lastFocus, mouseDownItem;
		boolean moving, resizing, itemModified = true;
		Event mouseMoveEvent;
		Cursor resizeCursor;
		int resizeStartDistance, resizeCellWidth, newCellWidth, distance, lastDistance;
		AutoScroll autoScroll;
		boolean focusMoved = true;
		private int resizeEvent;
    private boolean selectState;

		public AxisListener(Axis<N> axis) {
			this.axis = axis; 
			this.axisIndex = axis.index;
			if (axisIndex == 0) {
				resizeCursor = Resources.getCursor(SWT.CURSOR_SIZENS);
			} else {
				resizeCursor = Resources.getCursor(SWT.CURSOR_SIZEWE);
			}
//			item = last = axis.getFocusItem();
		}
		
		public void setItem(Event e) {
		  if (e.type == SWT.Activate) {
		    item = last = axis.getFocusItem();
		  }
		  else if (e.type == SWT.MouseMove) {
				distance = axisIndex == 0 ? e.y : e.x;
				AxisItem item2 = autoScroll.future != null && autoScroll.item != null  
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
						resizeCellWidth = resizeItem.section.core.getCellWidth(resizeItem.getIndex());
					}
					else if (item.section.core.isSelected(item.getIndex()) && 
					         item.section.core.isMoveable(item.getIndex())) {
						// Start moving
						moving = true;
						matrix.setCursor(cursor = Resources.getCursor(SWT.CURSOR_HAND));
						
						// snap item picture
						Display display = matrix.getDisplay();
						GC gc = new GC(matrix);
						gc.setAdvanced(true);
	          if (gc.getAdvanced()) {
	            int x, y, w, h;
	            Bound b = layout.getCellBound(item);
	            if (axisIndex == 0) {
	              x = 0;
	              y = b.distance - 1;
	              w = matrix.getClientArea().width;
	              h = b.width + 2; 
	            }
	            else {
	              x = b.distance - 1;
	              y = 0;
	              w = b.width + 2;
	              h = matrix.getClientArea().height; 
	            }
              lastImage = image = new Image(display, w, h);
	            imageSize = new Point(w, h);
	            gc.copyArea(image, x, y);
	            imageOffset = new Point(e.x - x, e.y - y);
//	            TestUtil.log("image offset", imageOffset);
	            lastImageLocation = new Point(x, y);
	            
	            ImageData imageData = image.getImageData();
	            imageData.alpha = 160;
	            image = new Image(display, imageData);
	          }
	          gc.dispose();
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
							
							if (item.section.core.equals(section) && 
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
							axis.pack(AxisItem.create(section, seq.index()));
						}
						addEvent(section, SWT.Resize, resizeItem);
						layout.compute();
						matrix.redraw();
					}
					
				}
				else if (moving && !instantMoving) {
					reorder();
				}
				moving = resizing = mouseDown = false;
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
				resizeItem.section.core.setCellWidth(
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
				  if (image != null) {
				    int x, y;
				    if (axisIndex == 0) {
				      x = 0;
				      y = e.y - imageOffset.y;
				    }
				    else {
				      x = e.x - imageOffset.x;
				      y = 0;
				    }
				    
				    if (instantMoving && itemModified) {
				      reorder();
				      
				      GC gc = new GC(matrix);
				      lastImage = new Image(matrix.getDisplay(), imageSize.x, imageSize.y);
				      matrix.redraw();
				      matrix.update();
				      gc.copyArea(lastImage, x, y);
				      gc.dispose();
				    }
				    
            GC gc = new GC(matrix);
            gc.drawImage(lastImage, lastImageLocation.x, lastImageLocation.y);
            
            lastImage = new Image(matrix.getDisplay(), imageSize.x, imageSize.y);
            matrix.redraw();
            matrix.update();
            gc.copyArea(lastImage, x, y);
            gc.drawImage(image, x, y);
            gc.dispose();
            lastImageLocation.x = x;
            lastImageLocation.y = y;
				  }
				} 
			}
		}
		
//		void restorLastImage(Event e) {
//		  GC gc = new GC(matrix);
//      gc.drawImage(lastImage, lastImageLocation.x, lastImageLocation.y);
//      
//      int x, y;
//      if (axisIndex == 0) {
//        x = 0;
//        y = e.y - imageOffset.y;
//      }
//      else {
//        x = e.x - imageOffset.x;
//        y = 0;
//      }
//      TestUtil.log(x, y);
//      lastImage = new Image(matrix.getDisplay(), imageSize.x, imageSize.y);
//      gc.copyArea(lastImage, x, y);
//      gc.drawImage(image, x, y);
//      gc.dispose();
//      lastImageLocation.x = x;
//      lastImageLocation.y = y;
//      TestUtil.log("last image location", lastImageLocation);
//      gc.dispose();
//		}
		

		public boolean isSelectable() {
			return !moving && !resizing; //resizeItem == null;
		}
		
		private boolean isInHeader() {
			if (zone == null) return false;
			Axis axis2 = axis.index == 0 ? matrix.axis1 : matrix.axis0;
			Section header = axis2.getHeader();
			return header != null && header.equals(
					axis.index == 1 ? zone.section0 : zone.section1);
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
		
		private boolean isSelected(AxisItem item) {
			return item.section.core.isSelected(item.getIndex());
		}
		
		public void setSelected(int commandId) {
			if (last == null || item == null) return;
			if (!last.section.core.equals(item.section.core)) return;
			

      boolean ctrlSelection = 
        commandId == CMD_SELECT_COLUMN_ALTER || commandId == CMD_SELECT_TO_COLUMN_ALTER ||
        commandId == CMD_SELECT_ROW_ALTER || commandId == CMD_SELECT_TO_ROW_ALTER;
      
      selectState = ctrlSelection 
        ? prev == null ? !isSelected(last) : selectState   
        : true;
      
//      TestUtil.log(last, item, isSelected(last), prev, selectState);
      
      // Make sure start < end
      boolean forward = axis.comparePosition(last, item) <= 0;
      AxisItem start = forward ? last : item; 
      AxisItem end = forward ? item : last; 
      
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
			    if (axisIndex == 0) {
			      p.y = matrix.toDisplay(0, r1.distance + r1.width / 2).y;
			    } else {
			      p.x = matrix.toDisplay(r1.distance + r1.width / 2, 0).x;
			    }
			    display.setCursorLocation(p);
			  }
			  
				addEvent(SectionCore.from(item), SWT.Move, item);
				axis.scroll();
				matrix.redraw();
				matrix.update();
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
			AxisItem item;
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
						AxisItem[] data = new AxisItem[2];
						data[axisIndex] = item;
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
			N count = item.section.core.getCount();
			if (axis.math.compare(item.getIndex(), count) >= 0) {
				item = axis.math.compare(count, axis.math.ZERO_VALUE()) == 0 ? null : 
					AxisItem.create(item.section, axis.math.decrement(count));
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
		return state0.isSelectable() && state1.isSelectable();
	}
	
	protected void executeCommand(GestureBinding b) {
	  int commandId = b.commandId;
	  
//		System.out.println("execute " + commandId);
		switch (commandId) {
		case CMD_RESIZE_PACK:		state0.pack(); state1.pack(); break;
		
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
			state0.last = state0.item;
			state1.last = state1.item;
//			matrix.selectFocusCell();
		}
		
		backupRestoreSelection(commandId);
		
		switch (commandId) {
		case CMD_SELECT_ALL:		    matrix.model.setSelected(true, true); matrix.redraw(); return;
		// Header Selection
		case CMD_SELECT_ROW:		    case CMD_SELECT_ROW_ALTER:		    state0.setSelected(commandId); break;	
		case CMD_SELECT_COLUMN: 	  case CMD_SELECT_COLUMN_ALTER: 	  state1.setSelected(commandId); break;
		case CMD_SELECT_TO_ROW:		  case CMD_SELECT_TO_ROW_ALTER:	    state0.setSelected(commandId); break;	
		case CMD_SELECT_TO_COLUMN:	case CMD_SELECT_TO_COLUMN_ALTER:	state1.setSelected(commandId); break;
			
		// Hiding
		case CMD_ITEM_HIDE:			state0.hide(true);  state1.hide(true);  break; 
		case CMD_ITEM_SHOW:			state0.hide(false); state1.hide(false); break;
		case CMD_RESIZE_PACK:		state0.pack(); break;
		}

		if (isBodySelect(commandId)) { // || state0.focusMoved || state1.focusMoved) {
			selectCells(commandId);
		}
		matrix.redraw();
		// forceFocus(); // in order to make focus go out of a pop-up editor
	}
	
	protected boolean moveCursor(int commandId) {
		if (!matrix.isFocusCellEnabled()) return true;
		state0.focusMoved = true;
		state1.focusMoved = true;
		m0 = null; m1 = null;
		switch (commandId) {
		
		case CMD_FOCUS_DOWN: 		case CMD_SELECT_DOWN: 		m0 = Move.NEXT; break;
		case CMD_FOCUS_UP:   		case CMD_SELECT_UP:			m0 = Move.PREVIOUS; break;
		case CMD_FOCUS_RIGHT: 		case CMD_SELECT_RIGHT:		m1 = Move.NEXT; break;
		case CMD_FOCUS_LEFT: 		case CMD_SELECT_LEFT:		m1 = Move.PREVIOUS; break;
		
		case CMD_FOCUS_PAGE_DOWN:	case CMD_SELECT_PAGE_DOWN:	m0 = Move.NEXT_PAGE; break;
		case CMD_FOCUS_PAGE_UP:		case CMD_SELECT_PAGE_UP: 	m0 = Move.PREVIOUS_PAGE; break;
		case CMD_FOCUS_PAGE_RIGHT:	case CMD_SELECT_PAGE_RIGHT:	m1 = Move.NEXT_PAGE; break;
		case CMD_FOCUS_PAGE_LEFT:	case CMD_SELECT_PAGE_LEFT:	m1 = Move.PREVIOUS_PAGE; break;

		case CMD_FOCUS_MOST_DOWN:	case CMD_SELECT_FULL_DOWN:	m0 = Move.END; break;
		case CMD_FOCUS_MOST_UP:		case CMD_SELECT_FULL_UP:	m0 = Move.HOME; break;
		case CMD_FOCUS_MOST_RIGHT:	case CMD_SELECT_FULL_RIGHT:	m1 = Move.END; break;
		case CMD_FOCUS_MOST_LEFT:	case CMD_SELECT_FULL_LEFT:	m1 = Move.HOME; break;
		
		case CMD_FOCUS_MOST_UP_LEFT:	case CMD_SELECT_FULL_UP_LEFT:	 m1 = Move.HOME;
					 													 m0 = Move.HOME; break;
						
		case CMD_FOCUS_MOST_DOWN_RIGHT:	case CMD_SELECT_FULL_DOWN_RIGHT: m1 = Move.END;
																		 m0 = Move.END; break;
		
		case CMD_FOCUS_LOCATION: 		case CMD_FOCUS_LOCATION_ALTER:
		case CMD_SELECT_TO_LOCATION:	case CMD_SELECT_TO_LOCATION_ALTER:
		case CMD_SELECT_TO_COLUMN: 		case CMD_SELECT_TO_COLUMN_ALTER:
		case CMD_SELECT_COLUMN:			case CMD_SELECT_COLUMN_ALTER:
		case CMD_SELECT_TO_ROW: 		case CMD_SELECT_TO_ROW_ALTER:
		case CMD_SELECT_ROW:			case CMD_SELECT_ROW_ALTER:
			state0.setCurrentItem();
			state1.setCurrentItem();
			break;														
		}
		
		if (m0 != null) state0.moveFocusItem(m0);
		if (m1 != null) state1.moveFocusItem(m1);
		if (m0 != null || m1 != null) {
			state0.item = state0.layout.current;
			state1.item = state1.layout.current;
		}
		return state0.focusMoved && state1.focusMoved;
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
		if (state0.item == null || state1.item == null) return;
		if (state0.last == null) state0.last = state0.item;
		if (state1.last == null) state1.last = state1.item;
		
		boolean ctrlSelection = commandId == CMD_FOCUS_LOCATION_ALTER || 
		  commandId == CMD_SELECT_TO_LOCATION_ALTER;
		
		if (ctrlSelection && isSelected(state0.last, state1.last)) {
			matrix.model.setSelected(state0.last, state0.item, state1.last, state1.item, false);
		}
		else {
			if (ctrlSelection) {
			  if (state0.lastFocus != null && state1.lastFocus != null) {
			    ZoneCore zone = matrix.model.getZone(
			      state0.lastFocus.section.core, state1.lastFocus.section.core);
			    if (BigInteger.ZERO.equals(zone.getSelectionCount())) {
			      Number index0 = state0.lastFocus.getIndex();
			      Number index1 = state1.lastFocus.getIndex();
			      zone.setSelected(index0, index0, index1, index1, true);
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
			  matrix.model.setSelected(state0.last, state0.item, state1.last, state1.item, true);
			}
		}
	}
	
	private boolean isSelected(AxisItem last0, AxisItem last1) {
		return matrix.model.getZone(last0.section.core, last1.section.core).
			isSelected(last0.getIndex(), last1.getIndex());
	}


	private void sendEvents() {
		state0.sendEvents();
		state1.sendEvents();
		for (ZoneCore zone: matrix.model.zones) {
			zone.sendEvents();
		}
	}
	
	void setLayout(Layout layout0, Layout layout1) {
		state0.layout = layout0;
		state0.axis = layout0.axis;
		state1.layout = layout1;
		state1.axis = layout1.axis;
		state0.autoScroll = state0.new AutoScroll();
		state1.autoScroll = state1.new AutoScroll();
	}


	/**
	 * Make sure the state does not contain indexes out of scope after deleting model items.
	 */
	public void refresh() {
		state0.refresh();
		state1.refresh();
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

