package pl.netanel.swt.matrix.painter;

import org.eclipse.swt.graphics.Color;

import pl.netanel.swt.matrix.Painter;


class BackgroundPainter extends Painter {
	public Color color;
	
	public BackgroundPainter(Color color) {
		super("background");
		this.color = color;
	}
	
	@Override
	protected boolean init() {
		if (color != null) {
			gc.setBackground(color);
		}
		return true;
	}

	@Override
	public void paint(int x, int y, int width, int height) {
		gc.fillRectangle(x, y, width, height);
	}
	
}
