package pl.netanel.swt.matrix;

import pl.netanel.util.Preconditions;

/**
 * Represents an immutable item of an axis and is characterized by a section and
 * an {@link MutableNumber} describing the position of the item the section.
 * <p> 
 * Only visible items are cached in the memory. They are created on demand 
 * when the axis layout is computed.
 * @param <N> defines the indexing class for this axis
 * @author Jacek Kolodziejczyk created 21-04-2011
 */
public class AxisItem<N extends Number> {
	private Section<N> section;
	private N index;
	
	/**
	 * Constructs axis item in the specified section and at the specified index.
	 * @param section
	 * @param index
	 * @throws IllegalArgumentException if the section is <code>null</code>
	 * or index is <code>null</code>
	 * @throws IndexOutOfBoundsException if index is out of 0 ... {@link #getCount()}-1 bounds
	 */
	AxisItem(Section<N> section, N index) {
		if (section instanceof SectionClient) section = ((SectionClient) section).core;
		Preconditions.checkNotNullWithName(section, "section");
		Preconditions.checkNotNullWithName(index, "index");
		section.checkIndex(index, section.math.increment(section.getCount()), "index");
		this.section = section;
		this.index = index;
	}
	
	private AxisItem() {}
	
	static AxisItem create(Section section, Number index) {
		AxisItem axisItem = new AxisItem();
		axisItem.section = section;
		axisItem.index = index;
		return axisItem;
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

	/**
	 * Returns section of this axis item.
	 * @return section of this axis item
	 */
	public Section<N> getSection() {
	  return section;
	}

	/**
	 * Return index of this axis item.
	 * @return index of this axis item
	 */
	public N getIndex() {
		return index;
	}
	
}