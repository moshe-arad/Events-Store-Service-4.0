package org.moshe.arad.mongo.events;

import java.util.Date;
import java.util.UUID;

import org.moshe.arad.entities.GameRoom;
import org.moshe.arad.kafka.events.GameRoomClosedEvent;
import org.moshe.arad.kafka.events.UserAddedAsWatcherEvent;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="gameRoomsEvents")
public class GameRoomClosedMongoEvent extends GameRoomMongoEvent {

	private String closedByUserName;
	
	public GameRoomClosedMongoEvent() {
	
	}

	public GameRoomClosedMongoEvent(String closedByUserName) {
		super();
		this.closedByUserName = closedByUserName;
	}

	public GameRoomClosedMongoEvent(String mongoEventId, UUID uuid, int serviceId, int eventId, Date arrived,
			String clazz, GameRoom gameRoom, String closedByUserName) {
		super(mongoEventId, uuid, serviceId, eventId, arrived, clazz, gameRoom);
		this.closedByUserName = closedByUserName;
	}

	public static GameRoomClosedMongoEvent convertIntoMongoEvent(GameRoomClosedEvent event) {
		GameRoomClosedMongoEvent gameRoomMongoEvent = new GameRoomClosedMongoEvent();
		
		gameRoomMongoEvent.setUuid(event.getUuid());
		gameRoomMongoEvent.setArrived(event.getArrived());
		gameRoomMongoEvent.setGameRoom(event.getGameRoom());
		gameRoomMongoEvent.setClazz(event.getClazz());
		gameRoomMongoEvent.setClosedByUserName(event.getClosedByUserName());
		return gameRoomMongoEvent;
	}

	@Override
	public String toString() {
		return "GameRoomClosedMongoEvent [closedByUserName=" + closedByUserName + "]";
	}

	public String getClosedByUserName() {
		return closedByUserName;
	}

	public void setClosedByUserName(String closedByUserName) {
		this.closedByUserName = closedByUserName;
	}
}
