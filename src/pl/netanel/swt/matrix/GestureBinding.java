package pl.netanel.swt.matrix;

import org.eclipse.swt.widgets.Event;

import pl.netanel.util.Preconditions;


class GestureBinding {
	int commandId;
	int eventType;
	int key;
	int zoneId;
	boolean enabled;

	public GestureBinding(int commandId, int eventType, int eventCode) {
		this(commandId, eventType, eventCode, Zone.ANY);
	}
	
	public GestureBinding(int commandId, int eventType, int code, int zoneId) {
		Preconditions.checkArgument(commandId != 0, "CommandId cannot equal to zero");
		this.commandId = commandId;
		this.eventType = eventType;
		this.key = code;
		this.zoneId = zoneId;
		this.enabled = true;
	}
	
	public void setKey(int code) {
		this.key = code;
	}

	public boolean isMatching(Event e, Zone zone) {
		return enabled && 
			eventType == e.type &&
			key == (e.stateMask | e.keyCode | e.button) && 
			(zoneId == Zone.ANY || zone == null || zone.is(zoneId));
	}
}