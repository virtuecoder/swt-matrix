package pl.netanel.swt.matrix;

import static java.lang.Math.*;
import static pl.netanel.swt.matrix.M.*;

import java.util.ArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import pl.netanel.swt.Listeners;
import pl.netanel.swt.Resources;

/**
 * Redirects events to listeners attached to zones.
 * Executes commands bound to zone gestures.
 *
 * @author Jacek
 * @created 16-11-2010
 */
class MatrixListener implements Listener {

	// TODO Limit the events that are redirected to zones
	private static final int[] EVENTS = { SWT.KeyDown, SWT.KeyUp, 
		SWT.MouseDown, SWT.MouseUp, SWT.MouseMove, SWT.MouseEnter, SWT.MouseExit, SWT.MouseDoubleClick, 
		/* SWT.Paint, */
		SWT.Move, SWT.Resize, /* SWT.Dispose, */
		SWT.Selection, SWT.DefaultSelection, /* 
		SWT.FocusIn, SWT.FocusOut, SWT.Expand, SWT.Collapse, SWT.Iconify, SWT.Deiconify, SWT.Close, 
		SWT.Show, SWT.Hide, SWT.Modify, SWT.Verify, SWT.Activate, SWT.Deactivate, SWT.Help, */
		/* SWT.DragDetect, listening to DragDetect causes the MouseMove to skip 4 pixels */ 
		/* SWT.Arm, SWT.Traverse, SWT.MouseHover, SWT.HardKeyDown, SWT.HardKeyUp, SWT.MenuDetect, 
		SWT.SetData, SWT.MouseWheel, SWT.Settings, SWT.EraseItem, SWT.MeasureItem, 
		SWT.PaintItem, SWT.ImeComposition */
	};

	Matrix matrix;
	ArrayList<Binding> bindings;
	AxisListener state0, state1;
	boolean instantMoving, ctrlSelectionMoving;
	Zone zone;
	Cursor cursor;
	Listeners listeners;
	AxisItem[] lastRange;

	private Move m0, m1;
	
	public MatrixListener(Matrix matrix) {
		this.matrix = matrix;
		lastRange = new AxisItem[4]; 
		listeners = new Listeners();
		
		// Initialize fields
		state0 = new AxisListener(0);
		state1 = new AxisListener(1);
		
		bindings = new ArrayList<Binding>();
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
		try {
			state0.setItem(e);
			state1.setItem(e);

			if (e.data instanceof Zone) {
				zone = (Zone) e.data;
			}
			else if (state0.item != null && state1.item != null) {
				zone = matrix.model.getZone(state0.item.section, state1.item.section);
			}

			state0.update(e, e.y);
			state1.update(e, e.x);
			
			// Execute zone listeners
			/* TODO Make zone detection only for x,y events (mouse and maybe something else),
			 * for other ??? 
			 */
			if (zone != null) {
				//e.data = new AxisItem[] {state0.item, state1.item}; 
				zone.listeners.sendEvent(e);
			}

			// Execute bound commands

			boolean mouseEvent = SWT.MouseDown <= e.type && e.type <= SWT.MouseDoubleClick;
			boolean keyEvent = e.type == SWT.KeyDown || e.type == SWT.KeyUp;
			if (e.type == SWT.MouseMove && !(state0.itemModified || state1.itemModified)) {
				mouseEvent = false;
			}
			if (e.doit && (mouseEvent || keyEvent)) {
//				matrix.t = System.nanoTime();

				// Find a command for the current zone or otherwise a command for any zone
				for (Binding b: bindings) {
					if (b.isMatching(e, zone)) {
						executeCommand(b.commandId);
						/* does not quit loop because can execute 
					   	   many both any zone and a specific zone bindings */
					}
				}
			}
			sendEvents();
		
		} 
		catch (Exception ex) {
			matrix.rethrow(ex);
		} 
	}
	
	class AxisListener {
		private final int axisIndex;
		final Axis axis;
		Layout layout;
		AxisModel model;
		AxisItem last, item, resizeItem;
		boolean moving, resizing, itemModified = true, mouseDown;
		Event mouseMoveEvent;
		Cursor resizeCursor;
		int headerId, resizeStartDistance, resizeCellWidth, newCellWidth, distance, lastDistance;
		AutoScroll autoScroll;

