/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import static pl.netanel.swt.matrix.Matrix.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;


/**
 * Provides editing support for zone cells.
 * <p>
 * @param <X> indexing type for the horizontal axis
 * @param <Y> indexing type for vertical axis
 */
public class ZoneEditor<X extends Number, Y extends Number> {

  private static final String ZONE_EDITOR_DATA = "swt matrix edited cell";
  private static final String DEFAULT_TRUE_TEXT = "\u2713";
  static final String NEW_LINE = System.getProperty("line.separator");

  final ZoneCore<X, Y> zone;
  final CommandListener controlListener;

  EmbeddedControlsPainter<X, Y> embedded;
  private Painter<X, Y> cellsPainter;
  private GestureBinding mouseDownActivation;
  private GestureBinding mouseDobleClickActivation;
  private ArrayList<EditLogEntry<X, Y>> history;
  private int historyLength;
  private int historyIndex;
  private boolean isBulkEditAtomic;

  /**
   * Default constructor, facilitates editing of the specified zone.
   * <p>
   * If an editor was created before it will be replace with this new one.
   * @param zone
   */
  public ZoneEditor(Zone<X, Y> zone) {
    super();
    this.zone = ZoneCore.from(zone);
    this.zone.setEditor(this);

    history = new ArrayList<EditLogEntry<X, Y>>();
    historyIndex = -1;

    // Painters
    cellsPainter = zone.getPainter(Painter.NAME_CELLS);
    zone.replacePainterPreserveStyle(new Painter<X, Y>(
        Painter.NAME_EMULATED_CONTROLS, Painter.SCOPE_CELLS)
        {
      @Override
      protected boolean init() {
        return true;
      }
      @Override
      public void setup(X indexX, Y indexY) {
        image = null;
        text = null;
        Object[] emul = getCheckboxEmulation(indexX, indexY);
        if (emul == null) return;
        Object value = getModelValue(indexX, indexY);

        if (emul[0] instanceof Image || emul[1] instanceof Image) {
          style.imageAlignX = style.imageAlignY = SWT.CENTER;
          image = (Image) (Boolean.TRUE.equals(value) ? emul[0] : emul[1]);
        } else {
          style.textAlignX = style.textAlignY = SWT.CENTER;
          text = (String) (Boolean.TRUE.equals(value) ? emul[0] : emul[1]);
        }
        super.setup(indexX, indexY);
      };
    });
    zone.replacePainter(embedded = new EmbeddedControlsPainter<X, Y>(this));

    // Command binding
    zone.bind(CMD_CUT, SWT.KeyDown, SWT.MOD1 | 'x');
    zone.bind(CMD_COPY, SWT.KeyDown, SWT.MOD1 | 'c');
    zone.bind(CMD_PASTE, SWT.KeyDown, SWT.MOD1 | 'v');
    zone.bind(CMD_DELETE, SWT.KeyDown, SWT.DEL);
    zone.bind(CMD_UNDO, SWT.KeyDown, SWT.MOD1 | 'z');
    zone.bind(CMD_REDO, SWT.KeyDown, SWT.MOD1 | 'y');
    zone.bind(CMD_EDIT_ACTIVATE, SWT.KeyUp, SWT.F2);
    //    zone.bind(CMD_EDIT_ACTIVATE, SWT.MouseDoubleClick, 1);
    zone.bind(CMD_EDIT_ACTIVATE, SWT.KeyDown, Matrix.PRINTABLE_CHARS);

    mouseDownActivation = new GestureBinding(CMD_EDIT_ACTIVATE, SWT.MouseDown, 1) {

      @Override public boolean isMatching(Event e) {
        if (!super.isMatching(e)) return false;

        // Change boolean value when clicked on the check box image
        Matrix<X, Y> matrix = getMatrix();

        AxisItem<Y> focusItemY = matrix.getAxisY().getMouseItem();
        if (focusItemY == null) return false;
        Y indexY = focusItemY.getIndex();

        AxisItem<X> focusItemX = matrix.getAxisX().getMouseItem();
        if (focusItemX == null) return false;
        X indexX = focusItemX.getIndex();

        Object[] emulation = getCheckboxEmulation(indexX, indexY);

        if (emulation != null &&
            (emulation[0] instanceof Image || emulation[1] instanceof Image)) {
          // Calculate the image bounds
          Rectangle cellBounds = matrix.getBody().getCellBounds(indexX, indexY);
          Rectangle imageBounds = ((Image) emulation[0]).getBounds();
          imageBounds.x = cellBounds.x + (cellBounds.width - imageBounds.width) / 2;
          imageBounds.y = cellBounds.y + (cellBounds.height - imageBounds.height) / 2;
          return imageBounds.contains(e.x, e.y);
        }
        return false;
      }
    };
    this.zone.bind(mouseDownActivation);

    mouseDobleClickActivation = new GestureBinding(CMD_EDIT_ACTIVATE, SWT.MouseDoubleClick, 1) {

      @Override public boolean isMatching(Event e) {
        if (!super.isMatching(e)) return false;

        // Change boolean value when clicked on the check box image
        Matrix<X, Y> matrix = getMatrix();

        AxisItem<Y> focusItemY = matrix.getAxisY().getMouseItem();
        if (focusItemY == null) return false;
        Y indexY = focusItemY.getIndex();

        AxisItem<X> focusItemX = matrix.getAxisX().getMouseItem();
        if (focusItemX == null) return false;
        X indexX = focusItemX.getIndex();

        Object[] emulation = getCheckboxEmulation(indexX, indexY);

        if (emulation != null &&
            (emulation[0] instanceof Image || emulation[1] instanceof Image)) {
          // Calculate the image bounds
          Rectangle cellBounds = matrix.getBody().getCellBounds(indexX, indexY);
          Rectangle imageBounds = ((Image) emulation[0]).getBounds();
          imageBounds.x = cellBounds.x + (cellBounds.width - imageBounds.width) / 2;
          imageBounds.y = cellBounds.y + (cellBounds.height - imageBounds.height) / 2;
          return !imageBounds.contains(e.x, e.y);
        }
        return true;
      }
    };
    this.zone.bind(mouseDobleClickActivation);

    controlListener = new CommandListener() {
      @Override protected void executeCommand(int commandId) {
        Control control = (Control) event.widget;

        switch (commandId) {
        case CMD_EDIT_DEACTIVATE_APPLY: apply(control); break;
        case CMD_EDIT_DEACTIVATE_CANCEL: cancel(control); break;
        }
      };
    };
    controlListener.bind(CMD_EDIT_DEACTIVATE_CANCEL, SWT.KeyDown, SWT.ESC);
    controlListener.bind(CMD_EDIT_DEACTIVATE_APPLY, SWT.KeyDown, SWT.CR);
    controlListener.bind(CMD_EDIT_DEACTIVATE_APPLY, SWT.FocusOut, 0);
    controlListener.bindings.add(new GestureBinding(CMD_EDIT_DEACTIVATE_APPLY, SWT.Selection, 0) {
      @Override public boolean isMatching(Event e) {
        if (!(e.widget instanceof Button)) return false;
        return super.isMatching(e);
      }
    });

  }

