package pl.netanel.swt.matrix.state;


class MutableInt extends MutableNumber<Integer> {
	private static final long serialVersionUID = 1L;
	
	int value;
	
	public MutableInt(int value) {
		this.value = value;
	}

	@Override
	public int intValue() {
		return value;
	}

	@Override
	public long longValue() {
		return value;
	}

	@Override
	public float floatValue() {
		return value;
	}

	@Override
	public double doubleValue() {
		return value;
	}

	@Override
	public void setValue(Integer value) {
		this.value = value;
	}
	
	@Override
	public Integer getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}
