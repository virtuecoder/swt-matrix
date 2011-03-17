package pl.netanel.swt.matrix;

import java.math.BigInteger;



class MutableInt extends MutableNumber<MutableInt, Integer> {
	private static final long serialVersionUID = 1L;
	
	int value;
	
	public MutableInt(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return Integer.toString(value);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MutableInt)) return false;
		return ((MutableInt) obj).value == value;
	}
	
	@Override
	public int hashCode() {
		return value;
	}
	
	@Override
	public MutableInt copy() {
		return new MutableInt(value);
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
	public MutableInt set(Number value) {
		this.value = value.intValue();
		return this;
	}
	
	@Override
	public Integer getValue() {
		return value;
	}

	public void set(int value) {
		this.value = value;
	}

	@Override
	public MutableInt increment() {
		value++;
		return this;
	}

	@Override
	public MutableInt decrement() {
		value--;
		return this;
	}
	
	@Override
	public MutableInt negate() {
		value = -value;
		return this;
	}

	@Override
	public MutableInt add(MutableInt n) {
		value += n.value;
		return this;
	}

	@Override
	public MutableInt subtract(MutableInt n) {
		value -= n.value;
		return this;
	}

	@Override
	public MutableInt multiply(MutableInt n) {
		value *= n.value;
		return this;
	}

	@Override
	public MutableInt divide(MutableInt n) {
		value /= n.value;
		return this;
	}

	
	@Override
	public MutableInt add(int n) {
		value += n;
		return this;
	}

	@Override
	public BigInteger toBigInteger() {
		return BigInteger.valueOf(value);
	}

	@Override
	MutableInt add(Number n) {
		value += n.intValue();
		return this;
	}

	@Override
	MutableInt subtract(Number n) {
		value -= n.intValue();
		return this;
	}

}
