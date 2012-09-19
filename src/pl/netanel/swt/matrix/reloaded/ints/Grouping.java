/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/
package pl.netanel.swt.matrix.reloaded.ints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.AxisItem;
import pl.netanel.swt.matrix.Extent;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.Zone;
import pl.netanel.swt.matrix.reloaded.CellImageButtonPainter;

/**
 * Groups items with parent header merged across its children.
 * Toggle button to collapse/expand groups.
 */
public class Grouping {
  private final Zone<Integer, Integer> zone;
  private final Matrix<Integer, Integer> matrix;
  private final Node root;
  private final int axisDirection;

  // private Extent<Integer>[][] groupExtents;
  Image trueImage, falseImage;
  private int levelCount;
  private Axis<Integer> axis;
  private Section<Integer> section, section2;
  private NodeVisitor layoutVisitor;
  private CellImageButtonPainter<Integer, Integer> cellPainter;

  /**
   * Default constructor.
   * @param zone
   * @param axisDirection
   * @param root
   */
  public Grouping(final Zone<Integer, Integer> zone, int axisDirection, Node root) {
    this.zone = zone;
    this.axisDirection = axisDirection;
    this.root = root;
    matrix = zone.getMatrix();
    if (axisDirection == SWT.HORIZONTAL) {
      axis = matrix.getAxisX();
      section = zone.getSectionX();
      section2 = zone.getSectionY();
    } else {
      axis = matrix.getAxisY();
      section = zone.getSectionY();
      section2 = zone.getSectionX();
    }

    createVisitors();
    initNodes();
    layout();

    createImages(axisDirection);
    zone.getMatrix().addListener(SWT.Dispose, new Listener() {
      @Override
      public void handleEvent(Event e) {
        if (trueImage != null) trueImage.dispose();
        if (falseImage != null) falseImage.dispose();
      }
    });

    cellPainter = new CellImageButtonPainter<Integer, Integer>(Painter.NAME_CELLS, trueImage, falseImage)
    {

      @Override
      protected boolean init() {
        boolean result = super.init();
        return result;
      }
      @Override
      public Boolean getToggleState(Integer indexX, Integer indexY) {
        // Return true if is expanded
        Node node = getNodeByCellIndex(indexX, indexY);
        return node.collapseDirection != SWT.NONE && isFirstItem(node, indexX, indexY) &&
            node.children.length > 1 && node.parent.collapsed == false ?
                !node.collapsed : null;
      }

      @Override
      public void setupSpatial(Integer indexX, Integer indexY) {
        super.setupSpatial(indexX, indexY);
        text = getText(indexX, indexY);
      }
    };
    zone.replacePainter(cellPainter);

    cellPainter.style.textAlignY = SWT.CENTER;
    cellPainter.style.imageAlignX = SWT.END;
    cellPainter.style.imageAlignY = SWT.CENTER;
    cellPainter.style.imageMarginX = 2;

    // Create toggle listener
    zone.unbind(Matrix.CMD_FOCUS_LOCATION, SWT.MouseDown, 1);
    final int commandId = axisDirection == SWT.HORIZONTAL ? Matrix.CMD_SELECT_COLUMN : Matrix.CMD_SELECT_ROW;
    zone.unbind(commandId, SWT.MouseDown, 1);
    zone.addListener(SWT.MouseDown, new Listener() {
      @Override
      public void handleEvent(Event e) {
        AxisItem<Integer> itemX = zone.getMatrix().getAxisX().getMouseItem();
        AxisItem<Integer> itemY = zone.getMatrix().getAxisY().getMouseItem();
        if (itemX == null || itemY == null) return;
        if (cellPainter.isOverImage(e.x, e.y)) {
          Integer indexX = itemX.getIndex();
          Integer indexY = itemY.getIndex();

          Node node = getNodeByCellIndex(indexX, indexY);
          node.setCollapsed(!node.collapsed);
        }
        else {
          zone.getMatrix().execute(commandId);
        }
      }
    });
  }

