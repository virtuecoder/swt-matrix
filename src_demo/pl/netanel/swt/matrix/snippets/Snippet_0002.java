package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.AxisModel;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.MatrixModel;
import pl.netanel.swt.matrix.Zone;

/**
 * Creates matrix with the given model.
 * 
 * @author Jacek Kolodziejczyk created 03-03-2011
 */
public class Snippet_0002 {
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setLayout(new FillLayout());
		
		AxisModel rowModel = new AxisModel();
		rowModel.getBody().setCount(10);
		
		AxisModel colModel = new AxisModel();
		colModel.getBody().setCount(4);
		colModel.getBody().setDefaultCellWidth(50);
		
		Zone body = new Zone(rowModel.getBody(), colModel.getBody());
		Zone columnHeader = new Zone(rowModel.getBody(), colModel.getHeader());
		Zone rowHeader = new Zone(rowModel.getHeader(), colModel.getBody());
		
		MatrixModel model = new MatrixModel(rowModel, colModel, 
				body, columnHeader, rowHeader);

		new Matrix(shell, SWT.NONE, model);
		
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
