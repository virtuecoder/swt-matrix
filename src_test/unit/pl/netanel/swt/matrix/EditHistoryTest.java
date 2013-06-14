/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.junit.Test;

import pl.netanel.swt.matrix.snippets.S0440_EditHistory_Undo_Redo.IntZoneEditorWithHistory;

@SuppressWarnings({"rawtypes", "unchecked"})
public class EditHistoryTest {

  String[][] model = new String[][] {
      new String[] {"", "" ,""}, new String[]{"", "" ,""}, new String[]{"", "" ,""}};
  private IntZoneEditorWithHistory editor;
  private Matrix matrix;

  /**
   * Default history limit of 0 should not limit the undo log
   */

  @Test
  public void defaultLimit() throws Exception {
    setup();
    edit(0, 0, "a");
    assertEquals("a", model[0][0]);

    edit(0, 0, "b");
    assertEquals("b", model[0][0]);

    editor.undo();
    assertEquals("a", model[0][0]);
    editor.undo();
    assertEquals("", model[0][0]);
  }

  /**
   * check if the history circulates within the given limit.
   */

  @Test
  public void circularLimit() throws Exception {
    setup();
    editor.setEditHistoryLimit(3);
    edit(0, 0, "a");
    edit(0, 0, "b");
    edit(0, 0, "c");
    edit(0, 0, "d");
    edit(0, 0, "e");
    editor.undo();
    editor.undo();
    editor.undo();
    editor.undo();
    editor.undo();
    assertEquals("b", model[0][0]);
    editor.redo();
    editor.redo();
    editor.redo();
    editor.redo();
    editor.redo();
    assertEquals("e", model[0][0]);
  }

  @Test
  public void bulk() throws Exception {
    setup();
    edit(0, 0, "a");
    editor.setBulkEdit(true);
    edit(1, 0, "b");
    edit(0, 1, "c");
    editor.setBulkEdit(false);
    edit(1, 1, "d");
    editor.undo();
    assertEquals("a", model[0][0]);
    assertEquals("b", model[0][1]);
    assertEquals("c", model[1][0]);
    assertEquals("", model[1][1]);
    editor.undo();
    assertEquals("a", model[0][0]);
    assertEquals("", model[0][1]);
    assertEquals("", model[1][0]);
    assertEquals("", model[1][1]);
    editor.redo();
    assertEquals("a", model[0][0]);
    assertEquals("b", model[0][1]);
    assertEquals("c", model[1][0]);
    assertEquals("", model[1][1]);
    editor.redo();
  }


  public void bulkExceedLimit() throws Exception {
    setup();
    editor.setEditHistoryLimit(2);
    editor.setBulkEditAtomic(true);
    editor.setBulkEdit(true);
    edit(0, 0, "a");
    edit(0, 1, "b");
    try {
      edit(0, 2, "c");
      fail("Expected an exception");
    }
    catch (Exception e) {
      assertEquals("", model[0][0]);
      assertEquals("", model[2][0]);
    }
  }

  @Test
  public void undoAfterDelete() throws Exception {
    setup();
    edit(0, 0, "a");
    edit(0, 1, "b");
    deleteY(1);
    editor.deleteY(1, 1);
    editor.undo();
    assertEquals("", model[0][0]);
    assertEquals("", model[1][0]);
  }

  @Test
  public void undoAfterDelete2() throws Exception {
    setup();
    edit(0, 0, "a");
    edit(0, 1, "b");
    edit(0, 2, "c");
    editor.undo();
    editor.undo();
    editor.deleteY(1, 1);
    editor.redo();
    editor.redo();
    assertEquals("a", model[0][0]);
    assertEquals("c", model[1][0]);
    assertEquals("", model[2][0]);
  }

  @Test
  public void undoAfterInsert() throws Exception {
    setup();
    edit(0, 0, "a");
    edit(0, 1, "b");
    insertY(1);
    editor.insertY(1, 1);
    model[1][0] = "";
    model[2][0] = "b";
    edit(0, 1, "c");
    assertEquals("c", model[1][0]);
    assertEquals("b", model[2][0]);
    editor.undo();
    assertEquals("", model[1][0]);
    assertEquals("b", model[2][0]);
    editor.undo();
    assertEquals("", model[2][0]);
  }

  private void setup() {
    matrix = new Matrix(new Shell(), SWT.NONE);
    matrix.getAxisX().getBody().setCount(3);
    matrix.getAxisY().getBody().setCount(3);
    editor = new IntZoneEditorWithHistory(matrix.getBody()) {
      @Override
      public boolean setModelValue(Integer indexX, Integer indexY, Object value) {
        Object oldValue = getModelValue(indexX, indexY);
        // Set new value
        model[indexY.intValue()][indexX.intValue()] = value.toString();

        addEditHistory(indexX, indexY, oldValue, value);
        return true;
      }
      @Override
      public Object getModelValue(Integer indexX, Integer indexY) {
        return model[indexY.intValue()][indexX.intValue()];
      }
    };
  }

  private void edit(int x, int y, String value) {
    Text text = (Text) editor.activate(x, y);
    text.setText(value);
    editor.apply(text);
  }

  private void deleteY(int y) {
    String[][] model2 = new String[model.length-1][];
    if (y > 0) System.arraycopy(model, 0, model2, 0, y);
    if (y < model2.length) System.arraycopy(model, y+1, model2, y, model2.length - y);
    model = model2;
    matrix.getAxisY().getBody().delete(y, y);
  }

  private void insertY(int y) {
    String[][] model2 = new String[model.length+1][];
    if (y > 0) System.arraycopy(model, 0, model2, 0, y);
    if (y < model.length) System.arraycopy(model, y, model2, y+1, model.length - y);
    model2[y] = new String[] {"", "", ""};
    model = model2;
    matrix.getAxisY().getBody().insert(y, 1);
  }
}
