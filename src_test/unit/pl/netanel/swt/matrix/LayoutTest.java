package pl.netanel.swt.matrix;

import static org.junit.Assert.assertEquals;
import static pl.netanel.swt.matrix.TestUtil.distances;
import static pl.netanel.swt.matrix.TestUtil.indexes;
import static pl.netanel.swt.matrix.TestUtil.item;
import static pl.netanel.swt.matrix.TestUtil.widths;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import pl.netanel.swt.matrix.Frozen;
import pl.netanel.swt.matrix.Layout;
import pl.netanel.swt.matrix.SectionCore;

@RunWith(JUnit4.class) public class  LayoutTest {
	
	@Test(expected = NullPointerException.class)
	public void constructorNull() throws Exception {
		new Layout(null);
	}
	
	@Test
	public void empty() throws Exception {
		Layout layout = new Layout(new Axis());
		layout.setViewportSize(1000);
		layout.compute();
	}
	
	@Test
	public void cellSequenceIndexSimple() throws Exception {
		Layout layout = new Layout(new Axis());
		layout.setViewportSize(350);

		Section body = layout.axis.getBody();
		body.setCount(5);
		body.setDefaultCellWidth(100);
		
		layout.compute();
		assertEquals("", indexes(layout.cellSequence(Frozen.HEAD, body)));
		assertEquals("0, 1, 2, 3", indexes(layout.cellSequence(Frozen.NONE, body)));
		assertEquals("", indexes(layout.cellSequence(Frozen.TAIL, body)));
		
		layout.setViewportSize(1000);
		layout.compute();
		assertEquals("", indexes(layout.cellSequence(Frozen.HEAD, body)));
		assertEquals("0, 1, 2, 3, 4", indexes(layout.cellSequence(Frozen.NONE, body)));
		assertEquals("", indexes(layout.cellSequence(Frozen.TAIL, body)));

		// Empty
		layout = new Layout(new Axis());
		layout.setViewportSize(350);
		layout.compute();
		assertEquals("", indexes(layout.cellSequence(Frozen.NONE, body)));
	}
	
	@Test
	public void cellSequenceIndexManySections() throws Exception {
		SectionCore section0 = new SectionCore(Integer.class);
		SectionCore section1 = new SectionCore(Integer.class);
		SectionCore section2 = new SectionCore(Integer.class);
		Axis model = new Axis(section0, section1, section2);
		section0.setCount(1); // header
		section1.setCount(5); // body
		section2.setCount(1); // footer
		
		section1.setDefaultCellWidth(100);
		
		Layout layout = new Layout(model);
		layout.setViewportSize(1000);
		
		layout.compute();
		assertEquals("", indexes(layout.cellSequence(Frozen.HEAD, section1)));
		assertEquals("0, 1, 2, 3, 4", indexes(layout.cellSequence(Frozen.NONE, section1)));
		assertEquals("0", indexes(layout.cellSequence(Frozen.NONE, section2)));
	}
	
