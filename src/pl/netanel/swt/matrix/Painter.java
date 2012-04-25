package pl.netanel.swt.matrix;

import static java.lang.Math.max;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DropTargetEvent;
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

public class Painter<X extends Number, Y extends Number> {
  /**
   * Single scope of the whole container
   */
  public static final int SCOPE_ENTIRE = 0;
  /**
   * Horizontal lines stretching from the left to the right edge of the zone to which the painter belongs.
   */
  public static final int SCOPE_LINES_X = 1;
  /**
   * Vertical lines stretching from the top to the bottom edge of the zone to which the painter belongs.
   */
  public static final int SCOPE_LINES_Y = 2;
  /**
   * Compound boundaries including all cells of an axis X item (column)
   */
  public static final int SCOPE_CELLS_ITEM_X = 4;
  /**
   * Compound boundaries including all cells of an axis Y item (row)
   */
  public static final int SCOPE_CELLS_ITEM_Y = 3;
  /**
   * Individual cells in horizontal order first. Aids graphics performance in case of
   * drawing homogenic rows.
   */
  public static final int SCOPE_CELLS_X = 5;
  /**
   * Individual cells in vertical order first. Aids graphics performance in case of
   * drawing homogenic columns.
   */
  public static final int SCOPE_CELLS_Y = 6;
  /**
   * Shortcut for {@link #SCOPE_CELLS_X}
   */
  public static final int SCOPE_CELLS = SCOPE_CELLS_X;

  /**
   * Default name of a painter belonging to a zone and responsible to paint its
   * cells. Painter with that name should not be removed, because
   * {@link ZoneEditor} and {@link #NAME_DRAG_ITEM_X} use it to perform their functions.
   */
  public static final String NAME_CELLS = "cells";
  /**
   * Default name of a painter belonging to a zone and responsible to paint its
   * lines along x axis.
   */
  public static final String NAME_LINES_X = "lines x";
  /**
   * Default name of a painter belonging to a zone and responsible to paint its
   * lines along y axis.
   */
  public static final String NAME_LINES_Y = "lines y";
  /**
   * Default name of a painter belonging to a zone and responsible to paint its
   * background.
   */
  public static final String NAME_BACKGORUND = "background";
  /**
   * Default name of a painter belonging to a matrix and responsible to paint the
   * area not frozen.
   */
  public static final String NAME_FROZEN_NONE_NONE = "frozen none none";
  /**
   * Default name of a painter belonging to a matrix and responsible to paint the
   * area that is frozen only at the horizontal end of the viewport.
   */
  public static final String NAME_FROZEN_NONE_TAIL = "frozen none tail";
  /**
   * Default name of a painter belonging to a matrix and responsible to paint the
   * area that is frozen only at the vertical end of the viewport.
   */
  public static final String NAME_FROZEN_TAIL_NONE = "frozen tail none";
  /**
   * Default name of a painter belonging to a matrix and responsible to paint the
   * area that is frozen only at the horizontal start of the viewport.
   */
  public static final String NAME_FROZEN_NONE_HEAD = "frozen none head";
  /**
   * Default name of a painter belonging to a matrix and responsible to paint the
   * area that is frozen only at the vertical start of the viewport.
   */
  public static final String NAME_FROZEN_HEAD_NONE = "frozen head none";
  /**
   * Default name of a painter belonging to a matrix and responsible to paint the
   * area that is frozen both at the vertical start and horizontal end of the
   * viewport.
   */
  public static final String NAME_FROZEN_HEAD_TAIL = "frozen head tail";
  /**
   * Default name of a painter belonging to a matrix and responsible to paint the
   * area that is frozen both at the vertical end and horizontal start of the
   * viewport.
   */
  public static final String NAME_FROZEN_TAIL_HEAD = "frozen tail head";
  /**
   * Default name of a painter belonging to a matrix and responsible to paint the
   * area that is frozen both at the vertical end and horizontal end of the
   * viewport.
   */
  public static final String NAME_FROZEN_TAIL_TAIL = "frozen tail tail";
  /**
   * Default name of a painter belonging to a matrix and responsible to paint the
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
  /**
   * Default name of the painter belonging to a matrix responsible to paint the
   * items being dragged on x axis.
   */
  public static final String NAME_DRAG_ITEM_X = "drag item x";
  /**
   * Default name of the painter belonging to a matrix responsible to paint the
   * items being dragged on y axis.
   */
  public static final String NAME_DRAG_ITEM_Y = "drag item y";
  /**
   * Default name of the painter belonging to a matrix responsible to paint the
   * line dividing the frozen head on the horizontal axis.
   */
  public static final String NAME_FREEZE_HEAD_LINE_X = "freeze head line x";
  /**
   * Default name of the painter belonging to a matrix responsible to paint the
   * line dividing the frozen head on the vertical axis.
   */
  public static final String NAME_FREEZE_HEAD_LINE_Y = "freeze head line y";
  /**
   * Default name of the painter belonging to a matrix responsible to paint the
   * line dividing the frozen tail on the horizontal axis.
   */
  public static final String NAME_FREEZE_TAIL_LINE_X = "freeze tail line x";
  /**
   * Default name of the painter belonging to a matrix responsible to paint the
   * line dividing the frozen tail on the vertical axis.
   */
  public static final String NAME_FREEZE_TAIL_LINE_Y = "freeze tail line y";

