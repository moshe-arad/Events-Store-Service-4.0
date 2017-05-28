package org.moshe.arad.mongo.events;

import java.util.Date;
import java.util.UUID;

import org.moshe.arad.entities.BackgammonUser;
import org.moshe.arad.kafka.events.ExistingUserJoinedLobbyEvent;
import org.moshe.arad.kafka.events.LoggedInEvent;
import org.moshe.arad.kafka.events.LoggedOutEvent;
import org.moshe.arad.kafka.events.NewUserCreatedEvent;
import org.moshe.arad.kafka.events.NewUserJoinedLobbyEvent;
import org.moshe.arad.kafka.events.UserPermissionsUpdatedEvent;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="userEvents")
public class UserMongoEvent implements IMongoEvent {

	@Id
	private String mongoEventId;
	private UUID uuid;
	private int serviceId;
	private int eventId;
	private Date arrived;
	private String clazz;
	private BackgammonUser backgammonUser;

	public UserMongoEvent() {
		
	}

	public UserMongoEvent(String mongoEventId, UUID uuid, int serviceId, int eventId, Date arrived,
			BackgammonUser backgammonUser) {
		super();
		this.mongoEventId = mongoEventId;
		this.uuid = uuid;
		this.serviceId = serviceId;
		this.eventId = eventId;
		this.arrived = arrived;
		this.backgammonUser = backgammonUser;
	}

	@Override
	public String toString() {
		return "LoggedInMongoEvent [mongoEventId=" + mongoEventId + ", uuid=" + uuid + ", serviceId=" + serviceId
				+ ", eventId=" + eventId + ", arrived=" + arrived + ", backgammonUser=" + backgammonUser + "]";
	}
	
	public static UserMongoEvent convertIntoMongoEvent(LoggedInEvent event) {
		UserMongoEvent loggedInMongoEvent = new UserMongoEvent();
		
		loggedInMongoEvent.setUuid(event.getUuid());
		loggedInMongoEvent.setArrived(event.getArrived());
		loggedInMongoEvent.setBackgammonUser(event.getBackgammonUser());
		loggedInMongoEvent.setClazz(event.getClazz());
		
		return loggedInMongoEvent;
	}

	public static UserMongoEvent convertIntoMongoEvent(ExistingUserJoinedLobbyEvent event) {
		UserMongoEvent existingUserJoinedLobbyMongoEvent = new UserMongoEvent();
		
		existingUserJoinedLobbyMongoEvent.setUuid(event.getUuid());
		existingUserJoinedLobbyMongoEvent.setArrived(event.getArrived());
		existingUserJoinedLobbyMongoEvent.setBackgammonUser(event.getBackgammonUser());
		existingUserJoinedLobbyMongoEvent.setClazz(event.getClazz());
		
		return existingUserJoinedLobbyMongoEvent;
	}
	
	public static UserMongoEvent convertIntoMongoEvent(LoggedOutEvent event) {
		UserMongoEvent loggedOutMongoEvent = new UserMongoEvent();
		
		loggedOutMongoEvent.setUuid(event.getUuid());
		loggedOutMongoEvent.setArrived(event.getArrived());
		loggedOutMongoEvent.setBackgammonUser(event.getBackgammonUser());
		loggedOutMongoEvent.setClazz(event.getClazz());
		
		return loggedOutMongoEvent;
	}
	
	public static UserMongoEvent convertIntoMongoEvent(NewUserCreatedEvent event) {
		UserMongoEvent newUserCreatedMongoEvent = new UserMongoEvent();
		
		newUserCreatedMongoEvent.setUuid(event.getUuid());
		newUserCreatedMongoEvent.setArrived(event.getArrived());
		newUserCreatedMongoEvent.setBackgammonUser(event.getBackgammonUser());
		newUserCreatedMongoEvent.setClazz(event.getClazz());
		
		return newUserCreatedMongoEvent;
	}
	
	public static UserMongoEvent convertIntoMongoEvent(NewUserJoinedLobbyEvent event) {
		UserMongoEvent newUserJoinedLobbyMongoEvent = new UserMongoEvent();
		
		newUserJoinedLobbyMongoEvent.setUuid(event.getUuid());
		newUserJoinedLobbyMongoEvent.setArrived(event.getArrived());
		newUserJoinedLobbyMongoEvent.setBackgammonUser(event.getBackgammonUser());
		newUserJoinedLobbyMongoEvent.setClazz(event.getClazz());
		
		return newUserJoinedLobbyMongoEvent;
	}
	
	public static UserMongoEvent convertIntoMongoEvent(UserPermissionsUpdatedEvent event) {
		UserMongoEvent userPermissionsUpdatedEvent = new UserMongoEvent();
		
		userPermissionsUpdatedEvent.setUuid(event.getUuid());
		userPermissionsUpdatedEvent.setArrived(event.getArrived());
		userPermissionsUpdatedEvent.setBackgammonUser(event.getBackgammonUser());
		userPermissionsUpdatedEvent.setClazz(event.getClazz());
		
		return userPermissionsUpdatedEvent;
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

	public BackgammonUser getBackgammonUser() {
		return backgammonUser;
	}

	public void setBackgammonUser(BackgammonUser backgammonUser) {
		this.backgammonUser = backgammonUser;
	}
	
	@Override
	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
}
