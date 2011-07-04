package pl.netanel.swt.matrix.snippets;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.Zone;
import pl.netanel.swt.matrix.ZoneEditor;

/**
 * Separate zone to insert new items. 
 */
public class Snippet_0410 {
  static final int COLUMN_COUNT = 3;
  ArrayList<Object[]> data;
	
	public static void main(String[] args) throws IOException {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		
		new Snippet_0410(shell);
		
	  shell.setBounds(400, 200, 400, 300);
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
	}
  
  public Snippet_0410(Composite parent) {
		// Data model
		data = new ArrayList();
		
		// Axis
		final Axis axis0 = new Axis(Integer.class, 3);
		axis0.getBody().setCount(data.size());
		Section insertSection = axis0.getSection(2);
		insertSection.setCount(1);
		
		// Matrix
		final Matrix matrix = new Matrix(parent, SWT.NONE, axis0, null);
		
		// Configure axises
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
    
    // Insert body editor
    final InsertEditor insertEditor = new InsertEditor(matrix);
    

    // Insert body painter
    Zone insertBody = matrix.getZone(insertSection, matrix.getAxis1().getBody());
    Painter insertPainter = new Painter("cells", Painter.SCOPE_CELLS_HORIZONTALLY) {
      @Override public String getText(Number index0, Number index1) {
        return (String) insertEditor.item[index1.intValue()];
      }
    };
    insertBody.replacePainter(insertPainter);
    insertBody.setDefaultBackground(matrix.getBackground());
    
		// Paint text in the row header of the insert section
    final Zone insertHeader = matrix.getZone(insertSection, axis1.getHeader());
    insertHeader.replacePainter(new Painter("cells", Painter.SCOPE_CELLS_HORIZONTALLY) {
      @Override public String getText(Number index0, Number index1) {
        textAlignX = textAlignY = SWT.CENTER;
        return "add:";
      }
    });

    new ZoneEditor(insertHeader) {
      @Override protected Control createControl(Number index0, Number index1) {
        if (insertEditor.isDirty) {
          Composite composite = new Composite(matrix, SWT.NONE);
          composite.setLayout(new FillLayout());
          
          Button button = new Button(composite, SWT.PUSH);
          button.setText("\u2713");
          button.setToolTipText("Submit the new item");
          button.addSelectionListener(new SelectionAdapter() {
            @Override public void widgetSelected(SelectionEvent e) {
              insertEditor.save();
              
            }
          });
          
          button = new Button(composite, SWT.PUSH);
          button.setText("\u2717");
          button.setToolTipText("Clear editions");
          button.addSelectionListener(new SelectionAdapter() {
            @Override public void widgetSelected(SelectionEvent e) {
              insertEditor.cancel();
            }
          });
          
          return composite;
        } 
        else {
          return null;
        }
      }
      @Override protected void setBounds(Number index0, Number index1, Control control) {
        Rectangle bounds = insertHeader.getCellBounds(index0, index1);
        bounds.y -= 2; bounds.height += 4;
        control.setBounds(bounds);
      }
      
      @Override protected boolean hasEmbeddedControl(Number index0, Number index1) {
        return insertEditor.isDirty;
      }
    };
	}
	
	class InsertEditor extends ZoneEditor {
    Object[] item;
	  boolean isDirty;
    final Matrix matrix;
	  
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
      matrix.refresh();
    }
       
    public boolean isDirty() {
      return isDirty;
    }
    
    public void cancel() {
      isDirty = false;
      item = new Object[COLUMN_COUNT];
      matrix.refresh();
      matrix.forceFocus();
    }
    
    public void save() {
      data.add(item);
      matrix.getAxis0().getBody().setCount(data.size());
      isDirty = false;
      item = new Object[COLUMN_COUNT];
      matrix.refresh();
      matrix.forceFocus();
    }
	}
}
