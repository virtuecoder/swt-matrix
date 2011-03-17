package pl.netanel.swt.matrix;

import java.math.BigInteger;

abstract class MutableNumber<MN extends MutableNumber, N extends Number> {
	private static final long serialVersionUID = 1L;
	
	abstract public BigInteger toBigInteger();
	abstract N getValue();
	
	public abstract MN copy();

	abstract MN set(Number n);
	abstract MN set(MutableNumber n);
	
	abstract MN increment();
	abstract MN decrement();
	abstract MN negate();
	abstract MN add(MN n);
	abstract MN subtract(MN n);
	abstract MN multiply(MN n);
	abstract MN divide(MN n);

	abstract MN add(int n);
	abstract MN add(Number n);
	abstract MN subtract(Number n);
	
	abstract MN min(MN n);

	abstract int intValue();
}
