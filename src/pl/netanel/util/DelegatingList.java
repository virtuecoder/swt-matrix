/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA.html
 ******************************************************************************/
package pl.netanel.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Delegates all List methods to the inner list, 
 * favoring composition over inheritance of third party classes.
 * Serves as a base class for specialized lists.
 *
 * @author jacek.p.kolodziejczyk@gmail.com
 */
public class DelegatingList<T> implements List<T> {
	protected final List<T> items;

	public DelegatingList(List<T> list) {
		if (list == null)
			list = new ArrayList<T>();
		this.items = list;
	}

	public void add(int index, T element) {
		items.add(index, element);
	}

	public boolean add(T  e) {
		return items.add(e);
	}

	public boolean addAll(Collection<? extends T> c) {
		return items.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends T> c) {
		return items.addAll(index, c);
	}

	public void clear() {
		items.clear();
	}

	public boolean contains(Object o) {
		return items.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return items.containsAll(c);
	}

	public T get(int index) {
		return items.get(index);
	}

	public int indexOf(Object o) {
		return items.indexOf(o);
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}

	public Iterator<T> iterator() {
		return items.iterator();
	}

	public int lastIndexOf(Object o) {
		return items.lastIndexOf(o);
	}

	public ListIterator<T> listIterator() {
		return items.listIterator();
	}

	public ListIterator<T> listIterator(int index) {
		return items.listIterator(index);
	}

	public T remove(int index) {
		return items.remove(index);
	}

	public boolean remove(Object o) {
		return items.remove(o);
	}

	public boolean removeAll(Collection<?> c) {
		return items.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return items.retainAll(c);
	}

	public T set(int index, T element) {
		return items.set(index, element);
	}

	public int size() {
		return items.size();
	}

	public List<T> subList(int fromIndex, int toIndex) {
		return items.subList(fromIndex, toIndex);
	}

	public Object[] toArray() {
		return items.toArray();
	}

	@SuppressWarnings("unchecked")
	@Override
  public Object[] toArray(Object[] a) {
		return items.toArray(a);
	}

	@Override
	public String toString() {
		return items.toString();
	}

	public DelegatingList<T> copy() {
		if (items instanceof ArrayList) {
			@SuppressWarnings("unchecked")
      List<T> clone = (List<T>) ((ArrayList<T>) items).clone();
			return new DelegatingList<T>(clone);
		}
		else if (items instanceof DelegatingList) {
			DelegatingList<T> clone = ((DelegatingList<T>) items).copy();
			return new DelegatingList<T>(clone);
		}
		throw new UnsupportedOperationException();
	}
}
