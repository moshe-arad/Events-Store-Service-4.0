package org.moshe.arad.mongo.events;

import java.util.Date;
import java.util.UUID;

import org.moshe.arad.entities.GameRoom;
import org.moshe.arad.kafka.events.GameRoomClosedEvent;
import org.moshe.arad.kafka.events.UserAddedAsWatcherEvent;
import org.moshe.arad.kafka.events.WatcherRemovedEvent;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="gameRoomsEvents")
public class WatcherRemovedMongoEvent extends GameRoomMongoEvent {

	private String removedWatcher;
	
	public WatcherRemovedMongoEvent() {
	
	}

	public WatcherRemovedMongoEvent(String removedWatcher) {
		super();
		this.removedWatcher = removedWatcher;
	}

	public WatcherRemovedMongoEvent(String mongoEventId, UUID uuid, int serviceId, int eventId, Date arrived,
			String clazz, GameRoom gameRoom, String removedWatcher) {
		super(mongoEventId, uuid, serviceId, eventId, arrived, clazz, gameRoom);
		this.removedWatcher = removedWatcher;
	}

	@Override
	public String toString() {
		return "WatcherRemovedMongoEvent [removedWatcher=" + removedWatcher + "]";
	}

	public String getRemovedWatcher() {
		return removedWatcher;
	}

	public void setRemovedWatcher(String removedWatcher) {
		this.removedWatcher = removedWatcher;
	}

	

	public static WatcherRemovedMongoEvent convertIntoMongoEvent(WatcherRemovedEvent event) {
		WatcherRemovedMongoEvent gameRoomMongoEvent = new WatcherRemovedMongoEvent();
		
		gameRoomMongoEvent.setUuid(event.getUuid());
		gameRoomMongoEvent.setArrived(event.getArrived());
		gameRoomMongoEvent.setGameRoom(event.getGameRoom());
		gameRoomMongoEvent.setClazz(event.getClazz());
		gameRoomMongoEvent.setRemovedWatcher(event.getRemovedWatcher());
		return gameRoomMongoEvent;
	}
}
