/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

/**
 * Contains an axis item's width and a calculated distance 
 * from the beginning of the viewport.
 * 
 * @author Jacek Kolodziejczyk created 25-03-2011
 */
class Bound {
	public int distance, width;

	public Bound() {}
	public Bound(int distance, int width) {
		this.distance = distance;
		this.width = width;
	}
	
	@Override
	public String toString() {
		return "[" + distance + ", " + width + "]";
	}
	
	public Bound copy() {
		return new Bound(distance, width);
	}
}