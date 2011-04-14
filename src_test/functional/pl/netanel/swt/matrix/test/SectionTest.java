package pl.netanel.swt.matrix.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.junit.Test;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.Matrix;

public class SectionTest {
	@Test
	public void hideAllSections() throws Exception {
		Matrix matrix = new Matrix(new Shell(), SWT.NONE);
		Axis axis0 = matrix.getAxis0();
		Axis axis1 = matrix.getAxis1();
		axis1.getBody().setCount(4);
		axis0.getBody().setCount(10);
		
		axis0.getBody().setVisible(false);
		axis0.getHeader().setVisible(false);
		
//		TestUtil.showMatrix(matrix);
	}
}
