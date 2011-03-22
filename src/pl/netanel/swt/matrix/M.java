package pl.netanel.swt.matrix;


/**
 * Matrix constants
 *
 * @author Jacek
 * @created 16-11-2010
 */
public class M {

	static final int CELL_WIDTH = 16;			
	static final int LINE_WIDTH = 1;
	static final int RESIZE_OFFSET_X = 3;
	static final int RESIZE_OFFSET_Y = 2;
	static final int AUTOSCROLL_OFFSET_X = 8;
	static final int AUTOSCROLL_OFFSET_Y = 6;
	static final int AUTOSCROLL_RATE = 50;

	
	/*
	 *  Navigation Key Actions. Key bindings for the actions are set
	 *  by the StyledText widget.
	 */	
	public static final int CURRENT_UP = 1; 					// binding = SWT.ARROW_UP
	public static final int CURRENT_DOWN = 2; 				// binding = SWT.ARROW_DOWN
	public static final int CURRENT_LEFT = 3; 				// binding = SWT.ARROW_LEFT
	public static final int CURRENT_RIGHT = 4; 				// binding = SWT.ARROW_RIGHT
	public static final int CURRENT_PAGE_UP = 5; 			// binding = SWT.PAGE_UP
	public static final int CURRENT_PAGE_DOWN = 6; 			// binding = SWT.PAGE_DOWN
	public static final int CURRENT_PAGE_LEFT = 7; 			// binding = SWT.MOD3 + SWT.PAGE_UP
	public static final int CURRENT_PAGE_RIGHT = 8; 			// binding = SWT.MOD3 + SWT.PAGE_DOWN
	public static final int CURRENT_MOST_LEFT = 9;			// binding = SWT.HOME
	public static final int CURRENT_MOST_RIGHT = 10; 		// binding = SWT.END
	public static final int CURRENT_MOST_UP = 11; 			// binding = SWT.MOD1 + SWT.PAGE_UP
	public static final int CURRENT_MOST_DOWN = 12; 			// binding = SWT.MOD1 + SWT.PAGE_DOWN
	public static final int CURRENT_START = 13; 				// binding = SWT.MOD1 + SWT.HOME
	public static final int CURRENT_END = 14; 				// binding = SWT.MOD1 + SWT.END
	public static final int CURRENT_LOCATION = 15; 			// binding = SWT.MouseDown
	public static final int CURRENT_LOCATION2 = 16; 			// binding = SWT.MOD1 + SWT.MouseDown
//	public static final int WORD_PREVIOUS = 17039363;		// binding = SWT.MOD1 + SWT.ARROW_LEFT
//	public static final int WORD_NEXT = 17039364; 			// binding = SWT.MOD1 + SWT.ARROW_RIGHT
	
	public static boolean isCursorMove(int id) {
		return CURRENT_UP <= id && id <= CURRENT_LOCATION2;
	}
	

	/* 
	 * Selection Key Actions 
	 */
	public static final int SELECT_ALL = 100; 				// binding = SWT.MOD1 + 'A'
	public static final int SELECT_UP = 101; 				// binding = SWT.MOD2 + SWT.ARROW_UP
	public static final int SELECT_DOWN = 102; 				// binding = SWT.MOD2 + SWT.ARROW_DOWN
	public static final int SELECT_LEFT = 103; 				// binding = SWT.MOD2 + SWT.ARROW_LEFT
	public static final int SELECT_RIGHT = 104; 			// binding = SWT.MOD2 + SWT.ARROW_RIGHT
	public static final int SELECT_PAGE_UP = 105; 			// binding = SWT.MOD2 + SWT.PAGE_UP
	public static final int SELECT_PAGE_DOWN = 106; 		// binding = SWT.MOD2 + SWT.PAGE_DOWN
	public static final int SELECT_PAGE_LEFT = 107; 		// binding = SWT.MOD2 + SWT.MOD3 + SWT.ARROW_LEFT
	public static final int SELECT_PAGE_RIGHT = 108; 		// binding = SWT.MOD2 + SWT.MOD3 + SWT.ARROW_RIGHT
	public static final int SELECT_FULL_UP = 109; 			// binding = SWT.MOD1 + SWT.MOD2 + SWT.ARROW_UP
	public static final int SELECT_FULL_DOWN = 110;   		// binding = SWT.MOD1 + SWT.MOD2 + SWT.ARROW_DOWN
	public static final int SELECT_FULL_LEFT = 111; 		// binding = SWT.MOD2 + SWT.HOME
	public static final int SELECT_FULL_RIGHT = 112;   		// binding = SWT.MOD2 + SWT.END
	public static final int SELECT_START = 113; 			// binding = SWT.MOD1 + SWT.MOD2 + SWT.HOME
	public static final int SELECT_END = 114; 				// binding = SWT.MOD1 + SWT.MOD2 + SWT.END
	public static final int SELECT_TO_LOCATION = 115; 		// binding = SWT.MOD2 + SWT.MouseDown
	public static final int SELECT_TO_LOCATION2 = 116; 		// binding = SWT.MOD2 + SWT.MouseDown
	public static final int SELECT_ROW = 120; 				// binding = SWT.MouseDown + Zone.ROW_HEADER
	public static final int SELECT_ROW2 = 121; 				// binding = SWT.MOD1 + SWT.MouseDown + Zone.ROW_HEADER
	public static final int SELECT_COLUMN = 122; 			// binding = SWT.MouseDown + Zone.COLUMN_HEADER
	public static final int SELECT_COLUMN2 = 123; 			// binding = SWT.MOD1 + SWT.MouseDown + Zone.COLUMN_HEADER
	public static final int SELECT_TO_ROW = 124; 			// binding = SWT.MouseDown + Zone.ROW_HEADER
	public static final int SELECT_TO_ROW2 = 125; 			// binding = SWT.MOD1 + SWT.MouseDown + Zone.ROW_HEADER
	public static final int SELECT_TO_COLUMN = 126;			// binding = SWT.MouseDown + Zone.COLUMN_HEADER
	public static final int SELECT_TO_COLUMN2 = 127;		// binding = SWT.MOD1 + SWT.MouseDown + Zone.COLUMN_HEADER
	
	public static boolean isBodySelect(int id) {
		return CURRENT_UP <= id && id <= SELECT_TO_LOCATION2;
	}
	
	public static boolean isHeaderSelect(int id) {
		return SELECT_ROW <= id && id <= SELECT_TO_COLUMN2;
	}
	
	public static boolean isExtendingSelect(int id) {
		return SELECT_UP <= id && id <= SELECT_TO_LOCATION2 || 
			SELECT_TO_ROW <= id && id <= SELECT_TO_COLUMN2;
	}

	/*
	 *  Modification Key Actions 
	 */
	public static final int CUT = 200; 						// binding = SWT.MOD2 + SWT.DEL
	public static final int COPY = 201; 					// binding = SWT.MOD1 + SWT.INSERT;
	public static final int PASTE = 202;					// binding = SWT.MOD2 + SWT.INSERT ;
	
	public static final int HIDE = 210;						// binding = SWT.MOD3 + SWT.DEL;
	public static final int UNHIDE = 211;					// binding = SWT.MOD3 + SWT.INSERT;
	
	static final int RESIZE_START = 220;					    
	static final int RESIZE_STOP = 221;					    
	static final int RESIZE_PACK = 222;					    
	

	/*------------------------------------------------------------------------
	 * Mouse event modifiers, cannot collide with SWT state masks or mouse button numbers
	 */

//	static final int RESIZE_AREA = 1 << 26;
	

}
