package pl.netanel.swt.matrix.state;

class IntMath extends NumberMath<MutableInt> {

	private static IntMath instance = new IntMath();

	public static IntMath getInstance() {
		return instance;
	}

	@Override
	public MutableInt create(int value) {
		return new MutableInt(value);
	}

}
