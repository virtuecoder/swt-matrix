/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import java.util.List;

class AxisLayoutSequence<N extends Number> implements Sequence {

  private final List<AxisItem<N>> items;
	private final List<Bound> bounds;
	private final SectionCore<N>section;
	private int i;
	Bound bound;
	N index;
  private boolean more;

	public AxisLayoutSequence(List<AxisItem<N>> items, List<Bound> bounds, SectionCore<N> section) {
    this.items = items;
		this.bounds = bounds;
		this.section = section;
	}

	public void init() {
		for (i = 0; i < items.size(); i++) {
			if (items.get(i).section.equals(section)) break;
		}
	}

	public boolean next() {
		if (i >= bounds.size()) return more = false;
		Section<N> section2 = items.get(i).section;
		if (section2 != section) {
			// Make sure last line is included between sections
			if (items.size() == bounds.size() /*&&
				axis.getZIndex(section2) < axis.getZIndex(item.section)*/)
			{
				index = section.math.increment(index);
				bound = bounds.get(i);
				i = bounds.size();
				return more = true;
			}
			return more = false;
		}
		bound = bounds.get(i);
		index = items.get(i++).index;
		return more = true;
	}

	public boolean over() {
	  return !more;
	}

	public int getDistance() {
		return bound.distance;
	}

	public int getWidth() {
		return bound.width;
	}

	public N getIndex() {
		return index;
	}
}