package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Section;

/**
 * Cell background calculated
 * 
 * @author Jacek Kolodziejczyk created 03-03-2011
 */
public class Snippet_0017 {
	
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setBounds(400, 200, 400, 300);
		shell.setLayout(new FillLayout());
		Display display = shell.getDisplay();
		
		Matrix matrix = new Matrix(shell, SWT.V_SCROLL);
		matrix.getAxis0().getBody().setCount(8);
		Section body1 = matrix.getAxis1().getBody();
		body1.setCount(8);
		body1.setDefaultCellWidth(16);
		
		final Color color = display.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
		matrix.getBody().replacePainter(new Painter("cells", Painter.SCOPE_CELLS_HORIZONTALLY) {
			@Override
			public void paint(Number index0, Number index1, int x, int y, int width, int height) {
				background = index0.intValue() % 2 + index1.intValue() % 2 == 1 ? color : null;
				super.paint(index0, index1, x, y, width, height);
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
