/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import java.util.ArrayList;

import pl.netanel.util.IntArray;


/**
 * Manages a set of exclusive cell spans.
 */
class CellSpanSet<X extends Number, Y extends Number> {
  private final NumberOrder<X> orderX;
  private final NumberOrder<Y> orderY;
	final ArrayList<MutableExtent<X>> itemsX;
	final ArrayList<MutableExtent<Y>> itemsY;
  private Math<X> mathX;
  private Math<Y> mathY;

	public CellSpanSet(NumberOrder<X> orderX, NumberOrder<Y> orderY) {
		this.orderX = orderX;
    this.orderY = orderY;
    mathX = orderX.math;
    mathY = orderY.math;
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
			sb.append(itemsX.get(i));
			sb.append(", ");
			sb.append(itemsY.get(i));
			sb.append("]");
		}
		return sb.toString();
	}

	public void add(X startX, X countX, Y startY, Y countY) {
	  itemsX.add(new MutableExtent<X>(mathX.create(startX), mathX.create(countX)));
    itemsY.add(new MutableExtent<Y>(mathY.create(startY), mathY.create(countY)));
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
        containsY = orderY.getSpanExtents(ey).contains(indexY);
			}
			if (indexX != null) {
			  containsX = orderX.getSpanExtents(ex).contains(indexX);
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

	/**
	 * If one of the arguments is null only the containment of the other one is checked.
	 * @param indexX
	 * @param indexY
	 * @return
	 */
	public boolean contains(X indexX, X countX, Y indexY, Y countY) {
	  int size = itemsX.size();
	  for (int i = 0; i < size; i++) {
	    MutableExtent<X> ex = itemsX.get(i);
	    MutableExtent<Y> ey = itemsY.get(i);

	    boolean containsX = false, containsY = false;
	    if (indexY != null) {
	      containsY = orderY.getSpanExtents(ey).contains(indexY, countY);
	    }
	    if (indexX != null) {
	      containsX = orderX.getSpanExtents(ex).contains(indexX, countX);
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

	/**
	 * Goes over each span and extends the given bounds if any of the merged cells
	 * is included in the given bouds
   * @param extent accumulates computation, visitor pattern
   */
  public CellExtent<X, Y> overlap(X startX, X endX, Y startY, Y endY) {
    // assert startX, endX, startY, endY != null
    MutableExtent<X> maxX = new MutableExtent<X>(mathX.create(startX), mathX.create(endX));
    MutableExtent<Y> maxY = new MutableExtent<Y>(mathY.create(startY), mathY.create(endY));
    X startIndexX = orderX.indexOf(startX);
    X endIndexX = orderX.indexOf(endX);
    Y startIndexY = orderY.indexOf(startY);
    Y endIndexY = orderY.indexOf(endY);

    int size = itemsX.size();
    for (int i = 0; i < size; i++) {
      MutableExtent<X> ex = itemsX.get(i);
      MutableExtent<Y> ey = itemsY.get(i);
      MutableExtent<X> extentX = new MutableExtent<X>(mathX.create(startX), mathX.create(endX));
      MutableExtent<Y> extentY = new MutableExtent<Y>(mathY.create(startY), mathY.create(endY));

      if (
        orderX.overlap(ex.start.getValue(), ex.end.getValue(), extentX) &&
        orderY.overlap(ey.start.getValue(), ey.end.getValue(), extentY))
      {
        if (mathX.compare(startIndexX, orderX.indexOf(extentX.start.getValue())) > 0) {
          maxX.start.set(extentX.start);
        }
        if (mathX.compare(endIndexX, orderX.indexOf(extentX.end.getValue())) < 0) {
          maxX.end.set(extentX.end);
        }
        if (mathY.compare(startIndexY, orderY.indexOf(extentY.start.getValue())) > 0) {
          maxY.start.set(extentY.start);
        }
        if (mathY.compare(endIndexY, orderY.indexOf(extentY.end.getValue())) < 0) {
          maxY.end.set(extentY.end);
        }
      }
    }
    return CellExtent.createUnchecked(
      maxX.start.getValue(), maxX.end.getValue(),
      maxY.start.getValue(), maxY.end.getValue());
  }



	/**
	 * Returns the index of span containing the given cell
	 * in the internal list of spans or -1 if such span does not exist.
	 * @param indexX must be not null
	 * @param indexY must be not null
	 * @return
	 */
	int indexOf(X indexX, Y indexY) {
	  int size = itemsX.size();
	  for (int i = 0; i < size; i++) {
	    MutableExtent<X> ex = itemsX.get(i);
	    MutableExtent<Y> ey = itemsY.get(i);

	    boolean containsX = orderX.getSpanExtents(ex).contains(indexX);
      boolean containsY = orderY.getSpanExtents(ey).contains(indexY);
	    if (containsX && containsY) {
	      return i;
	    }
	  }
	  return -1;
	}



	/**
	 * Removes cell groups that contain any cell from the given range.
	 * @param startX
	 * @param endX
	 * @param startY
	 * @param endY
	 * @return true if anything was removed
	 */
	public boolean removeContaining(X startX, X countX, Y startY, Y countY) {
	  IntArray toRemove = new IntArray();
	  for (int i = 0, size = itemsY.size(); i < size; i++) {
      MutableExtent<X> spanX = itemsX.get(i);
      MutableExtent<Y> spanY = itemsY.get(i);

      boolean containsX = orderX.getSpanExtents(spanX).contains(startX, countX);
      boolean containsY = orderY.getSpanExtents(spanY).contains(startY, countY);

      if (containsX && containsY) {
        toRemove.add(i);
      }
	  }
	  boolean removed = !toRemove.isEmpty();
    for (int j = toRemove.size(); j-- > 0;) {
      itemsY.remove(toRemove.get(j));
      itemsX.remove(toRemove.get(j));
    }
    return removed;
	}


	public boolean isEmpty() {
		return itemsY.isEmpty();
	}

	public void clear() {
		itemsY.clear();
		itemsX.clear();
	}

	public int size() {
		return itemsY.size();
	}

	public CellSpanSet<X, Y> copy() {
		CellSpanSet<X, Y> copy = new CellSpanSet<X, Y>(orderX, orderY);
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
		MutableExtent.deleteSpan(mathY, itemsY, start, end);
	}

	public void deleteX(X start, X end) {
		MutableExtent.deleteSpan(mathX, itemsX, start, end);
	}

	public void insertY(Y target, Y count) {
	  MutableExtent.insertSpan(mathY, itemsY, target, count);
	}

	public void insertX(X target, X count) {
		MutableExtent.insertSpan(mathX, itemsX, target, count);
	}

	/**
	 * Returns extents with start and count or null if does not exist.
	 * @param x
	 * @param y
	 * @return
	 */
	public CellExtent<X, Y> getSpan(X x, Y y) {
	  for (int i = 0; i < itemsX.size(); i++) {
	    MutableExtent<X> spanX = itemsX.get(i);
	    MutableExtent<Y> spanY = itemsY.get(i);

	    if (orderX.getSpanExtents(spanX).contains(x) &&
	        orderY.getSpanExtents(spanY).contains(y))
	    {
	      return CellExtent.createUnchecked(
	        spanX.start.getValue(), spanX.end.getValue(),
	        spanY.start.getValue(), spanY.end.getValue());
	    }
	  }
	  return null;
	}

	public class SpanSequence implements Sequence {
	  X indexX;
	  Y indexY;
    private int i;

    public SpanSequence(X indexX, Y indexY) {
      super();
      this.indexX = indexX;
      this.indexY = indexY;
    }

    @Override
    public void init() {
      i = 0;
    }

    @Override
    public boolean next() {
      if (i == size()) return false;
      return false;
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
