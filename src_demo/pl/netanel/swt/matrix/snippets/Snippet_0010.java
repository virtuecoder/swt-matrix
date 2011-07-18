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
 */
public class Snippet_0010 {
	public static void main(String[] args) {
		Shell shell = (new Shell());
    shell.setText("Draw custom background for the whole matrix, resize the window!");
		shell.setBounds(400, 200, 800, 400);
		shell.setLayout(new FillLayout());
		Display display = shell.getDisplay();
		
		Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);
		matrix.getAxis1().getBody().setCount(40);
		matrix.getAxis0().getBody().setCount(1000);
		
		matrix.getBody().setDefaultForeground(matrix.getBackground());
		matrix.getBody().setDefaultBackground(null);
		
		// Create an image
		final Image image2 = new Image(display, 100, 100);
		GC gc = new GC(image2);
		gc.setBackground(display.getSystemColor(SWT.COLOR_BLUE));
		gc.setAntialias(SWT.ON);
		gc.fillOval(0, 0, 100, 100);
		gc.dispose();
		
		matrix.addPainter(0, new Painter<Integer, Integer>("background") {
			public void paint(Integer index0, Integer index1, int x, int y, int width, int height) {
				Rectangle r = image2.getBounds();
				x += align(SWT.RIGHT, 50, r.width, width);
				y += align(SWT.CENTER, 0, r.height, height);
				
				gc.drawImage(image2, x, y);
			};
		});
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		image2.dispose();
	}

}
