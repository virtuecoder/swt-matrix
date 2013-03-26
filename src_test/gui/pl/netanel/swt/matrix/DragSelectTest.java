package pl.netanel.swt.matrix;

import static org.junit.Assert.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
@SuppressWarnings({"unchecked", "rawtypes"})
public class DragSelectTest extends SwtTestCase {


	@Test public void select() throws Exception {
		final Matrix matrix = new Matrix(shell, SWT.H_SCROLL);
		Zone body = matrix.getBody();

		//    listenToAll(matrix);
		matrix.getAxisY().getBody().setCount(10);
		matrix.getAxisY().getHeader().setVisible(true);
		matrix.getAxisX().getBody().setCount(5);

		shell.setBounds(200, 200, 400, 300);
		shell.open();

		processEvents();
		Rectangle bounds1 = body.getCellBounds(1, 1);
		Rectangle bounds2 = body.getCellBounds(2, 2);
		Point p1 = middle(bounds1);
		Point p2 = middle(bounds2);
		click(p1);
		dragAndDrop(SWT.BUTTON1, p1, p2);
		assertEquals(4, body.getSelectedCount().intValue());
	}


	@Test public void selectAlter() throws Exception {
		final Matrix matrix = new Matrix(shell, SWT.H_SCROLL);
		Zone body = matrix.getBody();

		//    listenToAll(matrix);
		matrix.getAxisY().getBody().setCount(10);
		matrix.getAxisY().getHeader().setVisible(true);
		matrix.getAxisX().getBody().setCount(5);
		body.setSelected(1, 2, 1, 2, true);

		shell.setBounds(200, 200, 400, 300);
		shell.open();

		processEvents();
		Rectangle bounds1 = body.getCellBounds(3, 3);
		Rectangle bounds2 = body.getCellBounds(2, 2);
		Point p1 = middle(bounds1);
		Point p2 = middle(bounds2);
		dragAndDrop(SWT.BUTTON1 | SWT.MOD1, p1, p2);
		assertEquals(7, body.getSelectedCount().intValue());
	}

	@Test public void autoScroll() throws Exception {
	  final Matrix matrix = new Matrix(shell, SWT.H_SCROLL);
	  Zone body = matrix.getBody();

	  Axis axisX = matrix.getAxisX();
    axisX.getBody().setCount(1000);
	  matrix.getAxisY().getBody().setCount(10);

	  shell.setBounds(200, 200, 400, 300);
	  shell.open();

	  processEvents();
	  Rectangle bounds1 = body.getCellBounds(0, 0);
	  Rectangle bounds2 = body.getCellBounds(1, 0);
	  Point p1 = middle(bounds1);
	  Point p2 = middle(bounds2);
	  Point p3 = new Point(200 + 400 + 20, 215);
	  dragAndDrop(SWT.BUTTON1, p1, p2, p3);
	  assertTrue(axisX.getItemByViewportPosition(0).index.intValue() != 0);
	  assertTrue(axisX.getItemByViewportPosition(axisX.getViewportItemCount()-1).index.intValue() != 999);
	}
}
