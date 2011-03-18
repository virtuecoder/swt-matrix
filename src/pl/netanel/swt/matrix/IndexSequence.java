package pl.netanel.swt.matrix;


/**
 * Allows iteration over a set of cells.
 * The order of cells is unspecified. 
 * <p>
 * Usage: <pre>
 * CellSequence seq = &ltget instance of the sequence&gt;
 * for (seq.init(); seq.next();) {
 *     System.out.println(
 *         seq.index0() + " : " + seq.index1());
 * }
 * </pre>
 * 
 * @see Sequence
 * @author Jacek created 21-02-2011
 */
public class IndexSequence<N extends Number> implements Sequence {
	NumberSet<N> set;
	int i, size;
	Extent e;
	MutableNumber<N> index;
	
	
	public IndexSequence(NumberSet set) {
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
}
