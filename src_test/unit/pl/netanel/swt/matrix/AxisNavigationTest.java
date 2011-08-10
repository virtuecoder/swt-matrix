package pl.netanel.swt.matrix;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@SuppressWarnings({"rawtypes", "unchecked"}) @RunWith(JUnit4.class) public class  AxisNavigationTest {
	@Test
	public void moveFocusPageFullScreen() throws Exception {
		Axis axis = new Axis();
		axis.getHeader().setVisible(true);
		axis.getBody().setCount(10);
		Layout layout = axis.layout;
		layout.setViewportSize(1000);
		layout.compute();
		
		assertEquals("1:0", layout.current.toString());
		layout.moveFocusItem(Move.PREVIOUS_PAGE);
		assertEquals("1:0", layout.current.toString());
		layout.moveFocusItem(Move.NEXT_PAGE);
		assertEquals("1:9", layout.current.toString());
		layout.moveFocusItem(Move.NEXT_PAGE);
		assertEquals("1:9", layout.current.toString());
		layout.moveFocusItem(Move.PREVIOUS_PAGE);
		assertEquals("1:0", layout.current.toString());
	}
	
	@Test
	public void moveFocusPage() throws Exception {
		Axis axis = new Axis();
		axis.getBody().setCount(10);
		axis.getBody().setDefaultCellWidth(10);
		axis.getBody().setDefaultLineWidth(0);
		Layout layout = axis.layout;
		layout.setViewportSize(55);
		layout.compute();
		
		assertEquals("1:0", layout.current.toString());
		layout.moveFocusItem(Move.NEXT_PAGE);
		assertEquals("1:4", layout.current.toString());
		layout.moveFocusItem(Move.NEXT_PAGE);
		assertEquals("1:9", layout.current.toString());
		assertEquals("1:9", layout.end.toString());
		layout.moveFocusItem(Move.NEXT_PAGE);
		assertEquals("1:9", layout.current.toString());
		
//		layout.moveFocusItem(Move.PREVIOUS_PAGE);
	}
	
	@Test
	public void moveFocusPageExactWidth() throws Exception {
		Axis axis = new Axis();
		axis.getBody().setCount(10);
		axis.getBody().setDefaultCellWidth(10);
		axis.getBody().setDefaultLineWidth(0);
		Layout layout = axis.layout;
		layout.setViewportSize(50);
		layout.compute();
		
		assertEquals("1:0", layout.current.toString());
		layout.moveFocusItem(Move.NEXT_PAGE);
//		TestUtil.showMatrix(layout);
		assertEquals("1:4", layout.current.toString());
		layout.moveFocusItem(Move.NEXT_PAGE);
		assertEquals("1:9", layout.current.toString());
		assertEquals("1:9", layout.end.toString());
		layout.moveFocusItem(Move.NEXT_PAGE);
		assertEquals("1:9", layout.current.toString());
		
//		layout.moveFocusItem(Move.PREVIOUS_PAGE);
	}
	
	@Test
	public void threeSections() throws Exception {
		Axis axis = new Axis(Integer.class, 3, 0, 2);
		axis.getHeader().setVisible(true);
		axis.getSection(1).setFocusItemEnabled(false);
		axis.getBody().setCount(10);
		
		Layout layout = axis.layout;
		layout.setViewportSize(1000);
		layout.compute();
		
		assertEquals("2:0", layout.current.toString());
	}
	
	@Test
	public void ensureCurrentIsValid() throws Exception {
		Axis axis = new Axis();
		axis.getHeader().setVisible(true);
		Section body = axis.getBody();
		body.setCount(10);
		Layout layout = axis.layout;
		layout.setViewportSize(1000);
		layout.compute();
		
		assertEquals("1:0", layout.current.toString());
		body.setHidden(0, true); layout.compute();
		assertEquals("1:1", layout.current.toString());
		axis.setFocusItem(body, 9); layout.compute();
		assertEquals("1:9", layout.current.toString());
		body.setHidden(9, true); layout.compute();
		assertEquals("1:8", layout.current.toString());
	}
	
	@Test
	public void name() throws Exception {
		
	}
}
