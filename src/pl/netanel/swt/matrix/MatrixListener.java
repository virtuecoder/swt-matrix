/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static pl.netanel.swt.matrix.Matrix.*;

import java.math.BigInteger;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
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
  private enum DropPosition { OUT_BEFORE, BEFORE, AFTER, OUT_AFTER, NONE }

  Matrix<X, Y> matrix;
  //	ArrayList<GestureBinding> bindings;
  AxisListener<X> stateX;
  AxisListener<Y> stateY;
  boolean instantMoving, mouseDown;
  ZoneCore<X, Y> zone;
  Cursor cursor;
  ZoneCore<X, Y> body, columnHeader, rowHeader, topLeft;

  Event mouseMoveEvent;
  Point imageOffset;
  private Move mY, mX;

  private CellExtent<X, Y> span;

  ZoneCore<X, Y> lastZone;

  public MatrixListener(final Matrix<X, Y> matrix) {
    this.matrix = matrix;
    SectionCore<X> bodyX = SectionCore.from(matrix.axisX.getBody());
    SectionCore<Y> bodyY = SectionCore.from(matrix.axisY.getBody());
    SectionCore<X> headerX = SectionCore.from(matrix.axisX.getHeader());
    SectionCore<Y> headerY = SectionCore.from(matrix.axisY.getHeader());
    body = matrix.layout.getZone(bodyX, bodyY);
    rowHeader = matrix.layout.getZone(headerX, bodyY);
    columnHeader = matrix.layout.getZone(bodyX, headerY);
    topLeft = matrix.layout.getZone(headerX, headerY);

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

    matrix.addListener(SWT.MouseExit, new Listener() {
      @Override
      public void handleEvent(Event event) {
        lastZone = null;
        matrix.setCursor(cursor = null);
      }
    });

    stateX.axisLayout = matrix.layoutX;
    stateY.axisLayout = matrix.layoutY;
    stateX.axis = matrix.axisX;
    stateY.axis = matrix.axisY;
    stateY.autoScroll = stateY.new AutoScroll();
    stateX.autoScroll = stateX.new AutoScroll();

    //		instantMoving = true;
  }


  @Override
  public void handleEvent(Event e) {
    //	  System.out.println(SwtTestCase.getTypeName(e.type));
    try {
      stateX.setItem(e);
      stateY.setItem(e);

      zone = null;
      boolean keyEvent = e.type == SWT.KeyDown || e.type == SWT.KeyUp;
      if (keyEvent) {
        AxisItem<X> focusItemX = matrix.getAxisX().getFocusItem();
        AxisItem<Y> focusItemY = matrix.getAxisY().getFocusItem();
        if (focusItemX != null && focusItemY != null) {
          zone = matrix.layout.getZone(focusItemX.section, focusItemY.section);
        }
      }
      else if (stateY.item != null && stateX.item != null) {
        zone = matrix.layout.getZone(stateX.item.section, stateY.item.section);
      }

      // Generate mouse enter/exit for the zone
      generateMouseEnterOrExitForZones(e);

      if (e.type == SWT.MouseDown && e.button == 1) {
        mouseDown = true;
        matrix.forceFocus();
      }
      stateX.update(e, e.x);
      stateY.update(e, e.y);

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
        boolean mouseEvent = GestureBinding.isMouseEvent(e.type);
        if (e.type == SWT.MouseMove && !(stateY.itemModified || stateX.itemModified)) {
          mouseEvent = false;
        }
        if (e.doit && (mouseEvent || keyEvent)) {
          //				matrix.t = System.nanoTime();

          // Find a command for the current zone or otherwise a command for any zone
          for (GestureBinding b: zone.getBindings()) {
            if (b.isMatching(e)) {
              if (e.type != SWT.MouseMove || mouseDown) {
                b.event = e;
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


  private void generateMouseEnterOrExitForZones(Event e) {
    if (e.type == SWT.MouseMove) {
      if (lastZone != null && !lastZone.equals(zone)) {
        Event event = new Event();
        event.type = SWT.MouseExit;
        event.widget = e.widget;
        event.display = e.display;
        event.doit = true;
        event.time = e.time;

        for (ZoneCore<X,Y>.ZoneListener zoneListener: lastZone.zoneListeners) {
          if (zoneListener.eventType == SWT.MouseExit) {
            zoneListener.listener.handleEvent(event);
          }
        }
      }

      if (zone != null && !zone.equals(lastZone)) {
        Event event = new Event();
        event.type = SWT.MouseEnter;
        event.widget = e.widget;
        event.display = e.display;
        event.doit = true;
        event.time = e.time;

        for (ZoneCore<X,Y>.ZoneListener zoneListener: zone.zoneListeners) {
          if (zoneListener.eventType == SWT.MouseEnter) {
            zoneListener.listener.handleEvent(event);
          }
        }
        lastZone = zone;
      }
    }
  }

  class AxisListener<N extends Number> {
    Axis<N> axis;
    AxisLayout<N> axisLayout;
    AxisItem<N> last, item, prev, resizeItem, lastFocus;
    boolean moving, resizing, itemModified = true;

    Cursor resizeCursor;
    int resizeStartDistance, resizeCellWidth, newCellWidth, distance, lastDistance;
    AutoScroll autoScroll;
    boolean focusMoved = true;
    private int resizeEvent;
    private boolean selectState;
    AxisItem<N> mouseOverItem;
    MutableNumber<N> tmp;
    private Frozen frozen;

    public AxisListener(Axis<N> axis) {
      this.axis = axis;
      if (axis.symbol == 'X') {
        resizeCursor = Resources.getCursor(SWT.CURSOR_SIZEWE);
      }
      //			item = last = axis.getFocusItem();
      else {
        resizeCursor = Resources.getCursor(SWT.CURSOR_SIZENS);
      }
      tmp = axis.math.create(0);
    }

    public void setItem(Event e) {
      if (e.type == SWT.Activate) {
        if (item == null) {
          item = last = axis.getFocusItem();
        }
      }
      else if (e.type == SWT.MouseMove) {
//        if (axis.symbol == 'X')
//          System.out.print(item + " -> ");
        distance = axis.symbol == 'X' ? e.x : e.y;
        mouseOverItem = autoScroll.future != null && autoScroll.item != null
            ? autoScroll.item
                : axisLayout.getItemByDistance(distance);

        if (mouseOverItem != null) {
          if (item != null) {
            itemModified = axisLayout.compare(item, mouseOverItem) != 0;
          }
          //					last = item;
          item = mouseOverItem;
          if (last == null) {
//            if (axis.symbol == 'X') TestUtil.log("null");
//            last = mouseOverItem;
            last = item;
          }
        }
        mouseMoveEvent = e;
        frozen = axisLayout.getFrozenByDistance(distance);
//        if (axis.symbol == 'X')
//          System.out.println(item);
      }
    }

    public void update(Event e, int distance) {
      if (axisLayout.isEmpty()) return;
      this.distance = distance;
      if (item == null) item = last = axisLayout.current;

      switch (e.type) {
      case SWT.MouseMove:
        if (mouseDown) {
          handleDrag(e);
        }
        else {
          if (isInHeader() &&
              (resizeItem = axisLayout.getResizeItem(distance)) != null && !isOverMergedLine()) {
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
        prev = null;
        if (mouseDown && isInHeader()) {
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
              Bound b = axisLayout.getCellBound(item);
              if (b == null) break;
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
        // Resize all selected except the current one
        if (resizeEvent == SWT.MouseMove) {
          int len = axis.sections.size();
          for (int i = 0; i < len; i++) {
            SectionCore<N> section = axisLayout.sections.get(i);
            ExtentSequence2<N> seq = section.getSelectedExtentSequence();
            for (seq.init(); seq.next();) {

              if (item.section.equals(section) &&
                  axisLayout.math.compare(seq.start, item.getIndex()) == 0 &&
                  axisLayout.math.compare(seq.end, item.getIndex()) == 0) {
                continue;
              }
              section.setCellWidth(seq.start, seq.end, newCellWidth);
            }
            //						addEvent(section, SWT.Resize, resizeItem);
            resizeEvent = 0;
            matrix.layout.compute(axis.symbol == 'X', axis.symbol == 'Y');
            matrix.redraw();
          }
        }
        else if (resizeEvent == SWT.MouseDoubleClick) {
          int len = axis.sections.size();
          for (int i = 0; i < len; i++) {
            SectionCore<N> section = axisLayout.sections.get(i);
            NumberSequence2<N> seq = section.getSelectedSequence();
            for (seq.init(); seq.next();) {
              section.setCellWidth(seq.index());
            }
            addEvent(section, SWT.Resize, resizeItem);
            matrix.layout.compute(axis.symbol == 'X', axis.symbol == 'Y');
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
        if (cursor == Resources.getCursor(SWT.CURSOR_HAND)) {
          matrix.setCursor(cursor = null);
        }
        autoScroll.stop();
        prev = null;
        break;
      }
    }

    @SuppressWarnings("unchecked")
    private boolean isOverMergedLine() {
      CellExtent<X, Y> span2 = null;
      if (axis.symbol == 'X') {
        span2 = zone.getMerged((X) resizeItem.index, stateY.item.index);
        if (span2 == null) return false;
        tmp.set((N) span2.endX).add((N) span2.startX).decrement();
      } else {
        span2 = zone.getMerged(stateX.item.index, (Y) resizeItem.index);
        if (span2 == null) return false;
        tmp.set((N) span2.endY).add((N) span2.startY).decrement();
      }
//      System.out.println(span2 + " " + tmp + " " + resizeItem.index + " " +
//       (axis.math.compare(tmp.getValue(), resizeItem.index) != 0));
      boolean result = axis.math.compare(tmp.getValue(), resizeItem.index) != 0;
      if (result) resizeItem = null;
      return result;
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

        //				System.out.println(Matrix.changeCount);
        matrix.layout.compute(axis.symbol == 'X', axis.symbol == 'Y');
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

    public boolean setFocusItem() {
      if (item == null) return false;
      return axisLayout.setFocusItem(item);
    }

    public void moveFocusItem(Move move, N start, N count) {
      if (item != null)  {
        //				matrix.model.setSelected(false, false);

        if (start != null) {
          N index = null;
          switch (move) {
          case HOME: case PREVIOUS: case PREVIOUS_PAGE:
            index = start;
            break;
          case END: case NEXT: case NEXT_PAGE:
            index = axisLayout.current.section.order.getIndexByOffset(start, axisLayout.math.decrement(count));
            break;
          case NULL: break;
          }
          if (index != null) {
            axisLayout.current = AxisItem.create(axisLayout.current.section, index);
          }
        }

        focusMoved = axisLayout.moveFocusItem(move);
        item = axisLayout.current;
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

      boolean skipHidden = matrix.getSelectSkipHidden();
      boolean ctrlSelection =
          commandId == CMD_SELECT_COLUMN_ALTER || commandId == CMD_SELECT_TO_COLUMN_ALTER ||
          commandId == CMD_SELECT_ROW_ALTER || commandId == CMD_SELECT_TO_ROW_ALTER;

      selectState = ctrlSelection
          ? prev == null ? !isSelected(last) : selectState : true;

      //      TestUtil.log(last, item, isSelected(last), prev, selectState);

      // Make sure start < end
      boolean forward = axisLayout.comparePosition(last, item) <= 0;
      AxisItem<N> start = forward ? last : item;
      AxisItem<N> end = forward ? item : last;

      if (commandId == CMD_SELECT_COLUMN || commandId == CMD_SELECT_COLUMN_ALTER ||
          commandId == CMD_SELECT_ROW || commandId == CMD_SELECT_ROW_ALTER) {

        // Backup all sections cell selection
        for (int i = 0, imax = axis.getSectionCount(); i < imax; i++) {
          axisLayout.sections.get(i).backupSelection();
        }

      }
      else if (commandId == CMD_SELECT_TO_COLUMN_ALTER || commandId == CMD_SELECT_TO_ROW_ALTER) {
        // Restore previous selection from the backup
        for (int i = 0, imax = axis.getSectionCount(); i < imax; i++) {
          axisLayout.sections.get(i).restoreSelection();
        }
      }

      if (!ctrlSelection) {
        //axis.setSelected(false, false, false);
        matrix.layout.setSelected(false, false);
      }

      axis.setSelected(start, end, selectState, skipHidden);
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
      AxisItem<N> subject = last, target = item;
      DropPosition dp = getDropPosition(target, distance);
      boolean dropBefore = dp == DropPosition.BEFORE || dp == DropPosition.OUT_BEFORE;
      if (dropBefore && (target == null || axisLayout.compare(target, subject) > 0)) {
        target = axisLayout.nextItem(target, axisLayout.backwardNavigator);
      }
      else if (!dropBefore && (target == null || axisLayout.compare(target, subject) < 0)) {
        target = axisLayout.nextItem(target, axisLayout.forwardNavigator);
      }

      if (axisLayout.reorder(subject, target)) {

        // Adjust cursor location if moving smaller to bigger
        Bound r1 = axisLayout.getCellBound(last);
        Bound r2 = axisLayout.getCellBound(item);
        if (r1 != null && r2 != null && r1.width < r2.width) {
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
      }
    }


    public void pack() {
      if (resizeItem == null) return;
      resizeItem.section.setCellWidth(resizeItem.getIndex());
      resizeEvent = SWT.MouseDoubleClick;
      addEvent(SectionCore.from(resizeItem), SWT.Resize, resizeItem);
      if ((resizeItem = axisLayout.getResizeItem(distance)) == null) {
        matrix.setCursor(cursor = null);
      }
    }

    public void hide(boolean b) {
      axis.setHidden(b);
      axisLayout.isComputingRequired = true;
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
        itemCountIndex = axisLayout.math.create(1);
      }

      public void handle() {
        offset = axisLayout.getAutoScrollOffset(lastDistance, distance);
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
          item = axisLayout.scroll(count, offset > 0 ? axisLayout.forward : axisLayout.backward);
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
      item = last = null;
      if (item == null) return;
      N count = item.section.getCount();
      if (axis.math.compare(item.getIndex(), count) >= 0) {
        item = axis.math.compare(count, axis.math.ZERO_VALUE()) == 0 ? null :
          AxisItem.createInternal(item.section, axis.math.decrement(count));
      }
    }

    public void sendEvents() {
      for (SectionCore<N> section: axisLayout.sections) {
        section.listeners.sendEvents();
      }
    }

    Bound getBodyBound() {
      return axisLayout.getBound(frozen, axisLayout.body);
    }


    Bound getCellBound(AxisItem<N> item) {
      Bound bound = axisLayout.getCellBound(item);
      return bound == null ? Bound.empty : bound;
    }

    Bound getLineBound(AxisItem<N> item) {
      Bound bound = axisLayout.getLineBound(item);
      return bound == null ? Bound.empty : bound;
    }

    DropPosition getDropPosition(AxisItem<N> target, int distance) {
      if (target == null) return DropPosition.NONE;
      if (target.section.index < axisLayout.body.index) return DropPosition.OUT_BEFORE;
      if (target.section.index > axisLayout.body.index) return DropPosition.OUT_AFTER;
//      if (distance < 0) return DropPosition.OUT_BEFORE;
      Bound bound = getCellBound(target);
      return distance < bound.distance + bound.width / 2 ? DropPosition.BEFORE : DropPosition.AFTER;
    }

    int getDropIndicatorDistance(AxisItem<N> item, int distance) {
//      if (distance > 200)
//        System.out.println(getDropPosition(item, distance) + " " + distance + " " + (item == null ? "null" : item));
      switch (getDropPosition(item, distance)) {
      case NONE: return -1;
      case OUT_BEFORE: return getBodyBound().distance;
      case OUT_AFTER: return getBodyBound().endDistance();
      case BEFORE: return getLineBound(item).distance;
      case AFTER: return getCellBound(item).endDistance();
      default: return -1;
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

    //		matrix.bind(Matrix.CMD_FOCUS_LOCATION, SWT.MouseDown, 1);
    //
    //		// Drag and drop
    //		matrix.bind(Matrix.CMD_DND_SELECT_START, SWT.MouseDown, 1);
    //		matrix.bind(Matrix.CMD_DND_SELECT, SWT.MouseMove, SWT.BUTTON1);
    //		matrix.bind(Matrix.CMD_DND_SELECT_STOP, SWT.MouseUp, 1);

//    body.bind(new GestureBinding(Matrix.CMD_COPY, SWT.KeyUp, SWT.MOD1 | 'c'));
//    body.bind(new GestureBinding(Matrix.CMD_PASTE, SWT.KeyUp, SWT.MOD1 | 'v'));

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
      rowHeader.bind(new GestureBinding(Matrix.CMD_PACK_ROW, SWT.MouseDoubleClick, 1));

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
      columnHeader.bind(new GestureBinding(Matrix.CMD_PACK_COLUMN, SWT.MouseDoubleClick, 1));

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
      topLeft.bind(new GestureBinding(Matrix.CMD_PACK_ROW, SWT.MouseDoubleClick, 1));
      topLeft.bind(new GestureBinding(Matrix.CMD_PACK_COLUMN, SWT.MouseDoubleClick, 1));
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

		//System.out.println("execute " + commandId + " last item " + stateY.last);
    switch (commandId) {
    //case CMD_DND_SELECT_START:		 state &= STATE_SELECTING; break;
    //case CMD_DND_SELECT_STOP:		   state &= ~STATE_SELECTING; break;

    case CMD_PACK_COLUMN:     stateX.pack(); break;
    case CMD_PACK_ROW:        stateY.pack(); break;

    case CMD_EDIT_ACTIVATE:				 if (zone.editor != null) zone.editor.edit(b); return;
    case CMD_CUT:				           if (zone.editor != null) zone.editor.cut(); return;
    case CMD_COPY:				         copy(); return;
    case CMD_PASTE:				         paste(); return;
    case CMD_DELETE:			         if (zone.editor != null) zone.editor.delete(); return;
//    case CMD_UNDO:			           if (zone.editor != null) zone.editor.undo(); return;
//    case CMD_REDO:			           if (zone.editor != null) zone.editor.redo(); return;
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
    case CMD_SELECT_ALL:		    matrix.layout.setSelected(true, true); matrix.redraw(); return;
    // Header Selection
    case CMD_SELECT_ROW:		    case CMD_SELECT_ROW_ALTER:		    stateY.setSelected(commandId); break;
    case CMD_SELECT_COLUMN: 	  case CMD_SELECT_COLUMN_ALTER: 	  stateX.setSelected(commandId); break;
    case CMD_SELECT_TO_ROW:		  case CMD_SELECT_TO_ROW_ALTER:	    stateY.setSelected(commandId); break;
    case CMD_SELECT_TO_COLUMN:	case CMD_SELECT_TO_COLUMN_ALTER:	stateX.setSelected(commandId); break;

    // Hiding
    case CMD_ITEM_HIDE:			  stateY.hide(true);  stateX.hide(true);  break;
    case CMD_ITEM_SHOW:			  stateY.hide(false); stateX.hide(false); break;
    }

    if (isBodySelect(commandId)) { // || state0.focusMoved || state1.focusMoved) {
      selectCells(commandId);
    }
    matrix.redraw();
    // forceFocus(); // in order to make focus go out of a pop-up editor
  }


  private void copy() {
    if (matrix.getCopyBeyondBody()) {

      StringBuilder sb = new StringBuilder();
      for (int sectionIndex = 0; sectionIndex < stateY.axisLayout.sections.size(); sectionIndex++) {
        SectionCore<Y> sectionY = stateY.axisLayout.sections.get(sectionIndex);
        if (!sectionY.isVisible()) continue;

        // Find vertical selection bounds
        Math<Y> mathY = matrix.axisY.math;
        Y minY = sectionY.getCount();
        Y maxY = mathY.create(-1).getValue();
        for (SectionCore<X> sectionX: stateX.axisLayout.sections) {
          ZoneCore<X, Y> zone = matrix.layout.getZone(sectionX, sectionY);
          if (zone.editor == null || zone.cellSelection.isEmpty()) continue;
          CellExtent<X, Y> extent = zone.cellSelection.getExtent();
          Y start = sectionY.hidden.firstExcluded(extent.startY, Matrix.FORWARD);
          Y end = sectionY.hidden.firstExcluded(extent.endY, Matrix.BACKWARD);
          if (mathY.compare(start, minY) < 0) minY = start;
          if (mathY.compare(end, maxY) > 0) maxY = end;
        }

        boolean isNext = false;

        for (MutableNumber<Y> i = sectionY.math.create(minY); sectionY.math.compare(i, maxY) <= 0; i.increment()) {
          Y row = i.getValue();
          int hiddenIndex = sectionY.hidden.getExtentIndex(row);
          if (hiddenIndex != -1) {
            i.set(sectionY.hidden.items.get(hiddenIndex).end);
            continue;
          }
          for (SectionCore<X> sectionX: stateX.axisLayout.sections) {
            ZoneCore<X, Y> zone = matrix.layout.getZone(sectionX, sectionY);
            if (zone.editor == null || zone.cellSelection.isEmpty()) continue;
            zone.editor.cellsPainter = zone.getPainter(Painter.NAME_CELLS);
            int lengthBefore = sb.length();
            zone.editor.copySelectionInRow(sb, row);
            if (isNext && sb.length() > lengthBefore) {
              sb.insert(lengthBefore, "\t");
            }
            isNext = true;
          }
          isNext = false;
          sb.append(ZoneEditor.NEW_LINE);
        }
      }
      if (sb.length() > 0) {
        sb.delete(sb.length() - ZoneEditor.NEW_LINE.length(), sb.length());
      }
      else {
        assert sb.length() == 0;
        // Copy single cell from the focus cell
        AxisItem<X> focusItemX = matrix.getAxisX().getFocusItem();
        AxisItem<Y> focusItemY = matrix.getAxisY().getFocusItem();
        if (focusItemX != null && focusItemY != null) {
          ZoneCore<X, Y> zone = matrix.layout.getZone(focusItemX.section, focusItemY.section);
          if (zone.editor != null) {
            zone.editor.cellsPainter = zone.getPainter(Painter.NAME_CELLS);
            sb.append(zone.editor.format(focusItemX.getIndex(), focusItemY.getIndex()));
          }
        }
      }
      if (sb.length() > 0) {
        Clipboard clipboard = new Clipboard(matrix.getDisplay());
        clipboard.setContents(new Object[] {sb.toString()}, new Transfer[] {TextTransfer.getInstance()});
        clipboard.dispose();
      }
    }
    else {
      if (zone.editor != null) zone.editor.copy();
    }
  }

  private void paste() {
    if (zone.editor != null) zone.editor.paste();
  }


  protected boolean moveCursor(int commandId) {
    stateY.focusMoved = true;
    stateX.focusMoved = true;
    mY = null; mX = null;
    switch (commandId) {

    case CMD_FOCUS_DOWN: 		case CMD_SELECT_DOWN: 	mY = Move.NEXT; break;
    case CMD_FOCUS_UP:   		case CMD_SELECT_UP:			mY = Move.PREVIOUS; break;
    case CMD_FOCUS_RIGHT: 	case CMD_SELECT_RIGHT:	mX = Move.NEXT; break;
    case CMD_FOCUS_LEFT: 		case CMD_SELECT_LEFT:		mX = Move.PREVIOUS; break;

    case CMD_FOCUS_PAGE_DOWN: 	case CMD_SELECT_PAGE_DOWN:	mY = Move.NEXT_PAGE; break;
    case CMD_FOCUS_PAGE_UP:		  case CMD_SELECT_PAGE_UP: 	  mY = Move.PREVIOUS_PAGE; break;
    case CMD_FOCUS_PAGE_RIGHT:	case CMD_SELECT_PAGE_RIGHT:	mX = Move.NEXT_PAGE; break;
    case CMD_FOCUS_PAGE_LEFT:	  case CMD_SELECT_PAGE_LEFT:	mX = Move.PREVIOUS_PAGE; break;

    case CMD_FOCUS_MOST_DOWN:	  case CMD_SELECT_FULL_DOWN:	mY = Move.END; break;
    case CMD_FOCUS_MOST_UP:		  case CMD_SELECT_FULL_UP:	  mY = Move.HOME; break;
    case CMD_FOCUS_MOST_RIGHT:	case CMD_SELECT_FULL_RIGHT:	mX = Move.END; break;
    case CMD_FOCUS_MOST_LEFT:	  case CMD_SELECT_FULL_LEFT:	mX = Move.HOME; break;

    case CMD_FOCUS_MOST_UP_LEFT:	  case CMD_SELECT_FULL_UP_LEFT:	   mX = Move.HOME;
    mY = Move.HOME; break;

    case CMD_FOCUS_MOST_DOWN_RIGHT:	case CMD_SELECT_FULL_DOWN_RIGHT: mX = Move.END;
    mY = Move.END; break;

    case CMD_FOCUS_LOCATION: 		  case CMD_FOCUS_LOCATION_ALTER:
    case CMD_SELECT_TO_LOCATION:	case CMD_SELECT_TO_LOCATION_ALTER:
    case CMD_SELECT_TO_COLUMN: 		case CMD_SELECT_TO_COLUMN_ALTER:
    case CMD_SELECT_COLUMN:			  case CMD_SELECT_COLUMN_ALTER:
    case CMD_SELECT_TO_ROW: 		  case CMD_SELECT_TO_ROW_ALTER:
    case CMD_SELECT_ROW:			    case CMD_SELECT_ROW_ALTER:
      //		  AxisItem<X> focusX = stateX.layout.current;
      //		  if (!stateX.setFocusItem()) return false;
      //		  if (!stateY.setFocusItem()) {
      //		    stateX.layout.setFocusItem(focusX);
      //		    return false;
      //		  }
      //			return true;
      stateX.setFocusItem();
      stateY.setFocusItem();
    }

    // Handle merging
    AxisItem<X> currentX = stateX.axisLayout.current;
    AxisItem<Y> currentY = stateY.axisLayout.current;
    X startX = null, countX = null;
    Y startY = null, countY = null;
    if (currentX != null && currentY != null) {
      span = matrix.layout.getZone(currentX.section, currentY.section).
          cellMerging.getSpan(currentX.index, currentY.index);
      if (span != null) {
        startX = span.startX; countX = span.endX;
        startY = span.startY; countY = span.endY;
      }

      if (mY != null) stateY.moveFocusItem(mY, startY, countY);
      if (mX != null) stateX.moveFocusItem(mX, startX, countX);
    }

    // Set current to the start of the merged cell
    if (currentX != null && currentY != null) {
      currentX = stateX.axisLayout.current;
      currentY = stateY.axisLayout.current;
      span = matrix.layout.getZone(currentX.section, currentY.section).
          cellMerging.getSpan(currentX.index, currentY.index);

      if (span != null) {
        stateX.axisLayout.setFocusItem(AxisItem.createInternal(currentX.section, span.startX));
        stateY.axisLayout.setFocusItem(AxisItem.createInternal(currentY.section, span.startY));
      }
    }

    return stateY.focusMoved && stateX.focusMoved;
  }


  private void backupRestoreSelection(int commandId) {
    if (commandId == CMD_FOCUS_LOCATION_ALTER ||
        commandId == CMD_SELECT_COLUMN_ALTER || commandId == CMD_SELECT_ROW_ALTER)
    {
      // Backup the zones cell selection
      for (ZoneCore<? extends Number, ? extends Number> zone: matrix.layout.zones) {
        if (zone.isSelectionEnabled()) {
          zone.backupSelection();
        }
      }
    }
    else if (commandId == CMD_SELECT_TO_LOCATION_ALTER ||
        commandId == CMD_SELECT_TO_COLUMN_ALTER || commandId == CMD_SELECT_TO_ROW_ALTER)
    {
      for (ZoneCore<X, Y> zone: matrix.layout.zones) {
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

    boolean skipHidden = matrix.getSelectSkipHidden();
    boolean ctrlSelection = commandId == CMD_FOCUS_LOCATION_ALTER ||
        commandId == CMD_SELECT_TO_LOCATION_ALTER;

    if (ctrlSelection && isSelected(stateX.last, stateY.last)) {
      matrix.layout.setSelectedFromUI(stateX.last, stateX.item, stateY.last, stateY.item, false, skipHidden);
    }
    else {
      if (ctrlSelection) {
        if (stateY.lastFocus != null && stateX.lastFocus != null) {
          ZoneCore<X, Y> zone = matrix.layout.getZone(
              stateX.lastFocus.section, stateY.lastFocus.section);
          if (BigInteger.ZERO.equals(zone.getSelectedCount())) {
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
        matrix.layout.setSelected(false, notify);
      }
      if (commandId > CMD_FOCUS_LOCATION) {
        matrix.layout.setSelectedFromUI(stateX.last, stateX.item, stateY.last, stateY.item, true, skipHidden);
      }
    }
  }

  private boolean isSelected(AxisItem<X> lastX, AxisItem<Y> lastY) {
    return matrix.layout.getZone(lastX.section, lastY.section).
        isSelected(lastX.getIndex(), lastY.getIndex());
  }


  private void sendEvents() {
    stateY.sendEvents();
    stateX.sendEvents();
    for (ZoneCore<X, Y> zone: matrix.layout.zones) {
      zone.sendEvents();
    }
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
    Rectangle bounds;
    protected int x2, y2;
    private boolean highlight;
    private boolean advanced;
    //	  protected int feedback;

    public DragItemPainter(String name) {
      super(name);
      setEnabled(false);
      matrix = MatrixListener.this.matrix;
    }

    @Override protected boolean init() {
      painter = header.getPainter(Painter.NAME_CELLS);
      if (painter != null) {
        painter.init(gc, frozenX, frozenY);
        highlight = painter.selectionHighlight;
        painter.selectionHighlight = false;
      }
      return true;
    }

    @Override public void clean() {
      super.clean();
      if (painter != null) {
        painter.clean();
        painter.selectionHighlight = highlight;
      }
      gc.setLineWidth(1);
      gc.setAlpha(255);
      gc.setAdvanced(advanced);
    }

    @Override public void paint(int x, int y, int width, int height) {
      if (!instantMoving) {
        gc.setForeground(matrix.getDisplay().getSystemColor(SWT.COLOR_BLACK));
        advanced = gc.getAdvanced();
        gc.setAdvanced(true);
        gc.setAlpha(128);
      }

      if ((stateX.moving || stateY.moving) && bounds != null) {
        // Background
        Painter<X, Y> cellPainter = header.getPainter(Painter.NAME_CELLS);
        if (cellPainter != null && cellPainter.style.background != null) {
          gc.setBackground(cellPainter.style.background);
          gc.fillRectangle(x2, y2, bounds.width, bounds.height);
        }

        // Text
        if (painter == null) {
          gc.drawText((stateY.moving ? stateY : stateX).last.getIndex().toString(), x2 + 1, y2 + 1);
        }
        else if (bounds != null) {
          painter.setup(stateX.last.getIndex(), stateY.last.getIndex());
          painter.paint(x2, y2, bounds.width, bounds.height);
        }

        // Border
        Painter<X, Y> linePainter = header.getPainter(Painter.NAME_LINES_X);
        if (linePainter == null) {
          linePainter = header.getPainter(Painter.NAME_LINES_Y);
        }
        Color color = linePainter == null
            ? matrix.getDisplay().getSystemColor(SWT.COLOR_WIDGET_DARK_SHADOW)
                : linePainter.style.background;
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
      header = (ZoneCore<X, Y>) MatrixListener.this.matrix.getHeaderX().getUnchecked();
    }

    @Override public void paint(int x, int y, int width, int height) {
      if (mouseMoveEvent == null ) return;
      // imageOffset can be null when dragging from outside of the widget
      x2 = mouseMoveEvent.x - (imageOffset == null ? 0 : imageOffset.x);
      y2 = 1;
      if (stateX.last != null) {
        bounds = header.getCellBounds(stateX.last.getIndex(), stateY.axis.math.ZERO_VALUE());
      }
      if (!instantMoving) {
        Rectangle area = matrix.getClientArea();
        AxisItem<X> item = stateX.item;
        if (item == null) return;
        int x3 = 0;
        Object d = getData();
        if (d instanceof DropTargetEvent) {
          DropTargetEvent event = (DropTargetEvent) d;
          x3 = matrix.toControl(event.x, event.y).x;
          item = matrix.axisX.getItemByViewportDistance(x3);
        }
        else {
          x3 = mouseMoveEvent.x;
        }

        int distance = stateX.getDropIndicatorDistance(item, x3);
        if (distance >= 0) {
          gc.setLineWidth(2);
          gc.drawLine(distance, area.y, distance, area.height);
        }
      }
      super.paint(x, y, width, height);
    }
  }

  class DragItemPainterY extends DragItemPainter{
    public DragItemPainterY() {
      super(Painter.NAME_DRAG_ITEM_Y);
      header = (ZoneCore<X, Y>) MatrixListener.this.matrix.getHeaderY().getUnchecked();
    }

    @Override public void paint(int x, int y, int width, int height) {
      if (mouseMoveEvent == null) return;
      x2 = 1;
      y2 = mouseMoveEvent.y - (imageOffset == null ? 0 : imageOffset.y);
      if (stateY.last != null) {
        bounds = header.getCellBounds(stateX.axis.math.ZERO_VALUE(), stateY.last.getIndex());
      }
      if (!instantMoving) {
        Rectangle area = matrix.getClientArea();
        AxisItem<Y> item = stateY.item;
        if (item == null) return;
        int y3 = 0;
        Object d = getData();
        if (d instanceof DropTargetEvent) {
          DropTargetEvent event = (DropTargetEvent) d;
          y3 = matrix.toControl(event.x, event.y).y;
          item = stateY.axisLayout.getItemByDistanceNotNullIfNotEmpty(y3);
        }
        else {
          y3 = mouseMoveEvent.y;
        }
        //          feedback = DND.FEEDBACK_INSERT_BEFORE;
        int distance = stateY.getDropIndicatorDistance(item, y3);
        if (distance >= 0) {
          gc.setLineWidth(2);
          gc.drawLine(area.x, distance, area.width, distance);
        }
      }
      super.paint(x, y, width, height);
    }
  }

  @SuppressWarnings("rawtypes")
  public AxisListener getAxisState(char symbol) {
    return symbol == 'X' ? stateX : stateY;
  }
}

