package pl.netanel.swt.matrix;

import java.math.BigInteger;



/**
 * Abstracts arithmetics on integer types including BigInteger.
 * 
 * @author Jacek Kolodziejczyk created 01-03-2011
 */
abstract class Math<MN extends MutableNumber<MN, N>, N extends Number> {

	public static final int BEFORE = 1;
	public static final int ADJACENT_BEFORE = 2;
	public static final int ADJACENT_AFTER = 3;
	public static final int AFTER = 4;
	public static final int OVERLAP = 5;
	public static final int CROSS_BEFORE = 6;
	public static final int CROSS_AFTER = 7;
	public static final int INSIDE = 8;
	public static final int EQUAL = 9;
	public static final int OTHER = 10;
	public static final int CROSS = 11;


	public static Math getInstance(Class numberClass) {
		if (numberClass == int.class) 				return IntMath.getInstance(); 
//		else if (numberClass == long.class) 		return LongMath.getInstance(); 
		else if	(numberClass == BigInteger.class) 	return BigIntegerMath.getInstance();
		else throw new IllegalArgumentException("Cannot do arithmetics on " + numberClass);
	}

	public abstract MN create(int value);
	public abstract MN create(Number n);
	public abstract MN create(MutableNumber n);

	public abstract MN ZERO();
	public abstract MN ONE();
	
	public abstract N ZERO_VALUE();
	public abstract N ONE_VALUE();

	public abstract N decrement(Number n);
	public abstract N increment(Number n);
	public abstract N subtract(Number x, Number y);
	
	/*------------------------------------------------------------------------
	 * Comparison 
	 */
	
	public abstract int compare(N x, N y);
	
	public int compare(MN x, MN y) {
		return compare(x.getValue(), y.getValue());
	}
	
	public int compare(N start1, N end1, N start2, N end2) {
		if (compare(end1, start2) < 0) {
			if (compare(increment(end1), start2) == 0)	return ADJACENT_BEFORE;
			else 										return BEFORE;
		}
		if (compare(start1, end2) > 0) {
			if (compare(decrement(start1), end2) == 0) 	return ADJACENT_AFTER;
			else 										return AFTER;
		}
		
		int ss = compare(start1, start2);
		int ee = compare(end1, end2);
		if (ss == 0 && ee == 0)							return EQUAL;
		if (ss <= 0) {
			if (ee >= 0) 								return OVERLAP;
			else										return CROSS_BEFORE;
		}
		if (ee >= 0)		 							return CROSS_AFTER;
		else 											return INSIDE; 
	}
	
	
	public boolean contains(N start, N end, N n) {
		return compare(start, n) <= 0 && compare(n, end) <= 0;
	}
	
	public boolean contains(Extent e, MutableNumber mn) {
		return contains((N) e.start(), (N) e.end(), (N) mn.getValue());
	}
	
	public boolean contains(Extent e, Number n) {
		return contains((N) e.start(), (N) e.end(), (N) n);
	}
	
	public boolean contains(MN start, MN end, N n) {
		return compare(start.getValue(), getValue(n)) <= 0 && 
			   compare(getValue(n), end.getValue()) <= 0;
	}

	public Number max(N x, N y) {
		return compare(x, y) < 0 ? y : x;
	}
	
	public Number min(N x, N y) {
		return compare(x, y) > 0 ? y : x;
	}
	
	public Number max(N ...n) {
		N max = n[0];
		for (int i = 1; i < n.length; i++) {
			if (compare(n[i], max) > 0) {
				max = n[i];
			}
		}
		return max;
	}
	
	public Number min(N ...n) {
		N min = n[0];
		for (int i = 1; i < n.length; i++) {
			if (compare(n[i], min) < 0) {
				min = n[i];
			}
		}
		return min;
	}

	abstract public N getValue(Number n);

	public MN min(MN x, MN y) {
		return x.min(y);
	}
}
