package pl.netanel.swt.matrix;

import java.util.ArrayList;
import java.util.List;

class AxisExtentSequence<N extends Number> {
	List<Section<N>> sections;
	Section section;
	int sectionIndex;
	Number start, end;
	private int startItemIndex, endItemIndex, i, istart, iend;
	private ArrayList<Extent<N>> items;
	private AxisItem startItem, endItem;
	private final Math math;
	
	public AxisExtentSequence(Math<N> math, List<Section<N>> sections) {
		super();
		this.math = math;
		this.sections = sections;
	}

	void init() {
		Section section2 = sections.get(sections.size() - 1);
		init(
			new AxisItem(sections.get(0), math.ZERO_VALUE()), 
			new AxisItem(section2, math.decrement(section2.getCount())));
	}
	void init(AxisItem startItem, AxisItem endItem) {
		this.startItemIndex = sections.indexOf(startItem.section);
		this.endItemIndex = sections.indexOf(endItem.section);
		Section sl;
		sl = startItem.section;
		istart = sl.order.items.isEmpty() ? 0 : sl.order
				.getExtentIndex(startItem.index);
		sl = endItem.section;
		iend = sl.order.items.isEmpty() ? 0 : sl.order
				.getExtentIndex(endItem.index);

		Section section = startItem.section;
		sectionIndex = sections.indexOf(section);
		items = section.order.items;
		i = istart;
	}

	boolean next() {
		if (i >= items.size()) {
			sectionIndex++;
			if (sectionIndex > endItemIndex)
				return false;
			items = sections.get(sectionIndex).order.items;
			i = 0;
		}
		Extent e = items.get(i);
		start = sectionIndex == startItemIndex && i == istart ? startItem.index
				: e.start();
		end = sectionIndex == endItemIndex && i == iend ? endItem.index : e
				.end();
		if (i >= iend && math.compare(end, endItem.index) == 0) {
			i = items.size();
		}
		i++;
		return true;
	}
}