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
import pl.netanel.swt.matrix.CellExtent;
import pl.netanel.swt.matrix.Extent;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.Zone;

/**
 * Selection and control event handling.
 */
public class _0902_SelectionAndControlEventHandling {
	public static void main(String[] args) {
		Shell shell = (new Shell());
    shell.setText(title);
		shell.setLayout(new FillLayout());
		
		Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);
		matrix.getAxisY().getHeader().setVisible(true);
		matrix.getAxisX().getHeader().setVisible(true);
		final Section<Integer> bodyX = matrix.getAxisX().getBody();
		final Section<Integer> bodyY = matrix.getAxisY().getBody();
		bodyX.setCount(4);
		bodyX.setDefaultResizable(true);
		bodyX.setDefaultMoveable(true);
		bodyY.setCount(10);
		
		// Cell selection
		final Zone<Integer, Integer> body = matrix.getBody();
		body.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				StringBuilder sb = new StringBuilder();
				Iterator<CellExtent<Integer, Integer>> it = body.getSelectedExtentIterator();
				while (it.hasNext()) {
					if (sb.length() > 0) sb.append(", ");
					CellExtent<Integer, Integer> n = it.next();
					sb.append(n.getStartX()).append("-").append(n.getEndX()).append(":");
					sb.append(n.getStartY()).append("-").append(n.getEndY());
				}
				sb.insert(0, "Cells selected: ");
				System.out.println(sb);
			}
		});
		
		bodyY.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectedItems("Rows selected: ", bodyY.getSelectedExtents());
			}
		});
		
		bodyX.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectedItems("Columns selected: ", bodyX.getSelectedExtents());
			}
		});
		
		bodyX.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				@SuppressWarnings("unchecked") AxisItem<Integer> item = (AxisItem<Integer>) e.data;
				Section<Integer> section = item.getSection();
        if (section.getSelectedCount() > 0) {
					selectedItems("Columns resized: ", section.getSelectedExtents());
				} else {
					System.out.println("Columns resized: " + item.getIndex()); 
				}
				
				System.out.println("New size: " + 
				  section.getCellWidth(item.getIndex()));
			}
			@Override
			public void controlMoved(ControlEvent e) {
				selectedItems("Columns moved: ", bodyX.getSelectedExtents());
				
				@SuppressWarnings("unchecked") AxisItem<Integer> item = (AxisItem<Integer>) e.data;
				System.out.println("Target: section " + item.getSection() + ", index " + item.getIndex());
			}
		});
		
		shell.setBounds(400, 200, 600, 400);
		shell.open();
		Display display = shell.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	static void selectedItems(String caption, Iterator<Extent<Integer>> it) {
		StringBuilder sb = new StringBuilder();
		while (it.hasNext()) {
			if (sb.length() > 0) sb.append(", ");
			Extent<Integer> n = it.next();
			sb.append(n.getStart()).append("-").append(n.getEnd());
		}
		sb.insert(0, caption);
		System.out.println(sb.toString());
	}

	// Meta data
	static final String title = "Selection and control event handling";
	static final String instructions = "";
	static final String code = "0902";
}