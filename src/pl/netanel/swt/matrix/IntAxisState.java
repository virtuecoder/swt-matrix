package pl.netanel.swt.matrix;

import pl.netanel.util.IntArray;

public class IntAxisState<N extends MutableNumber> extends AxisState<N> {
	private final IntArray values;
	private int defaultValue, value;
	
	public IntAxisState(Math math, int defaultValue) {
		super(math);
		this.defaultValue = defaultValue;
		values = new IntArray();
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

	
	public int getValue(Number index) {
		int i = indexOf(index);
		return i == -1 ? defaultValue : values.get(i);
	}
	
	public void setValue(Extent<N> extent, int value) {
		setValue(extent.start, extent.end, value);
	}
	
	public void setValue(Number from, Number to, int value) {
		if (value == defaultValue) {
			// TODO remove value if it is default
		}
		this.value = value;
		doSetValue(from, to);
	}


	
	/**
	 * Gets the size of the values collection.
	 * For testing purposes mainly
	 * @return
	 */
	public int getCount() {
		return values.size();
	}
	
	
	public int getDefault() {
		return defaultValue;
	}
	
	public void setDefault(int value) {
		defaultValue = value;
	}

}
