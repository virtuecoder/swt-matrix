package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.Resources;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.Zone;

/**
 * Mark selection with SWT.COLOR_LIST... colors.
 * 
 * @author Jacek Kolodziejczyk created 04-03-2011
 */
public class Snippet_0016 {
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setBounds(400, 200, 400, 300);
		shell.setLayout(new FillLayout());
		
		final Matrix matrix = new Matrix(shell, SWT.NONE);
		
		Section colBody = matrix.getAxis1().getBody();
		colBody.setCount(4);
		colBody.setDefaultCellWidth(50);
		
		Section rowBody = matrix.getAxis0().getBody();
		rowBody.setCount(10);

		final Zone body = matrix.getBody();
		body.setSelectionBackground(Resources.getColor(SWT.COLOR_LIST_SELECTION));
		body.setSelectionForeground(Resources.getColor(SWT.COLOR_LIST_SELECTION_TEXT));
		
		shell.open();
		Display display = shell.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
