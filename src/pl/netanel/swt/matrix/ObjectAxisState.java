package pl.netanel.swt.matrix;

import java.util.ArrayList;


class ObjectAxisState<T, N extends MutableNumber> extends AxisState<N> {
	T defaultValue, value;
	ArrayList<T> values;
	
	public ObjectAxisState(Math math, T defaultValue) {
		super(math);
		this.defaultValue = defaultValue;
		values = new ArrayList<T>();
	}
	
	@Override
	public boolean equalValue(int i) {
		return values.get(i) == value;
	}
	
	@Override
	public void addValue(int i) {
		values.add(i, value);
	}

	@Override
	public void setValue(int i) {
		values.set(i, value);
	}

	@Override
	public void addValue(int from, int to) {
		values.add(to, values.get(from));
	}

	@Override
	public void removeValue(int i) {
		values.remove(i);
	}

	@Override
	public boolean equalValues(int i, int j) {
		return values.get(i) == values.get(j);
	}

	
	public T getValue(Number index) {
		int i = indexOf(index);
		return i == -1 ? defaultValue : values.get(i);
	}
	
	public T getValueNUllable(Number index) {
		int i = indexOf(index);
		return i == -1 ? null: values.get(i);
	}
	
	public void setValue(Extent<N> extent, T value) {
		setValue(extent.start, extent.end, value);
	}
	
	public void setValue(Number from, Number to, T value) {
		this.value = value;
		doSetValue(from, to);
	}
	
	public void setValue(Number index, T value) {
		setValue(index, index, value);
	}


	
	/**
	 * Gets the size of the values collection.
	 * For testing purposes mainly
	 * @return
	 */
	public int getCount() {
		return values.size();
	}
	
	
	public T getDefault() {
		return defaultValue;
	}
	
	public void setDefault(T value) {
		defaultValue = value;
	}


}
