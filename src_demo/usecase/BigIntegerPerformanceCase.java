package usecase;


import java.math.BigDecimal;
import java.math.BigInteger;

public class BigIntegerPerformanceCase {
	public static void main(String[] args) {
		final int max = 20*1000*1000;
		
		printTimeHeader();
		
		long base = printTime("int", new Runnable() {
			public void run() {
				int x = 0;
				for (int i = 0; i < max; i++) {
					x = x + 1;
				}
			}
		}, 0);
		
		printTime("integer", new Runnable() {
			public void run() {
				Integer x = Integer.valueOf(0);
				Integer one = Integer.valueOf(1);
				for (int i = 0; i < max; i++) {
					x = x + one;
				}
			}
		}, base);
		
		printTime("big integer", new Runnable() {
			public void run() {
				BigInteger x = BigInteger.ZERO;
				for (int i = 0; i < max; i++) {
					x = x.add(BigInteger.ONE);
				}
			}
		}, base);
		
		printTime("mutable int value object", new Runnable() {
			public void run() {
				MutableIntValue x = new MutableIntValue();
				for (int i = 0; i < max; i++) {
					x.increment();
				}
			}
		}, base);
		
		printTime("mutable int value object with returning function", new Runnable() {
			public void run() {
				MutableIntValue x = new MutableIntValue();
				for (int i = 0; i < max; i++) {
					x.incrementWithReturn();
				}
			}
		}, base);
		
		printTime("mutable int value with field access", new Runnable() {
			public void run() {
				MutableIntValue x = new MutableIntValue();
				for (int i = 0; i < max; i++) {
					x.value++;
				}
			}
		}, base);
		
		printTime("immutable int value object", new Runnable() {
			public void run() {
				ImmutableIntValue x = new ImmutableIntValue(0);
				for (int i = 0; i < max; i++) {
					x = x.increment();
				}
			}
		}, base);
		
		printTime("int", new Runnable() {
			public void run() {
				int x = 0;
				for (int i = 0; i < max; i++) {
					x = x + 1;
				}
			}
		}, base);
		
		printTime("mutable int value object with returning function", new Runnable() {
			public void run() {
				MutableIntValue x = new MutableIntValue();
				for (int i = 0; i < max; i++) {
					x.incrementWithReturn();
				}
			}
		}, base);
	}
	
	static class MutableIntValue {
		int value;
		void increment() {
			value++;
		}
		public MutableIntValue incrementWithReturn() {
			value++;
			return this;
		}
	}
	
	static class ImmutableIntValue {
		int value;
		public ImmutableIntValue(int value) {
			this.value = value;
		}
		ImmutableIntValue increment() {
			return new ImmutableIntValue(value + 1); 
		}
		
	}
	
	public static void printTimeHeader() {
		System.out.println("time        ratio    \tcase");
		System.out.println("----------------");
	}
	
	public static long printTime(String name, Runnable r, long base) {
		long t = System.nanoTime();
		r.run();
		long delta = System.nanoTime() - t;
		
		String multitude = base == 0 ? "" : BigDecimal.valueOf(delta * 1000 / base, 2).toString();
		
		System.out.println(String.format("%11s %5s   \t%s", 
				BigDecimal.valueOf(delta, 6).toString(), multitude, name));
		return delta;
	}
}
