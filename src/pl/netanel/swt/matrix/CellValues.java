package pl.netanel.swt.matrix;

interface CellValues<X extends Number, Y extends Number, T> {

	public abstract void setValue(Y startY, Y endY, X startX, X endX,
			T value);

	public abstract T getValue(Y indexY, X indexX);

	public abstract void setDefaultValue(T value);

	public abstract T getDefaultValue();

	public abstract void deleteY(Y start, Y end);

	public abstract void deleteX(X start, X end);

	public abstract void insertY(Y target, Y count);

	public abstract void insertX(X target, X count);

}