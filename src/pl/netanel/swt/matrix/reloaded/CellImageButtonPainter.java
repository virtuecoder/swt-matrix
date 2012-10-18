/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/
package pl.netanel.swt.matrix.reloaded;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.AxisItem;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Zone;
import pl.netanel.util.Preconditions;

/**
 * Paints an image in the cell.
 * Get cell indexes when given coordinates are inside the cell image.
 * Toggle image
 */
public class CellImageButtonPainter<X extends Number, Y extends Number> extends Painter<X, Y> {

  private Image trueImage;
  private Image falseImage;
  private Rectangle imageBounds;

  /**
   * Creates toggle button behavior and displaying trueImage
   * when {@link #getToggleState(Number, Number)} returns {@link Boolean#TRUE},
   * <code>falseImage</code> when {@link Boolean#FALSE}
   * and nothing when it returns <code>null</code>.
   *
   * @param name name of the painter
   * @param trueImage image to display when the toggle is set to true
   * @param falseImage image to display when the toggle is set to false
   */
  public CellImageButtonPainter(String name, Image trueImage, Image falseImage) {
    super(name, Painter.SCOPE_CELLS);

    Preconditions.checkNotNullWithName(trueImage, "trueImage");
    Preconditions.checkNotNullWithName(falseImage, "falseImage");
    // Images must have the same sizes

    setToggleImages(trueImage, falseImage);
  }

  /**
   * Creates push button behavior using the given image as the button.
   * @param name name of the painter
   * @param trueImage image to emulate the button
   */
  public CellImageButtonPainter(String name, Image buttonImage) {
    super(name, Painter.SCOPE_CELLS);

    Preconditions.checkNotNullWithName(buttonImage, "buttonImage");
    image = buttonImage;
    imageBounds = image.getBounds();

    style.imageAlignX = SWT.RIGHT;
    style.imageMarginX = style.imageMarginY = 2;
  }

  public void setToggleImages(Image trueImage, Image falseImage) {
    this.trueImage = trueImage;
    this.falseImage = falseImage;

    image = trueImage == null ? falseImage : trueImage;
    imageBounds = image.getBounds();
  }

  /**
   * Returns a three state toggle status of the given cell with the following consequences:
   * <li> null - no image displayed
   * <li> true - trueImage displayed
   * <li> false - falseImage displayed
   * @param indexX
   * @param indexY
   * @return
   */
  public Boolean getToggleState(X indexX, Y indexY) {
    return true;
  }

  /**
   * Overrides the default method setting the image related to toggle state.
   */
  @Override
  public void setupSpatial(X indexX, Y indexY) {
    super.setupSpatial(indexX, indexY);
    if (trueImage != null) {
      Boolean toggle = getToggleState(indexX, indexY);
      image =
          toggle == null         ? null :
          toggle == Boolean.TRUE ? trueImage :
                                   falseImage;
    }
  };

  /**
   * Returns true if coordinates are over the image of drawn by this painter,
   * or false otherwise.
   * It is assumed the coordinates are within the last cell the mouse was over,
   * i.e. come from the mouse event (see {@link Axis#getMouseItem()})
   *
   * @param x x coordinate relative to the display
   * @param y y coordinate relative to the display
   * @return true if coordinates are over the image of drawn by this painter
   */
  public boolean isOverImage(int x, int y) {
    // Get cell bounds
    Zone<X, Y> zone = getZone();
    AxisItem<X> itemX = zone.getMatrix().getAxisX().getMouseItem();
    if (itemX == null) return false;
    AxisItem<Y> itemY = zone.getMatrix().getAxisY().getMouseItem();
    if (itemY == null) return false;
    Rectangle cellBounds = zone.getCellBounds(itemX.getIndex(), itemY.getIndex());

    // Compute image position
    int imageX = align(style.imageAlignX, style.imageMarginX, imageBounds.width, cellBounds.width);
    int imageY = align(style.imageAlignY, style.imageMarginY, imageBounds.height, cellBounds.height);
    imageX += cellBounds.x;
    imageY += cellBounds.y;

    return imageX <= x  && x <= imageX + imageBounds.width &&
        imageY <= y && y <= imageY + imageBounds.height;
  }

}
