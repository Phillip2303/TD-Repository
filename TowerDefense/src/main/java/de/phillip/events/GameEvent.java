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
	
	public static final EventType<GameEvent> TD_PLACETURRET = 
			new EventType<>(GameEvent.ANY, "TD_PLACETURRET");
	
	public static final EventType<GameEvent> TD_NEXTLEVEL =
			new EventType<>(GameEvent.ANY, "TD_NEXTLEVEL");
	
	public static final EventType<GameEvent> TD_REMOVETURRET = 
			new EventType<>(GameEvent.ANY, "TD_REMOVETURRET");
	
	public static final EventType<GameEvent> TD_LOST = 
			new EventType<>(GameEvent.ANY, "TD_LOST");
	
	public static final EventType<GameEvent> TD_REPEAT =
			new EventType<>(GameEvent.ANY, "TD_REPEAT");
	
	public static final EventType<GameEvent> TD_SHOWUPGRADE =
			new EventType<>(GameEvent.ANY, "TD_SHOWUPGRADE");
	
	public static final EventType<GameEvent> TD_UPGRADE =
			new EventType<>(GameEvent.ANY, "TD_UPGRADE");

	public GameEvent(EventType<GameEvent> eventType, Object data) {
		super(eventType);
		this.data = data;
	}

	public Object getData() {
		return data;
	}
}
