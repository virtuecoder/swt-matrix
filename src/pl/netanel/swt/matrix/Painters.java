package pl.netanel.swt.matrix;

import java.util.ArrayList;
import java.util.Iterator;

import pl.netanel.util.Preconditions;

class Painters<N0 extends Number, N1 extends Number> implements Iterable<Painter<N0, N1>> {
  
  private ArrayList<Painter<N0, N1>> items;

//	private Zone zone;
//	private Matrix matrix;

	public Painters() {
	  items = new ArrayList<Painter<N0, N1>>();
	}

	public boolean add(Painter<N0, N1> painter) {
	  Preconditions.checkNotNullWithName(painter, "painter");
		checkUniqueness(painter);
		return items.add(painter);
	}
	
	public void add(int index, Painter<N0, N1> painter) {
	  Preconditions.checkNotNullWithName(painter, "painter");
		checkUniqueness(painter);
		items.add(index, painter);
	}
	
	public Painter<N0, N1> set(int index, Painter<N0, N1> painter) {
	  Preconditions.checkPositionIndex(index, items.size());
	  Preconditions.checkNotNullWithName(painter, "painter");
	  
	  // Check uniqueness of painters names if the painter at index has different name then the parameter
	  if (!items.get(index).name.equals(painter.name)) {
	    for (int i = 0, imax = items.size(); i < imax; i++) {
	      if (i == index) continue;
	      Painter painter2 = items.get(i);
	      Preconditions.checkArgument(!painter2.name.equals(painter.name), 
	        "A painter with '%s' name already exist in this collection", painter.name);
	    }
	  }
	  return items.set(index, painter);
	  
	}
	
	public void replacePainter(Painter<N0, N1> painter) {
	  Preconditions.checkNotNullWithName(painter, "painter");
		int indexOfPainter = indexOfPainter(painter.name);
		if (indexOfPainter == -1) {
			add(painter);
		} else {
			set(indexOfPainter, painter);
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
	
	
	public Painter<N0, N1> remove(int index) {
	  Preconditions.checkPositionIndex(index, items.size());
	  Painter<N0, N1> painter = items.remove(index);
	  if (painter != null) { 
	    painter.zone = null;
	    painter.matrix = null;
	  }
    return painter;
	}
	
	public boolean remove(Painter painter) {
	  Preconditions.checkNotNullWithName(painter, "painter");
	  boolean removed = items.remove(painter);
	  if (removed) {
	    painter.zone = null;
	    painter.matrix = null;
	  }
    return removed;
	}
	
	public Painter<N0, N1> get(int index) {
	  Preconditions.checkPositionIndex(index, items.size());
	  return items.get(index);
	}
	
	public int size() {
	  return items.size();
	}
	
	@Override public Iterator<Painter<N0, N1>> iterator() {
	  return items.iterator();
	}

	
  private void checkUniqueness(Painter<N0, N1> painter) {
    for (int i = 0, imax = items.size(); i < imax; i++) {
      Painter painter2 = items.get(i);
      Preconditions.checkArgument(!painter2.name.equals(painter.name), 
          "A painter with '%s' name already exist in the collection", painter.name);
    }
  }
}

//	public void setMatrixAndZone(Matrix matrix, Zone zone) {
//		this.matrix = matrix;
//		this.zone = zone;
//	}
//	private void setMatrixAndZone(Painter painter) {
//		if (painter.scope == Painter.SCOPE_CELLS_HORIZONTALLY ||
//				painter.scope == Painter.SCOPE_CELLS_VERTICALLY) 
//		{
//			painter.matrix = matrix;
//			painter.zone = zone;
//		}
//	}