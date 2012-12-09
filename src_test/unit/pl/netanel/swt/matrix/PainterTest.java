package pl.netanel.swt.matrix;

import static org.junit.Assert.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/

//@SuppressWarnings({ "rawtypes", "unchecked" })
@RunWith(JUnit4.class)
public class PainterTest {
  @Test
  public void fontCache() throws Exception {
    Display display = Display.getDefault();
    Font font1 = new Font(display, "Arial", 10, SWT.NORMAL);
    Font font2 = new Font(display, "Arial", 10, SWT.NORMAL);
    Font font3 = new Font(display, "Arial", 12, SWT.NORMAL);
    GC gc = new GC(display);
    int[] cache1 = FontWidthCache.get(gc, font1);
    int[] cache2 = FontWidthCache.get(gc, font2);
    int[] cache3 = FontWidthCache.get(gc, font3);
    gc.dispose();
    font1.dispose();
    font2.dispose();
    font3.dispose();

    assertEquals(cache1, cache2);
    assertFalse(cache1.equals(cache3));
  }
}
