package pl.netanel.swt.matrix.snippets;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Zone;
import pl.netanel.swt.matrix.ZoneEditor;
import pl.netanel.util.Preconditions;

/**
 * Simplest zone
 */
public class S0440_EditHistory_Undo_Redo {

  public static void main(String[] args) throws IOException {
    Display display = Display.getDefault();
    Shell shell = new Shell(display);
    shell.setText(title);
    shell.setLayout(new FillLayout());

    // Data model
    final ArrayList<Object[]> data = new ArrayList<Object[]>();
    data.add(new Object[] { "a", true, new Date() });
    data.add(new Object[] { true, false, "Monday" });
    data.add(new Object[] { "c", "Sunday", "b" });

    // Matrix
    final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);

    matrix.getAxisY().getBody().setCount(data.size());
    matrix.getAxisX().getBody().setCount(3);

    // Data painter
    matrix.getBody().replacePainterPreserveStyle(
      new Painter<Integer, Integer>(Painter.NAME_CELLS) {
        @Override
        public void setupSpatial(Integer indexX, Integer indexY) {
          Object value = data.get(indexY.intValue())[indexX.intValue()];
          text = value == null ? "" : value.toString();
        }
      });

    // Body editor
    final ZoneEditorWithHistory<Integer, Integer> editor =
        new ZoneEditorWithHistory<Integer, Integer>(matrix.getBody())
    {
      @Override
      public Object getModelValue(Integer indexX, Integer indexY) {
        return data.get(indexY.intValue())[indexX.intValue()];
      }

      @Override
      public boolean setModelValue(Integer indexX, Integer indexY, Object value) {
        boolean isValid = true; // validation logic here
        if (isValid) {
          Object oldValue = getModelValue(indexX, indexY);
          // Set new value
          data.get(indexY.intValue())[indexX.intValue()] = value;

          addEditHistory(indexX, indexY, oldValue, value);
          return true;
        }
        return false;
      }
    };
    editor.setBulkEditAtomic(true);
    editor.setEditHistoryLimit(Integer.MAX_VALUE);

