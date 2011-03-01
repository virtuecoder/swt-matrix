package pl.netanel.swt.matrix.state;

import java.math.BigInteger;

class BigIntegerMath extends NumberMath<BigInteger> {

	private static BigIntegerMath instance = new BigIntegerMath();

	public static BigIntegerMath getInstance() {
		return instance;
	}

}
