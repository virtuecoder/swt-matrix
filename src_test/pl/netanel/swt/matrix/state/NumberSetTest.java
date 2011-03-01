package pl.netanel.swt.matrix.state;

import static org.junit.Assert.*;
import static pl.netanel.swt.matrix.state.TestUtil.*;

import org.junit.Test;


public class NumberSetTest {
	
	@Test
	public void getCount() throws Exception {
		NumberSet list = indexSet(4, 8);
		assertEquals(5, count(list));
		assertEquals(0, count(list, 0, 1));
		assertEquals(0, count(list, 0, 3));
		assertEquals(1, count(list, 3, 4));
		assertEquals(2, count(list, 2, 5));
		assertEquals(2, count(list, 4, 5));
		assertEquals(1, count(list, 5, 5));
		assertEquals(4, count(list, 5, 8));
		assertEquals(1, count(list, 8, 9));
		assertEquals(5, count(list, 0, 9));
		assertEquals(0, count(list, 9, 12));
		
		list = indexSet();
		list.add(extent(7, 8));
		list.add(extent(3, 4));
		assertEquals("7-8, 3-4", list.toString());
		assertEquals(4, count(list));
		assertEquals(4, count(list, 0, 9));
		assertEquals(2, count(list, 4, 7));
	}
	
	@Test
	public void addNotOverlap() throws Exception {
		NumberSet set = indexSet();
		assertEquals("", set.toString());
		
		assertTrue(add(set, 0));
		assertEquals("0", set.toString());
		
		set = indexSet();
		assertTrue(add(set, 0, 2));
		assertEquals("0-2", set.toString());
		
		set = indexSet();
		assertTrue(add(set, 4));
		assertEquals("4", set.toString());
		
		// Existing
		assertFalse(add(set, 4));
		assertEquals("4", set.toString());

		// Adjacent right
		assertTrue(add(set, 5));
		assertEquals("4-5", set.toString());
		
		// Adjacent left
		assertTrue(add(set, 3));
		assertEquals("3-5", set.toString());
		
		// Existing
		assertFalse(add(set, 3, 4));
		assertEquals("3-5", set.toString());
		
		// Gap on the right
		assertTrue(add(set, 7, 8));
		assertEquals("3-5, 7-8", set.toString());
		
		// Gap on the left
		assertTrue(add(set, 1));
		assertEquals("3-5, 7-8, 1", set.toString());
		
		// Existing
		assertFalse(add(set, 1));
		assertEquals("3-5, 7-8, 1", set.toString());
	}
	
	@Test
	public void addSingleOverlap() throws Exception {
		NumberSet set = indexSet(); add(set, 5, 10);
		assertTrue(add(set, 4, 5));
		assertEquals("4-10", set.toString());
		
		// Overlap on the left
		set = indexSet(); add(set, 5, 10);
		assertTrue(add(set, 3, 7));
		assertEquals("3-10", set.toString());

		// Overlap on the right
		set = indexSet(); add(set, 5, 10);
		assertTrue(add(set, 10, 12));
		assertEquals("5-12", set.toString());
		
		// Overlap left and right
		set = indexSet(); add(set, 5, 10);
		assertTrue(add(set, 3, 12));
		assertEquals("3-12", set.toString());
	}
	
	
	@Test public void addMultipleOverlap() throws Exception {
		// Overlaps two, each end in the middle
		NumberSet set = indexSet(); add(set, 0, 4); add(set, 8, 12);
		
		assertTrue(add(set, 2, 10));
		assertEquals("0-12", set.toString());
		
		// Overlaps two completely
		set = indexSet(); add(set, 2, 3); add(set, 5, 6);
		
		assertTrue(add(set, 1, 7));
		assertEquals("1-7", set.toString());
		
		// Overlaps three, each in the middle
		set = indexSet(); add(set, 0, 2); add(set, 4, 6); add(set, 8, 10); 
		assertTrue(add(set, 1, 9));
		assertEquals("0-10", set.toString());
		
		// Overlaps when one is adjacent 
		set = indexSet(); add(set, 2, 4); add(set, 6, 8);  
		assertTrue(add(set, 5, 7));
		assertEquals("2-8", set.toString());

		// Overlaps when one is adjacent 
		set = indexSet(); add(set, 2, 4); add(set, 6, 8);  
		assertTrue(add(set, 3, 5));
		assertEquals("2-8", set.toString());
	}

