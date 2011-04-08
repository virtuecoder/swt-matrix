package pl.netanel.swt.matrix;

import java.util.HashMap;
import java.util.Map.Entry;

class MapValueToCellSet<N0 extends Number, N1 extends Number, T> implements CellValues<N0, N1, T> {
	final HashMap<T, CellSet<N0, N1>> values;
	private final Math<N0> math0;
	private final Math<N1> math1;
	private T defaultValue;
	
	public MapValueToCellSet(Math math0, Math math1) {
		this.math0 = math0;
		this.math1 = math1;
		values = new HashMap();
	}

	/* (non-Javadoc)
	 * @see pl.netanel.swt.matrix.CellValues#setValue(N0, N0, N1, N1, T)
	 */
	@Override
	public void setValue(N0 start0, N0 end0, N1 start1, N1 end1, T value) {
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
	
	/* (non-Javadoc)
	 * @see pl.netanel.swt.matrix.CellValues#getValue(N0, N1)
	 */
	@Override
	public T getValue(N0 index0, N1 index1) {
		for (Entry<T, CellSet<N0, N1>> entry: values.entrySet()) {
			if (entry.getValue().contains(index0, index1)) {
				return entry.getKey();
			}
		}
		return defaultValue;
	}	
	
	/* (non-Javadoc)
	 * @see pl.netanel.swt.matrix.CellValues#setDefaultValue(T)
	 */
	@Override
	public void setDefaultValue(T value) {
		defaultValue = value;
	}
	
	/* (non-Javadoc)
	 * @see pl.netanel.swt.matrix.CellValues#getDefaultValue()
	 */
	@Override
	public T getDefaultValue() {
		return defaultValue;
	}

	/* (non-Javadoc)
	 * @see pl.netanel.swt.matrix.CellValues#delete0(N0, N0)
	 */
	@Override
	public void delete0(N0 start, N0 end) {
		for (Entry<T, CellSet<N0, N1>> entry: values.entrySet()) {
			entry.getValue().delete0(start, end);
		}
	}
	/* (non-Javadoc)
	 * @see pl.netanel.swt.matrix.CellValues#delete1(N1, N1)
	 */
	@Override
	public void delete1(N1 start, N1 end) {
		for (Entry<T, CellSet<N0, N1>> entry: values.entrySet()) {
			entry.getValue().delete1(start, end);
		}
	}
	
	/* (non-Javadoc)
	 * @see pl.netanel.swt.matrix.CellValues#insert0(N0, N0)
	 */
	@Override
	public void insert0(N0 target, N0 count) {
		for (Entry<T, CellSet<N0, N1>> entry: values.entrySet()) {
			entry.getValue().insert0(target, count);
		}
	}
	/* (non-Javadoc)
	 * @see pl.netanel.swt.matrix.CellValues#insert1(N1, N1)
	 */
	@Override
	public void insert1(N1 target, N1 count) {
		for (Entry<T, CellSet<N0, N1>> entry: values.entrySet()) {
			entry.getValue().insert1(target, count);
		}
	}
}
