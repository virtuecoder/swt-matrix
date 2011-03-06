package pl.netanel.swt.matrix;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Listener;

import pl.netanel.swt.Listeners;


/**
 * Constitutes a region of a matrix where a section from the row axis 
 * and a section from the column axis cross with each other.  
 * <p>
 * Region have cell painters and line painters to paint itself on the screen.
 * </p><p>
 * It can also have dedicated event handlers to implement special behavior 
 * for different parts of the matrix.  
 * </p>
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
	
	/**
	 * Painters for the cells of this zone.
	 */
	public Painters cellPainters;
	/**
	 * Painters for the horizontal lines inside of this zone.
	 */
	public Painters linePainters0; 
	/**
	 * Painters for the vertical lines inside of this zone.
	 */
	public Painters linePainters1;
	
	Section section0, section1;
	CellSet cellSelection;
	CellSet lastSelection; // For adding selection
	
	private final int type;
	final Listeners listeners;
	private boolean isSelectionEnabled;
	
	private Color defaultBackground, defaultForeground, selectionBackground, selectionForeground;
	private final Rectangle bounds;

	private Zone(int id) {
		this.type = id;
		cellPainters = new Painters();
		linePainters0 = new Painters();
		linePainters1 = new Painters();
		linePainters0.add(new LinePainter());
		linePainters1.add(new LinePainter());
		listeners = new Listeners();
		isSelectionEnabled = true;
		bounds = new Rectangle(0, 0, 0, 0);
	}

	public Zone(Section section0, Section section1) {
		this(NONE);
		this.section0 = section0;
		this.section1 = section1;
		cellSelection = new CellSet(section0.math, section1.math);
		lastSelection = new CellSet(section0.math, section1.math);
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

	public boolean isSelectionEnabled() {
		return isSelectionEnabled;
	}

	public void setSelectionEnabled(boolean isSelectionEnabled) {
		this.isSelectionEnabled = isSelectionEnabled;
		cellSelection.clear();
	}

	
	public boolean isSelected(MutableNumber index0, MutableNumber index1) {
		return cellSelection.contains(index0, index1);
	}
	
	public void setSelectedAll(boolean selected) {
		cellSelection.change(
			section0.math.ZERO(), section0.getLast(), 
			section1.math.ZERO(), section1.getLast(), selected);
	}

	public void backupSelection() {
		lastSelection = cellSelection.copy();
	}
	
	public void restoreSelection() {
		cellSelection = lastSelection.copy();
	}

	
	/**
	 * Returns the number of selected cells in this zone.
	 * <p>
	 * If the cell selection is disabled the it always returns a 
	 * {@link BigIntegerMutableNumber} with zero value.
	 * 
	 * @return {@link BigIntegerMutableNumber} with the count of selected cells
	 */
	public MutableBigInteger getSelectionCount() {
		if (!isSelectionEnabled()) {
			return BigIntegerMath.getInstance().create(0);
		}
		return cellSelection.getCount();
	}
	
	public CellSequence getSelection() {
		return new CellSequence(cellSelection.copy());
	}

	public String getText(MutableNumber index0, MutableNumber index1) {
		return index0.toString() + ", " + index1.toString();
	}
	
	
	public void setBackground(MutableNumber index0, MutableNumber index1, Color color) {
	}
	public Color getBackground(MutableNumber index0, MutableNumber index1) {
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
	
	
	public void setForeground(MutableNumber index0, MutableNumber index1, Color color) {
	}
	public Color getForeground(MutableNumber index0, MutableNumber index1) {
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

	public void setBounds(int x, int y, int width, int height) {
		bounds.x = x;
		bounds.y = y;
		bounds.width = width;
		bounds.height = height;
	}

	public boolean isVisible() {
		return section0.isVisible() && section1.isVisible();
	}

	
}