	@Test public void removeIndex() throws Exception {
		
		NumberSet set = indexSet();
		
		// Remove not existent
		assertFalse(remove(set, 3));
		assertEquals("", set.toString());
		
		// Remove index matching the existing extent
		add(set, 3);
		assertTrue(remove(set, 3));
		assertEquals("", set.toString());
		
		// Remove index matching the beginning of an extent
		add(set, 3, 4);
		assertTrue(remove(set, 3));
		assertEquals("4", set.toString());
		add(set, 3, 4);

		// Remove index matching the end of an extent
		add(set, 3, 4);
		assertTrue(remove(set, 4));
		assertEquals("3", set.toString());
		
		// Remove index matching the middle of an extent
		add(set, 2, 4);
		assertEquals("2-4", set.toString());
		assertTrue(remove(set, 3));
		assertEquals("2, 4", set.toString());
	}
	
	@Test public void removeSingleOverlap() throws Exception {
		NumberSet set = indexSet();
		
		// Remove not existent
		assertFalse(remove(set, 3, 3));
		assertEquals("", set.toString());
		
		// Remove index matching the existing extent
		add(set, 3);
		assertTrue(remove(set, 3, 3));
		assertEquals("", set.toString());
		
		// Remove extent completely overlapping the existing extent
		add(set, 3);
		assertTrue(remove(set, 2, 4));
		assertEquals("", set.toString());
		
		// Remove index matching the beginning of an extent
		add(set, 3, 4);
		assertTrue(remove(set, 3, 3));
		assertEquals("4", set.toString());
		add(set, 3, 4);
		
		// Remove index matching the end of an extent
		add(set, 3, 4);
		assertTrue(remove(set, 4, 4));
		assertEquals("3", set.toString());
		
		// Remove overlapping on the left
		add(set, 4,6);
		assertTrue(remove(set, 3, 5));
		assertEquals("6", set.toString());
		
		// Remove overlapping on the left
		add(set, 4,6);
		assertTrue(remove(set, 5, 7));
		assertEquals("4", set.toString());
		
		// Remove in the middle
		add(set, 4,6);
		assertTrue(remove(set, 5, 5));
		assertEquals("4, 6", set.toString());
	}

	@Test public void removeMultipleOverlap() throws Exception {
		NumberSet set = indexSet(); add(set, 2, 4); add(set, 6, 8);
		
		// Not existent
		assertFalse(remove(set, 5, 5));
		assertEquals("2-4, 6-8", set.toString());
		
		// Matching exactly first extent
		assertTrue(remove(set, 2, 4));
		assertEquals("6-8", set.toString());
		
		// Matching exactly last extent
		add(set, 2, 4);
		assertTrue(remove(set, 6, 8));
		assertEquals("2-4", set.toString());
		
		// Overlaps two, each end in the middle
		add(set, 6, 8);
		assertTrue(remove(set, 3, 7));
		assertEquals("2, 8", set.toString());
		
		// Overlaps two completely
		set = indexSet(); add(set, 2, 4); add(set, 6, 8);
		assertTrue(remove(set, 1, 9));
		assertEquals("", set.toString());
		
		// Overlaps first in the middle  
		set = indexSet(); add(set, 2, 4); add(set, 6, 8);  
		assertTrue(remove(set, 3, 5));
		assertEquals("2, 6-8", set.toString());

		// Overlaps when one is adjacent 
		set = indexSet(); add(set, 6, 8); add(set, 2, 4);   
		assertTrue(remove(set, 5, 7));
		assertEquals("8, 2-4", set.toString());
		assertTrue(remove(set, 4));
		assertEquals("8, 2-3", set.toString());
	}
	
	@Test public void contains() throws Exception {
		NumberSet set = indexSet(); 

		assertEquals(false, constains(set, 2));
		assertEquals(false, constains(set, 2, 4));
		
		add(set, 2, 4); add(set, 6, 8);
		assertEquals(false, constains(set, 1));
		assertEquals(true, constains(set, 2));
		assertEquals(true, constains(set, 3));
		assertEquals(true, constains(set, 4));
		assertEquals(false, constains(set, 5));
		
		assertEquals(true, constains(set, 2, 3));
		assertEquals(true, constains(set, 2, 4));
		assertEquals(true, constains(set, 3, 4));
		assertEquals(false, constains(set, 1, 3));
		assertEquals(false, constains(set, 2, 5));
		assertEquals(false, constains(set, 2, 5));
	}
	

	static private boolean constains(NumberSet set, int n) {
		return set.contains(number(n));
	}

	static private boolean constains(NumberSet set, int start, int end) {
		return set.contains(extent(start, end));
	}

	static private int count(NumberSet set) {
		return set.getCount().intValue();
	}
	
	static private int count(NumberSet set, int start, int end) {
		return set.getCount(number(start), number(end)).intValue();
	}

	static private boolean add(NumberSet set, int n) {
		return set.add(number(n));
	}
	
	static private boolean add(NumberSet set, int start, int end) {
		return set.add(number(start), number(end));
	}
	
	static private boolean remove(NumberSet set, int n) {
		return set.remove(number(n));
	}
	
	static private boolean remove(NumberSet set, int start, int end) {
		return set.remove(number(start), number(end));
	}


}
