package pl.netanel.swt.matrix;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import pl.netanel.swt.Resources;

public class MatrixModel {

	private final AxisModel model1;
	private final AxisModel model0;
	private final ArrayList<Zone> zones;
	private Zone body; //, columnHeader, rowHeader;

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
					zone = new Zone(section0, section1);
					this.zones.add(zone);

					if (section0.equals(body0) && section1.equals(body1)) {
						model1.getBody().setDefaultCellWidth(50);
					}
				}
				if (section0.equals(body0) && section1.equals(body1)) {
					body = zone;
//					zone.cellPainters.add(new DefaultBackgroundPainter(zone, null, 
//							BackgroundPainter.getDefaultBodySelectionColor()));
					zone.cellPainters.add(new ModelPainter(zone));
				}
				else if (section0.equals(header0) && section1.equals(header1)) {
					zone.cellPainters.add(new DefaultBackgroundPainter(zone, 
							Resources.getColor(SWT.COLOR_WIDGET_BACKGROUND), null));
					Color color = Resources.getColor(SWT.COLOR_WIDGET_DARK_SHADOW);
					zone.linePainters0.get(LinePainter.class).setBackground(color);
					zone.linePainters1.get(LinePainter.class).setBackground(color);
				}
				else if (section0.equals(header0) || section1.equals(header1)) {
					setHeaderStyle(zone);
				}
			}	
		}
	}
	
	public MatrixModel() {
		this(new AxisModel(), new AxisModel());
	}
	
	
	private Zone setHeaderStyle(Zone zone) {
		DefaultBackgroundPainter backgroundPainter = new DefaultBackgroundPainter(zone,
				Resources.getColor(SWT.COLOR_WIDGET_BACKGROUND), 
				Painter.getDefaultHeaderSelectionColor());
		
		zone.cellPainters.add(backgroundPainter);
		zone.cellPainters.add(new ModelPainter(zone));
		Color color = Resources.getColor(SWT.COLOR_WIDGET_DARK_SHADOW);
		zone.linePainters0.get(LinePainter.class).setBackground(color);
		zone.linePainters1.get(LinePainter.class).setBackground(color);
		return zone;
	}
	
	
	public Zone getBody() {
		return body;
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

}
