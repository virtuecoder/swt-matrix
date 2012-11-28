package pl.netanel.swt.matrix.snippets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.ZoneEditor;

/**
 * Check buttons emulated by images.
 */
/*
 * Note: The checked.png and unchecked.png images must be on the class path.
 */
public class S0403_CheckButtonsEmulatedByImages {

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

    final Image checkedImage = new Image(display, 14, 14);
    GC gc = new GC(checkedImage);
    gc.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
    gc.setAntialias(SWT.ON);
    gc.drawRectangle(0, 0, 13, 13);
    gc.setLineWidth(2);
    gc.drawPolyline(new int[] { 4, 5, 6, 9, 10, 3 });
    gc.dispose();

    final Image uncheckedImage = new Image(display, 14, 14);
    gc = new GC(uncheckedImage);
    gc.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
    gc.setAntialias(SWT.ON);
    gc.drawRectangle(0, 0, 13, 13);
    gc.dispose();

    final Image[] checkboxImages = new Image[] { checkedImage, uncheckedImage };

    // Matrix
    final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);

    matrix.getAxisY().getBody().setCount(data.size());
    matrix.getAxisY().getHeader().setVisible(true);
    matrix.getAxisX().getBody().setCount(3);
    matrix.getAxisX().getBody().setDefaultCellWidth(80);
    // To test positioning of the embedded controls while resizing columns
    matrix.getAxisX().getBody().setDefaultResizable(true);

    // Data painter
    matrix.getBody().replacePainterPreserveStyle(
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
      public boolean setModelValue(Integer indexX, Integer indexY, Object value) {
        data.get(indexY.intValue())[indexX.intValue()] = value;
        return true;
      }

      @Override
      public Object[] getCheckboxEmulation(Integer indexX, Integer indexY) {
        Object value = data.get(indexY.intValue())[indexX.intValue()];
        return value instanceof Boolean ? checkboxImages : null;
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
    checkedImage.dispose();
    uncheckedImage.dispose();
  }

  // Meta data
  static final String title = "Check buttons emulated by images";
  static final String instructions = "";
  static final String code = "0403";
}