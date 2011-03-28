package pl.netanel.swt.matrix;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.RGB;

import pl.netanel.util.Preconditions;



/**
 * Painter performs drawing operations on the given GC instance.
 * They are used for the whole matrix background painting as well as for the individual cells and lines. 
 * <p> 
 * The {@link #paint(int, int, int, int)} method is called in the loop to paint cells and lines.
 * So it is recommended to take as many operations out of it as possible in order to optimize 
 * the graphics performance. All the repetitive operations that are common to all elements, 
 * like setting a font or a background color can be done in the {@link #init()} method, which is called only once.
 * <p>
 * It's also a good practice to bring any of the GC attributes modified in {@link #init()} 
 * back to the default value to provide a clean start for a next painter. It can be odne in the {@link #clean()} method.  
 * <p>
 * This optimization is possible due to the fact that the {@link #paint(int, int, int, int)} method is called 
 * for all the cell or lines before the next painter is executed instead of executing all painters 
 * for a given cell before going to the next cell.   
 * 
 * @author jacek.p.kolodziejczyk@gmail.com
 * @created 2010-06-13
 */
// TODO add the paint(AxisLayoutIterator) here?
public class Painter<N0 extends Number, N1 extends Number> {
	/** 
	 * Single bounds for the whole component
	 */
	public static final int SCOPE_SINGLE = 0;
	/**
	 * Bounds for horizontal lines stretching from the left to the right edge of the component
	 */
	public static final int SCOPE_HORIZONTAL_LINES = 1;
	/**
	 * Bounds for vertical lines stretching from the top to the bottom edge of the component
	 */
	public static final int SCOPE_VERTICAL_LINES = 2;
	/**
	 * Bounds for compound cells each including all cells from one row 
	 */
	public static final int SCOPE_ROW_CELLS = 3;
	/**
	 * Bounds for compound cells each including all cells from one column 
	 */
	public static final int SCOPE_COLUMN_CELLS = 4;
	/**
	 * Bounds for individual cells inputed in the horizontal order
	 */
	public static final int SCOPE_CELLS_HORIZONTALLY = 5;
	/**
	 * Bounds for individual cells inputed in the vertical order
	 */
	public static final int SCOPE_CELLS_VERTICALLY = 6;
	
	protected GC gc;
	
	final ArrayList<Painter<N0, N1>> children = new ArrayList<Painter<N0, N1>>();
	final int scope;
	
	private final String name;
	private boolean enabled = true;
	
	public Painter(String name) {
		this(name, SCOPE_SINGLE);
	}

	public Painter(String name, int scope) {
		Preconditions.checkNotNullWithName(name, "name");
		this.name = name;
		this.scope = scope;
	}

	/**
	 * Returns the painter name;
	 * 
	 * @return the painter name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Initializes the GC property of the receiver to be used by its other methods.
	 * To change the painter initialization behavior override its protected {@link #init()} method. 
	 * @param gc
	 * @return 
	 */
	final boolean init(GC gc) {
		this.gc = gc;
		return init();
	};

	/** 
	 * To be called before any painting started.
	 * @return 
	 * @see <code>clean()</code>
	 */
	protected boolean init() { return children.isEmpty(); }

	/**
	 * To be called when the receiver has completed painting of all items.
	 * @see <code>init()</code>
	 */
	public void clean() {}

	public void paint(N0 index0, N1 index1, int x, int y, int width, int height) {}

	
	
	/**
	 * Sets the enabled state of the receiver.
	 * <p>
	 * Allows to communicate to the client to skip this painter in the painting sequence. 
	 * It can be used to hide lines for example.
	 * 
	 * @param enabled the new enabled state
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	/**
	 * Returns true if the painter is enabled.
	 * <p>
	 * Communicates to the client to skip this painter in the painting sequence.
	 * @return the enabled state
	 */
	public boolean isEnabled() {
		return enabled;
	}

	
	
	protected int align(int align, int margin, int distance, int width, int bound) {
		switch (align) {
		// Fast return
		case SWT.LEFT: case SWT.TOP: case SWT.BEGINNING:
			return distance + margin;
		case SWT.CENTER:
			return distance + (bound - width) / 2; 
		case SWT.RIGHT: case SWT.BOTTOM: case SWT.END:
			return distance + bound - width - margin; 
		}
		return distance + margin;
	}
	
	
	
	/*------------------------------------------------------------------------
	 * List methods 
	 */

	public void add(Painter painter) {
		add(children.size(), painter);
	}

	public void add(int index, Painter painter) {
		// Check uniqueness of children names
		for (int i = 0, imax = children.size(); i < imax; i++) {
			Painter painter2 = children.get(i);
			Preconditions.checkArgument(!painter2.name.equals(painter.name), 
				"A painter with '{0}' name already exist in this collection", painter.name);
		}
		children.add(index, painter);
	}
	
	public void set(int index, Painter painter) {
		// Check uniqueness of children names
		for (int i = 0, imax = children.size(); i < imax; i++) {
			if (i == index) continue;
			Painter painter2 = children.get(i);
			Preconditions.checkArgument(!painter2.name.equals(painter.name), 
					"A painter with '{0}' name already exist in this collection", painter.name);
		}
		children.set(index, painter);
	}
	
	public void replace(Painter painter) {
		set(indexOf(painter.name), painter);
	}
	
	public void remove(int index) {
		children.remove(index);
	}
	
	public int indexOf(String name) {
		for (int i = 0, imax = children.size(); i < imax; i++) {
			if (children.get(i).name.equals(name)) {
				return i;
			}
		}
		return -1;
	}

	public Painter get(String name) {
		return children.get(indexOf(name));
	}

	
	
	
	/*------------------------------------------------------------------------
	 * Static members 
	 */
	
	static RGB blend(RGB c1, RGB c2, int ratio) {
		int r = blend(c1.red, c2.red, ratio);
		int g = blend(c1.green, c2.green, ratio);
		int b = blend(c1.blue, c2.blue, ratio);
		return new RGB(r, g, b);
	}
	
	private static int blend(int v1, int v2, int ratio) {
		return (ratio*v1 + (100-ratio)*v2)/100;
	}

	
	
}
