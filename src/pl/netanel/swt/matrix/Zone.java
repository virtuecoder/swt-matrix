package pl.netanel.swt.matrix;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
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
 * Zone has painters to paint itself on the screen.
 * </p><p>
 * It can also have dedicated event handlers to implement special behavior 
 * for different parts of the matrix.  
 * </p>
 * @see SectionClient
 * 
 * @author Jacek
 * @created 13-10-2010
 */
public class Zone<N0 extends Number, N1 extends Number> {
	
	Painters<N0, N1> painters;
	Matrix<N0, N1> matrix;
	Section<N0> section0;
	Section<N1> section1;
	SectionClient<N0> sectionClient0;
	SectionClient<N1> sectionClient1;
	CellSet cellSelection;
	CellSet lastSelection; // For adding selection
	
	final Listeners listeners;
	final ArrayList<GestureBinding> bindings;
	boolean selectionEnabled;
	
	private Color selectionBackground, selectionForeground;
	private final Rectangle bounds;
	
	private CellValues<N0, N1, Color> background, foreground;
//	private CellValues<N0, N1, String> text;
//	private CellValues<N0, N1, Image> image;
	private boolean backgroundEnabled, foregroundEnabled;
	
	public Zone(Section<N0> section0, Section<N1> section1) {
		this();
		this.section0 = section0 instanceof SectionClient ? ((SectionClient) section0).core : section0;
		this.section1 = section1 instanceof SectionClient ? ((SectionClient) section1).core : section1;
		cellSelection = new CellSet(section0.math, section1.math);
		lastSelection = new CellSet(section0.math, section1.math);
		
		RGB selectionColor = Resources.getColor(SWT.COLOR_LIST_SELECTION).getRGB();
		RGB whiteColor = Resources.getColor(SWT.COLOR_LIST_BACKGROUND).getRGB();
		RGB color = Painter.blend(selectionColor, whiteColor, 40);
		selectionBackground = Resources.getColor(color);
		
		background = new MapValueToCellSet(this.section0.math, this.section1.math);
		foreground = new MapValueToCellSet(this.section0.math, this.section1.math);
//		text = new MapValueToCellSet(this.section0.math, this.section1.math);
//		image = new MapValueToCellSet(this.section0.math, this.section1.math);
	}
	
	Zone() {
		painters = new Painters();
		listeners = new Listeners();
		bindings = new ArrayList();
		bounds = new Rectangle(0, 0, 0, 0);
		selectionEnabled = true;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ZoneClient) obj = ((ZoneClient) obj).core;
		return super.equals(obj);
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
	
	public Section getSectionUnchecked0() {
		return section0;
	}
	public Section getSectionUnchecked1() {
		return section1;
	}

