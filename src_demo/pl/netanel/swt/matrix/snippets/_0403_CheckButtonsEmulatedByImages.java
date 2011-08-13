package pl.netanel.swt.matrix.snippets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.ZoneEditor;

/**
 * Check buttons emulated by images.
 */
/*
 * Note: The checked.png and unchecked.png images must be on the class path.
 */
public class _0403_CheckButtonsEmulatedByImages {

	public static void main(String[] args) throws IOException {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		shell.setText(title);
		shell.setLayout(new FillLayout());
		
		// Data model
		final ArrayList<Object[]> data = new ArrayList<Object[]>();
		data.add(new Object[] {"a", true, new Date()});
		data.add(new Object[] {true, false, "Monday"});
		data.add(new Object[] {new Date(), "Sunday", "b"});
		
		// Matrix
		final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);
		
		matrix.getAxisY().getBody().setCount(data.size());
		matrix.getAxisY().getHeader().setVisible(true);
		matrix.getAxisX().getBody().setCount(3);
		matrix.getAxisX().getBody().setDefaultCellWidth(80);
		// To test positioning of the embedded controls while resizing columns
		matrix.getAxisX().getBody().setDefaultResizable(true);

		// Data painter
		matrix.getBody().replacePainter(
		  new Painter<Integer, Integer>(Painter.NAME_CELLS) {
			@Override
			public void setupSpatial(Integer indexX, Integer indexY){
				Object value = data.get(indexY.intValue())[indexX.intValue()];
				text = value == null || value instanceof Boolean ? "" : value.toString();
			}
		});
		
		// Body editor
		new ZoneEditor<Integer, Integer>(matrix.getBody()) {
			@Override
      public Object getModelValue(Integer indexX, Integer indexY) {
				return data.get(indexY.intValue())[indexX.intValue()];
			}
			@Override
			public void setModelValue(Integer indexX, Integer indexY, Object value) {
				data.get(indexY.intValue())[indexX.intValue()] = value;
			}
			
			@Override
			public Object[] getCheckboxEmulation(Integer indexX, Integer indexY) {
				Object value = data.get(indexY.intValue())[indexX.intValue()];
				return value instanceof Boolean ? getDefaultCheckBoxImages() : null;
			}
			
			@Override
			protected Control createControl(Integer indexX, Integer indexY) {
				Object value = data.get(indexY.intValue())[indexX.intValue()];
				if (value instanceof Boolean) {
					return new Button(matrix, SWT.CHECK);
				}
				return super.createControl(indexX, indexY);
			}
		};
		
		shell.setBounds(400, 200, 600, 400);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	// Meta data
	static final String title = "Check buttons emulated by images";
	static final String instructions = "";
	static final String code = "0403";
}