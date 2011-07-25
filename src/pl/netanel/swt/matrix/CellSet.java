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
class CellSet<X extends Number, Y extends Number> {
	final ArrayList<Extent<Y>> items0;
	final ArrayList<Extent<X>> items1;
	final Math<Y> mathY;
	final Math<X> mathX;
	private boolean insertNew;
	
	public CellSet(Math<Y> mathY, Math<X> mathX) {
		this.mathY = mathY;
		this.mathX = mathX;
		items0 = new ArrayList<Extent<Y>>();
		items1 = new ArrayList<Extent<X>>();
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

	public boolean contains(Y indexY, X indexX) {
		int size = items0.size();
		for (int i = 0; i < size; i++) {
			Extent<Y> e1 = items0.get(i);
			Extent<X> e2 = items1.get(i);
			if (mathY.contains(e1.start(), e1.end(), indexY) &&
				mathX.contains(e2.start(), e2.end(), indexX)) 
			{
				return true;
			}
		}
		return false;
	}
	

	public void add(Y startY, Y endY, X startX, X endX) {
		insertNew = true;
		int i = 0;
		
		int size = items0.size();
		for (;i < size; i++) {
			Extent<Y> item0 = items0.get(i);
			Extent<X> item1 = items1.get(i);
			
			Y startYb = item0.start.getValue(), 	endYb = item0.end.getValue(); 
			X startXb = item1.start.getValue(), 	endXb = item1.end.getValue();
			
			int es0 = mathY.compare(endY, startYb);
			int se0 = mathY.compare(startY, endYb);
			int es1 = mathX.compare(endX, startXb);
			int se1 = mathX.compare(startX, endXb);
			// Intersect
			if (es0 >= 0 && se0 <= 0 && es1 >= 0 && se1 <= 0) {
				int ss0 = mathY.compare(startY, startYb);
				int ee0 = mathY.compare(endY, endYb);
				int ss1 = mathX.compare(startX, startXb);
				int ee1 = mathX.compare(endX, endXb);
				
				// Overlaps
				boolean overlaps0 = ss0 <= 0 && ee0 >= 0; 
				boolean overlaps1 = ss1 <= 0 && ee1 >= 0; 
				if (overlaps0 && overlaps1) {
					item0.start.set(startY = mathY.min(startY, startYb));
					item0.end.set(endY = mathY.max(endY, endYb));
					item1.start.set(startX = mathX.min(startX, startXb));
					item1.end.set(endX = mathX.max(endX, endXb));
					insertNew = false;
				} 
				// Inside
				else if (ss0 >= 0 && ee0 <= 0 && ss1 >= 0 && ee1 <= 0) {
					insertNew = false; break;
				} 
				// Crossing
				else {
					if (ss0 < 0) {
						insert(startY, mathY.decrement(startYb), startX, endX);
						startY = startYb;
					}
					if (ee0 > 0) {
						insert(mathY.increment(endYb), endY, startX, endX);
						endY = endYb;
					}
					if (ss1 < 0) {
						insert(startY, endY, startX, mathX.decrement(startXb));
					}
					if (ee1 > 0) {
						insert(startY, endY, mathX.increment(endXb), endX);
					}
					insertNew = false;
				}
			} 
		}
		if (insertNew) {
			items0.add(new Extent<Y>(mathY.create(startY), mathY.create(endY)));
			items1.add(new Extent<X>(mathX.create(startX), mathX.create(endX)));
		}
	}
	
	public void remove(Y startY, Y endY, X startX, X endX) {
		IntArray toRemove = new IntArray();
		int i = 0;

		int size = items0.size();
		for (;i < size; i++) {
			Extent<Y> item0 = items0.get(i);
			Extent<X> item1 = items1.get(i);
			
			Y startYb = item0.start.getValue(), endYb = item0.end.getValue(); 
			X startXb = item1.start.getValue(), endXb = item1.end.getValue();
			
			
			int es0 = mathY.compare(endY, startYb);
			int se0 = mathY.compare(startY, endYb);
			int es1 = mathX.compare(endX, startXb);
			int se1 = mathX.compare(startX, endXb);
			
			// Separate
			if (es0 < 0 || se0 > 0 || es1 < 0 || se1 > 0) continue;
			
			int ss0 = mathY.compare(startY, startYb);
			int ee0 = mathY.compare(endY, endYb);
			int ss1 = mathX.compare(startX, startXb);
			int ee1 = mathX.compare(endX, endXb);
			
			// Overlap
			if (ss0 <= 0 && ee0 >= 0 && ss1 <= 0 && ee1 >= 0) {
				toRemove.add(i);
				continue;
			}
			
			boolean remove = false;

			if (ss0 > 0) {
				// Add lines before the removal start
				insert(startYb, mathY.decrement(startY), startXb, endXb);
				startYb = startY;
				remove = true;
			}
			if (ee0 < 0) {
				// Add lines after the removal start
				insert(mathY.increment(endY), endYb, startXb, endXb);
				endYb = endY;
				remove = true;
			}
			
			if (ss1 > 0) {
				// Add lines before the removal start
				insert(startYb, endYb, startXb, mathX.decrement(startX));
				remove = true;
			}
			if (ee1 < 0) {
				// Add lines after the removal start
				insert(startYb, endYb, mathX.increment(endX), endXb);
				remove = true;
			}
			if (remove) toRemove.add(i);
		}
		for (int j = toRemove.size(); j-- > 0;) {
			items0.remove(toRemove.get(j));
			items1.remove(toRemove.get(j));
		}
	}
	
	private void insert(Y startY, Y endY, X startX, X endX) {
		items0.add(new Extent<Y>(mathY.create(startY), mathY.create(endY)));
		items1.add(new Extent<X>(mathX.create(startX), mathX.create(endX)));
	}
	
//	public void change(MutableNumber startY, MutableNumber endY, 
//			MutableNumber startX, MutableNumber endX, boolean add) 
//	{
//		if (add) {
//			add(startY, endY, startX, endX);
//		} else {
//			remove(startY, endY, startX, endX);
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
	

	public Number[] getExtent() {
		int size = items0.size();
		if (size == 0) {
			return new Number[] {
				mathY.ZERO_VALUE(), mathY.ZERO_VALUE(),
				mathX.ZERO_VALUE(), mathX.ZERO_VALUE()};
		} else {
			Number[] e = new Number[4];
			for (int i = 0; i < size; i++) {
				Extent<Y> extent0 = items0.get(i);
				Extent<X> extent1 = items1.get(i);
				if (e[0] == null || mathY.compare(extent0.start, (Y) e[0]) < 0) {
					e[0] = extent0.start.getValue();
				}
				if (e[1] == null || mathY.compare(extent0.end, (Y) e[1]) > 0) {
					e[1] = extent0.end.getValue();
				}
				if (e[2] == null || mathX.compare(extent1.start, (X) e[2]) < 0) {
					e[2] = extent1.start.getValue();
				}
				if (e[3] == null || mathX.compare(extent1.end, (X) e[3]) > 0) {
					e[3] = extent1.end.getValue();
				}
			}
			return e;
		}
	}
	
	
	boolean hasOne() {
		int size = items0.size();
		return size == 1 && 
				mathY.compare(mathY.getCount(items0.get(0)), mathY.ONE_VALUE()) == 0 &&
				mathX.compare(mathX.getCount(items1.get(0)), mathX.ONE_VALUE()) == 0;
	}
	
	private BigInteger count(Extent e) {
		return e.end.toBigInteger().subtract(e.start.toBigInteger()).add(BigInteger.ONE);
	}

	public int size() {
		return items0.size();
	}
	
	public CellSet copy() {
		CellSet copy = new CellSet(mathY, mathX);
		int size = size();
		for (int i = 0; i < size; i++) {
			Extent e0 = items0.get(i);
			Extent e1 = items1.get(i);
			copy.items0.add(new Extent(mathY.create(e0.start), mathY.create(e0.end)));
			copy.items1.add(new Extent(mathX.create(e1.start), mathX.create(e1.end)));
		}
		return copy;
	}


	public void deleteY(Y start, Y end) {
		for (int i = 0, imax = items0.size(); i < imax; i++) {
			Extent.delete(mathY, items0, start, end);
		}
	}
	
	public void deleteX(X start, X end) {
		for (int i = 0, imax = items0.size(); i < imax; i++) {
			Extent.delete(mathX, items1, start, end);
		}
	}
	
	public void insertY(Y target, Y count) {
		for (int i = 0, imax = items0.size(); i < imax; i++) {
			Extent.insert(mathY, items0, target, count);
		}
	}
	
	public void insertX(X target, X count) {
		for (int i = 0, imax = items0.size(); i < imax; i++) {
			Extent.insert(mathX, items1, target, count);
		}
	}

			
	
	
//	int intersect(MutableNumber startY, MutableNumber endY, MutableNumber startX, MutableNumber endX, 
//			MutableNumber startYb, MutableNumber endYb, MutableNumber startXb, MutableNumber endXb) 
//	{
//		int es0 = math.compare(endY, startYb);
//		int es1 = math.compare(endX, startXb);
//		MutableNumber startYc, endYc, startXc, endXc; // intersection index
//		if (math.compare(endY, startYb) >= 0 && math.compare(startY, endYb) <= 0 &&
//			math.compare(endX, startXb) >= 0 && math.compare(startX, endXb) <= 0) 
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
