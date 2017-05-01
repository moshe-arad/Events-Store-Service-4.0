package org.moshe.arad.mongo.events;

import java.util.Date;
import java.util.UUID;

import org.moshe.arad.entities.BackgammonUser;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="newUserJoinedLobbyEvents")
public class NewUserJoinedLobbyEvent extends MongoEvent{

	BackgammonUser backgammonUser;

	public NewUserJoinedLobbyEvent(String mongoEventId, UUID uuid, int serviceId, int eventId, Date arrived,
			BackgammonUser backgammonUser) {
		super(mongoEventId, uuid, serviceId, eventId, arrived);
		this.backgammonUser = backgammonUser;
	}

	@Override
	public String toString() {
		return "NewUserJoinedLobbyEvent [backgammonUser=" + backgammonUser + "]";
	}

	public BackgammonUser getBackgammonUser() {
		return backgammonUser;
	}

	public void setBackgammonUser(BackgammonUser backgammonUser) {
		this.backgammonUser = backgammonUser;
	}
}