  static <X extends Number, Y extends Number> ZoneEditor<X, Y> createCopyOnlyEditor(ZoneCore<X, Y> zone) {
    return new ZoneEditor<X, Y>(zone) {
      @Override
      protected Control createControl(X indexX, Y indexY) {
        return null;
      };
    };
  }

  void dispose() {
    if (zone.getMatrix().isDisposed()) return;
    zone.unbind(CMD_CUT, SWT.KeyDown, SWT.MOD1 | 'x');
    zone.unbind(CMD_COPY, SWT.KeyDown, SWT.MOD1 | 'c');
    zone.unbind(CMD_PASTE, SWT.KeyDown, SWT.MOD1 | 'v');
    zone.unbind(CMD_DELETE, SWT.KeyDown, SWT.DEL);
    zone.unbind(CMD_EDIT_ACTIVATE, SWT.KeyUp, SWT.F2);
    //    zone.bind(CMD_EDIT_ACTIVATE, SWT.MouseDoubleClick, 1);
    zone.unbind(CMD_EDIT_ACTIVATE, SWT.KeyDown, Matrix.PRINTABLE_CHARS);
    zone.unbind(mouseDownActivation);
    zone.unbind(mouseDobleClickActivation);
    embedded.dispose();
  }


  /**
   * Returns value from the model.
   * <p>
   * This method is usually overridden, since the default implementation returns
   * the {@link Painter#text} after calling {@link Painter#setupSpatial(Number, Number)} method of the
   * zone's Painter.NAME_CELLS painter, which is always {@link String}, while some edit
   * controls require other types, like {@link Boolean} or {@link Date}.
   * If the Painter.NAME_CELLS painter does not exist in the zone it always returns null.
   * @param indexX cell index on the horizontal axis
   * @param indexY cell index on the vertical axis
   */
  public Object getModelValue(X indexX, Y indexY) {
    if (cellsPainter == null) return null;
    cellsPainter.setupSpatial(indexX, indexY);
    return cellsPainter.text;
  }

  /**
   * Sets the specified value to the model.
   * It is used to apply the result of cell editing.
   * <p>
   * Performs the input validation and returns <code>false</code> if the value
   * is not valid, and <code>true</code> otherwise
   * @param indexX cell index on the horizontal axis
   * @param indexY cell index on the vertical axis
   * @param value to set in the model
   * @return
   * @return <code>false</code> if the value is not valid, and <code>true</code>
   *         otherwise
   */
  public boolean setModelValue(X indexX, Y indexY, Object value) {
    return true;
  }

