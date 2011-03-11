package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.Resources;
import pl.netanel.swt.matrix.AxisModel;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.MatrixModel;
import pl.netanel.swt.matrix.MutableNumber;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.Zone;
import pl.netanel.swt.matrix.painter.BorderPainter;
import pl.netanel.swt.matrix.painter.LinePainter;

/**
 * Cell span.
 * 
 * @author Jacek Kolodziejczyk created 04-03-2011
 */
public class Snippet_0013 {
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setBounds(400, 200, 400, 300);
		shell.setLayout(new FillLayout());
		Display display = shell.getDisplay();
		
		final AxisModel rowModel = new AxisModel(new Section(int.class) {
			@Override
			public int getLineWidth(MutableNumber index) {
				return 3;
			}
		});
		rowModel.getBody().setCount(10);
		
		AxisModel colModel = new AxisModel(new Section(int.class) {
			@Override
			public int getLineWidth(MutableNumber index) {
				return 3;
			}
		});
		colModel.getBody().setCount(4);
		colModel.getBody().setDefaultCellWidth(50);
		
		MatrixModel model = new MatrixModel(rowModel, colModel);
		Matrix matrix = new Matrix(shell, SWT.NONE, model);
		Zone body = matrix.getModel().getBody();
		body.linePainters0.get(LinePainter.class).setEnabled(false);
		body.linePainters1.get(LinePainter.class).setEnabled(false);
		body.cellPainters.add(new BorderPainter().color(Resources.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW)));
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
