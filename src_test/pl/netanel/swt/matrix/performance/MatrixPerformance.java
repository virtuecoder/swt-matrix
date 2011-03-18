package pl.netanel.swt.matrix.performance;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.junit.Test;

import pl.netanel.swt.matrix.Matrix;

public class MatrixPerformance {
	ArrayList<Long> time = new ArrayList<Long>();
	
	@Test
	public void cursorNavigation() throws Exception {
		Shell shell = new Shell();
		Display display = shell.getDisplay();
		shell.setLayout(new FillLayout());
		
		Matrix matrix = new Matrix(shell, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL) {
			@Override
			protected void onPaint(Event event) {
				long t = System.currentTimeMillis();
				super.onPaint(event);
				t = System.currentTimeMillis() - t;
				time.add(t);
//				System.out.println(BigDecimal.valueOf(System.nanoTime() - t, 6).toString());
			}
		}; 
				//, new MatrixModel(new AxisModel(BigInteger.class), new AxisModel(BigInteger.class)));
		matrix.getModel0().getBody().setCount(1000000000); //new BigInteger("1000000000000000"));
		matrix.getModel1().getBody().setCount(1000000000); //new BigInteger("1000000000000000"));
		
		shell.setBounds(display.getBounds());
		shell.open();
		
		for (int i = 0; i < 10; i++) {
			press(SWT.ARROW_DOWN);
		}
		
		long sum = 0;
		for (int i = 1, imax = time.size(); i < imax; i++) {
			sum += time.get(i);
		}
		long avg = sum / (time.size() - 1);
		System.out.println(avg);
		assertTrue("" + avg + " expected to be <60", avg < 60);
	
//		while (!shell.isDisposed()) {
//			if (!display.readAndDispatch()) {
//				display.sleep();
//			}
//		}
	}
	
	/**
     * Posts key event, for keys that are not characters
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
     * @param ch
     * @param type SWT.KeyDown or SWT.KeyUp
     */
    public void postChar(char ch, int type) {
        Event event = new Event();
        event.type = type;
        event.character = ch;
        postEvent(event);
    }


    /**
     * Puts the event in the system event queue
     * @param event
     * @return
     */
    public boolean postEvent(final Event event) {
    	Display display = Display.getCurrent();
    	if (Thread.currentThread() != display.getThread()) {
    		final boolean[] result = new boolean[] {false};
    		display.syncExec(new Runnable() {
				@Override public void run() {
					result[0] = postEvent(event);
				}
			});
    		return result[0];
    	}
		return display.post(event);
    }
    
    /**
     * Imitates pressing a key that is not a character
     * @param key
     */
    public void press(final int key) {
		postKey(key, SWT.KeyDown);
		postKey(key, SWT.KeyUp);
		processEvents();
    }
    
    /**
     * Imitates pressing a key that is not a character, with modifier keys like
     * SWT.CONTROL, SWT.SHIFT, SWT.ALT, which can be joind by <code>|</code>
     * @param stateMask
     * @param keyCode
     */
    public void press(final int stateMask, final int keyCode) {
    	Display display = Display.getCurrent();
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
        //sleep(50);
        postKey(keyCode, SWT.KeyUp);
        if ((stateMask & SWT.ALT) != 0) postKey(SWT.ALT, SWT.KeyUp);
        if ((stateMask & SWT.SHIFT) != 0) postKey(SWT.SHIFT, SWT.KeyUp);
        if ((stateMask & SWT.CONTROL) != 0) postKey(SWT.CTRL, SWT.KeyUp);

        processEvents();
        
        
    }
    
    /**
     * Process all the pending events in system event queue
     */
    public void processEvents() {
    	Display display = Display.getCurrent();
    	if (Thread.currentThread() != display.getThread()) { 
    		display.asyncExec(new Runnable() {
    			@Override public void run() {
    				processEvents();
    			}
    		});
    		return;
    	}
		//while (!display.isDisposed() && display.readAndDispatch());
    	while (display.readAndDispatch());
    }

}