  /**
   * Returns a value from the specified edit control.
   * <p>
   * It returns: <ul>
   * <li>{@link String} from {@link Text} and {@link Combo} controls
   * <li>{@link java.util.Date} from a {@link DateTime} control
   * <li>{@link Boolean} from a {@link Button}
   * </ul>
   * @return a value from the specified control
   * @see #setEditorValue(Control, Object)
   */
  protected Object getEditorValue(Control control) {
    if (control == null || control.isDisposed()) return null;
    if (control instanceof Text) {
      return ((Text) control).getText();
    }
    else if (control instanceof Button) {
      return ((Button) control).getSelection();
    }
    else if (control instanceof Combo) {
      return ((Combo) control).getText();
      //      Combo combo = (Combo) control;
      //      int selectionIndex = combo.getSelectionIndex();
      //      return selectionIndex == -1 ? null : combo.getItem(selectionIndex);
    }
    else if (control instanceof DateTime) {
      DateTime date = (DateTime) control;
      Calendar cal = Calendar.getInstance();
      cal.clear();
      cal.set(Calendar.YEAR, date.getYear());
      cal.set(Calendar.MONTH, date.getMonth());
      cal.set(Calendar.DAY_OF_MONTH, date.getDay());
      return cal.getTime();
    }

    return null;
  }

  /**
   * Sets the value in the cell editor control.
   * <p>
   * The value must of the following type: <ul>
   * <li>{@link java.util.Date} for a {@link DateTime} control
   * <li>{@link Boolean} for a {@link Button}
   * <li>any type for {@link Text} and {@link Combo} controls
   * </ul>
   * @param control to set the value for
   * @param value to set in the control
   * @see #getEditorValue(Control)
   */
  protected void setEditorValue(Control control, Object value) {
    if (control == null || control.isDisposed()) return;
    String s = value == null ? "" : value.toString();

    if (control instanceof Text) {
      Text text = (Text) control;
      text.setText(s);
      text.setSelection(s.length());
    }
    else if (control instanceof Combo) {
      Combo combo = (Combo) control;
      int indexOf = combo.indexOf(s);
      if (indexOf == -1) {
        combo.setText(s);
      } else {
        combo.select(indexOf);
      }
    }
    else if (control instanceof Button && (control.getStyle() & SWT.CHECK) != 0) {
      Button button = (Button) control;
      button.setSelection((Boolean) value);
    }
    else if (control instanceof DateTime) {
      //      if (value != null && !(value instanceof Date)) {
      //        try {
      //          value = dateFormat.parse(value.toString());
      //        }
      //        catch (ParseException e) {
      //          throw new RuntimeException(e);
      //        }
      //      }
      Calendar cal = Calendar.getInstance();
      cal.setTime((Date) value);
      DateTime dateTime = (DateTime) control;
      dateTime.setDate(
          cal.get(Calendar.YEAR),
          cal.get(Calendar.MONTH),
          cal.get(Calendar.DAY_OF_MONTH));
    }
  }

  /**
   * Returns editor control associated with the given cell or <code>null</code> if cell
   * does not have editor control created, which may happen when it is not visible or
   * the editor has not been activated.
   *
   * @param indexX
   * @param indexY
   * @return
   */
  public Control getControl(X indexX, Y indexY) {
    for (Control control: zone.getMatrix().getChildren()) {
      ZoneEditorData<X, Y> data = getData(control);
      if (data != null && data.indexX.equals(indexX) && data.indexY.equals(indexY)) {
        return control;
      }
    }
    return null;
  }

  /**
   * Applies the editor value to the model by invoking
   * {@link #setModelValue(Number, Number, Object)} and if successful then calls
   * {@link #cancel(Control)}, otherwise leaves the editor control active.
   *
   * @param control editor control to apply the data from.
   */
  public void apply(Control control) {
    if (control == null) return;
    ZoneEditorData<X, Y> data = getData(control);
    if (data == null) return;
    Object newValue = getEditorValue(control);
    if (setModelValueAndLog(data.indexX, data.indexY, data.value, newValue)) {
      cancel(control);
      getMatrix().redraw();
    }
  }

  private boolean setModelValueAndLog(X indexX, Y indexY, Object oldValue, Object newValue) {
    boolean valid = setModelValue(indexX, indexY, newValue);
    if (valid) {
      logEditHistory(indexX, indexY, oldValue, newValue);
    }
    return valid;
  }

  private void logEditHistory(X indexX, Y indexY, Object oldValue, Object newValue) {
    if (++historyIndex == historyLength) historyIndex = 0;
    history.add(historyIndex, new EditLogEntry<X, Y>(indexX, indexY, oldValue, newValue));
  }

  private void logEditHistory(ArrayList<EditLogEntry<X, Y>> log) {
    if (++historyIndex == historyLength) historyIndex = 0;
    history.add(historyIndex, new EditLogEntry<X, Y>(log));
  }



  private boolean setModelValueAndLog(X indexX, Y indexY, Object newValue, ArrayList<EditLogEntry<X, Y>> log) {
    Object oldValue = getModelValue(indexX, indexY);
    boolean isValid = setModelValue(indexX, indexY, newValue);
    if (log != null && isValid) {
      log.add(new EditLogEntry<X, Y>(indexX, indexY, oldValue, newValue));
    }
    return isValid;
  }

