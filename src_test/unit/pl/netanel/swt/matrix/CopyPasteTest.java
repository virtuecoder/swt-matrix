package pl.netanel.swt.matrix;

import static org.junit.Assert.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.Before;
import org.junit.Test;

public class CopyPasteTest {

  private Matrix<Integer, Integer> matrix;
  private String [][] data = new String[5][];
  private Zone<Integer, Integer> body;
  private ZoneEditor<Integer, Integer> editor;
  private Axis<Integer> axisX;
  private Axis<Integer> axisY;
  private Section<Integer> bodyX;
  private Section<Integer> bodyY;

  @Before
  public void setUp() {
    for (int i = 0; i < 5; i++) {
      data[i] = new String[5];
      for (int j = 0; j < 5; j++) {
        data[i][j] = "" + i + "," + j;
      }
    }

    matrix = new Matrix<Integer, Integer>(new Shell(), SWT.V_SCROLL);
    body = matrix.getBody();
    axisX = matrix.getAxisX();
    axisY = matrix.getAxisY();
    bodyX = axisX.getBody();
    bodyY = axisY.getBody();

    bodyX.setCount(5);
    bodyY.setCount(5);

    body.replacePainterPreserveStyle(new Painter<Integer, Integer>(Painter.NAME_CELLS) {
      @Override
      public void setupSpatial(Integer indexX, Integer indexY) {
        text = "" + indexX + "," + indexY;
      }
    });

    editor = new ZoneEditor<Integer, Integer>(body) {
      @Override
      public boolean setModelValue(Integer indexX, Integer indexY, Object value) {
        data[indexX][indexY] = value == null ? "" : value.toString();
        return true;
      }
    };
    clearClipboard();
  }

  @Test
  public void copyFromEmptyMatrix() throws Exception {
    body.setSelectedAll(true);
    bodyX.setCount(0);
    bodyY.setCount(0);
    editor.copy();
    assertClipboard(" ");
  }

  @Test
  public void pasteToEmptyMatrix() throws Exception {
    body.setSelectedAll(true);
    bodyX.setCount(0);
    bodyY.setCount(0);
    editor.paste();
  }

  @Test
  public void copySingleCell() throws Exception {
    editor.copy();
    assertClipboard("0,0");
  }

  @Test
  public void copySingleCellBeyondBody() throws Exception {
    matrix.setCopyBeyondBody(true);
    matrix.execute(Matrix.CMD_COPY);
    assertClipboard("0,0");
  }

  @Test
  public void copyHiddenCells() throws Exception {

    body.setSelected(1, 3, 0, 0, true);
    bodyX.setHidden(2, true);

    editor.copy();
    assertClipboard("1,0\t3,0");
    matrix.setCopyPasteHiddenCells(true);
    editor.copy();
    assertClipboard("1,0\t2,0\t3,0");

    matrix.setCopyPasteHiddenCells(false);
    editor.copy();
    axisX.setFocusItem(bodyX, 1);
    axisY.setFocusItem(bodyY, 1);
    editor.paste();

    assertEquals("1,0", data[1][1]);
    assertEquals("2,1", data[2][1]);
    assertEquals("3,0", data[3][1]);
  }

  @Test
  public void copyBeyondBodyColumns() throws Exception {
    matrix.setCopyBeyondBody(true);
    axisY.getHeader().setVisible(true);
    bodyX.setSelected(1, 1, true);

    matrix.execute(Matrix.CMD_COPY);
    assertClipboard("1\n1,0\n1,1\n1,2\n1,3\n1,4");
  }

  @Test
  public void copyBeyondBodyColumnsHidden() throws Exception {
    bodyX.setSelected(1, 3, true);
    bodyX.setHidden(2, true);
    axisY.getHeader().setVisible(true);
    matrix.setCopyBeyondBody(true);

    matrix.execute(Matrix.CMD_COPY);
    assertClipboard("1\t3\n1,0\t3,0\n1,1\t3,1\n1,2\t3,2\n1,3\t3,3\n1,4\t3,4");
  }

  @Test
  public void copyBeyondBodyColumnsHiddenIncluded() throws Exception {
    bodyX.setSelected(1, 3, true);
    bodyX.setHidden(2, true);
    axisY.getHeader().setVisible(true);
    matrix.setCopyBeyondBody(true);
    matrix.setCopyPasteHiddenCells(true);

    matrix.execute(Matrix.CMD_COPY);
    assertClipboard("1\t2\t3\n1,0\t2,0\t3,0\n1,1\t2,1\t3,1\n1,2\t2,2\t3,2\n1,3\t2,3\t3,3\n1,4\t2,4\t3,4");
  }

  @Test
  public void copyBeyondBodyRows() throws Exception {
    matrix.setCopyBeyondBody(true);
    axisY.getHeader().setVisible(true);
    bodyY.setSelected(1, 2, true);

    matrix.execute(Matrix.CMD_COPY);
    assertClipboard("1\t0,1\t1,1\t2,1\t3,1\t4,1\n2\t0,2\t1,2\t2,2\t3,2\t4,2");
  }

  void assertClipboard(String expected) {
    Clipboard clipboard = new Clipboard(Display.getDefault());
    String actual = clipboard.getContents(TextTransfer.getInstance()).toString();
    clipboard.dispose();
    assertEquals(expected.replace("\n", ZoneEditor.NEW_LINE), actual);
  }

  void assertData(String expected) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        if (j > 0) sb.append("\t");
        sb.append(data[i][j]);
      }
      sb.append("\n");
    }

    assertEquals(expected, sb.toString());
  }

  private void clearClipboard() {
    Clipboard clipboard = new Clipboard(Display.getDefault());
    clipboard.setContents(new Object[] {" "},
        new Transfer[] {TextTransfer.getInstance()});
    clipboard.dispose();
  }


}
