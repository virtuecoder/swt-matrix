package pl.netanel.swt.matrix;

import java.math.BigInteger;

class MutableBigInteger extends MutableNumber<BigInteger> {
	
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
	MutableBigInteger set(BigInteger n) {
		value = n;
		return this;
	}
	
	@Override
	MutableNumber<BigInteger> set(MutableNumber<BigInteger> n) {
		value = n.toBigInteger();
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
	MutableBigInteger add(MutableNumber<BigInteger> n) {
		value = value.add(n.toBigInteger());
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

	
	@Override
	MutableBigInteger add(BigInteger n) {
		value = value.add((BigInteger) n);
		return this;
	}

	@Override
	MutableBigInteger subtract(BigInteger n) {
		value = value.subtract((BigInteger) n);
		return this;
	}

	@Override
	int intValue() {
		return value.intValue();
	}

	@Override
	MutableNumber<BigInteger> subtract(MutableNumber<BigInteger> n) {
		value = value.subtract(n.toBigInteger());
		return this;
	}

	@Override
	MutableNumber<BigInteger> multiply(MutableNumber<BigInteger> n) {
		value = value.multiply(n.toBigInteger());
		return this;
	}

	@Override
	MutableNumber<BigInteger> divide(MutableNumber<BigInteger> n) {
		value = value.divide(n.toBigInteger());
		return this;
	}

	@Override
	MutableNumber<BigInteger> min(MutableNumber<BigInteger> n) {
		return value.compareTo(n.toBigInteger()) <= 0 ? this : n; 
	}

	@Override
	int compareTo(BigInteger n) {
		return value.compareTo(n);
	}

  @Override long longValue() {
    return value.longValue();
  }

}
