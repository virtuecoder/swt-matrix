/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

class CommandListener implements Listener {
	ArrayList<GestureBinding> bindings = new ArrayList<GestureBinding>();
	protected Event event;

	public void attachTo(Control control) {
		control.addListener(SWT.KeyDown, this);
		control.addListener(SWT.KeyUp, this);
		control.addListener(SWT.MouseDown, this);
		control.addListener(SWT.MouseUp, this);
		control.addListener(SWT.MouseMove, this);
		control.addListener(SWT.MouseEnter, this);
		control.addListener(SWT.MouseExit, this);
		control.addListener(SWT.MouseHover, this);
		control.addListener(SWT.MouseDoubleClick, this);
		control.addListener(SWT.MouseWheel, this);
		control.addListener(SWT.Selection, this);
		control.addListener(SWT.DefaultSelection, this);
		control.addListener(SWT.FocusOut, this);
		control.addListener(SWT.FocusIn, this);
	}

	public void handleEvent(Event e) {
		event = e;
		if (e.widget.isDisposed()) return;
		for (GestureBinding b: bindings) {
			if (b.isMatching(e)) {
				executeCommand(b.commandId);
				/* does not quit loop because can execute
		   	   many both any zone and a specific zone bindings */
			}
		}
	}

	/**
	 * Binds the command to the user gesture specified by the event type and code.
	 * Code is a logical <i>OR</i> of key, state mask and mouse button codes.
	 */
	public void bind(int commandId, int eventType, int code) {
		bindings.add(new GestureBinding(commandId, eventType, code));
	}

	/**
	 * Removes the binding the command to the user gesture specified by the event type and code.
	 * Code is a logical <i>OR</i> of key, state mask and mouse button codes.
	 */
	public void unbind(int commandId, int eventType, int code) {
		for (int i = bindings.size(); i-- > 0;) {
			GestureBinding binding = bindings.get(i);
			if (binding.commandId == commandId
					&& binding.eventType == eventType
					&& binding.key == code) {
				bindings.remove(i);
			};
		}
	}

	protected void executeCommand(int commandId) {
	}



}
