package usecase;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Zone;

public class SnippetTruncatedText
{
    public static void main(String[] args) {
        final Shell shell = new Shell();
        shell.setLayout(new FillLayout());

        final ArrayList<Object> list = new ArrayList<Object>();
        list.add(124);
        list.add(21234.1234);
        list.add("abcdefghijk abcdefghijk");
        list.add("abcdefghijk abcdefghijk");
        list.add(5023485234523452345L);
        list.add(65223523.234);

        Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.V_SCROLL);

        matrix.getAxisX().getBody().setCount(1);
        matrix.getAxisY().getBody().setCount(6);
        matrix.getAxisX().getBody().setDefaultResizable(true);
        matrix.getAxisY().getBody().setDefaultResizable(true);
        matrix.getAxisY().getBody().setCellWidth(3, 5, 30);

        matrix.getAxisX().getHeader().setVisible(true);
        matrix.getAxisY().getHeader().setVisible(true);

        Zone<Integer, Integer> body = matrix.getBody();

        body.replacePainter(new Painter<Integer, Integer>(Painter.NAME_CELLS) {
          private Object value;

          @Override
          public void setup(Integer indexX, Integer indexY) {
            value = list.get(indexY.intValue());
            style.textAlignX = value instanceof Number ? SWT.RIGHT : SWT.LEFT;
            super.setup(indexX, indexY);
          }

          @Override
          public void setupSpatial(Integer indexX, Integer indexY) {
            text = "" + value;
            style.hasWordWraping = indexY > 2;
          }

          @Override
          protected void clipText() {
            if (value instanceof Number) {
              if (style.hasWordWraping) {
                textLayout.setWidth(textSize.x);
                textLayout.setText(text);
                Rectangle bounds = textLayout.getBounds();
                if (bounds.height > textSize.y) {
                  text = "#";
                }
              }
              else {
                Point extent = gc.stringExtent(text);
                if (extent.x > textSize.x) {
                  text = "#";
                }
                textSize.x = gc.stringExtent(text).x;
              }
            }
            else {
              super.clipText();
            }
          }
        });



        shell.setBounds(400, 200, 600, 400);
        shell.open();
        Display display = shell.getDisplay();
        while (!shell.isDisposed())
        {
            if (!display.readAndDispatch())
            {
                display.sleep();
            }
        }
    }
}
