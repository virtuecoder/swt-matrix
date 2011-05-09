package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.Zone;

/**
 * Mark selection with SWT.COLOR_LIST... colors.
 */
public class Snippet_0016 {
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setBounds(400, 200, 400, 300);
		shell.setLayout(new FillLayout());
		final Display display = shell.getDisplay();
		
		final Matrix matrix = new Matrix(shell, SWT.NONE);
		matrix.setFocusCellEnabled(false);
		
		matrix.getAxis0().getBody().setCount(10);

		Section body1 = matrix.getAxis1().getBody();
		body1.setCount(4);
		body1.setDefaultCellWidth(50);

		final Zone body = matrix.getBody();
		body.setSelectionBackground(display.getSystemColor(SWT.COLOR_LIST_SELECTION));
		body.setSelectionForeground(display.getSystemColor(SWT.COLOR_LIST_SELECTION_TEXT));
		body.setSelected(1, 2, 1, 2, true);
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
