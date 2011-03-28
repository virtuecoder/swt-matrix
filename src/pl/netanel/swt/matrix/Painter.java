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
public class Painter {
	public static final int FULL = 0;
	public static final int ROW_LINE = 1;
	public static final int COLUMN_LINE = 2;
	public static final int ROW_CELL = 3;
	public static final int COLUMN_CELL = 4;
	public static final int CELL = 5;
	
	protected GC gc;
	protected boolean enabled = true;
	public ArrayList<Painter> children = new ArrayList<Painter>();
	private final String name;
//	protected SizeMeter meter;
	private final int scope;
	
	public Painter(String name) {
		this(name, FULL);
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

	/**
	 * To called before the <code>paint()</code> method.
	 * @param index0
	 * @param index1
	 */
	public boolean beforePaint(Number index0, Number index1) { return true; }
	
	public void paint(int x, int y, int width, int height) {
	}

	public void run() {
//		init();
//		paint(x, y, width, height);
//		clean();
		
		for (Painter painter: children) {
			painter.run();
		}
	}
	
	/**
	 * Sets the enabled state of the receiver.
	 * <p>
	 * Allows to communicate to the client to skip this painter in the painting sequence.
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


	
//	public SizeMeter getSizeMeter() {
//		return meter;
//	}
//
//	public void setMeter(SizeMeter meter) {
//		this.meter = meter;
//	}
	
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

	
	
	void paint(GC gc, BoundsProvider provider) {
		if (!enabled) return;
		if (init(gc)) {
			BoundsSequence seq = provider.getSequence(scope);
			for (seq.init(); seq.next();) {
				if (beforePaint(seq.getIndex0(), seq.getIndex1())) {
					paint(seq.seq1.getDistance(), seq.seq0.getDistance(), seq.seq1.getWidth(), seq.seq0.getWidth());
				}
			}
			clean();
		}
		
		// Paint children
		for (Painter painter: children) {
			painter.paint(gc, provider);
		}
	}

//	public void paint(Rectangle bounds) {
//		paint(bounds.x, bounds.y, bounds.width, bounds.height);
//	}

	public void add(Painter painter) {
		children.add(painter);
	}

	public int indexOf(String name) {
		for (int i = 0, imax = children.size(); i < imax; i++) {
			if (children.get(i).name.equals(name)) {
				return i;
			}
		}
		return -1;
	}

	public void set(int index, Painter painter) {
		children.set(index, painter);
	}

	public void add(int i, Painter painter) {
		children.add(i, painter);
	}

	public void replace(Painter painter) {
		set(indexOf(painter.name), painter);
	}

	public Painter get(String name) {
		return children.get(indexOf(name));
	}

}
