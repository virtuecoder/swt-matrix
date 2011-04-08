package pl.netanel.swt.matrix;

interface CellValues<N0 extends Number, N1 extends Number, T> {

	public abstract void setValue(N0 start0, N0 end0, N1 start1, N1 end1,
			T value);

	public abstract T getValue(N0 index0, N1 index1);

	public abstract void setDefaultValue(T value);

	public abstract T getDefaultValue();

	public abstract void delete0(N0 start, N0 end);

	public abstract void delete1(N1 start, N1 end);

	public abstract void insert0(N0 target, N0 count);

	public abstract void insert1(N1 target, N1 count);

}