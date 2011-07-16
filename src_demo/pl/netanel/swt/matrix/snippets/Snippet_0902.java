package pl.netanel.swt.matrix.snippets;

import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.AxisItem;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.Zone;

/**
 * Selection and control event handling.
 */
public class Snippet_0902 {
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setLayout(new FillLayout());
		
		Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);
		matrix.getAxis0().getHeader().setVisible(true);
		matrix.getAxis1().getHeader().setVisible(true);
		final Section<Integer> body0 = matrix.getAxis0().getBody();
		final Section<Integer> body1 = matrix.getAxis1().getBody();
		body0.setCount(10);
		body1.setCount(4);
		body1.setDefaultResizable(true);
		body1.setDefaultMoveable(true);
		
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
				selectedItems("Rows selected: ", body0.getSelectedExtentIterator());
			}
		});
		
		body1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectedItems("Columns selected: ", body1.getSelectedExtentIterator());
			}
		});
		
		body1.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				@SuppressWarnings("unchecked") AxisItem<Integer> item = (AxisItem<Integer>) e.data;
				Section<Integer> section = item.getSection();
        if (section.getSelectedCount() > 0) {
					selectedItems("Columns resized: ", section.getSelectedExtentIterator());
				} else {
					System.out.println("Columns resized: " + item.getIndex()); 
				}
				
				System.out.println("New size: " + 
				  section.getCellWidth(item.getIndex()));
			}
			@Override
			public void controlMoved(ControlEvent e) {
				selectedItems("Columns moved: ", body1.getSelectedExtentIterator());
				
				@SuppressWarnings("unchecked") AxisItem<Integer> item = (AxisItem<Integer>) e.data;
				System.out.println("Target: section " + item.getSection() + ", index " + item.getIndex());
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
	
	static void selectedItems(String caption, Iterator<Integer[]> it) {
		StringBuilder sb = new StringBuilder();
		while (it.hasNext()) {
			if (sb.length() > 0) sb.append(", ");
			Integer[] n = it.next();
			sb.append(n[0]).append("-").append(n[1]);
		}
		sb.insert(0, caption);
		System.out.println(sb.toString());
	}
}
