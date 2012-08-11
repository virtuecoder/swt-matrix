/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/
package pl.netanel.util;

public class Util {

	/**
	 * Returns the first value that is not null
	 *
	 * @param <T> generic type
	 * @param value value to inspect
	 * @param defaltValue default value
	 * @return value if not null or default value otherwise
	 */
	public static <T> T notNull(T ...value) {
		for (int i = 0; i < value.length; i++) {
			T v = value[i];
			if (v != null) {
				return v;
			}
		}
		return null;
	}

	public static <T> T notNull(T v1, T v2) {
	  return v1 != null ? v1 : v2;
	}

	/**
	 * Null safe object equality check.
	 * Returns false if any object is null, otherwise returns <code>o1.equals(o2)</code>
	 * @param o1 first object to compare
	 * @param o2 second object to compare
	 * @return object equality
	 */
	public static boolean equals(Object o1, Object o2) {
	  if (o1 == null || o2 == null) return false;
	  return o1.equals(o2);
	}
}
