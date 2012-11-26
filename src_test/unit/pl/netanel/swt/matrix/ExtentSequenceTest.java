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
public class ExtentSequenceTest {
  @Test
  public void empty() throws Exception {
    NumberSet set = new NumberSet(Math.getInstance(Integer.class));
    ExtentSequence seq = new SequenceBuilder(set).extents();
    seq.init();
    assertEquals(false, seq.next());
    assertEquals(null, seq.getStart());
    assertEquals(null, seq.getEnd());
  }

  @Test
  public void single() throws Exception {
    NumberSet set = new NumberSet(Math.getInstance(Integer.class));
    set.add(0, 9);

    ExtentSequence seq = new SequenceBuilder(set).extents();
    seq.init();

    assertEquals(true, seq.next());
    assertEquals(0, seq.getStart());
    assertEquals(9, seq.getEnd());

    assertEquals(false, seq.next());
    assertEquals(null, seq.getStart());
    assertEquals(null, seq.getEnd());
  }

  @Test
  public void two() throws Exception {
    NumberSet set = new NumberSet(Math.getInstance(Integer.class));
    set.add(0, 1);
    set.add(3, 4);

    ExtentSequence seq = new SequenceBuilder(set).extents();
    seq.init();

    assertEquals(true, seq.next());
    assertEquals(0, seq.getStart());
    assertEquals(1, seq.getEnd());

    assertEquals(true, seq.next());
    assertEquals(3, seq.getStart());
    assertEquals(4, seq.getEnd());

    assertEquals(false, seq.next());
    assertEquals(null, seq.getStart());
    assertEquals(null, seq.getEnd());
  }

  @Test
  public void backward() throws Exception {
    NumberSet set = new NumberSet(Math.getInstance(Integer.class));
    set.add(0, 1);
    set.add(3, 4);

    ExtentSequence seq = new SequenceBuilder(set).backward().extents();
    seq.init();

    assertEquals(true, seq.next());
    assertEquals(3, seq.getStart());
    assertEquals(4, seq.getEnd());

    assertEquals(true, seq.next());
    assertEquals(0, seq.getStart());
    assertEquals(1, seq.getEnd());

    assertEquals(false, seq.next());
    assertEquals(null, seq.getStart());
    assertEquals(null, seq.getEnd());
  }

  @Test
  public void originFinish() throws Exception {
    NumberSet set = new NumberSet(Math.getInstance(Integer.class));
    set.add(0, 2);
    set.add(5, 7);

    ExtentSequence seq = new SequenceBuilder(set).origin(1).finish(6).extents();
    seq.init();

    assertEquals(true, seq.next());
    assertEquals(1, seq.getStart());
    assertEquals(2, seq.getEnd());

    assertEquals(true, seq.next());
    assertEquals(5, seq.getStart());
    assertEquals(6, seq.getEnd());

    assertEquals(false, seq.next());
    assertEquals(null, seq.getStart());
    assertEquals(null, seq.getEnd());
  }

//  @Test
//  public void subtract() throws Exception {
//    NumberSet set1 = new NumberList(Math.getInstance(Integer.class));
//    set1.add(2, 6);
//    set1.add(0, 1);
//
//    NumberSet set2 = new NumberSet(Math.getInstance(Integer.class));
//    set2.add(0, 1);
//    set2.add(5);
//    set2.add(3);
//
//    ExtentSequence seq = new SequenceBuilder(set1).subtract(set2).extents();
//    seq.init();
//
//    assertEquals(true, seq.next());
//    assertEquals(2, seq.getStart());
//    assertEquals(2, seq.getEnd());
//
//    assertEquals(true, seq.next());
//    assertEquals(4, seq.getStart());
//    assertEquals(4, seq.getEnd());
//
//    assertEquals(true, seq.next());
//    assertEquals(6, seq.getStart());
//    assertEquals(6, seq.getEnd());
//
//    assertEquals(false, seq.next());
//    assertEquals(null, seq.getStart());
//    assertEquals(null, seq.getEnd());
//  }

}
