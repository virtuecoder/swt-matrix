package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.Resources;
import pl.netanel.swt.matrix.AxisModel;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.MatrixModel;
import pl.netanel.swt.matrix.MutableNumber;
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
		
		final AxisModel rowModel = new AxisModel(new Section(int.class) {
			// To additionally hide the lines
			@Override
			public int getLineWidth(MutableNumber index) {
				return 0;
			}
		});
		
		AxisModel colModel = new AxisModel(new Section(int.class) {
			// To additionally hide the lines
			@Override
			public int getLineWidth(MutableNumber index) {
				return 0;
			}
		});
		
		rowModel.getBody().setCount(10);
		colModel.getBody().setCount(4);
		colModel.getBody().setDefaultCellWidth(50);
		
		MatrixModel model = new MatrixModel(rowModel, colModel);

		final Zone body = model.getBody();
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
			public void beforePaint(MutableNumber index0, MutableNumber index1) {
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
		
		new Matrix(shell, SWT.NONE, model);
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
