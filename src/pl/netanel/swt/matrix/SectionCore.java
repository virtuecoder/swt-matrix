package pl.netanel.swt.matrix;

import java.text.MessageFormat;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.TypedListener;

import pl.netanel.util.ImmutableIterator;
import pl.netanel.util.Preconditions;

/**
 * Section represents a continuous segment of a matrix axis, for example a
 * header, body, footer. It contains a number of items indexed by the 
 * <code>&lt;N extends {@link Number}&gt;</code> type parameter.<br>
 * 
 * Index item width consists of the line width and the cell width - 
 * the line precedes the cell. The last line index equals to getCount().
 * If the item is moved then both the cell and the preceding line are moved.  
 * <p>
 * Item attributes include cell width, line width, moveable, resizable,
 * hideable, hidden, selected. To optimize data storage of those attributes one
 * value can be set for a range of items enclosed between the start and end
 * items, for example setCellWidth(start, end, width).
 * Also default values can be defined to save memory. If 1000000 items
 * have the same width, then its a waste to store 1000000 ints with the same
 * values. An example of such function: setDefaultCellWidth(width).
 * <p>
 * Section has boolean flags for visibility and navigation enablement. 
 * 
 * 
 * @author Jacek Kolodziejczyk created 02-03-2011
 */
class SectionCore<N extends Number> implements Section<N> {

	static final int DEFAULT_CELL_WIDTH = 16;
	static final int DEFAULT_LINE_WIDTH = 1;
	
	final Math<N> math;
	N count;
	
	final NumberOrder<N> order;
	final NumberSet<N> hidden;
	private final NumberSet<N> resizable;
	private final NumberSet<N> moveable;
	private final NumberSet<N> hideable;
	private final IntAxisState<N> cellWidth;
	private final IntAxisState<N> lineWidth;
	private final ObjectAxisState<N, N> cellSpan;
	
	final NumberQueueSet<N> selection;
	private final NumberQueueSet<N> lastSelection;

	private boolean defaultResizable, defaultMoveable, defaultHideable; 
	private boolean isNavigationEnabled, isVisible;
	
	Axis<N> axis;
	int index;
	final Listeners listeners;
  private final Class<N> indexClass;
  SectionClient<N> client;
	
	/**
	 * Constructs a section indexed by the given sub-class of {@link Number}.
	 * 
	 * @param numberClass defines the class used for indexing
	 */
	public SectionCore(Class<N> numberClass) {
	  math = Math.getInstance(numberClass);
	  indexClass = math.getNumberClass();
		count = math.ZERO_VALUE();
		
		order = new NumberOrder<N>(math);
		hidden = new NumberSet<N>(math, true);
		resizable = new NumberSet<N>(math, true);
		moveable = new NumberSet<N>(math, true);
		hideable = new NumberSet<N>(math, true);
		
		cellWidth = new IntAxisState<N>(math, DEFAULT_CELL_WIDTH);
		lineWidth = new IntAxisState<N>(math, DEFAULT_LINE_WIDTH);
		cellSpan = new ObjectAxisState<N, N>(math, math.ONE_VALUE());
		
		selection = new NumberQueueSet<N>(math);
		lastSelection = new NumberQueueSet<N>(math);
		
		defaultResizable = true;
		isNavigationEnabled = isVisible = true;
		listeners = new Listeners();
	}
	

	@Override
	public String toString() {
		return Integer.toString(index);
	}
	
	@Override public SectionCore<N> getUnchecked() {
	  return this;
	}
	
	@Override public Class<N> getIndexClass() {
	  return indexClass;
	}
	/*------------------------------------------------------------------------
	 * Collection like  
	 */

	@Override public void setCount(N count) {
		this.count = count;
		order.setCount(count);
	}
	
	@Override public N getCount() {
		return count;
	}

	@Override public boolean isEmpty() {
		return order.items.isEmpty();
	}

	@Override public N getIndex(N position) {
		if (math.compare(position, getVisibleCount()) >= 0) return null;
		
		MutableNumber<N> pos1 = math.create(0);
		MutableNumber<N> pos2 = math.create(0);
		
		for (int i = 0, size = order.items.size(); i < size; i++) {
			MutableExtent<N> e = order.items.get(i);
			pos2.add(e.end).subtract(e.start).subtract(hidden.getCount(e.start(), e.end()));
			if (math.compare(pos2, position) >= 0) {
				MutableNumber<N> count = hidden.getCount(e.start(), pos1.getValue());
				return pos1.subtract(position).negate().add(e.start).add(count).getValue();
			}
			pos2.increment();
			pos1.set(pos2); 
		}
		return null;
	}
	
