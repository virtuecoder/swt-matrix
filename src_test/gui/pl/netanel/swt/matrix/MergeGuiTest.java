package pl.netanel.swt.matrix;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
@SuppressWarnings({ "unchecked", "rawtypes" })
public class MergeGuiTest
  extends SwtTestCase {

  @Test public void merge() throws Exception {
    Matrix matrix = createMatrix();
    Zone body = matrix.getBody();
    body.setMerged(0,3,0,3);
    matrix.getAxisX().getBody().setHidden(1, true);
//    body.getSectionX().setHidden(0, true);
    matrix.refresh();
    matrix.update();
    processEvents();
    show();
  }

  @Test public void focusMovedOnHeaderClick() throws Exception {
    Matrix matrix = createMatrix();
    click(matrix, matrix.getHeaderX().getCellBounds(0, 0));
    assertEquals(0, matrix.getAxisY().getFocusItem().getIndex().intValue());
  }

}
