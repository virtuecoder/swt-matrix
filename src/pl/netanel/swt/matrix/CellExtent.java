package pl.netanel.swt.matrix;

import pl.netanel.util.Preconditions;

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
   * Creates a new instance of CellExtent. Arguments are validated.
   * <p>
   * <code>startX</code>,<code>endX</code>, <code>startY</code> and
   * <code>endY</code> numbers are item indexes in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   *
   * @param startX first index of the range of column items
   * @param endX last index of the range of column items
   * @param startY first index of the range of row items
   * @param endY last index of the range of row items
   * @return a new instance of this class
   *
   * @throws IllegalArgumentException if <code>startX</code> or <code>endX</code>
   *           or <code>startY</code> or <code>endY</code> is null.
   * @throws IndexOutOfBoundsException <code>startX</code> or <code>endX</code>
   *           or <code>startY</code> or <code>endY</code> is negative.
   * @throws IllegalArgumentException if <code>startX</code> is greater then
   *           <code>endX</code> or <code>startY</code> is greater then
   *           <code>endY</code>
   */
  public static <X extends Number, Y extends Number> CellExtent<X, Y>
    create(X startX, X endX, Y startY, Y endY)
  {
    Math.checkRange(startX, endX);
    Math.checkRange(startY, endY);
    return new CellExtent<X, Y>(startX, endX, startY, endY);
  }

  /**
   * Creates a new instance of CellExtent without checking arguments validity.
   * <p>
   * <code>startX</code>,<code>endX</code>, <code>startY</code> and
   * <code>endY</code> numbers are item indexes in the model,
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations.
   *
   * @param startX first index of the range of column items
   * @param endX last index of the range of column items
   * @param startY first index of the range of row items
   * @param endY last index of the range of row items
   * @return a new instance of this class
   */
  public static <X extends Number, Y extends Number> CellExtent<X, Y>
    createUnchecked(X startX, X endX, Y startY, Y endY)
  {
    return new CellExtent<X, Y>(startX, endX, startY, endY);
  }

  /**
   * Creates a new instance of CellExtent from start and end cells. Arguments are validated.
   *
   * @param start the top left corner of the range of cells
   * @param end the bottom right corner of the range of cells
   * @return a new instance of this class
   *
   * @throws IllegalArgumentException if <code>start</code> or <code>end</code> is null.
   * @throws IndexOutOfBoundsException indexX or indexY of <code>start</code> or
   *           <code>end</code> is negative.
   * @throws IllegalArgumentException if
   *           indexX of <code>start</code> is greater then indexX of <code>end</code> or
   *           indexY of <code>start</code> is greater then indexY of <code>end</code>
   */
  public static <X extends Number, Y extends Number> CellExtent<X, Y>
    create(Cell<X, Y> start, Cell<X, Y> end)
  {
    Preconditions.checkNotNullWithName(start, "start");
    Preconditions.checkNotNullWithName(end, "end");
    Math.checkRange(start.indexX, end.indexX);
    Math.checkRange(start.indexY, end.indexY);
    return new CellExtent<X, Y>(start, end);
  }

  /**
   * Creates a new instance of CellExtent without checking arguments validity.
   *
   * @param start the top left corner of the range of cells
   * @param end the bottom right corner of the range of cells
   * @return a new instance of this class
   */
  public static <X extends Number, Y extends Number> CellExtent<X, Y>
    createUnchecked(Cell<X, Y> start, Cell<X, Y> end)
  {
    return new CellExtent<X, Y>(start, end);
  }

  /**
   * Creates a new instance of CellExtent from X and Y extents. Arguments are validated.
   *
   * @param origin the top left corner of the range of cells
   * @param end the bottom right corner of the range of cells
   * @return a new instance of this class
   *
   * @throws IllegalArgumentException if <code>start</code> or <code>end</code> is null.
   * @throws IndexOutOfBoundsException indexX or indexY of <code>start</code> or
   *           <code>end</code> is negative.
   * @throws IllegalArgumentException if
   *           indexX of <code>start</code> is greater then indexX of <code>end</code> or
   *           indexY of <code>start</code> is greater then indexY of <code>end</code>
   */
  public static <X extends Number, Y extends Number> CellExtent<X, Y>
  create(Extent<X> extentX, Extent<Y> extentY)
  {
    Preconditions.checkNotNullWithName(extentX, "extentX");
    Preconditions.checkNotNullWithName(extentY, "extentY");
    return new CellExtent<X, Y>(extentX.start, extentX.end, extentY.start, extentY.end);
  }

  /**
   * Creates a new instance of CellExtent from X and Y extents without checking arguments validity.
   *
   * @param origin the top left corner of the range of cells
   * @param end the bottom right corner of the range of cells
   * @return a new instance of this class
   */
  public static <X extends Number, Y extends Number> CellExtent<X, Y>
  createUnchecked(Extent<X> extentX, Extent<Y> extentY)
  {
    return new CellExtent<X, Y>(extentX.start, extentX.end, extentY.start, extentY.end);
  }

  /**
   * Constructs the cell from the start indexes and end indexes.
   *
   * @param startX start cell index on the horizontal axis
   * @param endX end cell index on the horizontal axis
   * @param startY start cell index on the vertical axis
   * @param endY end cell index on the vertical axis
   */
  private CellExtent(X startX, X endX, Y startY, Y endY) {
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
  private CellExtent(Cell<X, Y> start, Cell<X, Y> end) {
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (endX == null ? 0 : endX.hashCode());
    result = prime * result + (endY == null ? 0 : endY.hashCode());
    result = prime * result + (startX == null ? 0 : startX.hashCode());
    result = prime * result + (startY == null ? 0 : startY.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) { return true; }
    if (obj == null) { return false; }
    if (!(obj instanceof CellExtent)) { return false; }
    @SuppressWarnings("rawtypes")
    CellExtent other = (CellExtent) obj;
    if (other.startX.getClass() != startX.getClass()) return false;
    if (other.startY.getClass() != startY.getClass()) return false;
    if (!endX.equals(other.endX)) { return false; }
    else if (!endY.equals(other.endY)) { return false; }
    else if (!startX.equals(other.startX)) { return false; }
    else if (!startY.equals(other.startY)) { return false; }
    return true;
  }

  @Override
  public String toString() {
    return "[" + startX + "-" + endX + ", " + startY + "-" + endY + "]";
  }



}
