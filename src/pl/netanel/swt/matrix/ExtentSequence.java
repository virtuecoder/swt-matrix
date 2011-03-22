package pl.netanel.swt.matrix;

import java.util.List;

class ExtentSequence<N extends Number> implements Sequence {
	protected final List<Extent<N>> items;
	protected int i;
	
	N start, end;
	
	public ExtentSequence(List<Extent<N>> items) {
		this.items = items;
	}

	@Override
	public void init() {
		i = -1;
	}

	@Override
	public boolean next() {
		if (++i >= items.size() ) return false;
		Extent<N> e = items.get(i);
		start = e.start();
		end = e.end();
		return true;
	}

}
