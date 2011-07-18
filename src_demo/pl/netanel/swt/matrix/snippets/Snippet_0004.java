package pl.netanel.swt.matrix.snippets;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.AxisPointer;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Section;

/**
 * Add / remove model items.
 */
public class Snippet_0004 {
	static int counter;
	
	public static void main(String[] args) {
		Shell shell = (new Shell());
		shell.setText("Add / remove model items");
		shell.setLayout(new GridLayout(2, false));
		
		final ArrayList<String> list = new ArrayList<String>();
		list.add(Integer.toString(1));
		list.add(Integer.toString(2));
		list.add(Integer.toString(3));
		list.add(Integer.toString(4));
		list.add(Integer.toString(5));
		list.add(Integer.toString(6));
		counter = 6;
		
		final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.V_SCROLL);
		matrix.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		Axis<Integer> axis0 = matrix.getAxis0();
		final Section<Integer> body0 = axis0.getBody();
		body0.setCount(list.size());
		body0.setDefaultResizable(true);
		axis0.getHeader().setVisible(true);
		
		Axis<Integer> axis1 = matrix.getAxis1();
		axis1.getBody().setCount(2);
		axis1.getHeader().setDefaultCellWidth(16);
		axis1.getHeader().setVisible(true);
		
		matrix.getBody().replacePainter(
		  new Painter<Integer, Integer>("cells", Painter.SCOPE_CELLS_HORIZONTALLY) {
		    @Override
		    public String getText(Integer index0, Integer index1) {
		      String value = list.get(index0.intValue());
		      return index1.intValue() == 0 
		        ? value
	          : Integer.toString(value.length());
		    }
		  }
		);
		
		matrix.getColumnHeader().replacePainter(
		  new Painter<Integer, Integer>("cells", Painter.SCOPE_CELLS_HORIZONTALLY) {
		    @Override
		    public String getText(Integer index0, Integer index1) {
		      return index1.intValue() == 0 ? "Value" : "Length";
		    }
		  }
	  );
		
		Button add = new Button(shell, SWT.PUSH);
		add.setText("Insert");
		add.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				AxisPointer<Integer> focusItem = matrix.getAxis0().getFocusItem();
				int focusIndex = focusItem == null ? 0 : focusItem.getIndex();
				list.add(focusIndex, Integer.toString(++counter));
				body0.insert(focusIndex, 1);
				matrix.refresh();
				matrix.setFocus();
			
			}
		});
		
		Button remove = new Button(shell, SWT.PUSH);
		remove.setText("Delete");
		remove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			  AxisPointer<Integer> focusItem = matrix.getAxis0().getFocusItem();
			  if (focusItem != null) {
			    Integer index = focusItem.getIndex();
          body0.delete(index, index);
			    list.remove(index);
			  }
				matrix.refresh();
				matrix.setFocus();
			}
		});
		
		shell.setBounds(400, 200, 600, 400);
		shell.open();
		Display display = shell.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
