package pl.netanel.swt.matrix;

import java.math.BigInteger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Listener;

import pl.netanel.swt.Listeners;
import pl.netanel.swt.Resources;


/**
 * Constitutes a region of a matrix where a section from the row axis 
 * and a section from the column axis cross with each other.  
 * <p>
 * Region have cell painters and line painters to paint itself on the screen.
 * </p><p>
 * It can also have dedicated event handlers to implement special behavior 
 * for different parts of the matrix.  
 * </p>
 * @see SectionUnchecked
 * 
 * @author Jacek
 * @created 13-10-2010
 */
public class Zone {
	public static final int NONE = -1;
	public static final int ANY = 0;
	public static final int BODY = 1;
	public static final int TOP_LEFT= 2;
	public static final int ROW_HEADER = 3;
	public static final int COLUMN_HEADER = 4;
//	public static final int ROW_FOOTER = 5;
//	public static final int COLUMN_FOOTER = 6;
//	public static final int BOTTOM_RIGHT = 7;
	
	public Painter painter;
	
	Section section0, section1;
	CellSet cellSelection;
	CellSet lastSelection; // For adding selection
	
	private final int type;
	final Listeners listeners;
	boolean selectionEnabled;
	
	private Color defaultBackground, defaultForeground, selectionBackground, selectionForeground;
	private final Rectangle bounds;

	private Zone(int id) {
		this.type = id;
		painter = new Painter("zone");
//
//		cellPainters = new Painters();
//		linePainters0 = new Painters();
//		linePainters1 = new Painters();
//		linePainters0.add(new LinePainter());
//		linePainters1.add(new LinePainter());
		listeners = new Listeners();
		selectionEnabled = true;
		bounds = new Rectangle(0, 0, 0, 0);
	}

	public Zone(Section section0, Section section1, int type) {
		this(type);
		this.section0 = section0;
		this.section1 = section1;
		cellSelection = new CellSet(section0.core.math, section1.core.math);
		lastSelection = new CellSet(section0.core.math, section1.core.math);
		
		RGB selectionColor = Resources.getColor(SWT.COLOR_LIST_SELECTION).getRGB();
		RGB whiteColor = Resources.getColor(SWT.COLOR_LIST_BACKGROUND).getRGB();
		RGB color = Painter.blend(selectionColor, whiteColor, 40);
		selectionBackground = Resources.getColor(color);
	}
	
	@Override
	public String toString() {
		return section0.toString() + " " + section1.toString();
//		return "zone " + (
//			type == ANY ? "ANY" :
//			type == BODY ? "BODY" :
//			type == TOP_LEFT ? "TOP_LEFT" :
//			type == ROW_HEADER ? "ROW_HEADER" :
//			type == COLUMN_HEADER ? "COLUMN_HEADER" : "?"
//				id == BOTTOM_RIGHT ? "BOTTOM_RIGHT" :
//				id == ROW_FOOTER ? "ROW_FOOTER" :
//				id == COLUMN_FOOTER ? "COLUMN_FOOTER" :
//		);
	}
	
	/**
	 * Returns true if the given id matches the zone id, or false otherwise.
	 * @param id to compare with
	 * @return id equality
	 */
	public boolean is(int id) {
		return this.type == id;
	}
	

	public void addListener(int type, Listener listener) {
		listeners.add(type, listener);
	}
	
	public void removeListener(int type, Listener listener) {
		listeners.remove(type, listener);
	}

	/**
     * Returns <code>true</code> if selection is enabled, false otherwise.
     * @return the selection enabled state
	 */
	public boolean isSelectionEnabled() {
		return selectionEnabled;
	}

	/**
     * Enables cell selection if the argument is <code>true</code>, 
     * or disables it otherwise.
     *
	 * @param selectionEnabled the new selection ability state.
	 */
	public void setSelectionEnabled(boolean isSelectionEnabled) {
		if (isSelectionEnabled == false) {
			cellSelection.clear();
		}
		this.selectionEnabled = isSelectionEnabled;
	}

	
		
	/**
	 * Returns the number of selected cells in this zone.
	 * <p>
	 * If the cell selection is disabled the it always returns a 
	 * {@link BigIntegerNumber} with zero value.
	 * 
	 * @return {@link BigIntegerNumber} with the count of selected cells
	 */
	public BigInteger getSelectionCount() {
		if (!selectionEnabled) {
			return BigInteger.ZERO;
		}
		return cellSelection.getCount().value;
	}
	
