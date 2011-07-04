package pl.netanel.swt.matrix;

import static org.junit.Assert.assertEquals;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.junit.Test;

public class SelectTest extends SwtTestCase {
  
  @Test public void initialSelection() throws Exception {
    Matrix matrix = createMatrix();
    Zone body = matrix.getBody();
    Zone columnHeader = matrix.getColumnHeader();
    Rectangle bounds = body.getCellBounds(0, 0);
    
    // Axis item and header cell should not be selected
    assertEquals(0, matrix.getAxis1().getBody().getSelectedCount());
    assertEquals(0, columnHeader.getSelectedCount().intValue());
    
    // Body cell should be selected
    assertEquals(1, body.getSelectedCount().intValue());
    
    // Body cell should not be highlighted    
    assertColor(body.getDefaultBackground(), bounds.x+2, bounds.y+2);
    
  }
  
  @Test public void selectBodyByShiftClick() throws Exception {
    Matrix matrix = createMatrix();
    Zone body = matrix.getBody();
    Rectangle bounds = body.getCellBounds(0, 1);
    
    click(matrix, bounds, SWT.MOD2 | SWT.BUTTON1);
    
    // Body cell should be highlighted
    assertColor(body.getSelectionBackground(), bounds.x+2, bounds.y+2);
    
    // Two body cells should be selected
    assertEquals(2, body.getSelectedCount().intValue());
   
  }
  
  @Test public void selectColumnOnHeaderClick() throws Exception {
    Matrix matrix = createMatrix();
    Zone body = matrix.getBody();
    Zone columnHeader = matrix.getColumnHeader();
    Rectangle bounds = body.getCellBounds(0, 0);
    Rectangle bounds2 = columnHeader.getCellBounds(0, 0);
    
    click(matrix, bounds2);
    
    // Axis item and header cell should be selected
    assertEquals(1, matrix.getAxis1().getBody().getSelectedCount());
    assertEquals(1, columnHeader.getSelectedCount().intValue());
    
    // Body cell should be highlighted
    assertColor(body.getSelectionBackground(), bounds.x+2, bounds.y+2);
    
    // Column header cell should be highlighted
    assertColor(columnHeader.getSelectionBackground(), bounds2.x+2, bounds2.y+2);
  }
  
  @Test public void selectColumnOnHeaderClickOneRow() throws Exception {
    Matrix matrix = new Matrix(shell, 0);
    matrix.getAxis0().getHeader().setVisible(true);
    matrix.getAxis1().getHeader().setVisible(true);
    matrix.getAxis0().getBody().setCount(1);
    matrix.getAxis1().getBody().setCount(1);
    shell.open();
    processEvents();
    Zone body = matrix.getBody();
    Zone columnHeader = matrix.getColumnHeader();
    Rectangle bounds = body.getCellBounds(0, 0);
    Rectangle bounds2 = columnHeader.getCellBounds(0, 0);
    
    click(matrix, bounds2);
    
    // Axis item and header cell should be selected
    assertEquals(1, matrix.getAxis1().getBody().getSelectedCount());
    assertEquals(1, columnHeader.getSelectedCount().intValue());
    
    // Body cell should be highlighted
    assertColor(body.getSelectionBackground(), bounds.x+2, bounds.y+2);
    
    // Column header cell should be highlighted
    assertColor(columnHeader.getSelectionBackground(), bounds2.x+2, bounds2.y+2);
  }
  
  @Test
  public void selectAppend() throws Exception {
    Matrix matrix = createMatrix();
    Zone body = matrix.getBody();
    Rectangle bounds = matrix.getBody().getCellBounds(2, 2);
    
    click(matrix, bounds, SWT.MOD1 | SWT.BUTTON1);
    
    assertEquals(2, matrix.getBody().getSelectedCount().intValue());
    assertColor(body.getSelectionBackground(), bounds.x+2, bounds.y+2);
  }
  
  Matrix createMatrix() {
    Matrix matrix = new Matrix(shell, 0);
//    listenToAll(matrix);
    matrix.getAxis0().getHeader().setVisible(true);
    matrix.getAxis1().getHeader().setVisible(true);
    matrix.getAxis0().getBody().setCount(5);
    matrix.getAxis1().getBody().setCount(5);
    shell.open();
    processEvents();
    return matrix;
  }
}
