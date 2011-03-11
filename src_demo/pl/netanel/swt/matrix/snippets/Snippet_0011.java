package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.Resources;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.painter.BorderPainter;

/**
 * Draw custom current navigation cell marker.
 * 
 * @author Jacek Kolodziejczyk created 04-03-2011
 */
public class Snippet_0011 {
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setBounds(400, 200, 400, 300);
		shell.setLayout(new FillLayout());
		shell.setText("Resize me !");
		Display display = shell.getDisplay();
		
		Matrix matrix = new Matrix(shell, SWT.NONE);
		matrix.getAxis1().getBody().setCount(40);
		matrix.getAxis0().getBody().setCount(1000);
		
		
		matrix.setNavigationPainter(new BorderPainter().radius(5)
				.color(Resources.getColor(SWT.COLOR_LIST_SELECTION)));
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
