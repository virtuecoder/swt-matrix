package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Zone;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Section;

/**
 * Altering row background.
 */
public class _0014_AlteringRowBackground {
	public static void main(String[] args) {
		Shell shell = (new Shell());
		shell.setText(title);
		shell.setBounds(400, 200, 600, 400);
		shell.setLayout(new FillLayout());
		final Display display = shell.getDisplay();
		
		final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);
		
		Section<Integer> bodyX = matrix.getAxisX().getBody();
		bodyX.setDefaultLineWidth(0);
		bodyX.setCount(4);
		bodyX.setDefaultCellWidth(50);
		
		Section<Integer> bodyY = matrix.getAxisY().getBody();
		bodyY.setDefaultLineWidth(0);
		bodyY.setCount(10);
		

		final Zone<Integer, Integer> body = matrix.getBody();
		// To additionally hide the lines
		body.getPainter(Painter.NAME_LINES_X).setEnabled(false);
		body.getPainter(Painter.NAME_LINES_Y).setEnabled(false);
		
		body.addPainter(0, 
		  new Painter<Integer, Integer>("alter row background", Painter.SCOPE_CELLS_ITEM_Y) {
			@Override
			protected boolean init() {
				super.init();
				gc.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
				return true;
			}
			@Override
			public void paint(Integer indexX, Integer indexY, int x, int y, int width, int height) {
				if (indexY.intValue() % 2 == 1) {
					gc.fillRectangle(x, y, width, height);
				}
			}
			
			@Override
			public void clean() {
				gc.setBackground(matrix.getBackground());
			}
		});
		
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	// Meta data
	static final String title = "Altering row background";
	static final String instructions = "";
	static final String code = "0014";
}