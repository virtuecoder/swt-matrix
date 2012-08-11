/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

interface CellValues<X extends Number, Y extends Number, T> {

	public abstract void setValue(X startX, X endX, Y startY, Y endY, T value);

	public abstract T getValue(X indexX, Y indexY);

	public abstract void setDefaultValue(T value);

	public abstract T getDefaultValue();
	
	public abstract void deleteX(X end, X start);

	public abstract void deleteY(Y end, Y start);
	
	public abstract void insertX(X target, X count);

	public abstract void insertY(Y target, Y count);

}