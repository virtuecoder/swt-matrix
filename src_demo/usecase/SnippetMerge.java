package usecase;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.Zone;

public class SnippetMerge
{
    public static void main(String[] args) {
        final Shell shell = new Shell();
        shell.setLayout(new FillLayout());

        Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.H_SCROLL | SWT.V_SCROLL);

        Axis<Integer> axisX = matrix.getAxisX();
        Axis<Integer> axisY = matrix.getAxisY();
        
        Section<Integer> bodyX = axisX.getBody();
        bodyX.setCount(10);
        bodyX.setDefaultMoveable(true);
        axisX.getHeader().setVisible(true);

        axisY.getBody().setCount(10);
        axisY.getHeader().setVisible(true);

        Zone<Integer, Integer> body = matrix.getBody();
        body.setMerged(2, 4, 2, 4);

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
