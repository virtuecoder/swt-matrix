package pl.netanel.swt.matrix;


class IntMath extends Math<MutableInt, Integer> {

	private static final MutableInt ZERO = new MutableInt(0);
	private static final MutableInt ONE = new MutableInt(1);
	
	private static IntMath instance = new IntMath();

	public static IntMath getInstance() {
		return instance;
	}
	
	@Override
	public MutableInt create(int value) {
		return new MutableInt(value);
	}

	@Override
	public MutableInt create(Number value) {
		return new MutableInt(value.intValue());
	}
	
	@Override
	public MutableInt ZERO() {
		return ZERO;
	}
	
	@Override
	public MutableInt ONE() {
		return ONE;
	}
	
	@Override
	public int compare(Integer x, Integer y) {
		int v1 = x.intValue();
		int v2 = y.intValue();
		return v1 == v2 ? 0 : v1 > v2 ? 1 : -1;
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

	
	@Override
	public Integer decrement(Number n) {
		return n.intValue() - 1;
	}

	@Override
	public Integer increment(Number n) {
		return n.intValue() + 1;
	}
	
	@Override
	public Integer subtract(Number x, Number y) {
		return x.intValue() - y.intValue();
	}

	@Override
	public Integer getValue(Number n) {
		return n.intValue();
	}

}
