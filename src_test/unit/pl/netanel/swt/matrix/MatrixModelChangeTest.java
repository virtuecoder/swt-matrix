package pl.netanel.swt.matrix;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.junit.Test;

public class MatrixModelChangeTest {

	@Test
	public void insert() throws Exception {
		Matrix matrix = new Matrix(new Shell(), SWT.NONE);
		Axis axis = matrix.getAxis0();
		Layout layout = axis.layout;
		layout.setViewportSize(1000);
		
		Section section = axis.getBody();
		section.insert(0, 1);
		section.insert(0, 1);
	}
}
