package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.AxisItem;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Section;

/**
 * Identifying items by distance.
 */
public class Snippet_0903 {
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setLayout(new FillLayout());
		
		final Matrix<Integer, Integer> matrix = new Matrix(shell, SWT.NONE);
		matrix.getAxis0().getHeader().setVisible(true);
		matrix.getAxis1().getHeader().setVisible(true);
		final Section<Integer> body0 = matrix.getAxis0().getBody();
		final Section<Integer> body1 = matrix.getAxis1().getBody();
		body0.setCount(10);
		body1.setCount(4);
		
		matrix.addMouseMoveListener(new MouseMoveListener() {
			@Override
			public void mouseMove(MouseEvent e) {
				AxisItem<Integer> item0 = matrix.getAxis0().getItemByDistance(e.y);
				AxisItem<Integer> item1 = matrix.getAxis1().getItemByDistance(e.x);
				System.out.println(item0.toString() + ":" + item1.toString());
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