		public AxisListener(int axisIndex) {
			this.axisIndex = axisIndex;
			if (axisIndex == 0) {
				axis = matrix.axis0; 
				resizeCursor = Resources.getCursor(SWT.CURSOR_SIZENS);
				headerId = Zone.ROW_HEADER;
			} else {
				axis = matrix.axis1; 
				resizeCursor = Resources.getCursor(SWT.CURSOR_SIZEWE);
				headerId = Zone.COLUMN_HEADER;
			}
		}
		
		public void setItem(Event e) {
			if (e.type == SWT.MouseMove) {
				distance = axisIndex == 0 ? e.y : e.x;
				AxisItem item2 = autoScroll.future != null && autoScroll.item != null  
					? autoScroll.item  
					: layout.getItemByDistance(distance);
				
				if (item2 != null && item != null) {
					itemModified = layout.compare(item, item2) != 0;
					item = item2;
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
//					if (isInHeader() && (resizeItem = layout.getResizeItem(distance)) != null) {
//						if (cursor != resizeCursor) {
//							matrix.setCursor(cursor = resizeCursor);
//						}
//					} else if (cursor == resizeCursor) {
//						matrix.setCursor(cursor = null);
//					}
				}
				break;
				
			case SWT.MouseDown:
				mouseDown = e.button == 1;
				lastDistance = distance;
				if (mouseDown && isInHeader() && e.stateMask == 0) {
					if (resizeItem != null) {
						resizing = true;
						resizeStartDistance = distance;
						resizeCellWidth = resizeItem.section.getCellWidth(resizeItem.index);
					}
					else if (item.section.isSelected(item.index) && item.section.isMoveable(item.index)) {
						// Start moving
						moving = true;
						matrix.setCursor(cursor = Resources.getCursor(SWT.CURSOR_HAND));
					}
				}
				break;
				
			case SWT.MouseUp:
				// Resize all selected except the current one
//				if (resizing && isSelected(resizeItem)) {
//					int len = layout.sections.size();
//					for (int i = 0; i < len; i++) {
//						Section section = layout.sections.get(i);
//						for (Extent extent: section.selection.items) {
//							if (item.section.equals(section) && 
//									layout.math.compare(extent.start, item.index) == 0 &&
//									layout.math.compare(extent.end, item.index) == 0) {
//								continue;
//							}
//							section.setCellWidth(extent.start, extent.end, newCellWidth);
//							layout.compute();
//							matrix.redraw();
//						}
//					}
//					addEvent(SWT.Resize);
//				}
//				else if (moving && !instantMoving) {
//					reorder();
//				}
				moving = resizing = mouseDown = false;
				lastRange = null;
				if (cursor == Resources.getCursor(SWT.CURSOR_HAND)) {
					matrix.setCursor(cursor = null);
				}
				autoScroll.stop();
				break;
			}
		}

		private void handleDrag(Event e) {
			// Resize item
			if (resizing && resizeItem != null) {
				newCellWidth = resizeCellWidth + distance - resizeStartDistance;
				if (newCellWidth < 1) newCellWidth = 1;
				resizeItem.section.setCellWidth(resizeItem.index, resizeItem.index, newCellWidth);
				layout.compute();
				matrix.redraw();
				addEvent(SWT.Resize);
				//event.data = matrix.getZone(axisIndex == 0 ? Zone.ROW_HEADER : Zone.COLUMN_HEADER);
			}
			else {
				// Auto-scroll
				if (!(e.data instanceof AxisItem[])) {
					autoScroll.handle();
				}

				// Move item
				if (moving && instantMoving && itemModified) {
//					reorder();
				} 
			}
		}

		public boolean isSelectable() {
			return !moving && !resizing; //resizeItem == null;
		}
		
		private boolean isInHeader() {
			return zone != null && zone.is(headerId);
		}

		public void setCurrentItem() {
			if (item != null) { // && item.section.isNavigationEnabled()) {
				layout.setCurrentItem(item);
			}
		}

		public void moveCurrentItem(Move move) {
			if (item != null && item.section.isNavigationEnabled()) {
				layout.moveCurrentItem(move);
				axis.scroll();
			}
		}
		
		private boolean isSelected(AxisItem item) {
			return item.section.isSelected(item.index);
		}
		
