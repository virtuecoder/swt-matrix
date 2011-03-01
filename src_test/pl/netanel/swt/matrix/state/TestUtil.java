package pl.netanel.swt.matrix.state;

public class TestUtil {
	static MutableNumber number(int n) {
		return new MutableInt(n);
	}
	
	static Extent extent(int start, int end) {
		return new Extent(number(start), number(end));
	}
	
	static NumberSet indexSet() {
		return new NumberSet(IntMath.getInstance());
	}
	
	static NumberSet indexSet(int start, int end) {
		NumberSet set = new NumberSet(IntMath.getInstance());
		set.add(number(start), number(end));
		return set;
	}
}
