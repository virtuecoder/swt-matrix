package pl.netanel.swt.matrix;


/**
 * The purpose of this class is to iterate over a set of numbers.
 * <p>
 * Two modes of iteration are possible by single numbers and by extents of numbers.
 * <p>
 * Example usage: <pre>
 * NumberSequence seq = section.getSelected();
 * // single number iteration
 * for (seq.init(); seq.next();) {
 *     System.out.println(seq.index0());
 * }
 * // number extents iteration
 * for (seq.init(); seq.nextExtent();) {
 *     System.out.println(seq.start() + " : " + seq.end());
 * }
 * </pre>
 * 
 * @author Jacek created 21-02-2011
 */
public class NumberSequence<N extends Number> implements Sequence {
	NumberSet<N> set;
	int i, size;
	Extent e;
	MutableNumber<N> index;
	
	
	NumberSequence(NumberSet set) {
		this.set = set;
	}
	
	public void init() {
		i = 0;
		size = set.items.size();
		if (size == 0) return;
		e = set.items.get(i);
		index = set.math.create(e.start);
		index.decrement();
	}
	
	/**
	 * Returns true if the next iteration step succeeded, false otherwise. 
	 * Another words false return value means the iteration has reached the end.   
	 * @return
	 */
	public boolean next() {
		if (size == 0) return false;
		if (set.math.compare(index.increment(), e.end) > 0) {
			if (++i >= size) return false;
			e = set.items.get(i);
			index.set(e.start);
		}
		return true;
	}
	
	/**
	 * Returns index of the current item. 
	 * 
	 * @return row axis index
	 */
	public N index() {
		return index.getValue();
	}

	boolean hasNext() {
		return i < size - 1 || set.math.compare(index.increment(), e.end) < 0;
	}
	
	
	public boolean nextExtent() {
		if (i++ >= size) return false;
		e = set.items.get(i-1);
		index.set(e.start);
		return true;
	}
	
	public N start() {
		return index.getValue();
	}
	
	public N end() {
		return index.getValue();
	}
}
