package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Style;
import pl.netanel.swt.matrix.Zone;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Section;

/**
 * Mark selection with SWT.COLOR_LIST... colors.
 */
public class S0216_MarkSelectionWithSWT_COLOR_LIST___Colors {
  public static void main(String[] args) {
    Shell shell = new Shell();
    shell.setText(title);
    shell.setBounds(400, 200, 600, 400);
    shell.setLayout(new FillLayout());
    final Display display = shell.getDisplay();

    final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);
    matrix.getAxisX().setFocusItemEnabled(false);
    matrix.getAxisY().setFocusItemEnabled(false);

    matrix.getAxisY().getBody().setCount(10);

    Section<Integer> body1X = matrix.getAxisX().getBody();
    body1X.setCount(4);
    body1X.setDefaultCellWidth(50);

    final Zone<Integer, Integer> body = matrix.getBody();
    Style style = body.getPainter(Painter.NAME_CELLS).style;
    style.selectionBackground = display
      .getSystemColor(SWT.COLOR_LIST_SELECTION);
    style.selectionForeground = display
      .getSystemColor(SWT.COLOR_LIST_SELECTION_TEXT);
    body.setSelected(1, 2, 1, 2, true);

    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  // Meta data
  static final String title = "Mark selection with SWT.COLOR_LIST... colors";
  static final String instructions = "";
}