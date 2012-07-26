package pl.netanel.swt.matrix;

import static org.junit.Assert.assertEquals;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@SuppressWarnings({"rawtypes", "unchecked"})
@RunWith(JUnit4.class) 
public class LayoutScrollTest {
  @Test public void initialPosition() throws Exception {
    Shell shell = new Shell();
    shell.setLayout(new FillLayout());
    
    Matrix matrix = new Matrix(shell, SWT.H_SCROLL);
    matrix.axisY.getBody().setCount(10);
    matrix.axisX.getBody().setCount(4);
    matrix.axisX.getBody().setCellWidth(0, 300);
    AxisLayout layout = matrix.layoutX;
    layout.setViewportSize(300);
    layout.compute();
    assertEquals(0, layout.getScrollMin());
    assertEquals(4, layout.getScrollMax());
    assertEquals(1, layout.getScrollThumb());
    assertEquals(0, layout.getScrollPosition());
  
  }  
  @Test public void scrollNextPosition() throws Exception {
    Shell shell = new Shell();
    shell.setLayout(new FillLayout());
    
    Matrix matrix = new Matrix(shell, SWT.H_SCROLL);
    matrix.axisY.getBody().setCount(10);
    matrix.axisX.getBody().setCount(4);
    matrix.axisX.getBody().setCellWidth(0, 300);
    AxisLayout layout = matrix.layoutX;
    layout.setViewportSize(300);
    layout.compute();
    
    layout.moveFocusItem(Move.NEXT);
    layout.compute();

    assertEquals(0, layout.getScrollMin());
    assertEquals(4, layout.getScrollMax());
    assertEquals(3, layout.getScrollThumb());
    assertEquals(1, layout.getScrollPosition());
  }
  
  @Test public void thumbSizeWhenNoTrim() throws Exception {
    Shell shell = new Shell();
    shell.setLayout(new FillLayout());
    
    Matrix matrix = new Matrix(shell, SWT.H_SCROLL);
    matrix.axisX.getBody().setCount(10);
    matrix.axisY.getBody().setCount(1);
    matrix.axisX.getBody().setDefaultCellWidth(100);
    AxisLayout layout = matrix.layoutX;
    
    layout.setViewportSize(303);
    layout.compute();
    assertEquals(2, layout.getScrollThumb());
    layout.setViewportSize(304);
    layout.compute();
    assertEquals(3, layout.getScrollThumb());
    layout.setViewportSize(305);
    layout.compute();
    assertEquals(3, layout.getScrollThumb());
    
//    TestUtil.showMatrix(matrix);
    
  }
  
  @Test public void thumbSizeWhenNoTrimVariedLineWidth() throws Exception {
    Shell shell = new Shell();
    shell.setLayout(new FillLayout());
    
    Matrix matrix = new Matrix(shell, SWT.H_SCROLL);
    matrix.axisY.getHeader().setVisible(true);
    matrix.axisX.getBody().setCount(10);
    matrix.axisY.getBody().setCount(1);
    matrix.axisX.getBody().setDefaultCellWidth(100);
    matrix.axisX.getBody().setLineWidth(3, 5);
    AxisLayout layout = matrix.layoutX;
    
    layout.setViewportSize(303);
    layout.compute();
    assertEquals(2, layout.getScrollThumb());
    layout.setViewportSize(304);
    layout.compute();
    assertEquals(3, layout.getScrollThumb());
    layout.setViewportSize(305);
    layout.compute();
    assertEquals(3, layout.getScrollThumb());
    
//    TestUtil.showMatrix(matrix);
    
  }  
}
