package pl.netanel.swt.matrix;

import java.math.BigInteger;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Listener;

import pl.netanel.swt.Listeners;
import pl.netanel.swt.Resources;
import pl.netanel.swt.matrix.Layout.LayoutSequence;
import pl.netanel.util.ImmutableIterator;


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
public class Zone<N0 extends Number, N1 extends Number> {
	public static final int NONE = -1;
	public static final int ANY = 0;
	public static final int BODY = 1;
	public static final int TOP_LEFT= 2;
	public static final int ROW_HEADER = 3;
	public static final int COLUMN_HEADER = 4;
//	public static final int ROW_FOOTER = 5;
//	public static final int COLUMN_FOOTER = 6;
//	public static final int BOTTOM_RIGHT = 7;
	
	public final Painter<N0, N1> painter;
	
	Section<N0> section0;
	Section<N1> section1;
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

	public Zone(Section<N0> section0, Section<N1> section1, int type) {
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
	
//	public IndexPairSequence getSelection() {
//		return new IndexPairSequence(cellSelection.copy());
//	}
	
	public Iterator<Cell<N0, N1>> getSelection() {
		return new ImmutableIterator<Cell<N0, N1>>() {
			IndexPairSequence seq = new IndexPairSequence(cellSelection.copy());
			private boolean next;
			@Override
			public boolean hasNext() {
				next = seq.next();
				return next;
			}

			@Override
			public Cell<N0, N1> next() {
				return next ? new Cell (seq.index0(), seq.index1()) : null;
			}
		};
	}

	public String getText(N0 index0, N1 index1) {
		return index0.toString() + ", " + index1.toString();
	}
	
	
	public void setBackground(N0 index0, N1 index1, Color color) {
	}
	public Color getBackground(N0 index0, N1 index1) {
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
	
	
	public void setForeground(N0 index0, N1 index1, Color color) {
	}
	public Color getForeground(N0 index0, N1 index1) {
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
	
	public boolean isSelected(N0 index0, N1 index1) {
		return cellSelection.contains(index0, index1);
	}
	
	
	boolean isSelected(MutableNumber index0, MutableNumber index1) {
		return cellSelection.contains(index0.getValue(), index1.getValue());
	}
	
	public void setSelected(
			N0 start0, N0 end0,
			N1 start1, N1 end1, boolean selected) {
		
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

	void paint(GC gc, final Layout layout0, final Layout layout1, final Dock dock0, final Dock dock1) {
		for (Painter p: painter.children) {
			if (!p.isEnabled() || !p.init(gc)) continue;
			
			switch (p.scope) {
			
			case Painter.SCOPE_CELLS_HORIZONTALLY:
				LayoutSequence seq0 = layout0.cellSequence(dock0, section0.core);
				LayoutSequence seq1 = layout1.cellSequence(dock1, section1.core);
				for (seq0.init(); seq0.next();) {
					for (seq1.init(); seq1.next();) {
						p.beforePaint(seq0.item.index, seq1.item.index);
						p.paint(seq1.getDistance(), seq0.getDistance(), seq1.getWidth(), seq0.getWidth());
					}
				}
				break;
				
			case Painter.SCOPE_CELLS_VERTICALLY:
				seq0 = layout0.cellSequence(dock0, section0.core);
				seq1 = layout1.cellSequence(dock1, section1.core);
				for (seq1.init(); seq1.next();) {
					for (seq0.init(); seq0.next();) {
						p.beforePaint(seq0.item.index, seq1.item.index);
						p.paint(seq1.getDistance(), seq0.getDistance(), seq1.getWidth(), seq0.getWidth());
					}
				}
				break;
			
			case Painter.SCOPE_ROW_CELLS:
				seq0 = layout0.cellSequence(dock0, section0.core);
				for (seq0.init(); seq0.next();) {
					p.beforePaint(seq0.item.index, null);
					p.paint(bounds.x, seq0.getDistance(), bounds.width, seq0.getWidth());
				}
				break;
				
			case Painter.SCOPE_COLUMN_CELLS:
				seq1 = layout1.cellSequence(dock1, section1.core);
				for (seq1.init(); seq1.next();) {
					p.beforePaint(null, seq1.item.index);
					p.paint(seq1.getDistance(), bounds.y, seq1.getWidth(), bounds.height);
				}
				break;
				
			case Painter.SCOPE_ROW_LINES:
				seq0 = layout0.lineSequence(dock0, section0.core);
				for (seq0.init(); seq0.next();) {
					p.beforePaint(seq0.item.index, null);
					p.paint(bounds.x, seq0.getDistance(), bounds.width, seq0.getWidth());
				}
				break;
			
			case Painter.SCOPE_COLUMN_LINES:
				seq1 = layout1.lineSequence(dock1, section1.core);
				for (seq1.init(); seq1.next();) {
					p.beforePaint(null, seq1.item.index);
					p.paint(seq1.getDistance(), bounds.y, seq1.getWidth(), bounds.height);
				}
				break;
				
			}
			p.clean();
		}
	}

	public Section getSection0() {
		return section0;
	}
	public Section getSection1() {
		return section1;
	}

	public static class Cell<N0, N1> {
		N0 index0;
		N1 index1;
		public Cell(N0 index0, N1 index1) {
			this.index0 = index0;
			this.index1 = index1;
		}
	}
}