	//private static int[] EXTENT_ALIGN = {SWT.RIGHT, SWT.END, SWT.BOTTOM, SWT.CENTER};
	static enum TextClipMethod {DOTS_IN_THE_MIDDLE, DOTS_AT_THE_END, CUT, NONE};

	/**
	 * Provides graphic to the {@link #init()}, {@link #clean()},
	 * {@link #paint(Number, Number, int, int, int, int)} methods.
	 * It is not safe to use it inside of other methods.
	 */
	protected GC gc;

	/**
	 * Expresses the selected state of the cell to paint set by default
	 * by the {@link #setup(Number, Number)} method.
	 */
	protected boolean isSelected = false;

	int scope;
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
	 * Selected cells will be highlighted if <tt>true</tt>. Otherwise cell selection
	 * will be ignored. Default value is <tt>true</true>.
	 */
	public boolean selectionHighlight = true;

	/**
	 * Painter style properties.
	 */
	public Style style;


	Matrix<X, Y> matrix;
	ZoneCore<X, Y> zone;
	Rectangle zoneBounds;

	private Color lastForeground, lastBackground, defaultBackground, defaultForeground;

	private Font lastFont;
	private int[] extentCache;
	private Point extent;
  private TextLayout textLayout;
  Rectangle clipping;
  private Object data;


