package pl.netanel.swt.matrix;

import static pl.netanel.swt.matrix.Math.ADJACENT_BEFORE;
import static pl.netanel.swt.matrix.Math.BEFORE;
import static pl.netanel.swt.matrix.Math.CROSS_AFTER;
import static pl.netanel.swt.matrix.Math.CROSS_BEFORE;


class NumberOrder<N extends Number> extends NumberSet<N> {

	private N count;
  SpanExtentSequence spanExtents;
  private ExtentCountSequence countForward, countBackward;

	public NumberOrder(Math<N> math) {
		super(math);
		count = math.ZERO_VALUE();
		spanExtents = new SpanExtentSequence();
		countForward = new ExtentCountForwardSequence();
		countBackward = new ExtentCountBackwardSequence();
	}

	public void setCount(N newCount) {
		int compare = math.compare(newCount, count);
		N last = math.decrement(count);
		if (compare < 0) {
			remove(newCount, last);
		}
		else if (compare > 0) {
			MutableNumber<N> newLast = math.create(newCount).decrement();
			if (items.isEmpty()) {
				items.add(new MutableExtent<N>(math.create(count), newLast));
			} else {
				MutableExtent<N> e = items.get(items.size() - 1);
				if (math.compare(e.end(), last) == 0) {
					e.end.set(newLast);
				} else {
					items.add(new MutableExtent<N>(math.create(count), newLast));
				}
			}
		} else {
			return;
		}
		count = newCount;
	}

	/**
	 * Adds indexes in a sequential order
	 */
	@Override
	public boolean add(N index) {
	  if (!items.isEmpty()) {
	    MutableExtent<N> e = items.get(items.size() - 1);
	    if (math.compare(index, math.increment(e.end())) == 0) {
	      e.end.set(index);
	      return true;
	    }
	  }
    items.add(new MutableExtent<N>(math.create(index), math.create(index)));
	  return true;
	}

	/**
	 * Adds extents in a sequential order
	 */
	@Override
	public boolean add(N start, N end) {
	  items.add(new MutableExtent<N>(math.create(start), math.create(end)));
	  return true;
	}

	public void move(N start, N end, N target) {
		// Adjust the target if subject contains it
		if (math.compare(start, target) <= 0 && math.compare(target, end) <= 0) {
//			target = math.increment(end);
			return;
		}
//		else {
//			target = math.create(target);
//		}

		remove(start, end);

		// Find the position i to insert
		int i = 0;
		for (; i < items.size(); i++) {
			MutableExtent<N> e = items.get(i);
			if (math.contains(e.start(), e.end(), target)) {
				if (math.compare(target, e.start()) == 0) {
					break;
				}
				else {
					MutableNumber<N> end2 = math.create(e.end);
					e.end.set(target).decrement();
					items.add(++i, new MutableExtent<N>(math.create(target), end2));
					break;
				}
			}
		}

		items.add(i, new MutableExtent<N>(math.create(start), math.create(end)));
		mergeAdjacentExtents();
	}

	/**
	 * Moves the given set of numbers before the given index
	 * order: 0 1 2 3 4, move(set(1 3), 1) -> 0 1 3 2 4
	 * @param set
	 * @param target
	 */
	public void move(NumberSet<N>set, N target) {
		N target2 = set.firstExcluded(target);
		assert !set.contains(target2);

		removeAll(set);

		// Find the position i to insert
		int i = 0;
		for (; i < items.size(); i++) {
			MutableExtent<N> e = items.get(i);
			if (math.contains(e.start(), e.end(), target2)) {
				if (math.compare(target2, e.start()) == 0) {
					break;
				}
				else {
					MutableNumber<N> end2 = math.create(e.end);
					e.end.set(target2).decrement();
					items.add(++i, new MutableExtent<N>(math.create(target2), end2));
					break;
				}
			}
		}

		for (MutableExtent<N> e: set.items) {
			items.add(i++, new MutableExtent<N>(math.create(e.start), math.create(e.end)));
		}
		mergeAdjacentExtents();
	}

