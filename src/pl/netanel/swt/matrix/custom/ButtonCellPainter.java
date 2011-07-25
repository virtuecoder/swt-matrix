package pl.netanel.swt.matrix.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.AxisItem;
import pl.netanel.swt.matrix.Zone;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;

/**
 * Adds (or replaces) a painter named "button cells" that emulates a raised
 * button shape.
 * <p>
 * When the cell is clicked the look of the cell becomes flat.
 * <p>
 * Only 2 pixel wide edge of cells is painted so the majority of the output 
 * of the underlying Painter.NAME_CELLS painter remains visible. Colors used are
 * SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW, SWT.COLOR_WIDGET_LIGHT_SHADOW and
 * SWT.COLOR_WIDGET_NORMAL_SHADOW. 
 * <p>
 * Cells in the zone become not selectable.
 * <p>
 * Usage example:
 * <pre>
 * new ButtonCellPainter<Integer, Integer>(matrix.getColumnHeader());
 * </pre>
 * 
 * @param <Y> indexing type for vertical axis
 * @param <X> indexing type for horizontal axis
 * @author Jacek Kolodziejczyk created 16-07-2011
 * @created 13-10-2010
 */
public class ButtonCellPainter<X extends Number, Y extends Number> 
  extends Painter<X, Y> implements Listener 
{
  private final Color highlightShadow, lightShadow, normalShadow;
  private final Zone<X, Y> zone;
  private AxisItem<X> pushedX;
  private AxisItem<Y> pushedY;
  
  public ButtonCellPainter(Zone<X, Y> zone) {
    super("button cells", SCOPE_CELLS);
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
    Matrix<X, Y> matrix = zone.getMatrix();

    switch (e.type) {
    
    case SWT.MouseDown:
      pushedX = zone.getMatrix().getAxisX().getItemByDistance(e.x);
      pushedY = zone.getMatrix().getAxisY().getItemByDistance(e.y);
      break;
      
    case SWT.MouseUp:
      pushedY = null;
      pushedX = null;
      break;
    }
    
    matrix.redraw();
    matrix.update();
  }
  
  @Override protected boolean init() {
    // Prevent setting background by the default super.init(); 
    return true;
  }
  
  @Override public void paint(X indexX, Y indexY, int x, int y, int width, int height) {
    if (isPushed(indexX, indexY)) {
      paintPushed(indexX, indexY, x, y, width, height);
    } else {
      paintIdle(indexX, indexY, x, y, width, height);
    }
  };
  
  /**
   * Returns <tt>true</tt> if the cell at the specified location is in pushed state, 
   * or <tt>false</tt> otherwise. 
   * @param indexX index of a section item in the column axis 
   * @param indexY index of a section item in the row axis. 
   * @return <tt>true</tt> if the cell at the specified location is in pushed state
   */
  protected boolean isPushed(X indexX, Y indexY) {
    return pushedY != null && pushedX != null && 
      pushedY.getIndex().equals(indexY) &&
      pushedX.getIndex().equals(indexX);
  }
  

  /**
   * Paints the cell when it is in the pushed state.  
   * @param indexX index of a section item in the column axis 
   * @param indexY index of a section item in the row axis. 
   * @param x the x coordinate of the painting boundaries
   * @param y the y coordinate of the painting boundaries
   * @param width the width of the painting boundaries
   * @param height the height of the painting boundaries
   */
  protected void paintPushed(X indexX, Y indexY, int x, int y, int width, int height) {
    super.paint(indexX, indexY, x, y, width, height);
  }

  /**
   * Paints the cell when it is in the idle (not pushed) state.
   * @param indexX index of a section item in the column axis 
   * @param indexY index of a section item in the row axis. 
   * @param x the x coordinate of the painting boundaries
   * @param y the y coordinate of the painting boundaries
   * @param width the width of the painting boundaries
   * @param height the height of the painting boundaries
   */
  protected void paintIdle(X indexX, Y indexY, int x, int y, int width, int height) {
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
    matrix.getAxisX().getBody().setCount(4);
    matrix.getAxisY().getBody().setCount(10);
    matrix.getAxisY().getHeader().setVisible(true);

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
