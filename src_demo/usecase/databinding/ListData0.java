package usecase.databinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.ZoneEditor;

public class ListData0<T> {
  
  private final List<T> list;
  public List<Column<?>> columns; 
  
  public ListData0(List<T> list) {
    this.list = list;
    this.columns = new ArrayList<Column<?>>();
  }

  private void show(Matrix<Integer, Integer> matrix) {
    matrix.getAxisX().getBody().setCount(columns.size());
    matrix.getAxisY().getBody().setCount(list.size());
    
    matrix.getBody().replacePainter(
      new Painter<Integer, Integer>(Painter.NAME_CELLS) {
        @Override public void setupSpatial(Integer indexX, Integer indexY){
          Column<?> column = columns.get(indexX);
          T item = list.get(indexY);
          style.textAlignX = column.align;
          text = column.getValue(item).toString(); 
        }
    });
    
    matrix.getHeaderX().replacePainter(
      new Painter<Integer, Integer>(Painter.NAME_CELLS) {
        @Override public void setupSpatial(Integer indexX, Integer indexY){
          Column<?> column = columns.get(indexX);
          style.textAlignX = column.align;
          text = column.name; 
        }
      });
    
    new ZoneEditor<Integer, Integer>(matrix.getBody()) {
      @Override
      public void setModelValue(Integer indexX, Integer indexY, Object value) {
        Column<?> column = columns.get(indexX);
        T item = list.get(indexY);
        column.setValue(item, value);
      }
    };
  }
  
  class Column<C> {
    String name;
    int align;
    public Column(String name, Class<C> dataType, int align) {
      this.name = name;
//      this.dataType = dataType;
      this.align = align;
      columns.add(this);
    }
    
    C getValue(T item) {
      return null;
    }
    void setValue(T item, Object value) {}
  }
  
  public static void main(String[] args) {
    Shell shell = new Shell();
    shell.setLayout(new FillLayout());
    shell.setBounds(400, 300, 600, 400);
    
    // Matrix
    Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.None);
    matrix.getAxisY().getHeader().setVisible(true);
    
    // Data source
    Person0 person1 = new Person0("John", new Date(), 'M', false);
    Person0 person2 = new Person0("Mary", new Date(), 'F', true);
    List<Person0> persons = new ArrayList<Person0>();
    persons.add(person1);
    persons.add(person2);
    
    // Data binder
    ListData0<Person0> data = new ListData0<Person0>(persons);

    // Columns
    data.new Column<String>("Name", String.class, SWT.LEFT) {
      @Override String getValue(Person0 item) {
        return item.name;
      }
      @Override void setValue(Person0 item, Object value) {
        item.name = (String) value;
      }
    };
    data.new Column<Date>("Date Of Birth", Date.class, SWT.LEFT) {
      @Override Date getValue(Person0 item) {
        return item.dateOfBirth;
      }
      @Override void setValue(Person0 item, Object value) {
        item.dateOfBirth = (Date) value;
      }
    };
    data.new Column<Character>("Gender", Character.class, SWT.CENTER) {
      @Override
      Character getValue(Person0 item) {
        return item.gender;
      };
      @Override void setValue(Person0 item, Object value) {
        item.gender = (Character) value;
      }
    };
    data.new Column<Boolean>("Has a Dog?", Boolean.class, SWT.CENTER) {
      @Override Boolean getValue(Person0 item) {
        return item.hasDog;
      }
      @Override void setValue(Person0 item, Object value) {
        item.hasDog = (Boolean) value;
      }
    };
    
    // Render
    data.show(matrix);
    
    shell.open();
    Display display = shell.getDisplay();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }   
}

class Person0 {
  String name;
  Date dateOfBirth;
  char gender;
  boolean hasDog;
  public Person0(String name, Date dateOfBirth, char gender, boolean hasDog) {
    super();
    this.name = name;
    this.dateOfBirth = dateOfBirth;
    this.gender = gender;
    this.hasDog = hasDog;
  }
}
