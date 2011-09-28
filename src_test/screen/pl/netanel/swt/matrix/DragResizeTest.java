package pl.netanel.swt.matrix;

import static org.junit.Assert.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class) 
@SuppressWarnings({"unchecked", "rawtypes"}) 
public class  DragResizeTest extends SwtTestCase {
  
  /**
   * Bug regression test. The scroll bar disappeared when the column was not trimmed.
   * @throws Exception
   */
  @Test public void resizeMakeTheNextColumnNotTrimmed() throws Exception {
    final Matrix matrix = new Matrix(shell, SWT.H_SCROLL);
//    listenToAll(matrix);
    matrix.getAxisY().getBody().setCount(0);
    matrix.getAxisY().getHeader().setVisible(true);
    matrix.getAxisX().getBody().setCount(5);
    matrix.getAxisX().getBody().setDefaultMoveable(true);
    
    matrix.getAxisX().getBody().addControlListener(new ControlAdapter() {
      @Override
      public void controlResized(ControlEvent e) {
        //System.out.println(display.getCursorLocation().x);
      }
    });
    
    shell.setBounds(200, 200, 400, 300);
    shell.open();
    
    processEvents();
    Rectangle bounds1 = matrix.getHeaderX().getCellBounds(0, 0);
    Point p1 = matrix.toDisplay(bounds1.x + bounds1.width, bounds1.y + 5);
    Point p2 = new Point(400, p1.y);
    Point p3 = new Point(442, p1.y);
    click(p1);
    dragAndDrop(SWT.BUTTON1, p1, p2, p3);
    assertTrue(matrix.getHorizontalBar().getVisible());
    
//    pause();
//    Point p4 = new Point(452, p1.y);
//    dragAndDrop(SWT.BUTTON1, p3, p4);
//    show();
    
  }
}
