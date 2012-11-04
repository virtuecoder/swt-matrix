/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.RGB;

import pl.netanel.swt.matrix.Painter.TextClipMethod;


/**
 * Graphical style properties used by painters.
 * 
 * @author Jacek Kolodziejczyk created 11-08-2011
 */
public class Style {
	
	/**
	 * Foreground color.
	 */
	public Color foreground;
	/**
	 * Background color.
	 */
	public Color background;
	/**
	 * Foreground color of selected cells.
	 */
	public Color selectionForeground;
	/**
	 * Background color of selected cells.
	 */
	public Color selectionBackground;
	
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
	 * Font for the text.
	 */
	public Font font;
	
	/**
   * Word wrapping for text in cells. 
   */
  public boolean hasWordWraping;
  
  TextClipMethod textClipMethod;

  public Style() {
    textMarginY = 1; 
    textMarginX = 4;
    textAlignY = SWT.BEGINNING; 
    textAlignX = SWT.BEGINNING;
    textClipMethod = TextClipMethod.DOTS_IN_THE_MIDDLE;
  }

  /**
   * Creates a default style for the cells of the body zone.
   * @return a new instance of this class
   */
  public static Style createBodyCellStyle() {
    Style style = new Style();
    
    style.foreground = style.background = null;

    // style.selectionForeground = Resources.getColor(SWT.COLOR_LIST_SELECTION_TEXT);
    style.selectionForeground = style.foreground;
    RGB selectionColor = Resources.getColor(SWT.COLOR_LIST_SELECTION).getRGB();
    RGB whiteColor = Resources.getColor(SWT.COLOR_LIST_BACKGROUND).getRGB();
    RGB color = Painter.blend(selectionColor, whiteColor, 40);
    style.selectionBackground = Resources.getColor(color);

    return style;
  }
	
  
  /**
   * Creates a default style for the lines of the body zone.
   * @return a new instance of this class
   */
  public static Style createBodyLineStyle() {
    Style style = new Style();
    style.background = Resources.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW);
    return style;
  }
  
  
  /**
   * Creates a default style for the cells of the header type of zones.
   * @return a new instance of this class
   */
  public static Style createHeaderCellStyle() {
    Style style = new Style();
    
    style.foreground = Resources.getColor(SWT.COLOR_WIDGET_FOREGROUND);
//    style.background = Resources.getColor(SWT.COLOR_WIDGET_BACKGROUND);

    // style.selectionForeground = Resources.getColor(SWT.COLOR_LIST_SELECTION_TEXT);
    style.selectionForeground = style.foreground;
    RGB selectionColor = Resources.getColor(SWT.COLOR_LIST_SELECTION).getRGB();
    RGB whiteColor = Resources.getColor(SWT.COLOR_WIDGET_BACKGROUND).getRGB();
    RGB rgb = Painter.blend(selectionColor, whiteColor, 90);
    style.selectionBackground = Resources.getColor(rgb);
    
    return style;
  }
  
  
  /**
   * Creates a default style for the lines of the header type of zones.
   * @return a new instance of this class
   */
  public static Style createHeaderLineStyle() {
    Style style = new Style();
    style.background = Resources.getColor(SWT.COLOR_WIDGET_DARK_SHADOW);
    return style;
  }
  
  
  
}
