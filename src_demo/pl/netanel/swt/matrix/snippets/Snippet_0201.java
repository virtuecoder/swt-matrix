package pl.netanel.swt.matrix.snippets;

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
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Resources;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.Zone;

/**
 * Freeze head and tail with different color for the dividing line 
 * 
 * @author Jacek Kolodziejczyk created 04-03-2011
 */
public class Snippet_0201 {
	static Section freezeHeadSection0, freezeHeadSection1, freezeTailSection0, freezeTailSection1;
	static Number freezeHeadIndex0, freezeHeadIndex1, freezeTailIndex0, freezeTailIndex1;
	
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setBounds(400, 200, 400, 300);
		shell.setLayout(new GridLayout(2, false));
		final Display display = shell.getDisplay();
		
		
		final Matrix matrix = new Matrix(shell, SWT.H_SCROLL | SWT.V_SCROLL);
		matrix.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		final Axis axis0 = matrix.getAxis0();
		final Axis axis1 = matrix.getAxis1();
		
		Section colBody = axis1.getBody();
		colBody.setCount(40);
		colBody.setDefaultCellWidth(50);
		
		Section rowBody = axis0.getBody();
		rowBody.setCount(100);

		final Zone body = matrix.getBody();
		body.setSelectionBackground(Resources.getColor(SWT.COLOR_LIST_SELECTION));
		body.setSelectionForeground(Resources.getColor(SWT.COLOR_LIST_SELECTION_TEXT));
		
		body.replacePainter(new Painter("column lines", Painter.SCOPE_VERTICAL_LINES) {
			@Override
			protected boolean init() {
				gc.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
				return true;
			}
			@Override
			public void paint(Number index0, Number index1, int x, int y, int width, int height) {
				if (index1.intValue() > 0 &&
					body.getSection1().equals(freezeHeadSection1) && 
					index1.equals(freezeHeadIndex1) || 
					freezeTailSection1 != null && 
					index1.intValue() < freezeTailSection1.getCount().intValue() &&
					body.getSection1().equals(freezeTailSection1) && 
					index1.equals(freezeTailIndex1.intValue() + 1)) 
				{
					gc.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
					gc.fillRectangle(x, y, width, height);
					gc.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
				} else {
					gc.fillRectangle(x, y, width, height);
				}
			}
		});
		
		
		Button add = new Button(shell, SWT.PUSH);
		add.setText("Freeze head");
		add.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				freezeHeadSection0 = axis0.getFocusSection();
				freezeHeadSection1 = axis1.getFocusSection();
				freezeHeadIndex0 = axis0.getFocusIndex();
				freezeHeadIndex1 = axis1.getFocusIndex();
				axis0.freezeHead(freezeHeadSection0, freezeHeadIndex0);
				axis1.freezeHead(freezeHeadSection1, freezeHeadIndex1);
				matrix.refresh();
			}
		});
		
		Button remove = new Button(shell, SWT.PUSH);
		remove.setText("Freeze tail");
		remove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				freezeTailSection0 = axis0.getFocusSection();
				freezeTailSection1 = axis1.getFocusSection();
				freezeTailIndex0 = axis0.getFocusIndex();
				freezeTailIndex1 = axis1.getFocusIndex();
				axis0.freezeTail(freezeTailSection0, freezeTailIndex0);
				axis1.freezeTail(freezeTailSection1, freezeTailIndex1);
				matrix.refresh();
			}
		});
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
