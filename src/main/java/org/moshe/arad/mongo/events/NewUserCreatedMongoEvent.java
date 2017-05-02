package org.moshe.arad.mongo.events;

import java.util.Date;
import java.util.UUID;

import org.moshe.arad.entities.BackgammonUser;
import org.moshe.arad.kafka.events.NewUserCreatedEvent;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="newUserCreatedEvents")
public class NewUserCreatedMongoEvent implements IMongoEvent {

	@Id
	private String mongoEventId;
	private UUID uuid;
	private int serviceId;
	private int eventId;
	private Date arrived;
	private BackgammonUser backgammonUser;

	public NewUserCreatedMongoEvent() {
	
	}
	
	public NewUserCreatedMongoEvent(String mongoEventId, UUID uuid, int serviceId, int eventId, Date arrived,
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
		return "NewUserCreatedMongoEvent [mongoEventId=" + mongoEventId + ", uuid=" + uuid + ", serviceId=" + serviceId
				+ ", eventId=" + eventId + ", arrived=" + arrived + ", backgammonUser=" + backgammonUser + "]";
	}

	public static NewUserCreatedMongoEvent convertIntoMongoEvent(NewUserCreatedEvent event) {
		NewUserCreatedMongoEvent newUserCreatedMongoEvent = new NewUserCreatedMongoEvent();
		
		newUserCreatedMongoEvent.setUuid(event.getUuid());
		newUserCreatedMongoEvent.setArrived(event.getArrived());
		newUserCreatedMongoEvent.setBackgammonUser(event.getBackgammonUser());
		
		return newUserCreatedMongoEvent;
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
}
