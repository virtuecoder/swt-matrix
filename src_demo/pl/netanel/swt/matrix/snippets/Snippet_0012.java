package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Zone;

/**
 * Change the vertical line color.
 * 
 * @author Jacek Kolodziejczyk created 04-03-2011
 */
public class Snippet_0012 {
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setBounds(400, 200, 400, 300);
		shell.setLayout(new FillLayout());
		final Display display = shell.getDisplay();
		
		Matrix matrix = new Matrix(shell, SWT.NONE);
		matrix.getAxis1().getBody().setCount(4);
		matrix.getAxis0().getBody().setCount(10);
		Zone body = matrix.getBody();
		
		body.painter.replace(new Painter("column lines", Painter.COLUMN_LINE) {
			@Override
			public void paint(int x, int y, int width, int height) {
				gc.setBackground(display.getSystemColor(SWT.COLOR_BLUE));
				gc.fillRectangle(x, y, width, height);
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
