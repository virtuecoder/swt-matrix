package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Section;

/**
 * Image painting
 */
public class Snippet_0018 {
	
	private static final int ROW_COUNT = 8;
	private static final int COLUMN_COUNT = 4;

	public static void main(String[] args) {
		Shell shell = (new Shell());
    shell.setText("Image painting");
		shell.setBounds(400, 200, 600, 400);
		shell.setLayout(new FillLayout());
		Display display = shell.getDisplay();
		
		// Create image
		Image image = new Image(display, 16, 16);
		GC gc = new GC(image);
		gc.setBackground(display.getSystemColor(SWT.COLOR_BLUE));
		gc.setAntialias(SWT.ON);
		gc.fillOval(0, 0, 16, 16);
		gc.dispose();
		
		// Image data model
		final Image[][] images = new Image[ROW_COUNT][];
		images[1] = new Image[COLUMN_COUNT];
		images[1][1] = image;
		images[5] = new Image[COLUMN_COUNT];
		images[5][3] = image;
		images[2] = new Image[COLUMN_COUNT];
		images[2][0] = image;
		
		Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.V_SCROLL);
		Section<Integer> body1 = matrix.getAxis1().getBody();
		body1.setCount(COLUMN_COUNT);
		body1.setDefaultCellWidth(50);
		
		matrix.getAxis0().getBody().setCount(ROW_COUNT);
		
		matrix.getBody().replacePainter(
		  new Painter<Integer, Integer>("cells", Painter.SCOPE_CELLS_HORIZONTALLY) {
			@Override
			public void paint(Integer index0, Integer index1, int x, int y, int width, int height) {
				text = index0.toString() + ", " + index1;
				Image[] row = images[index0.intValue()];
				image = row == null ? null : row[index1.intValue()];
				imageAlignX = index1.intValue() == 1 ? SWT.RIGHT : SWT.LEFT;
				super.paint(index0, index1, x, y, width, height);
			}
		});
		
		shell.setBounds(400, 200, 600, 400);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
