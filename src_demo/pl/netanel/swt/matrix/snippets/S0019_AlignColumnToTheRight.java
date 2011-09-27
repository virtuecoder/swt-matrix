package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;

/**
 * Align column to the right.
 */
public class S0019_AlignColumnToTheRight {

  public static void main(String[] args) {
    Shell shell = new Shell();
    shell.setText(title);
    shell.setBounds(400, 200, 600, 400);
    shell.setLayout(new FillLayout());

    Matrix<Integer, Integer> matrix = 
      new Matrix<Integer, Integer>(shell, SWT.H_SCROLL | SWT.V_SCROLL);
    matrix.getAxisX().getBody().setCount(4);
    matrix.getAxisX().getBody().setDefaultResizable(true);
    matrix.getAxisY().getBody().setCount(10);
    matrix.getAxisY().getHeader().setVisible(true);

    matrix.getHeaderX().replacePainter(
      new Painter<Integer, Integer>(Painter.NAME_CELLS) {
        @Override
        public void setupSpatial(Integer indexX, Integer indexY) {
          style.textAlignX = indexX.intValue() == 2 ? SWT.RIGHT : SWT.LEFT;
          text = indexX.toString();
        }
      });

    matrix.getBody().replacePainter(
      new Painter<Integer, Integer>(Painter.NAME_CELLS) {
        @Override
        public void setupSpatial(Integer indexX, Integer indexY) {
          style.textAlignX = indexX.intValue() == 2 ? SWT.RIGHT : SWT.LEFT;
          text = indexY.toString() + ", " + indexX;
        }
      });

    shell.open();
    Display display = shell.getDisplay();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  // Meta data
  static final String title = "Align column to the right";
  static final String instructions = "";
  static final String code = "0019";
}