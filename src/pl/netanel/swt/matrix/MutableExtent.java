/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import static pl.netanel.swt.matrix.Math.ADJACENT_AFTER;
import static pl.netanel.swt.matrix.Math.AFTER;
import static pl.netanel.swt.matrix.Math.CROSS_AFTER;
import static pl.netanel.swt.matrix.Math.CROSS_BEFORE;
import static pl.netanel.swt.matrix.Math.EQUAL;
import static pl.netanel.swt.matrix.Math.INSIDE;
import static pl.netanel.swt.matrix.Math.OVERLAP;

import java.util.ArrayList;

import pl.netanel.util.IntArray;


class MutableExtent<N extends Number> {
	MutableNumber<N> start, end;

	public MutableExtent(MutableNumber<N> start, MutableNumber<N> end) {
		super();
		this.start = start;
		this.end = end;
	}

	@Override
	public String toString() {
		return start.toString() + "-" + end.toString();
	}

	public N start() {
		return start.getValue();
	}

	public N end() {
		return end.getValue();
	}

	/**
	 * arguments: 0 2
	 * extent:        4 6 -> e.start -= p.count; e.end -= p.count
	 * arguments: 0 2
	 * extent:      2 4   -> e.start = end + 1;
	 * arguments: 0 2
	 * extent:      2 4   -> e.start = end + 1;
	 * arguments:  12
	 * extent:    0  3    -> add(end+1, e.end); e.end = end + 1;
	 * arguments:  1 3
	 * extent:    0 2     -> e.end = start - 1;
	 * @return
	 * */
	static <N extends Number> IntArray delete(Math<N> math, ArrayList<MutableExtent<N>> list, N start, N end) {
		IntArray toRemove = new IntArray();
		for (int i = 0, imax = list.size(); i < imax; i++) {
			MutableExtent<N> e = list.get(i);
			int compare = math.compare(e.start(), e.end(), start, end);
			switch (compare) {
			case AFTER:
			case ADJACENT_AFTER:
				MutableNumber<N> count = math.create(end).subtract(start).increment();
				e.start.subtract(count);
				e.end.subtract(count);
				break;

			case CROSS_AFTER:
				e.start.set(start);
				e.end.set(end).subtract(e.start).add(start).decrement();
				break;

			case CROSS_BEFORE:
			case OVERLAP:
				e.end.subtract(math.min(end, e.end())).add(start).decrement();
				break;

			case INSIDE:
			case EQUAL:
				toRemove.add(i); break;
			}
		}
		toRemove.sortDescending();
		for (int i = 0, imax = toRemove.size(); i < imax; i++) {
			list.remove(toRemove.get(i));
		}
		return toRemove;
	}
	
	static <N extends Number> IntArray deleteSpan(Math<N> math, ArrayList<MutableExtent<N>> list, N start, N end) {
	  IntArray toRemove = new IntArray();
	  for (int i = 0, imax = list.size(); i < imax; i++) {
	    MutableExtent<N> e = list.get(i);
	    int compare = math.compare(e.start(), e.end(), start, end);
	    switch (compare) {
	    case AFTER:
	    case ADJACENT_AFTER:
	      MutableNumber<N> count = math.create(end).subtract(start).increment();
	      e.start.subtract(count);
	      break;
	      
	    case CROSS_AFTER:
	      e.start.subtract(math.create(e.start).subtract(start));
	      e.end.set(end).subtract(e.start).add(start).decrement();
	      break;
	      
	    case CROSS_BEFORE:
	    case OVERLAP:
	      e.end.subtract(math.min(end, e.end())).add(start).decrement();
	      break;
	      
	    case INSIDE:
	    case EQUAL:
	      toRemove.add(i); break;
	    }
	  }
	  toRemove.sortDescending();
	  for (int i = 0, imax = toRemove.size(); i < imax; i++) {
	    list.remove(toRemove.get(i));
	  }
	  return toRemove;
	}

	static <N extends Number> void insert(Math<N> math, ArrayList<MutableExtent<N>> list, N target, N count) {
		for (int i = 0, imax = list.size(); i < imax; i++) {
			MutableExtent<N> e = list.get(i);

			if (math.compare(target, e.start()) <= 0) {
				e.start.add(count);
				e.end.add(count);
			}
			else if (math.compare(target, e.end()) <= 0) {
				e.end.add(count);
			}
		}
	}
	
	static <N extends Number> void insertSpan(Math<N> math, ArrayList<MutableExtent<N>> list, N target, N count) {
	  for (int i = 0, imax = list.size(); i < imax; i++) {
	    MutableExtent<N> e = list.get(i);
	    
	    if (math.compare(target, e.start()) <= 0) {
	      e.start.add(count);
	    }
	  }
	}

  public MutableExtent<N> copy() {
    return new MutableExtent<N>(start.copy(), end.copy());
  }

}
