/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

/**
 * Represents frozen area on the axis
 *
 * @author Jacek
 * @created 20-10-2010
 */
public enum Frozen {
  HEAD, NONE, TAIL;

  public static int getIndex(Frozen x, Frozen y) {
    return x.ordinal() + y.ordinal() * values().length;
  }


  static class PairSequence implements Sequence {

    private int i, j;
    private int size;
    Frozen frozenX, frozenY;
    int index;

    @Override
    public void init() {
      size = Frozen.values().length;
      i = -1;
      j = 0;
    }

    @Override
    public boolean next() {
      if (++i >= size) {
        if (++j >= size) return false;
        i = 0;
      }
      index = i + j * 3;
      frozenX = values()[i];
      frozenY = values()[j];
      return true;
    }

  }

  public static void main(String[] args) {
    Frozen.PairSequence seq = new PairSequence();
    for (seq.init(); seq.next();) {
      System.out.println(seq.index + " " + seq.frozenX + " " + seq.frozenY );
    }
  }

}
