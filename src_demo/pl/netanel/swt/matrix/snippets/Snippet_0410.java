package pl.netanel.swt.matrix.snippets;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Zone;
import pl.netanel.swt.matrix.ZoneEditor;

/**
 * Separate zone to insert new items. 
 */
public class Snippet_0410 {
  static final int COLUMN_COUNT = 3;
  static ArrayList<Object[]> data;
	
	public static void main(String[] args) throws IOException {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		
		// Data model
		data = new ArrayList();
		
		// Axis
		Axis axis0 = new Axis(Integer.class, 3);
		axis0.getBody().setCount(data.size());
		axis0.getSection(2).setCount(1);
		
		// Matrix
		final Matrix matrix = new Matrix(shell, SWT.NONE, axis0, null);
		
		Axis axis1 = matrix.getAxis1();
		axis0.getHeader().setVisible(true);
		axis1.getHeader().setVisible(true);
		axis1.getBody().setCount(COLUMN_COUNT);

		// Paint text from the model in the body
    matrix.getBody().replacePainter(new Painter("cells", Painter.SCOPE_CELLS_HORIZONTALLY) {
      @Override
      public String getText(Number index0, Number index1) {
        return (String) data.get(index0.intValue())[index1.intValue()];
      }
    });
    
    matrix.getZone(axis0.getSection(2), matrix.getAxis1().getBody())
      .setDefaultBackground(matrix.getBackground());
    
		// Body editor
    ZoneEditor insertEditor = new InsertEditor(matrix);
		
		// Paint text in the row header of the insert section
    Zone insertHeader = matrix.getZone(axis0.getSection(2), axis1.getHeader());
    insertHeader.replacePainter(new Painter("cells", Painter.SCOPE_CELLS_HORIZONTALLY) {
      @Override public String getText(Number index0, Number index1) {
        textAlignX = textAlignY = SWT.CENTER;
        return "*";
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
	
	static class InsertEditor extends ZoneEditor {
    private Object[] item;
	  private boolean isDirty;
    private final Matrix matrix;
	  
	  public InsertEditor(Matrix matrix) {
      super(matrix.getZone(matrix.getAxis0().getSection(2), matrix.getAxis1().getBody()));
      this.matrix = matrix;
      item = new Object[COLUMN_COUNT];
    }
	  
    @Override
    public Object getModelValue(Number index0, Number index1) {
      return item[index1.intValue()];
    }
    
    @Override
    public void setModelValue(Number index0, Number index1, Object value) {
      isDirty = true;
      item[index1.intValue()] = value;
    }
    
    @Override protected boolean hasEmbeddedControl(Number index0, Number index1) {
      return true;
    }
    
    public boolean isDirty() {
      return isDirty;
    }
    
    public void cancel() {
      isDirty = false;
      item = new Object[COLUMN_COUNT];
      matrix.redraw();
    }
    
    public void save() {
      data.add(item);
      isDirty = false;
      item = new Object[COLUMN_COUNT];
      matrix.redraw();
    }
	}
}
