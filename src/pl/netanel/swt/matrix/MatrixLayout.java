/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;

import pl.netanel.swt.matrix.Frozen.PairSequence;
import pl.netanel.util.ImmutableIterator;

class MatrixLayout<X extends Number, Y extends Number> implements Iterable<ZoneCore<X, Y>> {

  final ArrayList<ZoneCore<X, Y>> zones;
  final AxisLayout<X> layoutX;
  final AxisLayout<Y> layoutY;

  int[] paintOrder;
  private ExtentPairSequence seq;
  private Matrix<X, Y> matrix;
  List<MergeCache<X, Y>> mergeCache;
//  List<Rectangle> mergingCacheLineX;
//  List<Rectangle> mergingCacheLineY;
  Region region;

  private PairSequence frozenSeq;


  public MatrixLayout(AxisLayout<X> layoutX, AxisLayout<Y> layoutY) {
    this.layoutX = layoutX;
    this.layoutY = layoutY;
    zones = new ArrayList<ZoneCore<X, Y>>(layoutX.sections.size() * layoutY.sections.size());
    for (SectionCore<X> sectionX: layoutX.sections) {
      for (SectionCore<Y> sectionY: layoutY.sections) {
        ZoneCore<X, Y> zone = new ZoneCore<X, Y>(sectionX, sectionY);
        zones.add(zone);
      }
    }

    // Initialize merge cache
    mergeCache = new ArrayList<MergeCache<X, Y>>();
    frozenSeq = new Frozen.PairSequence();
    for (frozenSeq.init(); frozenSeq.next();) {
      mergeCache.add(new MergeCache<X, Y>());
    }
  }

  void setMatrix(Matrix<X, Y> matrix) {
    this.matrix = matrix;
    Section<X> bodyX = matrix.axisX.getBody();
    Section<Y> bodyY  = matrix.axisY.getBody();
    Section<X> headerX = matrix.axisX.getHeader();
    Section<Y> headerY = matrix.axisY.getHeader();

    for (SectionClient<Y> sectionY: matrix.axisY.sections) {
      for (SectionClient<X> sectionX: matrix.axisX.sections) {
        ZoneCore<X, Y> zone = getZone(sectionX.getUnchecked(), sectionY.getUnchecked());
        zone.setMatrix(matrix);
        if (zone.getPainterCount() == 0) {
          zone.addPainter(new BackgroundPainter<X, Y>());
          zone.addPainter(new LinePainter<X, Y>(Painter.NAME_LINES_X));
          zone.addPainter(new LinePainter<X, Y>(Painter.NAME_LINES_Y));

          if (sectionY.equals(bodyY) && sectionX.equals(bodyX)) {
            Painter<X, Y> painter = new Painter<X, Y>(Painter.NAME_CELLS) {
              @Override
              public void setupSpatial(X indexX, Y indexY) {
                  text = indexY.toString() + ", " + indexX.toString();
                  super.setupSpatial(indexX, indexY);
              }
            };
            zone.addPainter(painter);
            zone.setBodyStyle();
          }
          else if (sectionY.equals(headerY) && sectionX.equals(bodyX)) {
            zone.addPainter(new Painter<X, Y>(Painter.NAME_CELLS, Painter.SCOPE_CELLS_Y) {
              @Override
              public void setupSpatial(X indexX, Y indexY) {
                text = indexX.toString();
                super.setupSpatial(indexX, indexY);
              }
            });
            zone.setHeaderStyle();
          }
          else if (sectionX.equals(headerX) && sectionY.equals(bodyY)) {
            zone.addPainter(new Painter<X, Y>(Painter.NAME_CELLS, Painter.SCOPE_CELLS_X) {
              @Override
              public void setupSpatial(X indexX, Y indexY) {
                text = indexY.toString();
                super.setupSpatial(indexX, indexY);
              }
            });
            zone.setHeaderStyle();
          }
          else {
            zone.addPainter(new Painter<X, Y>(Painter.NAME_CELLS));
            zone.setHeaderStyle();
          }
        }
        ZoneEditor.createCopyOnlyEditor(zone);
      }
    }
    calculatePaintOrder();

    seq = new ExtentPairSequence();
  }

  public void dispose() {
    if (region != null && !region.isDisposed()) {
      region.dispose();
    }
  }


  private void calculatePaintOrder() {
    paintOrder = new int[zones.size()];
    int[] orderY = matrix.axisY.getPaintOrder();
    int[] orderX = matrix.axisX.getPaintOrder();
    int k = 0;
    for (int i = 0, imax = orderY.length; i < imax; i++) {
      for (int j = 0, jmax = orderX.length; j < jmax; j++) {
        paintOrder[k++] = zones.indexOf(getZone(
          matrix.axisX.sections.get(orderX[j]).getUnchecked(),
          matrix.axisY.sections.get(orderY[i]).getUnchecked()));
      }
    }
  }

