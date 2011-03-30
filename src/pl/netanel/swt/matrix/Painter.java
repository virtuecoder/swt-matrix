package pl.netanel.swt.matrix;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.RGB;

import pl.netanel.util.Preconditions;


/**
 * This class draws everything that appears on the matrix canvas: background, images, text, lines.
 * 
 * <h3>Optimization</h3>
 * Because the {@link #paint(Number, Number, int, int, int, int)} method is called in the loop to paint cells and lines 
 * then it is recommended to take as many operations out of it as possible in order to improve the
 * the graphics performance. All the repetitive operations that are common to all elements, 
 * like setting a font or a background color can be done in the {@link #init()} method, which is called only once.
 * <p>
 * It's also a good practice to restore any of the GC attributes modified by in {@link #init()} or {@link #paint(Number, Number, int, int, int, int)} 
 * back to the default value to provide a clean start for a next painter. 
 * It can be done in the {@link #clean()} method.  
 * <p>
 * This optimization is possible due to replacing of painting operations loop with the cell iteration loop.
 * In Matrix cell iteration happens inside of the painters iteration and it can still fold to a single
 * cell iteration if all the drawing is done by a single painter.
 * 
 * 	
 * @author jacek.p.kolodziejczyk@gmail.com
 * @created 2010-06-13
 */
// TODO add the paint(AxisLayoutIterator) here?
public class Painter<N0 extends Number, N1 extends Number> {
	/** 
	 * Single scope of the whole container
	 */
	public static final int SCOPE_SINGLE = 0;
	/**
	 * Horizontal lines stretching from the left to the right edge of the container
	 */
	public static final int SCOPE_HORIZONTAL_LINES = 1;
	/**
	 * Vertical lines stretching from the top to the bottom edge of the container
	 */
	public static final int SCOPE_VERTICAL_LINES = 2;
	/**
	 * Compound cells each including all cells of a single row 
	 */
	public static final int SCOPE_ROW_CELLS = 3;
	/**
	 * Compound cells each including all cells of a single column 
	 */
	public static final int SCOPE_COLUMN_CELLS = 4;
	/**
	 * Individual cells in horizontal order. Aids graphics performance in case of drawing homogenic rows.
	 */
	public static final int SCOPE_CELLS_HORIZONTALLY = 5;
	/**
	 * Individual cells in vertical order. Aids graphics performance in case of drawing homogenic columns. 
	 */
	public static final int SCOPE_CELLS_VERTICALLY = 6;

	/**
	 * Provides graphic to the {@link #init()}, {@link #clean()}, {@link #paint(Number, Number, int, int, int, int)} methods. 
	 * It is not safe to use it inside of other methods.
	 */
	protected GC gc;

	final int scope;
	final String name;
	private boolean enabled = true;
	
	/**
	 * Constructor with the scope defaulted to {@link #SCOPE_SINGLE}. 
	 * @param name the name of the painter, must be unique in the collection to which it is added
	 */
	public Painter(String name) {
		this(name, SCOPE_SINGLE);
	}

	/**
	 * The main constructor.
	 * 
	 * @param name the name of the painter, must be unique in the collection to which it is added
	 * @param scope the scope of the painter deciding on the order and size of the boundaries 
	 * the {@link #paint(Number, Number, int, int, int, int)} method receives. 
	 * The value must be one of the Painter constants prefixed with <code>SCOPE_</code>.
	 */
	public Painter(String name, int scope) {
		Preconditions.checkNotNullWithName(name, "name");
		this.name = name;
		this.scope = scope;
	}

	/**
	 * Returns the painter name.
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
	 * @return true if the initialization succeeded or false otherwise.
	 */
	final boolean init(GC gc) {
		this.gc = gc;
		return init();
	};

	/** 
	 * Allows graphic optimization by performing operation that can be taken out of the cell painting loop.
	 * <p>
	 * If this method returns false the {@link #paint(Number, Number, int, int, int, int)} 
	 * and {@link #clean()} methods will not be executed.
	 * 
	 * @return true if the initialization succeeded or false otherwise.
	 * @see <code>clean()</code>
	 */
	protected boolean init() { return true; }

	/**
	 * Restores the default {@link GC} settings modified by modified by in {@link #init()} 
	 * or {@link #paint(Number, Number, int, int, int, int)}.
	 * @see <code>init()</code>
	 */
	public void clean() {}

	/**
	 * Draws on the canvas within the given boundaries according to the given indexes. 
	 * <p>
	 * The types of the <code>index0</code>, <code>index1</code> arguments are not checked 
	 * in the runtime for performance reasons. Thus the use of generics is recommended to
	 * check against wrong type in compile time; 
	 * <p>
	 * <code>index0</code> is always null when the receiver's scope is one of the following:<ul>
	 *        <li>{@link #SCOPE_COLUMN_CELLS}, <li>{@link #SCOPE_VERTICAL_LINES}, <li>{@link #SCOPE_SINGLE}</ul>
	 * <code>index1</code> is always null when the receiver's scope is one of the following:<ul>
	 *        <li>{@link #SCOPE_ROW_CELLS}, <li>{@link #SCOPE_HORIZONTAL_LINES}, <li>{@link #SCOPE_SINGLE}</ul>
	 * @param index0 index of a section item in the row axis. 
	 * @param index1 index of a section item in the column axis 
	 * @param x the x coordinate of the boundaries
	 * @param y the y coordinate of the boundaries
	 * @param width the width of the boundaries
	 * @param height the height of the boundaries
	 */
	public void paint(N0 index0, N1 index1, int x, int y, int width, int height) {}

	
	
	/**
	 * Sets the enabled state of the receiver.
	 * <p>
	 * Allows to skip the receiver in the painting sequence. 
	 * It can be used to hide/show the lines for example.
	 * 
	 * @param enabled the new enabled state
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	/**
	 * Returns true if the painter is enabled, or false otherwise.
	 * <p>
	 * Communicates to the client to skip this painter in the painting sequence. 
	 * It can be used to hide/show the lines for example.
	 * 
	 * @return the enabled state
	 */
	public boolean isEnabled() {
		return enabled;
	}

	
	/**
	 * Returns the distance of a graphical element based on the align mode and the padding margin.
	 *  
	 * @param align the alignment mode, one of: {@link SWT#LEFT}, {@link SWT#RIGHT}, {@link SWT#CENTER}, 
	 * 		{@link SWT#TOP}, {@link SWT#BOTTOM}, {@link SWT#BEGINNING}, {@link SWT#END}
	 * @param margin the number of pixels from the edge to which to align, does not matter with {@link SWT#CENTER}
	 * @param distance the distance of the cell 
	 * @param width the width of the element
	 * @param bound the width of the cell
	 * @return
	 */
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
