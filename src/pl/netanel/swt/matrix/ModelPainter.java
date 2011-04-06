package pl.netanel.swt.matrix;

import java.math.BigInteger;

import org.eclipse.swt.graphics.Color;


/**
 * Gets text, vertical align and horizontal align for the cell from the <code>MatrixModel</code> 
 * using its methods:
 * <ul>
 * <li><code>getText(Index, Index)</code> 
 * <li><code>getVerticalAlignment(Index, Index)</code>
 * <li><code>getHorizontalAlignment(Index, Index)</code>
 * </ul> 
 * respectively.
 * <p>
 * Defines also a default <code>CellSizeMeter</code> to calculate an optimal 
 * column width or row height.
 * 
 * @author Jacek created 07-02-2011
 */
class ModelPainter extends TextPainter {
	
	private Color lastForeground, lastBackground, defaultBackground, background,  
		selectionBackground, selectionForeground;
	private final Zone zone;
	private boolean shouldHighlight;
	private final Matrix matrix;

//	private int lineWidth0, lineWidth1;
//	private Color lineColor;
	
	public ModelPainter(Matrix matrix, Zone zone) {
		this.matrix = matrix;
		this.zone = zone;
	}
	
	@Override
	public boolean init() {
		super.init();
		lastBackground = defaultBackground = zone.getDefaultBackground();
		lastForeground = zone.getDefaultForeground();
		selectionBackground = zone.getSelectionBackground();
		selectionForeground = zone.getSelectionForeground();
		gc.setForeground(lastForeground);
		if (lastBackground != null) {
			gc.setBackground(lastBackground);
			gc.fillRectangle(zone.getBounds());
		}
		
		shouldHighlight = !zone.equals(matrix.getBody()) || 
			zone.getSelectionCount().compareTo(BigInteger.ONE) != 0;
		//gc.setAdvanced(false);
		return true;
	}

	@Override
	public void paint(Number index0, Number index1, int x, int y, int width, int height) {
		Color foreground;
		boolean isSelected = shouldHighlight && zone.isSelected(index0, index1);
		if (isSelected) {
			foreground = selectionForeground;  
			background = selectionBackground;
		} else {
			foreground = zone.getForeground(index0, index1);
			background = zone.getBackground(index0, index1);
		}
		
		// Only set color if there is a change
		if (foreground != null) { // && !foreground.equals(lastForeground)) {
			gc.setForeground(lastForeground = foreground);
		}
		if (background != null) {
			if (!background.equals(lastBackground)) {
				gc.setBackground(lastBackground = background);
			}
			if (!background.equals(defaultBackground)) {
				gc.fillRectangle(x, y, width, height);
			}
		}
		
//		align0 = model.getVerticalAlignment(item0, item1);
//		align1 = model.getHorizontalAlignment(item0, item1);

//		lineWidth0 = zone.section0.getLineWidth(index0);
//		lineWidth1 = zone.section1.getLineWidth(index1);
//		lineColor = Resources.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW);
		
		text = zone.getText(index0, index1);
		super.paint(index0, index1, x, y, width, height);
		
//		// Paint lines
//		gc.setBackground(lastBackground = lineColor);
//		gc.fillRectangle(x + width, y, lineWidth0, y + height + lineWidth1);
//		gc.fillRectangle(x, y + height, x + width + lineWidth0, lineWidth1);
	}
	
//	@Override
//	public SizeMeter getSizeMeter() {
//		if (meter == null) {
//			meter = new ModelTextPainterSizeMeter();
//		}
//		return meter;
//	}
//	
//	
//	class ModelTextPainterSizeMeter implements SizeMeter {
//
//		@Override
//		public int measureWidth(ItemPairSequence seq) {
//			int w = 0;
//			Font lastFont = null;
//			int[] cache = null;
//			seq.init();
//			while (seq.next()) {
//				int s0 = seq.getSection0(), s1 = seq.getSection1();
//				Index i0 = seq.getIndex0(), i1 = seq.getIndex1();
//				String text = model.getText(s0, i0, s1, i1);
//				Font font = model.getFont(s0, i0, s1, i1);
//				
//				if (font != lastFont) {
//					lastFont = font;
//					cache = FontWidthCache.get(gc, font);
//				}
//				w = Math.max(w, FontWidthCache.getWidth(text, gc, cache));
//				//w = Math.max(w, gc.stringExtent(text).x);
//			}
//			return w + 2 * margin1;
//		}
//
//		@Override
//		public int measureHeight(ItemPairSequence seq) {
//			int w = 0;
//			Font lastFont = null;
//			seq.init();
//			while (seq.next()) {
//				int s0 = seq.getSection0(), s1 = seq.getSection1();
//				Index i0 = seq.getIndex0(), i1 = seq.getIndex1();
//				Font font = model.getFont(s0, i0, s1, i1);
//				if (font != lastFont) {
//					lastFont = font;
//					w = Math.max(w, gc.stringExtent("b").y);
//				}
//			}
//			return w + 2 * margin0;
//		}
//		
//	}
}
