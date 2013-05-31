/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.TypedListener;

import pl.netanel.swt.matrix.DirectionIndexSequence.Forward;
import pl.netanel.swt.matrix.NumberSetCore.ContentChangeListener;
import pl.netanel.util.ImmutableIterator;
import pl.netanel.util.Nullable;
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
 */
class SectionCore<N extends Number> implements Section<N> {

  static final int DEFAULT_CELL_WIDTH = 16;
  static final int DEFAULT_LINE_WIDTH = 1;

  SectionClient<N> client;
  final Math<N> math;
  N count;

  final NumberOrder<N> order;
  final NumberSetCore<N> hidden; // union of all hidden sets
  final NumberSetCore<N> hiddenByUser; // hidden by user form stanrd ui actions
  final ArrayList<NumberSet<N>> hiddenSets;
  final NumberSetCore<N> buried;
  final ValueNumberSetMap<N, N> parents;

  //	NumberOrder<N> finale;

  private final NumberSetCore<N> expanded;
  private final NumberSetCore<N> resizable;
  private final NumberSetCore<N> moveable;
  private final NumberSetCore<N> hideable;
  private final IntAxisState<N> cellWidth;
  private final IntAxisState<N> lineWidth;

  final NumberList<N> selection;
  private final NumberList<N> lastSelection;

  private boolean defaultResizable, defaultMoveable, defaultHideable;
  private boolean isNavigationEnabled, isVisible;

  Axis<N> axis;
  int index;
  final TypedListeners listeners;
  private final Class<N> indexClass;
  final private HashMap<NumberSet<N>, ContentChangeListener<N>> hiddenSetListeners;

  boolean isDirty;
  ExtentSequence.Forward<N> seq;

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
    //		merged = new NumberSet<N>(math, true);
    hidden = new NumberSetCore<N>(math, true);
    hiddenSets = new ArrayList<NumberSet<N>>();
    hiddenSetListeners = new HashMap<NumberSet<N>, ContentChangeListener<N>>();
    hiddenByUser = new NumberSetCore<N>(math, true);
    addHiddenSet(hiddenByUser);
    buried = new NumberSetCore<N>(math, true);
    expanded = new NumberSetCore<N>(math, true);
    parents = new ValueNumberSetMap<N, N>(math, null);

    //		finale = new NumberOrder<N>(math);

    resizable = new NumberSetCore<N>(math, true);
    moveable = new NumberSetCore<N>(math, true);
    hideable = new NumberSetCore<N>(math, true);

    cellWidth = new IntAxisState<N>(math, DEFAULT_CELL_WIDTH);
    lineWidth = new IntAxisState<N>(math, DEFAULT_LINE_WIDTH);

    selection = new NumberList<N>(math);
    lastSelection = new NumberList<N>(math);

    seq = new ExtentSequence.Forward<N>(order);

    defaultResizable = true;
    isNavigationEnabled = isVisible = true;
    listeners = new TypedListeners();
    isDirty = true;
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

  @Override public Axis<N> getAxis() {
    return axis;
  }

  /*------------------------------------------------------------------------
   * Collection like
   */

  @Override public void setCount(N count) {
    int compare = math.compare(count, this.count);
    if (compare == 0) {
      return;
    }
    else if (compare > 0) {
      parents.setValue(this.count, count, null);
    }
    else {
      parents.unsetValue(count, this.count, null);
      delete(count, math.decrement(this.count));
    }
    order.setCount(count);
    this.count = count;
    refreshFinale();
    isDirty = true;
  }

  @Override public N getCount() {
    return count;
  }

  @Override public boolean isEmpty() {
    return order.items.isEmpty();
  }

