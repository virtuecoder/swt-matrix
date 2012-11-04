/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import java.util.List;

abstract class AxisNavigationSequence<N extends Number> extends AxisSequence<N>  {

  protected AxisNavigationSequence(List<SectionCore<N>> sections) {
    super(sections);
  }

  public static class Forward<N2 extends Number> extends AxisSequence.Forward<N2> {
    public Forward(List<SectionCore<N2>> sections) {
      super(sections);
    }

    @Override
    protected boolean isSectionToSkip() {
      return !section.isVisible() || !section.isFocusItemEnabled() || section.isEmpty();
    }
  }

  public static class Backward<N2 extends Number> extends AxisSequence.Backward<N2> {
    public Backward(List<SectionCore<N2>> sections) {
      super(sections);
    }

    @Override
    protected boolean isSectionToSkip() {
      return !section.isVisible() || !section.isFocusItemEnabled() || section.isEmpty();
    }
  }

}
