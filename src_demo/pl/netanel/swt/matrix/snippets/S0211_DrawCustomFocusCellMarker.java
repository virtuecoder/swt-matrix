package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.AxisItem;
import pl.netanel.swt.matrix.Zone;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;

/**
 * Draw custom focus cell marker.
 */
public class S0211_DrawCustomFocusCellMarker {
  public static void main(String[] args) {
    Shell shell = new Shell();
    shell.setText(title);
    shell.setBounds(400, 200, 600, 400);
    shell.setLayout(new FillLayout());
    final Display display = shell.getDisplay();

    final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);
    matrix.getAxisX().getBody().setCount(40);
    matrix.getAxisY().getBody().setCount(1000);

    matrix.replacePainterPreserveStyle(new Painter<Integer, Integer>(Painter.NAME_FOCUS_CELL) {
        @Override
        public void paint(int x, int y, int width, int height) {
          // Get bounds of the focus cell
          AxisItem<Integer> itemX = matrix.getAxisX().getFocusItem();
          AxisItem<Integer> itemY = matrix.getAxisY().getFocusItem();
          if (itemX == null || itemY == null) return;

          Zone<Integer, Integer> zone = matrix.getZone(itemX.getSection(),
            itemY.getSection());
          if (zone == null) return;
          Rectangle r = zone.getCellBounds(itemX.getIndex(), itemY.getIndex());
          if (r == null) return;

          // Draw rounded rectangle with a changed color
          gc.setForeground(display.getSystemColor(SWT.COLOR_LIST_SELECTION));
          gc.drawRoundRectangle(r.x - 1, r.y - 1, r.width + 1, r.height + 1, 5, 5);
        }
      });

    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  // Meta data
  static final String title = "Draw custom focus cell marker";
  static final String instructions = "";
}