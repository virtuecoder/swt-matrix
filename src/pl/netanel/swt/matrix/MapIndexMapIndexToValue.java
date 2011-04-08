package pl.netanel.swt.matrix;

import java.util.HashMap;

class MapIndexMapIndexToValue<N0 extends Number, N1 extends Number, T> 
	implements CellValues<N0, N1, T> {

	final HashMap<N0, HashMap<N1, T>> values;
	private final Math<N0> math0;
	private final Math<N1> math1;
	private T defaultValue;
	
	public MapIndexMapIndexToValue(Math math0, Math math1) {
		this.math0 = math0;
		this.math1 = math1;
		values = new HashMap();
	}

	@Override
	public void setValue(N0 start0, N0 end0, N1 start1, N1 end1, T value) {
		for (MutableNumber<N0> i0 = math0.create(start0); math0.compare(i0, end0) <= 0; i0.increment()) {
			for (MutableNumber<N1> i1 = math1.create(start1); math1.compare(i1, end1) <= 0; i1.increment()) {
				HashMap<N1, T> row = values.get(i0.getValue());
				if (row == null) {
					values.put(i0.getValue(), row = new HashMap());
				}
				row.put(i1.getValue(), value);
			}
		}
	}

	@Override
	public T getValue(N0 index0, N1 index1) {
		HashMap<N1, T> row = values.get(index0);
		if (row == null) return null;
		return row.get(index1);
	}

	@Override
	public void setDefaultValue(T value) {
		defaultValue = value;
	}

	@Override
	public T getDefaultValue() {
		return defaultValue;
	}

	@Override
	public void delete0(N0 start, N0 end) {
		for (MutableNumber<N0> i0 = math0.create(start); math0.compare(i0, end) <= 0; i0.increment()) {
			// This will take extremely long
		}
	}

	@Override
	public void delete1(N1 start, N1 end) {
	}

	@Override
	public void insert0(N0 target, N0 count) {
	}

	@Override
	public void insert1(N1 target, N1 count) {
	}

}
