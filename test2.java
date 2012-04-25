/**
   * Returns <code>true</code> if the cell at given indexes is selected.
   * Otherwise, <code>false</code> is returned.
   * <p>
   * <code>index0</code> and <code>index1</code> refer to the model, 
   * not the visual position of the item on the screen
   * which can be altered by move and hide operations. 
   * 
   * @param indexX column index of the cell 
   * @param indexY row index of the cell  
   * @return the selection state of the specified cell
   * 
   * @throws IllegalArgumentException if <code>index0</code> or 
   *         <code>index1</code> is null.
   * @throws IndexOutOfBoundsException if <code>index0</code> is out of 
   *         0 ... this.getSection0().getCount() bounds
   * @throws IndexOutOfBoundsException if <code>index1</code> is out of 
   *         0 ... this.getSection1().getCount() bounds
   */
  @Override public boolean isSelected(X indexX, Y indexY) {
	return cellSelection.contains(indexX, indexY);
  }