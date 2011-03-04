package pl.netanel.swt.matrix;

import static org.junit.Assert.assertEquals;
import static pl.netanel.swt.matrix.TestUtil.layout;

import org.junit.Test;

public class CellIteratorTest {
	@Test
	public void all() throws Exception {
		Layout layout0 = layout(2); layout0.setViewportSize(1000); layout0.compute();
		Layout layout1 = layout(3); layout1.setViewportSize(1000); layout1.compute();
		
		CartesianLayoutSequence seq = new CartesianLayoutSequence (
				layout0.cellSequence(Dock.MAIN, layout0.model.getBody()),
				layout1.cellSequence(Dock.MAIN, layout1.model.getBody()));

		seq.init();
		
		assertEquals(true, seq.next());
		assertEquals("0", seq.getIndex0().toString());
		assertEquals("0", seq.getIndex1().toString());
		
		assertEquals(true, seq.next());
		assertEquals("0", seq.getIndex0().toString());
		assertEquals("1", seq.getIndex1().toString());
		
		assertEquals(true, seq.next());
		assertEquals("0", seq.getIndex0().toString());
		assertEquals("2", seq.getIndex1().toString());
		
		assertEquals(true, seq.next());
		assertEquals("1", seq.getIndex0().toString());
		assertEquals("0", seq.getIndex1().toString());
		
		assertEquals(true, seq.next());
		assertEquals("1", seq.getIndex0().toString());
		assertEquals("1", seq.getIndex1().toString());
		
		assertEquals(true, seq.next());
		assertEquals("1", seq.getIndex0().toString());
		assertEquals("2", seq.getIndex1().toString());
		
		assertEquals(false, seq.next());
	}
}
