package screenshots;

import java.math.BigInteger;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.Cell;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.Zone;
import pl.netanel.swt.matrix.ZoneEditor;
import pl.netanel.swt.matrix.custom.ButtonCellBehavior;

/**
 * Simplest matrix.
 */
public class Snippet_1001 {
  private BigInteger indexY0;
  private BigInteger[] indexYY;  
  private ArrayList<Cell<Integer, BigInteger>> booleanCells;
  
  private Matrix<Integer, BigInteger> matrix; 
  private Axis<Integer> axisX;
  private Axis<BigInteger> axisY;
  private Section<Integer> bodyX;
  private Section<BigInteger> bodyY;
  private Section<BigInteger> filter;
  private Section<BigInteger> total;
  private Zone<Integer, BigInteger> body;
  
	public Snippet_1001(Composite parent) {
    super();
    
    setData();
    createControls(parent);
    configureAxises();
    setPainters();
    setEditor();
  }
	
  private void setData() {
    indexY0 = new BigInteger("123456789012345678901234567000");
    indexYY = new BigInteger[16];
    for (int i = 0; i < indexYY.length; i++) {
      indexYY[i] = indexY0.subtract(new BigInteger(Integer.toString(i)));
    }
    booleanCells = new ArrayList<Cell<Integer, BigInteger>>();
    booleanCells.add(Cell.create(4, indexYY[5]));
    booleanCells.add(Cell.create(1, indexYY[8]));
    booleanCells.add(Cell.create(3, indexYY[3]));
  }
  
  private void createControls(Composite parent) {
    axisY = new Axis<BigInteger>(BigInteger.class, 4, 0, 2);
    filter = axisY.getSection(1);
    filter.setFocusItemEnabled(false);
    total = axisY.getSection(3);
    total.setFocusItemEnabled(false);
    
    matrix = new Matrix<Integer, BigInteger>(parent, SWT.V_SCROLL | SWT.H_SCROLL, null, axisY);
    matrix.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    
    axisX = matrix.getAxisX();
    
    Device display = parent.getDisplay();
    
    Composite buttons = new Composite(parent, SWT.NONE);
    buttons.setLayout(new RowLayout());
    Button button1 = new Button(buttons, SWT.PUSH);
    button1.setImage(new Image(display, "copy.png"));
    Button button2 = new Button(buttons, SWT.PUSH);
    button2.setImage(new Image(display, "paste.png"));
    Button button3 = new Button(buttons, SWT.PUSH);
    button3.setImage(new Image(display, "excel.png"));
  }

  private void configureAxises() {
    axisX.getHeader().setVisible(true);
    axisY.getHeader().setVisible(true);
    axisX.getHeader().setDefaultCellWidth(200);
    
    bodyX = axisX.getBody();
    bodyY = axisY.getBody();
    
    filter.setCount(BigInteger.ONE);
    total.setCount(BigInteger.ONE);
    bodyX.setCount(10);
    bodyY.setCount(new BigInteger("123456789012345678901234567890"));
    
    bodyX.setDefaultCellWidth(70);
    bodyX.setDefaultMoveable(true);
    bodyX.setOrder(5, 3);
    bodyY.setCellWidth(indexYY[5], 40);

    axisX.setFreezeHead(2);
    axisY.setFreezeHead(2);
    axisY.setFreezeTail(1);
    
  }
  
  private void setPainters() {
    matrix.getPainter(Painter.NAME_FREEZE_LINES_HEAD_Y).setEnabled(false);
    
    matrix.getHeaderX().replacePainter(
      new Painter<Integer, BigInteger>(Painter.NAME_CELLS, Painter.SCOPE_CELLS) {
        @Override
        public void setupSpatial(Integer indexX, BigInteger indexY) {
          text = "Column " + indexX;
        }
    });
    matrix.getHeaderX().setHeaderStyle();
    
    new ButtonCellBehavior<Integer, BigInteger>(matrix.getHeaderX());
    new ButtonCellBehavior<Integer, BigInteger>(matrix.getHeaderXY());
    
    body = matrix.getBody();
    body.setSelected(2, indexYY[5], true);
    body.setSelected(4, indexYY[6], true);
    body.setSelected(3, indexYY[1], true);
    
    body.replacePainter(
      new Painter<Integer, BigInteger>(Painter.NAME_CELLS, Painter.SCOPE_CELLS) {
        @Override
        public void setupSpatial(Integer indexX, BigInteger indexY) {
          hasWordWraping = false;
          if (booleanCells.contains(Cell.create(indexX, indexY))) {
            text = null;
          }
          else if (indexX == 3 && indexY.equals(indexYY[5])) {
            text = "This text has word wrap set on";
            hasWordWraping = true;
          }
          else {
            text = indexY + ", " + indexX;
          }
        }
    });
    body.setBodyStyle();
  }
  
  
  private void setEditor() {
    new ZoneEditor<Integer, BigInteger>(body) {
      @Override
      public Object getModelValue(Integer indexX, BigInteger indexY) {
        if (booleanCells.contains(Cell.create(indexX, indexY))) {
          return Boolean.TRUE;
        }
        return null;
      }
      
      @Override
      protected boolean hasEmbeddedControl(Integer indexX, BigInteger indexY) {
        return booleanCells.contains(Cell.create(indexX, indexY)) ||
          indexX == 4 && indexY.equals(indexYY[10]);
      }
      @Override
      protected Control createControl(Integer indexX, BigInteger indexY) {
        if (getModelValue(indexX, indexY) instanceof Boolean) {
          return new Button(matrix, SWT.CHECK);
        }
        else if (indexX == 4 && indexY.equals(indexYY[10])) {
          ProgressBar progressBar = new ProgressBar(matrix, SWT.BORDER);
          progressBar.setMaximum(10);
          progressBar.setSelection(5);
          return progressBar;
        }
        return super.createControl(indexX, indexY);
      }
      
      @Override
      protected void setBounds(Integer indexX, BigInteger indexY, Control control) {
        if (indexX == 4 && indexY.equals(indexYY[10])) {
          Rectangle bounds = body.getCellBounds(indexX, indexY);
          bounds.x++; bounds.y++; bounds.width -= 2; bounds.height -= 2;
          control.setBounds(bounds);
        } else {
          super.setBounds(indexX, indexY, control);
        }
      }
    };
  }

  
  private void afterOpen() {
    axisY.setFocusItem(bodyY, indexY0);
    axisY.showItem(bodyY, indexY0);
  }

  public static void main(String[] args) {
	  Display display = Display.getDefault();
    Shell shell = new Shell(display );
    shell.setText(title);
    shell.setLayout(new GridLayout(1, false));
  
    Snippet_1001 app = new Snippet_1001(shell);
    
    shell.setBounds(400, 200, 700, 400);
    shell.open();
    
    app.afterOpen();
    
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
	}

  // Meta data
	static final String title = "Screenshot 1";
	static final String instructions = "";
	static final String code = "1001";
 
}