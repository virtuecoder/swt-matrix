package pl.netanel.swt.matrix;

import static org.junit.Assert.*;

import org.junit.Test;

/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/

@SuppressWarnings({"rawtypes", "unchecked"})
public class NumberSequenceTest {
  @Test
  public void empty() throws Exception {
    NumberSetCore set = new NumberSetCore(Math.getInstance(Integer.class), true);
    NumberSequence seq = new SequenceBuilder(set).numbers();
    seq.init();
    assertEquals(false, seq.next());
//    assertEquals(null, seq.item());
  }


  @Test
  public void originFinish() throws Exception {
    NumberSetCore set = new NumberSetCore(Math.getInstance(Integer.class), true);
    set.add(0, 2);
    set.add(5, 7);

    NumberSequence seq = new SequenceBuilder(set).origin(1).finish(6).numbers();
    seq.init();

    assertEquals(true, seq.next());
    assertEquals(1, seq.item());
    assertEquals(true, seq.next());
    assertEquals(2, seq.item());

    assertEquals(true, seq.next());
    assertEquals(5, seq.item());
    assertEquals(true, seq.next());
    assertEquals(6, seq.item());

    assertEquals(false, seq.next());
//    assertEquals(null, seq.item());
  }
}
