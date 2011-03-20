package pl.netanel.swt.matrix;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

public class AxisTest {
	@Ignore
	@Test(expected = IllegalArgumentException.class)
	public void zeroSections() throws Exception {
		new Axis(int.class);
	}
	
	@Test
	public void hideCurrent() throws Exception {
		Axis model = new Axis();
		Layout layout = model.layout;
		layout.setViewportSize(350);

		Section body = model.getBody();
		body.setCount(5);
		body.setDefaultCellWidth(100);
		
		assertEquals(0, model.getNavigationIndex());
		
		model.getBody().hide(0, 0, true);
		layout.compute();
		
		assertEquals(1, model.getNavigationIndex());
	}
}