  /**
   * Deactivates the editor control. If the control is not embedded then it
   * calls {@link #removeControl(Control)}. Regardless of that is sets the
   * focus back to the Matrix.
   *
   * @param control
   */
  public void cancel(Control control) {
    ZoneEditorData<X, Y> data = getData(control);
    if (control != null && data != null && data.isEmbedded == false) {
      removeControl(control);
    }
    getMatrix().forceFocus();
  }


  /**
   * Shows a control to edit the value of the specified cell.
   * Before that it scrolls the viewport to make the cell visible.
   * If the cell is hidden then it does nothing.
   *
   * @param indexX cell index on the horizontal axis
   * @param indexY cell index on the vertical axis
   * @return the editor control for the given cell
   */
  public Control activate(X indexX, Y indexY ) {
    Axis<X> axisX = zone.getMatrix().getAxisX();
    Axis<Y> axisY = zone.getMatrix().getAxisY();
    if (axisX.getBody().isHidden(indexX) || axisY.getBody().isHidden(indexY)) return null;

    // Show cell
    axisX.showItem(axisX.getBody(), indexX);
    axisY.showItem(axisY.getBody(), indexY);

    return activate(indexX, indexY, null);
  }
  /**
   * Shows a control to edit the value of the specified cell.
   */
  private Control activate(X indexX, Y indexY, GestureBinding b) {
    cellsPainter = zone.getPainter(Painter.NAME_CELLS);

    //    Control control = embedded.getControl(indexY, indexX);
    Object[] emulation = getCheckboxEmulation(indexX, indexY);
    Control control = embedded.getControl(indexX, indexY);
    if (emulation != null || isCheckbox(control)) {
      boolean value = toggleBooleanValue(indexX, indexY);
      if (control != null) {
        ((Button) control).setSelection(value);
      }
    } else {
      if (control == null) {
        control = addControl(indexX, indexY);
        if (b != null && b.isCharActivated) {
          if (isCheckbox(control)) {
            toggleBooleanValue(indexX, indexY);
          } else {
            setEditorValue(control, b.character);
          }
        }
      }
      if (control != null) {
        control.setFocus();
      }
    }
    return control;
  }

  private boolean toggleBooleanValue(X indexX, Y indexY) {
    boolean value = !Boolean.TRUE.equals(getModelValue(indexX, indexY));
    if (setModelValue(indexX, indexY, value)) {
      logEditHistory(indexX, indexY, !value, value);
      getMatrix().redraw();
    }
    return value;
  }


  private boolean isCheckbox(Control control) {
    return control instanceof Button && (control.getStyle() & SWT.CHECK) != 0;
  }

  Control addControl(X indexX, Y indexY) {
    Control control = createControl(indexX, indexY);
    if (control != null) {
      Object modelValue = getModelValue(indexX, indexY);
      ZoneEditorData<X, Y> data = new ZoneEditorData<X, Y>(indexX, indexY, modelValue,
          hasEmbeddedControl(indexX, indexY));
      control.setData(ZONE_EDITOR_DATA, data);
      setEditorValue(control, modelValue);
      setBounds(indexX, indexY, control);
      control.moveAbove(getMatrix());
      controlListener.attachTo(control);
    }
    return control;
  }

  /**
   * Creates a control to edit the value of the specified cell.
   * <p>
   * It creates a {@link Text} control by default.
   * The method should return null to make the cell read only.
   * <p>
   * The <code>parent</code> argument is always the parent of {@link Matrix} component.
   * It is passed explicitly to discourage creating edit controls
   * as children of {@link Matrix} which would block the matrix
   * from receiving focus and thus any key or mouse events.
   * @param indexX cell index on the horizontal axis
   * @param indexY cell index on the vertical axis
   *
   * @return control to edit the value of the specified cell
   * @see #createControl(Number, Number)
   */
  protected Control createControl(X indexX, Y indexY) {
    return new Text(getMatrix(), SWT.BORDER);
  }

  /**
   * Disposes the edit control. This method can be overridden to implement
   * the control hiding instead of disposing, making also the {@link #createControl(Number, Number)}
   * method to show the control instead of creating a new one in that case.
   *
   * @param control
   * @see #createControl(Number, Number)
   */
  protected void removeControl(Control control) {
    if (control != null) {
      control.dispose();
    }
  }


  /**
   * Sets the bounds of the specified edit control in the specified cell.
   * <p>
   * The cell bounds are offset by 1 pixel and applied with
   * the {@link Control#setBounds(Rectangle)} method, unless the control
   * is a check button whose size is not modified. Then the control
   * location is centralized relating to the cell.
   * @param indexX cell index on the horizontal axis
   * @param indexY cell index on the vertical axis
   * @param control to set the bounds for
   */
  protected void setBounds(X indexX, Y indexY, Control control) {
    Rectangle bounds = zone.getCellBounds(indexX, indexY);
    Painter.offsetRectangle(bounds, 1);

    Point size  = null;
    if (isCheckbox(control)) {
      size = control.computeSize(SWT.DEFAULT, SWT.DEFAULT);
      if (size.y > size.x) size.y = size.x;
      control.setSize(size);
    } else {
      control.setBounds(bounds);
      size = control.getSize();
    }
    control.setLocation(
        bounds.x + Painter.align(SWT.CENTER, 0, size.x, bounds.width),
        bounds.y + Painter.align(SWT.CENTER, 0, size.y, bounds.height));
  }


