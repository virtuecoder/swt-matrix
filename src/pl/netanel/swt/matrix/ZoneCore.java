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
 * Constitutes a region of a matrix where a section from the vertical axis 
 * and a section from the horizontal axis intersect with each other.  
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
	final Rectangle bounds;
	

  /**
	 * Constructs zone at intersection of the specified sections.
   * @param sectionX section of the horizontal axis
   * @param sectionY section of the vertical axis
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
		
//		foreground = new MapValueToCellSet<X, Y, Color>(mathX, mathY);
//		text = new MapValueToCellSet(this.sectionY.math, this.sectionX.math);
//		image = new MapValueToCellSet(this.sectionY.math, this.sectionX.math);
	}
	
	
	@Override public String toString() {
		return sectionX.toString() + " " + sectionY.toString();
	}


	@Override public Zone<X, Y> getUnchecked() {
	  return this;
	}
	
	@Override public Section<X> getSectionX() {
	  return sectionX;
	}
	
	@Override public Section<Y> getSectionY() {
	  return sectionY;
	}
	
	/**
   * Sets the default body style for the painters of this zone. 
   * <p>
   * It sets the foreground, background, selection foreground, selection background 
   * colors for the {@link Painter#NAME_CELLS} painter and line (foreground) color 
   * for the  {@link Painter#NAME_LINES_X} and {@link Painter#NAME_LINES_Y} painters.
   */
	@Override
  public void setBodyStyle() {
	  Painter<X, Y> painter = getPainter(Painter.NAME_CELLS);
    if (painter != null) {
      painter.foreground = painter.background = null;

//      painter.selectionForeground = Resources.getColor(SWT.COLOR_LIST_SELECTION_TEXT);
      painter.selectionForeground = painter.foreground;
      RGB selectionColor = Resources.getColor(SWT.COLOR_LIST_SELECTION).getRGB();
      RGB whiteColor = Resources.getColor(SWT.COLOR_LIST_BACKGROUND).getRGB();
      RGB color = Painter.blend(selectionColor, whiteColor, 40);
      painter.selectionBackground = Resources.getColor(color);
    }
    
    final Color color = Resources.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW);
    painter = getPainter(Painter.NAME_LINES_X);
    if (painter != null) {
      painter.background = color;
    }
    painter = getPainter(Painter.NAME_LINES_Y);
    if (painter != null) {
      painter.background = color;
    }
	}
	
	/**
	 * Sets the default header style for the painters of this zone. 
	 * <p>
	 * It sets the foreground, background, selection foreground, selection background 
	 * colors for the {@link Painter#NAME_CELLS} painter and line (foreground) color 
	 * for the  {@link Painter#NAME_LINES_X} and {@link Painter#NAME_LINES_Y} painters.
	 */
	@Override
  public void setHeaderStyle() {
	  Painter<X, Y> painter = getPainter(Painter.NAME_CELLS);
	  if (painter != null) {
	    painter.foreground = Resources.getColor(SWT.COLOR_WIDGET_FOREGROUND);
	    painter.background = Resources.getColor(SWT.COLOR_WIDGET_BACKGROUND);

//	    painter.selectionForeground = Resources.getColor(SWT.COLOR_LIST_SELECTION_TEXT);
	    painter.selectionForeground = painter.foreground;
	    RGB selectionColor = Resources.getColor(SWT.COLOR_LIST_SELECTION).getRGB();
	    RGB whiteColor = Resources.getColor(SWT.COLOR_WIDGET_BACKGROUND).getRGB();
	    RGB rgb = Painter.blend(selectionColor, whiteColor, 90);
	    painter.selectionBackground = Resources.getColor(rgb);
	  }
	  
		final Color color = Resources.getColor(SWT.COLOR_WIDGET_DARK_SHADOW);
    painter = getPainter(Painter.NAME_LINES_X);
    if (painter != null) {
      painter.background = color;
    }
    painter = getPainter(Painter.NAME_LINES_Y);
    if (painter != null) {
      painter.background = color;
    }
	}
	
	@Override public Rectangle getCellBounds(X indexX, Y indexY) {
		Bound b0 = sectionY.axis.getCellBound(sectionY, indexY);
		Bound b1 = sectionX.axis.getCellBound(sectionX, indexX);
		if (b0 != null && b1 != null) {
			return new Rectangle(b1.distance, b0.distance, b1.width, b0.width);
		}
		return null; 
	}
	
	
	@Override public Rectangle getBounds(Frozen frozenX, Frozen frozenY) {
	  Bound bx = matrix.layoutX.getBound(frozenX, sectionX);
    Bound by = matrix.layoutY.getBound(frozenY, sectionY);
		return new Rectangle(bx.distance, by.distance, bx.width, by.width);
	}

	void setBounds(int x, int y, int width, int height) {
		bounds.x = x;
		bounds.y = y;
		bounds.width = width;
		bounds.height = height;
	}

	
	/*------------------------------------------------------------------------
	 * Selection
	 */

	
	@Override public boolean isSelectionEnabled() {
		return selectionEnabled;
	}

	@Override public void setSelectionEnabled(boolean enabled) {
		if (enabled == false) {
			cellSelection.clear();
		}
		this.selectionEnabled = enabled;
	}

	
	@Override public boolean isSelected(X indexX, Y indexY) {
		return cellSelection.contains(indexX, indexY);
	}
	
	@Override 
	public void setSelected(X startX, X endX, Y startY, Y endY, boolean state) {
		
		if (!selectionEnabled) return;
		if (state) {
			cellSelection.add(startX, endX, startY, endY);
		} else {
			cellSelection.remove(startX, endX, startY, endY);			
		}
	}
	
	@Override 
	public void setSelected(X indexX, Y indexY, boolean state) {
		setSelected(indexX, indexX, indexY, indexY, state);
	}

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
	
	@Override public BigInteger getSelectionCount() {
		if (!selectionEnabled) {
			return BigInteger.ZERO;
		}
		return cellSelection.getCount().value;
	}
	

	@Override public Iterator<Cell<X,Y>> getSelectedIterator() {
		return new ImmutableIterator<Cell<X,Y>>() {
			NumberPairSequence<X, Y> seq = new NumberPairSequence<X, Y>(cellSelection.copy());
			private boolean next;
			{
				seq.init();
			}
			
			@Override public boolean hasNext() {
				next = seq.next();
				return next;
			}

			
			@Override public Cell<X,Y> next() {
				return next ? Cell.createUnchecked(seq.indexX(), seq.indexY()) : null;
			}
		};
	}
	
	
	@Override public Iterator<CellExtent<X, Y>> getSelectedExtentIterator() {
		return new ImmutableIterator<CellExtent<X, Y>>() {
			NumberPairSequence<X, Y> seq = new NumberPairSequence<X, Y>(cellSelection.copy());
			private boolean next;
			{
				seq.init();
			}
			
			@Override public boolean hasNext() {
				next = seq.nextExtent();
				return next;
			}

			
			@Override public CellExtent<X, Y> next() {
				return next ? 
				  CellExtent.createUnchecked(seq.startX(), seq.endX(), seq.startY(), seq.endY()) : 
			    null;
			}
		};
	}
	
	
	ImmutableIterator<Cell<X, Y>> getSelectedBoundsIterator() {
		return new ImmutableIterator<Cell<X, Y>>() {
			NumberPairSequence<X, Y> seq;
			{
				CellExtent<X, Y> e = cellSelection.getExtent();
				CellSet<X, Y> set = new CellSet<X, Y>(sectionX.math, sectionY.math);
				set.add(e.startX, e.endX, e.startY, e.endY);
				seq = new NumberPairSequence<X, Y>(set);
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
				return next ? Cell.createUnchecked(seq.indexX(), seq.indexY()) : null;
			}
		};
	}
	
	
	
	@Override public CellExtent<X, Y> getSelectedExtent() {
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
	
	
	@Override public void addSelectionListener (SelectionListener listener) {
		Preconditions.checkNotNullWithName(listener, "listener");
		TypedListener typedListener = new TypedListener(listener);
		this.listeners.add(SWT.Selection, typedListener);
		this.listeners.add(SWT.DefaultSelection, typedListener);
	}
	
	@Override public void removeSelectionListener(SelectionListener listener) {
		Preconditions.checkNotNullWithName(listener, "listener");
		listeners.remove(SWT.Selection, listener);
		listeners.remove(SWT.DefaultSelection, listener);
	}

	@Override public void addListener(int eventType, final Listener listener) {
		matrix.addListener(eventType, new Listener() {
			
			@Override public void handleEvent(Event e) {
			  AxisItem<X> itemX = matrix.getAxisX().getItemByDistance(e.x);
				AxisItem<Y> itemY = matrix.getAxisY().getItemByDistance(e.y);
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

	void paint(final GC gc, final Layout<X> layoutX, final Layout<Y> layoutY, final Frozen dockX, final Frozen dockY) {
		Painter<X, Y> embedded = null;
		for (Painter<X, Y> p: painters) {
			if (p instanceof EmbeddedControlsPainter) {
				embedded = p;
				continue;
			}
			if (!p.isEnabled() || !p.init(gc)) continue;
			
			int distance = 0, width = 0;
			switch (p.scope) {
			
			case Painter.SCOPE_CELLS_X:
			  LayoutSequence<X> seqX = layoutX.cellSequence(dockX, sectionX);
				LayoutSequence<Y> seqY = layoutY.cellSequence(dockY, sectionY);
				for (seqY.init(); seqY.next();) {
					distance = seqY.getDistance();
					width = seqY.getWidth();
					Y indexX = seqY.item.getIndex();
					for (seqX.init(); seqX.next();) {
						p.paint(seqX.item.getIndex(), indexX, 
							seqX.getDistance(), distance, seqX.getWidth(), width);
					}
				}
				break;
				
			case Painter.SCOPE_CELLS_Y:
				seqY = layoutY.cellSequence(dockY, sectionY);
				seqX = layoutX.cellSequence(dockX, sectionX);
				for (seqX.init(); seqX.next();) {
					distance = seqX.getDistance();
					width = seqX.getWidth();
					X indexX = seqX.item.getIndex();
					for (seqY.init(); seqY.next();) {
						p.paint(indexX, seqY.item.getIndex(),
							distance, seqY.getDistance(), width, seqY.getWidth());
					}
				}
				break;
			
			case Painter.SCOPE_CELLS_ITEM_Y:
				seqY = layoutY.cellSequence(dockY, sectionY);
				distance = bounds.x;
				width = bounds.width;
				for (seqY.init(); seqY.next();) {
					p.paint(null, seqY.item.getIndex(), distance, seqY.getDistance(), width, seqY.getWidth());
				}
				break;
				
			case Painter.SCOPE_CELLS_ITEM_X:
				seqX = layoutX.cellSequence(dockX, sectionX);
				distance = bounds.y;
				width = bounds.height;
				for (seqX.init(); seqX.next();) {
					p.paint(seqX.item.getIndex(), null, seqX.getDistance(), distance, seqX.getWidth(), width);
				}
				break;
				
			case Painter.SCOPE_LINES_X:
				seqY = layoutY.lineSequence(dockY, sectionY);
				distance = bounds.x;
				width = bounds.width;
				for (seqY.init(); seqY.next();) {
					p.paint(null, seqY.item.getIndex(), distance, seqY.getDistance(), width, seqY.getWidth());
				}
				break;
			
			case Painter.SCOPE_LINES_Y:
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
			final Painter<X, Y> p = embedded;
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
	
	@Override public void addPainter(Painter<X, Y> painter) {
		painters.add(painter);
		setPainterMatrixAndZone(painter);
	}

	@Override public void addPainter(int index, Painter<X, Y> painter) {
		painters.add(index, painter);
		setPainterMatrixAndZone(painter);
	}
	
	@Override public void setPainter(int index, Painter<X, Y> painter) {
		painters.set(index, painter);
		setPainterMatrixAndZone(painter);
	}
	
	@Override public void replacePainter(Painter<X, Y> painter) {
		painters.replacePainter(painter);
		setPainterMatrixAndZone(painter);
	}
	
	@Override public Painter<X, Y> removePainter(int index) {
		return painters.remove(index);
	}
	
	
	@Override public boolean removePainter(Painter<X, Y> painter) {
    return painters.remove(painter);
  }
  
  @Override public boolean removePainter(String name) {
    int i = indexOfPainter(name);
    if (i == -1) return false;
    return painters.remove(painters.get(i));
  }
  
	@Override public int indexOfPainter(String name) {
		return painters.indexOfPainter(name);
	}
	
	@Override public Painter<X, Y> getPainter(String name) {
		int i = indexOfPainter(name);
		if (i == -1) return null;
    return painters.get(i);
	}
	
	@Override public int getPainterCount() {
		return painters.size();
	}
	
	@Override public Painter<X, Y> getPainter(int index) {
		return painters.get(index);
	}

	void replaceOrAddFirst(Painter<X, Y> painter) {
    int indexOf = painters.indexOfPainter(Painter.NAME_CELLS);
    if (indexOf != -1) {
      painters.set(indexOf, painter);
    } else {
      addPainter(0, painter);
    }
  }

	private void setPainterMatrixAndZone(Painter<X, Y> painter) {	
			painter.setZone(this);
			painter.setMatrix(matrix);
	}
	
	
	/*------------------------------------------------------------------------
	 * Non-public 
	 */
	
	void setMatrix(Matrix<X, Y> matrix) {
		this.matrix = matrix;
		for (Painter<X, Y> painter: painters) {
			if (painter.scope == Painter.SCOPE_CELLS ||
					painter.scope == Painter.SCOPE_CELLS_Y) 
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
    return (ZoneCore<X2, Y2>) zone.getUnchecked();
  }


}
