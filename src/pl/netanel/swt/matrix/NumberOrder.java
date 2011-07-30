package pl.netanel.swt.matrix;

import static pl.netanel.swt.matrix.Math.*;


class NumberOrder<N extends Number> extends NumberSet<N> {

	private N count; 

	public NumberOrder(Math<N> math) {
		super(math);
		count = math.ZERO_VALUE();
	}

	public void setCount(N newCount) {
		int compare = math.compare(newCount, count);
		N last = math.decrement(count);
		if (compare < 0) {
			remove(newCount, last);
		} 
		else if (compare > 0) {
			MutableNumber<N> newLast = math.create(newCount).decrement();
			if (items.isEmpty()) {
				items.add(new MutableExtent(math.create(count), newLast));
			} else {
				MutableExtent<N> e = items.get(items.size() - 1);
				if (math.compare(e.end(), last) == 0) {
					e.end.set(newLast);
				} else {
					items.add(new MutableExtent(math.create(count), newLast));
				}
			}
		} else {
			return;
		}
		count = newCount;
	}

	public void move(N start, N end, N target) {
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
			MutableExtent<N> e = items.get(i);
			if (math.contains(e.start(), e.end(), target)) {
				if (math.compare(target, e.start()) == 0) {
					break;
				} 
				else {
					MutableNumber end2 = math.create(e.end);
					e.end.set(target).decrement();
					items.add(++i, new MutableExtent(math.create(target), end2));
					break;
				}
			}
		}

		items.add(i, new MutableExtent(math.create(start), math.create(end)));
		mergeAdjacentExtents();
	}
	
	/**
	 * Moves the given set of numbers before the given index
	 * order: 0 1 2 3 4, move(set(1 3), 1) -> 0 1 3 2 4
	 * @param set
	 * @param target
	 */
	public void move(NumberSet<N>set, N target) {
		N target2 = set.firstExcluded(target);
		assert !set.contains(target2); 
		
		removeAll(set);
		
		// Find the position i to insert
		int i = 0;
		for (; i < items.size(); i++) {
			MutableExtent<N> e = items.get(i);
			if (math.contains(e.start(), e.end(), target2)) {
				if (math.compare(target2, e.start()) == 0) {
					break;
				} 
				else {
					MutableNumber end2 = math.create(e.end);
					e.end.set(target2).decrement();
					items.add(++i, new MutableExtent(math.create(target2), end2));
					break;
				}
			}
		}

		for (MutableExtent<N> e: set.items) {
			items.add(i++, new MutableExtent(math.create(e.start), math.create(e.end)));
		}
		mergeAdjacentExtents();
	}

	private void mergeAdjacentExtents() {
		for (int i = items.size(); i-- > 1;) {
			MutableExtent<N> e1 = items.get(i-1);
			MutableExtent<N> e2 = items.get(i);
			if (math.compare(math.increment(e1.end()), e2.start()) == 0) {
				e1.end.set(e2.end);
				items.remove(i);
			}
		}
	}

	public N indexOf(N modelIndex) {
//		System.out.println(modelIndex.getClass());
		if (math.compare(modelIndex, count) >= 0 || 
			math.compare(modelIndex, math.ZERO_VALUE()) < 0) return modelIndex;
			
		MutableNumber<N> sum = math.create(0);	
		for (MutableExtent<N> e: items) {
			if (math.contains(e.start(), e.end(), modelIndex)) {
				return sum.add(modelIndex).subtract(e.start).getValue();
			}
			sum.add(e.end).subtract(e.start).increment();
		}
		throw new RuntimeException("Cannot find index of " + modelIndex);
	}

	// TODO Merge inserted extent with the next one
	public void insert(N target, N count) {
		int imax = items.size();
		if (imax == 0) {
			items.add(new MutableExtent(math.create(0), math.create(0)));
		} else {
			for (int i = 0; i < imax; i++) {
				MutableExtent<N> e = items.get(i);
				int compare = math.compare(target, e.start());
				if (compare < 0) {
					e.start.add(count);
					e.end.add(count);
				}
				else if (math.compare(target, math.increment(e.end())) <= 0) {
					e.end.add(count);
				}
			}
		}
//		if (position != -1) {
//			items.add(position, new Extent(math.create(target), math.create(target).add(count).decrement()));
//		}
	};
	
	public void insert2(N target, N count) {
		MutableNumber<N> mutableEnd = math.create(target).add(count).decrement();
		N tstart = target;
		N tend = mutableEnd.getValue();
		int i = 0, imax = items.size();
		for (; i < imax; i++) {
			MutableExtent<N> e = items.get(i);

			N start = e.start(), end = e.end();
			int compare = math.compare(tstart, tend, start, end);
			switch (compare) {
			case BEFORE: case ADJACENT_BEFORE:
				e.start.add(count);
				e.end.add(count);
				break;

			case CROSS_AFTER:
				MutableNumber<N> delta = math.create(tend).subtract(start).increment();
				e.start.add(delta);
				e.end.add(delta);
				break;

			case CROSS_BEFORE:
				delta = math.create(tend).subtract(start).increment();
				e.start.add(delta);
				e.end.add(delta);
				break;
			}
			if (math.compare(target, start) <= 0) {
			}
			else if (math.compare(target, e.end()) <= 0) {
				if (math.compare(target, start) == 0) {

					e.start.set(math.increment(target));
				}
				e.end.add(count);
			}
			items.add(i, new MutableExtent(math.create(target), mutableEnd));
		}
	};

}
