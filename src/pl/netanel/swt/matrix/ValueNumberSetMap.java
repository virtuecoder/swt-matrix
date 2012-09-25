/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


class ValueNumberSetMap<N extends Number, T> {
  private final Math<N> math;
	T defaultValue, value;
	ArrayList<T> values;
  ArrayList<NumberSet<N>> sets;

	public ValueNumberSetMap(Math<N> math, T defaultValue) {
		this.math = math;
    this.defaultValue = defaultValue;
		values = new ArrayList<T>();
		sets = new ArrayList<NumberSet<N>>();
	}

	public boolean equalValues(int i, int j) {
		return values.get(i) == values.get(j);
	}

	public T getValue(N index) {
	  int i = -1;
	  while(++i < sets.size()) {
	    if (sets.get(i).contains(index)) {
	      break;
	    }
    }
		return i == -1 ? defaultValue : values.get(i);
	}

	public void setValue(N index, T value) {
	  setValue(index, index, value);
	}

	public void setValue(MutableExtent<N> extent, T value) {
		setValue(extent.start(), extent.end(), value);
	}

	public void setValue(N from, N to, T value) {
	  // Remove if from any other value set
	  for (NumberSet<N> set2: sets) {
	    set2.remove(from, to);
	  }
	  int i = values.indexOf(value);
	  NumberSet<N> set;
	  if (i == -1) {
	    values.add(value);
      set = new NumberSet<N>(math);
      sets.add(set);
	  }
	  else {
	    set = sets.get(i);
	  }
	  set.add(from, to);
	}

	public void unsetValue(N from, N to, T value) {
	  int i = values.indexOf(value);
	  if (i != -1) {
	      sets.get(i).remove(from, to);
	  }
	}




	/**
	 * Gets the size of the values collection.
	 * For testing purposes mainly
	 * @return
	 */
	public int getCount() {
		return values.size();
	}


	public T getDefault() {
		return defaultValue;
	}

	public void setDefault(T value) {
		defaultValue = value;
	}

	public N getValueIndexCount(T value) {
	  int i = values.indexOf(value);
    if (i == -1) {
      return math.ZERO_VALUE();
    } else {
      return sets.get(i).getCount().getValue();
    }
	}

	public List<MutableExtent<N>> getValueExtents(T value) {
	  int i = values.indexOf(value);
	  if (i == -1) {
	    return Collections.emptyList();
	  } else {
	    return sets.get(i).items;
	  }
	}

  public NumberSet<N> getSet(T value) {
    int i = values.indexOf(value);
    if (i == -1) {
      return null;
    }
    else {
      return sets.get(i);
    }
  }

  public void insert(N target, N count) {
    for (NumberSet<N> set: sets) {
      set.insert(target, count);
    }
  }

  public void delete(N start, N end) {
    for (NumberSet<N> set: sets) {
      set.delete(start, end);
    }
  }

}
