package usecase;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Zone;
import pl.netanel.swt.matrix.ZoneEditor;

public class Snippet
{
    public static void main(String[] args) {
        final Shell shell = new Shell();
        shell.setText("hehe");
        shell.setLayout(new FillLayout());

        Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.V_SCROLL);

        matrix.getAxisX().getBody().setCount(100);
        matrix.getAxisY().getBody().setCount(100);
        matrix.getAxisY().getHeader().setVisible(true);

//        bodyX.setSelected(bodyX.getIndex(1); bodyX.getIndex(2));


        Zone<Integer, Integer> body = matrix.getBody();
        body.setMerged(5, 10, 5, 10);

        Painter<Integer, Integer> painter = body.getPainter(Painter.NAME_CELLS);
        painter.style.textAlignX = SWT.CENTER;

        new ZoneEditor(body);


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
