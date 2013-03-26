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

  }}
