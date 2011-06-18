package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.AxisItem;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Zone;

/**
 * Current row gradient highlighter.
 */
public class Snippet_0015 {
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setBounds(400, 200, 400, 300);
		shell.setLayout(new FillLayout());
		final Display display = shell.getDisplay();
		
		final Matrix matrix = new Matrix(shell, SWT.NONE);
		
		matrix.getAxis0().getBody().setCount(10);
		matrix.getAxis1().getBody().setCount(4);
		
		final Zone body = matrix.getBody();
		body.getPainter("row lines").setEnabled(false);
		body.getPainter("column lines").setEnabled(false);
		
		body.addPainter(0, new Painter("gradient row background", Painter.SCOPE_ROW_CELLS) {
			int matrixWidth;
			@Override
			protected boolean init() {
				gc.setForeground(display.getSystemColor(SWT.COLOR_RED));
				gc.setBackground(display.getSystemColor(SWT.COLOR_LIST_BACKGROUND));
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
				AxisItem focusItem = axis0.getFocusItem();
				if (body.getSection0().equals(focusItem.getSection()) &&
					index0.equals(focusItem.getIndex())) 
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
