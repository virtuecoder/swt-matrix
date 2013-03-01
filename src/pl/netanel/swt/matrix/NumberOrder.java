/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA.html
 ******************************************************************************/
package pl.netanel.swt.matrix;


class NumberOrder<N extends Number> extends NumberSetCore<N> {

	private MutableNumber<N> count;
  SpanExtentSequence spanExtents;
  ExtentOriginLimitSequence countForward, countBackward;
  ForwardExtentFirstLastSequence untilForward;

	public NumberOrder(Math<N> math) {
		super(math, false);
		count = math.create(0);
		spanExtents = new SpanExtentSequence();
		countForward = new ForwardExtentOriginLimitSequence();
		countBackward = new BackwardExtentOriginLimitSequence();
		untilForward = new ForwardExtentFirstLastSequence();
	}

	public void setCount(N newCount) {
		int compare = math.compare(newCount, count.getValue());
		N last = math.decrement(count.getValue());
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
		count.set(newCount);
	}

	@Override
  public MutableNumber<N> getMutableCount() {
	  return count;
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
	public void move(NumberSetCore<N>set, N target) {
		N target2 = set.firstExcluded(target, Matrix.FORWARD);
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
		if (math.compare(modelIndex, count.getValue()) >= 0 ||
			math.compare(modelIndex, math.ZERO_VALUE()) < 0) return modelIndex;

		MutableNumber<N> sum = math.create(0);
		for (MutableExtent<N> e: items) {
			if (math.contains(e.start(), e.end(), modelIndex)) {
				return sum.add(modelIndex).subtract(e.start).getValue();
			}
			sum.add(e.end).subtract(e.start).increment();
		}
		return null;
	}

	// TODO Merge inserted extent with the next one
	@Override
  public void insert(N target, N count) {
		int imax = items.size();
		if (imax == 0) {
			items.add(new MutableExtent<N>(math.create(0), math.create(count).decrement()));
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
		this.count.add(count);
//		if (position != -1) {
//			items.add(position, new Extent(math.create(target), math.create(target).add(count).decrement()));
//		}
	};

	@Override
  public void delete(N start, N end) {
	  super.delete(start, end);
	  count.subtract(end).add(start).decrement();
	};

//	public void insert2(N target, N count) {
//		MutableNumber<N> mutableEnd = math.create(target).add(count).decrement();
//		N tstart = target;
//		N tend = mutableEnd.getValue();
//		int i = 0, imax = items.size();
//		for (; i < imax; i++) {
//			MutableExtent<N> e = items.get(i);
//
//			N start = e.start(), end = e.end();
//			int compare = math.compare(tstart, tend, start, end);
//			switch (compare) {
//			case BEFORE: case ADJACENT_BEFORE:
//				e.start.add(count);
//				e.end.add(count);
//				break;
//
//			case CROSS_AFTER:
//				MutableNumber<N> delta = math.create(tend).subtract(start).increment();
//				e.start.add(delta);
//				e.end.add(delta);
//				break;
//
//			case CROSS_BEFORE:
//				delta = math.create(tend).subtract(start).increment();
//				e.start.add(delta);
//				e.end.add(delta);
//				break;
//			}
//			if (math.compare(target, start) <= 0) {
//			}
//			else if (math.compare(target, e.end()) <= 0) {
//				if (math.compare(target, start) == 0) {
//
//					e.start.set(math.increment(target));
//				}
//				e.end.add(count);
//			}
//			items.add(i, new MutableExtent<N>(math.create(target), mutableEnd));
//		}
//	};

	@Override
  public NumberOrder<N> copy() {
    NumberOrder<N> copy = new NumberOrder<N>(math);
    for (MutableExtent<N> e: items) {
      copy.items.add(new MutableExtent<N>(math.create(e.start()), math.create(e.end())));
    }
    copy.count.set(count);
    return copy;
  }


	public SpanExtentSequence getSpanExtents(MutableExtent<N> extent) {
	  spanExtents.configure(extent.start.getValue(), extent.end.getValue());
	  return spanExtents;
	}

	class SpanExtentSequence implements Sequence {
	  N origin;
	  N limit;
	  MutableExtent<N> current;

	  private boolean over;
	  private int i0, i;
	  private MutableExtent<N> extent;
	  private MutableNumber<N> remain;

	  public SpanExtentSequence configure(N origin, N limit) {
	    this.origin = origin;;
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

	    i0 = i = getExtentIndex(origin);
	    if (i != -1) over = false;
	  }

	  @Override
	  public boolean next() {
	    if (over) return false;

	    extent = items.get(i);
	    current.start.set(i==i0 ? origin : extent.start.getValue());
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
	  ExtentOriginLimitSequence seq;
    if (math.compare(count, math.ZERO_VALUE()) >= 0) {
      seq = countForward;
      seq.limit = math.increment(count);
    } else {
      seq = countBackward;
      seq.limit = math.negate(math.increment(count));
    }
    seq.origin = start;
    N last = null;
	  for (seq.init(); seq.next();) {
	    last = seq.end == null ? null : seq.end.getValue();
	  }
	  return math.compare(seq.remain, math.ZERO_VALUE()) >= 0 ? null : last;
	}

	public N getIndex(N position) {
	  ExtentOriginLimitSequence seq = countForward;
	  seq.limit = math.increment(count);
	  N last = null;
    for (seq.init(); seq.next();) {
      last = seq.end == null ? null : seq.end.getValue();
    }
    return math.compare(seq.remain, math.ZERO_VALUE()) >= 0 ? null : last;
	}

	public boolean overlap(N origin, N limit, MutableExtent<N> extent) {
    ExtentOriginLimitSequence seq = countForward;
    seq.origin = origin;
    seq.limit = limit;
    boolean notExclusive = false;
    for (seq.init(); seq.next();) {
      if (!math.areExclusive(extent.start, extent.end, seq.start, seq.end)) {
        notExclusive = true;
        // don't break to catch seq.end
      }
    }
    if (notExclusive) {
      if (math.compare(indexOf(origin), extent.start.getValue()) < 0) {
        extent.start.set(origin);
      }
      if (math.compare(indexOf(seq.end.getValue()), extent.end.getValue()) > 0) {
        extent.end.set(seq.end.getValue());
      }
    }
    return notExclusive;
  }

	public abstract class ExtentOriginLimitSequence implements Sequence {
    N origin, limit;
    int i;
    MutableExtent<N> extent;
    MutableNumber<N> start, end;
    MutableNumber<N> remain = math.create(0);
    MutableNumber<N> diff = math.create(0);
    boolean isStarted, isEnded;

    @Override
    public void init() {
      isStarted = isEnded = false;
      i = getExtentIndex(origin);
      if (i == -1) {
        isEnded = true;
        return;
      }
      remain.set(limit).decrement();
      start = math.create(origin);
      end = math.create(origin);
      i--;
    }

    public void init(N origin, N limit) {
      this.origin = origin;
      this.limit = limit;
      init();
    }
	}

	/*
   * Sequence sample:
   * origin end1
   * start2 end2
   * start3 limit
   */
	public class ForwardExtentOriginLimitSequence extends ExtentOriginLimitSequence {
    @Override
    public boolean next() {
      if (isEnded || ++i >= items.size()) {
        return false;
      }
      extent = items.get(i);

      if (isStarted) {
        start.set(extent.start);
      }
      else {
        isStarted = true;
      }

      diff.set(extent.end).subtract(start);

      if (math.compare(diff, remain) >= 0) {
        end.set(start).add(remain);
        isEnded = true;
      } else {
        end.set(extent.end);
      }

      remain.subtract(diff).decrement();
      return true;
    }
	}

	/**
	 * Does not work
	 */
	public class BackwardExtentOriginLimitSequence extends ExtentOriginLimitSequence {
	   @Override
	    public void init() {
	      super.init();
	      throw new UnsupportedOperationException("Not completed yet");
//	      remain.set(limit).add(extent.start).subtract(origin);
	    }

	  @Override
	  public boolean next() {
	    end.set(extent.end);
	    if (math.compare(remain, math.ZERO_VALUE()) <= 0) {
	      start.set(extent.start).subtract(remain).getValue();
	      if (math.compare(start, math.ZERO_VALUE()) < 0) {
	        start = null;
	      }
	      return false;
	    }
	    if (i <= 0) {
	      start = null;
	      end = null;
	      return false;
	    }
	    extent = items.get(--i);
	    start.set(extent.start);
	    end.set(extent.start);
	    remain.add(extent.start).subtract(extent.end).decrement();
	    return true;
	  }
	}


	/*
   * Sequence sample:
   * origin end1
   * start2 end2
   * start3 limit
   */
  public class ForwardExtentFirstLastSequence implements Sequence {
    N first, last;
    int i;
    MutableExtent<N> extent;
    MutableNumber<N> start, end;
    boolean isStarted, isEnded;

    @Override
    public void init() {
      isStarted = isEnded = false;
      i = getExtentIndex(first);
      if (i == -1) {
        isEnded = true;
        return;
      }
      start = math.create(first);
      end = math.create(first);
      i--;
    }

    public void init(N first, N last) {
      this.first = first;
      this.last = last;
      init();
    }

    @Override
    public boolean next() {
      if (isEnded || ++i >= items.size()) {
        return false;
      }
      extent = items.get(i);

      if (isStarted) {
        start.set(extent.start);
      }
      else {
        isStarted = true;
      }

      if (math.contains(extent, last)) {
        end.set(last);
        isEnded = true;
      } else {
        end.set(extent.end);
      }

      return true;
    }
  }
}
