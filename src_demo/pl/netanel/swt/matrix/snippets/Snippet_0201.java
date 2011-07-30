package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
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
public class Snippet_0201 {
	static Section<Integer> freezeHeadSectionY, freezeHeadSectionX, freezeTailSectionY, freezeTailSectionX;
	static Number freezeHeadIndexY, freezeHeadIndexX, freezeTailIndexY, freezeTailIndexX;
	static int headY, headX, tailY, tailX;
	
	public static void main(String[] args) {
		Shell shell = (new Shell());
    shell.setText("Freeze head and tail with different color for the dividing line");
		shell.setBounds(400, 200, 600, 400);
		shell.setLayout(new GridLayout(2, false));
		final Display display = shell.getDisplay();
		
		
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
			public void paint(Integer indexX, Integer indexY, int x, int y, int width, int height) {
				Color background = gc.getBackground();
				gc.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
				if (headX > 0) {
					int[] bound = axisX.getLineBound(axisX.getItemAt(headX));
					gc.fillRectangle(bound[0], y, bound[1], height);
				} 
				int viewportItemCount = axisX.getViewportItemCount();
				if (tailX > 0 ) {
					int[] bound = axisX.getLineBound(axisX.getItemAt(viewportItemCount - tailX));
					gc.fillRectangle(bound[0], y, bound[1], height);
				}
				if (headY > 0) {
				  int[] bound = axisY.getLineBound(axisY.getItemAt(headY));
				  gc.fillRectangle(x, bound[0], width, bound[1]);
				} 
				viewportItemCount = axisY.getViewportItemCount();
				if (tailY > 0 ) {
				  int[] bound = axisY.getLineBound(axisY.getItemAt(viewportItemCount - tailY));
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
				headY = axisY.getViewportPosition(axisY.getFocusItem());
				headX = axisX.getViewportPosition(axisX.getFocusItem());
				axisY.freezeHead(headY);
				axisX.freezeHead(headX);
				matrix.refresh();
				matrix.setFocus();
			}
		});
		
		Button remove = new Button(shell, SWT.PUSH);
		remove.setText("Freeze tail");
		remove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tailY = axisY.getViewportItemCount() - 
					axisY.getViewportPosition(axisY.getFocusItem()) - 1;
				tailX = axisX.getViewportItemCount() - 
					axisX.getViewportPosition(axisX.getFocusItem()) - 1;
				axisY.freezeTail(tailY);
				axisX.freezeTail(tailX);
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
}
