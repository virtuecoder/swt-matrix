package pl.netanel.swt.matrix;

class LayoutSequence2<N1 extends Number, N2 extends Number> implements Sequence {
  AxisLayoutSequence<N1> seq1;
  AxisLayoutSequence<N2> seq2;
  private boolean empty;

  public LayoutSequence2(AxisLayoutSequence<N1> seq1, AxisLayoutSequence<N2> seq2) {
    this.seq1 = seq1;
    this.seq2 = seq2;
  }

  @Override
  public void init() {
    seq1.init();
    seq2.init();
    empty = !seq2.next();
  }

  @Override
  public boolean next() {
    if (empty) return false;
    if (seq1.next()) return true;
    if (seq2.next()) {
      seq1.init();
      return true;
    }
    return false;
  }

}
