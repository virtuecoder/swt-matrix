/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

public class ContentChangeEvent<N> {
  public final static int ADD = 0;
  public final static int REMOVE = 1;

  public N start;
  public N end;
  public int operation;

  public ContentChangeEvent(int operation, N start, N end) {
    this.operation = operation;
    this.start = start;
    this.end = end;
  }
}