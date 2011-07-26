package pl.netanel.swt.matrix;

import java.util.List;

class LayoutSequence<N extends Number> {

  private final List<AxisItem> items;
	private final List<Bound> bounds;
	private final SectionCore section;
	private int i;
	Bound bound;
	AxisItem<N> item;

	public LayoutSequence(List<AxisItem> items, List<Bound> bounds, SectionCore section) { 
    this.items = items;
		this.bounds = bounds;
		this.section = section;
	}

	public void init() {
		for (i = 0; i < items.size(); i++) {
			if (items.get(i).section.core.equals(section)) break;
		}
	}
	
	public boolean next() {
		if (i >= bounds.size()) return false;
		Section section2 = items.get(i).section.getCore();
		if (section2 != section) {
			// Make sure last line is included between sections  
			if (items.size() == bounds.size() /*&& 
				axis.getZIndex(section2) < axis.getZIndex(item.section)*/) 
			{
				item = AxisItem.create((SectionClient) item.getSection(), section.math.increment(item.getIndex()));
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
	
	public AxisItem getItem() {
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