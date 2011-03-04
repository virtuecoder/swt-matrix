package pl.netanel.swt.matrix;

import pl.netanel.util.Preconditions;
import pl.netanel.util.Sequence;

/**
 * Cartesian product of axis items from two ranges start0..end0 and statt1..end1 inclusively.
 * 
 * @author Jacek created 03-02-2011
 */
public class ItemPairSequence implements Sequence {
	private final AxisItem start0, end0, start1, end1;
	private final AxisModel model0, model1;
	
	boolean empty;
	int i0, section1, startSection0, startSection1;
	MutableNumber index0, index1;

	public ItemPairSequence(
			Layout layout0, AxisItem start0, AxisItem end0, 
			Layout layout1, AxisItem start1, AxisItem end1) 
	{
		Preconditions.checkArgument(layout0.compare(start0, end0) <= 0, "start0 must be lower or equal to end0");
		Preconditions.checkArgument(layout1.compare(start1, end1) <= 0, "start1 must be lower or equal to end1");
		this.start0 = start0;
		this.end0 = end0.copy();
		this.start1 = start1;
		this.end1 = end1.copy();
		
		index0 = layout0.math.create(0);
		index1 = layout1.math.create(0);
		
		// Skip empty row section
		for(i0 = start0.section.index; 
			i0 < layout.sections. && 
				model0.getItemCount(i0).compareTo(model0.factory.ZERO()) == 0;
			i0++);
		startSection0 = i0;
		
		// Skip empty column section
		for(section1 = start1.section; 
			section1 < model1.getSectionCount() && 
				model1.getItemCount(section1).compareTo(model1.factory.ZERO()) == 0;
			section1++);
		startSection1 = section1;
		
		// Ensure the indexes are not out of scope
		MutableNumber count = model0.getItemCount(end0.section);
		if (end0.index.compareTo(count) >= 0) this.end0.index.set(count).decrement();
		count = model1.getItemCount(end1.section);
		if (end1.index.compareTo(count) >= 0) this.end1.index.set(count).decrement();
	}

	public void init() {
		i0 = startSection0;
		index0.set(i0 == start0.section ? start0.index : model0.factory.ZERO());
		
		if (i0 >= model0.getSectionCount()) {
			// Enforce the end condition
			index1.set(end1.index).increment(); 
		} else {
			index1.set(section1 == start1.section ? start1.index : model1.factory.ZERO()).decrement();
		}
	}
	
	public boolean next() {
		
		// Before the last column section 
		if (section1 < end1.section) {
			// Next column index
			index1.increment();
			// If beyond the last column index in the section
			if (math1.compare(index1, model1.getItemCount(section1)) >= 0) {
				// Next column section
				section1++;
				index1.set(0);
			}
		// In the last column section 
		} else {
			// Next column index
			index1.increment();
			// If beyond the end column index
			if (math1.compare(index1, end1.index) > 0) {
				// Reset column iteration
				section1 = startSection1;
				index1.set(start1.index);
				
				// Before the last row section
				if (i0 < end0.section) {
					// Next row index
					index0.increment();
					// If beyond the last row index in the section
					if (math.compare(index0, model0.getItemCount(i0)) >= 0) {
						// next row section
						i0++;
						index0.set(0);
					}
				} 
				// In the last row section
				else {
					// Next row index
					index0.increment();
					// If beyond the end row index 
					if (math.compare(index0, end0.index) > 0) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public int getSection0() {
		return i0;
	}
	public int getSection1() {
		return section1;
	}
	public MutableNumber getIndex0() {
		return index0;
	}
	public MutableNumber getIndex1() {
		return index1;
	}
	
	public AxisItem getStart0() {
		return start0;
	}

	public AxisItem getEnd0() {
		return end0;
	}

	public AxisItem getStart1() {
		return start1;
	}

	public AxisItem getEnd1() {
		return end1;
	}

	@Override
	public String toString() {
		return "" + i0 + " " + index0 + 
			 ", " + section1 + " " + index1;
	}
}
