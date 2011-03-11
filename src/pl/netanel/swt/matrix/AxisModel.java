package pl.netanel.swt.matrix;

import java.util.ArrayList;
import java.util.Iterator;

import pl.netanel.util.Arrays;

public class AxisModel implements Iterable<Section> {
	private static final Section[] EMPTY = new Section[] {};
	
	private final Class<? extends Number> numberClass;
	private final Math math;
	private final ArrayList<Section> sections;
	private Section[] zOrder;
	private Section body, header;

	
	public AxisModel() {
		this(int.class, new Section(int.class), new Section(int.class));
	}

	public AxisModel(Class<? extends Number> numberClass, Section ...sections) {
		//Preconditions.checkArgument(sections.length > 0, "Model must have at least one section");
		this.numberClass = numberClass;
		math = Math.getInstance(numberClass);
		this.sections = new ArrayList(sections.length);
		for (int i = 0; i < sections.length; i++) {
			Section section = sections[i];
			section.index = i;
			this.sections.add(section);
		}
		if (sections.length == 0) {
			this.sections.add(body = new Section(numberClass));
		} else {
			body = sections.length > 1 ? sections[1] : sections.length == 1 ? sections[0] : null;
			header = sections.length > 1 ? sections[0] : null;
		}
		
		// Calculate z-order
		zOrder = new Section[this.sections.size()];
		int j = 0;
		int bodyIndex = this.sections.indexOf(body);
		for (int i = bodyIndex, imax = this.sections.size(); i < imax; i++) {
			zOrder[j++] = this.sections.get(i);
		}
		for (int i = bodyIndex; i-- > 0;) {
			zOrder[j++] = this.sections.get(i);
		}
	}

	public Class<? extends Number> getNumberClass() {
		return numberClass;
	}

	public Section getBody() {
		return body;
	}
	public Section getHeader() {
		return header;
	}

	public void setBody(Section body) {
		this.body = body;
	}

	public Section[] getSections() {
		return toArray();
	}

	public Section[] getSectionLayerOrder() {
		return zOrder;
	}

	
	private Section[] toArray() {
		return sections.toArray(EMPTY);
	}

	public int getZIndex(Section section) {
		return Arrays.indexOf(zOrder, section);
	}

	@Override
	public Iterator<Section> iterator() {
		return sections.iterator();
	}

	
	public void setSelected(AxisItem start, AxisItem end, boolean select) {
		// Make sure start < end 
		if (comparePosition(start, end) > 0) {
			AxisItem tmp = start; start = end; end = tmp;
		}
		
		for (int i = start.section.index; i <= end.section.index; i++) {
			Section section = sections.get(i);
			MutableNumber startIndex = i == start.section.index ? start.index : math.ZERO();
			MutableNumber endIndex = i == end.section.index ? end.index : math.increment(section.getCount());
			section.setSelected(startIndex, endIndex, select);
		}
	}

	int comparePosition(AxisItem item1, AxisItem item2) {
		int diff = item1.section.index - item2.section.index;
		if (diff != 0) return diff; 
		return item1.section.comparePosition(item1.index, item2.index);
	}
	
	
	class ItemSequence {
		Section section;
		MutableNumber start, end;
		private int i, istart, iend, sectionIndex, lastSectionIndex;
		private ArrayList<Extent> items;
		private AxisItem startItem, endItem;
	
		void init(AxisItem startItem, AxisItem endItem) {
			this.startItem = startItem;
			this.endItem = endItem;
			istart = startItem.section.order.items.isEmpty() ? 0 : 
				startItem.section.order.getExtentIndex(startItem.index);
			iend = endItem.section.order.items.isEmpty() ? 0 : 
				endItem.section.order.getExtentIndex(endItem.index);
			
			section = startItem.section;
			sectionIndex = sections.indexOf(section); 
			lastSectionIndex = sections.indexOf(endItem.section); 
			items = sections.get(sectionIndex).order.items;
			i = istart;
		}
		
		boolean next() {
			if (i >= items.size()) {
				sectionIndex++;
				if (sectionIndex > lastSectionIndex) return false;
				items = sections.get(sectionIndex).order.items;
				i = 0;
			}
			Extent e = items.get(i);	
			start = section == startItem.section && i == istart ? 
					startItem.index : e.start;
			end = section == endItem.section && i == iend ? 
					endItem.index : e.end;
			if (i >= iend && math.compare(end, endItem.index) == 0) {
				i = items.size();
			}
			i++;
			return true;
		}
	}
}
