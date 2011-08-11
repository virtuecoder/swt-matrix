package pl.netanel.swt.matrix.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Zone;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Section;

/**
 * Gap between cells like HTML table cell spacing attribute. Hide lines.
 */
public class _0013_GapBetweenCellsLikeHTMLTableCellspacingAttribute_HideLines {
	public static void main(String[] args) {
		Shell shell = new Shell();
    shell.setText(title);
		shell.setBounds(400, 200, 600, 400);
		shell.setLayout(new FillLayout());
		final Display display = shell.getDisplay();
		
		Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);
		matrix.getAxisY().getHeader().setVisible(true);
		
		Section<Integer> bodyY = matrix.getAxisY().getBody();
		bodyY.setDefaultLineWidth(3);
		bodyY.setCount(10);
		
		Section<Integer> bodyX = matrix.getAxisX().getBody();
		bodyX.setDefaultLineWidth(3);
		bodyX.setCount(4);
		
		// Column header painting
		Zone<Integer, Integer> columnHeader = matrix.getHeaderX();
		columnHeader.getPainter(Painter.NAME_CELLS).background = null;
		columnHeader.getPainter(Painter.NAME_LINES_X).setEnabled(false);
		columnHeader.getPainter(Painter.NAME_LINES_Y).setEnabled(false);
		columnHeader.addPainter(0, 
		  new Painter<Integer, Integer>("cell background and border", Painter.SCOPE_CELLS) {
			@Override
			public void paint(int x, int y, int width, int height) {
			  gc.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
			  gc.fillRectangle(x, y, width, height);
			  
				gc.setForeground(display.getSystemColor(SWT.COLOR_WIDGET_DARK_SHADOW));
				gc.drawRectangle(x - 1, y - 1, width + 1, height + 1);
			}
		});
		
		// Body painting
		Zone<Integer, Integer> body = matrix.getBody();
		body.getPainter(Painter.NAME_LINES_X).setEnabled(false);
		body.getPainter(Painter.NAME_LINES_Y).setEnabled(false);
		body.addPainter(
		  new Painter<Integer, Integer>("cell border", Painter.SCOPE_CELLS) {
		    @Override
		    public void paint(int x, int y, int width, int height) {
		      gc.setForeground(display.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		      gc.drawRectangle(x - 1, y - 1, width + 1, height + 1);
		    }
		  }
	  );
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	// Meta data
	static final String title = "Gap between cells like HTML table cellspacing attribute. Hide lines";
	static final String instructions = "";
	static final String code = "0013";
}