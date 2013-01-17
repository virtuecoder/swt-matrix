package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.CellExtent;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.Zone;

public class S0100_CellMerging
{
    private static Matrix<Integer, Integer> matrix;

    public static void main(String[] args) {
        final Shell shell = new Shell();
        shell.setLayout(new GridLayout(1, false));
//        shell.setLayout(new FillLayout());

        Button button = new Button(shell, SWT.PUSH);
        button.setText("Merge");
        button.addSelectionListener(new SelectionAdapter() {
          @Override
          public void widgetSelected(SelectionEvent e) {
            Zone<Integer, Integer> body = matrix.getBody();
            CellExtent<Integer, Integer> extent = body.getSelectedExtent();
            if (extent == null) return;
            Integer startX = extent.getStartX();
            Integer countX = extent.getEndX() - startX + 1;
            Integer startY = extent.getStartY();
            Integer countY = extent.getEndY() - startY + 1;
            body.setMerged(startX, countX, startY, countY,
                !body.isMerged(startX, countX, startY, countY));
            matrix.refresh();
          }
        });

        matrix = new Matrix<Integer, Integer>(shell, SWT.H_SCROLL | SWT.V_SCROLL);
        GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
        matrix.setLayoutData(gd);

        Axis<Integer> axisX = matrix.getAxisX();
        Axis<Integer> axisY = matrix.getAxisY();

        Section<Integer> bodyX = axisX.getBody();
        bodyX.setCount(10);
        bodyX.setDefaultMoveable(true);
        axisX.getHeader().setVisible(true);

        Section<Integer> bodyY = axisY.getBody();
        bodyY.setCount(10);
        bodyY.setDefaultMoveable(true);
        axisY.getHeader().setVisible(true);


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

 // Meta data
    static final String title = "Cell merging";
    static final String instructions = "";
  }