  public ZoneCore<X, Y> getZone(SectionCore<X> sectionX, SectionCore<Y> sectionY) {
    for (ZoneCore<X, Y> zone: zones) {
      if (zone.getSectionY().equals(sectionY) &&
          zone.getSectionX().equals(sectionX) ) {
        return zone;
      }
    }
    return null;
  }

  /**
   * Zone iterator
   */
  @Override
  public Iterator<ZoneCore<X, Y>> iterator() {
    return new ImmutableIterator<ZoneCore<X, Y>>() {
      int i;

      @Override
      public boolean hasNext() {
        return i < zones.size();
      }

      @Override
      public ZoneCore<X, Y> next() {
        return zones.get(paintOrder[i++]);
      }

    };
  }


  /**
   * Attention: it is to be called only by a UI handler, since it emits Selection event.
   * @param startY
   * @param endY
   * @param startX
   * @param endX
   * @param selectState
   */
  public void setSelected(boolean selectState, boolean notify) {
//    // Determine if there is a selection change
//    boolean modified = false;
//    if (notify) {
//      for (Zone zone: zones) {
//        if (selectState) {
//          boolean allSelected = zone.getSelectedCount().equals(
//            new BigInteger(zone.getSectionY().getCount().toString()).multiply(
//            new BigInteger(zone.getSectionX().getCount().toString())));
//          if (!allSelected) {
//            modified = true;
//            break;
//          }
//        }
//        else {
//          boolean nothingSelected = BigInteger.ZERO.equals(zone.getSelectionCount());
//          if (!nothingSelected) {
//            modified = true;
//            break;
//          }
//        }
//      }
//    }

    // Set selection and notify
    for (ZoneCore<X, Y> zone: zones) {
      zone.setSelectedAll(selectState);

      if (notify) {
        zone.addSelectionEvent();
      }
    }
    matrix.axisY.setSelected(selectState, notify, false);
    matrix.axisX.setSelected(selectState, notify, false);
//    if (notify && modified) {
//      for (Section section: matrix.axisY.sections) {
//        section.addSelectionEvent();
//      }
//      for (Section section: matrix.axisX.sections) {
//        section.addSelectionEvent();
//      }
//    }
  }

  /**
   * Attention: it is to be called only by a UI handler, since it emits Selection event
   * and passes the flag to skip the hidden items.
   * @param startX
   * @param endX
   * @param startY
   * @param endY
   * @param selected
   * @param skipHidden
   */
  void setSelectedFromUI (
      AxisItem<X> startX, AxisItem<X> endX,
      AxisItem<Y> startY, AxisItem<Y> endY, boolean selected, boolean skipHidden) {

//    System.out.println(String.format("%s %s %s %s",
//      startX.index, endX.index,
//      startY.index, endY.index));

    // Make sure start < end
    if (matrix.layoutX.comparePosition(startX, endX) > 0) {
      AxisItem<X> tmp;
      tmp = startX; startX = endX; endX = tmp;
    }
    if (matrix.layoutY.comparePosition(startY, endY) > 0) {
      AxisItem<Y> tmp;
      tmp = startY; startY = endY; endY = tmp;
    }

    // Check merged cells
    seq.init(startX, endX, startY, endY);
    while (seq.next()) {
      ZoneCore<X, Y> zone = getZone(seq.sectionX, seq.sectionY);
      CellExtent<X, Y> overlap = zone.cellMerging.overlap(
        seq.startX.getValue(), seq.endX.getValue(),
        seq.startY.getValue(), seq.endY.getValue());
      if (seq.sectionX.equals(startX.section) &&
        layoutX.comparePosition(startX.section, startX.index, seq.sectionX, overlap.startX) > 0) {
        startX = AxisItem.createUnchecked(seq.sectionX, overlap.startX);
      }
      if (seq.sectionX.equals(endX.section) &&
        layoutX.comparePosition(endX.section, endX.index, seq.sectionX, overlap.endX) < 0) {
        endX = AxisItem.createUnchecked(seq.sectionX, overlap.endX);
      }
      if (seq.sectionY.equals(startY.section) &&
        layoutY.comparePosition(startY.section, startY.index, seq.sectionY, overlap.startY) > 0) {
        startY = AxisItem.createUnchecked(seq.sectionY, overlap.startY);
      }
      if (seq.sectionY.equals(endY.section) &&
        layoutY.comparePosition(endY.section, endY.index, seq.sectionY, overlap.endY) < 0) {
        endY = AxisItem.createUnchecked(seq.sectionY, overlap.endY);
      }
    }


    ZoneCore<X, Y> lastZone = null;
    seq.init(startX, endX, startY, endY);
    while (seq.next()) {
      ZoneCore<X, Y> zone = getZone(seq.sectionX, seq.sectionY);

      if (zone.isSelectionEnabled()) {
        zone.setSelected(
          seq.startX.getValue(), seq.endX.getValue(),
          seq.startY.getValue(), seq.endY.getValue(), selected, true, skipHidden);

        if (!zone.equals(lastZone)) {
          zone.addSelectionEvent();
          lastZone = zone;
        }
      }
    }
  }

