/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/
package pl.netanel.swt.matrix.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import pl.netanel.swt.matrix.AxisItem;
import pl.netanel.swt.matrix.Extent;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Zone;

/**
 * Groups items with parent header merged across its children.
 * Toggle button to collapse/expand groups.
 */
public class Groups {
  private final Zone<Integer, Integer> zone;
  private final String[][] text;
  private Extent<Integer>[][] children;
  private boolean[][] collapsed;

//  private Extent<Integer>[][] groupExtents;
  Image trueImage, falseImage;
  private int collapse;

  public Groups(final Zone<Integer, Integer> zone, int axisDirection, String[][] text) {
    this.zone = zone;
    this.text = text;

    zone.getSectionY().setCount(text.length);
    initChildren();

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
        int index = getChildrenIndex(indexX, indexY);
        Extent<Integer> extent = getAbsoluteExtent(indexY, index);
        return collapse != SWT.NONE && indexX == extent.getStart() && extent.getEnd() - extent.getStart() > 0 ?
            !collapsed[indexY][index] : null;
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

    cellPainter.style.imageAlignX = togglePainter.style.imageAlignX = SWT.END;
    cellPainter.style.imageAlignY = togglePainter.style.imageAlignY = SWT.CENTER;
    cellPainter.style.imageMarginX = togglePainter.style.imageMarginX = 2;

    // Create toggle listener
    zone.addListener(SWT.MouseDown, new Listener() {
      @Override
      public void handleEvent(Event e) {
        AxisItem<Integer> itemX = zone.getMatrix().getAxisX().getMouseItem();
        AxisItem<Integer> itemY = zone.getMatrix().getAxisY().getMouseItem();
        if (itemX != null && itemY != null && togglePainter.isOverImage(e.x, e.y)) {
          Integer indexX = itemX.getIndex();
          Integer indexY = itemY.getIndex();

          int index = getChildrenIndex(indexX, indexY);
          Extent<Integer> extent = getAbsoluteExtent(indexY, index);
          if (extent.getEnd() - extent.getStart() > 0) {
            boolean isCollapsed = collapsed[indexY][index];
            if (isCollapsed) {
              expand(indexY+1, children[indexY][index]);
            }
            else {
              // Collapse
              zone.getSectionX().setHidden(extent.getStart() + 1, extent.getEnd(), true);
            }
            collapsed[indexY][index] = !isCollapsed;
            zone.getMatrix().refresh();
          }
        }
      }

      private void expand(int startLevel,  Extent<Integer> extent) {
        if (startLevel == children.length) {
          zone.getSectionX().setHidden(extent.getStart(), extent.getEnd(), false);
        }
        else {
          for (int level = startLevel; level < children.length; level++) {
            for (int index = extent.getStart(); index <= extent.getEnd(); index++) {
              Extent<Integer> extent2 = children[level][index];
              if (extent2.getEnd() - extent2.getStart() == 0) continue;
              if (collapsed[level][index]) {
                zone.getSectionX().setHidden(extent2.getStart(), false);
              }
              else {
                zone.getSectionX().setHidden(extent2.getStart(), extent2.getEnd(), false);
              }
            }
          }
        }
      }
    });
  }

  @SuppressWarnings("unchecked")
  private void initChildren() {
 // Initialize children collections
    children = new Extent[text.length-1][];
    collapsed = new boolean[text.length-1][];
    for (int level = 0; level < text.length - 1; level++) {
      String[] levelText = text[level];
      children[level] = new Extent[levelText.length];
      collapsed[level] = new boolean[levelText.length];
    }


//    // Map group index to its first column index
//    groupExtents = new Extent[text.length][];
//    for (int level = text.length - 1; level-- > 0;) {
//      String[] levelText = text[level];
//      groupExtents[level] = new Extent[levelText.length];
//    }
//
//    for (int level = groupExtents.length - 1; level-- > 0;) {
//      groupExtents[level]
//    }
  }


  public void setCollapse(int collapse) {
    this.collapse = collapse;
  }


  public Group get(int level, int index) {
    return new Group(level, index);
  }

  public String getText(Integer indexX, Integer indexY) {
    int index = getChildrenIndex(indexX, indexY);
    Extent<Integer> extent = getAbsoluteExtent(indexY, index);
    return indexX == extent.getStart() ? text[indexY][index] : null;
  }

  public void layout() {
    for (int level = children.length; level-- > 0;) {
      Extent<Integer>[] extents = children[level];
      for (int index = 0; index < extents.length; index++) {
        Extent<Integer> extent = getAbsoluteExtent(level, index);
        boolean hasMultipleChildren = extent.getEnd() - extent.getStart() > 0;
        if (hasMultipleChildren) {
          zone.setMerged(extent.getStart(), extent.getEnd() - extent.getStart() + 1,
              level, 1, true);
        }
      }
    }
  }

  private int getChildrenIndex(int indexX, int indexY) {
    if (indexY == text.length - 1) {
      return indexX;
    }
    else {
      int x = indexX;
      for (int level = children.length; level-- > indexY;) {
        Extent<Integer>[] extents = children[level];
        for (int index = 0; index < extents.length; index++) {
          Extent<Integer> extent = extents[index];
          if (extent.getStart() <= x && x <= extent.getEnd()) {
            x = index;
            break;
          }
          //x += extent.getEnd() - extent.getStart() + 1;
        }
      }
      return x;
    }
  }

  private Extent<Integer> getAbsoluteExtent(int level, int index) {
    if (level == text.length - 1) {
      return Extent.create(index, index);
    }
    else {
      Extent<Integer> extent = children[level][index];
      if (level == text.length - 2) {
        return extent;
      }
      else {
        Extent<Integer> extent1 = getAbsoluteExtent(level+1, extent.getStart());
        Extent<Integer> extent2 = getAbsoluteExtent(level+1, extent.getEnd());
        return Extent.create(extent1.getStart(), extent2.getEnd());
      }
    }
  }

  private void createImages(int axisDirection) {
    Display display = zone.getMatrix().getDisplay();
    int x = 6, y = 8;
    trueImage = new Image(display, x, y);
    falseImage = new Image(display, x, y);

    if (axisDirection == SWT.HORIZONTAL) {
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


  public class Group {
    int level, index;

    public Group(int level, int index) {
      this.level = level;
      this.index = index;
    }

    public void addChildren(int start, int end) {
      children[level][index] = Extent.create(start, end);
    }
  }



}
