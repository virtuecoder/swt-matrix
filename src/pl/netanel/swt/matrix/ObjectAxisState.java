/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import java.util.ArrayList;


class ObjectAxisState<N extends Number, T> extends AxisState<N> {
	T defaultValue, value;
	ArrayList<T> values;
	
	public ObjectAxisState(Math<N> math, T defaultValue) {
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

	
	public T getValue(N index) {
		int i = indexOf(index);
		return i == -1 ? defaultValue : values.get(i);
	}
	
	public T getValueNUllable(N index) {
		int i = indexOf(index);
		return i == -1 ? null: values.get(i);
	}
	
	public void setValue(MutableExtent<N> extent, T value) {
		setValue(extent.start(), extent.end(), value);
	}
	
	public void setValue(N from, N to, T value) {
		this.value = value;
		doSetValue(from, to);
	}
	
	public void setValue(N index, T value) {
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
