/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import static org.junit.Assert.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.junit.Test;

public class MatrixGuiTest extends SwtTestCase {
  private Matrix<Integer, Integer> matrix;

  @Test
  public void lastVisible() throws Exception {
    shell.setLayout(null);
    matrix = new Matrix<Integer, Integer>(shell, SWT.V_SCROLL | SWT.H_SCROLL);
    matrix.getAxisX().getHeader().setVisible(true);
    matrix.getAxisY().getHeader().setVisible(true);
    matrix.getAxisX().getBody().setCount(10);
    matrix.getAxisY().getBody().setCount(10);
    matrix.setSize(200, 195);

    shell.open();
    assertTrue(matrix.getHorizontalBar().isVisible());
    assertTrue(matrix.getVerticalBar().isVisible());
  }

  @Test
  public void resizeWithScrollbars() throws Exception {
    shell.setLayout(new FillLayout());
    matrix = new Matrix<Integer, Integer>(shell, SWT.V_SCROLL | SWT.H_SCROLL);
    matrix.getAxisX().getHeader().setVisible(true);
    matrix.getAxisY().getHeader().setVisible(true);
    matrix.getAxisX().getBody().setCount(10);
    matrix.getAxisY().getBody().setCount(10);

    shell.setBounds(600, 400, 200, 200);
    shell.open();
    processEvents();

    shell.setSize(100, 10);
    // There was a Region exception due to negative viewport size
    matrix.refresh();

    // Bug: endless loop while setting the size of the matrix
    br();
    shell.setSize(200, 239);
    shell.redraw();
    shell.update();
    // Check lines are painted in the last row
    Color color = matrix.getBody().getPainter(Painter.NAME_LINES_Y).style.background;
    Rectangle r = matrix.getBody().getCellBounds(0, 9);
    assertEquals(color.getRGB(), getRGB(r.x + r.width, r.y + 1));
//    pause();

  }
}
