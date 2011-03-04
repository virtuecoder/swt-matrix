package pl.netanel.swt.matrix;

import static org.junit.Assert.*;
import static pl.netanel.swt.matrix.TestUtil.*;


import org.junit.Test;

import pl.netanel.swt.matrix.Direction;
import pl.netanel.swt.matrix.Layout;
import pl.netanel.swt.matrix.Section;


public class DirectionTest {
	
	@Test
	public void init() throws Exception {
		// Empty
		Direction forward, backward;
		Layout layout = layout(0);
		assertEquals(false, layout.forward.init());
		assertEquals(false, layout.backward.init());
		assertEquals(false, layout.forwardNavigator.init());
		assertEquals(false, layout.backwardNavigator.init());
		
		// Not empty
		layout = layout(3);
		Section section = layout.getSection(0);
		
		forward = layout.forward;
		backward = layout.backward;
		assertEquals(true, forward.init());
		assertEquals(true, backward.init());
		
		// First hidden
		section.setHidden(0, true);
		assertEquals(true, forward.init());
		assertEquals(true, backward.init());
		
		// Only last not hidden
		section.setHidden(0, 1, true);
		assertEquals(true, forward.init());
		assertEquals(true, backward.init());
		
		// All hidden
		section.setHidden(0, 2, true);
		assertEquals(false, forward.init());
		assertEquals(false, backward.init());

		// All hidden with two extents
		section.move(1, 2, 0);
		assertEquals(false, forward.init());
		assertEquals(false, backward.init());
		
		// One not hidden in the first extent 
		section.setHidden(1, false);
		assertEquals(true, forward.init());
		assertEquals(true, backward.init());

		// One not hidden in the second extent 
		section.setHidden(1, true);
		section.setHidden(0, false);
		assertEquals(true, forward.init());
		assertEquals(true, backward.init());
		
		// All hidden with two sections
		layout = layout(3, 2);
		section = layout.getSection(0);
		forward = layout.forward;
		backward = layout.backward;
		layout.getSection(1).setHidden(0, 1, true);
		
		layout.getSection(0).setHidden(0, 2, true);
		assertEquals(false, forward.init());
		assertEquals(false, backward.init());
		
		// All hidden in first section and some not hidden in second one
		section.setHidden(0, false);
		assertEquals(true, forward.init());
		assertEquals(true, backward.init());
	}
	
	@Test
	public void first() throws Exception {
		// Empty
		Layout layout = layout(0);
		Direction direction = layout.forward;
		assertEquals(null, direction.first());
		
		// Not empty
		layout = layout(3);
		direction = layout.forward;
		assertEquals("0", direction.first().index.toString());
		
		// First hidden
		layout.getSection(0).setHidden(0, true);
		assertEquals("1", direction.first().index.toString());
		
		// Only last not hidden
		layout.getSection(0).setHidden(0, 1, true);
		assertEquals("2", direction.first().index.toString());
		
		// All hidden
		layout.getSection(0).setHidden(0, 2, true);
		assertEquals(null, direction.first());
		
		// One not hidden in the first extent 
		layout.getSection(0).move(1, 2, 0);
		layout.getSection(0).setHidden(1, false);
		assertEquals("1", direction.first().index.toString());
		
		// One not hidden in the second extent 
		layout.getSection(0).setHidden(1, true);
		layout.getSection(0).setHidden(0, false);
		assertEquals("0", direction.first().index.toString());
		
		// All hidden in first section and the second one empty
		layout = layout(3, 0);
		direction = layout.forward;
		layout.getSection(0).setHidden(0, 2, true);
		assertEquals(null, direction.first());
		
		// All hidden in the first section and the second one not empty
		layout = layout(3, 2);
		direction = layout.forward;
		layout.getSection(0).setHidden(0, 2, true);
		assertEquals("1 0", direction.first().toString());
		
		// All hidden in first section and first hidden in the second one
		layout.getSection(1).setHidden(0, true);
		direction = layout.forward;
		assertEquals("1 1", direction.first().toString());
	}
	
//	@Test(expected = IllegalStateException.class)
//	public void uninitialized() throws Exception {
//		// Empty
//		Layout layout = layout(0);
//		Direction direction = layout.forward;
//		direction.next();
//	}
	
