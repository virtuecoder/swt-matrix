package pl.netanel.swt.matrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import pl.netanel.swt.matrix.DirectionIndexSequence.Forward;

@SuppressWarnings({"rawtypes", "unchecked"}) @RunWith(JUnit4.class) public class  SectionTest {

	@Test(expected = IllegalArgumentException.class)
	public void createIllegalNumber() throws Exception {
		new SectionCore(float.class);
	}

	@Test
	public void setCount() throws Exception {
		SectionCore section = new SectionCore(int.class);
		section.setCount(2);
		assertEquals(2, section.getCount().intValue());

		section.setCount(3);
		assertEquals(3, section.getCount().intValue());
	}

	@Test(expected = NullPointerException.class)
	public void setCountNull() throws Exception {
	  SectionCore section = new SectionCore(int.class);
	  section.setCount(null);
	}

	@Test
	public void getIndexOutOfBounds() throws Exception {
	  SectionCore section = new SectionCore(int.class);
	  assertNull(section.getIndex(-1));
	}

	@Ignore
	@Test
	public void getSelectedExtentResizableSequence() throws Exception {
		SectionCore section = new SectionCore(int.class);
		section.setCount(3);
		section.setSelected(0, 2, true);
		section.setDefaultResizable(true);
		section.setResizable(1, 1, false);
		ExtentSequence seq = section.getSelectedExtentSequence();
		seq.init(); seq.next();
		assertEquals("0 0", "" + seq.start + " " + seq.end);
		seq.next();
		assertEquals("2 2", "" + seq.start + " " + seq.end);
	}

	@Test
	public void moveSelectedOne1() throws Exception {
		SectionCore section = new SectionCore(int.class);
		section.setCount(5);
		section.setSelected(1, 1, true);
		section.moveSelected(1, 1);
		assertEquals("0, 1, 2, 3, 4", toString(section));
	}

	@Test
	public void moveSelectedOne2() throws Exception {
		SectionCore section = new SectionCore(int.class);
		section.setCount(5);
		section.setSelected(1, 1, true);
		section.moveSelected(1, 0);
		assertEquals("1, 0, 2, 3, 4", toString(section));
	}

	@Test
	public void moveSelectedOne3() throws Exception {
		SectionCore section = new SectionCore(int.class);
		section.setCount(5);
		section.setSelected(1, 1, true);
		section.moveSelected(1, 2);
		assertEquals("0, 2, 1, 3, 4", toString(section));
	}

	@Test
	public void moveSelectedOne4() throws Exception {
		SectionCore section = new SectionCore(int.class);
		section.setCount(5);
		section.setSelected(1, 1, true);
		section.moveSelected(1, 4);
		assertEquals("0, 2, 3, 4, 1", toString(section));
	}

	@Test
	public void moveSelectedBlockInPlace1() throws Exception {
		SectionCore section = new SectionCore(int.class);
		section.setCount(5);
		section.setSelected(1, 3, true);
		section.moveSelected(1, 1);
		assertEquals("0, 1, 2, 3, 4", toString(section));
	}

	@Test
	public void moveSelectedBlockInPlace2() throws Exception {
		SectionCore section = new SectionCore(int.class);
		section.setCount(5);
		section.setSelected(1, 3, true);
		section.moveSelected(1, 2);
		assertEquals("0, 1, 2, 3, 4", toString(section));
	}

	@Test
	public void moveSelectedBlockInPlace3() throws Exception {
		SectionCore section = new SectionCore(int.class);
		section.setCount(5);
		section.setSelected(1, 3, true);
		section.moveSelected(1, 3);
		assertEquals("0, 1, 2, 3, 4", toString(section));
	}

	@Test
	public void moveSelectedBlock1() throws Exception {
		SectionCore section = new SectionCore(int.class);
		section.setCount(5);
		section.setSelected(1, 3, true);
		section.moveSelected(1, 0);
		assertEquals("1, 2, 3, 0, 4", toString(section));
	}

	@Test
	public void moveSelectedBlock2() throws Exception {
		SectionCore section = new SectionCore(int.class);
		section.setCount(5);
		section.setSelected(1, 3, true);
		section.moveSelected(3, 4);
		assertEquals("0, 4, 1, 2, 3", toString(section));
	}

	@Test
	public void moveSelectedBlock3() throws Exception {
		SectionCore section = new SectionCore(int.class);
		section.setCount(5);
		section.setSelected(1, 3, true);
		section.moveSelected(3, 0);
		assertEquals("1, 2, 3, 0, 4", toString(section));
	}

	@Test
	public void moveSelectedScattered1() throws Exception {
		SectionCore section = new SectionCore(int.class);
		section.setCount(5);
		section.setSelected(1, 1, true);
		section.setSelected(3, 3, true);
		section.moveSelected(1, 0);
		assertEquals("1, 3, 0, 2, 4", toString(section));
	}

	@Test
	public void moveSelectedScattered2() throws Exception {
		SectionCore section = new SectionCore(int.class);
		section.setCount(5);
		section.setSelected(1, 1, true);
		section.setSelected(3, 3, true);
		section.moveSelected(3, 0);
		assertEquals("1, 3, 0, 2, 4", toString(section));
	}

	@Test
	public void moveSelectedScattered3() throws Exception {
		SectionCore section = new SectionCore(int.class);
		section.setCount(5);
		section.setSelected(1, 1, true);
		section.setSelected(3, 3, true);
		section.moveSelected(1, 2);
		assertEquals("0, 2, 1, 3, 4", toString(section));
	}

	@Test
	public void moveSelectedScattered4() throws Exception {
		SectionCore section = new SectionCore(int.class);
		section.setCount(5);
		section.setSelected(1, 1, true);
		section.setSelected(3, 3, true);
		section.moveSelected(3, 2);
		assertEquals("0, 1, 3, 2, 4", toString(section));
	}

	@Test
	public void moveSelectedScattered5() throws Exception {
		SectionCore section = new SectionCore(int.class);
		section.setCount(5);
		section.setSelected(1, 1, true);
		section.setSelected(3, 3, true);
		section.moveSelected(3, 1);
		assertEquals("0, 1, 2, 3, 4", toString(section));
	}

	public void delete() throws Exception {
		SectionCore section = new SectionCore(int.class);
		section.setCount(5);
		section.setCellWidth(4, 4, 33);
		assertEquals(33, section.getCellWidth(4));
		section.delete(2, 2);
		assertEquals(33, section.getCellWidth(3));
		assertEquals(4, section.getCount());
	}

	@Test
	public void insertEmpty() throws Exception {
		SectionCore section = new SectionCore(int.class);
		section.insert(0, 1);
		assertEquals(1, section.getCount());
//		section.insert(0, 1);
//		assertEquals(2, section.getCount());
	}

//	private static String moveSelected(SectionUnchecked section, int source, int target) {
//		section.moveSelected(source, target);
//		return toString(section);
//	}
//
//	private static SectionUnchecked section(int count) {
//		SectionUnchecked section = new SectionUnchecked();
//		section.setCount(count);
//		return section;
//	}

	static String toString(SectionCore section) {
		StringBuilder sb = new StringBuilder();
		Forward seq = new DirectionIndexSequence.Forward(section);
		for (seq.init(); seq.next();) {
			if (sb.length() > 0) sb.append(", ");
			sb.append(seq.index());
		}
		return sb.toString();
	}

}
