package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;

/**
 * Change the line style.
 */
public class Snippet_0012 {
	public static void main(String[] args) {
		Shell shell = (new Shell());
		shell.setText("Change the line style");
		shell.setBounds(400, 200, 600, 400);
		shell.setLayout(new FillLayout());
		final Display display = shell.getDisplay();
		
		Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);
		matrix.getAxisX().getBody().setCount(4);
		matrix.getAxisY().getBody().setCount(10);
		
		
		matrix.getBody().replacePainter(
		  new Painter<Integer, Integer>("row lines", Painter.SCOPE_HORIZONTAL_LINES) {
		    @Override
		    public void paint(Integer indexX, Integer indexY, int x, int y, int width, int height) {
		      switch (indexY.intValue()) {
		      case 1:
		        gc.setBackground(display.getSystemColor(SWT.COLOR_BLUE));
		        gc.fillRectangle(x, y, width, height);
		        break;
		      case 2:
		        gc.setLineDash(new int[] {4, 2});
		        gc.drawLine(x, y, x + width - 1, y + height - 1);
		        gc.setLineDash(null);
		        break;
		      case 3:
		        gc.setLineWidth(5);
		        gc.setLineCap(SWT.CAP_ROUND);
		        gc.drawLine(x, y, x + width - 1, y + height - 1);
		        gc.setLineWidth(1);
		        break;
		      default:
		        gc.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		        gc.fillRectangle(x, y, width, height);
		      }
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
}
