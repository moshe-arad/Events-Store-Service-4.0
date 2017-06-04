package org.moshe.arad.mongo.events;

import java.util.Date;
import java.util.UUID;

import org.moshe.arad.entities.GameRoom;
import org.moshe.arad.kafka.events.UserAddedAsSecondPlayerEvent;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="gameRoomsEvents")
public class AddSecondPlayerMongoEvent extends GameRoomMongoEvent {

	private String secondPlayer;
	
	public AddSecondPlayerMongoEvent() {
	
	}

	public AddSecondPlayerMongoEvent(String removedWatcher) {
		super();
		this.secondPlayer = removedWatcher;
	}

	public AddSecondPlayerMongoEvent(String mongoEventId, UUID uuid, int serviceId, int eventId, Date arrived,
			String clazz, GameRoom gameRoom, String removedWatcher) {
		super(mongoEventId, uuid, serviceId, eventId, arrived, clazz, gameRoom);
		this.secondPlayer = removedWatcher;
	}

	@Override
	public String toString() {
		return "WatcherRemovedMongoEvent [removedWatcher=" + secondPlayer + "]";
	}

	public String getSecondPlayer() {
		return secondPlayer;
	}

	public void setSecondPlayer(String secondPlayer) {
		this.secondPlayer = secondPlayer;
	}

	public static AddSecondPlayerMongoEvent convertIntoMongoEvent(UserAddedAsSecondPlayerEvent event) {
		AddSecondPlayerMongoEvent gameRoomMongoEvent = new AddSecondPlayerMongoEvent();
		
		gameRoomMongoEvent.setUuid(event.getUuid());
		gameRoomMongoEvent.setArrived(event.getArrived());
		gameRoomMongoEvent.setGameRoom(event.getGameRoom());
		gameRoomMongoEvent.setClazz(event.getClazz());
		gameRoomMongoEvent.setSecondPlayer(event.getUsername());
		return gameRoomMongoEvent;
	}
}
