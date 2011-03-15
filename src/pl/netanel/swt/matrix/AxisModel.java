package pl.netanel.swt.matrix;

import java.util.ArrayList;
import java.util.Iterator;

import pl.netanel.util.Arrays;
import pl.netanel.util.Preconditions;

public class AxisModel implements Iterable<Section> {
	private static final Section[] EMPTY = new Section[] {};
	
	final Math math;
	private final ArrayList<Section> sections;
	private Section[] zOrder;
	private Section body, header;
	private int autoScrollOffset;

	
	public AxisModel() {
		this(new Section(int.class), new Section(int.class));
	}
	
	public AxisModel(Class<? extends Number> numberClass) {
		this(new Section(numberClass), new Section(numberClass));
	}

	public AxisModel(Section ...sections) {
		Preconditions.checkArgument(sections.length > 0, "Model must have at least one section");
		math = sections[0].math;
		this.sections = new ArrayList(sections.length);
		for (int i = 0; i < sections.length; i++) {
			Section section = sections[i];
			section.index = i;
			this.sections.add(section);
		}
		if (sections.length == 0) {
			this.sections.add(body = new Section(math));
		} else {
			body = sections.length > 1 ? sections[1] : sections.length == 1 ? sections[0] : null;
			header = sections.length > 1 ? sections[0] : null;
			if (header != null) {
				header.setNavigationEnabled(false);
			}
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
		
		autoScrollOffset = M.AUTOSCROLL_OFFSET_y;
	}

	public Section getBody() {
		return body;
	}
	public Section getHeader() {
		return header;
	}

	public void setBody(Section section) {
		this.body = section;
	}
	
	public void setHeader(Section section) {
		this.header = section;
	}
	
	public int getSectionCount() {
		return sections.size();
	}
	
	public Section getSection(int i) {
		return sections.get(i);
	}
	
	public Section[] getSections() {
		return toArray();
	}

	public Section[] getSectionLayerOrder() {
		return zOrder;
	}
	
	public int getZIndex(Section section) {
		return Arrays.indexOf(zOrder, section);
	}

	
	private Section[] toArray() {
		return sections.toArray(EMPTY);
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

	public void setSelected(boolean selected) {
		for (int i = 0, imax = sections.size(); i < imax; i++) {
			Section section = sections.get(i);
			section.setSelected(selected);
		}
	}

	
	AxisItem getLastItem() {
		for (int i = sections.size(); i-- > 0;) {
			Section section = sections.get(i);
			if (section.isEmpty()) continue;
			return new AxisItem(section, math.decrement(section.getCount()));
		}
		return getFirstItem();
	}

	AxisItem getFirstItem() {
		return new AxisItem(sections.get(0), math.create(0));
	}

	int comparePosition(AxisItem item1, AxisItem item2) {
		int diff = item1.section.index - item2.section.index;
		if (diff != 0) return diff; 
		return item1.section.comparePosition(item1.index, item2.index);
	}
	
	
	/**
	 * Iterates over extents between the start and end items. 
	 * 
	 * @author Jacek Kolodziejczyk created 11-03-2011
	 */
	class ExtentSequence {
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
				section = sections.get(sectionIndex);
				items = section.order.items;
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


	public int getAutoScrollOffset() {
		return autoScrollOffset;
	}

	public void setAutoScrollOffset(int autoScrollOffset) {
		this.autoScrollOffset = autoScrollOffset;
	}

	/**
	 * Sets the hidden state of selected indexes in each section.
	 * @param hidden new hiding state
	 */
	public void setHidden(boolean hidden) {
		for (int i = 0, imax = sections.size(); i < imax; i++) {
			Section section = sections.get(i);
			section.setHidden(hidden);
		}
	}
	
	
}
