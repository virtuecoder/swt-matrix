package usecase.databinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import pl.netanel.swt.matrix.Matrix;
import pl.netanel.swt.matrix.Painter;
import pl.netanel.swt.matrix.Section;
import pl.netanel.swt.matrix.ZoneEditor;

public class ListData<T> {
  
  private final int columnCount; 
  protected List<T> list;
  public Map<Integer, ItemData> itemsX;
  public Map<T, ItemData> itemsY;
  public List<CellDataEntry>cells;
  public DateFormat dateFormat = SimpleDateFormat.getDateInstance();

  
  public ListData(List<T> list, int columnCount) {
    this.list = list;
    this.columnCount = columnCount;
    this.itemsX = new HashMap<Integer, ItemData>(columnCount);
    this.itemsY = new HashMap<T, ItemData>();
    this.cells = new ArrayList<CellDataEntry>();
  }

  private void setOutput(final Matrix<Integer, Integer> matrix) {
    matrix.getAxisX().getBody().setCount(columnCount);
    matrix.getAxisY().getBody().setCount(list.size());
    
    matrix.getBody().replacePainter(
      new Painter<Integer, Integer>(Painter.NAME_CELLS, Painter.SCOPE_CELLS) {
        @Override public String getText(Integer indexX, Integer indexY) {
          Object value = getModelValue(indexX, indexY);
          return value == null || value instanceof Boolean ? "" : 
            value instanceof Date ? dateFormat.format(value) :
              value.toString(); 
        }
    });
    
    matrix.getHeaderX().replacePainter(
      new Painter<Integer, Integer>(Painter.NAME_CELLS, Painter.SCOPE_CELLS) {
        @Override public String getText(Integer indexX, Integer indexY) {
          ItemData itemX = itemsX.get(indexX);
          return itemX == null ? null : itemX.headerText; 
        }
      });
    
    new ZoneEditor<Integer, Integer>(matrix.getBody()) {
      @Override protected Object getModelValue(Integer indexX, Integer indexY) {
        return ListData.this.getModelValue(indexX, indexY);
      }
      @Override protected void setModelValue(Integer indexX, Integer indexY, Object value) {
        ListData.this.setModelValue(indexX, indexY, value);
      }
      @Override protected Control createControl(Integer indexX, Integer indexY) {
        Class<?> type = getDataType(indexX, indexY);
        if (type == String.class) return new Text(matrix, SWT.BORDER); 
        if (type == Boolean.class) return new Button(matrix, SWT.CHECK); 
        if (type == Date.class) return new DateTime(matrix, SWT.BORDER); 
        if (type == List.class) {
          Combo combo = new Combo(matrix, SWT.BORDER);
          CellData itemcellData = getCellData(indexX, indexY);
          if (itemcellData != null && itemcellData.values != null) {
            combo.setItems(itemcellData.values);
          }
          return combo; 
        }
        return null;
      }
      @Override protected boolean hasEmbeddedControl(Integer indexX, Integer indexY) {
        return getDataType(indexX, indexY) == Boolean.class;
      }
    };
    
    Section<Integer> bodyX = matrix.getAxisX().getBody();
    for (Entry<Integer, ItemData> entry: itemsX.entrySet()) {
      int width = entry.getValue().width;
      if (width >= 0) {
        bodyX.setCellWidth(entry.getKey(), width);
      }
    }
  }

  public void putCellData(Integer indexX, Integer indexY, CellData cellData) {
    cells.add(0, new CellDataEntry(new Integer[] {indexX, indexY}, cellData));
  }
  
  public CellData getCellData(Integer indexX, Integer indexY) {
    for (CellDataEntry entry: cells) {
      if (entry.index[0] == indexX || entry.index[1] == indexY) {
        return entry.cellData;
      }
    }
    return null;
  }
  
  public Object getModelValue(Integer indexX, Integer indexY) {
    return null;
  }
  
  public void setModelValue(Integer indexX, Integer indexY, Object value) { }
  
  public Class<?> getDataType(Integer indexX, Integer indexY) {
    return String.class;
  }
  
  public Control createEditorControl(Integer indexX, Integer indexY, Composite parent) {
    return new Text(parent, SWT.BORDER);
  }
  
  static class CellDataEntry {
    Integer[] index;
    CellData cellData;
    public CellDataEntry(Integer[] index, CellData cellData) {
      this.index = index;
      this.cellData = cellData;
    }
  }
  
  
  public static void main(String[] args) {
    Shell shell = new Shell();
    shell.setLayout(new FillLayout());
    shell.setBounds(400, 300, 600, 400);
    
    // Matrix
    Matrix<Integer, Integer> matrix = new Matrix<Integer, Integer>(shell, SWT.None);
    matrix.getAxisY().getHeader().setVisible(true);
    
    // Data source
    Person person1 = new Person("John", new Date(), 'M', false);
    Person person2 = new Person("Mary", new Date(), 'F', true);
    List<Person> persons = new ArrayList<Person>();
    persons.add(person1);
    persons.add(person2);
    
    // Data binder
    ListData<Person> data = new ListData<Person>(persons, 4) {
      @Override public Object getModelValue(Integer indexX, Integer indexY) {
        Person person = list.get(indexY);
        return 
          indexX == 0 ? person.name :
          indexX == 1 ? person.dateOfBirth :
          indexX == 2 ? person.gender == 'M' ? "Male" : "Female":
          indexX == 3 ? person.hasDog : null;
      }
      @Override public void setModelValue(Integer indexX, Integer indexY, Object value) {
        Person person = list.get(indexY);
        switch (indexX) {
        case 0 : person.name = (String) value; break;
        case 1 : person.dateOfBirth = (Date) value; break;
        case 2 : person.gender = ((String) value).charAt(0); break;
        case 3 : person.hasDog = (Boolean) value; break;
        }
      }
      @Override public Class<?> getDataType(Integer indexX, Integer indexY) {
        return 
          indexX == 0 ? String.class:
          indexX == 1 ? Date.class :
          indexX == 2 ? List.class :
          indexX == 3 ? Boolean.class : null;
      }
    };
    
    // Set data for columns
    data.itemsX.put(0, new ItemData("Name", SWT.LEFT, 150));
    data.itemsX.put(1, new ItemData("Date of Birth", SWT.LEFT));
    data.itemsX.put(2, new ItemData("Gender", SWT.CENTER));
    data.itemsX.put(3, new ItemData("Has a Dog?", SWT.CENTER));

    // Attach cell data to the third column
    CellData cellData = new CellData();
    cellData.values = new String[] { "Male", "Female"};
    data.putCellData(2, null, cellData);
    
    
    // Render
    data.setOutput(matrix);
    
    // Other configuration
    Section<Integer> bodyX = matrix.getAxisX().getBody();
    bodyX.setCellWidth(1);
    bodyX.setCellWidth(2);
    bodyX.setCellWidth(3);
    
    shell.open();
    Display display = shell.getDisplay();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }
}

class Person {
  String name;
  Date dateOfBirth;
  char gender;
  boolean hasDog;
  public Person(String name, Date dateOfBirth, char gender, boolean hasDog) {
    super();
    this.name = name;
    this.dateOfBirth = dateOfBirth;
    this.gender = gender;
    this.hasDog = hasDog;
  }
}
