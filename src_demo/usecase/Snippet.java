package usecase;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;

public class Snippet
{
    public static void main(String[] args) {
        final Shell shell = new Shell();
        shell.setText("hehe");
        shell.setLayout(new FillLayout());

        Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);
        matrix.getAxisX().getBody().setCount(4);
        matrix.getAxisY().getBody().setCount(10);
        matrix.getAxisY().getHeader().setVisible(true);

        matrix.getHeaderX().replacePainter(new Painter<Integer, Integer>(Painter.NAME_CELLS, Painter.SCOPE_CELLS) {
            @Override
            public void setup(Integer indexX, Integer indexY) {
                text = indexX.toString();
                style.textAlignX = SWT.CENTER;
                style.background = shell.getDisplay().getSystemColor(SWT.COLOR_CYAN);
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
