package pl.netanel.swt.matrix;

import java.util.ArrayList;
import java.util.List;

class AxisExtentSequence<N extends Number> {
	List<SectionCore<N>> sections;
	SectionCore<N> section;
	int sectionIndex;
	N start, end;
	private int startItemIndex, endItemIndex, i, istart, iend;
	private ArrayList<Extent<N>> items;
	private AxisPointer<N> startItem, endItem;
	private final Math<N> math;
	
	public AxisExtentSequence(Math<N> math, List<SectionCore<N>> sections) {
		super();
		this.math = math;
		this.sections = sections;
	}

	void init() {
		SectionCore<N> section2 = sections.get(sections.size() - 1);
		init(
			AxisPointer.create(sections.get(0), math.ZERO_VALUE()), 
			AxisPointer.create(section2, math.decrement(section2.getCount())));
	}
	void init(AxisPointer<N> startItem, AxisPointer<N> endItem) {
		this.startItem = startItem;
    this.endItem = endItem;
    this.startItemIndex = sections.indexOf(startItem.section);
		this.endItemIndex = sections.indexOf(endItem.section);
		SectionCore<N> sl;
    SectionCore<N> startSection = startItem.section;
    SectionCore<N> endSection = endItem.section;

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
			section = SectionCore.from(sections.get(sectionIndex));
      items = section.order.items;
			i = 0;
		}
		Extent<N> e = items.get(i);
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