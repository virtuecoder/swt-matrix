package pl.netanel.swt.matrix;

import java.util.HashMap;
import java.util.Map.Entry;

class CellValues<N0 extends Number, N1 extends Number, T> {
	final HashMap<T, CellSet<N0, N1>> values;
	private final Math<N0> math0;
	private final Math<N1> math1;
	private T defaultValue;
	
	public CellValues(Math math0, Math math1) {
		this.math0 = math0;
		this.math1 = math1;
		values = new HashMap();
	}

	void setValue(N0 start0, N0 end0, N1 start1, N1 end1, T value) {
		// Remove from other
		for (Entry<T, CellSet<N0, N1>> entry: values.entrySet()) {
			if (!entry.getKey().equals(value)) {
				entry.getValue().remove(start0, end0, start1, end1);
			}
		}
		if (value != null) {
			CellSet<N0, N1> set = values.get(value);
			if (set == null) {
				set = new CellSet(math0, math1);
				values.put(value, set);
			}
			set.add(start0, end0, start1, end1);
		}
	}
	
	T getValue(N0 index0, N1 index1) {
		for (Entry<T, CellSet<N0, N1>> entry: values.entrySet()) {
			if (entry.getValue().contains(index0, index1)) {
				return entry.getKey();
			}
		}
		return defaultValue;
	}	
	
	public void setDefaultValue(T value) {
		defaultValue = value;
	}
	
	public T getDefaultValue() {
		return defaultValue;
	}

	public void delete0(N0 start, N0 end) {
		for (Entry<T, CellSet<N0, N1>> entry: values.entrySet()) {
			entry.getValue().delete0(start, end);
		}
	}
	public void delete1(N1 start, N1 end) {
		for (Entry<T, CellSet<N0, N1>> entry: values.entrySet()) {
			entry.getValue().delete1(start, end);
		}
	}
	
	public void insert0(N0 target, N0 count) {
		for (Entry<T, CellSet<N0, N1>> entry: values.entrySet()) {
			entry.getValue().insert0(target, count);
		}
	}
	public void insert1(N1 target, N1 count) {
		for (Entry<T, CellSet<N0, N1>> entry: values.entrySet()) {
			entry.getValue().insert1(target, count);
		}
	}
}
