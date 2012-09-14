/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.AxisItem;
import pl.netanel.swt.matrix.AxisLayoutSequence;
import pl.netanel.swt.matrix.Cell;
import pl.netanel.swt.matrix.Frozen;
import pl.netanel.swt.matrix.Math;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.MutableNumber;
import pl.netanel.swt.matrix.NumberSet;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.SectionCore;
import pl.netanel.swt.matrix.ZoneCore;

public class ColumnGrouping<X extends Number, Y extends Number> {

  Matrix<X, Y> matrix;
  private SectionCore<X> bodyX;
  private ZoneCore<X, Y> headerX;
  private SectionCore<Y> headerY;
  private Math<X> mathX;
  private Math<Y> mathY;

  private final NumberSet<X> toggle;
//  private final NumberSet<X> summary;
  private Painter<X, Y> painter;

  public ColumnGrouping(final Matrix<X, Y> matrix) {
    super();
    this.matrix = matrix;
    bodyX = matrix.axisX.layout.body;
    headerY = matrix.axisY.layout.header;
    headerX = matrix.layout.getZone(bodyX, headerY);
    mathX = matrix.axisX.math;
    mathY = matrix.axisY.math;

    toggle = new NumberSet<X>(mathX);
//    summary = new NumberSet<X>(mathX);

    matrix.addPainter(0, new Painter<X, Y>("merge header X") {
      @Override
      protected boolean init() {
        merge();
        return super.init();
      }
      @Override
      protected void paint(int x, int y, int width, int height) {};
    });

    painter = new Painter<X, Y>(Painter.NAME_CELLS) {
      final Painter<X, Y> painter = headerX.getPainter(Painter.NAME_CELLS);
      {
        style.imageMarginX = style.imageMarginY = 4;
      }
      private boolean hasToggle;
      {
        Display display = ColumnGrouping.this.matrix.getDisplay();
        final Image image1 = new Image(display, 9, 9);
        GC gc = new GC(image1);
        gc.setAntialias(SWT.ON);
        gc.setForeground(display.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
        gc.drawRectangle(0, 0, 8, 8);
        gc.setForeground(display.getSystemColor(SWT.COLOR_WIDGET_FOREGROUND));
        // Draw <<
        gc.drawLine(2, 4, 4, 2);
        gc.drawLine(4, 4, 6, 2);
        gc.drawLine(2, 4, 4, 6);
        gc.drawLine(4, 4, 6, 6);
        gc.dispose();

        final Image image2 = new Image(display, 9, 9);
        gc = new GC(image2);
        gc.setAntialias(SWT.ON);
        gc.setForeground(display.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
        gc.drawRectangle(0, 0, 8, 8);
        gc.setForeground(display.getSystemColor(SWT.COLOR_WIDGET_FOREGROUND));
        // Draw >>
        gc.drawLine(2, 2, 4, 4);
        gc.drawLine(4, 2, 6, 4);
        gc.drawLine(2, 6, 4, 4);
        gc.drawLine(4, 6, 6, 4);
        gc.dispose();

        setNodeImages(image1, image2);
      }

      @Override
      protected boolean init() {
        merge();
        return painter.init(gc);
      }

      @Override
      public void setupSpatial(X indexX, Y indexY) {
        painter.setupSpatial(indexX, indexY);
        hasChildren = bodyX.hasChildren(indexX);
        hasToggle = toggle.contains(indexX) && bodyX.getLevelInTree(indexX).equals(indexY);
        expanded = hasChildren ? getZone().getSectionX().isExpanded(indexX) : false;
        text = painter.text;
      }

      @Override
      protected void paint(int x, int y, int width, int height) {
        painter.paint(x, y, width, height);

        if (hasToggle) {
          gc.drawImage(expanded ? collapsedImage : expandedImage,
              x + width - style.imageMarginX - nodeImageSize.x,
              y + style.imageMarginY );

        }
      }
    };
    headerX.replacePainter(painter);

    headerX.addListener(SWT.MouseDown, new Listener() {
      private X indexX;
      private Y indexY;

      @Override
      public void handleEvent(Event e) {
        AxisItem<X> itemX = matrix.getAxisX().getMouseOverItem();
        AxisItem<Y> itemY = matrix.getAxisY().getMouseOverItem();
        if (itemX == null || itemY == null) return;
        Cell<X, Y> cell = headerX.getMergeOrigin(itemX.index, itemY.index);
        indexX = cell.indexX;
        indexY = cell.indexY;
        if (isOverToggleImage(e.x, e.y)) {
          bodyX.setExpanded(indexX, !bodyX.isExpanded(indexX));
//          Y level = mathY.create(bodyX.getLevelInTree(indexX).intValue()).getValue();
//          MutableNumber<X> count = mathX.create(1);
//          countCells(bodyX, indexX, count);
//          headerX.setMerged(indexX, count.getValue(), level, mathY.ONE_VALUE(), true);
          matrix.refresh();
        }
      }

      public boolean isOverToggleImage(int x, int y) {
        if (!(toggle.contains(indexX) && bodyX.getLevelInTree(indexX).equals(indexY)))
          return false;

        Rectangle bounds = headerX.getCellBounds(indexX, indexY);
        int x0 = bounds.x + bounds.width - painter.style.imageMarginX - painter.nodeImageSize.x;
        int y0 = bounds.y + painter.style.imageMarginY;

        return x0 <= x && x <= x0 + painter.nodeImageSize.x &&
            y0 <= y && y < y0 + painter.nodeImageSize.y;
      }
    });
  }

  private void merge() {
    Y levelCount = headerY.getCount();
    AxisLayoutSequence<X> seq = matrix.axisX.layout.cellSequence(Frozen.NONE, bodyX);
    MutableNumber<X> count = mathX.create(1);

    for (seq.init(); seq.next();) {
      X index = seq.index;
      Y level = mathY.create(bodyX.getLevelInTree(index).intValue()).getValue();
      boolean hasChildren = bodyX.hasChildren(index);
      Y diff = mathY.subtract(levelCount, level);
      if (hasChildren && bodyX.isExpanded(index)) {
        count.set(mathX.ONE());
        countCells(bodyX, index, count);

        headerX.setMerged(index, count.getValue(), level, mathY.ONE_VALUE(), true);
        level = mathY.increment(level);
        diff = mathY.decrement(diff);
      }
      if (mathY.compare(diff, mathY.ZERO_VALUE()) > 0) {
        headerX.setMerged(index, mathX.ONE_VALUE(), level, diff, true);
      }
    }
    matrix.layout.computeMerging();
  }

  private void countCells(SectionCore<X> section, X parent, MutableNumber<X> count) {
    Iterator<X> it = section.getChildren(parent);
//    if (!section.isExpanded(parent)) return;
    while(it.hasNext()) {
      X index = it.next();
      if (section.isHidden(index)) continue;
      count.increment();
      countCells(section, index, count);
    }
  }

  /**
   * Creates grouping of columns.
   * @param start
   * @param end
   * @param parent
   * @param caption
   * @param toggle
   */
  public void group(X start, X end, X parent, boolean toggle) {
    bodyX.setParent(start, end, parent);
    if (toggle) {
      this.toggle.add(parent);
    } else {
      bodyX.setExpanded(parent, true);
    }

    Y level = mathY.create(bodyX.getLevelInTree(parent).intValue()).getValue();
    Y newCount = mathY.create(2).add(level).getValue();
    boolean moreLevels = mathY.compare(newCount, headerY.getCount()) >= 0;
    if (moreLevels) {
      headerY.setCount(newCount);
    }
  }


  public static void main(String[] args) {
    final Shell shell = new Shell();
    Display display = shell.getDisplay();
    shell.setLayout(new FillLayout());

    final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.V_SCROLL);

    final Axis<Integer> axisX = matrix.getAxisX();
    axisX.getHeader().setVisible(true);
    final Section<Integer> bodyX = axisX.getBody();
    bodyX.setCount(10);
    bodyX.setDefaultMoveable(true);
    bodyX.setDefaultHideable(true);

    final Axis<Integer> axisY = matrix.getAxisY();
    axisY.getHeader().setVisible(true);
//    axisY.getHeader().setCount(3);
    final Section<Integer> bodyY = axisY.getBody();
    bodyY.setCount(10);

    ColumnGrouping<Integer, Integer> grouping = new ColumnGrouping<Integer, Integer>(matrix);
    grouping.group(1, 2, 0, true);
    grouping.group(3, 5, 1, true);
    grouping.group(6, 7, 9, false);

    bodyX.setExpanded(0, true);


    shell.setBounds(400, 200, 600, 400);
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

}
