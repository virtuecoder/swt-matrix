package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.Resources;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Zone;
import pl.netanel.swt.matrix.painter.LinePainter;

/**
 * Change the line color.
 * 
 * @author Jacek Kolodziejczyk created 04-03-2011
 */
public class Snippet_0012 {
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setBounds(400, 200, 400, 300);
		shell.setLayout(new FillLayout());
		Display display = shell.getDisplay();
		
		Matrix matrix = new Matrix(shell, SWT.NONE);
		matrix.getModel1().getBody().setCount(4);
		matrix.getModel0().getBody().setCount(10);
		Zone body = matrix.getModel().getBody();
		body.linePainters1.get(LinePainter.class).color(Resources.getColor(SWT.COLOR_BLUE));
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
