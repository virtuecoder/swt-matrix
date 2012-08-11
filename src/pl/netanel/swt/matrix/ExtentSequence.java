/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import static pl.netanel.swt.matrix.Math.*;

import java.util.ArrayList;
import java.util.List;

class ExtentSequence<N extends Number> implements Sequence {
	protected final List<MutableExtent<N>> items;
	protected int i;

	N start, end;
  MutableExtent<N> e;

	public ExtentSequence(List<MutableExtent<N>> items) {
		this.items = items;
	}

  @Override
	public void init() {
		i = -1;
	}

	@Override
	public boolean next() {
		if (++i >= items.size() ) return false;
		e = items.get(i);
		start = e.start();
		end = e.end();
		return true;
	}

  public static <N extends Number> ExtentSequence<N> difference(final NumberSet<N> set, final NumberSet<N> set2) {
    return new ExtentSequence<N>(set.items) {
      private ArrayList<MutableExtent<N>> items2;
      private MutableExtent<N> e2;
      private boolean overlapped;

      @Override
      public void init() {
        super.init();
        items2 = set2.items;
        e2 = items2.isEmpty() ? null : items2.get(0);
      }

      @Override
      public boolean next() {
        if (overlapped) {
          start = set.math.increment(e2.end.getValue());
          end = e2.end.getValue();
        }
        overlapped = true;
        begin: {
          switch (set.math.compare(e, e2)) {
          case EQUAL: case INSIDE:
            if (!super.next()) return false;
            break begin;

          case OVERLAP:
            end = set.math.decrement(e2.start.getValue());
            overlapped = true;
          }
        }

        return super.next();
      }
    };
  }

}
