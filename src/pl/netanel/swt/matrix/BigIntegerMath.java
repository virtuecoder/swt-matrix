package pl.netanel.swt.matrix;

import java.math.BigInteger;

class BigIntegerMath extends Math<BigInteger> {

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
	public MutableBigInteger create(BigInteger n) {
		return new MutableBigInteger(getValue(n));
	}
	
	@Override
	public MutableBigInteger create(MutableNumber mn) {
		return new MutableBigInteger(mn.toBigInteger());
	}


	@Override
	public BigInteger ZERO_VALUE() {
		return BigInteger.ZERO;
	}

	@Override
	public BigInteger ONE_VALUE() {
		return BigInteger.ONE;
	}
	
	@Override
	public MutableBigInteger ZERO() {
		return ZERO;
	}
	
	@Override
	public MutableBigInteger ONE() {
		return ONE;
	}
	
	public int compare(BigInteger x, BigInteger y) {
		return x.compareTo(y);
//		return ((BigInteger) x).compareTo((BigInteger) y);
	}
	
//	@Override
//	public int compare(BigIntger x, Number y) {
//		return ((BigInteger) getValue(x)).compareTo(((BigInteger) getValue(y)));
////		return ((BigInteger) x).compareTo((BigInteger) y);
//	}

	@Override
	public BigInteger decrement(Number n) {
		return ((BigInteger) n).subtract(BigInteger.ONE);
	}

	@Override
	public BigInteger increment(Number n) {
		return ((BigInteger) n).add(BigInteger.ONE);
	}

	@Override
	public BigInteger subtract(Number x, Number y) {
		return ((BigInteger) x).subtract((BigInteger) y);
	}
	
	@Override
	public BigInteger add(Number x, Number y) {
		return ((BigInteger) x).add((BigInteger) y);
	}

	@Override
	public BigInteger getValue(BigInteger n) {
		if (n instanceof BigInteger) return (BigInteger) n;
//		if (n instanceof MutableBigInteger) return ((MutableBigInteger) n).value;
		return new BigInteger(n.toString());
	}

}
