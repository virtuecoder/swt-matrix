package pl.netanel.swt.matrix;

import java.math.BigInteger;

public abstract class MutableNumber<N extends MutableNumber> extends Number {
	private static final long serialVersionUID = 1L;
	
	public abstract N copy();

	abstract Number getValue();
	abstract N set(Number n);
	
	abstract N increment();
	abstract N decrement();
	abstract N negate();
	abstract N add(N n);
	abstract N subtract(N n);
	abstract N multiply(N n);
	abstract N divide(N n);

	abstract N add(int n);

	public BigInteger toBigInteger() {
		return new BigInteger(getValue().toString());
	}

}