	public IndexPairSequence getSelection() {
		return new IndexPairSequence(cellSelection.copy());
	}

	public String getText(Number index0, Number index1) {
		return index0.toString() + ", " + index1.toString();
	}
	
	
	public void setBackground(Number index0, Number index1, Color color) {
	}
	public Color getBackground(Number index0, Number index1) {
		return defaultBackground;
	}
	public void setDefaultBackground(Color color) {
		defaultBackground = color;
	}
	public Color getDefaultBackground() {
		return defaultBackground;
	}	
	public void setSelectionBackground(Color color) {
		selectionBackground = color;
	}
	public Color getSelectionBackground() {
		return selectionBackground;
	}
	
	
	public void setForeground(Number index0, Number index1, Color color) {
	}
	public Color getForeground(Number index0, Number index1) {
		return defaultForeground;
	}
	public void setDefaultForeground(Color color) {
		defaultForeground = color;
	}
	public Color getDefaultForeground() {
		return defaultForeground;
	}
	public void setSelectionForeground(Color color) {
		selectionForeground = color;
	}
	public Color getSelectionForeground() {
		return selectionForeground;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	void setBounds(int x, int y, int width, int height) {
		bounds.x = x;
		bounds.y = y;
		bounds.width = width;
		bounds.height = height;
	}

	public boolean isVisible() {
		return section0.isVisible() && section1.isVisible();
	}

	
	/*------------------------------------------------------------------------
	 * Selection
	 */
	
	public boolean isSelected(Number index0, Number index1) {
		return cellSelection.contains(index0, index1);
	}
	
	
	boolean isSelected(MutableNumber index0, MutableNumber index1) {
		return cellSelection.contains(index0.getValue(), index1.getValue());
	}
	
	public void setSelected(
			Number start0, Number end0,
			Number start1, Number end1, boolean selected) {
		
		if (!selectionEnabled) return;
		if (selected) {
			cellSelection.add(start0, end0, start1, end1);
		} else {
			cellSelection.remove(start0, end0, start1, end1);			
		}
	}

	public void setSelected(boolean selected) {
		if (!selectionEnabled) return;
		if (selected) {
			cellSelection.add(
					section0.core.math.ZERO_VALUE(), section0.core.math.decrement(section0.core.getCount()), 
					section1.core.math.ZERO_VALUE(), section1.core.math.decrement(section1.core.getCount()));
		} else {
			cellSelection.clear();
			lastSelection.clear();
		}
	}
	
	public BigInteger getSelectedCount() {
		return cellSelection.getCount().getValue();
	}

	
	
	void backupSelection() {
		lastSelection = cellSelection.copy();
	}
	
	void restoreSelection() {
		cellSelection = lastSelection.copy();
	}

	void paint(GC gc, final Layout layout0, final Layout layout1, final Dock dock1, final Dock dock0) {
		painter.paint(gc, new BoundsProvider() {
			@Override
			BoundsSequence getSequence(int scope) {
				Rectangle zoneBounds = getBounds();
				switch (scope) {
				case Painter.CELL: 
					return new BoundsSequence(
						layout0.cellSequence(dock0, section0.core),
						layout1.cellSequence(dock1, section1.core) );
					
				case Painter.ROW_CELL:
					return new BoundsSequence(
							layout0.cellSequence(dock0, section0.core),
							layout1.singleSequence(zoneBounds.x, zoneBounds.width) );
					
				case Painter.COLUMN_CELL:
					return new BoundsSequence(
							layout0.singleSequence(zoneBounds.y, zoneBounds.height),
							layout1.cellSequence(dock1, section1.core) );
					
				case Painter.ROW_LINE:
					return new BoundsSequence(
							layout0.lineSequence(dock0, section0.core),
							layout1.singleSequence(zoneBounds.x, zoneBounds.width) );
					
				case Painter.COLUMN_LINE:
					return new BoundsSequence(
							layout0.singleSequence(zoneBounds.y, zoneBounds.height),
							layout1.lineSequence(dock1, section1.core) );

				default: return new BoundsSequence(
						layout0.singleSequence(bounds.y, bounds.height), 
						layout1.singleSequence(bounds.x, bounds.width) );
				}
			}
		});
	}

	public Section getSection0() {
		return section0;
	}
	public Section getSection1() {
		return section1;
	}
	
}
