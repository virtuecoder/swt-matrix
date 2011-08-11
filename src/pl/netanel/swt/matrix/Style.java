package pl.netanel.swt.matrix;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;


/**
 * Graphical style properties used by painters.
 * 
 * @author Jacek Kolodziejczyk created 11-08-2011
 */
class Style {
	
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
	 * Word wrapping for text in cells. 
	 */
	private boolean wordWrap;

	
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
}
