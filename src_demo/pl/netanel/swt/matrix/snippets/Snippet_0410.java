package pl.netanel.swt.matrix.snippets;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.AxisItem;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Zone;
import pl.netanel.swt.matrix.ZoneEditor;

/**
 * Custom copy / paste.
 */
/*
 * Can also copy/paste irregular sets of selected cells, 
 * e.g. (0,0) and (1,1) cells at once.
 */
public class Snippet_0410 {
	static final String NEW_LINE = System.getProperty("line.separator");
	static final int count0 = 10, count1 = 4;
	
	public static void main(String[] args) {
		final Display display = Display.getDefault();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(2, false));
		
		// Data model
		final ArrayList<ArrayList> data = new ArrayList();
		for (int i = 0; i < count0; i++) {
			ArrayList row = new ArrayList();
			for (int j = 0; j < count1; j++) {
				row.add("" + i + ", " + j);
			}	
			data.add(row);
		}
		
		// Matrix
		final Matrix<Integer, Integer> matrix = new Matrix(shell, SWT.NONE);
		matrix.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		// Configure the matrix
		matrix.getAxis0().getHeader().setVisible(true);
		matrix.getAxis0().getBody().setCount(count0);
		matrix.getAxis1().getBody().setCount(count1);
		matrix.getBody().replacePainter(new Painter("cells", Painter.SCOPE_CELLS_HORIZONTALLY) {
			@Override
			public void paint(Number index0, Number index1, int x, int y, int width, int height) {
				text = data.get(index0.intValue()).get(index1.intValue()).toString();
				super.paint(index0, index1, x, y, width, height);
			}
		});
		
		final ZoneEditor zoneEditor = new ZoneEditor(matrix.getBody()) {
			@Override
			protected Control createControl(Number index0, Number index1, Composite parent) {
				return null;
			}
			
			@Override public void copy() {
				StringBuilder sb = new StringBuilder();
				Zone<Integer, Integer> body = matrix.getBody();
				Number[] n = body.getSelectedExtent();
				int max0 = n[1].intValue();
				int max1 = n[3].intValue();
				int min1 = n[2].intValue();
				for (int i = n[0].intValue(); i <= max0; i++) {
					for (int j = min1; j <= max1; j++) {
						if (j > min1) sb.append("\t");
						if (body.isSelected(i, j)) {
							sb.append(data.get(i).get(j).toString());
						}
					}
					if (i < max0) sb.append(NEW_LINE);
				}
				Clipboard clipboard = new Clipboard(display);
				clipboard.setContents(new Object[] {sb.toString()},
						new Transfer[] {TextTransfer.getInstance()});		
				clipboard.dispose();
				matrix.setFocus();
			}
			
			@Override public void paste() {
				Clipboard clipboard = new Clipboard(display);
				Object contents = clipboard.getContents(TextTransfer.getInstance());
				clipboard.dispose();
				
				if (contents == null) return;
				
				AxisItem focusItem0 = matrix.getAxis0().getFocusItem();
				AxisItem focusItem1 = matrix.getAxis1().getFocusItem();
				if (focusItem0 == null || focusItem1 == null) return;
				
				int start0 = focusItem0.getIndex().intValue();
				int start1 = focusItem1.getIndex().intValue();
				String[] rows = contents.toString().split(NEW_LINE);
				for (int i = 0; i < rows.length && start0 + i < count0; i++) {
					String[] cells = split(rows[i], "\t");
					for (int j = 0; j < cells.length && start1 + j < count1; j++) {
						String value = cells[j];
						if (value != null) {
							data.get(start0 + i).set(start1 + j, value);
						}
					}
				}
				
				matrix.redraw();
				matrix.setFocus();
			}
		};
		
		// Copy button
		Button copy = new Button(shell, SWT.PUSH);
		copy.setText("Copy");
		copy.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				zoneEditor.copy();
			}
		});
		
		Button paste = new Button(shell, SWT.PUSH);
		paste.setText("Paste");
		paste.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			  zoneEditor.paste();
			}
		});
		
		
		shell.setBounds(400, 200, 400, 300);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	private static String[] split(String s, String separator) {
		ArrayList<String> list = new ArrayList();
		int pos1 = 0, pos2 = 0;
		while (true) {
			pos1 = pos2;
			pos2 = s.indexOf("\t", pos2);
			if (pos2 == -1) {
				list.add(pos1 == s.length() ? null : s.substring(pos1, s.length()));
				break;
			}
			if (pos1 == pos2) {
				list.add(null);
			} else {
				list.add(s.substring(pos1, pos2));
			}
			pos2++;
		}
		return list.toArray(new String[] {});
	}
}
