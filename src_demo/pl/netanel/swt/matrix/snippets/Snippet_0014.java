package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.Resources;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.Zone;
import pl.netanel.swt.matrix.painter.BackgroundPainter;
import pl.netanel.swt.matrix.painter.LinePainter;

/**
 * Altering line background.
 * 
 * @author Jacek Kolodziejczyk created 04-03-2011
 */
public class Snippet_0014 {
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setBounds(400, 200, 400, 300);
		shell.setLayout(new FillLayout());
		Display display = shell.getDisplay();
		
		Matrix matrix = new Matrix(shell, SWT.NONE);
		
		
		Section colBody = matrix.getAxis1().getBody();
		colBody.setDefaultLineWidth(0);
		colBody.setCount(4);
		colBody.setDefaultCellWidth(50);
		
		Section rowBody = matrix.getAxis0().getBody();
		rowBody.setDefaultLineWidth(0);
		rowBody.setCount(10);
		

		final Zone body = matrix.getBody();
		// To additionally hide the lines
		body.linePainters0.get(LinePainter.class).setEnabled(false);
		body.linePainters1.get(LinePainter.class).setEnabled(false);
		
		body.cellPainters.add(0, new BackgroundPainter(Resources.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW)) {
			boolean skip, alter;
			Rectangle bounds;
			@Override
			protected void init() {
				super.init();
				bounds = body.getBounds();
			}
			@Override
			public void beforePaint(Number index0, Number index1) {
				skip = index1.intValue() > 0;
				alter = index0.intValue() % 2 == 1;
			}
			@Override
			public void paint(int x, int y, int width, int height) {
				if (skip) return;
				if (alter) {
					gc.fillRectangle(x, y, bounds.width, height);
				}
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
