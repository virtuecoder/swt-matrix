/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import static pl.netanel.swt.matrix.Math.ADJACENT_AFTER;
import static pl.netanel.swt.matrix.Math.ADJACENT_BEFORE;
import static pl.netanel.swt.matrix.Math.AFTER;
import static pl.netanel.swt.matrix.Math.BEFORE;
import static pl.netanel.swt.matrix.Math.CROSS_AFTER;
import static pl.netanel.swt.matrix.Math.CROSS_BEFORE;
import static pl.netanel.swt.matrix.Math.EQUAL;
import static pl.netanel.swt.matrix.Math.INSIDE;
import static pl.netanel.swt.matrix.Math.OVERLAP;
import static pl.netanel.swt.matrix.NumberSet.ContentChangeEvent.ADD;
import static pl.netanel.swt.matrix.NumberSet.ContentChangeEvent.REMOVE;

import java.util.ArrayList;
import java.util.Iterator;

import pl.netanel.util.ImmutableIterator;

/**
 * Stores extents of numbers ensuring they are continuous as much as possible
 * and do not overlap.
 * <p>
 * Made public prematurely to implement independent hiding for grouping.
 * The public API is not polished and may change.
 */
class NumberSet<N extends Number> implements ExtentSet<N> {

  protected Math<N> math;
	protected boolean sorted;
	ArrayList<MutableExtent<N>> items;
	protected ArrayList<MutableExtent<N>> toRemove;

	protected MutableExtent<N> modified;
	protected transient int modCount;
  final private ArrayList<ContentChangeListener<N>> listeners;
  final MutableExtent<N> tmp;


	public NumberSet(Math<N> math) {
		this(math, false);
	}

