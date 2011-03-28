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
 * Gap between cells like HTML table cellspacing attribute. Hide lines.
 * 
 * @author Jacek Kolodziejczyk created 04-03-2011
 */
public class Snippet_0013 {
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setBounds(400, 200, 400, 300);
		shell.setLayout(new FillLayout());
		final Display display = shell.getDisplay();
		
		Matrix matrix = new Matrix(shell, SWT.NONE);
		matrix.getAxis0().getHeader().setVisible(true);
		
		Section rowBody = matrix.getAxis0().getBody();
		rowBody.setDefaultLineWidth(3);
		rowBody.setCount(10);
		
		Section colBody = matrix.getAxis1().getBody();
		colBody.setDefaultLineWidth(3);
		colBody.setCount(4);
		
		// Column header painting
		Zone columnHeader = matrix.getColumneHeader();
		columnHeader.painter.get("row lines").setEnabled(false);
		columnHeader.painter.get("column lines").setEnabled(false);
		columnHeader.painter.add(new Painter("cell border", Painter.CELL) {
			@Override
			public void paint(int x, int y, int width, int height) {
				gc.setForeground(display.getSystemColor(SWT.COLOR_WIDGET_DARK_SHADOW));
				gc.drawRectangle(x - 1, y - 1, width + 1, height + 1);
			}
		});
		columnHeader.setDefaultBackground(matrix.getBackground());
		
		// Body painting
		Zone body = matrix.getBody();
		body.painter.get("row lines").setEnabled(false);
		body.painter.get("column lines").setEnabled(false);
		body.painter.add(new Painter("cell border", Painter.CELL) {
			@Override
			public void paint(int x, int y, int width, int height) {
				gc.setForeground(display.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
				gc.drawRectangle(x - 1, y - 1, width + 1, height + 1);
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
