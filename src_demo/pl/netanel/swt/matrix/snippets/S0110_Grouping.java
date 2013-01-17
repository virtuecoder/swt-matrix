package pl.netanel.swt.matrix.snippets;

import static pl.netanel.swt.matrix.reloaded.ints.Grouping.Node.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.Zone;
import pl.netanel.swt.matrix.reloaded.ints.Grouping;
import pl.netanel.swt.matrix.reloaded.ints.Grouping.Node;

public class S0110_Grouping {

  static final Node structure = new Node("root",
    new Node("Group1",
      new Node("Pricing",              // When collapsed to the first child will remain visible by default
        new Node("strike"),
        new Node("barier"),
        new Node("summary", SUMMARY)), // It will be hidden when expanded and show only when collapsed
      new Node("Dates",                // When collapsed last child will remain visible
        new Node("settlementDate"),
        new Node("expirationDate", REMAIN))
      ),
    new Node("Group2",
      // When collapsed 1st and 3rd child will be visible. This node will be initially collapsed
      new Node("Sub Group 2.1", REMAIN | COLLAPSED,
        new Node("currency", REMAIN),
        new Node("spot"),
        new Node("fwd", REMAIN),
        new Node("vol")
      ),
      new Node("Sub Group 2.2", PERMANENT, // It will be not possible to collapse this node
        new Node("value"),
        new Node("currencies"),
        new Node("expired")
      )
    )
  );
  private Matrix<Integer, Integer> matrix;
  private final int axisDirection;

  public S0110_Grouping(Shell shell, int axisDirection) {
    this.axisDirection = axisDirection;
    matrix = new Matrix<Integer, Integer>(shell, SWT.V_SCROLL | SWT.H_SCROLL);
    matrix.getAxisX().getBody().setCount(2);
    matrix.getAxisY().getBody().setCount(2);
    matrix.getAxisX().getHeader().setVisible(true);
    matrix.getAxisY().getHeader().setVisible(true);

    Zone<Integer, Integer> zone = axisDirection == SWT.HORIZONTAL ? matrix.getHeaderX() : matrix.getHeaderY();

    /* Create class holding the API and all the logic to achieve grouping effect
       in the given zone and along the given direction */
    Grouping grouping = new Grouping(zone, axisDirection, structure);
    grouping.getRoot().setCollapsedAll(true);

    matrix.getAxisX().getBody().setHidden(6, true); // It will be not visible when group is expanded
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

    S0110_Grouping snippet = new S0110_Grouping(shell, SWT.HORIZONTAL);

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

  static final String title = "Goruping";
  static final String instructions = "";
}
