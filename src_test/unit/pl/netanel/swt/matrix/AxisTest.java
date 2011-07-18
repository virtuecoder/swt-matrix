package pl.netanel.swt.matrix;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class) public class  AxisTest {
	@Ignore
	@Test(expected = IllegalArgumentException.class)
	public void zeroSections() throws Exception {
		new Axis(int.class, 2);
	}
	
	@Test
	public void hideCurrent() throws Exception {
		Axis model = new Axis();
		Layout layout = model.layout;
		layout.setViewportSize(350);

		Section body = model.getBody();
		body.setCount(5);
		body.setDefaultCellWidth(100);
		
		assertEquals(0, model.getFocusItem().getIndex());
		
		model.getBody().setHidden(0, 0, true);
		layout.compute();
		
		assertEquals(1, model.getFocusItem().getIndex());
	}
	
}
