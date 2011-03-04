package pl.netanel.swt.matrix;

import static org.junit.Assert.*;
import static pl.netanel.swt.matrix.TestUtil.*;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;

import pl.netanel.swt.matrix.MutableInt;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.Section.Forward;
import pl.netanel.swt.matrix.Section.SectionSequence;

public class SectionTest {
	
	@Test(expected = IllegalArgumentException.class)
	public void create() throws Exception {
		new Section(float.class);
	}
	
	@Test(expected = NullPointerException.class)
	public void createNull() throws Exception {
		new Section(null);
	}
	
	@Test
	public void itemCount() throws Exception {
		Section section = new Section(int.class);
		section.setCount(2);
		assertEquals(2, section.getCount().intValue());
		
		section.setCount(3);
		assertEquals(3, section.getCount().intValue());
		
		section.setCount(4L);
		assertEquals(4, section.getCount().intValue());
		
		section.setCount(5.2f);
		assertEquals(5, section.getCount().intValue());
		
		section.setCount(new BigInteger("6"));
		assertEquals(6, section.getCount().intValue());
		
		section.setCount(new BigDecimal("7.3"));
		assertEquals(7, section.getCount().intValue());
		
		section.setCount(new MutableInt(8));
		assertEquals(8, section.getCount().intValue());
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
	
	@Test
	public void sequence() throws Exception {
		Section section = new Section(int.class);
		Forward seq = section.new Forward();
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
		SectionSequence seq = section.new Forward();
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
		SectionSequence seq = section.new Backward();
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
		Forward seq = section.new Forward();
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
		SectionSequence seq = section.new Backward();
		assertSequence("4, 2, 1, 3, 0", seq);

		section.setHidden(2, true);
		
		seq.init();
		assertTrue(seq.next(number(4)));
		assertEquals(0, seq.index().intValue());
		
		seq.init();
		seq.next(number(10));
		assertEquals(0, seq.index().intValue());
	}
	
	
	
	
	private static void assertSequence(String expected, SectionSequence seq) {
		StringBuilder sb = new StringBuilder();
		for (seq.init(); seq.next();) {
			if (sb.length() > 0) sb.append(", ");
			sb.append(seq.index());
		}
		assertEquals(expected, sb.toString());
	}
}
