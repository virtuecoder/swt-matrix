package pl.netanel.swt.matrix.state;

interface Mutable<T> {
	void setValue(T value);
	T getValue();
}
