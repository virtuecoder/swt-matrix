package usecase;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Section;

/**
 * Simplest matrix.
 */
public class S0001_SimplestMatrix {
  public static void main(String[] args) {
    Shell shell = new Shell();
    shell.setText(title);
    shell.setLayout(new FillLayout());

    Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);
    matrix.getAxisX().getBody().setCount(4);
    matrix.getAxisY().getBody().setCount(10);
    matrix.getAxisY().getHeader().setVisible(true);

    int inc = 2;
    Display display = shell.getDisplay();
    FontData[] fd = display.getSystemFont().getFontData();
    fd[0].setHeight(fd[0].getHeight() + inc);
    Font font = new Font(display, fd[0]);
    matrix.getHeaderX().getPainter(Painter.NAME_CELLS).style.font = font;
    Section<Integer> header = matrix.getAxisY().getHeader();
    header.setDefaultCellWidth(header.getDefaultCellWidth() + inc);

    shell.setBounds(400, 200, 600, 400);
    shell.open();
    shell.pack();

    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  // Meta data
  static final String title = "Simplest matrix";
  static final String instructions = "";
  static final String code = "0001";
}