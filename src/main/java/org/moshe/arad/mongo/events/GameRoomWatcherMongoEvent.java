package org.moshe.arad.mongo.events;

import java.util.Date;
import java.util.UUID;

import org.moshe.arad.entities.GameRoom;
import org.moshe.arad.kafka.events.UserAddedAsWatcherEvent;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="gameRoomsEvents")
public class GameRoomWatcherMongoEvent extends GameRoomMongoEvent {

	private String newWatcher;
	
	public GameRoomWatcherMongoEvent() {
	
	}

	public GameRoomWatcherMongoEvent(String newWatcher) {
		super();
		this.newWatcher = newWatcher;
	}

	public GameRoomWatcherMongoEvent(String mongoEventId, UUID uuid, int serviceId, int eventId, Date arrived,
			String clazz, GameRoom gameRoom, String newWatcher) {
		super(mongoEventId, uuid, serviceId, eventId, arrived, clazz, gameRoom);
		this.newWatcher = newWatcher;
	}

	public static GameRoomWatcherMongoEvent convertIntoMongoEvent(UserAddedAsWatcherEvent event) {
		GameRoomWatcherMongoEvent gameRoomMongoEvent = new GameRoomWatcherMongoEvent();
		
		gameRoomMongoEvent.setUuid(event.getUuid());
		gameRoomMongoEvent.setArrived(event.getArrived());
		gameRoomMongoEvent.setGameRoom(event.getGameRoom());
		gameRoomMongoEvent.setClazz(event.getClazz());
		gameRoomMongoEvent.setNewWatcher(event.getUsername());
		return gameRoomMongoEvent;
	}
	
	@Override
	public String toString() {
		return "GameRoomWatcherMongoEvent [newWatcher=" + newWatcher + "]";
	}

	public String getNewWatcher() {
		return newWatcher;
	}

	public void setNewWatcher(String newWatcher) {
		this.newWatcher = newWatcher;
	}
}
