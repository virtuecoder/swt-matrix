package pl.netanel.swt.matrix.state;

class IntMath extends Math<MutableInt> {

	private static IntMath instance = new IntMath();

	public static IntMath getInstance() {
		return instance;
	}

	@Override
	public MutableInt create(int value) {
		return new MutableInt(value);
	}

	@Override
	public int compare(MutableInt x, MutableInt y) {
		return x.value == y.value ? 0 : x.value > y.value ? 1 : -1;
	}

	
	/*------------------------------------------------------------------------
	 * Operations
	 */
	
	@Override
	public MutableInt decrement(MutableInt n) {
		return new MutableInt(n.value - 1);
	}

	@Override
	public MutableInt increment(MutableInt n) {
		return new MutableInt(n.value + 1);
	}

	@Override
	public MutableInt add(MutableInt x, MutableInt y) {
		return new MutableInt(x.value + y.value);
	}

	@Override
	public MutableInt subtract(MutableInt x, MutableInt y) {
		return new MutableInt(x.value - y.value);
	}

	@Override
	public MutableInt multiply(MutableInt x, MutableInt y) {
		return new MutableInt(x.value * y.value);
	}

	@Override
	public MutableInt divide(MutableInt x, MutableInt y) {
		return new MutableInt(x.value / y.value);
	}

}
