package pl.netanel.swt.matrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class) public class  AxisViewportTest {
	@Test
	public void isViewportTrimmed_0() throws Exception {
		Axis axis = new Axis();
		axis.layout.setViewportSize(1000);
		assertFalse(axis.isLastCellTrimmed());
	}
	
	@Test
	public void isViewportTrimmed_1() throws Exception {
		Axis axis = new Axis();
		axis.layout.setViewportSize(1000);
		assertFalse(axis.isLastCellTrimmed());
	}
	
	@Test
	public void isViewportTrimmed_5() throws Exception {
		Axis axis = new Axis();
		axis.getBody().setCount(10);
		axis.layout.setViewportSize(85);
		assertTrue(axis.isLastCellTrimmed());
		axis.layout.setViewportSize(86);
		assertFalse(axis.isLastCellTrimmed());
		axis.layout.setViewportSize(87);
		assertTrue(axis.isLastCellTrimmed());
	}
	
	@Test
	public void getViewportItemCount_0() throws Exception {
		Axis axis = new Axis();
		axis.layout.setViewportSize(1000);
		assertEquals(0, axis.getViewportItemCount());
	}
	
	@Test
	public void getViewportItemCount_1() throws Exception {
		Axis axis = new Axis();
		axis.layout.setViewportSize(1000);
		axis.getBody().setCount(1);
		assertEquals(1, axis.getViewportItemCount());
	}
	
	@Test
	public void getViewportItemCount_Trim() throws Exception {
		Axis axis = new Axis();
		axis.layout.setViewportSize(100);
		axis.getBody().setCount(10);
		assertTrue(axis.isLastCellTrimmed());
		assertEquals(6, axis.getViewportItemCount());
	}
	
	@Test
	public void getViewportItemCount_freeze() throws Exception {
		Axis axis = new Axis();
		axis.layout.setViewportSize(100);
		axis.getBody().setCount(10);
		axis.freezeHead(1);
		assertTrue(axis.isLastCellTrimmed());
		assertEquals(6, axis.getViewportItemCount());
	}
	
	
	@Test
	public void getViewportIndexOf_freeze() throws Exception {
		Axis axis = new Axis();
		axis.layout.setViewportSize(100);
		axis.getBody().setCount(10);
		axis.freezeHead(1);
		assertEquals(2, axis.getViewportPosition(new AxisItem(axis.getBody(), 2)));
	}
}
