package pl.netanel.swt.matrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static pl.netanel.swt.matrix.TestUtil.number;

import org.junit.Test;

import pl.netanel.swt.matrix.DirectionIndexSequence.Backward;
import pl.netanel.swt.matrix.DirectionIndexSequence.Forward;

public class DirectionIndexSequenceTest {
	@Test
	public void sequence() throws Exception {
		SectionUnchecked section = new SectionUnchecked(int.class);
		Forward seq = new Forward(section);
		assertSequence("", seq);
		
		section.setCount(1);
		assertSequence("0", seq);
		
		section.setCount(5);
		assertSequence("0, 1, 2, 3, 4", seq);
	}
	
	@Test
	public void sequenceHiddenMoved() throws Exception {
		SectionUnchecked section = new SectionUnchecked(int.class);
		section.setCount(5);
		section.move(1, 2, 4);
		DirectionIndexSequence seq = new Forward(section);
		assertSequence("0, 3, 1, 2, 4", seq);
		section.hide(2, 2, true);
		assertSequence("0, 3, 1, 4", seq);
		section.hide(1, 1, true);
		assertSequence("0, 3, 4", seq);
		section.hide(4, 4, true);
		assertSequence("0, 3", seq);
		section.hide(0, 0, true);
		assertSequence("3", seq);
		section.hide(3, 3, true);
		assertSequence("", seq);
	}
	
	@Test
	public void sequenceHiddenMovedBackward() throws Exception {
		SectionUnchecked section = new SectionUnchecked(int.class);
		section.setCount(5);
		section.move(1, 2, 4);
		DirectionIndexSequence seq = new Backward(section);
		assertSequence("4, 2, 1, 3, 0", seq);
		section.hide(2, 2, true);
		assertSequence("4, 1, 3, 0", seq);
		section.hide(1, 1, true);
		assertSequence("4, 3, 0", seq);
		section.hide(4, 4, true);
		assertSequence("3, 0", seq);
		section.hide(0, 0, true);
		assertSequence("3", seq);
		section.hide(3, 3, true);
		assertSequence("", seq);
	}
	
	@Test
	public void sequenceNextCount() throws Exception {
		SectionUnchecked section = new SectionUnchecked(int.class);
		section.setCount(5);
		section.move(1, 2, 4);
		Forward seq = new Forward(section);
		assertSequence("0, 3, 1, 2, 4", seq);

		section.hide(2, 2, true);
		
		seq.init();
		assertTrue(seq.next(number(4)));
		assertEquals(4, seq.index().intValue());

		seq.init();
		seq.next(number(10));
		assertEquals(4, seq.index().intValue());
	}
	
	@Test
	public void sequenceNextCountBackward() throws Exception {
		SectionUnchecked section = new SectionUnchecked(int.class);
		section.setCount(5);
		section.move(1, 2, 4);
		DirectionIndexSequence seq = new Backward(section);
		assertSequence("4, 2, 1, 3, 0", seq);

		section.hide(2, 2, true);
		
		seq.init();
		assertTrue(seq.next(number(4)));
		assertEquals(0, seq.index().intValue());
		
		seq.init();
		seq.next(number(10));
		assertEquals(0, seq.index().intValue());
	}
	
	
	@Test
	public void getPosition() throws Exception {
		SectionUnchecked section = new SectionUnchecked(int.class);
		section.setCount(10);
		section.hide(2, 4, true);
		section.move(6, 9, 3);
		assertSequence("0, 1, 6, 7, 8, 9, 5", new Forward(section));
		
		assertEquals("0", section.indexOfNotHidden(0).toString());
		assertEquals("1", section.indexOfNotHidden(1).toString());
		assertEquals("2", section.indexOfNotHidden(6).toString());
		assertEquals("3", section.indexOfNotHidden(7).toString());
		assertEquals("4", section.indexOfNotHidden(8).toString());
		assertEquals("5", section.indexOfNotHidden(9).toString());
		assertEquals("6", section.indexOfNotHidden(5).toString());
		assertEquals(null, section.indexOfNotHidden(2));
		assertEquals(null, section.indexOfNotHidden(3));
		assertEquals(null, section.indexOfNotHidden(4));
		assertEquals(null, section.indexOfNotHidden(20));
		
		// Hide first
		section.hide(0, 0, true);
		assertEquals(null, section.indexOfNotHidden(0));
		assertEquals("0", section.indexOfNotHidden(1).toString());
		
		// Hide last
		section.hide(5, 5, true);
		assertEquals(null, section.indexOfNotHidden(5));
		assertEquals("4", section.indexOfNotHidden(9).toString());
		
	}
	
	@Test
	public void getByPosition() throws Exception {
		SectionUnchecked section = new SectionUnchecked(int.class);
		section.setCount(10);
		section.hide(2, 4, true);
		section.move(6, 9, 3);
		assertSequence("0, 1, 6, 7, 8, 9, 5", new Forward(section));
		
		assertEquals("0", section.get(0).toString());
		assertEquals("1", section.get(1).toString());
		assertEquals("6", section.get(2).toString());
		assertEquals("7", section.get(3).toString());
		assertEquals("8", section.get(4).toString());
		assertEquals("9", section.get(5).toString());
		assertEquals("5", section.get(6).toString());
		assertEquals(null, section.get(20));
		
		// Hide first
		section.hide(0, 0, true);
		assertEquals("1", section.get(0).toString());
		
		// Hide last
		section.hide(5, 5, true);
		assertEquals("9", section.get(4).toString());
		
	}
	
	@Test
	public void hide() throws Exception {
		SectionUnchecked section = new SectionUnchecked(int.class);
		section.hide(1, 2, true);
		
		assertFalse(section.isHidden(0));
		assertTrue(section.isHidden(1));
		assertTrue(section.isHidden(2));
		assertFalse(section.isHidden(3));
		
		section.hide(1, 1, false);
		assertFalse(section.isHidden(1));
		assertTrue(section.isHidden(2));
	}
	
	
	
	private static void assertSequence(String expected, DirectionIndexSequence seq) {
		StringBuilder sb = new StringBuilder();
		for (seq.init(); seq.next();) {
			if (sb.length() > 0) sb.append(", ");
			sb.append(seq.index());
		}
		assertEquals(expected, sb.toString());
	}
}
