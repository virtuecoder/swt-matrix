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
public class Snippet_0405 {
	
	public static void main(String[] args) throws IOException {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		
		// Data model
		final ArrayList<Object[]> data = new ArrayList();
		data.add(new Object[] {"a", "b"});
		data.add(new Object[] {"b", "c"});
		
		// Matrix
		final Matrix matrix = new Matrix(shell, SWT.NONE);
		
		matrix.getAxis0().getBody().setCount(data.size());
		matrix.getAxis0().getBody().setDefaultCellWidth(80);
		matrix.getAxis1().getBody().setCount(2);
		matrix.getAxis1().getBody().setDefaultCellWidth(80);

		// Paint text from the model in the body
    matrix.getBody().replacePainter(new Painter("cells", Painter.SCOPE_CELLS_HORIZONTALLY) {
      @Override
      public String getText(Number index0, Number index1) {
        return (String) data.get(index0.intValue())[index1.intValue()];
      }
    });
    
		// Body editor
		new ZoneEditor(matrix.getBody()) {
			@Override
			public Object getModelValue(Number index0, Number index1) {
				return data.get(index0.intValue())[index1.intValue()];
			}
			
			@Override
			public void setModelValue(Number index0, Number index1, Object value) {
				data.get(index0.intValue())[index1.intValue()] = value;
			}
			
			@Override
			public Control createControl(Number index0, Number index1) {
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
		
		shell.setBounds(400, 200, 400, 300);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
