package pl.netanel.swt.matrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.Before;
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

@SuppressWarnings({ "rawtypes", "unchecked" })
@RunWith(JUnit4.class)
public class PainterTest {

  private Display display;

  @Before public void setUp() {
    display = Display.getDefault();
  }

  /**
   * Cache for the fonts with same parameters should be one
   */
  @Test public void fontCacheEquality() throws Exception {
    Font font1 = new Font(display, "Arial", 10, SWT.NORMAL);
    Font font2 = new Font(display, "Arial", 10, SWT.NORMAL);
    Font font3 = new Font(display, "Arial", 12, SWT.NORMAL);
    GC gc = new GC(display);
    FontSizeCache cache1 = FontSizeCache.get(gc, font1);
    FontSizeCache cache2 = FontSizeCache.get(gc, font2);
    FontSizeCache cache3 = FontSizeCache.get(gc, font3);
    gc.dispose();
    font1.dispose();
    font2.dispose();
    font3.dispose();

    assertEquals(cache1, cache2);
    assertFalse(cache1.equals(cache3));
  }

  @Test public void computeFontHeight() throws Exception {
    Display display = Display.getDefault();
    final Font font = new Font(display, "Arial", 12, SWT.NORMAL);
    Painter painter = new Painter(Painter.NAME_CELLS) {
      @Override
      public void setupSpatial(Number indexX, Number indexY) {
        super.setupSpatial(indexX, indexY);
        text = "abc";
        style.font = indexY.intValue() == 1 ? font : null;
      }
    };
    painter.setMatrix(new Matrix(new Shell(), SWT.NONE));
    GC gc = new GC(display);
    Point extent1 = gc.stringExtent("by");
    gc.setFont(font);
    Point extent2 = gc.stringExtent("by");

    painter.init(gc, Frozen.NONE, Frozen.NONE);

    Point size = painter.computeSize(0, 0, SWT.DEFAULT, SWT.DEFAULT);
    assertEquals(extent1.y + 2*painter.style.textMarginY, size.y);

    size = painter.computeSize(1, 1, SWT.DEFAULT, SWT.DEFAULT);
    assertEquals(extent2.y + 2*painter.style.textMarginY, size.y);

    painter.clean();
    gc.dispose();
    font.dispose();
  }

  @Test
  public void treeAutoSizing() throws Exception {
    Matrix matrix = new Matrix(new Shell(), SWT.NONE);
    matrix.getAxisX().getBody().setCount(1);
    matrix.getAxisY().getHeader().setVisible(true);
    Section bodyY = matrix.getAxisY().getBody();
    bodyY.setCount(5);
    bodyY.setParent(1, 0);
    Painter painter = new Painter(Painter.NAME_CELLS) {
      @Override
      public void setupSpatial(Number indexX, Number indexY) {
        super.setupSpatial(indexX, indexY);
        text = indexX + " " + indexY;
      }
    };
    matrix.getBody().replacePainterPreserveStyle(painter);
    painter.setTreeVisible(true);
    GC gc = new GC(matrix.getDisplay());
    painter.init(gc, Frozen.NONE, Frozen.NONE);
    try {
      assertEquals(36, painter.computeSize(0, 0, SWT.DEFAULT, SWT.DEFAULT).x);
    }
    finally {
      gc.dispose();
    }
  }
}
