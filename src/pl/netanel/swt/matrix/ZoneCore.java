package pl.netanel.swt.matrix;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TypedListener;

import pl.netanel.swt.matrix.Layout.LayoutSequence;
import pl.netanel.util.ImmutableIterator;
import pl.netanel.util.Preconditions;


/**
 * Constitutes a region of a matrix where a section from the row axis 
 * and a section from the column axis intersect with each other.  
 * <p>
 * Zone has painters to paint itself on the screen.
 * </p><p>
 * </p>
 * 
 * @param <N0> defines indexing type for rows
 * @param <N1> defines indexing type for columns
 * @see SectionCore
 * 
 * @author Jacek
 * @created 13-10-2010
 */
class ZoneCore<N0 extends Number, N1 extends Number> implements Zone<N0, N1> {
	
	final Painters<N0, N1> painters;
	SectionClient<N0> section0;
	SectionClient<N1> section1;
	CellSet cellSelection;
	CellSet lastSelection; // For adding selection
	ZoneEditor<N0, N1> editor;
	
	private final Listeners listeners;
	private final ArrayList<GestureBinding> bindings;
	private boolean selectionEnabled;
	
	private Matrix<N0, N1> matrix;
	private Color selectionBackground, selectionForeground;
	private final Rectangle bounds;
	
	private CellValues<N0, N1, Color> background, foreground;
	
//	private CellValues<N0, N1, String> text;
//	private CellValues<N0, N1, Image> image;
//	private boolean backgroundEnabled, foregroundEnabled;
	

  /**
	 * Constructs zone at intersection of the specified sections.
	 * 
	 * @param section0 section of the row axis
	 * @param section1 section of the column axis
	 */
	public ZoneCore(SectionClient<N0> section0, SectionClient<N1> section1) {
		this();
		this.section0 = section0;
		this.section1 = section1;
		Math math0 = SectionCore.from(section0).math;
		Math math1 = SectionCore.from(section0).math;
		cellSelection = new CellSet(math0, math1);
		lastSelection = new CellSet(math0, math1);
		
		RGB selectionColor = Resources.getColor(SWT.COLOR_LIST_SELECTION).getRGB();
		RGB whiteColor = Resources.getColor(SWT.COLOR_LIST_BACKGROUND).getRGB();
		RGB color = Painter.blend(selectionColor, whiteColor, 40);
		selectionBackground = Resources.getColor(color);
		
		background = new MapValueToCellSet(math0, math1);
		foreground = new MapValueToCellSet(math0, math1);
//		text = new MapValueToCellSet(this.section0.math, this.section1.math);
//		image = new MapValueToCellSet(this.section0.math, this.section1.math);
	}
	
	ZoneCore() {
		painters = new Painters();
		listeners = new Listeners();
		bindings = new ArrayList();
		bounds = new Rectangle(0, 0, 0, 0);
		selectionEnabled = true;
	}
	
	@Override public String toString() {
		return section0.toString() + " " + section1.toString();
	}


