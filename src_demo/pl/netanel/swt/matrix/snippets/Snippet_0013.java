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
 * Gap between cells like HTML table cell spacing attribute. Hide lines.
 */
public class Snippet_0013 {
	public static void main(String[] args) {
		Shell shell = (new Shell());
    shell.setText("Gap between cells like HTML table cellspacing attribute. Hide lines");
		shell.setBounds(400, 200, 600, 400);
		shell.setLayout(new FillLayout());
		final Display display = shell.getDisplay();
		
		Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);
		matrix.getAxis0().getHeader().setVisible(true);
		
		Section<Integer> body0 = matrix.getAxis0().getBody();
		body0.setDefaultLineWidth(3);
		body0.setCount(10);
		
		Section<Integer> body1 = matrix.getAxis1().getBody();
		body1.setDefaultLineWidth(3);
		body1.setCount(4);
		
		// Column header painting
		Zone<Integer, Integer> columnHeader = matrix.getColumnHeader();
		columnHeader.getPainter("row lines").setEnabled(false);
		columnHeader.getPainter("column lines").setEnabled(false);
		columnHeader.addPainter(
		  new Painter<Integer, Integer>("cell border", Painter.SCOPE_CELLS_HORIZONTALLY) {
			@Override
			public void paint(Integer index0, Integer index1, int x, int y, int width, int height) {
				gc.setForeground(display.getSystemColor(SWT.COLOR_WIDGET_DARK_SHADOW));
				gc.drawRectangle(x - 1, y - 1, width + 1, height + 1);
			}
		});
		columnHeader.setDefaultBackground(null);
		
		// Body painting
		Zone<Integer, Integer> body = matrix.getBody();
		body.getPainter("row lines").setEnabled(false);
		body.getPainter("column lines").setEnabled(false);
		body.addPainter(
		  new Painter<Integer, Integer>("cell border", Painter.SCOPE_CELLS_HORIZONTALLY) {
		    @Override
		    public void paint(Integer index0, Integer index1, int x, int y, int width, int height) {
		      gc.setForeground(display.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		      gc.drawRectangle(x - 1, y - 1, width + 1, height + 1);
		    }
		  }
	  );
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
