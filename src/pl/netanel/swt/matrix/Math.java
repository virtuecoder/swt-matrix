package pl.netanel.swt.matrix;

import java.math.BigInteger;
import java.text.MessageFormat;

import pl.netanel.util.Preconditions;



/**
 * Abstracts arithmetics on integer types including BigInteger.
 *
 * @author Jacek Kolodziejczyk created 01-03-2011
 */
/**
 *
 * @author jacek.p.kolodziejczyk@gmail.com
 * @created 14-05-2012
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


	@SuppressWarnings("unchecked")
  public static <N2 extends Number> Math<N2> getInstance(Class<N2> numberClass) {
		if (numberClass == Integer.class) 			    return (Math<N2>) IntMath.getInstance();
		else if (numberClass == int.class) 	        return (Math<N2>) IntMath.getInstance();
		else if (numberClass == Long.class) 		    return (Math<N2>) LongMath.getInstance();
		else if (numberClass == long.class) 		    return (Math<N2>) LongMath.getInstance();
		else if	(numberClass == BigInteger.class) 	return (Math<N2>) BigIntegerMath.getInstance();
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

	public int compare(MutableExtent<N> x, MutableExtent<N> y) {
	  return compare(x.start.getValue(), x.end.getValue(), y.start.getValue(), y.end.getValue());
	}

	public int compare(MutableNumber<N> x, MutableNumber<N> y) {
		return compare(x.getValue(), y.getValue());
	}

	/**
	 * Returns -1, 0, or 1.
	 * @param x
	 * @param y
	 * @return
	 */
	public int compare(MutableNumber<N> x, N y) {
		return x.compareTo(y);
	}

	/**
	 * Returns a Math constant.
	 * @param start1
	 * @param end1
	 * @param start2
	 * @param end2
	 * @return
	 */
	public int compare(N start1, N end1, N start2, N end2) {
		if (compare(end1, start2) < 0) {
			if (compare(increment(end1), start2) == 0)	return ADJACENT_BEFORE;
			else 										                    return BEFORE;
		}
		if (compare(start1, end2) > 0) {
			if (compare(decrement(start1), end2) == 0) 	return ADJACENT_AFTER;
			else 										                    return AFTER;
		}

		int ss = compare(start1, start2);
		int ee = compare(end1, end2);
		if (ss == 0 && ee == 0)							          return EQUAL;
		if (ss <= 0) {
			if (ee >= 0) 								                return OVERLAP;
			else										                    return CROSS_BEFORE;
		}
		if (ee >= 0)		 							                return CROSS_AFTER;
		else 											                    return INSIDE;
	}

	/**
	 * Return Math constant
	 * @param start
	 * @param end
	 * @param n
	 * @return
	 */
	public int compare(N start, N end, N n) {
	  int s = compare(n, start);
	  int e = compare(n, end);
    if (s > 0) {
      if (e < 0)                                  return INSIDE;
	    else if (e == 0)                            return CROSS_AFTER;
	    else                                        return AFTER;
	  }
    else if (s == 0)                              return CROSS_BEFORE;
    else                                          return BEFORE;
	}

	public boolean contains(N start, N end, N n) {
		return compare(start, n) <= 0 && compare(n, end) <= 0;
	}

	public boolean contains(MutableExtent<N> e, MutableNumber<N> mn) {
		return contains(e.start(), e.end(), mn.getValue());
	}

	public boolean contains(MutableExtent<N> e, N n) {
		return contains(e.start(), e.end(), n);
	}

	public boolean contains(MutableNumber<N> start, MutableNumber<N> end, N n) {
		return compare(start.getValue(), n) <= 0 &&
			   compare(n, end.getValue()) <= 0;
	}

	public N max(N x, N y) {
		return compare(x, y) < 0 ? y : x;
	}

	public N min(N x, N y) {
		return compare(x, y) > 0 ? y : x;
	}

	public N max(N n1, N n2, N n3) {
    N max = n1;
    if (compare(n2, max) > 0) max = n2;
    else if (compare(n3, max) > 0) max = n3;
    return max;
  }

	public N min(N n1, N n2, N n3) {
	  N min = n1;
	  if (compare(n2, min) < 0) min = n2;
	  else if (compare(n3, min) < 0) min = n3;
	  return min;
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


	public MutableNumber<N> min(MutableNumber<N> x, MutableNumber<N> y) {
		return x.min(y);
	}

	N getCount(MutableExtent<N> e) {
		return create(e.end).subtract(e.start).increment().getValue();
	}

	abstract Class<N> getNumberClass();


	public void checkIndex(N index, String name) {
	  Preconditions.checkNotNullWithName(index, name);
	  if (getNumberClass() != index.getClass()) {
      throw new IndexOutOfBoundsException(MessageFormat.format(
        "invalid class of {0} ({1}), expected ({2})",
        name, index.getClass(), getNumberClass())) ;
    }
	  if (compare(index, ZERO_VALUE()) < 0) {
	    throw new IndexOutOfBoundsException(MessageFormat.format(
	      "{0} ({1}) cannot be negative", name, index)) ;
	  }
	}

	public static <N extends Number> void checkIndexStatic(N index, String name) {
	  Preconditions.checkNotNullWithName(index, name);
	  @SuppressWarnings("unchecked")
    Math<N> math = (Math<N>) getInstance(index.getClass());
	  math.checkIndex(index, name);
	}

	public static <N extends Number> void checkRange(N start, N end) {
	  Preconditions.checkNotNullWithName(start, "start");
	  Preconditions.checkNotNullWithName(end, "end");
	  @SuppressWarnings("unchecked")
	  Math<N> math = (Math<N>) Math.getInstance(start.getClass());

	  math.checkIndex(start, "start");
	  math.checkIndex(end, "start");

	  if (math.compare(start, end) > 0) {
	    throw new IllegalArgumentException(MessageFormat.format(
	      "start ({0}) cannot be greater then end {1}", start, end)) ;
	  }
	}


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
