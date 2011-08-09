package pl.netanel.swt.matrix;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@SuppressWarnings({"rawtypes", "unchecked"}) @RunWith(JUnit4.class) public class  AxisTest {
  
	@Test
	public void construct_primitiveClass() throws Exception {
	  new Axis(int.class, 2, 0, 1);
	}
	
	@Test
	public void construct_IllegalClass() throws Exception {
	  try {
	    new Axis(Float.class, 2, 0, 1);
	    fail("Expected IllegalArgumentException");
	  }
	  catch (Exception e) {
	    assertEquals("Cannot do arithmetics on java.lang.Float class", e.getMessage());
	  }
	}
	
	@Test
	public void construct_NullClass() throws Exception {
	  try {
	    new Axis(null, 2, 0, 1);
	    fail("Expected IllegalArgumentException");
	  }
	  catch (Exception e) {
	    assertEquals("numberClass cannot be null", e.getMessage());
	  }
	}
	
	@Test
	public void construct_zeroSectionCount() throws Exception {
	  try {
	    new Axis(int.class, 0, 0, 1);
	    fail("Expected IllegalArgumentException");
	  }
	  catch (Exception e) {
	    assertEquals("sectionCount must be greater then 1", e.getMessage());
	  }
	}
	
	@Test
	public void construct_negativeSectionCount() throws Exception {
	  try {
	    new Axis(int.class, -2, 0, 1);
	    fail("Expected IllegalArgumentException");
	  }
	  catch (Exception e) {
	    assertEquals("sectionCount must be greater then 1", e.getMessage());
	  }
	}
	
	
	@Test
	public void getSection_IsSectionClient() throws Exception {
	  Axis axis = new Axis();
	  assertTrue(axis.getSection(0) instanceof SectionClient);
	}
	
	@Test
	public void hideCurrent() throws Exception {
		Axis model = new Axis();
		Layout layout = model.layout;
		layout.setViewportSize(350);

		Section body = model.getBody();
		body.setCount(5);
		body.setDefaultCellWidth(100);
		
		assertEquals(0, model.getFocusItem().getIndex());
		
		model.getBody().setHidden(0, 0, true);
		layout.compute();
		
		assertEquals(1, model.getFocusItem().getIndex());
	}
	
}
