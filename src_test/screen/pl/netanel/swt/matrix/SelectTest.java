package pl.netanel.swt.matrix;

import static org.junit.Assert.assertEquals;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
    assertColor(matrix.getBackground(), bounds.x+2, bounds.y+2);
  }
  
  @Test public void notifyWhenBodySelectedByFocusKey() throws Exception {
    Matrix matrix = createMatrix();
    Zone body = matrix.getBody();
    ZoneSelectionCounter counter = new ZoneSelectionCounter(body);
    
    press(SWT.ARROW_RIGHT);
    assertEquals(1, body.getSelectedCount().intValue());
    assertEquals(1, counter.count);
  }
  
  @Test public void notifiedWhenColumnsSelectedByShiftClick() throws Exception {
    Matrix matrix = createMatrix();
    Zone columnHeader = matrix.getColumnHeader();
    
    click(matrix, columnHeader.getCellBounds(0, 0), SWT.BUTTON1);
    
    // ZoneSelectionCounter zoneCounter = new ZoneSelectionCounter(columnHeader);
    SectionSelectionCounter sectionCounter = new SectionSelectionCounter(matrix.getAxis1().getBody());
    
    click(matrix, columnHeader.getCellBounds(0, 1), SWT.MOD2 | SWT.BUTTON1);
    assertEquals(1, sectionCounter.count);
  }
  
  @Test public void notifiedWhenColumnsSelectedByShiftClickAfterReorder() throws Exception {
    Matrix matrix = createMatrix();
    matrix.getAxis1().getBody().setDefaultMoveable(true);
    Zone columnHeader = matrix.getColumnHeader();
    focusControl = matrix;
    
    // Reorder
    click(matrix, columnHeader.getCellBounds(0, 0), SWT.BUTTON1);
    dragAndDrop(columnHeader.getCellBounds(0, 0), columnHeader.getCellBounds(0, 4));
    assertEquals(0, matrix.getAxis1().getItemAt(5).getIndex().intValue());
    
    SectionSelectionCounter sectionCounter = new SectionSelectionCounter(matrix.getAxis1().getBody());
    ZoneSelectionCounter zoneCounter = new ZoneSelectionCounter(columnHeader);
    br();
    click(matrix, columnHeader.getCellBounds(0, 3), SWT.MOD2 | SWT.BUTTON1);
    SwtTestCase.breakFlag = false;
    assertEquals(3, columnHeader.getSelectedCount().intValue());
    assertEquals(1, sectionCounter.count);
    assertEquals(1, zoneCounter.count);
    
    
    click(matrix, columnHeader.getCellBounds(0, 1), SWT.MOD2 | SWT.BUTTON1);
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
  
  @Test public void unselectColumnOnHeaderCtrlClick() throws Exception {
    Matrix matrix = createMatrix();
    Zone columnHeader = matrix.getColumnHeader();
    Rectangle bounds = columnHeader.getCellBounds(0, 1);
    
    matrix.getAxis1().getBody().setSelected(0, 2, true);
    assertEquals(3, matrix.getAxis1().getBody().getSelectedCount());
    
    click(matrix, bounds, SWT.MOD1);
    
    // Axis item and header cell should be selected
    assertEquals(2, matrix.getAxis1().getBody().getSelectedCount());
    assertEquals(2, columnHeader.getSelectedCount().intValue());
  }
  
  @Test public void unselectColumnOnHeaderCtrlDrag() throws Exception {
    Matrix matrix = createMatrix();
    Zone columnHeader = matrix.getColumnHeader();
    Rectangle bounds1 = columnHeader.getCellBounds(0, 1);
    Rectangle bounds2 = columnHeader.getCellBounds(0, 3);
    
    matrix.getAxis1().getBody().setSelected(0, 2, true);
    
    dragAndDrop(SWT.MOD1, bounds1, bounds2);
    
    // Axis item and header cell should be selected
    assertEquals(1, matrix.getAxis1().getBody().getSelectedCount());
    assertEquals(1, columnHeader.getSelectedCount().intValue());
  }
  
  @Test public void unselectColumnOnHeaderCtrlClickAfterSelectCtrlClick() throws Exception {
    Matrix matrix = createMatrix();
    Zone columnHeader = matrix.getColumnHeader();
    Rectangle bounds = columnHeader.getCellBounds(0, 0);
    
    click(matrix, bounds, SWT.MOD1);
    assertEquals(1, matrix.getAxis1().getBody().getSelectedCount());
    
    click(matrix, bounds, SWT.MOD1);
    
    // Axis item and header cell should be selected
    assertEquals(0, matrix.getAxis1().getBody().getSelectedCount());
    assertEquals(0, columnHeader.getSelectedCount().intValue());
  }
  
  @Test public void selectColumnOnHeaderCtrlDrag() throws Exception {
    Matrix matrix = createMatrix();
    Zone columnHeader = matrix.getColumnHeader();
    Section columnSection = matrix.getAxis1().getBody();
    Rectangle bounds1 = columnHeader.getCellBounds(0, 1);
    Rectangle bounds2 = columnHeader.getCellBounds(0, 2);
    
    SectionSelectionCounter counter = new SectionSelectionCounter(columnSection);
    dragAndDrop(SWT.MOD1, bounds1, bounds2, bounds1);
    assertEquals(3, counter.count);
    
    // Axis item and header cell should be selected
    assertEquals(1, columnSection.getSelectedCount());
    assertEquals(1, columnHeader.getSelectedCount().intValue());
  }
  
  
  /**
   * Should not send the resize event on the click. 
   * @throws Exception
   */
  @Test public void selectColumnOnHeaderClickAfterResize() throws Exception {
    Matrix matrix = createMatrix();
    matrix.getAxis1().getBody().setDefaultResizable(true);
    Zone columnHeader = matrix.getColumnHeader();
    Rectangle bounds = columnHeader.getCellBounds(0, 0);
    int x = bounds.x + bounds.width;
    
    click(matrix, bounds);
    dragAndDrop(0,  matrix.toDisplay(x, bounds.y), matrix.toDisplay(x + 10, bounds.y));
    SectionResizeCounter counter = new SectionResizeCounter(matrix.getAxis1().getBody());
    br();
    click(matrix, bounds);
    assertEquals(0, counter.count);
  }
  
  
  @Test
  public void selectBodyAppend() throws Exception {
    Matrix matrix = createMatrix();
    Zone body = matrix.getBody();
    Rectangle bounds = matrix.getBody().getCellBounds(2, 2);
    
    click(matrix, bounds, SWT.MOD1 | SWT.BUTTON1);
    
    assertEquals(2, matrix.getBody().getSelectedCount().intValue());
    assertColor(body.getSelectionBackground(), bounds.x+2, bounds.y+2);
  }
  
  
  static class ZoneSelectionCounter implements SelectionListener {
    int count;
    public ZoneSelectionCounter(Zone zone) {
      zone.addSelectionListener(this);
    }
    @Override public void widgetSelected(SelectionEvent e) {
      count++;
    }
    @Override public void widgetDefaultSelected(SelectionEvent e) {
      count++;      
    }
  }
  
  static class SectionSelectionCounter implements SelectionListener {
    int count;
    public SectionSelectionCounter(Section section) {
      section.addSelectionListener(this);
    }
    @Override public void widgetSelected(SelectionEvent e) {
      count++;
    }
    @Override public void widgetDefaultSelected(SelectionEvent e) {
      count++;      
    }
  }
  
  static class SectionResizeCounter implements ControlListener {
    int count;
    public SectionResizeCounter(Section section) {
      section.addControlListener(this);
    }
    @Override public void controlResized(ControlEvent e) {
      count++;
    }
    @Override public void controlMoved(ControlEvent e) {}
  }
}
