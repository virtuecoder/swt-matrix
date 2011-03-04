package pl.netanel.swt.matrix;


public class NumberOrder<N extends MutableNumber> extends NumberSet<N> {

	private final MutableNumber count, last;

	public NumberOrder(Math math) {
		super(math);
		count = math.create(0);
		last = math.create(0);
	}

	public void setCount(MutableNumber newCount) {
		int compare = math.compare(newCount, count);
		if (compare < 0) {
			remove(newCount, last);
			last.set(newCount).decrement();
		} 
		else if (compare > 0) {
			MutableNumber newLast = math.decrement(newCount);
			if (items.isEmpty()) {
				items.add(new Extent(count.copy(), newLast));
			} else {
				Extent e = items.get(items.size() - 1);
				if (math.compare(e.end, last) == 0) {
					e.end.set(newLast);
				} else {
					items.add(new Extent(count.copy(), newLast));
				}
			}
			last.set(newLast);
		} else {
			return;
		}
		count.set(newCount);
	}

	public void move(MutableNumber start, MutableNumber end, MutableNumber target) {
		// Adjust the target if subject contains it
		if (math.compare(start, target) <= 0 && math.compare(target, end) <= 0) {
//			target = math.increment(end);
			return;
		} else {
			target = target.copy();
		}
		
		remove(start, end);
		
		// Find the position i to insert
		int i = 0;
		for (; i < items.size(); i++) {
			Extent e = items.get(i);
			if (math.contains(e, target)) {
				if (math.compare(target, e.start) == 0) {
					break;
				} 
				else {
					MutableNumber end2 = math.create(e.end);
					e.end.set(target).decrement();
					items.add(++i, new Extent(target, end2));
					break;
				}
			}
		}

		items.add(i, new Extent(start.copy(), end.copy()));
		mergeAdjacentExtents();
	}

	private void mergeAdjacentExtents() {
		for (int i = items.size(); i-- > 1;) {
			Extent e1 = items.get(i-1);
			Extent e2 = items.get(i);
			if (math.compare(math.increment(e1.end), e2.start) == 0) {
				e1.end.set(e2.end);
				items.remove(i);
			}
		}
	}

	public MutableNumber indexOf(MutableNumber modelIndex) {
		if (math.compare(modelIndex, count) >= 0 || 
			math.compare(modelIndex, math.ZERO()) < 0) return modelIndex;
			
		MutableNumber sum = math.create(0);	
		for (Extent e: items) {
			if (math.contains(e, modelIndex)) {
				return math.subtract(modelIndex, e.start).add(sum);
			}
			sum.add(e.end).subtract(e.start).increment();
		}
		throw new RuntimeException("Cannot find index of " + modelIndex);
	}

}
