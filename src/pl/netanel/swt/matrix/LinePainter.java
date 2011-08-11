package pl.netanel.swt.matrix;

class LinePainter<X extends Number, Y extends Number>  extends Painter<X, Y> {

  public LinePainter(String name, int scope) {
    super(name, scope);
  }
  
  
  @Override public void paint(int x, int y, int width, int height) {
    gc.setBackground(background);
    gc.fillRectangle(x, y, width, height);
  }
}