package pl.netanel.swt.matrix;

import static org.junit.Assert.assertEquals;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
@SuppressWarnings({"unchecked", "rawtypes"})
public class  EditorTest extends SwtTestCase {

  @Test public void activateEmbeddedCheckBoxBySpace() throws Exception {
    final Matrix matrix = new Matrix(shell, 0);
//    listenToAll(matrix);
    matrix.getAxisY().getBody().setCount(1);
    matrix.getAxisX().getBody().setCount(1);

    final Button button = new Button(matrix, SWT.CHECK);
    final boolean[] data = new boolean[] {false};

    ZoneEditor editor = new ZoneEditor(matrix.getBody()) {
      @Override protected boolean hasEmbeddedControl(Number indexY, Number indexX) {
        return true;
      }
      @Override protected Control createControl(Number indexY, Number indexX) {
        return button;
      }
      @Override
      public Object getModelValue(Number indexY, Number indexX) {
        return data[0];
      }
      @Override
      public boolean setModelValue(Number indexY, Number indexX, Object value) {
        data[0] = (Boolean) value;
        return true;
      }
    };

    shell.open();

    processEvents();
    type(" ");
    assertEquals(true, button.getSelection());
    assertEquals(true, editor.getModelValue(0, 0));
  }

  @Test public void embeddedCheckBoxLoosesFocusAfterSelection() throws Exception {
    final Matrix matrix = new Matrix(shell, 0);
//    listenToAll(matrix);
    matrix.getAxisY().getBody().setCount(1);
    matrix.getAxisX().getBody().setCount(1);

    final Button button = new Button(matrix, SWT.CHECK);
    final boolean[] data = new boolean[] {false};

    ZoneEditor editor = new ZoneEditor(matrix.getBody()) {
      @Override protected boolean hasEmbeddedControl(Number indexY, Number indexX) {
        return true;
      }
      @Override protected Control createControl(Number indexY, Number indexX) {
        return button;
      }
      @Override
      public Object getModelValue(Number indexY, Number indexX) {
        return data[0];
      }
      @Override
      public boolean setModelValue(Number indexY, Number indexX, Object value) {
        data[0] = (Boolean) value;
        return true;
      }
    };

    shell.open();

    processEvents();
    click(button);
    assertEquals(true, button.getSelection());
    assertEquals(true, editor.getModelValue(0, 0));
    assertEquals(matrix, display.getFocusControl());
  }

  @Test public void activateBySingleClick() throws Exception {
    final Matrix matrix = new Matrix(shell, 0);
//    listenToAll(matrix);
    matrix.getAxisY().getBody().setCount(1);
    matrix.getAxisX().getBody().setCount(1);

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
    matrix.getAxisY().getBody().setCount(1);
    matrix.getAxisX().getBody().setCount(1);
    Zone body = matrix.getBody();
    final Object[] data = new Object[1];
    new ZoneEditor(body) {
      @Override
      public boolean setModelValue(Number indexY, Number indexX, Object value) {
        data[0] = value;
        return true;
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
    assertEquals("0, 0a", data[0]);
  }


  @Ignore
  @Test public void copyPaste() throws Exception {
    final Matrix matrix = createMatrixAndEditor();

    new ZoneEditor(matrix.getBody());

    shell.open();

    //listenToAll(matrix);

    processEvents();
//    press(SWT.CTRL, 'c');
//    press(SWT.CTRL, 'v');
  }

  private Matrix createMatrixAndEditor() {
    Matrix matrix = new Matrix(shell, 0);
    matrix.getAxisY().getHeader().setVisible(true);
    matrix.getAxisX().getHeader().setVisible(true);
    matrix.getAxisY().getBody().setCount(5);
    matrix.getAxisX().getBody().setCount(5);
    matrix.setSize(1000, 1000);
    new ZoneEditor(matrix.getBody());
    shell.open();
    processEvents();
    return matrix;
  }

}
