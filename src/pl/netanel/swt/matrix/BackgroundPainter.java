package pl.netanel.swt.matrix;

import org.eclipse.swt.graphics.Color;


public class BackgroundPainter extends Painter {
	public Color color;
	
	public BackgroundPainter(Color color) {
		this.color = color;
	}
	
	@Override
	protected void init() {
		if (color != null) {
			gc.setBackground(color);
		}
	}

	@Override
	public void paint(int x, int y, int width, int height) {
		gc.fillRectangle(x, y, width, height);
	}
	

	

}
