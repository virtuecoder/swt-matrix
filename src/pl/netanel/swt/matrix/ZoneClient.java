/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Listener;

import pl.netanel.util.Arrays;
import pl.netanel.util.Preconditions;

class ZoneClient<X extends Number, Y extends Number> implements Zone<X, Y> {
  private static final int[] SUPPORTED_EVENTS = {
    SWT.MouseDoubleClick, SWT.MouseDown, SWT.MouseEnter, SWT.MouseExit, SWT.MouseUp,
    SWT.MouseMove, SWT.KeyDown, SWT.KeyUp };

  final ZoneCore<X, Y> core;
  final SectionClient<X> sectionX;
  final SectionClient<Y> sectionY;

  public ZoneClient(SectionClient<X> sectionX, SectionClient<Y> sectionY) {
    Preconditions.checkNotNullWithName(sectionX, "sectionX");
    Preconditions.checkNotNullWithName(sectionY, "sectionY");
    this.sectionX = sectionX;
    this.sectionY = sectionY;
    this.core = new ZoneCore<X, Y>(sectionX.getUnchecked(), sectionY.getUnchecked());
  }

//  public ZoneClient(ZoneCore<X, Y> zone) {
//    Preconditions.checkNotNullWithName(zone, "zone");
//    core = zone;
//  }


  ZoneClient(ZoneCore<X, Y> zone) {
    this.sectionX = zone.sectionX.client;
    this.sectionY = zone.sectionY.client;
    this.core = zone;
  }

  @Override
  public String toString() {
    return core.toString();
  }

  @Override
  public ZoneCore<X, Y> getUnchecked() {
    return core;
  }

  @Override
  public Section<X> getSectionX() {
    return sectionX;
  }

  @Override
  public Section<Y> getSectionY() {
    return sectionY;
  }

  @Override
  public Rectangle getBounds(Frozen frozenX, Frozen frozenY) {
    return core.getBounds(frozenX, frozenY);
  }

  @Override
  public Rectangle getCellBounds(X indexX, Y indexY) {
    sectionY.checkCellIndex(indexY, "indexY");
    sectionX.checkCellIndex(indexX, "indexX");
    return core.getCellBounds(indexX, indexY);
  }

  @Override
  public Point computeSize(X indexX, Y indexY, int wHint, int hHint) {
    return core.computeSize(indexX, indexY, wHint, hHint);
  }

  @Override
  public boolean isSelectionEnabled() {
    return core.isSelectionEnabled();
  }

   @Override
  public void setSelectionEnabled(boolean isSelectionEnabled) {
    core.setSelectionEnabled(isSelectionEnabled);
  }

  @Override
  public boolean isSelected(X indexX, Y indexY) {
    sectionY.checkCellIndex(indexY, "indexY");
    sectionX.checkCellIndex(indexX, "indexX");
    return core.isSelected(indexX, indexY);
  }


  @Override
  public void setSelected(X indexX, Y indexY, boolean state) {
    sectionY.checkCellIndex(indexY, "indexY");
    sectionX.checkCellIndex(indexX, "indexX");
    core.setSelected(indexX, indexY, state);
  }

  @Override
  public void setSelected(X startX, X endX, Y startY, Y endY, boolean state) {
    sectionX.checkRange(startX, endX, sectionX.getCount());
    sectionY.checkRange(startY, endY, sectionY.getCount());
    core.setSelected(startX, endX, startY, endY, state);
  }

  @Override
  public void setSelectedAll(boolean state) {
    core.setSelectedAll(state);
  }

  @Override
  @Deprecated
  public BigInteger getSelectionCount() {
    return getSelectedCount();
  }

  @Override
  public BigInteger getSelectedCount() {
    return core.getSelectedCount();
  }

  @Override
  public Iterator<CellExtent<X, Y>> getSelectedExtentIterator() {
    return core.getSelectedExtentIterator();
  }

  @Override
  public Iterator<Cell<X, Y>> getSelectedIterator() {
    return core.getSelectedIterator();
  }

  @Override
  public CellExtent<X, Y> getSelectedExtent() {
    return core.getSelectedExtent();
  }


  @Override
  public boolean setMerged(X indexX, X countX, Y indexY, Y countY, boolean state) {
    sectionX.checkCellIndex(indexX, "indexX");
    sectionY.checkCellIndex(indexY, "indexY");
    Preconditions.checkArgument(core.sectionX.math.compare(countX, core.cellMergeLimitX) <= 0,
        MessageFormat.format("count{0} {1} is beyond merge limit {2}", "X", countX, core.cellMergeLimitX));
    Preconditions.checkArgument(core.sectionY.math.compare(countY, core.cellMergeLimitY) <= 0,
        MessageFormat.format("count{0} {1} is beyond merge limit {2}", "Y", countY, core.cellMergeLimitY));
    return core.setMerged(indexX, countX, indexY, countY, state);
  }

  @Override
  public CellExtent<X, Y> getMerged(X indexX, Y indexY) {
    sectionX.checkCellIndex(indexX, "indexX");
    sectionY.checkCellIndex(indexY, "indexY");
    return core.getMerged(indexX, indexY);
  }