		public void setSelected(int commandId) {
			if (last == null || item == null) return;
//			if (last.section != item.section) return;
			
			if (commandId == SELECT_COLUMN || commandId == SELECT_COLUMN2 ||
				commandId == SELECT_ROW || commandId == SELECT_ROW2) {
				
				// Backup all sections cell selection
				for (int i = 0, imax = model.getSectionCount(); i < imax; i++) {
					model.getSection(i).backupSelection();
				}
				
			} 
			else if (commandId == SELECT_TO_COLUMN2 || commandId == SELECT_TO_ROW2) {
				// Restore previous selection from the backup
				for (int i = 0, imax = model.getSectionCount(); i < imax; i++) {
					model.getSection(i).restoreSelection();
				}
			}
			
			// Make sure start < end 
			if (model.comparePosition(last, item) > 0) {
				AxisItem tmp = last; last = item; item = tmp;
			}

			boolean ctrlSelection = 
				commandId == SELECT_COLUMN2 || commandId == SELECT_TO_COLUMN2 ||
				commandId == SELECT_ROW2 || commandId == SELECT_TO_ROW2;
			
			if (!ctrlSelection) {
				model.setSelected(false);
				matrix.model.setSelected(false);
			}
			
			model.setSelected(last, item, !(ctrlSelection && isSelected(last)));
			if (axisIndex == 0) {
				AxisModel model1 = matrix.model.getModel1();
				matrix.model.setSelected(last, item, model1.getFirstItem(), model1.getLastItem(), true);
			} else {
				AxisModel model0 = matrix.model.getModel0();
				matrix.model.setSelected(model0.getFirstItem(), model0.getLastItem(), last, item, true);
			}
			addEvent(SWT.Selection);
		}
		
		private void addEvent(int type) {
			Event event = new Event();
			event.type = type;
			event.widget = matrix;
			//event.data = matrix.getZone(axisIndex == 0 ? Zone.ROW_HEADER : Zone.COLUMN_HEADER);
			axis.listeners.add(event);
		}

//		boolean reorder() {
//			AxisItem item2 = layout.reorder(item);
//			if (item2 != null) {
//				// Move the cursor if outside of the moved column
//				int direction = model.comparePosition(item2, item);
//				int cellDistance = layout.getCellDistance(item2);
//				Point p = null;
//				if (direction > 0 && distance < cellDistance) {
//					p = matrix.getLocation();
//					p.x = axisIndex == 0 ? mouseMoveEvent.x : cellDistance;
//					p.y = axisIndex == 0 ? cellDistance : mouseMoveEvent.y;
//				} 
//				else if (direction < 0) {
//					int lineDistance = cellDistance + model.getCellWidth(item2);
//					if (distance > lineDistance) {
//						p = matrix.getLocation();
//						p.x = axisIndex == 0 ? mouseMoveEvent.x : lineDistance;
//						p.y = axisIndex == 0 ? lineDistance : mouseMoveEvent.y;
//					}
//				}
//				if (p != null) {
//					Display display = matrix.getDisplay();
//					p = display.map(matrix, null, p);
//					display.setCursorLocation(p);
//				}
//				addEvent(SWT.Move);
//				axis.scroll();
//				matrix.redraw();
//				return true;
//			}
//			return false;
//		}
		
		public void pack() {
//			if (resizeItem == null) return;
//			axis.pack(resizeItem.section, resizeItem.index);
//			if ((resizeItem = layout.getResizeItem(distance)) == null) {
//				matrix.setCursor(cursor = null);
//			}
		}

		public void hide(boolean b) {
			model.setHidden(b);
			layout.isComputingRequired = true;
		}
		
		
		/*------------------------------------------------------------------------
		 * Auto-scrolling
		 */
		
		class AutoScroll implements Runnable {
			ScheduledFuture<?> future;
			int offset, nextCycleCount, cycleCount, itemCount;
			AxisItem item;
			MutableNumber itemCountIndex;
			
			public AutoScroll() {
				itemCountIndex = layout.math.create(1);
			}

