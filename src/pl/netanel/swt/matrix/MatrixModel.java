package pl.netanel.swt.matrix;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

import pl.netanel.swt.Resources;
import pl.netanel.swt.matrix.Axis.ExtentSequence;
import pl.netanel.util.ImmutableIterator;

class MatrixModel implements Iterable<Zone> {

	final Axis axis0;
	final Axis axis1;
	private final ArrayList<Zone> zones;
	private int[] zOrder;
	private ExtentPairSequence seq;

//	public MatrixModel() {
//		this(new Axis<Integer>(), new Axis());
//		axis0.getHeader().setVisible(false);
//		axis0.setAutoScrollOffset(M.AUTOSCROLL_OFFSET_Y);
//		
//		axis1.getHeader().setDefaultCellWidth(40);
//		axis1.getHeader().setVisible(false);
//		axis1.getBody().setDefaultCellWidth(50);
//		axis1.setAutoScrollOffset(M.AUTOSCROLL_OFFSET_X);
//		
//	}

	public MatrixModel(
			Axis<? extends Number> axis0, 
			Axis<? extends Number> axis1, Zone ...zones) 
	{
		this.axis0 = axis0;
		this.axis1 = axis1;
		
		this.zones = new ArrayList<Zone>(zones.length);
		for (int i = 0; i < zones.length; i++) {
			this.zones.add(zones[i]);
		}
		
		Section body0  = axis0.getBody(), body1 = axis1.getBody();
		Section header0 = axis0.getHeader(), header1 = axis1.getHeader();
		
		for (Section section0: axis0) {
			for (Section section1: axis1) {
				Zone zone = getZone(section0, section1);
				if (zone == null) {
					zone = createZone(section0, section1);
					this.zones.add(zone);
				}
				if (section0.equals(body0) && section1.equals(body1)) {
					if (zone.painter.children.isEmpty()) {
						zone.painter.add(new ModelPainter(zone));
						Color color = Resources.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW);
						zone.painter.add(new LinePainter("row lines", Painter.ROW_LINE, color ));
						zone.painter.add(new LinePainter("column lines", Painter.COLUMN_LINE, color));
					}
				}
				else if (section0.equals(header0) || section1.equals(header1)) {
					setHeaderStyle(zone);
				}
			}
		}
		calculateZOrder();
		
