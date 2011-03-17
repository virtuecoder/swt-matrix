package pl.netanel.swt.matrix;

import static pl.netanel.swt.matrix.Math.*;

import java.util.Collection;

import pl.netanel.util.Preconditions;

/**
 * Defines a FILO queue of indexes without duplicates.
 * <p>
 * It stores index extents rather then individaul index objects, offering a
 * significant speed and memory optimazation compared to java.util
 * implementations. To take a full advantage of this the methods operating on
 * index extents e.g. <code>add(start, end)</code> rathen then single index
 * methods e.g. <code>add(index)</code> should be used whenever appliable.
 * <p>
 * It cannot contain null item.
 * <p>
 * One of the reasons this class does not implement the java collections
 * interfaces is that it can contain more elements then the <code>int</code>
 * capacity, making the {@link Collection#size()} method obsolete.

 * <p>
 * An example application of this class is recording the block selection of
 * indexes in a sequential order.
 * <p>
 * 
 * @author Jacek created 23-02-2011
 */
//TODO Complete with other collection methods like addAll, etc.
class NumberQueueSet extends NumberSet {
	
	public NumberQueueSet(Math math) {
		super(math);
	}

	/**
	 * Adds an extent of indexes delimited by start and end to this set.
	 * <p>
	 * If the new extent of indexes interesects with a an existing in the
	 * receiver already, then the intersection extent will be removed from the
	 * existing extent and the new one will be appended.
	 * 
	 * @param start index starting the range of indexes to add
	 * @param end index ending the range of indexes to add
	 * @return @return true if the receiever has been modified by this operation, 
	 * or false otherwise
	 */
	public boolean add(Number start, Number end) {
		Preconditions.checkNotNullWithName(start, "Start index");
		Preconditions.checkNotNullWithName(end, "End index");
		
		int last = items.size() - 1;
		int i = 0;
		
		for (;i <= last; i++) {
			Extent item = items.get(i);
			int location = math.compare(start, end, item.start(), item.end());
			
			switch (location) {
			case EQUAL: 			if (i == last) return false;
			case OVERLAP:			items.remove(i); i--; last--; break;
			case ADJACENT_AFTER: 	
			case CROSS_AFTER:		start = item.start.getValue(); items.remove(i); i--; last--; break;
			case CROSS_BEFORE:		item.start.set(math.increment(end)); break;
			case INSIDE:
				items.add(++i, new Extent(math.create(end).increment(), item.end.copy()));
				item.end.set(math.decrement(start));
				last++;
				break; 
			}
		}
		items.add(new Extent(math.create(start), math.create(end)));
		return true;
	}

	/**
	 * Removes an extent of indexes delimited by start and end from this set.
	 * <p>
	 * Indexes within the <code>start..end</code> extent that are not contained 
	 * in the receiver are ignored.
	 * 
	 * @param start index starting the range of indexes to remove
	 * @param end index ending the range of indexes to remove
	 * @return @return true if the receiever has been modified by this operation, 
	 * or false otherwise
	 */
	// TODO Optimize: merge if adjacent after remove
	public boolean remove(Number start, Number end) {
		Preconditions.checkNotNullWithName(start, "Start index");
		Preconditions.checkNotNullWithName(end, "End index");
		
		int last = items.size() - 1;
		int i = 0;
		
		for (;i <= last; i++) {
			Extent item = items.get(i);
			int location = math.compare(start, end, item.start(), item.end());
			
			switch (location) {
			case EQUAL: 			
			case OVERLAP:			items.remove(i); i--; last--; break;
			case CROSS_BEFORE:		item.start.set(math.increment(end)); break;
			case CROSS_AFTER:		item.end.set(math.decrement(start)); break;
			case INSIDE:
				items.add(++i, new Extent(math.create(end).increment(), item.end.copy()));
				item.end.set(math.decrement(start));
				last++;
				break; 
			}
		}
		return true;
	}

	/**
	 * Returns true if this set contains no elements.
	 * 
	 * @return true if this set contains no elements
	 */
	public boolean isEmpty() {
		return items.isEmpty();
	}
	
	/**
	 * Returns true if this set contains the specified element. 
	 * More formally, returns true if and only if this set contains 
	 * an element e such that o.equals(e).
	 * 
	 * @param index element whose presence in this set is to be tested
	 * @return true if this list contains the specified element
	 */
	public boolean contains(Number index) {
		for (Extent e: items) {
			if (math.contains(e.start(), e.end(), index)) {
				return true;
			}
		}
		return false;
	}
	
	
//	Extent getExtent(MutableNumber target) {
//		for (Extent e: items) {
//			if (e.contains(target)) {
//				return e;
//			}
//		}
//		return null;
//	}

	/**
	 * Returns a deep copy of the set.
	 * @return deep copy of the set.
	 */
	public NumberQueueSet copy() {
		NumberQueueSet copy = new NumberQueueSet(math);
		for (Extent e: items) {
			copy.items.add(new Extent(math.create(e.start), math.create(e.end)));
		}
		return copy;
	}

	}
