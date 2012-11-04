/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import java.util.List;


/**
 * Iterate over a set of numbers.
 * <p>
 * Example usage: <pre>
 * NumberSequence seq = section.getSelected();
 * // single number iteration
 * for (seq.init(); seq.next();) {
 *     System.out.println(seq.index());
 * }
 * </pre>
 *
 * @author Jacek created 21-02-2011
 */
class NumberSequence<N extends Number> implements Sequence {
	int i, size;
	MutableExtent<N> e;
	MutableNumber<N> index;
  private final Math<N> math;
  private final List<MutableExtent<N>> items;


	NumberSequence(NumberSet<N> set) {
		this(set.math, set.items);
	}

	public NumberSequence(Math<N> math, List<MutableExtent<N>> items) {
    this.math = math;
    this.items = items;

  }

  public void init() {
		i = 0;
		size = items.size();
		if (size == 0) return;
		e = items.get(i);
		index = math.create(e.start);
		index.decrement();
	}

	/**
	 * Returns true if the next iteration step succeeded, false otherwise.
	 * Another words false return value means the iteration has reached the end.
	 * @return
	 */
	public boolean next() {
		if (size == 0) return false;
		if (math.compare(index.increment(), e.end) > 0) {
			if (++i >= size) return false;
			e = items.get(i);
			index.set(e.start);
		}
		return true;
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