	public NumberSet(Math<N> math, boolean sorted) {
		this.sorted = sorted;
		this.math = math;
		items = new ArrayList<MutableExtent<N>>();
		toRemove = new ArrayList<MutableExtent<N>>();
		listeners = new ArrayList<ContentChangeListener<N>>();
		tmp = new MutableExtent<N>(math.create(0), math.create(0));
	};

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (MutableExtent<N> e: items) {
			if (sb.length() > 0) sb.append(", ");
			if (math.compare(e.start(), e.end()) == 0) {
				sb.append(e.start);
			} else {
				sb.append(e.start).append("-").append(e.end);
			}
		}
		return sb.toString();
	}

	public boolean contains(N n) {
	  for (MutableExtent<N> e: items) {
	    if (math.contains(e, n)) {
	      return true;
	    }
	  }
	  return false;
	}

	@Override
	public boolean contains(N start, N end) {
	  for (MutableExtent<N> e: items) {
	    if (math.contains(e.start(), e.end(), start) && math.contains(e.start(), e.end(), end)) {
	      return true;
	    }
	  }
	  return false;
	}

	public boolean contains(MutableExtent<N> extent) {
		return contains(extent.start(), extent.end());
	}


	@Override
	public boolean contains(Extent<N> extent) {
	  return contains(extent.getStart(), extent.getEnd());
	}

	/**
	 * Shortcut for <code>add(n, n)</code>.
	 * <p>
	 * Instead of adding a range of numbers one at a time it is recommended to
	 * add them with <code>add(start, end)</code> method.
	 *
	 * @param n number to be added.
	 * @return true if the receiver has been modified by this operation, or false otherwise
	 * @see #add(MutableNumber, MutableNumber)
	 */
	public boolean add(N n) {
		return add(n, n);
	}

	public boolean add(MutableExtent<N> e) {
		return add(e.start(), e.end());
	}

	public boolean add(N start, N end) {

/*
 * Cases
 *    ---	 nv
 * 1 -       nv.start > ov.end 				    	  : continue;
 * 1 -       nv.start = ov.end + 1 				      : ov.end = nv.end;
 * 2 -- 	 nv.start <= ov.end && nv.end > ov.start  : p.start = e.end++;
 * 3 -----	 nv.start >= ov.start && nv.end <= ov.end : return false;
 * 4   -	 e.start > p.start && e.end < p.end	      : insert (e.end++, e.start--); return true;
 * 5    --   e.end >= p.end && e.start <= p.end		  : insert (e.end++, p.end);
 * 6     -   nv.end < ov.start						  : insert (p.start, p.end); return true;
 */

		modified = null;
		boolean quit = false;
		int i = 0;

		for (;i < items.size(); i++) {
			MutableExtent<N> item = items.get(i);
			int compare = math.compare(item.start(), item.end(), start, end);

			switch (compare) {
			case AFTER: 		if (sorted) {quit = true; } break;
//			case BEFORE: 		break;
			case EQUAL:
			case OVERLAP:		return false;

			case CROSS_BEFORE:
			case CROSS_AFTER:
			case ADJACENT_AFTER:
			case ADJACENT_BEFORE:
			case INSIDE:
				extendItem(item, start, end);
				break;
			}
			if (quit) break;
		}
		if (modified == null) {
			items.add(i, new MutableExtent<N>(math.create(start), math.create(end)));
		}
		for (MutableExtent<N> e: toRemove) {
			items.remove(e);
		}
		toRemove.clear();
		modCount++;

		if (!listeners.isEmpty()) {
		  notify(ADD, start, end);
		}
		return true;
	}

	public boolean addAll(final ExtentSet<N> set) {
	  boolean result = false;
	  if (set instanceof NumberSet) {
      for (MutableExtent<N> e: ((NumberSet<N>) set).items) {
        result = add(e.start(), e.end()) || result;
      }
    } else {
      throw new UnsupportedOperationException();
    }
		return result;
	}


	protected void extendItem(MutableExtent<N> existing, N start, N end) {
		if (modified == null) modified = existing;
		else toRemove.add(0, existing);
		modified.start.set(math.min(start, modified.start(), existing.start()));
		modified.end.set(math.max(end, modified.end(), existing.end()));
	}



	/**
	 * Return total number of numbers in this set.
	 * @return
	 */
	public MutableNumber<N> getMutableCount() {
		MutableNumber<N> sum = math.create(0);
		for (MutableExtent<N> e: items) {
			sum.add(e.end).subtract(e.start).increment();
		}
		return sum;
	}

	/**
	 * Gets the count of indexes included in the given scope between start and end.
	 * @param start
	 * @param end
	 * @return
	 */
	public MutableNumber<N> getMutableCount(N start, N end) {
		MutableNumber<N> sum = math.create(0);
		for (MutableExtent<N> e: items) {
			switch (math.compare(e.start(), e.end(), start, end)) {
			case BEFORE:
			case ADJACENT_BEFORE:	continue;
			case AFTER:
			case ADJACENT_AFTER:	return sum;
			case CROSS_BEFORE:		sum.add(e.end).subtract(start).increment(); 	break;
			case CROSS_AFTER:		sum.add(end).subtract(e.start).increment(); 	break;
			case INSIDE:			sum.add(e.end).subtract(e.start).increment();	break;
			case EQUAL:
			case OVERLAP:			sum.add(end).subtract(start).increment(); 		break;
			}
		}
		return sum;
	}

	/**
   * Gets the count of indexes included in the given scope between start and end.
   * @param start
   * @param end
   * @return
   */
	public N getCount(N start, N end) {
	  return getMutableCount(start, end).getValue();
	}

	/**
	 * The return object is always the same with mutated values to avoid
	 * excessive object creation.
	 *
	 * @param start
	 * @param end
	 * @return mutable extent containing intersection.
	 */
	 ExtentSequence<N> intersect(N start, N end) {
//	  return new ExtentSequence<N>() {
//      private int i;
//      private MutableExtent<N> extent;
//      N start, end;
//      private int len;
//
//      @Override
//      public void init() {
//        if (items.isEmpty()) return;
//        len = items.size();
//        i = 0;
//        tmp.start.set(start);
//        tmp.end.set(end);
//      }
//
//      @Override
//      public boolean next() {
//        while (i < len) {
//          extent = items.get(0);
//
//        }
//        while (math.compare(tmp.end, extent.start) < 0 ||
//            math.compare(tmp.start, extent.end) > 0) {
//          tmp
//        }
//        return false;
//      }
//
//      @Override
//      public Iterator<Extent<N>> iterator() {
//        return null;
//      }
//
//      @Override
//      public N getStart() {
//        return null;
//      }
//
//      @Override
//      public N getEnd() {
//        return null;
//      }
//
//	  };
//	  for (MutableExtent<N> e: items) {
//	    if (math.compare(tmp.end, e.start) < 0 &&
//	        math.compare(tmp.start, e.end) > 0) {
//	      continue;
//      }
//	    else {
//	      tmp.start.max(e.start);
//	      tmp.end.min(e.end);
//	      return tmp;
//	    }
//	  }
	  return null;
	}

	/**
	 * Removes all of the elements from this set (optional operation). The set
	 * will be empty after this call returns.
	 */
	public void clear() {
		items.clear();
		modCount++;
		if (!listeners.isEmpty()) {
		  notify(REMOVE, math.ZERO_VALUE(), getMutableCount().getValue());
		}
	}

	public void replace(NumberSet<N> set) {
		items.clear();
		boolean isNotifyNeeded = listeners.isEmpty();
		for (MutableExtent<N> e: set.items) {
			items.add(new MutableExtent<N>(math.create(e.start()), math.create(e.end())));
			if (isNotifyNeeded) {
			  notify(ADD, e.start.getValue(), e.end.getValue());
			}
		}
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}


	public NumberSet<N> copy() {
		NumberSet<N> copy = new NumberSet<N>(math);
		for (MutableExtent<N> e: items) {
			copy.items.add(new MutableExtent<N>(math.create(e.start()), math.create(e.end())));
		}
		return copy;
	}

	public void change(N start, N end, boolean add) {
		if (add) {
			add(start, end);
		} else {
			remove(start, end);
		}
	}

	public int getExtentIndex(N n) {
		for (int i = 0, size = items.size(); i < size; i++) {
			MutableExtent<N> e = items.get(i);
			if (math.contains(e, n)) {
				return i;
			}
		}
		return -1;
	}

	public N firstExcluded(N n, int direction) {
//	  if (!sorted) {
//	    throw new UnsupportedOperationException();
//	  }
		N n2 = n;
		if (direction == Matrix.FORWARD) {
		  again: {
		  for (MutableExtent<N> e: items) {
		    if (math.contains(e, n2)) {
		      n2 = math.increment(e.end());
		      if (sorted) break; else break again;
		    }
		  }}
		}
		else {
		  again: {
      for (int i = items.size(); i-- > 0;) {
        MutableExtent<N> e = items.get(i);
        if (math.contains(e, n2)) {
          n2 = math.decrement(e.start());
          if (sorted) break; else break again;
        }
      }}
		}
		return n2;
	}

	public void delete(N start, N end) {
		MutableExtent.delete(math, items, start, end);
	}

	public void insert(N target, N count) {
		MutableExtent.insert(math, items, target, count);
	}



	class NumberSequence2 implements Sequence {
	  private int i, size;
	  MutableExtent<N> e;
	  MutableNumber<N> index;

	  public void init() {
	    i = 0;
	    size = items.size();
	    if (size == 0) return;
	    e = items.get(i);
	    index = math.create(e.start);
	    index.decrement();
	  }

	  public boolean next() {
	    if (size == 0) return false;
	    if (math.compare(index.increment(), e.end) > 0) {
	      if (++i >= size) return false;
	      e = items.get(i);
	      index.set(e.start);
	    }
	    return true;
	  }

	  boolean over() {
	    return i >= size - 1 && math.compare(index.increment(), e.end) >= 0;
	  }

	  /**
	   * Returns index of the current item.
	   *
	   * @return vertical axis index
	   */
	  public N index() {
	    return index.getValue();
	  }

	  boolean hasNext() {
	    return i < size - 1 || math.compare(index.increment(), e.end) < 0;
	  }
	}


	abstract class NumberCountSequence implements Sequence {
	  N origin, limit;
    private int i, size;
    MutableExtent<N> e;
    MutableNumber<N> index, remain = math.create(0);

    public void init() {
      size = items.size();
      if (size == 0) return;
      i = getExtentIndex(origin);
      e = items.get(i);
      index = math.create(origin);
      index.decrement();
      remain.set(limit) ;
    }

    public boolean next() {
      if (size == 0) return false;
      if (math.compare(remain, math.ZERO_VALUE()) == 0) return false;
      remain.decrement();
      if (math.compare(index.increment(), e.end) > 0) {
        if (++i >= size) return false;
        e = items.get(i);
        index.set(e.start);
      }
      return true;
    }

    boolean over() {
      return i >= size - 1 && math.compare(index.increment(), e.end) >= 0;
    }
  }

  class ForwardNumberCountSequence extends NumberCountSequence {

  }

  public Iterator<N> numberIterator() {
    return new ImmutableIterator<N>() {
      NumberSequence2 seq = new NumberSequence2();
      private boolean hasNext;
      {
        seq.init();
        hasNext = seq.next();
      }
      @Override
      public boolean hasNext() {
        return hasNext;
      }

      @Override
      public N next() {
        N next = hasNext ? seq.index() : null;
        hasNext = seq.next();
        return next;
      }
    };
  }

  public Iterator<Extent<N>> extentIterator() {
    return new ImmutableIterator<Extent<N>>() {
      ExtentSequence2<N> seq = new ExtentSequence2<N>(items);
      private Extent<N> next;
      private boolean hasNext;
      {
        seq.init();
        hasNext = seq.next();
      }
      @Override
      public boolean hasNext() {
        return hasNext;
      }

      @Override
      public Extent<N> next() {
        next = hasNext ? Extent.create(seq.start, seq.end) : null;
        hasNext = seq.next();
        return next;
      }
    };
  }

  private void notify(int operation, N start, N end) {
    ContentChangeEvent<N> event = new ContentChangeEvent<N>(
        operation, start, end);
    for (ContentChangeListener<N> listener: listeners) {
      listener.handle(event);
    }
  }

  static interface ContentChangeListener<N> {
    public void handle(ContentChangeEvent<N> e);
  }

  static class ContentChangeEvent<N> {
    public final static int ADD = 0;
    public final static int REMOVE = 1;

    public N start;
    public N end;
    public int operation;

    public ContentChangeEvent(int operation, N start, N end) {
      this.operation = operation;
      this.start = start;
      this.end = end;
    }
  }

  public void addListener(ContentChangeListener<N> listener) {
    listeners.add(listener);
  }

  public void removeListener(ContentChangeListener<N> listener) {
    listeners.remove(listener);
  }


  @Override
  public N getCount() {
    return getMutableCount().getValue();
  }

  @Override
  public boolean add(Extent<N> extent) {
    return add(extent.getStart(), extent.getEnd());
  }

  @Override
  public boolean remove(Extent<N> extent) {
    return remove(extent.getStart(), extent.getEnd());
  }


  /**
   * Shortcut for <code>remove(n, n)</code>.
   * <p>
   * Instead of removing a range of numbers one at a time it is recommended to
   * remove them with <code>remove(start, end)</code> method.
   *
   * @param n number to remove
   * @return true if the receiver has been modified by this operation,
   * or false otherwise
   * @see #remove(MutableNumber, MutableNumber)
   */
  public boolean remove(N n) {
    return remove(n, n);
  }

  public boolean remove(MutableExtent<N> e) {
    return remove(e.start(), e.end());
  }

  public boolean remove(N start, N end) {
    boolean modified = false;
    int i = 0;

    for (;i < items.size(); i++) {
      MutableExtent<N> item = items.get(i);

      int location = math.compare(start, end, item.start(), item.end());
      switch (location) {
      case AFTER:       continue;
      case BEFORE:
        if (sorted) i = items.size(); // Quit the loop
        break;

      case CROSS_BEFORE:
        item.start.set(end).increment();
        break;

      case CROSS_AFTER:
        item.end.set(math.max(math.decrement(start), item.start()));
        break;

      case EQUAL:
      case OVERLAP:
        toRemove.add(0, item);
        break;

      case INSIDE:
        MutableNumber<N> newEnd = item.end.copy();
        item.end.set(math.max(math.decrement(start), item.start()));
        items.add(i+1, new MutableExtent<N>(math.create(end).increment(), newEnd));
      }
      modified = location >= OVERLAP || modified;
    }
    for (MutableExtent<N> e: toRemove) {
      items.remove(e);
    }
    toRemove.clear();
    if (modified) {
      modCount++;

      notify(REMOVE, start, end);
    }

    return modified;
  }

  public boolean removeAll(ExtentSet<N> set) {
    boolean removed = false;
    if (set == this) {
      removed = !items.isEmpty();
      items.clear();
      return removed;
    }
    if (set instanceof NumberSet) {
      for (MutableExtent<N> e: ((NumberSet<N>) set).items) {
        removed = remove(e.start(), e.end()) || removed;
      }
    } else {
      throw new UnsupportedOperationException();
    }
    if (removed) modCount++;
    return removed;
  }

  @Override
  public void delete(N number) {
    delete(number, number);
  }

  @Override
  public void delete(Extent<N> extent) {
    delete(extent.getStart(), extent.getEnd());
  }


  @Override
  public Iterator<Extent<N>> getExtents(SequenceQuery<N> query) {
    return new ExtentSequence.Forward<N>(this).iterator();
  }

  @Override
  public Iterator<N> getNumbers(SequenceQuery<N> query) {
    return new NumberSequence<N>(math, new ExtentSequence.Forward<N>(this)).iterator();
  }
}
