package pl.netanel.swt.matrix;

class Bound {
	public int distance, width;

	public Bound() {}
	public Bound(int distance, int width) {
		this.distance = distance;
		this.width = width;
	}
	
	@Override
	public String toString() {
		return "Bound [distance=" + distance + ", width=" + width + "]";
	}
}