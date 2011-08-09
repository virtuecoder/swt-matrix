package pl.netanel.swt.matrix;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@SuppressWarnings({"rawtypes", "unchecked"}) @RunWith(JUnit4.class) public class  ExtentTest {
	@Test
	public void delete() throws Exception {
		// arguments: 0 2
		// extent:        4 6   
		assertEquals("1-3", delete(4, 6, 0, 2));
		
		// extent:      2 4      
		// arguments: 0 2
		assertEquals("0-1", delete(2, 4, 0, 2));
		
		// extent:    0  3      
		// arguments:  12
		assertEquals("0-1", delete(0, 3, 1, 2));
		
		// extent:    0 2   
		// arguments:  1 3
		assertEquals("0-0", delete(0, 2, 1, 3));
		
		// extent:    0 2    
		// arguments: 0 2
		assertEquals("", delete(0, 2, 0, 2));
		
		// extent:    0 2     
		// arguments:  1
		assertEquals("0-1", delete(0, 2, 1, 1));
	}
	
	private static String delete(int estart, int eend, int astart, int aend) {
		ArrayList<MutableExtent<Integer>> list = new ArrayList<MutableExtent<Integer>>();
		Math<Integer> math = Math.getInstance(int.class);
		list.add(new MutableExtent(math.create(estart), math.create(eend)));
		MutableExtent.delete(math, list, astart, aend);
		
		return toString(list);
	}

	private static String toString(ArrayList<MutableExtent<Integer>> list) {
		StringBuilder sb = new StringBuilder();
		for (MutableExtent e: list) {
			if (sb.length() > 0) sb.append(", ");
			sb.append(e);
		}
		return sb.toString();
	}
}
