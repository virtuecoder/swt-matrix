package pl.netanel.swt.matrix.state;

import static pl.netanel.swt.matrix.state.Math.*;

import java.util.ArrayList;


/**
 * Stores number extents ensuring they are continuous as much as possible and do not overlap.
 *
 * @author Jacek
 * @created 09-11-2010
 */
class NumberSet<N extends MutableNumber> {
	protected Math math;
	ArrayList<Extent<N>> items;
	ArrayList<Extent<N>> toRemove;
	protected Extent modified;
	boolean sorted;
	protected transient int modCount;
	
	public NumberSet(Math factory) {
		this(factory, false);
	}
	
	public NumberSet(Math factory, boolean sorted) {
		items = new ArrayList<Extent<N>>();
		this.sorted = sorted;
		this.math = factory;
		toRemove = new ArrayList<Extent<N>>();
	};
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Extent e: items) {
			if (sb.length() > 0) sb.append(", ");
			if (math.compare(e.start, e.end) == 0) {
				sb.append(e.start);
			} else {
				sb.append(e.start).append("-").append(e.end);
			}
		}
		return sb.toString();
	}
	
	
	public boolean contains(MutableNumber n) {
		for (Extent e: items) {
			if (math.contains(e, n)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean contains(Extent o) {
		for (Extent e: items) {
			if (math.contains(e, o)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean add(MutableNumber n) {
		return add(n, n);
	}
	
	public boolean add(Extent e) {
		return add(e.start, e.end);
	}
	
	public boolean add(MutableNumber start, MutableNumber end) {

/*
 * Cases
 *    ---	 nv
 * 1 -       nv.start > ov.end 				    	  : continue;
 * 1 -       nv.start = ov.end + 1 				      : ov.end = nv.end;
 * 2 -- 	 nv.start <= ov.end && nv.end > ov.start  : p.start = e.end++; 
 * 3 -----	 nv.start >= ov.start && nv.end <= ov.end : return false;
 * 4   -	 e.start > p.start && e.end < p.end	      : insert (e.end++, e.start--); return true;
 * 5    --   e.end >= p.end && e.start <= p.end		  : insert (e.end++, p.end);
 * 6     -   nv.end < ov.start						  : insert (p.start, p.end); return true;
 */
		
		modified = null;
		boolean quit = false;
		int i = 0;
		
		for (;i < items.size(); i++) {
			Extent item = items.get(i);
			int compare = math.compare(item.start, item.end, start, end);
			
			switch (compare) {
			case AFTER: 
			case BEFORE: 		if (sorted) {quit = true; break; } else {continue;}
			case EQUAL:
			case OVERLAP:		return false;
			
			case CROSS_BEFORE: 			
			case CROSS_AFTER:		
			case ADJACENT_AFTER:		
			case ADJACENT_BEFORE:		
			case INSIDE:		
				extendItem(item, start, end); 
				break; 
			}
			if (quit) break;
		}
		if (modified == null) {
			items.add(new Extent(start.copy(), end.copy()));
		}
		for (Extent e: toRemove) {
			items.remove(e); 
		}
		toRemove.clear();
		modCount++;
		return true;
	}
	
	public void addAll(NumberSet<N> selection) {
		for (Extent<N> e: selection.items) {
			add(e);
		}
	}


	protected void extendItem(Extent existing, MutableNumber start, MutableNumber end) {
		if (modified == null) modified = existing;
		else toRemove.add(0, existing);
		modified.start.set(math.min(start, modified.start, existing.start));
		modified.end.set(math.max(end, modified.end, existing.end));
	}
	
	
	public boolean remove(Object o) {
		// Check and adjust the type of the argument 
		if (o instanceof Extent) {
			Extent remove = (Extent) o;
			return remove(remove.start, remove.end);
		}
		else if (o instanceof MutableNumber) {
			return remove((MutableNumber) o, (MutableNumber) o);
		}
		return false;
	}
	
	public boolean remove(MutableNumber start, MutableNumber end) {
		toRemove.clear();
		boolean modified = false;
		int i = 0;
		
		for (;i < items.size(); i++) {
			Extent<N> item = items.get(i);
			
			int location = math.compare(start, end, item.start, item.end);
			switch (location) {
			case AFTER: 			continue;
			case BEFORE: 		
				if (sorted) i = items.size(); // Quit the loop
				break;
			
			case CROSS_BEFORE:	
				item.start.set(end).increment();
				break;
				
			case CROSS_AFTER:	
				item.end.set(math.max(math.decrement(start), item.start));
				break;
				
			case EQUAL:	
			case OVERLAP:	
				toRemove.add(0, item);
				break;
				
			case INSIDE:
				MutableNumber newEnd = item.end.copy();
				item.end.set(math.max(math.decrement(start), item.start));
				items.add(i+1, new Extent(math.increment(end), newEnd));
			}
			modified = modified || location >= OVERLAP; 
		}
		for (Extent e: toRemove) {
			items.remove(e); 
		}
		if (modified) modCount++;
		return modified;
	}
	
	/**
	 * Return total number of numbers in this set.
	 * @return
	 */
	public MutableNumber getCount() {
		MutableNumber sum = math.create(0);
		for (Extent e: items) {
			sum.add(e.end).subtract(e.start).increment();
		}
		return sum;
	}
	
	/**
	 * Gets the count of indexes included in the given scope between start and end.
	 * @param start
	 * @param end
	 * @return
	 */
	public MutableNumber getCount(MutableNumber start, MutableNumber end) {
		MutableNumber sum = math.create(0);
		for (Extent e: items) {
			switch (math.compare(e.start, e.end, start, end)) {
			case BEFORE:				
			case ADJACENT_BEFORE:	continue;
			case AFTER:				
			case ADJACENT_AFTER:	return sum;
			case CROSS_BEFORE:		sum.add(e.end).subtract(start).increment(); 	break;
			case CROSS_AFTER:		sum.add(end).subtract(e.start).increment(); 	break;
			case INSIDE:			sum.add(e.end).subtract(e.start).increment();	break;
			case EQUAL:
			case OVERLAP:			sum.add(end).subtract(start).increment(); 		break;
			}
		}
		return sum;
	}
	
	
	public void clear() {
		items.clear();
		modCount++;
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}


	public boolean removeAll(NumberSet selection2) {
		boolean removed = false;
		if (selection2 == this) {
			removed = !items.isEmpty();
			items.clear();
			return removed;
		}
		for (Object extent: selection2.items) {
			removed = removed || remove(extent);
		}
		if (removed) modCount++;
		return removed;
	}

	public NumberSet copy() {
		NumberSet copy = new NumberSet(math);
		for (Extent e: items) {
			copy.items.add(e.copy());
		}
		return copy;
	}

	
//	public class BackwardSelectionIterator extends SelectionIterator {
//		public void init() { 
//			i = items.size() - 1;
//		}
//		
//		@Override
//		public boolean hasNext() {
//			return i >= 0;
//		}
//
//		@Override
//		public Extent next() {
//			item = items.get(i--);
//			return item;
//		}		
//	}
}
