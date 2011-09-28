package usecase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Extent;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;

/**
 * Simplest matrix.
 */
public class S0000_OrderSample {
  
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setText(title);
		shell.setLayout(new FillLayout());
		
		final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.H_SCROLL);
		matrix.getAxisX().getBody().setCount(5);
		matrix.getAxisY().getBody().setCount(10);
		
		matrix.getAxisX().getHeader().setVisible(true);
		matrix.getAxisY().getHeader().setVisible(true);
		
		matrix.getHeaderX().replacePainterPreserveStyle(new Painter<Integer, Integer>(Painter.NAME_CELLS) {
		  @Override
		  public void setupSpatial(Integer indexX, Integer indexY) {
		    switch (indexX) {
		    case 0: text = "id"; break;
		    case 1: text = "long column"; break;
		    default: text = indexX.toString();
		    }
		  }
		});
		
		matrix.getBody().getPainter(Painter.NAME_CELLS).style.hasWordWraping = true;
		
		// One way to set the order
		matrix.getAxisX().getBody().setOrder(Arrays.asList(new Integer[] { 1, 2, 0, 3, 4 }).iterator());
		
		// Another way to set the order
		List<Extent<Integer>> extents = new ArrayList<Extent<Integer>>();
		extents.add(Extent.create(1, 2));
		extents.add(Extent.create(0, 0));
		extents.add(Extent.create(3, 4));
		matrix.getAxisX().getBody().setOrderExtents(extents.iterator());		
				
		shell.setBounds(400, 200, 600, 400);
		shell.open();
		Display display = shell.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	// Meta data
	static final String title = "Simplest matrix";
	static final String instructions = "";
	static final String code = "0001";
}