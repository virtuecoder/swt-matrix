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
public class AxisSequenceTest {

  @Test
  public void empty() throws Exception {
    Axis<Integer> axis = new Axis<Integer>(Integer.class, 1);
    SectionCore section = axis.layout.sections.get(0);
    section.setVisible(true);
    section.setCount(0);
    AxisSequence seq = new AxisSequence.Forward(axis.layout.sections);
    seq.setStart(AxisItem.createInternal(section, 0));
    seq.init();
    assertFalse(seq.next());
  }

  @Test
  public void strait() throws Exception {
    Axis<Integer> axis = new Axis<Integer>(Integer.class, 1);
    SectionCore section = axis.layout.sections.get(0);
    section.setVisible(true);
    section.setCount(5);
    AxisSequence seq = new AxisSequence.Forward(axis.layout.sections);
    seq.setStart(AxisItem.createInternal(section, 2));
    assertSequence("2, 3, 4", seq);
  }

  @Test
  public void straitLast() throws Exception {
    Axis<Integer> axis = new Axis<Integer>(Integer.class, 1);
    SectionCore section = axis.layout.sections.get(0);
    section.setVisible(true);
    section.setCount(5);
    AxisSequence seq = new AxisSequence.Forward(axis.layout.sections);
    seq.setStart(AxisItem.createInternal(section, 4));
    assertSequence("4", seq);
  }

  @Test
  public void order() throws Exception {
    Axis<Integer> axis = new Axis<Integer>(Integer.class, 1);
    SectionCore section = axis.layout.sections.get(0);
    section.setVisible(true);
    section.setCount(5);
    section.setOrder(1, 2, 4);
    AxisSequence seq = new AxisSequence.Forward(axis.layout.sections);
    seq.setStart(AxisItem.createInternal(section, 0));
    assertSequence("0, 3, 1, 2, 4", seq);
    seq.setStart(AxisItem.createInternal(section, 3));
    assertSequence("3, 1, 2, 4", seq);
    seq.setStart(AxisItem.createInternal(section, 2));
    assertSequence("2, 4", seq);
    seq.setStart(AxisItem.createInternal(section, 4));
    assertSequence("4", seq);
  }

  @Test
  public void hidden() throws Exception {
    Axis<Integer> axis = new Axis<Integer>(Integer.class, 1);
    SectionCore section = axis.layout.sections.get(0);
    section.setVisible(true);
    section.setCount(5);
    section.setOrder(1, 2, 4);
    AxisSequence seq = new AxisSequence.Forward(axis.layout.sections);
    seq.setStart(AxisItem.createInternal(section, 0));
    assertSequence("0, 3, 1, 2, 4", seq);

    section.setHidden(3, true);
    assertSequence("0, 1, 2, 4", seq);
    seq.setStart(AxisItem.createInternal(section, 3));
    assertSequence("1, 2, 4", seq);
  }


  public  static void assertSequence(String expected, AxisSequence seq) {
    StringBuilder sb = new StringBuilder();
    for (seq.init(); seq.next();) {
      if (sb.length() > 0) sb.append(", ");
      sb.append(seq.index);
    }
    assertEquals(expected, sb.toString());
  }
}