  @Override
  public boolean isMerged(X indexX, Y indexY) {
    sectionX.checkCellIndex(indexX, "indexX");
    sectionY.checkCellIndex(indexY, "indexY");
    return core.isMerged(indexX, indexY);
  }

  @Override
  public boolean isMerged(X indexX, X countX, Y indexY, Y countY) {
    sectionX.checkCellIndex(indexX, "indexX");
    sectionY.checkCellIndex(indexY, "indexY");
    Preconditions.checkArgument(core.sectionX.math.compare(countX, core.cellMergeLimitX) <= 0,
        MessageFormat.format("count{0} {1} is beyond merge limit {2}", "X", countX, core.cellMergeLimitX));
    Preconditions.checkArgument(core.sectionY.math.compare(countY, core.cellMergeLimitY) <= 0,
        MessageFormat.format("count{0} {1} is beyond merge limit {2}", "Y", countY, core.cellMergeLimitY));
    return core.isMerged(indexX, countX, indexY, countY);
  }

  @Override
  public void setMergeLimit(X limitX, Y limitY) {
    sectionX.checkCellIndex(limitX, "limitX");
    sectionY.checkCellIndex(limitY, "limitY");
    core.setMergeLimit(limitX, limitY);
  };

  @Override
  public Cell<X, Y> getMergeLimit() {
    return core.getMergeLimit();
  }

  @Override
  public void addPainter(Painter<X, Y> painter) {
    checkPainter(painter);
    core.addPainter(painter);
  }

  @Override
  public void addPainter(int index, Painter<X, Y> painter) {
    Preconditions.checkPositionIndex(index, core.getPainterCount() + 1);
    checkPainter(painter);
    core.addPainter(index, painter);
  }

  @Override
  public void setPainter(int index, Painter<X, Y> painter) {
    Preconditions.checkPositionIndex(index, core.getPainterCount());
    checkPainter(painter);
    core.setPainter(index, painter);
  }

  @Override
  public void replacePainter(Painter<X, Y> painter) {
    checkPainter(painter);
    core.replacePainterPreserveStyle(painter);
  }

  @Override
  public void replacePainterPreserveStyle(Painter<X, Y> painter) {
    checkPainter(painter);
    core.replacePainterPreserveStyle(painter);
  }

  @Override
  public Painter<X, Y> removePainter(int index) {
    Preconditions.checkPositionIndex(index, core.getPainterCount());
    return core.removePainter(index);
  }

  @Override
  public boolean removePainter(Painter<X, Y> painter) {
    checkPainter(painter);
    return core.removePainter(painter);
  }

  @Override
  public boolean removePainter(String name) {
    Preconditions.checkNotNullWithName(name, "name");
    return core.removePainter(name);
  }

  @Override
  public int indexOfPainter(String name) {
    Preconditions.checkNotNullWithName(name, "name");
    return core.indexOfPainter(name);
  }

  @Override
  public Painter<X, Y> getPainter(String name) {
    Preconditions.checkNotNullWithName(name, "name");
    return core.getPainter(name);
  }

  @Override
  public Painter<X, Y> getPainter(int index) {
    Preconditions.checkPositionIndex(index, core.getPainterCount());
    return core.getPainter(index);
  }

  @Override
  public int getPainterCount() {
    return core.getPainterCount();
  }

  @Override
  public void bind(int commandId, int eventType, int code) {
    core.bind(commandId, eventType, code);
  }

  @Override
  public void unbind(int commandId, int eventType, int code) {
    Preconditions.checkEventType(eventType);
    core.unbind(commandId, eventType, code);
  }

  @Override
  public void addListener(int eventType, Listener listener) {
    Preconditions.checkNotNullWithName(listener, "listener");
    Preconditions.checkArgument(Arrays.contains(SUPPORTED_EVENTS, eventType), "Event type not supported");
    core.addListener(eventType, listener);
  }

  @Override
  public void removeListener(int eventType, Listener listener) {
    Preconditions.checkNotNullWithName(listener, "listener");
    core.removeListener(eventType, listener);
  }

  @Override
  public void addSelectionListener(SelectionListener listener) {
    Preconditions.checkNotNullWithName(listener, "listener");
    core.addSelectionListener(listener);
  }

  @Override
  public void removeSelectionListener(SelectionListener listener) {
    Preconditions.checkNotNullWithName(listener, "listener");
    core.removeSelectionListener(listener);
  }


  @Override
  public Matrix<X, Y> getMatrix() {
    return core.getMatrix();
  }


  private void checkPainter(Painter<X, Y> painter) {
    Preconditions.checkNotNullWithName(painter, "painter");

    Matrix<X, Y> matrix2 = getMatrix();
    if (matrix2 != null) {
      Preconditions.checkArgument(painter.matrix == null || matrix2.equals(painter.matrix),
          "The painter belongs to a different matrix: %s", painter.matrix);
    }

    Preconditions.checkArgument(painter.zone == null || this.core.equals(painter.zone),
      "The painter belongs to a different zone: %s", painter.zone);
  }

  @Override
  public boolean contains(CellExtent<X, Y> cellExtent, X indexX, Y indexY) {
    return core.contains(cellExtent, indexX, indexY);
  }
}
