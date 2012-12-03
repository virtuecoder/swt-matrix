/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import java.util.List;

class SpanSequence<N extends Number> extends AxisSequence.Forward<N> {

  private MutableNumber<N> remain;
  private N count;

  public SpanSequence(List<SectionCore<N>> sections) {
    super(sections);
    remain = math.create(0);
  }

  public void set(AxisItem<N> item, N count) {
    setStart(item);
    this.count = count;
  }

  @Override
  public void init() {
    super.init();
    remain.set(count);
  }

  @Override
  public boolean next() {
    // Iterate until next correct index is found
    while (true) {
      nextIndex();

      if (beyond(orderExtent)) {
        orderIndex = next(orderIndex);

        // If order extents finished then next section
        if (beyond(section.order.items, orderIndex)) {
          return false;
        }
        orderExtent = section.order.items.get(orderIndex);
        index.set(first(orderExtent));
      }

      // Skip hidden or buried
      if (skip(section.hidden) || skip(section.buried)) {
        continue;
      }
      break;
    }

    if (math.compare(remain.decrement(), math.ZERO()) < 0) return false;

    moved = true;
    return true;
  }

  @Override
  protected boolean skip(NumberSetCore<N> set) {
    int i = set.getExtentIndex(index.getValue());
    if (i != -1) {
      N last = last(set.items.get(i));
      remain.subtract(last).add(index).decrement();
      index.set(last);
      return true;
    }
    return false;
  }

}
