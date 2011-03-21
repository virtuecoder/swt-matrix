package pl.netanel.swt.matrix;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Layout.LayoutSequence;

public class TestUtil {
	static MutableNumber number(int n) {
		return new MutableInt(n);
	}
	
	static Extent extent(int start, int end) {
		return new Extent(number(start), number(end));
	}
	
	static Extent extent(int n) {
		return new Extent(number(n), number(n));
	}
	
	public static AxisItem item(Section section, int index) {
		return new AxisItem(section.core, index);
	}
	
	public static AxisItem item(Axis model, int section, int index) {
		return new AxisItem(model.getSection(section).core, index);
	}
	
	static NumberSet indexSet() {
		return new NumberSet(IntMath.getInstance());
	}
	
	static NumberSet indexSet(int start, int end) {
		NumberSet set = new NumberSet(IntMath.getInstance());
		set.add(start, end);
		return set;
	}
	
	
	public static String indexes(LayoutSequence seq) {
		StringBuilder sb = new StringBuilder();
		for (seq.init(); seq.next();) {
			if (sb.length() > 0) sb.append(", ");
			sb.append(seq.getItem().index);
		}
		return sb.toString();
	}
	
	public static String distances(LayoutSequence seq) {
		StringBuilder sb = new StringBuilder();
		for (seq.init(); seq.next();) {
			if (sb.length() > 0) sb.append(", ");
			sb.append(seq.getDistance());
		}
		return sb.toString();
	}
	
	public static String widths(LayoutSequence seq) {
		StringBuilder sb = new StringBuilder();
		for (seq.init(); seq.next();) {
			if (sb.length() > 0) sb.append(", ");
			sb.append(seq.getWidth());
		}
		return sb.toString();
	}
	
	public static Layout layout(int ...count) {
		Section[] sections = new Section[count.length];
		for (int i = 0; i < count.length; i++) {
			Section section = new Section(int.class);
			sections[i] = section;
			if (i != 1) section.setNavigationEnabled(false);
			section.setCount(count[i]);
		}
		Layout layout = new Layout(new Axis(sections));
		return layout;
	}

	public static void showMatrix(Layout layout) {
		Axis rowModel = new Axis(); rowModel.getBody().setCount(1);
		
		// Make the columns variable width
//		Matrix matrix = new Matrix(shell, SWT.NONE);
		Shell shell = new Shell();
		Matrix matrix = new Matrix(shell, SWT.V_SCROLL | SWT.H_SCROLL, rowModel, layout.model);
		matrix.layout1 = layout;
//		matrix.rows.setHeaderVisible(true);
		
		//matrix.getZone(Zone.BODY).cellPainters.add(new DefaultBodyTextPainter());
		
		matrix.setBounds(0, 0, layout.getViewportSize() + matrix.getVerticalBar().getSize().x + 1, 400);
//		shell.pack();
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



}
