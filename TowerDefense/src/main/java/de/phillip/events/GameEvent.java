package de.phillip.events;

import javafx.event.Event;
import javafx.event.EventType;

public class GameEvent extends Event {
	
	private static final long serialVersionUID = 5554001394529098895L;
	
	Object data;
	public static final EventType<GameEvent> ANY =
            new EventType<> (Event.ANY, "TD");
	
	public static final EventType<GameEvent> TD_START =
            new EventType<>(GameEvent.ANY, "TD_START");
	
	public static final EventType<GameEvent> TD_STARTWAVE =
            new EventType<>(GameEvent.ANY, "TD_STARTWAVE");

	public GameEvent(EventType<GameEvent> eventType, Object data) {
		super(eventType);
		this.data = data;
	}

	public Object getData() {
		return data;
	}
}
