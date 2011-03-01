package pl.netanel.swt.matrix.state;


/**
 * Abstracts arithmetics on integer types including BigInteger.
 * 
 * @author Jacek Kolodziejczyk created 01-03-2011
 */
abstract class NumberMath<N extends MutableNumber> {

	public static NumberMath<? extends Number> get(Class<? extends Number> numberClass) {
		if (numberClass == int.class) 				return IntMath.getInstance(); 
//		else if (numberClass == long.class) 		return LongMath.getInstance(); 
//		else if	(numberClass == BigInteger.class) 	return BigIntegerMath.getInstance();
		else throw new IllegalArgumentException("Cannot do arithmetics on " + numberClass);
	}

	abstract public N create(int value);
	
}
