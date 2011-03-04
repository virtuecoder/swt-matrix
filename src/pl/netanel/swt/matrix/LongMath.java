package pl.netanel.swt.matrix.model;

class LongMath extends NumberMath<Long> {

	private static LongMath instance = new LongMath();

	public static LongMath getInstance() {
		return instance;
	}

}
