package pl.netanel.swt.matrix.snippets;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.Zone;

/**
 * Filter section between header and body.
 */
/*
 * Make sure filter.png is on your class path.
 */
public class S0003_FilterSectionBetweenHeaderAndBody {

  public static void main(String[] args) throws Exception {
    Shell shell = new Shell();
    shell.setText(title);
    shell.setLayout(new GridLayout(2, false));
    Display display = shell.getDisplay();

    // Filter container
    final String[] filter = new String[1];

    final ArrayList<Object[]> list = new ArrayList<Object[]>();
    list.add(new Object[] { "Task 1", "high" });
    list.add(new Object[] { "Task 2", "medium" });
    list.add(new Object[] { "Task 3", "low" });
    list.add(new Object[] { "Task 4", "medium" });
    list.add(new Object[] { "Task 5", "high" });
    final ArrayList<Object[]> filtered = new ArrayList<Object[]>();
    filtered.addAll(list);

    Image image = new Image(display, 16, 16);
    GC gc = new GC(image);
    gc.setAntialias(SWT.ON);
    gc.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
    gc.fillRectangle(0, 0, 16, 16);
    gc.setForeground(display.getSystemColor(SWT.COLOR_DARK_GREEN));
    gc.drawPolygon(new int[] { 2, 1, 7, 7, 7, 14, 9, 12, 9, 7, 14, 1 });
    gc.dispose();

    Axis<Integer> axisY = new Axis<Integer>(Integer.class, 3, 0, 2);
    Section<Integer> filterY = axisY.getSection(1);
    filterY.setCount(1);
    filterY.setFocusItemEnabled(false);
    final Section<Integer> bodyY = axisY.getBody();
    bodyY.setCount(list.size());
    bodyY.setDefaultResizable(true);

    final Matrix<Integer, Integer> matrix = 
      new Matrix<Integer, Integer>(shell, SWT.V_SCROLL, null, axisY);
    matrix.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

    Axis<Integer> axisX = matrix.getAxisX();

    Section<Integer> bodyX = axisX.getBody();
    Section<Integer> headerX = axisX.getHeader();
    Section<Integer> headerY = axisY.getHeader();

    bodyX.setCount(2);

    headerX.setDefaultCellWidth(16);
    headerX.setVisible(true);
    headerY.setVisible(true);

    matrix.getBody().replacePainter(
      new Painter<Integer, Integer>(Painter.NAME_CELLS) {
        @Override
        public void setupSpatial(Integer indexX, Integer indexY) {
          text = filtered.get(indexY.intValue())[indexX.intValue()].toString();
        }
      });

    // Column header
    matrix.getHeaderX().replacePainter(
      new Painter<Integer, Integer>(Painter.NAME_CELLS) {
        @Override
        public void setupSpatial(Integer indexX, Integer indexY) {
          text = indexX.intValue() == 0 ? "Task" : "Priority";
        }
      });

    // Filter row header
    matrix.getZone(headerX, filterY).getPainter(Painter.NAME_CELLS).image = image;

    // Filter columns
    Zone<Integer, Integer> filterColumns = matrix.getZone(bodyX, filterY);
    filterColumns.replacePainter(new Painter<Integer, Integer>(
      Painter.NAME_CELLS) {
      @Override
      public void setupSpatial(Integer indexX, Integer indexY) {
        text = indexX.intValue() == 1 ? filter[0] : null;
      };
    });

    Label label = new Label(shell, SWT.NONE);
    label
      .setText("Press to filter by priority: h - high, m - medium, l - low, a - all");

    matrix.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        switch (e.character) {
        case 'a':
          filter[0] = null;
          break;
        case 'h':
          filter[0] = "high";
          break;
        case 'm':
          filter[0] = "medium";
          break;
        case 'l':
          filter[0] = "low";
          break;
        default:
          return;
        }
        filtered.clear();
        if (filter[0] == null) {
          filtered.addAll(list);
        }
        else {
          for (Object[] item : list) {
            if (item[1].equals(filter[0])) {
              filtered.add(item);
            }
          }
        }
        bodyY.setCount(filtered.size());
        matrix.refresh();
      }
    });

    shell.setBounds(400, 200, 600, 400);
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    image.dispose();
  }

  // Meta data
  static final String title = "Filter section between header and body";
  static final String instructions = "";
  static final String code = "0003";
}