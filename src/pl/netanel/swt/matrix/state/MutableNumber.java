package pl.netanel.swt.matrix.state;

abstract class MutableNumber<N extends MutableNumber> extends Number {
	private static final long serialVersionUID = 1L;
	
	public abstract N copy();

	public abstract Number getValue();
	public abstract N set(Number n);
	
	public abstract N increment();
	public abstract N decrement();
	public abstract N add(N n);
	public abstract N subtract(N n);
	public abstract N multiply(N n);
	public abstract N divide(N n);
}
