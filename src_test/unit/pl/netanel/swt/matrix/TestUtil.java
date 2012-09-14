package pl.netanel.swt.matrix;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.DirectionIndexSequence.Forward;

@SuppressWarnings({"rawtypes", "unchecked"})
class TestUtil {
	static MutableNumber number(int n) {
		return new MutableInt(n);
	}

	static MutableExtent extent(int start, int end) {
		return new MutableExtent(number(start), number(end));
	}

	static MutableExtent extent(int n) {
		return new MutableExtent(number(n), number(n));
	}

	public static AxisItem<Integer> item(Section section, int index) {
	  if (section instanceof SectionCore) {
	    section = new SectionClient((SectionCore) section);
	  }
		return AxisItem.create(section, index);
	}

	static NumberSet numberSet() {
		return new NumberSet(IntMath.getInstance());
	}

	static NumberSet numberSet(int n) {
		NumberSet set = new NumberSet(IntMath.getInstance());
		set.add(n);
		return set;
	}

	static NumberSet numberSet(int start, int end) {
		NumberSet set = new NumberSet(IntMath.getInstance());
		set.add(start, end);
		return set;
	}


	public static String indexes(AxisLayoutSequence seq) {
		StringBuilder sb = new StringBuilder();
		for (seq.init(); seq.next();) {
			if (sb.length() > 0) sb.append(", ");
			sb.append(seq.getIndex());
		}
		return sb.toString();
	}

	public static String distances(AxisLayoutSequence seq) {
		StringBuilder sb = new StringBuilder();
		for (seq.init(); seq.next();) {
			if (sb.length() > 0) sb.append(", ");
			sb.append(seq.getDistance());
		}
		return sb.toString();
	}

	public static String widths(AxisLayoutSequence seq) {
		StringBuilder sb = new StringBuilder();
		for (seq.init(); seq.next();) {
			if (sb.length() > 0) sb.append(", ");
			sb.append(seq.getWidth());
		}
		return sb.toString();
	}

	public static AxisLayout layout(int ...count) {
		Axis axis = new Axis(Integer.class, count.length);
		AxisLayout layout = axis.layout;
		for (int i = 0; i < count.length; i++) {
		  SectionCore section = layout.getSection(i);
		  if (i != 1) {
		    section.setFocusItemEnabled(false);
		  }
		  section.setCount(count[i]);
		  section.setVisible(true);
		}
		layout.backward.rewind();
		layout.backwardNavigator.rewind();
		return layout;
	}

	public static <T> List<T> getList(Iterator<T> it) {
	  ArrayList<T> list = new ArrayList<T>();
	  while(it.hasNext()) {
	    list.add(it.next());
	  }
	  return list;
	}

	public static void showMatrix(AxisLayout layout) {
	  System.out.println("doing nothing");

//		Axis rowModel = new Axis(); rowModel.getBody().setCount(1);
//
//		// Make the columns variable width
////		Matrix matrix = new Matrix(shell, SWT.NONE);
//		Shell shell = new Shell();
//		Matrix matrix = new Matrix(shell, SWT.V_SCROLL | SWT.H_SCROLL, layout.axis, rowModel);
//		matrix.layoutX = layout;
////		matrix.rows.setHeaderVisible(true);
//
//		//matrix.getZone(Zone.BODY).cellPainters.add(new DefaultBodyTextPainter());
//
//		matrix.setBounds(0, 0, layout.getViewportSize() + matrix.getVerticalBar().getSize().x + 1, 400);
////		shell.pack();
//		shell.setLayout(new FillLayout());
//		shell.setBounds(400, 300, 600, 400);
//		shell.open();
//		Display display = shell.getDisplay();
//
//        while (!shell.isDisposed()) {
//    		if (!display.readAndDispatch()) {
//    			display.sleep();
//    		}
//        }
	}

	public static void showMatrix(Matrix matrix) {
		Shell shell = matrix.getShell();
		shell.setLayout(new FillLayout());
		shell.setBounds(400, 300, 600, 400);
		shell.open();
		Display display = shell.getDisplay();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

  public static void log(Object ...o) {
    for (int i = 0; i < o.length; i++) {
      if (i > 0) System.out.print(", ");
      System.out.print(o[i]);
      System.out.flush();
    }
    System.out.println();
    System.out.flush();
  }

  public static String seq(SectionCore section) {
    StringBuilder sb = new StringBuilder();
    Forward seq = new DirectionIndexSequence.Forward(section);
    for (seq.init(); seq.next();) {
      if (sb.length() > 0) sb.append(", ");
      sb.append(seq.index());
    }
    return sb.toString();
  }
}
