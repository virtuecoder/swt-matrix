package pl.netanel.swt.matrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import pl.netanel.util.Arrays;


/**
 * ANSII graphics the best viewed with the Consolas font.
 * 
 * @author Jacek created 15-02-2011
 */
@RunWith(JUnit4.class) public class  CellSetTest {
	private CellSet set;

	@Before
	public void _beforeEach() {
		IntMath math = IntMath.getInstance();
		set = new CellSet(math, math);
	}
	@After
	public void _afterEach() {
		set = null;
	}
	
	@Test
	public void testOfTest1() throws Exception {
		insert(	"■■" 	,
				"■■" 	);
		assertEquals("[0-1, 0-1]", set.toString());
	}
	
	@Test
	public void testOfTest2() throws Exception {
		insert(	"∟∟" 	,
				"∟■" 	);
		assertEquals("[1-1, 1-1]", set.toString());
	}
	
	@Test
	public void testOfTest3() throws Exception {
		insert(	"■∟" 	,
				"∟∟" 	);
		assertEquals("[0-0, 0-0]", set.toString());
	}
	
	@Test
	public void testOfTest4() throws Exception {
		insert(	"■∟■" );
		assertEquals("[0-0, 0-0], [0-0, 2-2]", set.toString());
	}
	
	@Test
	public void testOfTest5() throws Exception {
		set.add(1, 1, 1, 1);
		result(	"∟∟"	, 
				"∟■"	);
	}
	
	@Test
	public void addTheSame() throws Exception {
		insert(	"■"		);
		insert(	"■"		);
		result(	"■"		);
		count(1);
	}
	
	@Test
	public void addApart() throws Exception {
		insert(	"■∟"	);
		insert(	"∟∟"	, 
				"∟■"	);
		result(	"■∟" 	,
				"∟■" 	);
		count(2);
	}
	
	@Test
	public void addApart2() throws Exception {
		insert(	"■∟∟"		);
		insert(	"∟∟■" );
		result(	"■∟■" );
		count(2);
	}
	
	@Test
	public void addExtendAfterSimetric() throws Exception {
		insert(	"■∟"   );
		insert(	"■■" 	,
				"■■" 	);
		result(	"■■" 	,
				"■■" 	);
		count(4);
	}
	
	@Test
	public void addExtendBeforeSimetric() throws Exception {
		insert(	"∟∟" 	,
				"∟■" 	);
		insert(	"■■" 	,
				"■■" 	);
		result(	"■■" 	, 
				"■■" 	);
		count(4);
	}
	
	@Test
	public void addInside() throws Exception {
		insert(	"■■" 	,
				"■■" 	);
		insert(	"■∟" 	,
				"∟∟" 	);
		result(	"■■" 	, 
				"■■" 	);
		count(4);
	}
	
	@Test
	public void addInside2() throws Exception {
		insert( "■■" 	,
		    	"■■" 	);
		insert(	"∟∟" 	,
		    	"∟■" 	);
		result( "■■" 	, 
		        "■■" 	);
		count(4);
	}
	
	@Test
	public void addInside3() throws Exception {
		insert( "■■■" 	,
				"■■■" 	,
				"■■■" 	);
		insert(	"∟∟∟" 	,
				"∟■∟" 	);
		result( "■■■" 	,
				"■■■" 	,
				"■■■" 	);
		count(9);
	}
	
	@Test
	public void addOverlap() throws Exception {
		insert(	"∟∟∟" 	,
				"∟■∟" 	);
		insert( "■■■" 	,
				"■■■" 	,
				"■■■" 	);
		result( "■■■" 	,
				"■■■" 	,
				"■■■" 	);
		count(9);
	}
	
	@Test
	public void addCrossAfterSimetric() throws Exception {
		insert(	"■■∟" 	,
				"■■∟" 	);
		insert( "∟∟∟" 	,
				"∟■■" 	,
				"∟■■" 	);
		result( "■■∟" 	,
				"■■■" 	,
				"∟■■" 	);
		count(7);
	}
	
	@Test
	public void addCrossBeforeSimetric() throws Exception {
		insert( "∟∟∟" 	,
				"∟■■" 	,
				"∟■■" 	);
		insert(	"■■∟" 	,
				"■■∟" 	,
				"∟∟∟" 	);
		result( "■■∟" 	,
				"■■■" 	,
				"∟■■" 	);
		count(7);
	}
	
