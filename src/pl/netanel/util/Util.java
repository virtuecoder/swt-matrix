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
}
