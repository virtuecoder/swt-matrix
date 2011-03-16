package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.Resources;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.MatrixModel;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.Zone;
import pl.netanel.swt.matrix.painter.BackgroundPainter;
import pl.netanel.swt.matrix.painter.LinePainter;

/**
 * Current row gradient highlighter.
 * 
 * @author Jacek Kolodziejczyk created 04-03-2011
 */
public class Snippet_0015 {
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setBounds(400, 200, 400, 300);
		shell.setLayout(new FillLayout());
		Display display = shell.getDisplay();
		
		final Matrix matrix = new Matrix(shell, SWT.NONE);
		
		MatrixModel model = matrix.getModel();
		
		Section colBody = model.getModel1().getBody();
		colBody.setCount(4);
		colBody.setDefaultCellWidth(50);
		
		Section rowBody = model.getModel0().getBody();
		rowBody.setCount(10);
		

		final Zone body = model.getBody();
		// To additionally hide the lines
		body.linePainters0.get(LinePainter.class).setEnabled(false);
		body.linePainters1.get(LinePainter.class).setEnabled(false);
		
		body.cellPainters.add(0, new BackgroundPainter(Resources.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW)) {
			boolean skip;
			Rectangle bounds;
			@Override
			protected void init() {
				super.init();
				bounds = matrix.getClientArea();
				gc.setForeground(Resources.getColor(SWT.COLOR_RED));
				gc.setBackground(Resources.getColor(SWT.COLOR_LIST_BACKGROUND));
				gc.setAdvanced(true);
				if (gc.getAdvanced()) gc.setAlpha(127);
			}
			@Override
			public void beforePaint(Number index0, Number index1) {
				skip = index1.intValue() > 0 || 
					index0.intValue() != matrix.getAxis0().getNavigationIndex().intValue();
			}
			@Override
			public void paint(int x, int y, int width, int height) {
				if (skip) return;
				gc.fillGradientRectangle(x, y, bounds.width, height, false);
			}
		});
		
		matrix.getNavigationPainter().setEnabled(false);
		
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
