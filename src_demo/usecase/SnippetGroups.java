package usecase;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.custom.Groups;

public class SnippetGroups {
  static String[][] headers = new String[][] {
    { "Group1", "Group2" },
    { "Format", "Pricing", "Dates", "Sub Group 2.1", "Sub Group 2.2"},
    { "format", "strike", "barier","settlementDate", "expirationDate",
        "currency", "spot", "fwd", "vol",
        "value", "carrencies", "expired" }
  };

  private Matrix<Integer, Integer> matrix;

  private Groups groups;


  public SnippetGroups(Shell shell) {
    matrix = new Matrix<Integer, Integer>(shell, SWT.V_SCROLL | SWT.H_SCROLL);
    matrix.getAxisX().getBody().setCount(12);
    matrix.getAxisY().getBody().setCount(2);
    matrix.getAxisX().getHeader().setVisible(true);
    matrix.getAxisY().getHeader().setVisible(true);

    /* Create class holding the API and all the logic to achieve grouping effect
       in the given zone and along the given direction */
    groups = new Groups(matrix.getHeaderX(), SWT.HORIZONTAL, headers);
    groups.setCollapse(SWT.BEGINNING);

    groups.get(0, 0).addChildren(0, 2);
    groups.get(0, 1).addChildren(3, 4);
    groups.get(1, 0).addChildren(0, 0);
    groups.get(1, 1).addChildren(1, 2);
    groups.get(1, 2).addChildren(3, 4);
    groups.get(1, 3).addChildren(5, 8);
    groups.get(1, 4).addChildren(9, 11);

    groups.layout();
    //pack();
  }

  void pack() {
    // Optimize the column width
    for (int i = 0; i < headers[2].length; i++) {
      matrix.getAxisX().getBody().setCellWidth(i);
    }
  }

  public static void main(String[] args) {
    final Shell shell = new Shell();
    shell.setLayout(new FillLayout());

    SnippetGroups snippet = new SnippetGroups(shell);

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
