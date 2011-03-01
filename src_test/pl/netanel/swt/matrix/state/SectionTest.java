package pl.netanel.swt.matrix.state;

import static org.junit.Assert.assertEquals;

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
		section.setItemCount(2);
		assertEquals(2, section.getItemCount().intValue());
		
		section.setItemCount(3);
		assertEquals(3, section.getItemCount().intValue());
		
		section.setItemCount(4L);
		assertEquals(4, section.getItemCount().intValue());
		
		section.setItemCount(new BigInteger("5"));
		assertEquals(5, section.getItemCount().intValue());
		
		section.setItemCount(6.2f);
		assertEquals(6, section.getItemCount().intValue());
	}
	
}
