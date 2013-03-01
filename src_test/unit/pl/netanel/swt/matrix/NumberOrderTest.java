package pl.netanel.swt.matrix;

import static org.junit.Assert.*;
import static pl.netanel.swt.matrix.TestUtil.numberSet;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import pl.netanel.swt.matrix.NumberOrder.ExtentOriginLimitSequence;
import pl.netanel.swt.matrix.NumberOrder.ForwardExtentFirstLastSequence;


@SuppressWarnings({"rawtypes", "unchecked"}) @RunWith(JUnit4.class) public class  NumberOrderTest  {

//	@Test
//	public void getInnerIndexes() throws Exception {
//		NumberOrder order = new NumberOrder(extent(2, 3), extent(1, 1), extent(4, 9), extent(0, 0));
//
//		assertEquals("3, 1, 4, 6, 7", order.getModelIndexes(indexes(1,2,3,5,6)).toString());
//	}

  @Test
  public void copy() throws Exception {
    NumberSetCore list = numberOrder(5);
    assertEquals(5, list.getCount().intValue());
    NumberSetCore copy = list.copy();
    assertEquals(5, copy.getCount().intValue());
  }

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

	@Test
  public void moveToTheEnd() throws Exception {
    NumberOrder model = numberOrder(10);
    move(model, 2, 2, 10);
    assertEquals("0-1, 3-9, 2", model.toString());
  }

	@Test
	public void moveFragmented1() throws Exception {
		NumberOrder model = numberOrder(10);
		move(model, 2, 2, 2);
		move(model, 6, 6, 2);
		assertEquals("0-1, 6, 2-5, 7-9", model.toString());
	}

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

		model.setCount(12);
		assertEquals("0-11", model.toString());

		model.setCount(10);
		assertEquals("0-9", model.toString());

		// move and set total
		assertEquals("0-1, 7-9, 2-6", move(model, 7, 9, 2));
		model.setCount(12);
		assertEquals("0-1, 7-9, 2-6, 10-11", model.toString());
		model.setCount(10);
		assertEquals("0-1, 7-9, 2-6", model.toString());
		model.setCount(8);
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

	@Test
	public void insertEmpty() throws Exception {
	  NumberOrder order = numberOrder(0);
	  order.insert(0, 1);
	  assertEquals("0", order.toString());
	  assertEquals(1, order.getMutableCount().intValue());
	}



	@Test
	public void insertBefore() throws Exception {
		NumberOrder order = numberOrder(5);
		order.insert(0, 2);
		assertEquals("0-6", order.toString());
		assertEquals(7, order.getMutableCount().intValue());
	}

	@Test
	public void insertAfter() throws Exception {
		NumberOrder order = numberOrder(5);
		order.insert(5, 2);
		assertEquals("0-6", order.toString());
		assertEquals(7, order.getMutableCount().intValue());
	}
	@Test
	public void insertInside() throws Exception {
		NumberOrder order = numberOrder(5);
		order.insert(3, 2);
		assertEquals("0-6", order.toString());
		assertEquals(7, order.getMutableCount().intValue());
	}
	@Test
	public void insertAfterMoved() throws Exception {
		NumberOrder order = numberOrder(5);
		order.move(1, 2, 5);
		order.insert(5, 2);
		assertEquals("0, 3-6, 1-2", order.toString());
		assertEquals(7, order.getMutableCount().intValue());
	}
	@Test
	public void insertBeforeMoved() throws Exception {
		NumberOrder order = numberOrder(5);
		order.move(1, 2, 0);
		order.insert(0, 2);
		assertEquals("3-4, 0-2, 5-6", order.toString());
		assertEquals(7, order.getMutableCount().intValue());
	}
	@Test
	public void insertInsideMoved() throws Exception {
		NumberOrder order = numberOrder(5);
		order.move(3, 3, 0);
		assertEquals("3, 0-2, 4", order.toString());
		order.insert(1, 2);
		assertEquals("5, 0-4, 6", order.toString());
		assertEquals(7, order.getMutableCount().intValue());
	}

	@Test
	public void delete() throws Exception {
	  NumberOrder order = numberOrder(5);
	  order.delete(1, 2);
	  assertEquals("0-2", order.toString());
	  assertEquals(3, order.getMutableCount().intValue());
	}

	@Test
	public void deleteAll() throws Exception {
	  NumberOrder order = numberOrder(5);
	  order.delete(0, 4);
	  assertEquals("", order.toString());
	  assertEquals(0, order.getMutableCount().intValue());
	}

