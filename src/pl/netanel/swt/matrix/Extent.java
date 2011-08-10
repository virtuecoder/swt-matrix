package pl.netanel.swt.matrix;


/**
 * Represents an range of numbers between start and end values inclusive.
 * <p>
 * Instances of this class are immutable.
 * 
 * @param <N> specifies the indexing class for the receiver
 * 
 * @author Jacek Kolodziejczyk created 28-07-2011
 */
public class Extent<N extends Number> {
  final N start, end;

  /**
   * Creates extent of numbers between start and end values inclusively.
   * Arguments are validated.
   * <p>
   * <code>start</code> and <code>end</code>  
   * numbers are item indexes in the model, 
   * not the visual position of the item on the screen 
   * which can be altered by move and hide operations. 
   * 
   * @param start first index of the range of items  
   * @param end last index of the range of items
   * @return new instance of this class
   *   
   * @throws IllegalArgumentException if start or end is <code>null</code>
   * @throws IllegalArgumentException if start is greater then end
   * @throws IndexOutOfBoundsException if start or end is negative
   */
  public static <N extends Number> Extent<N> create(N start, N end) {
    Math.checkRange(start, end);
    return new Extent<N>(start, end);
  }
  
  /**
   * Creates extent of numbers between start and end values inclusively 
   * without arguments checking.
   * <p>
   * <code>start</code> and <code>end</code>  
   * numbers are item indexes in the model, 
   * not the visual position of the item on the screen 
   * which can be altered by move and hide operations. 
   * 
   * @param start first index of the range of items  
   * @param end last index of the range of items  
   * @return new instance of this class
   *
   */
  public static <N extends Number> Extent<N> createUnchecked(N start, N end) {
    return new Extent<N>(start, end);
  }
  
  private Extent(N start, N end) {
    this.start = start;
    this.end = end;
  }

  /**
   * Returns first index of the range of items. 
   * @return first index of the range of items
   */
  public N getStart() {
    return start;
  }

  /**
   * Returns the last index of the range of items.
   * @return the last index of the range of items
   */
  public N getEnd() {
    return end;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((end == null) ? 0 : end.hashCode());
    result = prime * result + ((start == null) ? 0 : start.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) { return true; }
    if (obj == null) { return false; }
    if (!(obj instanceof Extent)) { return false; }
    @SuppressWarnings("rawtypes")
    Extent other = (Extent) obj;
    if (other.start.getClass() != start.getClass()) return false;
    if (!end.equals(other.end)) { return false; }
    else if (!start.equals(other.start)) { return false; }
    return true;
  }

  @Override
  public String toString() {
    return start.toString() + "-" + end;
  }
}
