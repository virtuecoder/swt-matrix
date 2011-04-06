package pl.netanel.swt.matrix;

import java.math.BigInteger;
import java.util.Iterator;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Listener;

import pl.netanel.util.ImmutableIterator;

class ZoneClient<N0 extends Number, N1 extends Number> extends Zone{
	public static final int NONE = -1;
	public static final int ANY = 0;
	public static final int BODY = 1;
	public static final int TOP_LEFT= 2;
	public static final int ROW_HEADER = 3;
	public static final int COLUMN_HEADER = 4;
//	public static final int ROW_FOOTER = 5;
//	public static final int COLUMN_FOOTER = 6;
//	public static final int BOTTOM_RIGHT = 7;
	
	final Zone core;
	
	public ZoneClient(Zone<N0, N1> zone) {
		core = zone;
	}
	
	@Override
	public boolean equals(Object obj) {
		return core.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return core.hashCode();
	}
	
	@Override
	public String toString() {
		return core.toString();
	}

	@Override
	public Section getSection0() {
		return core.section0;
	}

	@Override
	public Section getSection1() {
		return core.section1;
	}
	
	void setDefaultBodyStyle() {
		core.setDefaultBodyStyle();
	}

	public void setDefaultHeaderStyle() {
		core.setDefaultHeaderStyle();
	}

	public void addListener(int type, Listener listener) {
		core.addListener(type, listener);
	}

	public void removeListener(int type, Listener listener) {
		core.removeListener(type, listener);
	}

	public String getText(Number index0, Number index1) {
		return core.getText(index0, index1);
	}

	public void setBackground(Number index0, Number index1, Color color) {
		core.setBackground(index0, index1, color);
	}

	public Color getBackground(Number index0, Number index1) {
		return core.getBackground(index0, index1);
	}

	public void setDefaultBackground(Color color) {
		core.setDefaultBackground(color);
	}

	public Color getDefaultBackground() {
		return core.getDefaultBackground();
	}

	public void setForeground(Number index0, Number index1, Color color) {
		core.setForeground(index0, index1, color);
	}

	public Color getForeground(Number index0, Number index1) {
		return core.getForeground(index0, index1);
	}

	public void setDefaultForeground(Color color) {
		core.setDefaultForeground(color);
	}

	public Color getDefaultForeground() {
		return core.getDefaultForeground();
	}

	public Rectangle getBounds() {
		return core.getBounds();
	}

	public boolean isVisible() {
		return core.isVisible();
	}

	public void setSelectionForeground(Color color) {
		core.setSelectionForeground(color);
	}

	public Color getSelectionForeground() {
		return core.getSelectionForeground();
	}

	public void setSelectionBackground(Color color) {
		core.setSelectionBackground(color);
	}

	public Color getSelectionBackground() {
		return core.getSelectionBackground();
	}

	
	
	/*------------------------------------------------------------------------
	 * Selection
	 */

	/**
     * Returns <code>true</code> if selection is enabled, false otherwise.
     * @return the selection enabled state
	 */
	public boolean isSelectionEnabled() {
		return core.isSelectionEnabled();
	}

	/**
     * Enables cell selection if the argument is <code>true</code>, 
     * or disables it otherwise.
     *
	 * @param selectionEnabled the new selection ability state.
	 */
	
	public void setSelectionEnabled(boolean isSelectionEnabled) {
		core.setSelectionEnabled(isSelectionEnabled);
	}

	public boolean isSelected(Number index0, Number index1) {
		return core.isSelected(index0, index1);
	}

	public void setSelected(Number start0, Number end0, Number start1,
			Number end1, boolean selected) {
		core.setSelected(start0, end0, start1, end1, selected);
	}

	public void setSelectedAll(boolean selected) {
		core.setSelectedAll(selected);
	}

	public BigInteger getSelectedCount() {
		return core.getSelectedCount();
	}

	public NumberPairSequence getSelected() {
		return core.getSelected();
	}

	public BigInteger getSelectionCount() {
		return core.getSelectionCount();
	}

	public void addPainter(Painter painter) {
		core.addPainter(painter);
	}

	public void addPainter(int index, Painter painter) {
		core.addPainter(index, painter);
	}

	public void setPainter(int index, Painter painter) {
		core.setPainter(index, painter);
	}

	public void replacePainter(Painter painter) {
		core.replacePainter(painter);
	}

	public void removePainter(int index) {
		core.removePainter(index);
	}

	public int indexOfPainter(String name) {
		return core.indexOfPainter(name);
	}

	public Painter getPainter(String name) {
		return core.getPainter(name);
	}

	public int getPainterCount() {
		return core.getPainterCount();
	}

	public Painter get(int index) {
		return core.get(index);
	}

	/**
	 * Returns the number of selected cells in this zone.
	 * <p>
	 * If the cell selection is disabled the it always returns a 
	 * {@link BigIntegerNumber} with zero value.
	 * 
	 * @return {@link BigIntegerNumber} with the count of selected cells
	 */
	
	
	Iterator<Cell<N0, N1>> getSelectedIterator() {
		return new ImmutableIterator<Cell<N0, N1>>() {
			NumberPairSequence seq = new NumberPairSequence(core.cellSelection.copy());
			private boolean next;
			@Override
			public boolean hasNext() {
				next = seq.next();
				return next;
			}

			@Override
			public Cell<N0, N1> next() {
				return next ? new Cell (seq.index0(), seq.index1()) : null;
			}
		};
	}
	
	static class Cell<N0, N1> {
		public N0 index0;
		public N1 index1;
		public Cell(N0 index0, N1 index1) {
			this.index0 = index0;
			this.index1 = index1;
		}
	}

	

}
