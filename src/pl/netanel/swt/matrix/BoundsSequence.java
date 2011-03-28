package pl.netanel.swt.matrix;

import org.eclipse.swt.graphics.Rectangle;

import pl.netanel.swt.matrix.Layout.LayoutSequence;

public class BoundsSequence implements Sequence {
	LayoutSequence seq0, seq1;
	private boolean empty;

	public BoundsSequence(LayoutSequence seq0, LayoutSequence seq1) {
		super();
		this.seq0 = seq0;
		this.seq1 = seq1;
	}

	@Override
	public void init() {
		seq0.init();
		empty = !seq0.next();
		seq1.init();
	}

	@Override
	public boolean next() {
		if (empty)
			return false;
		if (!seq1.next()) {
			if (!seq0.next())
				return false;
			seq1.init();
		}
		return true;
	}

	public Rectangle getBounds() {
		return new Rectangle(seq1.getDistance(), seq0.getDistance(),
				seq1.getWidth(), seq0.getWidth());
	}

	public Number getIndex0() {
		return seq0.getIndex();
	}

	public Number getIndex1() {
		return seq1.getIndex();
	}

}
