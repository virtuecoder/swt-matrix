package pl.netanel.swt.matrix;

class AxisItemSequence<N extends Number> {

	final Axis<N> axis;
	N index0, index1;

	public AxisItemSequence(Axis axis) {
		this.axis = axis;
	}

	public void init() {
		
	}
	
	public boolean next() {
		return true;
	}
}
