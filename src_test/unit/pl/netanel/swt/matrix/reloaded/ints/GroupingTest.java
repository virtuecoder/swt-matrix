/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/
package pl.netanel.swt.matrix.reloaded.ints;

import static org.junit.Assert.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.junit.Before;
import org.junit.Test;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.reloaded.ints.Grouping.Node;

public class GroupingTest {
  private Matrix<Integer, Integer> matrix;
  private Grouping grouping;
  private Section<Integer> bodyX;

  @Before
  public void setUp() {
    matrix = new Matrix<Integer, Integer>(new Shell(), SWT.V_SCROLL);
    matrix.getAxisY().getBody().setCount(5);
    matrix.getAxisX().getHeader().setVisible(true);
    matrix.getAxisY().getHeader().setVisible(true);
  }

  @Test
  public void nodes_0() throws Exception {
    grouping = new Grouping(matrix.getHeaderX(), SWT.HORIZONTAL, new Node("root"));
    assertEquals(0, matrix.getAxisX().getBody().getCount().intValue());
    grouping.getRoot().setCollapsed(true);
    grouping.getRoot().setCollapsedAll(true);
  }

  @Test
  public void nodes_1() throws Exception {
    grouping = new Grouping(matrix.getHeaderX(), SWT.HORIZONTAL, new Node("root", new Node("A")));
    assertEquals(1, matrix.getAxisX().getBody().getCount().intValue());
  }

  @Test
  public void nodes_tree_not_even() throws Exception {
    createGrouping();
    assertEquals(3, matrix.getAxisX().getBody().getCount().intValue());
  }

  @Test
  public void hide() throws Exception {
    createGrouping();
    bodyX = matrix.getAxisX().getBody();

    bodyX.setHidden(1, true);
    grouping.getRoot().setCollapsedAll(false);
    assertEquals(0, bodyX.getHiddenCount().intValue());

    bodyX.setHidden(0, true);
    grouping.getRoot().setCollapsedAll(true);
    assertEquals(2, bodyX.getHiddenCount().intValue());

    bodyX.setHidden(1, 2, true);
    grouping.getRoot().setCollapsedAll(false);
    assertEquals(0, bodyX.getHiddenCount().intValue());
//    TestUtil.showMatrix(matrix);
  }

  private void createGrouping() {
    grouping = new Grouping(matrix.getHeaderX(), SWT.HORIZONTAL,
        new Node("root", new Node("1", new Node("1.1"), new Node("1.2")), new Node("2")));
    matrix.refresh();
  }


}
