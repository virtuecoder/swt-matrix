package pl.netanel.swt.matrix.snippets;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Section;

/**
 * Custom column packing.
 */
public class Snippet_0020 {
	
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setLayout(new GridLayout(2, false));
		shell.setText("Double click on the right edge of a column header in order to resize it");
		final ArrayList<Object[]> data = new ArrayList<Object[]>();
		data.add(new Object[] {"Task 1", "2001-02-02"});
		data.add(new Object[] {"Task 2", "2001-04-02"});
		data.add(new Object[] {"Task 3", "2001-01-22"});
		data.add(new Object[] {"Task 4", "2001-02-02"});
		data.add(new Object[] {"Task 5", "2008-03-12"});
		
		final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.V_SCROLL);
		matrix.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		Axis<Integer> axis0 = matrix.getAxis0();
		final Section<Integer> body0 = axis0.getBody();
		body0.setCount(data.size());
		body0.setDefaultResizable(true);
		
		axis0.getHeader().setVisible(true);
		
		Axis<Integer> axis1 = matrix.getAxis1();
		axis1.getBody().setCount(2);
		axis1.getBody().setDefaultResizable(true);
		axis1.getHeader().setDefaultCellWidth(16);
		axis1.getHeader().setVisible(true);
		
		matrix.getBody().replacePainter(
		  new Painter<Integer, Integer>("cells", Painter.SCOPE_CELLS_HORIZONTALLY) {
		    @Override
		    public String getText(Integer index0, Integer index1) {
		      return data.get(index0.intValue())[index1.intValue()].toString();
		    }
		  }
	  );

		matrix.getColumnHeader().replacePainter(
		  new Painter<Integer, Integer>("cells", Painter.SCOPE_CELLS_HORIZONTALLY) {
		    @Override
		    public String getText(Integer index0, Integer index1) {
		      return index1.intValue() == 0 ? "Task" : "Completion date";
		    }
		  }
	  );
		
		shell.setBounds(400, 200, 600, 300);
		shell.open();
		Display display = shell.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