  /**
   * Returns <code>true</code> if the cell editor control for the specified cell
   * is embedded in the cell, or <code>false</code> if it is a pop-up.
   * <p>
   * By default this function returns <code>false</code>.
   * @param indexX cell index on the horizontal axis
   * @param indexY cell index on the vertical axis
   *
   * @return embedded state of the cell editor
   */
  protected boolean hasEmbeddedControl(X indexX, Y indexY) {
    return false;
  }

  /**
   * Returns a control embedded in the cell with the given coordinates or null
   * if the cell does not have an embedded control or the control is not visible.
   * @param indexX cell index on the horizontal axis
   * @param indexY cell index on the vertical axis
   * @return The control embedded in the cell with the given coordinates
   */
  public Control getEmbeddedControl(X indexX, Y indexY) {
    return embedded.getControl(indexX, indexY);
  }

  /*------------------------------------------------------------------------
   * Commands
   */

  void edit(GestureBinding b) {
    Matrix<X, Y> matrix = getMatrix();
    AxisItem<X> focusItemX;
    AxisItem<Y> focusItemY;
    if (GestureBinding.isMouseEvent(b.eventType)) {
      focusItemX = matrix.layoutX.getItemByDistance(b.event.x);
      focusItemY = matrix.layoutY.getItemByDistance(b.event.y);
    } else {
      focusItemX = matrix.axisX.getFocusItem();
      focusItemY = matrix.axisY.getFocusItem();
    }
    if (focusItemX == null || focusItemY == null) return;

    activate(focusItemX.getIndex(), focusItemY.getIndex(), b);
  }

  /**
   * Sets the <code>null</code> value to the selected cells.
   */
  void delete() {
    Iterator<Cell<X, Y>> it = zone.getSelectedBoundsIterator();
    ArrayList<EditLogEntry<X, Y>> log = null;
    if (isBulkEditAtomic) log = new ArrayList<EditLogEntry<X, Y>>();
    try {
      while (it.hasNext()) {
        Cell<X, Y> next = it.next();
        X indexX = next.getIndexX();
        Y indexY = next.getIndexY();
        if (zone.isSelected(indexX, indexY) ) {
          if (!setModelValueAndLog(indexX, indexY, null, log) && isBulkEditAtomic) {
            undo(log);
            return;
          }
        }
      }
      logEditHistory(log);
      embedded.needsPainting = true;
      getMatrix().redraw();
    }
    catch (Exception e) {
      if (isBulkEditAtomic) {
        undo(log);
      }
      throw new RuntimeException(e);
    }
  }

  /**
   * Copies selected cells to the clipboard.
   * <p>
   * Only rectangular area can be copied.
   */
  public void copy()
  {
    StringBuilder sb = new StringBuilder();
    cellsPainter = zone.getPainter(Painter.NAME_CELLS);

    CellExtent<X, Y> selected = zone.getSelectedExtent();
    if (selected == null) {
      // Copy single cell from the focus cell
      AxisItem<X> focusItemX = getMatrix().getAxisX().getFocusItem();
      AxisItem<Y> focusItemY = getMatrix().getAxisY().getFocusItem();
      if (focusItemX != null && focusItemY != null) {
        sb.append(format(focusItemX.getIndex(), focusItemY.getIndex()));
      }
    }
    else {

      Y maxY = selected.getEndY();
      X maxX = selected.getEndX();

      for (Iterator<Cell<X, Y>> it = zone.getSelectedBoundsIterator(); it.hasNext();) {
        Cell<X, Y> next = it.next();
        X indexX = next.getIndexX();
        Y indexY = next.getIndexY();
        sb.append(zone.isSelected(indexX, indexY) ? format(indexX, indexY) : "");
        if (getMatrix().axisX.math.compare(indexX, maxX) < 0) {
          sb.append("\t");
        }
        else if (getMatrix().axisY.math.compare(indexY, maxY) < 0) {
          sb.append(NEW_LINE);
        }
      }
    }
    if (sb.length() > 0) {
      Clipboard clipboard = new Clipboard(getMatrix().getDisplay());
      clipboard.setContents(new Object[] {sb.toString()},
          new Transfer[] {TextTransfer.getInstance()});
      clipboard.dispose();
    }
  }

