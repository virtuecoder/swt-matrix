package pl.netanel.swt.matrix;

import pl.netanel.util.Sequence;

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
public class CellSequence<N0 extends MutableNumber, N1 extends MutableNumber> implements Sequence {
	CellSet<N0, N1> set;
	int i, size;
	Extent<N0> e0;
	Extent<N1> e1;
	MutableNumber index0, index1;
	
	
	public CellSequence(CellSet set) {
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
		if (set.math1.compare(index1.increment(), e1.end) > 0) {
			if (set.math0.compare(index0.increment(), e0.end) > 0) {
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
	 * The index value is mutated by the sequence iteration 
	 * to avoid performance penalty for new MutableNumber object creation on each iteration step. 
	 * 
	 * @return row axis index
	 */
	public MutableNumber index0() {
		return index0;
	}

	/**
	 * Return column axis index of the current cell. 
	 * The index value is mutated by the sequence iteration
	 * to avoid performance penalty for new MutableNumber object creation on each iteration step.
	 * 
	 * @return column axis index 
	 */
	public MutableNumber index1() {
		return index1;
	}
}
