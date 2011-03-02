package pl.netanel.swt.matrix.state;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;

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
	
//	@Test
//	public void move() throws Exception {
//		Section section = new Section(int.class);
//		section.move(1, 2, 3);
//		assertArrayEquals("", section.);
//	}
}