	@Test
	public void freezeIndexes() throws Exception {
		Layout layout = new Layout(new Axis(new SectionCore(Integer.class)));
		Section body = layout.axis.getBody();
		body.setCount(5);
		body.setDefaultCellWidth(100);
		
		layout.setViewportSize(350);
		layout.freezeHead(1);
		layout.freezeTail(1);
		layout.compute();
		
//		showMatrix(layout);
		
		assertEquals("0", indexes(layout.cellSequence(Frozen.HEAD, body)));
		assertEquals("1, 2", indexes(layout.cellSequence(Frozen.NONE, body)));
		assertEquals("4", indexes(layout.cellSequence(Frozen.TAIL, body)));
		assertEquals("0, 1", indexes(layout.lineSequence(Frozen.HEAD, body)));
		assertEquals("1, 2, 3", indexes(layout.lineSequence(Frozen.NONE, body)));
		assertEquals("4, 5", indexes(layout.lineSequence(Frozen.TAIL, body)));
		assertEquals("100", widths(layout.cellSequence(Frozen.TAIL, body)));
		
		layout.freezeTail(2);
		layout.compute();
		
		assertEquals("0", indexes(layout.cellSequence(Frozen.HEAD, body)));
		assertEquals("1", indexes(layout.cellSequence(Frozen.NONE, body)));
		assertEquals("3, 4", indexes(layout.cellSequence(Frozen.TAIL, body)));
		
		layout.freezeTail(3);
		layout.compute();
		assertEquals("0", indexes(layout.cellSequence(Frozen.HEAD, body)));
		assertEquals("1", indexes(layout.cellSequence(Frozen.NONE, body)));
		assertEquals("3, 4", indexes(layout.cellSequence(Frozen.TAIL, body)));
		
		// Freeze head takes precedence over freeze tail
		layout.freezeTail(4);
		layout.compute();
		assertEquals("0", indexes(layout.cellSequence(Frozen.HEAD, body)));
		assertEquals("1", indexes(layout.cellSequence(Frozen.NONE, body)));
		assertEquals("3, 4", indexes(layout.cellSequence(Frozen.TAIL, body)));
		
		layout.freezeHead(10);
		layout.freezeTail(10);
		layout.compute();
		assertEquals("0, 1, 2", indexes(layout.cellSequence(Frozen.HEAD, body)));
		assertEquals("3", indexes(layout.cellSequence(Frozen.NONE, body)));
		assertEquals("", indexes(layout.cellSequence(Frozen.TAIL, body)));
		
		layout.setViewportSize(1000);
		layout.freezeHead(1);
		layout.freezeTail(1);
		layout.compute();
		assertEquals("0", indexes(layout.cellSequence(Frozen.HEAD, body)));
		assertEquals("1, 2, 3", indexes(layout.cellSequence(Frozen.NONE, body)));
		assertEquals("4", indexes(layout.cellSequence(Frozen.TAIL, body)));
	}
	
	@Test
	public void freezeDistances() throws Exception {
		Layout layout = new Layout(new Axis(new SectionCore(Integer.class)));
		Section body = layout.axis.getBody();
		body.setCount(5);
		body.setDefaultCellWidth(100);
		
		layout.setViewportSize(350);
		layout.freezeHead(1);
		layout.freezeTail(1);
		layout.compute();
		assertEquals("1", distances(layout.cellSequence(Frozen.HEAD, body)));
		assertEquals("102, 203", distances(layout.cellSequence(Frozen.NONE, body)));
		assertEquals("100, 100", widths(layout.cellSequence(Frozen.NONE, body)));
		assertEquals("249", distances(layout.cellSequence(Frozen.TAIL, body)));
		
		assertEquals("0, 101", distances(layout.lineSequence(Frozen.HEAD, body)));
		assertEquals("101, 202, 303", distances(layout.lineSequence(Frozen.NONE, body)));
		assertEquals("1, 1, 1", widths(layout.lineSequence(Frozen.NONE, body)));
		assertEquals("248, 349", distances(layout.lineSequence(Frozen.TAIL, body)));
		
		// Tail should follow main
		layout.setViewportSize(1000);
		layout.compute();
		assertEquals("1", distances(layout.cellSequence(Frozen.HEAD, body)));
		assertEquals("102, 203, 304", distances(layout.cellSequence(Frozen.NONE, body)));
		assertEquals("405", distances(layout.cellSequence(Frozen.TAIL, body)));
		assertEquals("404, 505", distances(layout.lineSequence(Frozen.TAIL, body)));
		
		
		layout.freezeHead(0);
		layout.compute();
		assertEquals("", distances(layout.cellSequence(Frozen.HEAD, body)));
		
		// TODO alternate cell and line widths
   	}
	
