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
		Section section = new Section(int.class);
		Forward seq = new Forward(section);
		assertSequence("", seq);
		
		section.setCount(1);
		assertSequence("0", seq);
		
		section.setCount(5);
		assertSequence("0, 1, 2, 3, 4", seq);
	}
	
	@Test
	public void sequenceHiddenMoved() throws Exception {
		Section section = new Section(int.class);
		section.setCount(5);
		section.move(1, 2, 4);
		DirectionIndexSequence seq = new Forward(section);
		assertSequence("0, 3, 1, 2, 4", seq);
		section.setHidden(2, true);
		assertSequence("0, 3, 1, 4", seq);
		section.setHidden(1, true);
		assertSequence("0, 3, 4", seq);
		section.setHidden(4, true);
		assertSequence("0, 3", seq);
		section.setHidden(0, true);
		assertSequence("3", seq);
		section.setHidden(3, true);
		assertSequence("", seq);
	}
	
	@Test
	public void sequenceHiddenMovedBackward() throws Exception {
		Section section = new Section(int.class);
		section.setCount(5);
		section.move(1, 2, 4);
		DirectionIndexSequence seq = new Backward(section);
		assertSequence("4, 2, 1, 3, 0", seq);
		section.setHidden(2, true);
		assertSequence("4, 1, 3, 0", seq);
		section.setHidden(1, true);
		assertSequence("4, 3, 0", seq);
		section.setHidden(4, true);
		assertSequence("3, 0", seq);
		section.setHidden(0, true);
		assertSequence("3", seq);
		section.setHidden(3, true);
		assertSequence("", seq);
	}
	
	@Test
	public void sequenceNextCount() throws Exception {
		Section section = new Section(int.class);
		section.setCount(5);
		section.move(1, 2, 4);
		Forward seq = new Forward(section);
		assertSequence("0, 3, 1, 2, 4", seq);

		section.setHidden(2, true);
		
		seq.init();
		assertTrue(seq.next(number(4)));
		assertEquals(4, seq.index().intValue());

		seq.init();
		seq.next(number(10));
		assertEquals(4, seq.index().intValue());
	}
	
	@Test
	public void sequenceNextCountBackward() throws Exception {
		Section section = new Section(int.class);
		section.setCount(5);
		section.move(1, 2, 4);
		DirectionIndexSequence seq = new Backward(section);
		assertSequence("4, 2, 1, 3, 0", seq);

		section.setHidden(2, true);
		
		seq.init();
		assertTrue(seq.next(number(4)));
		assertEquals(0, seq.index().intValue());
		
		seq.init();
		seq.next(number(10));
		assertEquals(0, seq.index().intValue());
	}
	
	
	@Test
	public void getPosition() throws Exception {
		Section section = new Section(int.class);
		section.setCount(10);
		section.setHidden(2, 4, true);
		section.move(6, 9, 3);
		assertSequence("0, 1, 6, 7, 8, 9, 5", new Forward(section));
		
		assertEquals("0", section.getPosition(0).toString());
		assertEquals("1", section.getPosition(1).toString());
		assertEquals("2", section.getPosition(6).toString());
		assertEquals("3", section.getPosition(7).toString());
		assertEquals("4", section.getPosition(8).toString());
		assertEquals("5", section.getPosition(9).toString());
		assertEquals("6", section.getPosition(5).toString());
		assertEquals(null, section.getPosition(2));
		assertEquals(null, section.getPosition(3));
		assertEquals(null, section.getPosition(4));
		assertEquals(null, section.getPosition(20));
		
		// Hide first
		section.setHidden(0, true);
		assertEquals(null, section.getPosition(0));
		assertEquals("0", section.getPosition(1).toString());
		
		// Hide last
		section.setHidden(5, true);
		assertEquals(null, section.getPosition(5));
		assertEquals("4", section.getPosition(9).toString());
		
	}
	
	@Test
	public void getByPosition() throws Exception {
		Section section = new Section(int.class);
		section.setCount(10);
		section.setHidden(2, 4, true);
		section.move(6, 9, 3);
		assertSequence("0, 1, 6, 7, 8, 9, 5", new Forward(section));
		
		assertEquals("0", section.getByPosition(0).toString());
		assertEquals("1", section.getByPosition(1).toString());
		assertEquals("6", section.getByPosition(2).toString());
		assertEquals("7", section.getByPosition(3).toString());
		assertEquals("8", section.getByPosition(4).toString());
		assertEquals("9", section.getByPosition(5).toString());
		assertEquals("5", section.getByPosition(6).toString());
		assertEquals(null, section.getByPosition(20));
		
		// Hide first
		section.setHidden(0, true);
		assertEquals("1", section.getByPosition(0).toString());
		
		// Hide last
		section.setHidden(5, true);
		assertEquals("9", section.getByPosition(4).toString());
		
	}
	
	@Test
	public void hide() throws Exception {
		Section section = new Section(int.class);
		section.setHidden(1, 2, true);
		
		assertFalse(section.isHidden(0));
		assertTrue(section.isHidden(1));
		assertTrue(section.isHidden(2));
		assertFalse(section.isHidden(3));
		
		section.setHidden(1, 1, false);
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
