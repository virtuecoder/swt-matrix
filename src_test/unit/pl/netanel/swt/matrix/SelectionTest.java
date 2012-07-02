package pl.netanel.swt.matrix;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@SuppressWarnings({"rawtypes", "unchecked"}) @RunWith(JUnit4.class) public class  SelectionTest {
	@Test
	public void emptyAxis() throws Exception {
		Matrix matrix = new Matrix(new Shell(), SWT.None);
		Section body = matrix.getAxisY().getBody();
		body.setSelected(true);
		assertEquals(0, body.getSelectedCount());
	}
	
//	@Test(expected = IndexOutOfBoundsException.class)
//	public void emptyBoundsException() throws Exception {
//		Matrix matrix = new Matrix(new Shell(), SWT.None);
//		Section body = matrix.getAxisY().getBody();
//		body.isSelected(0);
//	}
	
	
//	@Test(expected = IllegalArgumentException.class)
//	public void selectItemBackwards() throws Exception {
//		Matrix matrix = new Matrix(new Shell(), SWT.None);
//		Section body = matrix.getAxisY().getBody();
//		body.setCount(4);
//		body.setSelected(2, 1, true);
//	}
	
	@Test
	public void selectItemSelectsCells() throws Exception {
		Matrix matrix = new Matrix(new Shell(), SWT.None);
		matrix.getAxisY().getBody().setCount(10);
		Section bodyX = matrix.getAxisX().getBody();
		bodyX.setCount(4);
		bodyX.setSelected(0, 0, true);
		assertEquals(10, matrix.getBody().getSelectedCount().intValue());
	}
	
	// Ticket #9
	@Test public void bug9() throws Exception {
	  Axis<BigInteger> axisY = new Axis<BigInteger>(BigInteger.class, 2, 0, 1);

    Matrix<Integer, BigInteger> matrix = new Matrix<Integer, BigInteger>(
      new Shell(), SWT.V_SCROLL | SWT.H_SCROLL, null, axisY);
    
    matrix.getAxisX().getBody().setCount(2);
    matrix.getAxisY().getBody().setCount(new BigInteger("123456789012345678901234567890"));
    
    matrix.getBody().setSelected(0, BigInteger.ZERO, true);
  }
	
	@Test public void selectMergedEnd() throws Exception {
    Matrix matrix = new Matrix(new Shell(), SWT.None);
    matrix.getAxisX().getBody().setCount(10);
    matrix.getAxisY().getBody().setCount(10);
	  Zone body = matrix.getBody();
    body.setMerged(2, 3, 2, 3);
	  body.setSelected(4, 6, 4, 6, true);
//	  TestUtil.showMatrix(matrix);
	  assertTrue(body.isSelected(2, 2));
	  assertTrue(body.isSelected(3, 3));
	  assertTrue(body.isSelected(4, 4));
	  assertTrue(body.isSelected(5, 5));
	  assertTrue(body.isSelected(2, 6));
	}
	
	@Test public void selectMergedStart() throws Exception {
	  Matrix matrix = new Matrix(new Shell(), SWT.None);
	  matrix.getAxisX().getBody().setCount(10);
	  matrix.getAxisY().getBody().setCount(10);
	  Zone body = matrix.getBody();
	  body.setMerged(2, 3, 2, 3);
	  body.setSelected(1, 2, 1, 2, true);
//	  TestUtil.showMatrix(matrix);
	  assertTrue(body.isSelected(1, 1));
	  assertTrue(body.isSelected(2, 2));
	  assertTrue(body.isSelected(3, 3));
	  assertTrue(body.isSelected(4, 4));
	  assertTrue(body.isSelected(1, 4));
	  assertFalse(body.isSelected(5, 5));
	}
	
	@Test public void selectMergedStartXBeyondY() throws Exception {
    Matrix matrix = new Matrix(new Shell(), SWT.None);
    matrix.getAxisX().getBody().setCount(10);
    matrix.getAxisY().getBody().setCount(10);
    Zone body = matrix.getBody();
    body.setMerged(2, 3, 2, 3);
    body.setSelected(1, 2, 1, 6, true);
//    TestUtil.showMatrix(matrix);
    assertTrue(body.isSelected(1, 1));
    assertTrue(body.isSelected(2, 2));
    assertTrue(body.isSelected(3, 3));
    assertTrue(body.isSelected(4, 4));
    assertTrue(body.isSelected(4, 6));
  }
	
	@Test public void selectMerged2() throws Exception {
	  Matrix matrix = new Matrix(new Shell(), SWT.None);
	  matrix.getAxisX().getBody().setCount(10);
	  matrix.getAxisY().getBody().setCount(10);
	  Zone body = matrix.getBody();
	  body.setMerged(2, 1, 2, 2);
	  body.setSelected(1, 2, 2, 2, true);
//    TestUtil.showMatrix(matrix);
	  assertTrue(body.isSelected(1, 2));
	  assertTrue(body.isSelected(1, 3));
	  assertTrue(body.isSelected(2, 2));
	  assertTrue(body.isSelected(2, 3));
	}
	
	@Test public void selectMoved() throws Exception {
    Matrix matrix = new Matrix(new Shell(), SWT.None);
    matrix.getAxisX().getBody().setCount(10);
    matrix.getAxisY().getBody().setCount(10);
    Zone body = matrix.getBody();
    body.getSectionX().setOrder(4, 3);
    body.setMerged(2, 3, 2, 2);
    body.setSelected(1, 2, 2, 4, true);
//    TestUtil.showMatrix(matrix);
    assertTrue(body.isSelected(1, 2));
    assertTrue(body.isSelected(1, 3));
    assertTrue(body.isSelected(2, 2));
    assertTrue(body.isSelected(2, 3));
    assertTrue(body.isSelected(4, 4));
  }
}
