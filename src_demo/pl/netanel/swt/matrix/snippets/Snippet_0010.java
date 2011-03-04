package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.ImagePainter;
import pl.netanel.swt.matrix.Matrix;

/**
 * Draw custom background for the whole matrix.
 * 
 * @author Jacek Kolodziejczyk created 04-03-2011
 */
public class Snippet_0010 {
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setBounds(400, 200, 400, 300);
		shell.setLayout(new FillLayout());
		shell.setText("Resize me !");
		Display display = shell.getDisplay();
		
		Matrix matrix = new Matrix(shell, SWT.NONE);
		matrix.getModel1().getBody().setCount(40);
		matrix.getModel0().getBody().setCount(100);
		matrix.getModel().getBody().setDefaultForeground(matrix.getBackground());
		
		// Create an image
		Image image = new Image(display, 100, 100);
		GC gc = new GC(image);
		gc.setBackground(display.getSystemColor(SWT.COLOR_BLUE));
		gc.fillOval(0, 0, 100, 100);
		gc.dispose();
		
		matrix.setBackgroundPainter(new ImagePainter(image)
			.align(SWT.RIGHT, SWT.CENTER).margin(50, 0));
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		image.dispose();
	}
}
