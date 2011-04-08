package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.Zone;

/**
 * Image painting
 * 
 * @author Jacek Kolodziejczyk created 03-03-2011
 */
public class Snippet_0018 {
	
	private static final int ROW_COUNT = 8;
	private static final int COLUMN_COUNT = 4;

	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setBounds(400, 200, 400, 300);
		shell.setLayout(new FillLayout());
		Display display = shell.getDisplay();
		
		Section colBody = new Section();
		colBody.setCount(COLUMN_COUNT);
		colBody.setDefaultCellWidth(50);
		
		Section rowBody = new Section();
		rowBody.setCount(ROW_COUNT);
		
		
		Zone body = new Zone(rowBody, colBody) {
			Image[][] images = new Image[ROW_COUNT][];
			@Override
			public Image getImage(Number index0, Number index1) {
				Image[] row = images[index0.intValue()];
				if (row == null) return null;
				return row[index1.intValue()];
			}
			@Override
			public void setImage(Number index0, Number index1, Image value) {
				Image[] row = images[index0.intValue()];
				if (row == null) {
					row = images[index0.intValue()] = new Image[COLUMN_COUNT];
				}
				row[index1.intValue()] = value;
			}
			
			@Override
			public String getText(Number index0, Number index1) {
				return index0.intValue() + ", " + index1.intValue();
			}
		};
		
		Image image = new Image(display, 16, 16);
		GC gc = new GC(image);
		gc.setBackground(display.getSystemColor(SWT.COLOR_BLUE));
		gc.fillOval(0, 0, 16, 16);
		gc.dispose();
		
		body.setImage(1, 1, image);
		body.setImage(5, 3, image);
		body.setImage(2, 0, image);
		
		new Matrix(shell, SWT.V_SCROLL, 
				new Axis(rowBody), new Axis(colBody), body);
		
		body.getPainter("cells").imageAlignX = SWT.RIGHT;
		
		shell.setBounds(400, 200, 400, 300);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
