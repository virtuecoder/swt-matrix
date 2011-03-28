package pl.netanel.swt.matrix.painter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import pl.netanel.swt.matrix.Painter;


class BorderPainter extends Painter {
	public int radius;
	public int outerOffset = -1;
	public int lineWidth = 1;
	public Color color;

	public BorderPainter(int lineWidth) {
		super("border");
		this.lineWidth = lineWidth;
	}

	@Override
	public boolean init() {
		if (color == null) {
			color = Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BORDER); 
		}
		gc.setForeground(color);
		return true;
	}
	
	@Override
	public void paint(int x2, int y2, int width, int height) {
		if (width <= 0) return;
		
		int x = x2 + outerOffset + lineWidth / 2;
		int y = y2 + outerOffset + lineWidth / 2;
		int w = width - 2 * outerOffset - lineWidth;
		int h = height - 2 * outerOffset - lineWidth;
		
		// Draw rectangles from startOffset to endOffset compared to the cell bounds
		gc.setLineWidth(lineWidth);
//		for (int i = 0; i < lineWidth; i++) {
			if (radius > 0)
				gc.drawRoundRectangle(x, y, w, h, radius, radius);
			else
				gc.drawRectangle(x, y, w, h);
			
//			x += 1; 
//			y += 1;
//			w -= 2; 
//			h -= 2;
//			
//		}
	}
	
	/*------------------------------------------------------------------------
	 * Builder pattern 
	 */

	public BorderPainter radius(int radius) {
		this.radius = radius;
		return this;
	}
	
	
	public BorderPainter offset(int offset) {
		this.outerOffset = offset;
		return this;
	}
	
	
	public BorderPainter color(Color color) {
		this.color = color;
		return this;
	}
	
	
	
}
