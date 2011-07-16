package pl.netanel.swt.matrix.painter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.AxisItem;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Zone;

/**
 * 
 * @author Jacek Kolodziejczyk created 16-07-2011
 * @param <N0>
 * @param <N1>
 */
public class ButtonCellPainter<N0 extends Number, N1 extends Number> 
  extends Painter<N0, N1> implements Listener 
{
  private Color highlightShadow, lightShadow, normalShadow;
  private final Zone<N0, N1> zone;
  private AxisItem<N0> pushed0;
  private AxisItem<N1> pushed1;
  
  public ButtonCellPainter(Zone<N0, N1> zone) {
    super("button", SCOPE_CELLS_HORIZONTALLY);
    this.zone = zone;
    zone.replacePainter(this);

    Display display = zone.getMatrix().getDisplay();
    highlightShadow = display.getSystemColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW);
    lightShadow = display.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW);
    normalShadow = display.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW);
    
    zone.setSelectionEnabled(false);
    zone.addListener(SWT.MouseDown, this);
    zone.addListener(SWT.MouseUp, this);
  }

  @Override public void handleEvent(Event e) {
    Matrix<N0, N1> matrix = zone.getMatrix();

    switch (e.type) {
    
    case SWT.MouseDown:
      pushed0 = zone.getMatrix().getAxis0().getItemByDistance(e.y);
      pushed1 = zone.getMatrix().getAxis1().getItemByDistance(e.x);
      break;
      
    case SWT.MouseUp:
      pushed0 = null;
      pushed1 = null;
      break;
    }
    
    matrix.redraw();
    matrix.update();
  }
  
  @Override protected boolean init() {
    // Prevent setting background by the default super.init(); 
    return true;
  }
  
  public void paint(N0 index0, N1 index1, int x, int y, int width, int height) {
    if (isPushed(index0, index1)) {
      paintPushed(index0, index1, x, y, width, height);
    } else {
      paintIdle(index0, index1, x, y, width, height);
    }
  };
  
  /**
   * Returns <tt>true</tt> if the cell at the specified location is in pushed state, 
   * or <tt>false</tt> otherwise. 
   * @param index0 index of a section item in the row axis. 
   * @param index1 index of a section item in the column axis 
   * @return <tt>true</tt> if the cell at the specified location is in pushed state
   */
  protected boolean isPushed(N0 index0, N1 index1) {
    return pushed0 != null && pushed1 != null && 
      pushed0.getIndex().equals(index0) &&
      pushed1.getIndex().equals(index1);
  }
  

  /**
   * Paints the cell when it is in the pushed state.  
   * @param index0 index of a section item in the row axis. 
   * @param index1 index of a section item in the column axis 
   * @param x the x coordinate of the painting boundaries
   * @param y the y coordinate of the painting boundaries
   * @param width the width of the painting boundaries
   * @param height the height of the painting boundaries
   */
  protected void paintPushed(N0 index0, N1 index1, int x, int y, int width, int height) {
    super.paint(index0, index1, x, y, width, height);
  }

  /**
   * Paints the cell when it is in the idle (not pushed) state.
   * @param index0 index of a section item in the row axis. 
   * @param index1 index of a section item in the column axis 
   * @param x the x coordinate of the painting boundaries
   * @param y the y coordinate of the painting boundaries
   * @param width the width of the painting boundaries
   * @param height the height of the painting boundaries
   */
  protected void paintIdle(N0 index0, N1 index1, int x, int y, int width, int height) {
    gc.setForeground(highlightShadow);
    gc.drawLine(x, y - 1, x + width - 1, y - 1);
    gc.drawLine(x, y - 1, x, y + height - 1);

    gc.setForeground(lightShadow);
    gc.drawLine(x + 1, y, x + width - 2, y);
    gc.drawLine(x + 1, y, x + 1, y + height - 2);

    gc.setForeground(normalShadow);
    gc.drawLine(x + 1, y + height - 1, x + width - 1, y + height - 1);
    gc.drawLine(x + width - 1, y, x + width - 1, y + height - 1);
  }

  @Override public void clean() {
    super.clean();
    gc.setForeground(zone.getMatrix().getForeground());
  }
  
  
  
  public static void main(String[] args) {
    Shell shell = new Shell();
    shell.setLayout(new FillLayout());

    
    final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);
    matrix.getAxis1().getBody().setCount(4);
    matrix.getAxis0().getBody().setCount(10);
    matrix.getAxis0().getHeader().setVisible(true);

    new ButtonCellPainter<Integer, Integer>(matrix.getColumnHeader());
    
    shell.setBounds(400, 200, 400, 300);
    shell.open();
    Display display = shell.getDisplay();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }
}
