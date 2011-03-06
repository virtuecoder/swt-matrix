package pl.netanel.swt.matrix;

import org.junit.Ignore;
import org.junit.Test;

public class AxisModelTest {
	@Ignore
	@Test(expected = IllegalArgumentException.class)
	public void zeroSections() throws Exception {
		new AxisModel(int.class);
	}
	
	
}
