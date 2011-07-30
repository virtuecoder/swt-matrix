package pl.netanel.swt.matrix.snippets;

import java.io.FileInputStream;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Section;

/**
 * Filter section between header and body.
 */
/*
 * 	Make sure filter.png is on your class path.
 */
public class _0003_FilterSectionBetweenHeaderAndBody {
	static String filter;
	
	public static void main(String[] args) throws Exception {
		Shell shell = (new Shell());
		shell.setText(title);
		shell.setLayout(new GridLayout(2, false));
		Display display = shell.getDisplay();
		
		final ArrayList<Object[]> list = new ArrayList<Object[]>();
		list.add(new Object[] {"Task 1", "high"});
		list.add(new Object[] {"Task 2", "medium"});
		list.add(new Object[] {"Task 3", "low"});
		list.add(new Object[] {"Task 4", "medium"});
		list.add(new Object[] {"Task 5", "high"});
		final ArrayList<Object[]> filtered = new ArrayList<Object[]>();
		filtered.addAll(list);
		
		Axis<Integer> axisY = new Axis<Integer>(Integer.class, 3, 0, 2);
		axisY.getSection(1).setCount(1);
		axisY.getSection(1).setFocusItemEnabled(false);
		final Section<Integer> bodyY = axisY.getBody();
		bodyY.setCount(list.size());
		bodyY.setDefaultResizable(true);
		
		final Matrix<Integer, Integer> matrix = 
		  new Matrix<Integer, Integer>(shell, SWT.V_SCROLL, null, axisY);
		matrix.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		axisY.getHeader().setVisible(true);
		
		Axis<Integer> axisX = matrix.getAxisX();
		axisX.getBody().setCount(2);
		axisX.getHeader().setDefaultCellWidth(16);
		axisX.getHeader().setVisible(true);
		
		matrix.getBody().replacePainter(
		  new Painter<Integer, Integer>(Painter.NAME_CELLS, Painter.SCOPE_CELLS) {
  			@Override
  			public String getText(Integer indexX, Integer indexY) {
  				return filtered.get(indexY.intValue())[indexX.intValue()].toString();
  			}
  		}
	  );
		
		FileInputStream stream = new FileInputStream("filter.png");
		Image image = new Image(display, new ImageData(stream));
		stream.close();
		
		matrix.getHeaderX().replacePainter(
		  new Painter<Integer, Integer>(Painter.NAME_CELLS, Painter.SCOPE_CELLS) {
		    @Override
		    public String getText(Integer indexX, Integer indexY) {
		      return indexX.intValue() == 0 ? "Task" : "Priority";
		    }
		  }
		);
		
		// Filter row header
		matrix.getZone(axisX.getHeader(), axisY.getSection(1)).getPainter(Painter.NAME_CELLS).image = image;
		
		// Filter columns
		matrix.getZone(axisX.getBody(), axisY.getSection(1)).replacePainter(
			new Painter<Integer, Integer>(Painter.NAME_CELLS, Painter.SCOPE_CELLS) {
				public String getText(Integer indexX, Integer indexY) {
					return indexX.intValue() == 1 ? filter : null;
				};
			}
		); 
		
		Label label = new Label(shell, SWT.NONE);
		label.setText("Press to filter by priority: h - high, m - medium, l - low, a - all");
		
		matrix.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.character) {
				case 'a': filter = null; break;
				case 'h': filter = "high"; break;
				case 'm': filter = "medium"; break;
				case 'l': filter = "low"; break;
				default: return;
				}
				filtered.clear();
				if (filter == null) {
					filtered.addAll(list);
				} else {
					for (Object[] item: list) {
						if (item[1].equals(filter)) {
							filtered.add(item);
						}
					}
				}
				bodyY.setCount(filtered.size());
				matrix.refresh();
			}
		});
		
		shell.setBounds(400, 200, 600, 400);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	// Meta data
	static final String title = "Filter section between header and body";
	static final String instructions = "";
	static final String code = "0003";
}