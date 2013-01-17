/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * Iterates over items of axis, according to order of sections and items,
 * skipping the ones hidden or collapsed in three.
 * <p>
 * Subclasses differentiate forward and backward iterations.
 */
abstract class AxisSequence<N extends Number> implements Sequence {

  protected final Math<N> math;

  SectionCore<N> section;
  MutableExtent<N> orderExtent, hiddenExtent, buriedExtent;
  final MutableNumber<N> index;
  N lastIndex;
  int sectionIndex, lastSectionIndex, orderIndex;
  protected List<SectionCore<N>> sections;
  AxisItem<N> freeze, min, start;

  protected boolean moved;

  public AxisSequence(List<SectionCore<N>> sections) {
    this.sections = sections;
    lastSectionIndex = sections.size() - 1;
    math = sections.get(0).math;
    index = math.create(0);
    rewind();
  }

  public void setStart(AxisItem<N> item) {
    start = item;
  }

  public boolean init(AxisItem<N> item) {
    start = item;
    init();
    return item.section.equals(section) && math.compare(item.index, index.getValue()) == 0;
  }

  @Override
  public void init() {
    // Set section
//    sectionIndex = start.section.index;
//    section = sections.get(sectionIndex);
    int oldSectionIndex = start.section.index;
    sectionIndex = previous(start.section.index);
    nextSection();

    if (oldSectionIndex == sectionIndex) {
      // Set extents
      orderIndex = section.order.getExtentIndex(start.index);
      if (orderIndex == -1) {
        orderExtent = new MutableExtent<N>(math.create(1), math.create(-1));
        orderIndex = 0;
        index.set(math.ZERO_VALUE()).decrement();
      } else {
        orderExtent = section.order.items.get(orderIndex);
        index.set(start.index);
        previousIndex();
      }
    }
    else {
      orderIndex = first(section.order.items);
      if (orderIndex == -1) {
        orderExtent = new MutableExtent<N>(math.create(1), math.create(-1));
        orderIndex = 0;
        index.set(math.ZERO_VALUE()).decrement();
      } else {
        orderExtent = section.order.items.get(orderIndex);
        index.set(first(orderExtent));
        previousIndex();
      }
    }
    moved = false;
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
          if (!nextSection()) return false;
          orderIndex = first(section.order.items);
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
    moved = true;
    return true;
  }

  public AxisItem<N> seek(AxisItem<N> item) {
    init(item);
    init();
    next();
    return getItem();
  }

  public AxisItem<N> first() {
    rewind();
    init();
    return next() ? getItem() : null;
  }

  public AxisItem<N> getItem() {
    return moved ? AxisItem.createInternal(section, index.getValue()) : null;
  }

  public N getIndex() {
    return moved ? index.getValue() : null;
  }

  public AxisItem<N> nextItem() {
    if (!next()) return null;
    return getItem();
  }

  public AxisItem<N> nextItem(AxisItem<N> item) {
    init(item);
    if (!next()) return null;
    return getItem();
  }

  public AxisItem<N> nextItem(MutableNumber<N> count) {
    MutableNumber<N> counter = math.create(0);
    while (next()) {
      counter.increment();
      if (math.compare(counter, count) >= 0) break;
    }
    return math.compare(counter, math.ZERO_VALUE()) > 0 ? getItem() : null;
  }

  public boolean setHasMore(AxisItem<N> item) {
    init(item);
    return next();
  }

  /**
   * Sets the start to the first number in order.
   */
  public abstract void rewind();
  protected abstract void previousIndex();
  protected abstract void nextIndex();
  protected abstract int first(List<MutableExtent<N>> list);
  protected abstract N first(MutableExtent<N> extent);
  protected abstract N last(MutableExtent<N> extent);
  protected abstract int next(int i);
  protected abstract int previous(int i);
  protected abstract boolean beyond(MutableExtent<N> extent);
  protected abstract boolean beyond(List<?> list, int i);


  protected boolean isSectionToSkip() {
    return !section.isVisible() || section.isEmpty();
  }

  protected boolean nextSection() {
    int oldSectionIndex = sectionIndex;
    while (true) {
      sectionIndex = next(sectionIndex);
      if (beyond(sections, sectionIndex)) {
        previousIndex();
        if (0 <= oldSectionIndex && oldSectionIndex < sections.size()) {
          sectionIndex = oldSectionIndex;
          section = sections.get(sectionIndex);
        }
        return false;
      }
      section = sections.get(sectionIndex);
      if (!isSectionToSkip()) {
        return true;
      }
    }
  }


  protected boolean skip(NumberSetCore<N> set) {
    int i = set.getExtentIndex(index.getValue());
    if (i != -1) {
      index.set(last(set.items.get(i)));
      return true;
    }
    return false;
  }



  // Subclasses ---------------------------------------------------------------

  public static class Forward<N extends Number> extends AxisSequence<N> {
    public Forward(List<SectionCore<N>> sections) {
      super(sections);
    }

    @Override
    public void rewind() {
      section = sections.get(0);
      ArrayList<MutableExtent<N>> items = section.order.items;
      start = AxisItem.createInternal(section, math.compare(section.count, math.ZERO_VALUE()) > 0 ?
          items.get(0).start.getValue() : math.ZERO_VALUE());
    }

    @Override
    protected void previousIndex() {
       index.decrement();
    }

    @Override
    protected void nextIndex() {
      index.increment();
    }

    @Override
    protected int next(int i) {
      return i+1;
    }

    @Override
    protected int previous(int i) {
      return i-1;
    }

    @Override
    protected boolean beyond(MutableExtent<N> extent) {
      return math.compare(extent.end, index) < 0;
    }

    @Override
    protected boolean beyond(List<?> list, int i) {
      return i >= list.size();
    }

    @Override
    protected int first(List<MutableExtent<N>> list) {
      return list.isEmpty() ? -1 : 0;
    }

    @Override
    protected N first(MutableExtent<N> extent) {
      return extent.start.getValue();
    }

    @Override
    protected N last(MutableExtent<N> extent) {
      return extent.end.getValue();
    }
  }

  public static class Backward<N extends Number> extends AxisSequence<N> {
    public Backward(List<SectionCore<N>> sections) {
      super(sections);
    }

    @Override
    public void rewind() {
      section = sections.get(lastSectionIndex);
      ArrayList<MutableExtent<N>> items = section.order.items;
      start = AxisItem.createInternal(section, math.compare(section.count, math.ZERO_VALUE()) > 0 ?
          items.get(items.size()-1).end.getValue() : math.ZERO_VALUE());
    }

    @Override
    protected void previousIndex() {
      index.increment();
    }

    @Override
    protected void nextIndex() {
      index.decrement();
    }

    @Override
    protected int next(int i) {
      return i-1;
    }

    @Override
    protected int previous(int i) {
      return i+1;
    }

    @Override
    protected boolean beyond(MutableExtent<N> extent) {
      return math.compare(extent.start, index) > 0;
    }

    @Override
    protected boolean beyond(List<?> list, int i) {
      return i < 0;
    }

    @Override
    protected int first(List<MutableExtent<N>> list) {
      return list.size()-1;
    }

    @Override
    protected N first(MutableExtent<N> extent) {
      return extent.end.getValue();
    }

    @Override
    protected N last(MutableExtent<N> extent) {
      return extent.start.getValue();
    }
  }
}
