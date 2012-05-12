package pl.netanel.swt.matrix;

import static org.junit.Assert.*;

import org.junit.Test;

public class SequencesTest {
  @Test
  public void test() throws Exception {
    IntSequence seq1 = new IntSequence(3);
    IntSequence seq2 = new IntSequence(2);
    MultiSequence seq = new MultiSequence(seq1, seq2);
  }

  class IntSequence implements Sequence {

    private final int count;
    int index;

    public IntSequence(int count) {
      this.count = count;
    }

    @Override
    public void init() {
      index = 0;
    }

    @Override
    public boolean next() {
      return index++ < count;
    }

  }
}
