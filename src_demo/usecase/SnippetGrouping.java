package usecase;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.Zone;
import pl.netanel.swt.matrix.reloaded.ints.Grouping;
import pl.netanel.swt.matrix.reloaded.ints.Grouping.Node;

public class SnippetGrouping {

  static final Node structure = new Node("root",
    new Node("Group1",
        new Node("Format",
            new Node("format")),
        new Node("Pricing",
          new Node("strike"),
          new Node("barier")),
        new Node("Dates",
          new Node("settlementDate"),
          new Node("expirationDate"))
        ),
    new Node("Group2",
        new Node("Sub Group 2.1",
          new Node("currency"),
          new Node("spot"),
          new Node("fwd"),
          new Node("vol")),
        new Node("Sub Group 2.1",
            new Node("value"),
            new Node("currencies"),
            new Node("expired"))
    )
  );
  private Matrix<Integer, Integer> matrix;
  private final int axisDirection;

  public SnippetGrouping(Shell shell, int axisDirection) {
    this.axisDirection = axisDirection;
    matrix = new Matrix<Integer, Integer>(shell, SWT.V_SCROLL | SWT.H_SCROLL);
    matrix.getAxisX().getBody().setCount(2);
    matrix.getAxisY().getBody().setCount(2);
    matrix.getAxisX().getHeader().setVisible(true);
    matrix.getAxisY().getHeader().setVisible(true);

    /* Create class holding the API and all the logic to achieve grouping effect
       in the given zone and along the given direction */
    Zone<Integer, Integer> zone = axisDirection == SWT.HORIZONTAL ? matrix.getHeaderX() : matrix.getHeaderY();
    Grouping grouping = new Grouping(zone, axisDirection, structure);
    grouping.dispose();
  }

  void pack() {
    // Optimize the column width
    Section<Integer> section = axisDirection == SWT.HORIZONTAL ?
        matrix.getAxisX().getBody() : matrix.getAxisX().getHeader();
    for (int i = 0; i < section.getCount(); i++) {
      section.setCellWidth(i);
    }
  }

  public static void main(String[] args) {
    final Shell shell = new Shell();
    shell.setLayout(new FillLayout());

    SnippetGrouping snippet = new SnippetGrouping(shell, SWT.HORIZONTAL);

    shell.setBounds(400, 200, 900, 400);
    shell.open();
    snippet.pack();
    Display display = shell.getDisplay();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }
}
