package pl.netanel.swt.matrix;

import java.math.BigInteger;
import java.util.Iterator;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

class ZoneClient<N0 extends Number, N1 extends Number> extends Zone{
	final Zone core;
	
	public ZoneClient(Zone<N0, N1> zone) {
		core = zone;
		section0 = new SectionClient(zone.section0);
		section1 = new SectionClient(zone.section1);
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

	void setDefaultHeaderStyle(Painter cellsPainter) {
		core.setDefaultHeaderStyle(cellsPainter);
	}

	@Override
	public Rectangle getCellBounds(Number index0, Number index1) {
		if (index0 == null || index1 == null) return null;
		section0.checkIndex(index0, section0.getCount(), "index0");
		section1.checkIndex(index1, section1.getCount(), "index1");
		return core.getCellBounds(index0, index1);
	}
	
//	public void addListener(int type, Listener listener) {
//		core.addListener(type, listener);
//	}
//
//	public void removeListener(int type, Listener listener) {
//		core.removeListener(type, listener);
//	}

	public void setDefaultBackground(Color color) {
		core.setDefaultBackground(color);
	}

	public Color getDefaultBackground() {
		return core.getDefaultBackground();
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

//	public boolean isVisible() {
//		return core.isVisible();
//	}

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
		section0.checkIndex(index0, section0.getCount(), "index0");
		section1.checkIndex(index1, section1.getCount(), "index1");
		return core.isSelected(index0, index1);
	}

	public void setSelected(Number start0, Number end0, 
			Number start1, Number end1, boolean selected) 
	{
		section0.checkRange(start0, end0, section0.getCount());
		section1.checkRange(start1, end1, section1.getCount());
		core.setSelected(start0, end0, start1, end1, selected);
	}

	public void setSelectedAll(boolean selected) {
		core.setSelectedAll(selected);
	}

	public BigInteger getSelectedCount() {
		return core.getSelectedCount();
	}

	@Override
	public Iterator getSelectedExtentIterator() {
		return core.getSelectedExtentIterator();
	}
	
	@Override
	public Iterator getSelectedIterator() {
		return core.getSelectedIterator();
	}
	
	@Override
	public Section getSectionUnchecked0() {
		return core.getSectionUnchecked0();
	}

	@Override
	public Section getSectionUnchecked1() {
		return core.getSectionUnchecked1();
	}
	
	public BigInteger getSelectionCount() {
		return core.getSelectionCount();
	}
	
	@Override
	public void addSelectionListener(SelectionListener listener) {
		core.addSelectionListener(listener);
	}
	
	@Override
	public void removeSelectionListener(SelectionListener listener) {
		core.removeSelectionListener(listener);
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

	public Painter removePainter(int index) {
		return core.removePainter(index);
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

	public Painter getPainter(int index) {
		return core.getPainter(index);
	}

	@Override
	void setMatrix(Matrix matrix) {
		core.setMatrix(matrix);
	}

	public void setSelected(Number index0, Number index1, boolean state) {
		core.setSelected(index0, index1, state);
	}

	public void bind(int commandId, int eventType, int code) {
		core.bind(commandId, eventType, code);
	}

	public void unbind(int commandId, int eventType, int code) {
		core.unbind(commandId, eventType, code);
	}

	@Override
	void backupSelection() {
		core.backupSelection();
	}
	
	@Override
	void delete(int axisIndex, Section section, Number start, Number end) {
		core.delete(axisIndex, section, start, end);
	}
	
	@Override
	void insert(int axisIndex, Section section, Number target, Number count) {
		core.insert(axisIndex, section, target, count);
	}
	
	@Override
	void paint(GC gc, Layout layout0, Layout layout1, Frozen dock0, Frozen dock1) {
		core.paint(gc, layout0, layout1, dock0, dock1);
	}
	
	@Override
	void restoreSelection() {
		core.restoreSelection();
	}
	
	@Override
	void setBounds(int x, int y, int width, int height) {
		core.setBounds(x, y, width, height);
	}

	
}
