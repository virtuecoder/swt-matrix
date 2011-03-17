package pl.netanel.swt.matrix;


class NumberOrder extends NumberSet {

	private final MutableNumber count; 
	private Number last;

	public NumberOrder(Math math) {
		super(math);
		count = math.create(0);
	}

	public void setCount(Number newCount) {
		int compare = math.compare(newCount, count.getValue());
		if (compare < 0) {
			remove(newCount, last);
			last = math.decrement(newCount);
		} 
		else if (compare > 0) {
			MutableNumber newLast = math.create(newCount).decrement();
			if (items.isEmpty()) {
				items.add(new Extent(count.copy(), newLast));
			} else {
				Extent e = items.get(items.size() - 1);
				if (math.compare(e.end(), last) == 0) {
					e.end.set(newLast);
				} else {
					items.add(new Extent(count.copy(), newLast));
				}
			}
			last = newLast.getValue();
		} else {
			return;
		}
		count.set(newCount);
	}

	public void move(Number start, Number end, Number target) {
		// Adjust the target if subject contains it
		if (math.compare(start, target) <= 0 && math.compare(target, end) <= 0) {
//			target = math.increment(end);
			return;
		} 
//		else {
//			target = math.create(target);
//		}
		
		remove(start, end);
		
		// Find the position i to insert
		int i = 0;
		for (; i < items.size(); i++) {
			Extent e = items.get(i);
			if (math.contains(e.start(), e.end(), target)) {
				if (math.compare(target, e.start()) == 0) {
					break;
				} 
				else {
					MutableNumber end2 = math.create(e.end);
					e.end.set(target).decrement();
					items.add(++i, new Extent(math.create(target), end2));
					break;
				}
			}
		}

		items.add(i, new Extent(math.create(start), math.create(end)));
		mergeAdjacentExtents();
	}

	private void mergeAdjacentExtents() {
		for (int i = items.size(); i-- > 1;) {
			Extent e1 = items.get(i-1);
			Extent e2 = items.get(i);
			if (math.compare(math.increment(e1.end).getValue(), e2.start()) == 0) {
				e1.end.set(e2.end);
				items.remove(i);
			}
		}
	}

	public Number indexOf(Number modelIndex) {
//		System.out.println(modelIndex.getClass());
		if (math.compare(modelIndex, count.getValue()) >= 0 || 
			math.compare(modelIndex, math.ZERO().getValue()) < 0) return modelIndex;
			
		MutableNumber sum = math.create(0);	
		for (Extent e: items) {
			if (math.contains(e.start(), e.end(), modelIndex)) {
				return sum.add(math.getValue(modelIndex)).subtract(e.start).getValue();
			}
			sum.add(e.end).subtract(e.start).increment();
		}
		throw new RuntimeException("Cannot find index of " + modelIndex);
	}

}
