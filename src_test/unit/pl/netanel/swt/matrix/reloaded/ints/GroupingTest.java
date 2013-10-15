/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA.html
 ******************************************************************************/
package pl.netanel.swt.matrix.reloaded.ints;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static pl.netanel.swt.matrix.reloaded.ints.Grouping.Node.PERMANENT;
import static pl.netanel.swt.matrix.reloaded.ints.Grouping.Node.REMAIN;
import static pl.netanel.swt.matrix.reloaded.ints.Grouping.Node.SUMMARY;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import pl.netanel.swt.matrix.CellExtent;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.Zone;
import pl.netanel.swt.matrix.reloaded.ints.Grouping.Node;

public class GroupingTest {
  private Matrix<Integer, Integer> matrix;
  private Grouping grouping;
  private Section<Integer> bodyX;
  private Zone<Integer, Integer> zone;

  @Before
  public void setUp() {
    matrix = new Matrix<Integer, Integer>(new Shell(), SWT.V_SCROLL);
    matrix.getAxisY().getBody().setCount(5);
    matrix.getAxisX().getHeader().setVisible(true);
    matrix.getAxisY().getHeader().setVisible(true);
    zone = matrix.getBody();
  }

  @Test
  public void nodes_0() throws Exception {
    grouping = new Grouping(zone, SWT.HORIZONTAL, new Node("root"));
    assertEquals(0, matrix.getAxisX().getBody().getCount().intValue());
    grouping.getRoot().setCollapsed(true);
    grouping.getRoot().setCollapsedAll(true);
  }

