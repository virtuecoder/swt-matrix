package pl.netanel.swt.matrix.snippets;

import java.math.BigInteger;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Axis;
import pl.netanel.swt.matrix.AxisItem;
import pl.netanel.swt.matrix.Cell;
import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.Style;
import pl.netanel.swt.matrix.Zone;
import pl.netanel.swt.matrix.ZoneEditor;

/**
 * Compound example.
 */
public class S1001_CompoundExample {
  // Meta data
  static final String title = "Screenshot 1";
  static final String instructions = "";

  protected static final BigInteger BigIntegerTWO = new BigInteger("2");
  private BigInteger indexY0;
  private BigInteger[] indexYY;
  private ArrayList<Cell<Integer, BigInteger>> booleanCells;

  private Matrix<Integer, BigInteger> matrix;
  private Axis<Integer> axisX;
  private Axis<BigInteger> axisY;
  private Section<Integer> bodyX;
  private Section<BigInteger> bodyY;
  private Section<BigInteger> filterSection;
  private Section<BigInteger> totalSection;
  private Zone<Integer, BigInteger> body;
  private Zone<Integer, BigInteger> filterBody;
  private Zone<Integer, BigInteger> totalBody;

  private static Display display;
  private Image circleImage;
  private Image sortImage;
  private Image squareImage;
  private Image checkedImage;
  private Image uncheckedImage;
  private final Color blueColor;
  private final Color redColor;

  private Zone<Integer, BigInteger> headerX;
  private ZoneEditor<Integer, BigInteger> bodyEditor;
  private Painter<Integer, BigInteger> bodyPainter;
  private Image[] checkboxImages;
  private Font boldFont;

  public S1001_CompoundExample(Composite parent) {
    super();

    setData();
    createResources();
    createControls(parent);
    configureAxises();
    setPainters();
    setEditors();
    setListeners();

    // Set colors
    redColor = display.getSystemColor(SWT.COLOR_RED);
    blueColor = display.getSystemColor(SWT.COLOR_BLUE);

  }

  private void setData() {
    // There is no actual storage for model data in this snippet.
    // All data is calculated on the fly.
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
    filterSection = axisY.getSection(1);
    filterSection.setFocusItemEnabled(false);
    totalSection = axisY.getSection(3);
    totalSection.setFocusItemEnabled(false);

    matrix = new Matrix<Integer, BigInteger>(parent,
      SWT.V_SCROLL | SWT.H_SCROLL, null, axisY);
    matrix.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

    axisX = matrix.getAxisX();

    filterBody = matrix.getZone(axisX.getBody(), filterSection);
    totalBody = matrix.getZone(axisX.getBody(), totalSection);
    body = matrix.getBody();

    display = parent.getDisplay();
  }

  private void configureAxises() {
    axisX.getHeader().setVisible(true);
    axisY.getHeader().setVisible(true);
    axisX.getHeader().setDefaultCellWidth(200);

    bodyX = axisX.getBody();
    bodyY = axisY.getBody();

    filterSection.setCount(BigInteger.ONE);
    filterSection.setCellWidth(BigInteger.ZERO, 16);
    filterSection.setFocusItemEnabled(false);

    totalSection.setCount(BigInteger.ONE);
    totalSection.setLineWidth(BigInteger.ZERO, 2);
    totalSection.setFocusItemEnabled(false);

    bodyX.setCount(10);
    bodyY.setCount(new BigInteger("123456789012345678901234567890"));
    bodyY.setOrder(indexYY[11], indexYY[9]);

    bodyX.setDefaultCellWidth(70);
    bodyX.setDefaultMoveable(true);
    bodyX.setCellWidth(2, 100);
    bodyX.setOrder(5, 3);

    bodyY.setCellWidth(indexYY[5], 40);

    axisX.setFrozenHead(2);
    axisY.setFrozenHead(2);
    axisY.setFrozenTail(1);

  }

