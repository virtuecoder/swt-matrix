package pl.netanel.swt.matrix.snippets;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.ZoneEditor;

/**
 * List as a custom editor control. 
 */
public class _0405_ListAsACustomEditorControl {
	
	public static void main(String[] args) throws IOException {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		shell.setText(title);
		shell.setLayout(new FillLayout());
		
		// Data model
		final ArrayList<Object[]> data = new ArrayList<Object[]>();
		data.add(new Object[] {"a", "b"});
		data.add(new Object[] {"b", "c"});
		
		// Matrix
		final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);
		
		matrix.getAxisY().getBody().setCount(data.size());
		matrix.getAxisY().getBody().setDefaultCellWidth(80);
		matrix.getAxisX().getBody().setCount(2);
		matrix.getAxisX().getBody().setDefaultCellWidth(80);

		// Paint text from the model in the body
    matrix.getBody().replacePainter(new Painter<Integer, Integer>(Painter.NAME_CELLS, Painter.SCOPE_CELLS) {
      @Override
      public String getText(Integer indexX, Integer indexY) {
        return (String) data.get(indexY.intValue())[indexX.intValue()];
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
			public Control createControl(Integer indexX, Integer indexY) {
		    List list = new List(matrix, SWT.BORDER);
		    list.setItems(new String[] {"a", "b", "c"});
		    return list;
			}
			
			@Override
			public void setEditorValue(Control control, Object value) {
				if (control instanceof List) {
					List list = ((List) control);
					list.deselectAll();
					int position = list.indexOf((String) value);
					if (position != -1) {
						list.select(position);
					}
				} 
				else {
					super.setEditorValue(control, value);
				}
			}
			
			@Override
			public Object getEditorValue(Control control) {
				if (control instanceof List) {
					List list = (List) control;
					int position = list.getSelectionIndex();
					return position == -1 ? null : list.getItem(position);
				} 
				else {
					return super.getEditorValue(control);
				}
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
	static final String title = "List as a custom editor control";
	static final String instructions = "";
	static final String code = "0405";
}