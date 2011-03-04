package pl.netanel.swt.matrix;

import static org.junit.Assert.*;
import static pl.netanel.swt.matrix.TestUtil.*;

import org.junit.Test;

import pl.netanel.swt.matrix.IntMath;
import pl.netanel.swt.matrix.NumberOrder;


public class NumberOrderTest  {

//	@Test
//	public void getInnerIndexes() throws Exception {
//		NumberOrder order = new NumberOrder(extent(2, 3), extent(1, 1), extent(4, 9), extent(0, 0));
//		
//		assertEquals("3, 1, 4, 6, 7", order.getModelIndexes(indexes(1,2,3,5,6)).toString());
//	}

	@Test
	public void moveEmpty() throws Exception {
		NumberOrder model = numberOrder(0);
		move(model, 1, 2, 3);
		assertEquals("1-2", model.toString());
	}
	
	@Test
	public void moveEdgeCases() throws Exception {
		assertEquals("0-9", move(10, 0, 0, 0));
		assertEquals("0-9", move(10, 0, 0, 1));
		
		assertEquals("0-9", move(10, 9, 9, 9));
		assertEquals("0-9", move(10, 9, 9, 10));
		
		assertEquals("0-9", move(10, 0, 9, 0));
		assertEquals("0-9", move(10, 0, 9, 9));
		assertEquals("0-9", move(10, 0, 9, 10));
		assertEquals("0-9", move(10, 0, 9, 5));
		
		assertEquals("0-9", move(10, 0, 5, 2));
		assertEquals("0-9", move(10, 0, 5, 5));
		assertEquals("0-9", move(10, 0, 5, 6));
		
		assertEquals("0-9", move(10, 5, 9, 5));
		assertEquals("0-9", move(10, 5, 9, 9));
		assertEquals("0-9", move(10, 5, 9, 10));
		
		assertEquals("0-9", move(10, 3, 3, 3));
	}
	
	@Test
	public void moveTheSame() throws Exception {
		NumberOrder model = numberOrder(10);
		move(model, 2, 2, 2);
		assertEquals("0-9", model.toString());
	}
	
//	@Test
//	public void moveFragmented1() throws Exception {
//		NumberOrder model = numberOrder(10);
//		move(model, 2, 2, 2);
//		move(model, 6, 6, 2);
//		assertEquals("0-2, 6, 3-5, 7-9", model.toString());
//	}
	
	@Test
	public void moveFragmented2() throws Exception {
		NumberOrder model = numberOrder(10);
		move(model, 2, 2, 3);
		move(model, 6, 6, 3);
		assertEquals("0-2, 6, 3-5, 7-9", model.toString());
	}
	
	@Test
	public void moveFragmented3() throws Exception {
		NumberOrder model = numberOrder(10);
		move(model, 2, 2, 4);
		move(model, 6, 6, 4);
		assertEquals("0-1, 3, 2, 6, 4-5, 7-9", model.toString());
	}
	
	@Test
	public void moveFragmented4() throws Exception {
		NumberOrder model = numberOrder(10);
		move(model, 2, 2, 6);
		move(model, 6, 6, 6);
		assertEquals("0-1, 3-5, 2, 6-9", model.toString());
	}

	@Test
	public void moveFragmented5() throws Exception {
		NumberOrder model = numberOrder(10);
		move(model, 2, 2, 7);
		move(model, 6, 6, 7);
		assertEquals("0-1, 3-5, 2, 6-9", model.toString());
	}
	
	@Test
	public void moveFragmentedEnd() throws Exception {
		NumberOrder model = numberOrder(10);
		move(model, 2, 2, 9);
		move(model, 9, 9, 9);
		assertEquals("0-1, 3-8, 2, 9", model.toString());
	}
	
	@Test
	public void moveFragmentedEnd2() throws Exception {
		NumberOrder model = numberOrder(10);
		move(model, 2, 2, 10);
		move(model, 9, 9, 10);
		assertEquals("0-1, 3-8, 2, 9", model.toString());
	}
	
