package pl.netanel.swt.matrix.snippets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.ZoneEditor;

/**
 * Embedded check buttons.
 */
public class S0402_EmbeddedCheckButtons {

  public static void main(String[] args) throws IOException {
    Display display = Display.getDefault();
    Shell shell = new Shell(display);
    shell.setText(title);
    shell.setLayout(new FillLayout());

    // Data model
    final ArrayList<Object[]> data = new ArrayList<Object[]>();
    data.add(new Object[] { "a", true, new Date() });
    data.add(new Object[] { true, false, "Monday" });
    data.add(new Object[] { new Date(), "Sunday", "b" });
    // for (int i = 0; i < 100; i++) {
    // data.add(new Object[] {i % 3 == 0 ? true : null, null, null});
    // }J

    // Matrix
    final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);

    matrix.getAxisY().getBody().setCount(data.size());
    matrix.getAxisY().getHeader().setVisible(true);
    matrix.getAxisX().getBody().setCount(3);
    matrix.getAxisX().getBody().setDefaultCellWidth(80);
    // To test positioning of the embedded controls while resizing columns
    matrix.getAxisX().getBody().setDefaultResizable(true);

    // Data painter
    matrix.getBody().replacePainter(
      new Painter<Integer, Integer>(Painter.NAME_CELLS) {
        @Override
        public void setupSpatial(Integer indexX, Integer indexY) {
          Object value = data.get(indexY.intValue())[indexX.intValue()];
          text = value == null || value instanceof Boolean ? "" : value
            .toString();
        }
      });

    // Body editor
    new ZoneEditor<Integer, Integer>(matrix.getBody()) {
      @Override
      public Object getModelValue(Integer indexX, Integer indexY) {
        return data.get(indexY.intValue())[indexX.intValue()];
      }

      @Override
      public void setModelValue(Integer indexX, Integer indexY, Object value) {
        data.get(indexY.intValue())[indexX.intValue()] = value;
      }

      @Override
      public boolean hasEmbeddedControl(Integer indexX, Integer indexY) {
        Object value = data.get(indexY.intValue())[indexX.intValue()];
        return value instanceof Boolean;
      }

      @Override
      protected Control createControl(Integer indexX, Integer indexY) {
        Object value = data.get(indexY.intValue())[indexX.intValue()];
        if (value instanceof Boolean) { return new Button(matrix, SWT.CHECK); }
        return super.createControl(indexX, indexY);
      }
    };

    shell.setBounds(400, 200, 600, 400);
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  // Meta data
  static final String title = "Embedded check buttons";
  static final String instructions = "";
  static final String code = "0402";
}