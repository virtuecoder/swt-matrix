package pl.netanel.swt.matrix.snippets;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.AxisItem;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Section;

/**
 * Drag and drop files from external window.
 */
public class S0910_DragAndDropFilesFromExternalWindow {
  public static void main(String[] args) {
    Shell shell = new Shell();
    shell.setText(title);
    shell.setLayout(new FillLayout());
    shell.setBounds(400, 200, 600, 400);

    final List<File> files = new ArrayList<File>();

    final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);
    final Axis<Integer> axisX = matrix.getAxisX();
    final Axis<Integer> axisY = matrix.getAxisY();

    final Section<Integer> bodyX = axisX.getBody();
    bodyX.setCount(3);
    bodyX.setCellWidth(0, 300);
    bodyX.setCellWidth(2, 100);

    axisX.getHeader().setVisible(true);
    axisX.getBody().setDefaultMoveable(true);
    axisY.getHeader().setVisible(true);

    axisX.setFocusItemEnabled(false);
    axisY.setFocusItemEnabled(false);

    matrix.getBody().replacePainter(
      new Painter<Integer, Integer>(Painter.NAME_CELLS) {
        @Override
        public void setupSpatial(Integer indexX, Integer indexY) {
          File file = files.get(indexY);
          text = indexX == 0 ? file.getAbsolutePath() : indexX == 1 ? Long
            .toString(file.length()) : indexX == 2 ? SimpleDateFormat
            .getInstance().format(file.lastModified()) : null;
        }
      });

    matrix.getHeaderX().replacePainter(
      new Painter<Integer, Integer>(Painter.NAME_CELLS) {
        @Override
        public void setupSpatial(Integer indexX, Integer indexY) {
          text = indexX == 0 ? "Path" : indexX == 1 ? "Size"
            : indexX == 2 ? "Last Modified" : null;
        }
      });

    // new ButtonCellBehavior<Integer, Integer>(matrix.getHeaderX());
    // new ButtonCellBehavior<Integer, Integer>(matrix.getHeaderY());
    // new ButtonCellBehavior<Integer, Integer>(matrix.getTopLeft());

    final Painter<Integer, Integer> dragPainterY = matrix
      .getPainter(Painter.NAME_DRAG_ITEM_Y);

    DropTarget dt = new DropTarget(matrix, DND.DROP_DEFAULT | DND.DROP_MOVE);
    dt.setTransfer(new Transfer[] { FileTransfer.getInstance() });
    dt.addDropListener(new DropTargetAdapter() {
      @Override
      public void drop(DropTargetEvent event) {
        FileTransfer ft = FileTransfer.getInstance();
        if (ft.isSupportedType(event.currentDataType)) {
          Point p = matrix.toControl(event.x, event.y);
          AxisItem<Integer> item = axisY.getItemByViewportDistance(p.y);
          int position = 0;
          if (item != null) {
            int compare = axisY.compare(item.getSection(), axisY.getBody());
            if (compare < 0) {
              position = 0;
            }
            else if (compare > 0) {
              position = files.size() - 1;
            }
            else {
              int[] bound = axisY.getCellBound(item);
              position = axisY.getBody().getOrder(item.getIndex());
              if (p.y > bound[0] + bound[1] / 2) {
                position++;
              }
            }
          }

          String[] fileNames = (String[]) event.data;
          for (String fileName : fileNames) {
            files.add(position, new File(fileName));
          }
          matrix.getAxisY().getBody().setCount(files.size());
          matrix.refresh();
        }
      }

      @Override
      public void dragEnter(DropTargetEvent event) {
        super.dragEnter(event);
        dragPainterY.setEnabled(true);
        matrix.redraw();
      }

      @Override
      public void dragLeave(DropTargetEvent event) {
        super.dragLeave(event);
        dragPainterY.setEnabled(false);
        matrix.redraw();
      }

      @Override
      public void dragOver(DropTargetEvent event) {
        dragPainterY.setData(event);
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
  static final String title = "Drag and drop files";
  static final String instructions = "";
  static final String code = "0910";
}