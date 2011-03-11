package pl.netanel.swt.matrix;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;

import pl.netanel.swt.Listeners;

public class Axis {

	final Matrix matrix;
	final int axisIndex;
	final AxisModel model;
	final Layout layout;
	final Listeners listeners;
	private ScrollBar scrollBar;
	
	public Axis(final Matrix matrix, int axisIndex) {
		this.matrix = matrix;
		this.axisIndex = axisIndex;
		model = axisIndex == 0 ? matrix.getModel().getModel0() : matrix.getModel().getModel1();
		layout = new Layout(model);
		listeners = new Listeners();
		
		scrollBar = axisIndex == 0 ? matrix.getHorizontalBar() : matrix.getVerticalBar();
		if (scrollBar != null) {
			scrollBar.addListener(SWT.Selection, new Listener() {
				private int selection = -1;

				public void handleEvent(Event e) {
					int newSelection = scrollBar.getSelection();
					if (newSelection == selection) return;
					selection = newSelection;
					//				debugSWT(e.detail);
					Move move = 
						e.detail == SWT.ARROW_DOWN 	? Move.NEXT :
							e.detail == SWT.ARROW_UP 	? Move.PREVIOUS : 
								e.detail == SWT.PAGE_DOWN 	? Move.NEXT_PAGE: 
									e.detail == SWT.PAGE_UP 	? Move.PREVIOUS_PAGE: 
										Move.NULL;

					layout.setScrollPosition(selection, move);
					scrollBar.setThumb(layout.getScrollThumb());
					matrix.redraw();
				}
			});
		}
	}
	
	public Section getBody() {
		return model.getBody();
	}

	public void setHeaderVisible(boolean visible) {
//		AxisModel opposite = axisIndex == 1 ? matrix.getModel0() : matrix.getModel1();
		Section header = model.getHeader();
		if (header == null) return;
		header.setVisible(visible);
		if (visible) {
			if (header.isEmpty()) header.setCount(1);	
		}
	}
	
	/**
	 * Scrolls the bar according to the axis state.
	 */
	void scroll() {
		if (scrollBar == null) return;
		scrollBar.setSelection(layout.getScrollPosition());
		scrollBar.setThumb(layout.getScrollThumb());
	}
}
