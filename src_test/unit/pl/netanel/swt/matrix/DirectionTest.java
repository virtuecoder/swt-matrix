package pl.netanel.swt.matrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static pl.netanel.swt.matrix.TestUtil.item;
import static pl.netanel.swt.matrix.TestUtil.layout;
import static pl.netanel.swt.matrix.TestUtil.number;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@SuppressWarnings({"rawtypes", "unchecked"}) @RunWith(JUnit4.class) public class  DirectionTest {
	
	@Test
	public void init() throws Exception {
		// Empty
		Direction forward, backward;
		AxisLayout layout = layout(0);
		assertEquals(false, layout.forward.initNotEmpty());
		assertEquals(false, layout.backward.initNotEmpty());
		assertEquals(false, layout.forwardNavigator.initNotEmpty());
		assertEquals(false, layout.backwardNavigator.initNotEmpty());
		
		// Not empty
		layout = layout(3);
		Section section = layout.getSection(0);
		
		forward = layout.forward;
		backward = layout.backward;
		assertEquals(true, forward.initNotEmpty());
		assertEquals(true, backward.initNotEmpty());
		
		// First hidden
		section.setHidden(0, 0, true);
		assertEquals(true, forward.initNotEmpty());
		assertEquals(true, backward.initNotEmpty());
		
		// Only last not hidden
		section.setHidden(0, 1, true);
		assertEquals(true, forward.initNotEmpty());
		assertEquals(true, backward.initNotEmpty());
		
		// All hidden
		section.setHidden(0, 2, true);
		assertEquals(false, forward.initNotEmpty());
		assertEquals(false, backward.initNotEmpty());

		// All hidden with two extents
		section.setOrder(1, 2, 0);
		assertEquals(false, forward.initNotEmpty());
		assertEquals(false, backward.initNotEmpty());
		
		// One not hidden in the first extent 
		section.setHidden(1, 1, false);
		assertEquals(true, forward.initNotEmpty());
		assertEquals(true, backward.initNotEmpty());

		// One not hidden in the second extent 
		section.setHidden(1, 1, true);
		section.setHidden(0, 0, false);
		assertEquals(true, forward.initNotEmpty());
		assertEquals(true, backward.initNotEmpty());
		
		// All hidden with two sections
		layout = layout(3, 2);
		section = layout.getSection(0);
		forward = layout.forward;
		backward = layout.backward;
		layout.getSection(1).setHidden(0, 1, true);
		
		layout.getSection(0).setHidden(0, 2, true);
		assertEquals(false, forward.initNotEmpty());
		assertEquals(false, backward.initNotEmpty());
		
		// All hidden in first section and some not hidden in second one
		section.setHidden(0, 0, false);
		assertEquals(true, forward.initNotEmpty());
		assertEquals(true, backward.initNotEmpty());
	}
	
	@Test
	public void first() throws Exception {
		// Empty
		AxisLayout layout = layout(0);
		Direction direction = layout.forward;
		assertEquals(null, direction.first());
		
		// Not empty
		layout = layout(3);
		direction = layout.forward;
		assertEquals("0", direction.first().getIndex().toString());
		
		// First hidden
		layout.getSection(0).setHidden(0, 0, true);
		assertEquals("1", direction.first().getIndex().toString());
		
		// Only last not hidden
		layout.getSection(0).setHidden(0, 1, true);
		assertEquals("2", direction.first().getIndex().toString());
		
		// All hidden
		layout.getSection(0).setHidden(0, 2, true);
		assertEquals(null, direction.first());
		
		// One not hidden in the first extent 
		layout.getSection(0).setOrder(1, 2, 0);
		layout.getSection(0).setHidden(1, 1, false);
		assertEquals("1", direction.first().getIndex().toString());
		
		// One not hidden in the second extent 
		layout.getSection(0).setHidden(1, 1, true);
		layout.getSection(0).setHidden(0, 0, false);
		assertEquals("0", direction.first().getIndex().toString());
		
		// All hidden in first section and the second one empty
		layout = layout(3, 0);
		direction = layout.forward;
		layout.getSection(0).setHidden(0, 2, true);
		assertEquals(null, direction.first());
		
		// All hidden in the first section and the second one not empty
		layout = layout(3, 2);
		direction = layout.forward;
		layout.getSection(0).setHidden(0, 2, true);
		assertEquals("1:0", direction.first().toString());
		
		// All hidden in first section and first hidden in the second one
		layout.getSection(1).setHidden(0, 0, true);
		direction = layout.forward;
		assertEquals("1:1", direction.first().toString());
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
		AxisLayout layout = layout(0);
		Direction direction = layout.forward;
		direction.initNotEmpty();
		assertEquals(null, direction.nextItem());
		
		// Not empty
		layout = layout(3);
		direction = layout.forward;
		direction.initNotEmpty();
		assertEquals("0", direction.nextItem().getIndex().toString());
		assertEquals("1", direction.nextItem().getIndex().toString());
		assertEquals("2", direction.nextItem().getIndex().toString());
		assertEquals(null, direction.nextItem());
		
		// First hidden
		layout.getSection(0).setHidden(0, 0, true);
		assertEquals("1", direction.first().getIndex().toString());
		
		// Only last not hidden
		layout.getSection(0).setHidden(0, 1, true);
		direction.initNotEmpty();
		assertEquals("2", direction.first().getIndex().toString());
		
		// Last is hidden
		layout.getSection(0).setHidden(0, 2, false);
		layout.getSection(0).setHidden(2, 2, true);
		direction.initNotEmpty();
		assertEquals("0", direction.nextItem().getIndex().toString());
		assertEquals("1", direction.nextItem().getIndex().toString());
		assertEquals(null, direction.nextItem());
		
		// All hidden
		layout.getSection(0).setHidden(0, 2, true);
		direction.initNotEmpty();
		assertEquals(null, direction.first());
		
		// One hidden in the first extent 
		layout.getSection(0).setOrder(1, 2, 0);
		layout.getSection(0).setHidden(1, 1, false);
		layout.getSection(0).setHidden(0, 0, false);
		direction.initNotEmpty();
		assertEquals("1", direction.nextItem().getIndex().toString());
		assertEquals("0", direction.nextItem().getIndex().toString());
		
		// Two sections
		layout = layout(2, 2);
		direction = layout.forward;
		direction.initNotEmpty();
		assertEquals("0:0", direction.nextItem().toString());
		assertEquals("0:1", direction.nextItem().toString());
		assertEquals("1:0", direction.nextItem().toString());
		assertEquals("1:1", direction.nextItem().toString());
		
		// Hide the last index in the first section and the first index in the second section
		layout.getSection(0).setHidden(1, 1, true);
		layout.getSection(1).setHidden(0, 0, true);
		direction.initNotEmpty();
		assertEquals("0:0", direction.nextItem().toString());
		assertEquals("1:1", direction.nextItem().toString());
	}
	
	@Test
	public void nextBacward() throws Exception {
		// Empty
		AxisLayout layout = layout(0);
		Direction direction = layout.backward;
		direction.initNotEmpty();
		assertEquals(null, direction.nextItem());
		
		// Not empty
		layout = layout(3);
		direction = layout.backward;
		direction.initNotEmpty();
		assertEquals("2", direction.nextItem().getIndex().toString());
		assertEquals("1", direction.nextItem().getIndex().toString());
		assertEquals("0", direction.nextItem().getIndex().toString());
		assertEquals(null, direction.nextItem());
		
		// First hidden
		layout.getSection(0).setHidden(2, 2, true);
		assertEquals("1", direction.first().getIndex().toString());
		
		// Only last not hidden
		layout.getSection(0).setHidden(1, 2, true);
		direction.initNotEmpty();
		assertEquals("0", direction.first().getIndex().toString());
		
		// Last is hidden
		layout.getSection(0).setHidden(0, 2, false);
		layout.getSection(0).setHidden(0, 0, true);
		direction.initNotEmpty();
		assertEquals("2", direction.nextItem().getIndex().toString());
		assertEquals("1", direction.nextItem().getIndex().toString());
		assertEquals(null, direction.nextItem());
		
		// All hidden
		layout.getSection(0).setHidden(0, 2, true);
		direction.initNotEmpty();
		assertEquals(null, direction.first());
		
		// One hidden in the first extent 
		layout.getSection(0).setOrder(0, 1, 3);
		layout.getSection(0).setHidden(1, 1, false);
		layout.getSection(0).setHidden(2, 2, false);
		direction.initNotEmpty();
		assertEquals("1", direction.nextItem().getIndex().toString());
		assertEquals("2", direction.nextItem().getIndex().toString());
		
		// Two sections
		layout = layout(2, 2);
		direction = layout.backward;
		direction.initNotEmpty();
		assertEquals("1:1", direction.nextItem().toString());
		assertEquals("1:0", direction.nextItem().toString());
		assertEquals("0:1", direction.nextItem().toString());
		assertEquals("0:0", direction.nextItem().toString());
		
		// Hide the last index in the first section and the first index in the second section
		layout.getSection(0).setHidden(1, 1, true);
		layout.getSection(1).setHidden(0, 0, true);
		direction.initNotEmpty();
		assertEquals("1:1", direction.nextItem().toString());
		assertEquals("0:0", direction.nextItem().toString());
	}
	
	@Test
	public void nextCount() throws Exception {
		// Empty
		AxisLayout layout = layout(0);
		Direction direction = layout.forward;
		direction.initNotEmpty();
		assertEquals(null, direction.nextItem());
		
		// Not empty
		layout = layout(10);
		direction = layout.forward;
		direction.initNotEmpty();
		assertEquals("2", direction.next(number(3)).getIndex().toString());
		assertEquals("5", direction.next(number(3)).getIndex().toString());
		assertEquals("8", direction.next(number(3)).getIndex().toString());
		assertEquals("9", direction.next(number(3)).getIndex().toString());
		assertEquals(null, direction.next(number(3)));
		
		// With hidden
		layout.getSection(0).setHidden(0, 0, true);
		layout.getSection(0).setHidden(3, 6, true);
		layout.getSection(0).setHidden(9, 9, true);
		direction.initNotEmpty();
		assertEquals("2", direction.next(number(2)).getIndex().toString());
		assertEquals("8", direction.next(number(2)).getIndex().toString());
		assertEquals(null, direction.next(number(2)));
		
		direction.initNotEmpty();
		assertEquals("8", direction.next(number(4)).getIndex().toString());
		
		// With many hidden on the way
		layout.getSection(0).setHidden(4, 4, false);
		direction.initNotEmpty();
		assertEquals("8", direction.next(number(5)).getIndex().toString());
		
		// With many extents
		layout.getSection(0).setHidden(0, 9, false);
		layout.getSection(0).setOrder(6, 9, 3);
		layout.getSection(0).setHidden(2, 4, true);
		direction.initNotEmpty();
		assertEquals("6", direction.next(number(3)).getIndex().toString());
		assertEquals("5", direction.next(number(5)).getIndex().toString());
		assertEquals(null, direction.next(number(2)));
		direction.initNotEmpty();
//		assertEquals("5", direction.next(number(8)).index.toString());
		
		
		// With many sections
		layout = layout(5, 5);
		direction = layout.forward;
		direction.initNotEmpty();
		assertEquals("1:1", direction.next(number(7)).toString());
		
		// Many sections with hidden
	}
	
	@Test
	public void nextCountBackward() throws Exception {
		// Empty
		AxisLayout layout = layout(0);
		Direction direction = layout.backward;
		direction.initNotEmpty();
		assertEquals(null, direction.nextItem());
		
		// Not empty
		layout = layout(10);
		direction = layout.backward;
		direction.initNotEmpty();
		assertEquals("7", direction.next(number(3)).getIndex().toString());
		assertEquals("4", direction.next(number(3)).getIndex().toString());
		assertEquals("1", direction.next(number(3)).getIndex().toString());
		assertEquals("0", direction.next(number(3)).getIndex().toString());
		assertEquals(null, direction.next(number(3)));
		
		// With hidden
		layout.getSection(0).setHidden(0, 0, true);
		layout.getSection(0).setHidden(3, 6, true);
		layout.getSection(0).setHidden(9, 9, true);
		direction.initNotEmpty();
		assertEquals("7", direction.next(number(2)).getIndex().toString());
		assertEquals("1", direction.next(number(2)).getIndex().toString());
		assertEquals(null, direction.next(number(2)));
		
		direction.initNotEmpty();
		assertEquals("1", direction.next(number(4)).getIndex().toString());
		
		// With many hidden on the way
		layout.getSection(0).setHidden(4, 4, false);
		direction.initNotEmpty();
		assertEquals("1", direction.next(number(5)).getIndex().toString());
		
		// With many extents
		layout.getSection(0).setHidden(0, 9, false);
		layout.getSection(0).setOrder(6, 9, 3);
		layout.getSection(0).setHidden(2, 4, true);
		direction.initNotEmpty();
		assertEquals("8", direction.next(number(3)).getIndex().toString());
		assertEquals("0", direction.next(number(5)).getIndex().toString());
		assertEquals(null, direction.next(number(2)));
		direction.initNotEmpty();
//		assertEquals("5", direction.next(number(8)).index.toString());
		
		
		// With many sections
		layout = layout(5, 5);
		direction = layout.backward;
		direction.initNotEmpty();
		assertEquals("0:3", direction.next(number(7)).toString());
		
		// Many sections with hidden
	}
	
	@Test
	public void set() throws Exception {
		AxisLayout layout = layout(10);
		Direction direction = layout.forward;
		Section section = layout.getSection(0);
		direction.setHasMore(item(section, 0));
		assertEquals("0", direction.getItem().getIndex().toString());
		direction.setHasMore(item(section, 5));
		assertEquals("5", direction.getItem().getIndex().toString());
		assertEquals("6", direction.nextItem().getIndex().toString());
		
		direction = layout.backward;
		direction.setHasMore(item(section, 9));
		assertEquals("9", direction.getItem().getIndex().toString());
		direction.setHasMore(item(section, 5));
		assertEquals("5", direction.getItem().getIndex().toString());
		assertEquals("4", direction.nextItem().getIndex().toString());
	}
	
	@Test
	public void setCurrentItemEnabled() throws Exception {
		AxisLayout layout = layout(2, 10, 1);
		Section section = layout.getSection(0);
		
		Direction forward = layout.forwardNavigator;
		assertEquals("1:0", forward.first().toString());
		section.setFocusItemEnabled(true);
		assertEquals("0:0", forward.first().toString());
		section.setFocusItemEnabled(false);
		forward.initNotEmpty();
		assertTrue(forward.nextItem() != null);
		assertEquals("1:9", forward.next(number(20)).toString());
		assertTrue(forward.nextItem() == null);
		
		forward.setHasMore(item(section, 0));
		assertEquals("1:0", forward.getItem().toString());
		forward.setHasMore(item(layout.getSection(1), 9));
		assertEquals(null, forward.nextItem());
		
		section = layout.getSection(2);
		Direction backward = layout.backwardNavigator;
		assertEquals("1:9", backward.first().toString());
		section.setFocusItemEnabled(true);
		assertEquals("2:0", backward.first().toString());
		section.setFocusItemEnabled(false);
		backward.initNotEmpty();
		assertTrue(backward.nextItem() != null);
		assertEquals("1:0", backward.next(number(20)).toString());
		assertTrue(backward.nextItem() == null);
		
		backward.setHasMore(item(layout.getSection(2), 0));
		assertEquals("1:9", backward.getItem().toString());

	}
	
	@Test
	public void sectionsWithCurrentEnableDisabledEnabled() throws Exception {
		AxisLayout layout = layout(2, 10, 1);
		layout.getSection(0).setFocusItemEnabled(true);
		layout.getSection(1).setFocusItemEnabled(false);
		layout.getSection(2).setFocusItemEnabled(true);
		
		Direction forward = layout.forwardNavigator;
		Direction backward = layout.backwardNavigator;
		assertEquals("0:0", forward.first().toString());
		assertEquals("0:1", forward.nextItem().toString());
		assertEquals("2:0", forward.nextItem().toString());
		backward.initNotEmpty();
		assertEquals("0:0", backward.next(number(20)).toString());
	}
	
	@Test
	public void simple() throws Exception {
		AxisLayout layout = layout(3);
		Direction direction = layout.forward;
		direction.initNotEmpty();
		assertEquals("0", direction.nextItem().getIndex().toString());
		assertEquals("1", direction.nextItem().getIndex().toString());
		assertEquals("2", direction.nextItem().getIndex().toString());
		assertEquals(null, direction.nextItem());
	
		// Next count
		layout = layout(10);
		direction = layout.forward;
		direction.initNotEmpty();
		assertEquals("2", direction.next(number(3)).getIndex().toString());
		assertEquals("5", direction.next(number(3)).getIndex().toString());
		assertEquals("8", direction.next(number(3)).getIndex().toString());
		assertEquals("9", direction.next(number(3)).getIndex().toString());
		assertEquals(null, direction.next(number(3)));
		
		// Multiple extents
		layout.getSection(0).setOrder(6, 9, 3);
		direction.initNotEmpty();
		assertEquals("7", direction.next(number(5)).getIndex().toString());
		assertEquals("4", direction.next(number(4)).getIndex().toString());
		assertEquals("5", direction.next(number(4)).getIndex().toString());
		assertEquals(null, direction.next(number(4)));
		
		// With many sections
		layout = layout(5, 5);
		direction = layout.forward;
		direction.initNotEmpty();
		assertEquals("1:1", direction.next(number(7)).toString());
	}
}
