package pl.netanel.swt.matrix;


public class Extent<N extends MutableNumber> {
	N start, end;

	public Extent(N start, N end) {
		super();
		this.start = start;
		this.end = end;
	}

	public Extent copy() {
		return new Extent(start.copy(), end.copy());
	}
	
}
