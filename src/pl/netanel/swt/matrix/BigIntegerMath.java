package pl.netanel.swt.matrix;

import java.math.BigInteger;

class BigIntegerMath extends Math<MutableBigInteger> {

	private static final MutableBigInteger ZERO = new MutableBigInteger(BigInteger.ZERO);
	private static final MutableBigInteger ONE = new MutableBigInteger(BigInteger.ONE);
	
	private static BigIntegerMath instance = new BigIntegerMath();

	public static BigIntegerMath getInstance() {
		return instance;
	}

	@Override
	public MutableBigInteger create(int value) {
		return new MutableBigInteger(BigInteger.valueOf(value));
	}

	@Override
	public MutableBigInteger create(Number n) {
		return new MutableBigInteger(new BigInteger(n.toString()));
	}

	@Override
	public MutableBigInteger decrement(MutableBigInteger n) {
		return new MutableBigInteger(n.value.subtract(BigInteger.ONE));
	}

	@Override
	public MutableBigInteger increment(MutableBigInteger n) {
		return new MutableBigInteger(n.value.add(BigInteger.ONE));
	}

	@Override
	public MutableBigInteger add(MutableBigInteger x, MutableBigInteger y) {
		return new MutableBigInteger(x.value.add(y.value));
	}

	@Override
	public MutableBigInteger subtract(MutableBigInteger x, MutableBigInteger y) {
		return new MutableBigInteger(x.value.subtract(y.value));
	}

	@Override
	public MutableBigInteger multiply(MutableBigInteger x, MutableBigInteger y) {
		return new MutableBigInteger(x.value.multiply(y.value));
	}

	@Override
	public MutableBigInteger divide(MutableBigInteger x, MutableBigInteger y) {
		return new MutableBigInteger(x.value.divide(y.value));
	}

	@Override
	public MutableBigInteger ZERO() {
		return ZERO;
	}

	@Override
	public MutableBigInteger ONE() {
		return ONE;
	}
	
	@Override
	public int compare(Number x, Number y) {
		return ((BigInteger) x).compareTo((BigInteger) y);
	}

	@Override
	public Number decrement(Number n) {
		return ((BigInteger) n).subtract(BigInteger.ONE);
	}

	@Override
	public Number increment(Number n) {
		return ((BigInteger) n).add(BigInteger.ONE);
	}

	@Override
	public Number subtract(Number x, Number y) {
		return ((BigInteger) x).subtract((BigInteger) y);
	}

}
