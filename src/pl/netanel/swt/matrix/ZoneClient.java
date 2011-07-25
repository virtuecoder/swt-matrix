package pl.netanel.swt.matrix;

import java.math.BigInteger;
import java.util.Iterator;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Listener;

import pl.netanel.util.Preconditions;

class ZoneClient<N0 extends Number, N1 extends Number> implements Zone<N0, N1> {
  final ZoneCore core;
  private final SectionClient<N0> section0;
  private final SectionClient<N1> section1;

  public ZoneClient(SectionClient<N0> section0, SectionClient<N1> section1) {
    Preconditions.checkNotNullWithName(section0, "sectoin0");
    Preconditions.checkNotNullWithName(section1, "section1");
    this.section0 = section0;
    this.section1 = section1;
    this.core = new ZoneCore(section0.getCore(), section1.getCore());
  }
  
//  public ZoneClient(ZoneCore<N0, N1> zone) {
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
  public Section getSection0() {
    return section0;
  }

  @Override
  public Section getSection1() {
    return section1;
  }
  
  @Override
  public Rectangle getBounds() {
    return core.getBounds();
  }

  @Override
  public Rectangle getCellBounds(N0 index0, N1 index1) {
    section0.checkCellIndex(index0, "index0");
    section1.checkCellIndex(index1, "index1");
    return core.getCellBounds(index0, index1);
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
  public boolean isSelected(N0 index0, N1 index1) {
    section0.checkCellIndex(index0, "index0");
    section1.checkCellIndex(index1, "index1");
    return core.isSelected(index0, index1);
  }


  @Override
  public void setSelected(N0 index0, N1 index1, boolean state) {
    section0.checkCellIndex(index0, "index0");
    section1.checkCellIndex(index1, "index1");
    core.setSelected(index0, index1, state);
  }
  
  @Override
  public void setSelected(N0 start0, N0 end0, N1 start1, N1 end1, boolean state) {
    section0.checkRange(start0, end0, section0.getCount());
    section1.checkRange(start1, end1, section1.getCount());
    core.setSelected(start0, end0, start1, end1, state);
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
  public Number[] getSelectedExtent() {
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

  
  private void checkPainter(Painter<N0, N1> painter) {
    Preconditions.checkNotNullWithName(painter, "painter");
    
    Preconditions.checkArgument(painter.zone == null || this.equals(painter.zone), 
      "The painter belongs to a different zone: %s", painter.zone);
    
    Matrix<N0, N1> matrix2 = getMatrix();
    if (matrix2 != null) {
      Preconditions.checkArgument(painter.matrix == null || matrix2.equals(painter.matrix), 
        "The painter belongs to a different matrix: %s", painter.matrix);
    }
  }

}