    matrix.addListener(SWT.KeyDown, new Listener() {
      @Override
      public void handleEvent(Event e) {
        if ((e.stateMask & SWT.CTRL) != 0) {
          switch (e.keyCode) {
          case 'z': editor.undo(); break;
          case 'y': editor.redo(); break;
          }
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

  public static class ZoneEditorWithHistory<X extends Number, Y extends Number> extends ZoneEditor<X, Y> {

    protected ArrayList<EditLogEntry<X, Y>> history;
    private int bulk;
    private int historyLimit;
    protected int historyIndex;
    private boolean isBulkEditAtomic;
    private boolean wasRecorded;

    public ZoneEditorWithHistory(Zone<X, Y> zone) {
      super(zone);
      history = new ArrayList<EditLogEntry<X, Y>>();
      historyIndex = -1;
    }

    /**
     * Sets the <code>null</code> value to the selected cells.
     * <p>
     * @return <code> true if the delete was successful for all the selected values.
     * Atomicity: Model changes done with the method will be rolled back when
     * <code>isBulkEditAtomic</code> returns <code>true</code> and:<ul>
     * <li>one of the cells does not accept null value (<code>setModelValue</code> returning false)
     * <li>the number of changes during the bulk operation exceeds the value of
     * <code>getEditHistoryLimit()</code>. A runtime exception is thrown is such case as well.
     * <li>there is an exception during the process
     * </ul>
     */
    @Override
    public boolean delete() {
      try {
        setBulkEdit(true);
        if (!super.delete() && isBulkEditAtomic) {
          undo();
          return false;
        }
        return true;
      }
      catch (Exception e) {
        if (isBulkEditAtomic) {
          undo();
        }
        throw new RuntimeException(e);
      }
      finally {
        setBulkEdit(false);
      }

    }

    /**
     * Pastes from the clipboard to the zone starting from the focus cell.
     * <p>
     * Values in the clipboard exceeding the existing zone capacity are ignored.
     * <p>
     * Atomicity: Model changes done with the method will be rolled back when
     * <code>isBulkEditAtomic</code> returns <code>true</code> and:<ul>
     * <li>one of the cells does not accept
     * the pasted value (<code>setModelValue</code> returning false)
     * <li>the number of changes during the bulk operation exceeds the value of
     * <code>getEditHistoryLimit()</code>. A runtime exception is thrown is such case as well.
     * <li>there is an exception during the process
     * </ul>
     * @return <code> true if the delete was successful for all the selected values.
     */
    @Override
    public boolean paste() {
      try {
        setBulkEdit(true);
        if (!super.paste() && isBulkEditAtomic) {
          undo();
          return false;
        }
        return true;
      }
      catch (Exception e) {
        if (isBulkEditAtomic) {
          undo();
        }
        throw new RuntimeException(e);
      }
      finally {
        setBulkEdit(false);
      }
    }

    /**
     * Starts bulk edit if the <code>state</code> is <code>true</code>, or stops
     * it otherwise.
     * <p>
     * All the changes recorded in the edit history between starting the bulk edit and stopping it
     * can be reverted or applied again (undo/redo) only together as one operation.
     *
     * @param state the new bulk edit state
     *
     * @see #undo()
     * @see #redo()
     */
    public void setBulkEdit(boolean state) {
      bulk = state ? (int) System.currentTimeMillis() : 0;
    }

    /**
     * Makes all the bulk edit operations atomic.
     * <p>
     * If set to <code>true</code> and <code>setModelValue</code> for any cell
     * returns <code>false</code> then all the previous changes during bulk edit operation
     * will be rolled back. Bulk edit operations are paste, cut, delete of cell selection.
     *
     * @param state new bulk edit atomicity state to set
     */
    public void setBulkEditAtomic(boolean state) {
      isBulkEditAtomic = state;
    }

    /**
     * Returns <code>true</code> if bulk editor operation should be atomic
     *
     * @return bulk edit atomicity state
     *
     * @see #setBulkEditAtomic(boolean)
     */
    public boolean isBulkEditAtomic() {
      return isBulkEditAtomic;
    }

    /**
     * Sets the limit of the history of modifications to the model done with this
     *  In case of the need to put more entries in the history than the limit the oldest
     * entries will be removed to make place for the new ones.
     * <p>
     * Default is 0 and makes the number of entries limited only by available memory.
     *
     * @param limit maximum number of entries in the history of edit modifications
     */
    public void setEditHistoryLimit(int limit) {
      historyLimit = limit;
    }

    /**
     * Returns the limit of the history of modifications to the model done with
     * this
     *
     * @return the length of the history of modifications to the model done with
     * this editor
     */
    public int getEditHistoryLimit() {
      return historyLimit;
    }

    /**
     * Reverts the latest change done with this
     * <p>
     * If the latest change was done in bulk mode than all the adjacent bulk changes are reverted
     * as well. If there is nothing to revert the method does nothing.
     *
     * @see #isBulkEditAtomic
     */
    public void undo() {
      boolean moved = false;
      int bulk = 0;
      while (historyIndex >= 0) {
        EditLogEntry<X, Y> entry = history.get(historyIndex);
        if (!moved || bulk != 0 && bulk == entry.bulk) {
          moved = true;
          bulk = entry.bulk;
          wasRecorded = true;
          setModelValue(entry.indexX, entry.indexY, entry.oldValue);
          wasRecorded = false;
          historyIndex--;
        }
        else break;
      }
      if (moved) {
        redraw();
      }
    }

    /**
     * Applies again the latest change done with this editor that was reverted with {@link #undo()}.
     * <p>
     * If the latest reverted change was done in bulk mode than all the adjacent bulk changes will
     * be applied again too. If there is nothing to apply again the method does nothing.
     *
     * @see #isBulkEditAtomic
     */
    public void redo() {
      boolean moved = false;
      int bulk = 0;
      while (historyIndex < history.size() - 1) {
        EditLogEntry<X, Y> entry = history.get(historyIndex+1);
        if (!moved || bulk != 0 && bulk == entry.bulk) {
          moved = true;
          bulk = entry.bulk;
          setModelValue(entry.indexX, entry.indexY, entry.newValue);
          historyIndex++;
        }
        else break;
      }
      if (moved) {
        redraw();
      }
    }

    /**
     * Adds an entry to the history of model changes done with this
     * <p>
     * If <code>isBulkEditAtomic</code> returns <code>true</code> and the number of changes during
     * the bulk operation exceeds the value of <code>getEditHistoryLimit()</code> than all the changes
     * will be rolled back. A runtime exception is thrown is such case as well.
     *
     * @param indexX cell index on the horizontal axis
     * @param indexY cell index on the vertical axis
     * @param oldValue value of the cell before modification
     * @param newValue value of the cell after modification
     */
    public void addEditHistory(X indexX, Y indexY, Object oldValue, Object newValue) {
      if (wasRecorded) return;
      if (historyLimit > 0 && historyIndex == historyLimit - 1) {
        if (isBulkEditAtomic && bulk == history.get(0).bulk) {
          setModelValue(indexX, indexY, oldValue);
          undo();
          throw new RuntimeException(MessageFormat.format(
              "Number of changes in atomic bulk edit operation exceeds the history limit: {0}",
              historyLimit ));
        }
        history.remove(0);
      }
      else historyIndex++;
      history.add(historyIndex, new EditLogEntry<X, Y>(indexX, indexY, oldValue, newValue, bulk));
    }

    /**
     * Removes all the entries between <code>fromIndex</code> (inclusive) and
     * {@link #getEditHistorySize()} (exclusive) from the history of model modifications.
     * <p>
     * It can be useful for preventing undo in case when it does no makes, for example after
     * changing the source of model data.
     *
     * @param fromIndex index from which the edit history should be cleared forward.
     */
    public void clearEditHistory(int fromIndex) {
      Preconditions.checkPositionIndex(fromIndex, history.size());
      historyIndex = fromIndex-1;
      if (fromIndex == 0) history.clear();
      else {
        for (int i = history.size(); i-- > fromIndex;) {
          history.remove(i);
        }
      }
    }

    /**
     * Returns the number of entries in the edit history log.
     * @return the number of entries in the edit history log
     */
    public int getEditHistorySize() {
      return history.size();
    }

    /**
     * Returns the current index of the edit history log pointing at the entry
     * that will be used for the next {@link #undo()} operation.
     * @return current index in the edit history log
     */
    public int getEditHistoryIndex() {
      return historyIndex;
    }

    static class EditLogEntry<X extends Number, Y extends Number> {
      X indexX;
      Y indexY;
      Object oldValue, newValue;
      int bulk;
      public EditLogEntry(X indexX, Y indexY, Object oldValue, Object newValue, int bulk) {
        this.indexX = indexX;
        this.indexY = indexY;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.bulk = bulk;
      }
      public EditLogEntry(ArrayList<EditLogEntry<X, Y>> log) {
        newValue = log;
      }
      @Override
      public String toString() {
        return indexX + ", " + indexY + ", " + oldValue + ", " + newValue + ", " + bulk;
      }
    }
  }

  public static class IntZoneEditorWithHistory extends ZoneEditorWithHistory<Integer, Integer> {
    public IntZoneEditorWithHistory(Zone<Integer, Integer> zone) {
      super(zone);
    }

    public void deleteX(Integer start, Integer end) {
      // Delete from history
      ListIterator<EditLogEntry<Integer, Integer>> it = history.listIterator();
      while (it.hasNext()) {
        EditLogEntry<Integer, Integer> entry = it.next();
        if (start <= entry.indexX && entry.indexX <= end) {
          it.remove();
          if (historyIndex >= it.nextIndex()) historyIndex--;
        } else if (entry.indexX > end) {
          entry.indexX--;
        }
      }
    }

    /**
     * Adjusts the indexes in the history when the items have been deleted in the model.
     * @param start first index of the range of items
     * @param end last index of the range of items
     */
    public void deleteY(Integer start, Integer end) {
      ListIterator<EditLogEntry<Integer, Integer>> it = history.listIterator();
      while (it.hasNext()) {
        EditLogEntry<Integer, Integer> entry = it.next();
        if (start <= entry.indexY && entry.indexY <= end) {
          it.remove();
          if (historyIndex >= it.nextIndex()) historyIndex--;
        } else if (entry.indexY > end) {
          entry.indexY--;
        }
      }
    }

    public void insertX(Integer target, Integer count) {
      // Delete from history
      ListIterator<EditLogEntry<Integer, Integer>> it = history.listIterator();
      while (it.hasNext()) {
        EditLogEntry<Integer, Integer> entry = it.next();
        if (target <= entry.indexX) {
          entry.indexX += count;
          if (historyIndex >= it.nextIndex()) historyIndex += count.intValue();
        }
      }
    }

    public void insertY(Integer target, Integer count) {
      // Delete from history
      ListIterator<EditLogEntry<Integer, Integer>> it = history.listIterator();
      while (it.hasNext()) {
        EditLogEntry<Integer, Integer> entry = it.next();
        if (target <= entry.indexY) {
          entry.indexY += count;
          if (historyIndex >= it.nextIndex()) historyIndex += count.intValue();
        }
      }
    }
  }

  // Meta data
  static final String title = "EditHistory Undo Redo";
  static final String instructions = "";
}