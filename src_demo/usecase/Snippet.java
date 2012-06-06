package usecase;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;

public class Snippet {
  public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setLayout(new FillLayout());


		Axis<Integer> axisX = new Axis<Integer>(Integer.class, 2, 0, 1);
		Axis<Integer> axisY = new Axis<Integer>(Integer.class, 2, 0, 1);

		final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(
		    shell, SWT.V_SCROLL | SWT.H_SCROLL | SWT.SINGLE | SWT.FULL_SELECTION, axisX, axisY);

		axisX.getBody().setCount(4);
		axisX.getBody().setDefaultCellWidth(50);
		axisX.getHeader().setDefaultCellWidth(40);
		axisX.getHeader().setVisible(true);

		axisY.getBody().setCount(10);
		axisY.getHeader().setVisible(true);

		final Color fg = shell.getDisplay().getSystemColor(SWT.COLOR_BLUE);
		matrix.getBody().getPainter(Painter.NAME_CELLS).style.foreground = fg;

//		shell.addFocusListener(new FocusAdapter() {
//      @Override
//      public void focusGained(FocusEvent arg0) {
//        matrix.redraw();
//      }
//    });

shell.addListener(SWT.Activate, new Listener() {
  @Override
  public void handleEvent(Event arg0) {
    matrix.redraw();
  }
});

		Button button = new Button(shell, SWT.PUSH);
		button.addSelectionListener(new SelectionAdapter() {
		  @Override
		  public void widgetSelected(SelectionEvent arg0) {
		    matrix.getBody().replacePainterPreserveStyle(new Painter<Integer, Integer>(Painter.NAME_CELLS) {
		      @Override
		      public void setup(Integer indexX, Integer indexY) {
		        super.setup(indexX, indexY);
		        style.foreground = null;
		      }
		      @Override
		      public void setupSpatial(Integer indexX, Integer indexY) {
		        text = "asfasdf";
		      }
		    });
		    //matrix.redraw();
		  }
		});


		shell.setBounds(200, 20, 1024, 568);
		shell.open();
		Display display = shell.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
