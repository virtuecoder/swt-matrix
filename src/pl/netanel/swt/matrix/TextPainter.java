package pl.netanel.swt.matrix;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;

import pl.netanel.swt.Resources;
import pl.netanel.swt.FontWidthCache;
import pl.netanel.util.Arrays;

/**
 * Facilitates text drawing parameterized by align, margin, and text content obviously.
 *
 * @author jacek.p.kolodziejczyk@gmail.com
 * @created 2010-06-13
 */
class TextPainter extends Painter {
	private static int[] EXTENT_ALIGN = {SWT.RIGHT, SWT.END, SWT.BOTTOM, SWT.CENTER};
	static enum TextClipMethod {DOTS_IN_THE_MIDDLE, DOTS_AT_THE_END, CUT, NONE};
	
	public String text;
	public TextClipMethod textClipMethod;
	
	private Font lastFont;
	private int[] extentCache;
	private Point extent;
//	private boolean isClipped;
	
	public TextPainter(String name) {
		super("name", Painter.SCOPE_CELLS_HORIZONTALLY);
		textMarginY = 1; textMarginX = 4;
		textAlignY = SWT.BEGINNING; textAlignX = SWT.BEGINNING;
		textClipMethod = TextClipMethod.DOTS_IN_THE_MIDDLE;
	}

	/**
	 * Sets the the default foreground color and vertical string extent.
	 * @return 
	 */
	@Override
	public boolean init() {
		gc.setBackground(Resources.getColor(SWT.COLOR_LIST_BACKGROUND));
		gc.setForeground(Resources.getColor(SWT.COLOR_LIST_FOREGROUND));
		extentCache = FontWidthCache.get(gc, gc.getFont());
		extent = new Point(-1, gc.stringExtent("ty").y);
		return true;
	}
	
//	@Override
//	public void clean() {
//		if (isClipped) {
//			gc.setClipping((Rectangle) null);
//		}
//	}

	/**
	 * For performance reasons it is assumed that the vertical string extent 
	 * is constant for a given font.
	 */
	@Override
	public void paint(Number index0, Number index1, int x, int y, int width, int height) {
		// It is impossible to print anything when cell is to small 
		if (width < 4 || height < 4) return;
		
		if (text == null) return;
		
		if (textClipMethod == TextClipMethod.DOTS_IN_THE_MIDDLE) {
			text = FontWidthCache.shortenTextMiddle(text, width - textMarginX * 2, extent, extentCache);			
		} 
		else if (textClipMethod == TextClipMethod.DOTS_AT_THE_END) {
			text = FontWidthCache.shortenTextEnd(text, width - textMarginX * 2, extent, extentCache);			
		} 
		// Compute extent only when font changes or text horizontal align is center or right  
		else if (lastFont != null && lastFont != gc.getFont() || Arrays.contains(EXTENT_ALIGN, textAlignX)) {
			extent = gc.stringExtent(text);
		}
		
		// Clipping
//		if (textClipMethod == TextClipMethod.CUT || extent.x > width || extent.y > height) {
//			gc.setClipping(x, y, width, height);
//			isClipped = true;
//		} else if (isClipped) {
//			gc.setClipping((Rectangle) null);
//			isClipped = false;
//		}

		switch (textAlignX) {
		case SWT.BEGINNING: case SWT.LEFT: case SWT.TOP: 
			x += textMarginX; break;
		case SWT.CENTER:
			x += (width - extent.x) / 2; break; 
		case SWT.RIGHT: case SWT.END: case SWT.BOTTOM:
			x += width - extent.x - textMarginX; break;
		}
		switch (textAlignY) {
		case SWT.BEGINNING: case SWT.TOP: case SWT.LEFT:
			y += textMarginY; break;
		case SWT.CENTER:
			y += (height - extent.y) / 2; break; 
		case SWT.BOTTOM: case SWT.END: case SWT.RIGHT:
			y += height - extent.y - textMarginY; break;
		}
		
		gc.drawString(text, x, y, true);
//		lastFont = gc.getFont();
	}
}
