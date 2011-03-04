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

	public DelegatingList(List list) {
		if (list == null)
			list = new ArrayList();
		this.items = list;
	}

	public void add(int index, T element) {
		items.add(index, element);
	}

	public boolean add(T  e) {
		return items.add(e);
	}

	public boolean addAll(Collection c) {
		return items.addAll(c);
	}

	public boolean addAll(int index, Collection c) {
		return items.addAll(index, c);
	}

	public void clear() {
		items.clear();
	}

	public boolean contains(Object o) {
		return items.contains(o);
	}

	public boolean containsAll(Collection c) {
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

	public Iterator iterator() {
		return items.iterator();
	}

	public int lastIndexOf(Object o) {
		return items.lastIndexOf(o);
	}

	public ListIterator listIterator() {
		return items.listIterator();
	}

	public ListIterator listIterator(int index) {
		return items.listIterator(index);
	}

	public T remove(int index) {
		return items.remove(index);
	}

	public boolean remove(Object o) {
		return items.remove(o);
	}

	public boolean removeAll(Collection c) {
		return items.removeAll(c);
	}

	public boolean retainAll(Collection c) {
		return items.retainAll(c);
	}

	public T set(int index, T element) {
		return items.set(index, element);
	}

	public int size() {
		return items.size();
	}

	public List subList(int fromIndex, int toIndex) {
		return items.subList(fromIndex, toIndex);
	}

	public Object[] toArray() {
		return items.toArray();
	}

	public Object[] toArray(Object[] a) {
		return items.toArray(a);
	}

	@Override
	public String toString() {
		return items.toString();
	}

	public DelegatingList copy() {
		if (items instanceof ArrayList) {
			List clone = (List) ((ArrayList) items).clone();
			return new DelegatingList(clone);
		}
		else if (items instanceof DelegatingList) {
			List clone = ((DelegatingList) items).copy();
			return new DelegatingList(clone);
		}
		throw new UnsupportedOperationException();
	}
}
