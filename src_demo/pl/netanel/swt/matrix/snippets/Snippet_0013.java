package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.Resources;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.Zone;
import pl.netanel.swt.matrix.painter.BorderPainter;
import pl.netanel.swt.matrix.painter.LinePainter;

/**
 * Gap between cells.
 * 
 * @author Jacek Kolodziejczyk created 04-03-2011
 */
public class Snippet_0013 {
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setBounds(400, 200, 400, 300);
		shell.setLayout(new FillLayout());
		Display display = shell.getDisplay();
		
		Matrix matrix = new Matrix(shell, SWT.NONE);
		matrix.getModel0().getHeader().setVisible(true);
		
		Section rowBody = matrix.getModel().getModel0().getBody();
		rowBody.setDefaultLineWidth(3);
		rowBody.setCount(10);
		
		Section colBody = matrix.getModel().getModel1().getBody();
		colBody.setDefaultLineWidth(3);
		colBody.setCount(4);
		
		// Column header painting
		Zone columnHeader = matrix.getModel().getColumneHeader();
		columnHeader.linePainters0.get(LinePainter.class).setEnabled(false);
		columnHeader.linePainters1.get(LinePainter.class).setEnabled(false);
		columnHeader.cellPainters.add(new BorderPainter().color(Resources.getColor(SWT.COLOR_WIDGET_DARK_SHADOW)));
		columnHeader.setDefaultBackground(matrix.getBackground());
		
		// Body painting
		Zone body = matrix.getModel().getBody();
		body.linePainters0.get(LinePainter.class).setEnabled(false);
		body.linePainters1.get(LinePainter.class).setEnabled(false);
		body.cellPainters.add(new BorderPainter().color(Resources.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW)));
		
		// Uncomment the next line to change the current cell marker to extend outside
		// matrix.setNavigationPainter(new BorderPainter(2).offset(-2));
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
