package pl.netanel.swt.matrix;

public class MatrixLayout<X extends Number, Y extends Number>  {

  private final AxisLayout<X> layoutX;
  private final AxisLayout<Y> layoutY;

  public MatrixLayout(AxisLayout<X> layoutX, AxisLayout<Y> layoutY) {
    this.layoutX = layoutX;
    this.layoutY = layoutY;
  }


}
