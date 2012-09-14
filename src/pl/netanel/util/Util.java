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

	/**
	 * Null safe conversion to string using standard {@link Object#toString()} method.
	 * @param o object to convert
	 * @return string representation of object
	 */
	public static String toString(Object o) {
	  if (o == null) return "";
	  return o.toString();
	}


  /**
   * Substitutes each {@code %s} in {@code template} with an argument. These
   * are matched by position - the first {@code %s} gets {@code args[0]}, etc.
   * If there are more arguments than placeholders, the unmatched arguments will
   * be appended to the end of the formatted message in square braces.
   *
   * @param template a non-null string containing 0 or more {@code %s}
   *     placeholders.
   * @param args the arguments to be substituted into the message
   *     template. Arguments are converted to strings using
   *     {@link String#valueOf(Object)}. Arguments can be null.
   */
  public static String format(String template, Object... args) {
    // start substituting the arguments into the '%s' place holders
    StringBuilder builder = new StringBuilder(
        template.length() + 16 * args.length);
    int templateStart = 0;
    int i = 0;
    while (i < args.length) {
      int placeholderStart = template.indexOf("%s", templateStart);
      if (placeholderStart == -1) {
        break;
      }
      builder.append(template.substring(templateStart, placeholderStart));
      builder.append(args[i++]);
      templateStart = placeholderStart + 2;
    }
    builder.append(template.substring(templateStart));

    // if we run out of placeholders, append the extra args in square braces
    if (i < args.length) {
      builder.append(" [");
      builder.append(args[i++]);
      while (i < args.length) {
        builder.append(", ");
        builder.append(args[i++]);
      }
      builder.append("]");
    }

    return builder.toString();
  }
}