			public void handle() {
				offset = layout.getAutoScrollOffset(lastDistance, distance);
				if (offset != 0 ) { 
					int m = model.getAutoScrollOffset();
					
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
							autoScroll, 0, M.AUTOSCROLL_RATE, TimeUnit.MILLISECONDS);
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
					public void run() {
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
	}
	
	
	protected void bindCommands() {
		// Key navigation
		bindKey(M.CURRENT_UP, SWT.ARROW_UP);
		bindKey(M.CURRENT_DOWN, SWT.ARROW_DOWN);
		bindKey(M.CURRENT_LEFT, SWT.ARROW_LEFT);
		bindKey(M.CURRENT_RIGHT, SWT.ARROW_RIGHT);
		bindKey(M.CURRENT_PAGE_DOWN, SWT.PAGE_DOWN);
		bindKey(M.CURRENT_PAGE_UP, SWT.PAGE_UP);
		bindKey(M.CURRENT_PAGE_RIGHT, SWT.MOD3 | SWT.PAGE_DOWN);
		bindKey(M.CURRENT_PAGE_LEFT, SWT.MOD3 | SWT.PAGE_UP);
		bindKey(M.CURRENT_MOST_LEFT, SWT.HOME);
		bindKey(M.CURRENT_MOST_RIGHT, SWT.END);
		bindKey(M.CURRENT_MOST_UP, SWT.MOD1 | SWT.PAGE_UP);
		bindKey(M.CURRENT_MOST_DOWN, SWT.MOD1 | SWT.PAGE_DOWN);
		bindKey(M.CURRENT_START, SWT.MOD1 | SWT.HOME);
		bindKey(M.CURRENT_END, SWT.MOD1 | SWT.END);
		
		// Key Selection
		bindKey(M.SELECT_ALL, SWT.MOD1 | 'a');
		bindKey(M.SELECT_UP, SWT.MOD2 | SWT.ARROW_UP);
		bindKey(M.SELECT_DOWN, SWT.MOD2 | SWT.ARROW_DOWN);
		bindKey(M.SELECT_LEFT, SWT.MOD2 | SWT.ARROW_LEFT);
		bindKey(M.SELECT_RIGHT, SWT.MOD2 | SWT.ARROW_RIGHT);
		bindKey(M.SELECT_PAGE_UP, SWT.MOD2 | SWT.PAGE_UP);
		bindKey(M.SELECT_PAGE_DOWN, SWT.MOD2 | SWT.PAGE_DOWN);
		bindKey(M.SELECT_PAGE_LEFT, SWT.MOD2 | SWT.MOD3 | SWT.ARROW_LEFT);
		bindKey(M.SELECT_PAGE_RIGHT, SWT.MOD2 | SWT.MOD3 | SWT.ARROW_RIGHT);
		bindKey(M.SELECT_FULL_UP, SWT.MOD1 | SWT.MOD2 | SWT.PAGE_UP);
		bindKey(M.SELECT_FULL_DOWN, SWT.MOD1 | SWT.MOD2 | SWT.PAGE_DOWN);
		bindKey(M.SELECT_FULL_LEFT, SWT.MOD2 | SWT.HOME);
		bindKey(M.SELECT_FULL_RIGHT, SWT.MOD2 | SWT.END);
		bindKey(M.SELECT_START, SWT.MOD1 | SWT.MOD2 | SWT.HOME);
		bindKey(M.SELECT_END, SWT.MOD1 | SWT.MOD2 | SWT.END);
		
		// Mouse current item 
		bindings.add(new Binding(M.CURRENT_LOCATION, SWT.MouseDown, 1, Zone.BODY));
		bindings.add(new Binding(M.CURRENT_LOCATION2, SWT.MouseDown, SWT.MOD1 | 1, Zone.BODY));
		
		// Mouse selection
		/* 1 is for e.button == 1 */
		bindings.add(new Binding(M.SELECT_TO_LOCATION, SWT.MouseDown, SWT.MOD2 | 1, Zone.BODY));
		bindings.add(new Binding(M.SELECT_TO_LOCATION2, SWT.MouseDown, SWT.MOD1 | SWT.MOD2 | 1, Zone.BODY));
		bindings.add(new Binding(M.SELECT_TO_LOCATION, SWT.MouseMove, SWT.BUTTON1, Zone.BODY));
		bindings.add(new Binding(M.SELECT_TO_LOCATION2, SWT.MouseMove, SWT.MOD1 + SWT.BUTTON1, Zone.BODY));
		bindings.add(new Binding(M.SELECT_ROW, SWT.MouseDown, 1, Zone.ROW_HEADER));
		bindings.add(new Binding(M.SELECT_ROW2, SWT.MouseDown, SWT.MOD1 | 1, Zone.ROW_HEADER));
		bindings.add(new Binding(M.SELECT_COLUMN, SWT.MouseDown, 1, Zone.COLUMN_HEADER));
		bindings.add(new Binding(M.SELECT_COLUMN2, SWT.MouseDown, SWT.MOD1 | 1, Zone.COLUMN_HEADER));
		bindings.add(new Binding(M.SELECT_TO_ROW, SWT.MouseDown, SWT.MOD2 | 1, Zone.ROW_HEADER));
		bindings.add(new Binding(M.SELECT_TO_ROW2, SWT.MouseDown, SWT.MOD1 | SWT.MOD2 | 1, Zone.ROW_HEADER));
		bindings.add(new Binding(M.SELECT_TO_ROW, SWT.MouseMove, SWT.BUTTON1, Zone.ROW_HEADER));
		bindings.add(new Binding(M.SELECT_TO_ROW2, SWT.MouseMove, SWT.MOD1 | SWT.BUTTON1, Zone.ROW_HEADER));
		bindings.add(new Binding(M.SELECT_TO_COLUMN, SWT.MouseDown, SWT.MOD2 | 1, Zone.COLUMN_HEADER));
		bindings.add(new Binding(M.SELECT_TO_COLUMN2, SWT.MouseDown, SWT.MOD1 | SWT.MOD2 | 1, Zone.COLUMN_HEADER));
		bindings.add(new Binding(M.SELECT_TO_COLUMN, SWT.MouseMove, SWT.BUTTON1, Zone.COLUMN_HEADER));
		bindings.add(new Binding(M.SELECT_TO_COLUMN2, SWT.MouseMove, SWT.MOD1 | SWT.BUTTON1, Zone.COLUMN_HEADER));
		// Select all on top left click
		bindings.add(new Binding(M.SELECT_ALL, SWT.MouseDown, 1, Zone.TOP_LEFT));
		
		bindings.add(new Binding(M.RESIZE_PACK, SWT.MouseDoubleClick, 1, Zone.ROW_HEADER));
		bindings.add(new Binding(M.RESIZE_PACK, SWT.MouseDoubleClick, 1, Zone.COLUMN_HEADER));
		
		// Modification
		bindKey(M.HIDE, SWT.MOD3 | SWT.DEL);
		bindKey(M.UNHIDE, SWT.MOD3 | SWT.INSERT);
	}

	protected void bindKey(int commandId, int condition) {
		bindings.add(new Binding(commandId, SWT.KeyDown, condition));
	}
	
	private boolean isSelectable() {
		return state0.isSelectable() && state1.isSelectable();
	}
	
	protected void executeCommand(int commandId) {
//		System.out.println("execute " + commandId);
		switch (commandId) {
		case RESIZE_PACK:		state0.pack(); state1.pack(); break;
		case SELECT_ALL:		matrix.model.setSelected(true); matrix.redraw(); return;
		}
		
		if (!isSelectable()) return;
		
		moveCursor(commandId);
		if (!isExtendingSelect(commandId)) {
			state0.last = state0.item;
			state1.last = state1.item;
		}
		
		backupRestoreSelection(commandId);
		
		switch (commandId) {
		// Header Selection
		case SELECT_ROW:		case SELECT_ROW2:		state0.setSelected(commandId); break;	
		case SELECT_COLUMN: 	case SELECT_COLUMN2: 	state1.setSelected(commandId); break;
		case SELECT_TO_ROW:		case SELECT_TO_ROW2:	state0.setSelected(commandId); break;	
		case SELECT_TO_COLUMN:	case SELECT_TO_COLUMN2:	state1.setSelected(commandId); break;
			
		// Hiding
		case HIDE:				state0.hide(true);  state1.hide(true);  break; 
		case UNHIDE:			state0.hide(false); state1.hide(false); break;
		case RESIZE_PACK:		state0.pack(); break;
		}

		if (isBodySelect(commandId) || m0 != null || m1 != null) {
			selectCells(commandId);
		}
		matrix.redraw();
		// forceFocus(); // in order to make focus go out of a pop-up editor
	}
	
	protected void moveCursor(int commandId) {
		m0 = null; m1 = null;
		switch (commandId) {
		
		case CURRENT_DOWN: 		case SELECT_DOWN: 		m0 = Move.NEXT; break;
		case CURRENT_UP:   		case SELECT_UP:			m0 = Move.PREVIOUS; break;
		case CURRENT_RIGHT: 	case SELECT_RIGHT:		m1 = Move.NEXT; break;
		case CURRENT_LEFT: 		case SELECT_LEFT:		m1 = Move.PREVIOUS; break;
		
		case CURRENT_PAGE_DOWN:	case SELECT_PAGE_DOWN:	m0 = Move.NEXT_PAGE; break;
		case CURRENT_PAGE_UP:	case SELECT_PAGE_UP: 	m0 = Move.PREVIOUS_PAGE; break;
		case CURRENT_PAGE_RIGHT:case SELECT_PAGE_RIGHT:	m1 = Move.NEXT_PAGE; break;
		case CURRENT_PAGE_LEFT:	case SELECT_PAGE_LEFT:	m1 = Move.PREVIOUS_PAGE; break;

		case CURRENT_MOST_DOWN:	case SELECT_FULL_DOWN:	m0 = Move.END; break;
		case CURRENT_MOST_UP:	case SELECT_FULL_UP:	m0 = Move.HOME; break;
		case CURRENT_MOST_RIGHT:case SELECT_FULL_RIGHT:	m1 = Move.END; break;
		case CURRENT_MOST_LEFT:	case SELECT_FULL_LEFT:	m1 = Move.HOME; break;
		
		case CURRENT_START:		case SELECT_START:		m1 = Move.HOME;
														m0 = Move.HOME; break;
						
		case CURRENT_END:		case SELECT_END:		m1 = Move.END;
														m0 = Move.END; break;
		
		case CURRENT_LOCATION: 		case CURRENT_LOCATION2:
		case SELECT_TO_LOCATION:	case SELECT_TO_LOCATION2:
		case SELECT_TO_COLUMN: 		case SELECT_TO_COLUMN2:
		case SELECT_COLUMN:			case SELECT_COLUMN2:
		case SELECT_TO_ROW: 		case SELECT_TO_ROW2:
		case SELECT_ROW:			case SELECT_ROW2:
			state0.setCurrentItem();
			state1.setCurrentItem();
			break;														
		}
		
		if (m0 != null) state0.moveCurrentItem(m0);
		if (m1 != null) state1.moveCurrentItem(m1);
		if (m0 != null || m1 != null) {
			state0.item = state0.layout.current;
			state1.item = state1.layout.current;
		}
	}

	
	private void backupRestoreSelection(int commandId) {
		if (commandId == CURRENT_LOCATION2 ||  
			commandId == SELECT_COLUMN2 || commandId == SELECT_ROW2) 
		{
			// Backup the zones cell selection
			for (Zone zone: matrix.model) {
				if (zone.isSelectionEnabled()) {
					zone.backupSelection();
				}
			}
		} 
		else if (commandId == SELECT_TO_LOCATION2 ||  
				 commandId == SELECT_TO_COLUMN2 || commandId == SELECT_TO_ROW2) 
		{
			for (Zone zone: matrix.model) {
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
		
		boolean ctrlSelection = commandId == CURRENT_LOCATION2 || commandId == SELECT_TO_LOCATION2;
		
		if (ctrlSelection && isSelected(state0.last, state1.last)) {
			matrix.model.setSelected(state0.last, state0.item, state1.last, state1.item, false);
		}
		else {
			if (!ctrlSelection) {
				matrix.model.setSelected(false);
			}
			matrix.model.setSelected(state0.last, state0.item, state1.last, state1.item, true);			
		}
		addEvent(SWT.Selection);
	}
	
	private boolean isSelected(AxisItem last0, AxisItem last1) {
		return matrix.model.getZone(last0.section, last1.section).
			isSelected(last0.index, last1.index);
	}


	public void addEvent(int type) {
		Event event = new Event();
		event.type = type;
		event.widget = matrix;
		listeners.add(event);
	}
	
	private void sendEvents() {
		state0.axis.listeners.sendEvents();
		state1.axis.listeners.sendEvents();
		listeners.sendEvents();
	}
	
	/**
	 * Gets the 
	 * @param commandId command id defined by a constant in {@link M}
	 * @param eventType SWT event type
	 * @return
	 */
	public Binding getBinding(int commandId, int eventType) {
		for (Binding b: bindings) {
			if (b.commandId == commandId && b.eventType == eventType) {
				return b;
			}
		}
		return null;
	}
	
	void setLayout(Layout layout0, Layout layout1) {
		state0.layout = layout0;
		state0.model = layout0.model;
		state1.layout = layout1;
		state1.model = layout1.model;
		state0.autoScroll = state0.new AutoScroll();
		state1.autoScroll = state1.new AutoScroll();
	}
}

