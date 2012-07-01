package usecase;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.AxisItem;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.Zone;
import pl.netanel.swt.matrix.ZoneEditor;

public class Snippet
{
    public static void main(String[] args) {
        final Shell shell = new Shell();
        shell.setText("hehe");
        shell.setLayout(new FillLayout());

        Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.V_SCROLL);

        Section<Integer> bodyX = matrix.getAxisX().getBody();
        bodyX.setCount(100);
        bodyX.setDefaultMoveable(true);

        matrix.getAxisY().getBody().setCount(100);
        matrix.getAxisY().getHeader().setVisible(true);
        
        matrix.getAxisX().getBody().setHidden(2, true);
        matrix.getAxisX().showItem(AxisItem.create(matrix.getAxisX().getBody(), 3));

//        bodyX.setSelected(bodyX.getIndex(1); bodyX.getIndex(2));


        Zone<Integer, Integer> body = matrix.getBody();
//        body.setSelected(5, 10, 5, 10, true);
        bodyX.setOrder(6, 1);

        Painter<Integer, Integer> painter = body.getPainter(Painter.NAME_CELLS);
        painter.style.textAlignX = SWT.CENTER;

        new ZoneEditor<Integer, Integer>(body);


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