  private void setPainters() {
    matrix.getPainter(Painter.NAME_FREEZE_TAIL_LINE_Y).setEnabled(false);

    matrix.getHeaderX().replacePainterPreserveStyle(
      new Painter<Integer, BigInteger>(Painter.NAME_CELLS) {
        @Override
        protected boolean init() {
          style.imageAlignX = SWT.RIGHT;
          style.imageAlignY = SWT.CENTER;
          style.imageMarginX = 4;
          return super.init();
        }

        @Override
        public void setup(Integer indexX, BigInteger indexY) {
          super.setup(indexX, indexY);
          style.foreground = indexX == 2 ? blueColor : null;
          style.textAlignX = indexX == 2 ? SWT.RIGHT : SWT.LEFT;
          style.imageAlignX = indexX == 2 ? SWT.LEFT : SWT.RIGHT;

          if (indexX == 2) {
            style.font = boldFont;
          }
          else {
            style.font = null;
          }
        }

        @Override
        public void setupSpatial(Integer indexX, BigInteger indexY) {
          text = "Column " + indexX;
          image = null;
          if (indexX == 5) {
            image = sortImage;
          }
          else if (indexX == 2) {
            image = squareImage;
          }
        }
      });

    matrix.getHeaderY().addPainter(
      new Painter<Integer, BigInteger>("Overlay", Painter.SCOPE_CELLS) {
        @Override
        protected boolean init() {
          style.imageAlignX = SWT.RIGHT;
          style.imageMarginX = 4;
          return super.init();
        }

        @Override
        public void setupSpatial(Integer indexX, BigInteger indexY) {
          // text = indexY.toString();
          image = indexY.equals(indexYY[8]) ? squareImage : null;
        }
      });

    body.setSelected(2, indexYY[5], true);
    body.setSelected(4, indexYY[6], true);
    body.setSelected(3, indexYY[1], true);
    body.setSelected(1, indexYY[5], true);
    body.setSelected(5, indexYY[4], true);
    body.setSelected(5, indexYY[6], true);

    body.replacePainterPreserveStyle(bodyPainter = new Painter<Integer, BigInteger>(
      Painter.NAME_CELLS) {
      @Override
      public void setup(Integer indexX, BigInteger indexY) {
        super.setup(indexX, indexY);

        style.textAlignX = indexX == 2 ? SWT.RIGHT : SWT.LEFT;

        style.background = indexX % 2 != 0 && indexY.equals(indexYY[10]) ? display
          .getSystemColor(SWT.COLOR_YELLOW) : null;

        boolean isOddY = indexY.divideAndRemainder(BigIntegerTWO)[1].equals(BigInteger.ZERO);
        style.foreground = style.selectionForeground = indexX == 5 && isOddY
          ? redColor
          : null;

        style.imageAlignX = SWT.LEFT;
        if (indexY.equals(indexYY[9])) {
          style.imageAlignX = SWT.RIGHT;
        }
      }

      @Override
      public void setupSpatial(Integer indexX, BigInteger indexY) {
        style.hasWordWraping = false;

        // Don't print boolean labels
        if (booleanCells.contains(Cell.create(indexX, indexY)) || indexX == 4
          && indexY.equals(indexYY[3])) {
          text = null;
        }

        // Text wrapping
        else if (indexX == 3 && indexY.equals(indexYY[5])) {
          text = "This text has word wrap set on";
          style.hasWordWraping = true;
        }

        // Default text
        else {
          text = indexY + ", " + indexX;
        }

        // Image
        image = indexX == 2
          && (indexY.equals(indexYY[3]) || indexY.equals(indexYY[9])) ? circleImage
          : null;

      }

    });


    // Vertical lines painter
    body.replacePainterPreserveStyle(new Painter<Integer, BigInteger>(Painter.NAME_LINES_Y) {
      private boolean merge;
      private boolean dotted;

      @Override
      protected boolean init() {
        gc.setBackground(style.background);
        return true;
      }

      @Override
      public void setup(Integer indexX, BigInteger indexY) {
        super.setup(indexX, indexY);
        merge = indexX == 4;
        int position = axisX.getViewportPosition(AxisItem.create(bodyX, 2));
        AxisItem<Integer> nextItem = axisX
          .getItemByViewportPosition(position + 1);
        dotted = indexX == 2 || nextItem != null
          && indexX == nextItem.getIndex();
      }

      @Override
      protected void paint(int x, int y, int width, int height) {
        if (merge) {
          int[] cellY = axisY.getCellBound(AxisItem.create(bodyY, indexYY[8]));
          int[] lineY = axisY.getLineBound(AxisItem.create(bodyY,
            indexYY[8].add(BigInteger.ONE)));
          if (cellY != null && lineY != null) {
            gc.fillRectangle(x, y, width, cellY[0] - y);
            int y2 = lineY[0] + lineY[1];
            gc.fillRectangle(x, y2, width, height + y - y2);
          }
        }
        else if (dotted) {
          gc.setForeground(display.getSystemColor(SWT.COLOR_BLUE));
          gc.setLineDash(new int[] { 5, 2 });
          gc.drawLine(x, y, x, y + height - 1);
          gc.setLineDash(null);
        }
        else {
          gc.fillRectangle(x, y, width, height);
        }
      }
    });

    // Filter header
    Zone<Integer, BigInteger> filterHeader = matrix.getZone(axisX.getHeader(),
      filterSection);
    Painter<Integer, BigInteger> painter = filterHeader
      .getPainter(Painter.NAME_CELLS);
    painter.text = "Filters:";
    painter.style.textAlignX = SWT.RIGHT;

    // Total header
    Zone<Integer, BigInteger> totalHeader = matrix.getZone(axisX.getHeader(),
      totalSection);
    painter = totalHeader.getPainter(Painter.NAME_CELLS);
    painter.text = "Total:";
    painter.style.textAlignX = SWT.RIGHT;

    // Total body
    totalBody.replacePainterPreserveStyle(new Painter<Integer, BigInteger>(
      Painter.NAME_CELLS) {
      @Override
      public void setupSpatial(Integer indexX, BigInteger indexY) {
        super.setupSpatial(indexX, indexY);
        if (indexX.intValue() == 2) {
          text = "2030020";
          style.textAlignX = SWT.RIGHT;
        }
        else {
          text = null;
        }
      }
    });
  }