  void copySelectionInRow(StringBuilder sb, Y row) {
    CellExtent<X, Y> extent = zone.getSelectedExtent();
    if (extent == null) return;

    X maxX = extent.getEndX();

    CellSet<X, Y> cellSet = zone.cellSelection;
    if (getMatrix().getCopyPasteHiddenCells() == false) {
      cellSet = zone.cellSelection.copy();
      for (MutableExtent<X> extentX: zone.sectionX.hidden.items) {
        cellSet.remove(extentX.start(), extentX.end(),
            zone.sectionY.math.ZERO_VALUE(), zone.sectionY.math.decrement(zone.sectionY.getCount()));
      }
      for (MutableExtent<Y> extentY: zone.sectionY.hidden.items) {
        cellSet.remove(zone.sectionX.math.ZERO_VALUE(), zone.sectionX.math.decrement(zone.sectionX.getCount()),
            extentY.start(), extentY.end());
      }
    }
    NumberPairSequence<X, Y> seq = new NumberPairSequence<X, Y>(
        new ExtentPairScopeSequence<X, Y>(cellSet).scope(null, null, row, row));
    for (seq.init(); seq.next();) {
      X indexX = seq.getX();
      Y indexY = seq.getY();
      sb.append(zone.isSelected(indexX, indexY) ? format(indexX, indexY) : "");
      if (getMatrix().axisX.math.compare(indexX, maxX) < 0) {
        sb.append("\t");
      }
    }
  }


  /**
   * Pastes from the clipboard to the zone starting from the focus cell.
   * <p>
   * The items in the clipboard exceeding the section item count will be ignored.
   */
  public void paste()
  {
    Clipboard clipboard = new Clipboard(getMatrix().getDisplay());
    Object contents = clipboard.getContents(TextTransfer.getInstance());
    clipboard.dispose();

    if (contents == null) return;

    Axis<X> axisX = getMatrix().getAxisX();
    Axis<Y> axisY = getMatrix().getAxisY();

    // Get the focus cell to start the pasting from
    AxisItem<X> focusItemX = axisX.getFocusItem();
    AxisItem<Y> focusItemY = axisY.getFocusItem();
    if (focusItemX == null || focusItemY == null) return;
    Math<Y> mathY = axisY.math;
    Math<X> mathX = axisX.math;
    X startX = focusItemX.getIndex(), indexX = startX;
    Y startY = focusItemY.getIndex(), indexY = startY;
    X countX = axisX.getBody().getCount();
    Y countY = axisY.getBody().getCount();

    ArrayList<EditLogEntry<X, Y>> log = null;
    if (isBulkEditAtomic) log = new ArrayList<EditLogEntry<X, Y>>();
    try {
      String[] rows = contents.toString().split(NEW_LINE);
      for (int i = 0; i < rows.length && mathY.compare(indexY, countY) < 0; i++) {
        String[] cells = split(rows[i], "\t");
        indexX = startX;
        for (int j = 0; j < cells.length && mathX.compare(indexX, countX) < 0; j++) {
          Object value = parse(indexX, indexY, cells[j]);
          if (value != null) {
            if (!setModelValueAndLog(indexX, indexY, value, log)) {
              undo(log);
              return;
            }
          }
          indexX = zone.sectionX.nextNotHiddenIndex(mathX.increment(indexX), Matrix.FORWARD);
          if (indexX == null) break;
        }
        indexY = zone.sectionY.nextNotHiddenIndex(mathY.increment(indexY), Matrix.FORWARD);
        if (indexY == null) break;
      }
      logEditHistory(log);

      embedded.needsPainting = true;
      getMatrix().redraw();
    }
    catch (Exception e) {
      if (isBulkEditAtomic) {
        undo(log);
      }
      throw new RuntimeException(e);
    }
  }

  /**
   * Cuts the selected cells by copying them to the clipboard
   * and setting <code>null</code> value to them.
   * <p>
   * Only rectangular area can be copied.
   *
   * @see #copy()
   */
  public void cut() {
    copy();
    delete();
  }

  /**
   * Returns the label for the specified cell to be included
   * in the clipboard copying. By default it returns a value from
   * the {@link Painter#text} field after calling {@link Painter#setupSpatial(Number, Number)}.
   *
   * @param indexX cell index on the horizontal axis
   * @param indexY cell index on the vertical axis
   *
   * @return the label for the specified cell to be included
   * in the clipboard copying
   */
  protected String format(X indexX, Y indexY) {
    if (cellsPainter == null) return null;
    cellsPainter.setupSpatial(indexX, indexY);
    return cellsPainter.text == null ? "" : cellsPainter.text;
    //    Object value = getModelValue(indexY, indexX);
    //    return value == null ? "" : value.toString();
  }

  /**
   * Parses a text for the specified cell during paste operation.
   * To parse a value from editor control use {@link #getEditorValue(Control)}.
   * <p>
   * By default it returns the <code>s</code> argument.
   * @param indexX cell index on the horizontal axis
   * @param indexY cell index on the vertical axis
   * @param s text to parse from
   *
   * @return parsed text of specified cell during paste operation
   */
  protected Object parse(X indexX, Y indexY, String s) {
    return s;
  }

  /**
   * If set to <code>true</code> and #setModelValue for any cell returns
   * <code>false</code> then all the previous changes during bulk edit will be
   * rolled back. Bulk edit operations are paste, cut, delete of selection or
   * regular bulk edit to apply a value from a control to a set of cells.
   *
   * @param state new bulk edit atomicity state to set
   */
  public void setBulkEditAtomic(boolean state) {
    isBulkEditAtomic = state;
  }

