package org.moshe.arad.mongo.events;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="events")
public class MongoEvent {

	@Id
	private String mongoEventId;
	
	@Field(value="createdDate")
	private Date createdDate;
	
	@Field(value="arrivedDate")
	private Date arrivedDate;
	
	@Field(value="eventId")
	private int eventId;
	
	public MongoEvent() {
	
	}
	
	public MongoEvent(String mongoEventId, Date createdDate, Date arrivedDate, int eventId) {
		this.mongoEventId = mongoEventId;
		this.createdDate = createdDate;
		this.arrivedDate = arrivedDate;
		this.eventId = eventId;
	}

	@Override
	public String toString() {
		return "MongoEvent [mongoEventId=" + mongoEventId + ", createdDate=" + createdDate + ", arrivedDate="
				+ arrivedDate + ", eventId=" + eventId + "]";
	}

	public String getMongoEventId() {
		return mongoEventId;
	}

	public void setMongoEventId(String mongoEventId) {
		this.mongoEventId = mongoEventId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getArrivedDate() {
		return arrivedDate;
	}

	public void setArrivedDate(Date arrivedDate) {
		this.arrivedDate = arrivedDate;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}	
}
