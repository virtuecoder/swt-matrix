package pl.netanel.swt.matrix;

/**
 * Sequence of cell extents within a single zone.
 *
 * @author jacek.p.kolodziejczyk@gmail.com
 * @created 25-05-2012
 */
public class ZoneExtentSequence<X extends Number, Y extends Number> implements Sequence {

  private final ZoneCore<X, Y> zone;

  public ZoneExtentSequence(ZoneCore<X, Y> zone) {
    this.zone = zone;
  }

  @Override
  public void init() {

  }

  @Override
  public boolean next() {
    return false;
  }

}
