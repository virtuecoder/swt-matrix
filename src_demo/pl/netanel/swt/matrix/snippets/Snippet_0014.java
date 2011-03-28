package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.Zone;

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
		final Display display = shell.getDisplay();
		
		final Matrix matrix = new Matrix(shell, SWT.NONE);
		
		
		Section colBody = matrix.getAxis1().getBody();
		colBody.setDefaultLineWidth(0);
		colBody.setCount(4);
		colBody.setDefaultCellWidth(50);
		
		Section rowBody = matrix.getAxis0().getBody();
		rowBody.setDefaultLineWidth(0);
		rowBody.setCount(10);
		

		final Zone body = matrix.getBody();
		// To additionally hide the lines
		body.painter.get("row lines").setEnabled(false);
		body.painter.get("column lines").setEnabled(false);
		
		body.painter.add(0, new Painter("alter row background", Painter.SCOPE_ROW_CELLS) {
			@Override
			protected boolean init() {
				super.init();
				gc.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
				return true;
			}
			@Override
			public boolean beforePaint(Number index0, Number index1) {
				return index0.intValue() % 2 == 1;
			}
			@Override
			public void paint(int x, int y, int width, int height) {
				gc.fillRectangle(x, y, width, height);
			}
			
			@Override
			public void clean() {
				gc.setBackground(matrix.getBackground());
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
