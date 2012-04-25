package pl.netanel.swt.matrix;

import org.junit.Test;

public class ExtentSequenceTest {
  @Test
  public void difference() {
    IntMath math = IntMath.getInstance();
    NumberQueueSet selected = new NumberQueueSet(math);
    NumberSet hidden = new NumberSet(math);
    ExtentSequence seq = ExtentSequence.difference(selected, hidden);
  }
}
