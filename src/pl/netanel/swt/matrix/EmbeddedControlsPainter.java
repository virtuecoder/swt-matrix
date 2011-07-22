package pl.netanel.swt.matrix;

import java.util.HashMap;
import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
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
	HashMap<Number, HashMap<Number, Control>> controls;
	boolean needsPainting;
	private final Listener focusInListener;
	private final ControlListener controlListener;

	public EmbeddedControlsPainter(final ZoneEditor<N0, N1> editor) {
		super(Painter.NAME_EMBEDDED_CONTROLS, Painter.SCOPE_CELLS_HORIZONTALLY);
		this.editor = editor;
		controls = new HashMap<Number, HashMap<Number, Control>>();
		
		// Set the focus cell in the matrix when the cell control gets focus  
		focusInListener = new Listener() {
			@Override
			public void handleEvent(Event e) {
				Matrix<N0, N1> matrix = getMatrix();
				ZoneEditorData data = editor.getData(e.widget);
				matrix.model.setSelected(false, true); 
				matrix.axis0.setFocusItem(editor.zone.getSection0(), (N0) data.index0);
				matrix.axis1.setFocusItem(editor.zone.getSection1(), (N1) data.index1);
				matrix.redraw();
			}
		};
		
		// Set the repainting flag when the axis item gets resized or moved
		controlListener = new ControlListener() {
			@Override
			public void controlMoved(ControlEvent e) {
				needsPainting = true;
			}
			@Override
			public void controlResized(ControlEvent e) {
				needsPainting = true;
			}
		};
		editor.zone.getSection0().addControlListener(controlListener);
		editor.zone.getSection1().addControlListener(controlListener);
	}
	
	
	@Override
	protected boolean init() {
		if (!needsPainting) return false;
		clearControls();
		return true;
	}


  void clearControls() {
    for (Entry<Number, HashMap<Number, Control>> entry: controls.entrySet()) {
			for (Control control: entry.getValue().values()) {
				control.dispose();
			}
		}	
		controls.clear();
  }
	
	@Override
	public void paint(N0 index0, N1 index1, int x, int y, int width, int height) {
		if (editor.hasEmbeddedControl(index0, index1)) {
			Control control = editor.addControl(index0, index1);
			control.addListener(SWT.FocusIn, focusInListener );
			control.addListener(SWT.Selection, new Listener() {
        @Override public void handleEvent(Event event) {
          getMatrix().forceFocus();
        }
      });
			
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
		if (needsPainting) {
			getMatrix().forceFocus();
		needsPainting = false;
		}
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
	protected void setMatrix(final Matrix matrix) {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				needsPainting = true;
			}
		};
		matrix.layout0.callbacks.add(r);
		matrix.layout1.callbacks.add(r);
		
		super.setMatrix(matrix);
	}
}
