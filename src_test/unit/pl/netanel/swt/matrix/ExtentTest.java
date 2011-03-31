package pl.netanel.swt.matrix;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class ExtentTest {
	@Test
	public void subtract() throws Exception {
		// arguments: 0 2
		// extent:        4 6   
		assertEquals("1-3", subtract(4, 6, 0, 2));
		
		// extent:      2 4      
		// arguments: 0 2
		assertEquals("0-1", subtract(2, 4, 0, 2));
		
		// extent:    0  3      
		// arguments:  12
		assertEquals("0-1", subtract(0, 3, 1, 2));
		
		// extent:    0 2   
		// arguments:  1 3
		assertEquals("0-0", subtract(0, 2, 1, 3));
		
		// extent:    0 2    
		// arguments: 0 2
		assertEquals("", subtract(0, 2, 0, 2));
		
		// extent:    0 2     
		// arguments:  1
		assertEquals("0-1", subtract(0, 2, 1, 1));
	}
	
	private static String subtract(int estart, int eend, int astart, int aend) {
		ArrayList<Extent<Integer>> list = new ArrayList<Extent<Integer>>();
		Math<Integer> math = Math.getInstance(int.class);
		list.add(new Extent(math.create(estart), math.create(eend)));
		Extent.delete(math, list, astart, aend);
		
		StringBuilder sb = new StringBuilder();
		for (Extent e: list) {
			if (sb.length() > 0) sb.append(", ");
			sb.append(e);
		}
		return sb.toString();
	}
}
