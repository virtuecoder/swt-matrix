package pl.netanel.swt.matrix;

import java.math.BigInteger;
import java.util.ArrayList;

import pl.netanel.util.IntArray;



/**
 * Manages a consistent set of extent arrays (cubes). It can model a cell selection. 
 *
 * @author Jacek
 * @created 15-11-2010
 */
class CellSet<N0 extends Number, N1 extends Number> {
	final ArrayList<Extent<N0>> items0;
	final ArrayList<Extent<N1>> items1;
	final Math<N0> math0;
	final Math<N1> math1;
	private boolean insertNew;
	
	public CellSet(Math math0, Math math1) {
		this.math0 = math0;
		this.math1 = math1;
		items0 = new ArrayList<Extent<N0>>();
		items1 = new ArrayList<Extent<N1>>();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int size = items0.size();
		for (int i = 0; i < size; i++) {
			if (i > 0) sb.append(", ");
			sb.append("[");
			sb.append(items0.get(i));
			sb.append(", ");
			sb.append(items1.get(i));
			sb.append("]");
		}
		return sb.toString();
	}

	public boolean contains(N0 index0, N1 index1) {
		int size = items0.size();
		for (int i = 0; i < size; i++) {
			Extent<N0> e1 = items0.get(i);
			Extent<N1> e2 = items1.get(i);
			if (math0.contains(e1.start(), e1.end(), index0) &&
				math1.contains(e2.start(), e2.end(), index1)) 
			{
				return true;
			}
		}
		return false;
	}
	

	public void add(N0 start0, N0 end0, N1 start1, N1 end1) {
		insertNew = true;
		int i = 0;
		
		int size = items0.size();
		for (;i < size; i++) {
			Extent<N0> item0 = items0.get(i);
			Extent<N1> item1 = items1.get(i);
			
			N0 start0b = item0.start.getValue(), 	end0b = item0.end.getValue(); 
			N1 start1b = item1.start.getValue(), 	end1b = item1.end.getValue();
			
			int es0 = math0.compare(end0, start0b);
			int se0 = math0.compare(start0, end0b);
			int es1 = math1.compare(end1, start1b);
			int se1 = math1.compare(start1, end1b);
			// Intersect
			if (es0 >= 0 && se0 <= 0 && es1 >= 0 && se1 <= 0) {
				int ss0 = math0.compare(start0, start0b);
				int ee0 = math0.compare(end0, end0b);
				int ss1 = math1.compare(start1, start1b);
				int ee1 = math1.compare(end1, end1b);
				
				// Overlaps
				boolean overlaps0 = ss0 <= 0 && ee0 >= 0; 
				boolean overlaps1 = ss1 <= 0 && ee1 >= 0; 
				if (overlaps0 && overlaps1) {
					item0.start.set(start0 = math0.min(start0, start0b));
					item0.end.set(end0 = math0.max(end0, end0b));
					item1.start.set(start1 = math1.min(start1, start1b));
					item1.end.set(end1 = math1.max(end1, end1b));
					insertNew = false;
				} 
				// Inside
				else if (ss0 >= 0 && ee0 <= 0 && ss1 >= 0 && ee1 <= 0) {
					insertNew = false; break;
				} 
				// Crossing
				else {
					if (ss0 < 0) {
						insert(start0, math0.decrement(start0b), start1, end1);
						start0 = start0b;
					}
					if (ee0 > 0) {
						insert(math0.increment(end0b), end0, start1, end1);
						end0 = end0b;
					}
					if (ss1 < 0) {
						insert(start0, end0, start1, math1.decrement(start1b));
					}
					if (ee1 > 0) {
						insert(start0, end0, math1.increment(end1b), end1);
					}
					insertNew = false;
				}
			} 
		}
		if (insertNew) {
			items0.add(new Extent(math0.create(start0), math0.create(end0)));
			items1.add(new Extent(math1.create(start1), math1.create(end1)));
		}
	}
	
	public void remove(N0 start0, N0 end0, N1 start1, N1 end1) {
		IntArray toRemove = new IntArray();
		int i = 0;

		int size = items0.size();
		for (;i < size; i++) {
			Extent<N0> item0 = items0.get(i);
			Extent<N1> item1 = items1.get(i);
			
			N0 start0b = item0.start.getValue(), end0b = item0.end.getValue(); 
			N1 start1b = item1.start.getValue(), end1b = item1.end.getValue();
			
			
			int es0 = math0.compare(end0, start0b);
			int se0 = math0.compare(start0, end0b);
			int es1 = math1.compare(end1, start1b);
			int se1 = math1.compare(start1, end1b);
			
			// Separate
			if (es0 < 0 || se0 > 0 || es1 < 0 || se1 > 0) continue;
			
			int ss0 = math0.compare(start0, start0b);
			int ee0 = math0.compare(end0, end0b);
			int ss1 = math1.compare(start1, start1b);
			int ee1 = math1.compare(end1, end1b);
			
			// Overlap
			if (ss0 <= 0 && ee0 >= 0 && ss1 <= 0 && ee1 >= 0) {
				toRemove.add(i);
				continue;
			}
			
			boolean remove = false;

			if (ss0 > 0) {
				// Add lines before the removal start
				insert(start0b, math0.decrement(start0), start1b, end1b);
				start0b = start0;
				remove = true;
			}
			if (ee0 < 0) {
				// Add lines after the removal start
				insert(math0.increment(end0), end0b, start1b, end1b);
				end0b = end0;
				remove = true;
			}
			
			if (ss1 > 0) {
				// Add lines before the removal start
				insert(start0b, end0b, start1b, math1.decrement(start1));
				remove = true;
			}
			if (ee1 < 0) {
				// Add lines after the removal start
				insert(start0b, end0b, math1.increment(end1), end1b);
				remove = true;
			}
			if (remove) toRemove.add(i);
		}
		for (int j = toRemove.size(); j-- > 0;) {
			items0.remove(toRemove.get(j));
			items1.remove(toRemove.get(j));
		}
	}
	
