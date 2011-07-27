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
 * @param <Y> indexing type for vertical axis
 * @param <X> indexing type for the horizontal axis
 * @see SectionCore
 * 
 * @author Jacek
 * @created 13-10-2010
 */
class ZoneCore<X extends Number, Y extends Number> implements Zone<X, Y> {
	
	final Painters<X, Y> painters;
	final SectionCore<X> sectionX;
	final SectionCore<Y> sectionY;
	CellSet<X, Y> cellSelection;
	CellSet<X, Y> lastSelection; // For adding selection
	ZoneEditor<X, Y> editor;
	
	private final Listeners listeners;
	private final ArrayList<GestureBinding> bindings;
	private boolean selectionEnabled;
	
	private Matrix<X, Y> matrix;
	private Color selectionBackground, selectionForeground;
	private final Rectangle bounds;
	
	private final CellValues<X, Y, Color> background, foreground;
	

  /**
	 * Constructs zone at intersection of the specified sections.
   * @param sectionX section of the column axis
   * @param sectionY section of the row axis
	 */
	public ZoneCore(SectionCore<X> sectionX, SectionCore<Y> sectionY) {
		this.sectionY = sectionY;
		this.sectionX = sectionX;
		painters = new Painters<X, Y>();
    listeners = new Listeners();
    bindings = new ArrayList<GestureBinding>();
    bounds = new Rectangle(0, 0, 0, 0);
    selectionEnabled = true;
    
    Math<X> mathX = SectionCore.from(sectionX).math;
		Math<Y> mathY = SectionCore.from(sectionY).math;
		cellSelection = new CellSet<X, Y>(mathX, mathY);
		lastSelection = new CellSet<X, Y>(mathX, mathY);
		
		RGB selectionColor = Resources.getColor(SWT.COLOR_LIST_SELECTION).getRGB();
		RGB whiteColor = Resources.getColor(SWT.COLOR_LIST_BACKGROUND).getRGB();
		RGB color = Painter.blend(selectionColor, whiteColor, 40);
		selectionBackground = Resources.getColor(color);
		
		background = new MapValueToCellSet(mathY, mathX);
		foreground = new MapValueToCellSet(mathY, mathX);
//		text = new MapValueToCellSet(this.sectionY.math, this.sectionX.math);
//		image = new MapValueToCellSet(this.sectionY.math, this.sectionX.math);
	}
	
	
	@Override public String toString() {
		return sectionY.toString() + " " + sectionX.toString();
	}


