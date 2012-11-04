/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA.html
 ******************************************************************************/
package pl.netanel.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashMapArrayList<K, V> extends DelegatingMap<K, List<V>> {

	public HashMapArrayList() {
		super(new HashMap<K, List<V>>());
	}

	@Override
	public Object put(K key, java.util.List<V> value) {
		return super.put(key, value);
	};
	
	public void add(K key, V value) {
		List<V> list = map.get(key);
		if (list == null) {
			list = new ArrayList<V>();
			map.put(key, list);
		}
		list.add(value);
	};

	public List<V> get(K key) {
		List<V> list = map.get(key);
		if (list == null) {
			list = new ArrayList<V>();
		}
		return list;
	}

	public List<V> getNotNull(K key) {
		List<V> list = map.get(key);
		if (list == null) {
			list = new ArrayList<V>();
			map.put(key, list);
		}
		return list;
	}

	public int putIndexed(K key, V value) {
		List<V> list = map.get(key);
		if (list == null) {
			list = new ArrayList<V>();
			map.put(key, list);
		}
		int i = list.size();
		list.add(value);
		return i;
	}
	
	public Object remove(K key, V value) {
		return map.get(key).remove(value);
	}

}
