package pl.netanel.swt.matrix;

import static org.junit.Assert.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class MatrixListenerTest extends SwtTestCase {
	@Test
	@Ignore
	public void dragSelect() throws Exception {
		Matrix<Integer, Integer> matrix = createMatrix();
		Point p0 = middle(matrix.getHeaderX().getCellBounds(0, 0));
		Point p1 = middle(matrix.getHeaderX().getCellBounds(2, 0));
		dragAndDrop(0, p0, p1);
		assertTrue(matrix.getAxisX().getBody().isSelected(0));
		assertTrue(matrix.getAxisX().getBody().isSelected(1));
		assertTrue(matrix.getAxisX().getBody().isSelected(2));
		assertFalse(matrix.getAxisX().getBody().isSelected(3));
	}

	@Test
  public void focusAfterDelete() throws Exception {
    Matrix<Integer, Integer> matrix = createMatrix();
    matrix.getAxisX().setFocusItem(AxisItem.create(matrix.getAxisX().getBody(), 9));
    matrix.getAxisX().getBody().delete(5,9);
    assertEquals("1:4", matrix.getAxisX().getFocusItem().toString());
  }

	@Test
	public void focusAfterSetCount() throws Exception {
	  Matrix<Integer, Integer> matrix = createMatrix();
	  matrix.getAxisX().setFocusItem(AxisItem.create(matrix.getAxisX().getBody(), 9));
	  matrix.getAxisX().getBody().setCount(5);
	  assertEquals("1:4", matrix.getAxisX().getFocusItem().toString());
	}

	@Test
	public void selecttWithShiftKey() throws Exception {
	  Matrix<Integer, Integer> matrix = createMatrix();
	  Event e = new Event();
	  e.type = SWT.Activate;
    matrix.listener.handleEvent(e);
	  matrix.execute(Matrix.CMD_SELECT_RIGHT);
	  assertTrue(matrix.getBody().isSelected(0, 0));
	  assertTrue(matrix.getBody().isSelected(1, 0));
	}

  @Override
  protected Matrix<Integer, Integer> createMatrix() {
    Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(new Shell(), SWT.V_SCROLL);
	  matrix.getAxisX().getBody().setCount(10);
	  matrix.getAxisY().getBody().setCount(10);
    return matrix;
  }


}