	@Test
	public void addCrossBeforeBig() throws Exception {
		insert( "∟∟∟∟" 	,
				"∟∟∟∟" 	,
				"∟∟■■" 	,
				"∟∟■■" 	);
		insert(	"■■■∟" 	,
				"■■■∟" 	,
				"■■■∟" 	,
				"∟∟∟∟" 	);
		result( "■■■∟" 	,
				"■■■∟" 	,
				"■■■■" 	,
				"∟∟■■" 	);
		count(12);
	}
	
	@Test
	public void addCrossLeft() throws Exception {
		insert( "∟∟∟" 	,
				"■■■" 	,
				"∟∟∟" 	);
		insert(	"■∟∟" 	,
				"■∟∟" 	,
				"■∟∟" 	);
		result( "■∟∟" 	,
				"■■■" 	,
				"■∟∟" 	);
		count(5);
	}
	
	@Test
	public void addCrossMiddle() throws Exception {
		insert( "∟■∟" 	,
				"∟■∟" 	,
				"∟■∟" 	);
		insert(	"∟∟∟" 	,
				"■■■" 	,
				"∟∟∟" 	);
		result( "∟■∟" 	,
				"■■■" 	,
				"∟■∟" 	);
		count(5);
	}
	
	@Test
	public void addColumnAfter() throws Exception {
		insert( "■" 	,
				"■" 	);
		insert(	"∟■" 	,
				"∟■"	);
		result( "■■" 	,
				"■■" 	);
		count(4);
	}
	
	@Test
	public void addCoumnBefore() throws Exception {
		insert( "∟■" 	,
				"∟■" 	);
		insert(	"■" 	,
				"■"		);
		result( "■■" 	,
				"■■" 	);
		count(4);
	}
	
	@Test
	public void addRowAfter() throws Exception {
		insert( "■■" 	,
				"∟∟" 	);
		insert(	"∟∟" 	,
				"■■"		);
		result( "■■" 	,
				"■■" 	);
		count(4);
	}
	
	@Test
	public void addRowBefore() throws Exception {
		insert( "∟∟" 	,
				"■■" 	);
		insert(	"■■" 	,
				"∟∟"	);
		result( "■■" 	,
				"■■" 	);
		count(4);
	}
	
	@Test
	public void addAdjacentRight() throws Exception {
		insert( "■■" 	,
				"■■" 	);
		insert(	"∟∟■"	);
		result( "■■■"	,
				"■■∟"	);
		count(5);
	}
	
	
	
	/*------------------------------------------------------------------------
	 * Remove 
	 */
	
	@Test
	public void removeFromEmpty() throws Exception {
		remove( "■" 	);
		result( ""		);
		count(0);
	}

	@Test
	public void removeTheSame1() throws Exception {
		insert( "■" 	);
		remove( "■" 	);
		result( ""		);
		count(0);
	}
	
	@Test
	public void removeNothing1() throws Exception {
		insert( "■" 	);
		remove( "∟" 	);
		result( "■"		);
		count(1);
	}
	
	@Test
	public void removeNothing2() throws Exception {
		insert( "∟■∟" 	);
		remove( "∟∟∟" 	);
		result( "∟■∟"		);
		count(1);
	}
	
	@Test
	public void removeTheSame2() throws Exception {
		insert( "■■" 	,
				"■■"	);
		remove( "■■" 	,
				"■■"	);
		result( ""		);
		count(0);
	}
	
	@Test
	public void removeOverlap() throws Exception {
		insert( "■"		);
		remove( "■■"	);
		result( ""		);
		count(0);
	}
	
	@Test
	public void removePart1() throws Exception {
		insert( "■■" 	,
				"■■"	);
		remove( "■∟"	,
				"∟∟"	);
		result( "∟■"	,
				"■■"	);
		count(3);
	}
	
	@Test
	public void removePart2() throws Exception {
		insert( "■■" 	,
				"■■"	);
		remove( "∟■"	,
				"∟∟"	);
		result( "■∟"	,
				"■■"	);
		count(3);
	}
	
	@Test
	public void removePart3() throws Exception {
		insert( "■■" 	,
				"■■"	);
		remove( "∟∟"	,
				"■∟"	);
		result( "■■"	,
				"∟■"	);
		count(3);
	}
	
	@Test
	public void removePart4() throws Exception {
		insert( "■■" 	,
				"■■"	);
		remove( "∟∟"	,
				"∟■"	);
		result( "■■"	,
				"■∟"	);
		count(3);
	}
	
	@Test
	public void removeCrossing() throws Exception {
		insert( "■■" 	,
				"■■"	);
		remove( "∟∟∟"	,
				"∟■■"	,
				"∟■■" );
		result( "■■"	,
				"■∟"	);
		count(3);
	}
	
