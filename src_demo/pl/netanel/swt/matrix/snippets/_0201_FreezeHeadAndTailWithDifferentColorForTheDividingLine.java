package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
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
 * Freeze head and tail with different color for the dividing line 
 */
public class _0201_FreezeHeadAndTailWithDifferentColorForTheDividingLine {
	
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

		matrix.addPainter(new Painter<Integer, Integer>("freeze lines") {
			@Override
			public void paint(int x, int y, int width, int height) {
				Color background = gc.getBackground();
				gc.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
				if (head.x > 0) {
					int[] bound = axisX.getLineBound(axisX.getItemAt(head.x));
					gc.fillRectangle(bound[0], y, bound[1], height);
				} 
				int viewportItemCount = axisX.getViewportItemCount();
				if (tail.x > 0 ) {
					int[] bound = axisX.getLineBound(axisX.getItemAt(viewportItemCount - tail.x));
					gc.fillRectangle(bound[0], y, bound[1], height);
				}
				if (head.y > 0) {
				  int[] bound = axisY.getLineBound(axisY.getItemAt(head.y));
				  gc.fillRectangle(x, bound[0], width, bound[1]);
				} 
				viewportItemCount = axisY.getViewportItemCount();
				if (tail.y > 0 ) {
				  int[] bound = axisY.getLineBound(axisY.getItemAt(viewportItemCount - tail.y));
				  gc.fillRectangle(x, bound[0], width, bound[1]);
				}
				gc.setBackground(background);
			}
		});
		
		Button add = new Button(shell, SWT.PUSH);
		add.setText("Freeze head");
		add.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				head.y = axisY.getViewportPosition(axisY.getFocusItem());
				head.x = axisX.getViewportPosition(axisX.getFocusItem());
				axisY.freezeHead(head.y);
				axisX.freezeHead(head.x);
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
				axisY.freezeTail(tail.y);
				axisX.freezeTail(tail.x);
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