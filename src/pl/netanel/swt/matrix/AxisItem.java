package pl.netanel.swt.matrix;

import pl.netanel.util.Preconditions;

/**
 * Represents an immutable position at axis characterized by a section and
 * an index within that section.
 * @param <N> specifies the indexing class for this axis
 * @author Jacek Kolodziejczyk created 21-04-2011
 */
public class AxisItem<N extends Number> {
	SectionCore<N> section;
	N index;
	
	/**
	 * Constructs axis item in the specified section and at the specified index.
	 * @param section
	 * @param index
	 * @throws IllegalArgumentException if the section is <code>null</code>
	 * or index is <code>null</code>
	 * @throws IndexOutOfBoundsException if index is out of 0 ... {@link #getCount()}-1 bounds
	 */
	public AxisItem(Section<N> section, N index) {
		Preconditions.checkNotNullWithName(section, "section");
		Preconditions.checkNotNullWithName(index, "index");
		SectionCore<N> section2 = SectionCore.from(section);
		section2.checkLineIndex(index, "index");
		this.section = section2;
		this.index = index;
	}
	
	
	private AxisItem() {}
	
	static <N2 extends Number> AxisItem<N2> create(SectionCore<N2> section, N2 index) {
		AxisItem<N2> axisItem = new AxisItem<N2>();
		axisItem.section = section;
		axisItem.index = index;
		return axisItem;
	}
	
	
	@Override
	public String toString() {
		return "" + section + " " + index;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof AxisItem)) return false;
		@SuppressWarnings("unchecked") AxisItem<N> item = (AxisItem<N>) o;
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
	  return section.client;
	}

	/**
	 * Return index of this axis item.
	 * @return index of this axis item
	 */
	public N getIndex() {
		return index;
	}
	
}