package pl.netanel.swt.matrix.state;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SectionTest {
	@Test
	public void itemCount() throws Exception {
		Section section = new Section(int.class);
		section.setItemCount(2);
		assertEquals(2, section.getItemCount());
	}
}
