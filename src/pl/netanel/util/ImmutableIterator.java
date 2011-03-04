package pl.netanel.util;

import java.util.Iterator;

public abstract class ImmutableIterator<E> implements Iterator<E> {
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
