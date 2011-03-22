package pl.netanel.swt.matrix;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
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
	
	@Ignore
	@Test
	public void getSelectedExtentResizableSequence() throws Exception {
		SectionUnchecked section = new SectionUnchecked(int.class);
		section.setCount(3);
		section.setSelected(0, 2, true);
		section.setDefaultResizable(true);
		section.setResizable(1, 1, false);
		ExtentSequence seq = section.getSelectedExtentResizableSequence();
		seq.init(); seq.next();
		assertEquals("0 0", "" + seq.start + " " + seq.end);
		seq.next();
		assertEquals("2 2", "" + seq.start + " " + seq.end);
	}
}
