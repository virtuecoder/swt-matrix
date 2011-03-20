package pl.netanel.swt.matrix;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SectionTest {
	
	@Test(expected = IllegalArgumentException.class)
	public void createIllegalNumber() throws Exception {
		new Section(float.class);
	}

	
	@Test
	public void itemCount() throws Exception {
		Section section = new Section(int.class);
		section.setCount(2);
		assertEquals(2, section.getCount().intValue());
		
		section.setCount(3);
		assertEquals(3, section.getCount().intValue());
		
//		section.setCount(4L);
//		assertEquals(4, section.getCount().intValue());
//		
//		section.setCount(5.2f);
//		assertEquals(5, section.getCount().intValue());
//		
//		section.setCount(new BigInteger("6"));
//		assertEquals(6, section.getCount().intValue());
//		
//		section.setCount(new BigDecimal("7.3"));
//		assertEquals(7, section.getCount().intValue());
//		
//		section.setCount(new MutableInt(8));
//		assertEquals(8, section.getCount().intValue());
	}
	
	
}
