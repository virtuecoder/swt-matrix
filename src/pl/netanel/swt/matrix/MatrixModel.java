package pl.netanel.swt.matrix;

import java.util.ArrayList;
import java.util.Iterator;

import pl.netanel.swt.matrix.Axis.ExtentSequence;
import pl.netanel.util.ImmutableIterator;

class MatrixModel<N0 extends Number, N1 extends Number> implements Iterable<Zone<N0, N1>> {

	final Axis<N0> axis0;
	final Axis<N1> axis1;
	final ArrayList<Zone<N0, N1>> zones;
	final ArrayList<ZoneClient<N0, N1>> zoneClients;
	int[] paintOrder;
	private ExtentPairSequence seq;

	public MatrixModel(Axis<N0> axis0, Axis<N1> axis1, Zone<N0, N1> ...zones) {
		this.axis0 = axis0;
		this.axis1 = axis1;
		
		this.zones = new ArrayList<Zone<N0, N1>>(zones.length);
		this.zoneClients = new ArrayList<ZoneClient<N0, N1>>(zones.length);
		for (int i = 0; i < zones.length; i++) {
			this.zones.add(zones[i]);
			this.zoneClients.add(new ZoneClient(zones[i]));
		}
	}

	void setMatrix(Matrix matrix) {
		Section body0  = axis0.getBody(), body1 = axis1.getBody();
		Section header0 = axis0.getHeader(), header1 = axis1.getHeader();
		
		for (Section<N0> section0: axis0.sections) {
			for (Section<N1> section1: axis1.sections) {
				Zone zone = getZone(section0, section1);
				if (zone == null) {
					zone = createZone(section0, section1);
					this.zones.add(zone);
					this.zoneClients.add(new ZoneClient(zone));
				}
				zone.setMatrix(matrix);
				if (zone.getPainterCount() == 0) {
					if (section0.equals(body0) && section1.equals(body1)) {
						zone.setDefaultBodyStyle();
					}
					else if (section0.equals(header0) || section1.equals(header1)) {
						zone.setDefaultHeaderStyle();
					}
				}
			}
		}
		calculatePaintOrder();
		
		seq = new ExtentPairSequence();
	}

	private Zone createZone(Section section0, Section section1) {
		Zone zone = null;
		Section header0 = axis0.getHeader();
		Section header1 = axis1.getHeader();
		Section body0 = axis0.getBody();
		Section body1 = axis1.getBody();
		if (section0.equals(header0) && section1.equals(header1)) {
			// top left
			zone = new Zone(section0, section1);
		} else {
			if (body0.equals(section0) && header1.equals(section1)) {
				// row header
				zone = new Zone(section0, section1) {
					@Override
					public String getText(Number index0, Number index1) {
						return index0.toString();
					};
				};
			}
			else if (header0.equals(section0) && body1.equals(section1)) {
				// column header
				zone = new Zone(section0, section1) {
					@Override
					public String getText(Number index0, Number index1) {
						return index1.toString();
					};
				};
			} 
			else {
				// body
				zone = new Zone(section0, section1) {
					public String getText(Number index0, Number index1) {
						return index0.toString() + ", " + index1.toString();
					};
				};
			}
		}
		return zone;
	}
	
	private void calculatePaintOrder() {
		paintOrder = new int[zones.size()];
		int[] order0 = axis0.getZOrder();
		int[] order1 = axis1.getZOrder();
		int k = 0;
		for (int i = 0, imax = order0.length; i < imax; i++) {
			for (int j = 0, jmax = order1.length; j < jmax; j++) {
				paintOrder[k++] = zones.indexOf(getZone(axis0.sections.get(order0[i]), axis1.sections.get(order1[j])));
			}			
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
	 * Returns a zone located at the intersection of the given axis sections.
	 * 
	 * @param section0 section of the row axis
	 * @param section1 section of the column axis
	 * @return
	 * @exception IllegalArgumentException 
	 * 	 	if the any of the section parameters is out of scope.
	 */
	public Zone getZone(Section section0, Section section1) {
		if (section0 == null || section1 == null) return null;
		for (ZoneClient zone: zoneClients) {
			if (section0.equals(zone.core.section0) && section1.equals(zone.core.section1)) {
				return zone;
			}
		}
		return null;
	}
	
	Zone getZoneUnchecked(Section section0, Section section1) {
		if (section0 == null || section1 == null) return null;
		for (Zone zone: zones) {
			if (section0.equals(zone.section0) && section1.equals(zone.section1)) {
				return zone;
			}
		}
		return null;
	}

	/**
	 * Zone iterator
	 */
	@Override
	public Iterator<Zone<N0, N1>> iterator() {
		return new ImmutableIterator<Zone<N0, N1>>() {
			int i;
			
			@Override
			public boolean hasNext() {
				return i < zones.size();
			}

			@Override
			public Zone next() {
				return zones.get(paintOrder[i++]);
			}
			
		};
	}

	public void setSelected(boolean selected) {
		for (Zone zone: zones) {
			zone.setSelectedAll(selected);
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
			Zone zone = getZoneUnchecked(seq.section0, seq.section1);
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
		Section section0, section1;
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

	void deleteInZones(int axisIndex, Section section, Number start, Number end) {
		for (Zone zone: zones) {
			zone.delete(axisIndex, section, start, end);
		}
		
	}
	
	void insertInZones(int axisIndex, Section section, Number target, Number count) {
		for (Zone zone: zones) {
			zone.insert(axisIndex, section, target, count);
		}
	}

}
