package pl.netanel.swt.matrix;

import java.util.ArrayList;

public class AxisModel {
	private static final Section[] EMPTY = new Section[] {};
	
	private final ArrayList<Section> sections;
	private final Class<Integer> numberClass;
	private Section[] layerOrder;
	private Section body, header;
	
	public AxisModel() {
		this(int.class, new Section(int.class), new Section(int.class));
	}

	public AxisModel(Class<Integer> numberClass, Section ...sections) {
		this.numberClass = numberClass;
		this.sections = new ArrayList(sections.length);
		for (int i = 0; i < sections.length; i++) {
			Section section = sections[i];
			section.index = i;
			this.sections.add(section);
		}
		body = sections.length > 1 ? sections[1] : sections.length == 1 ? sections[0] : null;
		header = sections.length > 1 ? sections[0] : null;
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
		return layerOrder == null ? toArray() : layerOrder;
	}

	
	private Section[] toArray() {
		return sections.toArray(EMPTY);
	}

	
}