  @Override public N getIndex(N position) {
    if (math.compare(position, getVisibleCount()) > 0) return null;

    MutableNumber<N> pos1 = math.create(0);
    MutableNumber<N> pos2 = math.create(0);

    for (int i = 0, size = order.items.size(); i < size; i++) {
      MutableExtent<N> e = order.items.get(i);
      pos2.add(e.end).subtract(e.start).subtract(hidden.getMutableCount(e.start(), e.end()));
      if (math.compare(pos2, position) >= 0) {
        MutableNumber<N> count = hidden.getMutableCount(e.start(), pos1.getValue());
        return pos1.subtract(position).negate().add(e.start).add(count).getValue();
      }
      pos2.increment();
      pos1.set(pos2);
    }
    return math.compare(pos2, position) == 0 ? pos2.getValue() : null;
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
      hiddenCount.add(hidden.getMutableCount(e.start(), contains ? index : e.end()));
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
  public NumberSet<N> getOrder() {
    return order;
  }

  @Override
  public Iterator<Extent<N>> getOrderExtents() {
    return order.extentIterator();
  }

  /*------------------------------------------------------------------------
   * Section properties
   */

  @Override public void setVisible(boolean visible) {
    this.isVisible = visible;
    isDirty = true;
  }

  @Override public boolean isVisible() {
    return isVisible;
  }

  @Override public void setFocusItemEnabled(boolean enabled) {
    this.isNavigationEnabled = enabled;
    isDirty = true;
  }

  @Override public boolean isFocusItemEnabled() {
    return isNavigationEnabled;
  }

  //	/**
  //   * Returns the maximum number of cells that can be merged along this axis.
  //   * Merging higher number of cells will throws an exception.
  //   * The reason for limitation of merged cells is to avoid performance degradation
  //   * when summing sizes of large merged extents during layout computation.
  //   * @return the maxSpan
  //   * @see {@link #setMaxSpan(Number)}
  //   */
  //  public N getMaxSpan() {
  //    return maxSpan;
  //  }
  //
  //  public void setMaxSpan(N maxSpan) {
  //    this.maxSpan = maxSpan;
  //  }
  //
  //  /**
  //   * Sets the maximum number of cells that can be merged along this axis.
  //   * Merging higher number of cells will throws an exception.
  //   * The reason for limitation of merged cells is to avoid performance degradation
  //   * when summing sizes of large merged extents during layout computation.
  //   * <p>
  //   * The default number is 1000.
  //   * @param maxSpan the maximum number of cells that can be merged.
  //   * @param force forces removal of merging that exceeds the given <code>maxSpan</code>
  //   * @throws IllegalArgumentException if some merged cells of higher number then maxSpan exist
  //   * and <code>force</code> equals false.
  //   * @see {@link #getMaxSpan()}
  //   */
  //  @SuppressWarnings({ "rawtypes", "unchecked" }) // Generics is not always helpful
  //  void setMaxSpan2(N maxSpan, boolean force) {
  //    ArrayList<Integer> exceeding = new ArrayList<Integer>();
  //    for (ZoneCore zone: axis.matrix.layout.zones) {
  //      if (this.equals(zone.sectionX)) {
  //        for (int i = 0; i < zone.cellMerging.itemsX.size(); i++) {
  //          MutableExtent extentX = ((ArrayList<MutableExtent>) zone.cellMerging.itemsX).get(i);
  //          MutableExtent extentY = ((ArrayList<MutableExtent>) zone.cellMerging.itemsY).get(i);
  //          if (math.compare(math.count(extentX), maxSpan) > 0) {
  //            exceeding.add(CellExtent.createUnchecked(
  //                extentX.start.getValue(), extentX.end.getValue(),
  //                extentY.start.getValue(), extentY.end.getValue()));
  //          }
  //        }
  //      }
  //    }
  //    if (force) {
  //      for (int i = exceeding.length; i++) {
  //
  //      }
  //    }
  //    this.maxSpan = maxSpan;
  //  }

  /*------------------------------------------------------------------------
   * Default values
   */

  @Override public void setDefaultCellWidth(int width) {
    if (width < 0) return;
    cellWidth.setDefault(width);
    isDirty = true;
  }

  @Override public int getDefaultCellWidth() {
    return cellWidth.getDefault();
  }

  @Override public void setDefaultLineWidth(int width) {
    if (width < 0) return;
    lineWidth.setDefault(width);
    isDirty = true;
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

  @Override
  public void setLineWidth(N start, N end, int width) {
    lineWidth.setValue(start, end, width);
    isDirty = true;
  }

  @Override
  public void setLineWidth(N index, int width) {
    lineWidth.setValue(index, index, width);
    isDirty = true;
  }

  @Override
  public int getLineWidth(N index) {
    return lineWidth.getValue(index);
  }

  @Override
  public void setCellWidth(N start, N end, int width) {
    cellWidth.setValue(start, end, width);
    isDirty = true;
  }

  @Override
  public void setCellWidth(N index, int width) {
    cellWidth.setValue(index, index, width);
    isDirty = true;
  }

  @Override
  public void setCellWidth(N index) {
    if (axis != null) {
      axis.matrix.pack(axis.symbol, this, index);
    }
  }

  @Override
  public int computeSize(N index) {
    return axis == null ? getCellWidth(index) :
      axis.matrix.computeSize(axis.symbol, this, index);
  }

  @Override
  public void setCellWidth() {
    if (axis != null) {
      NumberSequence<N> seq = order.numberSequence(null);
      for (seq.init(); seq.next();) {
        axis.matrix.pack(axis.symbol, this, seq.item());
      }
    }
  }

  @Override
  public int getCellWidth(N index) {
    return cellWidth.getValue(index);
  }

  @Override
  public void setMoveable(N start, N end, boolean enabled) {
    moveable.change(start, end, enabled != defaultMoveable);
  }

  @Override
  public void setMoveable(N index, boolean enabled) {
    moveable.change(index, index, enabled != defaultMoveable);
  }

  @Override
  public boolean isMoveable(N index) {
    return moveable.contains(index) != defaultMoveable;
  }

  @Override
  public void setResizable(N start, N end, boolean enabled) {
    resizable.change(start, end, enabled != defaultResizable);
  }

  @Override
  public void setResizable(N index, boolean enabled) {
    resizable.change(index, index, enabled != defaultMoveable);
  }

  @Override
  public boolean isResizable(N index) {
    return resizable.contains(index) != defaultResizable;
  }

  @Override
  public void setHideable(N start, N end, boolean enabled) {
    hideable.change(start, end, enabled != defaultHideable);
  }

  @Override
  public void setHideable(N index, boolean enabled) {
    hideable.change(index, index, enabled != defaultMoveable);
  }

  @Override
  public boolean isHideable(N index) {
    return hideable.contains(index) != defaultHideable;
  }


  /*------------------------------------------------------------------------
   * Hiding
   */



  @Override
  public void setHidden(N start, N end, boolean state) {
    hiddenByUser.change(start, end, state);
    refreshFinale();
    isDirty = true;
  }

  @Override
  public void setHidden(N index, boolean state) {
    setHidden(index, index, state);
  }

  @Override
  public boolean isHidden(N index) {
    return hidden.contains(index);
  }

  @Override
  public N getHiddenCount() {
    return hidden.getMutableCount().getValue();
  }

  @Override
  public NumberSet<N> getHidden() {
    return hidden;
  }

  @Override
  public Iterator<Extent<N>> getHiddenExtents() {
    return hidden.extentIterator();
  }

  @Override
  public NumberSetCore<N> getDefaultHiddenSet() {
    return hiddenByUser;
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
    setSelected(index, index, state);
  }


  @Override public void setSelected(boolean state) {
    setSelectedAll(state, false, true);
  }

  void setSelected(N start, N end, boolean state, boolean notify, boolean skipHidden) {
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
    if (skipHidden) {
      selection.removeAll(hidden);
    }

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
    return selection.getMutableCount().getValue();
  }

  /**
   * Returns a sequence of indexes of selected items.
   * @return a sequence of indexes of selected items
   */
  NumberSequence2<N> getSelectedSequence() {
    return new NumberSequence2<N>(math, selection.items);
  }

  @Override public NumberSet<N> getSelected() {
    return selection;
  }

  @Override public Iterator<Extent<N>> getSelectedExtents() {
    return selection.extentIterator();
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
    parents.setValue(start, end, getParent(target));
    order.move(start, end, target);
    refreshFinale();
    isDirty = true;
  }

  @Override public void setOrder(N index, N target) {
    setOrder(index, index, target);
  }

  @Override public void setOrder(Iterator<N> iterator) {
    order.clear();
    while(iterator.hasNext()) {
      N next = iterator.next();
      order.add(next);
    }
    refreshFinale();
    isDirty = true;
  }

  @Override public void setOrderExtents(Iterator<Extent<N>> iterator) {
    order.clear();
    while(iterator.hasNext()) {
      Extent<N> next = iterator.next();
      order.add(next.start, next.end);
    }
    refreshFinale();
    isDirty = true;
  }

  boolean moveSelected(N source, N target) {
    assert selection.contains(source);
    if (selection.isEmpty() || selection.contains(target)) return false;

    N targetRank = getOrder(target);
    N sourceRank = getOrder(source);
    int compareRank = math.compare(targetRank, sourceRank);
    if (compareRank == 0) return false;
    N index = compareRank < 0 ? target :
      math.compare(targetRank, math.decrement(count)) == 0 ? count :
        getIndex(math.increment(targetRank));
    if (index == null) return false;

    order.move(selection, index);

    //  	N parent = getParent(target);
    //  	if (parent != null) {
    //  	  for (int i = 0; i < selection.items.size(); i++) {
    //  	    MutableExtent<N> extent = selection.items.get(i);
    //  	    N item = isInTree(parent, extent.start, extent.end);
    //  	    if (item != null) {
    //  	      throw new RuntimeException(Util.format("Item is in tree of ", parent, ));
    //  	    }
    //  	  }
    //  	}

    isDirty = true;
    return true;
  }

  //
  //  private N isInTree(N parent, MutableNumber<N> start, MutableNumber<N> end) {
  //    return false;
  //  }


  @Override public void delete(N start, N end) {
    cellWidth.delete(start, end);
    lineWidth.delete(start, end);
    resizable.delete(start, end);
    moveable.delete(start, end);
    hideable.delete(start, end);
    hidden.delete(start, end);
    for (NumberSet<N> set: hiddenSets) {
      set.delete(start, end);
    }
    buried.delete(start, end);
    order.delete(start, end);
    selection.delete(start, end);
    lastSelection.delete(start, end);
    count = math.create(count).subtract(end).add(start).decrement().getValue();
    refreshFinale();

    if (axis != null) {
      axis.deleteInZones(this, start, end);
    }
    isDirty = true;
  }

  @Override public void insert(N target, N count) {
    cellWidth.insert(target, count);
    lineWidth.insert(target, count);
    resizable.insert(target, count);
    moveable.insert(target, count);
    hideable.insert(target, count);
    hidden.insert(target, count);
    for (NumberSet<N> set: hiddenSets) {
      set.insert(target, count);
    }
    buried.insert(target, count);
    order.insert(target, count);
    parents.insert(target, count);
    expanded.insert(target, count);
    selection.insert(target, count);
    lastSelection.insert(target, count);
    this.count = math.create(this.count).add(count).getValue();
    refreshFinale();

    if (axis != null) {
      axis.insertInZones(this, target, count);
    }
    isDirty = true;
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


  @Override
  public void addHiddenSet(final NumberSet<N> set) {
    hiddenSets.add(set);
    hidden.addAll(set);

    // Listen to content changes in the added set
    ContentChangeListener<N> listener = new NumberSetCore.ContentChangeListener<N>() {
      public void handle(NumberSetCore.ContentChangeEvent<N> e) {
        if (e.operation == NumberSetCore.ContentChangeEvent.ADD) {
          hidden.add(e.start, e.end);
        }
        else {
          hidden.remove(e.start, e.end);
          // Ensure this extent or part of it is still hidden
          // if it was hidden in any other set
          for (NumberSet<N> set2: hiddenSets) {
            if (set2 == set) continue;
            hidden.addAll(set2);
          }
        }
      }
    };
    hiddenSetListeners.put(set, listener);
    if (set instanceof NumberSetCore) {
      ((NumberSetCore<N>) set).addListener(listener);
    }
    isDirty = true;
  }

  @Override
  public void removeHiddenSet(NumberSet<N> set) {
    hidden.removeAll(set);
    hiddenSets.remove(set);
    if (set instanceof NumberSetCore) {
      ((NumberSetCore<N>) set).removeListener(hiddenSetListeners.remove(set));
    }
    isDirty = true;
  }

  /*------------------------------------------------------------------------
   * Non public
   */

  N getVisibleCount() {
    return math.create(count).subtract(hidden.getMutableCount()).getValue();
  }

  @Nullable
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

  ExtentSequence2<N> getSelectedExtentSequence() {
    return new ExtentSequence2<N>(selection.items);
  }


  class IndexIterator extends ImmutableIterator<N> {

    private final NumberSequence2<N> seq;

    IndexIterator(NumberSequence2<N> seq) {
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



  @Override
  public void setParent(N child, N parent) {
    setParent(child, child, parent);
  }

  @Override
  public void setParent(N start, N end, N parent) {
    if (parent != null) {
      N next = order.getIndexByOffset(parent, math.increment(parents.getValueIndexCount(parent)));
      if (next == null) next = getCount();
      order.move(start, end, next);
    }
    parents.setValue(start, end, parent);
    collapse(parent, parent);
    isDirty = true;
}

  /**
   * Can return null
   * @param parent
   * @return
   */
  @Override
  public Iterator<Extent<N>> getChildrenExtents(N parent) {
    final Iterator<MutableExtent<N>> it = parents.getValueExtents(parent).iterator();
    return new ImmutableIterator<Extent<N>>() {
      @Override
      public boolean hasNext() {
        return it.hasNext();
      }

      @Override
      public Extent<N> next() {
        MutableExtent<N> next = it.next();
        return Extent.createUnchecked(next.start.getValue(), next.end.getValue());
      }
    };
  }

  @Override
  public Iterator<N> getChildren(N parent) {
    return Extent.numberIterator(math, parents.getValueExtents(parent));
  }

  @Override
  public boolean hasChildren(N parent) {
    return !parents.getValueExtents(parent).isEmpty();
  }

  @Override
  public N getChildrenCount(N parent) {
    return parents.getValueIndexCount(parent);
  }
  @Override
  public N getParent(N index) {
    return parents.getValue(index);
  }

  @Override
  public N getLevelInTree(N index) {
    MutableNumber<N> count = math.create(-1);
    N parent = index;
    do {
      parent = getParent(parent);
      count.increment();
    }
    while (parent != null);

    return count.getValue();
  }

  @Override
  public void setExpanded(N parent, boolean state) {
    if (parent == null) {
      for (Iterator<Extent<N>> it = getChildrenExtents(null); it.hasNext();) {
        Extent<N> extent = it.next();
        setExpanded(extent.start, extent.end, state);
      }
    }
    else {
      setExpanded(parent, parent, state);
    }
  }

  @Override
  public void setExpanded(N start, N end, boolean state) {
    if (state == true) {
      expanded.add(start, end);
    }
    else {
      expanded.remove(start, end);
    }

    collapse(start, end);
    isDirty = true;
  }


  DirectionIndexSequence<N> numbers(final N start, N end) {
    Forward<N> seq = new DirectionIndexSequence.Forward<N>(this);

    return seq;
  }


  private void collapse(N start, N end) {
    MutableNumber<N> index = math.create(start);
    for (; math.compare(index, end) <= 0; index.increment()) {
      N parent = index.getValue();
      boolean isExpanded = expanded.contains(parent);
      boolean isBurried = buried.contains(parent);
      for (Iterator<Extent<N>> it = getChildrenExtents(parent); it.hasNext();) {
        Extent<N> extent = it.next();
        if (isExpanded && !isBurried) {
          buried.remove(extent.start, extent.end);
        }
        else {
          buried.add(extent.start, extent.end);
        }
        collapse(extent.start, extent.end);
      }
    }
    isDirty = true;
  }

  @Override
  public boolean isExpanded(N index) {
    return expanded.contains(index);
  }

  boolean isBuried(N index) {
    return buried.contains(index);
  }

  private void checkIndex(N index, N limit, String name) {
    Preconditions.checkNotNullWithName(index, name);
    checkIndexClass(index, name);
    if (math.compare(index, math.ZERO_VALUE()) < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(
          "{0} ({1}) cannot be negative", name, index)) ;
    }
    if (math.compare(index, limit) >= 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(
          "{0} ({1}) must be lower then limit {2}", name, index, limit))  ;
    }
  }

  public void checkIndexClass(N index, String name) {
    if (getIndexClass() != index.getClass()) {
      throw new IndexOutOfBoundsException(MessageFormat.format(
          "section indexing class ({0}) must be the same as the class of {2} ({1})",
          getIndexClass(), index.getClass(), name)) ;
    }
  }

  void addSelectionEvent() {
    Event event = new Event();
    event.type = SWT.Selection;
    event.widget = axis.matrix;
    listeners.add(event);
  }

  private void refreshFinale() {
    //	  finale.clear();
    //	  for (MutableExtent<N> extent: order.items) {
    //	    finale.items.add(extent.copy());
    //	  }
    //	  for (MutableExtent<N> extent: hidden.items) {
    //	    finale.remove(extent);
    //	  }
  }

  static <N2 extends Number> SectionCore<N2> from(AxisItem<N2> item) {
    return item.section;
  }

  static <N2 extends Number> SectionCore<N2> from(Section<N2> section) {
    return (SectionCore<N2>) section.getUnchecked();
  }





  //	@Override
  //  public boolean isMerged(N index) {
  //    return merged.contains(index);
  //  }


}
