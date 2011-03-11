package pl.netanel.swt.matrix;

import java.math.BigInteger;

public abstract class BigNumber<N extends MutableNumber> extends Number {
	private static final long serialVersionUID = 1L;

	public BigNumber() {
		super();
	}

	abstract public BigInteger toBigInteger();
	abstract Number getValue();
}