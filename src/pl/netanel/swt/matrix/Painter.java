package pl.netanel.swt.matrix;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.TextLayout;

import pl.netanel.util.Arrays;
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
   * Default name of a painter belonging to z zone and responsible to paint its
   * cells
   */
  public static final String NAME_CELLS = "cells";
  /**
   * Default name of a painter belonging to z zone and responsible to paint its
   * row lines
   */
  public static final String NAME_ROW_LINES = "row lines";
  /**
   * Default name of a painter belonging to z zone and responsible to paint its
   * column lines
   */
  public static final String NAME_COLUMN_LINES = "column lines";
  /**
   * Default name of a painter belonging to matrix and responsible to paint the
   * area not frozen
   */
  public static final String NAME_FROZEN_NONE_NONE = "frozen none none";
  /**
   * Default name of a painter belonging to matrix and responsible to paint the
   * area that is frozen only at the horizontal end of the viewport.
   */
  public static final String NAME_FROZEN_NONE_TAIL = "frozen none tail";
  /**
   * Default name of a painter belonging to matrix and responsible to paint the
   * area that is frozen only at the vertical end of the viewport.
   */
  public static final String NAME_FROZEN_TAIL_NONE = "frozen tail none";
  /**
   * Default name of a painter belonging to matrix and responsible to paint the
   * area that is frozen only at the horizontal start of the viewport.
   */
  public static final String NAME_FROZEN_NONE_HEAD = "frozen none head";
  /**
   * Default name of a painter belonging to matrix and responsible to paint the
   * area that is frozen only at the vertical start of the viewport.
   */
  public static final String NAME_FROZEN_HEAD_NONE = "frozen head none";
  /**
   * Default name of a painter belonging to matrix and responsible to paint the
   * area that is frozen both at the vertical start and horizontal end of the
   * viewport.
   */
  public static final String NAME_FROZEN_HEAD_TAIL = "frozen head tail";
  /**
   * Default name of a painter belonging to matrix and responsible to paint the
   * area that is frozen both at the vertical end and horizontal start of the
   * viewport.
   */
  public static final String NAME_FROZEN_TAIL_HEAD = "frozen tail head";
  /**
   * Default name of a painter belonging to matrix and responsible to paint the
   * area that is frozen both at the vertical end and horizontal end of the
   * viewport.
   */
  public static final String NAME_FROZEN_TAIL_TAIL = "frozen tail tail";
  /**
   * Default name of a painter belonging to matrix and responsible to paint the
   * area that is frozen both at the vertical start and horizontal start of the
   * viewport.
   */
  public static final String NAME_FROZEN_HEAD_HEAD = "frozen head head";
  /**
   * Default name of a painter belonging to a matrix and responsible to paint
   * the focus cell.
   */
  public static final String NAME_FOCUS_CELL = "focus cell";
  /**
   * Default name of a painter belonging to a zone and responsible to paint the
   * emulated controls.
   */
  public static final String NAME_EMULATED_CONTROLS = "emulated controls";
  /**
   * Default name of a painter belonging to a zone and responsible to
   * create/dispose or show/hide the embedded controls.
   */
  public static final String NAME_EMBEDDED_CONTROLS = "embedded controls";
	
	private static int[] EXTENT_ALIGN = {SWT.RIGHT, SWT.END, SWT.BOTTOM, SWT.CENTER};
	static enum TextClipMethod {DOTS_IN_THE_MIDDLE, DOTS_AT_THE_END, CUT, NONE};
	
	/**
	 * Provides graphic to the {@link #init()}, {@link #clean()}, 
	 * {@link #paint(Number, Number, int, int, int, int)} methods. 
	 * It is not safe to use it inside of other methods.
	 */
	protected GC gc;

	final int scope;
	final String name;
	private boolean enabled = true;
	
	/**
	 * Text to be painted. The placement of the text depends on the {@link #textAlignX}, 
	 * {@link #textAlignY}, {@link #textMarginX}, {@link #textMarginY} properties.
	 */
	public String text;
	/**
	 * Image to be painted. The placement of the image depends on the {@link #imageAlignX}, 
	 * {@link #imageAlignY}, {@link #imageMarginX}, {@link #imageMarginY} properties.
	 */
	public Image image;
	/**
	 * Foreground color.
	 */
	public Color foreground;
	/**
	 * Background color.
	 */
	public Color background;
	
	/**
	 * Horizontal text alignment. One of the following constants defined in class SWT: 
	 * SWT.LEFT, SWT.RIGHT, SWT.CENTER, SWT.BEGINING, SWT.END.
	 */
	public int textAlignX = SWT.BEGINNING;
	/**
	 * Vertical text alignment. One of the following constants defined in class SWT: 
	 * SWT.LEFT, SWT.RIGHT, SWT.CENTER, SWT.BEGINING, SWT.END.
	 */
	public int textAlignY = SWT.BEGINNING;
	/**
	 * Horizontal image alignment. One of the following constants defined in class SWT: 
	 * SWT.LEFT, SWT.RIGHT, SWT.CENTER, SWT.BEGINING, SWT.END.
	 */
	public int imageAlignX = SWT.BEGINNING;
	/**
	 * Vertical image alignment. One of the following constants defined in class SWT: 
	 * SWT.TOP, SWT.BOTTOM, SWT.CENTER, SWT.BEGINING, SWT.END.
	 */
	public int imageAlignY = SWT.BEGINNING;
	/**
	 * Horizontal text margin. It is measured from the cell boundaries 
	 * (which don't include dividing lines). 
	 */
	public int textMarginX;
	/**
	 * Vertical text margin. It is measured from the cell boundaries 
	 * (which don't include dividing lines). 
	 */
	public int textMarginY;
	/**
	 * Horizontal image margin. It is measured from the cell boundaries 
	 * (which don't include dividing lines). 
	 */
	public int imageMarginX;
	/**
	 * Vertical image margin. It is measured from the cell boundaries 
	 * (which don't include dividing lines). 
	 */
	public int imageMarginY;
	
	/**
	 * Word wrapping for text in cells. 
	 */
	private boolean wordWrap;

	TextClipMethod textClipMethod;
	Zone<N0, N1> zone;
	Matrix<N0, N1> matrix;

	private Color lastForeground, lastBackground, defaultBackground, defaultForeground,  
		 selectionBackground, selectionForeground;
