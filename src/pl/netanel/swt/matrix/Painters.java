package pl.netanel.swt.matrix;

import java.util.ArrayList;

import pl.netanel.util.DelegatingList;
import pl.netanel.util.Preconditions;

class Painters<N0 extends Number, N1 extends Number> extends DelegatingList<Painter<N0, N1>> {

	public Painters() {
		super(new ArrayList());
	}

	@Override
	public void add(int index, Painter<N0, N1> painter) {
		checkUniqueness(painter);
		super.add(index, painter);
	}

	@Override
	public boolean add(Painter<N0, N1> painter) {
		checkUniqueness(painter);
		return super.add(painter);
	}
	
	public void replacePainter(Painter<N0, N1> painter) {
		set(indexOfPainter(painter.name), painter);
	}

	
	private void checkUniqueness(Painter<N0, N1> painter) {
		for (int i = 0, imax = size(); i < imax; i++) {
			Painter painter2 = get(i);
			Preconditions.checkArgument(!painter2.name.equals(painter.name), 
					"A painter with '{0}' name already exist in this collection", painter.name);
		}
	}
	
	@Override
	public Painter<N0, N1> set(int index, Painter<N0, N1> painter) {
		// Check uniqueness of painters names
		for (int i = 0, imax = size(); i < imax; i++) {
			if (i == index) continue;
			Painter painter2 = get(i);
			Preconditions.checkArgument(!painter2.name.equals(painter.name), 
				"A painter with '{0}' name already exist in this collection", painter.name);
		}
		return super.set(index, painter);
	}
	
	public int indexOfPainter(String name) {
		for (int i = 0, imax = size(); i < imax; i++) {
			if (get(i).name.equals(name)) {
				return i;
			}
		}
		return -1;
	}
}
