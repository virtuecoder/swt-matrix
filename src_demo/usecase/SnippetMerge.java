package usecase;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.AxisItem;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.Zone;
import pl.netanel.swt.matrix.ZoneEditor;

public class SnippetMerge
{
    public static void main(String[] args) {
        final Shell shell = new Shell();
        shell.setLayout(new FillLayout());

        Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.H_SCROLL | SWT.V_SCROLL);

        Axis<Integer> axisX = matrix.getAxisX();
        Section<Integer> bodyX = axisX.getBody();
        bodyX.setCount(100);
        bodyX.setDefaultMoveable(true);

        matrix.getAxisY().getBody().setCount(100);
//        matrix.getAxisY().getHeader().setVisible(true);

//        bodyX.setSelected(bodyX.getIndex(1); bodyX.getIndex(2));


        Zone<Integer, Integer> body = matrix.getBody();
//        body.setSelected(5, 10, 5, 10, true);
//        bodyX.setOrder(6, 1);

        Painter<Integer, Integer> painter = body.getPainter(Painter.NAME_CELLS);
        painter.style.textAlignX = SWT.CENTER;

        body.setMerged(0, 10, 0, 5);

        shell.setBounds(400, 200, 600, 400);
        shell.open();

//        matrix.execute(Matrix.CMD_FOCUS_PAGE_RIGHT);
//        matrix.execute(Matrix.CMD_FOCUS_RIGHT);
//        axisX.showItem(AxisItem.createUnchecked(bodyX, 11));


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