  private void initNodes() {
    final int[] maxLevel = new int[] {0};
    final int[] index = new int[] {0};

    new NodeVisitor() {
      int level = -1;
      Node parent = root;

      @Override
      protected void visitBefore() {
        if (!node.hasChildren()) {
          node.extent = Extent.createUnchecked(index[0], index[0]);
          node.index = index[0]++;
        }
        node.parent = parent;
        node.level = level;
        node.grouping = Grouping.this;
        parent = node;
        level++;
      }

      @Override
      protected void visitAfter() {
        maxLevel[0] = java.lang.Math.max(maxLevel[0], level);
        parent = node.parent;
        level--;
        if (node.hasChildren()) {
          node.extent = Extent.createUnchecked(
              node.children[0].extent.getStart(),
              node.children[node.children.length-1].extent.getEnd());
        }
      }

    }.traverse(root);

    section.setCount(index[0]);
    section2.setCount(levelCount = maxLevel[0]);
  }

  public String getText(Integer indexX, Integer indexY) {
    Node node = getNodeByCellIndex(indexX, indexY);
    return node != null && isFirstItem(node, indexX, indexY) ?
        node.caption : null;
  }

  /**
   * Merges cells in the zone according to the grouping structure.
   */
  public void layout() {
    layoutVisitor.traverse();
  }

  public Node getRoot() {
    return root;
  }

  public Matrix<Integer, Integer> getMatrix() {
    return matrix;
  }

  private boolean isFirstItem(Node node, int indexX, int indexY) {
    if (node == null) return false;
    return node.extent.getStart() == (axisDirection == SWT.HORIZONTAL ? indexX : indexY);
  }

  void createVisitors() {
    layoutVisitor = new NodeVisitor() {
      @Override protected void visitBefore() {
        if (axisDirection == SWT.HORIZONTAL) {
          if (node.hasChildren()) {
            zone.setMerged(node.extent.getStart(), node.extent.getEnd() - node.extent.getStart() + 1,
                node.level, 1, true);
          }
          else if (node.level < levelCount - 1) {
            zone.setMerged(node.index, 1, node.level, levelCount - node.level, true);
          }
        }
        else {
          if (node.hasChildren()) {
            zone.setMerged( node.level, 1,
                node.extent.getStart(), node.extent.getEnd() - node.extent.getStart() + 1, true);
          }
          else if (node.level < levelCount - 1) {
            zone.setMerged(node.level, levelCount - node.level, node.index, 1, true);
          }
        }
        if (node.collapsed) {
          node.collapsed = false;
          node.setCollapsed(true);
        }
      }
      @Override
      public NodeVisitor traverse() {
        super.traverse(root.children);
        return this;
      }
    };
  }

  void createImages(int axisDirection) {
    Display display = zone.getMatrix().getDisplay();
    int x = 6, y = 8;

    if (axisDirection == SWT.HORIZONTAL) {
      trueImage = new Image(display, x, y);
      falseImage = new Image(display, x, y);
      GC gc = new GC(trueImage);
      gc.setAntialias(SWT.ON);
      gc.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_DARK_SHADOW));
      // Draw <
      gc.fillPolygon(new int[] {0, y/2, x, 0, x, y});
      trueImage = setWhiteAsTransparent(trueImage);
      gc.dispose();

