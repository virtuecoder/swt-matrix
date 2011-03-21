package pl.netanel.swt.matrix;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SectionTest {
	
	@Test(expected = IllegalArgumentException.class)
	public void createIllegalNumber() throws Exception {
		new SectionUnchecked(float.class);
	}

	
	@Test
	public void itemCount() throws Exception {
		SectionUnchecked section = new SectionUnchecked(int.class);
		section.setCount(2);
		assertEquals(2, section.getCount().intValue());
		
		section.setCount(3);
		assertEquals(3, section.getCount().intValue());
		
	}
	
	
}
