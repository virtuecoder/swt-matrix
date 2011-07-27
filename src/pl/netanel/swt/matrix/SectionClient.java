package pl.netanel.swt.matrix;

import java.util.Iterator;

import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionListener;

import pl.netanel.util.Preconditions;


class SectionClient<N extends Number> implements Section<N> {
	SectionCore<N> core;

  /**
   * Constructs a section indexed by the given sub-class of {@link Number}.
   * 
   * @param numberClass defines the class used for indexing
   */
  public SectionClient(Class<N> numberClass) {
    core = new SectionCore<N>(numberClass);
    core.client = this;
  }

	public SectionClient(SectionCore<N> section) {
	  core = section;
	  core.client = this;
	}

  /**
	 * Indicates whether some other object is "equal to" this one.
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Section))
			return false;
		if (obj instanceof SectionClient)
			obj = ((SectionClient) obj).core;
		return core.equals(obj);
	}

	/**
	 * Returns the section position on the axis.
	 */
	@Override
	public String toString() {
		return core.toString();
	}

	@Override public SectionCore getCore() {
	  return core;
	}

  @Override public Class getIndexClass() {
    return core.getIndexClass();
  };
	
	/**
	 * Specifies the number of section items.
	 * 
	 * @param count the new count of the receiver's items
	 * @see #getCount()
	 */
	@Override public void setCount(N count) {
	  Preconditions.checkNotNullWithName(count, "count");
	  Preconditions.checkArgument(core.math.compare(count, core.math.ZERO_VALUE()) >= 0, 
	    "count must be lower then 0");
		core.setCount(count);
	}

	/**
	 * Returns the number of sections items.
	 * 
	 * @return the number of sections items.
	 * @see #setCount(Number)
	 */
	@Override public N getCount() {
		return core.count;
	}

	/**
	 * Returns <code>true</code> if the receiver contains contains no items.
	 * Otherwise <code>false</code> is returned.
	 * 
	 * @return <code>true</code> if the receiver contains contains no items
	 */
	@Override public boolean isEmpty() {
		return core.isEmpty();
	}

	/*------------------------------------------------------------------------
	 * Section properties
	 */

	/**
	 * Marks the receiver as visible if the argument is <code>true</code>, and
	 * marks it invisible otherwise.
	 * 
	 * @param visible the new visibility state
	 */
	@Override public void setVisible(boolean visible) {
		core.setVisible(visible);
	}

	/**
	 * Returns <code>true</code> if the receiver is visible. Otherwise,
	 * <code>false</code> is returned.
	 * 
	 * @return the receiver's visibility state
	 */
	@Override public boolean isVisible() {
		return core.isVisible();
	}

	/**
	 * Enables current item navigation in the receiver if the argument is
	 * <code>true</code>, and disables it invisible otherwise.
	 * 
	 * @param enabled
	 *            the new visibility state
	 */
	public void setNavigationEnabled(boolean enabled) {
		core.setFocusItemEnabled(enabled);
	}

	/**
	 * Returns <code>true</code> if the current item navigation is enabled in
	 * the receiver. Otherwise, <code>false</code> is returned.
	 * 
	 * @return the receiver's visibility state
	 */
	@Override public boolean isFocusItemEnabled() {
		return core.isFocusItemEnabled();
	}

	
	/*------------------------------------------------------------------------
	 * Default values 
	 */

	@Override public void setDefaultCellWidth(int width) {
		if (width < 0) return;
		core.setDefaultCellWidth(width);
	}

	/**
	 * Returns the default cell width of the receiver's items.
	 * 
	 * @return default width of cells in this section.
	 */
	@Override public int getDefaultCellWidth() {
		return core.getDefaultCellWidth();
	}

	@Override public void setDefaultLineWidth(int width) {
		if (width < 0) return;
		core.setDefaultLineWidth(width);
	}

	@Override public int getDefaultLineWidth() {
		return core.getDefaultLineWidth();
	}

	@Override public boolean isDefaultResizable() {
		return core.isDefaultResizable();
	}

	@Override public void setDefaultResizable(boolean resizable) {
		core.setDefaultResizable(resizable);
	}

	@Override public boolean isDefaultMoveable() {
		return core.isDefaultMoveable();
	}

	@Override public void setDefaultMoveable(boolean moveable) {
		core.setDefaultMoveable(moveable);
	}

	@Override public boolean isDefaultHideable() {
		return core.isDefaultHideable();
	}

	@Override public void setDefaultHideable(boolean hideable) {
		core.setDefaultHideable(hideable);
	}

	/*------------------------------------------------------------------------
	 * Item properties 
	 */

	@Override public void setLineWidth(N start, N end, int width) {
		if (width < 0) return;
		checkRange(start, end, core.math.increment(core.count));
		core.setLineWidth(start, end, width);
	}
	
  @Override public void setLineWidth(N index, int width) {
    if (width < 0) return;
    checkCellIndex(index, "index");    
    core.setLineWidth(index, width);
  }

	@Override public int getLineWidth(N index) {
		checkLineIndex(index,  "index");
		return core.getLineWidth(index);
	}

	@Override public void setCellWidth(N start, N end, int width) {
		if (width < 0) return;
		checkRange(start, end, core.count);
		core.setCellWidth(start, end, width);
	}

	@Override public int getCellWidth(N index) {
		checkCellIndex(index, "index");
		return core.getCellWidth(index);
	}

	@Override public void setMoveable(N start, N end, boolean enabled) {
		checkRange(start, end, core.count);
		core.setMoveable(start, end, enabled);
	}
	