	@Override
	public N getPosition(N index) {
		if (index == null || hidden.contains(index)) return null;
		MutableNumber<N> hiddenCount = math.create(0);
		MutableNumber<N> pos1 = math.create(0);
		MutableNumber<N> pos2 = math.create(0);
		
		for (int i = 0, size = order.items.size(); i < size; i++) {
			MutableExtent<N> e = order.items.get(i);
			boolean contains = math.contains(e, index);
			hiddenCount.add(hidden.getCount(e.start(), contains ? index : e.end()));
			if (contains) {
				return pos2.add(index).subtract(e.start).subtract(hiddenCount).getValue();
			}
			pos1.set(pos2);
			pos2.add(e.end).subtract(e.start).increment(); //.subtract(hiddenCount);
		}
		return null;
	}

	@Override public N getOrder(N item) {
	  return item == null ? null : order.indexOf(item);
	}
	
	@Override
	public Iterator<N> getOrder() {
	  return new ImmutableIterator<N>() {
      NumberSequence<N> seq = new NumberSequence<N>(order.copy());
      private boolean next;
      {
        seq.init();
      }
      @Override
      public boolean hasNext() {
        next = seq.next();
        return next;
      }

      @Override
      public N next() {
        return next ? seq.index() : null;
      }
    };
	}

	@Override
	public Iterator<Extent<N>> getOrderExtents() {
	  return new ImmutableIterator<Extent<N>>() {
	    NumberSequence<N> seq = new NumberSequence<N>(order.copy());
	    private boolean next;
	    {
	      seq.init();
	    }
	    @Override
	    public boolean hasNext() {
	      next = seq.nextExtent();
	      return next;
	    }

	    @Override
	    public Extent<N> next() {
	      return next ? Extent.createUnchecked(seq.start(), seq.end()) : null;
	    }
	  };
	}
	
	/*------------------------------------------------------------------------
	 * Section properties
	 */

	@Override public void setVisible(boolean visible) {
		this.isVisible = visible;
	}
	
	@Override public boolean isVisible() {
		return isVisible;
	}

	@Override public void setFocusItemEnabled(boolean enabled) {
		this.isNavigationEnabled = enabled;
	}
	
	@Override public boolean isFocusItemEnabled() {
		return isNavigationEnabled;
	}

	
	
	/*------------------------------------------------------------------------
	 * Default values 
	 */
	
	  @Override public void setDefaultCellWidth(int width) {
		if (width < 0) return;
		cellWidth.setDefault(width);
	}
	
	@Override public int getDefaultCellWidth() {
		return cellWidth.getDefault();
	}
	
	@Override public void setDefaultLineWidth(int width) {
		if (width < 0) return;
		lineWidth.setDefault(width);
	}
	
	@Override public int getDefaultLineWidth() {
		return lineWidth.getDefault();
	}
	
	
	@Override public boolean isDefaultResizable() {
		return defaultResizable;
	}

	  @Override public void setDefaultResizable(boolean resizable) {
		this.defaultResizable = resizable;
	}
	
	@Override public boolean isDefaultMoveable() {
		return defaultMoveable;
	}

	@Override public void setDefaultMoveable(boolean moveable) {
		this.defaultMoveable = moveable;
	}

	@Override public boolean isDefaultHideable() {
		return defaultHideable;
	}

	  @Override public void setDefaultHideable(boolean hideable) {
		this.defaultHideable = hideable;
	}
	
	
	
	/*------------------------------------------------------------------------
	 * Item properties 
	 */
	
	@Override public void setLineWidth(N start, N end, int width) {
    	lineWidth.setValue(start, end, width);
    }


  @Override public void setLineWidth(N index, int width) {
		lineWidth.setValue(index, index, width);
	}
	
	@Override public int getLineWidth(N index) {
		return lineWidth.getValue(index);
	}
	
	@Override public void setCellWidth(N start, N end, int width) {
  	cellWidth.setValue(start, end, width);
  }


  @Override public void setCellWidth(N index, int width) {
		cellWidth.setValue(index, index, width);
	}
	public void setCellWidth(N index) {
	  if (axis != null) {
	    axis.matrix.pack(axis.symbol, this, index);
	  }
  }
	
