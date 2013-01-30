/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;

import pl.netanel.util.Preconditions;


class GestureBinding {
	int commandId;
	int eventType;
	int key;
	boolean enabled;
	boolean isCharActivated;
  char character;
  Event event;

	public GestureBinding(int commandId, int eventType, int code) {
		Preconditions.checkArgument(commandId != 0, "CommandId cannot equal to zero");
		this.commandId = commandId;
		this.eventType = eventType;
		this.key = code;
		this.enabled = true;
	}

	public void setKey(int code) {
		this.key = code;
	}

	public boolean isMatching(Event e) {
	  if (enabled && eventType == e.type) {
  		if (key == (e.stateMask | e.keyCode | e.button)) {
  		  return true;
  		}
  		else {
    		isCharActivated = key == Matrix.PRINTABLE_CHARS && isPrintable(e.character) &&
    		  (e.stateMask == 0 || (e.stateMask & SWT.MOD2) != 0);
    		character = e.character;
    		return isCharActivated;
  		}
	  }
	  else {
	    return false;
	  }
	}

	public static boolean isPrintable(char c) {
	  int index = Arrays.binarySearch(FontSizeCache.PRINTABLE_CHARS, c);
	  return index >= 0;
	}

  public static boolean isMouseEvent(int type) {
    return SWT.MouseDown <= type && type <= SWT.MouseDoubleClick;
  }
}