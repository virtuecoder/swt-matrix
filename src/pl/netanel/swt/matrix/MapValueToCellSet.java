package pl.netanel.swt.matrix;

import java.util.HashMap;
import java.util.Map.Entry;

class MapValueToCellSet<X extends Number, Y extends Number, T> implements CellValues<X, Y, T> {
	final HashMap<T, CellSet<X, Y>> values;
	private final Math<X> mathX;
	private final Math<Y> mathY;
	private T defaultValue;
	
	public MapValueToCellSet(Math<X> mathX, Math<Y> mathY) {
		this.mathY = mathY;
		this.mathX = mathX;
		values = new HashMap<T, CellSet<X, Y>>();
	}

	@Override
	public void setValue(X startX, X endX, Y startY, Y endY, T value) {
		// Remove from other
		for (Entry<T, CellSet<X, Y>> entry: values.entrySet()) {
			if (!entry.getKey().equals(value)) {
				entry.getValue().remove(startX, endX, startY, endY);
			}
		}
		if (value != null) {
			CellSet<X, Y> set = values.get(value);
			if (set == null) {
				set = new CellSet<X, Y>(mathX, mathY);
				values.put(value, set);
			}
			set.add(startX, endX, startY, endY);
		}
	}
	
	@Override
	public T getValue(X indexX, Y indexY) {
		for (Entry<T, CellSet<X, Y>> entry: values.entrySet()) {
			if (entry.getValue().contains(indexX, indexY)) {
				return entry.getKey();
			}
		}
		return defaultValue;
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
	public void deleteY(Y end, Y start) {
		for (Entry<T, CellSet<X, Y>> entry: values.entrySet()) {
			entry.getValue().deleteY(start, end);
		}
	}
	@Override
	public void deleteX(X end, X start) {
		for (Entry<T, CellSet<X, Y>> entry: values.entrySet()) {
			entry.getValue().deleteX(start, end);
		}
	}
	
	@Override
	public void insertY(Y target, Y count) {
		for (Entry<T, CellSet<X, Y>> entry: values.entrySet()) {
			entry.getValue().insertY(target, count);
		}
	}
	@Override
	public void insertX(X target, X count) {
		for (Entry<T, CellSet<X, Y>> entry: values.entrySet()) {
			entry.getValue().insertX(target, count);
		}
	}
}
