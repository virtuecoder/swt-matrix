/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import java.math.BigInteger;

class MutableInt extends MutableNumber<Integer> {
  private static final long serialVersionUID = 1L;

  int value;

	public MutableInt(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return Integer.toString(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MutableInt)) return false;
		return ((MutableInt) obj).value == value;
	}

	@Override
	public int hashCode() {
		return value;
	}

	@Override
	public MutableInt copy() {
		return new MutableInt(value);
	}

	@Override
	public Integer getValue() {
		return value;
	}

	@Override
	public MutableInt set(Integer n) {
		value = n.intValue();
		return this;
	}

	public MutableInt set(int n) {
		value = n;
		return this;
	}

	@Override
	MutableNumber<Integer> set(MutableNumber<Integer> n) {
		value = n.intValue();
		return this;
	}

	@Override
	public MutableInt increment() {
		value++;
		return this;
	}

	@Override
	public MutableInt decrement() {
		value--;
		return this;
	}

	@Override
	public MutableInt negate() {
		value = -value;
		return this;
	}

	@Override
	public MutableInt add(MutableNumber<Integer> n) {
		value += n.intValue();
		return this;
	}

	@Override
	public MutableInt subtract(MutableNumber<Integer> n) {
		value -= n.intValue();
		return this;
	}

	@Override
	public MutableInt multiply(MutableNumber<Integer> n) {
		value *= n.intValue();
		return this;
	}

	@Override
	public MutableInt divide(MutableNumber<Integer> n) {
		value /= n.intValue();
		return this;
	}


	@Override
	public MutableInt add(int n) {
		value += n;
		return this;
	}

	@Override
	public BigInteger toBigInteger() {
		return BigInteger.valueOf(value);
	}

	@Override
	MutableInt add(Integer n) {
		value += n.intValue();
		return this;
	}

	@Override
	MutableInt subtract(Integer n) {
		value -= n.intValue();
		return this;
	}

	@Override
	public int intValue() {
		return value;
	}

	@Override
	MutableNumber<Integer> min(MutableNumber<Integer> n) {
		return value <= n.intValue() ? this : n;
	}
	@Override
	MutableNumber<Integer> max(MutableNumber<Integer> n) {
	  return value >= n.intValue() ? this : n;
	}


	@Override
	int compareTo(Integer n) {
		int x = n.intValue();
		return value == x ? 0 : value > x ? 1 : -1;
	}

  @Override
  public long longValue() {
    return value;
  }

  @Override
  public float floatValue() {
    return value;
  }

  @Override
  public double doubleValue() {
    return value;
  }
}