	@Test
	public void next() throws Exception {
		// Empty
		Layout layout = layout(0);
		Direction direction = layout.forward;
		direction.init();
		assertEquals(null, direction.next());
		
		// Not empty
		layout = layout(3);
		direction = layout.forward;
		direction.init();
		assertEquals("0", direction.next().index.toString());
		assertEquals("1", direction.next().index.toString());
		assertEquals("2", direction.next().index.toString());
		assertEquals(null, direction.next());
		
		// First hidden
		layout.getSection(0).setHidden(0, true);
		assertEquals("1", direction.first().index.toString());
		
		// Only last not hidden
		layout.getSection(0).setHidden(0, 1, true);
		direction.init();
		assertEquals("2", direction.first().index.toString());
		
		// Last is hidden
		layout.getSection(0).setHidden(0, 2, false);
		layout.getSection(0).setHidden(2, true);
		direction.init();
		assertEquals("0", direction.next().index.toString());
		assertEquals("1", direction.next().index.toString());
		assertEquals(null, direction.next());
		
		// All hidden
		layout.getSection(0).setHidden(0, 2, true);
		direction.init();
		assertEquals(null, direction.first());
		
		// One hidden in the first extent 
		layout.getSection(0).move(1, 2, 0);
		layout.getSection(0).setHidden(1, false);
		layout.getSection(0).setHidden(0, false);
		direction.init();
		assertEquals("1", direction.next().index.toString());
		assertEquals("0", direction.next().index.toString());
		
		// Two sections
		layout = layout(2, 2);
		direction = layout.forward;
		direction.init();
		assertEquals("0 0", direction.next().toString());
		assertEquals("0 1", direction.next().toString());
		assertEquals("1 0", direction.next().toString());
		assertEquals("1 1", direction.next().toString());
		
		// Hide the last index in the first section and the first index in the second section
		layout.getSection(0).setHidden(1, true);
		layout.getSection(1).setHidden(0, true);
		direction.init();
		assertEquals("0 0", direction.next().toString());
		assertEquals("1 1", direction.next().toString());
	}
	
	@Test
	public void nextBacward() throws Exception {
		// Empty
		Layout layout = layout(0);
		Direction direction = layout.backward;
		direction.init();
		assertEquals(null, direction.next());
		
		// Not empty
		layout = layout(3);
		direction = layout.backward;
		direction.init();
		assertEquals("2", direction.next().index.toString());
		assertEquals("1", direction.next().index.toString());
		assertEquals("0", direction.next().index.toString());
		assertEquals(null, direction.next());
		
		// First hidden
		layout.getSection(0).setHidden(2, true);
		assertEquals("1", direction.first().index.toString());
		
		// Only last not hidden
		layout.getSection(0).setHidden(1, 2, true);
		direction.init();
		assertEquals("0", direction.first().index.toString());
		
		// Last is hidden
		layout.getSection(0).setHidden(0, 2, false);
		layout.getSection(0).setHidden(0, true);
		direction.init();
		assertEquals("2", direction.next().index.toString());
		assertEquals("1", direction.next().index.toString());
		assertEquals(null, direction.next());
		
		// All hidden
		layout.getSection(0).setHidden(0, 2, true);
		direction.init();
		assertEquals(null, direction.first());
		
		// One hidden in the first extent 
		layout.getSection(0).move(0, 1, 3);
		layout.getSection(0).setHidden(1, false);
		layout.getSection(0).setHidden(2, false);
		direction.init();
		assertEquals("1", direction.next().index.toString());
		assertEquals("2", direction.next().index.toString());
		
		// Two sections
		layout = layout(2, 2);
		direction = layout.backward;
		direction.init();
		assertEquals("1 1", direction.next().toString());
		assertEquals("1 0", direction.next().toString());
		assertEquals("0 1", direction.next().toString());
		assertEquals("0 0", direction.next().toString());
		
		// Hide the last index in the first section and the first index in the second section
		layout.getSection(0).setHidden(1, true);
		layout.getSection(1).setHidden(0, true);
		direction.init();
		assertEquals("1 1", direction.next().toString());
		assertEquals("0 0", direction.next().toString());
	}
	
