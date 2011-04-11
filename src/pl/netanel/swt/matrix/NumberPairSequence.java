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
 *         seq.index0() + " : " + seq.index1());
 * }
 * // pair extents iteration
 * for (seq.init(); seq.nextExtent();) {
 *     System.out.println(
 *         seq.start0() + "-" + seq.end0() + " : " +
 *         seq.start1() + "-" + seq.end1());
 * }
 * </pre>
 * 
 * @author Jacek created 21-02-2011
 */
public class NumberPairSequence<N0 extends Number, N1 extends Number> implements Sequence {
	CellSet<N0, N1> set;
	int i, size;
	Extent<N0> e0;
	Extent<N1> e1;
	MutableNumber<N0> index0;
	MutableNumber<N1> index1;
	
	
	NumberPairSequence(CellSet set) {
		this.set = set;
	}
	
	public void init() {
		i = 0;
		size = set.size();
		if (size == 0) return;
		e0 = set.items0.get(i);
		e1 = set.items1.get(i);
		index0 = set.math0.create(e0.start);
		index1 = set.math1.create(e1.start);
		index1.decrement();
	}
	
	/**
	 * Returns true if the next iteration step succeeded, false otherwise. 
	 * Another words false return value means the iteration has reached the end.   
	 * @return
	 */
	public boolean next() {
		if (size == 0) return false;
		if (set.math1.compare(index1.increment().getValue(), e1.end()) > 0) {
			if (set.math0.compare(index0.increment().getValue(), e0.end()) > 0) {
				if (++i >= size) return false;
				e0 = set.items0.get(i);
				e1 = set.items1.get(i);
				index0.set(e0.start);
			}
			index1.set(e1.start);
		}
		return true;
	}
	
	/**
	 * Returns row axis index of the current cell. 
	 * 
	 * @return row axis index
	 */
	public N0 index0() {
		return index0.getValue();
	}

	/**
	 * Return column axis index of the current cell. 
	 * 
	 * @return column axis index 
	 */
	public N1 index1() {
		return index1.getValue();
	}
	
	
	public boolean nextExtent() {
		if (i++ >= size) return false;
		e0 = set.items0.get(i-1);
		e1 = set.items1.get(i-1);
		index0 = set.math0.create(e0.start);
		index1 = set.math1.create(e1.start);
		index1.decrement();
		return true;
	}
	
	public N0 start0() {
		return e0.start();
	}
	public N0 end0() {
		return e0.end();
	}
	public N1 start1() {
		return e1.start();
	}
	public N1 end1() {
		return e1.end();
	}
}