	private void mergeAdjacentExtents() {
		for (int i = items.size(); i-- > 1;) {
			MutableExtent<N> e1 = items.get(i-1);
			MutableExtent<N> e2 = items.get(i);
			if (math.compare(math.increment(e1.end()), e2.start()) == 0) {
				e1.end.set(e2.end);
				items.remove(i);
			}
		}
	}

	public N indexOf(N modelIndex) {
//		System.out.println(modelIndex.getClass());
		if (math.compare(modelIndex, count) >= 0 ||
			math.compare(modelIndex, math.ZERO_VALUE()) < 0) return modelIndex;

		MutableNumber<N> sum = math.create(0);
		for (MutableExtent<N> e: items) {
			if (math.contains(e.start(), e.end(), modelIndex)) {
				return sum.add(modelIndex).subtract(e.start).getValue();
			}
			sum.add(e.end).subtract(e.start).increment();
		}
		throw new RuntimeException("Cannot find index of " + modelIndex);
	}

	// TODO Merge inserted extent with the next one
	@Override
  public void insert(N target, N count) {
		int imax = items.size();
		if (imax == 0) {
			items.add(new MutableExtent<N>(math.create(0), math.create(0)));
		} else {
			for (int i = 0; i < imax; i++) {
				MutableExtent<N> e = items.get(i);
				int compare = math.compare(target, e.start());
				if (compare < 0) {
					e.start.add(count);
					e.end.add(count);
				}
				else if (math.compare(target, math.increment(e.end())) <= 0) {
					e.end.add(count);
				}
			}
		}
//		if (position != -1) {
//			items.add(position, new Extent(math.create(target), math.create(target).add(count).decrement()));
//		}
	};

	public void insert2(N target, N count) {
		MutableNumber<N> mutableEnd = math.create(target).add(count).decrement();
		N tstart = target;
		N tend = mutableEnd.getValue();
		int i = 0, imax = items.size();
		for (; i < imax; i++) {
			MutableExtent<N> e = items.get(i);

			N start = e.start(), end = e.end();
			int compare = math.compare(tstart, tend, start, end);
			switch (compare) {
			case BEFORE: case ADJACENT_BEFORE:
				e.start.add(count);
				e.end.add(count);
				break;

			case CROSS_AFTER:
				MutableNumber<N> delta = math.create(tend).subtract(start).increment();
				e.start.add(delta);
				e.end.add(delta);
				break;

			case CROSS_BEFORE:
				delta = math.create(tend).subtract(start).increment();
				e.start.add(delta);
				e.end.add(delta);
				break;
			}
			if (math.compare(target, start) <= 0) {
			}
			else if (math.compare(target, e.end()) <= 0) {
				if (math.compare(target, start) == 0) {

					e.start.set(math.increment(target));
				}
				e.end.add(count);
			}
			items.add(i, new MutableExtent<N>(math.create(target), mutableEnd));
		}
	};

	@Override
  public NumberOrder<N> copy() {
    NumberOrder<N> copy = new NumberOrder<N>(math);
    for (MutableExtent<N> e: items) {
      copy.items.add(new MutableExtent<N>(math.create(e.start()), math.create(e.end())));
    }
    return copy;
  }


	public SpanExtentSequence getSpanExtents(MutableExtent<N> extent) {
	  spanExtents.configure(extent.start.getValue(), extent.end.getValue());
	  return spanExtents;
	}

	class SpanExtentSequence implements Sequence {
	  N start;
	  N limit;
	  MutableExtent<N> current;

	  private boolean over;
	  private int i0, i;
	  private MutableExtent<N> extent;
	  private MutableNumber<N> remain;

	  public SpanExtentSequence configure(N start, N limit) {
	    this.start = start;
	    this.limit = limit;

	    current = new MutableExtent<N>(math.create(0), math.create(0));
	    remain = math.create(limit);
	    return this;
	  }

