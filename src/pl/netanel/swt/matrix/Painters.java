package pl.netanel.swt.matrix;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

import pl.netanel.util.Preconditions;

class Painters<X extends Number, Y extends Number> implements Iterable<Painter<X, Y>> {

  private final ArrayList<Painter<X, Y>> items;

//	private Zone zone;
//	private Matrix matrix;

	public Painters() {
	  items = new ArrayList<Painter<X, Y>>();
	}

	public boolean add(Painter<X, Y> painter) {
	  Preconditions.checkNotNullWithName(painter, "painter");
		checkUniqueness(painter);
		return items.add(painter);
	}

	public void add(int index, Painter<X, Y> painter) {
	  Preconditions.checkNotNullWithName(painter, "painter");
		checkUniqueness(painter);
		items.add(index, painter);
	}

	public Painter<X, Y> set(int index, Painter<X, Y> painter) {
	  Preconditions.checkPositionIndex(index, items.size());
	  Preconditions.checkNotNullWithName(painter, "painter");

	  // Check uniqueness of painters names if the painter at index has different name then the parameter
	  if (!items.get(index).name.equals(painter.name)) {
	    for (int i = 0, imax = items.size(); i < imax; i++) {
	      if (i == index) continue;
	      Painter<X, Y> painter2 = items.get(i);
	      Preconditions.checkArgument(!painter2.name.equals(painter.name),
	        "A painter with '%s' name already exist in this collection", painter.name);
	    }
	  }
	  return items.set(index, painter);

	}

	public void replacePainter(Painter<X, Y> painter) {
	  Preconditions.checkNotNullWithName(painter, "painter");
		int indexOfPainter = indexOfPainter(painter.name);
		if (indexOfPainter == -1) {
			add(painter);
		} else {
			set(indexOfPainter, painter);
		}
	}

	public void replacePainterPreserveStyle(Painter<X, Y> painter) {
	  Preconditions.checkNotNullWithName(painter, "painter");
	  int indexOfPainter = indexOfPainter(painter.name);
	  if (indexOfPainter == -1) {
	    add(painter);
	  } else {
	    Painter<X, Y> painter2 = get(indexOfPainter);
	    set(indexOfPainter, painter);
	    painter.setStyle(painter2.style);
	  }
	}

	public int indexOfPainter(String name) {
	  Preconditions.checkNotNullWithName(name, "name");
		for (int i = 0, imax = items.size(); i < imax; i++) {
			if (items.get(i).name.equals(name)) {
				return i;
			}
		}
		return -1;
	}


	public Painter<X, Y> remove(int index) {
	  Preconditions.checkPositionIndex(index, items.size());
	  Painter<X, Y> painter = items.remove(index);
	  if (painter != null) {
	    painter.zone = null;
	    painter.matrix = null;
	  }
    return painter;
	}

	public boolean remove(Painter<X, Y> painter) {
	  Preconditions.checkNotNullWithName(painter, "painter");
	  boolean removed = items.remove(painter);
	  if (removed) {
	    painter.zone = null;
	    painter.matrix = null;
	  }
    return removed;
	}

	public Painter<X, Y> get(int index) {
	  Preconditions.checkPositionIndex(index, items.size());
	  return items.get(index);
	}

	public int size() {
	  return items.size();
	}

	@Override public Iterator<Painter<X, Y>> iterator() {
	  return items.iterator();
	}


  private void checkUniqueness(Painter<X, Y> painter) {
    for (int i = 0, imax = items.size(); i < imax; i++) {
      Painter<X, Y> painter2 = items.get(i);
      Preconditions.checkArgument(!painter2.name.equals(painter.name),
          "A painter with '%s' name already exist in the collection", painter.name);
    }
  }


  public Point computeSize(X x, Y y, int wHint, int hHint) {
    Point result = new Point(0, 0);
    GC gc = new GC(Display.getDefault());
    for (Painter<X, Y> painter: items) {
      painter.init(gc);
      Point size = painter.computeSize( x, y, wHint, hHint);
      result.x = java.lang.Math.max(result.x, size.x);
      result.y = java.lang.Math.max(result.y, size.y);
    }
    gc.dispose();
    return result;
  }

}

//	public void setMatrixAndZone(Matrix matrix, Zone zone) {
//		this.matrix = matrix;
//		this.zone = zone;
//	}
//	private void setMatrixAndZone(Painter<X, Y> painter) {
//		if (painter.scope == Painter.SCOPE_CELLS_HORIZONTALLY ||
//				painter.scope == Painter.SCOPE_CELLS_Y)
//		{
//			painter.matrix = matrix;
//			painter.zone = zone;
//		}
//	}