	@Override public int getCellWidth(N index) {
		return cellWidth.getValue(index);
	}

	
	@Override public void setMoveable(N start, N end, boolean enabled) {
  	moveable.change(start, end, enabled != defaultMoveable);
  }


  @Override public void setMoveable(N index, boolean enabled) {
		moveable.change(index, index, enabled != defaultMoveable);
	}
	
	@Override public boolean isMoveable(N index) {
		return moveable.contains(index) != defaultMoveable;
	}
	
	
	@Override public void setResizable(N start, N end, boolean enabled) {
  	resizable.change(start, end, enabled != defaultResizable);
  }


  @Override public void setResizable(N index, boolean enabled) {
		resizable.change(index, index, enabled != defaultMoveable);
	}
	
	@Override public boolean isResizable(N index) {
		return resizable.contains(index) != defaultResizable;
	}
	
	@Override public void setHideable(N start, N end, boolean enabled) {
  	hideable.change(start, end, enabled != defaultHideable);
  }


  @Override public void setHideable(N index, boolean enabled) {
		hideable.change(index, index, enabled != defaultMoveable);
	}
	
	@Override public boolean isHideable(N index) {
		return hideable.contains(index) != defaultHideable;
	}

	
	/*------------------------------------------------------------------------
	 * Hiding 
	 */
	
	
	
	@Override public void setHidden(N start, N end, boolean state) {
  	hidden.change(start, end, state);
  }


  @Override public void setHidden(N index, boolean state) {
		hidden.change(index, index, state);
	}
	
	@Override public boolean isHidden(N index) {
		return hidden.contains(index);
	}
	
	@Override public N getHiddenCount() {
		return hidden.getCount().getValue();
	}
	
	@Override public Iterator<N> getHidden() {
		return new IndexIterator(new NumberSequence<N>(hidden));
	}
	
	@Override public Iterator<Extent<N>> getHiddenExtents() {
	  return new ImmutableIterator<Extent<N>>() {
	    NumberSequence<N> seq = new NumberSequence<N>(hidden.copy());
	    private boolean next;
	    {
	      seq.init();
	    }
	    @Override
	    public boolean hasNext() {
	      next = seq.nextExtent();
	      return next;
	    }

	    @Override
	    public Extent<N> next() {
	      return next ? Extent.createUnchecked(seq.start(), seq.end()) : null;
	    }
	  };
	}


	/*------------------------------------------------------------------------
	 * Selection 
	 */
	
	@Override public void setSelected(N start, N end, boolean state) {
  	selection.change(start, end, state);
  	
  	if (axis != null) {
  		axis.selectInZones(this, start, end, state, false);
  	}
  }


  @Override public void setSelected(N index, boolean state) {
  	hidden.change(index, index, state);
  }


  @Override public void setSelected(boolean state) {
    setSelectedAll(state, false, true);
  }
	
	void setSelected(N start, N end, boolean state, boolean notify) {
	// Determine if there is a selection change
//    boolean modified = false;
//    if (notify) {
//        if (state == true) {
//          boolean allSelected = getSelectedCount().equals(getCount());
//          if (!allSelected) {
//            modified = true;
//          }
//        }
//        else {
//          boolean nothingSelected = getSelectedCount().equals(math.ZERO_VALUE());
//          if (!nothingSelected) {
//            modified = true;
//          }
//        }
//    }
    
	  selection.change(start, end, state);
	  
//	  if (modified) {
      addSelectionEvent();
//    }
	  
	  if (axis != null) {
	    axis.selectInZones(this, start, end, state, notify);
	  }
	}
	
	void setSelectedAll(boolean state, boolean notify, boolean notifyInZones) {
	  N start = math.ZERO_VALUE();
	  N end = math.decrement(count);
		if (state) {
      selection.add(start, end);
		} else {
			selection.clear();
		}
		
		if (axis != null) {
      axis.selectInZones(this, start, end, state, notifyInZones);
    }
	}
	
	@Override public boolean isSelected(N index) {
		return selection.contains(index);
	}
	
	@Override public N getSelectedCount() {
		return selection.getCount().getValue();
	}
	
	/**
	 * Returns a sequence of indexes of selected items. 
	 * @return a sequence of indexes of selected items
	 */
	NumberSequence<N> getSelectedSequence() {
		return new NumberSequence<N>(selection);
	}
	
