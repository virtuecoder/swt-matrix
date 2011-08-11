package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Section;

/**
 * Cell background calculated
 */
public class _0017_CellBackgroundCalculated {
	
	public static void main(String[] args) {
		Shell shell = new Shell();
    shell.setText(title);
		shell.setBounds(400, 200, 600, 400);
		shell.setLayout(new FillLayout());
		Display display = shell.getDisplay();
		
		Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.V_SCROLL);
		matrix.getBody().setSelectionEnabled(false);
		
		Section<Integer> bodyX = matrix.getAxisX().getBody();
		Section<Integer> bodyY = matrix.getAxisY().getBody();
		
		bodyX.setCount(8);
		bodyY.setCount(8);
		
		bodyX.setDefaultCellWidth(16);
		
		final Color color = display.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
		matrix.getBody().replacePainter(
		  new Painter<Integer, Integer>(Painter.NAME_CELLS, Painter.SCOPE_CELLS) {
		    @Override
        public void setup(Integer indexX, Integer indexY) {
		      background = indexY.intValue() % 2 + indexX.intValue() % 2 == 1 ? color : null;
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

	// Meta data
	static final String title = "Cell background calculated";
	static final String instructions = "";
	static final String code = "0017";
}