  @Override public void setMoveable(N index, boolean enabled) {
    checkCellIndex(index, "index");
    core.setMoveable(index, enabled);
  }

	@Override public boolean isMoveable(N index) {
		checkCellIndex(index, "index");
		return core.isMoveable(index);
	}

	@Override public void setResizable(N start, N end, boolean enabled) {
		checkRange(start, end, core.count);
		core.setResizable(start, end, enabled);
	}

	@Override public boolean isResizable(N index) {
		checkCellIndex(index, "index");
		return core.isResizable(index);
	}

	@Override public void setHideable(N start, N end, boolean enabled) {
		checkRange(start, end, core.count);
		core.setHideable(start, end, enabled);
	}

	@Override public boolean isHideable(N index) {
		checkCellIndex(index, "index");
		return core.isHideable(index);
	}

	/*------------------------------------------------------------------------
	 * Hiding 
	 */


	@Override public void setHidden(N start, N end, boolean state) {
		checkRange(start, end, core.count);
		core.setHidden(start, end, state);
	}

	@Override public boolean isHidden(N index) {
		checkCellIndex(index, "index");
		return core.isHidden(index);
	}

	@Override public N getHiddenCount() {
		return core.getHiddenCount();
	}

	@Override public Iterator getHidden() {
		return core.getHidden();
	}

	/*------------------------------------------------------------------------
	 * Selection 
	 */

	
	@Override public void setSelected(N start, N end, boolean state) {
		checkRange(start, end, core.count);
		core.setSelected(start, end, state);
	}

	@Override public void setSelected(boolean state) {
    core.setSelected(state);
  }
	
	@Override public boolean isSelected(N index) {
		checkCellIndex(index, "index");
		return core.isSelected(index);
	}

	@Override public N getSelectedCount() {
		return core.getSelectedCount();
	}

	@Override
	public Iterator<Number[]> getSelectedExtentIterator() {
		return core.getSelectedExtentIterator();
	}

	@Override
	public Iterator<N> getSelectedIterator() {
		return core.getSelectedIterator();
	}

	@Override
	public void addControlListener(ControlListener listener) {
		core.addControlListener(listener);
	}

	@Override
	public void addSelectionListener(SelectionListener listener) {
		core.addSelectionListener(listener);
	}

	@Override
	public void removeControlListener(ControlListener listener) {
		core.removeControlListener(listener);
	}

	@Override
	public void removeSelectionListener(SelectionListener listener) {
		core.removeSelectionListener(listener);
	}

	/*------------------------------------------------------------------------
	 * Moving 
	 */

	@Override public void move(N start, N end, N target) {
		checkRange(start, end, core.count);
		checkLineIndex(target, "target");
		core.move(start, end, target);
	}

	@Override public void delete(N start, N end) {
		checkRange(start, end, core.count);
		core.delete(start, end);
	}

	@Override public void insert(N target, N count) {
	  checkLineIndex(target, "target");
		Preconditions.checkNotNullWithName(count, "count");
		core.insert(target, count);
	}

	@Override public int hashCode() {
		return core.hashCode();
	}

	@Override public N get(N position) {
	  checkCellIndex(position, "position");
		return core.get(position);
	}

	@Override public N indexOf(N item) {
	  checkCellIndex(item, "item");
		return core.indexOf(item);
	}

	@Override public N indexOfNotHidden(N item) {
	  checkCellIndex(item, "item");
		return core.indexOfNotHidden(item);
	}

	@Override public void setFocusItemEnabled(boolean enabled) {
		core.setFocusItemEnabled(enabled);
	}

	@Override public void setCellWidth(N index, int width) {
	  checkCellIndex(index, "index");
		core.setCellWidth(index, width);
	}

	@Override public void setResizable(N index, boolean enabled) {
	  checkCellIndex(index, "index");
		core.setResizable(index, enabled);
	}

	@Override public void setHideable(N index, boolean enabled) {
	  checkCellIndex(index, "index");
		core.setHideable(index, enabled);
	}

	@Override public void setHidden(N index, boolean state) {
	  checkCellIndex(index, "index");
		core.setHidden(index, state);
	}

	@Override public void setHiddenSelected(boolean state) {
		core.setHiddenSelected(state);
	}

	@Override public N getHiddenCount(N start, N end) {
	  checkRange(start, end, getCount());
		return core.getHiddenCount(start, end);
	}

	@Override public void setSelected(N index, boolean state) {
	  checkCellIndex(index, "index");
	  core.setSelected(index, state);
	}

	/*------------------------------------------------------------------------
	 * Non public methods 
	 */

	 /**
   * Checks the validity of the given cell index using the name to indicate
   * which parameter is wrong.
   * 
   * @param index index to validate
   * @param name indicates the parameter
   * @throws IllegalArgumentException if the index is lower the 0 or greater
   *           then the number of cells in this section
   */
	public void checkCellIndex(N index, String name) {
    core.checkCellIndex(index, name);
  }

	 /**
   * Checks the validity of the given line index using the name to indicate
   * which parameter is wrong.
   * 
   * @param index index to validate
   * @param name indicates the parameter
   * @throws IllegalArgumentException if the index is lower the 0 or greater
   *           then the number of lines in this section
   */
  public void checkLineIndex(N index, String name) {
    core.checkLineIndex(index, name);
  }

	protected void checkRange(N start, N end, N limit) {
		core.checkRange(start, end, limit);
	};
	
}