  /**
   * Returns <code>true</code> if bulk editor operation should be atomic and
   * all failing if one cell change fails. Otherwise the bulk edit should continue
   * in chance of single cell not successful to chance the value in the model.
   * @return
   */
  public boolean isBulkEditAtomic() {
    return isBulkEditAtomic;
  }

  /**
   * Sets the length of the history of modifications to the model done with this
   * editor.
   * <p>
   * Default is 0.
   *
   * @param length
   */
  public void setEditHistoryLength(int length) {
    historyLength = length;
  }

  /**
   * Returns the length of the history of modifications to the model done with
   * this editor.
   *
   * @return the length of the history of modifications to the model done with
   * this editor
   */
  public int getEditHistoryLength() {
    return historyLength;
  }

  /**
   * Undo the latest model change operation.
   */
  public void undo() {
    if (historyIndex >= 0) {
      EditLogEntry<X, Y> entry = history.get(historyIndex--);
      if (entry.newValue instanceof ArrayList) {
        @SuppressWarnings("unchecked")
        ArrayList<EditLogEntry<X, Y>> list = (ArrayList<EditLogEntry<X, Y>>) entry.newValue;
        undo(list);
      }
      else {
        setModelValue(entry.indexX, entry.indexY, entry.oldValue);
      }
      embedded.needsPainting = true;
      getMatrix().redraw();
    }
  }

  private void undo(ArrayList<EditLogEntry<X, Y>> list) {
    for (EditLogEntry<X, Y> entry2: list) {
      setModelValue(entry2.indexX, entry2.indexY, entry2.oldValue);
    }
  }

  /**
   * Redo the latest model change operation that was undone.
   */
  public void redo() {
    if (historyIndex < history.size() - 1) {
      EditLogEntry<X, Y> entry = history.get(++historyIndex);
      if (entry.newValue instanceof ArrayList) {
        @SuppressWarnings("unchecked")
        ArrayList<EditLogEntry<X, Y>> list = (ArrayList<EditLogEntry<X, Y>>) entry.newValue;
        for (EditLogEntry<X, Y> entry2: list) {
          setModelValue(entry2.indexX, entry2.indexY, entry2.newValue);
        }
      }
      else {
        setModelValue(entry.indexX, entry.indexY, entry.newValue);
      }
      embedded.needsPainting = true;
      getMatrix().redraw();
    }
  }

  /**
   * Overcomes the limitation of the String split method needed to parse
   * the pasted text.
   *
   * @param s String to parse
   * @param separator
   * @return
   */
  private String[] split(String s, String separator) {
    ArrayList<String> list = new ArrayList<String>();
    int pos1 = 0, pos2 = 0;
    while (true) {
      pos1 = pos2;
      pos2 = s.indexOf("\t", pos2);
      if (pos2 == -1) {
        list.add(pos1 == s.length() ? null : s.substring(pos1, s.length()));
        break;
      }
      if (pos1 == pos2) {
        list.add(null);
      } else {
        list.add(s.substring(pos1, pos2));
      }
      pos2++;
    }
    return list.toArray(new String[] {});
  }



  Matrix<X, Y> getMatrix() {
    return zone.getMatrix();
  }



  /*------------------------------------------------------------------------
   * Emulation
   */

  /**
   * Returns the check box emulation data.
   * It is an array with either two {@link String} elements
   * for a true value label and false value label,
   * or two {@link Image} elements for a true value image and a false value image.
   * @param indexX cell index on the horizontal axis
   * @param indexY cell index on the vertical axis
   *
   * @return the check box emulation data.
   * @see #getDefaultCheckboxLabels()
   */
  protected Object[] getCheckboxEmulation(X indexX, Y indexY) {
    return null;
  }

  //  /**
  //   * Returns the default images to emulate check boxes.
  //   * The first image in the returned array is for the <code>true</code> value,
  //   * the second one is for the <code>false</code> value.
  //   * <p>
  //   * The default check box images can be configured with
  //   * the {@link #setImagePath(String)} method.
  //   *
  //   * @return default images to emulate check boxes.
  //   * @see #getCheckboxEmulation(Number, Number)
  //   * @see #setImagePath(String)
  //   */
  //  protected final Object[] getDefaultCheckBoxImages() {
  //    if (trueImage == null && falseImage == null) {
  //      return null;
  //    }
  //    else {
  //      return new Object[] {trueImage, falseImage};
  //    }
  //  }

  /**
   * Returns the default labels to emulate check boxes.
   * The first label in the returned array is &#x2713; for the
   * <code>true</code> value, the second one is <code>null</code>
   * for the <code>false</code> value.
   *
   * @return default labels to emulate check boxes.
   * @see #getCheckboxEmulation(Number, Number)
   */
  protected final Object[] getDefaultCheckboxLabels() {
    return new Object[] {DEFAULT_TRUE_TEXT, null};
  }

