/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA.html
 ******************************************************************************/
package pl.netanel.swt.matrix.reloaded.ints;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
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
import pl.netanel.swt.matrix.NumberSet;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.Zone;
import pl.netanel.swt.matrix.reloaded.CellImageButtonPainter;
import pl.netanel.util.NotNull;
import pl.netanel.util.Preconditions;

/**
 * Manages collapse-able hierarchy of item groups.
 * Parent nodes span across its children.
 * There are toggle buttons to collapse/expand groups.
 */
public class Grouping {

  private static final Boolean TOGGLE_EXPAND = false;
  private static final Boolean TOGGLE_COLLAPSE = true;
  private static final Boolean TOGGLE_NONE = null;

  private static final String SEPARATOR_PAINTER_NAME = "separator";

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
  private Integer selectLevel = 0;
  private Painter<Integer, Integer> oldCellPainter;
  private Listener selectItemListener;
  private NumberSet<Integer> hidden;
  private boolean isBulkCollapse;
  private AxisItem<Integer> focusItem;
  private Listener disposeListener;
  private int[] backupLineWidths;


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
    section.addHiddenSet(hidden = axis.createNumberSet());

    createVisitors();
    initNodes();
    layout();

    createImages(axisDirection);
    disposeListener = new Listener() {
      @Override
      public void handleEvent(Event e) {
        dispose();
      }
    };
    zone.getMatrix().addListener(SWT.Dispose, disposeListener);

    oldCellPainter = zone.getPainter(Painter.NAME_CELLS);
    CellImageButtonPainter<Integer, Integer> cellPainter = new CellPainter();
    zone.replacePainter(cellPainter);

    cellPainter.style.textAlignY = SWT.CENTER;
    cellPainter.style.imageAlignX = SWT.END;
    cellPainter.style.imageAlignY = SWT.CENTER;
    cellPainter.style.imageMarginX = matrix.getAxisX().getResizeOffset();

    zone.addPainter(new SeparatorPainter(axisDirection));

    // Create toggle and selection listener
    //zone.unbind(Matrix.CMD_FOCUS_LOCATION, SWT.MouseDown, 1);
    final int commandId = axisDirection == SWT.HORIZONTAL ? Matrix.CMD_SELECT_COLUMN : Matrix.CMD_SELECT_ROW;
    zone.unbind(commandId, SWT.MouseDown, 1);
    selectItemListener = new Listener() {
      @Override
      public void handleEvent(Event e) {
        AxisItem<Integer> itemX = zone.getMatrix().getAxisX().getMouseItem();
        AxisItem<Integer> itemY = zone.getMatrix().getAxisY().getMouseItem();
        if (itemX == null || itemY == null) return;

        Integer indexX = itemX.getIndex();
        Integer indexY = itemY.getIndex();
        Node node = getNode(indexX, indexY);
        if (node == null) return;

        // toggle
        Painter<Integer, Integer> painter = zone.getPainter(Painter.NAME_CELLS);
        CellImageButtonPainter<Integer, Integer> cellPainter = null;
        if (painter instanceof CellImageButtonPainter) {
          cellPainter = (CellImageButtonPainter<Integer, Integer>) painter;
        }
        if (node.getToggleState() != TOGGLE_NONE && cellPainter != null &&
            cellPainter.isOverImage(e.x, e.y))
        {
          node.setCollapsed(!node.isCollapsed);
        }
        else {
          Cursor cursor = matrix.getCursor();
          if (cursor != null && (
              cursor.equals(matrix.getDisplay().getSystemCursor(SWT.CURSOR_SIZENS)) ||
              cursor.equals(matrix.getDisplay().getSystemCursor(SWT.CURSOR_SIZEWE)))) return;
          AxisItem<Integer> item = axis2.getMouseItem();
          selectLevel = item == null ? levelCount : item.getIndex();
//          section.setSelected(false);
//          axis.setFocusItem(axisDirection == SWT.HORIZONTAL ? itemX : itemY);
          matrix.execute(commandId);
          section.setSelected(node.extent.getStart(), node.extent.getEnd(), true);
          matrix.redraw();
        }
      }
    };
    zone.addListener(SWT.MouseDown, selectItemListener);
  }

  /**
   * Disposes the grouping.
   * <ul>
   * <li>Reverts back the old cell painter</li>
   * <li>Removes the separator painter</li>
   * <li>Removes cell merging</li>
   * <li>Removes cell merging</li>
   * <li>Sets the number of levels to 1</li>
   * <li>Sets the line widths to default value</li>
   * <li>Brings back the normal item selection handler</li>
   * </ul>
   */
  public void dispose() {
    if (trueImage != null) trueImage.dispose();
    if (falseImage != null) falseImage.dispose();

    if (matrix.isDisposed()) return;
    matrix.removeListener(SWT.Dispose, disposeListener);

    zone.replacePainter(oldCellPainter);
    zone.removePainter(SEPARATOR_PAINTER_NAME);
    zone.setMerged(0, zone.getSectionX().getCount(), 0, zone.getSectionY().getCount(), false);

    final int commandId = axisDirection == SWT.HORIZONTAL ? Matrix.CMD_SELECT_COLUMN : Matrix.CMD_SELECT_ROW;
    zone.bind(commandId, SWT.MouseDown, 1);
    zone.removeListener(SWT.MouseDown, selectItemListener);

    for (int i = 0; i < section.getCount(); i++) {
      section.setLineWidth(i, section.getDefaultLineWidth());
    }
    section2.setCount(1);
    section.removeHiddenSet(hidden);
  }

