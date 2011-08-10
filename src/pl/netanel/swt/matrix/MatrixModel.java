package pl.netanel.swt.matrix;

import java.util.ArrayList;
import java.util.Iterator;

import pl.netanel.util.ImmutableIterator;

class MatrixModel<X extends Number, Y extends Number> implements Iterable<ZoneCore<X, Y>> {

	final Axis<Y> axisY;
	final Axis<X> axisX;
	final ArrayList<ZoneCore<X, Y>> zones;
	int[] paintOrder;
	private ExtentPairSequence seq;

	public MatrixModel(Axis<Y> axisY, Axis<X> axisX, ArrayList<ZoneCore<X, Y>> zones) {
		this.axisY = axisY;
		this.axisX = axisX;
		this.zones = zones;
	}

	void setMatrix(Matrix<X, Y> matrix) {
	  Section<X> bodyX = axisX.getBody();
		Section<Y> bodyY  = axisY.getBody();
		Section<X> headerX = axisX.getHeader();
		Section<Y> headerY = axisY.getHeader();
		
		for (SectionClient<Y> sectionY: axisY.sections) {
			for (SectionClient<X> sectionX: axisX.sections) {
				ZoneCore<X, Y> zone = getZone(sectionX.getUnchecked(), sectionY.getUnchecked());
				zone.setMatrix(matrix);
				if (zone.getPainterCount() == 0) {
					if (sectionY.equals(bodyY) && sectionX.equals(bodyX)) {
						zone.setDefaultBodyStyle();
					}
					else if (sectionY.equals(headerY) && sectionX.equals(bodyX)) {
					  zone.setDefaultHeaderStyle(
					    new Painter<X, Y>(Painter.NAME_CELLS, Painter.SCOPE_CELLS_Y) {
					      @Override
					      public String getText(Number indexX, Number indexY) {
					        return indexX.toString();
					      }
					    }
					  );
					}
					else if (sectionX.equals(headerX) && sectionY.equals(bodyY)) {
						zone.setDefaultHeaderStyle(
						  new Painter<X, Y>(Painter.NAME_CELLS, Painter.SCOPE_CELLS) {
						    @Override
						    public String getText(X indexX, Y indexY) {
						      return indexY.toString();
						    }
						  }
						);						
					} else {
						zone.setDefaultHeaderStyle(
						  new Painter<X, Y>(Painter.NAME_CELLS, Painter.SCOPE_CELLS));
					}
				}
			}
		}
		calculatePaintOrder();
		
		seq = new ExtentPairSequence();
	}
	
	private void calculatePaintOrder() {
		paintOrder = new int[zones.size()];
		int[] orderY = axisY.getZOrder();
		int[] orderX = axisX.getZOrder();
		int k = 0;
		for (int i = 0, imax = orderY.length; i < imax; i++) {
			for (int j = 0, jmax = orderX.length; j < jmax; j++) {
				paintOrder[k++] = zones.indexOf(getZone(
				  axisX.sections.get(orderX[j]).getUnchecked(), 
				  axisY.sections.get(orderY[i]).getUnchecked()));
			}			
		}
	}
	
	public ZoneCore<X, Y> getZone(SectionCore<X> sectionX, SectionCore<Y> sectionY) {
		for (ZoneCore<X, Y> zone: zones) {
			if (zone.getSectionY().equals(sectionY) && 
			    zone.getSectionX().equals(sectionX) ) {
				return zone;
			}
		}
		return null;
	}
	
	/**
	 * Zone iterator
	 */
	@Override
	public Iterator<ZoneCore<X, Y>> iterator() {
		return new ImmutableIterator<ZoneCore<X, Y>>() {
			int i;
			
			@Override
			public boolean hasNext() {
				return i < zones.size();
			}

			@Override
			public ZoneCore<X, Y> next() {
				return zones.get(paintOrder[i++]);
			}
			
		};
	}

	
	/**
	 * Attention: it is to be called only by a UI handler, since it emits Selection event.
	 * @param startY
	 * @param endY
	 * @param startX
	 * @param endX
	 * @param selectState
	 */
	public void setSelected(boolean selectState, boolean notify) {
//	  // Determine if there is a selection change
//	  boolean modified = false;
//	  if (notify) {
//	    for (Zone zone: zones) {
//	      if (selectState) {
//	        boolean allSelected = zone.getSelectedCount().equals(
//	          new BigInteger(zone.getSectionY().getCount().toString()).multiply(
//            new BigInteger(zone.getSectionX().getCount().toString())));
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
		for (ZoneCore<X, Y> zone: zones) {
		  zone.setSelectedAll(selectState);

			if (notify) {
			  zone.addSelectionEvent();
			}
		}
		axisY.setSelected(selectState, notify, false);
		axisX.setSelected(selectState, notify, false);
//		if (notify && modified) {
//			for (Section section: axisY.sections) {
//				section.addSelectionEvent();
//			}
//			for (Section section: axisX.sections) {
//				section.addSelectionEvent();
//			}
//		}
	}
	
