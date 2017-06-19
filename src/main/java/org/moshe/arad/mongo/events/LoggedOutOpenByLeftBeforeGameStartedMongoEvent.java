package org.moshe.arad.mongo.events;

import java.util.Date;
import java.util.UUID;

import org.moshe.arad.entities.GameRoom;
import org.moshe.arad.kafka.events.LoggedOutOpenByLeftBeforeGameStartedEvent;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="LoggedOutOpenByLeftBeforeGameStartedEvents")
public class LoggedOutOpenByLeftBeforeGameStartedMongoEvent implements IMongoEvent {

	@Id
	private String mongoEventId;
	private UUID uuid;
	private int serviceId;
	private int eventId;
	private Date arrived;
	private String clazz;
	private String loggedOutUserName;
	private GameRoom gameRoom;

	public LoggedOutOpenByLeftBeforeGameStartedMongoEvent() {
		
	}

	public LoggedOutOpenByLeftBeforeGameStartedMongoEvent(String mongoEventId, UUID uuid, int serviceId, int eventId,
			Date arrived, String clazz, String loggedOutUserName, GameRoom gameRoom) {
		super();
		this.mongoEventId = mongoEventId;
		this.uuid = uuid;
		this.serviceId = serviceId;
		this.eventId = eventId;
		this.arrived = arrived;
		this.clazz = clazz;
		this.loggedOutUserName = loggedOutUserName;
		this.gameRoom = gameRoom;
	}

	@Override
	public String toString() {
		return "LoggedOutOpenByLeftBeforeGameStartedMongoEvent [mongoEventId=" + mongoEventId + ", uuid=" + uuid
				+ ", serviceId=" + serviceId + ", eventId=" + eventId + ", arrived=" + arrived + ", clazz=" + clazz
				+ ", loggedOutUserName=" + loggedOutUserName + ", gameRoom=" + gameRoom + "]";
	}

	public static LoggedOutOpenByLeftBeforeGameStartedMongoEvent convertIntoMongoEvent(LoggedOutOpenByLeftBeforeGameStartedEvent event) {
		LoggedOutOpenByLeftBeforeGameStartedMongoEvent mongoEvent = new LoggedOutOpenByLeftBeforeGameStartedMongoEvent();
		
		mongoEvent.setUuid(event.getUuid());
		mongoEvent.setArrived(event.getArrived());
		mongoEvent.setClazz(event.getClazz());
		mongoEvent.setLoggedOutUserName(event.getLoggedOutUserName());
		mongoEvent.setGameRoom(event.getGameRoom());
		return mongoEvent;
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

	@Override
	public Date getArrived() {
		return arrived;
	}

	@Override
	public void setArrived(Date arrived) {
		this.arrived = arrived;
	}
	
	@Override
	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getLoggedOutUserName() {
		return loggedOutUserName;
	}

	public void setLoggedOutUserName(String loggedOutUserName) {
		this.loggedOutUserName = loggedOutUserName;
	}

	public GameRoom getGameRoom() {
		return gameRoom;
	}

	public void setGameRoom(GameRoom gameRoom) {
		this.gameRoom = gameRoom;
	}
}
