package pl.netanel.swt.matrix;

import java.math.BigInteger;

abstract class MutableNumber<N extends Number> {
	private static final long serialVersionUID = 1L;
	
	abstract public BigInteger toBigInteger();
	abstract N getValue();
	
	public abstract MutableNumber<N> copy();

	abstract MutableNumber<N> set(N n);
	abstract MutableNumber<N> set(MutableNumber<N> n);
	
	abstract MutableNumber<N> increment();
	abstract MutableNumber<N> decrement();
	abstract MutableNumber<N> negate();
	abstract MutableNumber<N> add(MutableNumber n);
	abstract MutableNumber<N> subtract(MutableNumber n);
	abstract MutableNumber<N> multiply(MutableNumber n);
	abstract MutableNumber<N> divide(MutableNumber n);

	abstract MutableNumber<N> add(int n);
	abstract MutableNumber<N> add(N n);
	abstract MutableNumber<N> subtract(N n);
	
	abstract int compareTo(N n);
	abstract MutableNumber<N> min(MutableNumber<N> n);

	abstract int intValue();
}
