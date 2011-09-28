package pl.netanel.swt.matrix.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Zone;

public class FillLinesBehavior<X extends Number, Y extends Number>  {
  public boolean extendLinesX = true;
  public boolean extendLinesY = true;
  public boolean extraLinesX = true;
  public boolean extraLinesY;
  private final Zone<X, Y> zone;

  public FillLinesBehavior(final Zone<X, Y> zone) {
    this.zone = zone;
    Painter<X, Y> painterX = new LinePainter(Painter.NAME_LINES_X) {

      @Override
      public void paint(int x, int y, int width, int height) {
        super.paint(x, max = y, length = extendLinesX ? clientArea.width : width, height);
      }
      
      @Override
      public void clean() {
        if (extraLinesX) {
          int cell = zone.getSectionY().getDefaultCellWidth();
          int line = zone.getSectionY().getDefaultLineWidth();
          for (int distance = max + cell; distance < clientArea.height; distance += cell + line) {
            super.paint(0, distance, length, line);
          }
        }
        super.clean();
      }
    };
    Painter<X, Y> painterY = new LinePainter(Painter.NAME_LINES_Y) {
      @Override
      public void paint(int x, int y, int width, int height) {
        super.paint(max = x, y, width, length = extendLinesY ? clientArea.height : height);
      }
      
      @Override
      public void clean() {
        if (extraLinesY) {
          int cell = zone.getSectionX().getDefaultCellWidth();
          int line = zone.getSectionX().getDefaultLineWidth();
          for (int distance = max + cell; distance < clientArea.width; distance += cell + line) {
            super.paint(distance, 0, line, length);
          }
        }
        super.clean();
      }
    };
    zone.replacePainter(painterX);
    zone.replacePainter(painterY);
  }

  class LinePainter extends Painter<X, Y> {
    Rectangle clientArea;
    int max, length;
    
    public LinePainter(String name) {
      super(name);
    }
    
    @Override
    protected boolean init() {
      clientArea = zone.getMatrix().getClientArea();
      gc.setBackground(style.background);
      return true;
    }
    
    @Override public void paint(int x, int y, int width, int height) {
      gc.fillRectangle(x, y, width, height);
    }
  }
  
  public static void main(String[] args) {
    Shell shell = new Shell();
    shell.setLayout(new FillLayout());

    
    final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);
    matrix.getAxisX().getBody().setCount(4);
    matrix.getAxisY().getBody().setCount(10);
    matrix.getAxisY().getHeader().setVisible(true);

    FillLinesBehavior<Integer, Integer> behavior = new FillLinesBehavior<Integer, Integer>(matrix.getBody());
    behavior.extendLinesX = true;
    behavior.extendLinesY = true;
    behavior.extraLinesX = true;
    behavior.extraLinesY = true;
    
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
