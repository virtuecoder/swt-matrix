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
import pl.netanel.util.Preconditions;

/**
 * Manages collapse-able hierarchy of item groups.
 * Parent node spans across its children.
 * There are toggle button to collapse/expand groups.
 */
public class Grouping {

  private static final Boolean TOGGLE_EXPAND = false;
  private static final Boolean TOGGLE_COLLAPSE = true;
  private static final Boolean TOGGLE_NONE = null;

  private final Zone<Integer, Integer> zone;
  private final Matrix<Integer, Integer> matrix;
  private final Node root;
  private final int axisDirection;

  // private Extent<Integer>[][] groupExtents;
  Image trueImage, falseImage;
  private int levelCount;
  private Axis<Integer> axis, axis2;
  private Section<Integer> section, section2;
  private NodeVisitor layoutVisitor;
  private CellImageButtonPainter<Integer, Integer> cellPainter;
  private Integer selectLevel = 0;


  /**
   * Creates groups in the given zone that are spanning in the giving direction
   * according to the given node hierarchy.
   * <p>
   * Sets the count of items in the zone sections automatically.
   *
   * @param zone zone at which grouping should happen
   * @param axisDirection direction of grouping: SWT.HORIZONTAL or SWT.VERTICAL
   * @param root root Node of the grouping hierarchy
   */
  public Grouping(final Zone<Integer, Integer> zone, final int axisDirection, Node root) {
    Preconditions.checkNotNullWithName(zone, "zone");
    Preconditions.checkNotNullWithName(root, "root");
    Preconditions.checkArgument(
        axisDirection == SWT.HORIZONTAL || axisDirection == SWT.VERTICAL,
        "axisDirection must be either SWT.HORIZONTAL or SWT.VERTICAL");

    this.zone = zone;
    this.axisDirection = axisDirection;
    this.root = root;
    matrix = zone.getMatrix();
    if (axisDirection == SWT.HORIZONTAL) {
      axis = matrix.getAxisX();
      axis2 = matrix.getAxisY();
      section = zone.getSectionX();
      section2 = zone.getSectionY();
    } else {
      axis = matrix.getAxisY();
      axis2 = matrix.getAxisX();
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
        return node.getToggleState();
//        return node.collapseDirection != SWT.NONE && isFirstItem(node, indexX, indexY) &&
//            node.children.size() > 1 /*&& node.parent.collapsed == false*/ ?
//                !node.collapsed : null;
      }

      @Override
      public void setupSpatial(Integer indexX, Integer indexY) {
        super.setupSpatial(indexX, indexY);
        text = getText(indexX, indexY);
      }

      @Override
      public void setup(Integer indexX, Integer indexY) {
        super.setup(indexX, indexY);
        AxisItem<Integer> item = axis2.getMouseItem();
        if (item != null) {
          Node node = getNodeByCellIndex(indexX, indexY);
          if (node.level < selectLevel && isFirstItem(node, indexX, indexY)) {
            isSelected = false;
          }
        }
      }
    };
    zone.replacePainterPreserveStyle(cellPainter);

    cellPainter.style.textAlignY = SWT.CENTER;
    cellPainter.style.imageAlignX = SWT.END;
    cellPainter.style.imageAlignY = SWT.CENTER;
    cellPainter.style.imageMarginX = 2;

