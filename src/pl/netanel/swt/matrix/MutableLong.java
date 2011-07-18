package pl.netanel.swt.matrix;

import java.math.BigInteger;

class MutableLong extends MutableNumber<Long> {
	
	long value;
	
	public MutableLong(long value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return Long.toString(value);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MutableLong)) return false;
		return ((MutableLong) obj).value == value;
	}
	
	@Override
	public int hashCode() {
		return ((Long) value).hashCode();
	}
	
	@Override
	public MutableLong copy() {
		return new MutableLong(value);
	}
	
	@Override
	public Long getValue() {
		return value;
	}

	@Override
	public MutableLong set(Long n) {
		value = n.longValue();
		return this;
	}
	
	public MutableLong set(long n) {
		value = n;
		return this;
	}
	
	@Override
	MutableNumber<Long> set(MutableNumber<Long> n) {
		value = n.longValue();
		return this;
	}

	@Override
	public MutableLong increment() {
		value++;
		return this;
	}

	@Override
	public MutableLong decrement() {
		value--;
		return this;
	}
	
	@Override
	public MutableLong negate() {
		value = -value;
		return this;
	}

	@Override
	public MutableLong add(MutableNumber n) {
		value += n.longValue();
		return this;
	}

	@Override
	public MutableLong subtract(MutableNumber n) {
		value -= n.longValue();
		return this;
	}

	@Override
	public MutableLong multiply(MutableNumber n) {
		value *= n.longValue();
		return this;
	}

	@Override
	public MutableLong divide(MutableNumber n) {
		value /= n.longValue();
		return this;
	}

	
	@Override
	public MutableLong add(int n) {
		value += n;
		return this;
	}

	@Override
	public BigInteger toBigInteger() {
		return BigInteger.valueOf(value);
	}

	@Override
	MutableLong add(Long n) {
		value += n.longValue();
		return this;
	}

	@Override
	MutableLong subtract(Long n) {
		value -= n.longValue();
		return this;
	}

	@Override
	int intValue() {
		return ((Long) value).intValue();
	}
	
	@Override
	long longValue() {
	  return ((Long) value).longValue();
	}

	@Override
	MutableNumber<Long> min(MutableNumber<Long> n) {
		return value <= n.longValue() ? this : n; 
	}


	@Override
	int compareTo(Long n) {
		long x = n.longValue();
		return value == x ? 0 : value > x ? 1 : -1;
	}

}
