package pl.netanel.swt.matrix;
import static org.junit.Assert.assertEquals;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.junit.Test;

import pl.netanel.swt.matrix.Matrix;

public class SelectionTest {
	@Test
	public void emptyAxis() throws Exception {
		Matrix matrix = new Matrix(new Shell(), SWT.None);
		Section body = matrix.getAxis0().getBody();
		body.setSelectedAll(true);
		assertEquals(0, body.getSelectedCount());
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void emptyBoundsException() throws Exception {
		Matrix matrix = new Matrix(new Shell(), SWT.None);
		Section body = matrix.getAxis0().getBody();
		body.isSelected(0);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void selectItemBackwards() throws Exception {
		Matrix matrix = new Matrix(new Shell(), SWT.None);
		Section body = matrix.getAxis0().getBody();
		body.setCount(4);
		body.setSelected(2, 1, true);
	}
	
	@Test
	public void selectItemSelectsCells() throws Exception {
		Matrix matrix = new Matrix(new Shell(), SWT.None);
		matrix.getAxis0().getBody().setCount(10);
		Section body = matrix.getAxis1().getBody();
		body.setCount(4);
		body.setSelected(0, 0, true);
		assertEquals(10, matrix.getBody().getSelectedCount().intValue());
	}
}
