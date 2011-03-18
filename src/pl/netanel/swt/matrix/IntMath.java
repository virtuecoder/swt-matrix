package pl.netanel.swt.matrix;


class IntMath extends Math<Integer> {

	private static final MutableInt ZERO = new MutableInt(0);
	private static final MutableInt ONE = new MutableInt(1);
	private static final Integer ZERO_VALUE = Integer.valueOf(0);
	private static final Integer ONE_VALUE = Integer.valueOf(1);
	
	private static IntMath instance = new IntMath();

	public static IntMath getInstance() {
		return instance;
	}
	
	@Override
	public MutableInt create(int value) {
		return new MutableInt(value);
	}

	@Override
	public MutableInt create(Integer value) {
		return new MutableInt(value.intValue());
	}
	
	@Override
	public MutableInt create(MutableNumber mn) {
		return new MutableInt(mn instanceof MutableInt ? ((MutableInt) mn).value : mn.getValue().intValue());
	}
	
	@Override
	public Integer ZERO_VALUE() {
		return ZERO_VALUE;
	}
	
	@Override
	public Integer ONE_VALUE() {
		return ONE_VALUE;
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
	public Integer getValue(Integer n) {
		return n.intValue();
	}

}