    // Create toggle and selection listener
    //zone.unbind(Matrix.CMD_FOCUS_LOCATION, SWT.MouseDown, 1);
    final int commandId = axisDirection == SWT.HORIZONTAL ? Matrix.CMD_SELECT_COLUMN : Matrix.CMD_SELECT_ROW;
    zone.unbind(commandId, SWT.MouseDown, 1);
    zone.addListener(SWT.MouseDown, new Listener() {
      @Override
      public void handleEvent(Event e) {
        AxisItem<Integer> itemX = zone.getMatrix().getAxisX().getMouseItem();
        AxisItem<Integer> itemY = zone.getMatrix().getAxisY().getMouseItem();
        if (itemX == null || itemY == null) return;

        Integer indexX = itemX.getIndex();
        Integer indexY = itemY.getIndex();
        Node node = getNodeByCellIndex(indexX, indexY);

        // toggle
        if (cellPainter.isOverImage(e.x, e.y)) {
          node.setCollapsed(!node.collapsed);
        }
        else {
          AxisItem<Integer> item = axis2.getMouseItem();
          selectLevel = item == null ? levelCount : item.getIndex();
//          section.setSelected(false);
//          axis.setFocusItem(axisDirection == SWT.HORIZONTAL ? itemX : itemY);
          matrix.execute(commandId);
          section.setSelected(node.extent.getStart(), node.extent.getEnd(), true);
          matrix.redraw();
        }
      }
    });


  }

  private void initNodes() {
    final int[] maxLevel = new int[] {0};
    final int[] index = new int[] {0};

    new NodeVisitor() {
      int level = 0;
      Node parent = root;

      @Override
      protected void visitBefore(Node node) {
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
      protected void visitAfter(Node node) {
        maxLevel[0] = java.lang.Math.max(maxLevel[0], level);
        parent = node.parent;
        level--;
        if (node.hasChildren()) {
          node.extent = Extent.createUnchecked(
              node.children.get(0).extent.getStart(),
              node.children.get(node.children.size()-1).extent.getEnd());
        }
      }

    }.traverse(root.children);

    section.setCount(index[0]);
    section2.setCount(levelCount = maxLevel[0]);
    selectLevel = index[0];

    root.grouping = this;
    root.extent = Extent.createUnchecked(0, section.getCount()-1);
  }

  /**
   * Returns the text of the cell at the given indexes.
   * If the cell is merged it will return the text of the first cell from the merged group.
   *
   * @param indexX cell index on the horizontal axis
   * @param indexY cell index on the vertical axis
   * @return the text of the cell at the given indexes
   */
  public String getText(Integer indexX, Integer indexY) {
    Node node = getNodeByCellIndex(indexX, indexY);
    return node != null && isFirstItem(node, indexX, indexY) ?
        node.caption : null;
  }

  /**
   * Merges cells in the zone according to the grouping hierarchy.
   */
  public void layout() {
    layoutVisitor.traverse();
  }

  /**
   * Return the root of the grouping hierarchy. This node is not visible.
   * @return
   */
  public Node getRoot() {
    return root;
  }

  /**
   * Returns the matrix the grouping is created for.
   * @return the matrix the grouping is created for
   */
  public Matrix<Integer, Integer> getMatrix() {
    return matrix;
  }

  private boolean isFirstItem(Node node, int indexX, int indexY) {
    if (node == null) return false;
    return node.extent.getStart() == (axisDirection == SWT.HORIZONTAL ? indexX : indexY);
  }

  void createVisitors() {
    layoutVisitor = new NodeVisitor() {
      @Override protected void visitBefore(Node node) {
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
      node = node.children.get(index[i]);
    }
    return root.equals(node) ? null : node;
  }

  public void setToggleImages(Image collapseImage, Image expandImage) {
    cellPainter.setToggleImages(collapseImage, expandImage);
  }

  /**
   * Represent a node in the grouping hierarchy.
   * Implements Builder pattern.
   */
  public static class Node {
    private final String caption;
    private final List<Node> children;
    private Node parent;
    private int level = -1, index;
    private Extent<Integer> extent;
    private boolean collapsed;
    Grouping grouping;
    private int collapseDirection;

    public Node(String caption, Node ...children) {
      this.caption = caption;
      this.children = Arrays.asList(children);
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
      return !children.isEmpty();
    }

    /**
     * Return list of children of this node.
     * @return list of children of this node
     */
    public List<Node> getChildren() {
      return children;
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
      if (collapseDirection == SWT.NONE || level == -1) return this;
      collapsed = state;
      if (grouping != null) {
        AxisItem<Integer> focusItem = grouping.axis.getFocusItem();

        // Collapse
        if (state == true && children.size() > 1) {
          // Find the not collapse able extents to exclude
          final ArrayList<Extent<Integer>> extents = new ArrayList<Extent<Integer>>();
          new NodeVisitor() {
            @Override
            protected void visitBefore(Node node) {
              if (node.collapseDirection == SWT.NONE) {
                stopBranch = true;
                extents.add(node.extent);
              }
            }
          }.traverse(this);

          // Hide
          int ds = collapseDirection == SWT.END ? 0 : 1;
          int de = collapseDirection == SWT.END ? 1 : 0;
          grouping.section.setHidden(extent.getStart() + ds, extent.getEnd() - de, true);

          // Unhide the excluded ones
          for (int i = 0; i < extents.size(); i++) {
            Extent<Integer> extent2 = extents.get(i);
            grouping.section.setHidden(extent2.getStart(), extent2.getEnd(), false);
          }
        }
        // Expand
        else {
          if (hasChildren()) {
            expand(level+1, this);
          }
        }

//        // Expand the parent when all its children are expanded
//        Node node = this.parent;
//        while (node != grouping.root) {
//          boolean allExpanded = true;
//          for (Node child: node.getChildren()) {
//            allExpanded = allExpanded && !child.collapsed;
//          }
//          if (allExpanded) {
//            node.collapsed = false;
//          }
//          node = node.parent;
//        }
//
//        // Collapse the children o


        grouping.matrix.refresh();
        if (focusItem != null) {
          grouping.axis.setFocusItem(focusItem);
        }
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

      if (state == false) {
        if (grouping != null) {
          grouping.section.setHidden(extent.getStart(), extent.getEnd(), false);
        }
        new NodeVisitor() {
          @Override
          protected void visitBefore(Node node) {
            node.collapsed = false;
          }
        }.traverse(this);
      }
      else {
        new NodeVisitor() {
          @Override
          protected void visitAfter(Node node) {
            node.setCollapsed(state);
          }
        }.traverse(this);
      }
      return this;
    }

    /**
     * Sets the collapse direction of this node to the given value.
     *
     * @param direction collapse direction to set
     * <p>
     * The possible values are {@link SWT#BEGINNING}, {@link SWT#END} and {@link SWT#NONE}
     * <li> With {@link SWT#BEGINNING} the first node is shown when node is collapsed
     * <li> With {@link SWT#END} the last node is shown when node is collapsed (<b>not implemented yet!</b>)
     * <li> With {@link SWT#NONE} the node cannot be collapsed
     * @return this node
     */
    public Node setCollapseDirection(int direction) {
      checkCollapseDirection(direction);

      if (direction == SWT.END) throw new UnsupportedOperationException("Not implemnted yet");
      if (direction == SWT.NONE) {
        collapsed = false;
      }
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
     * <p>
     * The possible values are {@link SWT#BEGINNING}, {@link SWT#END} and {@link SWT#NONE}
     * <li> With {@link SWT#BEGINNING} the first node is shown when node is collapsed
     * <li> With {@link SWT#END} the last node is shown when node is collapsed (<b>not implemented yet!</b>)
     * <li> With {@link SWT#NONE} the node cannot be collapsed
     * @return this node
     * @see #setCollapseDirection(int)
     */
    public Node setCollapseDirectionAll(final int direction) {
      checkCollapseDirection(direction);

      new NodeVisitor() {
        @Override
        protected void visitBefore(Node node) {
          node.setCollapseDirection(direction);
        }
      }.traverse(this);
      return this;
    }

    private void checkCollapseDirection(int direction) {
      Preconditions.checkArgument(direction == SWT.BEGINNING
          || direction == SWT.END || direction == SWT.NONE,
          "direction must be either SWT.BEGINNING or SWT.END or SWT.NONE");
    }

    /**
     * Returns <code>true</code> if this node is collapsed, or <code>false</code> otherwise.
     * @return <code>true</code> if this node is collapsed, or <code>false</code> otherwise
     */
    public boolean isCollapsed() {
      return collapsed;
    }

    Boolean getToggleState() {
      if (collapseDirection == SWT.NONE || children.size() <= 1) {
        return TOGGLE_NONE;
      }
      not_found: {
        for (int i = 1; i < children.size(); i++) {
          if (children.get(i).collapseDirection != SWT.NONE) {
            break not_found;
          }
        }
        return TOGGLE_NONE;
      }
      /*&& node.parent.collapsed == false*/
      return collapsed ? TOGGLE_EXPAND : TOGGLE_COLLAPSE;
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
     * @param node2
     * @param node parent for the children to traverse
     */
    protected void visitBefore(Node node) {}
    /**
     * Method executed after traversing children
     * @param node2
     * @param node parent for the children to traverse
     */
    protected void visitAfter(Node node) {}

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
      visitBefore(node);
      if (stop) return this;
      for (Node child: node.children) {
        traverse(child);
        if (stop || stopBranch) return this;
      }
      visitAfter(node);
      return this;
    }

    /**
     * Traverses starting from the given nodes.
     * @param nodes nodes to traverse
     * @return this object
     */
    public NodeVisitor traverse(List<Node> nodes) {
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
