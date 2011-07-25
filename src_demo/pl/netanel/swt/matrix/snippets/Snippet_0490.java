package pl.netanel.swt.matrix.snippets;

import java.math.BigInteger;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.AxisItem;
import pl.netanel.swt.matrix.CellExtent;
import pl.netanel.swt.matrix.Zone;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.ZoneEditor;

/**
 * Custom copy / paste.
 */
/*
 * Can also copy/paste irregular sets of selected cells, 
 * e.g. (0,0) and (1,1) cells at once.
 */
public class Snippet_0490 {
	static final String NEW_LINE = System.getProperty("line.separator");
	static final int countY = 10, countX = 4;
	static final Transfer transfer = new Transfer() {

    @Override public TransferData[] getSupportedTypes() {
      return null;
    }

    @Override public boolean isSupportedType(TransferData transferData) {
      return false;
    }

    @Override protected int[] getTypeIds() {
      return null;
    }

    @Override protected String[] getTypeNames() {
      return null;
    }

    @Override protected void javaToNative(Object object,
      TransferData transferData) {}

    @Override protected Object nativeToJava(TransferData transferData) {
      return null;
    }
	  
	};
	
	public static void main(String[] args) {
		final Display display = Display.getDefault();
		Shell shell = new Shell(display);
		shell.setText("Custom copy / paste");
		shell.setLayout(new GridLayout(2, false));
		
		// Data model
		final ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < countY; i++) {
			ArrayList<String> row = new ArrayList<String>();
			for (int j = 0; j < countX; j++) {
				row.add("" + i + ", " + j);
			}	
			data.add(row);
		}
		
		// Matrix
		final Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.NONE);
		matrix.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		// Configure the matrix
		matrix.getAxisY().getHeader().setVisible(true);
		matrix.getAxisY().getBody().setCount(countY);
		matrix.getAxisX().getBody().setCount(countX);
		matrix.getBody().replacePainter(new Painter<Integer, Integer>(Painter.NAME_CELLS, Painter.SCOPE_CELLS) {
			@Override
			public void paint(Integer indexX, Integer indexY, int x, int y, int width, int height) {
				text = data.get(indexY.intValue()).get(indexX.intValue()).toString();
				super.paint(indexX, indexY, x, y, width, height);
			}
		});
		
		final ZoneEditor<Integer, Integer> zoneEditor = 
		  new ZoneEditor<Integer, Integer>(matrix.getBody()) {
  			@Override
  			protected Control createControl(Integer indexX, Integer indexY) {
  				return null;
  			}
  			
  			@Override public void copy() {
  				StringBuilder sb = new StringBuilder();
  				Zone<Integer, Integer> body = matrix.getBody();
  				if (body.getSelectedCount().compareTo(BigInteger.ZERO) == 0) {
  				  AxisItem<Integer> focusItemX = matrix.getAxisX().getFocusItem();
  				  AxisItem<Integer> focusItemY = matrix.getAxisY().getFocusItem();
  				  if (focusItemX != null && focusItemY != null) {
  				    sb.append(data.get(focusItemY.getIndex().intValue()).get(
  				      focusItemX.getIndex().intValue()));
  				  }
  				} else {
  				  CellExtent n = body.getSelectedExtent();
  				  int maxX = n.getEndX().intValue();
  				  int maxY = n.getEndY().intValue();
  				  int minX = n.getStartX().intValue();
  				  for (int i = n.getStartY().intValue(); i <= maxY; i++) {
  				    for (int j = minX; j <= maxX; j++) {
  				      if (j > minX) sb.append("\t");
  				      if (body.isSelected(j, i)) {
  				        sb.append(data.get(i).get(j).toString());
  				      }
  				    }
  				    if (i < maxY) sb.append(NEW_LINE);
  				  }
  				}
  				Clipboard clipboard = new Clipboard(display);
  				clipboard.setContents(new Object[] {sb.toString()},
  						new Transfer[] {TextTransfer.getInstance()});		
  				clipboard.dispose();
  				matrix.setFocus();
  			}
  			
  			@Override public void paste() {
  				Clipboard clipboard = new Clipboard(display);
  				Object contents = clipboard.getContents(TextTransfer.getInstance());
  				clipboard.dispose();
  				
  				if (contents == null) return;
  				
  				AxisItem<Integer> focusItemX = matrix.getAxisX().getFocusItem();
  				AxisItem<Integer> focusItemY = matrix.getAxisY().getFocusItem();
  				if (focusItemX == null || focusItemY == null) return;
  				
  				int startY = focusItemY.getIndex().intValue();
  				int startX = focusItemX.getIndex().intValue();
  				String[] rows = contents.toString().split(NEW_LINE);
  				for (int i = 0; i < rows.length && startY + i < countY; i++) {
  					String[] cells = split(rows[i], "\t");
  					for (int j = 0; j < cells.length && startX + j < countX; j++) {
  						String value = cells[j];
  						if (value != null) {
  							data.get(startY + i).set(startX + j, value);
  						}
  					}
  				}
  				
  				matrix.redraw();
  				matrix.setFocus();
  			}
  		};
		
		// Copy button
		Button copy = new Button(shell, SWT.PUSH);
		copy.setText("Copy");
		copy.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				zoneEditor.copy();
			}
		});
		
		Button paste = new Button(shell, SWT.PUSH);
		paste.setText("Paste");
		paste.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			  zoneEditor.paste();
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
	
	private static String[] split(String s, String separator) {
		ArrayList<String> list = new ArrayList<String>();
		int pos1 = 0, pos2 = 0;
		while (true) {
			pos1 = pos2;
			pos2 = s.indexOf("\t", pos2);
			if (pos2 == -1) {
				list.add(pos1 == s.length() ? null : s.substring(pos1, s.length()));
				break;
			}
			if (pos1 == pos2) {
				list.add(null);
			} else {
				list.add(s.substring(pos1, pos2));
			}
			pos2++;
		}
		return list.toArray(new String[] {});
	}
}