  private void setEditors() {
    bodyEditor = new ZoneEditor<Integer, BigInteger>(body) {
      @Override
      public Object getModelValue(Integer indexX, BigInteger indexY) {
        if (booleanCells.contains(Cell.create(indexX, indexY))) {
          return Boolean.TRUE;
        }
        else if (indexX == 4 && indexY.equals(indexYY[3])) { return Boolean.TRUE; }
        return null;
      }

      @Override
      protected boolean hasEmbeddedControl(Integer indexX, BigInteger indexY) {
        return booleanCells.contains(Cell.create(indexX, indexY))
          || indexX == 4 && indexY.equals(indexYY[10]) || indexX == 1
          && indexY.equals(indexYY[5]);
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
        else if (indexX == 1 && indexY.equals(indexYY[5])) {
          StyledText text = new StyledText(matrix, SWT.NONE);
          text.setText("This is an example of styled text");
          text.setWordWrap(true);
          StyleRange range = new StyleRange(8, 2, null, null);
          range.strikeout = true;
          text.setStyleRange(range);
          text
            .setStyleRange(new StyleRange(11, 7, display
              .getSystemColor(SWT.COLOR_DARK_GREEN), null, SWT.BOLD
              | SWT.ITALIC));
          text.setStyleRange(new StyleRange(22, 6, display
            .getSystemColor(SWT.COLOR_WHITE), display
            .getSystemColor(SWT.COLOR_BLUE)));

          Painter<Integer, BigInteger> painter = body
            .getPainter(Painter.NAME_CELLS);
          if (body.isSelected(indexX, indexY)) {
            text.setBackground(painter.style.selectionBackground);
          }
          else {
            text.setBackground(painter.style.background);
          }
          return text;
        }
        return super.createControl(indexX, indexY);
      }

      @Override
      protected void setBounds(Integer indexX, BigInteger indexY,
        Control control) {
        if (indexX == 4 && indexY.equals(indexYY[10])) {
          Rectangle bounds = body.getCellBounds(indexX, indexY);
          bounds.x++;
          bounds.y++;
          bounds.width -= 2;
          bounds.height -= 2;
          control.setBounds(bounds);
        }
        else if (indexX == 1 && indexY.equals(indexYY[5])) {
          Rectangle bounds = body.getCellBounds(indexX, indexY);
          control.setBounds(bounds);
        }
        else {
          super.setBounds(indexX, indexY, control);
        }
      }

      @Override
      protected Object[] getCheckboxEmulation(Integer indexX, BigInteger indexY) {
        return indexX == 4 && indexY.equals(indexYY[3]) ? checkboxImages : null;
      }

    };

    // Filter body
    new ZoneEditor<Integer, BigInteger>(filterBody) {
      @Override
      protected Control createControl(Integer indexX, BigInteger indexY) {
        if (indexX.intValue() == 2) {
          CCombo combo = new CCombo(matrix, SWT.NONE);
          combo.setItems(new String[] { "Is number" });
          combo.setText("Is number");
          combo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
              // Apply filter to the model
              matrix.refresh();
            }
          });
          return combo;
        }
        return null;
      }

      @Override
      protected void setBounds(Integer indexX, BigInteger indexY, Control control) {
        Rectangle bounds = filterBody.getCellBounds(indexX, indexY);
        control.setBounds(bounds);
      }

      @Override
      protected boolean hasEmbeddedControl(Integer indexX, BigInteger indexY) {
        return indexX.intValue() == 2;
      }
    };

    // Total body
    new ZoneEditor<Integer, BigInteger>(totalBody) {
      @Override
      protected Control createControl(Integer indexX, BigInteger indexY) {
        if (indexX.intValue() == 0) {
          CCombo combo = new CCombo(matrix, SWT.NONE);
          combo.setItems(new String[] { "Sum", "Avg", "Count", "Count Unique", "Nothing" });
          combo.setText("Sum");
          combo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
              // Apply filter to the model
              matrix.refresh();
            }
          });
          return combo;
        }
        return null;
      }

      @Override
      protected void setBounds(Integer indexX, BigInteger indexY,
        Control control) {
        /*
         * By default the bounds of the editor control are enlarged by offset =
         * 1 to match the borders of the cell. Here they take the cell bounds
         * without offset.
         */
        Rectangle bounds = totalBody.getCellBounds(indexX, indexY);
        control.setBounds(bounds);
      }
    };
  }

  private void setListeners() {
    // Header highlighting for the focus cell
    body.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        highlightHeaders();
        highlightStyledText();
      }
    });

    matrix.getHeaderX().unbind(Matrix.CMD_PACK_COLUMN, SWT.MouseDoubleClick, 1);
    matrix.getHeaderY().unbind(Matrix.CMD_PACK_ROW, SWT.MouseDoubleClick, 1);
  }

  void highlightHeaders() {
    AxisItem<Integer> focusItemX = axisX.getFocusItem();
    if (focusItemX != null) {
      headerX = matrix.getHeaderX();
      headerX.setSelected(focusItemX.getIndex(), BigInteger.ZERO, true);
    }
    AxisItem<BigInteger> focusItemY = axisY.getFocusItem();
    if (focusItemY != null) {
      matrix.getHeaderY().setSelected(0, focusItemY.getIndex(), true);
    }
  }

  private void highlightStyledText() {
    StyledText text = (StyledText) bodyEditor.getEmbeddedControl(1, indexYY[5]);
    if (text != null) {
      Style style = bodyPainter.style;
      text.setBackground(body.isSelected(1, indexYY[5])
          ? style.selectionBackground
          : style.background);
    }
  }

  private void afterOpen() {
    AxisItem<BigInteger> item = AxisItem.create(bodyY, indexY0);
    axisY.setFocusItem(AxisItem.create(bodyY, indexYY[12]));
    axisY.showItem(item);
    highlightHeaders();
    highlightStyledText();
  }

  private void createResources() {
    // Make images
    circleImage = new Image(display, 16, 16);
    GC gc = new GC(circleImage);
    gc.setBackground(display.getSystemColor(SWT.COLOR_BLUE));
    gc.setAntialias(SWT.ON);
    gc.fillOval(0, 0, 16, 16);
    gc.dispose();

    ImageData imageData = new ImageData(9, 5, 24,
      new PaletteData(0xFF, 0xFF00, 0xFF0000));
    int transparentPixel = imageData.palette.getPixel(new RGB(255, 255, 255));
    imageData.transparentPixel = transparentPixel;
    sortImage = new Image(display, imageData);
    gc = new GC(sortImage);
    gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
    gc.fillRectangle(0, 0, 9, 5);
    gc.setBackground(display.getSystemColor(SWT.COLOR_DARK_GRAY));
    gc.setAntialias(SWT.ON);
    gc.fillPolygon(new int[] { 5, 0, 9, 5, 0, 5 });
    gc.dispose();

    imageData = new ImageData(16, 16, 24, new PaletteData(0xFF, 0xFF00,
      0xFF0000));
    transparentPixel = imageData.palette.getPixel(new RGB(255, 255, 255));
    imageData.transparentPixel = transparentPixel;
    squareImage = new Image(display, imageData);
    gc = new GC(squareImage);
    gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
    gc.fillRectangle(0, 0, 16, 16);
    gc.setBackground(display.getSystemColor(SWT.COLOR_DARK_CYAN));
    gc.setAntialias(SWT.ON);
    gc.fillRoundRectangle(1, 1, 14, 14, 3, 3);
    gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
    gc.fillRoundRectangle(4, 4, 8, 8, 2, 2);
    gc.dispose();

    checkedImage = new Image(display, 14, 14);
    gc = new GC(checkedImage);
    gc.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
    gc.setAntialias(SWT.ON);
    gc.drawRectangle(0, 0, 13, 13);
    gc.setLineWidth(2);
    gc.drawPolyline(new int[] { 4, 5, 6, 9, 10, 3 });
    gc.dispose();

    uncheckedImage = new Image(display, 14, 14);
    gc = new GC(uncheckedImage);
    gc.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
    gc.setAntialias(SWT.ON);
    gc.drawRectangle(0, 0, 13, 13);
    gc.dispose();

    checkboxImages = new Image[] { checkedImage, uncheckedImage };

    FontData[] fd = display.getSystemFont().getFontData();
    fd[0].setStyle(SWT.BOLD);
    boldFont = new Font(display, fd[0]);
  }

  public static void main(String[] args) {
    display = Display.getDefault();
    Shell shell = new Shell(display);
    shell.setText(title);
    shell.setLayout(new GridLayout(1, false));

    S1001_CompoundExample app = new S1001_CompoundExample(shell);

    shell.setBounds(400, 200, 700, 400);
    shell.open();

    app.afterOpen();

    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    app.circleImage.dispose();
    app.sortImage.dispose();
    app.squareImage.dispose();
    app.checkedImage.dispose();
    app.uncheckedImage.dispose();
  }

}