  //  /**
  //   * Snaps the image of a selected and unselected check box control
  //   * to be used for a check box emulation. The images are placed
  //   * in the specified directory or if the argument is null then in the
  //   * system default location for application files.
  //   * <p>
  //   * Note: it opens a temporary shell in order to snap the images and then closes it immediately.
  //   * @param imagePath
  //   */
  //  void snapControlImages(String imagePath) {
  //    if (imagePath == null) {
  //      systemThemePath = OsUtil.getUserDirectory("SWT Matrix").getAbsolutePath();
  //      if (!new File(systemThemePath).mkdirs()) {
  //        return;
  //      }
  //    }
  //    else {
  //      setImagePath(imagePath);
  //    }
  //
  //    File file = new File(systemThemePath);
  //    Preconditions.checkArgument(file.exists(), "Directory {0} does not exist.",
  //      file.getAbsoluteFile());
  //    Preconditions.checkArgument(file.isDirectory(),
  //      "Path {0} is not e directory.", file.getAbsoluteFile());
  //
  //    Shell shell = new Shell();
  //    RowLayout layout = new RowLayout();
  //    layout.spacing = layout.marginBottom = layout.marginTop = 0;
  //    layout.marginLeft = layout.marginRight = 0;
  //    shell.setLayout(layout);
  //    shell.setSize(100, 100);
  //    shell.open();
  //
  //    Display display = shell.getDisplay();
  //    ImageLoader loader = new ImageLoader();
  //
  //    // Check boxes
  //    Button button = new Button(shell, SWT.CHECK);
  //    shell.layout();
  //    shell.update();
  //    Point size = button.getSize();
  //    GC gc = new GC(button);
  //    Image image = new Image(display, size.x, size.y);
  //    gc.copyArea(image, 0, 0);
  //    loader.data = new ImageData[] { image.getImageData() };
  //    loader.save(new File(imagePath, "unchecked.png").getAbsolutePath(), SWT.IMAGE_PNG);
  //    gc.dispose();
  //    image.dispose();
  //    button.dispose();
  //
  //    button = new Button(shell, SWT.CHECK);
  //    button.setSelection(true);
  //    shell.layout();
  //    shell.update();
  //    gc = new GC(button);
  //    image = new Image(display, size.x, size.y);
  //    gc.copyArea(image, 0, 0);
  //    loader.data = new ImageData[] { image.getImageData() };
  //    loader.save(new File(imagePath, "checked.png").getAbsolutePath(),
  //      SWT.IMAGE_PNG);
  //    gc.dispose();
  //    image.dispose();
  //    button.dispose();
  //
  //    shell.dispose();
  //  }
  //
  //  /**
  //   * Sets the path to the folder containing images emulating the system theme.
  //   * @param path to the folder containing system theme emulation images
  //   */
  //  public void setImagePath(String path) {
  //    systemThemePath = path == null
  //      ? "" : path + System.getProperty("file.separator");
  //    File file = new File(systemThemePath);
  //    if (path != null) {
  //      Preconditions.checkArgument(file.exists(), " does not the exist", systemThemePath);
  //      Preconditions.checkArgument(file.isDirectory(), " is not a directory", systemThemePath);
  //    }
  //
  //    // Read check box images
  //    Display display = getMatrix().getDisplay();
  //    FileInputStream stream;
  //    try {
  //      stream = new FileInputStream(systemThemePath + "checked.png");
  //      trueImage = new Image(display, new ImageData(stream));
  //      stream.close();
  //
  //      stream = new FileInputStream(systemThemePath + "unchecked.png");
  //      falseImage = new Image(display, new ImageData(stream));
  //      stream.close();
  //    }
  //    catch (FileNotFoundException e) {
  //      throw new RuntimeException(e);
  //    }
  //    catch (IOException e) {
  //      throw new RuntimeException(e);
  //    }
  //  }
  //
  //  /**
  //   * Returns the path to the folder containing system theme emulation images.
  //   * @return the path to the folder containing system theme emulation images
  //   * @see #snapControlImages(String)
  //   */
  //  public String getImagePath() {
  //    return systemThemePath;
  //  }

  /**
   * Makes the embedded controls to be recreated.
   */
  public void redraw() {
    embedded.needsPainting = true;
    getMatrix().redraw();
  }

  static class ZoneEditorData<X2 extends Number, Y2 extends Number> {
    public X2 indexX;
    public Y2 indexY;
    public boolean isEmbedded;
    private Object value;
    public ZoneEditorData(X2 indexX2, Y2 indexY2, Object value, boolean embedded) {
      indexY = indexY2;
      indexX = indexX2;
      this.value = value;
      isEmbedded = embedded;
    }
  }

  @SuppressWarnings("unchecked")
  ZoneEditorData<X, Y> getData(Widget widget) {
    return (ZoneEditorData<X, Y>) widget.getData(ZONE_EDITOR_DATA);
  }


  static class EditLogEntry<X extends Number, Y extends Number> {
    X indexX;
    Y indexY;
    Object oldValue, newValue;
    public EditLogEntry(X indexX, Y indexY, Object oldValue, Object newValue) {
      super();
      this.indexX = indexX;
      this.indexY = indexY;
      this.oldValue = oldValue;
      this.newValue = newValue;
    }
    public EditLogEntry(ArrayList<EditLogEntry<X, Y>> log) {
      newValue = log;
    }
  }
}
