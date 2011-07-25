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