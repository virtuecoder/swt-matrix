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
 * Altering row background.
 */
public class Snippet_0014 {
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setBounds(400, 200, 400, 300);
		shell.setLayout(new FillLayout());
		final Display display = shell.getDisplay();
		
		final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);
		
		Section<Integer> body1 = matrix.getAxis1().getBody();
		body1.setDefaultLineWidth(0);
		body1.setCount(4);
		body1.setDefaultCellWidth(50);
		
		Section<Integer> body0 = matrix.getAxis0().getBody();
		body0.setDefaultLineWidth(0);
		body0.setCount(10);
		

		final Zone<Integer, Integer> body = matrix.getBody();
		// To additionally hide the lines
		body.getPainter("row lines").setEnabled(false);
		body.getPainter("column lines").setEnabled(false);
		
		body.addPainter(0, 
		  new Painter<Integer, Integer>("alter row background", Painter.SCOPE_ROW_CELLS) {
			@Override
			protected boolean init() {
				super.init();
				gc.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
				return true;
			}
			@Override
			public void paint(Integer index0, Integer index1, int x, int y, int width, int height) {
				if (index0.intValue() % 2 == 1) {
					gc.fillRectangle(x, y, width, height);
				}
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
