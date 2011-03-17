package pl.netanel.swt.matrix;


class Extent {
	MutableNumber start, end;

	public Extent(MutableNumber start, MutableNumber end) {
		super();
		this.start = start;
		this.end = end;
	}

	@Override
	public String toString() {
		return start.toString() + "-" + end.toString();
	}
	
	public Number start() {
		return start.getValue();
	}
	
	public Number end() {
		return end.getValue();
	}
	
//	public Extent copy() {
//		return new Extent(start.copy(), end.copy());
//	}
//
//	public static boolean contains(Math math, Extent e, MutableNumber n) {
//		return math.contains(e.start, e.end, n);
//	}
//
//	public static MutableNumber count(Math math, Extent e) {
//		return math.create(e.end).subtract(e.start).increment();
//	}
	
}
