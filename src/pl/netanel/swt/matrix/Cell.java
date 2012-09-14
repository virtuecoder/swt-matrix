/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

/**
 * Pair of indexes representing cell coordinates.
 * Instances of this class are immutable.
 *
 * @param <X> indexing type for horizontal axis
 * @param <Y> indexing type for vertical axis
 */
public class Cell<X extends Number, Y extends Number> {
  final X indexX;
  final Y indexY;

  /**
   * Creates a new instance of cell. Arguments are validated.
   *
   * @param indexX cell index on the horizontal axis
   * @param indexY cell index on the vertical axis
   * @return a new instance of this class
   *
   * @throws IllegalArgumentException if indexX or indexY is <code>null</code>
   * @throws IndexOutOfBoundsException if indexX or IndexY is negative
   */
  public static <X extends Number, Y extends Number> Cell<X, Y> create(X indexX, Y indexY) {
    Math.checkIndexStatic(indexX, "indexX");
    Math.checkIndexStatic(indexY, "indexY");
    return new Cell<X, Y>(indexX, indexY);
  }

  /**
   * Creates a new instance of cell without checking arguments validity.
   *
   * @param indexX cell index on the horizontal axis
   * @param indexY cell index on the vertical axis
   * @return a new instance of this class
   */
  public static <X extends Number, Y extends Number> Cell<X, Y> createUnchecked(X indexX, Y indexY) {
    return new Cell<X, Y>(indexX, indexY);
  }

  private Cell(X indexX, Y indexY) {
    this.indexX = indexX;
    this.indexY = indexY;
  }

  /**
   * Returns cell index on the horizontal axis.
   * @return cell index on the horizontal axis
   */
  public X getIndexX() {
    return indexX;
  }
  /**
   * Returns cell index on the vertical axis.
   * @return cell index on the vertical axis
   */
  public Y getIndexY() {
    return indexY;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((indexX == null) ? 0 : indexX.hashCode());
    result = prime * result + ((indexY == null) ? 0 : indexY.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) { return true; }
    if (obj == null) { return false; }
    if (!(obj instanceof Cell)) { return false; }
    @SuppressWarnings("rawtypes")
    Cell other = (Cell) obj;
    if (other.indexX.getClass() != indexX.getClass()) return false;
    if (other.indexY.getClass() != indexY.getClass()) return false;
    if (!indexX.equals(other.indexX)) { return false; }
    else if (!indexY.equals(other.indexY)) { return false; }
    return true;
  }

  @Override
  public String toString() {
    return "[" + indexX + ", " + indexY + "]";
  }



}
