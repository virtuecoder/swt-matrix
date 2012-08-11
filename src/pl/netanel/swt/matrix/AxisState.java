/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import static pl.netanel.swt.matrix.Math.*;

import java.util.ArrayList;

import pl.netanel.util.IntArray;

abstract class AxisState<N extends Number> {
	private final Math<N> math;
	private final ArrayList<MutableExtent<N>>extents;
	private final IntArray toRemove;

	
	public AxisState(Math<N> math) {
		this.math = math;
		extents = new ArrayList<MutableExtent<N>>();
		toRemove = new IntArray();
	}

	protected int indexOf(N index) {
		for (int i = 0; i < extents.size(); i++) {
			MutableExtent<N> e = extents.get(i);
			if (math.contains(e.start(), e.end(), index)) {
				return i;
			}
		}
		return -1;
	}

	protected void doSetValue(N start, N end) {
		MutableExtent<N> e, modified = null;
		toRemove.clear();
		boolean quit = false;
		int i = 0; 
		for (; i < extents.size(); i++) {
			e = extents.get(i);
			boolean sameValue = equalValue(i);
			int compare = math.compare(e.start(), e.end(), start, end);
			int ss = math.compare(e.start(), start);
			int ee = math.compare(e.end(), end);
			switch(compare) {
			case AFTER:					break;
			case BEFORE:				continue;
			
			case OVERLAP: 	
				if (sameValue) return;
				else {
					if (ee > 0) {
						extents.add(i+1, new MutableExtent<N>(math.create(end).increment(), e.end.copy()));
						addValue(i, i+1);
					}
					if (ss < 0) {
						e.end.set(start).decrement();
						modified = new MutableExtent<N>(math.create(start), math.create(end));
						extents.add(++i, modified);
						addValue(i);
						;
					} else {
						toRemove.add(i);
					}
				}
				break;
				
			case EQUAL: case INSIDE:
			case CROSS_BEFORE: case CROSS_AFTER:
			case ADJACENT_AFTER: case ADJACENT_BEFORE:
				if (sameValue) {
					// enlarge
					if (modified == null) modified = e;
					else toRemove.add(i);
					modified.start.set(math.min(start, modified.start(), e.start()));
					modified.end.set(math.max(end, modified.end(), e.end()));
					
				} else {
					// shrink
					switch (compare) {				
					case ADJACENT_AFTER: quit = true; break; 
					case CROSS_BEFORE: 	 if (ss < 0) e.end.set(start).decrement(); else toRemove.add(i); break; 
					case CROSS_AFTER: 	 if (ee > 0) e.start.set(end).increment(); else toRemove.add(i); break;
					case INSIDE: 		 toRemove.add(i); break;
					case EQUAL: 		
						if (modified == null) {		
							setValue(i);
							modified = e;
						} 
						else toRemove.add(i); 
					}
				}
				break;
			}
			if (quit) break;
		}
		
		if (modified == null) {
			extents.add(i, new MutableExtent<N>(math.create(start), math.create(end)));
			addValue(i);
		}
		
		for (int j = toRemove.size(); j-- > 0;) {
			int k = toRemove.get(j);
			extents.remove(k); 
			removeValue(k); 
		}
	}

	/**
	 * arguments: 0 2
	 * extent:        4 6 -> e.start -= p.count; e.end -= p.count  
	 * arguments: 0 2
	 * extent:      2 4   -> e.start = end + 1;   
	 * arguments: 0 2
	 * extent:      2 4   -> e.start = end + 1;   
	 * arguments:  12
	 * extent:    0  3    -> add(end+1, e.end); e.end = end + 1;    
	 * arguments:  1 3
	 * extent:    0 2     -> e.end = start - 1;    
	 * 
	 * @param start
	 * @param end
	 * @return 
	 */
	public IntArray delete(N start, N end) {
		return MutableExtent.delete(math, extents, start, end);
	}
	
	public void insert(N target, N count) {
		MutableExtent.insert(math, extents, target, count);
	}
	
	
	abstract boolean equalValue(int i);
	abstract boolean equalValues(int i, int j);
	abstract void addValue(int i);
	abstract void addValue(int from, int to);
	abstract void setValue(int i);
	abstract void removeValue(int i);
}
