package pl.netanel.swt.matrix;

import java.text.MessageFormat;


/**
 * Sequence of item extents within a single section.
 *
 * @author jacek.p.kolodziejczyk@gmail.com
 * @created 25-05-2012
 */
class SectionExtentSequenceLimitedByCount<N extends Number> implements Sequence {

  private final SectionCore<N> section;
  N start;
  N count;
  private boolean empty;
  private int i0, i;
  MutableExtent<N> extent, current; //current extent
  MutableNumber<N> remain;
  private MutableNumber<N> zero;

  public SectionExtentSequenceLimitedByCount(SectionCore<N> section, N start, N count) {
    this.section = section;
    this.start = start;
    this.count = count;
//    last = section.order.items.size() - 1;

    zero = section.math.ZERO();
    current = new MutableExtent<N>(zero, zero);
    remain = section.math.create(count);
  }

  @Override
  public void init() {
    empty = section.order.items.isEmpty() || section.math.compare(count, zero.getValue()) <= 0;
    if (empty) return;
    i0 = i = section.order.getExtentIndex(start);
    if (i == -1) empty = true;

  }

  @Override
  public boolean next() {
    if (empty) return false;
    boolean isEnd = i >= section.order.items.size();
    boolean hasRemaining = section.order.math.compare(remain, zero) > 0;
    if (isEnd && hasRemaining) {
      throw new IllegalArgumentException(MessageFormat.format("Cannot get remaining {0}", remain));
    }
    if (isEnd || !hasRemaining) return false;

    extent = section.order.items.get(i);
    extent.start.set(i==i0 ? start : extent.start.getValue());
    MutableNumber<N> c = section.math.count(extent);
    if (section.math.compare(remain, c) < 0) {
      extent.end.set(remain.add(extent.start).decrement());
      remain.set(section.math.ZERO_VALUE());
    } else {
      extent.end.set(extent.end);
      remain.subtract(c);
    }
    i++;
    return true;
//    return i++ < section.order.items.size() && section.order.math.compare(remain, zero) == 0;
  }

}
