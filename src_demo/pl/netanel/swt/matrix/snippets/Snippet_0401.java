package pl.netanel.swt.matrix.snippets;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;

/**
 * Edit text.
 */
public class Snippet_0401 {
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setLayout(new FillLayout());
		
		// Model
		int count0 = 10, count1 = 4;
		final ArrayList<ArrayList> data = new ArrayList();
		for (int i = 0; i < count0; i++) {
			ArrayList row = new ArrayList();
			for (int j = 0; j < count1; j++) {
				row.add("" + i + ", " + j);
			}	
			data.add(row);
		}
		
		// Matrix
		final Matrix<Integer, Integer> matrix = new Matrix(shell, SWT.NONE);
		matrix.getAxis0().getBody().setCount(count0);
		matrix.getAxis1().getBody().setCount(count1);
		matrix.getBody().replacePainter(new Painter("cells", Painter.SCOPE_CELLS_HORIZONTALLY) {
			@Override
			public void paint(Number index0, Number index1, int x, int y, int width, int height) {
				text = data.get(index0.intValue()).get(index1.intValue()).toString();
				super.paint(index0, index1, x, y, width, height);
			}
		});
		
		
		// Edit
		final Text text = new Text(matrix, SWT.BORDER);
		text.setLocation(-1000, -1000);
		text.setVisible(false);
		
		// Editor events
		Listener listener = new Listener() {
			int[] editing = new int[2];
			@Override
			public void handleEvent(Event e) {
				// Text event
				if (e.widget == text) {
					// Apply on Enter
					if (e.type == SWT.KeyDown && e.keyCode == SWT.CR) {
						apply();
					}
					// Cancel on Escape
					else if (e.type == SWT.KeyDown && (e.keyCode == SWT.ESC || e.keyCode == SWT.TAB)) {
						text.setVisible(false);
						text.setLocation(-1000, -1000);
					}
				} 
				
				// Matrix event
				else {
					// Apply when clicked outside of the text editor
					if (e.type == SWT.MouseDown && text.isVisible()) {
						Rectangle bounds = text.getBounds();
						if (!bounds.contains(e.x, e.y)) {
							apply();
						}
					}		
					// Open text editor on F2 or double click
					else if (e.keyCode == SWT.F2 || e.type == SWT.MouseDoubleClick) {
						int i = matrix.getAxis0().getFocusItem().getIndex().intValue();
						int j = matrix.getAxis1().getFocusItem().getIndex().intValue();
						if (matrix.getBody().getCellBounds(i, j).contains(e.x, e.y)) {
							editing[0] = i; editing[1] = j;
							text.setText(data.get(i).get(j).toString());
							Rectangle bounds = matrix.getBody().getCellBounds(i, j);
							bounds.x--; bounds.y--; bounds.width += 2; bounds.height += 2;
							text.setBounds(bounds);
							text.setVisible(true);
							text.setFocus();
						}
					}
				}
			}

			private void apply() {
				data.get(editing[0]).set(editing[1], text.getText());
				text.setVisible(false);
				matrix.redraw();
			}
		};
		text.addListener(SWT.KeyDown, listener);
		text.addListener(SWT.FocusOut, listener);
		matrix.addListener(SWT.MouseDown, listener);
		matrix.addListener(SWT.MouseDoubleClick, listener);
		matrix.addListener(SWT.KeyDown, listener);
		
		matrix.getAxis0().getHeader().setVisible(true);
		matrix.getAxis1().getBody().setDefaultResizable(true);
		
		shell.setBounds(400, 200, 400, 300);
		shell.open();
		Display display = shell.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
