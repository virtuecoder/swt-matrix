package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Resources;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.Zone;

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
		
		Axis axis0 = matrix.getAxis0();
		Axis axis1 = matrix.getAxis1();
		
		Section colBody = axis1.getBody();
		colBody.setCount(4);
		colBody.setDefaultCellWidth(50);
		
		Section rowBody = axis0.getBody();
		rowBody.setCount(10);
		
		final Zone body = matrix.getBody();
		body.getPainter("row lines").setEnabled(false);
		body.getPainter("column lines").setEnabled(false);
		
		body.addPainter(0, new Painter("gradient row background", Painter.SCOPE_ROW_CELLS) {
			int matrixWidth;
			@Override
			protected boolean init() {
				gc.setForeground(Resources.getColor(SWT.COLOR_RED));
				gc.setBackground(Resources.getColor(SWT.COLOR_LIST_BACKGROUND));
				gc.setAdvanced(true);
				if (gc.getAdvanced()) gc.setAlpha(127);
				matrixWidth = matrix.getClientArea().width;
				return true;
			}
			@Override
			public void clean() {
				gc.setAlpha(255);
			}
			
			@Override
			public void paint(Number index0, Number index1, int x, int y, int width, int height) {
				Axis axis0 = matrix.getAxis0();
				if (body.getSection0().equals(axis0.getFocusSection()) &&
					index0.equals(axis0.getFocusIndex())) 
				{
					gc.fillGradientRectangle(0, y - 1, matrixWidth, height + 2, false);
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