	@Test
	public void nextCount() throws Exception {
		// Empty
		Layout layout = layout(0);
		Direction direction = layout.forward;
		direction.init();
		assertEquals(null, direction.next());
		
		// Not empty
		layout = layout(10);
		direction = layout.forward;
		direction.init();
		assertEquals("2", direction.next(number(3)).index.toString());
		assertEquals("5", direction.next(number(3)).index.toString());
		assertEquals("8", direction.next(number(3)).index.toString());
		assertEquals("9", direction.next(number(3)).index.toString());
		assertEquals(null, direction.next(number(3)));
		
		// With hidden
		layout.getSection(0).setHidden(0, true);
		layout.getSection(0).setHidden(3, 6, true);
		layout.getSection(0).setHidden(9, true);
		direction.init();
		assertEquals("2", direction.next(number(2)).index.toString());
		assertEquals("8", direction.next(number(2)).index.toString());
		assertEquals(null, direction.next(number(2)));
		
		direction.init();
		assertEquals("8", direction.next(number(4)).index.toString());
		
		// With many hidden on the way
		layout.getSection(0).setHidden(4, false);
		direction.init();
		assertEquals("8", direction.next(number(5)).index.toString());
		
		// With many extents
		layout.getSection(0).setHidden(0, 9, false);
		layout.getSection(0).move(6, 9, 3);
		layout.getSection(0).setHidden(2, 4, true);
		direction.init();
		assertEquals("6", direction.next(number(3)).index.toString());
		assertEquals("5", direction.next(number(5)).index.toString());
		assertEquals(null, direction.next(number(2)));
		direction.init();
//		assertEquals("5", direction.next(number(8)).index.toString());
		
		
		// With many sections
		layout = layout(5, 5);
		direction = layout.forward;
		direction.init();
		assertEquals("1 1", direction.next(number(7)).toString());
		
		// Many sections with hidden
	}
	
	@Test
	public void nextCountBackward() throws Exception {
		// Empty
		Layout layout = layout(0);
		Direction direction = layout.backward;
		direction.init();
		assertEquals(null, direction.next());
		
		// Not empty
		layout = layout(10);
		direction = layout.backward;
		direction.init();
		assertEquals("7", direction.next(number(3)).index.toString());
		assertEquals("4", direction.next(number(3)).index.toString());
		assertEquals("1", direction.next(number(3)).index.toString());
		assertEquals("0", direction.next(number(3)).index.toString());
		assertEquals(null, direction.next(number(3)));
		
		// With hidden
		layout.getSection(0).setHidden(0, true);
		layout.getSection(0).setHidden(3, 6, true);
		layout.getSection(0).setHidden(9, true);
		direction.init();
		assertEquals("7", direction.next(number(2)).index.toString());
		assertEquals("1", direction.next(number(2)).index.toString());
		assertEquals(null, direction.next(number(2)));
		
		direction.init();
		assertEquals("1", direction.next(number(4)).index.toString());
		
		// With many hidden on the way
		layout.getSection(0).setHidden(4, false);
		direction.init();
		assertEquals("1", direction.next(number(5)).index.toString());
		
		// With many extents
		layout.getSection(0).setHidden(0, 9, false);
		layout.getSection(0).move(6, 9, 3);
		layout.getSection(0).setHidden(2, 4, true);
		direction.init();
		assertEquals("8", direction.next(number(3)).index.toString());
		assertEquals("0", direction.next(number(5)).index.toString());
		assertEquals(null, direction.next(number(2)));
		direction.init();
//		assertEquals("5", direction.next(number(8)).index.toString());
		
		
		// With many sections
		layout = layout(5, 5);
		direction = layout.backward;
		direction.init();
		assertEquals("0 3", direction.next(number(7)).toString());
		
		// Many sections with hidden
	}
	
