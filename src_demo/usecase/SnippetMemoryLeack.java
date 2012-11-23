package usecase;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.ZoneEditor;
import pl.netanel.swt.matrix.reloaded.ints.Grouping;
import pl.netanel.swt.matrix.reloaded.ints.Grouping.Node;

public class SnippetMemoryLeack {
  public static void main(String[] args) throws InterruptedException {
    final Shell shell = new Shell();
    shell.setLayout(new FillLayout());

    shell.setBounds(400, 200, 600, 400);
    shell.open();

    matrix(shell);

    Display display = shell.getDisplay();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  static void matrix(final Shell shell) throws InterruptedException {

    for (int i = 0; i < 10000; i++) {
      System.out.println(i);

      Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.V_SCROLL);
      matrix.getAxisX().getBody().setCount(10);
      matrix.getAxisY().getBody().setCount(10);
      matrix.getAxisX().getHeader().setVisible(true);
      matrix.getAxisY().getHeader().setVisible(true);

      new ZoneEditor<Integer, Integer>(matrix.getBody());
      matrix.refresh();
      shell.layout(true);

      Thread.sleep(100);
      matrix.dispose();
    }
  }

  static void editor(final Shell shell) throws InterruptedException {
    Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.V_SCROLL);
    matrix.getAxisX().getBody().setCount(10);
    matrix.getAxisY().getBody().setCount(10);
    matrix.getAxisX().getHeader().setVisible(true);
    matrix.getAxisY().getHeader().setVisible(true);

    for (int i = 0; i < 10000; i++) {
      System.out.println(i);

      new ZoneEditor<Integer, Integer>(matrix.getBody());
      matrix.refresh();
      shell.layout(true);

      Thread.sleep(10);
    }
  }

  static void grouping(final Shell shell) throws InterruptedException {
    Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.V_SCROLL);
    matrix.getAxisX().getBody().setCount(10);
    matrix.getAxisY().getBody().setCount(10);
    matrix.getAxisX().getHeader().setVisible(true);
    matrix.getAxisY().getHeader().setVisible(true);

    for (int i = 0; i < 4000; i++) {
      System.out.println(i);

      Grouping grouping = new Grouping(matrix.getBody(), SWT.HORIZONTAL, new Node("root", new Node("1"), new Node("2")));
      matrix.refresh();
      shell.layout(true);

      Thread.sleep(10);
      grouping.dispose();
    }
  }
}
