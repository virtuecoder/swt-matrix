/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;

import pl.netanel.util.IntArray;



/**
 * Manages a consistent, not overlapping set of cell ranges.
 * It can model a cell selection.
 */
class CellSet<X extends Number, Y extends Number> {
	final ArrayList<MutableExtent<X>> itemsX;
	final ArrayList<MutableExtent<Y>> itemsY;
	final Math<X> mathX;
	final Math<Y> mathY;
	private boolean insertNew;

	public CellSet(Math<X> mathX, Math<Y> mathY) {
		this.mathX = mathX;
		this.mathY = mathY;
		itemsX = new ArrayList<MutableExtent<X>>();
		itemsY = new ArrayList<MutableExtent<Y>>();
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


	/**
	 * If one of the arguments is null only the containment of the other one is checked.
	 * @param indexX
	 * @param indexY
	 * @return
	 */
	public boolean contains(X indexX, Y indexY) {
		int size = itemsX.size();
		for (int i = 0; i < size; i++) {
			MutableExtent<X> ex = itemsX.get(i);
			MutableExtent<Y> ey = itemsY.get(i);

			boolean containsX = false, containsY = false;
			if (indexY != null) {
        containsY = mathY.contains(ey.start(), ey.end(), indexY);
			}
			if (indexX != null) {
			  containsX = mathX.contains(ex.start(), ex.end(), indexX);
			}
			if (indexX == null && containsY ||
			    indexY == null && containsX ||
	        containsX && containsY)
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
			MutableExtent<X> itemX = itemsX.get(i);
			MutableExtent<Y> itemY = itemsY.get(i);

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
		  itemsX.add(new MutableExtent<X>(mathX.create(startX), mathX.create(endX)));
			itemsY.add(new MutableExtent<Y>(mathY.create(startY), mathY.create(endY)));
		}
	}

	public void remove(X startX, X endX, Y startY, Y endY) {
		IntArray toRemove = new IntArray();

		int i = 0;
		int size = itemsY.size();
		for (;i < size; i++) {
		  MutableExtent<X> itemX = itemsX.get(i);
			MutableExtent<Y> itemY = itemsY.get(i);

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

	/**
	 * Removes cell groups that contain any cell from the given range.
	 * @param startX
	 * @param endX
	 * @param startY
	 * @param endY
	 * @return true if anything was removed
	 */
	public boolean removeContaining(X startX, X endX, Y startY, Y endY) {
	  IntArray toRemove = new IntArray();
	  int i = 0;
	  int size = itemsY.size();
	  for (;i < size; i++) {
      MutableExtent<X> itemX = itemsX.get(i);
      MutableExtent<Y> itemY = itemsY.get(i);

      X startXb = itemX.start.getValue(), endXb = itemX.end.getValue();
      Y startYb = itemY.start.getValue(), endYb = itemY.end.getValue();

      int esX = mathX.compare(endX, startXb);
      int seX = mathX.compare(startX, endXb);
      int esY = mathY.compare(endY, startYb);
      int seY = mathY.compare(startY, endYb);

      // Separate
      if (esY < 0 || seY > 0 || esX < 0 || seX > 0) continue;

      toRemove.add(i);
	  }
	  boolean removed = !toRemove.isEmpty();
    for (int j = toRemove.size(); j-- > 0;) {
      itemsY.remove(toRemove.get(j));
      itemsX.remove(toRemove.get(j));
    }
    return removed;
	}


	private void insert(X startX, X endX, Y startY, Y endY) {
		itemsY.add(new MutableExtent<Y>(mathY.create(startY), mathY.create(endY)));
		itemsX.add(new MutableExtent<X>(mathX.create(startX), mathX.create(endX)));
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


	/**
	 * Returns a smallest cell extent containing all cell extents in the set.
	 * <p>
	 * If the set is empty returns extent (0,0,0,0).
	 * @return a smallest cell extent containing all cell extents in the set
	 */
	public CellExtent<X, Y> getExtent() {
		int size = itemsY.size();
		if (size == 0) {
			return null;
		} else {
		  Y startY = null, endY = null;
		  X startX = null, endX = null;
			for (int i = 0; i < size; i++) {
				MutableExtent<X> extentX = itemsX.get(i);
				MutableExtent<Y> extentY = itemsY.get(i);
				if (startX == null || mathX.compare(extentX.start, startX) < 0) {
				  startX = extentX.start.getValue();
				}
				if (endX == null || mathX.compare(extentX.end, endX) > 0) {
				  endX = extentX.end.getValue();
				}
				if (startY == null || mathY.compare(extentY.start, startY) < 0) {
					startY = extentY.start.getValue();
				}
				if (endY == null || mathY.compare(extentY.end, endY) > 0) {
					endY = extentY.end.getValue();
				}
			}
			return CellExtent.createUnchecked(startX, endX, startY, endY);
		}
	}


	/**
	 * Returns extent that contains the given cell or null if such extent does not exist.
	 *
	 * @param itemX
	 * @param itemY
	 * @return
	 */
	public CellExtent<X, Y> getExtent(X x, Y y) {
	  for (int i = itemsY.size(); i-- > 0;) {
	    MutableExtent<X> extentX = itemsX.get(i);
	    MutableExtent<Y> extentY = itemsY.get(i);
	    if (mathX.contains(extentX, x) && mathY.contains(extentY, y)) {
	      return CellExtent.createUnchecked(
	          extentX.start.getValue(), extentX.end.getValue(),
	          extentY.start.getValue(), extentY.end.getValue());
	    }
	  }
	  return null;
	}


	boolean hasOne() {
		int size = itemsY.size();
		return size == 1 &&
				mathY.compare(mathY.getCount(itemsY.get(0)), mathY.ONE_VALUE()) == 0 &&
				mathX.compare(mathX.getCount(itemsX.get(0)), mathX.ONE_VALUE()) == 0;
	}

	private BigInteger count(MutableExtent<? extends Number> e) {
		return e.end.toBigInteger().subtract(e.start.toBigInteger()).add(BigInteger.ONE);
	}

	public int size() {
		return itemsY.size();
	}

	public CellSet<X, Y> copy() {
		CellSet<X, Y> copy = new CellSet<X, Y>(mathX, mathY);
		int size = size();
		for (int i = 0; i < size; i++) {
		  MutableExtent<X> e1 = itemsX.get(i);
			MutableExtent<Y> e0 = itemsY.get(i);
			copy.itemsX.add(new MutableExtent<X>(mathX.create(e1.start), mathX.create(e1.end)));
			copy.itemsY.add(new MutableExtent<Y>(mathY.create(e0.start), mathY.create(e0.end)));
		}
		return copy;
	}


	public void deleteY(Y start, Y end) {
		IntArray toRemove = MutableExtent.delete(mathY, itemsY, start, end);
    for (int j = 0; j < toRemove.size(); j++) {
      itemsX.remove(toRemove.get(j));
    }
	}

	public void deleteX(X start, X end) {
		IntArray toRemove = MutableExtent.delete(mathX, itemsX, start, end);
		for (int j = 0; j < toRemove.size(); j++) {
		  itemsY.remove(toRemove.get(j));
    }
	}

	public void insertY(Y target, Y count) {
		MutableExtent.insert(mathY, itemsY, target, count);
	}

	public void insertX(X target, X count) {
	  MutableExtent.insert(mathX, itemsX, target, count);
	}

	/**
	 * Creates sequence of cells in the given range of rows moving horizontally first.
	 */
  public NumberPairSequence2<X, Y> sequenceY(final Y start, final Y end) {
    return new NumberPairSequence2<X, Y>(this) {
      @Override
      public void init() {
        super.init();
      }

      @Override
      public boolean next() {
        if (size == 0) return false;
        X x = indexX.increment().getValue();
        if (set.mathX.compare(x, ex.end()) > 0) {
          Y y = indexY.increment().getValue();
          if (set.mathY.compare(y, ey.end()) > 0 || set.mathY.compare(y, end) > 0) {
            if (++i >= size) return false;
            ey = set.itemsY.get(i);
            ex = set.itemsX.get(i);
            indexY.set(mathY.min(ey.start.getValue(), start));
          }
          indexX.set(ex.start);
        }
        return true;
      }
    };
  }


  public Iterator<CellExtent<X, Y>> extentIterator(NumberSet.Query<X> queryX, NumberSet.Query<Y> queryY) {
    return new ExtentPairSequence<X, Y>(this).iterator();
  }

  public Iterator<Cell<X, Y>> numberIterator(NumberSet.Query<X> queryX, NumberSet.Query<X> queryY) {
    return new NumberPairSequence<X, Y>(new ExtentPairSequence<X, Y>(this)).iterator();
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
