package pl.netanel.swt.matrix;
import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class) public class  SelectionTest {
	@Test
	public void emptyAxis() throws Exception {
		Matrix matrix = new Matrix(new Shell(), SWT.None);
		Section body = matrix.getAxis0().getBody();
		body.setSelected(true);
		assertEquals(0, body.getSelectedCount());
	}
	
//	@Test(expected = IndexOutOfBoundsException.class)
//	public void emptyBoundsException() throws Exception {
//		Matrix matrix = new Matrix(new Shell(), SWT.None);
//		Section body = matrix.getAxis0().getBody();
//		body.isSelected(0);
//	}
	
	
//	@Test(expected = IllegalArgumentException.class)
//	public void selectItemBackwards() throws Exception {
//		Matrix matrix = new Matrix(new Shell(), SWT.None);
//		Section body = matrix.getAxis0().getBody();
//		body.setCount(4);
//		body.setSelected(2, 1, true);
//	}
	
	@Test
	public void selectItemSelectsCells() throws Exception {
		Matrix matrix = new Matrix(new Shell(), SWT.None);
		matrix.getAxis0().getBody().setCount(10);
		Section body = matrix.getAxis1().getBody();
		body.setCount(4);
		body.setSelected(0, 0, true);
		assertEquals(10, matrix.getBody().getSelectedCount().intValue());
	}
	
	// Ticket #9
	@Test public void bug9() throws Exception {
	  Axis<BigInteger> axis0 = new Axis<BigInteger>(BigInteger.class, 2);

    Matrix<BigInteger, Integer> matrix = new Matrix<BigInteger, Integer>(
      new Shell(), SWT.V_SCROLL | SWT.H_SCROLL, axis0, null);
    
    matrix.getAxis1().getBody().setCount(2);
    matrix.getAxis0().getBody().setCount(new BigInteger("123456789012345678901234567890"));
    
    matrix.getBody().setSelected(BigInteger.ZERO, 0, true);
  }
}
