/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import static org.junit.Assert.*;

import org.junit.*;

@SuppressWarnings({"rawtypes", "unchecked"})
public class ExtentPairSequenceTest {

  private Math math = new IntMath();
  private CellSet set;

  @Before
  public void setUp() {
    set = new CellSet(math, math);
  }

  @Test
  public void empty() throws Exception {
    ExtentPairSequence seq = new ExtentPairSequence(set);
    seq.init();
    assertFalse(seq.next());
  }

  @Test
  public void simple() throws Exception {
    set.add(0, 1, 0, 1);
    ExtentPairSequence seq = new ExtentPairSequence(set);
    seq.init();
    assertTrue(seq.next());
    assertExtentPair("0-1 0-1", seq);
    assertFalse(seq.next());
  }


  @Test
  public void scope() throws Exception {
    set.add(0, 2, 0, 2);
    set.add(4, 6, 0, 2);
    ExtentPairScopeSequence seq = new ExtentPairScopeSequence(set);
    seq.scope(1, 5, 1, 5);
    seq.init();
    assertTrue(seq.next());
    assertExtentPair("1-2 1-2", seq);
    assertTrue(seq.next());
    assertExtentPair("4-5 1-2", seq);
    assertFalse(seq.next());
  }

  @Test
  public void scopeStart() throws Exception {
    set.add(0, 2, 0, 2);
    set.add(4, 6, 0, 2);
    ExtentPairScopeSequence seq = new ExtentPairScopeSequence(set);
    seq.scope(1, null, null, null);
    seq.init();
    assertTrue(seq.next());
    assertExtentPair("1-2 0-2", seq);
    assertTrue(seq.next());
    assertExtentPair("4-6 0-2", seq);
    assertFalse(seq.next());
  }

  void assertExtentPair(String expected, ExtentPairSequence seq) {
    StringBuilder sb = new StringBuilder();
    sb.append(seq.getStartX()).append("-");
    sb.append(seq.getEndX()).append(" ");
    sb.append(seq.getStartY()).append("-");
    sb.append(seq.getEndY());
    assertEquals(expected, sb.toString());
  }

}
