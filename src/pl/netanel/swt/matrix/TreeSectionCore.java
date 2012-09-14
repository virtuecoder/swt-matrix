package pl.netanel.swt.matrix;

import java.util.Iterator;

public abstract class TreeSectionCore<N extends Number> extends SectionCore<N> {
  private final NumberSet<N> expanded;
  private final NumberSet<N> buried;

  public TreeSectionCore(Class<N> numberClass) {
    super(numberClass);

    expanded = new NumberSet<N>(math);
    buried = new NumberSet<N>(math);
  }

  /**
   * Can return null
   * @param parent
   * @return
   */
  public abstract Iterator<Extent<N>> getChildrenExtents(N parent);
  public abstract Iterator<N> getChildren(N parent);
  public abstract N getChildrenCount(N parent);
  public abstract N getParent(N index);

  public N getLevelInTree(N index) {
    MutableNumber<N> count = math.create(-1);
    N parent = index;
    do {
      parent = getParent(parent);
      count.increment();
    }
    while (parent != null);

    return count.getValue();
  }

  public void setExpanded(N parent, boolean state) {
    if (parent == null) {
      for (Iterator<Extent<N>> it = getChildrenExtents(null); it.hasNext();) {
        Extent<N> extent = it.next();
        setExpanded(extent.start, extent.end, state);
      }
    }
    else {
      setExpanded(parent, parent, state);
    }
  }

  public void setExpanded(N start, N end, boolean state) {
    if (state == true) {
      expanded.add(start, end);
    }
    else {
      expanded.remove(start, end);
    }

    collapse(start, end);
  }

  private void collapse(N start, N end) {
    MutableNumber<N> index = math.create(start);
    for (; math.compare(index, end) <= 0; index.increment()) {
      N parent = index.getValue();
      boolean isExpanded = expanded.contains(parent);
      boolean isBurried = buried.contains(parent);
      for (Iterator<Extent<N>> it = getChildrenExtents(parent); it.hasNext();) {
        Extent<N> extent = it.next();
        if (isExpanded && !isBurried) {
            buried.remove(extent.start, extent.end);
          }
        else {
          buried.add(extent.start, extent.end);
        }
        collapse(extent.start, extent.end);
      }
    }
  }

  public boolean isExpanded(N index) {
    return expanded.contains(index);
  }

  boolean isBuried(N index) {
    return buried.contains(index);
  }
}
