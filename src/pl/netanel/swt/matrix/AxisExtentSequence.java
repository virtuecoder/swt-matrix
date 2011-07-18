package pl.netanel.swt.matrix;

import java.util.ArrayList;
import java.util.List;

class AxisExtentSequence<N extends Number> {
	List<SectionCore<N>> sections;
	SectionCore section;
	int sectionIndex;
	Number start, end;
	private int startItemIndex, endItemIndex, i, istart, iend;
	private ArrayList<Extent<N>> items;
	private AxisItem startItem, endItem;
	private final Math math;
	
	public AxisExtentSequence(Math<N> math, List<SectionCore<N>> sections) {
		super();
		this.math = math;
		this.sections = sections;
	}

	void init() {
		SectionCore section2 = sections.get(sections.size() - 1);
		init(
			AxisItem.create(sections.get(0), math.ZERO_VALUE()), 
			AxisItem.create(section2, math.decrement(section2.getCount())));
	}
	void init(AxisItem startItem, AxisItem endItem) {
		this.startItem = startItem;
    this.endItem = endItem;
    this.startItemIndex = sections.indexOf(startItem.getSection());
		this.endItemIndex = sections.indexOf(endItem.getSection());
		SectionCore sl;
    SectionCore startSection = (SectionCore) startItem.getSection().getCore();
    SectionCore endSection = (SectionCore) endItem.getSection().getCore();

		sl = startSection;
		istart = sl.order.items.isEmpty() ? 0 : sl.order
				.getExtentIndex(startItem.getIndex());
		sl = endSection;
		iend = sl.order.items.isEmpty() ? 0 : sl.order
				.getExtentIndex(endItem.getIndex());

		section = startSection;
		sectionIndex = sections.indexOf(section);
		items = section.order.items;
		i = istart;
	}

	boolean next() {
		if (i >= items.size()) {
			sectionIndex++;
			if (sectionIndex > endItemIndex)
				return false;
			section = sections.get(sectionIndex);
      items = section.order.items;
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