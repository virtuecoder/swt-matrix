package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;

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
		matrix.getAxis1().getBody().setCount(40);
		matrix.getAxis0().getBody().setCount(1000);
		
		matrix.getBody().setDefaultForeground(matrix.getBackground());
		
		// Create an image
		final Image image = new Image(display, 100, 100);
		GC gc = new GC(image);
		gc.setBackground(display.getSystemColor(SWT.COLOR_BLUE));
		gc.setAntialias(SWT.ON);
		gc.fillOval(0, 0, 100, 100);
		gc.dispose();
		
		matrix.addPainter(0, new Painter("background") {
			public void paint(Number index0, Number index1, int x, int y, int width, int height) {
				Rectangle r = image.getBounds();
				x = align(SWT.RIGHT, 50, x, r.width, width);
				y = align(SWT.CENTER, 0, y, r.height, height);
				
				gc.drawImage(image, x, y);
			};
		});
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		image.dispose();
	}

}
