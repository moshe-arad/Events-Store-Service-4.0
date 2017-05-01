package org.moshe.arad.mongo.events;

import java.util.Date;
import java.util.UUID;

import org.moshe.arad.entities.BackgammonUser;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="newUserCreatedEvents")
public class NewUserCreatedEvent extends MongoEvent {

	BackgammonUser backgammonUser;

	public NewUserCreatedEvent(String mongoEventId, UUID uuid, int serviceId, int eventId, Date arrived,
		BackgammonUser backgammonUser) {
		super(mongoEventId, uuid, serviceId, eventId, arrived);
		this.backgammonUser = backgammonUser;
	}

	public BackgammonUser getBackgammonUser() {
		return backgammonUser;
	}

	public void setBackgammonUser(BackgammonUser backgammonUser) {
		this.backgammonUser = backgammonUser;
	}	
}
