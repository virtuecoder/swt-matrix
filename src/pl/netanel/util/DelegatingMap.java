package pl.netanel.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DelegatingMap<K, V> implements Cloneable {
	HashMap<K, V> map;

	public DelegatingMap(HashMap<K, V> map) {
		super();
		this.map = map;
	}

	public void clear() {
		map.clear();
	}

	@SuppressWarnings("unchecked")
  @Override
	public Object clone() throws CloneNotSupportedException {
    DelegatingMap<K, V> o = (DelegatingMap<K, V>) super.clone();
		o.map = (HashMap<K, V>) map.clone();
		return o;
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	public Set<Map.Entry<K, V>> entrySet() {
		return map.entrySet();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Set<K> keySet() {
		return map.keySet();
	}

	public Object put(K key, V value) {
		return map.put(key, value);
	}

	public void putAll(Map<K, V> m) {
		map.putAll(m);
	}

	public Object remove(Object key) {
		return map.remove(key);
	}
	
	public int size() {
		return map.size();
	}

	@Override
	public String toString() {
		return map.toString();
	}

	public Collection<V> values() {
		return map.values();
	}
}
