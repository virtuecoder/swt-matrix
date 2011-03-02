package pl.netanel.swt.matrix.state;

import pl.netanel.util.Preconditions;


/**
 * Contains the application state of an axis section.
 * 
 * @author Jacek Kolodziejczyk created 01-03-2011
 */
public class Section {

	static final int DEFAULT_CELL_WIDTH = 10;
	static final int DEFAULT_LINE_WIDTH = 1;
	
	private final Math math;
	private final MutableNumber count;
	private final NumberOrder order;
	private final NumberSet hidden;
	private final NumberSet resizable;
	private final NumberSet moveable;
	private final NumberSet hideable;
	private final IntAxisState cellWidth;
	private final IntAxisState lineWidth;
	
	private final NumberQueueSet selection;
	private final NumberQueueSet lastSelection;

	
	private boolean defaultResizable, defaultMoveable, defaultHideable; 
	private boolean isNavigationEnabled, isVisible;
	
	
	public Section(Class<? extends Number> numberClass) {
		super();
		Preconditions.checkNotNullWithName(numberClass, "numberClass");
		math = Math.getInstance(numberClass);
		count = math.create(0);
		
		order = new NumberOrder(math);
		hidden = new NumberSet(math);
		resizable = new NumberSet(math);
		moveable = new NumberSet(math);
		hideable = new NumberSet(math);
		
		cellWidth = new IntAxisState(math, DEFAULT_CELL_WIDTH);
		lineWidth = new IntAxisState(math, DEFAULT_LINE_WIDTH);
		
		selection = new NumberQueueSet(math);
		lastSelection = new NumberQueueSet(math);
	}
	
	
	/*------------------------------------------------------------------------
	 * Primary methods 
	 */

	public Number getCount() {
		return count;
	}

	public void setCount(MutableNumber count) {
//		Preconditions.checkNotNull(count);
//		Preconditions.checkArgument(math.compare(count, math.ZERO()) >= 0,
//			"Item count must be greater or equal to zero");
		
		this.count.set(count);
		order.setCount(count);
	}

	public boolean isDefaultResizable() {
		return defaultResizable;
	}

	public void setDefaultResizable(boolean defaultResizable) {
		this.defaultResizable = defaultResizable;
	}

	public boolean isDefaultMoveable() {
		return defaultMoveable;
	}

	public void setDefaultMoveable(boolean defaultMoveable) {
		this.defaultMoveable = defaultMoveable;
	}

	public boolean isDefaultHideable() {
		return defaultHideable;
	}

	public void setDefaultHideable(boolean defaultHideable) {
		this.defaultHideable = defaultHideable;
	}
	
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	public boolean isVisible() {
		return isVisible;
	}

	public boolean isNavigationEnabled() {
		return isNavigationEnabled;
	}

	public void setNavigationEnabled(boolean isCurrentMarkerEnabled) {
		this.isNavigationEnabled = isCurrentMarkerEnabled;
	}

	public void move(MutableNumber start, MutableNumber end, MutableNumber target) {
		order.move(start, end, target);
	}
	
	public void setHidden(MutableNumber start, MutableNumber end, boolean flag) {
		hidden.change(start, end, flag);
	}
	
	public boolean isHidden(MutableNumber n) {
		return hidden.contains(n);
	}
	
	public void setMoveable(MutableNumber start, MutableNumber end, boolean flag) {
		moveable.change(start, end, flag != defaultMoveable);
	}
	
	public boolean isMoveable(MutableNumber n) {
		return moveable.contains(n) != defaultMoveable;
	}
	
	public void setResizable(MutableNumber start, MutableNumber end, boolean flag) {
		resizable.change(start, end, flag != defaultResizable);
	}
	
	public boolean isResizable(MutableNumber n) {
		return resizable.contains(n) != defaultResizable;
	}
	
	public void setHideable(MutableNumber start, MutableNumber end, boolean flag) {
		hideable.change(start, end, flag != defaultHideable);
	}
	
	public boolean isHideable(MutableNumber n) {
		return hideable.contains(n) != defaultHideable;
	}
	
	public void setSelected(MutableNumber start, MutableNumber end, boolean flag) {
		selection.change(start, end, flag);
	}
	
	public boolean isSelected(MutableNumber n) {
		return selection.contains(n);
	}
	
	
	public void setCellWidth(MutableNumber start, MutableNumber end, int width) {
		cellWidth.setValue(start, end, width);
	}
	
	public int getCellWidth(MutableNumber n) {
		return cellWidth.getValue(n);
	}
	
	public void setLineWidth(MutableNumber start, MutableNumber end, int width) {
		lineWidth.setValue(start, end, width);
	}
	
	public int getLineWidth(MutableNumber n) {
		return lineWidth.getValue(n);
	}
	
	public void backupSelection() {
		lastSelection.replace(selection);
	}
	
	public void restoreSelection() {
		selection.replace(lastSelection);
	}
	                               
	
	
	/*------------------------------------------------------------------------
	 * Derived methods
	 */
	
	public void setCount(Number count) {
		setCount(math.getMutable(count));
	}

	public void move(Number start, Number end, Number target) {
		order.move(math.getMutable(start), math.getMutable(end), math.getMutable(target));
	}
	
	public void setHidden(Number start, Number end, boolean flag) {
		hidden.change(math.getMutable(start), math.getMutable(end), flag);
	}
	
	public boolean isHidden(Number n) {
		return hidden.contains(math.getMutable(n));
	}
	
	public void setMoveable(Number start, Number end, boolean flag) {
		moveable.change(math.getMutable(start), math.getMutable(end), flag != defaultMoveable);
	}
	
	public boolean isMoveable(Number n) {
		return moveable.contains(math.getMutable(n));
	}
	
	public void setResizable(Number start, Number end, boolean flag) {
		resizable.change(math.getMutable(start), math.getMutable(end), flag != defaultResizable);
	}
	
	public boolean isResizable(Number n) {
		return resizable.contains(math.getMutable(n));
	}
	
	public void setHideable(Number start, Number end, boolean flag) {
		hideable.change(math.getMutable(start), math.getMutable(end), flag != defaultHideable);
	}
	
	public boolean isHideable(Number n) {
		return hideable.contains(math.getMutable(n));
	}
	
	public void setSelected(Number start, Number end, boolean flag) {
		selection.change(math.getMutable(start), math.getMutable(end), flag);
	}
	
	public boolean isSelected(Number n) {
		return selection.contains(math.getMutable(n));
	}

	
	public void setCellWidth(Number start, Number end, int width) {
		cellWidth.setValue(math.getMutable(start), math.getMutable(end), width);
	}
	
	public int getCellWidth(Number n) {
		return cellWidth.getValue(math.getMutable(n));
	}
	
	public void setLineWidth(Number start, Number end, int width) {
		lineWidth.setValue(math.getMutable(start), math.getMutable(end), width);
	}

	public int getLineWidth(Number n) {
		return lineWidth.getValue(math.getMutable(n));
	}
	

}
