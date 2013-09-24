/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/
package pl.netanel.swt.matrix.reloaded.ints;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.junit.Assert;
import org.junit.Test;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.SwtTestCase;
import pl.netanel.swt.matrix.reloaded.ints.Grouping.Node;

public class GroupingGuiTest extends SwtTestCase {
  private Matrix<Integer, Integer> matrix;

  @Test
  public void separatorLinesWhenFrozen() throws Exception {

    matrix = new Matrix<Integer, Integer>(shell, SWT.V_SCROLL | SWT.H_SCROLL);
//    matrix.getAxisX().getHeader().setVisible(true);
    matrix.getAxisY().getHeader().setVisible(true);
    matrix.getAxisX().getBody().setCount(10);
    matrix.getAxisY().getBody().setCount(10);
    matrix.setSize(200, 195);

    Color red = display.getSystemColor(SWT.COLOR_RED);
    new Grouping(matrix.getHeaderX(), SWT.HORIZONTAL, new Node("root",
        new Node("0",
            new Node("0.0"),
            new Node("0.1"),
            new Node("0.2")).separator(2, red),
        new Node("1",
            new Node("1.0"))));

    matrix.getAxisX().setFrozenHead(2);
    shell.open();
    assertNotColor(red, 256, 2);
  }

  // Bug fix for OPTIMUS-3228
  @Test
  public void contentDisappearsWhenTheLastGroupCoversWholeAreaAndIsCollaped() throws Exception {
    matrix = new Matrix<Integer, Integer>(shell, SWT.V_SCROLL | SWT.H_SCROLL);
    matrix.getAxisY().getHeader().setVisible(true);
    matrix.getAxisY().getBody().setCount(1);
    matrix.getAxisX().setFrozenHead(1);

    Grouping grouping = new Grouping(matrix.getHeaderX(), SWT.HORIZONTAL,
        new Node("root",
            new Node("0", new Node("0.0"), new Node("0.1")),
            new Node("1", new Node("1.0"), new Node("1.1"))));
    matrix.refresh();

    shell.setSize(100, 300);
    shell.open();
    matrix.getAxisX().showItem(matrix.getAxisX().getBody(), 3);
    grouping.getNodeByTreeIndex(1).setCollapsed(true);
    Assert.assertTrue( matrix.getAxisX().getViewportItemCount() > 1);
    Assert.assertEquals(2, matrix.getAxisX().getItemByViewportPosition(1).getIndex().intValue());
  }

}