      gc = new GC(falseImage);
      gc.setAntialias(SWT.ON);
      gc.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_DARK_SHADOW));
      // Draw >
      gc.fillPolygon(new int[] {0, 0, 0, y, x, y/2});
      falseImage = setWhiteAsTransparent(falseImage);
      gc.dispose();
    }
    else {
      trueImage = new Image(display, y, x);
      falseImage = new Image(display, y, x);
      GC gc = new GC(trueImage);
      gc.setAntialias(SWT.ON);
      gc.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_DARK_SHADOW));
      // Draw ^
      gc.fillPolygon(new int[] {y/2, 0, 0, x, y, x});
      trueImage = setWhiteAsTransparent(trueImage);
      gc.dispose();

      gc = new GC(falseImage);
      gc.setAntialias(SWT.ON);
      gc.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_DARK_SHADOW));
      // Draw v
      gc.fillPolygon(new int[] {y/2, x, 0, 0, y, 0});
      falseImage = setWhiteAsTransparent(falseImage);
      gc.dispose();
    }
  }

  void createImages2(int axisDirection) {
    Display display = zone.getMatrix().getDisplay();
    trueImage = new Image(display, 9, 9);
    falseImage = new Image(display, 9, 9);

    if (axisDirection == SWT.HORIZONTAL) {
      GC gc = new GC(trueImage);
      gc.setAntialias(SWT.ON);
      gc.setForeground(display.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
      gc.drawRectangle(0, 0, 8, 8);
      gc.setForeground(display.getSystemColor(SWT.COLOR_WIDGET_FOREGROUND));
      // Draw <<
      gc.drawLine(2, 4, 4, 2);
      gc.drawLine(4, 4, 6, 2);
      gc.drawLine(2, 4, 4, 6);
      gc.drawLine(4, 4, 6, 6);
      gc.dispose();

      gc = new GC(falseImage);
      gc.setAntialias(SWT.ON);
      gc.setForeground(display.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
      gc.drawRectangle(0, 0, 8, 8);
      gc.setForeground(display.getSystemColor(SWT.COLOR_WIDGET_FOREGROUND));
      // Draw >>
      gc.drawLine(2, 2, 4, 4);
      gc.drawLine(4, 2, 6, 4);
      gc.drawLine(2, 6, 4, 4);
      gc.drawLine(4, 6, 6, 4);
      gc.dispose();
    }
    else {

    }
  }

  private Image setWhiteAsTransparent(Image image) {
    ImageData imageData = image.getImageData();
    int whitePixel = imageData.palette.getPixel(new RGB(255,255,255));
    imageData.transparentPixel = whitePixel;
    return new Image(zone.getMatrix().getDisplay(), imageData);
  }

  /**
   * Returns the grouping node in the given cell.
   * @param indexX cell index on the horizontal axis
   * @param indexY cell index on the vertical axis
   * @return grouping node in the given cell
   */
  public Node getNodeByCellIndex(int indexX, int indexY) {
    int level, index;
    if (axisDirection == SWT.HORIZONTAL) {
      level = indexY;
      index = indexX;
    } else {
      level = indexX;
      index = indexY;
    }
    Node node = root;
    for (int i = 0; i < levelCount; i++) {
      for (Node child: node.children) {
        if (child.extent.getStart() <= index && index <= child.extent.getEnd()) {
          if (child.level == level) {
            return child;
          }
          else if (child.children == null) {
            return null;
          }
          else {
            node = child;
          }
        }
      }
    }
    return null;
  }

  public Node getNodeByTreeIndex(final int ...index) {
    Node node  = root;
    for (int i = 0; i < index.length; i++) {
      node = node.children[index[i]];
    }
    return root.equals(node) ? null : node;
  }

  public void setToggleImages(Image collapseImage, Image expandImage) {
    cellPainter.setToggleImages(collapseImage, expandImage);
  }

  /**
   * Represent a node in the grouping structure.
   * Implements Builder pattern.
   */
  public static class Node {
    private final String caption;
    private final Node[] children;
    private Node parent;
    private int level, index;
    private Extent<Integer> extent;
    private boolean collapsed;
    Grouping grouping;
    private int collapseDirection;

    public Node(String caption, Node ...children) {
      this.caption = caption;
      this.children = children;
      this.collapseDirection = SWT.BEGINNING;
    }

    @Override
    public String toString() {
      return caption;
    }

    /**
     * Return <code>true</code> if this node has children, or false otherwise.
     * @return <code>true</code> if this node has children, or false otherwise
     */
    public boolean hasChildren() {
      return children.length > 0;
    }

    /**
     * Return list of children of this node.
     * @return list of children of this node
     */
    public List<Node> getChildren() {
      return Arrays.asList(children);
    }

    /**
     * Returns parent of this node.
     * @return parent of this node
     */
    public Node getParent() {
      return parent;
    }

    /**
     * Returns caption of this node.
     * @return caption of this node
     */
    public String getCaption() {
      return this.caption;
    }
    /**
     * Sets the collapsed state of this node to the given value.
     * @param state state to set for <code>collapsed</code> property
     * @return this node
     */
    public Node setCollapsed(boolean state) {
      if (collapseDirection == SWT.NONE || collapsed == state || level == -1) return this;
      collapsed = !collapsed;
      if (grouping != null) {
        AxisItem<Integer> focusItem = grouping.axis.getFocusItem();

        if (state == true && children.length > 1) {
          // Collapse
          final ArrayList<Extent<Integer>> extents = new ArrayList<Extent<Integer>>();
          new NodeVisitor() {
            @Override
            protected void visitBefore() {
              if (node.collapseDirection == SWT.NONE) {
                stopBranch = true;
                extents.add(node.extent);
              }
            }
          }.traverse(this);

          int ds = collapseDirection == SWT.END ? 0 : 1;
          int de = collapseDirection == SWT.END ? 1 : 0;
          grouping.section.setHidden(extent.getStart() + ds, extent.getEnd() - de, true);

          for (int i = 0; i < extents.size(); i++) {
            Extent<Integer> extent2 = extents.get(i);
            grouping.section.setHidden(extent2.getStart(), extent2.getEnd(), false);
          }
        }
        else {
          if (hasChildren()) {
            expand(level+1, this);
          }
        }
        grouping.matrix.refresh();
        grouping.axis.setFocusItem(focusItem);
      }
      return this;
    }

    private void expand(int startLevel, Node node) {
      if (node.hasChildren()) {
        for (Node child: node.children) {
          if (child.collapsed) {
            grouping.section.setHidden(child.extent.getStart(), false);
          }
          else {
            grouping.section.setHidden(child.extent.getStart(), child.extent.getEnd(), false);
          }
        }
      }
      else {
        grouping.section.setHidden(node.index, node.index, false);
      }
    }

    /**
     * Sets the collapsed state of this node
     * and all related nodes on the lower level to the given value.
     *
     * @param state state to set for <code>collapsed</code> property
     * @return this node
     */
    public Node setCollapsedAll(final boolean state) {
      new NodeVisitor() {
        @Override
        protected void visitAfter() {
          if (state == true && node.level > 0) {
            node.collapsed = true;
          }
          else {
            node.setCollapsed(state);
          }
        }
      }.traverse(this);
      return this;
    }

    /**
     * Sets the collapse direction of this node to the given value.
     * <p>
     * The possible values are {@link SWT#BEGINNING}, {@link SWT#END} and {@link SWT#NONE}
     * <li> With {@link SWT#BEGINNING} the first node is shown when node is collapsed
     * <li> With {@link SWT#END} the last node is shown when node is collapsed (<b>not implemented yet!</b>)
     * <li> With {@link SWT#NONE} the node cannot be collapsed
     *
     * @param direction collapse direction to set
     * @return this node
     */
    public Node setCollapseDirection(int direction) {
      if (direction == SWT.END) throw new UnsupportedOperationException("Not implemnted yet");
      collapseDirection = direction;
      if (grouping != null) {
        grouping.matrix.redraw();
      }
      return this;
    }

    /**
     * Sets the collapse direction of this node
     * and all related nodes in the lower level to the given value.
     * @param direction collapse direction to set.
     * @return this node
     * @see #setCollapseDirection(int)
     */
    public Node setCollapseDirectionAll(final int direction) {
      new NodeVisitor() {
        @Override
        protected void visitBefore() {
          node.setCollapseDirection(direction);
        }
      }.traverse(this);
      return this;
    }
  }

  /**
   * Traverses the given node or array of nodes and all the related nodes at the lower levels.
   * The order of visiting is parent first then children.
   * <p>
   * Implements the Visitor pattern.
   */
  public static abstract class NodeVisitor {
    /**
     * Node being visited
     */
    protected Node node;
    /**
     * Stops the execution after {@link #visit(Node)} or <code>traverse(child)</code>
     * if set to <code>true</code>
     */
    protected boolean stop;

    /**
     * Stops processing the current branch and got the one
     * if set to <code>true</code>
     */
    protected boolean stopBranch;

    /**
     * Method executed before traversing children
     * @param node parent for the children to traverse
     */
    protected void visitBefore() {}
    /**
     * Method executed after traversing children
     * @param node parent for the children to traverse
     */
    protected void visitAfter() {}

    /**
     * Returns the latest visited node.
     * @return the latest visited node
     */
    public Node get() {
      return node;
    }
    /**
     * Traverses starting from the given node.
     * @param node node to traverse
     * @return this object
     */
    public NodeVisitor traverse(Node node) {
      this.node = node;
      visitBefore();
      if (stop) return this;
      for (Node child: node.children) {
        traverse(child);
        if (stop || stopBranch) return this;
      }
      this.node = node;
      visitAfter();
      return this;
    }

    /**
     * Traverses starting from the given nodes.
     * @param nodes nodes to traverse
     * @return this object
     */
    public NodeVisitor traverse(Node[] nodes) {
      for (Node node: nodes) {
        traverse(node);
        if (stop || stopBranch) break;
      }
      return this;
    }

    /**
     * Can encapsulate the traversal starting node or nodes.
     * @return this object
     */
    public NodeVisitor traverse() {
      return this;
    }
  }
}
