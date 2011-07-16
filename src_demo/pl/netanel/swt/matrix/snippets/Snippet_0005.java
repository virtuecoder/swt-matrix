package pl.netanel.swt.matrix.snippets;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.AxisItem;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Section;

/**
 * Sorting by columns.
 */
/*
 * Make sure ascending.png and descending.png is on your class path.
 */
public class Snippet_0005 {

  public static void main(String[] args) throws Exception {
    Shell shell = new Shell();
    shell.setLayout(new GridLayout(2, false));
    Display display = shell.getDisplay();

    // Model
    final ArrayList<String[]> list = new ArrayList<String[]>();
    list.add(new String[] { "Task 1", "high" });
    list.add(new String[] { "Task 2", "medium" });
    list.add(new String[] { "Task 3", "low" });
    list.add(new String[] { "Task 4", "high" });
    list.add(new String[] { "Task 5", "medium" });
    
    // Sorted list holder
    final ArrayList<String[]> sorted = new ArrayList<String[]>();
    sorted.addAll(list);

    // Sorting images and state
    final Image sortAsc = readImage("sort-windows-asc.png");
    final Image sortDesc = readImage("sort-windows-desc.png");
    final int[] direction = new int[] { 0, 0 };

    // Matrix
    final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.V_SCROLL);
    matrix.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

    Axis<Integer> axis0 = matrix.getAxis0();
    final Section<Integer> body0 = axis0.getBody();
    body0.setCount(list.size());
    body0.setDefaultResizable(true);
    body0.setDefaultMoveable(true);
    axis0.getHeader().setVisible(true);

    Axis<Integer> axis1 = matrix.getAxis1();
    axis1.getBody().setCount(2);
    axis1.getHeader().setDefaultCellWidth(16);
    axis1.getHeader().setVisible(true);

    // Paint data text in the body zone
    matrix.getBody().replacePainter(
      new Painter<Integer, Integer>("cells", Painter.SCOPE_CELLS_HORIZONTALLY) {
        @Override public String getText(Integer index0, Integer index1) {
          return sorted.get(index0.intValue())[index1.intValue()].toString();
        }
      });

    // Paint text and sorting image in the column headers zone
    Painter<Integer, Integer> columnHeaderPainter = new Painter<Integer, Integer>(
      "cells", Painter.SCOPE_CELLS_HORIZONTALLY) 
    {
      @Override public String getText(Integer index0, Integer index1) {
        return index1.toString();
      }

      @Override public Image getImage(Integer index0, Integer index1) {
        int column = index1.intValue();
        return direction[column] == 0 ? null
          : direction[column] == 1 ? sortAsc : sortDesc;
      }
    };

    columnHeaderPainter.imageAlignX = SWT.RIGHT;
    columnHeaderPainter.imageAlignY = SWT.CENTER;
    columnHeaderPainter.imageMarginX = 5;
    matrix.getColumnHeader().replacePainter(columnHeaderPainter);

    // Change sorting on mouse down in a column header 
    matrix.getColumnHeader().addListener(SWT.MouseDown, new Listener() {
      public void handleEvent(Event e) {
        // AxisItem<N0> item0 = matrix.getAxis0().getItemByDistance(e.y);
        AxisItem<Integer> item1 = matrix.getAxis1().getItemByDistance(e.x);
        final int column = item1.getIndex().intValue();

        int previousDirection = direction[column];
        for (int i = 0; i < direction.length; i++) {
          direction[i] = 0;
        }
        direction[column] = previousDirection <= 0 ? 1 : -1;

        Collections.sort(sorted, new Comparator<String[]>() {
          public int compare(String[] o1, String[] o2) {
            return o1[column].compareTo(o2[column]) * direction[column];
          }
        });
        matrix.redraw();
      }
    });
    
    // Unbind column selection on click
    matrix.getColumnHeader().unbind(Matrix.CMD_SELECT_COLUMN, SWT.MouseDown, 1);
    matrix.getColumnHeader().unbind(Matrix.CMD_FOCUS_LOCATION, SWT.MouseDown, 1);

    shell.setBounds(400, 200, 400, 300);
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  static Image readImage(String path) throws IOException {
    FileInputStream stream = new FileInputStream(path);
    Image image = new Image(Display.getCurrent(), new ImageData(stream));
    stream.close();
    return image;
  }
}
