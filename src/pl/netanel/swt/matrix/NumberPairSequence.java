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
 * 
 * @author Jacek created 21-02-2011
 */
class NumberPairSequence<X extends Number, Y extends Number> implements Sequence {
	CellSet<X, Y> set;
	int i, size;
	Extent<Y> e0;
	Extent<X> e1;
	MutableNumber<Y> indexY;
	MutableNumber<X> indexX;
	
	
	NumberPairSequence(CellSet set) {
		this.set = set;
	}
	
	public void init() {
		i = 0;
		size = set.size();
		if (size == 0) return;
		e0 = set.itemsY.get(i);
		e1 = set.itemsX.get(i);
		indexY = set.mathY.create(e0.start);
		indexX = set.mathX.create(e1.start);
		indexX.decrement();
	}
	
	/**
	 * Returns true if the next iteration step succeeded, false otherwise. 
	 * Another words false return value means the iteration has reached the end.   
	 * @return
	 */
	public boolean next() {
		if (size == 0) return false;
		if (set.mathX.compare(indexX.increment().getValue(), e1.end()) > 0) {
			if (set.mathY.compare(indexY.increment().getValue(), e0.end()) > 0) {
				if (++i >= size) return false;
				e0 = set.itemsY.get(i);
				e1 = set.itemsX.get(i);
				indexY.set(e0.start);
			}
			indexX.set(e1.start);
		}
		return true;
	}
	
	/**
	 * Returns row axis index of the current cell. 
	 * 
	 * @return row axis index
	 */
	public Y indexY() {
		return indexY.getValue();
	}

	/**
	 * Return column axis index of the current cell. 
	 * 
	 * @return column axis index 
	 */
	public X indexX() {
		return indexX.getValue();
	}
	
	
	public boolean nextExtent() {
		if (i++ >= size) return false;
		e0 = set.itemsY.get(i-1);
		e1 = set.itemsX.get(i-1);
		indexY = set.mathY.create(e0.start);
		indexX = set.mathX.create(e1.start);
		indexX.decrement();
		return true;
	}
	
	public Y startY() {
		return e0.start();
	}
	public Y endY() {
		return e0.end();
	}
	public X startX() {
		return e1.start();
	}
	public X endX() {
		return e1.end();
	}
}
