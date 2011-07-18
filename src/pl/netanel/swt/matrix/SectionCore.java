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
	private final IntAxisState cellWidth;
	private final IntAxisState lineWidth;
	private final ObjectAxisState<N> cellSpan;
	
	final NumberQueueSet<N> selection;
	private final NumberQueueSet<N> lastSelection;

	private boolean defaultResizable, defaultMoveable, defaultHideable; 
	private boolean isNavigationEnabled, isVisible;
	
	Axis<N> axis;
	int index;
	final Listeners listeners;
  private final Class<N> indexClass;
	
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
		hidden = new NumberSet(math, true);
		resizable = new NumberSet(math, true);
		moveable = new NumberSet(math, true);
		hideable = new NumberSet(math, true);
		
		cellWidth = new IntAxisState(math, DEFAULT_CELL_WIDTH);
		lineWidth = new IntAxisState(math, DEFAULT_LINE_WIDTH);
		cellSpan = new ObjectAxisState(math, 1);
		
		selection = new NumberQueueSet(math);
		lastSelection = new NumberQueueSet(math);
		
		isNavigationEnabled = isVisible = true;
		listeners = new Listeners();
	}
	

	@Override
	public String toString() {
		return Integer.toString(index);
	}
	
	@Override public Section getCore() {
	  return this;
	}
	
	@Override public Class getIndexClass() {
	  return indexClass;
	}
	/*------------------------------------------------------------------------
	 * Collection like  
	 */

	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#setCount(N)
   */
	public void setCount(N count) {
		this.count = count;
		order.setCount(count);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#getCount()
   */
	public N getCount() {
		return count;
	}

	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#isEmpty()
   */
	public boolean isEmpty() {
		return order.items.isEmpty();
	}

	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#get(N)
   */
	public N get(N position) {
		if (math.compare(position, getVisibleCount()) >= 0) return null;
		
		MutableNumber<N> pos1 = math.create(0);
		MutableNumber<N> pos2 = math.create(0);
		
		for (int i = 0, size = order.items.size(); i < size; i++) {
			Extent<N> e = order.items.get(i);
			pos2.add(e.end).subtract(e.start).subtract(hidden.getCount(e.start(), e.end()));
			if (math.compare(pos2, position) >= 0) {
				MutableNumber count = hidden.getCount(e.start(), pos1.getValue());
				return pos1.subtract(position).negate().add(e.start).add(count).getValue();
			}
			pos2.increment();
			pos1.set(pos2); 
		}
		return null;
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#indexOf(N)
   */
	public N indexOf(N item) {
		return item == null ? null : order.indexOf(item);
	}
	
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#indexOfNotHidden(N)
   */
	public N indexOfNotHidden(N index) {
		if (index == null || hidden.contains(index)) return null;
		MutableNumber<N> hiddenCount = math.create(0);
		MutableNumber<N> pos1 = math.create(0);
		MutableNumber<N> pos2 = math.create(0);
		
		for (int i = 0, size = order.items.size(); i < size; i++) {
			Extent<N> e = order.items.get(i);
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


	/*------------------------------------------------------------------------
	 * Section properties
	 */

	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#setVisible(boolean)
   */
 	public void setVisible(boolean visible) {
		this.isVisible = visible;
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#isVisible()
   */
	public boolean isVisible() {
		return isVisible;
	}

	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#setFocusItemEnabled(boolean)
   */
	public void setFocusItemEnabled(boolean enabled) {
		this.isNavigationEnabled = enabled;
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#isFocusItemEnabled()
   */
	public boolean isFocusItemEnabled() {
		return isNavigationEnabled;
	}

	
	
	/*------------------------------------------------------------------------
	 * Default values 
	 */
	
	  /* (non-Javadoc)
     * @see pl.netanel.swt.matrix.ISection#setDefaultCellWidth(int)
     */
	public void setDefaultCellWidth(int width) {
		if (width < 0) return;
		cellWidth.setDefault(width);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#getDefaultCellWidth()
   */
	public int getDefaultCellWidth() {
		return cellWidth.getDefault();
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#setDefaultLineWidth(int)
   */
	public void setDefaultLineWidth(int width) {
		if (width < 0) return;
		lineWidth.setDefault(width);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#getDefaultLineWidth()
   */
	public int getDefaultLineWidth() {
		return lineWidth.getDefault();
	}
	
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#isDefaultResizable()
   */
	public boolean isDefaultResizable() {
		return defaultResizable;
	}

	  /* (non-Javadoc)
     * @see pl.netanel.swt.matrix.ISection#setDefaultResizable(boolean)
     */
	public void setDefaultResizable(boolean resizable) {
		this.defaultResizable = resizable;
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#isDefaultMoveable()
   */
	public boolean isDefaultMoveable() {
		return defaultMoveable;
	}

	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#setDefaultMoveable(boolean)
   */
	public void setDefaultMoveable(boolean moveable) {
		this.defaultMoveable = moveable;
	}

	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#isDefaultHideable()
   */
	public boolean isDefaultHideable() {
		return defaultHideable;
	}

	  /* (non-Javadoc)
     * @see pl.netanel.swt.matrix.ISection#setDefaultHideable(boolean)
     */
	public void setDefaultHideable(boolean hideable) {
		this.defaultHideable = hideable;
	}
	
	
	
	/*------------------------------------------------------------------------
	 * Item properties 
	 */
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#setLineWidth(N, int)
   */ 
	public void setLineWidth(N index, int width) {
		lineWidth.setValue(index, index, width);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#setLineWidth(N, N, int)
   */ 
	public void setLineWidth(N start, N end, int width) {
		lineWidth.setValue(start, end, width);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#getLineWidth(N)
   */
	public int getLineWidth(N index) {
		return lineWidth.getValue(index);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#setCellWidth(N, int)
   */ 
	public void setCellWidth(N index, int width) {
		cellWidth.setValue(index, index, width);
	}
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#setCellWidth(N, N, int)
   */ 
	public void setCellWidth(N start, N end, int width) {
		cellWidth.setValue(start, end, width);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#getCellWidth(N)
   */
	public int getCellWidth(N index) {
		return cellWidth.getValue(index);
	}

	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#setMoveable(N, boolean)
   */ 
	public void setMoveable(N index, boolean enabled) {
		moveable.change(index, index, enabled != defaultMoveable);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#setMoveable(N, N, boolean)
   */ 
	public void setMoveable(N start, N end, boolean enabled) {
		moveable.change(start, end, enabled != defaultMoveable);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#isMoveable(N)
   */
	public boolean isMoveable(N index) {
		return moveable.contains(index) != defaultMoveable;
	}
	
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#setResizable(N, boolean)
   */ 
	public void setResizable(N index, boolean enabled) {
		resizable.change(index, index, enabled != defaultMoveable);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#setResizable(N, N, boolean)
   */ 
	public void setResizable(N start, N end, boolean enabled) {
		resizable.change(start, end, enabled != defaultResizable);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#isResizable(N)
   */
	public boolean isResizable(N index) {
		return resizable.contains(index) != defaultResizable;
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#setHideable(N, boolean)
   */ 
	public void setHideable(N index, boolean enabled) {
		hideable.change(index, index, enabled != defaultMoveable);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#setHideable(N, N, boolean)
   */ 
	public void setHideable(N start, N end, boolean enabled) {
		hideable.change(start, end, enabled != defaultHideable);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#isHideable(N)
   */
	public boolean isHideable(N index) {
		return hideable.contains(index) != defaultHideable;
	}

	
	/*------------------------------------------------------------------------
	 * Hiding 
	 */
	
	
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#setHidden(N, boolean)
   */ 
	public void setHidden(N index, boolean state) {
		hidden.change(index, index, state);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#setHidden(N, N, boolean)
   */ 
	public void setHidden(N start, N end, boolean state) {
		hidden.change(start, end, state);
	}

	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#setHiddenSelected(boolean)
   */ 
	public void setHiddenSelected(boolean state) {
		for (int i = 0, imax = selection.items.size(); i < imax; i++) {
			Extent<N> e = selection.items.get(i);
			setHidden(e.start(), e.end(), state);
		}
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#isHidden(N)
   */
	public boolean isHidden(N index) {
		return hidden.contains(index);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#getHiddenCount(N, N)
   */
	public N getHiddenCount(N start, N end) {
		return hidden.getCount(start, end).getValue();
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#getHiddenCount()
   */
	public N getHiddenCount() {
		return hidden.getCount().getValue();
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#getHidden()
   */
	public Iterator<N> getHidden() {
		return new IndexIterator(new NumberSequence(hidden));
	}
	
	
	
	/*------------------------------------------------------------------------
	 * Selection 
	 */
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#setSelected(N, boolean)
   */ 
	public void setSelected(N index, boolean state) {
		hidden.change(index, index, state);
	}
	
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#setSelected(N, N, boolean)
   */ 
	public void setSelected(N start, N end, boolean state) {
		selection.change(start, end, state);
		
		if (axis != null) {
			axis.selectInZones(this, start, end, state, false);
		}
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
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#setSelectedAll(boolean, boolean, boolean)
   */ 
	public void setSelectedAll(boolean state, boolean notify, boolean notifyInZones) {
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
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#isSelected(N)
   */
	public boolean isSelected(N index) {
		return selection.contains(index);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#getSelectedCount()
   */
	public N getSelectedCount() {
		return selection.getCount().getValue();
	}
	
	/**
	 * Returns a sequence of indexes of selected items. 
	 * @return a sequence of indexes of selected items
	 */
	NumberSequence<N> getSelected() {
		return new NumberSequence(selection);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#getSelectedIterator()
   */
	public Iterator<N> getSelectedIterator() {
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
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#getSelectedExtentIterator()
   */
	public Iterator<Number[]> getSelectedExtentIterator() {
		return new ImmutableIterator<Number[]>() {
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
			public Number[] next() {
				return next ? new Number[] {seq.start(), seq.end()} : null;
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
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#move(N, N, N)
   */
	public void move(N start, N end, N target) {
		order.move(start, end, target);
	}
	
	 /* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#delete(N, N)
   */
	public void delete(N start, N end) {
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

	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#insert(N, N)
   */
	public void insert(N target, N count) {
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
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#addControlListener(org.eclipse.swt.events.ControlListener)
   */
	public void addControlListener(ControlListener listener) {
		TypedListener typedListener = new TypedListener(listener);
		listeners.add(SWT.Resize, typedListener);
		listeners.add(SWT.Move, typedListener);
	}
	
	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#addSelectionListener(org.eclipse.swt.events.SelectionListener)
   */
	public void addSelectionListener (SelectionListener listener) {
		Preconditions.checkNotNullWithName(listener, "listener");
		TypedListener typedListener = new TypedListener(listener);
		listeners.add(SWT.Selection, typedListener);
		listeners.add(SWT.DefaultSelection, typedListener);
	}

	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#removeControlListener(org.eclipse.swt.events.ControlListener)
   */
	public void removeControlListener (ControlListener listener) {
		Preconditions.checkNotNullWithName(listener, "listener");
		listeners.remove(SWT.Move, listener);
		listeners.remove(SWT.Resize, listener);
	}

	/* (non-Javadoc)
   * @see pl.netanel.swt.matrix.ISection#removeSelectionListener(org.eclipse.swt.events.SelectionListener)
   */
	public void removeSelectionListener(SelectionListener listener) {
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
		for (Extent e: hidden.items) {
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
		return new ExtentSequence(selection.items);
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
		
		private NumberSequence<N> seq;

		IndexIterator(NumberSequence seq) {
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
			
		int position = math.compare(indexOf(target), indexOf(source));
		if (position == 0) return false;
		N index = position < 0 ? target : math.increment(target);

		order.move(selection, index);
		return true;
	}
	
	protected void checkRange(N start, N end, N limit) {
		Preconditions.checkNotNull(start, "start");
		Preconditions.checkNotNull(end, "end");
		if (math.compare(start, math.ZERO_VALUE()) < 0) {
			throw new IndexOutOfBoundsException(MessageFormat.format(
				"start ({0}) cannot be negative", start)) ;
		}
		if (math.compare(end, math.ZERO_VALUE()) < 0) {
			throw new IndexOutOfBoundsException(MessageFormat.format(
				"end ({0}) cannot be negative", end)) ;
		}
		if (math.compare(start, limit) >= 0) {
			throw new IndexOutOfBoundsException(MessageFormat.format(
				"start ({0}) must be lower then limit {1}", start, limit)) ;
		}
		if (math.compare(end, limit) >= 0) {
			throw new IndexOutOfBoundsException(MessageFormat.format(
				"end ({0}) must be lower then limit {1}", end, limit)) ;
		}
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
		Preconditions.checkNotNull(index, name);
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
	
	static SectionCore from(AxisPointer item) {
	  return (SectionCore) item.getSection().getCore();
	}
	
	static SectionCore from(Section section) {
	  return (SectionCore) section.getCore();
	}

}
