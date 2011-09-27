package pl.netanel.swt.matrix;

import static org.junit.Assert.assertEquals;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;
import org.junit.Test;

public class StringExtentTest {
  @Test
  public void name() throws Exception {
    GC gc = new GC(Display.getDefault());
    assertEquals(gc.stringExtent("11").x, gc.stringExtent("1").x + gc.stringExtent("1").x);
    gc.dispose();
  }
}
