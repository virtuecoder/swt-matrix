package pl.netanel.swt.matrix.snippets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.ZoneEditor;
import pl.netanel.util.Arrays;

/**
 * Edit controls Text, DateTime, Boolean, Combo depending on the cell value.
 */
public class S0404_EditControlsText_DateTime_Boolean_ComboDependingOnTheCellValue {
  static final String[] weekDays = new String[] { "Monday", "Tuesday",
    "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };

  public static void main(String[] args) throws IOException {
    Display display = Display.getDefault();
    Shell shell = new Shell(display);
    shell.setText(title);
    shell.setLayout(new FillLayout());

    final DateFormat dateFormat = SimpleDateFormat.getDateInstance();

    // Data model
    final ArrayList<Object[]> data = new ArrayList<Object[]>();
    data.add(new Object[] { "a", true, new Date() });
    data.add(new Object[] { true, false, "Monday" });
    data.add(new Object[] { new Date(), "Sunday", "b" });

    // Matrix
    final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);

    matrix.getAxisY().getBody().setCount(data.size());
    matrix.getAxisX().getBody().setCount(3);
    matrix.getAxisX().getBody().setDefaultCellWidth(80);

    // Body editor
    new ZoneEditor<Integer, Integer>(matrix.getBody()) {
      @Override
      public Object getModelValue(Integer indexX, Integer indexY) {
        return data.get(indexY.intValue())[indexX.intValue()];
      }

      @Override
      public boolean setModelValue(Integer indexX, Integer indexY, Object value) {
        if (value instanceof String) {
          try {
            value = dateFormat.parse((String) value);
          }
          catch (ParseException e) {
            if ("true".equals(value) || "false".equals(value)) {
              value = Boolean.parseBoolean((String) value);
            }
          }
        }
        data.get(indexY.intValue())[indexX.intValue()] = value;
        return true;
      }

      @Override
      protected Object parse(Integer indexX, Integer indexY, String s) {
        Object o = s;
        try {
          o = dateFormat.parse(s);
        }
        catch (ParseException e) {
          throw new RuntimeException(e);
        }
        return o;
      }

      @Override
      public Control createControl(Integer indexX, Integer indexY) {
        Object value = data.get(indexY.intValue())[indexX.intValue()];

        if (value instanceof String) {
          // Combo
          if (Arrays.contains(weekDays, (String) value)) {
            Combo combo = new Combo(matrix, SWT.BORDER);
            combo.setItems(weekDays);
            return combo;
          }

          // Text
          else {
            return new Text(matrix, SWT.BORDER);
          }
        }

        // Boolean
        else if (value instanceof Boolean) {
          return new Button(matrix, SWT.CHECK);
        }

        // Date
        else if (value instanceof Date) {
          return new DateTime(matrix, SWT.BORDER);
        }

        // Text
        else if (value == null) { return new Text(matrix, SWT.BORDER); }
        return null;
      }

      @Override
      protected boolean hasEmbeddedControl(Integer indexX, Integer indexY) {
        Object value = data.get(indexY.intValue())[indexX.intValue()];
        return value instanceof Boolean;
      }
    };

    // Paint text from the model in the body
    matrix.getBody().replacePainterPreserveStyle(
      new Painter<Integer, Integer>(Painter.NAME_CELLS) {
        @Override
        public void setupSpatial(Integer indexX, Integer indexY) {
          Object value = data.get(indexY.intValue())[indexX.intValue()];
          if (value instanceof String) {
            text = (String) value;
          }
          else if (value instanceof Date) {
            text = dateFormat.format(value);
          }
          else {
            text = null;
          }
        }
      });

    shell.setBounds(400, 200, 600, 400);
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  // Meta data
  static final String title = "Edit controls Text, DateTime, Boolean, Combo depending on the cell value";
  static final String instructions = "";
}