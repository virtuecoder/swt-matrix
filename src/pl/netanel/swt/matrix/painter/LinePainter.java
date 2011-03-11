package pl.netanel.swt.matrix.painter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import pl.netanel.swt.Resources;


public class LinePainter extends Painter {
	public Color color;
	
	public LinePainter() {
		color = Resources.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW);
	}

	@Override
	/**
	 * Background color is to paint lines because the GC.fillRectangle method 
	 * is used due to possibility of line having width greater then 1.
	 */
	protected void init() {
		gc.setBackground(color);
	}
	
	@Override
	public void paint(int x, int y, int width, int height) {
		gc.fillRectangle(x, y, width, height);
	}

	
	/*------------------------------------------------------------------------
	 * Color 
	 */

	public LinePainter color(Color color) {
		this.color = color;
		return this;
	}

}
