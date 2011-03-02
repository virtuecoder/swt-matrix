package pl.netanel.swt.matrix.state;


/**
 * Abstracts arithmetics on integer types including BigInteger.
 * 
 * @author Jacek Kolodziejczyk created 01-03-2011
 */
abstract class Math<N extends MutableNumber> {

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


	public static Math<? extends Number> getInstance(Class<? extends Number> numberClass) {
		if (numberClass == int.class) 				return IntMath.getInstance(); 
//		else if (numberClass == long.class) 		return LongMath.getInstance(); 
//		else if	(numberClass == BigInteger.class) 	return BigIntegerMath.getInstance();
		else throw new IllegalArgumentException("Cannot do arithmetics on " + numberClass);
	}

	public MutableNumber getMutable(Number n) {
		if (n instanceof MutableNumber) return (MutableNumber) n;
		return create(n);
	}

	public abstract N create(int value);
	public abstract N create(Number n);

	public abstract N decrement(N n);
	public abstract N increment(N n);
	public abstract N add(N x, N y);
	public abstract N subtract(N x, N y);
	public abstract N multiply(N x, N y);
	public abstract N divide(N x, N y);
	
	public abstract N ZERO();
	
	/*------------------------------------------------------------------------
	 * Comparison 
	 */
	
	public abstract int compare(N x, N y);
	
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
	
	public boolean contains(Extent<N> e, N n) {
		return compare(e.start, n) <= 0 && compare(n, e.end) <= 0;
	}

	public boolean contains(Extent<N> e, Extent<N> o) {
		return contains(e, o.start) && contains(e, o.end);
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
	
}
