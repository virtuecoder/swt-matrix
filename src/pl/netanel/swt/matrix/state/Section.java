package pl.netanel.swt.matrix.state;

import pl.netanel.util.Preconditions;


/**
 * Contains the application state of an axis section.
 * 
 * @author Jacek Kolodziejczyk created 01-03-2011
 */
public class Section {

	MutableNumber itemCount; 
	boolean defaultResizable, defaultMoveable, defaultHideable; 
	boolean isNavigationEnabled, isVisible;
	Math<? extends Number> math;
	
	
	public Section(Class<? extends Number> numberClass) {
		super();
		Preconditions.checkNotNullWithName(numberClass, "numberClass");
		math = Math.get(numberClass);
		itemCount = math.create(0);
	}
	
	
	/*------------------------------------------------------------------------
	 * Primary methods 
	 */

	public Number getItemCount() {
		return itemCount;
	}

	public void setItemCount(Number itemCount) {
		this.itemCount.set(itemCount);
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

	public boolean isNavigationEnabled() {
		return isNavigationEnabled;
	}

	public void setNavigationEnabled(boolean isCurrentMarkerEnabled) {
		this.isNavigationEnabled = isCurrentMarkerEnabled;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
}
