package pl.netanel.swt.matrix;

import java.util.ArrayList;
import java.util.Iterator;

import pl.netanel.util.Arrays;

public class AxisModel implements Iterable<Section> {
	private static final Section[] EMPTY = new Section[] {};
	
	private final ArrayList<Section> sections;
	private final Class<? extends Number> numberClass;
	private Section[] zOrder;
	private Section body, header;
	
	public AxisModel() {
		this(int.class, new Section(int.class), new Section(int.class));
	}

	public AxisModel(Class<? extends Number> numberClass, Section ...sections) {
		//Preconditions.checkArgument(sections.length > 0, "Model must have at least one section");
		this.numberClass = numberClass;
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

	
	public void setSelected(AxisItem start, AxisItem end, boolean selected) {
		
		
	}

	
}