	@Test
	public void moveFragmentedDescending() throws Exception {
		NumberOrder model = numberOrder(10);
		move(model, 9, 9, 3);
		move(model, 2, 2, 3);
		assertEquals("0-1, 9, 2-8", model.toString());
	}
	
//	@Test
//	public void move() throws Exception {
//		NumberOrder order;
//		assertEquals("1-9, 0", move(10, 0, 0, 10));
//		assertEquals("1-8, 0, 9", move(10, 0, 0, 9));
//		assertEquals("9, 0-8", move(10, 9, 9, 0));
//		assertEquals("0, 9, 1-8", move(10, 9, 9, 1));
//		
//		order = numberOrder(10);
//		assertEquals("0-2, 6-9, 3-5", move(order, 3, 5, 10));
//		order.move(
//				order.getModelIndexes(indexList(extent(2, 4))), 
//				order.getModelIndex(index(8)));
//		assertEquals("0-1, 8-9, 3, 2, 6-7, 4-5", order.toString());
//		
//		order = numberOrder(10);
//		assertEquals("0-1, 3, 2, 4-9", move(order, 2, 2, 4));
//		assertEquals("0-1, 3-4, 2, 5-9", move(order, 2, 2, 5));
//		
//		order = numberOrder(10);
//		assertEquals("0-3, 5-6, 4, 7-9", move(order, 4, 4, 7));
//		assertEquals("0-3, 5, 4, 6-9", move(order, 4, 4, 6));
//	}
	
	@Test
	public void recorderManyAtOnce() throws Exception {
		NumberOrder order = numberOrder(10);
		assertEquals("0-2, 5, 3-4, 6-9", move(order, 5, 5, 3));
		move(order, 5, 5, 7);
		move(order, 3, 3, 7);
		assertEquals("0-2, 4, 6, 5, 3, 7-9", order.toString());
	}
	
	@Test
	public void backAndForth() throws Exception {
		NumberOrder model = numberOrder(2);
		assertEquals("1, 0", move(model, 1, 1, 0));
		assertEquals("0-1", move(model, 1, 1, 2));
	}
	
	@Test
	public void setTotal() throws Exception {
		NumberOrder model = numberOrder(5);
		assertEquals("0-4", model.toString());
		
		model.setCount(number(12));
		assertEquals("0-11", model.toString());
		
		model.setCount(number(10));
		assertEquals("0-9", model.toString());
		
		// move and set total
		assertEquals("0-1, 7-9, 2-6", move(model, 7, 9, 2));
		model.setCount(number(12));
		assertEquals("0-1, 7-9, 2-6, 10-11", model.toString());
		model.setCount(number(10));
		assertEquals("0-1, 7-9, 2-6", model.toString());
		model.setCount(number(8));
		assertEquals("0-1, 7, 2-6", model.toString());
	}

	@Test
	public void indexOf() throws Exception {
		NumberOrder order = numberOrder(10);
		assertIndexOf(order, 0, 0);
		assertIndexOf(order, 1, 1);
		assertIndexOf(order, 9, 9);
		
		assertEquals("0-2, 6-7, 3-5, 8-9", move(order, 3, 5, 8));
		assertIndexOf(order, 0, 0);
		assertIndexOf(order, 2, 2);
		assertIndexOf(order, 6, 3);
		assertIndexOf(order, 7, 4);
		assertIndexOf(order, 3, 5);
		assertIndexOf(order, 4, 6);
		assertIndexOf(order, 5, 7);
		assertIndexOf(order, 9, 9);
	}
	
	// Helper test methods
	
	private static NumberOrder numberOrder(int n) {
		NumberOrder order = new NumberOrder(IntMath.getInstance());
		order.setCount(number(n));
		return order;
	}

	private static String move(NumberOrder order, int start, int end, int target) {
		order.move(number(start), number(end), number(target));
		return order.toString();
	}

	private static String move(int count, int start, int end, int destination) {
		return move(numberOrder(count), start, end, destination);
	}
	
	private void assertIndexOf(NumberOrder order, int x, int y) {
		assertEquals(y, order.indexOf(number(x)).intValue());
	}
}
