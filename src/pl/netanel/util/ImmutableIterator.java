/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA.html
 ******************************************************************************/
package pl.netanel.util;

import java.util.Iterator;

public abstract class ImmutableIterator<E> implements Iterator<E> {
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
