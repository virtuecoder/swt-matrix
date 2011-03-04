package pl.netanel.swt.matrix;

public class Axis {

	final AxisModel model;
	final Layout layout;

	public Axis(AxisModel model) {
		this.model = model;
		layout = new Layout(model);
	}

}
