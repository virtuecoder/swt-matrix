package pl.netanel.swt.matrix;

/**
 * Contains an axis item's width and a calculated distance 
 * from the beginning of the viewport.
 * 
 * @author Jacek Kolodziejczyk created 25-03-2011
 */
public class Bound {
	public int distance, width;

	public Bound() {}
	public Bound(int distance, int width) {
		this.distance = distance;
		this.width = width;
	}
	
	@Override
	public String toString() {
		return "[" + distance + ", " + width + "]";
	}
	
	public Bound copy() {
		return new Bound(distance, width);
	}
}