	@Test
	public void removeInside1() throws Exception {
		insert( "■■■" );
		remove( "∟■∟"	);
		result( "■∟■"	);
		count(2);
	}
	
	@Test
	public void removeInside2() throws Exception {
		insert( "■■■" ,
				"■■■"	,
				"■■■"	);
		remove( "∟∟∟"	,
				"∟■∟"	,
				"∟∟∟" );
		result( "■■■"	,
				"■∟■"	,
				"■■■"	);
		count(8);
	}
	
	@Test
	public void removeCrossMiddle() throws Exception {
		insert( "∟■∟" ,
				"■■■"	,
				"∟■∟"	);
		remove( "∟∟∟"	,
				"∟■∟"	,
				"∟∟∟" );
		result( "∟■∟"	,
				"■∟■"	,
				"∟■∟"	);
		count(4);
	}
	
	@Test
	public void removeCorner() throws Exception {
		insert( "∟■■" ,
				"∟■■"	);
		remove( "∟∟"	,
				"■■"	,
				"■■" 	);
		result( "∟■■"	,
				"∟∟■"	);
		count(3);
	}
	
	@Test
	public void removeCorner1() throws Exception {
		insert( "■■" 	,
				"∟■"	);
		remove( "∟■"	,
				"∟■"	);
		result( "■∟"	,
				"∟∟"	);
	}
	
	@Test
	public void removeCorner2() throws Exception {
		set.add(0, 2, 2, 3);
		set.remove(2, 4, 0, 2);
		result( "∟∟■■"	,
				"∟∟■■"	,
				"∟∟∟■" 	);
		count(5);
	}
	
//	@Test
//	public void subsetEmpty() throws Exception {
//		insert( "∟■" ,
//				"■■"	);
//		subset(0, 0, 0, 0, "");
//	}
//	
//	@Test
//	public void subsetTheSame() throws Exception {
//		insert( "∟■" ,
//				"■■"	);
//		subset(0, 1, 0, 1, 
//				"∟■" ,
//				"■■"	);
//	}
//	
//	@Test
//	public void subsetMore() throws Exception {
//		insert( "∟■" ,
//				"■■"	);
//		subset(0, 10, 0, 10, 
//				"∟■" ,
//				"■■"	);
//	}
//	
//	@Test
//	public void subset() throws Exception {
//		insert( "∟■∟" ,
//				"■■■"	,
//				"∟■∟"	);
//		subset(0, 1, 0, 1,
//				"∟■" ,
//				"■■");
//	}
	
	@Test
	public void cellSequenceEmpty() throws Exception {
		assertInSequence("");
	}
	
	@Test
	public void cellSequenceSingle() throws Exception {
		insert( "■" );
		assertInSequence("0:0");
	}
	
	@Test
	public void cellSequence() throws Exception {
		insert( "∟■"   ,
				"■■"	);
		assertInSequence("0:1, 1:0, 1:1");
	}
	
	@Test
	public void cellSequence2() throws Exception {
		insert( "■■"   ,
				"■■"	);
		assertInSequence("0:0, 0:1, 1:0, 1:1");
	}

	@Test
	public void getExtentEmpty() throws Exception {
		assertExtent(0, 0, 0, 0);
	}
	
	@Test
	public void getExtentOne() throws Exception {
		insert( "∟■" );
		assertExtent(0, 0, 1, 1);
	}
	
	@Test
	public void getExtent() throws Exception {
		insert( "∟■∟"	,
				"■∟■"	,
				"∟■∟"	);
		assertExtent(0, 2, 0, 2);
	}
	
	private void assertExtent(int startY, int endY, int startX, int endX) {
		Number[] e = set.getExtent();
		assertEquals(startY + " " + endY + " " + startX + " " + endX, 
				e[0] + " " + e[1] + " " + e[2] + " " + e[3]);
	}
	
