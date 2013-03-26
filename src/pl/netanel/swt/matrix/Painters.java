/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import pl.netanel.util.Arrays;
import pl.netanel.util.Preconditions;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Manages a collection of painters ensuring uniqueness of name.
 * Q: can one painter be assigned to many collections. A: theoretically it should be possible.
 *
 * @param <X>
 * @param <Y>
 */
class Painters<X extends Number, Y extends Number> implements Iterable<Painter<X, Y>> {

  private static final int[] CELL_PAINTERS = new int[]{Painter.SCOPE_CELLS, Painter.SCOPE_CELLS_X, Painter.SCOPE_CELLS_Y};

  private final ArrayList<Painter<X, Y>> items;

  public Painters() {
    items = new ArrayList<Painter<X, Y>>();
  }

  public void add( Painter<X, Y> painter ) {
    Preconditions.checkNotNullWithName(painter, "painter");
    checkUniqueness(painter);
    items.add(painter);
  }

  public void add( int index, Painter<X, Y> painter ) {
    Preconditions.checkNotNullWithName(painter, "painter");
    checkUniqueness(painter);
    items.add(index, painter);
  }

  public void set( int index, Painter<X, Y> painter ) {
    Preconditions.checkPositionIndex(index, items.size());
    Preconditions.checkNotNullWithName(painter, "painter");
    checkUniqueness(painter, index);
    items.set(index, painter);
  }

  public void replacePainter( Painter<X, Y> painter ) {
    Preconditions.checkNotNullWithName(painter, "painter");
    int i = indexOf(painter.name);
    checkUniqueness(painter, i);
    if (i == -1) {
      items.add(painter);
    } else {
      items.set(i, painter);
    }
  }

  public void replacePainterPreserveStyle( Painter<X, Y> painter ) {
    Preconditions.checkNotNullWithName(painter, "painter");
    int i = indexOf(painter.name);
    checkUniqueness(painter, i);
    if (i == -1) {
      items.add(painter);
    } else {
      Painter<X, Y> painter2 = get(i);
      items.set(i, painter);
      painter.style = painter2.style;
    }
  }

  public boolean contains( String name ) {
    return indexOf(name) != -1;
  }

  public int indexOf( String name ) {
    Preconditions.checkNotNullWithName(name, "name");
    for (int i = 0, imax = items.size(); i < imax; i++) {
      if (items.get(i).name.equals(name)) {
        return i;
      }
    }
    return -1;
  }

  public Painter<X, Y> get( String name ) {
    int i = indexOf(name);
    if (i == -1) throw Preconditions.illegalArgument("The receiver does not have a painter with name: '%s'", name);
    return items.get(i);
  }

  public Painter<X, Y> remove( int index ) {
    Preconditions.checkPositionIndex(index, items.size());
    return items.remove(index);
  }

  public Painter<X, Y> remove( String name) {
    int i = indexOf(name);
    if (i == -1) throw Preconditions.illegalArgument("The receiver does not have a painter with name: '%s'", name);
    return items.remove(i);
  }

  public boolean remove( Painter<X, Y> painter ) {
    Preconditions.checkNotNullWithName(painter, "painter");
    return items.remove(painter);
  }

  public Painter<X, Y> get( int index ) {
    Preconditions.checkPositionIndex(index, items.size());
    return items.get(index);
  }

  public int size() {
    return items.size();
  }

  @Override
  public Iterator<Painter<X, Y>> iterator() {
    return items.iterator();
  }

  public Point computeSize( X indexX, Y indexY, int wHint, int hHint ) {
    Point result = new Point(0, 0);
    GC gc = new GC(Display.getDefault());
    for (Painter<X, Y> painter : items) {
      if (Arrays.contains(CELL_PAINTERS, painter.scope)) {
        painter.init(gc, Frozen.NONE, Frozen.NONE);
        Point size = painter.computeSize(indexX, indexY, wHint, hHint);
        result.x = java.lang.Math.max(result.x, size.x);
        result.y = java.lang.Math.max(result.y, size.y);
        painter.clean();
      }
    }
    gc.dispose();
    return result;
  }

  // Private

  private void checkUniqueness( Painter<X, Y> painter) {
    checkUniqueness(painter, -1);
  }

  private void checkUniqueness( Painter<X, Y> painter, int except ) {
    for (int i = 0, imax = items.size(); i < imax; i++) {
      if (i == except) continue;
      Painter<X, Y> painter2 = items.get(i);
      Preconditions.checkArgument(!painter2.name.equals(painter.name),
          "The receiver already has a painter with name: %s", painter.name);
    }
  }
}