	@Test
	public void set() throws Exception {
		Layout layout = layout(10);
		Direction direction = layout.forward;
		Section section = layout.getSection(0);
		direction.set(item(section, 0));
		assertEquals("0", direction.getItem().index.toString());
		direction.set(item(section, 5));
		assertEquals("5", direction.getItem().index.toString());
		assertEquals("6", direction.next().index.toString());
		
		direction = layout.backward;
		direction.set(item(section, 9));
		assertEquals("9", direction.getItem().index.toString());
		direction.set(item(section, 5));
		assertEquals("5", direction.getItem().index.toString());
		assertEquals("4", direction.next().index.toString());
	}
	
	@Test
	public void setCurrentItemEnabled() throws Exception {
		Layout layout = layout(2, 10, 1);
		Section section = layout.getSection(0);
		
		Direction forward = layout.forwardNavigator;
		assertEquals("1 0", forward.first().toString());
		section.setNavigationEnabled(true);
		assertEquals("0 0", forward.first().toString());
		section.setNavigationEnabled(false);
		forward.init();
		assertTrue(forward.next() != null);
		assertEquals("1 9", forward.next(number(20)).toString());
		assertTrue(forward.next() == null);
		
		forward.set(item(section, 0));
		assertEquals("1 0", forward.getItem().toString());
		forward.set(item(layout.getSection(1), 9));
		assertEquals(null, forward.next());
		
		section = layout.getSection(2);
		Direction backward = layout.backwardNavigator;
		assertEquals("1 9", backward.first().toString());
		section.setNavigationEnabled(true);
		assertEquals("2 0", backward.first().toString());
		section.setNavigationEnabled(false);
		backward.init();
		assertTrue(backward.next() != null);
		assertEquals("1 0", backward.next(number(20)).toString());
		assertTrue(backward.next() == null);
		
		backward.set(item(layout.getSection(2), 0));
		assertEquals("1 9", backward.getItem().toString());

	}
	
	@Test
	public void sectionsWithCurrentEnableDisabledEnabled() throws Exception {
		Layout layout = layout(2, 10, 1);
		layout.getSection(0).setNavigationEnabled(true);
		layout.getSection(1).setNavigationEnabled(false);
		layout.getSection(2).setNavigationEnabled(true);
		
		Direction forward = layout.forwardNavigator;
		Direction backward = layout.backwardNavigator;
		assertEquals("0 0", forward.first().toString());
		assertEquals("0 1", forward.next().toString());
		assertEquals("2 0", forward.next().toString());
		backward.init();
		assertEquals("0 0", backward.next(number(20)).toString());
	}
	
	@Test
	public void simple() throws Exception {
		Layout layout = layout(3);
		Direction direction = layout.forward;
		direction.init();
		assertEquals("0", direction.next().index.toString());
		assertEquals("1", direction.next().index.toString());
		assertEquals("2", direction.next().index.toString());
		assertEquals(null, direction.next());
	
		// Next count
		layout = layout(10);
		direction = layout.forward;
		direction.init();
		assertEquals("2", direction.next(number(3)).index.toString());
		assertEquals("5", direction.next(number(3)).index.toString());
		assertEquals("8", direction.next(number(3)).index.toString());
		assertEquals("9", direction.next(number(3)).index.toString());
		assertEquals(null, direction.next(number(3)));
		
		// Multiple extents
		layout.getSection(0).move(6, 9, 3);
		direction.init();
		assertEquals("7", direction.next(number(5)).index.toString());
		assertEquals("4", direction.next(number(4)).index.toString());
		assertEquals("5", direction.next(number(4)).index.toString());
		assertEquals(null, direction.next(number(4)));
		
		// With many sections
		layout = layout(5, 5);
		direction = layout.forward;
		direction.init();
		assertEquals("1 1", direction.next(number(7)).toString());
	}
}
