package pl.netanel.swt.matrix;

import java.util.HashMap;
import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import pl.netanel.swt.matrix.ZoneEditor.ZoneEditorData;

/**
 * Creates embedded controls rather then drawing directly if the layout has been modified. 
 * 
 * @author Jacek Kolodziejczyk created 14-06-2011
 */
class EmbeddedControlsPainter<N0 extends Number, N1 extends Number> extends Painter<N0, N1> {
	private final ZoneEditor editor;
	private HashMap<Number, HashMap<Number, Control>> controls;
	boolean layoutModified;
	private Listener listener;

	public EmbeddedControlsPainter(final ZoneEditor<N0, N1> editor) {
		super("editor controls", Painter.SCOPE_CELLS_HORIZONTALLY);
		this.editor = editor;
		controls = new HashMap<Number, HashMap<Number, Control>>();
		listener = new Listener() {
			@Override
			public void handleEvent(Event e) {
				Matrix matrix = getMatrix();
				ZoneEditorData data = editor.getData(e.widget);
				matrix.axis0.setFocusItem(editor.zone.getSection0(), data.index0);
				matrix.axis1.setFocusItem(editor.zone.getSection1(), data.index1);
				matrix.setFocus();
			}
		};
	}
	
	@Override
	protected boolean init() {
		if (!layoutModified) return false;
		for (Entry<Number, HashMap<Number, Control>> entry: controls.entrySet()) {
			for (Control control: entry.getValue().values()) {
				control.dispose();
			}
		}	
		controls.clear();
		return true;
	}
	
	@Override
	public void paint(N0 index0, N1 index1, int x, int y, int width, int height) {
		if (editor.hasEmbeddedControl(index0, index1)) {
			Control control = editor.addControl(index0, index1);
			control.addListener(SWT.KeyUp, listener );
			control.addListener(SWT.MouseUp, listener );
			
//			control.addListener(SWT.Selection, listener );
			HashMap<Number, Control> row = controls.get(index0);
			if (row == null) {
				controls.put(index0, row = new HashMap<Number, Control>());
			}
			row.put(index1, control);
		}
	}
	
	@Override
	public void clean() {
		if (layoutModified) {
			getMatrix().setFocus();
		}
		layoutModified = false;
		
	}
	
	public Control getControl(N0 index0, N1 index1) {
		HashMap<Number, Control> row = controls.get(index0);
		if (row == null) {
			return null;
		}
		else {
			return row.get(index1);
		}
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
}
