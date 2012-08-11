/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/
package pl.netanel.util;



/**
 * Auto-growing array of integers with List-like API to avoid auto boxing between primitive and
 * object if ArrayList<Integer> was used instead.
 * 
 * @author Jacek
 * @created Jul 29, 2009
 */
public class IntArray {
	public int[] a;
	int size;

	public IntArray() {
		a = new int[10];
		size = 0;
	}

	public IntArray(int[] a) {
		this.a = a;
		size = a.length;
	}

	public IntArray(int capacity) {
		this.a = new int[capacity];
		size = 0;
	}

	public void add(int value) {
		ensureSize(size + 1);
		a[size++] = value;
	}
	
	public void add(int index, int value) {
		ensureSize(size + 1);
		System.arraycopy(a, index, a, index+1, size - index);
		a[index] = value;
		size++;
	}
	
	public void set(int index, int value) {
		ensureSize(index + 1);
		a[index] = value;
	}


	public int size() {
		return size;
	}

	private void ensureSize(int newSize) {
		if (a.length <= newSize) {
			int[] a2 = new int[Math.max(a.length + a.length / 2, newSize)];
			System.arraycopy(a, 0, a2, 0, newSize);
			a = a2;
		}
	}

	public int get(int i) {
		Preconditions.checkElementIndex(i, size);
		return a[i];
	}

	/**
	 * Does not check index range for correctness.
	 * 
	 * @param i
	 * @return
	 */
	public int getDirectly(int i) {
		return a[i];
	}

	public boolean contains(int col) {
		for (int i = 0; i < size; i++)
			if (a[i] == col)
				return true;
		return false;
	}

	public void addAll(int... col) {
		ensureSize(size + col.length);
		System.arraycopy(col, 0, a, size, col.length);
		size += col.length;
	}

	public void remove(int index) {
		Preconditions.checkElementIndex(index, size);
		if (index < size - 1)
			System.arraycopy(a, index+1, a, index, size - index - 1);
		size--;
	}
	public void removeAll(int[] par) {
		IntArray toDelete = new IntArray(par); 
		int[] copy = a.clone();
		int counter = 0;
		for (int i = 0; i < size; i++) {
			if (!toDelete.contains(a[i]))
				copy[counter++] = a[i];
		}
		a = copy;
		size = counter;
	}

	public boolean isEmpty() {
		return size == 0;
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("[");
		for (int i = 0; i < size; i++) {
			if (i > 0) b.append(", ");
			b.append(a[i]);
		}
		b.append("]");
		return b.toString();
	}

	public IntArray rangeExclusive(int start, int end) {
		for (int row = start; row < end; row++) {
			add(row);
		}
		return this;
	}

	public void clear() {
		size = 0;
	}

	public int indexOf(int x) {
		for (int i = 0; i < size; i++) {
			if (a[i] == x)
				return i;
		}
		return -1;
	}

	public void reverse() {
		for (int i = 0; i < size / 2; i++) {
			int tmp = a[i];
			int opposite = size - i - 1;
			a[i] = a[opposite];
			a[opposite] = tmp;
		}
	}

	public int getLast() {
		return a[size - 1];
	}
	
	public static boolean isNullOrEmpty(IntArray object) {
		return object == null || object.isEmpty();
	}

	public int[] toArray() {
		int[] a2 = new int[size];
		System.arraycopy(a, 0, a2, 0, size);
		return a2;
	}
	
	public IntArray copy() {
		int[] a2 = a.clone();
		IntArray copy = new IntArray(a2);
		copy.size = size;
		return copy;
	}

	public void sortDescending() {
		java.util.Arrays.sort(a);
		
		// reverse the array 
		for( int i = 0; i < a.length/2; ++i ) { 
			int j = a.length - i - 1;
			int tmp = a[i]; 
			a[i] = a[j]; 
			a[j] = tmp; 
		} 
	}
}
