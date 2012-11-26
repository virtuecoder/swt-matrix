/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings({"rawtypes", "unchecked"})
public class NumberPairSequenceTest {
  private Math math = new IntMath();

  @Test
  public void empty() throws Exception {
    CellSet set = new CellSet(math, math);
    NumberPairSequence seq = new NumberPairSequence(new ExtentPairSequence(set));
    seq.init();
    assertFalse(seq.next());
  }

  @Test
  public void simple() throws Exception {
    CellSet set = new CellSet(math, math);
    set.add(0, 1, 0, 1);
    NumberPairSequence seq = new NumberPairSequence(new ExtentPairSequence(set));
    seq.init();
    assertTrue(seq.next());
    assertCell("0 0", seq);
    assertTrue(seq.next());
    assertCell("1 0", seq);
    assertTrue(seq.next());
    assertCell("0 1", seq);
    assertTrue(seq.next());
    assertCell("1 1", seq);
    assertFalse(seq.next());
  }

  void assertCell(String expected, NumberPairSequence seq) {
    StringBuilder sb = new StringBuilder();
    sb.append(seq.getX()).append(" ").append(seq.getY());
    assertEquals(expected, sb.toString());
  }
}
