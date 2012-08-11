/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import pl.netanel.util.Preconditions;

/**
 * Represents an axis item characterized by a section and
 * an index within that section.
 * Instances of this class are immutable.
 * 
 * @param <N> specifies the indexing class for the axis
 * 
 * @author Jacek Kolodziejczyk created 21-04-2011
 */
public class AxisItem<N extends Number> {
	final SectionCore<N> section;
	final N index;
	
	/**
	 * Creates a new instance of axis item. Arguments are validated.
	 * 
	 * @param section section of an axis
	 * @param index item index in the section
	 * @return a new instance of this class
	 * 
	 * @throws IllegalArgumentException if section or index is null
   * @throws IndexOutOfBoundsException if index is out of 0 ...
   *         {@link #getCount()} bounds
	 */
	public static <N2 extends Number> AxisItem<N2> create(Section<N2> section, N2 index) {
	  Preconditions.checkNotNullWithName(section, "section");
	  SectionCore<N2> section2 = SectionCore.from(section);
	  section2.checkLineIndex(index, "index");
    return new AxisItem<N2>(section2, index);
	}
	
	/**
	 * Creates a new instance of cell without checking arguments validity.
	 * 
	 * @param section section of an axis
	 * @param index item index in the section
	 * @return a new instance of this class
	 * @return
	 */
	public static <N2 extends Number> AxisItem<N2> createUnchecked(Section<N2> section, N2 index) {
		return new AxisItem<N2>(SectionCore.from(section), index);
	}
	
	static <N2 extends Number> AxisItem<N2> createInternal(SectionCore<N2> section, N2 index) {
	  return new AxisItem<N2>(section, index);
	}
	
	/**
	 * Constructs axis item in the specified section and at the specified index.
	 * @param section
	 * @param index
	 * @throws IllegalArgumentException if the section is <code>null</code>
	 * or index is <code>null</code>
	 * @throws IndexOutOfBoundsException if index is out of 0 ... {@link #getCount()}-1 bounds
	 */
	private AxisItem(SectionCore<N> section, N index) {
	  Preconditions.checkNotNullWithName(section, "section");
	  Preconditions.checkNotNullWithName(index, "index");
	  SectionCore<N> section2 = SectionCore.from(section);
	  section2.checkLineIndex(index, "index");
	  this.section = section2;
	  this.index = index;
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
	
	
	@Override
	public String toString() {
	  return "" + section + ":" + index;
	}
	
	
	@Override
  public boolean equals(Object obj) {
    if (this == obj) { return true; }
    if (obj == null) { return false; }
    if (!(obj instanceof AxisItem)) { return false; }
    @SuppressWarnings("rawtypes")
    AxisItem other = (AxisItem) obj;
    if (other.index.getClass() != index.getClass()) return false;
    else if (!index.equals(other.index)) { return false; }
    else if (!section.equals(other.section)) { return false; }
    return true;
  }

	@Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((index == null) ? 0 : index.hashCode());
    result = prime * result + ((section == null) ? 0 : section.hashCode());
    return result;
  }
}