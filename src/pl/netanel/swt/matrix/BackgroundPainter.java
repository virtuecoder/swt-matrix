package pl.netanel.swt.matrix;

class BackgroundPainter<X extends Number, Y extends Number>  extends Painter<X, Y> {

  public BackgroundPainter() {
    super(Painter.NAME_BACKGORUND, Painter.SCOPE_ENTIRE);
  }

  @Override
  protected boolean init() {
    if (style.background != null) {
      gc.setBackground(style.background);
      return true;
    }
    return false;
  }
  
  @Override public void paint(int x, int y, int width, int height) {
    gc.fillRectangle(x, y, width, height);
  }
}