package pl.netanel.swt.matrix;

import static org.junit.Assert.assertEquals;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.junit.Ignore;
import org.junit.Test;

public class EditorTest extends SwtTestCase {
  @Test public void activateEmbeddedCheckBoxBySpace() throws Exception {
    final Matrix matrix = new Matrix(shell, 0);
//    listenToAll(matrix);
    matrix.getAxis0().getBody().setCount(1);
    matrix.getAxis1().getBody().setCount(1);
    
    final Button button = new Button(matrix, SWT.CHECK);
    final boolean[] data = new boolean[] {false};
    
    ZoneEditor editor = new ZoneEditor(matrix.getBody()) {
      @Override protected boolean hasEmbeddedControl(Number index0, Number index1) {
        return true;
      }
      @Override protected Control createControl(Number index0, Number index1) {
        return button;
      }
      @Override protected Object getModelValue(Number index0, Number index1) {
        return data[0];
      }
      @Override protected void setModelValue(Number index0, Number index1, Object value) {
        data[0] = (Boolean) value;
      }
    };
 
    shell.open();
    
    processEvents();
    type(" ");
    assertEquals(true, button.getSelection());
    assertEquals(true, editor.getModelValue(0, 0));
  }
  
  @Test public void activateBySingleClick() throws Exception {
    final Matrix matrix = new Matrix(shell, 0);
//    listenToAll(matrix);
    matrix.getAxis0().getBody().setCount(1);
    matrix.getAxis1().getBody().setCount(1);
    
    Zone body = matrix.getBody();
    new ZoneEditor(body);
    body.unbind(Matrix.CMD_EDIT_ACTIVATE, SWT.MouseDoubleClick, 1);
    body.bind(Matrix.CMD_EDIT_ACTIVATE, SWT.MouseDown, 1);
    
    shell.open();

    processEvents();
    click(matrix, body.getCellBounds(0, 0));
    assertEquals(Text.class, display.getFocusControl().getClass());
  }
  
  @Test public void activateByTypingCapitalLetter() throws Exception {
    createMatrixAndEditor();
    type("A");
    assertEquals(Text.class, display.getFocusControl().getClass());
    assertEquals("A", ((Text) display.getFocusControl()).getText());
  }
  
  @Test public void applyEditorValueOnClickOutside() throws Exception {
    final Matrix matrix = new Matrix(shell, 0);
    matrix.getAxis0().getBody().setCount(1);
    matrix.getAxis1().getBody().setCount(1);
    Zone body = matrix.getBody();
    final Object[] data = new Object[1];
    new ZoneEditor(body) {
      @Override protected void setModelValue(Number index0, Number index1, Object value) {
        data[0] = value;
      }
    };
    shell.open();
    
    press(SWT.F2);
    type("a");
    Rectangle bounds = body.getCellBounds(0, 0);
    bounds.x += bounds.width; bounds.width = 10;
    click(matrix, bounds);
    assertEquals(Matrix.class, display.getFocusControl().getClass());    
    assertEquals(0, matrix.getChildren().length);    
    assertEquals("a", data[0]);
  }
  
 
  @Ignore
  @Test public void copyPaste() throws Exception {
    final Matrix matrix = createMatrixAndEditor();
    
    new ZoneEditor(matrix.getBody());
      
    shell.open();
    
    //listenToAll(matrix);
    
    processEvents();
    show();
//    press(SWT.CTRL, 'c');
//    press(SWT.CTRL, 'v');
  }
  
  private Matrix createMatrixAndEditor() {
    Matrix matrix = new Matrix(shell, 0);
    matrix.getAxis0().getHeader().setVisible(true);
    matrix.getAxis1().getHeader().setVisible(true);
    matrix.getAxis0().getBody().setCount(5);
    matrix.getAxis1().getBody().setCount(5);
    matrix.setSize(1000, 1000);
    new ZoneEditor(matrix.getBody());
    shell.open();
    processEvents();
    return matrix;
  }

}
