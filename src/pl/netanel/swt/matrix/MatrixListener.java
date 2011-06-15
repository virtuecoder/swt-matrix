package pl.netanel.swt.matrix;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static pl.netanel.swt.matrix.Matrix.*;

import java.util.ArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;


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
		/*SWT.Move, SWT.Resize, SWT.Dispose, */
		SWT.Selection, SWT.DefaultSelection, /* 
		SWT.FocusIn, SWT.FocusOut, SWT.Expand, SWT.Collapse, SWT.Iconify, SWT.Deiconify, SWT.Close, 
		SWT.Show, SWT.Hide, SWT.Modify, SWT.Verify, SWT.Activate, SWT.Deactivate, SWT.Help, */
		/* SWT.DragDetect, listening to DragDetect causes the MouseMove to skip 4 pixels */ 
		/* SWT.Arm, SWT.Traverse, SWT.MouseHover, SWT.HardKeyDown, SWT.HardKeyUp, SWT.MenuDetect, 
		SWT.SetData, SWT.MouseWheel, SWT.Settings, SWT.EraseItem, SWT.MeasureItem, 
		SWT.PaintItem, SWT.ImeComposition */
	};

	Matrix<? extends Number, ? extends Number> matrix;
	ArrayList<GestureBinding> bindings;
	AxisListener state0, state1;
	boolean instantMoving, ctrlSelectionMoving;
	Zone<? extends Number, ? extends Number> zone;
	Cursor cursor;
	AxisItem[] lastRange;
	Zone body, columnHeader, rowHeader, topLeft;

	private Move m0, m1;
	
	public MatrixListener(Matrix matrix) {
		this.matrix = matrix;
		lastRange = new AxisItem[4]; 
		body = ((ZoneClient) matrix.getBody()).core;
		rowHeader = matrix.model.getZoneUnchecked(matrix.axis0.getBody(), matrix.axis1.getHeader());
		columnHeader = matrix.model.getZoneUnchecked(matrix.axis0.getHeader(), matrix.axis1.getBody());
		topLeft = matrix.model.getZoneUnchecked(matrix.axis0.getHeader(), matrix.axis1.getHeader());
		
		// Initialize fields
		state0 = new AxisListener(matrix.axis0);
		state1 = new AxisListener(matrix.axis1);
		
		bindings = new ArrayList<GestureBinding>();
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
				zone = matrix.model.getZoneUnchecked(state0.item.getSectionUnchecked(), state1.item.getSectionUnchecked());
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

			boolean mouseEvent = SWT.MouseDown <= e.type && e.type <= SWT.MouseDoubleClick;
			boolean keyEvent = e.type == SWT.KeyDown || e.type == SWT.KeyUp;
			if (e.type == SWT.MouseMove && !(state0.itemModified || state1.itemModified)) {
				mouseEvent = false;
			}
			if (e.doit && (mouseEvent || keyEvent)) {
//				matrix.t = System.nanoTime();

				// Find a command for the current zone or otherwise a command for any zone
				for (GestureBinding b: bindings) {
					if (b.isMatching(e)) {
						executeCommand(b.commandId);
						/* does not quit loop because can execute 
					   	   many both any zone and a specific zone bindings */
					}
				}
				if (zone != null) {
					for (GestureBinding b: zone.bindings) {
						if (b.isMatching(e)) {
							executeCommand(b.commandId);
							/* does not quit loop because can execute 
					   	   	   many both any zone and a specific zone bindings */
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
		Layout layout;
		AxisItem<N> last, item, resizeItem;
		boolean moving, resizing, itemModified = true, mouseDown;
		Event mouseMoveEvent;
		Cursor resizeCursor;
		int resizeStartDistance, resizeCellWidth, newCellWidth, distance, lastDistance;
		AutoScroll autoScroll;
		boolean focusMoved = true;
		private int resizeEvent;

		public AxisListener(Axis<N> axis) {
			this.axis = axis; 
			this.axisIndex = axis.index;
			if (axisIndex == 0) {
				resizeCursor = Resources.getCursor(SWT.CURSOR_SIZENS);
			} else {
				resizeCursor = Resources.getCursor(SWT.CURSOR_SIZEWE);
			}
			item = axis.getFocusItem();
		}
		
		public void setItem(Event e) {
			if (e.type == SWT.MouseMove) {
				distance = axisIndex == 0 ? e.y : e.x;
				AxisItem item2 = autoScroll.future != null && autoScroll.item != null  
					? autoScroll.item  
					: layout.getItemByDistance(distance);
				
				if (item2 != null && item != null) {
					itemModified = layout.compare(item, item2) != 0;
//					last = item;
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
				mouseDown = e.button == 1;
				lastDistance = distance;
				if (mouseDown && isInHeader() && e.stateMask == 0) {
					if (resizeItem != null) {
						resizing = true;
						resizeStartDistance = distance;
						resizeCellWidth = resizeItem.getSectionUnchecked().getCellWidth(resizeItem.getIndex());
					}
					else if (item.getSectionUnchecked().isSelected(item.getIndex()) && item.getSectionUnchecked().isMoveable(item.getIndex())) {
						// Start moving
						moving = true;
						matrix.setCursor(cursor = Resources.getCursor(SWT.CURSOR_HAND));
					}
				}
				break;
				
			case SWT.MouseUp:
				// Resize all selected except the current one
				if (resizeEvent == SWT.MouseMove) {
					int len = axis.sections.size();
					for (int i = 0; i < len; i++) {
						Section<N> section = axis.sections.get(i);
						ExtentSequence<N> seq = section.getSelectedExtentResizableSequence();
						for (seq.init(); seq.next();) {
							
							if (item.getSectionUnchecked().equals(section) && 
									layout.math.compare(seq.start, item.getIndex()) == 0 &&
									layout.math.compare(seq.end, item.getIndex()) == 0) {
								continue;
							}
							section.setCellWidth(seq.start, seq.end, newCellWidth);
						}
						addEvent(section, SWT.Resize, resizeItem);
						layout.compute();
						matrix.redraw();
					}
				}
				else if (resizeEvent == SWT.MouseDoubleClick) {
					int len = axis.sections.size();
					for (int i = 0; i < len; i++) {
						Section<N> section = axis.sections.get(i);
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
				break;
			}
		}

		private void handleDrag(Event e) {
			resizeEvent = 0;
			// Resize item
			if (resizing && resizeItem != null) {
				newCellWidth = resizeCellWidth + distance - resizeStartDistance;
				if (newCellWidth < 1) newCellWidth = 1;
				resizeItem.getSectionUnchecked().setCellWidth(
						resizeItem.getIndex(), resizeItem.getIndex(), newCellWidth);
				layout.compute();
				matrix.redraw();
				resizeEvent = SWT.MouseMove;
				addEvent(resizeItem.getSectionUnchecked(), SWT.Resize, resizeItem);
				//event.data = matrix.getZone(axisIndex == 0 ? Zone.ROW_HEADER : Zone.COLUMN_HEADER);
			}
			else {
				// Auto-scroll
				if (!(e.data instanceof AxisItem[])) {
					autoScroll.handle();
				}

				// Move item
				if (moving && instantMoving && itemModified) {
					reorder();
				} 
			}
		}

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
				layout.setCurrentItem(item);
			}
		}

		public void moveFocusItem(Move move) {
			if (matrix.isFocusCellEnabled() && item != null)  {
				matrix.model.setSelected(false);
				focusMoved = layout.moveFocusItem(move);
				if (focusMoved) {
					axis.scroll();
				}
			}
		}
		
		private boolean isSelected(AxisItem item) {
			return item.getSectionUnchecked().isSelected(item.getIndex());
		}
		
		public void setSelected(int commandId) {
			if (last == null || item == null) return;
			if (last.getSectionUnchecked() != item.getSectionUnchecked()) return;
			
			if (commandId == CMD_SELECT_COLUMN || commandId == CMD_SELECT_COLUMN_ALTER ||
				commandId == CMD_SELECT_ROW || commandId == CMD_SELECT_ROW_ALTER) {
				
				// Backup all sections cell selection
				for (int i = 0, imax = axis.getSectionCount(); i < imax; i++) {
					axis.sections.get(i).backupSelection();
				}
				
			} 
			else if (commandId == CMD_SELECT_TO_COLUMN_ALTER || commandId == CMD_SELECT_TO_ROW_ALTER) {
				// Restore previous selection from the backup
				for (int i = 0, imax = axis.getSectionCount(); i < imax; i++) {
					axis.sections.get(i).restoreSelection();
				}
			}
			
			// Make sure start < end
			boolean forward = axis.comparePosition(last, item) <= 0;
			AxisItem start = forward ? last : item; 
			AxisItem end = forward ? item : last; 

			boolean ctrlSelection = 
				commandId == CMD_SELECT_COLUMN_ALTER || commandId == CMD_SELECT_TO_COLUMN_ALTER ||
				commandId == CMD_SELECT_ROW_ALTER || commandId == CMD_SELECT_TO_ROW_ALTER;
			
			if (!ctrlSelection) {
				axis.setSelected(false);
				matrix.model.setSelected(false);
			}
			
			axis.setSelected(start, end, !(ctrlSelection && isSelected(start)));
			if (axisIndex == 0) {
				Axis model1 = matrix.model.axis1;
				matrix.model.setSelected(start, end, model1.getFirstItem(), model1.getLastItem(), true);
			} else {
				Axis model0 = matrix.model.axis0;
				matrix.model.setSelected(model0.getFirstItem(), model0.getLastItem(), start, end, true);
			}
		}
		
		private void addEvent(Section<N> section, int type, Object data) {
			Event event = new Event();
			event.type = type;
			event.widget = matrix;
			event.data = data;
			//event.data = matrix.getZone(axisIndex == 0 ? Zone.ROW_HEADER : Zone.COLUMN_HEADER);
			section.listeners.add(event);
		}

		void reorder() {
			if (layout.reorder(last, item)) {
//			AxisItem item2 = layout.reorder(item);
//			if (item2 != null) {
//				/* Move the cursor if it is outside of the moved column 
//				 (happens when items have different widths) */
//				int direction = axis.comparePosition(item2, item);
//				int cellDistance = layout.getCellDistance(item2);
//				Point p = null;
//				if (direction > 0 && distance < cellDistance) {
//					p = matrix.getLocation();
//					p.x = axisIndex == 0 ? mouseMoveEvent.x : cellDistance;
//					p.y = axisIndex == 0 ? cellDistance : mouseMoveEvent.y;
//				} 
//				else if (direction < 0) {
//					int lineDistance = cellDistance + axis.getCellWidth(item2);
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
				addEvent(item.getSectionUnchecked(), SWT.Move, item);
				axis.scroll();
				matrix.redraw();
//				return true;
			}
		}
		
		public void pack() {
			if (resizeItem == null) return;
			axis.pack(resizeItem);
			resizeEvent = SWT.MouseDoubleClick;
			addEvent(resizeItem.getSectionUnchecked(), SWT.Resize, resizeItem);
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

		public void refresh() {
			if (item == null) return;
			N count = item.getSectionUnchecked().getCount();
			if (axis.math.compare(item.getIndex(), count) >= 0) {
				item = axis.math.compare(count, axis.math.ZERO_VALUE()) == 0 ? null : 
					AxisItem.create(item.getSectionUnchecked(), axis.math.decrement(count));
			}
		}

		public void sendEvents() {
			for (Section section: axis.sections) {
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
		body.bindings.add(new GestureBinding(Matrix.CMD_FOCUS_LOCATION, SWT.MouseDown, 1));
		body.bindings.add(new GestureBinding(Matrix.CMD_FOCUS_LOCATION_ALTER, SWT.MouseDown, SWT.MOD1 | 1));
		
		// Mouse selection
		/* 1 is for e.button == 1 */
		body.bindings.add(new GestureBinding(Matrix.CMD_SELECT_TO_LOCATION, SWT.MouseDown, SWT.MOD2 | 1));
		body.bindings.add(new GestureBinding(Matrix.CMD_SELECT_TO_LOCATION_ALTER, SWT.MouseDown, SWT.MOD1 | SWT.MOD2 | 1));
		body.bindings.add(new GestureBinding(Matrix.CMD_SELECT_TO_LOCATION, SWT.MouseMove, SWT.BUTTON1));
		body.bindings.add(new GestureBinding(Matrix.CMD_SELECT_TO_LOCATION_ALTER, SWT.MouseMove, SWT.MOD1 | SWT.BUTTON1));
		
		if (rowHeader != null) {
			rowHeader.bindings.add(new GestureBinding(Matrix.CMD_SELECT_ROW, SWT.MouseDown, 1));
			rowHeader.bindings.add(new GestureBinding(Matrix.CMD_SELECT_ROW_ALTER, SWT.MouseDown, SWT.MOD1 | 1));
			rowHeader.bindings.add(new GestureBinding(Matrix.CMD_SELECT_TO_ROW, SWT.MouseDown, SWT.MOD2 | 1));
			rowHeader.bindings.add(new GestureBinding(Matrix.CMD_SELECT_TO_ROW_ALTER, SWT.MouseDown, SWT.MOD1 | SWT.MOD2 | 1));
			rowHeader.bindings.add(new GestureBinding(Matrix.CMD_SELECT_TO_ROW, SWT.MouseMove, SWT.BUTTON1));
			rowHeader.bindings.add(new GestureBinding(Matrix.CMD_SELECT_TO_ROW_ALTER, SWT.MouseMove, SWT.MOD1 | SWT.BUTTON1));
			rowHeader.bindings.add(new GestureBinding(Matrix.CMD_RESIZE_PACK, SWT.MouseDoubleClick, 1));
		}
		if (columnHeader != null) {
			columnHeader.bindings.add(new GestureBinding(Matrix.CMD_SELECT_COLUMN, SWT.MouseDown, 1));
			columnHeader.bindings.add(new GestureBinding(Matrix.CMD_SELECT_COLUMN_ALTER, SWT.MouseDown, SWT.MOD1 | 1));
			columnHeader.bindings.add(new GestureBinding(Matrix.CMD_SELECT_TO_COLUMN, SWT.MouseDown, SWT.MOD2 | 1));
			columnHeader.bindings.add(new GestureBinding(Matrix.CMD_SELECT_TO_COLUMN_ALTER, SWT.MouseDown, SWT.MOD1 | SWT.MOD2 | 1));
			columnHeader.bindings.add(new GestureBinding(Matrix.CMD_SELECT_TO_COLUMN, SWT.MouseMove, SWT.BUTTON1));
			columnHeader.bindings.add(new GestureBinding(Matrix.CMD_SELECT_TO_COLUMN_ALTER, SWT.MouseMove, SWT.MOD1 | SWT.BUTTON1));
			columnHeader.bindings.add(new GestureBinding(Matrix.CMD_RESIZE_PACK, SWT.MouseDoubleClick, 1));
		}
		if (topLeft != null) {
			// Select all on top left click
			topLeft.bindings.add(new GestureBinding(Matrix.CMD_SELECT_ALL, SWT.MouseDown, 1));
		}
		
		// Modification
		bindKey(Matrix.CMD_HIDE, SWT.MOD3 | SWT.DEL);
		bindKey(Matrix.CMD_UNHIDE, SWT.MOD3 | SWT.INSERT);
	}

	protected void bindKey(int commandId, int condition) {
		bindings.add(new GestureBinding(commandId, SWT.KeyDown, condition));
	}
	
	private boolean isSelectable() {
		return state0.isSelectable() && state1.isSelectable();
	}
	
	protected void executeCommand(int commandId) {
//		System.out.println("execute " + commandId);
		switch (commandId) {
		case CMD_RESIZE_PACK:		state0.pack(); state1.pack(); break;
		
		case CMD_EDIT:				if (zone.editor != null) zone.editor.edit(); return;
		case CMD_CUT:				if (zone.editor != null) zone.editor.cut(); return;
		case CMD_COPY:				if (zone.editor != null) zone.editor.copy(); return;
		case CMD_PASTE:				if (zone.editor != null) zone.editor.paste(); return;
		case CMD_DELETE:			if (zone.editor != null) zone.editor.delete(); return;
		}
		
		if (!isSelectable()) return;
		
		if (!moveCursor(commandId)) return;
		if (!isExtendingSelect(commandId)) {
			state0.last = state0.item;
			state1.last = state1.item;
//			matrix.selectFocusCell();
		}
		if (commandId == CMD_SELECT_TO_LOCATION || commandId == CMD_SELECT_TO_LOCATION_ALTER) {
			if (state0.last.getSectionUnchecked().equals(state0.axis.getHeader())) {
				commandId = commandId == CMD_SELECT_TO_LOCATION ? CMD_SELECT_TO_COLUMN : CMD_SELECT_TO_COLUMN_ALTER;
			}
			else if (state1.last.getSectionUnchecked().equals(state1.axis.getHeader())) {
				commandId = commandId == CMD_SELECT_TO_LOCATION ? CMD_SELECT_TO_ROW : CMD_SELECT_TO_ROW_ALTER;
			}
		}
		
		backupRestoreSelection(commandId);
		
		switch (commandId) {
		case CMD_SELECT_ALL:		matrix.model.setSelected(true); matrix.redraw(); return;
		// Header Selection
		case CMD_SELECT_ROW:		case CMD_SELECT_ROW_ALTER:		state0.setSelected(commandId); break;	
		case CMD_SELECT_COLUMN: 	case CMD_SELECT_COLUMN_ALTER: 	state1.setSelected(commandId); break;
		case CMD_SELECT_TO_ROW:		case CMD_SELECT_TO_ROW_ALTER:	state0.setSelected(commandId); break;	
		case CMD_SELECT_TO_COLUMN:	case CMD_SELECT_TO_COLUMN_ALTER:	state1.setSelected(commandId); break;
			
		// Hiding
		case CMD_HIDE:				state0.hide(true);  state1.hide(true);  break; 
		case CMD_UNHIDE:			state0.hide(false); state1.hide(false); break;
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
			for (Zone<? extends Number, ? extends Number> zone: matrix.model.zones) {
				if (zone.selectionEnabled) {
					zone.backupSelection();
				}
			}
		} 
		else if (commandId == CMD_SELECT_TO_LOCATION_ALTER ||  
				 commandId == CMD_SELECT_TO_COLUMN_ALTER || commandId == CMD_SELECT_TO_ROW_ALTER) 
		{
			for (Zone zone: matrix.model.zones) {
				if (zone.selectionEnabled) {
					zone.restoreSelection();
				}
			}
		}
	}
	
	private void selectCells(int commandId) {
		if (state0.item == null || state1.item == null) return;
		if (state0.last == null) state0.last = state0.item;
		if (state1.last == null) state1.last = state1.item;
		
		boolean ctrlSelection = commandId == CMD_FOCUS_LOCATION_ALTER || commandId == CMD_SELECT_TO_LOCATION_ALTER;
		
		if (ctrlSelection && isSelected(state0.last, state1.last)) {
			matrix.model.setSelected(state0.last, state0.item, state1.last, state1.item, false);
		}
		else {
			if (!ctrlSelection) {
				matrix.model.setSelected(false);
			}
			matrix.model.setSelected(state0.last, state0.item, state1.last, state1.item, true);			
		}
	}
	
	private boolean isSelected(AxisItem last0, AxisItem last1) {
		return matrix.model.getZoneUnchecked(last0.getSectionUnchecked(), last1.getSectionUnchecked()).
			isSelected(last0.getIndex(), last1.getIndex());
	}


	private void sendEvents() {
		state0.sendEvents();
		state1.sendEvents();
		for (Zone zone: matrix.model.zones) {
			zone.listeners.sendEvents();
		}
	}
	
	/**
	 * Gets the 
	 * @param commandId command id defined by a constant in {@link M}
	 * @param eventType SWT event type
	 * @return
	 */
	public GestureBinding getBinding(int commandId, int eventType) {
		for (GestureBinding b: bindings) {
			if (b.commandId == commandId && b.eventType == eventType) {
				return b;
			}
		}
		return null;
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
}

