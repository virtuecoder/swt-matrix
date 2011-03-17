package pl.netanel.swt.matrix;

import java.math.BigInteger;

class MutableBigInteger extends MutableNumber<MutableBigInteger, BigInteger> {
	private static final long serialVersionUID = 1L;
	
	BigInteger value;
	
	public MutableBigInteger(BigInteger value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MutableBigInteger)) return false;
		return value.equals(((MutableBigInteger) obj).value);
	}
	
	@Override
	public int hashCode() {
		return value.hashCode();
	}
	
	
	@Override
	public MutableBigInteger copy() {
		return new MutableBigInteger(value);
	}

	@Override
	BigInteger getValue() {
		return value;
	}

	@Override
	MutableBigInteger set(Number n) {
		value = n instanceof BigInteger ? (BigInteger) n : new BigInteger(n.toString());
		return this;
	}
	
	@Override
	MutableBigInteger set(MutableNumber n) {
		value = n instanceof MutableBigInteger ? n.toBigInteger() : new BigInteger(n.toString());
		return this;
	}

	@Override
	MutableBigInteger increment() {
		value = value.add(BigInteger.ONE);
		return this;
	}

	@Override
	MutableBigInteger decrement() {
		value = value.subtract(BigInteger.ONE);
		return this;
	}

	@Override
	MutableBigInteger negate() {
		value = value.negate();
		return this;
	}

	@Override
	MutableBigInteger add(MutableBigInteger n) {
		value = value.add(n.value);
		return this;
	}

	@Override
	MutableBigInteger subtract(MutableBigInteger n) {
		value = value.subtract(n.value);
		return this;
	}

	@Override
	MutableBigInteger multiply(MutableBigInteger n) {
		value = value.multiply(n.value);
		return this;
	}

	@Override
	MutableBigInteger divide(MutableBigInteger n) {
		value = value.divide(n.value);
		return this;
	}

	@Override
	MutableBigInteger add(int n) {
		value = value.add(BigInteger.valueOf(n));
		return this;
	}

	@Override
	public BigInteger toBigInteger() {
		return value;
	}

	
	public void add(BigInteger n) {
		value = value.add(n);
	}

	@Override
	MutableBigInteger add(Number n) {
		value = value.add((BigInteger) n);
		return this;
	}

	@Override
	MutableBigInteger subtract(Number n) {
		value = value.subtract((BigInteger) n);
		return this;
	}

	@Override
	int intValue() {
		return value.intValue();
	}

	@Override
	MutableBigInteger min(MutableBigInteger n) {
		return value.compareTo(n.value) <= 0 ? this : n; 
	}

}
