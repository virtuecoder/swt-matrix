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
	final ArrayList<Extent<X>> itemsX;
	final ArrayList<Extent<Y>> itemsY;
	final Math<X> mathX;
	final Math<Y> mathY;
	private boolean insertNew;
	
	public CellSet(Math<X> mathX, Math<Y> mathY) {
		this.mathX = mathX;
		this.mathY = mathY;
		itemsX = new ArrayList<Extent<X>>();
		itemsY = new ArrayList<Extent<Y>>();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int size = itemsY.size();
		for (int i = 0; i < size; i++) {
			if (i > 0) sb.append(", ");
			sb.append("[");
			sb.append(itemsY.get(i));
			sb.append(", ");
			sb.append(itemsX.get(i));
			sb.append("]");
		}
		return sb.toString();
	}

	public boolean contains(X indexX, Y indexY) {
		int size = itemsY.size();
		for (int i = 0; i < size; i++) {
			Extent<Y> e1 = itemsY.get(i);
			Extent<X> e2 = itemsX.get(i);
			if (mathY.contains(e1.start(), e1.end(), indexY) &&
				mathX.contains(e2.start(), e2.end(), indexX)) 
			{
				return true;
			}
		}
		return false;
	}
	

	public void add(X startX, X endX, Y startY, Y endY) {
		insertNew = true;
		int i = 0;
		
		int size = itemsY.size();
		for (;i < size; i++) {
			Extent<X> itemX = itemsX.get(i);
			Extent<Y> itemY = itemsY.get(i);
			
			X startXb = itemX.start.getValue(), 	endXb = itemX.end.getValue();
			Y startYb = itemY.start.getValue(), 	endYb = itemY.end.getValue(); 
			
			int esX = mathX.compare(endX, startXb);
			int seX = mathX.compare(startX, endXb);
			int esY = mathY.compare(endY, startYb);
			int seY = mathY.compare(startY, endYb);
			// Intersect
			if (esY >= 0 && seY <= 0 && esX >= 0 && seX <= 0) {
				int ssX = mathX.compare(startX, startXb);
				int eeX = mathX.compare(endX, endXb);
				int ssY = mathY.compare(startY, startYb);
				int eeY = mathY.compare(endY, endYb);
				
				// Overlaps
				boolean overlapsX = ssX <= 0 && eeX >= 0; 
				boolean overlapsY = ssY <= 0 && eeY >= 0; 
				if (overlapsY && overlapsX) {
					itemX.start.set(startX = mathX.min(startX, startXb));
					itemX.end.set(endX = mathX.max(endX, endXb));
					itemY.start.set(startY = mathY.min(startY, startYb));
					itemY.end.set(endY = mathY.max(endY, endYb));
					insertNew = false;
				} 
				// Inside
				else if (ssX >= 0 && eeX <= 0 && ssY >= 0 && eeY <= 0) {
					insertNew = false; break;
				} 
				// Crossing
				else {
					if (ssY < 0) {
						insert(startX, endX, startY, mathY.decrement(startYb));
						startY = startYb;
					}
					if (eeY > 0) {
						insert(startX, endX, mathY.increment(endYb), endY);
						endY = endYb;
					}
					if (ssX < 0) {
						insert(startX, mathX.decrement(startXb), startY, endY);
					}
					if (eeX > 0) {
						insert(mathX.increment(endXb), endX, startY, endY);
					}
					insertNew = false;
				}
			} 
		}
		if (insertNew) {
		  itemsX.add(new Extent<X>(mathX.create(startX), mathX.create(endX)));
			itemsY.add(new Extent<Y>(mathY.create(startY), mathY.create(endY)));
		}
	}
	
	public void remove(X startX, X endX, Y startY, Y endY) {
		IntArray toRemove = new IntArray();
		int i = 0;

		int size = itemsY.size();
		for (;i < size; i++) {
		  Extent<X> itemX = itemsX.get(i);
			Extent<Y> itemY = itemsY.get(i);
			
			X startXb = itemX.start.getValue(), endXb = itemX.end.getValue();
			Y startYb = itemY.start.getValue(), endYb = itemY.end.getValue(); 
			
			
			int esX = mathX.compare(endX, startXb);
			int seX = mathX.compare(startX, endXb);
			int esY = mathY.compare(endY, startYb);
			int seY = mathY.compare(startY, endYb);
			
			// Separate
			if (esY < 0 || seY > 0 || esX < 0 || seX > 0) continue;
			
			int ssX = mathX.compare(startX, startXb);
			int eeX = mathX.compare(endX, endXb);
			int ssY = mathY.compare(startY, startYb);
			int eeY = mathY.compare(endY, endYb);
			
			// Overlap
			if (ssX <= 0 && eeX >= 0 && ssY <= 0 && eeY >= 0) {
				toRemove.add(i);
				continue;
			}
			
			boolean remove = false;

			if (ssY > 0) {
				// Add lines before the removal start
				insert(startXb, endXb, startYb, mathY.decrement(startY));
				startYb = startY;
				remove = true;
			}
			if (eeY < 0) {
				// Add lines after the removal start
				insert(startXb, endXb, mathY.increment(endY), endYb);
				endYb = endY;
				remove = true;
			}
			
			if (ssX > 0) {
				// Add lines before the removal start
				insert(startXb, mathX.decrement(startX), startYb, endYb);
				remove = true;
			}
			if (eeX < 0) {
				// Add lines after the removal start
				insert(mathX.increment(endX), endXb, startYb, endYb);
				remove = true;
			}
			if (remove) toRemove.add(i);
		}
		for (int j = toRemove.size(); j-- > 0;) {
			itemsY.remove(toRemove.get(j));
			itemsX.remove(toRemove.get(j));
		}
	}
	
	private void insert(X startX, X endX, Y startY, Y endY) {
		itemsY.add(new Extent<Y>(mathY.create(startY), mathY.create(endY)));
		itemsX.add(new Extent<X>(mathX.create(startX), mathX.create(endX)));
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
		return itemsY.isEmpty();
	}
	
	public void clear() {
		itemsY.clear();
		itemsX.clear();
	}

	public MutableBigInteger getCount() {
		BigIntegerMath math = BigIntegerMath.getInstance();
		MutableBigInteger count = math.create(0);
		int size = itemsY.size();
		for (int i = 0; i < size; i++) {
			BigInteger x = count(itemsY.get(i));
			BigInteger y = count(itemsX.get(i));
			count.add(x.multiply(y));
		}
		return count;
	}
	

	public CellExtent getExtent() {
		int size = itemsY.size();
		if (size == 0) {
			return new CellExtent(
			  mathX.ZERO_VALUE(), mathX.ZERO_VALUE(),
				mathY.ZERO_VALUE(), mathY.ZERO_VALUE());
		} else {
		  Y startY = null, endY = null;
		  X startX = null, endX = null;
			for (int i = 0; i < size; i++) {
				Extent<X> extentX = itemsX.get(i);
				Extent<Y> extentY = itemsY.get(i);
				if (startX == null || mathX.compare(extentX.start, (X) startX) < 0) {
				  startX = extentX.start.getValue();
				}
				if (endX == null || mathX.compare(extentX.end, (X) endX) > 0) {
				  endX = extentX.end.getValue();
				}
				if (startY == null || mathY.compare(extentY.start, (Y) startY) < 0) {
					startY = extentY.start.getValue();
				}
				if (endY == null || mathY.compare(extentY.end, (Y) endY) > 0) {
					endY = extentY.end.getValue();
				}
			}
			return new CellExtent(startX, endX, startY, endY);
		}
	}
	
	
	boolean hasOne() {
		int size = itemsY.size();
		return size == 1 && 
				mathY.compare(mathY.getCount(itemsY.get(0)), mathY.ONE_VALUE()) == 0 &&
				mathX.compare(mathX.getCount(itemsX.get(0)), mathX.ONE_VALUE()) == 0;
	}
	
	private BigInteger count(Extent e) {
		return e.end.toBigInteger().subtract(e.start.toBigInteger()).add(BigInteger.ONE);
	}

	public int size() {
		return itemsY.size();
	}
	
	public CellSet copy() {
		CellSet copy = new CellSet(mathX, mathY);
		int size = size();
		for (int i = 0; i < size; i++) {
			Extent e0 = itemsY.get(i);
			Extent e1 = itemsX.get(i);
			copy.itemsY.add(new Extent(mathY.create(e0.start), mathY.create(e0.end)));
			copy.itemsX.add(new Extent(mathX.create(e1.start), mathX.create(e1.end)));
		}
		return copy;
	}


	public void deleteY(Y start, Y end) {
		for (int i = 0, imax = itemsY.size(); i < imax; i++) {
			Extent.delete(mathY, itemsY, start, end);
		}
	}
	
	public void deleteX(X start, X end) {
		for (int i = 0, imax = itemsY.size(); i < imax; i++) {
			Extent.delete(mathX, itemsX, start, end);
		}
	}
	
	public void insertY(Y target, Y count) {
		for (int i = 0, imax = itemsY.size(); i < imax; i++) {
			Extent.insert(mathY, itemsY, target, count);
		}
	}
	
	public void insertX(X target, X count) {
		for (int i = 0, imax = itemsY.size(); i < imax; i++) {
			Extent.insert(mathX, itemsX, target, count);
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
