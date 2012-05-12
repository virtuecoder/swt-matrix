package pl.netanel.swt.matrix;

public class MultiSequence implements Sequence {

  private final Sequence[] seq;

  public MultiSequence(Sequence ...seq) {
    this.seq = seq;
  }

  @Override
  public void init() {
    for (int i = seq.length; i-- > 0;) {
      seq[i].init();
    }
  }

  @Override
  public boolean next() {

    return false;
  }

}