	private void assertInSequence(String expected) {
		String[] cells = expected.split(", "); if (cells[0].equals("")) cells = new String[0];
		
		NumberPairSequence seq = new NumberPairSequence(set);
		int count = 0;
		for (seq.init(); seq.next(); count++) {
			String cell = seq.indexY.toString() + ":" + seq.indexX.toString();
			assertTrue("Unexpected " + cell, Arrays.contains(cells, cell));
		}
		assertEquals("Wrong count, ", cells.length, count);
	}
	
	
//	@Ignore
//	@Test
//	public void apply() throws Exception {
////		Random random = new Random();
//		int[][] data = new int[] [] {
//				new int[] {0, 2, 2, 3},
//				new int[] {4, 2, 2, 0}};
//		
//		int len = data.length;
//		for (int i = 0; i < len; i++) {
////			Index startY = number(random.nextInt(5));
////			Index endY = number(random.nextInt(5));
////			Index startX = number(random.nextInt(5));
////			Index endX = number(random.nextInt(5));
////			Strings.println(startY, endY, startX, endX);
//			Index startY = number(data[i][0]);
//			Index endY = number(data[i][1]);
//			Index startX = number(data[i][2]);
//			Index endX = number(data[i][3]);
//			CellSet subset = new CellSet(IntIndexFactory.instance, IntIndexFactory.instance);
//			subset.add(startY, endY, startX, endX);
//			set.remove(startY, endY, startX, endX);
//			set.apply(subset);
//			assertTrue("Number of items should not be more then 25", set.size() <= 25);
//		}
//	}
	
//	private void subset(int startY, int endY, int startX, int endX, String ...expected) {
//		Math f = IntMath.getInstance();
//		CellSet subset = new CellSet(f, f);
//		set.subset(startY, endY, startX, endX, subset);
//		result(subset, expected);
//	}
	
	private void count(int i) {
		assertEquals("Wrong count for: "+set, i, set.getCount().intValue());
	}

	void add(int startY, int endY, int startX, int endX) {
		set.add(startY, endY, startX, endX);
	}
	
	private void insert(String ...lines) {
		operation(true, lines);
	}
	
	private void remove(String ...lines) {
		operation(false, lines);
	}
	
	private void operation(boolean insert, String ...lines) {
		for (int i = 0; i < lines.length; i++) {
			String s = lines[i];
			int startX = -1, endX = -1, x1 = -1, x2 = -1;
			
			// Find the block on x axis
			while (endX < s.length() - 1) {
				startX = s.indexOf('■', endX + 1);
				if (startX == -1) break;
				endX = s.indexOf('∟', startX) - 1; 
				if (endX < 0) endX = s.length() - 1;

				int j = i+1;
				for (; j < lines.length; j++) {
					String s2 = lines[j];
					x1 = s2.indexOf('■', x1);
					if (x1 != startX) break;
					x2 = s2.indexOf('∟', x1) - 1; 
					if (x2 < 0) x2 = s2.length() - 1;
					if (x2 != endX) break;
				}
				doOperation(insert, i, j-1, startX, endX);
				i = j - 1;
			}
		}
	}

	private void doOperation(boolean insert, int startY, int endY, int startX,
			int endX) {
		if (insert)
			set.add(startY, endY, startX, endX);
		else 
			set.remove(startY, endY, startX, endX);
	}
	
	private void result(String ...expected) {
		result(set, expected);
	}
	private void result(CellSet set, String ...expected) {
		ArrayList<ArrayList<Boolean>> data = new ArrayList<ArrayList<Boolean>>();
		int size = set.items0.size();
		int max = 0;
		
		for (int i = 0; i < size; i++) {
			Extent item0 = (Extent) set.items0.get(i);
			Extent item1 = (Extent) set.items1.get(i);
			for (int j = 0; j <= item0.end.intValue(); j++) {
				ArrayList<Boolean> line;
				if (j >= data.size()) {
					data.add(line = new ArrayList<Boolean>());
				}	
				if (j < item0.start.intValue()) {
					continue;
				} else {
					line = data.get(j);
				}
				if (max < item1.end.intValue() + 1) {
					max = item1.end.intValue() + 1;
				}
				for (int k = 0; k <= item1.end.intValue(); k++) {
					if (k < item1.start.intValue()) {
						if (k >= line.size()) {
							line.add(false);
						}
					} 
					else {
						if (k >= line.size()) {
							line.add(true);
						} else {
							line.set(k, true);
						}
					}
				}
			}
		}
		String message = "";
		boolean quit = false;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < data.size(); i++) {
			ArrayList<Boolean> line = data.get(i);
			for (int j = 0; j < max; j++) {
				String s = j < line.size() ? line.get(j) ? "■" : "∟" : "∟";
				sb.append(s);
				if (expected[i].charAt(j) != s.charAt(0)) {
					message += "\nAt "+i+":"+j+" expected "+String.valueOf(expected[i].charAt(j))+
						" but was "+s;
				}
			}
			sb.append("\n");
			if (quit) break;
		}
		if (message.length() > 0) assertFalse(message + "\n" + sb, true);
	}
}
