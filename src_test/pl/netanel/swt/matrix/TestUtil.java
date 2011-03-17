package pl.netanel.swt.matrix;

import pl.netanel.swt.matrix.AxisItem;
import pl.netanel.swt.matrix.Extent;
import pl.netanel.swt.matrix.IntMath;
import pl.netanel.swt.matrix.Layout;
import pl.netanel.swt.matrix.MutableInt;
import pl.netanel.swt.matrix.MutableNumber;
import pl.netanel.swt.matrix.NumberSet;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.Layout.LayoutSequence;

public class TestUtil {
	static MutableNumber number(int n) {
		return new MutableInt(n);
	}
	
	static Extent extent(int start, int end) {
		return new Extent(number(start), number(end));
	}
	
	static Extent extent(int n) {
		return new Extent(number(n), number(n));
	}
	
	public static AxisItem item(Section section, int index) {
		return new AxisItem(section, number(index));
	}
	
	public static AxisItem item(AxisModel model, int section, int index) {
		return new AxisItem(model.getSection(section), number(index));
	}
	
	static NumberSet indexSet() {
		return new NumberSet(IntMath.getInstance());
	}
	
	static NumberSet indexSet(int start, int end) {
		NumberSet set = new NumberSet(IntMath.getInstance());
		set.add(start, end);
		return set;
	}
	
	public static String indexes(LayoutSequence seq) {
		StringBuilder sb = new StringBuilder();
		for (seq.init(); seq.next();) {
			if (sb.length() > 0) sb.append(", ");
			sb.append(seq.getItem().index);
		}
		return sb.toString();
	}
	
	public static Layout layout(int ...count) {
		Section[] sections = new Section[count.length];
		for (int i = 0; i < count.length; i++) {
			Section section = new Section(int.class);
			sections[i] = section;
			if (i != 1) section.setNavigationEnabled(false);
			section.setCount(count[i]);
		}
		Layout layout = new Layout(new AxisModel(sections));
		return layout;
	}

	
}
