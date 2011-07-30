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
import pl.netanel.swt.matrix.AxisItem;
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
		
		Axis<Integer> axisY = matrix.getAxisY();
		final Section<Integer> bodyY = axisY.getBody();
		bodyY.setCount(list.size());
		bodyY.setDefaultResizable(true);
		axisY.getHeader().setVisible(true);
		
		Axis<Integer> axisX = matrix.getAxisX();
		axisX.getBody().setCount(2);
		axisX.getHeader().setDefaultCellWidth(16);
		axisX.getHeader().setVisible(true);
		
		matrix.getBody().replacePainter(
		  new Painter<Integer, Integer>(Painter.NAME_CELLS, Painter.SCOPE_CELLS) {
		    @Override
		    public String getText(Integer indexX, Integer indexY) {
		      String value = list.get(indexY.intValue());
		      return indexX.intValue() == 0 
		        ? value
	          : Integer.toString(value.length());
		    }
		  }
		);
		
		matrix.getHeaderX().replacePainter(
		  new Painter<Integer, Integer>(Painter.NAME_CELLS, Painter.SCOPE_CELLS) {
		    @Override
		    public String getText(Integer indexX, Integer indexY) {
		      return indexX.intValue() == 0 ? "Value" : "Length";
		    }
		  }
	  );
		
		Button add = new Button(shell, SWT.PUSH);
		add.setText("Insert");
		add.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				AxisItem<Integer> focusItem = matrix.getAxisY().getFocusItem();
				int focusIndex = focusItem == null ? 0 : focusItem.getIndex();
				list.add(focusIndex, Integer.toString(++counter));
				bodyY.insert(focusIndex, 1);
				matrix.refresh();
				matrix.setFocus();
			
			}
		});
		
		Button remove = new Button(shell, SWT.PUSH);
		remove.setText("Delete");
		remove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			  AxisItem<Integer> focusItem = matrix.getAxisY().getFocusItem();
			  if (focusItem != null) {
			    Integer index = focusItem.getIndex();
          bodyY.delete(index, index);
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
