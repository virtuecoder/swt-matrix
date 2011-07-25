package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Zone;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Section;

/**
 * Mark selection with SWT.COLOR_LIST... colors.
 */
public class Snippet_0016 {
	public static void main(String[] args) {
		Shell shell = (new Shell());
    shell.setText("Mark selection with SWT.COLOR_LIST... colors");
		shell.setBounds(400, 200, 600, 400);
		shell.setLayout(new FillLayout());
		final Display display = shell.getDisplay();
		
		final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);
		matrix.setFocusCellEnabled(false);
		
		matrix.getAxisY().getBody().setCount(10);

		Section<Integer> body1X = matrix.getAxisX().getBody();
		body1X.setCount(4);
		body1X.setDefaultCellWidth(50);

		final Zone<Integer, Integer> body = matrix.getBody();
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
