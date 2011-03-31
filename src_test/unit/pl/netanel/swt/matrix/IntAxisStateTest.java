package pl.netanel.swt.matrix;

import static org.junit.Assert.assertEquals;
import static pl.netanel.swt.matrix.TestUtil.*;

import org.junit.Test;


public class IntAxisStateTest {

	@Test
	public void name() throws Exception {
		IntAxisState state = new IntAxisState(IntMath.getInstance(), 100);
		assertEquals(100, state.getValue(5));
		
		// Set value in an empty collection
		state.setValue(5, 5, 40);
		assertEquals(40, state.getValue(5));
		assertEquals(1, state.getCount());
		
		// Change the one existing value
		state.setValue(5, 5,  50);
		assertEquals(50, state.getValue(5));
		assertEquals(1, state.getCount());
		
		// Setting the same value again should not change the number of values stored
		state.setValue(5, 5,  50);
		assertEquals(50, state.getValue(5));
		assertEquals(1, state.getCount());
		
		// Adding adjacent index with the same value should enlardge extent 
		state.setValue(6, 6, 50);
		assertEquals(50, state.getValue(6));
		assertEquals(1, state.getCount());
		state.setValue(4, 4, 50);
		assertEquals(50, state.getValue(4));
		assertEquals(1, state.getCount());
		
		// Adding adjacent index with different value should create new extent
		state.setValue(7, 7, 80);
		assertEquals(80, state.getValue(7));
		assertEquals(2, state.getCount());
		
		// Set the same value inside of existing extent
		state.setValue(5, 5,  50);
		assertEquals(50, state.getValue(5));
		assertEquals(2, state.getCount());
		
		// Set different value inside of existing extent
		state.setValue(5, 5,  70);
		assertEquals(70, state.getValue(5));
		assertEquals(4, state.getCount());
		
		// Set the same value as the extent on the left, should merge
		state.setValue(6, 6, 70);
		assertEquals(70, state.getValue(6));
		assertEquals(3, state.getCount());

		// Set the same value as the extent on the right, should merge
		state.setValue(4, 4, 70);
		assertEquals(70, state.getValue(4));
		assertEquals(2, state.getCount());
		
	}
	
	@Test
	public void size1() throws Exception {
		IntAxisState state = new IntAxisState(IntMath.getInstance(), 100);
		assertEquals(100, state.getValue(5));
		
		// Set value in an empty collection
		state.setValue(extent(5), 40);
		assertEquals(40, state.getValue(5));
		assertEquals(1, state.getCount());
		
		// Change the single existing value
		state.setValue(extent(5), 50);
		assertEquals(50, state.getValue(5));
		assertEquals(1, state.getCount());

		// Setting the same value again should not change the number of values stored
		state.setValue(extent(5), 50);
		assertEquals(50, state.getValue(5));
		assertEquals(1, state.getCount());
		
		// Adding adjacent index with the same value should enlardge extent 
		state.setValue(extent(6), 50);
		assertEquals(50, state.getValue(5));
		assertEquals(50, state.getValue(6));
		assertEquals(1, state.getCount());
		state.setValue(extent(4), 50);
		assertEquals(50, state.getValue(4));
		assertEquals(1, state.getCount());

		// Adding adjacent index with different value should create new extent
		state.setValue(extent(7), 80);
		assertEquals(80, state.getValue(7));
		assertEquals(2, state.getCount());
		
		// Set the same value inside of existing extent
		state.setValue(extent(5), 50);
		assertEquals(50, state.getValue(5));
		assertEquals(2, state.getCount());
		
		// Set different value inside of existing extent
		state.setValue(extent(5), 70);
		assertEquals(70, state.getValue(5));
		assertEquals(50, state.getValue(4));
		assertEquals(50, state.getValue(6));
		assertEquals(4, state.getCount());
		
		// Set the same value as the extent on the left, should merge
		state.setValue(extent(6), 70);
		assertEquals(70, state.getValue(6));
		assertEquals(3, state.getCount());

		// Set the same value as the extent on the right, should merge
		state.setValue(extent(4), 70);
		assertEquals(70, state.getValue(4));
		assertEquals(2, state.getCount());
		
		// Set the different inside and same adjacent right at the same time
		state.setValue(extent(6), 80);
		assertEquals(80, state.getValue(6));
		assertEquals(2, state.getCount());
		
		// Set the different inside and same adjacent left at the same time
		state.setValue(extent(3), 30);
		assertEquals(30, state.getValue(3));
		assertEquals(3, state.getCount());
		state.setValue(extent(4), 30);
		assertEquals(30, state.getValue(3));
		assertEquals(30, state.getValue(4));
		assertEquals(3, state.getCount());
	}
	
	
	@Test
	public void sizeNSameValue() throws Exception {
		IntAxisState state = new IntAxisState(IntMath.getInstance(), 100);
		assertEquals(100, state.getValue(5));
		
		// Set value in an empty collection
		state.setValue(extent(5,10), 40);
		assertEquals(40, state.getValue(5));
		assertEquals(40, state.getValue(7));
		assertEquals(1, state.getCount());
		
		// Set value in the same extent
		state.setValue(extent(5,10), 50);
		assertEquals(50, state.getValue(5));
		assertEquals(50, state.getValue(7));
		assertEquals(1, state.getCount());

		// Set value in the same extent
		state.setValue(extent(6,6), 50);
		assertEquals(50, state.getValue(5));
		assertEquals(50, state.getValue(7));
		assertEquals(1, state.getCount());

		// Set value crossing left
		state.setValue(extent(4,7), 50);
		assertEquals(50, state.getValue(4));
		assertEquals(50, state.getValue(5));
		assertEquals(50, state.getValue(8));
		assertEquals(1, state.getCount());
		
		// Set value crossing right
		state.setValue(extent(9,11), 50);
		assertEquals(50, state.getValue(9));
		assertEquals(50, state.getValue(11));
		assertEquals(1, state.getCount());
		
		
		// Set value overlap
		state.setValue(extent(1,15), 50);
		assertEquals(50, state.getValue(1));
		assertEquals(50, state.getValue(15));
		assertEquals(1, state.getCount());
	}
	
	
	@Test
	public void sizeNSameValueAdjacent() throws Exception {
		IntAxisState state = new IntAxisState(IntMath.getInstance(), 100);
		state.setValue(extent(5,5), 50);

		// Set value adjacent left
		state.setValue(extent(4,4), 50);
		assertEquals(50, state.getValue(4));
		assertEquals(1, state.getCount());
		
		// Set value adjacent right
		state.setValue(extent(6,6), 50);
		assertEquals(50, state.getValue(6));
		assertEquals(1, state.getCount());
		
		// Set value between 
		state.setValue(extent(2,2), 50);
		state.setValue(extent(3,3), 50);
		assertEquals(50, state.getValue(2));
		assertEquals(50, state.getValue(3));
		assertEquals(50, state.getValue(4));
		assertEquals(1, state.getCount());

		// Set value overlap
		state.setValue(extent(10), 50);
		state.setValue(extent(12), 50);
		assertEquals(3, state.getCount());
		state.setValue(extent(1, 11), 50);
		assertEquals(1, state.getCount());
	}
	
