package pl.netanel.swt.matrix;

/**
 * Iterates over item indexes of a section according to their order and skipping the hidden ones.
 *
 * @author Jacek
 * @created 03-06-2012
 */
class ForwardSectionIndexSequence<N extends Number> implements Sequence {
  SectionCore<N> section;
  private final N start;
  private N index;
  private final int direction;

  /**
   * @param section
   * @param start
   * @param direction {@link Matrix#BACKWARD} OR {@link Matrix#FORWARD}
   */
  public ForwardSectionIndexSequence(SectionCore<N> section, N start, int direction) {
    this.section = section;
    this.start = start;
    this.direction = direction;
  }

  @Override
  public void init() {
    index = start;
  }

  @Override
  public boolean next() {
    return false;
  }


}