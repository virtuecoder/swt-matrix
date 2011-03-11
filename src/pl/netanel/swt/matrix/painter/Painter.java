package pl.netanel.swt.matrix.painter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.RGB;

import pl.netanel.swt.Resources;
import pl.netanel.swt.matrix.MutableNumber;



/**
 * Base class for painting something within the given boundaries to the <code>paint()</code> method.
 * <p> 
 * It's used for both lines and cells.
 *  
 * @author jacek.p.kolodziejczyk@gmail.com
 * @created 2010-06-13
 */
// TODO add the paint(AxisLayoutIterator) here?
public abstract class Painter {
	protected GC gc;
	protected boolean enabled = true;
//	protected SizeMeter meter;

	/**
	 * To be called before any painting started.
	 * Initializes the GC property of the receiver to be used by its other methods.
	 * To change the painter initialization behavior override the protected <code>init()</code> method. 
	 * @param gc
	 */
	public final void init(GC gc) {
		this.gc = gc;
		init();
	};

	/** 
	 * To be called before any painting started.
	 * @see <code>clean()</code>
	 */
	protected void init() {}

	/**
	 * To be called when the receiver has completed painting of all items.
	 * @see <code>init()</code>
	 */
	public void clean() {}

	/**
	 * To called before the <code>paint()</code> method.
	 * @param n0
	 * @param n2
	 */
	public void beforePaint(MutableNumber index0, MutableNumber index1) { }
	
	public abstract void paint(int x, int y, int width, int height);
	
	/**
	 * To called after the <code>paint()</code> method.
	 * @param item0
	 * @param item1
	 */
	public void afterPaint(MutableNumber index0, MutableNumber index1) { }
	
	
	/**
	 * Sets the enabled state of the receiver.
	 * <p>
	 * Allows to communicate to the client to skip this painter in the painting sequence.
	 * @param enabled the new enabled state
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	/**
	 * Returns true if the painter is enabled.
	 * <p>
	 * Communicates to the client to skip this painter in the painting sequence.
	 * @return the enabled state
	 */
	public boolean isEnabled() {
		return enabled;
	}


	
//	public SizeMeter getSizeMeter() {
//		return meter;
//	}
//
//	public void setMeter(SizeMeter meter) {
//		this.meter = meter;
//	}
	
	protected int alignDelta(int align, int bound, int width, int margin) {
		switch (align) {
		case SWT.CENTER:
			return (bound - width) / 2; 
		case SWT.RIGHT: case SWT.BOTTOM: case SWT.END:
			return bound - width - margin; 
		}
		return margin;
	}
	
	
	/*------------------------------------------------------------------------
	 * Static members 
	 */
	
	public static RGB blend(RGB c1, RGB c2, int ratio) {
		int r = blend(c1.red, c2.red, ratio);
		int g = blend(c1.green, c2.green, ratio);
		int b = blend(c1.blue, c2.blue, ratio);
		return new RGB(r, g, b);
	}
	
	private static int blend(int v1, int v2, int ratio) {
		return (ratio*v1 + (100-ratio)*v2)/100;
	}

	/**
	 * Creates a default selection color for body cells by blending 40& between 
	 * the <code>SWT.COLOR_LIST_SELECTION</code> and <code>COLOR_LIST_BACKGROUND</code>.
	 * @return
	 */
	public static Color getDefaultBodySelectionColor() {
		RGB selectionColor = Resources.getColor(SWT.COLOR_LIST_SELECTION).getRGB();
		RGB whiteColor = Resources.getColor(SWT.COLOR_LIST_BACKGROUND).getRGB();
		RGB color = blend(selectionColor, whiteColor, 40);
		return Resources.getColor(color);
	}
	
	/**
	 * Creates a default selection color for header cells by blending 40& between 
	 * the <code>SWT.COLOR_LIST_SELECTION</code> and <code>SWT.COLOR_WIDGET_BACKGROUND</code>.
	 * @return
	 */
	public static Color getDefaultHeaderSelectionColor() {
		RGB selectionColor = Resources.getColor(SWT.COLOR_LIST_SELECTION).getRGB();
		RGB whiteColor = Resources.getColor(SWT.COLOR_WIDGET_BACKGROUND).getRGB();
		RGB color = blend(selectionColor, whiteColor, 90);
		return Resources.getColor(color);
	}
}
