package pl.netanel.swt.matrix;

import static junit.framework.Assert.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.junit.Test;

public class MatrixTest {

  private Matrix<Integer, Integer> matrix;

  @Test
  public void computeSizeEmpty() throws Exception {
    matrix = new Matrix<Integer, Integer>(new Shell(), SWT.V_SCROLL);
    assertSize(0, 0, 0, 0, 0, 0);
    assertSize(100, 100, 0, 0, 0, 0);
    assertSize(100, 100, -1, -1, 0, 0);
    assertSize(100, 100, 50, 50, 50, 50);
    assertSize(100, 100, 100, 100, 100, 100);
  }

  @Test
  public void computeSizeNotEmpty() throws Exception {
    matrix = new Matrix<Integer, Integer>(new Shell(), SWT.NONE);
    matrix.getAxisX().getBody().setCount(10);
    matrix.getAxisY().getBody().setCount(10);

    assertSize(0, 0, 0, 0, 0, 0);
    assertSize(100, 100, 0, 0, 0, 0);
    assertSize(100, 100, -1, -1, 511, 171);
    assertSize(100, 100, 50, 50, 50, 50);
    assertSize(100, 100, 100, 100, 100, 100);
  }

  @Test
  public void computeSizeWithScrollBar() throws Exception {
    matrix = new Matrix<Integer, Integer>(new Shell(), SWT.V_SCROLL | SWT.H_SCROLL);
    matrix.getAxisX().getBody().setCount(10);
    matrix.getAxisY().getBody().setCount(10);

    assertSize(100, 100, 100, -1, 100, 171+17);
//    Shell shell = matrix.getShell();
//    matrix.setSize(100, 171+17);
//    matrix.getShell().open();
//    while (!shell.isDisposed()) {
//      if (!shell.getDisplay().readAndDispatch()) {
//        shell.getDisplay().sleep();
//      }
//    }
  }

  @Test
  public void setSizeWithoutScrollBar() throws Exception {
    matrix = new Matrix<Integer, Integer>(new Shell(), SWT.V_SCROLL | SWT.H_SCROLL);
    matrix.getAxisX().getBody().setCount(10);
    matrix.getAxisY().getBody().setCount(10);

    //assertSize(10*51+1, 10*17+1, -1, -1, 10*51+1, 10*17+1);
    matrix.setSize(10*51+1, 10*17+1);

    assertFalse(matrix.getAxisX().layout.isScrollRequired());
    assertFalse(matrix.getAxisY().layout.isScrollRequired());

//    Shell shell = matrix.getShell();
//    matrix.getShell().open();
//    while (!shell.isDisposed()) {
//      if (!shell.getDisplay().readAndDispatch()) {
//        shell.getDisplay().sleep();
//      }
//    }
  }

  void assertSize(int viewportX, int viewportY, int wHint, int hHint, int matrixX, int matrixY) {
    matrix.getAxisX().layout.setViewportSize(viewportX);
    matrix.getAxisY().layout.setViewportSize(viewportY);
    Point size = matrix.computeSize(wHint, hHint);
    assertEquals(matrixX, size.x);
    assertEquals(matrixY, size.y);
  }

}
