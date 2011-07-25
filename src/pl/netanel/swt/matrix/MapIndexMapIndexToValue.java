package pl.netanel.swt.matrix;

import java.util.HashMap;

class MapIndexMapIndexToValue<X extends Number, Y extends Number, T> 
	implements CellValues<X, Y, T> {

	final HashMap<Y, HashMap<X, T>> values;
	private final Math<Y> mathY;
	private final Math<X> mathX;
	private T defaultValue;
	
	public MapIndexMapIndexToValue(Math mathY, Math mathX) {
		this.mathY = mathY;
		this.mathX = mathX;
		values = new HashMap();
	}

	@Override
	public void setValue(X startX, X endX, Y startY, Y endY, T value) {
		for (MutableNumber<Y> i0 = mathY.create(startY); mathY.compare(i0, endY) <= 0; i0.increment()) {
			for (MutableNumber<X> i1 = mathX.create(startX); mathX.compare(i1, endX) <= 0; i1.increment()) {
				HashMap<X, T> row = values.get(i0.getValue());
				if (row == null) {
					values.put(i0.getValue(), row = new HashMap());
				}
				row.put(i1.getValue(), value);
			}
		}
	}

	@Override
	public T getValue(X indexX, Y indexY) {
		HashMap<X, T> row = values.get(indexY);
		if (row == null) return null;
		return row.get(indexX);
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
		for (MutableNumber<Y> i0 = mathY.create(start); mathY.compare(i0, end) <= 0; i0.increment()) {
			// This will take extremely long
		}
	}

	@Override
	public void deleteX(X end, X start) {
	}

	@Override
	public void insertY(Y target, Y count) {
	}

	@Override
	public void insertX(X target, X count) {
	}

}
