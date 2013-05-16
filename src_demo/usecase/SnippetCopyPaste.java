package usecase;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.ZoneEditor;

public class SnippetCopyPaste {
  static final int countY = 10, countX = 10;

  public static void main(String[] args) {
    final Shell shell = new Shell();
    shell.setLayout(new FillLayout());

    // Data model
    final ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
    for (int i = 0; i < countY; i++) {
      ArrayList<String> row = new ArrayList<String>();
      for (int j = 0; j < countX; j++) {
        row.add("" + i + ", " + j);
      }
      data.add(row);
    }

    Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.V_SCROLL);
    matrix.setCopyBeyondBody(true);

    matrix.getAxisX().getBody().setCount(countX);
    matrix.getAxisY().getBody().setCount(countY);
    matrix.getAxisX().getHeader().setVisible(true);
    matrix.getAxisY().getHeader().setVisible(true);

    matrix.getBody().replacePainterPreserveStyle(new Painter<Integer, Integer>(Painter.NAME_CELLS) {
      @Override
      public void setupSpatial(Integer indexX, Integer indexY) {
        super.setupSpatial(indexX, indexY);
        text = data.get(indexY).get(indexX);
      }
    });

    ZoneEditor<Integer, Integer> editor = new ZoneEditor<Integer, Integer>(matrix.getBody()) {
      @Override
      public Object getModelValue(Integer indexX, Integer indexY) {
        return data.get(indexY).get(indexX);
      }
      @Override
      public boolean setModelValue(Integer indexX, Integer indexY, Object value) {
//        if (indexX == 3) {
//          System.err.println("Not valid");
//          return false;
//        }
        data.get(indexY).set(indexX, (String) value);
        return true;
      }
    };
    editor.setBulkEditAtomic(true);
    editor.setEditHistoryLimit(Integer.MAX_VALUE);

//    matrix.setSelectSkipHidden(true);

    shell.setBounds(400, 200, 600, 400);
    shell.open();

    Display display = shell.getDisplay();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }
}
