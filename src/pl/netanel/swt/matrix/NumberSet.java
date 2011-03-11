package pl.netanel.swt.matrix;

import static pl.netanel.swt.matrix.Math.*;

import java.util.ArrayList;



/**
 * Stores number extents ensuring they are continuous as much as possible and do not overlap.
 *
 * @author Jacek
 * @created 09-11-2010
 */
class NumberSet<N extends MutableNumber> {
	protected Math math;
	protected boolean sorted;
	ArrayList<Extent<N>> items;
	protected ArrayList<Extent<N>> toRemove;
	
	protected Extent modified;
	protected transient int modCount;

	
	public NumberSet(Math math) {
		this(math, false);
	}
	
	public NumberSet(Math math, boolean sorted) {
		this.sorted = sorted;
		this.math = math;
		items = new ArrayList<Extent<N>>();
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
	
	/**
	 * Shortcut for <code>add(n, n)</code>.
	 * <p>
	 * Instead of adding a range of numbers one at a time it is recommended to
	 * add them with <code>add(start, end)</code> method.
	 * 
	 * @param n number to be added.
	 * @return true if the receiver has been modified by this operation, or false otherwise
	 * @see #add(MutableNumber, MutableNumber)
	 */
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
			case AFTER: 		if (sorted) {quit = true; } break; 
//			case BEFORE: 		break;
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
			items.add(i, new Extent(start.copy(), end.copy()));
		}
		for (Extent e: toRemove) {
			items.remove(e); 
		}
		toRemove.clear();
		modCount++;
		return true;
	}
	
	public void addAll(NumberSet<N> set) {
		for (Extent<N> e: set.items) {
			add(e);
		}
	}


	protected void extendItem(Extent existing, MutableNumber start, MutableNumber end) {
		if (modified == null) modified = existing;
		else toRemove.add(0, existing);
		modified.start.set(math.min(start, modified.start, existing.start));
		modified.end.set(math.max(end, modified.end, existing.end));
	}
	
	/**
	 * Shortcut for <code>remove(n, n)</code>.
	 * <p>
	 * Instead of removing a range of numbers one at a time it is recommended to
	 * remove them with <code>remove(start, end)</code> method.
	 * 
	 * @param n number to remove
	 * @return true if the receiver has been modified by this operation, 
	 * or false otherwise
	 * @see #remove(MutableNumber, MutableNumber)
	 */
	public boolean remove(MutableNumber n) {
		return remove(n, n);
	}
	
	public boolean remove(Extent e) {
		return remove(e.start, e.end);
	}
	
	public boolean remove(MutableNumber start, MutableNumber end) {
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
		toRemove.clear();
		if (modified) modCount++;
		return modified;
	}
	
	public boolean removeAll(NumberSet<N> set) {
		boolean removed = false;
		if (set == this) {
			removed = !items.isEmpty();
			items.clear();
			return removed;
		}
		for (Extent<N> e: set.items) {
			removed = removed || remove(e);
		}
		if (removed) modCount++;
		return removed;
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
	
	
	/**
	 * Removes all of the elements from this set (optional operation). The set
	 * will be empty after this call returns.
	 */
	public void clear() {
		items.clear();
		modCount++;
	}
	
	public void replace(NumberSet set) {
		items.clear();
		for (Extent<N> e: items) {
			items.add(e.copy());
		}
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}


	public NumberSet copy() {
		NumberSet copy = new NumberSet(math);
		for (Extent e: items) {
			copy.items.add(e.copy());
		}
		return copy;
	}

	public void change(MutableNumber start, MutableNumber end, boolean add) {
		if (add) {
			add(start, end);
		} else {
			remove(start, end);
		}
	}

	public int getExtentIndex(MutableNumber n) {
		for (int i = 0, size = items.size(); i < size; i++) {
			if (math.contains(items.get(i), n)) {
				return i;
			}
		}
		return -1;
	}
	
}
