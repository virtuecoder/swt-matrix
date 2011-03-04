package pl.netanel.swt.matrix;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

public class ImagePainter extends Painter {
	public Image image;
	public boolean repeatX, repeatY, fit;
	public int alignX;
	public int alignY;
	public int marginX;
	public int marginY;
	
	public ImagePainter(Image image) {
		super();
		this.image = image;
	}


	@Override
	public void paint(int x, int y, int width, int height) {
		Rectangle r = image.getBounds();
		x += alignDelta(alignX, width, r.width, marginX);
		y += alignDelta(alignY, height, r.height, marginY);
		
		gc.drawImage(image, x, y);
	}


	public ImagePainter align(int alignX, int alignY) {
		this.alignX = alignX;
		this.alignY = alignY;
		return this;
	}
	
	public ImagePainter margin(int marginX, int marginY) {
		this.marginX = marginX;
		this.marginY = marginY;
		return this;
	}


}
