package usecase;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;

public class Snippet
{
  public static void main(String[] args) {
        final Shell shell = new Shell();
		shell.setLayout(new FillLayout());

        new Matrix<Integer, Integer>(shell, SWT.V_SCROLL);

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
