package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;

/**
 * Draw custom background for the whole matrix. 
 */
public class _0010_DrawCustomBackgroundForTheWholeMatrix_ResizeTheWindow_ {
  static Point point = new Point(-1000, -1000);
  
	public static void main(String[] args) {
		Shell shell = new Shell();
    shell.setText(title);
		shell.setBounds(400, 200, 600, 400);
		shell.setLayout(new FillLayout());
		Display display = shell.getDisplay();
		
		final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);
		matrix.getAxisX().getBody().setCount(40);
		matrix.getAxisY().getBody().setCount(1000);
		
		// Create an image
		final Image image2 = new Image(display, 100, 100);
		GC gc = new GC(image2);
		gc.setBackground(display.getSystemColor(SWT.COLOR_YELLOW));
		gc.setAntialias(SWT.ON);
		gc.fillOval(0, 0, 100, 100);
		gc.dispose();
		
		matrix.addListener(SWT.MouseMove, new Listener() {
      @Override
      public void handleEvent(Event event) {
        point.x = event.x;
        point.y = event.y;
        matrix.redraw();
      }
    });
		
		matrix.addListener(SWT.MouseExit, new Listener() {
		  @Override
		  public void handleEvent(Event event) {
		    point.x = -1000;
		    point.y = -1000;
		    matrix.redraw();
		  }
		});
		
		matrix.addPainter(0, new Painter<Integer, Integer>("background") {
			@Override
      public void paint(int x, int y, int width, int height) {
				gc.drawImage(image2, point.x - 50, point.y - 50);
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


	// Meta data
	static final String title = "Draw custom background for the whole matrix, resize the window!";
	static final String instructions = "";
	static final String code = "0010";
}