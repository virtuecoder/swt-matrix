package pl.netanel.swt.matrix;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AxisNavigationText {
	@Test
	public void pageUp() throws Exception {
		Axis axis = new Axis();
		axis.getHeader().setVisible(true);
		axis.getBody().setCount(10);
		Layout layout = axis.layout;
		layout.setViewportSize(1000);
		layout.compute();
		
		assertEquals("1 0", layout.current.toString());
		layout.moveFocusItem(Move.PREVIOUS_PAGE);
		assertEquals("1 0", layout.current.toString());
		layout.moveFocusItem(Move.NEXT_PAGE);
		assertEquals("1 9", layout.current.toString());
		layout.moveFocusItem(Move.PREVIOUS_PAGE);
		assertEquals("1 0", layout.current.toString());
	}
}
