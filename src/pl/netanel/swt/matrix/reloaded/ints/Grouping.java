/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/
package pl.netanel.swt.matrix.reloaded.ints;

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
  private int collapse;
  private int levelCount;
  private Axis<Integer> axis;
  private Section<Integer> section, section2;

  public Grouping(final Zone<Integer, Integer> zone, int axisDirection, Node root) {
    this.zone = zone;
    this.axisDirection = axisDirection;
    this.root = root;
    collapse = SWT.BEGINNING;
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

    // Create toggle image button painter
    final CellImageButtonPainter<Integer, Integer> togglePainter =
        new CellImageButtonPainter<Integer, Integer>(trueImage, falseImage)
    {

      @Override
      protected boolean init() {
        boolean result = super.init();
        return result;
      }
      @Override
      public Boolean getToggleState(Integer indexX, Integer indexY) {
        // Return true if is expanded
        Node node = getNode(indexX, indexY);
        return collapse != SWT.NONE && isFirstItem(node, indexX, indexY) &&
            node.children != null && node.children.length > 1 ?
            !node.collapsed : null;
      }
    };
    zone.addPainter(togglePainter);

    // Replace cell painter
    Painter<Integer, Integer> cellPainter = new Painter<Integer, Integer> (Painter.NAME_CELLS) {
      @Override
      public void setupSpatial(Integer indexX, Integer indexY) {
        super.setupSpatial(indexX, indexY);
        text = getText(indexX, indexY);
        togglePainter.setupSpatial(indexX, indexY);
        image = togglePainter.image;
      }
    };
    zone.replacePainter(cellPainter);

    cellPainter.style.textAlignY = SWT.CENTER;
    cellPainter.style.imageAlignX = togglePainter.style.imageAlignX = SWT.END;
    cellPainter.style.imageAlignY = togglePainter.style.imageAlignY = SWT.CENTER;
    cellPainter.style.imageMarginX = togglePainter.style.imageMarginX = 2;

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
        if (togglePainter.isOverImage(e.x, e.y)) {
          Integer indexX = itemX.getIndex();
          Integer indexY = itemY.getIndex();

          Node node = getNode(indexX, indexY);
          if (node.hasChildren()) {
            AxisItem<Integer> focusItem = axis.getFocusItem();
            if (node.collapsed) {
              expand(indexY+1, node);
            }
            else {
              // Collapse
              section.setHidden(node.extent.getStart() + 1, node.extent.getEnd(), true);
            }
            node.collapsed = !node.collapsed;
            zone.getMatrix().refresh();
            axis.setFocusItem(focusItem);
          }
        }
        else {
          zone.getMatrix().execute(commandId);
        }
      }

      private void expand(int startLevel, Node node) {
        if (node.hasChildren()) {
          for (Node child: node.children) {
            if (child.collapsed) {
              section.setHidden(child.extent.getStart(), false);
            }
            else {
              section.setHidden(child.extent.getStart(), child.extent.getEnd(), false);
            }
          }
        }
        else {
          section.setHidden(node.index, node.index, false);
        }
      }
    });
  }

  private void initNodes() {
    final int[] maxLevel = new int[] {0};
    final int[] index = new int[] {0};

    new NodeVisitor() {
      int level = -1;

      @Override
      void beforeChildren(Node node) {
        level++;
      }

      @Override
      void visit(Node node) {
        if (node.children == null) {
          node.extent = Extent.createUnchecked(index[0], index[0]);
          node.index = index[0]++;
        }
        node.level = level;
      }

      @Override
      void afterChildren(Node node) {
        maxLevel[0] = java.lang.Math.max(maxLevel[0], level);
        level--;
        if (node.children != null) {
          node.extent = Extent.createUnchecked(
              node.children[0].extent.getStart(),
              node.children[node.children.length-1].extent.getEnd());
        }
      }

    }.traverse(root);

    section.setCount(index[0]);
    section2.setCount(levelCount = maxLevel[0]+1);
  }

  public void setCollapse(int collapse) {
    this.collapse = collapse;
  }

  public String getText(Integer indexX, Integer indexY) {
    Node node = getNode(indexX, indexY);
    return node != null && isFirstItem(node, indexX, indexY) ?
        node.caption : null;
  }

  public void layout() {
    new NodeVisitor() {
      @Override void visit(Node node) {
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
      }

      @Override void beforeChildren(Node node) {}
      @Override void afterChildren(Node node) {}

    }.traverse(root.children);
  }

  private boolean isFirstItem(Node node, int indexX, int indexY) {
    if (node == null) return false;
    return node.extent.getStart() == (axisDirection == SWT.HORIZONTAL ? indexX : indexY);
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

  public Node getNode(int indexX, int indexY) {
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

  public static class Node {
    private final String caption;
    private final Node[] children;
    private int level, index;
    private Extent<Integer> extent;
    private boolean collapsed;

    public Node(String caption, Node ...children) {
      this.caption = caption;
      this.children = children;
    }

    public Node(String caption) {
      this.caption = caption;
      this.children = null;
    }

    public boolean hasChildren() {
      return children != null;
    }
  }

  public static abstract class NodeVisitor {
    abstract void beforeChildren(Node node);
    abstract void visit(Node node);
    abstract void afterChildren(Node node);

    public void traverse(Node node) {
      visit(node);
      if (node.children == null) return;
      beforeChildren(node);
      for (Node child: node.children) traverse(child);
      afterChildren(node);
    }
    public void traverse(Node[] nodes) {
      for (Node node: nodes) {
        traverse(node);
      }
    }

  }

}
