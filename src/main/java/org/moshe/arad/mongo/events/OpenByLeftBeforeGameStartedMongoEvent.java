package org.moshe.arad.mongo.events;

import java.util.Date;
import java.util.UUID;

import org.moshe.arad.entities.GameRoom;
import org.moshe.arad.kafka.events.LoggedOutOpenByLeftBeforeGameStartedEvent;
import org.moshe.arad.kafka.events.OpenByLeftBeforeGameStartedEvent;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="OpenByLeftBeforeGameStartedEvents")
public class OpenByLeftBeforeGameStartedMongoEvent implements IMongoEvent {

	@Id
	private String mongoEventId;
	private UUID uuid;
	private int serviceId;
	private int eventId;
	private Date arrived;
	private String clazz;
	private String leavingUserName;
	private GameRoom gameRoom;

	public OpenByLeftBeforeGameStartedMongoEvent() {
		
	}

	public OpenByLeftBeforeGameStartedMongoEvent(String mongoEventId, UUID uuid, int serviceId, int eventId,
			Date arrived, String clazz, String leavingUserName, GameRoom gameRoom) {
		super();
		this.mongoEventId = mongoEventId;
		this.uuid = uuid;
		this.serviceId = serviceId;
		this.eventId = eventId;
		this.arrived = arrived;
		this.clazz = clazz;
		this.leavingUserName = leavingUserName;
		this.gameRoom = gameRoom;
	}
	
	@Override
	public String toString() {
		return "OpenByLeftBeforeGameStartedMongoEvent [mongoEventId=" + mongoEventId + ", uuid=" + uuid + ", serviceId="
				+ serviceId + ", eventId=" + eventId + ", arrived=" + arrived + ", clazz=" + clazz
				+ ", leavingUserName=" + leavingUserName + ", gameRoom=" + gameRoom + "]";
	}

	public static OpenByLeftBeforeGameStartedMongoEvent convertIntoMongoEvent(OpenByLeftBeforeGameStartedEvent event) {
		OpenByLeftBeforeGameStartedMongoEvent mongoEvent = new OpenByLeftBeforeGameStartedMongoEvent();
		
		mongoEvent.setUuid(event.getUuid());
		mongoEvent.setArrived(event.getArrived());
		mongoEvent.setClazz(event.getClazz());
		mongoEvent.setLeavingUserName(event.getLeavingUserName());
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

	public String getLeavingUserName() {
		return leavingUserName;
	}

	public void setLeavingUserName(String leavingUserName) {
		this.leavingUserName = leavingUserName;
	}

	public GameRoom getGameRoom() {
		return gameRoom;
	}

	public void setGameRoom(GameRoom gameRoom) {
		this.gameRoom = gameRoom;
	}
}
