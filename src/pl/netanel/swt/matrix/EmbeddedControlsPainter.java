package pl.netanel.swt.matrix;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * Creates embedded controls rather then drawing directly if the layout has been modified. 
 * 
 * @author Jacek Kolodziejczyk created 14-06-2011
 */
class EmbeddedControlsPainter extends Painter {

	private static final String EDITED_CELL = "edited cell";
	
	private final ZoneEditor editor;
	private ArrayList<Control> controls;
	boolean layoutModified;
	private Listener listener;

	public EmbeddedControlsPainter(final ZoneEditor editor) {
		super("editor controls", Painter.SCOPE_CELLS_HORIZONTALLY);
		this.editor = editor;
		controls = new ArrayList<Control>();
		listener = new Listener() {
			@Override
			public void handleEvent(Event e) {
				Matrix matrix = getMatrix();
				Number[] data = (Number[]) e.widget.getData(EDITED_CELL);
				matrix.axis0.setFocusItem(editor.zone.getSection0(), data[0]);
				matrix.axis1.setFocusItem(editor.zone.getSection1(), data[1]);
				matrix.setFocus();
			}
		};
	}
	
	@Override
	protected boolean init() {
		if (!layoutModified) return false;
		for (Control control: controls) {
			control.dispose();
		}	
		return true;
	}
	
	@Override
	public void paint(Number index0, Number index1, int x, int y, int width, int height) {
		if (editor.hasEmbededControl(index0, index1)) {
			System.out.println("paint");
			Control control = editor.edit(index0, index1);
			controls.add(control);
			control.moveAbove(getMatrix());
			control.addListener(SWT.KeyUp, listener );
			control.addListener(SWT.MouseUp, listener );
			
//			control.addListener(SWT.Selection, listener );
			control.setData(EDITED_CELL, new Number[] {index0, index1});
		}
	}
	
	@Override
	public void clean() {
		if (layoutModified) {
			getMatrix().setFocus();
		}
		layoutModified = false;
		
	}
	
	@Override
	protected void setMatrix(Matrix matrix) {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				layoutModified = true;
			}
		};
		matrix.layout0.callbacks.add(r);
		matrix.layout1.callbacks.add(r);
		super.setMatrix(matrix);
	}

	public boolean isEmbedded(Control control) {
		return control.getData(EDITED_CELL) != null;
	}
	
}
