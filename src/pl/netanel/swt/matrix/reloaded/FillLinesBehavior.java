/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA.html
 ******************************************************************************/
package pl.netanel.swt.matrix.reloaded;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Frozen;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Zone;

public class FillLinesBehavior<X extends Number, Y extends Number>  {
  public boolean extendLinesX = true;
  public boolean extendLinesY = true;
  public boolean extraLinesX = true;
  public boolean extraLinesY;
  private final Matrix<X, Y> matrix;

  public FillLinesBehavior(final Matrix<X, Y> matrix) {
        
    
    this.matrix = matrix;
    Painter<X, Y> extraLinesPainter = new Painter<X, Y>("fill lines") {
      @Override
      protected void paint(int x, int y, int width, int height) {
        final Zone<X, Y> body = matrix.getBody();
        Rectangle bodyBounds = body.getBounds(Frozen.NONE, Frozen.NONE);
        Rectangle areaBounds = matrix.getClientArea();
        Painter<X, Y> painter2 = matrix.getBody().getPainter(Painter.NAME_LINES_X);
        if (painter2 != null) {
          gc.setBackground(painter2.style.background);
        }
        if (extraLinesX) {
          int cell = body.getSectionY().getDefaultCellWidth();
          int line = body.getSectionY().getDefaultLineWidth();
          int delta = cell + line;
          int length = extendLinesX ? areaBounds.width : bodyBounds.width;
          for (int distance = bodyBounds.y + bodyBounds.height - line; 
            distance < areaBounds.height; 
            distance += delta) {
            gc.fillRectangle(0, distance, length, line);
          }
        }
        if (extraLinesY) {
          int cell = body.getSectionX().getDefaultCellWidth();
          int line = body.getSectionX().getDefaultLineWidth();
          int delta = cell + line;
          int length = extendLinesY ? areaBounds.height : bodyBounds.height;
          for (int distance = bodyBounds.x + bodyBounds.width - line; 
            distance < areaBounds.width; 
            distance += delta) {
            gc.fillRectangle(distance, 0, line, length);
          }
        }
        gc.setBackground(matrix.getBackground());
      }
    };
    
    int index = matrix.indexOfPainter(Painter.NAME_FROZEN_NONE_NONE);
    if (index >= 0) {
      matrix.addPainter(index, extraLinesPainter);
    }
    
    
    
    Painter<X, Y> painterX = new LinePainter(Painter.NAME_LINES_X) {
      @Override
      public void paint(int x, int y, int width, int height) {
        super.paint(x, max = y, length = extendLinesX ? clientArea.width : width, height);
      }
    };
    
    Painter<X, Y> painterY = new LinePainter(Painter.NAME_LINES_Y) {
      @Override
      public void paint(int x, int y, int width, int height) {
        super.paint(max = x, y, width, length = extendLinesY ? clientArea.height : height);
      }
    };
    
    final Zone<X, Y> body = matrix.getBody();
    body.replacePainter(painterX);
    body.replacePainter(painterY);
  }

  class LinePainter extends Painter<X, Y> {
    Rectangle clientArea;
    int max, length;
    
    public LinePainter(String name) {
      super(name);
    }
    
    @Override
    protected boolean init() {
      clientArea = matrix.getClientArea();
      gc.setBackground(style.background);
      return true;
    }
    
    @Override public void paint(int x, int y, int width, int height) {
      gc.fillRectangle(x, y, width, height);
    }
  }
  
 
  public static void main(String[] args) {
    Shell shell = new Shell();
    shell.setLayout(new FillLayout());

    
    final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);
    matrix.getAxisX().getBody().setCount(4);
    matrix.getAxisY().getBody().setCount(10);
    matrix.getAxisY().getHeader().setVisible(true);

    FillLinesBehavior<Integer, Integer> behavior = new FillLinesBehavior<Integer, Integer>(matrix);
    behavior.extendLinesX = true;
    behavior.extendLinesY = true;
    behavior.extraLinesX = true;
    behavior.extraLinesY = true;
    
    shell.setBounds(400, 200, 400, 300);
    shell.open();
    Display display = shell.getDisplay();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }
}