  void insertInZonesX(SectionCore<X> section, X target, X count) {
     for (ZoneCore<X, Y> zone: zones) {
        if (zone.sectionX.equals(section)) {
          zone.cellSelection.insertX(target, count);
          zone.lastSelection.deleteX(target, count);
        }
     }
  }

  void insertInZonesY(SectionCore<Y> section, Y target, Y count) {
    for (ZoneCore<X, Y> zone: zones) {
      if (zone.sectionY.equals(section)) {
        zone.cellSelection.insertY(target, count);
        zone.lastSelection.insertY(target, count);
      }
    }
  }

//  /**
//   * Returns the body zone of this matrix.
//   * @return the body zone of this matrix
//   */
//  public ZoneCore<X, Y> getBody() {
//    return getZone(matrix.axisX.getBody().ge, matrix.axisY.getBody());
//  }
//  /**
//   * Returns the column header zone of this matrix.
//   * @return the column header zone of this matrix
//   */
//  public ZoneCore<X, Y> getHeaderX() {
//    return getZone(matrix.axisX.getBody(), matrix.axisY.getHeader());
//  }
//  /**
//   * Returns the row header zone of this matrix.
//   * @return the row header zone of this matrix
//   */
//  public ZoneCore<X, Y> getHeaderY() {
//    return getZone(matrix.axisX.getHeader(), matrix.axisY.getBody());
//  }
//  /**
//   * Returns the top left zone of this matrix.
//   * @return the top left zone of this matrix
//   */
//  public ZoneCore<X, Y> getTopLeft() {
//    return getZone(matrix.axisX.getHeader(), matrix.axisY.getHeader());
//  }



  /**
   * Iterates over all extents within the boundaries defined
   * by items passed to the init() method.
   *
   * @author Jacek created 21-02-2011
   */
  class ExtentPairSequence {
    SectionCore<X> sectionX;
    SectionCore<Y> sectionY;
    MutableNumber<X> startX, endX;
    MutableNumber<Y> startY, endY;
    Axis<X>.ExtentSequence seqX;
    Axis<Y>.ExtentSequence seqY;
    boolean empty;
    private AxisItem<X> startItem1;
    private AxisItem<X> endItem1;

    public ExtentPairSequence() {
      seqY = matrix.axisY.new ExtentSequence();
      seqX = matrix.axisX.new ExtentSequence();
    }

    public void init(AxisItem<X> startX, AxisItem<X> endX, AxisItem<Y> startY, AxisItem<Y> endY) {
      startItem1 = startX;
      endItem1 = endX;
      seqX.init(startX, endX);
      seqY.init(startY, endY);
      empty = !seqY.next();
      this.sectionY = seqY.section;
      this.startY = seqY.start;
      this.endY = seqY.end;
    }

    public boolean next() {
      if (empty) return false;

      if (!seqX.next()) {
        if (!seqY.next()) {
          return false;
        }
        this.sectionY = seqY.section;
        this.startY = seqY.start;
        this.endY = seqY.end;
        seqX.init(startItem1, endItem1);
        seqX.next();
      }
      sectionX = seqX.section;
      startX = seqX.start;
      endX = seqX.end;
      return true;
    }
  }

  public void compute() {
    layoutX.compute();
    layoutY.compute();
    computeMerging();
  }

  public void compute(boolean includeX, boolean includeY) {
    if (includeX) layoutX.compute();
    if (includeY) layoutY.compute();
    computeMerging();
  }

  public void computeIfRequired() {
    layoutX.computeIfRequired();
    layoutY.computeIfRequired();

    for (ZoneCore<X, Y> zone: zones) {
      if (zone.isDirty) {
        computeMerging();
        break;
      }
    }
  }

