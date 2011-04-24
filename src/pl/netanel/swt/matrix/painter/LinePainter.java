package pl.netanel.swt.matrix.painter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import pl.netanel.swt.matrix.Painter;


class LinePainter extends Painter {
	public Color color;
	
	public LinePainter() {
		super("line");
		color = Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW);
	}

	@Override
	/**
	 * Background color is to paint lines because the GC.fillRectangle method 
	 * is used due to possibility of line having width greater then 1.
	 */
	protected boolean init() {
		gc.setBackground(color);
		return true;
	}
	
	@Override
	public void paint(Number index0, Number index1, int x, int y, int width, int height) {
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