//	private boolean shouldHighlight;
//	private boolean backgroundEnabled, foregroundEnabled;

	private Font lastFont;
	private int[] extentCache;
	private Point extent;
  private TextLayout textLayout;
  private Rectangle clipping;

	
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
		textMarginY = 1; textMarginX = 4;
		textAlignY = SWT.BEGINNING; textAlignX = SWT.BEGINNING;
		textClipMethod = TextClipMethod.DOTS_IN_THE_MIDDLE;

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
   * Allows graphic optimization by performing operation that can be taken out
   * of the cell painting loop.
   * <p>
   * If this method returns false the
   * {@link #paint(Number, Number, int, int, int, int)} and {@link #clean()}
   * methods will not be executed.
   * 
   * @return true if the initialization succeeded or false otherwise.
   * @see <code>clean()</code>
   */
	protected boolean init() {
		if (scope < SCOPE_CELLS_HORIZONTALLY) return true;
		lastForeground = defaultForeground = zone.getDefaultForeground();
		lastBackground = defaultBackground = zone.getDefaultBackground();
		selectionBackground = zone.getSelectionBackground();
		selectionForeground = zone.getSelectionForeground();
		if (lastForeground != null) {
		  gc.setForeground(lastForeground);
		}
		if (lastBackground != null) {
			gc.setBackground(lastBackground);
			gc.fillRectangle(zone.getBounds());
		}
//		backgroundEnabled = zone.isBackgroundEnabled();
//		foregroundEnabled = zone.isForegroundEnabled();
		
		// Body must be checked otherwise a header of a single item selected would not be highlighted 
//		BigInteger selectionCount = BigInteger.ZERO;
//		for (Zone zone: matrix) {
//		  selectionCount = selectionCount.add(zone.getSelectedCount());
//		}
//		
//		shouldHighlight = 
//		  !zone.section0.isFocusItemEnabled() ||
//		  !zone.section1.isFocusItemEnabled() ||
//		  selectionCount.compareTo(BigInteger.ONE) > 0;
		  
//		shouldHighlight = zone.isSingleCellSelectionHighlight() || 
//		  zone.getSelectionCount().compareTo(BigInteger.ONE) != 0;
		
		
		extentCache = FontWidthCache.get(gc, gc.getFont());
		extent = new Point(-1, gc.stringExtent("ty").y);
		clipping = gc.getClipping();
		return true; 
	}
	

	/**
	 * Restores the default {@link GC} settings modified by modified by in {@link #init()} 
	 * or {@link #paint(Number, Number, int, int, int, int)}.
	 * @see <code>init()</code>
	 */
	public void clean() {
	  gc.setClipping(clipping);
	}

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
   * @param x the x coordinate of the painting boundaries
   * @param y the y coordinate of the painting boundaries
   * @param width the width of the painting boundaries
   * @param height the height of the painting boundaries
	 */
	public void paint(N0 index0, N1 index1, int x, int y, int width, int height) {
	  setup(index0, index1);
	  if (isWordWrap()) {
	    gc.setClipping(x, y, width, height);
	  }
	  
		Color foreground2 = foreground == null ? defaultForeground : foreground; 
		Color background2 = background == null ? defaultBackground : background;
		if (zone != null && zone.isSelected(index0, index1)) {
			// TODO Revise and maybe optimize the background / foreground color setting algorithm
			foreground2 = selectionForeground;  
			background2 = selectionBackground;
		}
		
		// Only set color if there is a change
		if (foreground2 != null && !foreground2.equals(lastForeground)) {
			gc.setForeground(lastForeground = foreground2);
		}
		if (background2 != null) {
			if (!background2.equals(lastBackground)) {
				gc.setBackground(lastBackground = background2);
			}
			if (!background2.equals(defaultBackground)) {
				gc.fillRectangle(x, y, width, height);
			}
		}
		
//		lineWidth0 = zone.section0.getLineWidth(index0);
//		lineWidth1 = zone.section1.getLineWidth(index1);
//		lineColor = Resources.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW);
		int x2 = x, y2 = y;
		int x3 = x, y3 = y;
		
		image = getImage(index0, index1);
//		imageAlignX = getImageAlignX(index0, index1);
//		imageAlignY = getImageAlignY(index0, index1);
		if (image != null) {
			Rectangle bounds = image.getBounds();
			switch (imageAlignX) {
			case SWT.BEGINNING: case SWT.LEFT: case SWT.TOP: 
				x2 += imageMarginX; x3 += bounds.width; break;
			case SWT.CENTER:
				x2 += (width - bounds.width) / 2; break; 
			case SWT.RIGHT: case SWT.END: case SWT.BOTTOM:
				x2 += width - bounds.width - imageMarginX; break;
			}
			switch (imageAlignY) {
			case SWT.BEGINNING: case SWT.TOP: case SWT.LEFT:
				y2 += imageMarginY; break;
			case SWT.CENTER:
				y2 += (height - bounds.height) / 2; break; 
			case SWT.BOTTOM: case SWT.END: case SWT.RIGHT:
				y2 += height - bounds.height - imageMarginY; break;
			}
			Rectangle lastClipping = gc.getClipping();
			gc.setClipping(x, y, width, height);
			gc.drawImage(image, x2, y2);
			gc.setClipping(lastClipping);
			width -= bounds.width;
		}
		
		text = getText(index0, index1);
//		textAlignX = getTextAlignX(index0, index1);
//		textAlignY = getTextAlignY(index0, index1);
		if (text != null) {
		  
		  if (textClipMethod == TextClipMethod.DOTS_IN_THE_MIDDLE) {
        text = FontWidthCache.shortenTextMiddle(text, width - textMarginX * 2, extent, extentCache);      
      } 
      else if (textClipMethod == TextClipMethod.DOTS_AT_THE_END) {
        text = FontWidthCache.shortenTextEnd(text, width - textMarginX * 2, extent, extentCache);     
      } 
      // Compute extent only when font changes or text horizontal align is center or right  
      else if (lastFont != null && lastFont != gc.getFont() || 
        Arrays.contains(EXTENT_ALIGN, textAlignX)) {
        extent = gc.stringExtent(text);
      }
      
		  
		  switch (textAlignX) {
      case SWT.BEGINNING: case SWT.LEFT: case SWT.TOP: 
        x3 += textMarginX; break;
      case SWT.CENTER:
        x3 += (width - extent.x) / 2; break; 
      case SWT.RIGHT: case SWT.END: case SWT.BOTTOM:
        x3 += width - extent.x - textMarginX; break;
      }
      switch (textAlignY) {
      case SWT.BEGINNING: case SWT.TOP: case SWT.LEFT:
        y3 += textMarginY; break;
      case SWT.CENTER:
        y3 += (height - extent.y) / 2; break; 
      case SWT.BOTTOM: case SWT.END: case SWT.RIGHT:
        y3 += height - extent.y - textMarginY; break;
      }
      
		  if (isWordWrap()) {
		    if (textLayout == null) {
		      textLayout = new TextLayout(gc.getDevice());
		      getMatrix().addDisposeListener(new DisposeListener() {
		        @Override public void widgetDisposed(DisposeEvent e) {
		          textLayout.dispose();
		        }
		      });
		    }
		    textLayout.setFont(gc.getFont());
		    textLayout.setText(text);
		    textLayout.setAlignment(textAlignX);
		    textLayout.setWidth(width < 1 ? 1 : width - textMarginX);

//		    Rectangle clipping2 = gc.getClipping();
		    textLayout.draw(gc, x3, y3);
		  } 
		  else {
//			if (width < 4 || height < 4) return;
		    
		    
		    gc.drawString(text, x3, y3, true);
		  }
		}
	}
	
	public void setup(N0 index0, N1 index1) {
	  
	}

  public int getTextAlignY(N0 index0, N1 index1) {
		return textAlignX;
	}

	public int getTextAlignX(N0 index0, N1 index1) {
		return textAlignY;
	}
	
	public int getImageAlignY(N0 index0, N1 index1) {
		return imageAlignX;
	}
	
	public int getImageAlignX(N0 index0, N1 index1) {
		return imageAlignY;
	}

	/**
	 * Returns the text to be drawn by the painter.
	 * <p>
	 * This method is used both by {@link #paint(Number, Number, int, int, int, int)} 
	 * and by {@link #computeHeight(Number, Number)} and {@link #computeWidth(Number, Number)}
	 *  
	 * @param index0 row index of the cell  
	 * @param index1 column index of the cell 
	 * @return the text to be drawn by the painter
	 */
	public String getText(N0 index0, N1 index1) {
		return text;
	}
	
	/**
	 * Returns the image to be drawn by the painter.
	 * <p>
	 * This method is used both by {@link #paint(Number, Number, int, int, int, int)} 
	 * and by {@link #computeHeight(Number, Number)} and {@link #computeWidth(Number, Number)}
	 *  
	 * @param index0 row index of the cell  
	 * @param index1 column index of the cell 
	 * @return the image to be drawn by the painter
	 */
	public Image getImage(N0 index0, N1 index1) {
		return image;
	}
	
	
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


//	/**
//	 * Computes the optimal width to fit the content of the cell at specified indexes.
//	 * <p>
//	 * To be called only by the framework, since the {@link GC} instance must be
//	 * injected to measure the text extent.
//	 * <p>
//	 * <code>index0</code> and <code>index1</code> refer to the model, 
//	 * not the visual position of the item on the screen
//	 * which can be altered by move and hide operations. 
//	 * 
//	 * @param index0 row index of the cell  
//	 * @param index1 column index of the cell 
//	 */
//	public int computeWidth(N0 index0, N1 index1) {
//		assert zone != null;
//		setup(index0, index1);
//		int x = 0;
//		image = getImage(index0, index1);
//		if (image != null) {
//			Rectangle bounds = image.getBounds();
//			x = bounds.width + 2 * imageMarginX;
//		}
//		text = getText(index0, index1);
//		if (text != null) {
//			Point p = gc.stringExtent(text);
//			x += p.x + 2 * textMarginX;
//		}
//		return x;
//	}
//	
//	 
//  /**
//   * Computes the optimal height to fit the content of the cell at specified indexes.
//   * <p>
//   * To be called only by the framework, since the {@link GC} instance must be
//   * injected to measure the text extent.
//   * <p>
//   * <code>index0</code> and <code>index1</code> refer to the model, 
//   * not the visual position of the item on the screen
//   * which can be altered by move and hide operations. 
//   * 
//   * @param index0 row index of the cell  
//   * @param index1 column index of the cell 
//   */
//  public int computeHeight(N0 index0, N1 index1) {
//    assert zone != null;
//    setup(index0, index1);
//    int y = 0;
//    image = getImage(index0, index1);
//    if (image != null) {
//      Rectangle bounds = image.getBounds();
//      y = bounds.height + 2 * imageMarginY;
//    }
//    text = getText(index0, index1);
//    if (text != null) {
//      
//      Point p = gc.stringExtent(text);
//      y = java.lang.Math.max(p.y, y + 2 * textMarginY);
//    }
//    return y;
//  }

	/**
	 * hHint is taken with priority.
	 * @param index0
	 * @param index1
	 * @param wHint
	 * @param hHint
	 * @return
	 */
	public Point computeSize(N0 index0, N1 index1, int wHint, int hHint) {
	  assert zone != null;
	  setup(index0, index1);
	  
	  int x = 0, y = 0;
	  
	  image = getImage(index0, index1);
	  if (image != null) {
	    Rectangle bounds = image.getBounds();
	    x = bounds.width + 2 * imageMarginX;
	    y = bounds.height + 2 * imageMarginY;
	  }
	  
    text = getText(index0, index1);
    Point p;
    if (text != null) {
      p = gc.stringExtent(text);
      
      if (isWordWrap()) {
        if (wHint == SWT.DEFAULT) {
          return new Point(
            zone.getSection1().getCellWidth(index1), 
            zone.getSection0().getCellWidth(index0));
        }
        int x2 = wHint == SWT.DEFAULT ?  zone.getSection1().getCellWidth(index1) : wHint;
        int y2 = 0; // = wHint == SWT.DEFAULT ?  zone.getSection0().getCellWidth(index0) : wHint;
        if (textLayout == null) {
          textLayout = new TextLayout(gc.getDevice());
          matrix.addDisposeListener(new DisposeListener() {
            @Override public void widgetDisposed(DisposeEvent e) {
              textLayout.dispose();
            }
          });
        }
        textLayout.setFont(gc.getFont());
        textLayout.setText(text);
        textLayout.setAlignment(textAlignX);
        textLayout.setWidth(x2 < 1 ? 1 : x2 - 2 * textMarginX);
        
        for (int i = 0; i < textLayout.getLineCount(); i++)
          y2 += textLayout.getLineBounds(i).height;
        
        x += wHint == SWT.DEFAULT ? textLayout.getBounds().width + 2 * textMarginX: x2;
        y = java.lang.Math.max(y, y2 + 2 * textMarginY);
      } 
      else {
        x += p.x + 2 * textMarginX;
        y = java.lang.Math.max(y, y + 2 * textMarginY);
      }
    }
    
	  return new Point(x, y);
	}

	
	
	/*------------------------------------------------------------------------
	 * Static members 
	 */
	
	/**
	 * Returns the distance of a graphical element based on the align mode and the padding margin.
	 *  
	 * @param align the alignment mode, one of: {@link SWT#LEFT}, {@link SWT#RIGHT}, {@link SWT#CENTER}, 
	 * 		{@link SWT#TOP}, {@link SWT#BOTTOM}, {@link SWT#BEGINNING}, {@link SWT#END}
	 * @param margin the number of pixels from the edge to which to align, does not matter with {@link SWT#CENTER}
	 * @param width the width of the element
	 * @param bound the width of the cell
	 * @return
	 */
	public static int align(int align, int margin, int width, int bound) {
		switch (align) {
		// Fast return
		case SWT.LEFT: case SWT.TOP: case SWT.BEGINNING:
			return margin;
		case SWT.CENTER:
			return (bound - width) / 2; 
		case SWT.RIGHT: case SWT.BOTTOM: case SWT.END:
			return bound - width - margin; 
		}
		return margin;
	}
	
	/**
	 * Enlarge or shrink the rectangle by the given offset. 
	 * The rectangle is enlarged if the offset is positive and shrunk otherwise. 
	 * @param r
	 * @param offset
	 */
	public static void offsetRectangle(Rectangle r, int offset) {
		r.x -= offset;
		r.y -= offset;
		r.width += 2 *offset;
		r.height += 2 *offset;
	}
	
	static RGB blend(RGB c1, RGB c2, int ratio) {
		int r = blend(c1.red, c2.red, ratio);
		int g = blend(c1.green, c2.green, ratio);
		int b = blend(c1.blue, c2.blue, ratio);
		return new RGB(r, g, b);
	}
	
	private static int blend(int v1, int v2, int ratio) {
		return (ratio*v1 + (100-ratio)*v2)/100;
	}

	void setMatrix(Matrix<N0, N1> matrix) {
		this.matrix = matrix;
	}

	/**
	 * Returns the matrix to which the painter belongs.
	 * <p>
	 * It returns <code>null</code> before the painter is added to the collection 
	 * of the zone painters or matrix painters, which means it's always 
	 * null in the painter's constructor.
	 *  
	 * @return the matrix to which the painter belongs
	 */
	Matrix<N0, N1> getMatrix() {
		return matrix;
	}

  Zone<N0, N1> getZone() {
    return zone;
  }

  void setZone(Zone<N0, N1> zone) {
    this.zone = zone;
    this.matrix = zone.getMatrix();
  }

  /**
   * Returns the word wrapping state.
   * @return the word wrapping state
   */
  public boolean isWordWrap() {
    return wordWrap;
  }

  /**
   * Sets the word wrapping state. If true then the words will be wrapped to many lines. 
   * @param state word wrapping state
   */
  public void setWordWrap(boolean state) {
    this.wordWrap = state;
  }

  void printGC(GC gc) {
//    System.out.println("getForeground() " +  gc.getForeground());
//    System.out.println("getBackground() " +  gc.getBackground());
//    System.out.println("getForegroundPattern() " +  gc.getForegroundPattern());
//    System.out.println("getBackgroundPattern() " +  gc.getBackgroundPattern());
//    System.out.println("isClipped() " +  gc.isClipped());
//    System.out.println("getLineWidth() " +  gc.getLineWidth());
//    System.out.println("getXORMode() " +  gc.getXORMode());
    
//    System.out.println("getAdvanced() " +  gc.getAdvanced());
//    System.out.println("getAlpha() " +  gc.getAlpha());
//    System.out.println("getAntialias() " +  gc.getAntialias());
//    System.out.println("getFillRule() " +  gc.getFillRule());
//    System.out.println("getInterpolation() " +  gc.getInterpolation());
//    System.out.println("getStyle() " +  gc.getStyle());
//    System.out.println("getTextAntialias() " +  gc.getTextAntialias());
  }
}
