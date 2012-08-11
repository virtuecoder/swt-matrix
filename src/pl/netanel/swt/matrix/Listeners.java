/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA_v1.0.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TypedListener;

import pl.netanel.util.HashMapArrayList;
import pl.netanel.util.Preconditions;

class Listeners {
	final private HashMapArrayList<Integer, Listener> listeners;
	final private ArrayList<Event> events;

	public Listeners() {
		events = new ArrayList<Event>();
		listeners = new HashMapArrayList<Integer, Listener>();
	}

	public void add(int type, Listener listener) {
		Preconditions.checkNotNullWithName(listener, "listener");
		listeners.add(type, listener);
	}

	public void remove(int type, Object listener) {
		Preconditions.checkNotNullWithName(listener, "listener");
		List<Listener> list = listeners.get(type);
		for (int i = list.size(); i-- > 0;) {
			Listener listener2 = list.get(i);
			if (listener2 != listener && listener2 instanceof TypedListener) {
				listener2 = (Listener) ((TypedListener) listener2).getEventListener();
			}
			if (listener2 == listener) {
				list.remove(i);
				return;
			}
		}
	}

	public void add(Event event) {
		events.add(event);
	}

	public void sendEvents() {
    @SuppressWarnings("unchecked")
    ArrayList<Event> copy = (ArrayList<Event>) events.clone();
		events.clear();
		for (Event e: copy) {
			sendEvent(e);
		}
	}

	public void sendEvent(Event e) {
		for (Listener listener: listeners.get(e.type)) {
			listener.handleEvent(e);
		}
	}
}
