package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.Zone;

/**
 * Cell background calculated
 * 
 * @author Jacek Kolodziejczyk created 03-03-2011
 */
public class Snippet_0017 {
	static int counter;
	
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setBounds(400, 200, 400, 300);
		shell.setLayout(new FillLayout());
		Display display = shell.getDisplay();
		
		Section colBody = new Section();
		colBody.setDefaultLineWidth(0);
		colBody.setCount(8);
//		colBody.setDefaultCellWidth(16);
		
		Section rowBody = new Section();
		rowBody.setDefaultLineWidth(0);
		rowBody.setCount(8);
		
		final Color color = display.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
		
		Zone body = new Zone(rowBody, colBody) {
			@Override
			public Color getBackground(Number index0, Number index1) {
				return index0.intValue() % 2 + index1.intValue() % 2 == 1 
					? color : null;
			}
			@Override
			public String getText(Number index0, Number index1) {
				return null;
			}
		};
		
		new Matrix(shell, SWT.V_SCROLL, 
				new Axis(rowBody), new Axis(colBody), body);
		
		shell.setBounds(400, 200, 400, 300);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
