package pl.netanel.swt.matrix;

import static org.junit.Assert.assertEquals;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class) public class  SelectTest extends SwtTestCase {
  
  @Test public void initialSelection() throws Exception {
    Matrix matrix = createMatrix(); 
    Zone body = matrix.getBody();
    Zone columnHeader = matrix.getColumnHeader();
    Rectangle bounds = body.getCellBounds(0, 0);
    
    // Axis item and header cell should not be selected
    assertEquals(0, matrix.getAxisX().getBody().getSelectedCount());
    assertEquals(0, columnHeader.getSelectedCount().intValue());
    
    // Body cell should be not selected
    assertEquals(0, body.getSelectedCount().intValue());
    
    // Body cell should not be highlighted    
    assertColor(matrix.getBackground(), bounds.x+2, bounds.y+2);
  }
  
  @Test public void noNotificationOnBodyNavigationKey() throws Exception {
    Matrix matrix = createMatrix();
    Zone body = matrix.getBody();
    Rectangle bounds = body.getCellBounds(0, 0);

    press(SWT.ARROW_RIGHT);
    
    // Body cell should not be selected
    assertEquals(0, body.getSelectedCount().intValue());
    
    // Body cell should not be highlighted    
    assertColor(matrix.getBackground(), bounds.x+2, bounds.y+2);
  }
  
  @Test public void noNotificationOnBodyMouseClick() throws Exception {
    Matrix matrix = createMatrix();
    Zone body = matrix.getBody();
    Section columnSection = matrix.getAxisX().getBody();
    Rectangle bounds = body.getCellBounds(2, 2);
    
    ZoneSelectionCounter zoneCounter = new ZoneSelectionCounter(body);
    SectionSelectionCounter sectionCounter = new SectionSelectionCounter(columnSection);
    click(matrix, bounds);
    
    assertEquals(0, zoneCounter.count);
    assertEquals(0, sectionCounter.count);
    
    // Body cell should not be selected
    assertEquals(0, body.getSelectedCount().intValue());
    
    // Body cell should not be highlighted    
    assertColor(matrix.getBackground(), bounds.x+2, bounds.y+2);
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
  
  
  @Test public void unselectBodyByKeyFocusChange() throws Exception {
    Matrix matrix = createMatrix();
    Zone body = matrix.getBody();
    Rectangle bounds = body.getCellBounds(0, 0);
    body.setSelected(0, 2, 0, 2, true);
    
    press(SWT.ARROW_RIGHT);
    
    // Body cell should not be selected
    assertEquals(0, body.getSelectedCount().intValue());
    
    // Body cell should not be highlighted    
    assertColor(matrix.getBackground(), bounds.x+2, bounds.y+2);
  }
  
  @Test public void unselectBodyByClick() throws Exception {
    Matrix matrix = createMatrix();
    Zone body = matrix.getBody();
    Rectangle bounds = body.getCellBounds(0, 0);
    body.setSelected(0, 2, 0, 2, true);
    
    click(matrix, body.getCellBounds(3, 3));
    
    // Body cell should not be selected
    assertEquals(0, body.getSelectedCount().intValue());
    
    // Body cell should not be highlighted    
    assertColor(matrix.getBackground(), bounds.x+2, bounds.y+2);
  }
  
//  @Test public void notifyWhenBodySelectedByFocusKey() throws Exception {
//    Matrix matrix = createMatrix();
//    Zone body = matrix.getBody();
//    ZoneSelectionCounter counter = new ZoneSelectionCounter(body);
//    
//    press(SWT.ARROW_RIGHT);
//    assertEquals(1, body.getSelectedCount().intValue());
//    assertEquals(1, counter.count);
//  }
  
  @Test public void notifiedWhenColumnsSelectedByShiftClick() throws Exception {
    Matrix matrix = createMatrix();
    Zone columnHeader = matrix.getColumnHeader();
    
    click(matrix, columnHeader.getCellBounds(0, 0), SWT.BUTTON1);
    
    // ZoneSelectionCounter zoneCounter = new ZoneSelectionCounter(columnHeader);
    SectionSelectionCounter sectionCounter = new SectionSelectionCounter(matrix.getAxisX().getBody());
    
    click(matrix, columnHeader.getCellBounds(1, 0), SWT.MOD2 | SWT.BUTTON1);
    assertEquals(1, sectionCounter.count);
  }
  
  @Test public void notifiedWhenColumnsSelectedByShiftClickAfterReorder() throws Exception {
    Matrix matrix = createMatrix();
    matrix.getAxisX().getBody().setDefaultMoveable(true);
    Zone columnHeader = matrix.getColumnHeader();
    focusControl = matrix;
    
    // Reorder
    click(matrix, columnHeader.getCellBounds(0, 0), SWT.BUTTON1);
    dragAndDrop(columnHeader.getCellBounds(0, 0), columnHeader.getCellBounds(4, 0));
    assertEquals(0, matrix.getAxisX().getItemAt(5).getIndex().intValue());
    
    SectionSelectionCounter sectionCounter = new SectionSelectionCounter(matrix.getAxisX().getBody());
    ZoneSelectionCounter zoneCounter = new ZoneSelectionCounter(columnHeader);
    
    click(matrix, columnHeader.getCellBounds(3, 0), SWT.MOD2 | SWT.BUTTON1);
    
    assertEquals(3, columnHeader.getSelectedCount().intValue());
    assertEquals(2, sectionCounter.count);
    assertEquals(2, zoneCounter.count);
    
    click(matrix, columnHeader.getCellBounds(1, 0), SWT.MOD2 | SWT.BUTTON1);
  }
  
  @Test public void selectBodyByShiftClick() throws Exception {
    Matrix matrix = createMatrix();
    Zone body = matrix.getBody();
    Rectangle bounds = body.getCellBounds(1, 0);
    
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
    Section columnSection = matrix.getAxisX().getBody();
    Rectangle bounds = body.getCellBounds(0, 0);
    Rectangle bounds2 = columnHeader.getCellBounds(0, 0);
    
    SectionSelectionCounter sectionCounter = new SectionSelectionCounter(columnSection);
    click(matrix, bounds2);
    
    // Notification count
    assertEquals(1, sectionCounter.count);
    
    // Axis item and header cell should be selected
    assertEquals(1, columnSection.getSelectedCount());
    assertEquals(1, columnHeader.getSelectedCount().intValue());
    
    // Body cell should be highlighted
    assertColor(body.getSelectionBackground(), bounds.x+2, bounds.y+2);
    
    // Column header cell should be highlighted
    assertColor(columnHeader.getSelectionBackground(), bounds2.x+2, bounds2.y+2);
    
    // Click again should notify 
    sectionCounter.count = 0;
    click(matrix, bounds2);
    assertEquals(1, sectionCounter.count);

    // Click another column should deselect body cells in the previous one
    click(matrix, columnHeader.getCellBounds(2, 0));
    assertColor(matrix.getBackground(), bounds.x+2, bounds.y+2);
  }
  
  @Test public void unselectColumnOnHeaderCtrlClick() throws Exception {
    Matrix matrix = createMatrix();
    Zone columnHeader = matrix.getColumnHeader();
    Rectangle bounds = columnHeader.getCellBounds(1, 0);
    
    matrix.getAxisX().getBody().setSelected(0, 2, true);
    assertEquals(3, matrix.getAxisX().getBody().getSelectedCount());
    
    click(matrix, bounds, SWT.MOD1);
    
    // Axis item and header cell should be selected
    assertEquals(2, matrix.getAxisX().getBody().getSelectedCount());
    assertEquals(2, columnHeader.getSelectedCount().intValue());
  }
  
  @Test public void unselectColumnOnHeaderCtrlDrag() throws Exception {
    Matrix matrix = createMatrix();
    Zone columnHeader = matrix.getColumnHeader();
    Rectangle bounds1 = columnHeader.getCellBounds(1, 0);
    Rectangle bounds2 = columnHeader.getCellBounds(3, 0);
    
    matrix.getAxisX().getBody().setSelected(0, 2, true);
    matrix.redraw();

    dragAndDrop(SWT.MOD1, bounds1, bounds2);
    
    // Axis item and header cell should be selected
    assertEquals(1, matrix.getAxisX().getBody().getSelectedCount());
    assertEquals(1, columnHeader.getSelectedCount().intValue());
  }
  
  @Test public void unselectColumnOnHeaderCtrlClickAfterSelectCtrlClick() throws Exception {
    Matrix matrix = createMatrix();
    Zone columnHeader = matrix.getColumnHeader();
    Rectangle bounds = columnHeader.getCellBounds(0, 0);
    
    click(matrix, bounds, SWT.MOD1);
    assertEquals(1, matrix.getAxisX().getBody().getSelectedCount());
    
    click(matrix, bounds, SWT.MOD1);
    
    // Axis item and header cell should be selected
    assertEquals(0, matrix.getAxisX().getBody().getSelectedCount());
    assertEquals(0, columnHeader.getSelectedCount().intValue());
  }
  
  @Test public void selectColumnOnHeaderCtrlDrag() throws Exception {
    Matrix matrix = createMatrix();
    Zone columnHeader = matrix.getColumnHeader();
    Section columnSection = matrix.getAxisX().getBody();
    Rectangle bounds1 = columnHeader.getCellBounds(1, 0);
    Rectangle bounds2 = columnHeader.getCellBounds(2, 0);
    
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
    matrix.getAxisX().getBody().setDefaultResizable(true);
    Zone columnHeader = matrix.getColumnHeader();
    Rectangle bounds = columnHeader.getCellBounds(0, 0);
    int x = bounds.x + bounds.width;
    
    click(matrix, bounds);
    dragAndDrop(0,  matrix.toDisplay(x, bounds.y), matrix.toDisplay(x + 10, bounds.y));
    SectionResizeCounter counter = new SectionResizeCounter(matrix.getAxisX().getBody());
    br();
    click(matrix, bounds);
    assertEquals(0, counter.count);
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