	@Test
	public void getIndexByOfftset_simple_forward() throws Exception {
	  NumberOrder order = numberOrder(5);
	  assertEquals(1, order.getIndexByOffset(1, 0));
	  assertEquals(2, order.getIndexByOffset(1, 1));
	  assertEquals(3, order.getIndexByOffset(1, 2));
	  assertEquals(4, order.getIndexByOffset(1, 3));
	  assertEquals(null, order.getIndexByOffset(1, 4));
	  assertEquals(null, order.getIndexByOffset(1, 10));
	}

	@Test
	public void getIndexByOffset_forward() throws Exception {
	  NumberOrder order = numberOrder(5);
	  order.move(3, 3, 0);
	  assertEquals("3, 0-2, 4", order.toString());
	  assertEquals(3, order.getIndexByOffset(3, 0));
	  assertEquals(0, order.getIndexByOffset(3, 1));
	  assertEquals(1, order.getIndexByOffset(3, 2));
	  assertEquals(2, order.getIndexByOffset(3, 3));
	  assertEquals(4, order.getIndexByOffset(3, 4));
	  assertEquals(null, order.getIndexByOffset(3, 5));
	  assertEquals(null, order.getIndexByOffset(3, 10));
	  assertEquals(4, order.getIndexByOffset(4, 0));
	}

	@Ignore
	@Test
  public void getIndexByOfftset_simple_backward() throws Exception {
    NumberOrder order = numberOrder(5);
    assertEquals(3, order.getIndexByOffset(3, 0));
    assertEquals(2, order.getIndexByOffset(3, -1));
    assertEquals(1, order.getIndexByOffset(3, -2));
    assertEquals(0, order.getIndexByOffset(3, -3));
    assertEquals(null, order.getIndexByOffset(3, -4));
    assertEquals(null, order.getIndexByOffset(3, -10));
  }

	 @Ignore
	 @Test
   public void getIndexByOffset_backward() throws Exception {
	    NumberOrder order = numberOrder(5);
	    order.move(3, 3, 0);
	    assertEquals("3, 0-2, 4", order.toString());
	    assertEquals(4, order.getIndexByOffset(4, 0));
	    assertEquals(2, order.getIndexByOffset(4, -1));
	    assertEquals(1, order.getIndexByOffset(4, -2));
	    assertEquals(0, order.getIndexByOffset(4, -3));
	    assertEquals(3, order.getIndexByOffset(4, -4));
	    assertEquals(null, order.getIndexByOffset(4, -5));
	    assertEquals(null, order.getIndexByOffset(4, -10));
	  }

	 @Test
	 public void forwardExtentOriginLimitSequence() throws Exception {
     NumberOrder<Integer> order = numberOrder(5);
     ExtentOriginLimitSequence seq = order.countForward;

     order.move(3, 3, 2);
     assertEquals("0-1, 3, 2, 4", order.toString());
     seq.init(1, 3);
     assertStep(seq, true, 1, 1);
     assertStep(seq, true, 3, 3);
     assertStep(seq, true, 2, 2);
     assertFalse(seq.next());
	 }

	 @Test
	 public void forwardExtentFirstLastSequence() throws Exception {
	   NumberOrder<Integer> order = numberOrder(5);
	   ForwardExtentFirstLastSequence seq = order.untilForward;

	   order.move(3, 3, 2);
	   assertEquals("0-1, 3, 2, 4", order.toString());
	   seq.init(1, 2);
	   assertStep(seq, true, 1, 1);
	   assertStep(seq, true, 3, 3);
	   assertStep(seq, true, 2, 2);
	   assertFalse(seq.next());
	 }


  // Helper test methods

	private void assertStep(ExtentOriginLimitSequence seq, boolean next, Integer start, Integer end) {
	  assertEquals(next, seq.next());
	  assertEquals(start, seq.start == null ? null : seq.start.getValue());
	  assertEquals(end, seq.end == null ? null : seq.end.getValue());
	}

	private void assertStep(ForwardExtentFirstLastSequence seq, boolean next, Integer start, Integer end) {
	  assertEquals(next, seq.next());
	  assertEquals(start, seq.start == null ? null : seq.start.getValue());
	  assertEquals(end, seq.end == null ? null : seq.end.getValue());
	}

  private static NumberOrder numberOrder(int n) {
		NumberOrder order = new NumberOrder(IntMath.getInstance());
		order.setCount(n);
		return order;
	}

	private static String move(NumberOrder order, int start, int end, int target) {
		order.move(start, end, target);
		return order.toString();
	}

	private static String move(int count, int start, int end, int destination) {
		return move(numberOrder(count), start, end, destination);
	}

	private void assertIndexOf(NumberOrder order, int x, int y) {
		assertEquals(y, order.indexOf(x).intValue());
	}
}
