package pl.netanel.swt.matrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import pl.netanel.util.ImmutableIterator;

class MatrixLayout<X extends Number, Y extends Number> implements Iterable<ZoneCore<X, Y>> {

  final ArrayList<ZoneCore<X, Y>> zones;
  final AxisLayout<X> layoutX;
  final AxisLayout<Y> layoutY;

  int[] paintOrder;
  private ExtentPairSequence seq;
  private Matrix<X, Y> matrix;
  List<List<MergeCache<X, Y>>> mergingCache;


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

    int frozenCount = Frozen.values().length;
    mergingCache = new ArrayList<List<MergeCache<X, Y>>>(frozenCount);
    for (int i = 0; i < frozenCount; i++) {
      ArrayList<MergeCache<X, Y>> row = new ArrayList<MergeCache<X, Y>>(frozenCount);
      mergingCache.add(row);
      for (int j = 0; j < frozenCount; j++) {
        row.add(new MergeCache<X, Y>());
      }
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
              }
            });
            zone.setHeaderStyle();
          }
          else if (sectionX.equals(headerX) && sectionY.equals(bodyY)) {
            zone.addPainter(new Painter<X, Y>(Painter.NAME_CELLS, Painter.SCOPE_CELLS_X) {
              @Override
              public void setupSpatial(Number indexX, Number indexY) {
                text = indexY.toString();
              }
            });
            zone.setHeaderStyle();
          }
          else {
            zone.addPainter(new Painter<X, Y>(Painter.NAME_CELLS));
            zone.setHeaderStyle();
          }
        }
      }
    }
    calculatePaintOrder();

    seq = new ExtentPairSequence();
  }


  private void calculatePaintOrder() {
    paintOrder = new int[zones.size()];
    int[] orderY = matrix.axisY.getZOrder();
    int[] orderX = matrix.axisX.getZOrder();
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
   * Attention: it is to be called only by a UI handler, since it emits Selection event.
   * @param startX
   * @param endX
   * @param startY
   * @param endY
   * @param selected
   */
  void setSelected (
      AxisItem<X> startX, AxisItem<X> endX,
      AxisItem<Y> startY, AxisItem<Y> endY, boolean selected) {

    // Make sure start < end
    if (matrix.layoutX.comparePosition(startX, endX) > 0) {
      AxisItem<X> tmp;
      tmp = startX; startX = endX; endX = tmp;
    }
    if (matrix.layoutY.comparePosition(startY, endY) > 0) {
      AxisItem<Y> tmp;
      tmp = startY; startY = endY; endY = tmp;
    }

    ZoneCore<X, Y> lastZone = null;
    seq.init(startX, endX, startY, endY);
    while (seq.next()) {
      ZoneCore<X, Y> zone = getZone(seq.sectionX, seq.sectionY);
      if (zone.isSelectionEnabled()) {
        zone.setSelected(
          seq.startX.getValue(), seq.endX.getValue(),
          seq.startY.getValue(), seq.endY.getValue(), selected);

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
    layoutX.computeIfRequired();
    layoutY.computeIfRequired();
    computeMerging();
  }

  public void compute(boolean includeX, boolean includeY) {
    if (includeX) layoutX.compute();
    if (includeY) layoutY.compute();
    computeMerging();
  }

   public void computeMerging() {
     Math<X> mathX = layoutX.math;
     Math<Y> mathY = layoutY.math;

    // Clear merging cache
    int frozenCount = Frozen.values().length;
    for (int i = 0; i < frozenCount; i++) {
      for (int j = 0; j < frozenCount; j++) {
        mergingCache.get(i).get(j).bounds.clear();
      }
    }

    // For each frozen area
    for (int i1 = 0; i1 < frozenCount; i1++) {
      AxisLayout<X>.Cache cacheX = layoutX.getCache(Frozen.values()[i1]);
      ArrayList<AxisItem<X>> itemsX = cacheX.items;
      List<MergeCache<X, Y>> mergeX = mergingCache.get(i1);
      for (int j1 = 0; j1 < frozenCount; j1++) {
        AxisLayout<Y>.Cache cacheY = layoutY.getCache(Frozen.values()[j1]);
        ArrayList<AxisItem<Y>> itemsY = cacheY.items;

        Map<CellExtent<X, Y>, Bound[]> cache = mergeX.get(j1).bounds;
        if (itemsX.isEmpty()) continue;

        // Check each cell for merging
        for (int i = 0; i < itemsX.size() - 1; i++) {
          for (int j = 0; j < itemsY.size() - 1; j++) {
            AxisItem<X> itemX = itemsX.get(i);
            AxisItem<Y> itemY = itemsY.get(j);

            CellExtent<X, Y> span =
                getZone(itemX.section, itemY.section).cellMerging.getSpan(itemX.index, itemY.index);

            // If cell is not merged then continue
            if (span == null) continue;

            // Otherwise compute the merged bounds
            Bound boundX = null, boundY = null;
            Bound[] bounds = cache.get(span);
            if (bounds == null) {
              boundX = new Bound();
              boundY = new Bound();
              cache.put(span, new Bound[] {boundX, boundY});

              boundX.distance = cacheX.cells.get(i).distance;
              boundY.distance = cacheY.cells.get(j).distance;

              // If extent is beyond viewport
              if (mathX.compare(span.endX, itemsX.get(itemsX.size() - 2).index) > 0) {

              }
            }
            else {
              boundX = bounds[0];
              boundY = bounds[1];
            }

            int compareX = mathX.compare(span.startX, itemX.index);
            int compareY = mathY.compare(span.startY, itemY.index);

            if (compareX == 0) {
              boundY.width += cacheY.cells.get(j).width;
              if (compareY < 0) {
                boundY.width += cacheY.lines.get(j).width;
              }
            }
            if (compareY == 0) {
              boundX.width += cacheX.cells.get(i).width;
              if (compareX < 0) {
                boundX.width += cacheX.lines.get(i).width;
              }
            }
            //if (itemY.index.equals(extent.endY) && itemX.index.equals(extent.endX)) continue;
          }
        }
      }
    }
  }


  static class MergeCache<X extends Number, Y extends Number> {
    Map<CellExtent<X, Y>, Bound[]> bounds = new HashMap<CellExtent<X, Y>, Bound[]>();
  }
}
