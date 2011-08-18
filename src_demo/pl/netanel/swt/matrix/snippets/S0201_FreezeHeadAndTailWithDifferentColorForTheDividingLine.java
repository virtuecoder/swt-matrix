package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Section;

/**
 * Freeze head and tail with different color for the dividing line 
 */
public class S0201_FreezeHeadAndTailWithDifferentColorForTheDividingLine {
	
	public static void main(String[] args) {
		Shell shell = new Shell();
    shell.setText(title);
		shell.setBounds(400, 200, 600, 400);
		shell.setLayout(new GridLayout(2, false));
		final Display display = shell.getDisplay();
		
		final Point head = new Point(0, 0);
		final Point tail = new Point(0, 0);
		
		final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.H_SCROLL | SWT.V_SCROLL);
		matrix.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		final Axis<Integer> axisY = matrix.getAxisY();
		final Axis<Integer> axisX = matrix.getAxisX();
		
		final Section<Integer> bodyX = axisX.getBody();
		bodyX.setCount(40);
		bodyX.setDefaultCellWidth(50);
		
		Section<Integer> bodyY = axisY.getBody();
		bodyY.setCount(100);

		Button add = new Button(shell, SWT.PUSH);
		add.setText("Freeze head");
		add.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				head.y = axisY.getViewportPosition(axisY.getFocusItem());
				head.x = axisX.getViewportPosition(axisX.getFocusItem());
				axisY.setFrozenHead(head.y); 
				axisX.setFrozenHead(head.x);
				matrix.refresh();
				matrix.setFocus();
			}
		});
		
		Button remove = new Button(shell, SWT.PUSH);
		remove.setText("Freeze tail");
		remove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tail.y = axisY.getViewportItemCount() - 
					axisY.getViewportPosition(axisY.getFocusItem()) - 1;
				tail.x = axisX.getViewportItemCount() - 
					axisX.getViewportPosition(axisX.getFocusItem()) - 1;
				axisY.setFrozenTail(tail.y);
				axisX.setFrozenTail(tail.x);
				matrix.refresh();
				matrix.setFocus();
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
	static final String title = "Freeze head and tail with different color for the dividing line";
	static final String instructions = "";
	static final String code = "0201";
}