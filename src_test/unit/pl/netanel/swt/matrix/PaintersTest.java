package pl.netanel.swt.matrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.swt.widgets.Shell;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@SuppressWarnings({"rawtypes", "unchecked"}) @RunWith(JUnit4.class) public class  PaintersTest {
  
  @Test 
  public void addNullPainter() throws Exception {
    final Matrix matrix = new Matrix(new Shell(), 0);
    checkExceptionContains("painter", new Runnable() {
      public void run() {
        matrix.addPainter(null);        
      }
    });
  }
  
  @Test 
  public void addNullPainterAtIndex() throws Exception {
    final Matrix matrix = new Matrix(new Shell(), 0);
    checkExceptionContains("painter", new Runnable() {
      public void run() {
        matrix.addPainter(0, null);        
      }
    });
  }
  
  @Test 
  public void removeNullPainter() throws Exception {
    final Matrix matrix = new Matrix(new Shell(), 0);
    checkExceptionContains("painter", new Runnable() {
      public void run() {
        matrix.removePainter(null);        
      }
    });
  }
  
  @Test 
  public void replaceNullPainter() throws Exception {
    final Matrix matrix = new Matrix(new Shell(), 0);
    checkExceptionContains("painter", new Runnable() {
      public void run() {
        matrix.replacePainterPreserveStyle(null);        
      }
    });
  }
    
  @Test 
  public void setNullPainter() throws Exception {
    final Matrix matrix = new Matrix(new Shell(), 0);
    checkExceptionContains("painter", new Runnable() {
      public void run() {
        matrix.setPainter(0, null);        
      }
    });
  }
  
  @Test 
  public void getNullPainter() throws Exception {
    final Matrix matrix = new Matrix(new Shell(), 0);
    checkExceptionContains("name", new Runnable() {
      public void run() {
        matrix.getPainter(null);        
      }
    });
  }
  
  @Test 
  public void indexOfNullPainter() throws Exception {
    final Matrix matrix = new Matrix(new Shell(), 0);
    checkExceptionContains("name", new Runnable() {
      public void run() {
        matrix.indexOfPainter(null);        
      }
    });
  }
  
  @Test(expected = IndexOutOfBoundsException.class)
  public void addPainterWrongIndex() throws Exception {
    final Matrix matrix = new Matrix(new Shell(), 0);
    matrix.addPainter(-1, new Painter("test"));        
  }
  
  @Test(expected = IndexOutOfBoundsException.class)
  public void setPainterWrongIndex() throws Exception {
    final Matrix matrix = new Matrix(new Shell(), 0);
    matrix.setPainter(-1, new Painter("test"));        
  }
  
  @Test(expected = IndexOutOfBoundsException.class)
  public void removePainterWrongIndex() throws Exception {
    final Matrix matrix = new Matrix(new Shell(), 0);
    matrix.removePainter(-1);        
  }
  
  @Test(expected = IndexOutOfBoundsException.class)
  public void getPainterWrongIndex() throws Exception {
    final Matrix matrix = new Matrix(new Shell(), 0);
    matrix.getPainter(-1);        
  }
  
  @Test 
  public void addPainterDuplicated() throws Exception {
    final Matrix matrix = new Matrix(new Shell(), 0);
    matrix.addPainter(new Painter("test"));
    checkExceptionContains("name", new Runnable() {
      public void run() {
        matrix.addPainter(new Painter("test"));        
      }
    });
  }
  
  @Test
  public void setPainterDuplicated() throws Exception {
    final Matrix matrix = new Matrix(new Shell(), 0);
    matrix.addPainter(new Painter("test"));
    try {
      matrix.setPainter(0, new Painter("test"));              
      fail("Expected " + IllegalArgumentException.class.getName());
    } catch (IllegalArgumentException e) {
      assertEquals("The receiver already has a painter with name: test", e.getMessage());
    }
  }
  
  @Test 
  public void setPainterDuplicated2() throws Exception {
    final Matrix matrix = new Matrix(new Shell(), 0);
    matrix.addPainter(new Painter("test"));
    int i = matrix.indexOfPainter("test");
    matrix.setPainter(i, new Painter("test"));        
  }
  
  
  
  
  static void checkExceptionContains(String s, Runnable r) {
    try {
      r.run();
      fail("Expected " + IllegalArgumentException.class.getName());
    } catch (IllegalArgumentException e) {
      assertTrue(e.getMessage().contains(s));
    }
  }
}


