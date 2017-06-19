package org.moshe.arad.mongo.events;

import java.util.Date;
import java.util.UUID;

import org.moshe.arad.entities.GameRoom;
import org.moshe.arad.kafka.events.UserAddedAsWatcherEvent;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="UserAddedAsWatcherEvents")
public class UserAddedAsWatcherMongoEvent implements IMongoEvent {

	@Id
	private String mongoEventId;
	private UUID uuid;
	private int serviceId;
	private int eventId;
	private Date arrived;
	private String clazz;
	private GameRoom gameRoom;
	private String newWatcher;
	
	public UserAddedAsWatcherMongoEvent() {
	
	}

	public UserAddedAsWatcherMongoEvent(String mongoEventId, UUID uuid, int serviceId, int eventId, Date arrived,
			String clazz, GameRoom gameRoom, String newWatcher) {
		super();
		this.mongoEventId = mongoEventId;
		this.uuid = uuid;
		this.serviceId = serviceId;
		this.eventId = eventId;
		this.arrived = arrived;
		this.clazz = clazz;
		this.gameRoom = gameRoom;
		this.newWatcher = newWatcher;
	}

	public static UserAddedAsWatcherMongoEvent convertIntoMongoEvent(UserAddedAsWatcherEvent event) {
		UserAddedAsWatcherMongoEvent gameRoomMongoEvent = new UserAddedAsWatcherMongoEvent();
		
		gameRoomMongoEvent.setUuid(event.getUuid());
		gameRoomMongoEvent.setArrived(event.getArrived());
		gameRoomMongoEvent.setGameRoom(event.getGameRoom());
		gameRoomMongoEvent.setClazz(event.getClazz());
		gameRoomMongoEvent.setNewWatcher(event.getUsername());
		return gameRoomMongoEvent;
	}

	@Override
	public String toString() {
		return "UserAddedAsWatcherMongoEvent [mongoEventId=" + mongoEventId + ", uuid=" + uuid + ", serviceId="
				+ serviceId + ", eventId=" + eventId + ", arrived=" + arrived + ", clazz=" + clazz + ", gameRoom="
				+ gameRoom + ", newWatcher=" + newWatcher + "]";
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

	public String getNewWatcher() {
		return newWatcher;
	}

	public void setNewWatcher(String newWatcher) {
		this.newWatcher = newWatcher;
	}
}