	/**
	 * Constructs a painter with the given name.
	 * <p>
	 * The scope of the painter is determined according to the following rules:<br>
	 * for name {@link #NAME_CELLS} scope = {@link #SCOPE_CELLS}<br>
	 * for name {@link #NAME_LINES_X} scope = {@link #SCOPE_LINES_X} <br>
	 * for name {@link #NAME_LINES_Y} scope = {@link #SCOPE_LINES_Y}<br>
	 * for name {@link #NAME_EMBEDDED_CONTROLS} scope = {@link #SCOPE_CELLS}<br>
	 * for name {@link #NAME_EMULATED_CONTROLS} scope = {@link #SCOPE_CELLS}<br>
	 * else {@link #SCOPE_ENTIRE}.
	 * @param name of the painter, must be unique in the collection to which it is added
	 * @see #Painter(String, int)
	 */
	public Painter(String name) {
		this(name,
  		name.equals(NAME_CELLS) ||
  		name.equals(NAME_EMBEDDED_CONTROLS) ||
  		name.equals(NAME_EMULATED_CONTROLS) ?     SCOPE_CELLS :
  		name.equals(NAME_LINES_X) ?               SCOPE_LINES_X :
  		name.equals(NAME_LINES_Y) ?               SCOPE_LINES_Y :
  		                                          SCOPE_ENTIRE);
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
		this.style = new Style();
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
	 * Returns the painter scope.
	 *
	 * @return the painter scope
	 */
	public int getScope() {
    return scope;
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
		if (scope < SCOPE_CELLS) return true;

		// Set default value if foreground is null, background is not painted if null
		if (style.foreground == null) {
		  style.foreground = Resources.getColor(SWT.COLOR_LIST_FOREGROUND);
		}
		defaultForeground = style.foreground;
		if (defaultForeground == null) {
		  defaultForeground = matrix.getForeground();
		}
		lastForeground = defaultForeground;
		lastBackground = defaultBackground = style.background;

		gc.setForeground(lastForeground);
		if (style.background != null) {
			gc.setBackground(lastBackground);
//			gc.fillRectangle(zone.bounds);
		}

		Font font = style.font == null ? matrix.getDisplay().getSystemFont() : style.font;
    gc.setFont(font);
		extentCache = FontWidthCache.get(gc, font);
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
	  //gc.setClipping(clipping);
	}

	/**
	 * Draws on the canvas within the given boundaries according to the given indexes.
	 * <p>
	 * @param x the x coordinate of the painting boundaries
	 * @param y the y coordinate of the painting boundaries
	 * @param width the width of the painting boundaries
	 * @param height the height of the painting boundaries
	 */
	protected void paint(int x, int y, int width, int height) {
//	  setup(indexX, indexY);
	  Rectangle clipping2 = null;
	  if (style.hasWordWraping) {
      clipping2 = gc.getClipping();
	    gc.setClipping(x, y, width, height);
	  }

		Color foreground2, background2;
		if (isSelected) {
			// TODO Revise and maybe optimize the background / foreground color setting algorithm
			foreground2 = style.selectionForeground;
			background2 = style.selectionBackground;
		} else {
		  foreground2 = style.foreground;
		  background2 = style.background;
		}

		if (foreground2 == null) {
		  if (defaultForeground == null) {
		    defaultForeground = matrix.getForeground();
		  }
		  foreground2 = defaultForeground;
		}
		// Only set color if there is a change
		if (!foreground2.equals(lastForeground)) {
			gc.setForeground(lastForeground = foreground2);
		}
		if (background2 != null) {
			if (!background2.equals(lastBackground)) {
				gc.setBackground(lastBackground = background2);
			}
			// Needed in calculated background scenario
			//if (!background2.equals(defaultBackground)) {
				gc.fillRectangle(x, y, width, height);
			//}
		}

		int x2 = x, y2 = y;
		int x3 = x, y3 = y;

		if (image != null) {
			Rectangle bounds = image.getBounds();
			switch (style.imageAlignX) {
			case SWT.BEGINNING: case SWT.LEFT: case SWT.TOP:
				x2 += style.imageMarginX; x3 += bounds.width; break;
			case SWT.CENTER:
				x2 += (width - bounds.width) / 2; break;
			case SWT.RIGHT: case SWT.END: case SWT.BOTTOM:
				x2 += width - bounds.width - style.imageMarginX; break;
			}
			switch (style.imageAlignY) {
			case SWT.BEGINNING: case SWT.TOP: case SWT.LEFT:
				y2 += style.imageMarginY; break;
			case SWT.CENTER:
				y2 += (height - bounds.height) / 2; break;
			case SWT.BOTTOM: case SWT.END: case SWT.RIGHT:
				y2 += height - bounds.height - style.imageMarginY; break;
			}
			if (clipping2 == null) {
			  clipping2 = gc.getClipping();
			  gc.setClipping(x, y, width, height);
			}
			gc.drawImage(image, x2, y2);
			width -= bounds.width;
		}


		if (text != null) {
		  boolean fontChange = style.font != lastFont;
		  if (fontChange) {
		    Font font = style.font == null ? matrix.getDisplay().getSystemFont() : style.font;
        gc.setFont(font);
		    extentCache = FontWidthCache.get(gc, font);
		    lastFont = font;
		  }

		  if (!style.hasWordWraping) {
		    // Compute extent only when font changes or text horizontal align is center or right
//		    if (fontChange ||
//          Arrays.contains(EXTENT_ALIGN, style.textAlignX)) {
//          extent = gc.stringExtent(text);
//        }
        extent = gc.stringExtent(text);

		    if (style.textClipMethod == TextClipMethod.DOTS_IN_THE_MIDDLE) {
//		      text = shortenTextMiddle(text, width - style.textMarginX * 2);
		      text = FontWidthCache.shortenTextMiddle(
		        text, width - style.textMarginX * 2, extent, extentCache);
		    }
//		    else if (style.textClipMethod == TextClipMethod.DOTS_AT_THE_END) {
//		      text = FontWidthCache.shortenTextEnd(
//		        text, width - style.textMarginX * 2, extent, extentCache);
//		    }
		  }

		  switch (style.textAlignX) {
      case SWT.BEGINNING: case SWT.LEFT: case SWT.TOP:
        x3 += style.textMarginX; break;
      case SWT.CENTER:
        x3 += (width - extent.x) / 2; break;
      case SWT.RIGHT: case SWT.END: case SWT.BOTTOM:
        x3 += width - extent.x - style.textMarginX; break;
      }
      switch (style.textAlignY) {
      case SWT.BEGINNING: case SWT.TOP: case SWT.LEFT:
        y3 += style.textMarginY; break;
      case SWT.CENTER:
        y3 += (height - extent.y) / 2; break;
      case SWT.BOTTOM: case SWT.END: case SWT.RIGHT:
        y3 += height - extent.y - style.textMarginY; break;
      }

		  if (style.hasWordWraping) {
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
		    textLayout.setAlignment(style.textAlignX);
		    textLayout.setWidth(width < 1 ? 1 : width - style.textMarginX);

//		    Rectangle clipping2 = gc.getClipping();
		    textLayout.draw(gc, x3, y3);
		  }
		  else {
//			if (width < 4 || height < 4) return;
	      if (clipping2 == null && height < extent.y + 2 * style.textMarginY) {
	        clipping2 = gc.getClipping();
	        gc.setClipping(x, y, width, height);
	      }

		    gc.drawString(text, x3, y3, true);
		  }
		}
		if (clipping2 != null) {
		  gc.setClipping(clipping2);
		}
	}

	public String shortenTextMiddle(String s, int width) {
    if (s == null) return s;

    int len = s.length();
    int w = 0;
    int i = 0;
    if (extent.x > width) {
      int dot = gc.stringExtent(".").x;
      int dot2 = gc.stringExtent("..").x;
      int pivot = i / 2;
      int pos1 = pivot;
      int pos2 = len - pivot;
      int len1 = gc.stringExtent(s.substring(0, pos1)).x;
      int len2 = gc.stringExtent(s.substring(pos2, len)).x;
      int last = len - 1;
      while (pos1 > 0 && pos2 < last) {
        if ((w = len1 + dot2 + len2) <= width) break;
        else if (pos1 <= 1 && (w = len1 + dot2) <= width) {
          pos2 = len; break;
        }
        int w2 = gc.stringExtent(Character.toString(s.charAt(--pos1))).x;
        len1 -= w2;
        if (w - w2 > width) {
          len2 -= gc.stringExtent(Character.toString(s.charAt(++pos2))).x;
        }
      }
      if (w <= width) {
        s = s.substring(0, pos1) + ".." + s.substring(pos2, len);
      }
      else {
        w = dot2 <= width ? dot2 : dot <= width ? dot : 0;
        s = dot2 <= width ? ".." : dot <= width ? "." : "";
      }
    }
    extent.x = w;
    return s;
  }


	/**
   * Configures the painter properties according to the given indexes.
   * <p>
   * Default implementation invokes {@link #setupSpatial(Number, Number)}
   * and determines if the cell is selected.
   *
   * @param indexX cell index on the horizontal axis
   * @param indexY cell index on the vertical axis
   */
	public void setup(X indexX, Y indexY) {
	  setupSpatial(indexX, indexY);
    isSelected = selectionHighlight && zone != null && zone.isSelected(indexX, indexY);
	}

  /**
   * Sets the spatial properties for this painter.
   * Spatial properties are the ones that effect the space of the cell and
   * include: text, image, text and image margins, text wrapping.
   * <p>
   * It is utilized by both painting mechanism as well as
   * {@link #computeSize(Number, Number, int, int)} routine.
   * The reason to separate it from {@link #setup(Number, Number)} method
   * was to improve performance of size computing by eliminating unnecessary
   * processing, like setting colors, determining whether the cell is selected, etc.
   * <p>
   * The most common usage is to set the text to display:
   * <pre>  public void setupSpatial(Integer indexX, Integer indexY){
      text = data[indexY][indexX];
  }
   * </pre>
   *
   * @param indexX cell index on the horizontal axis
   * @param indexY cell index on the vertical axis
   */
	public void setupSpatial(X indexX, Y indexY) {}

  /**
   * Set the style of the painter.
   * @param style to set
   */
  public void setStyle(Style style) {
    this.style = style;
  }

	/**
   * Sets custom data for this painter.
   * <p>
   * Painters {@link #NAME_DRAG_ITEM_X} and {@link #NAME_DRAG_ITEM_Y} use it to
   * get mouse coordinates and draw drag feedback indicator if supplied with
   * {@link DropTargetEvent} object with this method. Example:
   * <pre>dropTarget.addDropListener(new DropTargetAdapter() {
   *   &#64;Override public void dragOver(DropTargetEvent event) {
   *     dragPainterY.setData(event);
   *   }
   * });</pre>
   *
   * @param data data to set
   */
	public void setData(Object data) {
    this.data = data;
	}

	/**
	 * Returns custom data of this painter.
	 * @return custom data of this painter
	 */
	public Object getData() {
    return data;
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

	/**
	 * Returns the preferred size of the receiver.
   * @param indexX cell index on the horizontal axis
   * @param indexY cell index on the vertical axis
	 * @param wHint the width hint (can be <code>SWT.DEFAULT</code>)
	 * @param hHint the height hint (can be <code>SWT.DEFAULT</code>)
	 * @return the preferred size of the control
	 */
	public Point computeSize(X indexX, Y indexY, int wHint, int hHint) {
	  setupSpatial(indexX, indexY);

	  int x = 0, y = 0;

	  if (image != null) {
	    Rectangle bounds = image.getBounds();
	    x = bounds.width + 2 * style.imageMarginX;
	    y = bounds.height + 2 * style.imageMarginY;
	  }

    if (text != null) {

      if (style.hasWordWraping) {
        if (wHint == SWT.DEFAULT) {
          return new Point(
            zone.getSectionX().getCellWidth(indexX),
            zone.getSectionY().getCellWidth(indexY));
        }
        int x2 = wHint == SWT.DEFAULT ?  zone.getSectionX().getCellWidth(indexX) : wHint;
        int y2 = 0; // = wHint == SWT.DEFAULT ?  zone.getSectionY().getCellWidth(indexY) : wHint;
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
        textLayout.setAlignment(style.textAlignX);
        textLayout.setWidth(x2 < 1 ? 1 : x2 - 2 * style.textMarginX);

        for (int i = 0; i < textLayout.getLineCount(); i++)
          y2 += textLayout.getLineBounds(i).height;

        x += wHint == SWT.DEFAULT ? textLayout.getBounds().width + 2 * style.textMarginX: x2;
        y = max(y, y2 + 2 * style.textMarginY);
      }
      else {
//        extent = gc.stringExtent(text);
//        x += extent.x + 2 * style.textMarginX;
        x += FontWidthCache.getWidth(text, gc, extentCache) + 2 * style.textMarginX;
        y = max(y, extent.y + 2 * style.textMarginY);
      }
    }

	  return new Point(max(x, wHint), max(y, hHint));
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
	static void offsetRectangle(Rectangle r, int offset) {
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

	void setMatrix(Matrix<X, Y> matrix) {
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
	Matrix<X, Y> getMatrix() {
		return matrix;
	}

  Zone<X, Y> getZone() {
    return zone;
  }

  void setZone(ZoneCore<X, Y> zone) {
    this.zone = zone;
    this.matrix = zone.getMatrix();
  }



  static void printGC(GC gc) {
    System.out.println("getForeground() " +  gc.getForeground());
    System.out.println("getBackground() " +  gc.getBackground());
    System.out.println("getForegroundPattern() " +  gc.getForegroundPattern());
    System.out.println("getBackgroundPattern() " +  gc.getBackgroundPattern());
    System.out.println("isClipped() " +  gc.isClipped());
    System.out.println("getLineWidth() " +  gc.getLineWidth());
    System.out.println("getXORMode() " +  gc.getXORMode());

    System.out.println("getAdvanced() " +  gc.getAdvanced());
    System.out.println("getAlpha() " +  gc.getAlpha());
    System.out.println("getAntialias() " +  gc.getAntialias());
    System.out.println("getFillRule() " +  gc.getFillRule());
    System.out.println("getInterpolation() " +  gc.getInterpolation());
    System.out.println("getTextAntialias() " +  gc.getTextAntialias());
  }
}
