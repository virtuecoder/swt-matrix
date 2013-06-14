package pl.netanel.swt.matrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
      assertEquals("sectionCount must be greater then 0", e.getMessage());
    }
  }

  @Test
  public void construct_negativeSectionCount() throws Exception {
    try {
      new Axis(int.class, -2, 0, 1);
      fail("Expected IllegalArgumentException");
    }
    catch (Exception e) {
      assertEquals("sectionCount must be greater then 0", e.getMessage());
    }
  }


  @Test
  public void getSection_IsSectionClient() throws Exception {
    Axis axis = new Axis();
    assertTrue(axis.getSection(0) instanceof SectionClient);
  }

  @Test
  public void hideCurrent() throws Exception {
    Axis axis = new Axis();
    AxisLayout layout = axis.layout;
    layout.setViewportSize(350);

    Section body = axis.getBody();
    body.setCount(5);
    body.setDefaultCellWidth(100);

    assertEquals(0, axis.getFocusItem().getIndex());

    axis.getBody().setHidden(0, 0, true);
    layout.compute();

    assertEquals(1, axis.getFocusItem().getIndex());
  }

  @Test
  public void getContentWidth_Empty() throws Exception {
    Axis axis = new Axis();
    AxisLayout layout = axis.layout;
    layout.setViewportSize(350);

    assertEquals(0, axis.getContentWidth());
  }

  @Test
  public void getContentWidth_NotFull() throws Exception {
    Axis axis = new Axis();
    AxisLayout layout = axis.layout;
    layout.setViewportSize(350);

    Section body = axis.getBody();
    body.setCount(5);

    layout.compute();
    assertEquals(5*16 + 6*1, axis.getContentWidth());
  }

  @Test
  public void getContentWidth_Full() throws Exception {
    Axis axis = new Axis();
    AxisLayout layout = axis.layout;
    layout.setViewportSize(4*16);

    Section body = axis.getBody();
    body.setCount(5);

    layout.compute();
    assertEquals(axis.layout.getViewportSize(), axis.getContentWidth());
  }
}
