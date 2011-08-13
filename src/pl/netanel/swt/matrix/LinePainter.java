package pl.netanel.swt.matrix;

class LinePainter<X extends Number, Y extends Number>  extends Painter<X, Y> {

  public LinePainter(String name, int scope) {
    super(name, scope);
  }
  
  public LinePainter(String name) {
    super(name);
  }

  @Override
  protected boolean init() {
    gc.setBackground(style.background);
    return true;
  }
  
  @Override public void paint(int x, int y, int width, int height) {
    gc.fillRectangle(x, y, width, height);
  }
}