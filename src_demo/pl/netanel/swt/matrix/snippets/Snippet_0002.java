package pl.netanel.swt.matrix.snippets;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Section;

/**
 * Add / remove model items.
 * 
 * @author Jacek Kolodziejczyk created 03-03-2011
 */
public class Snippet_0002 {
	static int counter;
	
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setLayout(new GridLayout(2, false));
		
		final ArrayList<String> list = new ArrayList<String>();
		list.add(Integer.toString(1));
		list.add(Integer.toString(2));
		list.add(Integer.toString(3));
		list.add(Integer.toString(4));
		list.add(Integer.toString(5));
		list.add(Integer.toString(6));
		counter = 6;
		
		final Matrix matrix = new Matrix(shell, SWT.V_SCROLL);
		matrix.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		Axis rowAxis = matrix.getAxis0();
		final Section rowBody = rowAxis.getBody();
		rowBody.setCount(list.size());
		rowAxis.getBody().setDefaultResizable(true);
		rowAxis.getHeader().setVisible(true);
		
		Axis colAxis = matrix.getAxis1();
		colAxis.getBody().setCount(2);
		colAxis.getHeader().setDefaultCellWidth(16);
		colAxis.getHeader().setVisible(true);
		
		matrix.getBody().replacePainter(new Painter("cells", Painter.SCOPE_CELLS_HORIZONTALLY) {
			@Override
			public void paint(Number index0, Number index1, int x, int y, int width, int height) {
				String value = list.get(index0.intValue());
				text = index1.intValue() == 0 
					? value
					: Integer.toString(value.length());
				super.paint(index0, index1, x, y, width, height);
			}
		});
		
		matrix.getColumnHeader().replacePainter(new Painter("cells", Painter.SCOPE_CELLS_HORIZONTALLY) {
			@Override
			public void paint(Number index0, Number index1, int x, int y, int width, int height) {
				text = index1.intValue() == 0 ? "Value" : "Length";
				super.paint(index0, index1, x, y, width, height);
			}
		});
		
		rowBody.setCellWidth(2, 4, 25);
		rowBody.setResizable(2, 4, false);
		
		Button add = new Button(shell, SWT.PUSH);
		add.setText("Insert");
		add.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Number focusIndex = matrix.getAxis0().getFocusItem().getIndex();
				list.add(focusIndex.intValue(), Integer.toString(++counter));
				rowBody.insert(focusIndex, 1);
				matrix.refresh();
				matrix.setFocus();
			}
		});
		
		Button remove = new Button(shell, SWT.PUSH);
		remove.setText("Delete");
		remove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Iterator<Number[]> it = matrix.getBody().getSelectedExtentIterator();
				while (it.hasNext()) {
					Number[] next = it.next();
					rowBody.delete(next[0], next[1]);
					for (int i = next[0].intValue(); i >= next[1].intValue(); i--) {
						list.remove(i);
					}
				}
				matrix.refresh();
				matrix.setFocus();
			}
		});
		
		shell.setBounds(400, 200, 400, 300);
		shell.open();
		Display display = shell.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
