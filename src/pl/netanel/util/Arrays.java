package pl.netanel.util;


/**
 * Arrays utilities.
 *
 * @author jacek.p.kolodziejczyk@gmail.com
 * @created Dec 28, 2009
 */
public class Arrays {
	
	/**
	 * Removes item at given index from the given array of Objects.
	 * 
	 * @param src array from which to remove
	 * @param index of the item to remove
	 * @return copy of source array, but without the removed item
	 */
	public static Object[] remove(Object[] src, int index) {
		Object[] dest = new Object[src.length - 1];
		if (index > 0)
			System.arraycopy(src, 0, dest, 0, index);
		if (index < src.length - 1)
			System.arraycopy(src, index+1, dest, index, dest.length - index);
		return dest;
	}
	
	/**
	 * Inserts value at given index into the array of objects.
	 * 
	 * @param src array to which to insert
	 * @param index of the item to insert
	 * @return copy of source array, but with the added item
	 */
	public static Object[] insert(Object[] src, int index, Object value) {
		Object[] dest = new Object[src.length + 1];
		if (index > 0)
			System.arraycopy(src, 0, dest, 0, index);
		if (index < src.length)
			System.arraycopy(src, index, dest, index+1, dest.length - index - 1);
		dest[index] = value;
		return dest;
	}
	
	/**
	 * Inserts value at given index into the array of strings.
	 * 
	 * @param src array to which to insert
	 * @param index of the item to insert
	 * @return copy of source array, but with the added item
	 */
	public static String[] insert(String[] src, int index, String value) {
		String[] dest = new String[src.length + 1];
		if (index > 0)
			System.arraycopy(src, 0, dest, 0, index);
		if (index < src.length)
			System.arraycopy(src, index, dest, index+1, dest.length - index - 1);
		dest[index] = value;
		return dest;
	}
	
	/** Inserts value at given index into the array of strings.
	* 
	* @param src array to which to insert
	* @param index of the item to insert
	* @return copy of source array, but with the added item
	*/
	public static int[] insert(int[] src, int index, int value) {
		int[] dest = new int[src.length + 1];
		if (index > 0)
			System.arraycopy(src, 0, dest, 0, index);
		if (index < src.length)
			System.arraycopy(src, index, dest, index+1, dest.length - index - 1);
		dest[index] = value;
		return dest;
	}
	
	/**
	 * Inserts value at given index into the array of classes.
	 * 
	 * @param src array to which to insert
	 * @param index of the item to insert
	 * @return copy of source array, but with the added item
	 */
	public static Class[] insert(Class[] src, int index, Class value) {
		Class[] dest = new Class[src.length + 1];
		if (index > 0)
			System.arraycopy(src, 0, dest, 0, index);
		if (index < src.length)
			System.arraycopy(src, index, dest, index+1, dest.length - index - 1);
		dest[index] = value;
		return dest;
	}

	public static boolean contains(int[] a, int value) {
		for (int i = 0; i < a.length; i++) {
			if (a[i] == value) return true;
		}
		return false;
	}
	
	public static boolean contains(String[] a, String value) {
		for (int i = 0; i < a.length; i++) {
			if (a[i].equals(value)) return true;
		}
		return false;
	}

	public static <T> int indexOf(T[] a, T  item) {
		for (int i = 0; i < a.length; i++) {
			if (a[i].equals(item)) {
				return i;
			}
		}
		return -1;
	}
}
