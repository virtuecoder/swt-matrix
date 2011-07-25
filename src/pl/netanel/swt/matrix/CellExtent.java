package pl.netanel.swt.matrix;
/**
 * Rectangular set of cells represented by start and end indexes 
 * on both axises. Instances of this class are immutable.
 * 
 * @param <X> indexing type for horizontal axis
 * @param <Y> indexing type for vertical axis
 * 
 * @author Jacek Kolodziejczyk created 25-07-2011
 */
public class CellExtent<X extends Number, Y extends Number> {
  final X startX, endX;
  final Y startY, endY;

  /**
   * Constructs the cell from the start indexes and end indexes.
   * 
   * @param startX start cell index on the horizontal axis
   * @param endX end cell index on the horizontal axis
   * @param startY start cell index on the vertical axis
   * @param endY end cell index on the vertical axis
   */
  public CellExtent(X startX, X endX, Y startY, Y endY) {
    this.startX = startX;
    this.endX = endX;
    this.startY = startY;
    this.endY = endY;
  }

  /**
   * Constructs the cell from the start cell and end cell. 
   * @param start top left starting point of the cell extent rectangle
   * @param end bottom right ending point of the cell extent rectangle
   */
  public CellExtent(Cell<X, Y> start, Cell<X, Y> end) {
    this.startX = start.indexX;
    this.endX = end.indexX;
    this.startY = start.indexY;
    this.endY = end.indexY;
  }
  
  /**
   * Returns start cell index on the horizontal axis. 
   * @return start cell index on the horizontal axis
   */
  public X getStartX() {
    return startX;
  }
  /**
   * Returns end cell index on the horizontal axis. 
   * @return end cell index on the horizontal axis
   */
  public X getEndX() {
    return endX;
  }
  /**
   * Returns start cell index on the vertical axis. 
   * @return start cell index on the vertical axis
   */
  public Y getStartY() {
    return startY;
  }
  /**
   * Returns end cell index on the vertical axis. 
   * @return end cell index on the vertical axis
   */
  public Y getEndY() {
    return endY;
  }
  
  
}
