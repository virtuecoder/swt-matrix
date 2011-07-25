package pl.netanel.swt.matrix;

import java.math.BigInteger;
import java.util.Iterator;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Listener;

import pl.netanel.util.Preconditions;

class ZoneClient<X extends Number, Y extends Number> implements Zone<X, Y> {
  final ZoneCore core;
  private final SectionClient<X> sectionX;
  private final SectionClient<Y> sectionY;

  public ZoneClient(SectionClient<X> sectionX, SectionClient<Y> sectionY) {
    Preconditions.checkNotNullWithName(sectionX, "sectionX");
    Preconditions.checkNotNullWithName(sectionY, "sectionY");
    this.sectionX = sectionX;
    this.sectionY = sectionY;
    this.core = new ZoneCore(sectionX.getCore(), sectionY.getCore());
  }
  
//  public ZoneClient(ZoneCore<X, Y> zone) {
//    Preconditions.checkNotNullWithName(zone, "zone");
//    core = zone;
//  }

  @Override
  public boolean equals(Object obj) {
    return core.equals(obj);
  }

  @Override
  public int hashCode() {
    return core.hashCode();
  }

  @Override
  public String toString() {
    return core.toString();
  }

  @Override
  public ZoneCore getCore() {
    return core;
  }

  @Override
  public Section getSectionY() {
    return sectionY;
  }

  @Override
  public Section getSectionX() {
    return sectionX;
  }
  
  @Override
  public Rectangle getBounds() {
    return core.getBounds();
  }

  @Override
  public Rectangle getCellBounds(X indexX, Y indexY) {
    sectionY.checkCellIndex(indexY, "indexY");
    sectionX.checkCellIndex(indexX, "indexX");
    return core.getCellBounds(indexX, indexY);
  }

  @Override
  public void setDefaultBackground(Color color) {
    Preconditions.checkNotNullWithName(color, "color");
    core.setDefaultBackground(color);
  }

  @Override
  public Color getDefaultBackground() {
    return core.getDefaultBackground();
  }

  @Override
  public void setDefaultForeground(Color color) {
    Preconditions.checkNotNullWithName(color, "color");
    core.setDefaultForeground(color);
  }

  @Override
  public Color getDefaultForeground() {
    return core.getDefaultForeground();
  }

  @Override
  public void setSelectionForeground(Color color) {
    Preconditions.checkNotNullWithName(color, "color");
    core.setSelectionForeground(color);
  }

  @Override
  public Color getSelectionForeground() {
    return core.getSelectionForeground();
  }

  @Override
  public void setSelectionBackground(Color color) {
    Preconditions.checkNotNullWithName(color, "color");
    core.setSelectionBackground(color);
  }

  @Override
  public Color getSelectionBackground() {
    return core.getSelectionBackground();
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
    sectionY.checkRange(startY, endY, sectionY.getCount());
    sectionX.checkRange(startX, endX, sectionX.getCount());
    core.setSelected(startX, endX, startY, endY, state);
  }

  @Override
  public void setSelectedAll(boolean state) {
    core.setSelectedAll(state);
  }

  @Override
  public BigInteger getSelectionCount() {
    return core.getSelectionCount();
  }

  @Override
  public BigInteger getSelectedCount() {
    return core.getSelectedCount();
  }

  @Override
  public Iterator getSelectedExtentIterator() {
    return core.getSelectedExtentIterator();
  }

  @Override
  public Iterator getSelectedIterator() {
    return core.getSelectedIterator();
  }
  
  @Override
  public CellExtent getSelectedExtent() {
    return core.getSelectedExtent();
  }


  @Override
  public void addPainter(Painter painter) {
    checkPainter(painter);
    core.addPainter(painter);
  }

  @Override
  public void addPainter(int index, Painter painter) {
    Preconditions.checkPositionIndex(index, core.getPainterCount() + 1);
    checkPainter(painter);
    core.addPainter(index, painter);
  }

  @Override
  public void setPainter(int index, Painter painter) {
    Preconditions.checkPositionIndex(index, core.getPainterCount());
    checkPainter(painter);
    core.setPainter(index, painter);
  }

  @Override
  public void replacePainter(Painter painter) {
    checkPainter(painter);
    core.replacePainter(painter);
  }

  @Override
  public Painter removePainter(int index) {
    Preconditions.checkPositionIndex(index, core.getPainterCount());
    return core.removePainter(index);
  }

  @Override
  public boolean removePainter(Painter painter) {
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
  public Painter getPainter(String name) {
    Preconditions.checkNotNullWithName(name, "name");
    return core.getPainter(name);
  }
  
  @Override
  public Painter getPainter(int index) {
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
    core.addListener(eventType, listener);
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
  public Matrix getMatrix() {
    return core.getMatrix();
  }

  
  private void checkPainter(Painter<X, Y> painter) {
    Preconditions.checkNotNullWithName(painter, "painter");
    
    Preconditions.checkArgument(painter.zone == null || this.equals(painter.zone), 
      "The painter belongs to a different zone: %s", painter.zone);
    
    Matrix<X, Y> matrix2 = getMatrix();
    if (matrix2 != null) {
      Preconditions.checkArgument(painter.matrix == null || matrix2.equals(painter.matrix), 
        "The painter belongs to a different matrix: %s", painter.matrix);
    }
  }

}
