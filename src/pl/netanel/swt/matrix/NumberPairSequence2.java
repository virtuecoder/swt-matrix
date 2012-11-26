/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA.html
 ******************************************************************************/
package pl.netanel.swt.matrix;


/**
 * Iterates over a set of number pairs.
 * <p>
 * Two modes of iteration are possible by single numbers and by extents of numbers.
 * <p>
 * Example usage: <pre>
 * NumberPairSequence seq = zone.getSelected();
 * // single pair iteration
 * for (seq.init(); seq.next();) {
 *     System.out.println(
 *         seq.indexY() + " : " + seq.indexX());
 * }
 * // pair extents iteration
 * for (seq.init(); seq.nextExtent();) {
 *     System.out.println(
 *         seq.startY() + "-" + seq.endY() + " : " +
 *         seq.startX() + "-" + seq.endX());
 * }
 * </pre>
 */
class NumberPairSequence2<X extends Number, Y extends Number> implements Sequence {
	CellSet<X, Y> set;
	int i, size;
	MutableExtent<Y> ey;
	MutableExtent<X> ex;
	MutableNumber<Y> indexY;
	MutableNumber<X> indexX;


	NumberPairSequence2(CellSet<X, Y> set) {
		this.set = set;
	}

	public void init() {
		i = 0;
		size = set.size();
		if (size == 0) return;
		ey = set.itemsY.get(i);
		ex = set.itemsX.get(i);
		indexY = set.mathY.create(ey.start);
		indexX = set.mathX.create(ex.start);
		indexX.decrement();
	}

	/**
	 * Returns true if the next iteration step succeeded, false otherwise.
	 * Another words false return value means the iteration has reached the end.
	 * @return
	 */
	public boolean next() {
		if (size == 0) return false;
		if (set.mathX.compare(indexX.increment().getValue(), ex.end()) > 0) {
			if (set.mathY.compare(indexY.increment().getValue(), ey.end()) > 0) {
				if (++i >= size) return false;
				ey = set.itemsY.get(i);
				ex = set.itemsX.get(i);
				indexY.set(ey.start);
			}
			indexX.set(ex.start);
		}
		return true;
	}

	/**
	 * Returns vertical axis index of the current cell.
	 *
	 * @return vertical axis index
	 */
	public Y indexY() {
		return indexY.getValue();
	}

	/**
	 * Return horizontal axis index of the current cell.
	 *
	 * @return horizontal axis index
	 */
	public X indexX() {
		return indexX.getValue();
	}


	public boolean nextExtent() {
		if (i++ >= size) return false;
		ey = set.itemsY.get(i-1);
		ex = set.itemsX.get(i-1);
		indexY = set.mathY.create(ey.start);
		indexX = set.mathX.create(ex.start);
		indexX.decrement();
		return true;
	}

	public Y startY() {
		return ey.start();
	}
	public Y endY() {
		return ey.end();
	}
	public X startX() {
		return ex.start();
	}
	public X endX() {
		return ex.end();
	}
}
