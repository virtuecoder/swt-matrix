package pl.netanel.swt.matrix;

import org.junit.Test;

public class AxisModelTest {
	@Test(expected = IllegalArgumentException.class)
	public void zeroSections() throws Exception {
		new AxisModel(int.class);
	}
	
	
}
