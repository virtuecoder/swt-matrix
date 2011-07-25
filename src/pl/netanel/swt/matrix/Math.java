package pl.netanel.swt.matrix;

import java.math.BigInteger;



/**
 * Abstracts arithmetics on integer types including BigInteger.
 * 
 * @author Jacek Kolodziejczyk created 01-03-2011
 */
abstract class Math<N extends Number> {

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
		if (numberClass == Integer.class) 			    return IntMath.getInstance(); 
		else if (numberClass == int.class) 	        return IntMath.getInstance(); 
		else if (numberClass == Long.class) 		    return LongMath.getInstance(); 
		else if (numberClass == long.class) 		    return LongMath.getInstance(); 
		else if	(numberClass == BigInteger.class) 	return BigIntegerMath.getInstance();
		else throw new IllegalArgumentException(
		  "Cannot do arithmetics on " + numberClass.getName() + " class");
	}

	public abstract MutableNumber<N> create(int value);
	public abstract MutableNumber<N> create(N n);
	public abstract MutableNumber<N> create(MutableNumber<N> n);

	public abstract MutableNumber<N> ZERO();
	public abstract MutableNumber<N> ONE();
	
	public abstract N ZERO_VALUE();
	public abstract N ONE_VALUE();

	public abstract N decrement(Number n);
	public abstract N increment(Number n);
	public abstract N add(Number x, Number y);
	public abstract N subtract(Number x, Number y);
	
	/*------------------------------------------------------------------------
	 * Comparison 
	 */
	
	public abstract int compare(N x, N y);
	
	public int compare(MutableNumber<N> x, MutableNumber<N> y) {
		return compare(x.getValue(), y.getValue());
	}
	
	public int compare(MutableNumber<N> x, N y) {
		return x.compareTo(y);
	}
	
	public int compare(N startX, N endX, N start2, N end2) {
		if (compare(endX, start2) < 0) {
			if (compare(increment(endX), start2) == 0)	return ADJACENT_BEFORE;
			else 										return BEFORE;
		}
		if (compare(startX, end2) > 0) {
			if (compare(decrement(startX), end2) == 0) 	return ADJACENT_AFTER;
			else 										return AFTER;
		}
		
		int ss = compare(startX, start2);
		int ee = compare(endX, end2);
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
	
	public boolean contains(Extent e, MutableNumber<N> mn) {
		return contains((N) e.start(), (N) e.end(), (N) mn.getValue());
	}
	
	public boolean contains(Extent e, N n) {
		return contains((N) e.start(), (N) e.end(), (N) n);
	}
	
	public boolean contains(MutableNumber<N> start, MutableNumber<N> end, N n) {
		return compare((N) start.getValue(), getValue(n)) <= 0 && 
			   compare(getValue(n), (N) end.getValue()) <= 0;
	}

	public N max(N x, N y) {
		return compare(x, y) < 0 ? y : x;
	}
	
	public N min(N x, N y) {
		return compare(x, y) > 0 ? y : x;
	}
	
	public N max(N ...n) {
		N max = n[0];
		for (int i = 1; i < n.length; i++) {
			if (compare(n[i], max) > 0) {
				max = n[i];
			}
		}
		return max;
	}
	
	public N min(N ...n) {
		N min = n[0];
		for (int i = 1; i < n.length; i++) {
			if (compare(n[i], min) < 0) {
				min = n[i];
			}
		}
		return min;
	}


	public MutableNumber min(MutableNumber x, MutableNumber y) {
		return x.min(y);
	}
	
	N getCount(Extent<N> e) {
		return create(e.end).subtract(e.start).increment().getValue();
	}

	abstract public N getValue(N n);
	abstract Class<N> getNumberClass();

//	/**
//	 * 
//	 * @param e
//	 * @param start
//	 * @param end
//	 */
//	public void remove(Extent<N> e, N start, N end) {
//		if (compare(e.start, ))
//	}
}
