package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;

/**
 * Draw custom current cell marker.
 * 
 * @author Jacek Kolodziejczyk created 04-03-2011
 */
public class Snippet_0011 {
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setBounds(400, 200, 400, 300);
		shell.setLayout(new FillLayout());
		final Display display = shell.getDisplay();
		
		final Matrix matrix = new Matrix(shell, SWT.NONE);
		matrix.getAxis1().getBody().setCount(40);
		matrix.getAxis0().getBody().setCount(1000);
		
		int index = matrix.painter.indexOf("focus cell");
		matrix.painter.set(index, new Painter("focus cell") {
			@Override
			public void paint(Number index0, Number index1, int x, int y, int width, int height) {
				// Get bounds of the focus cell 
				Axis axis0 = matrix.getAxis0();
				Axis axis1 = matrix.getAxis1();
				Rectangle r = matrix.getCellBounds(
						axis0.getFocusSection(), axis0.getFocusIndex(), 
						axis1.getFocusSection(), axis1.getFocusIndex() );
				
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
