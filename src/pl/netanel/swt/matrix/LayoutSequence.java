package pl.netanel.swt.matrix;

import java.util.List;

class LayoutSequence<N extends Number> {

  private final List<AxisItem<N>> items;
	private final List<Bound> bounds;
	private final SectionCore<N>section;
	private int i;
	Bound bound;
	AxisItem<N> item;

	public LayoutSequence(List<AxisItem<N>> items, List<Bound> bounds, SectionCore<N> section) { 
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
		if (i >= bounds.size()) return false;
		Section<N> section2 = items.get(i).section;
		if (section2 != section) {
			// Make sure last line is included between sections  
			if (items.size() == bounds.size() /*&& 
				axis.getZIndex(section2) < axis.getZIndex(item.section)*/) 
			{
				item = AxisItem.create(item.section, section.math.increment(item.getIndex()));
				bound = bounds.get(i);
				i = bounds.size();
				return true;
			}
			return false;
		}
		bound = bounds.get(i);
		item = items.get(i++);
		return true;
	}
	
	public AxisItem<N> getItem() {
		return item;
	}
	
	public int getDistance() {
		return bound.distance;
	}
	
	public int getWidth() {
		return bound.width;
	}

	public N getIndex() {
		return item == null ? null : item.getIndex();
	}
}