	/**
	 * Attention: it is to be called only by a UI handler, since it emits Selection event.
	 * @param startX
	 * @param endX
	 * @param startY
	 * @param endY
	 * @param selected
	 */
	void setSelected (
			AxisItem<X> startX, AxisItem<X> endX, 
			AxisItem<Y> startY, AxisItem<Y> endY, boolean selected) {
		
		// Make sure start < end 
	  if (axisX.comparePosition(startX, endX) > 0) {
	    AxisItem<X> tmp;
	    tmp = startX; startX = endX; endX = tmp;
	  }
		if (axisY.comparePosition(startY, endY) > 0) {
		  AxisItem<Y> tmp;
			tmp = startY; startY = endY; endY = tmp;
		}
	
		ZoneCore<X, Y> lastZone = null;
		seq.init(startX, endX, startY, endY);
		while (seq.next()) {
			ZoneCore<X, Y> zone = getZone(seq.sectionX, seq.sectionY);
			if (zone.isSelectionEnabled()) {
				zone.setSelected(
				  seq.startX.getValue(), seq.endX.getValue(), 
				  seq.startY.getValue(), seq.endY.getValue(), selected);
				
				if (!zone.equals(lastZone)) {
					zone.addSelectionEvent();
					lastZone = zone;
				}
			}	
		}
	}
	
	void insertInZonesX(SectionCore<X> section, X target, X count) {
	   for (ZoneCore<X, Y> zone: zones) {
        if (zone.sectionX.equals(section)) {
          zone.cellSelection.insertX(target, count);
          zone.lastSelection.deleteX(target, count);
        }
     }
	}
	
  void insertInZonesY(SectionCore<Y> section, Y target, Y count) {
    for (ZoneCore<X, Y> zone: zones) {
      if (zone.sectionY.equals(section)) {
        zone.cellSelection.insertY(target, count);
        zone.lastSelection.insertY(target, count);
      }
    }
  }
  
//  /**
//   * Returns the body zone of this matrix.
//   * @return the body zone of this matrix
//   */
//  public ZoneCore<X, Y> getBody() {
//    return getZone(axisX.getBody().ge, axisY.getBody());
//  }
//  /**
//   * Returns the column header zone of this matrix.
//   * @return the column header zone of this matrix
//   */
//  public ZoneCore<X, Y> getHeaderX() {
//    return getZone(axisX.getBody(), axisY.getHeader());
//  }
//  /**
//   * Returns the row header zone of this matrix.
//   * @return the row header zone of this matrix
//   */
//  public ZoneCore<X, Y> getHeaderY() {
//    return getZone(axisX.getHeader(), axisY.getBody());
//  }
//  /**
//   * Returns the top left zone of this matrix.
//   * @return the top left zone of this matrix
//   */
//  public ZoneCore<X, Y> getTopLeft() {
//    return getZone(axisX.getHeader(), axisY.getHeader());
//  }


	
	/**
	 * Iterates over all extents within the boundaries defined 
	 * by items passed to the init() method. 
	 * 
	 * @author Jacek created 21-02-2011
	 */
	class ExtentPairSequence {
	  SectionCore<X> sectionX;
		SectionCore<Y> sectionY;
		MutableNumber<X> startX, endX;
		MutableNumber<Y> startY, endY;
		Axis<X>.ExtentSequence seqX;
		Axis<Y>.ExtentSequence seqY;
		boolean empty;
		private AxisItem<X> startItem1;
		private AxisItem<X> endItem1;
		
		public ExtentPairSequence() {
			seqY = axisY.new ExtentSequence();
			seqX = axisX.new ExtentSequence();
		}

		public void init(AxisItem<X> startX, AxisItem<X> endX, AxisItem<Y> startY, AxisItem<Y> endY) {
			
			startItem1 = startX;
			endItem1 = endX;
			seqX.init(startX, endX);
			seqY.init(startY, endY);
			empty = !seqY.next();
			this.sectionY = seqY.section;
			this.startY = seqY.start;
			this.endY = seqY.end;
		}
		
		public boolean next() {
			if (empty) return false; 
			
			if (!seqX.next()) {
				if (!seqY.next()) {
					return false;
				}
				this.sectionY = seqY.section;
				this.startY = seqY.start;
				this.endY = seqY.end;
				seqX.init(startItem1, endItem1);
				seqX.next();
			}
			sectionX = seqX.section;
			startX = seqX.start;
			endX = seqX.end;
			return true;
		}
	}
}
