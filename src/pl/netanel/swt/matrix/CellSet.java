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
class CellSet {
	final ArrayList<Extent> items0, items1;
	final Math math0, math1;
	private boolean insertNew;
	
	public CellSet(Math math0, Math math1) {
		this.math0 = math0;
		this.math1 = math1;
		items0 = new ArrayList<Extent>();
		items1 = new ArrayList<Extent>();
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

	public boolean contains(MutableNumber index0, MutableNumber index1) {
		int size = items0.size();
		for (int i = 0; i < size; i++) {
			if (Extent.contains(math0, items0.get(i), index0) &&
				Extent.contains(math1, items1.get(i), index1)) 
			{
				return true;
			}
		}
		return false;
	}
	

	public void add(MutableNumber start0, MutableNumber end0, MutableNumber start1, MutableNumber end1) {
		insertNew = true;
		int i = 0;
		
		int size = items0.size();
		for (;i < size; i++) {
			Extent item0 = items0.get(i);
			Extent item1 = items1.get(i);
			
			MutableNumber start0a = item0.start, end0a = item0.end, 
				  start1a = item1.start, end1a = item1.end;
			
			int es0 = math0.compare(end0, start0a);
			int se0 = math0.compare(start0, end0a);
			int es1 = math1.compare(end1, start1a);
			int se1 = math1.compare(start1, end1a);
			// Intersect
			if (es0 >= 0 && se0 <= 0 && es1 >= 0 && se1 <= 0) {
				int ss0 = math0.compare(start0, start0a);
				int ee0 = math0.compare(end0, end0a);
				int ss1 = math1.compare(start1, start1a);
				int ee1 = math1.compare(end1, end1a);
				
				// Overlaps
				boolean overlaps0 = ss0 <= 0 && ee0 >= 0; 
				boolean overlaps1 = ss1 <= 0 && ee1 >= 0; 
				if (overlaps0 && overlaps1) {
					extend(item0, start0, end0);
					extend(item1, start1, end1);
				} 
				// Inside
				else if (ss0 >= 0 && ee0 <= 0 && ss1 >= 0 && ee1 <= 0) {
					insertNew = false; break;
				} 
				// Crossing
				else {
					if (ss0 < 0) {
						insert(start0.copy(), math0.decrement(item0.start), start1.copy(), end1.copy());
						start0 = start0a;
					}
					if (ee0 > 0) {
						insert(math0.increment(item0.end), end0.copy(), start1.copy(), end1.copy());
						end0 = end0a;
					}
					if (ss1 < 0) {
						insert(start0.copy(), end0.copy(), start1.copy(), math1.decrement(item1.start));
					}
					if (ee1 > 0) {
						insert(start0.copy(), end0.copy(), math1.increment(item1.end), end1.copy());
					}
					insertNew = false;
				}
			} 
		}
		if (insertNew) {
			items0.add(new Extent(start0.copy(), end0.copy()));
			items1.add(new Extent(start1.copy(), end1.copy()));
		}
	}
	
	void extend(Extent e, MutableNumber start, MutableNumber end) {
		e.start.set(math0.min(e.start, start));
		e.end.set(math1.max(e.end, end));
		insertNew = false;
	}
	
	public void remove(MutableNumber start0, MutableNumber end0, MutableNumber start1, MutableNumber end1) {
		IntArray toRemove = new IntArray();
		int i = 0;
		
		int size = items0.size();
		for (;i < size; i++) {
			Extent item0 = items0.get(i);
			Extent item1 = items1.get(i);
			
			MutableNumber start0a = item0.start, end0a = item0.end, 
				  start1a = item1.start, end1a = item1.end;
			
			
			int es0 = math0.compare(end0, start0a);
			int se0 = math0.compare(start0, end0a);
			int es1 = math1.compare(end1, start1a);
			int se1 = math1.compare(start1, end1a);
			
			// Separate
			if (es0 < 0 || se0 > 0 || es1 < 0 || se1 > 0) continue;
			
			int ss0 = math0.compare(start0, start0a);
			int ee0 = math0.compare(end0, end0a);
			int ss1 = math1.compare(start1, start1a);
			int ee1 = math1.compare(end1, end1a);
			
			// Overlap
			if (ss0 <= 0 && ee0 >= 0 && ss1 <= 0 && ee1 >= 0) {
				toRemove.add(i);
				continue;
			}
			
			boolean remove = false;

			if (ss0 > 0) {
				// Add lines before the removal start
				insert(start0a, math0.decrement(start0), start1a, end1a);
				start0a = start0;
				remove = true;
			}
			if (ee0 < 0) {
				// Add lines after the removal start
				insert(math0.increment(end0), end0a, start1a, end1a);
				end0a = end0;
				remove = true;
			}
			
			if (ss1 > 0) {
				// Add lines before the removal start
				insert(start0a, end0a, start1a, math1.decrement(start1));
				remove = true;
			}
			if (ee1 < 0) {
				// Add lines after the removal start
				insert(start0a, end0a, math1.increment(end1), end1a);
				remove = true;
			}
			if (remove) toRemove.add(i);
		}
		for (int j = toRemove.size(); j-- > 0;) {
			items0.remove(toRemove.get(j));
			items1.remove(toRemove.get(j));
		}
	}
	
	private void insert(MutableNumber start0, MutableNumber end0, MutableNumber start1, MutableNumber end1) {
		items0.add(new Extent(start0, end0));
		items1.add(new Extent(start1, end1));
	}
	
	public void change(MutableNumber start0, MutableNumber end0, 
			MutableNumber start1, MutableNumber end1, boolean add) 
	{
		if (add) {
			add(start0, end0, start1, end1);
		} else {
			remove(start0, end0, start1, end1);
		}
	}

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
			copy.items0.add(items0.get(i).copy());
			copy.items1.add(items1.get(i).copy());
		}
		return copy;
	}
	
//	int intersect(MutableNumber start0a, MutableNumber end0a, MutableNumber start1a, MutableNumber end1a, 
//			MutableNumber start0b, MutableNumber end0b, MutableNumber start1b, MutableNumber end1b) 
//	{
//		int es0 = math.compare(end0a, start0b);
//		int es1 = math.compare(end1a, start1b);
//		MutableNumber start0c, end0c, start1c, end1c; // intersection index
//		if (math.compare(end0a, start0b) >= 0 && math.compare(start0a, end0b) <= 0 &&
//			math.compare(end1a, start1b) >= 0 && math.compare(start1a, end1b) <= 0) 
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
