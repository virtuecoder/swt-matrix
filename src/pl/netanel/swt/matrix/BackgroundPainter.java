/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

class BackgroundPainter<X extends Number, Y extends Number>  extends Painter<X, Y> {

  public BackgroundPainter() {
    super(Painter.NAME_BACKGORUND, Painter.SCOPE_ENTIRE);
  }

  @Override
  protected boolean init() {
    if (style.background != null) {
      gc.setBackground(style.background);
      return true;
    }
    return false;
  }
  
  @Override public void paint(int x, int y, int width, int height) {
    gc.fillRectangle(x, y, width, height);
  }
}