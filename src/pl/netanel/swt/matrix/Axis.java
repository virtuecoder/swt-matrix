package pl.netanel.swt.matrix;

import pl.netanel.swt.Listeners;

public class Axis {

	final Matrix matrix;
	final int axisIndex;
	final AxisModel model;
	final Layout layout;
	final Listeners listeners;
	
	public Axis(Matrix matrix, int axisIndex) {
		this.matrix = matrix;
		this.axisIndex = axisIndex;
		model = axisIndex == 0 ? matrix.getModel().getModel0() : matrix.getModel().getModel1();
		layout = new Layout(model);
		listeners = new Listeners();
	}
	
	public Section getBody() {
		return model.getBody();
	}

	public void setHeaderVisible(boolean visible) {
//		AxisModel opposite = axisIndex == 1 ? matrix.getModel0() : matrix.getModel1();
		Section header = model.getHeader();
		if (header == null) return;
		header.setVisible(visible);
		if (visible) {
			if (header.isEmpty()) header.setCount(1);	
		}
	}
}
