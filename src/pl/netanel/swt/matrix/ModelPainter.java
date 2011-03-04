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
public class ModelPainter extends TextPainter {
	private static final MutableBigInteger ONE = new MutableBigInteger(BigInteger.ONE);
	
	private Color lastForeground, lastBackground, defaultBackground, background,  
		selectionBackground, selectionForeground;
	private final Zone zone;
	private boolean shouldHighlight;
	
	public ModelPainter(Zone zone) {
		this.zone = zone;
	}
	
	@Override
	public void init() {
		super.init();
		lastBackground = defaultBackground = zone.getDefaultBackground();
		lastForeground = zone.getDefaultForeground();
		selectionBackground = zone.getSelectionBackground();
		selectionForeground = zone.getSelectionForeground();
		gc.setBackground(lastBackground);
		gc.setForeground(lastForeground);
		
		shouldHighlight = zone.is(Zone.BODY) || 
			BigIntegerMath.getInstance().compare(zone.getSelectionCount(), ONE) != 0;
		
		gc.fillRectangle(zone.getBounds());
	}

	@Override
	public void beforePaint(MutableNumber index0, MutableNumber index1) {
		super.beforePaint(index0, index1);
		
		boolean isSelected = zone.isSelected(index0, index1) && shouldHighlight;
		Color foreground = isSelected 
				? selectionForeground
				: zone.getForeground(index0, index1);
		
		// Only set color if there is a change
		if (!foreground.equals(lastForeground)) {
			gc.setForeground(lastForeground = foreground);
		}
		
		background = isSelected 
				? selectionBackground
				: zone.getBackground(index0, index1);
		
		// Only set color if there is a change
		if (!background.equals(lastBackground)) {
			gc.setBackground(lastBackground = background);
		}
		
		text = zone.getText(index0, index1);
		
//		align0 = model.getVerticalAlignment(item0, item1);
//		align1 = model.getHorizontalAlignment(item0, item1);
//		
//		Color foreground = Util.notNull(model.getForeground(item0, item1), matrix.getForeground()); 
//		if (!foreground.equals(lastForeground)) {
//			lastForeground = foreground;
//			gc.setForeground(foreground);
//		}
	}
	
	@Override
	public void paint(int x, int y, int width, int height) {
		if (!background.equals(defaultBackground)) {
			gc.fillRectangle(x, y, width, height);
		}
		super.paint(x, y, width, height);
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
