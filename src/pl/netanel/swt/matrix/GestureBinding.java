package pl.netanel.swt.matrix;

import org.eclipse.swt.widgets.Event;

import pl.netanel.util.Preconditions;


class GestureBinding {
	int commandId;
	int eventType;
	int key;
	boolean enabled;

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
		return enabled && 
			eventType == e.type &&
			key == (e.stateMask | e.keyCode | e.button); 
	}
}