package org.moshe.arad.mongo.events;

import java.util.Date;
import java.util.UUID;

import org.moshe.arad.entities.BackgammonUser;
import org.moshe.arad.entities.GameRoom;
import org.moshe.arad.kafka.events.ExistingUserJoinedLobbyEvent;
import org.moshe.arad.kafka.events.LoggedInEvent;
import org.moshe.arad.kafka.events.LoggedOutEvent;
import org.moshe.arad.kafka.events.LoggedOutWatcherLeftLastEvent;
import org.moshe.arad.kafka.events.NewUserCreatedEvent;
import org.moshe.arad.kafka.events.NewUserJoinedLobbyEvent;
import org.moshe.arad.kafka.events.UserPermissionsUpdatedEvent;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="LoggedOutWatcherLeftLastEvents")
public class LoggedOutWatcherLeftLastMongoEvent implements IMongoEvent {

	@Id
	private String mongoEventId;
	private UUID uuid;
	private int serviceId;
	private int eventId;
	private Date arrived;
	private String clazz;
	private String watcher;
	private GameRoom gameRoom;

	public LoggedOutWatcherLeftLastMongoEvent() {
		
	}

	public LoggedOutWatcherLeftLastMongoEvent(String mongoEventId, UUID uuid, int serviceId, int eventId, Date arrived,
			String clazz, String watcher, GameRoom gameRoom) {
		super();
		this.mongoEventId = mongoEventId;
		this.uuid = uuid;
		this.serviceId = serviceId;
		this.eventId = eventId;
		this.arrived = arrived;
		this.clazz = clazz;
		this.watcher = watcher;
		this.gameRoom = gameRoom;
	}

	@Override
	public String toString() {
		return "LoggedOutWatcherLeftLastMongoEvent [mongoEventId=" + mongoEventId + ", uuid=" + uuid + ", serviceId="
				+ serviceId + ", eventId=" + eventId + ", arrived=" + arrived + ", clazz=" + clazz + ", watcher="
				+ watcher + ", gameRoom=" + gameRoom + "]";
	}

	public static LoggedOutWatcherLeftLastMongoEvent convertIntoMongoEvent(LoggedOutWatcherLeftLastEvent event) {
		LoggedOutWatcherLeftLastMongoEvent loggedOutWatcherLeftLastMongoEvent = new LoggedOutWatcherLeftLastMongoEvent();
		
		loggedOutWatcherLeftLastMongoEvent.setUuid(event.getUuid());
		loggedOutWatcherLeftLastMongoEvent.setArrived(event.getArrived());
		loggedOutWatcherLeftLastMongoEvent.setClazz(event.getClazz());
		loggedOutWatcherLeftLastMongoEvent.setWatcher(event.getWatcher());
		loggedOutWatcherLeftLastMongoEvent.setGameRoom(event.getGameRoom());
		
		return loggedOutWatcherLeftLastMongoEvent;
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

	public String getWatcher() {
		return watcher;
	}

	public void setWatcher(String watcher) {
		this.watcher = watcher;
	}

	public GameRoom getGameRoom() {
		return gameRoom;
	}

	public void setGameRoom(GameRoom gameRoom) {
		this.gameRoom = gameRoom;
	}
}
