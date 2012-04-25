/*******************************************************************************
 * Copyright (c) 2008-2011 by Marcin Kuszczak. All rights reserved.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/

package usecase;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.AxisItem;
import pl.netanel.swt.matrix.Frozen;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Zone;

/**
 * This class is another usage of decorator pattern.
 * 
 * @author marcin
 */
public class GuiTable1 extends Composite {

  private Matrix<Integer, Integer> matrix;
  private static String[] allCols;
  private static Object[][] values;

  public GuiTable1(Composite parentComposite, int style) {
    super(parentComposite, style);
    setLayout(new FillLayout());
    init();
  }

  private void init() {

    matrix = new Matrix<Integer, Integer>(this, SWT.V_SCROLL | SWT.H_SCROLL);

    matrix.getAxisX().getBody().setCount(allCols.length);
    matrix.getAxisY().getBody().setCount(values.length);

    matrix.getAxisX().getHeader().setVisible(true);
    matrix.getAxisY().getHeader().setVisible(true);

    matrix.getAxisX().getBody().setDefaultMoveable(true);

    matrix.addControlListener(new ControlAdapter() {
      @Override
      public void controlResized(ControlEvent e) {
        resize();
        matrix.refresh();
      }
    });

    final Zone<Integer, Integer> body = matrix.getBody();
    body.getPainter(Painter.NAME_LINES_X).setEnabled(true);
    body.getPainter(Painter.NAME_LINES_Y).setEnabled(true);

    body.addPainter(0, new Painter<Integer, Integer>("gradient row background",
      Painter.SCOPE_CELLS_ITEM_Y) {
      int matrixWidth;
      AxisItem<Integer> focusItem;
      boolean isFocused;

      @Override
      protected boolean init() {
        gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_BLUE));
        gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
        gc.setAdvanced(true);
        if (gc.getAdvanced()) gc.setAlpha(127);
        matrixWidth = matrix.getBody().getBounds(Frozen.NONE, Frozen.NONE).width;

        // Get focus item
        Axis<Integer> axisY = matrix.getAxisY();
        focusItem = axisY.getFocusItem();

        return true;
      }

      @Override
      public void clean() {
        gc.setAlpha(255);
        gc.setAdvanced(false);
      }

      @Override
      public void setup(Integer indexX, Integer indexY) {
        isFocused = body.getSectionY().equals(focusItem.getSection())
          && indexY.equals(focusItem.getIndex());
      }

      @Override
      public void paint(int x, int y, int width, int height) {
        if (isFocused) {
          gc.fillGradientRectangle(0, y - 1, matrixWidth, height + 2, false);
        }
      }
    });

    Painter<Integer, Integer> bodyPainter = new Painter<Integer, Integer>(
      Painter.NAME_CELLS) {
      @Override
      public void setupSpatial(Integer indexX, Integer indexY) {
        text = values[indexY][indexX].toString();
        style.hasWordWraping = true;
      }
    };

    bodyPainter.style.hasWordWraping = true;
    body.replacePainter(bodyPainter);
    matrix.getAxisY().getBody().setCellWidth(0);

    matrix.getHeaderX().replacePainterPreserveStyle(
      new Painter<Integer, Integer>(Painter.NAME_CELLS, Painter.SCOPE_CELLS) {
        @Override
        public void setupSpatial(Integer indexX, Integer indexY) {
          text = allCols[indexX];
        }
      });

    layout();
  }

  private void resize() {
    int width = matrix.getClientArea().width;
    if (width <= 0) return;

    // Remove 1 px for every column's line and one for last one
    // width = width - parent.getXs().getIndex().size() - 1;
    //
    // int[] widths =
    // GeometryService.getRealColWidths(parent.getXs().getIndex(), width);
    //
    // for (int i = 0; i < widths.length; i++) {
    // //System.out.println("i: " + i + "; width: " + widths[i]);
    // matrix.getBody().getSectionX().setCellWidth(i, widths[i]);
    // }

    // TODO: wiersze nie zmieniają wielkości po resize()???
  }

  public static void main(String[] args) {
    // Test
    Display display = new Display();
    final Shell shell = new Shell(display);
    shell.setLayout(new FillLayout());

    allCols = new String[] { "id", "name", "bla", "bla2", "bla3" };

    values = new Object[][] {
      { 11, "12 asf aa sdfasdfasd asdf asfasf", "13", 14, 15 },
      { 21, "22", "23", 24, 25 }, { 31, "32", "33", 34, 35 }, };

    new GuiTable1(shell, SWT.RIGHT);

    shell.open();

    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) display.sleep();
    }
    display.dispose();
  }
}
