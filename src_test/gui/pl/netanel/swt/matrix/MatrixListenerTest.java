package pl.netanel.swt.matrix;

import static org.junit.Assert.*;

import org.eclipse.swt.graphics.Point;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
@SuppressWarnings({ "unchecked", "rawtypes" })
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
}