		seq = new ExtentPairSequence();
	}
	
	private void calculateZOrder() {
		zOrder = new int[zones.size()];
		Section[] sections0 = axis0.getZOrder();
		Section[] sections1 = axis1.getZOrder();
		int k = 0;
		for (int i = 0, imax = sections0.length; i < imax; i++) {
			for (int j = 0, jmax = sections1.length; j < jmax; j++) {
				zOrder[k++] = zones.indexOf(getZone(sections0[i], sections1[j]));
			}			
		}
	}

	private Zone createZone(Section section0, Section section1) {
		Zone zone = null;
		Section header0 = axis0.getHeader();
		Section header1 = axis1.getHeader();
		Section body0 = axis0.getBody();
		Section body1 = axis1.getBody();
		if (section0.equals(header0) && section1.equals(header1)) {
			zone = new Zone(section0, section1, Zone.TOP_LEFT) {
				@Override
				public String getText(Number index0, Number index1) {
					return null;
				};
			};
		} else {
			if (section0.equals(body0) && section1.equals(header1)) {
				zone = new Zone(section0, section1, Zone.ROW_HEADER) {
					@Override
					public String getText(Number index0, Number index1) {
						return index0.toString();
					};
				};
			}
			else if (section0.equals(header0) && section1.equals(body1)) {
				zone = new Zone(section0, section1, Zone.COLUMN_HEADER) {
					@Override
					public String getText(Number index0, Number index1) {
						return index1.toString();
					};
				};
			} 
			else if (section0.equals(body0) && section1.equals(body1)) {
				zone = new Zone(section0, section1, Zone.BODY);
			}
			else {
				zone = new Zone(section0, section1, Zone.NONE);
			}
		}
		return zone;
	}
	
	
	private Zone setHeaderStyle(Zone zone) {
		zone.setDefaultForeground(Resources.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		zone.setDefaultBackground(Resources.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		
		RGB selectionColor = Resources.getColor(SWT.COLOR_LIST_SELECTION).getRGB();
		RGB whiteColor = Resources.getColor(SWT.COLOR_WIDGET_BACKGROUND).getRGB();
		RGB rgb = Painter.blend(selectionColor, whiteColor, 90);
		zone.setSelectionBackground(Resources.getColor(rgb));
		
		if (zone.painter.children.isEmpty()) {
			zone.painter.add(new ModelPainter(zone));
			final Color color = Resources.getColor(SWT.COLOR_WIDGET_DARK_SHADOW);
			zone.painter.add(new LinePainter("row lines", Painter.ROW_LINE, color));
			zone.painter.add(new LinePainter("column lines", Painter.COLUMN_LINE, color));
		}
		return zone;
	}
	
	private static class LinePainter extends Painter {
		private final Color color;

		public LinePainter(String name, int scope, Color color) {
			super(name, scope);
			this.color = color;
		}
		
		@Override
		public void paint(int x, int y, int width, int height) {
			gc.setBackground(color);
			gc.fillRectangle(x, y, width, height);
		}
	}
	
	public Zone getBody() {
		return getZone(axis0.getBody(), axis1.getBody());
	}
	public Zone getColumneHeader() {
		return getZone(axis0.getHeader(), axis1.getBody());
	}
	public Zone getRowHeader() {
		return getZone(axis0.getBody(), axis1.getHeader());
	}
	public Zone getTopLeft() {
		return getZone(axis0.getHeader(), axis1.getHeader());
	}
	
	/**
	 * Returns a zone by an identifier defined in {@link Zone}.  
	 * The parameter can have four possible values: <ul>
	 * <li>BODY</li>
	 * <li>ROW_HEADER</li>
	 * <li>COLUMN_HEADER</li>
	 * <li>TOP_LEFT</li>
	 * </ul>
	 * Otherwise the function returns null.
	 * <p>
	 * @param id 
	 * @return
	 */
	public Zone getZone(int id) {
		for (Zone zone: zones) {
			if (zone.is(id)) {
				return zone;
			}
		}
		return null;
	}
	
	/**
	 * Returns a zone located at the intersection of the given axis sections.
	 * 
	 * @param section0 section of the row axis
	 * @param section1 section of the column axis
	 * @return
	 * @exception IllegalArgumentException 
	 * 	 	if the any of the section parameters is out of scope.
	 */
	public Zone getZone(Section section0, Section section1) {
		for (Zone zone: zones) {
			if (zone.section0.equals(section0) && zone.section1.equals(section1)) {
				return zone;
			}
		}
		return null;
	}
	
	public Zone getZone(SectionUnchecked section0, SectionUnchecked section1) {
		for (Zone zone: zones) {
			if (zone.section0.core.equals(section0) && zone.section1.core.equals(section1)) {
				return zone;
			}
		}
		return null;
	}

	/**
	 * Zone iterator
	 */
	@Override
	public Iterator<Zone> iterator() {
		return new ImmutableIterator<Zone>() {
			int i;
			
			@Override
			public boolean hasNext() {
				return i < zones.size();
			}

			@Override
			public Zone next() {
				return zones.get(zOrder[i++]);
			}
			
		};
	}

	public void setSelected(boolean selected) {
		for (Zone zone: zones) {
			zone.setSelected(selected);
		}
	}
	
	void setSelected (
			AxisItem start0, AxisItem end0, 
			AxisItem start1, AxisItem end1, boolean selected) {
		
		// Make sure start < end 
		AxisItem tmp;
		if (axis0.comparePosition(start0, end0) > 0) {
			tmp = start0; start0 = end0; end0 = tmp;
		}
		if (axis1.comparePosition(start1, end1) > 0) {
			tmp = start1; start1 = end1; end1 = tmp;
		}
	
		seq.init(start0, end0, start1, end1);
		while (seq.next()) {
			Zone zone = getZone(seq.section0, seq.section1);
			if (zone.selectionEnabled) {
				zone.setSelected(seq.start0.getValue(), seq.end0.getValue(), seq.start1.getValue(), seq.end1.getValue(), selected);
			}	
		}
	}
	
	/**
	 * Iterates over all extents within the boundaries defined 
	 * by items passed to the init() method. 
	 * 
	 * @author Jacek created 21-02-2011
	 */
	class ExtentPairSequence {
		SectionUnchecked section0, section1;
		MutableNumber start0, end0, start1, end1;
		ExtentSequence seq0, seq1;
		boolean empty;
		private AxisItem startItem1;
		private AxisItem endItem1;
		
		public ExtentPairSequence() {
			seq0 = axis0.new ExtentSequence();
			seq1 = axis1.new ExtentSequence();
		}

		public void init(AxisItem start0, AxisItem end0, AxisItem start1, AxisItem end1) {
			
			startItem1 = start1;
			endItem1 = end1;
			seq0.init(start0, end0);
			seq1.init(start1, end1);
			empty = !seq0.next();
			this.section0 = seq0.section;
			this.start0 = seq0.start;
			this.end0 = seq0.end;
		}
		
		public boolean next() {
			if (empty) return false; 
			
			if (!seq1.next()) {
				if (!seq0.next()) {
					return false;
				}
				this.section0 = seq0.section;
				this.start0 = seq0.start;
				this.end0 = seq0.end;
				seq1.init(startItem1, endItem1);
			}
			section1 = seq1.section;
			start1 = seq1.start;
			end1 = seq1.end;
			return true;
		}
	}

	
}