	@Test
	public void sizeNDifferentValue() throws Exception {
		IntAxisState state = new IntAxisState(IntMath.getInstance(), 100);
		state.setValue(extent(5,9), 50); 
		
		// Set value inside left edge
		state.setValue(extent(5,5), 20);
		assertEquals(20, state.getValue(5));
		assertEquals(50, state.getValue(6));
		assertEquals(2, state.getCount());
		
		// Set value inside right edge
		state.setValue(extent(9,9), 20);
		assertEquals(20, state.getValue(9));
		assertEquals(50, state.getValue(8));
		assertEquals(3, state.getCount());
		
		// Set value inside right edge
		state.setValue(extent(9,9), 20);
		assertEquals(20, state.getValue(9));
		assertEquals(50, state.getValue(8));
		assertEquals(3, state.getCount());
		
		// Set value inside in the midlle
		state.setValue(extent(7,7), 30);
		assertEquals(50, state.getValue(6));
		assertEquals(30, state.getValue(7));
		assertEquals(50, state.getValue(8));
		assertEquals(5, state.getCount());
		
		// Set value for the whole extent
		state.setValue(extent(5,9), 70);
		assertEquals(70, state.getValue(5));
		assertEquals(70, state.getValue(6));
		assertEquals(70, state.getValue(7));
		assertEquals(70, state.getValue(8));
		assertEquals(70, state.getValue(9));
		assertEquals(1, state.getCount());
		
		// Set overlap left
		state.setValue(extent(4,9), 50);
		assertEquals(50, state.getValue(4));
		assertEquals(50, state.getValue(5));
		assertEquals(50, state.getValue(9));
		assertEquals(1, state.getCount());
		
		// Set overlap right
		state.setValue(extent(4,10), 60);
		assertEquals(60, state.getValue(4));
		assertEquals(60, state.getValue(10));
		assertEquals(1, state.getCount());
		
		// Set cross left
		state.setValue(extent(3,4), 70);
		assertEquals(70, state.getValue(3));
		assertEquals(70, state.getValue(4));
		assertEquals(2, state.getCount());
		
		// Set cross left
		state.setValue(extent(10,11), 70);
		assertEquals(70, state.getValue(10));
		assertEquals(70, state.getValue(11));
		assertEquals(3, state.getCount());
		
	}
	
	@Test
	public void sizeNDifferentValueAdjacent() throws Exception {
		IntAxisState state = new IntAxisState(IntMath.getInstance(), 100);
		state.setValue(extent(5,6), 50);
		state.setValue(extent(7,8), 60);
		
		state.setValue(extent(6,6), 60);
		assertEquals(2, state.getCount());
	}
}