//  Separator separator;
//  Node parent;
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
        node.remain = node.grouping.axis.createNumberSet();
        parent = node;
        level++;
      }

      @Override
      protected void visitAfter(Node node) {
        maxLevel[0] = java.lang.Math.max(maxLevel[0], level);
        parent = node.parent;
        level--;

        // Group node
        if (node.hasChildren()) {
          Node firstChild = node.children.get(0);
          Node lastChild = node.children.get(node.children.size()-1);
          node.extent = Extent.createUnchecked( firstChild.extent.getStart(), lastChild.extent.getEnd());

          // Find the children to remain
          int count = 0;
          for (Node child: node.children) {
            if (child.isRemain || child.isSummary || child.isPermanent || node.isPermanent) {
              addRemain(node, child);
              count++;
            }
          }

          if (count == node.children.size()) {
            node.isPermanent = true;
          }

          // Make the first child to remain by default if nothing was set to remain
          if (node.remain.isEmpty()) {
            firstChild.isRemain = true;
            addRemain(node, firstChild);
          }
        }

        if (node.isSummary) {
          Grouping.this.hidden.add(node.extent.getStart(), node.extent.getEnd());
        }
        node.extentCount = node.extent.getEnd() - node.extent.getStart() + 1;
      }

      private void addRemain(Node node, Node child) {
        if (child.hasChildren()) {
          node.remain.addAll(child.remain);
        }
        else {
          Integer index = child.extent.getStart();
          node.remain.add(index);
          Node node2 = node.parent;
          while (node2 != root) {
            node2.remain.add(index);
            node2 = node2.parent;
          }
        }
      }
    }.traverse(root.children);

    section.setCount(index[0]);
    section2.setCount(levelCount = maxLevel[0]);
    selectLevel = index[0];
    backupLineWidths = new int[index[0] + 1];
    for (int i = 0; i < backupLineWidths.length; i++) {
      backupLineWidths[i] = section.getLineWidth(i);
    }

    root.grouping = this;
    root.extent = Extent.createUnchecked(0, section.getCount()-1);

    setSeparatorLines();
  }

  private void setSeparatorLines() {
    for (int i = 0; i <= section.getCount(); i++) {
      section.setLineWidth(i, backupLineWidths[i]);
    }
    new NodeVisitor() {
      @Override
      protected void visitBefore(Node node) {
        if (node.separatorLineWidth != -1) {
          int lineIndex = node.extent.getEnd() + 1;

          // Set separator line width in the first visible child of the next node
          int nodeIndex = node.parent.children.indexOf(node);
          if (nodeIndex < node.parent.children.size() - 1) {
            Node nextNode = node.parent.children.get(nodeIndex + 1);
            for (int i = nextNode.extent.getStart(); i <= nextNode.extent.getEnd(); i++) {
              if (!section.isHidden(i)) {
                lineIndex = i;
                break;
              }
            }
          }
          section.setLineWidth(lineIndex, node.separatorLineWidth);
          for (int i = node.children.size(); i-- > 0;) {
            Node child = node.children.get(i);
            for (int j = child.extent.getEnd(); j >= child.extent.getStart(); j--) {
              if (!section.isHidden(j) && child.separatorLineWidth != -1) {
                section.setLineWidth(j + 1, child.separatorLineWidth);
                break;
              }
            }
          }
        }
      };
    }.traverse(root.children);
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
    Node node = getNode(indexX, indexY);
    return node != null  ?
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

        // Merge horizontal
        if (axisDirection == SWT.HORIZONTAL) {
          if (node.hasChildren()) {
            zone.setMerged(node.extent.getStart(), node.extent.getEnd() - node.extent.getStart() + 1,
                node.level, 1, true);
          }
          else if (node.level < levelCount - 1) {
            zone.setMerged(node.index, 1, node.level, levelCount - node.level, true);
          }
        }

        // Merge vertical
        else {
          if (node.hasChildren()) {
            zone.setMerged( node.level, 1,
                node.extent.getStart(), node.extent.getEnd() - node.extent.getStart() + 1, true);
          }
          else if (node.level < levelCount - 1) {
            zone.setMerged(node.level, levelCount - node.level, node.index, 1, true);
          }
        }

        // Apply collapsed state set before Grouping creation
        if (node.isCollapsed) {
          node.isCollapsed = false;
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

//  void createImages2(int axisDirection) {
//    Display display = zone.getMatrix().getDisplay();
//    trueImage = new Image(display, 9, 9);
//    falseImage = new Image(display, 9, 9);
//
//    if (axisDirection == SWT.HORIZONTAL) {
//      GC gc = new GC(trueImage);
//      gc.setAntialias(SWT.ON);
//      gc.setForeground(display.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
//      gc.drawRectangle(0, 0, 8, 8);
//      gc.setForeground(display.getSystemColor(SWT.COLOR_WIDGET_FOREGROUND));
//      // Draw <<
//      gc.drawLine(2, 4, 4, 2);
//      gc.drawLine(4, 4, 6, 2);
//      gc.drawLine(2, 4, 4, 6);
//      gc.drawLine(4, 4, 6, 6);
//      gc.dispose();
//
//      gc = new GC(falseImage);
//      gc.setAntialias(SWT.ON);
//      gc.setForeground(display.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
//      gc.drawRectangle(0, 0, 8, 8);
//      gc.setForeground(display.getSystemColor(SWT.COLOR_WIDGET_FOREGROUND));
//      // Draw >>
//      gc.drawLine(2, 2, 4, 4);
//      gc.drawLine(4, 2, 6, 4);
//      gc.drawLine(2, 6, 4, 4);
//      gc.drawLine(4, 6, 6, 4);
//      gc.dispose();
//    }
//    else {
//
//    }
//  }

  private Image setWhiteAsTransparent(Image image) {
    ImageData imageData = image.getImageData();
    int whitePixel = imageData.palette.getPixel(new RGB(255,255,255));
    imageData.transparentPixel = whitePixel;
    return new Image(zone.getMatrix().getDisplay(), imageData);
  }

  /**
   * Returns the grouping node of the given cell.
   * @param indexX cell index on the horizontal axis
   * @param indexY cell index on the vertical axis
   * @return grouping node in the given cell
   * @throws
   */
  @NotNull
  private Node getNode(int indexX, int indexY) {
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
          else {
            node = child;
          }
        }
      }
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
        "There is no node for cell ({0}, {1})", indexX, indexY));
  }

  @NotNull
  public Node getNodeByCellIndex(int indexX, int indexY) {
    if (axisDirection == SWT.HORIZONTAL) {
      Preconditions.checkElementIndex(indexX, section.getCount());
      Preconditions.checkElementIndex(indexY, levelCount);
    } else {
      Preconditions.checkElementIndex(indexY, section.getCount());
      Preconditions.checkElementIndex(indexX, levelCount);
    }
    return getNode(indexX, indexY);
  }

  public Node getNodeByTreeIndex(final int ...index) {
    Node node  = root;
    for (int i = 0; i < index.length; i++) {
      node = node.children.get(index[i]);
    }
    return root.equals(node) ? null : node;
  }

  public void setToggleImages(Image collapseImage, Image expandImage) {
    Painter<Integer, Integer> painter = zone.getPainter(Painter.NAME_CELLS);
    if (painter instanceof CellImageButtonPainter) {
      ((CellImageButtonPainter<Integer, Integer>) painter).setToggleImages(collapseImage, expandImage);
    }
  }


  public class CellPainter extends CellImageButtonPainter<Integer, Integer> {

    public CellPainter() {
      super(Painter.NAME_CELLS, trueImage, falseImage);
    }

    @Override
    protected boolean init() {
      boolean result = super.init();
      return result;
    }
    @Override
    public Boolean getToggleState(Integer indexX, Integer indexY) {
      // Return true if is expanded
      Node node = getNode(indexX, indexY);
      return node == null ? null : node.getToggleState();
//      return node.collapseDirection != SWT.NONE && isFirstItem(node, indexX, indexY) &&
//          node.children.size() > 1 /*&& node.parent.collapsed == false*/ ?
//              !node.collapsed : null;
    }

    @Override
    public void setupSpatial(Integer indexX, Integer indexY) {
      super.setupSpatial(indexX, indexY);
      text = getText(indexX, indexY);
    }

    @Override
    public void setup(Integer indexX, Integer indexY) {
      super.setup(indexX, indexY);
      AxisItem<Integer> item = axis2.getItemByViewportDistance(
          axisDirection == SWT.HORIZONTAL ? indexY : indexX);
      if (item != null) {
        Node node = getNode(indexX, indexY);
        if (node != null && node.level < selectLevel && isFirstItem(node, indexX, indexY)) {
          isSelected = false;
        }
      }
    }
  }

  public class SeparatorPainter extends Painter<Integer, Integer> {
    private Color background;
    private int lineWidth;
    private final int axisDirection;
    private Node node;
    private int contentSize;

    public SeparatorPainter(int axisDirection) {
      super(SEPARATOR_PAINTER_NAME, Painter.SCOPE_CELLS);
      this.axisDirection = axisDirection;
    }

    @Override
    protected boolean init() {
      int count = axis2.getViewportItemCount();
      if (count == 0) contentSize = 0; else {
        AxisItem<Integer> lastCell = axis2.getItemByViewportPosition(count-1);
        if (lastCell == null) contentSize = 0;
        else {
//          int[] bound = axis2.getLineBound(
//              AxisItem.create(lastCell.getSection(), lastCell.getIndex() + 1));
//          contentSize = bound == null ? 0 : bound[0] + bound[1];
          contentSize = axis2.getContentWidth();
        }
      }
      return super.init();
    }

    @Override
    public void setup(Integer indexX, Integer indexY) {
      node = getNode(indexX, indexY);
      if (node == null) {
        background = null;
        return;
      }
      background = node.lineColor;
      lineWidth = node.separatorLineWidth;
    }

    @Override
    protected void paint(int x, int y, int width, int height) {
      if (background == null) return;
      gc.setBackground(background);
      if (axisDirection == SWT.HORIZONTAL) {
        x += width;
        width = lineWidth;
        height = contentSize - y;
      } else {
        y += height;
        height = lineWidth;
        width = contentSize - x;
      }
      gc.fillRectangle(x, y, width, height);
    }
  }

  /**
   * Represent a node in the grouping hierarchy.
   * Implements Builder pattern.
   */
  public static class Node {

    /**
     * Makes the node to stay visible when the parent is collapsed.
     */
    public final static int REMAIN = 1 << 0;
    /**
     * The node is visible only when node is collapsed
     */
    public final static int SUMMARY = 1 << 1;
    /**
     * Makes the node not collapse-able.
     */
    public final static int PERMANENT = 1 << 2;
    /**
     * the node is initially collapsed
     */
    public final static int COLLAPSED = 1 << 3;

    private final String caption;
    private final List<Node> children;
    private NumberSet<Integer> remain;
    private Node parent;
    private int level = -1, index;
    private Extent<Integer> extent;
    private int extentCount;
    private boolean isCollapsed;
    Grouping grouping;
    private boolean isRemain;
    private boolean isPermanent;
    private boolean isSummary;
    protected int separatorLineWidth = -1;
    protected Color lineColor;

    public Node(String caption, Node ...children) {
      this.caption = caption;
      this.children = new ArrayList<Node>();
      this.children.addAll(Arrays.asList(children));
    }

    public Node(String caption, int options, Node ...children) {
      // Check options
      Preconditions.checkArgument((options & SUMMARY) == 0 || (options & REMAIN) == 0,
          "the node cannot remain and be a summary at the same time");
      Preconditions.checkArgument((options & PERMANENT) == 0 || (options & REMAIN) == 0,
          "the node cannot be permament and summary at the same time");
      Preconditions.checkArgument(options <= (REMAIN | SUMMARY | PERMANENT | COLLAPSED),
          "Uknown options");

      if ((options & REMAIN) != 0 ) isRemain = true;
      if ((options & SUMMARY) != 0 ) isSummary = true;
      if ((options & PERMANENT) != 0 ) isPermanent = true;
      if ((options & COLLAPSED) != 0 ) isCollapsed = true;

      this.caption = caption;
      this.children = new ArrayList<Node>();
      this.children.addAll(Arrays.asList(children));
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
     * @param newState state to set for <code>collapsed</code> property
     * @return this node
     */
    public Node setCollapsed(boolean newState) {
      if (isPermanent || children.size() < 1 || grouping != null && this == grouping.root) return this;
      isCollapsed = newState;
      if (grouping != null) {
        grouping.saveFocusItem();

        // Collapse
        if (newState == true) {
          grouping.hidden.add(extent.getStart(), extent.getEnd());
          grouping.hidden.removeAll(remain);
        }
        // Expand
        else {
          expand();
        }

        grouping.restoreFocusItem();
      }
      return this;
    }

    private void expand() {
      // Group node
      if (hasChildren()) {
        for (Node child: children) {
          if (child.isCollapsed) {
            grouping.hidden.removeAll(child.remain);
          }
          else {
            if (child.isSummary) {
              grouping.hidden.add(child.extent.getStart(), child.extent.getEnd());
            }
            else {
              child.expand();
            }
          }
        }
      }
      // Leaf node
      else {
        grouping.hidden.remove(index, index);
      }
    }


    /**
     * Sets the collapsed state of this node
     * and all related nodes on the lower level to the given value.
     *
     * @param newState state to set for <code>collapsed</code> property
     * @return this node
     */
    public Node setCollapsedAll(final boolean newState) {

      // Expand
      if (newState == false) {
        if (grouping != null) {
          grouping.hidden.remove(extent.getStart(), extent.getEnd());
        }
        new NodeVisitor() {
          @Override
          protected void visitBefore(Node node) {
            node.isCollapsed = false;
            if (grouping != null && node.isSummary) {
              grouping.hidden.add(node.extent);
            }
          }
        }.traverse(this);
      }
      // Collapse
      else {
        grouping.saveFocusItem();
        grouping.isBulkCollapse = true;
        new NodeVisitor() {
          @Override
          protected void visitAfter(Node node) {
            node.setCollapsed(newState);
          }
        }.traverse(this);
        grouping.isBulkCollapse = false;
        grouping.restoreFocusItem();
      }
      return this;
    }


    /**
     * Returns <code>true</code> if this node is collapsed, or <code>false</code> otherwise.
     * @return <code>true</code> if this node is collapsed, or <code>false</code> otherwise
     */
    public boolean isCollapsed() {
      return isCollapsed;
    }

    Boolean getToggleState() {
      if (isPermanent || children.size() <= 1 ||
          // less then two not hidden items to display
          // TODO Replace with (all hidden - grouping.hidden)
          grouping.section.getDefaultHiddenSet().getCount(extent.getStart(), extent.getEnd()) >= extentCount - 1)
      {
        return TOGGLE_NONE;
      }
      something_to_collapse: {
        for (int i = 0; i < children.size(); i++) {
          Node node = children.get(i);
          if (!node.isRemain) {
            break something_to_collapse;
          }
        }
        return TOGGLE_NONE;
      }
      /*&& node.parent.collapsed == false*/
      return isCollapsed ? TOGGLE_EXPAND : TOGGLE_COLLAPSE;
    }

    /**
     * Defines the line at the end of the current node
     * @param lineWidth
     * @param lineColor
     * @return this node
     */
    public Node separator(int lineWidth, Color lineColor) {
      this.separatorLineWidth = lineWidth;
      this.lineColor = lineColor;
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
     * Stops the traversing if set to <code>true</code>
     */
    protected boolean stop;

    /**
     * Stops traversing deeper into the current branch if set to <code>true</code>
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
      if (stop || stopBranch) return this;
      for (Node child: node.children) {
        traverse(child);
        if (stop) return this;
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
        if (stop) break;
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

  void saveFocusItem() {
    if (!isBulkCollapse) {
      focusItem = axis.getFocusItem();
    }
  }

  void restoreFocusItem() {
    if (!isBulkCollapse) {
      setSeparatorLines();
      matrix.refresh();
      if(focusItem != null) {
        axis.setFocusItem(focusItem);
      }
    }

  }
}