	  @Override
	  public void init() {
	    over = true;
	    //  limit is <= 0
	    if (math.compare(limit, math.ZERO_VALUE()) <= 0) return;

	    i0 = i = getExtentIndex(start);
	    if (i != -1) over = false;
	  }

	  @Override
	  public boolean next() {
	    if (over) return false;

	    extent = items.get(i);
	    current.start.set(i==i0 ? start : extent.start.getValue());
	    current.end.set(extent.end);

	    MutableNumber<N> c = math.count(current);
	    if (math.compare(remain, c) < 0) {
	      current.end.set(remain.add(current.start).decrement());
	      remain.set(math.ZERO_VALUE());
	    } else {
	      remain.subtract(c);
	    }
	    i++;
	    boolean isEnd = i >= items.size();
	    boolean hasRemaining = math.compare(remain, math.ZERO()) > 0;
//	    if (isEnd && hasRemaining) {
//	      throw new IllegalArgumentException(MessageFormat.format("Cannot get remaining {0}", remain));
//	    }
	    over = isEnd || !hasRemaining;
	    return true;
//	    return i++ < items.size() && math.compare(remain, zero) == 0;
	  }

	  public boolean over() {
      return over;
    }

    public boolean contains(N index) {
	    for (init(); next();) {
	      if (math.contains(current.start(), current.end(), index)) return true;
	    }
	    return false;
	  }

    public boolean contains(N start, N count) {
      SpanExtentSequence seq = new SpanExtentSequence();
      seq.configure(start, count);
      for (init(); next();) {
        for (seq.init(); seq.next();) {
          if (!math.areExclusive(current, seq.current)) return true;
        }
      }
      return false;
    }
	}

	public N getIndexByOffset(N start, N count) {
	  ExtentCountSequence seq;
    if (math.compare(count, math.ZERO_VALUE()) >= 0) {
      seq = countForward;
      seq.limit = count;
    } else {
      seq = countBackward;
      seq.limit = math.negate(count);
    }
    seq.origin = start;
	  for (seq.init(); seq.next(););
	  return seq.index == null ? null : seq.index.getValue();
	}

	public abstract class ExtentCountSequence implements Sequence {
    N origin, limit;
    int i;
    MutableExtent<N> extent;
    MutableNumber<N> index;
    MutableNumber<N> remain = math.create(0);
    MutableNumber<N> diff = math.create(0);

    @Override
    public void init() {
      index = math.create(origin);
      remain.set(limit);
      i = getExtentIndex(origin);
      extent = items.get(i);
    }
	}

	public class ExtentCountForwardSequence extends ExtentCountSequence {
	  @Override
    public void init() {
      super.init();
      //remaining.set(limit).add(origin).subtract(extent.start);
    }
    @Override
    public boolean next() {
      if (math.compare(remain, math.ZERO()) <= 0) {
        return false;
      }

      diff.set(extent.end).subtract(index);
      if (math.compare(diff, remain) >= 0) {
        index.add(remain);
        remain.set(math.ZERO_VALUE());
        return true;
      }
      if (++i >= items.size()) {
        index = null;
        return false;
      }
      extent = items.get(i);
      index.set(extent.start);
      remain.subtract(diff).decrement();
      return true;
    }
	}

	public class ExtentCountBackwardSequence extends ExtentCountSequence {
	   @Override
	    public void init() {
	      super.init();
	      remain.set(limit).add(extent.start).subtract(origin);
	    }

	  @Override
	  public boolean next() {
	    if (math.compare(remain, math.ZERO_VALUE()) <= 0) {
	      index.set(extent.start).subtract(remain).getValue();
	      if (math.compare(index, math.ZERO_VALUE()) < 0) {
	        index = null;
	      }
	      return false;
	    }
	    if (i <= 0) {
	      index = null;
	      return false;
	    }
	    extent = items.get(--i);
	    remain.add(extent.start).subtract(extent.end).decrement();
	    return true;
	  }
	}
}
