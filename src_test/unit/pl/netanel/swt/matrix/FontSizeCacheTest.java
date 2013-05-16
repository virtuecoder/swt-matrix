/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import static org.junit.Assert.*;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.junit.Before;
import org.junit.Test;

public class FontSizeCacheTest {
  private FontSizeCache cache;

  @Before
  public void setUp() {
    Display display = Display.getDefault();
    GC gc = new GC(display);
    cache = FontSizeCache.get(gc, display.getSystemFont());
    gc.dispose();
  }

  @Test
  public void shortenTextEnd_empty() throws Exception {
    assertEquals("", cache.shortenTextEnd("", 0, new Point(0, 0)));
    assertEquals("", cache.shortenTextEnd("", 20, new Point(0, 0)));
    assertEquals("", cache.shortenTextEnd("", 100, new Point(100, 20)));
  }

  @Test
  public void shortenTextEnd_single() throws Exception {
    assertEquals(".", cache.shortenTextEnd("a", 1, new Point(0, 0)));
    assertEquals(".", cache.shortenTextEnd("a", 5, new Point(0, 0)));
    assertEquals("a", cache.shortenTextEnd("a", 6, new Point(0, 0)));
  }

  @Test
  public void shortenTextEnd() throws Exception {
    assertEquals(".", cache.shortenTextEnd("aa", 1, new Point(0, 0)));
    assertEquals("..", cache.shortenTextEnd("WW", 8, new Point(0, 0)));
    assertEquals("..", cache.shortenTextEnd("aaa", 8, new Point(0, 0)));
    assertEquals("a..", cache.shortenTextEnd("aaa", 15, new Point(0, 0)));
  }

  @Test
  public void shortenTextMiddle() throws Exception {
    assertEquals("a..aaa", cache.shortenTextMiddle("aaaaaaa", 18, 2, new Point(0, 0)));
    assertEquals("a..aaa", cache.shortenTextMiddle("aaaaaaa", 19, 2, new Point(0, 0)));
    assertEquals("aa..aaa", cache.shortenTextMiddle("aaaaaaa", 20, 2, new Point(0, 0)));
  }

  @Test
  public void shortenTextEnd_multiline() throws Exception {
    assertEquals(".", cache.shortenTextEnd("a", 3, 1, new Point(0, 0)));
    assertEquals("..", cache.shortenTextEnd("aa", 10, 1, new Point(0, 0)));
    assertEquals("..", cache.shortenTextEnd("ab, ab, ab, ab", 10, 1, new Point(0, 0)));
  }

}
