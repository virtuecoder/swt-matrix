package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.AxisPointer;
import pl.netanel.swt.matrix.Zone;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;

/**
 * Current row gradient highlighter.
 */
public class Snippet_0015 {
	public static void main(String[] args) {
		Shell shell = (new Shell());
    shell.setText("Current row gradient highlighter");
		shell.setBounds(400, 200, 600, 400);
		shell.setLayout(new FillLayout());
		final Display display = shell.getDisplay();
		
		final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);
		
		matrix.getAxisY().getBody().setCount(10);
		matrix.getAxisX().getBody().setCount(4);
		
		final Zone<Integer, Integer> body = matrix.getBody();
		body.getPainter("row lines").setEnabled(false);
		body.getPainter("column lines").setEnabled(false);
		
		body.addPainter(0, 
		  new Painter<Integer, Integer>("gradient row background", Painter.SCOPE_ROW_CELLS) {
  		  int matrixWidth;
  		  @Override
  		  protected boolean init() {
  		    gc.setForeground(display.getSystemColor(SWT.COLOR_RED));
  		    gc.setBackground(display.getSystemColor(SWT.COLOR_LIST_BACKGROUND));
  		    gc.setAdvanced(true);
  		    if (gc.getAdvanced()) gc.setAlpha(127);
  		    matrixWidth = matrix.getClientArea().width;
  		    return true;
  		  }
  		  @Override
  		  public void clean() {
  		    gc.setAlpha(255);
  		  }
  
  		  @Override
  		  public void paint(Integer indexX, Integer indexY, int x, int y, int width, int height) {
  		    Axis<Integer> axisY = matrix.getAxisY();
  		    AxisPointer<Integer> focusItem = axisY.getFocusItem();
  		    if (body.getSectionY().equals(focusItem.getSection()) &&
  		      indexY.equals(focusItem.getIndex())) 
  		    {
  		      gc.fillGradientRectangle(0, y - 1, matrixWidth, height + 2, false);
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