	@Test
	public void freezeLineWidth() throws Exception {
		Layout layout = new Layout(new Axis(new SectionCore(Integer.class)));
		Section body = layout.axis.getBody();
		body.setCount(5);
		body.setDefaultCellWidth(100);
		layout.head.freezeLineWidth = 10;
		layout.tail.freezeLineWidth = 10;
		layout.freezeHead(1);
		layout.freezeTail(1);
		layout.setViewportSize(1000);
		layout.compute();
		
		assertEquals("1, 10", widths(layout.lineSequence(Frozen.HEAD, body)));		
		assertEquals("0, 101", distances(layout.lineSequence(Frozen.HEAD, body)));		
		assertEquals("111, 212, 313", distances(layout.cellSequence(Frozen.NONE, body)));		
		assertEquals("10, 1", widths(layout.lineSequence(Frozen.TAIL, body)));		
		assertEquals("413, 523", distances(layout.lineSequence(Frozen.TAIL, body)));
		
		layout.setViewportSize(450);
		layout.compute();
		
		assertEquals("339, 449", distances(layout.lineSequence(Frozen.TAIL, body)));

	}
	
	@Test
	public void getItemByDistance() throws Exception {
		Layout layout = new Layout(new Axis());
		layout.setViewportSize(450);
		
		Section body = layout.axis.getBody();
		body.setCount(10);
		body.setCellWidth(0, 0, 50);
		body.setDefaultCellWidth(100);
		
		body.move(6, 9, 3);
		body.setHidden(2, 4, true);
		// 0, 1, 6, 7, 8, 9, 5
		
		layout.freezeHead(1);
		layout.freezeTail(1);
		layout.compute();
//		assertEquals("1, 6, 7", indexes(layout.cellSequence(Dock.MAIN, body)));
//		showMatrix(layout);
		
		assertEquals("0", layout.getItemByDistance(50).getIndex().toString());
		assertEquals("1", layout.getItemByDistance(101).getIndex().toString());
		assertEquals("1", layout.getItemByDistance(102).getIndex().toString());
		assertEquals("6", layout.getItemByDistance(250).getIndex().toString());
		assertEquals("7", layout.getItemByDistance(348).getIndex().toString());
		assertEquals("5", layout.getItemByDistance(349).getIndex().toString());
		assertEquals("5", layout.getItemByDistance(449).getIndex().toString());
		
	}

//	@Test
//	public void navigationDisabled() throws Exception {
//		Layout layout = new Layout(new Axis());
//		layout.setViewportSize(1000);
//		
//		layout.axis.getHeader().setNavigationEnabled(false);
//		layout.compute();
//		assertEquals(layout.axis.getBody(), layout.current.section);
//	}
	
	@Test
	public void reorderBeforeStart() throws Exception {
		Layout layout = new Layout(new Axis(new SectionCore(Integer.class)));
		layout.setViewportSize(1000);
		
		Section body = layout.axis.getBody();
		body.setCount(10);
		body.setDefaultCellWidth(100);
		body.setSelected(1, 1, true);
		
		layout.compute();
		
		assertEquals(0, layout.start.getIndex());

		layout.reorder(item(body, 1), item(body, 0));
		assertEquals(1, layout.start.getIndex());
		
		layout.reorder(item(body, 1), item(body, 0));
		assertEquals(0, layout.start.getIndex());
		
		layout.reorder(item(body, 1), item(body, 9));
		assertEquals(0, layout.start.getIndex());
	}
	
	@Test
	public void reorderScattered() throws Exception {
		Layout layout = new Layout(new Axis(new SectionCore(Integer.class)));
		layout.setViewportSize(1000);
		
		Section body = layout.axis.getBody();
		body.setCount(5);
		body.setDefaultCellWidth(100);
		body.setSelected(1, 1, true);
		body.setSelected(3, 3, true);
		
		layout.compute();
		
		layout.reorder(item(body, 1), item(body, 2));
		assertEquals("0, 2, 1, 3, 4", indexes(layout.cellSequence(Frozen.NONE, body)));
	}
	
	
	
}