	@Override public Zone getCore() {
	  return this;
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#getSection0()
   */
	public Section getSection0() {
	  return section0;
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#getSection1()
   */
	public Section getSection1() {
	  return section1;
	}

	
void setDefaultBodyStyle() {
//	  setDefaultBackground(matrix.getBackground());
	  
		Painter painter = new Painter("cells", Painter.SCOPE_CELLS_HORIZONTALLY) {
			@Override public String getText(Number index0, Number index1) {
				return index0.toString() + ", " + index1.toString();
			}
		};
		painter.setZone(this);
		replacePainter(painter);
		Color color = Resources.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW);
		replacePainter(new LinePainter("row lines", Painter.SCOPE_HORIZONTAL_LINES, color ));
		replacePainter(new LinePainter("column lines", Painter.SCOPE_VERTICAL_LINES, color));
	}
	
	void setDefaultHeaderStyle(Painter cellsPainte) {
		setDefaultForeground(Resources.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		setDefaultBackground(Resources.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		
		RGB selectionColor = Resources.getColor(SWT.COLOR_LIST_SELECTION).getRGB();
		RGB whiteColor = Resources.getColor(SWT.COLOR_WIDGET_BACKGROUND).getRGB();
		RGB rgb = Painter.blend(selectionColor, whiteColor, 90);
		setSelectionBackground(Resources.getColor(rgb));
		
		if (getPainterCount() == 0) {
			cellsPainte.setZone(this);
			addPainter(cellsPainte);
			final Color color = Resources.getColor(SWT.COLOR_WIDGET_DARK_SHADOW);
			addPainter(new LinePainter("row lines", Painter.SCOPE_HORIZONTAL_LINES, color));
			addPainter(new LinePainter("column lines", Painter.SCOPE_VERTICAL_LINES, color));
		}
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#getCellBounds(N0, N1)
   */
	public Rectangle getCellBounds(N0 index0, N1 index1) {
		if (index0 == null || index1 == null) return null;
		Bound b0 = section0.core.axis.getCellBound(section0.core, index0);
		Bound b1 = section1.core.axis.getCellBound(section1.core, index1);
		if (b0 != null && b1 != null) {
			return new Rectangle(b1.distance, b0.distance, b1.width, b0.width);
		}
		return null; 
	}
	
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#setDefaultBackground(org.eclipse.swt.graphics.Color)
   */
	public void setDefaultBackground(Color color) {
		background.setDefaultValue(color);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#getDefaultBackground()
   */
	public Color getDefaultBackground() {
		return background.getDefaultValue();
	}	
	
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#setDefaultForeground(org.eclipse.swt.graphics.Color)
   */
	public void setDefaultForeground(Color color) {
		foreground.setDefaultValue(color);
	}

	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#getDefaultForeground()
   */
	public Color getDefaultForeground() {
		return foreground.getDefaultValue();
	}	

	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#getBounds()
   */
	public Rectangle getBounds() {
		return bounds;
	}

	void setBounds(int x, int y, int width, int height) {
		bounds.x = x;
		bounds.y = y;
		bounds.width = width;
		bounds.height = height;
	}

//	
//	public boolean isVisible() {
//		return section0.isVisible() && section1.isVisible();
//	}

	
	
	
	/*------------------------------------------------------------------------
	 * Selection
	 */

	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#setSelectionForeground(org.eclipse.swt.graphics.Color)
   */
	public void setSelectionForeground(Color color) {
		selectionForeground = color;
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#getSelectionForeground()
   */
	public Color getSelectionForeground() {
		return selectionForeground;
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#setSelectionBackground(org.eclipse.swt.graphics.Color)
   */
	public void setSelectionBackground(Color color) {
		selectionBackground = color;
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#getSelectionBackground()
   */
	public Color getSelectionBackground() {
		return selectionBackground;
	}
	

	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#isSelectionEnabled()
   */
	public boolean isSelectionEnabled() {
		return selectionEnabled;
	}

	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#setSelectionEnabled(boolean)
   */
	public void setSelectionEnabled(boolean enabled) {
		if (enabled == false) {
			cellSelection.clear();
		}
		this.selectionEnabled = enabled;
	}

	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#isSelected(N0, N1)
   */
	public boolean isSelected(N0 index0, N1 index1) {
		return cellSelection.contains(index0, index1);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#setSelected(N0, N0, N1, N1, boolean)
   */
	public void setSelected( N0 start0, N0 end0, N1 start1, N1 end1, 
			boolean state) {
		
		if (!selectionEnabled) return;
		if (state) {
			cellSelection.add(start0, end0, start1, end1);
		} else {
			cellSelection.remove(start0, end0, start1, end1);			
		}
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#setSelected(N0, N1, boolean)
   */
	public void setSelected(N0 index0, N1 index1, boolean state) {
		setSelected(index0, index0, index1, index1, state);
	}

	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#setSelectedAll(boolean)
   */ 
	public void setSelectedAll(boolean state) {
		if (!selectionEnabled) return;
		if (state) {
			Math<N0> math0 = section0.core.math;
      Math<N1> math1 = section1.core.math;
      cellSelection.add(
					math0.ZERO_VALUE(), math0.decrement(section0.getCount()), 
					math1.ZERO_VALUE(), math1.decrement(section1.getCount()));
		} else {
			cellSelection.clear();
			lastSelection.clear();
		}
//		section0.setSelectedAll(state);
//		section1.setSelectedAll(state);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#getSelectedCount()
   */
	public BigInteger getSelectedCount() {
		return cellSelection.getCount().getValue();
	}

	
	/**
	 * Returns a sequence of index pairs for selected cells. 
	 * @return a sequence of index pairs for selected cells
	 */
/*
	public NumberPairSequence<N0, N1> getSelected() {
		return new NumberPairSequence(cellSelection.copy());
	}
*/
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#getSelectionCount()
   */
	public BigInteger getSelectionCount() {
		if (!selectionEnabled) {
			return BigInteger.ZERO;
		}
		return cellSelection.getCount().value;
	}
	

	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#getSelectedIterator()
   */
	public Iterator<Number[]> getSelectedIterator() {
		return new ImmutableIterator<Number[]>() {
			NumberPairSequence seq = new NumberPairSequence(cellSelection.copy());
			private boolean next;
			{
				seq.init();
			}
			
			public boolean hasNext() {
				next = seq.next();
				return next;
			}

			
			public Number[] next() {
				return next ? new Number[] {seq.index0(), seq.index1()} : null;
			}
		};
	}
	
	
	
	/*static class Cell<N0, N1> {
		public N0 index0;
		public N1 index1;
		public Cell(N0 index0, N1 index1) {
			this.index0 = index0;
			this.index1 = index1;
		}
	}*/

	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#getSelectedExtentIterator()
   */
	public Iterator<Number[]> getSelectedExtentIterator() {
		return new ImmutableIterator<Number[]>() {
			NumberPairSequence seq = new NumberPairSequence(cellSelection.copy());
			private boolean next;
			{
				seq.init();
			}
			
			public boolean hasNext() {
				next = seq.nextExtent();
				return next;
			}

			
			public Number[] next() {
				return next ? new Number[] {seq.start0(), seq.end0(), seq.start1(), seq.end1()} : null;
			}
		};
	}
	
	
	/**
	 * Returns iterator for a minimal rectangular set of cells covering the selected cells. 
	 * First number in the array returned by the {@link Iterator#next()} method 
	 * is a row axis index, the second one is a column axis index.
	 * <p>
	 * <code>index0</code> and <code>index1</code> refer to the model, 
	 * not the visual position of the item on the screen
	 * which can be altered by move and hide operations.  
	 * <p>
	 * <strong>Warning</strong> iterating index by index over large extents 
	 * may cause a performance problem.
	 */
	Iterator<Number[]> getSelectedBoundsIterator() {
		return new ImmutableIterator<Number[]>() {
			NumberPairSequence seq;
			{
				Number[] e = cellSelection.getExtent();
				CellSet set = new CellSet(section0.core.math, section1.core.math);
				set.add(e[0], e[1], e[2], e[3]);
				seq = new NumberPairSequence(set);
			}
			
			private boolean next;
			{
				seq.init();
			}
			
			public boolean hasNext() {
				next = seq.next();
				return next;
			}

			
			public Number[] next() {
				return next ? new Number[] {seq.index0(), seq.index1()} : null;
			}
		};
	}
	
	
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#getSelectedExtent()
   */
	public Number[] getSelectedExtent() {
		return cellSelection.getExtent();
	}
//	
//	/**
//	 * Copies selected data to the clip board using tab for cells and new line for rows concatenation.
//	 */
//	void copy() {
//		StringBuilder sb = new StringBuilder();
//		Number[] e = cellSelection.getExtent();
//		NumberSet set0 = new NumberSet(section0.math);
//		NumberSet set1 = new NumberSet(section1.math);
//		set0.add(e[0], e[1]);
//		set1.add(e[2], e[3]);
//		NumberSequence seq0 = new NumberSequence(set0);
//		NumberSequence seq1 = new NumberSequence(set1);
//		for (seq0.init(); seq0.next();) {
//			int i = 0;
//			for (seq1.init(); seq1.next();) {
//				if (i++ > 0) sb.append("\t");
//				sb.append(NEW_LINE);
//			}
//			sb.append(NEW_LINE);
//		}
//	}
	
	void backupSelection() {
		lastSelection = cellSelection.copy();
	}
	
	void restoreSelection() {
		cellSelection = lastSelection.copy();
	}


	/*------------------------------------------------------------------------
	 * Gesture 
	 */
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#bind(int, int, int)
   */
	public void bind(int commandId, int eventType, int code) {
		bind(new GestureBinding(commandId, eventType, code));
	}
	
	void bind(GestureBinding binding) {
		if (editor != null && (
				binding.commandId == Matrix.CMD_EDIT_DEACTIVATE_APPLY || 
				binding.commandId == Matrix.CMD_EDIT_DEACTIVATE_CANCEL )) {
			editor.controlListener.bindings.add(binding);
		} else {
			bindings.add(binding);
		}
	}
	
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#unbind(int, int, int)
   */
	public void unbind(int commandId, int eventType, int code) {
		if (editor != null && (
				commandId == Matrix.CMD_EDIT_DEACTIVATE_APPLY || 
				commandId == Matrix.CMD_EDIT_DEACTIVATE_CANCEL )) {
			editor.controlListener.unbind(commandId, eventType, code);
		}
		else {
			for (int i = bindings.size(); i-- > 0;) {
				GestureBinding binding = bindings.get(i);
				if (binding.commandId == commandId && binding.eventType == eventType 
						&& binding.key == code) {
					bindings.remove(i);
				};
			}
		}
	}
	
	Iterable<GestureBinding> getBindings() {
		return bindings;
	}
	
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#addSelectionListener(org.eclipse.swt.events.SelectionListener)
   */
	public void addSelectionListener (SelectionListener listener) {
		Preconditions.checkNotNullWithName(listener, "listener");
		TypedListener typedListener = new TypedListener(listener);
		this.listeners.add(SWT.Selection, typedListener);
		this.listeners.add(SWT.DefaultSelection, typedListener);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#removeSelectionListener(org.eclipse.swt.events.SelectionListener)
   */
	public void removeSelectionListener(SelectionListener listener) {
		Preconditions.checkNotNullWithName(listener, "listener");
		listeners.remove(SWT.Selection, listener);
		listeners.remove(SWT.DefaultSelection, listener);
	}

	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#addListener(int, org.eclipse.swt.widgets.Listener)
   */
	public void addListener(int eventType, final Listener listener) {
		matrix.addListener(eventType, new Listener() {
			
			public void handleEvent(Event e) {
				AxisPointer<N0> item0 = matrix.getAxis0().getItemByDistance(e.y);
				AxisPointer<N1> item1 = matrix.getAxis1().getItemByDistance(e.x);
				if (item0 != null && item1 != null && ZoneCore.this == 
						matrix.getZone(
								item0.getSection(), 
								item1.getSection())) 
				{
					listener.handleEvent(e);
				}
			}
		});
	}

	
	/*------------------------------------------------------------------------
	 * Painting 
	 */

	void paint(final GC gc, final Layout layout0, final Layout layout1, final Frozen dock0, final Frozen dock1) {
		Painter embedded = null;
		for (Painter p: painters) {
			if (p instanceof EmbeddedControlsPainter) {
				embedded = p;
				continue;
			}
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
					index = seq0.item.getIndex();
					for (seq1.init(); seq1.next();) {
						p.paint(index, seq1.item.getIndex(), 
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
					index = seq1.item.getIndex();
					for (seq0.init(); seq0.next();) {
						p.paint(seq0.item.getIndex(), index,
							distance, seq0.getDistance(), width, seq0.getWidth());
					}
				}
				break;
			
			case Painter.SCOPE_ROW_CELLS:
				seq0 = layout0.cellSequence(dock0, section0);
				distance = bounds.x;
				width = bounds.width;
				for (seq0.init(); seq0.next();) {
					p.paint(seq0.item.getIndex(), null, distance, seq0.getDistance(), width, seq0.getWidth());
				}
				break;
				
			case Painter.SCOPE_COLUMN_CELLS:
				seq1 = layout1.cellSequence(dock1, section1);
				distance = bounds.y;
				width = bounds.height;
				for (seq1.init(); seq1.next();) {
					p.paint(null, seq1.item.getIndex(), seq1.getDistance(), distance, seq1.getWidth(), width);
				}
				break;
				
			case Painter.SCOPE_HORIZONTAL_LINES:
				seq0 = layout0.lineSequence(dock0, section0);
				distance = bounds.x;
				width = bounds.width;
				for (seq0.init(); seq0.next();) {
					p.paint(seq0.item.getIndex(), null, distance, seq0.getDistance(), width, seq0.getWidth());
				}
				break;
			
			case Painter.SCOPE_VERTICAL_LINES:
				seq1 = layout1.lineSequence(dock1, section1);
				distance = bounds.y;
				width = bounds.height;
				for (seq1.init(); seq1.next();) {
					p.paint(null, seq1.item.getIndex(), seq1.getDistance(), distance, seq1.getWidth(), width);
				}
				break;
				
			}
			p.clean();
		}
		
		if (embedded != null) {
			final Painter p = embedded;
			Display display = matrix.getDisplay();
			display.asyncExec(new Runnable() {
				public void run() {
					if (!p.isEnabled() || !p.init(gc)) return;
					
					LayoutSequence seq0 = layout0.cellSequence(dock0, section0);
					LayoutSequence seq1 = layout1.cellSequence(dock1, section1);
					for (seq0.init(); seq0.next();) {
						int distance = seq0.getDistance();
						int width = seq0.getWidth();
						Number index = seq0.item.getIndex();
						for (seq1.init(); seq1.next();) {
							p.paint(index, seq1.item.getIndex(), 
									seq1.getDistance(), distance, seq1.getWidth(), width);
						}
					}
					p.clean();
				}
			});
		}
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#addPainter(pl.netanel.swt.matrix.Painter)
   */
	public void addPainter(Painter<N0, N1> painter) {
	  checkOwner(painter);
		painters.add(painter);
		setPainterMatrixAndZone(painter);
	}

	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#addPainter(int, pl.netanel.swt.matrix.Painter)
   */
  public void addPainter(int index, Painter<N0, N1> painter) {
    checkOwner(painter);
		painters.add(index, painter);
		setPainterMatrixAndZone(painter);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#setPainter(int, pl.netanel.swt.matrix.Painter)
   */
	public void setPainter(int index, Painter<N0, N1> painter) {
	  checkOwner(painter);
		painters.set(index, painter);
		setPainterMatrixAndZone(painter);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#replacePainter(pl.netanel.swt.matrix.Painter)
   */
	public void replacePainter(Painter<N0, N1> painter) {
	  checkOwner(painter);
		painters.replacePainter(painter);
		setPainterMatrixAndZone(painter);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#removePainter(int)
   */
	public Painter<N0, N1> removePainter(int index) {
		return painters.remove(index);
	}
	
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#removePainter(pl.netanel.swt.matrix.Painter)
   */
  public boolean removePainter(Painter painter) {
    checkOwner(painter);
    return painters.remove(painter);
  }
  
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#indexOfPainter(java.lang.String)
   */
	public int indexOfPainter(String name) {
		return painters.indexOfPainter(name);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#getPainter(java.lang.String)
   */
	public Painter<N0, N1> getPainter(String name) {
		Preconditions.checkNotNullWithName(name, "name");
		return painters.get(indexOfPainter(name));
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#getPainterCount()
   */
	public int getPainterCount() {
		return painters.size();
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#getPainter(int)
   */
	public Painter<N0, N1> getPainter(int index) {
		Preconditions.checkPositionIndex(index, painters.size());
		return painters.get(index);
	}
	
  private void checkOwner(Painter<N0, N1> painter) {
    if (painter == null) return;
    
    Preconditions.checkArgument(painter.zone == null || this.equals(painter.zone), 
      "The painter belongs to a different zone: %s", painter.zone);
    
    Matrix<N0, N1> matrix2 = getMatrix();
    if (matrix2 != null) {
      Preconditions.checkArgument(painter.matrix == null || matrix2.equals(painter.matrix), 
        "The painter belongs to a different matrix: %s", painter.matrix);
    }
  }

	private void setPainterMatrixAndZone(Painter painter) {	
			painter.setZone(this);
			painter.setMatrix(matrix);
	}
	
	private static class LinePainter extends Painter {
		private final Color color;

		public LinePainter(String name, int scope, Color color) {
			super(name, scope);
			this.color = color;
		}
		
		
		@Override public void paint(Number index0, Number index1, int x, int y, int width, int height) {
			gc.setBackground(color);
			gc.fillRectangle(x, y, width, height);
		}
	}

	
	void insert(int axisIndex, Section section, Number target, Number count) {
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

	void delete(int axisIndex, Section section, Number start, Number end) {
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
				painter.setZone(this);
				painter.setMatrix(matrix);
			}
		}
	}

	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#getMatrix()
   */
	public Matrix<N0, N1> getMatrix() {
		return matrix;
	}
	
	void setEditor(ZoneEditor editor) {
		this.editor = editor;
	}

  void sendEvents() {
    listeners.sendEvents();
  }

  void addSelectionEvent() {
    Event event = new Event();
    event.type = SWT.Selection;
    event.widget = matrix;
    listeners.add(event);
  }

  public static ZoneCore from(Zone zone) {
    return (ZoneCore) zone.getCore();
  }


}
