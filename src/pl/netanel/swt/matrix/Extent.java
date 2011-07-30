package pl.netanel.swt.matrix;

/**
 * Represents an extent of numbers between start and end values inclusive.
 * <p>
 * Instances of this class are immutable.
 * 
 * @author Jacek Kolodziejczyk created 28-07-2011
 * @param <N>
 */
public class Extent<N extends Number> {
  final N start, end;

  public Extent(N start, N end) {
    super();
    this.start = start;
    this.end = end;
  }

  public N getStart() {
    return start;
  }

  public N getEnd() {
    return end;
  }
  
  
}
