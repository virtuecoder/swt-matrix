package pl.netanel.swt.matrix;

import java.math.BigInteger;

import org.eclipse.swt.graphics.Color;


/**
 * Uses <code>MatrixModel.getBackgroundColor(LayoutItem, LayoutItem)</code> 
 * to draw the background of the painted cell.
 * <p>
 * Alters the background if the cell is selected. 
 * 
 * @author Jacek created 07-02-2011
 */
public class DefaultBackgroundPainter extends BackgroundPainter {
	private static final MutableBigInteger ONE = new MutableBigInteger(BigInteger.ONE);
	private Color lastColor, selectionColor;
	private boolean isHighlighted;
	private final Zone zone;

	public DefaultBackgroundPainter(Zone zone, Color defaultColor, Color selectionColor) {
		super(defaultColor);
		this.zone = zone;
		this.selectionColor = selectionColor;
	}
	
	@Override
	protected void init() {
		lastColor = null;
		color = zone.getDefaultBackground();
		super.init();
		isHighlighted = zone.is(Zone.BODY) || 
			BigIntegerMath.getInstance().compare(zone.getSelectionCount(), ONE) != 0;
	}

	@Override
	public void beforePaint(MutableNumber index0, MutableNumber index1) {
		super.beforePaint(index0, index1);
		
		Color color2 = 
			zone.isSelected(index0, index1) && isHighlighted
				? selectionColor 
				: zone.getBackground(index0, index1);
		
		// Only set color if there is a change
		if (!color2.equals(lastColor)) {
			lastColor = color2;
			gc.setBackground(color2);
		}
	}
}
