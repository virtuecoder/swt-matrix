package pl.netanel.swt.matrix.snippets;

import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.Zone;

/**
 * Selection event handling.
 * 
 * @author Jacek Kolodziejczyk created 26-04-2011
 */
public class Snippet_0902 {
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setLayout(new FillLayout());
		
		Matrix<Integer, Integer> matrix = new Matrix(shell, SWT.NONE);
		matrix.getAxis0().getHeader().setVisible(true);
		matrix.getAxis1().getHeader().setVisible(true);
		final Section<Integer> body0 = matrix.getAxis0().getBody();
		final Section<Integer> body1 = matrix.getAxis1().getBody();
		body0.setCount(10);
		body1.setCount(4);
		
		// Cell selection
		final Zone<Integer, Integer> body = matrix.getBody();
		body.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				StringBuilder sb = new StringBuilder();
				Iterator<Number[]> it = body.getSelectedExtentIterator();
				while (it.hasNext()) {
					if (sb.length() > 0) sb.append(", ");
					Number[] n = it.next();
					sb.append(n[0]).append("-").append(n[1]).append(":");
					sb.append(n[2]).append("-").append(n[3]);
				}
				sb.insert(0, "Cells selected: ");
				System.out.println(sb);
			}
		});
		
		body0.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				StringBuilder sb = new StringBuilder();
				Iterator<Integer[]> it = body0.getSelectedExtentIterator();
				while (it.hasNext()) {
					if (sb.length() > 0) sb.append(", ");
					Number[] n = it.next();
					sb.append(n[0]).append("-").append(n[1]);
				}
				sb.insert(0, "Rows selected: ");
				System.out.println(sb);
			}
		});
		
		body1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				StringBuilder sb = new StringBuilder();
				Iterator<Integer[]> it = body1.getSelectedExtentIterator();
				while (it.hasNext()) {
					if (sb.length() > 0) sb.append(", ");
					Number[] n = it.next();
					sb.append(n[0]).append("-").append(n[1]);
				}
				sb.insert(0, "Columns selected: ");
				System.out.println(sb);
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
