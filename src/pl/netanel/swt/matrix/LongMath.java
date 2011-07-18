package pl.netanel.swt.matrix;


class LongMath extends Math<Long> {

	private static final MutableLong ZERO = new MutableLong(0);
	private static final MutableLong ONE = new MutableLong(1);
	private static final Long ZERO_VALUE = Long.valueOf(0);
	private static final Long ONE_VALUE = Long.valueOf(1);
	
	private static LongMath instance = new LongMath();

	public static LongMath getInstance() {
		return instance;
	}
	
	@Override
	public MutableLong create(int value) {
		return new MutableLong(value);
	}

	@Override
	public MutableLong create(Long value) {
		return new MutableLong(value.longValue());
	}
	
	@Override
	public MutableLong create(MutableNumber mn) {
		return new MutableLong(mn instanceof MutableLong ? ((MutableLong) mn).value : mn.getValue().longValue());
	}
	
	@Override
	public Long ZERO_VALUE() {
		return ZERO_VALUE;
	}
	
	@Override
	public Long ONE_VALUE() {
		return ONE_VALUE;
	}
	
	@Override
	public MutableLong ZERO() {
		return ZERO;
	}
	
	@Override
	public MutableLong ONE() {
		return ONE;
	}
	
	@Override
	public int compare(Long x, Long y) {
		long v1 = x.longValue();
		long v2 = y.longValue();
		return v1 == v2 ? 0 : v1 > v2 ? 1 : -1;
	}

	
	/*------------------------------------------------------------------------
	 * Operations
	 */
	
	
	@Override
	public Long decrement(Number n) {
		return n.longValue() - 1;
	}

	@Override
	public Long increment(Number n) {
		return n.longValue() + 1;
	}
	
	@Override
	public Long subtract(Number x, Number y) {
		return x.longValue() - y.longValue();
	}
	
	@Override
	public Long add(Number x, Number y) {
		return x.longValue() + y.longValue();
	}

	@Override
	public Long getValue(Long n) {
		return n.longValue();
	}

	@Override
	Class<Long> getNumberClass() {
		return Long.class;
	}

}
