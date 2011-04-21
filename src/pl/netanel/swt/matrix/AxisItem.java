package pl.netanel.swt.matrix;

import pl.netanel.util.Preconditions;

/**
 * Represents an immutable item of an axis and is characterized by a section and
 * an {@link MutableNumber} describing the position of the item the section.
 * <p> 
 * Only visible items are cached in the memory. They are created on demand 
 * when the axis layout is computed.
 */
class AxisItem<N extends Number> {
	final public Section<N> section;
	final public N index;
	
	public AxisItem(Section<N> section, N index) {
		Preconditions.checkNotNullWithName(index, "index");
		this.section = section;
		this.index = index;
	}
	@Override
	public String toString() {
		return "" + section.index + " " + index;
	}
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof AxisItem)) return false;
		AxisItem item = (AxisItem) o;
		return item.section.equals(section) && item.index.equals(index);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index.hashCode();
		result = prime * result + section.hashCode();
		return result;
	}
	
}