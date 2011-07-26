package pl.netanel.swt.matrix;

/**
 * Pair of indexes representing a cell coordinates.
 * Instances of this class are immutable.
 * 
 * @param <X> indexing type for horizontal axis
 * @param <Y> indexing type for vertical axis
 * 
 * @author Jacek Kolodziejczyk created 25-07-2011
 */
class Cell<X extends Number, Y extends Number> {
  final X indexX;
  final Y indexY;
  public Cell(X indexX, Y indexY) {
    this.indexX = indexX;
    this.indexY = indexY;
  }
  
  /**
   * Returns cell index on the horizontal axis. 
   * @return cell index on the horizontal axis
   */
  public X getIndexX() {
    return indexX;
  }
  /**
   * Returns cell index on the vertical axis. 
   * @return cell index on the vertical axis
   */
  public Y getIndexY() {
    return indexY;
  }
  
}