	public Section getSection0() {
		return matrix.axis0.sections.get(section0.index);
	}
	public Section getSection1() {
		return matrix.axis1.sections.get(section1.index);
	}

	
	void setDefaultBodyStyle() {
		Painter painter = new Painter("cells", Painter.SCOPE_CELLS_HORIZONTALLY);
		painter.matrix = matrix;
		painter.zone = this;
		addPainter(painter);
		Color color = Resources.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW);
		addPainter(new LinePainter("row lines", Painter.SCOPE_HORIZONTAL_LINES, color ));
		addPainter(new LinePainter("column lines", Painter.SCOPE_VERTICAL_LINES, color));
	}
	
	void setDefaultHeaderStyle() {
		setDefaultForeground(Resources.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		setDefaultBackground(Resources.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		
		RGB selectionColor = Resources.getColor(SWT.COLOR_LIST_SELECTION).getRGB();
		RGB whiteColor = Resources.getColor(SWT.COLOR_WIDGET_BACKGROUND).getRGB();
		RGB rgb = Painter.blend(selectionColor, whiteColor, 90);
		setSelectionBackground(Resources.getColor(rgb));
		
		if (getPainterCount() == 0) {
			Painter painter = new Painter("cells", Painter.SCOPE_CELLS_HORIZONTALLY);
			painter.matrix = matrix;
			painter.zone = this;
			addPainter(painter);
			final Color color = Resources.getColor(SWT.COLOR_WIDGET_DARK_SHADOW);
			addPainter(new LinePainter("row lines", Painter.SCOPE_HORIZONTAL_LINES, color));
			addPainter(new LinePainter("column lines", Painter.SCOPE_VERTICAL_LINES, color));
		}
	}
	
	public void addListener(int type, Listener listener) {
		listeners.add(type, listener);
	}
	
	public void removeListener(int type, Listener listener) {
		listeners.remove(type, listener);
	}

	
	public String getText(N0 index0, N1 index1) {
//		return text.getValue(index0, index1);
		return null;
	}
	public void setText(N0 index0, N1 index1, String value) {
		setText(index0, index0, index1, index1, value);
	}
	public void setText(N0 start0, N0 end0, N1 start1, N1 end1, String value) {
//		text.setValue(start0, end0, start1, end1, value);
		throw new UnsupportedOperationException();
	}
	
	public Image getImage(N0 index0, N1 index1) {
//		return image.getValue(index0, index1);
		return null;
	}
	public void setImage(N0 index0, N1 index1, Image value) {
		setImage(index0, index0, index1, index1, value);
	}
	public void setImage(N0 start0, N0 end0, N1 start1, N1 end1, Image value) {
//		image.setValue(start0, end0, start1, end1, value);
		throw new UnsupportedOperationException();
	}
	
	
	
	public boolean isForegroundEnabled() {
		return foregroundEnabled;
	}

	public void setForegroundEnabled(boolean enabled) {
		this.foregroundEnabled = enabled;
	}

	public boolean isBackgroundEnabled() {
		return backgroundEnabled;
	}
	
	public void setBackgroundEnabled(boolean enabled) {
		this.backgroundEnabled = enabled;
	}

	public Color getBackground(N0 index0, N1 index1) {
		return background.getValue(index0, index1);
	}
	
	public void setBackground(N0 index0, N1 index1, Color color) {
		setBackground(index0, index0, index1, index1, color);
	}
	
	public void setBackground(N0 start0, N0 end0, N1 start1, N1 end1, Color color) {
		backgroundEnabled = true;
		background.setValue(start0, end0, start1, end1, color);
	}

	public void setDefaultBackground(Color color) {
		background.setDefaultValue(color);
	}
	public Color getDefaultBackground() {
		return background.getDefaultValue();
	}	
	
	
	public Color getForeground(N0 index0, N1 index1) {
		return foreground.getValue(index0, index1);
	}
	
	public void setForeground(N0 index0, N1 index1, Color color) {
		setForeground(index0, index0, index1, index1, color);
	}
	
	public void setForeground(N0 start0, N0 end0, N1 start1, N1 end1, Color color) {
		foreground.setValue(start0, end0, start1, end1, color);
	}
	
	public void setDefaultForeground(Color color) {
		foreground.setDefaultValue(color);
	}
	public Color getDefaultForeground() {
		return foreground.getDefaultValue();
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

	public void setSelectionForeground(Color color) {
		selectionForeground = color;
	}
	public Color getSelectionForeground() {
		return selectionForeground;
	}
	
	public void setSelectionBackground(Color color) {
		selectionBackground = color;
	}
	public Color getSelectionBackground() {
		return selectionBackground;
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

	public void setSelectedAll(boolean selected) {
		if (!selectionEnabled) return;
		if (selected) {
			cellSelection.add(
					section0.math.ZERO_VALUE(), section0.math.decrement(section0.getCount()), 
					section1.math.ZERO_VALUE(), section1.math.decrement(section1.getCount()));
		} else {
			cellSelection.clear();
			lastSelection.clear();
		}
		section0.setSelectedAll(selected);
		section1.setSelectedAll(selected);
	}
	
	public BigInteger getSelectedCount() {
		return cellSelection.getCount().getValue();
	}

	public NumberPairSequence<N0, N1> getSelected() {
		return new NumberPairSequence(cellSelection.copy());
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
	
	Iterator<Cell<N0, N1>> getSelectedIterator() {
		return new ImmutableIterator<Cell<N0, N1>>() {
			NumberPairSequence seq = new NumberPairSequence(cellSelection.copy());
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
	
	static class Cell<N0, N1> {
		public N0 index0;
		public N1 index1;
		public Cell(N0 index0, N1 index1) {
			this.index0 = index0;
			this.index1 = index1;
		}
	}
	
	
	
	void backupSelection() {
		lastSelection = cellSelection.copy();
	}
	
	void restoreSelection() {
		cellSelection = lastSelection.copy();
	}


	
	/*------------------------------------------------------------------------
	 * Painting 
	 */

	void paint(GC gc, final Layout layout0, final Layout layout1, final Frozen dock0, final Frozen dock1) {
		for (Painter p: painters) {
			if (!p.isEnabled() || !p.init(gc)) continue;
			
			int distance = 0, width = 0;
			Number index;
			switch (p.scope) {
			
			case Painter.SCOPE_CELLS_HORIZONTALLY:
				LayoutSequence seq0 = layout0.cellSequence(dock0, section0);
				LayoutSequence seq1 = layout1.cellSequence(dock1, section1);
				for (seq0.init(); seq0.next();) {
					distance = seq0.getDistance();
					width = seq0.getWidth();
					index = seq0.item.index;
					for (seq1.init(); seq1.next();) {
						p.paint(index, seq1.item.index, 
							seq1.getDistance(), distance, seq1.getWidth(), width);
					}
				}
				break;
				
			case Painter.SCOPE_CELLS_VERTICALLY:
				seq0 = layout0.cellSequence(dock0, section0);
				seq1 = layout1.cellSequence(dock1, section1);
				for (seq1.init(); seq1.next();) {
					distance = seq1.getDistance();
					width = seq1.getWidth();
					index = seq1.item.index;
					for (seq0.init(); seq0.next();) {
						p.paint(seq0.item.index, seq1.item.index,
							distance, seq0.getDistance(), width, seq0.getWidth());
					}
				}
				break;
			
			case Painter.SCOPE_ROW_CELLS:
				seq0 = layout0.cellSequence(dock0, section0);
				distance = bounds.x;
				width = bounds.width;
				for (seq0.init(); seq0.next();) {
					p.paint(seq0.item.index, null, distance, seq0.getDistance(), width, seq0.getWidth());
				}
				break;
				
			case Painter.SCOPE_COLUMN_CELLS:
				seq1 = layout1.cellSequence(dock1, section1);
				distance = bounds.y;
				width = bounds.height;
				for (seq1.init(); seq1.next();) {
					p.paint(null, seq1.item.index, seq1.getDistance(), distance, seq1.getWidth(), width);
				}
				break;
				
			case Painter.SCOPE_HORIZONTAL_LINES:
				seq0 = layout0.lineSequence(dock0, section0);
				distance = bounds.x;
				width = bounds.width;
				for (seq0.init(); seq0.next();) {
					p.paint(seq0.item.index, null, distance, seq0.getDistance(), width, seq0.getWidth());
				}
				break;
			
			case Painter.SCOPE_VERTICAL_LINES:
				seq1 = layout1.lineSequence(dock1, section1);
				distance = bounds.y;
				width = bounds.height;
				for (seq1.init(); seq1.next();) {
					p.paint(null, seq1.item.index, seq1.getDistance(), distance, seq1.getWidth(), width);
				}
				break;
				
			}
			p.clean();
		}
	}
	
	public void addPainter(Painter<N0, N1> painter) {
		painters.add(painter);
		setPainterMatrixAndZone(painter);
	}

	public void addPainter(int index, Painter<N0, N1> painter) {
		// Check uniqueness of painters names
		painters.add(index, painter);
		setPainterMatrixAndZone(painter);
	}
	
	public void setPainter(int index, Painter<N0, N1> painter) {
		painters.set(index, painter);
		setPainterMatrixAndZone(painter);
	}
	
	public void replacePainter(Painter<N0, N1> painter) {
		painters.replacePainter(painter);
		setPainterMatrixAndZone(painter);
	}
	
	private void setPainterMatrixAndZone(Painter painter) {
		if (painter.scope == Painter.SCOPE_CELLS_HORIZONTALLY ||
				painter.scope == Painter.SCOPE_CELLS_VERTICALLY) 
		{
			painter.zone = this;
			painter.matrix = matrix;
		}
	}
	
	public void removePainter(int index) {
		painters.remove(index);
	}
	
	public int indexOfPainter(String name) {
		return painters.indexOfPainter(name);
	}
	
	public Painter<N0, N1> getPainter(String name) {
		return painters.get(indexOfPainter(name));
	}
	
	int getPainterCount() {
		return painters.size();
	}
	
	public Painter<N0, N1> get(int index) {
		return painters.get(index);
	}
	
	private static class LinePainter extends Painter {
		private final Color color;

		public LinePainter(String name, int scope, Color color) {
			super(name, scope);
			this.color = color;
		}
		
		@Override
		public void paint(Number index0, Number index1, int x, int y, int width, int height) {
			gc.setBackground(color);
			gc.fillRectangle(x, y, width, height);
		}
	}


	public void insert(int axisIndex, Section section, Number target, Number count) {
		if (axisIndex == 0) {
			if (section0.equals(section)) {
				cellSelection.insert0(target, count);
				lastSelection.insert0(target, count);
			}
		}
		else {
			if (section1.equals(section1)) {
				cellSelection.insert1(target, count);
				lastSelection.insert1(target, count);
			}
		}
	}

	public void delete(int axisIndex, Section section, Number start, Number end) {
		if (axisIndex == 0) {
			if (section0.equals(section)) {
				cellSelection.delete0(start, end);
				lastSelection.delete0(start, end);
			}
		}
		else {
			if (section1.equals(section1)) {
				cellSelection.delete1(start, end);
				lastSelection.delete1(start, end);
			}
		}
	}
	
	
	/*------------------------------------------------------------------------
	 * Non-public 
	 */
	
	void setMatrix(Matrix<N0, N1> matrix) {
		this.matrix = matrix;
		for (Painter painter: painters) {
			if (painter.scope == Painter.SCOPE_CELLS_HORIZONTALLY ||
					painter.scope == Painter.SCOPE_CELLS_VERTICALLY) 
			{
				painter.zone = this;
				painter.matrix = matrix;
			}
		}
	}
	

}
