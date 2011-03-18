package pl.netanel.swt.matrix;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import pl.netanel.swt.Resources;
import pl.netanel.swt.matrix.AxisModel.ExtentSequence;
import pl.netanel.swt.matrix.painter.LinePainter;
import pl.netanel.swt.matrix.painter.ModelPainter;
import pl.netanel.swt.matrix.painter.Painter;
import pl.netanel.util.ImmutableIterator;

public class MatrixModel implements Iterable<Zone> {

	private final AxisModel model1;
	private final AxisModel model0;
	private final ArrayList<Zone> zones;
	private int[] zOrder;
	private ExtentPairSequence seq;

	public MatrixModel() {
		this(new AxisModel(), new AxisModel());
		model0.getHeader().setVisible(false);
		model0.setAutoScrollOffset(M.AUTOSCROLL_OFFSET_Y);
		
		model1.getHeader().setDefaultCellWidth(40);
		model1.getHeader().setVisible(false);
		model1.getBody().setDefaultCellWidth(50);
		model1.setAutoScrollOffset(M.AUTOSCROLL_OFFSET_X);
		
	}

	public MatrixModel(AxisModel model0, AxisModel model1, Zone ...zones) {
		this.model0 = model0;
		this.model1 = model1;
		
		this.zones = new ArrayList<Zone>(zones.length);
		for (int i = 0; i < zones.length; i++) {
			this.zones.add(zones[i]);
		}
		
		Section body0  = model0.getBody(), body1 = model1.getBody();
		Section header0 = model0.getHeader(), header1 = model1.getHeader();
		
		for (Section section0: model0.getSections()) {
			for (Section section1: model1.getSections()) {
				Zone zone = getZone(section0, section1);
				if (zone == null) {
					zone = createZone(section0, section1);
					this.zones.add(zone);
				}
				if (section0.equals(body0) && section1.equals(body1)) {
					zone.cellPainters.add(new ModelPainter(zone));
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
		Section[] sections0 = model0.getSectionLayerOrder();
		Section[] sections1 = model1.getSectionLayerOrder();
		int k = 0;
		for (int i = 0, imax = sections0.length; i < imax; i++) {
			for (int j = 0, jmax = sections1.length; j < jmax; j++) {
				zOrder[k++] = zones.indexOf(getZone(sections0[i], sections1[j]));
			}			
		}
	}

	private Zone createZone(Section section0, Section section1) {
		Zone zone = null;
		Section header0 = model0.getHeader();
		Section header1 = model1.getHeader();
		Section body0 = model0.getBody();
		Section body1 = model1.getBody();
		if (section0.equals(header0) && section1.equals(header1)) {
			zone = new Zone(section0, section1, Zone.TOP_LEFT) {
				public String getText(Number index0, Number index1) {
					return null;
				};
			};
		} else {
			if (section0.equals(body0) && section1.equals(header1)) {
				zone = new Zone(section0, section1, Zone.ROW_HEADER) {
					public String getText(Number index0, Number index1) {
						return index0.toString();
					};
				};
			}
			else if (section0.equals(header0) && section1.equals(body1)) {
				zone = new Zone(section0, section1, Zone.COLUMN_HEADER) {
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
		zone.setSelectionBackground(Painter.getDefaultHeaderSelectionColor());
		zone.cellPainters.add(new ModelPainter(zone));
		Color color = Resources.getColor(SWT.COLOR_WIDGET_DARK_SHADOW);
		zone.linePainters0.get(LinePainter.class).color(color);
		zone.linePainters1.get(LinePainter.class).color(color);
		return zone;
	}
	
	
	public Zone getBody() {
		return getZone(model0.getBody(), model1.getBody());
	}
	public Zone getColumneHeader() {
		return getZone(model0.getHeader(), model1.getBody());
	}
	public Zone getRowHeader() {
		return getZone(model0.getBody(), model1.getHeader());
	}
	public Zone getTopLeft() {
		return getZone(model0.getHeader(), model1.getHeader());
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


	public AxisModel getModel0() {
		return model0;
	}
	
	public AxisModel getModel1() {
		return model1;
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
		if (model0.comparePosition(start0, end0) > 0) {
			tmp = start0; start0 = end0; end0 = tmp;
		}
		if (model1.comparePosition(start1, end1) > 0) {
			tmp = start1; start1 = end1; end1 = tmp;
		}
	
		seq.init(start0, end0, start1, end1);
		while (seq.next()) {
			Zone zone = getZone(seq.section0, seq.section1);
			if (zone.isSelectionEnabled()) {
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
		Section section0, section1;
		MutableNumber start0, end0, start1, end1;
		ExtentSequence seq0, seq1;
		boolean empty;
		private AxisItem startItem1;
		private AxisItem endItem1;
		
		public ExtentPairSequence() {
			seq0 = model0.new ExtentSequence();
			seq1 = model1.new ExtentSequence();
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
