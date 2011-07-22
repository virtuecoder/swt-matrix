package pl.netanel.swt.matrix;

import java.util.ArrayList;
import java.util.Iterator;

import pl.netanel.swt.matrix.Axis.ExtentSequence;
import pl.netanel.util.ImmutableIterator;

class MatrixModel<N0 extends Number, N1 extends Number> implements Iterable<ZoneCore<N0, N1>> {

	final Axis<N0> axis0;
	final Axis<N1> axis1;
	final ArrayList<ZoneCore<N0, N1>> zones;
	int[] paintOrder;
	private ExtentPairSequence seq;

	public MatrixModel(Axis<N0> axis0, Axis<N1> axis1, ArrayList<ZoneCore<N0, N1>> zones) {
		this.axis0 = axis0;
		this.axis1 = axis1;
		this.zones = zones;
	}

	void setMatrix(Matrix matrix) {
		Section body0  = axis0.getBody(), body1 = axis1.getBody();
		Section header0 = axis0.getHeader(), header1 = axis1.getHeader();
		
		for (SectionClient<N0> section0: axis0.sections) {
			for (SectionClient<N1> section1: axis1.sections) {
				ZoneCore zone = getZone(section0.getCore(), section1.getCore());
				zone.setMatrix(matrix);
				if (zone.getPainterCount() == 0) {
					if (section0.equals(body0) && section1.equals(body1)) {
						zone.setDefaultBodyStyle();
					}
					else if (section0.equals(header0) && section1.equals(body1)) {
					  zone.setDefaultHeaderStyle(
					    new Painter(Painter.NAME_CELLS, Painter.SCOPE_CELLS_VERTICALLY) {
					      @Override
					      public String getText(Number index0, Number index1) {
					        return index1.toString();
					      }
					    }
					  );
					}
					else if (section1.equals(header1) && section0.equals(body0)) {
						zone.setDefaultHeaderStyle(
						  new Painter(Painter.NAME_CELLS, Painter.SCOPE_CELLS_HORIZONTALLY) {
						    @Override
						    public String getText(Number index0, Number index1) {
						      return index0.toString();
						    }
						  }
						);						
					} else {
						zone.setDefaultHeaderStyle(
						  new Painter(Painter.NAME_CELLS, Painter.SCOPE_CELLS_HORIZONTALLY));
					}
				}
			}
		}
		calculatePaintOrder();
		
		seq = new ExtentPairSequence();
	}
	
	private void calculatePaintOrder() {
		paintOrder = new int[zones.size()];
		int[] order0 = axis0.getZOrder();
		int[] order1 = axis1.getZOrder();
		int k = 0;
		for (int i = 0, imax = order0.length; i < imax; i++) {
			for (int j = 0, jmax = order1.length; j < jmax; j++) {
				paintOrder[k++] = zones.indexOf(getZone(
				  axis0.sections.get(order0[i]).getCore(), 
				  axis1.sections.get(order1[j]).getCore()));
			}			
		}
	}
	
	public ZoneCore getZone(SectionCore section0, SectionCore section1) {
		for (ZoneCore zone: zones) {
			if (zone.getSection0().equals(section0) && 
			    zone.getSection1().equals(section1) ) {
				return zone;
			}
		}
		return null;
	}
	
	/**
	 * Zone iterator
	 */
	@Override
	public Iterator<ZoneCore<N0, N1>> iterator() {
		return new ImmutableIterator<ZoneCore<N0, N1>>() {
			int i;
			
			@Override
			public boolean hasNext() {
				return i < zones.size();
			}

			@Override
			public ZoneCore next() {
				return zones.get(paintOrder[i++]);
			}
			
		};
	}

	
	/**
	 * Attention: it is to be called only by a UI handler, since it emits Selection event.
	 * @param start0
	 * @param end0
	 * @param start1
	 * @param end1
	 * @param selectState
	 */
	public void setSelected(boolean selectState, boolean notify) {
//	  // Determine if there is a selection change
//	  boolean modified = false;
//	  if (notify) {
//	    for (Zone zone: zones) {
//	      if (selectState) {
//	        boolean allSelected = zone.getSelectedCount().equals(
//	          new BigInteger(zone.getSection0().getCount().toString()).multiply(
//            new BigInteger(zone.getSection1().getCount().toString())));
//	        if (!allSelected) {
//	          modified = true;
//	          break;
//	        }
//	      }
//	      else {
//	        boolean nothingSelected = BigInteger.ZERO.equals(zone.getSelectionCount());
//	        if (!nothingSelected) {
//	          modified = true;
//	          break;
//	        }
//	      }
//	    }
//	  }
	  
	  // Set selection and notify
		for (ZoneCore zone: zones) {
		  zone.setSelectedAll(selectState);

			if (notify) {
			  zone.addSelectionEvent();
			}
		}
		axis0.setSelected(selectState, notify, false);
		axis1.setSelected(selectState, notify, false);
//		if (notify && modified) {
//			for (Section section: axis0.sections) {
//				section.addSelectionEvent();
//			}
//			for (Section section: axis1.sections) {
//				section.addSelectionEvent();
//			}
//		}
	}
	
	/**
	 * Attention: it is to be called only by a UI handler, since it emits Selection event.
	 * @param start0
	 * @param end0
	 * @param start1
	 * @param end1
	 * @param selected
	 */
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
	
		Zone lastZone = null;
		seq.init(start0, end0, start1, end1);
		while (seq.next()) {
			ZoneCore zone = getZone(seq.section0, seq.section1);
			if (zone.isSelectionEnabled()) {
				zone.setSelected(seq.start0.getValue(), seq.end0.getValue(), seq.start1.getValue(), seq.end1.getValue(), selected);
				
				if (!zone.equals(lastZone)) {
					zone.addSelectionEvent();
					lastZone = zone;
				}
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
		SectionCore section0, section1;
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
				seq1.next();
			}
			section1 = seq1.section;
			start1 = seq1.start;
			end1 = seq1.end;
			return true;
		}
	}

	void deleteInZones(int axisIndex, Section section, Number start, Number end) {
		for (ZoneCore zone: zones) {
			zone.delete(axisIndex, section, start, end);
		}
		
	}
	
	void insertInZones(int axisIndex, Section section, Number target, Number count) {
		for (ZoneCore zone: zones) {
			zone.insert(axisIndex, section, target, count);
		}
	}

}
