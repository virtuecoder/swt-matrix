package usecase.databinding;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import pl.netanel.swt.matrix.Matrix;

public class ItemData {
  public String headerText;
  public int width = -1;
  public boolean autoWidth;
  public int textAlign;
  
  public ItemData(String name, int align) {
    headerText = name;
    textAlign = align;
  }

  public ItemData(String name, int align, int width) {
    headerText = name;
    textAlign = align;
    this.width = width;    
  }

  /**
   * Creates a control to edit the value of the specified cell.
   * <p> 
   * It creates a {@link Text} control by default. 
   * The method should return null to make the cell read only. 
   * <p>
   * The <code>parent</code> argument is always the parent of {@link Matrix} component. 
   * It is passed explicitly to discourage creating edit controls 
   * as children of {@link Matrix} which would block the matrix 
   * from receiving focus and thus any key or mouse events.
   * @param indexX cell index on the horizontal axis 
   * @param indexY cell index on the vertical axis 
   * @param parent composite to create the control in
   *  
   * @return control to edit the value of the specified cell
   * @see #createControl(Number, Number)
   */
  protected Control createControl(Composite parent) {
    return new Text(parent, SWT.BORDER);
  }
  
  /**
   * Disposes the edit control. This method can be overridden to implement 
   * the control hiding instead of disposing, making also the {@link #createControl(Number, Number)}
   * method to show the control instead of creating a new one in that case.  
   * 
   * @param control
   * @see #createControl(Number, Number)
   */
  protected void disposeControl(Control control) {
    control.dispose();
  }
  
}
