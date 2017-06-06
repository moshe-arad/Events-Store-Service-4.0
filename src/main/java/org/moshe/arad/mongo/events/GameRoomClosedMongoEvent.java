package org.moshe.arad.mongo.events;

import java.util.Date;
import java.util.UUID;

import org.moshe.arad.entities.GameRoom;
import org.moshe.arad.kafka.events.GameRoomClosedEvent;
import org.moshe.arad.kafka.events.NewGameRoomOpenedEvent;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="GameRoomClosedEvents")
public class GameRoomClosedMongoEvent implements IMongoEvent{

	@Id
	private String mongoEventId;
	private UUID uuid;
	private int serviceId;
	private int eventId;
	private Date arrived;
	private String clazz;
	private String closedByUserName;
	private GameRoom gameRoom;
	
	public GameRoomClosedMongoEvent() {
	
	}

	public GameRoomClosedMongoEvent(String mongoEventId, UUID uuid, int serviceId, int eventId, Date arrived,
			String clazz, String closedByUserName, GameRoom gameRoom) {
		super();
		this.mongoEventId = mongoEventId;
		this.uuid = uuid;
		this.serviceId = serviceId;
		this.eventId = eventId;
		this.arrived = arrived;
		this.clazz = clazz;
		this.closedByUserName = closedByUserName;
		this.gameRoom = gameRoom;
	}

	@Override
	public String toString() {
		return "GameRoomClosedMongoEvent [mongoEventId=" + mongoEventId + ", uuid=" + uuid + ", serviceId=" + serviceId
				+ ", eventId=" + eventId + ", arrived=" + arrived + ", clazz=" + clazz + ", closedByUserName="
				+ closedByUserName + ", gameRoom=" + gameRoom + "]";
	}

	public static GameRoomClosedMongoEvent convertIntoMongoEvent(GameRoomClosedEvent event) {
		GameRoomClosedMongoEvent gameRoomClosedMongoEvent = new GameRoomClosedMongoEvent();
		
		gameRoomClosedMongoEvent.setUuid(event.getUuid());
		gameRoomClosedMongoEvent.setArrived(event.getArrived());
		gameRoomClosedMongoEvent.setGameRoom(event.getGameRoom());
		gameRoomClosedMongoEvent.setClosedByUserName(event.getLoggedOutUserName());
		gameRoomClosedMongoEvent.setClazz(event.getClazz());
		
		return gameRoomClosedMongoEvent;
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

	public String getClosedByUserName() {
		return closedByUserName;
	}

	public void setClosedByUserName(String closedByUserName) {
		this.closedByUserName = closedByUserName;
	}
}