	@Override public Zone getCore() {
	  return this;
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#getSectionY()
   */
	@Override public Section getSectionY() {
	  return sectionY;
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#getSectionX()
   */
	@Override public Section getSectionX() {
	  return sectionX;
	}

	
void setDefaultBodyStyle() {
//	  setDefaultBackground(matrix.getBackground());
	  
		Painter painter = new Painter(Painter.NAME_CELLS, Painter.SCOPE_CELLS) {
			@Override public String getText(Number indexY, Number indexX) {
				return indexY.toString() + ", " + indexX.toString();
			}
		};
		painter.setZone(this);
		replacePainter(painter);
		Color color = Resources.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW);
		replacePainter(new LinePainter(Painter.NAME_ROW_LINES, Painter.SCOPE_HORIZONTAL_LINES, color ));
		replacePainter(new LinePainter(Painter.NAME_COLUMN_LINES, Painter.SCOPE_VERTICAL_LINES, color));
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
			addPainter(new LinePainter(Painter.NAME_ROW_LINES, Painter.SCOPE_HORIZONTAL_LINES, color));
			addPainter(new LinePainter(Painter.NAME_COLUMN_LINES, Painter.SCOPE_VERTICAL_LINES, color));
		}
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#getCellBounds(Y, X)
   */
	@Override public Rectangle getCellBounds(X indexX, Y indexY) {
		Bound b0 = sectionY.axis.getCellBound(sectionY, indexY);
		Bound b1 = sectionX.axis.getCellBound(sectionX, indexX);
		if (b0 != null && b1 != null) {
			return new Rectangle(b1.distance, b0.distance, b1.width, b0.width);
		}
		return null; 
	}
	
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#setDefaultBackground(org.eclipse.swt.graphics.Color)
   */
	@Override public void setDefaultBackground(Color color) {
		background.setDefaultValue(color);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#getDefaultBackground()
   */
	@Override public Color getDefaultBackground() {
		return background.getDefaultValue();
	}	
	
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#setDefaultForeground(org.eclipse.swt.graphics.Color)
   */
	@Override public void setDefaultForeground(Color color) {
		foreground.setDefaultValue(color);
	}

	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#getDefaultForeground()
   */
	@Override public Color getDefaultForeground() {
		return foreground.getDefaultValue();
	}	

	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#getBounds()
   */
	@Override public Rectangle getBounds() {
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
//		return sectionY.isVisible() && sectionX.isVisible();
//	}

	
	
	
	/*------------------------------------------------------------------------
	 * Selection
	 */

	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#setSelectionForeground(org.eclipse.swt.graphics.Color)
   */
	@Override public void setSelectionForeground(Color color) {
		selectionForeground = color;
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#getSelectionForeground()
   */
	@Override public Color getSelectionForeground() {
		return selectionForeground;
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#setSelectionBackground(org.eclipse.swt.graphics.Color)
   */
	@Override public void setSelectionBackground(Color color) {
		selectionBackground = color;
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#getSelectionBackground()
   */
	@Override public Color getSelectionBackground() {
		return selectionBackground;
	}
	

	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#isSelectionEnabled()
   */
	@Override public boolean isSelectionEnabled() {
		return selectionEnabled;
	}

	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#setSelectionEnabled(boolean)
   */
	@Override public void setSelectionEnabled(boolean enabled) {
		if (enabled == false) {
			cellSelection.clear();
		}
		this.selectionEnabled = enabled;
	}

	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#isSelected(Y, X)
   */
	@Override public boolean isSelected(X indexX, Y indexY) {
		return cellSelection.contains(indexX, indexY);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#setSelected(Y, Y, X, X, boolean)
   */
	@Override 
	public void setSelected(X startX, X endX, Y startY, Y endY, boolean state) {
		
		if (!selectionEnabled) return;
		if (state) {
			cellSelection.add(startX, endX, startY, endY);
		} else {
			cellSelection.remove(startX, endX, startY, endY);			
		}
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#setSelected(Y, X, boolean)
   */
	@Override 
	public void setSelected(X indexX, Y indexY, boolean state) {
		setSelected(indexX, indexX, indexY, indexY, state);
	}

	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#setSelectedAll(boolean)
   */ 
	@Override public void setSelectedAll(boolean state) {
		if (!selectionEnabled) return;
		if (state) {
			Math<Y> mathY = sectionY.math;
      Math<X> mathX = sectionX.math;
      cellSelection.add(
					mathX.ZERO_VALUE(), mathX.decrement(sectionX.getCount()), 
					mathY.ZERO_VALUE(), mathY.decrement(sectionY.getCount()));
		} else {
			cellSelection.clear();
			lastSelection.clear();
		}
//		sectionY.setSelectedAll(state);
//		sectionX.setSelectedAll(state);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#getSelectedCount()
   */
	@Override public BigInteger getSelectedCount() {
		return cellSelection.getCount().getValue();
	}

	
	/**
	 * Returns a sequence of index pairs for selected cells. 
	 * @return a sequence of index pairs for selected cells
	 */
/*
	public NumberPairSequence<X, Y> getSelected() {
		return new NumberPairSequence(cellSelection.copy());
	}
*/
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#getSelectionCount()
   */
	@Override public BigInteger getSelectionCount() {
		if (!selectionEnabled) {
			return BigInteger.ZERO;
		}
		return cellSelection.getCount().value;
	}
	

	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#getSelectedIterator()
   */
	@Override public Iterator<Number[]> getSelectedIterator() {
		return new ImmutableIterator<Number[]>() {
			NumberPairSequence seq = new NumberPairSequence(cellSelection.copy());
			private boolean next;
			{
				seq.init();
			}
			
			@Override public boolean hasNext() {
				next = seq.next();
				return next;
			}

			
			@Override public Number[] next() {
				return next ? new Number[] {seq.indexY(), seq.indexX()} : null;
			}
		};
	}
	
	
	
	/*static class Cell<X, Y> {
		public Y indexY;
		public X indexX;
		public Cell(Y indexY, X indexX) {
			this.indexY = indexY;
			this.indexX = indexX;
		}
	}*/

	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#getSelectedExtentIterator()
   */
	@Override public Iterator<Number[]> getSelectedExtentIterator() {
		return new ImmutableIterator<Number[]>() {
			NumberPairSequence seq = new NumberPairSequence(cellSelection.copy());
			private boolean next;
			{
				seq.init();
			}
			
			@Override public boolean hasNext() {
				next = seq.nextExtent();
				return next;
			}

			
			@Override public Number[] next() {
				return next ? new Number[] {seq.startY(), seq.endY(), seq.startX(), seq.endX()} : null;
			}
		};
	}
	
	
	/**
	 * Returns iterator for a minimal rectangular set of cells covering the selected cells. 
	 * First number in the array returned by the {@link Iterator#next()} method 
	 * is a row axis index, the second one is a column axis index.
	 * <p>
	 * <code>indexX</code> and <code>indexY</code> refer to the model, 
	 * not the visual position of the item on the screen
	 * which can be altered by move and hide operations.  
	 * <p>
	 * <strong>Warning</strong> iterating index by index over large extents 
	 * may cause a performance problem.
	 */
	ImmutableIterator<Cell<X, Y>> getSelectedBoundsIterator() {
		return new ImmutableIterator<Cell<X, Y>>() {
			NumberPairSequence seq;
			{
				CellExtent e = cellSelection.getExtent();
				CellSet set = new CellSet(sectionX.math, sectionY.math);
				set.add(e.startX, e.endX, e.startY, e.endY);
				seq = new NumberPairSequence(set);
			}
			
			private boolean next;
			{
				seq.init();
			}
			
			@Override public boolean hasNext() {
				next = seq.next();
				return next;
			}

			
			@Override public Cell<X, Y> next() {
				return next ? new Cell(seq.indexX(), seq.indexY()) : null;
			}
		};
	}
	
	
	
	@Override public CellExtent getSelectedExtent() {
		return cellSelection.getExtent();
	}
	
	void backupSelection() {
		lastSelection = cellSelection.copy();
	}
	
	void restoreSelection() {
		cellSelection = lastSelection.copy();
	}


	/*------------------------------------------------------------------------
	 * Gesture 
	 */
	
	@Override public void bind(int commandId, int eventType, int code) {
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
	
	@Override public void unbind(int commandId, int eventType, int code) {
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
	@Override public void addSelectionListener (SelectionListener listener) {
		Preconditions.checkNotNullWithName(listener, "listener");
		TypedListener typedListener = new TypedListener(listener);
		this.listeners.add(SWT.Selection, typedListener);
		this.listeners.add(SWT.DefaultSelection, typedListener);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#removeSelectionListener(org.eclipse.swt.events.SelectionListener)
   */
	@Override public void removeSelectionListener(SelectionListener listener) {
		Preconditions.checkNotNullWithName(listener, "listener");
		listeners.remove(SWT.Selection, listener);
		listeners.remove(SWT.DefaultSelection, listener);
	}

	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#addListener(int, org.eclipse.swt.widgets.Listener)
   */
	@Override public void addListener(int eventType, final Listener listener) {
		matrix.addListener(eventType, new Listener() {
			
			@Override public void handleEvent(Event e) {
			  AxisPointer<X> itemX = matrix.getAxisX().getItemByDistance(e.x);
				AxisPointer<Y> itemY = matrix.getAxisY().getItemByDistance(e.y);
				if (itemX != null && itemY != null && ZoneCore.this == 
						matrix.model.getZone(itemX.section, itemY.section))
				{
					listener.handleEvent(e);
				}
			}
		});
	}

	
	/*------------------------------------------------------------------------
	 * Painting 
	 */

	void paint(final GC gc, final Layout layoutY, final Layout layoutX, final Frozen dockY, final Frozen dockX) {
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
				LayoutSequence seqY = layoutY.cellSequence(dockY, sectionY);
				LayoutSequence seqX = layoutX.cellSequence(dockX, sectionX);
				for (seqY.init(); seqY.next();) {
					distance = seqY.getDistance();
					width = seqY.getWidth();
					index = seqY.item.getIndex();
					for (seqX.init(); seqX.next();) {
						p.paint(seqX.item.getIndex(), index, 
							seqX.getDistance(), distance, seqX.getWidth(), width);
					}
				}
				break;
				
			case Painter.SCOPE_CELLS_VERTICALLY:
				seqY = layoutY.cellSequence(dockY, sectionY);
				seqX = layoutX.cellSequence(dockX, sectionX);
				for (seqX.init(); seqX.next();) {
					distance = seqX.getDistance();
					width = seqX.getWidth();
					index = seqX.item.getIndex();
					for (seqY.init(); seqY.next();) {
						p.paint(index, seqY.item.getIndex(),
							distance, seqY.getDistance(), width, seqY.getWidth());
					}
				}
				break;
			
			case Painter.SCOPE_ROW_CELLS:
				seqY = layoutY.cellSequence(dockY, sectionY);
				distance = bounds.x;
				width = bounds.width;
				for (seqY.init(); seqY.next();) {
					p.paint(null, seqY.item.getIndex(), distance, seqY.getDistance(), width, seqY.getWidth());
				}
				break;
				
			case Painter.SCOPE_COLUMN_CELLS:
				seqX = layoutX.cellSequence(dockX, sectionX);
				distance = bounds.y;
				width = bounds.height;
				for (seqX.init(); seqX.next();) {
					p.paint(seqX.item.getIndex(), null, seqX.getDistance(), distance, seqX.getWidth(), width);
				}
				break;
				
			case Painter.SCOPE_HORIZONTAL_LINES:
				seqY = layoutY.lineSequence(dockY, sectionY);
				distance = bounds.x;
				width = bounds.width;
				for (seqY.init(); seqY.next();) {
					p.paint(null, seqY.item.getIndex(), distance, seqY.getDistance(), width, seqY.getWidth());
				}
				break;
			
			case Painter.SCOPE_VERTICAL_LINES:
				seqX = layoutX.lineSequence(dockX, sectionX);
				distance = bounds.y;
				width = bounds.height;
				for (seqX.init(); seqX.next();) {
					p.paint(seqX.item.getIndex(), null, seqX.getDistance(), distance, seqX.getWidth(), width);
				}
				break;
				
			}
			p.clean();
		}
		
		if (embedded != null) {
			final Painter p = embedded;
			Display display = matrix.getDisplay();
			display.asyncExec(new Runnable() {
				@Override public void run() {
					if (!p.isEnabled() || !p.init(gc)) return;
					
					LayoutSequence<Y> seqY = layoutY.cellSequence(dockY, sectionY);
					LayoutSequence<X> seqX = layoutX.cellSequence(dockX, sectionX);
					for (seqY.init(); seqY.next();) {
						int distance = seqY.getDistance();
						int width = seqY.getWidth();
						Y index = seqY.item.getIndex();
						for (seqX.init(); seqX.next();) {
							p.paint(seqX.item.getIndex(), index, 
									seqX.getDistance(), distance, seqX.getWidth(), width);
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
	@Override public void addPainter(Painter<X, Y> painter) {
		painters.add(painter);
		setPainterMatrixAndZone(painter);
	}

	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#addPainter(int, pl.netanel.swt.matrix.Painter)
   */
  @Override public void addPainter(int index, Painter<X, Y> painter) {
		painters.add(index, painter);
		setPainterMatrixAndZone(painter);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#setPainter(int, pl.netanel.swt.matrix.Painter)
   */
	@Override public void setPainter(int index, Painter<X, Y> painter) {
		painters.set(index, painter);
		setPainterMatrixAndZone(painter);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#replacePainter(pl.netanel.swt.matrix.Painter)
   */
	@Override public void replacePainter(Painter<X, Y> painter) {
		painters.replacePainter(painter);
		setPainterMatrixAndZone(painter);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#removePainter(int)
   */
	@Override public Painter<X, Y> removePainter(int index) {
		return painters.remove(index);
	}
	
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#removePainter(pl.netanel.swt.matrix.Painter)
   */
  @Override public boolean removePainter(Painter painter) {
    return painters.remove(painter);
  }
  
  @Override public boolean removePainter(String name) {
    int i = indexOfPainter(name);
    if (i == -1) return false;
    return painters.remove(painters.get(i));
  }
  
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#indexOfPainter(java.lang.String)
   */
	@Override public int indexOfPainter(String name) {
		return painters.indexOfPainter(name);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#getPainter(java.lang.String)
   */
	@Override public Painter<X, Y> getPainter(String name) {
		int i = indexOfPainter(name);
		if (i == -1) return null;
    return painters.get(i);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#getPainterCount()
   */
	@Override public int getPainterCount() {
		return painters.size();
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.IZone#getPainter(int)
   */
	@Override public Painter<X, Y> getPainter(int index) {
		return painters.get(index);
	}
	

	private void setPainterMatrixAndZone(Painter painter) {	
			painter.setZone(this);
			painter.setMatrix(matrix);
	}
	
	static class LinePainter extends Painter {

		public LinePainter(String name, int scope, Color color) {
			super(name, scope);
			this.background = color;
		}
		
		
		@Override public void paint(Number indexY, Number indexX, int x, int y, int width, int height) {
			gc.setBackground(background);
			gc.fillRectangle(x, y, width, height);
		}
	}
	
	
	/*------------------------------------------------------------------------
	 * Non-public 
	 */
	
	void setMatrix(Matrix<X, Y> matrix) {
		this.matrix = matrix;
		for (Painter<X, Y> painter: painters) {
			if (painter.scope == Painter.SCOPE_CELLS ||
					painter.scope == Painter.SCOPE_CELLS_VERTICALLY) 
			{
				painter.setZone(this);
				painter.setMatrix(matrix);
			}
		}
	}

	@Override public Matrix<X, Y> getMatrix() {
		return matrix;
	}
	
	void setEditor(ZoneEditor<X, Y> editor) {
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

  public static <X2 extends Number, Y2 extends Number> ZoneCore<X2, Y2> from(Zone<X2, Y2> zone) {
    return (ZoneCore<X2, Y2>) zone.getCore();
  }


}
