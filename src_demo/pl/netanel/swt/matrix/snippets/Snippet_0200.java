package pl.netanel.swt.matrix.snippets;

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
import pl.netanel.swt.matrix.Resources;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.Zone;

/**
 * Freeze head and tail
 * 
 * @author Jacek Kolodziejczyk created 04-03-2011
 */
public class Snippet_0200 {
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setBounds(400, 200, 400, 300);
		shell.setLayout(new GridLayout(2, false));
		
		final Matrix matrix = new Matrix(shell, SWT.H_SCROLL | SWT.V_SCROLL);
		matrix.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		final Axis axis0 = matrix.getAxis0();
		final Axis axis1 = matrix.getAxis1();
		
		Section colBody = axis1.getBody();
		colBody.setCount(40);
		colBody.setDefaultCellWidth(50);
		
		Section rowBody = axis0.getBody();
		rowBody.setCount(100);

		final Zone body = matrix.getBody();
		body.setSelectionBackground(Resources.getColor(SWT.COLOR_LIST_SELECTION));
		body.setSelectionForeground(Resources.getColor(SWT.COLOR_LIST_SELECTION_TEXT));
		
		
		Button add = new Button(shell, SWT.PUSH);
		add.setText("Freeze head");
		add.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				axis0.freezeHead(axis0.getFocusSection(), axis0.getFocusIndex());
				axis1.freezeHead(axis1.getFocusSection(), axis1.getFocusIndex());
				matrix.refresh();
			}
		});
		
		Button remove = new Button(shell, SWT.PUSH);
		remove.setText("Freeze tail");
		remove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				axis0.freezeTail(axis0.getFocusSection(), axis0.getFocusIndex());
				axis1.freezeTail(axis1.getFocusSection(), axis1.getFocusIndex());
				matrix.refresh();
			}
		});
		
		shell.open();
		Display display = shell.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
