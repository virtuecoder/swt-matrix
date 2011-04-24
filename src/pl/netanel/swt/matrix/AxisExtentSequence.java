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
			AxisItem.create(sections.get(0), math.ZERO_VALUE()), 
			AxisItem.create(section2, math.decrement(section2.getCount())));
	}
	void init(AxisItem startItem, AxisItem endItem) {
		this.startItemIndex = sections.indexOf(startItem.getSection());
		this.endItemIndex = sections.indexOf(endItem.getSection());
		Section sl;
		sl = startItem.getSection();
		istart = sl.order.items.isEmpty() ? 0 : sl.order
				.getExtentIndex(startItem.getIndex());
		sl = endItem.getSection();
		iend = sl.order.items.isEmpty() ? 0 : sl.order
				.getExtentIndex(endItem.getIndex());

		Section section = startItem.getSection();
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
		start = sectionIndex == startItemIndex && i == istart ? startItem.getIndex()
				: e.start();
		end = sectionIndex == endItemIndex && i == iend ? endItem.getIndex() : e
				.end();
		if (i >= iend && math.compare(end, endItem.getIndex()) == 0) {
			i = items.size();
		}
		i++;
		return true;
	}
}