package pl.netanel.swt.matrix;

import java.math.BigInteger;

abstract class MutableNumber<N extends MutableNumber> extends Number {
	private static final long serialVersionUID = 1L;
	
	abstract public BigInteger toBigInteger();
	abstract Number getValue();
	
	public abstract N copy();

	abstract N set(Number n);
	
	abstract N increment();
	abstract N decrement();
	abstract N negate();
	abstract N add(N n);
	abstract N subtract(N n);
	abstract N multiply(N n);
	abstract N divide(N n);

	abstract N add(int n);
	abstract N add(Number n);
	abstract N subtract(Number n);

}