  @Test
  public void nodes_1() throws Exception {
    grouping = new Grouping(zone, SWT.HORIZONTAL,
        new Node("root", new Node("A")));
    assertEquals(1, matrix.getAxisX().getBody().getCount().intValue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void bothRemainAndSummary() throws Exception {
    grouping = new Grouping(zone, SWT.HORIZONTAL,
        new Node("root", new Node("A", REMAIN | SUMMARY)));
  }

  public void optionsRemain() throws Exception {
    // No exception
    grouping = new Grouping(zone, SWT.HORIZONTAL,
        new Node("root", new Node("A", REMAIN)));
  }

  public void optionsSummary() throws Exception {
    // No exception
    grouping = new Grouping(zone, SWT.HORIZONTAL,
        new Node("root", new Node("A", SUMMARY)));
  }

  @Test
  public void nodes_tree_not_even_layout() throws Exception {
    grouping = new Grouping(zone, SWT.HORIZONTAL, new Node("root",
        new Node("0", new Node("0.0"), new Node("0.1")), new Node("1")));
    assertEquals(3, matrix.getAxisX().getBody().getCount().intValue());
    assertEquals(2, matrix.getAxisY().getBody().getCount().intValue());
    CellExtent<Integer, Integer> merged = zone.getMerged(2, 0);
    assertEquals("[2-1, 0-2]", merged.toString());
  }

  @Test
  public void ramainFirst() throws Exception {
    grouping = new Grouping(matrix.getBody(), SWT.HORIZONTAL, new Node("root",
        new Node("0", new Node("0.0"), new Node("0.1")), new Node("1")));
    assertEquals(3, matrix.getAxisX().getBody().getCount().intValue());
    assertEquals(2, matrix.getAxisY().getBody().getCount().intValue());

    assertEquals(0, zone.getSectionX().getHiddenCount().intValue());
    grouping.getRoot().setCollapsedAll(true);
    assertEquals(1, zone.getSectionX().getHiddenCount().intValue());
    assertTrue(zone.getSectionX().isHidden(1));
  }

  @Test
  public void ramainLast() throws Exception {
    grouping = new Grouping(matrix.getBody(), SWT.HORIZONTAL, new Node("root",
        new Node("0", new Node("0.0"), new Node("0.1", REMAIN)), new Node("1")));
    assertEquals(3, matrix.getAxisX().getBody().getCount().intValue());
    assertEquals(2, matrix.getAxisY().getBody().getCount().intValue());

    grouping.getRoot().setCollapsedAll(true);
    assertEquals(1, zone.getSectionX().getHiddenCount().intValue());
    assertTrue(zone.getSectionX().isHidden(0));
  }

  @Test
  public void ramainLast3Levels() throws Exception {
    grouping = new Grouping(matrix.getBody(), SWT.HORIZONTAL, new Node("root",
        new Node("0", new Node("0.0"), new Node("0.1",
            new Node("0.1.0"), new Node("0.1.1", REMAIN))),
        new Node("1")));
    assertEquals(4, matrix.getAxisX().getBody().getCount().intValue());
    assertEquals(3, matrix.getAxisY().getBody().getCount().intValue());

    grouping.getRoot().setCollapsedAll(true);
    assertEquals("0-1", zone.getSectionX().getHidden().toString());

    grouping.getNodeByTreeIndex(0).setCollapsed(false);
    assertEquals("1", zone.getSectionX().getHidden().toString());
  }

  @Test
  public void ramainTwo3Levels() throws Exception {
    grouping = new Grouping(matrix.getBody(), SWT.HORIZONTAL, new Node("root",
        new Node("0",
            new Node("0.0", new Node("0.0.0", REMAIN), new Node("0.0.1"), new Node("0.0.2", REMAIN)),
            new Node("0.1")),
        new Node("1")));
    assertEquals(5, matrix.getAxisX().getBody().getCount().intValue());
    assertEquals(3, matrix.getAxisY().getBody().getCount().intValue());

    grouping.getRoot().setCollapsedAll(true);
    assertEquals(2, zone.getSectionX().getHiddenCount().intValue());
    assertTrue(zone.getSectionX().isHidden(1));
    assertTrue(zone.getSectionX().isHidden(3));

    grouping.getNodeByTreeIndex(0).setCollapsed(false);
    assertEquals(1, zone.getSectionX().getHiddenCount().intValue());
    assertTrue(zone.getSectionX().isHidden(1));
  }

  @Test
  public void permanent3Levels() throws Exception {
    grouping = new Grouping(matrix.getBody(), SWT.HORIZONTAL, new Node("root",
        new Node("0",
            new Node("0.0", new Node("0.0.0"), new Node("0.0.1")),
            new Node("0.1", PERMANENT, new Node("0.1.0"), new Node("0.1.1"))),
        new Node("1")));
    assertEquals(5, matrix.getAxisX().getBody().getCount().intValue());
    assertEquals(3, matrix.getAxisY().getBody().getCount().intValue());

    assertEquals(true, grouping.getNodeByTreeIndex(0).getToggleState());
    assertEquals(null, grouping.getNodeByTreeIndex(0, 1).getToggleState());

    grouping.getRoot().setCollapsedAll(true);
    assertEquals("1", zone.getSectionX().getHidden().toString());
  }

  @Test
  public void permanentGetToggle() throws Exception {
    grouping = new Grouping(matrix.getBody(), SWT.HORIZONTAL, new Node("root",
        new Node("0",
            new Node("0.0", REMAIN, new Node("0.0.0"), new Node("0.0.1")),
            new Node("0.1", PERMANENT, new Node("0.1.0"), new Node("0.1.1"))),
            new Node("1")));
    assertEquals(5, matrix.getAxisX().getBody().getCount().intValue());
    assertEquals(3, matrix.getAxisY().getBody().getCount().intValue());

    assertEquals(null, grouping.getNodeByTreeIndex(0).getToggleState());
  }

  @Test
  public void summary() throws Exception {
    grouping = new Grouping(matrix.getBody(), SWT.HORIZONTAL, new Node("root",
        new Node("0",
            new Node("0.0", new Node("0.0.0"), new Node("0.0.1", SUMMARY)),
            new Node("0.1", new Node("0.1.0"), new Node("0.1.1"))),
            new Node("1")));
    assertEquals(5, matrix.getAxisX().getBody().getCount().intValue());
    assertEquals(3, matrix.getAxisY().getBody().getCount().intValue());

    assertEquals(1, zone.getSectionX().getHiddenCount().intValue());
    assertTrue(zone.getSectionX().isHidden(1));
    grouping.getNodeByTreeIndex(0, 0).setCollapsed(true);
    assertFalse(zone.getSectionX().isHidden(1));
  }

  @Test
  public void hide() throws Exception {
    createGrouping();
    bodyX = matrix.getAxisX().getBody();

    bodyX.setHidden(1, true);
    grouping.getRoot().setCollapsedAll(false);
    assertEquals(1, bodyX.getHiddenCount().intValue());

    bodyX.setHidden(0, true);
    grouping.getRoot().setCollapsedAll(true);
    assertEquals(2, bodyX.getHiddenCount().intValue());

    bodyX.setHidden(1, 2, true);
    grouping.getRoot().setCollapsedAll(false);
    assertEquals(3, bodyX.getHiddenCount().intValue());
//    TestUtil.showMatrix(matrix);
  }

  @Test
  public void expandedInitially() throws Exception {
    createGrouping3();
    new Grouping.NodeVisitor() {
      @Override
      protected void visitAfter(Node node) {
        assertFalse(node.isCollapsed());
      }
    }.traverse(grouping.getRoot().getChildren());
  }

  @Test
  public void collapseAll() throws Exception {
    createGrouping3();
    grouping.getRoot().setCollapsedAll(true);
    assertTrue(grouping.getNodeByTreeIndex(0).isCollapsed());
    assertTrue(grouping.getNodeByTreeIndex(0, 0).isCollapsed());
    assertFalse(grouping.getNodeByTreeIndex(0, 0, 0).isCollapsed());
    assertFalse(grouping.getNodeByTreeIndex(0, 0, 1).isCollapsed());
    assertFalse(grouping.getNodeByTreeIndex(0, 1).isCollapsed());
    assertTrue(grouping.getNodeByTreeIndex(1).isCollapsed());
    assertTrue(grouping.getNodeByTreeIndex(1, 0).isCollapsed());
    assertFalse(grouping.getNodeByTreeIndex(1, 0, 0).isCollapsed());
    assertFalse(grouping.getNodeByTreeIndex(1, 0, 1).isCollapsed());
    assertFalse(grouping.getNodeByTreeIndex(1, 1).isCollapsed());
    assertFalse(grouping.getNodeByTreeIndex(2).isCollapsed());
    assertTrue(grouping.getNodeByTreeIndex(2, 0).isCollapsed());
    assertFalse(grouping.getNodeByTreeIndex(2, 0, 0).isCollapsed());
    assertFalse(grouping.getNodeByTreeIndex(2, 0, 1).isCollapsed());
    assertFalse(grouping.getNodeByTreeIndex(2, 1).isCollapsed());
  }


  // Bug fix for OPTIMUS-3227
  @Test
  public void separatorWhenFirstInGroupIsHidden() throws Exception {
    grouping = new Grouping(matrix.getHeaderX(), SWT.HORIZONTAL,
        new Node("root",
            new Node("0", new Node("0.0"), new Node("0.1")).separator(10, null),
            new Node("1", new Node("1.0"), new Node("1.1"))));
    matrix.refresh();
    matrix.getAxisX().getBody().setHidden(2, true);
    matrix.refresh();
    assertEquals(10, matrix.getAxisX().getBody().getLineWidth(3));
  }

  //Bug fix for OPTIMUS-3227
  @Test
  public void separatorWhenGroupIsHidden() throws Exception {
    grouping = new Grouping(matrix.getHeaderX(), SWT.HORIZONTAL,
        new Node("root",
            new Node("0", new Node("0.0"), new Node("0.1")).separator(10, null),
            new Node("1", new Node("1.0"), new Node("1.1")),
            new Node("2", new Node("2.0"), new Node("2.1")),
            new Node("3", new Node("3.0"), new Node("3.1")),
            new Node("4", new Node("4.0"), new Node("4.1"))));
    matrix.refresh();
    matrix.getAxisX().getBody().setHidden(2, 5, true);
    matrix.refresh();
    assertEquals(10, matrix.getAxisX().getBody().getLineWidth(6));
  }

  @Ignore
  @Test
  public void remainSecondIfFirstHidden() throws Exception {
    grouping = new Grouping(matrix.getHeaderX(), SWT.HORIZONTAL,
        new Node("root",
            new Node("0", new Node("0.0"), new Node("0.1")),
            new Node("1", new Node("1.0"), new Node("1.1"))));
    matrix.refresh();
    matrix.getAxisX().getBody().setHidden(2, true);
    matrix.refresh();
    Node node = grouping.getNodeByTreeIndex(1);
    node.setCollapsed(true);
    assertFalse(matrix.getAxisX().getBody().isHidden(3));
  }

  void showMatrix() {
    Shell shell = matrix.getShell();
    shell.setLayout(new FillLayout());
    shell.layout();
    Display display = matrix.getDisplay();
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }


  void createGrouping() {
    grouping = new Grouping(matrix.getHeaderX(), SWT.HORIZONTAL,
        new Node("root", new Node("1", new Node("1.1"), new Node("1.2")), new Node("2")));
    matrix.refresh();
  }

  void createGrouping2() {
    grouping = new Grouping(matrix.getHeaderX(), SWT.HORIZONTAL,
        new Node("root",
            new Node("0", new Node("0.0", new Node("0.0.0"), new Node("0.0.1")), new Node("0.1")),
            new Node("1",
                new Node("1.0", new Node("1.0.0"), new Node("1.0.1")),
                new Node("1.1", PERMANENT, new Node("1.1.0"), new Node("1.1.1"))
            )));
    matrix.refresh();
  }

  void createGrouping3() {
    grouping = new Grouping(matrix.getHeaderX(), SWT.HORIZONTAL,
        new Node("root",
            new Node("1", new Node("1.1", new Node("1.1.1"), new Node("1.1.2")), new Node("1.2")),
            new Node("2", new Node("2.1", new Node("2.1.1"), new Node("2.1.2")), new Node("2.2")),
            new Node("3", PERMANENT, new Node("3.1", new Node("3.1.1"), new Node("3.1.2")), new Node("3.2"))));
    matrix.refresh();
  }

}
