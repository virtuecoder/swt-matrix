package pl.netanel.swt.matrix;

import static org.junit.Assert.*;
import static pl.netanel.swt.matrix.TestUtil.*;

import org.junit.Test;

import pl.netanel.swt.matrix.Dock;
import pl.netanel.swt.matrix.Layout;
import pl.netanel.swt.matrix.Section;


public class LayoutComputeTest {
	
	@Test(expected = NullPointerException.class)
	public void constructorNull() throws Exception {
		new Layout(null);
	}
	
	@Test
	public void empty() throws Exception {
		Layout layout = new Layout(new AxisModel());
		layout.setViewportSize(1000);
		layout.compute();
	}
	
	@Test
	public void indexSimple() throws Exception {
		Layout layout = new Layout(new AxisModel());
		layout.setViewportSize(350);

		Section body = layout.getSection(0);
		body.setCount(5);
		body.setDefaultCellWidth(100);
		
		layout.compute();
		assertEquals("", indexes(layout.cellSequence(Dock.HEAD, body)));
		assertEquals("0, 1, 2, 3", indexes(layout.cellSequence(Dock.MAIN, body)));
		assertEquals("", indexes(layout.cellSequence(Dock.TAIL, body)));
		
		layout.setViewportSize(1000);
		layout.compute();
		assertEquals("", indexes(layout.cellSequence(Dock.HEAD, body)));
		assertEquals("0, 1, 2, 3, 4", indexes(layout.cellSequence(Dock.MAIN, body)));
		assertEquals("", indexes(layout.cellSequence(Dock.TAIL, body)));

		// Empty
		layout = new Layout(new AxisModel());
		layout.setViewportSize(350);
		layout.compute();
		assertEquals("", indexes(layout.cellSequence(Dock.MAIN, body)));
	}
	
	@Test
	public void indexManySections() throws Exception {
		Section section0 = new Section(int.class);
		Section section1 = new Section(int.class);
		Section section2 = new Section(int.class);
		AxisModel model = new AxisModel(int.class, section0, section1, section2);
		section0.setCount(1); // header
		section1.setCount(5); // body
		section2.setCount(1); // footer
		
		section1.setDefaultCellWidth(100);
		
		Layout layout = new Layout(model);
		layout.setViewportSize(1000);
		
		layout.compute();
		assertEquals("", indexes(layout.cellSequence(Dock.HEAD, section1)));
		assertEquals("0, 1, 2, 3, 4", indexes(layout.cellSequence(Dock.MAIN, section1)));
		assertEquals("0", indexes(layout.cellSequence(Dock.MAIN, section2)));
	}
	
	
	
}
