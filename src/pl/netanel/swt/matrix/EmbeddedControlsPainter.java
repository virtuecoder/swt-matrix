/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import java.util.HashMap;
import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import pl.netanel.swt.matrix.ZoneEditor.ZoneEditorData;

/**
 * Creates embedded controls rather then drawing directly if the layout has been
 * modified.
 */
class EmbeddedControlsPainter<X extends Number, Y extends Number> extends Painter<X, Y> {
  private final ZoneEditor<X, Y> editor;
  HashMap<Number, HashMap<Number, Control>> controls;
  boolean needsPainting;
  private final Listener focusInListener;
  private final ControlListener controlListener;
  private Runnable layoutCallback;
  private boolean hadFocus;

  public EmbeddedControlsPainter(final ZoneEditor<X, Y> editor) {
    super(Painter.NAME_EMBEDDED_CONTROLS);
    this.editor = editor;
    controls = new HashMap<Number, HashMap<Number, Control>>();

    // Set the focus cell in the matrix when the cell control gets focus
    focusInListener = new Listener() {
      @Override
      public void handleEvent(Event e) {
        Matrix<X, Y> matrix = getMatrix();
        ZoneEditorData<X, Y> data = editor.getData(e.widget);
        matrix.layout.setSelected(false, true);
        matrix.axisY.setFocusItem(AxisItem.createInternal(editor.zone.sectionY, data.indexY));
        matrix.axisX.setFocusItem(AxisItem.createInternal(editor.zone.sectionX, data.indexX));
        matrix.redraw();
      }
    };

    // Set the repainting flag when the axis item gets resized or moved
    controlListener = new ControlListener() {
      @Override
      public void controlMoved(ControlEvent e) {
        needsPainting = true;
      }

      @Override
      public void controlResized(ControlEvent e) {
        needsPainting = true;
      }
    };
    editor.zone.getSectionY().addControlListener(controlListener);
    editor.zone.getSectionX().addControlListener(controlListener);
  }

  @Override
  protected boolean init() {
    if (matrix.isDisposed()) return false;
    if (!needsPainting) {
      return false;
    }
    hadFocus = matrix.getDisplay().getFocusControl() == matrix;
    clearControls();
    if (zone.editor != null) zone.editor.cellsPainter = zone.getPainter(Painter.NAME_CELLS);
    return true;
  }

  @Override
  public void dispose() {
    super.dispose();
    editor.zone.getSectionY().removeControlListener(controlListener);
    editor.zone.getSectionX().removeControlListener(controlListener);
    matrix.layoutX.callbacks.remove(layoutCallback);
    matrix.layoutY.callbacks.remove(layoutCallback);
  }

  void clearControls() {
    for (Entry<Number, HashMap<Number, Control>> entry : controls.entrySet()) {
      for (Control control : entry.getValue().values()) {
        editor.removeControl(control);
      }
    }
    controls.clear();
  }

  @Override
  public void setup(X indexX, Y indexY) {
    if (editor.hasEmbeddedControl(indexX, indexY)) {
      Control control = editor.addControl(indexX, indexY);
      if (control != null && !control.isDisposed()) {
        control.addListener(SWT.FocusIn, focusInListener);
        control.addListener(SWT.Selection, new Listener() {
          @Override
          public void handleEvent(Event event) {
            getMatrix().forceFocus();
          }
        });
      }

      // control.addListener(SWT.Selection, listener );
      HashMap<Number, Control> row = controls.get(indexY);
      if (row == null) {
        controls.put(indexY, row = new HashMap<Number, Control>());
      }
      row.put(indexX, control);
    }
  }

  @Override
  protected void paint(int x, int y, int width, int height) {
    // do nothing
  }

  @Override
  public void clean() {
    if (needsPainting) {
      needsPainting = false;
      if (hadFocus) {
        getMatrix().forceFocus();
      }
    }
  }

  public Control getControl(X indexX, Y indexY) {
    HashMap<Number, Control> row = controls.get(indexY);
    if (row == null) {
      return null;
    } else {
      return row.get(indexX);
    }
  }

  @Override
  protected void setMatrix(final Matrix<X, Y> matrix) {
    layoutCallback = new Runnable() {
      @Override
      public void run() {
        needsPainting = true;
      }
    };
    matrix.layoutY.callbacks.add(layoutCallback);
    matrix.layoutX.callbacks.add(layoutCallback);

    super.setMatrix(matrix);
  }

  @Override
  public Point computeSize(X indexX, Y indexY, int wHint, int hHint) {
    Control control = getControl(indexX, indexY);
    if (control == null) {
      return new Point(wHint, hHint);
    }
    else {
      return control.computeSize(wHint, hHint);
    }
  };
}
