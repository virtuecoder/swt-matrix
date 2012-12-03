package pl.netanel.swt.matrix.snippets;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.Zone;
import pl.netanel.swt.matrix.ZoneEditor;

/**
 * Separate zone to insert new items.
 */
public class S0410_SeparateZoneToInsertNewItems {
  static final int COLUMN_COUNT = 3;
  ArrayList<Object[]> data;

  public static void main(String[] args) throws IOException {
    Display display = Display.getDefault();
    Shell shell = new Shell(display);
    shell.setText(title);
    shell.setLayout(new FillLayout());

    new S0410_SeparateZoneToInsertNewItems(shell);

    shell.setBounds(400, 200, 600, 400);
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  public S0410_SeparateZoneToInsertNewItems(Composite parent) {
    // Data model
    data = new ArrayList<Object[]>();

    // Axis
    final Axis<Integer> axisY = new Axis<Integer>(Integer.class, 3, 0, 1);
    axisY.getBody().setCount(data.size());
    Section<Integer> insertSection = axisY.getSection(2);
    insertSection.setCount(1);

    // Matrix
    final Matrix<Integer, Integer> matrix =
      new Matrix<Integer, Integer>( parent, SWT.NONE, null, axisY);

    // Configure axises
    Axis<Integer> axisX = matrix.getAxisX();
    axisY.getHeader().setVisible(true);
    axisX.getHeader().setVisible(true);
    axisX.getBody().setCount(COLUMN_COUNT);

    // Paint text from the model in the body
    Painter<Integer, Integer> bodyPainter = new Painter<Integer, Integer>(
      Painter.NAME_CELLS) {
      @Override
      public void setupSpatial(Integer indexX, Integer indexY) {
        text = (String) data.get(indexY.intValue())[indexX.intValue()];
      }
    };
    matrix.getBody().replacePainterPreserveStyle(bodyPainter);

    // Insert body editor
    final InsertEditor insertEditor = new InsertEditor(matrix);

    // Insert body painter
    Zone<Integer, Integer> insertBody = matrix.getZone(matrix.getAxisX()
      .getBody(), insertSection);
    Painter<Integer, Integer> insertPainter = new Painter<Integer, Integer>(
      Painter.NAME_CELLS) {
      @Override
      public void setupSpatial(Integer indexX, Integer indexY) {
        text = (String) insertEditor.item[indexX.intValue()];
      }
    };
    insertBody.replacePainterPreserveStyle(insertPainter);

    insertBody.getPainter(Painter.NAME_BACKGORUND).style.background = null;

    // Paint text in the row header of the insert section
    final Zone<Integer, Integer> insertHeader = matrix.getZone(
      axisX.getHeader(), insertSection);
    Painter<Integer, Integer> insertHeaderPainter = new Painter<Integer, Integer>(
      Painter.NAME_CELLS) {
      @Override
      public void setupSpatial(Integer indexX, Integer indexY) {
        style.textAlignX = style.textAlignY = SWT.CENTER;
        text = "add:";
      }
    };
    insertHeader.replacePainterPreserveStyle(insertHeaderPainter);

    new ZoneEditor<Integer, Integer>(insertHeader) {
      @Override
      protected Control createControl(Integer indexX, Integer indexY) {
        if (insertEditor.isDirty) {
          Composite composite = new Composite(matrix, SWT.NONE);
          composite.setLayout(new FillLayout());

          Button button = new Button(composite, SWT.PUSH);
          button.setText("\u2713");
          button.setToolTipText("Submit the new item");
          button.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
              insertEditor.save();

            }
          });

          button = new Button(composite, SWT.PUSH);
          button.setText("\u2717");
          button.setToolTipText("Clear editions");
          button.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
              insertEditor.cancel();
            }
          });

          return composite;
        }
        else {
          return null;
        }
      }

      @Override
      protected void setBounds(Integer indexX, Integer indexY, Control control) {
        Rectangle bounds = insertHeader.getCellBounds(indexX, indexY);
        bounds.y -= 2;
        bounds.height += 4;
        control.setBounds(bounds);
      }

      @Override
      protected boolean hasEmbeddedControl(Integer indexX, Integer indexY) {
        return insertEditor.isDirty;
      }
    };
  }

  class InsertEditor extends ZoneEditor<Integer, Integer> {
    Object[] item;
    boolean isDirty;
    final Matrix<Integer, Integer> matrix;

    public InsertEditor(Matrix<Integer, Integer> matrix) {
      super(matrix.getZone(matrix.getAxisX().getBody(), matrix.getAxisY()
        .getSection(2)));
      this.matrix = matrix;
      item = new Object[COLUMN_COUNT];
    }

    @Override
    public Object getModelValue(Integer indexX, Integer indexY) {
      return item[indexX.intValue()];
    }

    @Override
    public boolean setModelValue(Integer indexX, Integer indexY, Object value) {
      isDirty = true;
      item[indexX.intValue()] = value;
      matrix.refresh();
      return true;
    }

    public boolean isDirty() {
      return isDirty;
    }

    public void cancel() {
      isDirty = false;
      item = new Object[COLUMN_COUNT];
      matrix.refresh();
      matrix.forceFocus();
    }

    public void save() {
      data.add(item);
      matrix.getAxisY().getBody().setCount(data.size());
      isDirty = false;
      item = new Object[COLUMN_COUNT];
      matrix.refresh();
      matrix.forceFocus();
    }
  }

  // Meta data
  static final String title = "Separate zone to insert new items";
  static final String instructions = "";
}