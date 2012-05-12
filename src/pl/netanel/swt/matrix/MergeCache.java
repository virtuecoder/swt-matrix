package pl.netanel.swt.matrix;

import pl.netanel.swt.matrix.AxisLayout.Cache;

/**
 * Stores the current cells and lines bounds considering merging settings.
 *
 * @author Jacek
 * @created 12-05-2012
 */
class MergeCache<X extends Number, Y extends Number> {

  private final Matrix<X, Y> matrix;
  private final AxisLayout<X> layoutX;
  private final AxisLayout<Y> layoutY;
  private FrozenCache[] cache;

  public MergeCache(Matrix<X, Y> matrix) {
    this.matrix = matrix;
    this.layoutX = matrix.layoutX;
    this.layoutY = matrix.layoutY;
    cache = new FrozenCache[9];
    cache[0] = new FrozenCache(layoutX.head, layoutY.head);
    cache[1] = new FrozenCache(layoutX.head, layoutY.main);
    cache[2] = new FrozenCache(layoutX.head, layoutY.tail);
    cache[3] = new FrozenCache(layoutX.main, layoutY.head);
    cache[4] = new FrozenCache(layoutX.main, layoutY.main);
    cache[5] = new FrozenCache(layoutX.main, layoutY.tail);
    cache[6] = new FrozenCache(layoutX.tail, layoutY.head);
    cache[7] = new FrozenCache(layoutX.tail, layoutY.main);
    cache[8] = new FrozenCache(layoutX.tail, layoutY.tail);
  }

  /**
   * Computes merged cells bounds and breaks the dividing lines where the merged cells are.
   */
  public void compute() {

    for (AxisItem<X> itemX: layoutX.head.items) {
      for (AxisItem<Y> itemY: layoutY.head.items) {

        matrix.model.getZone(itemX.section, itemY.section).cellMerging.getExtent(itemX.index, itemY.index);
      }
    }
  }




  static class FrozenCache {

    private final Cache cacheX;
    private final Cache cacheY;

    public FrozenCache(Cache cacheX, Cache cacheY) {
      this.cacheX = cacheX;
      this.cacheY = cacheY;
    }
  }

}
