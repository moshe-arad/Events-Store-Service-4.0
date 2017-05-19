package org.moshe.arad.mongo.events;

import java.util.Date;
import java.util.UUID;

import org.moshe.arad.entities.GameRoom;
import org.moshe.arad.kafka.events.GameRoomClosedEvent;
import org.moshe.arad.kafka.events.NewGameRoomOpenedEvent;
import org.moshe.arad.kafka.events.UserAddedAsWatcherEvent;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="gameRoomsEvents")
public class GameRoomMongoEvent implements IMongoEvent{

	@Id
	private String mongoEventId;
	private UUID uuid;
	private int serviceId;
	private int eventId;
	private Date arrived;
	private String clazz;
	private GameRoom gameRoom;
	
	public GameRoomMongoEvent() {
	
	}
	
	public GameRoomMongoEvent(String mongoEventId, UUID uuid, int serviceId, int eventId, Date arrived, String clazz,
			GameRoom gameRoom) {
		super();
		this.mongoEventId = mongoEventId;
		this.uuid = uuid;
		this.serviceId = serviceId;
		this.eventId = eventId;
		this.arrived = arrived;
		this.clazz = clazz;
		this.gameRoom = gameRoom;
	}

	@Override
	public String toString() {
		return "GameRoomMongoEvent [mongoEventId=" + mongoEventId + ", uuid=" + uuid + ", serviceId=" + serviceId
				+ ", eventId=" + eventId + ", arrived=" + arrived + ", clazz=" + clazz + ", gameRoom=" + gameRoom + "]";
	}

	public static GameRoomMongoEvent convertIntoMongoEvent(NewGameRoomOpenedEvent event) {
		GameRoomMongoEvent gameRoomMongoEvent = new GameRoomMongoEvent();
		
		gameRoomMongoEvent.setUuid(event.getUuid());
		gameRoomMongoEvent.setArrived(event.getArrived());
		gameRoomMongoEvent.setGameRoom(event.getGameRoom());
		gameRoomMongoEvent.setClazz(event.getClazz());
		
		return gameRoomMongoEvent;
	}
	
	public static GameRoomMongoEvent convertIntoMongoEvent(GameRoomClosedEvent event) {
		GameRoomMongoEvent gameRoomMongoEvent = new GameRoomMongoEvent();;
		
		gameRoomMongoEvent.setUuid(event.getUuid());
		gameRoomMongoEvent.setArrived(event.getArrived());
		gameRoomMongoEvent.setGameRoom(event.getGameRoom());
		gameRoomMongoEvent.setClazz(event.getClazz());
		
		return gameRoomMongoEvent;
	}	

	public String getMongoEventId() {
		return mongoEventId;
	}

	public void setMongoEventId(String mongoEventId) {
		this.mongoEventId = mongoEventId;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public Date getArrived() {
		return arrived;
	}

	public void setArrived(Date arrived) {
		this.arrived = arrived;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public GameRoom getGameRoom() {
		return gameRoom;
	}

	public void setGameRoom(GameRoom gameRoom) {
		this.gameRoom = gameRoom;
	}	
}
