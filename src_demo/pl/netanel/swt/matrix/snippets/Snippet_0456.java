package pl.netanel.swt.matrix.snippets;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import pl.netanel.swt.matrix.Zone;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.ZoneEditor;

/**
 * Traverse cells (Shift+) Tab and Enter. Traverse Matrix with (Shift+) Ctrl+Tab. 
 */
public class Snippet_0456 {

	public static void main(String[] args) throws IOException {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		shell.setText("Traverse cells (Shift+) Tab and Enter. Traverse Matrix with (Shift+) Ctrl+Tab");
		shell.setLayout(new GridLayout(2, false));
		
		// Data model
		final ArrayList<Object[]> data = new ArrayList<Object[]>();
		data.add(new Object[] {"a", "b"});
		data.add(new Object[] {"c", "Monday"});
		data.add(new Object[] {"d", "Sunday"});
		
		// Matrix
		final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);
		matrix.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		new Text(shell, SWT.BORDER);
		
		matrix.getAxisY().getBody().setCount(data.size());
		matrix.getAxisX().getBody().setCount(2);

		Zone<Integer, Integer> body = matrix.getBody();
		
		// Data painter
    body.replacePainter(new Painter<Integer, Integer>(Painter.NAME_CELLS, Painter.SCOPE_CELLS) {
			@Override
			public String getText(Integer indexX, Integer indexY) {
				Object value = data.get(indexY.intValue())[indexX.intValue()];
				return value == null ? "" : value.toString();
			}
		});
		
    final Listener cellTraverser = new Listener() {
      @Override public void handleEvent(Event e) {
        switch (e.keyCode) {
        case SWT.TAB:
          if (e.stateMask == 0) {
            matrix.execute(Matrix.CMD_FOCUS_RIGHT);
          } 
          else if ((e.stateMask & SWT.MOD2) == SWT.MOD2) {
              matrix.execute(Matrix.CMD_FOCUS_LEFT);
            }
          break;
        case SWT.CR:
          if (e.stateMask == 0) {
            matrix.execute(Matrix.CMD_FOCUS_DOWN);
          }
          break;
        }
      }
    };
    
    // Change the traversing from matrix to (Shift+) Ctrl+Tab 
    matrix.unbind(Matrix.CMD_TRAVERSE_TAB_NEXT, SWT.KeyDown, SWT.TAB);
    matrix.unbind(Matrix.CMD_TRAVERSE_TAB_PREVIOUS, SWT.KeyDown, SWT.MOD2 | SWT.TAB);
    matrix.bind(Matrix.CMD_TRAVERSE_TAB_NEXT, SWT.KeyDown, SWT.MOD1 | SWT.TAB);
    matrix.bind(Matrix.CMD_TRAVERSE_TAB_PREVIOUS, SWT.KeyDown, SWT.MOD1 | SWT.MOD2 | SWT.TAB);
    
    // (Shift+) Tab traverses the cells
    matrix.addListener(SWT.KeyDown, cellTraverser);
    
		// Body editor
		new ZoneEditor<Integer, Integer>(body) {
			@Override
			public void setModelValue(Integer indexX, Integer indexY, Object value) {
				data.get(indexY.intValue())[indexX.intValue()] = value;
			}
			@Override protected Control createControl(final Integer indexX, final Integer indexY) {
			  Control control = super.createControl(indexX, indexY);
			  // (Shift+) Tab traverses the cells from editor control as well
        control.addListener(SWT.Traverse, new Listener() {
          @Override public void handleEvent(Event e) {
            switch (e.detail) {
            case SWT.TRAVERSE_TAB_NEXT:
              if (e.stateMask == 0) {
                matrix.execute(Matrix.CMD_FOCUS_RIGHT);
                matrix.forceFocus();
              } 
            case SWT.TRAVERSE_TAB_PREVIOUS:
              if ((e.stateMask & SWT.MOD2) == SWT.MOD2) {
                matrix.execute(Matrix.CMD_FOCUS_LEFT);
                matrix.forceFocus();
              }
              break;
            case SWT.TRAVERSE_RETURN:
              if (e.stateMask == 0) {
                matrix.execute(Matrix.CMD_FOCUS_DOWN);
                matrix.forceFocus();
              }
              break;
            }
          }
        });
        return control;
			}
		};
		
		body.unbind(Matrix.CMD_EDIT_ACTIVATE, SWT.MouseDoubleClick, 1);
		body.bind(Matrix.CMD_EDIT_ACTIVATE, SWT.MouseDown, 1);
		
		shell.setBounds(400, 200, 600, 400);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