	@Override public Iterator<N> getSelected() {
	  return new ImmutableIterator<N>() {
			NumberSequence<N> seq = new NumberSequence<N>(selection.copy());
			private boolean next;
			{
				seq.init();
			}
			@Override
			public boolean hasNext() {
				next = seq.next();
				return next;
			}

			@Override
			public N next() {
				return next ? seq.index() : null;
			}
		};
	}
	
	@Override public Iterator<Extent<N>> getSelectedExtents() {
		return new ImmutableIterator<Extent<N>>() {
			NumberSequence<N> seq = new NumberSequence<N>(selection.copy());
			private boolean next;
			{
				seq.init();
			}
			@Override
			public boolean hasNext() {
				next = seq.nextExtent();
				return next;
			}
			
			@Override
			public Extent<N> next() {
				return next ? Extent.create(seq.start(), seq.end()) : null;
			}
		};
	}
	
	void backupSelection() {
		lastSelection.replace(selection);
	}
	
	void restoreSelection() {
		selection.replace(lastSelection);
	}
	
	
	
	/*------------------------------------------------------------------------
	 * Moving
	 */
	
	@Override public void setOrder(N start, N end, N target) {
		order.move(start, end, target);
	}
	
	@Override public void setOrder(N index, N target) {
	  order.move(index, index, target);
	}
	
	@Override public void delete(N start, N end) {
		cellWidth.delete(start, end);
		lineWidth.delete(start, end);
		cellSpan.delete(start, end);
		resizable.delete(start, end);
		moveable.delete(start, end);
		hideable.delete(start, end);
		hidden.delete(start, end);
		order.delete(start, end);
		selection.delete(start, end);
		lastSelection.delete(start, end);
		count = math.create(count).subtract(end).add(start).decrement().getValue();
		
		if (axis != null) {
			axis.deleteInZones(this, start, end);
		}
	}

	@Override public void insert(N target, N count) {
		cellWidth.insert(target, count);
		lineWidth.insert(target, count);
		cellSpan.insert(target, count);
		resizable.insert(target, count);
		moveable.insert(target, count);
		hideable.insert(target, count);
		hidden.insert(target, count);
		order.insert(target, count);
		selection.insert(target, count);
		lastSelection.insert(target, count);
		this.count = math.create(this.count).add(count).getValue();
		
		if (axis != null) {
		  axis.insertInZones(this, target, count);
		}
	}
	
	@Override public void addControlListener(ControlListener listener) {
		TypedListener typedListener = new TypedListener(listener);
		listeners.add(SWT.Resize, typedListener);
		listeners.add(SWT.Move, typedListener);
	}
	
	@Override public void addSelectionListener (SelectionListener listener) {
		Preconditions.checkNotNullWithName(listener, "listener");
		TypedListener typedListener = new TypedListener(listener);
		listeners.add(SWT.Selection, typedListener);
		listeners.add(SWT.DefaultSelection, typedListener);
	}

	@Override public void removeControlListener (ControlListener listener) {
		Preconditions.checkNotNullWithName(listener, "listener");
		listeners.remove(SWT.Move, listener);
		listeners.remove(SWT.Resize, listener);
	}

	@Override public void removeSelectionListener(SelectionListener listener) {
		Preconditions.checkNotNullWithName(listener, "listener");
		listeners.remove(SWT.Selection, listener);
		listeners.remove(SWT.DefaultSelection, listener);
	}

	
	/*------------------------------------------------------------------------
	 * Non public 
	 */
	
	N getVisibleCount() {
		return math.create(count).subtract(hidden.getCount()).getValue();
	}
	
	N getCellSpan(N index) {
		return cellSpan.getValue(index);
	}

	N nextNotHiddenIndex(N index, int direction) {
		for (MutableExtent<N> e: hidden.items) {
			if (math.contains(e, index)) {
				if (direction > 0) {
					index = math.increment(e.end());
					if (math.compare(index, count) >= 0) {
						return null;
					}
				} else {
					index = math.decrement(e.start());
					if (math.compare(index, math.ZERO_VALUE()) < 0) {
						return null;
					}
				}
				break;
			}
		}
		return math.compare(index, count) < 0 ? index : null;
	}

	ExtentSequence<N> getSelectedExtentSequence() {
		return new ExtentSequence<N>(selection.items);
	}
	
