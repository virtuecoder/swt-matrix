package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.AxisItem;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Zone;

/**
 * Draw custom focus cell marker.
 */
public class Snippet_0011 {
	public static void main(String[] args) {
		Shell shell = (new Shell());
		shell.setText("Draw custom focus cell marker");
		shell.setBounds(400, 200, 800, 400);
		shell.setLayout(new FillLayout());
		final Display display = shell.getDisplay();
		
		final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);
		matrix.getAxis1().getBody().setCount(40);
		matrix.getAxis0().getBody().setCount(1000);
		
		int index = matrix.indexOfPainter("focus cell");
		matrix.setPainter(index, new Painter<Integer, Integer>("focus cell") {
			@Override
			public void paint(Integer index0, Integer index1, int x, int y, int width, int height) {
				// Get bounds of the focus cell 
				AxisItem<Integer> item0 = matrix.getAxis0().getFocusItem();
				AxisItem<Integer> item1 = matrix.getAxis1().getFocusItem();
				Zone<Integer, Integer> zone = matrix.getZone(item0.getSection(), item1.getSection());
				if (zone == null) return;
				Rectangle r = zone.getCellBounds(item0.getIndex(), item1.getIndex());
				
				// Draw rounded rectangle with a changed color
				gc.setForeground(display.getSystemColor(SWT.COLOR_LIST_SELECTION));
				gc.drawRoundRectangle(r.x-1, r.y-1, r.width+1, r.height+1, 5, 5);
			}
		});
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
