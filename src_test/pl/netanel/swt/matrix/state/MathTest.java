package pl.netanel.swt.matrix.state;

import static org.junit.Assert.assertEquals;
import static pl.netanel.swt.matrix.state.Math.*;

import org.junit.Test;

public class MathTest {
	@Test
	public void compare() throws Exception {
		Math math = new IntMath();
		assertEquals(BEFORE, compare(math, 1, 1, 3, 7));
		assertEquals(ADJACENT_BEFORE, compare(math, 1, 2, 3, 7));
		assertEquals(ADJACENT_BEFORE, compare(math, 2, 2, 3, 7));
		assertEquals(CROSS_BEFORE, compare(math, 3, 3, 3, 7));
		assertEquals(CROSS_BEFORE, compare(math, 1, 4, 3, 7));
		assertEquals(INSIDE, compare(math, 4, 4, 3, 7));
		assertEquals(INSIDE, compare(math, 5, 5, 3, 7));
		assertEquals(INSIDE, compare(math, 6, 6, 3, 7));
		assertEquals(INSIDE, compare(math, 4, 6, 3, 7));
		assertEquals(CROSS_AFTER, compare(math, 7, 7, 3, 7));
		assertEquals(CROSS_AFTER, compare(math, 6, 8, 3, 7));
		assertEquals(ADJACENT_AFTER, compare(math, 8, 8, 3, 7));
		assertEquals(ADJACENT_AFTER, compare(math, 8, 9, 3, 7));
		assertEquals(AFTER, compare(math, 9, 9, 3, 7));
		assertEquals(OVERLAP, compare(math, 1, 9, 3, 7));
		assertEquals(EQUAL, compare(math, 3, 7, 3, 7));
	}
	
	
	public int compare(Math math, Number start1, Number end1, Number start2, Number end2) {
		return math.compare(math.getMutable(start1), math.getMutable(end1), 
				math.getMutable(start2), math.getMutable(end2));
	}

}