  /*
   * Each frozen area contains a
   */
  public void computeMerging() {
    // Clear region
    if (region != null && !region.isDisposed()) {
      region.dispose();
    }
    region = new Region();
    region.add(0, 0, layoutX.getViewportSize(), layoutY.getViewportSize());

    // For each frozen area
    for (frozenSeq.init(); frozenSeq.next();) {
      AxisLayout<X>.Cache cacheX = layoutX.getCache(frozenSeq.frozenX);
      AxisLayout<Y>.Cache cacheY = layoutY.getCache(frozenSeq.frozenY);
      ArrayList<AxisItem<X>> itemsX = cacheX.items;
      ArrayList<AxisItem<Y>> itemsY = cacheY.items;
      if (itemsX.isEmpty() || itemsY.isEmpty()) continue;

      MergeCache<X, Y> cache = mergeCache.get(frozenSeq.index);
      cache.clear();

      // Check each cell in the viewport cache
      int sizeX = itemsX.size();
      int sizeY = itemsY.size();
      for (int i = 0; i < sizeX - 1; i++) {
        for (int j = 0; j < sizeY - 1; j++) {
          AxisItem<X> itemX = itemsX.get(i);
          AxisItem<Y> itemY = itemsY.get(j);

          ZoneCore<X, Y> zone = getZone(itemX.section, itemY.section);
          int spanIndex = zone.cellMerging.indexOf(itemX.index, itemY.index);

          Bound boundX, boundY;

          // If cell is not merged then continue
          if (spanIndex == -1) {
            boundX = cacheX.cells.get(i);
            boundY = cacheY.cells.get(j);
            cache.add(zone, itemX, itemY, boundX, boundY);
            continue;
          }

          // Otherwise compute the merged bounds
          if (!cache.contains(zone, spanIndex)) {
            boundX = layoutX.getBound(itemX.section, zone.cellMerging.itemsX.get(spanIndex), cacheX.cells.get(i).distance);
            boundY = layoutY.getBound(itemY.section, zone.cellMerging.itemsY.get(spanIndex), cacheY.cells.get(j).distance);
            cache.add(zone, spanIndex, itemX, itemY, boundX, boundY);
            region.subtract(
                java.lang.Math.max(boundX.distance, 0),
                java.lang.Math.max(boundY.distance, 0), boundX.width, boundY.width);
          }
        }
      }
    }
  }


  Rectangle getMergedBounds(ZoneCore<X, Y> zone, X indexX, Y indexY) {
    int spanIndex = zone.cellMerging.indexOf(indexX, indexY);
    if (spanIndex == -1) return null;

    // For each frozen area
    for (frozenSeq.init(); frozenSeq.next();) {
      MergeCache<X, Y> cache = mergeCache.get(frozenSeq.index);
      Integer boundIndex = cache.get(zone, spanIndex);
      if (boundIndex == null) continue;
      Bound boundX = cache.boundsX.get(boundIndex);
      Bound boundY = cache.boundsY.get(boundIndex);
      return new Rectangle(boundX.distance, boundY.distance, boundX.width, boundY.width);
    }

    return null;
  }

  /**
   * Caches layout data for a single frozen area.
   * <p>
   * If a value in the bounds is null it should be skipped in the sequence,
   * because the cell is part of merged cell and it's area has been draw already.
   *
   * @author jacek.p.kolodziejczyk@gmail.com
   * @created 25-06-2012
   */
  static class MergeCache<X extends Number, Y extends Number> {
    ArrayList<AxisItem<X>> itemsX = new ArrayList<AxisItem<X>>();
    ArrayList<AxisItem<Y>> itemsY = new ArrayList<AxisItem<Y>>();
    ArrayList<Bound> boundsX = new ArrayList<Bound>();
    ArrayList<Bound> boundsY = new ArrayList<Bound>();

    // maps span index to bound index
    private HashMap<Zone<X, Y>, HashMap<Integer, Integer>> spans =
        new HashMap<Zone<X, Y>, HashMap<Integer, Integer>>();

    public void clear() {
      itemsX.clear();
      itemsY.clear();
      boundsX.clear();
      boundsY.clear();
      spans.clear();
    }

    public void add(Zone<X, Y> zone, int spanIndex, AxisItem<X> itemX, AxisItem<Y> itemY, Bound boundX, Bound boundY) {
      int boundIndex = this.boundsX.size();
      this.itemsX.add(itemX);
      this.itemsY.add(itemY);
      this.boundsX.add(boundX);
      this.boundsY.add(boundY);
      HashMap<Integer, Integer> map = spans.get(zone);
      if (map == null) {
        spans.put(zone, map = new HashMap<Integer, Integer>());
      }
      map.put(spanIndex, boundIndex);
    }

    public void add(Zone<X, Y> zone, AxisItem<X> itemX, AxisItem<Y> itemY, Bound boundX, Bound boundY) {
      this.itemsX.add(itemX);
      this.itemsY.add(itemY);
      this.boundsX.add(boundX);
      this.boundsY.add(boundY);
    }

    public boolean contains(ZoneCore<X, Y> zone, int spanIndex) {
      HashMap<Integer, Integer> map = spans.get(zone);
      if (map == null) return false;
      return map.containsKey(spanIndex);
    }

    public Integer get(Zone<X, Y> zone, Integer spanIndex) {
      HashMap<Integer, Integer> map = spans.get(zone);
      if (map == null) return null;
      return map.get(spanIndex);
    }
  }
}
