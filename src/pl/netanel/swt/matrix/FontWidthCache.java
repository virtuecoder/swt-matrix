package pl.netanel.swt.matrix;

import java.util.HashMap;

import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

class FontWidthCache {
	public static final int[] PRINTABLE_CHARS = new int[] {
        1,2,4,5,6,7,11,12,14,15,16,17,18,19,20,21,22,23,24,25,26,28,29,30,
        31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,
        53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,
        75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,
        97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,
        115,116,117,118,119,120,121,122,123,124,125,126,211,243,260,261,262,
        263,280,281,321,322,323,324,346,347,377,378,379,380,8364,61440,61441};

	static HashMap<Font, int[]> cache = new HashMap<Font, int[]>();

	public static int[] get(GC gc, Font font) {
		int[] a = cache.get(font);
		if (a == null) {
			a = new int[0xffff];
			for (int ch: PRINTABLE_CHARS) {
				a[ch] = gc.stringExtent(Character.toString((char) ch)).x;
			}
			cache.put(font, a);
		}
		return a;
	}

	public static int getWidth(String s, GC gc, int[] cache) {
		int extent = 0;
		for (int i = 0; i < s.length(); i++) {
			final char ch = s.charAt(i);
			int w = cache[ch];
			if (w == 0) {
				w = gc.stringExtent(Character.toString(ch)).x;
				cache[ch] = w;
			}
			extent += w;
		}
		return extent;
	}

	// TODO unit test it!
	public static String shortenTextMiddle(String s, int width, Point extent, int[] cache) {
		if (s == null) return s;

		int len = s.length();
		int w = 0;
		int i = 0;
		while (i < len && w <= width) {
			w += cache[s.charAt(i++)];
		}
		if (i < len || w > width) {
			int dot = cache['.'];
			int dot2 = 2 * dot;
			int pivot = i / 2;
			int pos1 = pivot;
			int pos2 = len - pivot;
			int len1 = getExtent(s, cache, 0, pos1);
			int len2 = getExtent(s, cache, pos2, len);
			int last = len - 1;
			while (pos1 > 0 && pos2 < last) {
				if ((w = len1 + dot2 + len2) <= width) break;
				else if (pos1 <= 1 && (w = len1 + dot2) <= width) {
					pos2 = len; break;
				}
				int w2 = cache[s.charAt(--pos1)];
        len1 -= w2;
				if (w - w2 > width) {
				  len2 -= cache[s.charAt(++pos2)];
				}
			}
			if (w <= width) {
				s = s.substring(0, pos1) + ".." + s.substring(pos2, len);
			}
			else {
				w = dot2 <= width ? dot2 : dot <= width ? dot : 0;
				s = dot2 <= width ? ".." : dot <= width ? "." : "";
			}
		}
		extent.x = w;
		return s;
	}

	public static String shortenTextMiddle(String s, int width, int lineCount, Point extent, int[] cache) {
	  if (s == null) return s;

	  int len = s.length();
	  boolean isEven = lineCount % 2 == 0;
	  int dotLine = (lineCount - 1) / 2;
	  StringBuilder sb = new StringBuilder();

	  // Fill lines before dot line
	  int iBefore = 0;
	  for (int line = 0; line < dotLine; line++) {
	    int w = 0;
	    while (iBefore < len) {
	      char c = s.charAt(iBefore);
	      int w2 = w + cache[c];
	      if (w2 > width) break;
        w = w2;
        iBefore++;
	    }
    }

	  // Fill lines after dot line
	  int iAfter = s.length()-1;
    for (int line = lineCount-1; line > dotLine; line--) {
      int w = 0;
      while (iAfter > iBefore) {
        char c = s.charAt(iAfter);
        int w2 = w + cache[c];
        if (w2 > width) break;
        w = w2;
        iAfter--;
      }
    }

    if (iBefore < iAfter) {
      iAfter++;
      sb.append(s.substring(0, iBefore));

      if (isEven) {
        sb.append(shortenTextEnd(s.substring(iBefore, iAfter), width, extent, cache));
      }
      else {
        sb.append(shortenTextMiddle(s.substring(iBefore, iAfter), width, extent, cache));
      }

      sb.append(s.substring(iAfter));
      return sb.toString();
    }
    else {
      return s;
    }
	}

	public static String shortenTextEnd(String s, int width, Point extent, int[] cache) {
		int len = s.length();
		if (len < 2) return s;
		int w = 0;
		int i = 0;
		while (i < len && w <= width) {
			w += cache[s.charAt(i++)];
		}
		if (i < len || w > width) {
		  int dot = cache['.'];
      int dot2 = 2 * dot;
      int w2 = 0;
      while(i-- >= 0) {
        w2 += cache[s.charAt(i)];
        if (w2 >= dot2) break;
      }
      w -= w2;

      s = s.substring(0, i) + (
//          w + dot3 <= width ? "..." :
          w + dot2 <= width ? ".." :
          w + dot <= width ? "." : "");
		}

		extent.x = w;
		return s;
	}

	private static int getExtent(String s, int[] cache, int pos1, int pos2) {
		int extent = 0;
		for (int i = pos1; i < pos2; i++) {
			extent += cache[s.charAt(i)];
		}
		return extent;
	}

}
