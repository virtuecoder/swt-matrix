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
 * 
 * @author Jacek Kolodziejczyk created 04-03-2011
 */
public class Snippet_0201 {
	static Section freezeHeadSection0, freezeHeadSection1, freezeTailSection0, freezeTailSection1;
	static Number freezeHeadIndex0, freezeHeadIndex1, freezeTailIndex0, freezeTailIndex1;
	static int head0, head1, tail0, tail1;
	
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setBounds(400, 200, 400, 300);
		shell.setLayout(new GridLayout(2, false));
		final Display display = shell.getDisplay();
		
		
		final Matrix matrix = new Matrix(shell, SWT.H_SCROLL | SWT.V_SCROLL);
		matrix.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		final Axis axis0 = matrix.getAxis0();
		final Axis axis1 = matrix.getAxis1();
		
		final Section colBody = axis1.getBody();
		colBody.setCount(40);
		colBody.setDefaultCellWidth(50);
		
		Section rowBody = axis0.getBody();
		rowBody.setCount(100);

		matrix.addPainter(new Painter("freeze lines") {
			@Override
			public void paint(Number index0, Number index1, int x, int y, int width, int height) {
				Color background = gc.getBackground();
				gc.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
				if (head0 > 0) {
					int[] bound = axis0.getLineBound(head0);
					gc.fillRectangle(x, bound[0], width, bound[1]);
				} 
				int viewportItemCount = axis0.getViewportItemCount();
				if (tail0 > 0 ) {
					int[] bound = axis0.getLineBound(viewportItemCount - tail0);
					gc.fillRectangle(x, bound[0], width, bound[1]);
				}
				if (head1 > 0) {
					int[] bound = axis1.getLineBound(head1);
					gc.fillRectangle(bound[0], y, bound[1], height);
				} 
				viewportItemCount = axis1.getViewportItemCount();
				if (tail1 > 0 ) {
					int[] bound = axis1.getLineBound(viewportItemCount - tail1);
					gc.fillRectangle(bound[0], y, bound[1], height);
				}
				gc.setBackground(background);
			}
		});
		
		Button add = new Button(shell, SWT.PUSH);
		add.setText("Freeze head");
		add.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				head0 = axis0.getViewportPosition(axis0.getFocusItem());
				head1 = axis1.getViewportPosition(axis1.getFocusItem());
				axis0.freezeHead(head0);
				axis1.freezeHead(head1);
				matrix.refresh();
				matrix.setFocus();
			}
		});
		
		Button remove = new Button(shell, SWT.PUSH);
		remove.setText("Freeze tail");
		remove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tail0 = axis0.getViewportItemCount() - 
					axis0.getViewportPosition(axis0.getFocusItem()) - 1;
				tail1 = axis1.getViewportItemCount() - 
					axis1.getViewportPosition(axis1.getFocusItem()) - 1;
				axis0.freezeTail(tail0);
				axis1.freezeTail(tail1);
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
