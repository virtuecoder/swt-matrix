package pl.netanel.swt.matrix;

import pl.netanel.swt.matrix.Layout.LayoutSequence;
import pl.netanel.util.Sequence;

public class CartesianLayoutSequence implements Sequence {

	private final LayoutSequence seq0;
	private final LayoutSequence seq1;
	private boolean empty;

	public CartesianLayoutSequence(LayoutSequence seq0, LayoutSequence seq1) {
		this.seq0 = seq0;
		this.seq1 = seq1;
		
	}

	@Override
	public void init() {
		seq0.init();
		seq1.init();
		empty = !seq0.next();
	}

	@Override
	public boolean next() {
		if (empty) return false;
		if (!seq1.next()) {
			if (!seq0.next()) return false;
			seq1.init();
			seq1.next();
		}
		return true;
	}

	public Bound getBound0() {
		return seq0.bound;
	}

	public Bound getBound1() {
		return seq1.bound;
	}

	public MutableNumber getIndex0() {
		return seq0.getItem().index;
	}

	public MutableNumber getIndex1() {
		return seq1.getItem().index;
	}

}