	ExtentSequence<N> getSelectedExtentResizableSequence() {
		return new ExtentSequence<N>(selection.items) {
//			private int j;
//			@Override
//			public void init() {
//				super.init();
//				j = -1;
//			}
			@Override
			public boolean next() {
				return super.next();
//				if (++i >= items.size() ) return false;
//				Extent<N> e = items.get(i);
//				start = e.start();
//				end = e.end();
//				
//				boolean quit = false;
//				while (++j < resizable.items.size()) {
//					Extent<N> e2 = resizable.items.get(j);
//					int location = math.compare(e.start(), e.end(), e2.start(), e2.end());
//					switch (location) {
//					case AFTER: 			continue;
//					case BEFORE: 		
//						j = resizable.items.size(); // Quit the loop
//						break;
//					
//					case CROSS_BEFORE:	
//						start = math.increment(end);
//						break;
//						
//					case CROSS_AFTER:	
//						item.end.set(math.max(math.decrement(start), item.start()));
//						break;
//						
//					case EQUAL:	
//					case OVERLAP:	
//						toRemove.add(0, item);
//						break;
//						
//					case INSIDE:
//						MutableNumber newEnd = item.end.copy();
//						item.end.set(math.max(math.decrement(start), item.start()));
//						items.add(i+1, new Extent(math.create(end).increment(), newEnd));
//					}
//					
//					
//					int compare = math.compare(e.start(), e.end(), e2.start(), e2.end());
//					switch (compare) {
//					case AFTER: 		quit = true; break; 
//					case EQUAL:
//					case INSIDE:		return false;
//					
//					case CROSS_BEFORE: 			
//					case CROSS_AFTER:		
//					case OVERLAP:		
//						if (math.contains(e, e2.start()) || math.contains(e, e2.end())) {
//							if (defaultResizable == true) {
//								start = math.min(e.start(), e2.start());
//								end = math.min(e.start(), e2.start());
//							}
//						} 
//						break; 
//					}
//					
//					if (quit) break;
//				}
//				return true;
			}
		};
	}
	
	class IndexIterator extends ImmutableIterator<N> {
		
		private final NumberSequence<N> seq;

		IndexIterator(NumberSequence<N> seq) {
			super();
			this.seq = seq;
			seq.init();
		}

		@Override
		public boolean hasNext() {
			return seq.hasNext();
		}

		@Override
		public N next() {
			seq.next();
			return seq.index();
		}

	}

	boolean moveSelected(N source, N target) {
		assert selection.contains(source);
		if (selection.isEmpty() || selection.contains(target)) return false;
			
		int position = math.compare(getOrder(target), getOrder(source));
		if (position == 0) return false;
		N index = position < 0 ? target : math.increment(target);

		order.move(selection, index);
		return true;
	}
	
	protected void checkRange(N start, N end, N limit) {
		checkIndex(start, limit, "start");
		checkIndex(end, limit, "end");
		
		if (math.compare(start, end) > 0) {
			throw new IllegalArgumentException(MessageFormat.format(
				"start ({0}) cannot be greater then end {1}", start, end)) ;
		}
	}
	
	public void checkCellIndex(N index, String name) {
	  checkIndex(index, count, name);
	}
	
	public void checkLineIndex(N index, String name) {
	  checkIndex(index, math.increment(count), name);
	}
	
	private void checkIndex(N index, N limit, String name) {
		Preconditions.checkNotNullWithName(index, name);
		if (getIndexClass() != index.getClass()) {
		  throw new IndexOutOfBoundsException(MessageFormat.format(
		    "section indexing class ({0}) must be the same as the class of index ({1})", 
		    getIndexClass(), index.getClass())) ;
		}
		if (math.compare(index, math.ZERO_VALUE()) < 0) {
			throw new IndexOutOfBoundsException(MessageFormat.format(
					"{0} ({1}) cannot be negative", name, index)) ;
		}
		if (math.compare(index, limit) >= 0) {
			throw new IndexOutOfBoundsException(MessageFormat.format(
					"{0} ({1}) must be lower then limit {2}", name, index, limit))  ;
		}
	}

	void addSelectionEvent() {
    Event event = new Event();
    event.type = SWT.Selection;
    event.widget = axis.matrix;
    listeners.add(event);
  }
	
	static <N2 extends Number> SectionCore<N2> from(AxisItem<N2> item) {
	  return item.section;
	}
	
	static <N2 extends Number> SectionCore<N2> from(Section<N2> section) {
	  return (SectionCore<N2>) section.getUnchecked();
	}


}
