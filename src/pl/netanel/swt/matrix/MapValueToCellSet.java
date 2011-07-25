package pl.netanel.swt.matrix;

import java.util.HashMap;
import java.util.Map.Entry;

class MapValueToCellSet<X extends Number, Y extends Number, T> implements CellValues<X, Y, T> {
	final HashMap<T, CellSet<X, Y>> values;
	private final Math<Y> mathY;
	private final Math<X> mathX;
	private T defaultValue;
	
	public MapValueToCellSet(Math mathY, Math mathX) {
		this.mathY = mathY;
		this.mathX = mathX;
		values = new HashMap();
	}

	/* (non-Javadoc)
	 * @see pl.netanel.swt.matrix.CellValues#setValue(Y, Y, X, X, T)
	 */
	@Override
	public void setValue(Y startY, Y endY, X startX, X endX, T value) {
		// Remove from other
		for (Entry<T, CellSet<X, Y>> entry: values.entrySet()) {
			if (!entry.getKey().equals(value)) {
				entry.getValue().remove(startY, endY, startX, endX);
			}
		}
		if (value != null) {
			CellSet<X, Y> set = values.get(value);
			if (set == null) {
				set = new CellSet(mathY, mathX);
				values.put(value, set);
			}
			set.add(startY, endY, startX, endX);
		}
	}
	
	/* (non-Javadoc)
	 * @see pl.netanel.swt.matrix.CellValues#getValue(Y, X)
	 */
	@Override
	public T getValue(Y indexY, X indexX) {
		for (Entry<T, CellSet<X, Y>> entry: values.entrySet()) {
			if (entry.getValue().contains(indexY, indexX)) {
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
	 * @see pl.netanel.swt.matrix.CellValues#deleteY(Y, Y)
	 */
	@Override
	public void deleteY(Y start, Y end) {
		for (Entry<T, CellSet<X, Y>> entry: values.entrySet()) {
			entry.getValue().deleteY(start, end);
		}
	}
	/* (non-Javadoc)
	 * @see pl.netanel.swt.matrix.CellValues#deleteX(X, X)
	 */
	@Override
	public void deleteX(X start, X end) {
		for (Entry<T, CellSet<X, Y>> entry: values.entrySet()) {
			entry.getValue().deleteX(start, end);
		}
	}
	
	/* (non-Javadoc)
	 * @see pl.netanel.swt.matrix.CellValues#insertY(Y, Y)
	 */
	@Override
	public void insertY(Y target, Y count) {
		for (Entry<T, CellSet<X, Y>> entry: values.entrySet()) {
			entry.getValue().insertY(target, count);
		}
	}
	/* (non-Javadoc)
	 * @see pl.netanel.swt.matrix.CellValues#insertX(X, X)
	 */
	@Override
	public void insertX(X target, X count) {
		for (Entry<T, CellSet<X, Y>> entry: values.entrySet()) {
			entry.getValue().insertX(target, count);
		}
	}
}
