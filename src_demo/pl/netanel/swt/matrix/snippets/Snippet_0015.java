package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.Resources;
import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
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
		
		Section colBody = matrix.getAxis1().getBody();
		colBody.setCount(4);
		colBody.setDefaultCellWidth(50);
		
		Section rowBody = matrix.getAxis0().getBody();
		rowBody.setCount(10);
		
		matrix.painter.get("focus cell").setEnabled(false);

		final Zone body = matrix.getBody();
		body.painter.get("row lines").setEnabled(false);
		body.painter.get("column lines").setEnabled(false);
		
		body.painter.add(0, new Painter("alter row background", Painter.SCOPE_ROW_CELLS) {
			@Override
			protected boolean init() {
				gc.setForeground(Resources.getColor(SWT.COLOR_RED));
				gc.setBackground(Resources.getColor(SWT.COLOR_LIST_BACKGROUND));
				gc.setAdvanced(true);
				if (gc.getAdvanced()) gc.setAlpha(127);
				return true;
			}
			@Override
			public void clean() {
				gc.setAlpha(255);
			}

			@Override
			public boolean beforePaint(Number index0, Number index1) {
				Axis axis0 = matrix.getAxis0();
				return body.getSection0().equals(axis0.getCurrentSection()) &&
					index0.equals(axis0.getCurrentIndex());
			}
			
			@Override
			public void paint(int x, int y, int width, int height) {
				gc.fillGradientRectangle(x, y, width, height, false);
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
