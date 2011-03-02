package pl.netanel.swt.matrix.state;

public abstract class MutableNumber<N extends MutableNumber> extends Number {
	private static final long serialVersionUID = 1L;
	
	abstract N copy();

	abstract Number getValue();
	abstract N set(Number n);
	
	abstract N increment();
	abstract N decrement();
	abstract N add(N n);
	abstract N subtract(N n);
	abstract N multiply(N n);
	abstract N divide(N n);
}
