package pl.netanel.swt.matrix;

/**
 * Sequence of item extents within a single section.
 *
 * @author jacek.p.kolodziejczyk@gmail.com
 * @created 25-05-2012
 */
class SpanExtentSequence<N extends Number> implements Sequence {

  private final NumberOrder<N> set;
  N start;
  N limit;
  MutableExtent<N> current;

  private boolean over;
  private int i0, i;
  private MutableExtent<N> extent;
  private MutableNumber<N> remain;

  public SpanExtentSequence(NumberOrder<N> set, N start, N limit) {
    this.set = set;
    this.start = start;
    this.limit = limit;
//    last = set.items.size() - 1;

    current = new MutableExtent<N>(set.math.create(0), set.math.create(0));
    remain = set.math.create(limit);
  }

  @Override
  public void init() {
    over = true;
    //  limit is <= 0
    if (set.math.compare(limit, set.math.ZERO_VALUE()) <= 0) return;

    i0 = i = set.getExtentIndex(start);
    if (i != -1) over = false;
  }

  @Override
  public boolean next() {
    if (over) return false;

    extent = set.items.get(i);
    current.start.set(i==i0 ? start : extent.start.getValue());
    current.end.set(extent.end);

    MutableNumber<N> c = set.math.count(current);
    if (set.math.compare(remain, c) < 0) {
      current.end.set(remain.add(current.start).decrement());
      remain.set(set.math.ZERO_VALUE());
    } else {
      remain.subtract(c);
    }
    i++;
    boolean isEnd = i >= set.items.size();
    boolean hasRemaining = set.math.compare(remain, set.math.ZERO()) > 0;
//    if (isEnd && hasRemaining) {
//      throw new IllegalArgumentException(MessageFormat.format("Cannot get remaining {0}", remain));
//    }
    over = isEnd || !hasRemaining;
    return true;
//    return i++ < set.items.size() && set.math.compare(remain, zero) == 0;
  }

  public boolean contains(N index) {
    for (init(); next();) {
      if (set.math.contains(current.start(), current.end(), index)) return true;
    }
    return false;
  }

  public boolean over() {
    return over;
  }

}
