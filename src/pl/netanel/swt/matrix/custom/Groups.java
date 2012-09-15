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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

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
//  private Extent<Integer>[][] groupExtents;
  Image trueImage, falseImage;

  public Groups(Zone<Integer, Integer> zone, int axisDirection, String[][] text) {
    this.zone = zone;
    this.text = text;

    zone.getSectionY().setCount(text.length);
    initChildren();
    createImages(axisDirection);

    // Replace cell painter
    zone.replacePainter(new Painter<Integer, Integer> (Painter.NAME_CELLS) {
      @Override
      public void setupSpatial(Integer indexX, Integer indexY) {
        super.setupSpatial(indexX, indexY);
        text = getText(indexX, indexY);
      }
    });

    // Create toggle image button painter
    CellImageButtonPainter<Integer, Integer> painter =
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
        return null;
      }
    };
    painter.style.imageAlignX = SWT.END;
    painter.style.imageAlignY = SWT.CENTER;
    zone.addPainter(painter);
  }

  public void setCollapse(int beginning) {
  }


  public Group get(int level, int index) {
    return new Group(level, index);
  }

  public String getText(Integer indexX, Integer indexY) {
    if (indexY == text.length - 1) {
      return text[indexY][indexX];
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
      return text[indexY][x];
    }
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

    zone.getMatrix().addListener(SWT.Dispose, new Listener() {
      @Override
      public void handleEvent(Event e) {
        if (trueImage != null) trueImage.dispose();
        if (falseImage != null) falseImage.dispose();
      }
    });
  }

  @SuppressWarnings("unchecked")
  private void initChildren() {
 // Initialize children collections
    children = new Extent[text.length-1][];
    for (int level = 0; level < text.length - 1; level++) {
      String[] levelText = text[level];
      children[level] = new Extent[levelText.length];
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
