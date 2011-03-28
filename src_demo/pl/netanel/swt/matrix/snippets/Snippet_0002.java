package pl.netanel.swt.matrix.snippets;

import java.util.ArrayList;

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
import pl.netanel.swt.matrix.Zone;

/**
 * Creates matrix with a custom model with add / remove model items.
 * 
 * @author Jacek Kolodziejczyk created 03-03-2011
 */
public class Snippet_0002 {
	
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setLayout(new GridLayout(2, false));
		
		final ArrayList<String> list = new ArrayList<String>();
		list.add(Integer.toString(1));
		list.add(Integer.toString(2));
		list.add(Integer.toString(3));
		list.add(Integer.toString(4));
		
		final Axis rowModel = new Axis();
		rowModel.getBody().setCount(4);
		
		Axis colModel = new Axis();
		colModel.getBody().setCount(2);
		colModel.getBody().setDefaultCellWidth(50);
		
		Zone body = new Zone(rowModel.getBody(), colModel.getBody(), Zone.BODY) {
			@Override
			public String getText(Number index0, Number index1) {
				String value = list.get(index0.intValue());
				return index1.intValue() == 0 
					? value
					: Integer.toString(value.length());
			}
		};
		Zone columnHeader = new Zone(rowModel.getHeader(), colModel.getBody(), Zone.COLUMN_HEADER) {
			@Override
			public String getText(Number index0, Number index1) {
				return index1.intValue() == 0 ? "Value" : "Length";
			}
		};
		
		final Matrix matrix = new Matrix(shell, SWT.V_SCROLL, 
				rowModel, colModel, body, columnHeader);
		matrix.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		
		
		Button add = new Button(shell, SWT.PUSH);
		add.setText("Add");
		add.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				list.add(Integer.toString(Integer.parseInt(list.get(list.size() - 1)) + 1));
				rowModel.getBody().setCount(list.size());
				matrix.refresh();
			}
		});
		
		Button remove = new Button(shell, SWT.PUSH);
		remove.setText("Remove");
		remove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				list.remove(rowModel.getFocusIndex().intValue());
				rowModel.getFocusSection().setCount(list.size());
				matrix.refresh();
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
