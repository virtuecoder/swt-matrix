package pl.netanel.swt.matrix.snippets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.AxisItem;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.Style;

/**
 * Sorting by columns.
 */
/*
 * Make sure ascending.png and descending.png is on your class path.
 */
public class S0056_SortingByMultipleColumns {

  private static final int COLUMN_COUNT = 3;

  public static void main(String[] args) throws Exception {
    Shell shell = new Shell();
    shell.setText(title);
    shell.setLayout(new GridLayout(2, false));
    Display display = shell.getDisplay();

    Label label = new Label(shell, SWT.NONE);
    label.setText("Single column sort by click on the column header. Multiple column sort by Ctrl+click.");

    // Model
    final ArrayList<String[]> list = new ArrayList<String[]>();
    list.add(new String[] { "Task 1", "high", "a" });
    list.add(new String[] { "Task 2", "medium", "c" });
    list.add(new String[] { "Task 3", "low", "a" });
    list.add(new String[] { "Task 4", "high", "b" });
    list.add(new String[] { "Task 5", "medium", "b" });

    // Sorted list holder serving as a model for the matrix widget
    final ArrayList<String[]> sorted = new ArrayList<String[]>();
    sorted.addAll(list);

    // Sorting images and state
    final Image sortAscImage = new Image(display, 9, 5);
    GC gc = new GC(sortAscImage);
    gc.setAntialias(SWT.ON);
    gc.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
    gc.fillRectangle(0, 0, 9, 5);
    gc.setBackground(display.getSystemColor(SWT.COLOR_DARK_GRAY));
    gc.setAntialias(SWT.ON);
    gc.fillPolygon(new int[] { 5, 0, 9, 5, 0, 5 });
    gc.dispose();

    final Image sortDescImage = new Image(display, 9, 5);
    gc = new GC(sortDescImage);
    gc.setAntialias(SWT.ON);
    gc.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
    gc.fillRectangle(0, 0, 9, 5);
    gc.setBackground(display.getSystemColor(SWT.COLOR_DARK_GRAY));
    gc.setAntialias(SWT.ON);
    gc.fillPolygon(new int[] { 5, 5, 9, 0, 0, 0 });
    gc.dispose();

    final int[] sortDirections = new int[COLUMN_COUNT];

    // Matrix
    final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.V_SCROLL);
    matrix.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

    Axis<Integer> axisY = matrix.getAxisY();
    final Section<Integer> bodyY = axisY.getBody();
    bodyY.setCount(list.size());
    bodyY.setDefaultResizable(true);
    bodyY.setDefaultMoveable(true);
    axisY.getHeader().setVisible(true);

    Axis<Integer> axisX = matrix.getAxisX();
    axisX.getBody().setCount(3);
    axisX.getHeader().setDefaultCellWidth(16);
    axisX.getHeader().setVisible(true);

    // Paint data text in the body zone
    matrix.getBody().replacePainterPreserveStyle(
      new Painter<Integer, Integer>(Painter.NAME_CELLS) {
        @Override
        public void setupSpatial(Integer indexX, Integer indexY) {
          text = sorted.get(indexY.intValue())[indexX.intValue()].toString();
        }
      });

    // Paint text and sorting image in the column headers zone
    Painter<Integer, Integer> columnHeaderPainter = new Painter<Integer, Integer>(
      Painter.NAME_CELLS) {
      @Override
      public void setupSpatial(Integer indexX, Integer indexY) {
        text = indexX.toString();

        int column = indexX.intValue();
        image = sortDirections[column] == 0 ? null
          : sortDirections[column] == 1 ? sortAscImage : sortDescImage;
      }
    };

    matrix.getHeaderX().replacePainterPreserveStyle(columnHeaderPainter);
    Style style = columnHeaderPainter.style;
    style.imageAlignX = SWT.RIGHT;
    style.imageAlignY = SWT.CENTER;
    style.imageMarginX = 5;

    // Change sorting on mouse down in a column header
    matrix.getHeaderX().addListener(SWT.MouseDown, new Listener() {
      int lastColumn = -1;
      ArrayList<Integer> columnsOrder = new ArrayList<Integer>(COLUMN_COUNT);

      @Override
      public void handleEvent(Event e) {
        // AxisItem<Y> item0 = matrix.getAxisY().getItemByDistance(e.y);
        AxisItem<Integer> itemX = matrix.getAxisX().getItemByViewportDistance(e.x);
        final int column = itemX.getIndex().intValue();

        int previousDirection = sortDirections[column];

        // Clear Sorting for other columns than the clicked one
        if (e.stateMask == 0) {
          for (int i = 0; i < sortDirections.length; i++) {
            if (column != lastColumn) {
              sortDirections[i] = 0;
            }
          }
          columnsOrder.clear();
          columnsOrder.add(column);
        }
        else {
          // Move the current column to the end
          columnsOrder.remove((Integer) column);
          columnsOrder.add(column);
        }

        // Cycle through 3 direction options 1, -1, 0
        sortDirections[column] = previousDirection + 1;
        if (sortDirections[column] == 2) sortDirections[column] = -1;

        // Check if there is any sorting defined
        boolean shouldSort = false;
        for (int i = 0; i < columnsOrder.size(); i++) {
          if (sortDirections[columnsOrder.get(i)] != 0) {
            shouldSort = true;
            break;
          }
        }

        if (shouldSort) {
          Collections.sort(sorted, new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
              for (int i = 0; i < columnsOrder.size(); i++) {
                int column = columnsOrder.get(i);
                int compareTo = o1[column].compareTo(o2[column]) * sortDirections[column];
                if (compareTo != 0) return compareTo;
              }
              return 0;
            }
          });
        }
        else {
          sorted.clear();
          sorted.addAll(list);
        }

        lastColumn = column;
        matrix.redraw();
      }
    });

    // Unbind column selection on click
    matrix.getHeaderX().unbind(Matrix.CMD_SELECT_COLUMN, SWT.MouseDown, 1);
    matrix.getHeaderX().unbind(Matrix.CMD_FOCUS_LOCATION, SWT.MouseDown, 1);
    matrix.getHeaderX().unbind(Matrix.CMD_SELECT_COLUMN_ALTER, SWT.MouseDown, SWT.MOD1 | 1);
    matrix.getHeaderX().unbind(Matrix.CMD_FOCUS_LOCATION_ALTER, SWT.MouseDown, SWT.MOD1 | 1);

    shell.setBounds(400, 200, 600, 400);
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    sortAscImage.dispose();
    sortDescImage.dispose();
  }


  // Meta data
  static final String title = "Sorting by multiple columns";
  static final String instructions = "";
}