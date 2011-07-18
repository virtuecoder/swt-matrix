package pl.netanel.swt.matrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static pl.netanel.swt.matrix.TestUtil.extent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class) public class  NumberQueueSetTest {
	private NumberQueueSet list;

	@Before
	public void _beforeEach() {
		list = new NumberQueueSet(IntMath.getInstance());
	}
	@After
	public void _afterEach() {
		list = null;
	}
	
	@Test
	public void addSingleTheSame() throws Exception {
		list.add(1);
		assertEquals(false, list.add(1));
	}
	
	@Test
	public void addSingleTheSameNotLast() throws Exception {
		list.add(1);
		list.add(3);
		assertEquals(true, list.add(1));
		
		assertEquals("3, 1", list.toString());
	}
	
	@Test
	public void addSingle() throws Exception {
		list.add(1);
		list.add(5);
		list.add(4);
		assertTrue(list.contains(1));
		assertTrue(list.contains(4));
		assertTrue(list.contains(5));
		
		assertEquals("1, 5, 4", list.toString());
	}
	
	@Test
	public void addTheSame() throws Exception {
		list.add(extent(1, 2));
		assertFalse(list.add(extent(1, 2)));
		assertTrue(list.contains(1));
		assertTrue(list.contains(2));
		assertTrue(list.contains(extent(1, 2)));
		
		assertEquals("1-2", list.toString());
	}
	
	@Test
	public void addApart() throws Exception {
		list.add(extent(1, 2));
		list.add(extent(5, 6));
		assertTrue(list.contains(1));
		assertTrue(list.contains(2));
		assertTrue(list.contains(5));
		assertTrue(list.contains(6));
		assertTrue(list.contains(extent(1, 2)));
		assertTrue(list.contains(extent(5, 6)));
		
		assertEquals("1-2, 5-6", list.toString());
	}
	
	@Test
	public void addOverlap() throws Exception {
		list.add(extent(3, 5));
		list.add(extent(2, 6));
		
		assertEquals("2-6", list.toString());
	}
	
	@Test
	public void addInside1() throws Exception {
		list.add(extent(3, 5));
		list.add(extent(4, 4));
		
		assertEquals("3, 5, 4", list.toString());
	}
	
	@Test
	public void addInside2() throws Exception {
		list.add(extent(3, 5));
		list.add(extent(3, 4));
		
		assertEquals("5, 3-4", list.toString());
	}
	
	@Test
	public void addInside3() throws Exception {
		list.add(extent(3, 5));
		list.add(extent(4, 5));
		
		assertEquals("3-5", list.toString());
	}
	
	@Test
	public void addCrossAfter() throws Exception {
		list.add(extent(3, 5));
		list.add(extent(2, 3));
		
		assertEquals("4-5, 2-3", list.toString());
	}
	
	@Test
	public void addAdjacentAfter() throws Exception {
		list.add(extent(3, 5));
		list.add(extent(1, 2));
		
		assertEquals("3-5, 1-2", list.toString());
	}
	
	
	@Test
	public void addCrossBefore() throws Exception {
		list.add(extent(3, 5));
		list.add(extent(4, 8));
		
		assertEquals("3-8", list.toString());
	}
	
	@Test
	public void addAdjacentBefore() throws Exception {
		list.add(extent(3, 5));
		list.add(extent(6, 8));
		
		assertEquals("3-8", list.toString());
	}
	
	@Test
	public void addOverlapMany() throws Exception {
		list.add(extent(1, 2));
		list.add(extent(3, 5));
		list.add(extent(7, 8));
		list.add(extent(0, 9));
		assertEquals("0-9", list.toString());
	}
	
	@Test
	public void addOverlapMany2() throws Exception {
		list.add(extent(1, 2));
		list.add(extent(3, 5));
		list.add(extent(7, 8));
		list.add(extent(0, 8));
		assertEquals("0-8", list.toString());
	}
	
	@Test
	public void addOverlapMany3() throws Exception {
		list.add(extent(1, 2));
		list.add(extent(3, 5));
		list.add(extent(7, 8));
		list.add(extent(0, 7));
		assertEquals("8, 0-7", list.toString());
	}

	@Test
	public void removeNotExisting() throws Exception {
		list.remove(1);
		assertTrue(list.isEmpty());
		
		list.add(2);
		list.remove(1);
		list.remove(3);
		assertTrue(list.contains(2));
		assertFalse(list.contains(1));
		assertFalse(list.contains(3));
	}
	
	@Test
	public void removeTheSame() throws Exception {
		list.add(extent(1, 2));
		list.remove(extent(1, 2));
		assertTrue(list.isEmpty());
		assertFalse(list.contains(extent(1, 2)));
		
		list.add(0);
		list.add(3);
		list.remove(0);
		assertFalse(list.contains(0));
		assertTrue(list.contains(3));
	}
	
	
	@Test
	public void removeOverlap1() throws Exception {
		list.add(extent(3, 5));
		list.remove(extent(1, 7));
		assertTrue(list.isEmpty());
		assertFalse(list.contains(extent(3, 5)));
	}
	
	@Test
	public void removeOverlap2() throws Exception {
		list.add(extent(3, 5));
		list.remove(extent(1, 5));
		assertTrue(list.isEmpty());
		assertFalse(list.contains(extent(3, 5)));
	}
	
	@Test
	public void removeOverlap3() throws Exception {
		list.add(extent(3, 5));
		list.remove(extent(3, 7));
		assertTrue(list.isEmpty());
		assertFalse(list.contains(extent(3, 5)));
	}
	
	@Test
	public void removeInside() throws Exception {
		list.add(extent(3, 5));
		list.remove(extent(4, 4));
		assertEquals("3, 5", list.toString());
	}
	
	@Test
	public void removeCrossBefore1() throws Exception {
		list.add(extent(3, 5));
		list.remove(extent(3, 4));
		assertEquals("5", list.toString());
	}
	
	@Test
	public void removeCrossBefore2() throws Exception {
		list.add(extent(3, 5));
		list.remove(extent(1, 4));
		assertEquals("5", list.toString());
	}
	
	@Test
	public void removeCrossAfter1() throws Exception {
		list.add(extent(3, 5));
		list.remove(extent(4, 5));
		assertEquals("3", list.toString());
	}
	
	@Test
	public void removeCrossAfter2() throws Exception {
		list.add(extent(3, 5));
		list.remove(extent(4, 7));
		assertEquals("3", list.toString());
	}
	
	@Test
	public void getCount() throws Exception {
		assertEquals(0, list.getCount().intValue());
		list.add(extent(1, 2));
		assertEquals(2, list.getCount().intValue());
		list.add(5);
		assertEquals(3, list.getCount().intValue());
	}
}
























