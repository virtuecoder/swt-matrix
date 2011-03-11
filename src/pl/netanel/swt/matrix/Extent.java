package pl.netanel.swt.matrix;


public class Extent<N extends MutableNumber> {
	N start, end;

	public Extent(N start, N end) {
		super();
		this.start = start;
		this.end = end;
	}

	@Override
	public String toString() {
		return start.toString() + "-" + end.toString();
	}
	
	public Extent copy() {
		return new Extent(start.copy(), end.copy());
	}

	public static boolean contains(Math math, Extent e, MutableNumber n) {
		return math.contains(e.start, e.end, n);
	}

	public static MutableNumber count(Math math, Extent e) {
		return math.create(e.end).subtract(e.start).increment();
	}
	
}
