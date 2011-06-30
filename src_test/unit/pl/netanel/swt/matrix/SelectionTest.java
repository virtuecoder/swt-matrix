package pl.netanel.swt.matrix;
import static org.junit.Assert.assertEquals;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
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
	
	@Test
	public void selectAppend() throws Exception {
		Matrix matrix = new Matrix(new Shell(), SWT.None);
		matrix.getAxis0().getBody().setCount(10);
		matrix.getAxis1().getBody().setCount(10);
		matrix.setSize(1000, 1000);
		Rectangle r = matrix.getBody().getCellBounds(2, 2);
		
		Event e = new Event();
		e.type = SWT.MouseMove;
		e.x = r.x + 1;
		e.y = r.y + 1;
		matrix.listener.handleEvent(e);
		
		e = new Event();
		e.type = SWT.MouseDown;
//		e.x = r.x + 1;
//    e.y = r.y + 1;
    e.button = 1;
		e.stateMask = SWT.MOD1;
		matrix.listener.handleEvent(e);
		
		assertEquals(2, matrix.getBody().getSelectedCount().intValue());
	}
}