	private void insert(N0 start0, N0 end0, N1 start1, N1 end1) {
		items0.add(new Extent(math0.create(start0), math0.create(end0)));
		items1.add(new Extent(math1.create(start1), math1.create(end1)));
	}
	
//	public void change(MutableNumber start0, MutableNumber end0, 
//			MutableNumber start1, MutableNumber end1, boolean add) 
//	{
//		if (add) {
//			add(start0, end0, start1, end1);
//		} else {
//			remove(start0, end0, start1, end1);
//		}
//	}

	public boolean isEmpty() {
		return items0.isEmpty();
	}
	
	public void clear() {
		items0.clear();
		items1.clear();
	}

	public MutableBigInteger getCount() {
		BigIntegerMath math = BigIntegerMath.getInstance();
		MutableBigInteger count = math.create(0);
		int size = items0.size();
		for (int i = 0; i < size; i++) {
			BigInteger x = count(items0.get(i));
			BigInteger y = count(items1.get(i));
			count.add(x.multiply(y));
		}
		return count;
	}
	
	private BigInteger count(Extent e) {
		return e.end.toBigInteger().subtract(e.start.toBigInteger()).add(BigInteger.ONE);
	}

	public int size() {
		return items0.size();
	}
	
	public CellSet copy() {
		CellSet copy = new CellSet(math0, math1);
		int size = size();
		for (int i = 0; i < size; i++) {
			Extent e0 = items0.get(i);
			Extent e1 = items1.get(i);
			copy.items0.add(new Extent(math0.create(e0.start), math0.create(e0.end)));
			copy.items1.add(new Extent(math1.create(e1.start), math1.create(e1.end)));
		}
		return copy;
	}


	public void delete0(N0 start, N0 end) {
		for (int i = 0, imax = items0.size(); i < imax; i++) {
			Extent.delete(math0, items0, start, end);
		}
	}
	
	public void delete1(N1 start, N1 end) {
		for (int i = 0, imax = items0.size(); i < imax; i++) {
			Extent.delete(math1, items1, start, end);
		}
	}
	
	public void insert0(N0 target, N0 count) {
		for (int i = 0, imax = items0.size(); i < imax; i++) {
			Extent.insert(math0, items0, target, count);
		}
	}
	
	public void insert1(N1 target, N1 count) {
		for (int i = 0, imax = items0.size(); i < imax; i++) {
			Extent.insert(math1, items1, target, count);
		}
	}
	
	
	
	
//	int intersect(MutableNumber start0, MutableNumber end0, MutableNumber start1, MutableNumber end1, 
//			MutableNumber start0b, MutableNumber end0b, MutableNumber start1b, MutableNumber end1b) 
//	{
//		int es0 = math.compare(end0, start0b);
//		int es1 = math.compare(end1, start1b);
//		MutableNumber start0c, end0c, start1c, end1c; // intersection index
//		if (math.compare(end0, start0b) >= 0 && math.compare(start0, end0b) <= 0 &&
//			math.compare(end1, start1b) >= 0 && math.compare(start1, end1b) <= 0) 
//		{
//			return 
//		}
//		return 0;
//	}
	
}

///**
// * Detects if the extents is inside or overlaps. 
// * @param e1
// * @param e2
// * @return
// */
//static int getLocation(Extent[] e1, Extent[] e2) {
//	int lastLocation = e1[0].compareTo(e2[0]);
//	for (int i = 1; i < e1.length; i++) {
//		int location = e1[i].compareTo(e2[i]);
//		if (location == Extent.CROSS_LEFT || location == Extent.CROSS_RIGHT || 
//				location == Extent.ADJACENT_RIGHT || location == Extent.ADJACENT_LEFT ) {
//			return Extent.CROSS;
//		}
//		if (location != Extent.INSIDE && location != Extent.OVERLAP &&
//				i > 0 && location != lastLocation) {
//			return Extent.OTHER;
//		}
//	}
//	return lastLocation;
//}
