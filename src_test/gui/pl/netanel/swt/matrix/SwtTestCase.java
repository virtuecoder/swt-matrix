package pl.netanel.swt.matrix;

import static java.lang.Math.abs;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import pl.netanel.util.Util;

/**
 * Facilitates testing GUI code that uses SWT.
 *
 * @author jacek.p.kolodziejczyk@gmail.com
 * @created Apr 29, 2008
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class  SwtTestCase {
  public static boolean breakFlag = false;
  public static boolean runManual = false;

  protected Shell shell;
  protected Display display;
  protected Control focusControl; // control on which you click
  protected List<Throwable> exceptions;

  int delay = 0;

  private long lastClickTime;
  private Point lastClickPoint;
  protected int remedyCommandIndex = -1;

  @Before public void setUp() throws Exception {
    display = Display.getDefault();
    shell = new Shell(display);
    shell.setLayout(new FillLayout());
    shell.setBounds(new Rectangle(600, 400, 400, 400));
    // Main.setUpShell(shell, MockSession.INSTANCE);
  }

  @After public  void tearDown() throws Exception {
    processEvents();
    Resources.dispose();
    if (display != null && !display.isDisposed()) {
      display.dispose();
    }
  }

  public void show() {
    // shell.layout();
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  /**
   * Pressg a key that is not a character
   *
   * @param key
   */
  public void press(final int key) {
    postKey(key, SWT.KeyDown);
    postKey(key, SWT.KeyUp);
    processEvents();
  }

  /**
   * Press keys matching characters in s and hit enter.
   *
   * @param s
   */
  public void enter(String s) {
    type(s);
    press(SWT.CR);
  }

  // public void enter(String s) {
  // type(s);
  // press(SWT.SHIFT, SWT.CR);
  // }

  /**
   * Clears the current control value before entering the new text
   */
  public void clearEnterCR(String s) {
    Control control = display.getFocusControl();
    if (control instanceof Text) ((Text) control).setText("");
    enter(s);
  }

  public void comboSelect(String s) {
    Combo combo = (Combo) display.getFocusControl();
    combo.select(combo.indexOf(s));
    combo.notifyListeners(SWT.Selection, new Event());
    processEvents();
    press(SWT.CR);
  }

  /**
   * Imitates pressing a key that is not a character, with modifier keys like
   * SWT.CONTROL, SWT.SHIFT, SWT.ALT, which can be joind by <code>|</code>
   *
   * @param stateMask
   * @param keyCode
   */
  public void press(final int stateMask, final int keyCode) {
    if (Thread.currentThread() != display.getThread()) {
      display.syncExec(new Runnable() {
        @Override public void run() {
          press(stateMask, keyCode);
        }
      });
      return;
    }
    if ((stateMask & SWT.CONTROL) != 0) postKey(SWT.CTRL, SWT.KeyDown);
    if ((stateMask & SWT.SHIFT) != 0) postKey(SWT.SHIFT, SWT.KeyDown);
    if ((stateMask & SWT.ALT) != 0) postKey(SWT.ALT, SWT.KeyDown);
    postKey(keyCode, SWT.KeyDown);
    // sleep(50);
    postKey(keyCode, SWT.KeyUp);
    if ((stateMask & SWT.ALT) != 0) postKey(SWT.ALT, SWT.KeyUp);
    if ((stateMask & SWT.SHIFT) != 0) postKey(SWT.SHIFT, SWT.KeyUp);
    if ((stateMask & SWT.CONTROL) != 0) postKey(SWT.CTRL, SWT.KeyUp);

    processEvents();
  }

  /**
   * Imitates pressing a key that is not a character, with modifier keys like
   * SWT.CONTROL, SWT.SHIFT, SWT.ALT, which can be joind by <code>|</code>
   *
   * @param stateMask
   * @param keyCode
   */
  public void press(final int stateMask, final char ch) {
    if (Thread.currentThread() != display.getThread()) {
      display.syncExec(new Runnable() {
        @Override public void run() {
          press(stateMask, ch);
        }
      });
      return;
    }
    if ((stateMask & SWT.CONTROL) != 0) postKey(SWT.CTRL, SWT.KeyDown);
    if ((stateMask & SWT.SHIFT) != 0) postKey(SWT.SHIFT, SWT.KeyDown);
    if ((stateMask & SWT.ALT) != 0) postKey(SWT.ALT, SWT.KeyDown);
    postChar(ch, SWT.KeyDown);
    postChar(ch, SWT.KeyUp);
    if ((stateMask & SWT.ALT) != 0) postKey(SWT.ALT, SWT.KeyUp);
    if ((stateMask & SWT.SHIFT) != 0) postKey(SWT.SHIFT, SWT.KeyUp);
    if ((stateMask & SWT.CONTROL) != 0) postKey(SWT.CTRL, SWT.KeyUp);

    processEvents();
  }

  // /**
  // * Posts a text described sequence of keys
  // * @param s e.g. CTRL+INSERT
  // */
  // public void press(String s) {
  // try {
  // KeyStroke keyStroke = KeyStroke.getInstance(s);
  // if (isPrintable((char) keyStroke.getNaturalKey()))
  // press(keyStroke.getModifierKeys(), (char) keyStroke.getNaturalKey());
  // else
  // press(keyStroke.getModifierKeys(), keyStroke.getNaturalKey());
  // }
  // catch (ParseException e) {
  // throw new RuntimeException(e);
  // }
  // }

  /**
   * Posts key event, for keys that are not characters
   *
   * @param key
   * @param type SWT.KeyDown or SWT.KeyUp
   */
  public void postKey(int key, int type) {
    Event event = new Event();
    event.type = type;
    event.keyCode = key;
    postEvent(event);
  }

  /**
   * Posts a character event
   *
   * @param ch
   * @param type SWT.KeyDown or SWT.KeyUp
   */
  public void postChar(char ch, int type) {
    Event event = new Event();
    event.type = type;
    event.character = ch;
    postEvent(event);
  }

  public static boolean isPrintable(char ch) {
    if (ch == 0) return false;
    return Arrays.binarySearch(
      new int[] { 1, 2, 4, 5, 6, 7, 11, 12, 14, 15, 16, 17, 18, 19, 20, 21, 22,
        23, 24, 25, 26, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41,
        42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59,
        60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77,
        78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95,
        96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110,
        111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124,
        125, 126, 211, 243, 260, 261, 262, 263, 280, 281, 321, 322, 323, 324,
        346, 347, 377, 378, 379, 380, 8364, 61440, 61441 }, ch) > -1;
  }

  /**
   * Puts the event in the system event queue
   *
   * @param event
   * @return
   */
  public boolean postEvent(final Event event) {
    if (Thread.currentThread() != display.getThread()) {
      final boolean[] result = new boolean[] { false };
      display.syncExec(new Runnable() {
        @Override public void run() {
          result[0] = postEvent(event);
        }
      });
      return result[0];
    }
//    log(event);
    return display.post(event);
  }

  /**
   * Process all the pending events in system event queue
   */
  public void processEvents() {
    if (Thread.currentThread() != display.getThread()) {
      display.asyncExec(new Runnable() {
        @Override public void run() {
          processEvents();
        }
      });
      return;
    }
    while (!display.isDisposed() && display.readAndDispatch())
      ;
    sleep(delay);
  }

  /**
   * Imitates typing a text on the keyboard
   *
   * @param s
   */
  public void type(String s) {
    for (int i = 0; i < s.length(); i++) {
      char ch = s.charAt(i);
      // boolean shift = Character.isUpperCase(ch) ||
      // !Character.isLetterOrDigit(ch);
      boolean shift = needsShift(ch);
      if (isPrintable(ch)) ch = Character.toLowerCase(ch);
      if (shift) {
        postKey(SWT.SHIFT, SWT.KeyDown);
        processEvents();
//        if (breakFlag) TestUtil.log("shift", ch);
      }
      postChar(ch, SWT.KeyDown);
      postChar(ch, SWT.KeyUp);
      if (shift) {
        postKey(SWT.SHIFT, SWT.KeyUp);
      }
    }
    processEvents();
  }

  /**
   * Recognizes whether a keyCode is an upper case
   *
   * @param keyCode
   * @return
   */
  private boolean needsShift(char keyCode) {
    if (keyCode >= 62 && keyCode <= 90) return true;
    if (keyCode >= 123 && keyCode <= 126) return true;
    if (keyCode >= 33 && keyCode <= 43 && keyCode != 39) return true;
    if (keyCode >= 94 && keyCode <= 95) return true;
    if (keyCode == 58 || keyCode == 60 || keyCode == 62) return true;

    return false;
  }

  // Mouse ---------------------------------------------------------------------

  /**
   * Imitates clicking on the middle of the control with left mouse button
   *
   * @param control
   */
  public void click(Control control) {
    postClick(middle(control.getParent(), control.getBounds()), SWT.BUTTON1);
    processEvents();
  }

  /**
   * Imitates clicking on the middle of the control with right mouse button
   *
   * @param control
   */
  public void clickRight(Control control) {
    postClick(middle(control.getParent(), control.getBounds()), SWT.BUTTON3);
    processEvents();
  }

  /**
   * Imitates clicking with left mouse button on the middle of rectangle
   * positioned relatively to the control. Useful for instanace to click on the
   * specific cell of the table control.
   *
   * @param control
   * @param rect
   */
  public void click(Control control, Rectangle rect) {
    postClick(middle(control, rect), SWT.BUTTON1);
    processEvents();

//    Event event = new Event();
//    event.type = SWT.MouseMove;
//    event.x = -1;
//    event.y = -1;
//    postEvent(event);
//    processEvents();
  }

  public void click(Control control, Rectangle rect, int code) {
    postClick(middle(control, rect), code);
    processEvents();
  }

  /**
   * Imitates clicking with right mouse button on the middle of rectangle
   * positioned relatively to the control. Useful for instanace to click on the
   * specific cell of the table control.
   *
   * @param control
   * @param rect
   */
  public void clickRight(Control control, Rectangle rect) {
    postClick(middle(control, rect), SWT.BUTTON3);
    processEvents();
  }

  /**
   * Imitates clicking with left mouse button on the middle of rectangle
   * positioned relatively to the current focus control. Useful for instanace to
   * click on the specific cell of the table control.
   *
   * @param rect
   */
  public void click(Rectangle rect) {
    postClick(middle(display.getFocusControl(), rect), SWT.BUTTON1);
    processEvents();
  }

  public void click(Point p) {
    postClick(p, SWT.BUTTON1);
    processEvents();
  }

  public void click(int x, int y) {
    click(new Point(x, y));
  }

  /**
   * Imitates double clicking on the middle of the control with left mouse
   * button
   *
   * @param control
   */
  public void dblclick(Control control) {
    dblclick(middle(control.getParent(), control.getBounds()));
  }

  /**
   * Imitates double clicking with left mouse button on the middle of rectangle
   * positioned relatively to the control. Useful for instanace to click on the
   * specific cell of the table control.
   *
   * @param control
   * @param rect
   */
  public void dblclick(Control control, Rectangle rect) {
    dblclick(middle(control, rect));
  }

  /**
   * Imitates double clicking with left mouse button on the middle of rectangle
   * positioned relatively to the current focus control. Useful for instanace to
   * click on the specific cell of the table control.
   *
   * @param rect
   */
  public void dblclick(Rectangle rect) {
    dblclick(middle(focusControl, rect));
  }

  /**
   * Imitates double click with left mouse button in the given absolute point of
   * screen.
   *
   * @param pt
   */
  public void dblclick(Point pt) {
    long duration = System.currentTimeMillis() - lastClickTime;
    // System.out.println(duration+" "+pt+" "+lastClickPoint);
    if (duration < display.getDoubleClickTime() /* && pt.equals(lastClickPoint) */) {
      try {
        Thread.sleep(display.getDoubleClickTime() - duration + 10);
      }
      catch (InterruptedException e) {}
    }
    Event event = new Event();
    event.type = SWT.MouseMove;
    event.x = pt.x;
    event.y = pt.y;
    postEvent(event);

    event = new Event();
    event.type = SWT.MouseDown;
    event.x = pt.x;
    event.y = pt.y;
    event.button = 1;
    postEvent(event);

    event = new Event();
    event.type = SWT.MouseUp;
    event.x = pt.x;
    event.y = pt.y;
    event.button = 1;
    postEvent(event);

    event = new Event();
    event.type = SWT.MouseDown;
    event.x = pt.x;
    event.y = pt.y;
    event.button = 1;
    postEvent(event);

    event = new Event();
    event.type = SWT.MouseUp;
    event.x = pt.x;
    event.y = pt.y;
    event.button = 1;
    postEvent(event);
    setLastClick(pt);

    processEvents();
  }

  private void setLastClick(Point p) {
    lastClickTime = System.currentTimeMillis();
    lastClickPoint = p;
  }

  /**
   * Imitates double click with given mouse button in the given absolute point
   * of screen.
   *
   * @param pt
   * @param button 1-left, 2-middle, 3-right
   * @param stateMask
   */
  public void postClick(final Point pt, final int code) {
    if (Thread.currentThread() != display.getThread()) {
      display.syncExec(new Runnable() {
        @Override public void run() {
          postClick(pt, code);
        }
      });
      return;
    }

    checkShellVisible();
    long duration = System.currentTimeMillis() - lastClickTime;
    if (duration < display.getDoubleClickTime() && pt.equals(lastClickPoint)) {
      try {
        Thread.sleep(display.getDoubleClickTime() - duration + 10);
      }
      catch (InterruptedException e) {}
    }

    Event event = new Event();
    event.type = SWT.MouseEnter;
    event.x = pt.x;
    event.y = pt.y;
    postEvent(event);

    event = new Event();
    event.type = SWT.MouseMove;
    event.x = pt.x;
    event.y = pt.y;
    postEvent(event);

    int decodedButton = decodeButton(code);
    if (decodedButton < 1 || 3 < decodedButton) {
      throw new IllegalArgumentException("Mouse button must be between 1 and 3");
    }
    if ((code & SWT.CONTROL) != 0) postKey(SWT.CTRL, SWT.KeyDown);
    if ((code & SWT.SHIFT) != 0) postKey(SWT.SHIFT, SWT.KeyDown);
    if ((code & SWT.ALT) != 0) postKey(SWT.ALT, SWT.KeyDown);

    event = new Event();
    event.type = SWT.MouseDown;
    event.x = pt.x;
    event.y = pt.y;
    event.button = decodedButton;
//    event.stateMask = code;
    postEvent(event);

    event = new Event();
    event.type = SWT.MouseUp;
    event.x = pt.x;
    event.y = pt.y;
    event.button = decodedButton;
//    event.stateMask = code;
    postEvent(event);

    if ((code & SWT.ALT) != 0) postKey(SWT.ALT, SWT.KeyUp);
    if ((code & SWT.SHIFT) != 0) postKey(SWT.SHIFT, SWT.KeyUp);
    if ((code & SWT.CONTROL) != 0) postKey(SWT.CTRL, SWT.KeyUp);

    setLastClick(pt);

//    // To avoid hovering over a text box before exit, which causes typing
//    // with modifier keys in a next test to fail
//    event = new Event();
//    event.type = SWT.MouseMove;
//    event.x = -1;
//    event.y = -1;
//    postEvent(event);

    processEvents();

  }

  private int decodeButton(int button) {
    return
      (button & SWT.BUTTON1) != 0 ? 1 :
        (button & SWT.BUTTON2) != 0 ? 2 :
          (button & SWT.BUTTON3) != 0 ? 3 :
            (button & SWT.BUTTON4) != 0 ? 4 :
              (button & SWT.BUTTON5) != 0 ? 5 : 1;
  }

  /**
   * Imitates drag and drop from the middle of the start rectangle to the middle
   * of the end rectangle.
   *
   * @param start
   * @param end
   */
  public void dragAndDrop(Rectangle start, Rectangle end) {
    dragAndDrop(0, middle(focusControl, start), middle(focusControl, end));
    processEvents();
  }

  public void dragAndDrop(int code, Rectangle ...r) {
    Point[] p = new Point[r.length];
    for (int i = 0; i < p.length; i++) {
      p[i] = middle(focusControl, r[i]);
    }
    dragAndDrop(code, p);
    processEvents();
  }

  // public void dragAndDrop(TabularItem startItem, int location, TabularItem
  // endItem) {
  // Rectangle start = startItem.getBounds(0);
  // start.x += 4; start.width = 0;
  // Rectangle end = endItem.getBounds(0);
  // end.x += 4; end.width = 0;
  // switch (location ) {
  // case CMD.DND_BEFORE: end.y ++; end.height = 0; break;
  // case CMD.DND_AFTER: end.y += end.height-1; end.height = 0; break;
  // }
  // postDragAndDrop(middle(focusControl, start), middle(focusControl, end));
  // processEvents();
  // }

  // private void selfDragAndDrop(Rectangle start) {
  // Event event = new Event();
  // event.type = SWT.MouseMove;
  // event.x = start.x;
  // event.y = start.y;
  // postEvent(event);
  //
  // event = new Event();
  // event.type = SWT.MouseDown;
  // event.x = start.x;
  // event.y = start.y;
  // event.button = 1;
  // postEvent(event);
  //
  // if (focusControl instanceof Grid) {
  // // Dragging columns in Grid does not work without it.
  // event = new Event();
  // event.type = SWT.MouseMove;
  // event.x = start.x+4;
  // event.y = start.y+4;
  // postEvent(event);
  // processEvents();
  // }
  //
  // event = new Event();
  // event.type = SWT.MouseMove;
  // event.x = end.x;
  // event.y = end.y;
  // postEvent(event);
  // //sleep(100);
  //
  // event = new Event();
  // event.type = SWT.MouseUp;
  // event.x = end.x;
  // event.y = end.y;
  // event.button = 1;
  // postEvent(event);
  // processEvents();
  // }

  private void checkShellVisible() {
    if (Thread.currentThread() != display.getThread()) {
      display.syncExec(new Runnable() {
        @Override public void run() {
          checkShellVisible();
        }
      });
      return;
    }
    if (!shell.isVisible()) throw new RuntimeException("Shell is not visible");
  }

  /**
   * For the grid
   *
   * @param startR
   * @param endR
   */
  public void customDragAndDrop(Rectangle startR, Rectangle endR) {
    Point start = middle(focusControl, startR);
    Point end = middle(focusControl, endR);
    Event event = new Event();
    event.type = SWT.MouseMove;
    event.x = start.x;
    event.y = start.y;
    postEvent(event);

    event = new Event();
    event.type = SWT.MouseDown;
    event.x = start.x;
    event.y = start.y;
    event.button = 1;
    postEvent(event);

    // Dragging columns in Grid does not work without it.
    event = new Event();
    event.type = SWT.MouseMove;
    event.x = start.x + 4;
    event.y = start.y + 4;
    postEvent(event);
    processEvents();

    event = new Event();
    event.type = SWT.MouseMove;
    event.x = end.x;
    event.y = end.y;
    postEvent(event);

    event = new Event();
    event.type = SWT.MouseUp;
    event.x = end.x;
    event.y = end.y;
    event.button = 1;
    postEvent(event);

    setLastClick(start);
  }

  /**
   * Imitates drag and drop from the start point to the end point.
   *
   * @param origin
   * @param end
   */
  public void dragAndDrop(int code, Point ...p) {
    checkShellVisible();
    if (p.length == 0) return;

    Point start = p[0];
    Point end = p[p.length - 1];

    long duration = System.currentTimeMillis() - lastClickTime;
    if (duration < display.getDoubleClickTime() && start.equals(lastClickPoint)) {
      try {
        Thread.sleep(display.getDoubleClickTime() - duration + 1);
      }
      catch (InterruptedException e) {}
    }

    Event event = new Event();
    event.type = SWT.MouseMove;
    event.x = start.x;
    event.y = start.y;
    postEvent(event);
    processEvents();

    int decodedButton = decodeButton(code);
    if (decodedButton < 1 || 3 < decodedButton) {
      throw new IllegalArgumentException("Mouse button must be between 1 and 3");
    }
    if ((code & SWT.CONTROL) != 0) postKey(SWT.CTRL, SWT.KeyDown);
    if ((code & SWT.SHIFT) != 0) postKey(SWT.SHIFT, SWT.KeyDown);
    if ((code & SWT.ALT) != 0) postKey(SWT.ALT, SWT.KeyDown);

    event = new Event();
    event.type = SWT.MouseDown;
    event.x = start.x;
    event.y = start.y;
    event.button = decodedButton;
    postEvent(event);
    processEvents();

    event = new Event();
    event.type = SWT.DragDetect;
    event.x = start.x;
    event.y = start.y;
    event.button = decodedButton;
    event.stateMask = code;
    postEvent(event);
    processEvents();

    for (int i = 1; i < p.length; i++) {
      event = new Event();
      event.type = SWT.MouseMove;
      event.x = p[i].x;
      event.y = p[i].y;
      event.stateMask = code;
      postEvent(event);
      processEvents();
    }

    event = new Event();
    event.type = SWT.MouseUp;
    event.x = end.x;
    event.y = end.y;
    event.button = decodedButton;
    postEvent(event);
    processEvents();

    if ((code & SWT.ALT) != 0) postKey(SWT.ALT, SWT.KeyUp);
    if ((code & SWT.SHIFT) != 0) postKey(SWT.SHIFT, SWT.KeyUp);
    if ((code & SWT.CONTROL) != 0) postKey(SWT.CTRL, SWT.KeyUp);

    setLastClick(start);
  }

  public void move(int code, Point ...p) {
    checkShellVisible();
    if (p.length == 0) return;

    int decodedButton = decodeButton(code);
    if (decodedButton < 1 || 3 < decodedButton) {
      throw new IllegalArgumentException("Mouse button must be between 1 and 3");
    }
    if ((code & SWT.CONTROL) != 0) postKey(SWT.CTRL, SWT.KeyDown);
    if ((code & SWT.SHIFT) != 0) postKey(SWT.SHIFT, SWT.KeyDown);
    if ((code & SWT.ALT) != 0) postKey(SWT.ALT, SWT.KeyDown);

    Point start = p[0];
    Event event = new Event();
    event.type = SWT.DragDetect;
    event.x = start.x;
    event.y = start.y;
    event.button = decodedButton;
    event.stateMask = code;
    postEvent(event);
    processEvents();

    for (int i = 0; i < p.length; i++) {
      event = new Event();
      event.type = SWT.MouseMove;
      event.x = p[i].x;
      event.y = p[i].y;
      event.stateMask = code;
      postEvent(event);
      processEvents();
    }

    if ((code & SWT.ALT) != 0) postKey(SWT.ALT, SWT.KeyUp);
    if ((code & SWT.SHIFT) != 0) postKey(SWT.SHIFT, SWT.KeyUp);
    if ((code & SWT.CONTROL) != 0) postKey(SWT.CTRL, SWT.KeyUp);

    setLastClick(start);
  }

  public void selfDragAndDrop(Rectangle rect) {
    Point start = middle(focusControl, rect);

    Event event = new Event();
    event.type = SWT.MouseMove;
    event.x = start.x;
    event.y = start.y;
    postEvent(event);

    event = new Event();
    event.type = SWT.MouseDown;
    event.x = start.x;
    event.y = start.y;
    event.button = 1;
    postEvent(event);

    // in case start equals end
    event = new Event();
    event.type = SWT.MouseMove;
    event.x = start.x + 5;
    event.y = start.y + 5;
    postEvent(event);

    // processEvents();
    //
    // event = new Event();
    // event.type = SWT.MouseMove;
    // event.x = start.x;
    // event.y = start.y;
    // postEvent(event);
    // //sleep(100);

    event = new Event();
    event.type = SWT.MouseUp;
    event.x = start.x;
    event.y = start.y;
    event.button = 1;
    postEvent(event);
    setLastClick(start);

    processEvents();
  }

  /**
   * Calculates the point in the middle of the rectangle, which is relative to
   * the control and then translates it the coordinates of the screen.
   *
   * @param control
   * @param rect
   * @return
   */
  public Point middle(Control control, Rectangle rect) {
    return display.map(control, null, rect.x + rect.width / 2, rect.y
      + rect.height / 2);
  }

  public Point middle(Rectangle rect) {
    return display.getFocusControl().toDisplay(
      rect.x + rect.width / 2, rect.y + rect.height / 2);
  }

  public Point offset(Point p, int dx, int dy) {
    return new Point(p.x + dx, p.y + dy);
  }

  public Point toDisplay(Point p) {
    return display.getFocusControl().toDisplay(p);
  }

  public Rectangle toDisplay(Rectangle r) {
    Point p = display.getFocusControl().toDisplay(r.x, r.y);
    return new Rectangle(p.x, p.y, r.width, r.height);
  }

  public Point toDisplay(int x, int y) {
    return display.getFocusControl().toDisplay(x, y);
  }

  /**
   * Makes the current Thread to sleep delay milliseconds
   *
   * @param aDelay
   */
  public void sleep(Integer delay) {
    try {
      Thread.sleep(delay);
    }
    catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Slows down the imitated event flow to allow observing the action by a human
   *
   * @param delay
   */
  public void slow(int delay) {
    this.delay = delay;
  }

  /**
   * Sets the current focus control that will receive the events
   *
   * @param control
   */
  public void setFocus(final Control control) {
    // if (!control.setFocus()) throw new RuntimeException("Cannot set focus");
    if (Thread.currentThread() != display.getThread()) {
      display.syncExec(new Runnable() {
        @Override public void run() {
          setFocus(control);
        }
      });
      return;
    }
    control.setFocus();
    focusControl = control;
    processEvents();
  }

  public String getMessage() {
    if (exceptions.isEmpty()) return null;
    Throwable last = exceptions.get(exceptions.size() - 1);
    exceptions.remove(last);
    String s = last.getMessage();
    if (!shell.isDisposed()) shell.setText("");
    return s == null ? null : s.trim();
  }

  public <T> T findParent(Control control, Class<T> clazz) {
    if (control.getClass().equals(clazz)) return (T) control;
    if (control.getData() != null && clazz.isInstance(control.getData()))
      return (T) control.getData();
    Composite parent = control.getParent();
    return parent == null ? null : findParent(parent, clazz);
    // return parent instanceof Control ? findParent(parent, clazz) : null;
  }

  public <T> T findParent(Control control, Class<T> clazz, int count) {
    return _findParent(control, clazz, new int[] { count });
  }

  private <T> T _findParent(Control control, Class<T> clazz, int[] count) {
    if (control.getClass().equals(clazz)) {
      if (count[0] == 0)
        return (T) control;
      else
        count[0]--;
    }
    if (control.getData() != null && clazz.isInstance(control.getData())) {
      if (count[0] == 0)
        return (T) control.getData();
      else
        count[0]--;
    }
    Composite parent = control.getParent();
    return parent == null ? null : _findParent(parent, clazz, count);
    // return parent instanceof Control ? _findParent(parent, clazz, count) :
    // null;
  }

  public <T> T findChild(Control control, Class<T> clazz) {
    if (control.getClass().equals(clazz)) return (T) control;
    if (control.getData() != null && clazz.isInstance(control.getData()))
      return (T) control.getData();

    if (control instanceof Composite)
      for (Control child : ((Composite) control).getChildren()) {
        T o = findChild(child, clazz);
        if (o != null) return o;
      }
    return null;
  }

  public <T> T findChild(Control control, Class<T> clazz, int count) {
    return _findChild(control, clazz, new int[] { count });
  }

  private <T> T _findChild(Control control, Class<T> clazz, int[] count) {
    if (control.getClass().equals(clazz)) {
      if (count[0] == 0)
        return (T) control;
      else
        count[0]--;
    }
    if (control.getData() != null && clazz.isInstance(control.getData())) {
      if (count[0] == 0)
        return (T) control.getData();
      else
        count[0]--;
    }

    if (control instanceof Composite)
      for (Control child : ((Composite) control).getChildren()) {
        T o = _findChild(child, clazz, count);
        if (o != null) return o;
      }
    return null;
  }

  public void pause() {
    if (Thread.currentThread() != display.getThread()) {
      display.syncExec(new Runnable() {
        @Override public void run() {
          pause();
        }
      });
      return;
    }
    final Control control = Util.notNull(display.getFocusControl(), shell);
    final boolean quit[] = new boolean[] { false };
    final KeyAdapter listener = new KeyAdapter() {
      @Override public void keyPressed(KeyEvent e) {
        if (e.keyCode == SWT.F12) {
          quit[0] = true;
          control.removeKeyListener(this);
        }
      }
    };
    control.addKeyListener(listener);
    while (!shell.isDisposed() && !quit[0]) {
      if (!display.readAndDispatch()) display.sleep();
    }
  }

  public void close() {
    if (display.isDisposed()) return;
    display.syncExec(new Runnable() {
      @Override public void run() {
        shell.dispose();
      }
    });
  }

  /**
   * The index of remedy command that will be called when the exception happens
   *
   * @param i
   */
  public void setRemedyCommand(int i) {
    remedyCommandIndex = i;
  }

  public static void br() {
    breakFlag = true;
  }



  public void assertColor(Color color, int x, int y) {
    RGB rgb = getRGB(x, y);

    if (color.getBlue() != rgb.blue ||
        color.getGreen() != rgb.green ||
        color.getRed() != rgb.red) {
      fail(String.format("Wrong color, expected %s, actual %s", color.toString(), rgb.toString()));
    }
  }

  public void assertNotColor(Color color, int x, int y) {
    RGB rgb = getRGB(x, y);

    if (color.getBlue() == rgb.blue &&
        color.getGreen() == rgb.green &&
        color.getRed() == rgb.red) {
      fail(String.format("Wrong color, expected not %s", color.toString()));
    }
  }

  public RGB getRGB(Rectangle r, int offsetX, int offsetY) {
    return getRGB(r.x + offsetX, r.y + offsetY);
  }

  public RGB getRGB(int x, int y) {
    GC gc = new GC(display);
    Rectangle bounds = shell.getClientArea();
    Image image = new Image(display, bounds.width, bounds.height);
    Point p = shell.toDisplay(new Point(bounds.x, bounds.y));
    gc.copyArea(image, p.x, p.y);

    ImageData data = image.getImageData();
//    ImageLoader loader = new ImageLoader();
//    loader.data = new ImageData[] { data };
//    loader.save(new File("image.png").getAbsolutePath(), SWT.IMAGE_PNG);

    RGB rgb = data.palette.getRGB(data.getPixel(x, y));
    gc.dispose();
    return rgb;
  }




  public static void listenToAll(Control control) {
    listenToAll(control, new Listener() {
      @Override public void handleEvent(Event event) {
        log(event);
      }
    });
  }

  public static void listenToAll(Control control, Listener listener) {
    control.addListener(SWT.KeyDown, listener);
    control.addListener(SWT.KeyUp, listener);
    control.addListener(SWT.MouseDown, listener);
    control.addListener(SWT.MouseUp, listener);
    control.addListener(SWT.MouseMove, listener);
    control.addListener(SWT.MouseEnter, listener);
    control.addListener(SWT.MouseExit, listener);
    control.addListener(SWT.MouseDoubleClick, listener);
    control.addListener(SWT.Paint, listener);
    control.addListener(SWT.Move, listener);
    control.addListener(SWT.Resize, listener);
    control.addListener(SWT.Dispose, listener);
    control.addListener(SWT.Selection, listener);
    control.addListener(SWT.DefaultSelection, listener);
    control.addListener(SWT.FocusIn, listener);
    control.addListener(SWT.FocusOut, listener);
    control.addListener(SWT.Expand, listener);
    control.addListener(SWT.Collapse, listener);
    control.addListener(SWT.Iconify, listener);
    control.addListener(SWT.Deiconify, listener);
    control.addListener(SWT.Close, listener);
    control.addListener(SWT.Show, listener);
    control.addListener(SWT.Hide, listener);
    control.addListener(SWT.Modify, listener);
    control.addListener(SWT.Verify, listener);
    control.addListener(SWT.Activate, listener);
    control.addListener(SWT.Deactivate, listener);
    control.addListener(SWT.Help, listener);
    control.addListener(SWT.DragDetect, listener);
    control.addListener(SWT.Arm, listener);
    control.addListener(SWT.Traverse, listener);
    control.addListener(SWT.MouseHover, listener);
    control.addListener(SWT.HardKeyDown, listener);
    control.addListener(SWT.HardKeyUp, listener);
    control.addListener(SWT.MenuDetect, listener);
    control.addListener(SWT.SetData, listener);
    control.addListener(SWT.MouseWheel, listener);
    control.addListener(SWT.Settings, listener);
    control.addListener(SWT.EraseItem, listener);
    control.addListener(SWT.MeasureItem, listener);
    control.addListener(SWT.PaintItem, listener);
    control.addListener(SWT.ImeComposition, listener);
  }


  static String getCommandName(int x) {
    return
    x == Matrix.CMD_FOCUS_UP ? "CMD_FOCUS_UP" :
    x == Matrix.CMD_FOCUS_DOWN ? "CMD_FOCUS_DOWN" :
    x == Matrix.CMD_FOCUS_LEFT ? "CMD_FOCUS_LEFT" :
    x == Matrix.CMD_FOCUS_RIGHT ? "CMD_FOCUS_RIGHT" :
    x == Matrix.CMD_FOCUS_PAGE_UP ? "CMD_FOCUS_PAGE_UP" :
    x == Matrix.CMD_FOCUS_PAGE_DOWN ? "CMD_FOCUS_PAGE_DOWN" :
    x == Matrix.CMD_FOCUS_PAGE_LEFT ? "CMD_FOCUS_PAGE_LEFT" :
    x == Matrix.CMD_FOCUS_PAGE_RIGHT ? "CMD_FOCUS_PAGE_RIGHT" :
    x == Matrix.CMD_FOCUS_MOST_LEFT ? "CMD_FOCUS_MOST_LEFT" :
    x == Matrix.CMD_FOCUS_MOST_RIGHT ? "CMD_FOCUS_MOST_RIGHT" :
    x == Matrix.CMD_FOCUS_MOST_UP ? "CMD_FOCUS_MOST_UP" :
    x == Matrix.CMD_FOCUS_MOST_DOWN ? "CMD_FOCUS_MOST_DOWN" :
    x == Matrix.CMD_FOCUS_MOST_UP_LEFT ? "CMD_FOCUS_MOST_UP_LEFT" :
    x == Matrix.CMD_FOCUS_MOST_DOWN_RIGHT ? "CMD_FOCUS_MOST_DOWN_RIGHT" :
    x == Matrix.CMD_FOCUS_LOCATION ? "CMD_FOCUS_LOCATION" :
    x == Matrix.CMD_FOCUS_LOCATION_ALTER ? "CMD_FOCUS_LOCATION_ALTER" :
    x == Matrix.CMD_SELECT_ALL ? "CMD_SELECT_ALL" :
    x == Matrix.CMD_SELECT_UP ? "CMD_SELECT_UP" :
    x == Matrix.CMD_SELECT_DOWN ? "CMD_SELECT_DOWN" :
    x == Matrix.CMD_SELECT_LEFT ? "CMD_SELECT_LEFT" :
    x == Matrix.CMD_SELECT_RIGHT ? "CMD_SELECT_RIGHT" :
    x == Matrix.CMD_SELECT_PAGE_UP ? "CMD_SELECT_PAGE_UP" :
    x == Matrix.CMD_SELECT_PAGE_DOWN ? "CMD_SELECT_PAGE_DOWN" :
    x == Matrix.CMD_SELECT_PAGE_LEFT ? "CMD_SELECT_PAGE_LEFT" :
    x == Matrix.CMD_SELECT_PAGE_RIGHT ? "CMD_SELECT_PAGE_RIGHT" :
    x == Matrix.CMD_SELECT_FULL_UP ? "CMD_SELECT_FULL_UP" :
    x == Matrix.CMD_SELECT_FULL_DOWN ? "CMD_SELECT_FULL_DOWN" :
    x == Matrix.CMD_SELECT_FULL_LEFT ? "CMD_SELECT_FULL_LEFT" :
    x == Matrix.CMD_SELECT_FULL_RIGHT ? "CMD_SELECT_FULL_RIGHT" :
    x == Matrix.CMD_SELECT_FULL_UP_LEFT ? "CMD_SELECT_FULL_UP_LEFT" :
    x == Matrix.CMD_SELECT_FULL_DOWN_RIGHT ? "CMD_SELECT_FULL_DOWN_RIGHT" :
    x == Matrix.CMD_SELECT_TO_LOCATION ? "CMD_SELECT_TO_LOCATION" :
    x == Matrix.CMD_SELECT_TO_LOCATION_ALTER ? "CMD_SELECT_TO_LOCATION_ALTER" :
    x == Matrix.CMD_SELECT_ROW ? "CMD_SELECT_ROW" :
    x == Matrix.CMD_SELECT_ROW_ALTER ? "CMD_SELECT_ROW_ALTER" :
    x == Matrix.CMD_SELECT_COLUMN ? "CMD_SELECT_COLUMN" :
    x == Matrix.CMD_SELECT_COLUMN_ALTER ? "CMD_SELECT_COLUMN_ALTER" :
    x == Matrix.CMD_SELECT_TO_ROW ? "CMD_SELECT_TO_ROW" :
    x == Matrix.CMD_SELECT_TO_ROW_ALTER ? "CMD_SELECT_TO_ROW_ALTER" :
    x == Matrix.CMD_SELECT_TO_COLUMN ? "CMD_SELECT_TO_COLUMN" :
    x == Matrix.CMD_SELECT_TO_COLUMN_ALTER ? "CMD_SELECT_TO_COLUMN_ALTER" :
    x == Matrix.CMD_CUT ? "CMD_CUT" :
    x == Matrix.CMD_COPY ? "CMD_COPY" :
    x == Matrix.CMD_PASTE ? "CMD_PASTE" :
    x == Matrix.CMD_EDIT_ACTIVATE ? "CMD_EDIT_ACTIVATE" :
    x == Matrix.CMD_EDIT_DEACTIVATE_APPLY ? "CMD_EDIT_DEACTIVATE_APPLY" :
    x == Matrix.CMD_EDIT_DEACTIVATE_CANCEL ? "CMD_EDIT_DEACTIVATE_CANCEL" :
    x == Matrix.CMD_EDIT_APPLY ? "CMD_EDIT_APPLY" :
    x == Matrix.CMD_DELETE ? "CMD_DELETE" :
    x == Matrix.CMD_ITEM_HIDE ? "CMD_ITEM_HIDE" :
    x == Matrix.CMD_ITEM_SHOW ? "CMD_ITEM_SHOW" :
    x == Matrix.CMD_RESIZE_PACK ? "CMD_RESIZE_PACK" :
    x == Matrix.CMD_TRAVERSE_TAB_NEXT ? "CMD_TRAVERSE_TAB_NEXT" :
    x == Matrix.CMD_TRAVERSE_TAB_PREVIOUS ? "CMD_TRAVERSE_TAB_PREVIOUS" : "";

  }

  public static String getTypeName(int x) {
    return x == 1 ? "KeyDown" :
      x == 2 ? "KeyUp" :
      x == 3 ? "MouseDown" :
      x == 4 ? "MouseUp" :
      x == 5 ? "MouseMove" :
      x == 6 ? "MouseEnter" :
      x == 7 ? "MouseExit" :
      x == 8 ? "MouseDoubleClick" :
      x == 9 ? "Paint" :
      x == 10 ? "Move" :
      x == 11 ? "Resize" :
      x == 12 ? "Dispose" :
      x == 13 ? "Selection" :
      x == 14 ? "DefaultSelection" :
      x == 15 ? "FocusIn" :
      x == 16 ? "FocusOut" :
      x == 17 ? "Expand" :
      x == 18 ? "Collapse" :
      x == 19 ? "Iconify" :
      x == 20 ? "Deiconify" :
      x == 21 ? "Close" :
      x == 22 ? "Show" :
      x == 23 ? "Hide" :
      x == 24 ? "Modify" :
      x == 25 ? "Verify" :
      x == 26 ? "Activate" :
      x == 27 ? "Deactivate" :
      x == 28 ? "Help" :
      x == 29 ? "DragDetect" :
      x == 30 ? "Arm" :
      x == 31 ? "Traverse" :
      x == 32 ? "MouseHover" :
      x == 33 ? "HardKeyDown" :
      x == 34 ? "HardKeyUp" :
      x == 35 ? "MenuDetect" :
      x == 36 ? "SetData" :
      x == 37 ? "MouseWheel" :
      x == 39 ? "Settings" :
      x == 40 ? "EraseItem" :
      x == 41 ? "MeasureItem" :
      x == 42 ? "PaintItem" :
      x == 43 ? "ImeComposition" : "";
  }

  public static String getTraverseName(int x) {
    return
      x == SWT.TRAVERSE_NONE ? "TRAVERSE_NONE" :
      x == SWT.TRAVERSE_ESCAPE ? "TRAVERSE_ESCAPE" :
      x == SWT.TRAVERSE_RETURN ? "TRAVERSE_RETURN" :
      x == SWT.TRAVERSE_TAB_PREVIOUS ? "TRAVERSE_TAB_PREVIOUS" :
      x == SWT.TRAVERSE_TAB_NEXT ? "TRAVERSE_TAB_NEXT" :
      x == SWT.TRAVERSE_ARROW_PREVIOUS ? "TRAVERSE_ARROW_PREVIOUS" :
      x == SWT.TRAVERSE_ARROW_NEXT ? "TRAVERSE_ARROW_NEXT" :
      x == SWT.TRAVERSE_MNEMONIC ? "TRAVERSE_MNEMONIC" :
      x == SWT.TRAVERSE_PAGE_PREVIOUS ? "TRAVERSE_PAGE_PREVIOUS" :
      x == SWT.TRAVERSE_PAGE_NEXT ? "TRAVERSE_PAGE_NEXT" : "";
  }

  public static String getStateMaskName(int x) {
    int[] mask = new int[]
      {SWT.MOD1, SWT.MOD2, SWT.MOD3, SWT.BUTTON1, SWT.BUTTON2, SWT.BUTTON3};
    String[] name= new String[]
      {"SWT.MOD1", "SWT.MOD2", "SWT.MOD3", "SWT.BUTTON1", "SWT.BUTTON2", "SWT.BUTTON3"};
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < mask.length; i++) {
      if ((x & mask[i]) != 0) {
        if (sb.length() > 0) sb.append("+");
        sb.append(name[i]);
      }
    }
    return sb.toString();
  }

  public static void log(Event e) {
    StringBuilder sb = new StringBuilder();
    sb.append(getTypeName(e.type));
    if (e.stateMask > 0) {
      if (sb.length() > 0) sb.append(" ");
      sb.append(getStateMaskName(e.stateMask));
    }
    if (e.character > 0) {
      if (sb.length() > 0) sb.append(" ");
      sb.append(e.character);
    }
    sb.append(" ");
    sb.append(e.button + e.keyCode);
    TestUtil.log(sb);
  }

  /**
   * Creates 5x5 matrix indexed by Integer with visible headers
   * and opens the shell with it.
   * @return matrix
   */
  protected Matrix<Integer, Integer> createMatrix() {
    Matrix matrix = new Matrix(shell, 0);
//    listenToAll(matrix);
    matrix.getAxisY().getHeader().setVisible(true);
    matrix.getAxisX().getHeader().setVisible(true);
    matrix.getAxisY().getBody().setCount(5);
    matrix.getAxisX().getBody().setCount(5);
    focusControl = matrix;
    shell.open();
    processEvents();
    return matrix;
  }

  public void runBinding(GestureBinding b) {
	  switch (b.eventType) {
	  case SWT.MouseDown:
	  }
  }

  public static void log(Object ...s) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < s.length; i++) {
      if (i > 0) sb.append(" ");
      sb.append(s[i]);
    }
    System.out.println(sb);
  }

  public static void saveImage(Image image) {
    ImageLoader loader = new ImageLoader();
    loader.data = new ImageData[] { image.getImageData() };
    loader.save("image.png", SWT.IMAGE_PNG);
  }
  public static void saveImage(Image image, String name) {
    ImageLoader loader = new ImageLoader();
    loader.data = new ImageData[] { image.getImageData() };
    loader.save(name, name.endsWith(".png") ? SWT.IMAGE_PNG : -1);
  }

  Image getImage(Rectangle r) {
    GC gc = new GC(display);
    final Image image = new Image(display, r.width, r.height);
    Point p = shell.toDisplay(new Point(r.x, r.y));
    gc.copyArea(image, p.x, p.y);
    gc.dispose();
    shell.addDisposeListener(new DisposeListener() {
      @Override public void widgetDisposed(DisposeEvent e) {
        image.dispose();
      }
    });
    return image;
  }

  void assertEqualImage(Image expected, Image actual) {
    GC gc = new GC(display);
    ImageData data1 = expected.getImageData();
    ImageData data2 = actual.getImageData();
    assertEquals("Images have different width", data1.width, data2.width);
    assertEquals("Images have different height", data1.height, data2.height);
    for (int x = 0; x < data1.width; x++) {
      for (int y = 0; y < data1.height; y++) {
        Assert.assertTrue(MessageFormat.format("Wrong pixel at {0}, {1}", x, y),
//          data1.getPixel(x, y), data2.getPixel(x, y));
          compareRGB(
            data1.palette.getRGB(data1.getPixel(x, y)),
            data2.palette.getRGB(data2.getPixel(x, y))));
      }
    }
    gc.dispose();
  }

  boolean compareRGB(RGB rgb1, RGB rgb2) {
    return abs(rgb1.red - rgb2.red) +
      abs(rgb1.green - rgb2.green) +
      abs(rgb1.blue - rgb2.blue) < 100;
  }
}
